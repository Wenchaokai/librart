package com.best.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.best.Constants;
import com.best.domain.Project;
import com.best.domain.Report;
import com.best.domain.User;
import com.best.domain.wms.Asn;
import com.best.domain.wms.AsnItem;
import com.best.domain.wms.Ldo;
import com.best.domain.wms.LdoItem;
import com.best.domain.wms.SystemField;
import com.best.service.ProjectService;
import com.best.service.ReportService;
import com.best.utils.CommonUtils;
import com.best.utils.DateUtil;

/**
 * ClassName:ReportController Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-5
 */
@Controller
public class ReportController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/report/report-index.do")
	public String projectIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String reportName = req.getParameter("reportName");
		String reportEnableFlag = req.getParameter("reportEnableFlag");
		String reportProjectId = req.getParameter("reportProjectId");
		String reportTypeString = req.getParameter("reportType");

		model.addAttribute("reportName", reportName == null ? "" : reportName);

		int enableFlag = -1;
		try {
			enableFlag = Integer.parseInt(reportEnableFlag.trim());
		} catch (Exception e) {
		}
		model.addAttribute("reportEnableFlag", enableFlag);

		int reportType = -1;
		try {
			reportType = Integer.parseInt(reportTypeString.trim());
		} catch (Exception e) {

		}
		model.addAttribute("reportType", reportType);

		int searchProjectId = -1;
		try {
			searchProjectId = Integer.parseInt(reportProjectId);
		} catch (Exception e) {
		}

		List<Project> projects = projectService.listAllProject();
		if (searchProjectId != -1) {
			for (Project project : projects) {
				if (project.getProjectId() == searchProjectId)
					project.setIsChecked(true);
				else
					project.setIsChecked(false);
			}
		}
		model.addAttribute("projects", projects);

		List<Report> allReports = reportService.listAllReport();
		List<Report> res = new ArrayList<Report>();

		for (Report report : allReports) {
			if (enableFlag >= 0) {
				if (report.getReportEnable() != enableFlag)
					continue;
			}
			if (reportType >= 0) {
				if (report.getReportType() != reportType)
					continue;
			}
			if (searchProjectId >= 0) {
				if (report.getReportProjectId() != searchProjectId)
					continue;
			}
			if (StringUtils.isNotEmpty(reportName)) {
				if (!report.getReportName().contains(reportName))
					continue;
			}
			res.add(report);
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
			model.addAttribute("res", new ArrayList<Report>());
		else
			model.addAttribute("res", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchParams", "reportType=" + reportType + "&reportName=" + (reportName == null ? "" : reportName)
				+ "&reportEnableFlag=" + enableFlag + "&reportProjectId=" + searchProjectId);

		return "report/report-index";
	}

	@RequestMapping(value = "/report/report-add-view.do")
	public String projectAddView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		List<Project> projects = projectService.listAllProject();
		model.addAttribute("projects", projects);

		List<SystemField> ldoSystemFields = Ldo.getSystemFields(false);
		List<List<SystemField>> ldoSystemFieldsList = new ArrayList<List<SystemField>>();
		List<SystemField> temp = new ArrayList<SystemField>();
		for (SystemField systemField : ldoSystemFields) {
			temp.add(systemField);
			if (temp.size() == 3) {
				ldoSystemFieldsList.add(temp);
				temp = new ArrayList<SystemField>();
			}
		}
		if (temp.size() != 0)
			ldoSystemFieldsList.add(temp);
		model.addAttribute("ldoList", ldoSystemFieldsList);

		List<SystemField> ldoItemSystemFields = LdoItem.getSystemFields(false);
		List<List<SystemField>> ldoItemSystemFieldsList = new ArrayList<List<SystemField>>();
		temp = new ArrayList<SystemField>();
		for (SystemField systemField : ldoItemSystemFields) {
			temp.add(systemField);
			if (temp.size() == 3) {
				ldoItemSystemFieldsList.add(temp);
				temp = new ArrayList<SystemField>();
			}
		}
		if (temp.size() != 0)
			ldoItemSystemFieldsList.add(temp);
		model.addAttribute("ldoItemList", ldoItemSystemFieldsList);

		List<SystemField> asnSystemFields = Asn.getSystemFields(false);
		List<List<SystemField>> asnSystemFieldsList = new ArrayList<List<SystemField>>();
		temp = new ArrayList<SystemField>();
		for (SystemField systemField : asnSystemFields) {
			temp.add(systemField);
			if (temp.size() == 3) {
				asnSystemFieldsList.add(temp);
				temp = new ArrayList<SystemField>();
			}
		}
		if (temp.size() != 0)
			asnSystemFieldsList.add(temp);
		model.addAttribute("asnList", asnSystemFieldsList);

		List<SystemField> asnItemSystemFields = AsnItem.getSystemFields(false);
		List<List<SystemField>> asnItemSystemFieldsList = new ArrayList<List<SystemField>>();
		temp = new ArrayList<SystemField>();
		for (SystemField systemField : asnItemSystemFields) {
			temp.add(systemField);
			if (temp.size() == 3) {
				asnItemSystemFieldsList.add(temp);
				temp = new ArrayList<SystemField>();
			}
		}
		if (temp.size() != 0)
			asnItemSystemFieldsList.add(temp);
		model.addAttribute("asnItemList", asnItemSystemFieldsList);

		return "report/report-add-view";
	}

	@RequestMapping(value = "/report/report-add.do")
	public String projectAdd(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String reportProjectId = req.getParameter("projectId");
		String reportName = req.getParameter("reportName");
		String reportType = req.getParameter("reportType");
		String reportObjectType = req.getParameter("reportObjectType");
		String reportClassObject = req.getParameter("reportClassObject");
		String enableFlag = req.getParameter("enableFlag");
		if (StringUtils.isEmpty(enableFlag))
			enableFlag = "0";

		Report report = new Report();
		report.setReportName(reportName == null ? "" : reportName);
		report.setReportProjectId(Integer.parseInt(reportProjectId.trim()));
		report.setReportType(Integer.parseInt(reportType));
		report.setReportObjectType(Integer.parseInt(reportObjectType));
		report.setReportClassObject(Integer.parseInt(reportClassObject));
		report.setReportEnable(Integer.parseInt(enableFlag));

		String classDivName = "field-" + report.getReportClassObject();
		String[] params = req.getParameterValues(classDivName);

		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		if (null != params) {
			for (String param : params) {
				if (flag)
					builder.append(",");
				builder.append(param);
				flag = true;
			}
		}
		report.setReportField(builder.toString());

		report.setReportGmtCreate(DateUtil.getCurrentDateString());

		User obj = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		report.setReportCreator(obj.getUserId());

		reportService.addReport(report);

		return "redirect:/report/report-index.do";
	}

	@RequestMapping(value = "/report/report-view.do")
	public String projectView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String reportId = req.getParameter("reportId");

		Report report = reportService.getReport(Integer.parseInt(reportId));

		model.addAttribute("report", report);

		return "report/report-view";
	}

	@RequestMapping(value = "/report/report-modify-view.do")
	public String projectModifyView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String reportId = req.getParameter("reportId");

		Report report = reportService.getReport(Integer.parseInt(reportId));

		List<SystemField> reportSystemFields = report.getSystemFields();

		Map<String, String> hashMap = new HashMap<String, String>();
		for (SystemField systemField : reportSystemFields) {
			hashMap.put(systemField.getClassIdentify() + "-" + systemField.getFieldName(), systemField.getFieldDesc());
		}
		model.addAttribute("report", report);

		List<SystemField> ldoSystemFields = Ldo.getSystemFields(false);
		if (report.getReportClassObject() == 1) {
			for (SystemField systemField : ldoSystemFields) {
				if (hashMap.containsKey(systemField.getClassIdentify() + "-" + systemField.getFieldName()))
					systemField.setChecked(true);
				else
					systemField.setChecked(false);
			}
		}
		List<List<SystemField>> ldoSystemFieldsList = new ArrayList<List<SystemField>>();
		List<SystemField> temp = new ArrayList<SystemField>();
		for (SystemField systemField : ldoSystemFields) {
			temp.add(systemField);
			if (temp.size() == 3) {
				ldoSystemFieldsList.add(temp);
				temp = new ArrayList<SystemField>();
			}
		}
		if (temp.size() != 0)
			ldoSystemFieldsList.add(temp);
		model.addAttribute("ldoList", ldoSystemFieldsList);

		List<SystemField> ldoItemSystemFields = LdoItem.getSystemFields(false);
		if (report.getReportClassObject() == 2) {
			for (SystemField systemField : ldoItemSystemFields) {
				if (hashMap.containsKey(systemField.getClassIdentify() + "-" + systemField.getFieldName()))
					systemField.setChecked(true);
				else
					systemField.setChecked(false);
			}
		}
		List<List<SystemField>> ldoItemSystemFieldsList = new ArrayList<List<SystemField>>();
		temp = new ArrayList<SystemField>();
		for (SystemField systemField : ldoItemSystemFields) {
			temp.add(systemField);
			if (temp.size() == 3) {
				ldoItemSystemFieldsList.add(temp);
				temp = new ArrayList<SystemField>();
			}
		}
		if (temp.size() != 0)
			ldoItemSystemFieldsList.add(temp);
		model.addAttribute("ldoItemList", ldoItemSystemFieldsList);

		List<SystemField> asnSystemFields = Asn.getSystemFields(false);
		if (report.getReportClassObject() == 3) {
			for (SystemField systemField : asnSystemFields) {
				if (hashMap.containsKey(systemField.getClassIdentify() + "-" + systemField.getFieldName()))
					systemField.setChecked(true);
				else
					systemField.setChecked(false);
			}
		}
		List<List<SystemField>> asnSystemFieldsList = new ArrayList<List<SystemField>>();
		temp = new ArrayList<SystemField>();
		for (SystemField systemField : asnSystemFields) {
			temp.add(systemField);
			if (temp.size() == 3) {
				asnSystemFieldsList.add(temp);
				temp = new ArrayList<SystemField>();
			}
		}
		if (temp.size() != 0)
			asnSystemFieldsList.add(temp);
		model.addAttribute("asnList", asnSystemFieldsList);

		List<SystemField> asnItemSystemFields = AsnItem.getSystemFields(false);
		if (report.getReportClassObject() == 4) {
			for (SystemField systemField : asnItemSystemFields) {
				if (hashMap.containsKey(systemField.getClassIdentify() + "-" + systemField.getFieldName()))
					systemField.setChecked(true);
				else
					systemField.setChecked(false);
			}
		}
		List<List<SystemField>> asnItemSystemFieldsList = new ArrayList<List<SystemField>>();
		temp = new ArrayList<SystemField>();
		for (SystemField systemField : asnItemSystemFields) {
			temp.add(systemField);
			if (temp.size() == 3) {
				asnItemSystemFieldsList.add(temp);
				temp = new ArrayList<SystemField>();
			}
		}
		if (temp.size() != 0)
			asnItemSystemFieldsList.add(temp);
		model.addAttribute("asnItemList", asnItemSystemFieldsList);

		return "report/report-modify-view";
	}

	@RequestMapping(value = "/report/report-modify.do")
	public String projectModify(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String reportId = req.getParameter("reportId");

		Report report = reportService.getReport(Integer.parseInt(reportId));

		String reportName = req.getParameter("reportName");
		String reportType = req.getParameter("reportType");
		String reportObjectType = req.getParameter("reportObjectType");
		String reportClassObject = req.getParameter("reportClassObject");
		String enableFlag = req.getParameter("enableFlag");
		if (StringUtils.isEmpty(enableFlag))
			enableFlag = "0";

		report.setReportName(reportName == null ? "" : reportName);
		report.setReportType(Integer.parseInt(reportType));
		report.setReportObjectType(Integer.parseInt(reportObjectType));
		report.setReportClassObject(Integer.parseInt(reportClassObject));
		report.setReportEnable(Integer.parseInt(enableFlag));

		String classDivName = "field-" + report.getReportClassObject();
		String[] params = req.getParameterValues(classDivName);

		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		if (null != params) {
			for (String param : params) {
				if (flag)
					builder.append(",");
				builder.append(param);
				flag = true;
			}
		}
		report.setReportField(builder.toString());

		report.setReportGmtCreate(DateUtil.getCurrentDateString());

		User obj = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		report.setReportCreator(obj.getUserId());

		reportService.updateReport(report);

		return "redirect:/report/report-index.do";
	}
}
