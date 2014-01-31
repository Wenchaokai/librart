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

import com.best.Constants;
import com.best.domain.Field;
import com.best.domain.Project;
import com.best.domain.Subject;
import com.best.domain.User;
import com.best.domain.wms.SystemField;
import com.best.service.FieldService;
import com.best.service.FieldWarmService;
import com.best.service.ProjectService;
import com.best.service.SubjectService;
import com.best.utils.CommonUtils;
import com.best.utils.FieldValueCacheUtils;

/**
 * ClassName:SubjectController Description: -1表示单价 -2表示数量
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-9
 */
@Controller
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private FieldWarmService fieldWarmService;

	@Autowired
	private FieldService fieldService;

	@RequestMapping(value = "/subject/subject-index.do")
	public String subjectIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String searchSubjectType = req.getParameter("searchSubjectType");
		String searchBelongProject = req.getParameter("searchBelongProject");
		String searchSubjectEnable = req.getParameter("searchSubjectEnabled");
		String searchProjectNameStr = req.getParameter("searchProjectName");

		int enableFlag = -1;
		try {
			enableFlag = Integer.parseInt(searchSubjectEnable.trim());
		} catch (Exception e) {
		}

		int subjectType = -1;
		try {
			subjectType = Integer.parseInt(searchSubjectType.trim());
		} catch (Exception e) {

		}

		int searchProjectId = -1;
		try {
			searchProjectId = Integer.parseInt(searchBelongProject.split("#")[0].trim());
		} catch (Exception e) {
		}

		if (searchProjectNameStr == null) {
			searchProjectNameStr = "";
		}

		List<Project> projects = projectService.listAllProject();
		if (StringUtils.isNotBlank(searchBelongProject)) {
			int projectId = Integer.parseInt(searchBelongProject.trim());
			for (Project project : projects) {
				if (project.getProjectId() == projectId)
					project.setIsChecked(true);
				else
					project.setIsChecked(false);
			}
		}
		model.addAttribute("projects", projects);

		List<Subject> allSubjects = subjectService.listAllSubject();
		List<Subject> res = new ArrayList<Subject>();
		for (Subject subject : allSubjects) {
			if (enableFlag >= 0) {
				if (subject.getSubjectEnabled() != enableFlag)
					continue;
			}
			if (subjectType >= 0) {
				if (subject.getSubjectType() != subjectType)
					continue;
			}
			if (searchProjectId >= 0) {
				if (subject.getSubjectProjectId() != searchProjectId)
					continue;
			}
			if (StringUtils.isNotBlank(searchProjectNameStr)) {
				if (!subject.getSubjectName().contains(searchProjectNameStr))
					continue;
			}
			res.add(subject);
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
			model.addAttribute("res", new ArrayList<Subject>());
		else
			model.addAttribute("res", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchSubjectEnabled", enableFlag);
		model.addAttribute("searchBelongProject", searchProjectId);
		model.addAttribute("searchSubjectType", subjectType);
		model.addAttribute("searchProjectName", searchProjectNameStr);
		model.addAttribute("searchParams", "searchSubjectType=" + subjectType + "&searchBelongProject=" + searchProjectId
				+ "&searchSubjectEnabled=" + enableFlag);

		return "subject/subject-index";
	}

	@RequestMapping(value = "/subject/subject-add-view.do")
	public String subjectAddView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		List<Project> projects = projectService.listAllProject();
		model.addAttribute("projects", projects);

		return "subject/subject-add-view";
	}

	@RequestMapping(value = "/subject/subject-add-step1.do")
	public String subjectAddStep1(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String subjectProject = req.getParameter("subjectProject");
		String subjectType = req.getParameter("subjectType");
		String subjectDesc = req.getParameter("subjectDesc");
		String subjectName = req.getParameter("subjectName");
		String subjectEnable = req.getParameter("subjectEnable");

		model.addAttribute("subjectProject", subjectProject != null ? subjectProject : "");
		model.addAttribute("subjectType", subjectType != null ? subjectType : "0");
		model.addAttribute("subjectDesc", subjectDesc != null ? subjectDesc : "0");
		model.addAttribute("subjectName", subjectName != null ? subjectName : "");
		model.addAttribute("subjectEnable", subjectEnable != null ? subjectEnable : "0");

		List<SystemField> systemFields = FieldValueCacheUtils.getNumberSystemFields();
		List<Field> typeInFields = fieldService.listAllTypeInField();
		List<Field> deriveFields = fieldService.listAllDeriveField();

		model.addAttribute("systemFields", systemFields);
		model.addAttribute("typeInFields", typeInFields);
		model.addAttribute("deriveFields", deriveFields);

		return "subject/subject-add-view1";
	}

	@RequestMapping(value = "/subject/subject-add-step2.do")
	public String subjectAddStep2(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String subjectProject = req.getParameter("subjectProject");
		String subjectType = req.getParameter("subjectType");
		String subjectDesc = req.getParameter("subjectDesc");
		String subjectName = req.getParameter("subjectName");
		String subjectEnable = req.getParameter("subjectEnable");
		String subjectFormula = req.getParameter("hidden-gongshi");
		String subjectFormulaTxt = req.getParameter("hidden-gongshi-txt");
		String numSumString = req.getParameter("numSum");
		Double numSum = 0.0;
		try {
			numSum = Double.parseDouble(numSumString);
		} catch (Exception e) {
		}

		Subject subject = new Subject();
		subject.setSubjectDesc(subjectDesc);
		subject.setSubjectEnabled(Integer.parseInt(subjectEnable));
		subject.setSubjectFormula(subjectFormula == null ? "" : subjectFormula);
		int type = Integer.parseInt(subjectType);
		subject.setSubjectType(type);
		subject.setSubjectName(subjectName);
		if (type == 1)
			subject.setSubjectProjectId(Integer.parseInt(subjectProject));
		else
			subject.setSubjectProjectId(-1);
		subject.setSubjectFormulaTxt(subjectFormulaTxt == null ? "" : subjectFormulaTxt);
		subject.setSubjectNumSum(numSum);

		User user = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		subject.setUserName(user.getUserName());
		subject.setModifyTime(System.currentTimeMillis());

		subjectService.addSubject(subject);

		return "redirect:/subject/subject-index.do";
	}

	@RequestMapping(value = "/subject/subject-view.do")
	public String subjectView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String subjectId = req.getParameter("subjectId");
		if (StringUtils.isEmpty(subjectId))
			throw new IOException("没有输入subjectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));
		model.addAttribute("subject", subject);
		return "subject/subject-view";
	}

	@RequestMapping(value = "/subject/subject-delete.do")
	public String subjectDelete(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String subjectId = req.getParameter("subjectId");
		if (StringUtils.isEmpty(subjectId))
			throw new IOException("没有输入subjectId");

		String projectId = req.getParameter("projectId");

		subjectService.deleteSubject(Integer.parseInt(subjectId), Integer.parseInt(projectId));

		return "redirect:/subject/subject-index.do";
	}

	@RequestMapping(value = "/subject/subject-update-view.do")
	public String subjectUpdateView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String subjectId = req.getParameter("subjectId");
		if (StringUtils.isEmpty(subjectId))
			throw new IOException("没有输入subjectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));
		List<Project> projects = projectService.listAllProject();
		for (Project project : projects) {
			if (project.getProjectId().intValue() == subject.getSubjectProjectId().intValue())
				project.setIsChecked(true);
			else
				project.setIsChecked(false);
		}

		model.addAttribute("subject", subject);
		model.addAttribute("projects", projects);
		return "subject/subject-update-view";
	}

	@RequestMapping(value = "/subject/subject-update-step1.do")
	public String subjectUpdateStep1(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String subjectId = req.getParameter("subjectId");
		if (StringUtils.isEmpty(subjectId))
			throw new IOException("没有输入subjectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		String subjectProject = req.getParameter("subjectProject");
		String subjectType = req.getParameter("subjectType");
		String subjectDesc = req.getParameter("subjectDesc");
		String subjectName = req.getParameter("subjectName");
		String subjectEnable = req.getParameter("subjectEnable");

		model.addAttribute("subjectProject", subjectProject != null ? subjectProject : "");
		model.addAttribute("subjectType", subjectType != null ? subjectType : "0");
		model.addAttribute("subjectDesc", subjectDesc != null ? subjectDesc : "0");
		model.addAttribute("subjectName", subjectName != null ? subjectName : "");
		model.addAttribute("subjectEnable", subjectEnable != null ? subjectEnable : "0");
		model.addAttribute("subject", subject);

		List<SystemField> systemFields = FieldValueCacheUtils.getNumberSystemFields();
		List<Field> typeInFields = fieldService.listAllTypeInField();
		List<Field> deriveFields = fieldService.listAllDeriveField();

		model.addAttribute("systemFields", systemFields);
		model.addAttribute("typeInFields", typeInFields);
		model.addAttribute("deriveFields", deriveFields);

		return "subject/subject-update-view1";
	}

	@RequestMapping(value = "/subject/subject-update-step2.do")
	public String subjectUpdateStep2(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String subjectId = req.getParameter("subjectId");
		if (StringUtils.isEmpty(subjectId))
			throw new IOException("没有输入subjectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		String subjectProject = req.getParameter("subjectProject");
		String subjectType = req.getParameter("subjectType");
		String subjectDesc = req.getParameter("subjectDesc");
		String subjectName = req.getParameter("subjectName");
		String subjectEnable = req.getParameter("subjectEnable");
		String subjectFormula = req.getParameter("hidden-gongshi");
		String subjectFormulaTxt = req.getParameter("hidden-gongshi-txt");
		String numSumString = req.getParameter("numSum");
		Double numSum = 0.0;
		try {
			numSum = Double.parseDouble(numSumString);
		} catch (Exception e) {
		}

		subject.setSubjectDesc(subjectDesc);
		subject.setSubjectEnabled(Integer.parseInt(subjectEnable));
		subject.setSubjectFormula(subjectFormula == null ? "" : subjectFormula);
		int type = Integer.parseInt(subjectType);
		subject.setSubjectType(type);
		subject.setSubjectName(subjectName);
		if (type == 1)
			subject.setSubjectProjectId(Integer.parseInt(subjectProject.split("#")[0]));
		else
			subject.setSubjectProjectId(-1);
		subject.setSubjectFormulaTxt(subjectFormulaTxt == null ? "" : subjectFormulaTxt);
		subject.setSubjectNumSum(numSum);

		User user = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		subject.setUserName(user.getUserName());
		subject.setModifyTime(System.currentTimeMillis());

		subjectService.updateSubject(subject);

		return "redirect:/subject/subject-index.do";
	}

}
