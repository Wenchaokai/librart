package com.best.controller;

import java.io.IOException;
import java.text.ParseException;
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
import com.best.domain.Project;
import com.best.domain.Report;
import com.best.domain.ReportDetail;
import com.best.domain.User;
import com.best.service.ProjectService;
import com.best.service.ReportDetailService;
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
public class ReportDetailController {

	@Autowired
	private ReportDetailService reportDetailService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/report-detail/report-detail-index.do")
	public String reportDetailIndex(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");

		model.addAttribute("startTime", startTime == null ? "" : startTime);
		model.addAttribute("endTime", endTime == null ? "" : endTime);

		int searchProjectId = -1;
		try {
			searchProjectId = Integer.parseInt(projectId);
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

		List<ReportDetail> allReportDetails = reportDetailService.listAllReportDetail();
		List<ReportDetail> res = new ArrayList<ReportDetail>();

		for (ReportDetail reportDetail : allReportDetails) {
			if (searchProjectId >= 0) {
				if (reportDetail.getReportDetailProjectId() != searchProjectId)
					continue;
			}
			if (StringUtils.isNotEmpty(startTime)) {
				if (reportDetail.getReportDetailStart().compareTo(startTime) < 0)
					continue;
			}
			if (StringUtils.isNotEmpty(endTime)) {
				if (reportDetail.getReportDetailEnd().compareTo(endTime) > 0)
					continue;
			}
			res.add(reportDetail);
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
			model.addAttribute("res", new ArrayList<ReportDetail>());
		else
			model.addAttribute("res", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchParams", "startTime=" + (startTime == null ? "" : startTime) + "&endTime="
				+ (endTime == null ? "" : endTime) + "&projectId=" + projectId);

		return "report-detail/report-detail-index";
	}

	@RequestMapping(value = "/report-detail/report-detail-delete.do")
	public String reportDetailDelete(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String reportDetailId = req.getParameter("reportDetailId");

		reportDetailService.deleteReportDetail(Integer.parseInt(reportDetailId));

		return "redirect:/report-detail/report-detail-index.do";
	}

	@RequestMapping(value = "/report-detail/report-detail-add-index.do")
	public String reportDetailAddIndex(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");

		model.addAttribute("startTime", (startTime == null ? "" : startTime));
		model.addAttribute("endTime", (startTime == null ? "" : endTime));

		int searchProjectId = -1;
		try {
			searchProjectId = Integer.parseInt(projectId);
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

		if (searchProjectId != -1) {
			List<Report> reports = reportService.listAllReportByProjectId(searchProjectId);
			model.addAttribute("reports", reports);
		}

		return "report-detail/report-detail-add-index";
	}

	@RequestMapping(value = "/report-detail/report-detail-add.do")
	public String reportDetailAdd(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
		if (StringUtils.isEmpty(projectId))
			return "redirect:/report-detail/report-detail-index.do";
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		if (StringUtils.isEmpty(startTime)) {
			if (StringUtils.isEmpty(endTime)) {
				try {
					startTime = DateUtil.getPreSevenDate();
				} catch (ParseException e) {
				}
				endTime = DateUtil.getCurrentDateString();
			} else {
				try {
					startTime = DateUtil.getPreSevenDate(endTime);
				} catch (ParseException e) {
				}
			}
		} else {
			if (StringUtils.isEmpty(endTime)) {
				try {
					endTime = DateUtil.getNextSevenDate(startTime);
				} catch (ParseException e) {
				}
			}
		}

		String reportId = req.getParameter("reportId");
		if (StringUtils.isEmpty(reportId))
			reportId = "-1";

		ReportDetail reportDetail = new ReportDetail();
		reportDetail.setReportDetailProjectId(Integer.parseInt(projectId));
		reportDetail.setReportId(Integer.parseInt(reportId));
		reportDetail.setReportDetailStart(startTime);
		reportDetail.setReportDetailEnd(endTime);

		User obj = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);

		reportDetail.setGmtCreator(obj.getUserId());

		reportDetailService.addDetailReport(reportDetail);

		return "redirect:/report-detail/report-detail-index.do";
	}
}
