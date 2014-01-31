package com.best.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.best.domain.Field;
import com.best.domain.FieldWarm;
import com.best.domain.Project;
import com.best.domain.User;
import com.best.domain.ValueDate;
import com.best.service.FieldService;
import com.best.service.FieldWarmService;
import com.best.service.MessageService;
import com.best.service.ProjectService;
import com.best.service.UserService;
import com.best.utils.CommonUtils;
import com.best.utils.DateUtil;

/**
 * ClassName:FieldWarmController Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-31
 */
@Controller
public class FieldWarmController {

	@Autowired
	private FieldWarmService fieldWarmService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private FieldService fieldService;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/fieldwarm/field-warm-delete.do")
	public String fieldDelete(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldWarmId = req.getParameter("fieldWarmId");
		String fieldWarmProjectId = req.getParameter("fieldWarmProjectId");
		if (null != fieldWarmId) {
			Integer warmId = Integer.parseInt(fieldWarmId);
			fieldWarmService.deleteFieldWarmById(warmId);
		}
		if (null != fieldWarmProjectId) {
			List<FieldWarm> fieldWarms = fieldWarmService.listFieldWarm(Integer.parseInt(fieldWarmProjectId));
			if (null == fieldWarms || fieldWarms.size() == 0) {
				Project project = projectService.getProject(Integer.parseInt(fieldWarmProjectId));
				project.setProjectEnable(0);
				projectService.updateProject(project);
			}
		}

		return "redirect:/fieldwarm/field-warm-index.do";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-view.do")
	public String fieldWarmView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldWarmId = req.getParameter("fieldWarmId");

		FieldWarm fieldWarm = fieldWarmService.getFieldWarm(Integer.parseInt(fieldWarmId));

		model.addAttribute("projectName", fieldWarm.getFieldWarmProjectName());

		List<User> allUsers = userService.listAllUsers();

		User firstCharge = null;
		User secondeCharge = null;

		if (null != fieldWarm) {
			for (User user : allUsers) {
				if (user.getUserId().longValue() == fieldWarm.getFieldWarmChargeManFirstId())
					firstCharge = user;
				if (user.getUserId().longValue() == fieldWarm.getFieldWarmChargeManSecondId())
					secondeCharge = user;
			}
			model.addAttribute("firstCharge", firstCharge);
			model.addAttribute("secondCharge", secondeCharge);
		}

		List<FieldWarm> res = fieldWarmService.listFieldWarm(fieldWarm.getFieldWarmProjectId());

		model.addAttribute("fieldWarms", res);

		return "/fieldwarm/field-warm-view";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-index.do")
	public String fieldWarmIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		List<Project> allProjects = projectService.listAllProject();
		Map<Integer, Project> projectMap = new HashMap<Integer, Project>();
		for (Project project : allProjects) {
			projectMap.put(project.getProjectId(), project);
		}

		List<User> allUsers = userService.listAllUsers();

		Map<Long, User> userMap = new HashMap<Long, User>();
		for (User user : allUsers) {
			userMap.put(user.getUserId(), user);
		}

		Map<Integer, Field> fieldMap = new HashMap<Integer, Field>();
		List<Field> fields = fieldService.listAllTypeInField();
		for (Field field : fields) {
			fieldMap.put(field.getFieldId(), field);
		}

		String projectIdString = req.getParameter("projectId");
		if (null == projectIdString)
			projectIdString = "-1";
		int projectId = Integer.parseInt(projectIdString.trim());
		for (Project project : allProjects) {
			if (projectId >= 0 && project.getProjectId() == projectId)
				project.setIsChecked(true);
			else
				project.setIsChecked(false);
		}

		String userIdString = req.getParameter("userId");
		if (null == userIdString)
			userIdString = "-1";
		long userId = Long.parseLong(userIdString.trim());
		for (User user : allUsers) {
			if (userId >= 0 && user.getUserId() == userId)
				user.setChecked(true);
			else
				user.setChecked(false);
		}

		model.addAttribute("projects", allProjects);
		model.addAttribute("users", allUsers);
		model.addAttribute("projectId", projectId);
		model.addAttribute("userId", userId);

		List<FieldWarm> allFieldWarms = fieldWarmService.listAllFieldWarm();
		List<FieldWarm> res = new ArrayList<FieldWarm>();

		for (FieldWarm fieldWarm : allFieldWarms) {
			if (projectId >= 0 && fieldWarm.getFieldWarmProjectId() != projectId)
				continue;
			if (userId >= 0
					&& (fieldWarm.getFieldWarmChargeManFirstId() != userId && fieldWarm.getFieldWarmChargeManSecondId() != userId))
				continue;
			res.add(fieldWarm);
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

		List<FieldWarm> returnRes = null;
		if (end < start)
			returnRes = new ArrayList<FieldWarm>();

		else
			returnRes = res.subList(start, end);
		for (FieldWarm fieldWarm : returnRes) {
			Long fieldWarmUserId = fieldWarm.getFieldWarmChargeManFirstId();
			if (fieldWarmUserId == null || !userMap.containsKey(fieldWarmUserId)) {
				fieldWarm.setFieldWarmChargeManFirstEmail("");
				fieldWarm.setFieldWarmChargeManFirstName("");
			} else {
				User user = userMap.get(fieldWarmUserId);
				fieldWarm.setFieldWarmChargeManFirstEmail(user.getUserEmail() != null ? user.getUserEmail() : "");
				fieldWarm.setFieldWarmChargeManFirstName(user.getUserName() != null ? user.getUserName() : "");
			}

			Integer fieldId = fieldWarm.getFieldWarmFieldId();
			if (null == fieldId || !fieldMap.containsKey(fieldId)) {
				fieldWarm.setFieldWarmFieldName("");
				fieldWarm.setFieldWarmFieldPeriod(-1);
			} else {
				Field field = fieldMap.get(fieldId);
				fieldWarm.setFieldWarmFieldName(field.getFieldName() != null ? field.getFieldName() : "");
				fieldWarm.setFieldWarmFieldPeriod(field.getFieldPeriod());
			}

			Integer fielWarmProjectId = fieldWarm.getFieldWarmProjectId();
			if (null == fielWarmProjectId || !projectMap.containsKey(fielWarmProjectId)) {
				fieldWarm.setFieldWarmProjectName("");
			} else {
				Project project = projectMap.get(fielWarmProjectId);
				fieldWarm.setFieldWarmProjectName(project.getProjectName() != null ? project.getProjectName() : "");
			}
		}
		model.addAttribute("res", returnRes);
		model.addAttribute("currentPage", currentPage);

		return "fieldwarm/field-warm-index";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-add-view.do")
	public String fieldWarmAddView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String filterTitle = req.getParameter("filterTitle");

		List<Project> allProjects = projectService.listAllProject();

		List<Field> allFields = fieldService.listAllTypeInField();

		if (StringUtils.isNotEmpty(filterTitle)) {
			List<Field> tempFields = new ArrayList<Field>();
			for (Field field : allFields) {
				if (field.getFieldName().contains(filterTitle))
					tempFields.add(field);
			}
			allFields = tempFields;
		}

		model.addAttribute("projects", allProjects);
		List<List<Field>> fieldRes = new ArrayList<List<Field>>();
		List<Field> temp = new ArrayList<Field>();
		int index = 1;
		for (Field field : allFields) {
			temp.add(field);
			if (index++ % 4 == 0) {
				fieldRes.add(temp);
				temp = new ArrayList<Field>();
			}
		}
		if (temp.size() > 0)
			fieldRes.add(temp);

		model.addAttribute("fields", fieldRes);

		List<User> users = userService.listAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("filterTitle", filterTitle == null ? "" : filterTitle);

		return "fieldwarm/field-warm-add-view";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-add-first.do")
	public String fieldWarmAddFirst(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String project = req.getParameter("project");

		List<Field> allFields = fieldService.listAllTypeInField();

		String[] fieldIds = req.getParameterValues("field");

		List<Field> checkFields = new ArrayList<Field>();
		if (null != fieldIds) {
			for (String fieldId : fieldIds) {
				for (Field field : allFields) {
					if (field.getFieldId() == Integer.parseInt(fieldId.trim())) {
						checkFields.add(field);
					}
				}
			}
		}
		String first = req.getParameter("first");
		String second = req.getParameter("second");

		model.addAttribute("project", project);
		model.addAttribute("fieldSize", checkFields.size());
		model.addAttribute("fields", checkFields);
		model.addAttribute("first", first == null ? "-1" : first);
		model.addAttribute("second", second == null ? "-1" : second);

		return "fieldwarm/field-warm-add-view-step";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-add-second.do")
	public String fieldWarmAddSecond(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String project = req.getParameter("project");
		String first = req.getParameter("first");
		String second = req.getParameter("second");

		String fieldSize = req.getParameter("fieldSize");

		fieldSize = (fieldSize == null) ? "0" : fieldSize.trim();

		int size = 0;
		try {
			size = Integer.parseInt(fieldSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < size; i++) {

			String fieldId = req.getParameter("fieldId_" + (i));

			String res = "* ";
			String fieldUnit = req.getParameter("fieldUnit_" + i);
			if ("2".equals(fieldUnit)) {
				// 表示按日来计算

				String hour = req.getParameter("hour_" + i);
				String minite = req.getParameter("minite_" + i);
				res += minite + " " + hour + " * * ?";
			} else if ("3".equals(fieldUnit)) {
				// 表示按月来计算
				String hour = req.getParameter("hour_" + i);
				String minite = req.getParameter("minite_" + i);
				String day = req.getParameter("day_" + i);
				if (day.equals("-1"))
					day = "*";
				res += minite + " " + hour + " " + day + "  * ?";
			} else if ("1".equals(fieldUnit)) {
				// 表示按周来计算
				String hour = req.getParameter("hour_" + i);
				String minite = req.getParameter("minite_" + i);
				String week = req.getParameter("week_" + i);
				if (week.equals("-1"))
					res += minite + " " + hour + " *  * ?";
				else
					res += minite + " " + hour + " ?  * " + week;
			}

			FieldWarm fieldWarm = new FieldWarm();
			fieldWarm.setFieldWarmProjectId(Integer.parseInt(project));
			fieldWarm.setFieldWarmPeriod(res);

			fieldWarm.setFieldWarmEnabled(0);
			fieldWarm.setFieldWarmValue(0.0);
			fieldWarm.setFieldWarmChargeManFirstId(Long.parseLong(first));
			fieldWarm.setFieldWarmChargeManSecondId(Long.parseLong(second));
			fieldWarm.setFieldWarmFieldId(Integer.parseInt(fieldId));

			fieldWarmService.addFieldWarm(fieldWarm);

			Project project2 = projectService.getProject(Integer.parseInt((project)));
			project2.setProjectEnable(1);
			projectService.updateProject(project2);

		}

		return "redirect:/fieldwarm/field-warm-index.do";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-update-view.do")
	public String fieldWarmUpdateView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldWarmId = req.getParameter("fieldWarmId");
		if (StringUtils.isEmpty(fieldWarmId))
			throw new IOException("没有输入fieldWarmId");

		String filterTitle = req.getParameter("filterTitle");

		FieldWarm fieldWarm = fieldWarmService.getFieldWarm(Integer.parseInt(fieldWarmId));
		model.addAttribute("fieldWarm", fieldWarm);

		List<User> allUsers = userService.listAllUsers();

		model.addAttribute("users", allUsers);

		List<FieldWarm> res = fieldWarmService.listFieldWarm(fieldWarm.getFieldWarmProjectId());

		Set<Integer> includeFieldIds = new HashSet<Integer>();
		for (FieldWarm fieldWarm2 : res) {
			includeFieldIds.add(fieldWarm2.getFieldWarmFieldId());
		}

		List<Field> allFields = fieldService.listAllTypeInField();
		if (StringUtils.isNotEmpty(filterTitle)) {
			List<Field> tempFields = new ArrayList<Field>();
			for (Field field : allFields) {
				if (field.getFieldName().contains(filterTitle))
					tempFields.add(field);
			}
			allFields = tempFields;
		}
		for (Field field : allFields) {
			if (includeFieldIds.contains(field.getFieldId()))
				field.setIsChecked(true);
			else
				field.setIsChecked(false);
		}

		List<List<Field>> fieldRes = new ArrayList<List<Field>>();
		List<Field> temp = new ArrayList<Field>();
		int index = 1;
		for (Field field : allFields) {
			temp.add(field);
			if (index++ % 4 == 0) {
				fieldRes.add(temp);
				temp = new ArrayList<Field>();
			}
		}
		if (temp.size() > 0)
			fieldRes.add(temp);

		model.addAttribute("fields", fieldRes);
		model.addAttribute("filterTitle", filterTitle == null ? "" : filterTitle);

		return "fieldwarm/field-warm-update-view";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-update-first.do")
	public String fieldWarmUpdateFirst(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String project = req.getParameter("project");

		List<FieldWarm> fieldWarms = fieldWarmService.listFieldWarm(Integer.parseInt(project));

		List<Field> allFields = fieldService.listAllTypeInField();

		String[] fieldIds = req.getParameterValues("field");

		Set<Integer> includeFieldIds = new HashSet<Integer>();
		for (String fieldId : fieldIds) {
			includeFieldIds.add(Integer.parseInt(fieldId));
		}

		List<FieldWarm> continueFieldWarms = new ArrayList<FieldWarm>();
		for (FieldWarm fieldWarm : fieldWarms) {
			if (includeFieldIds.contains(fieldWarm.getFieldWarmFieldId())) {
				continueFieldWarms.add(fieldWarm);
				includeFieldIds.remove(fieldWarm.getFieldWarmFieldId());
			}
		}

		List<Field> checkFields = new ArrayList<Field>();
		if (null != fieldIds && includeFieldIds.size() > 0) {
			for (Field field : allFields) {
				if (includeFieldIds.contains(field.getFieldId())) {
					checkFields.add(field);
				}
			}
		}

		String first = req.getParameter("first");
		String second = req.getParameter("second");

		model.addAttribute("project", project);
		model.addAttribute("continueFieldWarms", continueFieldWarms);
		model.addAttribute("fieldSize", checkFields.size() + continueFieldWarms.size());
		model.addAttribute("fields", checkFields);
		model.addAttribute("first", first == null ? "-1" : first);
		model.addAttribute("second", second == null ? "-1" : second);

		return "fieldwarm/field-warm-update-view-step";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-update-second.do")
	public String fieldWarmUpdateSecond(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String project = req.getParameter("project");
		String first = req.getParameter("first");
		String second = req.getParameter("second");

		fieldWarmService.deleteFieldWarmByProjectId(Integer.parseInt(project));

		String fieldSize = req.getParameter("fieldSize");

		fieldSize = (fieldSize == null) ? "0" : fieldSize.trim();

		int size = 0;
		try {
			size = Integer.parseInt(fieldSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < size; i++) {

			String fieldId = req.getParameter("fieldId_" + (i));

			String res = "* ";
			String fieldUnit = req.getParameter("fieldUnit_" + i);
			if ("2".equals(fieldUnit)) {
				// 表示按日来计算

				String hour = req.getParameter("hour_" + i);
				String minite = req.getParameter("minite_" + i);
				res += minite + " " + hour + " * * ?";
			} else if ("3".equals(fieldUnit)) {
				// 表示按月来计算
				String hour = req.getParameter("hour_" + i);
				String minite = req.getParameter("minite_" + i);
				String day = req.getParameter("day_" + i);
				if (day.equals("-1"))
					day = "*";
				res += minite + " " + hour + " " + day + "  * ?";
			} else if ("1".equals(fieldUnit)) {
				// 表示按周来计算
				String hour = req.getParameter("hour_" + i);
				String minite = req.getParameter("minite_" + i);
				String week = req.getParameter("week_" + i);
				if (week.equals("-1"))
					res += minite + " " + hour + " *  * ?";
				else
					res += minite + " " + hour + " ?  * " + week;
			}

			FieldWarm fieldWarm = new FieldWarm();
			fieldWarm.setFieldWarmProjectId(Integer.parseInt(project));
			fieldWarm.setFieldWarmPeriod(res);

			fieldWarm.setFieldWarmEnabled(0);
			fieldWarm.setFieldWarmValue(0.0);
			fieldWarm.setFieldWarmChargeManFirstId(Long.parseLong(first));
			fieldWarm.setFieldWarmChargeManSecondId(Long.parseLong(second));
			fieldWarm.setFieldWarmFieldId(Integer.parseInt(fieldId));

			fieldWarmService.addFieldWarm(fieldWarm);

		}

		return "redirect:/fieldwarm/field-warm-index.do";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-get.do")
	public String fieldWarmGet(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldWarmId = req.getParameter("fieldWarmId");
		if (StringUtils.isEmpty(fieldWarmId))
			throw new IOException("没有输入fieldWarmId");

		FieldWarm fieldWarm = fieldWarmService.getFieldWarm(Integer.parseInt(fieldWarmId));

		model.addAttribute("fieldWarm", fieldWarm);

		return "";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-value-initial.do")
	public String fieldWarmValueInitial(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldWarmId = req.getParameter("fieldWarmId");
		if (StringUtils.isEmpty(fieldWarmId))
			throw new IOException("没有输入fieldWarmId");

		FieldWarm fieldWarm = fieldWarmService.getFieldWarm(Integer.parseInt(fieldWarmId));

		String fieldValueInit = req.getParameter("fieldValueInit");

		if (null == fieldValueInit)
			fieldValueInit = "0";

		fieldWarm.setFieldWarmEnabled(1);
		fieldWarm.setFieldWarmValue(Double.parseDouble(fieldValueInit));

		fieldWarmService.updateFieldWarm(fieldWarm);

		return "";
	}

	@RequestMapping(value = "/fieldwarm/field-warm-enable-manage-index.do")
	public String fieldWarmEnableManagerIndex(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		String projectIdString = req.getParameter("projectId");
		String fieldIdString = req.getParameter("fieldId");
		String userIdString = req.getParameter("userId");
		String dataTypeString = req.getParameter("dataType");

		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		if (StringUtils.isNotBlank(projectIdString)) {
			builder.append("projectId=").append(projectIdString);
			flag = true;
		}
		if (StringUtils.isNotBlank(fieldIdString)) {
			if (flag)
				builder.append("&");
			builder.append("fieldId=").append(fieldIdString);
			flag = true;
		}
		if (StringUtils.isNotBlank(userIdString)) {
			if (flag)
				builder.append("&");
			builder.append("userId=").append(userIdString);
		}

		int dataType = -1;
		if (StringUtils.isNotBlank(dataTypeString)) {
			dataType = Integer.parseInt(dataTypeString);
			if (flag)
				builder.append("&");
			builder.append("dataType=").append(dataTypeString);
			flag = true;
		}

		model.addAttribute("dataType", dataTypeString);

		model.addAttribute("param", builder.toString());

		List<FieldWarm> fieldWarms = null;

		if (StringUtils.isNotBlank(projectIdString))
			fieldWarms = fieldWarmService.listFieldWarm(Integer.parseInt(projectIdString));
		// fieldWarms =
		// fieldWarmService.listFieldWarmEnabled(Integer.parseInt(projectIdString));
		else
			fieldWarms = fieldWarmService.listAllFieldWarm();

		List<Project> allProjects = projectService.listAllProject();
		for (Project project : allProjects) {
			if (StringUtils.isNotBlank(projectIdString)) {
				if (project.getProjectId() == Integer.parseInt(projectIdString)) {
					project.setIsChecked(true);
					continue;
				}
			}
			project.setIsChecked(false);
		}

		model.addAttribute("projects", allProjects);

		List<User> allUsers = userService.listAllUsers();
		for (User user : allUsers) {
			if (StringUtils.isNotBlank(userIdString)) {
				if (user.getUserId() == Long.parseLong(userIdString)) {
					user.setChecked(true);
					continue;
				}
			}
			user.setChecked(false);
		}
		model.addAttribute("users", allUsers);

		Map<Long, User> userMap = new HashMap<Long, User>();
		for (User user : allUsers) {
			userMap.put(user.getUserId(), user);
		}

		List<Field> allTypeInFields = fieldService.listAllTypeInField();
		for (Field field : allTypeInFields) {
			if (StringUtils.isNotBlank(fieldIdString)) {
				if (field.getFieldId() == Integer.parseInt(fieldIdString)) {
					field.setIsChecked(true);
					continue;
				}
			}
			field.setIsChecked(false);
		}
		model.addAttribute("fields", allTypeInFields);

		List<FieldWarm> res = new ArrayList<FieldWarm>();

		if (null != fieldWarms) {
			for (FieldWarm fieldWarm : fieldWarms) {
				List<ValueDate> dates = fieldWarm.getDateValues();
				if (dates.size() > 0) {
					for (ValueDate valueDate : dates) {
						try {
							FieldWarm ff = (FieldWarm) fieldWarm.getClone();
							ff.setDataType(valueDate.getDateType());
							ff.setFieldWarmValue(valueDate.getDateValue());
							ff.setFieldWarmServiceDate(valueDate.getDateTime());
							res.add(ff);
						} catch (CloneNotSupportedException e) {
						}
					}
				} else
					res.add(fieldWarm);
			}
		}

		List<FieldWarm> newRes = new ArrayList<FieldWarm>();
		for (FieldWarm fieldWarm : res) {
			if (StringUtils.isNotBlank(fieldIdString) && fieldWarm.getFieldWarmFieldId() != Integer.parseInt(fieldIdString))
				continue;
			if (StringUtils.isNotBlank(userIdString) && (fieldWarm.getFieldWarmModifier() != Long.parseLong(userIdString)))
				continue;
			if (dataType >= 0 && fieldWarm.getDataType().intValue() != dataType)
				continue;
			newRes.add(fieldWarm);
		}

		res = newRes;

		int yugu = 0;
		for (FieldWarm fieldWarm : res) {
			if (fieldWarm.getDataType() == 1)
				yugu++;
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

		List<FieldWarm> returnRes = null;
		if (end < start)
			returnRes = new ArrayList<FieldWarm>();

		else
			returnRes = res.subList(start, end);

		for (FieldWarm fieldWarm : returnRes) {

			Long fieldWarmModifier = fieldWarm.getFieldWarmModifier();
			if (fieldWarmModifier == null || !userMap.containsKey(fieldWarmModifier)) {
				fieldWarm.setFieldWarmModifierName("");
			} else {
				User user = userMap.get(fieldWarmModifier);
				fieldWarm.setFieldWarmModifierName(user.getUserName() != null ? user.getUserName() : "");
			}

		}
		model.addAttribute("res", returnRes);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("yugu", yugu);

		return "fieldwarm/field-warm-data-index";
	}

	@RequestMapping(value = "/fieldWarm/field-warm-data-modify-view.do")
	public String fieldWarmDataModifyView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		String fieldWarmId = req.getParameter("fieldWarmId");
		String dateTime = req.getParameter("dateTime");

		if (!CommonUtils.checkSessionTimeOut(req)) {
			String url = "/fieldWarm/field-warm-data-modify-view.do?fieldWarmId=" + fieldWarmId + "&dateTime="
					+ (dateTime == null ? "" : dateTime);
			model.addAttribute("url", url);
			return "redirect:/login.do";
		}

		String messageId = req.getParameter("messageId");
		if (StringUtils.isNotBlank(messageId)) {
			model.addAttribute("messageId", messageId);
		}

		if (StringUtils.isEmpty(fieldWarmId)) {
			return "redirect:/fieldwarm/field-warm-enable-manage-index.do";
		}

		FieldWarm fieldWarm = fieldWarmService.getFieldWarm(Integer.parseInt(fieldWarmId));
		List<ValueDate> dates = fieldWarm.getDateValues();
		for (ValueDate valueDate : dates) {
			if (valueDate.getDateTime().equals(dateTime)) {
				fieldWarm.setDataType(valueDate.getDateType());
				fieldWarm.setFieldWarmGmtModified(valueDate.getDateTime());
				fieldWarm.setFieldWarmValue(valueDate.getDateValue());
			}
		}

		model.addAttribute("fieldWarm", fieldWarm);

		return "fieldwarm/field-warm-data-modify-view";
	}

	@RequestMapping(value = "/fieldWarm/field-warm-data-modify.do")
	public String fieldWarmDataModify(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String fieldWarmId = req.getParameter("fieldWarmId");

		User user = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		String messageId = req.getParameter("messageId");
		try {
			messageService.deleteMessage(Integer.parseInt(messageId), user.getUserId());
		} catch (Exception e) {
		}

		if (StringUtils.isEmpty(fieldWarmId)) {
			return "redirect:/fieldwarm/field-warm-enable-manage-index.do";
		}

		FieldWarm fieldWarm = fieldWarmService.getFieldWarm(Integer.parseInt(fieldWarmId));

		String year = req.getParameter("year");
		String month = req.getParameter("month");
		String day = req.getParameter("day");
		String serviceDate = (year == null ? getCurrentServiceYear() : year) + (month == null ? getCurrentServiceMonth() : month)
				+ (day == null ? getCurrentServiceDay() : day);
		String value = req.getParameter("fieldWarmValue");

		String desc = req.getParameter("fieldWarmDataDesc");
		String dataType = req.getParameter("dataType");

		fieldWarm.setFieldWarmServiceDate(serviceDate);
		try {
			fieldWarm.setFieldWarmValue(Double.parseDouble(value.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		fieldWarm.setFieldWarmDataDesc(desc == null ? "" : desc);

		Object obj = req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		fieldWarm.setFieldWarmModifier(((User) obj).getUserId());
		fieldWarm.setFieldWarmGmtModified(DateUtil.getCurrentDateString());
		fieldWarm.setDataType(Integer.parseInt(dataType));
		String historyRecord = fieldWarm.getHistoryRecord();
		String[] params = historyRecord.split("\\|");
		StringBuilder sb = new StringBuilder();
		boolean flag = false;
		boolean append = false;
		for (String param : params) {
			if (param.trim().length() == 0)
				continue;
			if (flag)
				sb.append("|");
			if (param.startsWith(serviceDate)) {
				append = true;
				sb.append(serviceDate).append("$").append(value).append("$").append(Integer.parseInt(dataType));
			} else {
				sb.append(param);
			}
			flag = true;
		}
		if (!append) {
			if (flag)
				sb.append("|");
			sb.append(serviceDate).append("$").append(value).append("$").append(Integer.parseInt(dataType));
		}
		fieldWarm.setHistoryRecord(sb.toString());

		fieldWarmService.updateFieldWarm(fieldWarm);

		return "redirect:/fieldwarm/field-warm-enable-manage-index.do";
	}

	public String getCurrentServiceYear() {
		String currentDay = DateUtil.getCurrentDateString();
		return currentDay.substring(0, 4);
	}

	public String getCurrentServiceMonth() {
		String currentDay = DateUtil.getCurrentDateString();
		return currentDay.substring(4, 6);
	}

	public String getCurrentServiceDay() {
		String currentDay = DateUtil.getCurrentDateString();
		return currentDay.substring(6, 8);
	}
}
