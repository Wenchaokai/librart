package com.best.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.best.Constants;
import com.best.domain.Customer;
import com.best.domain.Field;
import com.best.domain.FieldType;
import com.best.domain.FieldValue;
import com.best.domain.FieldWarm;
import com.best.domain.MinPrice;
import com.best.domain.Price;
import com.best.domain.PriceType;
import com.best.domain.Project;
import com.best.domain.Subject;
import com.best.domain.User;
import com.best.domain.WareHouse;
import com.best.domain.wms.SystemField;
import com.best.service.CustomerService;
import com.best.service.FieldService;
import com.best.service.FieldWarmService;
import com.best.service.MessageService;
import com.best.service.ProjectService;
import com.best.service.SubjectService;
import com.best.service.WareHouseService;
import com.best.utils.CommonUtils;
import com.best.utils.DerivedField;
import com.best.utils.FieldValueCacheUtils;

/**
 * ClassName:RoleController Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private WareHouseService wareHouseService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FieldService fieldService;

	@Autowired
	private FieldWarmService fieldWarmService;

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/project/project-index.do")
	public String projectIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String searchProjectParams = req.getParameter("searchProjectParams");
		String enableString = req.getParameter("searchEnable");
		enableString = (enableString == null) ? "-1" : enableString;

		int projectEnable = Integer.parseInt(enableString);

		List<Project> allProjects = projectService.listAllProject();
		List<Project> res = null;
		if (StringUtils.isNotBlank(searchProjectParams)) {
			res = new ArrayList<Project>();
			for (Project project : allProjects) {
				if (project.getProjectName().toLowerCase().contains(searchProjectParams.toLowerCase())) {
					res.add(project);
				}
			}
		} else {
			res = allProjects;
		}

		allProjects = res;
		res = new ArrayList<Project>();

		for (Project project : allProjects) {
			if (projectEnable >= 0 && project.getProjectEnable() == projectEnable)
				res.add(project);
			else if (projectEnable < 0)
				res.add(project);
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
			model.addAttribute("projectRes", new ArrayList<Project>());
		else
			model.addAttribute("projectRes", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchProjectParams", searchProjectParams == null ? "" : searchProjectParams.trim());
		model.addAttribute("searchEnable", projectEnable);

		return "admin/project/project-index";
	}

	@RequestMapping(value = "/project/project-add-view.do")
	public String projectAddView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		List<WareHouse> wareHouses = wareHouseService.getAllWareHouse();

		List<Customer> customers = customerService.getAllCustomer();

		model.addAttribute("warehouses", wareHouses);

		model.addAttribute("customers", customers);

		return "admin/project/project-add-view";
	}

	@RequestMapping(value = "/project/project-add.do")
	public String projectAdd(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectName = req.getParameter("projectName");
		String projectCustomer = req.getParameter("projectCustomer");
		String projectWareHouse = req.getParameter("projectWareHouse");
		String projectCooper = req.getParameter("projectCooper");
		String projectBusiness = req.getParameter("projectBusiness");
		String projectEnable = req.getParameter("projectEnable");
		projectEnable = (("on".equals(projectEnable) || "1".equals(projectEnable)) ? "1" : "0");

		Project project = new Project();
		project.setProjectName(projectName == null ? "" : projectName.trim());
		project.setProjectCustomer(projectCustomer == null ? "" : projectCustomer.trim());
		project.setProjectWareHouse(projectWareHouse == null ? "" : projectWareHouse.trim());
		project.setProjectCooper(projectCooper == null ? "" : projectCooper.trim());
		project.setProjectBusiness(projectBusiness == null ? "" : projectBusiness.trim());
		project.setProjectEnable(Integer.parseInt(projectEnable));

		project.setGmtCreate(Constants.decia.format(new Date()));

		projectService.addProject(project);

		return "redirect:/project/project-index.do";
	}

	@RequestMapping(value = "/project/project-update-view.do")
	public String projectUpdateView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
		if (StringUtils.isEmpty(projectId))
			throw new IOException("没有输入ProjectId");

		Project project = projectService.getProject(Integer.parseInt(projectId));

		List<WareHouse> wareHouses = wareHouseService.getAllWareHouse();

		for (WareHouse wareHouse : wareHouses) {
			if (wareHouse.getWareHouseCode().equals(project.getWareHouseCode()))
				wareHouse.setChecked(true);
			else
				wareHouse.setChecked(false);
		}

		List<Customer> customers = customerService.getAllCustomer();

		for (Customer customer : customers) {
			if (customer.getCustomerCode().equals(project.getCustomerCode()))
				customer.setChecked(true);
			else
				customer.setChecked(false);
		}

		model.addAttribute("warehouses", wareHouses);

		model.addAttribute("customers", customers);

		model.addAttribute("project", project);

		return "admin/project/project-update-view";
	}

	@RequestMapping(value = "/project/project-update.do")
	public String projectUpdate(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
		if (StringUtils.isEmpty(projectId))
			throw new IOException("没有输入RoleId");

		Project project = projectService.getProject(Integer.parseInt(projectId));

		String projectName = req.getParameter("projectName");
		String projectCustomer = req.getParameter("projectCustomer");
		String projectWareHouse = req.getParameter("projectWareHouse");
		String projectCooper = req.getParameter("projectCooper");
		String projectBusiness = req.getParameter("projectBusiness");
		String projectEnable = req.getParameter("projectEnable");
		projectEnable = (("on".equals(projectEnable) || "1".equals(projectEnable)) ? "1" : "0");

		project.setProjectName(projectName == null ? "" : projectName.trim());
		project.setProjectCustomer(projectCustomer == null ? "" : projectCustomer.trim());
		project.setProjectWareHouse(projectWareHouse == null ? "" : projectWareHouse.trim());
		project.setProjectCooper(projectCooper == null ? "" : projectCooper.trim());
		project.setProjectBusiness(projectBusiness == null ? "" : projectBusiness.trim());
		project.setProjectEnable(Integer.parseInt(projectEnable));

		projectService.updateProject(project);

		return "redirect:/project/project-index.do";
	}

	@RequestMapping(value = "/project/project-subject-index.do")
	public String projectSubjectIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectId = req.getParameter("projectId");
		List<Project> projects = projectService.listAllProject();
		Project project = null;
		for (Project temp : projects) {
			if (null != projectId && temp.getProjectId() == Integer.parseInt(projectId)) {
				project = temp;
				temp.setIsChecked(true);
			} else
				temp.setIsChecked(false);
		}

		String showType = req.getParameter("showType");
		if (showType == null || !(showType.equals("1") || showType.equals("2")))
			showType = "1";

		model.addAttribute("projects", projects);
		if (project != null) {
			int pId = project.getProjectId();
			List<Subject> subjects = subjectService.listSubjectById(pId);
			List<Subject> commonSubjects = new ArrayList<Subject>();
			List<Subject> privateSubjects = new ArrayList<Subject>();
			for (Subject subject : subjects) {
				if (subject.getSubjectType() == 2)
					commonSubjects.add(subject);
				else if (subject.getSubjectType() == 1)
					privateSubjects.add(subject);
			}
			model.addAttribute("flag", true);
			model.addAttribute("project", project);
			model.addAttribute("commonSubjects", commonSubjects);
			model.addAttribute("privateSubjects", privateSubjects);
		} else {
			model.addAttribute("flag", false);
			model.addAttribute("project", new Project());

			model.addAttribute("commonSubjects", new ArrayList<Subject>());
			model.addAttribute("privateSubjects", new ArrayList<Subject>());
		}
		model.addAttribute("showType", showType);

		return "/project/project-subject-index";
	}

	@RequestMapping(value = "/project/project-subject-add-index.do")
	public String projectSubjectAddIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String projectIdString = req.getParameter("projectId");
		String projectName = req.getParameter("projectName");
		String filterTitle = req.getParameter("filterTitle");

		List<Subject> commonSubjects = subjectService.listCommonSubjects();
		List<Subject> existedCommonSubjects = subjectService.listCommonSubjectsByProjectId(Integer.parseInt(projectIdString));

		if (StringUtils.isNotBlank(filterTitle)) {
			List<Subject> tmp = new ArrayList<Subject>();
			if (null != commonSubjects) {
				for (Subject subject : commonSubjects) {
					if (subject.getSubjectName().contains(filterTitle))
						tmp.add(subject);
				}
			}
			commonSubjects = tmp;
		}

		Set<Integer> existedCommonIds = new HashSet<Integer>();
		if (null != existedCommonSubjects) {
			for (Subject subject : existedCommonSubjects) {
				existedCommonIds.add(subject.getParentSubjectId());
			}
		}

		List<List<Subject>> returnCommons = new ArrayList<List<Subject>>();
		if (null != commonSubjects) {
			List<Subject> temp = new ArrayList<Subject>();
			for (Subject subject : commonSubjects) {
				if (existedCommonIds.contains(subject.getSubjectId()))
					continue;
				temp.add(subject);
				if (temp.size() == 3) {
					returnCommons.add(temp);
					temp = new ArrayList<Subject>();
				}
			}
			if (temp.size() != 0)
				returnCommons.add(temp);
		}

		model.addAttribute("commonSubjects", returnCommons);
		model.addAttribute("projectId", projectIdString);
		model.addAttribute("projectName", projectName);
		model.addAttribute("filterTitle", filterTitle == null ? "" : filterTitle);
		return "project/project-subject-add-view";
	}

	@RequestMapping(value = "/project/project-subject-add.do")
	public String projectSubjectAddView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		List<Subject> commonSubjects = subjectService.listCommonSubjects();

		String[] subjectIdStrings = req.getParameterValues("subjectIds");

		String projectIdString = req.getParameter("projectId");

		Set<Integer> subjectIds = new HashSet<Integer>();

		if (null != subjectIdStrings) {
			for (String subjectIdString : subjectIdStrings) {
				subjectIds.add(Integer.parseInt(subjectIdString.trim()));
			}
		}

		if (StringUtils.isNotBlank(projectIdString)) {
			Integer projectId = Integer.parseInt(projectIdString.trim());
			for (Subject subject : commonSubjects) {
				if (!subjectIds.contains(subject.getSubjectId()))
					continue;
				subject.setSubjectProjectId(projectId);
				subject.setParentSubjectId(subject.getSubjectId());

				User user = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
				subject.setUserName(user.getUserName());
				subject.setModifyTime(System.currentTimeMillis());

				subjectService.addSubject(subject);
			}
		}

		return "redirect:/project/project-subject-index.do?projectId=" + projectIdString;
	}

	@RequestMapping(value = "/project/project-subject-delete.do")
	public String projectSubjectDelete(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String subjectId = req.getParameter("subjectId");

		String projectId = req.getParameter("projectId");

		if (null == subjectId || projectId == null)
			return "redirect:/project/project-subject-index.do";

		subjectService.deleteSubject(Integer.parseInt(subjectId), Integer.parseInt(projectId));

		return "redirect:/project/project-subject-index.do?projectId=" + projectId;
	}

	@RequestMapping(value = "/project/project-subject-unitprice-index.do")
	public String projectSubjectIndexUnitPriceView(HttpServletRequest req, HttpServletResponse resp, Model model)
			throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		String subjectId = req.getParameter("subjectId");

		String messageId = req.getParameter("messageId");
		if (StringUtils.isNotBlank(messageId)) {
			model.addAttribute("messageId", messageId);
		}

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		String subjectPrice = subject.getSubjectPrice();
		List<PriceType> prices = PriceType.parsePriceString(subjectPrice);
		model.addAttribute("subject", subject);
		model.addAttribute("prices", prices);

		return "project/project-subject-unitprice-index";
	}

	@RequestMapping(value = "/project/project-subject-unitprice-add.do")
	public String projectSubjectIndexUnitPriceAddView(HttpServletRequest req, HttpServletResponse resp, Model model)
			throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		User user = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		String messageId = req.getParameter("messageId");
		try {
			messageService.deleteMessage(Integer.parseInt(messageId), user.getUserId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		String subjectId = req.getParameter("subjectId");

		String projectId = req.getParameter("projectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		List<PriceType> priceTypes = new ArrayList<PriceType>();
		Map<String, String[]> parameterMaps = req.getParameterMap();
		for (String key : parameterMaps.keySet()) {
			if (key.startsWith("price_type_")) {
				String type = req.getParameter(key);
				PriceType priceType = new PriceType();
				try {
					priceType.setPriceType(Integer.parseInt(type));
				} catch (Exception e) {
					priceType.setPriceType(PriceType.STANDARD_PRICE);
				}
				String index = key.substring("price_type_".length());
				if (priceType.getPriceType() == PriceType.STANDARD_PRICE) {
					String startDate = req.getParameter("start_date_" + index);
					String endDate = req.getParameter("end_date_" + index);
					priceType.setStartDate(startDate);
					priceType.setEndDate(endDate);
					boolean fault = false;
					for (String temp : parameterMaps.keySet()) {
						if (temp.startsWith("amount_" + index + "_")) {
							String value = req.getParameter(temp);
							if (value == null)
								value = "0.0";
							try {
								priceType.setStandardPrice(Double.parseDouble(value));
							} catch (Exception e) {
								fault = true;
								break;
							}
							priceType.setPriceUnit(1);
							break;
						}
					}
					if (fault)
						continue;
				} else {
					// 阶梯价格
					String startDate = req.getParameter("start_date_" + index);
					String endDate = req.getParameter("end_date_" + index);
					priceType.setStartDate(startDate);
					priceType.setEndDate(endDate);
					String fieldInfo = req.getParameter("base_field_" + index);
					priceType.setFieldInfo(fieldInfo);
					Map<Integer, Price> prices = new HashMap<Integer, Price>();
					boolean fault = false;
					for (String temp : parameterMaps.keySet()) {
						if (temp.startsWith("cycle_time_" + index + "_")) {
							String value = req.getParameter(temp);
							if (value == null)
								value = "1";
							try {
								priceType.setPriceUnit(Integer.parseInt(value));
							} catch (Exception e) {
								fault = true;
								break;
							}
						} else if (temp.startsWith("pricing_mode_" + index + "_")) {
							String value = req.getParameter(temp);
							if (value == null)
								value = "1";
							if (value.equals("1"))
								priceType.setStandardPrice(1.0);
							else {
								value = req.getParameter("standard_" + index);
								try {
									priceType.setStandardPrice(Double.parseDouble(value) / 100);
								} catch (Exception e) {
									fault = true;
									break;
								}
							}
						} else if (temp.startsWith("num_start_" + index)) {
							int prefix = 0;
							String value = req.getParameter(temp);
							if (temp.endsWith("num_start_" + index)) {
								if (value.trim().length() == 0)
									value = "0";
								Price price = prices.get(prefix);
								if (price == null) {
									price = new Price();
									prices.put(prefix, price);
								}
								try {
									price.min = Double.parseDouble(value);
								} catch (Exception e) {
									fault = true;
									break;
								}
							} else {
								prefix = Integer.parseInt(temp.substring(("num_start_" + index + "_").length()));
								Price price = prices.get(prefix);
								if (price == null) {
									price = new Price();
									prices.put(prefix, price);
								}
								try {
									price.min = Double.parseDouble(value);
								} catch (Exception e) {
									fault = true;
									break;
								}
							}
						} else if (temp.startsWith("num_end_" + index)) {
							int prefix = 0;
							String value = req.getParameter(temp);
							if (temp.endsWith("num_end_" + index)) {
								Price price = prices.get(prefix);
								if (price == null) {
									price = new Price();
									prices.put(prefix, price);
								}
								try {
									price.max = Double.parseDouble(value);
								} catch (Exception e) {
									fault = true;
									break;
								}
							} else {
								if (value.length() == 0)
									value = "1000000000000";
								prefix = Integer.parseInt(temp.substring(("num_end_" + index + "_").length()));
								Price price = prices.get(prefix);
								if (price == null) {
									price = new Price();
									prices.put(prefix, price);
								}
								try {
									price.max = Double.parseDouble(value);
								} catch (Exception e) {
									fault = true;
									break;
								}
							}
						} else if (temp.startsWith("jieti_value_" + index)) {
							int prefix = 0;
							String value = req.getParameter(temp);
							if (temp.endsWith("jieti_value_" + index)) {
								Price price = prices.get(prefix);
								if (price == null) {
									price = new Price();
									prices.put(prefix, price);
								}
								try {
									price.value = Double.parseDouble(value);
								} catch (Exception e) {
									fault = true;
									break;
								}
							} else {
								prefix = Integer.parseInt(temp.substring(("jieti_value_" + index + "_").length()));
								Price price = prices.get(prefix);
								if (price == null) {
									price = new Price();
									prices.put(prefix, price);
								}
								try {
									price.value = Double.parseDouble(value);
								} catch (Exception e) {
									fault = true;
									break;
								}
							}
						}

					}
					if (fault)
						continue;
					priceType.setPrices(new ArrayList<Price>(prices.values()));
				}
				priceTypes.add(priceType);
			}

		}

		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		for (PriceType priceType : priceTypes) {
			if (flag)
				builder.append("|");
			builder.append(priceType.toString());
			flag = true;
		}
		subject.setSubjectPrice(builder.toString());

		subject.setUserName(user.getUserName());
		subject.setModifyTime(System.currentTimeMillis());

		subjectService.updateSubject(subject);

		return "redirect:/project/project-subject-index.do?projectId=" + projectId;
	}

	@RequestMapping(value = "/project/project-subject-minprice-index.do")
	public String projectSubjectIndexMinPriceView(HttpServletRequest req, HttpServletResponse resp, Model model)
			throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		String subjectId = req.getParameter("subjectId");

		String messageId = req.getParameter("messageId");
		if (StringUtils.isNotBlank(messageId)) {
			model.addAttribute("messageId", messageId);
		}

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		model.addAttribute("subject", subject);

		return "project/project-subject-minprice-index";
	}

	@RequestMapping(value = "/project/project-subject-minprice-add.do")
	public String projectSubjectIndexMinPriceAddView(HttpServletRequest req, HttpServletResponse resp, Model model)
			throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		User user = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		String messageId = req.getParameter("messageId");
		try {
			messageService.deleteMessage(Integer.parseInt(messageId), user.getUserId());
		} catch (Exception e) {
			// TODO: handle exception
		}

		String subjectId = req.getParameter("subjectId");

		String projectId = req.getParameter("projectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		List<MinPrice> minPrices = new ArrayList<MinPrice>();
		Map<String, String[]> parameterMaps = req.getParameterMap();
		for (String key : parameterMaps.keySet()) {
			if (key.startsWith("lowest_standard_")) {
				String type = req.getParameter(key);
				MinPrice minPrice = new MinPrice();
				String index = key.substring("lowest_standard_".length());
				String startDate = req.getParameter("start_date_" + index);
				String endDate = req.getParameter("end_date_" + index);
				minPrice.setStartDate(startDate);
				minPrice.setEndDate(endDate);
				if (type.equals("1")) {
					String value = req.getParameter("shoufei_type_" + index);
					minPrice.setPriceType(Integer.parseInt(value));

					if (minPrice.getPriceType() == MinPrice.LOWEST_NUM_PRICE) {
						value = req.getParameter("field_" + index);
						minPrice.setFieldInfo(value == null ? "" : value);
					}
					boolean fault = false;
					for (String temp : parameterMaps.keySet()) {
						if (temp.startsWith("amount_" + index + "_")) {
							value = req.getParameter(temp);
							if (value == null)
								value = "0.0";
							try {
								minPrice.setStandardPrice(Double.parseDouble(value));
							} catch (Exception e) {
								fault = true;
								minPrice.setStandardPrice(0.0);
								break;
							}
							break;
						}
					}
					if (fault)
						continue;
				} else {
					minPrice.setPriceType(MinPrice.NON_LOWEST);
				}
				minPrices.add(minPrice);
			}

		}

		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		for (MinPrice minPrice : minPrices) {
			if (flag)
				builder.append("|");
			builder.append(minPrice.toString());
			flag = true;
		}
		subject.setSubjectMinPrice(builder.toString());

		subject.setUserName(user.getUserName());
		subject.setModifyTime(System.currentTimeMillis());

		subjectService.updateSubject(subject);

		return "redirect:/project/project-subject-index.do?projectId=" + projectId;
	}

	@RequestMapping(value = "/project/project-subject-customer-index.do")
	public String projectSubjectIndexCustomerView(HttpServletRequest req, HttpServletResponse resp, Model model)
			throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		String subjectId = req.getParameter("subjectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		model.addAttribute("subject", subject);

		model.addAttribute("fieldTypes", FieldType.parseFieldString(subject.getSubjectFieldCustom()));

		List<SystemField> systemFields = DerivedField.getSystemFields();
		List<FieldWarm> fieldWarms = fieldWarmService.listFieldWarmEnabled(subject.getSubjectProjectId());
		List<Field> deriveFields = fieldService.listAllDeriveField();

		model.addAttribute("systemFields", systemFields);
		model.addAttribute("fieldWarms", fieldWarms);
		model.addAttribute("deriveFields", deriveFields);

		return "project/project-subject-customer-index";
	}

	@RequestMapping(value = "/project/project-subject-customer-add.do")
	public String projectSubjectIndexMinCustomerAddView(HttpServletRequest req, HttpServletResponse resp, Model model)
			throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		String subjectId = req.getParameter("subjectId");

		String projectId = req.getParameter("projectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		List<FieldType> fieldTypes = new ArrayList<FieldType>();
		Map<String, String[]> parameterMaps = req.getParameterMap();
		for (String key : parameterMaps.keySet()) {
			if (key.startsWith("shoufei_type_")) {
				String fieldInfo = req.getParameter(key);
				FieldType fieldType = new FieldType();
				String index = key.substring("shoufei_type_".length());
				String type = req.getParameter("rule_type_" + index);
				fieldType.setFieldInfo(fieldInfo);
				fieldType.setFieldType(Integer.parseInt(type));

				if (fieldType.getFieldType() == FieldType.CUSTOMER_TYPE) {
					Map<Integer, FieldValue> fieldValues = new HashMap<Integer, FieldValue>();
					boolean fault = false;
					for (String temp : parameterMaps.keySet()) {
						if (temp.startsWith("s_type_1_" + index)) {
							String value = req.getParameter(temp);
							int prefix = Integer.parseInt(temp.substring(("s_type_1_" + index + "_").length()));
							FieldValue fieldValue = fieldValues.get(prefix);
							if (fieldValue == null) {
								fieldValue = new FieldValue();
								fieldValues.put(prefix, fieldValue);
							}
							fieldValue.fieldInfo = value;
							String[] params = fieldValue.fieldInfo.split("-");
							if (params.length < 2) {
								fieldValue.fieldType = "int";
							} else {
								List<SystemField> systemFields = FieldValueCacheUtils.getAllSystemFields();
								for (SystemField systemField : systemFields) {
									if ((systemField.getClassIdentify() + "-" + systemField.getFieldName())
											.equals(fieldValue.fieldInfo)) {
										fieldValue.fieldType = systemField.getFieldType();
										break;
									}
								}
							}
						} else if (temp.startsWith("s_type_2_" + index)) {
							String value = req.getParameter(temp);
							int prefix = Integer.parseInt(temp.substring(("s_type_2_" + index + "_").length()));
							FieldValue fieldValue = fieldValues.get(prefix);
							if (fieldValue == null) {
								fieldValue = new FieldValue();
								fieldValues.put(prefix, fieldValue);
							}
							try {
								fieldValue.compare = Integer.parseInt(value);
							} catch (Exception e) {
								fault = true;
								break;
							}
						} else if (temp.startsWith("s_type_3_" + index)) {
							String value = req.getParameter(temp);
							if (StringUtils.isEmpty(value)) {
								fault = true;
								break;
							}
							int prefix = Integer.parseInt(temp.substring(("s_type_3_" + index + "_").length()));
							FieldValue fieldValue = fieldValues.get(prefix);
							if (fieldValue == null) {
								fieldValue = new FieldValue();
								fieldValues.put(prefix, fieldValue);
							}
							fieldValue.value = value;

						}
					}
					if (fault)
						continue;
					fieldType.setFields(new ArrayList<FieldValue>(fieldValues.values()));
				}
				fieldTypes.add(fieldType);
			}

		}

		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		for (FieldType fieldType : fieldTypes) {
			if (flag)
				builder.append("|");
			builder.append(fieldType.toString());
			flag = true;
		}
		subject.setSubjectFieldCustom(builder.toString());

		User user = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		subject.setUserName(user.getUserName());
		subject.setModifyTime(System.currentTimeMillis());

		subjectService.updateSubject(subject);

		return "redirect:/project/project-subject-index.do?projectId=" + projectId;
	}

	@RequestMapping(value = "/project/project-subject-view.do")
	public String projectSubjectView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		String subjectId = req.getParameter("subjectId");

		String projectId = req.getParameter("projectId");

		Subject subject = subjectService.getSubject(Integer.parseInt(subjectId));

		model.addAttribute("subject", subject);

		List<MinPrice> minPrices = MinPrice.parseMinPrice(subject.getSubjectMinPrice());

		List<PriceType> priceTypes = PriceType.parsePriceString(subject.getSubjectPrice());
		List<FieldType> fieldTypes = FieldType.parseFieldString(subject.getSubjectFieldCustom());

		Map<String, String> systemFieldMap = new HashMap<String, String>();
		List<SystemField> systemFields = FieldValueCacheUtils.getAllSystemFields();
		for (SystemField systemField : systemFields) {
			systemFieldMap.put(systemField.getClassIdentify() + "-" + systemField.getFieldName(), systemField.getFieldDesc());
		}
		systemFields = DerivedField.getSystemFields();
		for (SystemField systemField : systemFields) {
			systemFieldMap.put(systemField.getClassIdentify() + "-" + systemField.getFieldName(), systemField.getFieldDesc());
		}
		List<Field> allTypeInFields = fieldService.listAllTypeInField();
		List<Field> allDeriveFields = fieldService.listAllDeriveField();
		Map<Integer, Field> fieldMap = new HashMap<Integer, Field>();
		for (Field field : allDeriveFields) {
			fieldMap.put(field.getFieldId(), field);
		}
		for (Field field : allTypeInFields) {
			fieldMap.put(field.getFieldId(), field);
		}

		for (MinPrice minPrice : minPrices) {
			if (minPrice.getPriceType() == MinPrice.LOWEST_NUM_PRICE) {
				if (systemFieldMap.containsKey(minPrice.getFieldInfo())) {
					minPrice.setFieldDesc(systemFieldMap.get(minPrice.getFieldInfo()));
					continue;
				}
				try {
					if (fieldMap.containsKey(Integer.parseInt(minPrice.getFieldInfo()))) {
						Field field = fieldMap.get(Integer.parseInt(minPrice.getFieldInfo()));
						minPrice.setFieldDesc(field.getFieldName());
						continue;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				minPrice.setFieldDesc(minPrice.getFieldInfo());
			}
		}

		for (PriceType priceType : priceTypes) {
			if (priceType.getPriceType() == PriceType.STEP_PRICE) {
				if (systemFieldMap.containsKey(priceType.getFieldInfo())) {
					priceType.setFieldDesc(systemFieldMap.get(priceType.getFieldInfo()));
					continue;
				}
				try {
					if (fieldMap.containsKey(Integer.parseInt(priceType.getFieldInfo()))) {
						Field field = fieldMap.get(Integer.parseInt(priceType.getFieldInfo()));
						priceType.setFieldDesc(field.getFieldName());
						continue;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				priceType.setFieldDesc(priceType.getFieldInfo());
			}
		}

		for (FieldType fieldType : fieldTypes) {
			try {
				if (systemFieldMap.containsKey(fieldType.getFieldInfo())) {
					fieldType.setFieldDesc(systemFieldMap.get(fieldType.getFieldInfo()));
				}

				else if (fieldMap.containsKey(Integer.parseInt(fieldType.getFieldInfo()))) {
					Field field = fieldMap.get(Integer.parseInt(fieldType.getFieldInfo()));
					fieldType.setFieldDesc(field.getFieldName());
				} else {
					fieldType.setFieldDesc(fieldType.getFieldInfo());
				}
			} catch (Exception e) {
				fieldType.setFieldDesc(fieldType.getFieldInfo());
			}

			if (fieldType.getFieldType() == FieldType.CUSTOMER_TYPE) {
				if (fieldType.getFields() != null) {
					for (FieldValue fieldValue : fieldType.getFields()) {

						if (systemFieldMap.containsKey(fieldValue.getFieldInfo())) {
							fieldValue.setFieldDesc(systemFieldMap.get(fieldValue.getFieldInfo()));
							continue;
						}
						try {
							if (fieldMap.containsKey(Integer.parseInt(fieldValue.getFieldInfo()))) {
								Field field = fieldMap.get(Integer.parseInt(fieldValue.getFieldInfo()));
								fieldValue.setFieldDesc(field.getFieldName());
								continue;
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						fieldValue.setFieldDesc(fieldValue.getFieldInfo());

					}
				}
			}

		}
		Collections.sort(minPrices);
		Collections.sort(priceTypes);
		model.addAttribute("minPrices", minPrices);
		model.addAttribute("priceTypes", priceTypes);
		model.addAttribute("fieldTypes", fieldTypes);

		return "project/project-subject-view";
	}
}
