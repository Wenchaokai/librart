package com.best.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.best.domain.Field;
import com.best.service.FieldService;
import com.best.utils.CommonUtils;

/**
 * ClassName:FieldController Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-31
 */
@Controller
public class FieldController {
	@Autowired
	private FieldService fieldService;

	@RequestMapping(value = "/field/field-index.do")
	public String fieldIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		// String searchFieldType = req.getParameter("searchFieldType");
		String searchFieldName = req.getParameter("searchFieldName");
		String pageString = req.getParameter("currentPage");
		List<Field> allFields = fieldService.listAllTypeInField();

		List<Field> res = new ArrayList<Field>();

		for (Field field : allFields) {
			if (StringUtils.isNotBlank(searchFieldName)) {
				if (field.getFieldName().toLowerCase().contains(searchFieldName.toLowerCase()))
					;
				else
					continue;
			}
			res.add(field);
		}

		String page = req.getParameter("page");
		if (null == page) {
			page = "1";
		}
		int currentPage = Integer.parseInt(page.trim());
		if (currentPage < 1)
			currentPage = 1;

		int totalSize = res.size() % CommonUtils.pageSize == 0 ? res.size() / CommonUtils.pageSize : (res.size()
				/ CommonUtils.pageSize + 1);

		if (currentPage > totalSize)
			currentPage = totalSize;

		int start = (currentPage - 1) * CommonUtils.pageSize;
		if (start < 0)
			start = 0;
		int end = start + CommonUtils.pageSize;
		if (end > res.size())
			end = res.size();

		int pStart = currentPage - 2;
		if (pStart < 1)
			pStart = 1;

		int pEnd = pStart + 4;
		if (pEnd > totalSize)
			pEnd = totalSize;

		int pPre = currentPage - 1;
		if (pPre < 1)
			pPre = 1;

		int pNext = currentPage + 1;
		if (pNext > totalSize)
			pNext = totalSize;

		model.addAttribute("pStart", pStart);
		model.addAttribute("pEnd", pEnd);
		model.addAttribute("pPre", pPre);
		model.addAttribute("pNext", pNext);
		if (end < start)
			model.addAttribute("fieldRes", new ArrayList<Field>());
		else
			model.addAttribute("fieldRes", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchFieldName", searchFieldName == null ? "" : searchFieldName.trim());

		return "field/field-index";
	}

	@RequestMapping(value = "/field/field-add-view.do")
	public String fieldAddView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		return "field/field-add-view";
	}

	@RequestMapping(value = "/field/field-add.do")
	public String fieldAdd(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldType = req.getParameter("fieldType");

		String fieldName = req.getParameter("fieldName");
		String fieldDesc = req.getParameter("fieldDesc");
		String fieldUnit = req.getParameter("fieldUnit");
		String fieldPeriod = req.getParameter("fieldPeriod");

		Field field = new Field();
		field.setFieldType(fieldType == null ? 0 : Integer.parseInt(fieldType.split("#")[0]));
		field.setFieldDesc(fieldDesc == null ? "" : fieldDesc.trim());
		field.setFieldName(fieldName == null ? "" : fieldName.trim());
		field.setFieldPeriod(fieldPeriod == null ? 0 : Integer.parseInt(fieldPeriod));
		field.setFieldTypeName(fieldType == null ? "录入字段" : fieldType.split("#")[1]);
		field.setFieldUnit(fieldUnit == null ? "" : fieldUnit.trim());

		fieldService.addField(field);

		return "redirect:/field/field-index.do";
	}

	@RequestMapping(value = "/field/field-update-view.do")
	public String fieldUpdateView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldId = req.getParameter("fieldId");
		if (StringUtils.isEmpty(fieldId))
			throw new IOException("没有输入fieldId");

		Field field = fieldService.getField(Integer.parseInt(fieldId));

		model.addAttribute("field", field);
		return "field/field-update-view";
	}

	@RequestMapping(value = "/field/field-view.do")
	public String fieldView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldId = req.getParameter("fieldId");
		if (StringUtils.isEmpty(fieldId))
			throw new IOException("没有输入fieldId");

		Field field = fieldService.getField(Integer.parseInt(fieldId));

		model.addAttribute("field", field);
		return "field/field-view";
	}

	@RequestMapping(value = "/field/field-update.do")
	public String fieldUpdate(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldId = req.getParameter("fieldId");
		if (StringUtils.isEmpty(fieldId))
			throw new IOException("没有输入fieldId");

		Field field = fieldService.getField(Integer.parseInt(fieldId));

		String fieldType = req.getParameter("fieldType");

		String fieldName = req.getParameter("fieldName");
		String fieldDesc = req.getParameter("fieldDesc");
		String fieldUnit = req.getParameter("fieldUnit");
		String fieldPeriod = req.getParameter("fieldPeriod");

		field.setFieldType(fieldType == null ? 0 : Integer.parseInt(fieldType.split("#")[0]));
		field.setFieldDesc(fieldDesc == null ? "" : fieldDesc.trim());
		field.setFieldName(fieldName == null ? "" : fieldName.trim());
		field.setFieldPeriod(fieldPeriod == null ? 0 : Integer.parseInt(fieldPeriod));
		field.setFieldTypeName(fieldType == null ? "录入字段" : fieldType.split("#")[1]);
		field.setFieldUnit(fieldUnit == null ? "" : fieldUnit.trim());

		fieldService.updateField(field);

		return "redirect:/field/field-index.do";
	}
}
