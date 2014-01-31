package com.best.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.best.dao.FieldWarmDao;
import com.best.domain.Bill;
import com.best.domain.BillDetail;
import com.best.domain.Field;
import com.best.domain.FieldWarm;
import com.best.domain.Project;
import com.best.domain.Subject;
import com.best.domain.User;
import com.best.domain.ValueDate;
import com.best.service.BillDetailService;
import com.best.service.BillService;
import com.best.service.FieldService;
import com.best.service.ProjectService;
import com.best.service.SubjectService;
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
public class BillController {

	@Autowired
	private BillService billService;

	@Autowired
	private BillDetailService billDetailService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private FieldService fieldService;

	@Autowired
	private FieldWarmDao fieldWarmDao;

	@RequestMapping(value = "/bill/bill-index.do")
	public String billIndex(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		String billStatus = req.getParameter("billStatus");

		model.addAttribute("startTime", startTime == null ? "" : startTime);
		model.addAttribute("endTime", endTime == null ? "" : endTime);

		int searchProjectId = -1;
		try {
			searchProjectId = Integer.parseInt(projectId);
		} catch (Exception e) {
		}

		int status = -1;
		try {
			status = Integer.parseInt(billStatus);
		} catch (Exception e) {
		}
		model.addAttribute("billStatus", status);

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

		List<Bill> allBills = billService.listAllBill();
		List<Bill> res = new ArrayList<Bill>();

		for (Bill bill : allBills) {
			if (searchProjectId >= 0) {
				if (bill.getBillProjectId() != searchProjectId)
					continue;
			}
			if (StringUtils.isNotEmpty(startTime)) {
				if (bill.getBillStartTime().compareTo(startTime) < 0)
					continue;
			}
			if (StringUtils.isNotEmpty(endTime)) {
				if (bill.getBillEndTime().compareTo(endTime) > 0)
					continue;
			}
			if (status >= 0) {
				if (bill.getBillStatus() != status)
					continue;
			}
			res.add(bill);
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
			model.addAttribute("res", new ArrayList<Bill>());
		else
			model.addAttribute("res", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchParams", "startTime=" + (startTime == null ? "" : startTime) + "&endTime="
				+ (endTime == null ? "" : endTime) + "&projectId=" + projectId + "&billStatus="
				+ (billStatus == null ? "-1" : billStatus));

		return "bill/bill-index";
	}

	@RequestMapping(value = "/bill/bill-delete.do")
	public String billDelete(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String billId = req.getParameter("billId");

		billService.deleteBill(Integer.parseInt(billId));

		return "redirect:/bill/bill-index.do";
	}

	@RequestMapping(value = "/bill/bill-add-index.do")
	public String reportDetailAddIndex(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		String projectId = req.getParameter("projectId");
		String startTime = req.getParameter("startTime");
		startTime = (startTime == null ? "" : startTime);
		String endTime = req.getParameter("endTime");
		endTime = (endTime == null ? "" : endTime);
		String billBelongTime = req.getParameter("billBelongTime");
		billBelongTime = (billBelongTime == null ? "" : billBelongTime);
		String billName = req.getParameter("billName");
		billName = (billName == null ? "" : URLDecoder.decode(billName, "UTF-8"));

		String yugu = req.getParameter("yugu");
		yugu = (yugu == null ? "0" : yugu);

		String unTypeInStr = req.getParameter("unTypeIn");
		unTypeInStr = (unTypeInStr == null) ? "" : unTypeInStr;

		String needUpdateStr = req.getParameter("needUpdate");
		needUpdateStr = (needUpdateStr == null) ? "" : needUpdateStr;

		String confirm = req.getParameter("showConfirm");
		int showConfirm = 0;
		if (StringUtils.isNotBlank(confirm))
			showConfirm = Integer.parseInt(confirm);

		int searchProjectId = -1;

		if (StringUtils.isNotBlank(projectId))
			searchProjectId = Integer.parseInt(projectId);

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
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("billBelongTime", billBelongTime);
		model.addAttribute("billName", billName);
		model.addAttribute("showConfirm", showConfirm);
		model.addAttribute("yugu", yugu);
		model.addAttribute("unTypeIn", unTypeInStr);
		model.addAttribute("needUpdate", needUpdateStr);
		model.addAttribute("projectId", searchProjectId);

		return "bill/bill-add-index";
	}

	@RequestMapping(value = "/bill/bill-validate-add.do")
	public String billValidate(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
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

		String billBelongTime = req.getParameter("billBelongTime");
		String billName = req.getParameter("billName");
		if (StringUtils.isEmpty(billName))
			billName = "";

		List<Subject> subjects = subjectService.listSubjectById(Integer.parseInt(projectId));

		int yugu = 0;
		String unTypeIn = "";
		String needUpdate = "";
		if (subjects != null) {
			for (Subject subject : subjects) {
				List<Field> allFields = subject.getFields();
				if (null != allFields) {
					for (Field tmpField : allFields) {
						tmpField = fieldService.getField(tmpField.getFieldId());
						if (tmpField.getFieldType() != 2)
							continue;

						FieldWarm fieldWarm = fieldWarmDao.getFieldWarm(Integer.parseInt(projectId), tmpField.getFieldId());

						if (fieldWarm == null) {
							if (unTypeIn.length() != 0)
								unTypeIn += ",";
							unTypeIn += tmpField.getFieldName();
							yugu++;
							continue;
						}
						List<ValueDate> valueDates = fieldWarm.getDateValues();
						if (valueDates != null) {
							for (ValueDate valueDate : valueDates) {
								if (valueDate.getDateType() == 1 && valueDate.getDateTime().compareTo(startTime) >= 0
										&& valueDate.getDateTime().compareTo(endTime) <= 0) {
									if (needUpdate.length() != 0)
										needUpdate += ",";
									needUpdate += tmpField.getFieldName();
									yugu++;
								}
							}
						}
					}
				}
			}
		}
		if (yugu == 0) {
			return "redirect:/bill/bill-add.do?projectId=" + projectId + "&startTime=" + startTime + "&endTime=" + endTime
					+ "&billBelongTime=" + billBelongTime + "&billName=" + URLEncoder.encode(billName, "UTF-8");
		}
		return "redirect:/bill/bill-add-index.do?projectId=" + projectId + "&startTime=" + startTime + "&endTime=" + endTime
				+ "&billBelongTime=" + billBelongTime + "&billName=" + URLEncoder.encode(billName, "UTF-8")
				+ "&showConfirm=1&yugu=" + yugu + "&needUpdate=" + URLEncoder.encode(needUpdate, "UTF-8") + "&unTypeIn="
				+ URLEncoder.encode(unTypeIn, "UTF-8");
	}

	@RequestMapping(value = "/bill/bill-add.do")
	public String billAdd(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
		if (StringUtils.isEmpty(projectId))
			return "redirect:/bill/bill-index.do";
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

		String billBelongTime = req.getParameter("billBelongTime");
		String billName = req.getParameter("billName");
		if (StringUtils.isEmpty(billName))
			billName = "";

		if (StringUtils.isEmpty(billBelongTime))
			billBelongTime = DateUtil.getCurrentMonth();

		Bill bill = new Bill();
		bill.setBillName(billName);
		bill.setBillProjectId(Integer.parseInt(projectId));
		bill.setBillStartTime(startTime);
		User obj1 = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);

		bill.setGmtCreator(obj1.getUserId());
		bill.setBillSubmitTime(DateUtil.getCurrentDateString());
		bill.setBillEndTime(endTime);
		bill.setBillBelongTime(billBelongTime);

		billService.addBill(bill);

		return "redirect:/bill/bill-index.do";
	}

	@RequestMapping(value = "/bill/bill-detail-view.do")
	public String billDetailView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		String billId = req.getParameter("billId");
		if (StringUtils.isEmpty(billId))
			return "redirect:/bill/bill-index.do";
		int id = Integer.parseInt(billId);

		Bill bill = billService.getBillById(id);

		model.addAttribute("bill", bill);

		List<BillDetail> billDetails = billDetailService.listAllBillDetail(id);

		model.addAttribute("billDetails", billDetails);

		return "bill/bill-detail-view";
	}

	@RequestMapping(value = "/bill/bill-detail-delete.do")
	public String billDetailDelete(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String billId = req.getParameter("billId");
		if (StringUtils.isEmpty(billId))
			return "redirect:/bill/bill-index.do";

		int id = Integer.parseInt(billId);

		String billDetailId = req.getParameter("billDetailId");
		if (StringUtils.isEmpty(billDetailId))
			return "redirect:/bill/bill-detail-view.do?billId=" + billId;

		int detailId = Integer.parseInt(billDetailId);

		String amount = req.getParameter("billDetailSum");
		if (StringUtils.isEmpty(amount))
			amount = "0.0";

		Double detailSum = Double.parseDouble(amount);

		Bill bill = billService.getBillById(id);
		bill.setBillSum(bill.getBillSum() - detailSum);
		billService.updateBill(bill);

		billDetailService.deleteBillDetail(detailId, id);

		return "redirect:/bill/bill-detail-view.do?billId=" + billId;
	}

	@RequestMapping(value = "/bill/bill-detail-update.do")
	public String billDetailUpdate(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String billId = req.getParameter("billId");
		if (StringUtils.isEmpty(billId))
			return "redirect:/bill/bill-index.do";

		int id = Integer.parseInt(billId);

		String billDetailId = req.getParameter("billDetailId");
		if (StringUtils.isEmpty(billDetailId))
			return "redirect:/bill/bill-detail-view.do?billId=" + billId;

		int detailId = Integer.parseInt(billDetailId);

		BillDetail billDetail = billDetailService.getBillDetail(detailId);

		String amount = req.getParameter("billDetailOldSum");
		if (StringUtils.isEmpty(amount))
			amount = "0.0";

		Double detailOldSum = Double.parseDouble(amount);

		String newAmount = req.getParameter("billDetailSum");
		if (StringUtils.isEmpty(amount))
			amount = "0.0";

		Double detailNewSum = Double.parseDouble(newAmount);

		Bill bill = billService.getBillById(id);
		bill.setBillSum(bill.getBillSum() - detailOldSum);
		bill.setBillSum(bill.getBillSum() + detailNewSum);
		billService.updateBill(bill);

		billDetail.setBillDetailSum(detailNewSum);

		billDetailService.updateBillDetail(billDetail);

		return "redirect:/bill/bill-detail-view.do?billId=" + billId;
	}

	@RequestMapping(value = "/bill/bill-detail-update-view.do")
	public String billDetailUpdateView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String billId = req.getParameter("billId");
		if (StringUtils.isEmpty(billId))
			return "redirect:/bill/bill-index.do";

		int id = Integer.parseInt(billId);

		String billDetailId = req.getParameter("billDetailId");
		if (StringUtils.isEmpty(billDetailId))
			return "redirect:/bill/bill-detail-view.do?billId=" + billId;

		int detailId = Integer.parseInt(billDetailId);
		model.addAttribute("billDetail", billDetailService.getBillDetail(detailId));

		String amount = req.getParameter("billDetailSum");
		if (StringUtils.isEmpty(amount))
			amount = "0.0";

		Double detailSum = Double.parseDouble(amount);

		Bill bill = billService.getBillById(id);

		model.addAttribute("bill", bill);
		model.addAttribute("detailSum", Bill.df.format(detailSum));

		return "bill/bill-detail-update-view";
	}

	@RequestMapping(value = "/bill/bill-update-status-submit.do")
	public String billUpdateStatus(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String billId = req.getParameter("billId");
		if (StringUtils.isEmpty(billId))
			return "redirect:/bill/bill-index.do";

		int id = Integer.parseInt(billId);

		Bill bill = billService.getBillById(id);

		bill.setBillStatus(2);

		User obj = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);

		bill.setGmtCreator(obj.getUserId());

		billService.updateBill(bill);

		return "redirect:/bill/bill-detail-view.do?billId=" + billId;
	}

	@RequestMapping(value = "/bill/bill-update-status-reset-submit.do")
	public String billUpdateResetStatus(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String billId = req.getParameter("billId");
		if (StringUtils.isEmpty(billId))
			return "redirect:/bill/bill-index.do";

		int id = Integer.parseInt(billId);

		Bill bill = billService.getBillById(id);

		bill.setBillStatus(1);

		bill.setGmtCreator(-1L);

		billService.updateBill(bill);

		return "redirect:/bill/bill-detail-view.do?billId=" + billId;
	}

	@RequestMapping(value = "/bill/bill-update-status-finish.do")
	public String billUpdateFinishStatus(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String billId = req.getParameter("billId");
		if (StringUtils.isEmpty(billId))
			return "redirect:/bill/bill-index.do";

		int id = Integer.parseInt(billId);

		Bill bill = billService.getBillById(id);

		bill.setBillStatus(3);

		billService.updateBill(bill);

		return "redirect:/bill/bill-detail-view.do?billId=" + billId;
	}
}
