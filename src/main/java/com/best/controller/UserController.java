package com.best.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.best.Constants;
import com.best.domain.Message;
import com.best.domain.Project;
import com.best.domain.Role;
import com.best.domain.User;
import com.best.service.MessageService;
import com.best.service.ProjectService;
import com.best.service.RoleService;
import com.best.service.UserService;
import com.best.utils.CommonUtils;

/**
 * ClassName:UserController Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-8-27
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/user/user-login.do")
	public String checkUser(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		// 验证登陆信息
		String loginName = req.getParameter("userName");
		String userPsw = req.getParameter("password");

		String url = req.getParameter("url");

		User user = new User();
		user.setLoginName(loginName);
		user.setUserPsw(userPsw);

		user = userService.logging(user);

		if (user == null) {
			// 输入有错误

			model.addAttribute("loginName", loginName);
			model.addAttribute("errorLogin", Boolean.TRUE);

			return "redirect:/login.do";
		} else {
			String userRole = user.getUserRole();
			if (!StringUtils.isEmpty((userRole))) {
				String[] roleParams = userRole.split(",");

				Set<Integer> userRoles = new HashSet<Integer>();
				for (String param : roleParams) {
					userRoles.add(Integer.parseInt(param));
				}
				Set<Integer> rolePriviliages = new HashSet<Integer>();
				List<Role> roles = roleService.listAllRole();
				for (Role role : roles) {
					if (userRoles.contains(role.getRoleId()))
						rolePriviliages.addAll(role.getPriviliege());
				}
				user.setUserPrivliages(rolePriviliages);
			}
			List<Message> messages = messageService.listAllMessage(user.getUserId());
			HttpSession session = req.getSession();
			user.setMessageSize(messages == null ? 0 : messages.size());
			session.setAttribute(Constants.USER_TOKEN_IDENTIFY, user);
			session.setAttribute(Constants.MESSAGES_TOKEN_IDENTIFY + user.getUserId(), messages);

			if (StringUtils.isNotBlank(url))
				return "redirect:" + url;
			else
				return "redirect:/index.do";
		}
	}

	@RequestMapping(value = "/user/user-logout.do")
	public String loginout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.USER_TOKEN_IDENTIFY);
		if (user != null) {
			session.removeAttribute(Constants.USER_TOKEN_IDENTIFY);
			session.removeAttribute(Constants.MESSAGES_TOKEN_IDENTIFY + user.getUserId());
		}

		return "redirect:/login.do";
	}

	@RequestMapping(value = "/user/user-password.do")
	public String userPassword(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		return "user-password";
	}

	@RequestMapping(value = "/user/user-modify-password.do")
	public String userModifyPassword(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.USER_TOKEN_IDENTIFY);

		Long userId = user.getUserId();
		String oldPassword = req.getParameter("old-pwd");
		String newPassword = req.getParameter("new-pwd");

		int modifyCount = userService.updateUserPassword(userId, oldPassword, newPassword);

		if (modifyCount == 0) {
			model.addAttribute("error", Boolean.TRUE);
			return "user-password";
		} else {
			model.addAttribute("error", Boolean.FALSE);
			model.addAttribute("modifyStatus", Boolean.TRUE);
			return "user-password";
		}
	}

	@RequestMapping(value = "/user/user-update-step1.do")
	public String updateUserStep1(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String userId = req.getParameter("userId");
		User user = userService.getUser(Long.parseLong(userId));

		model.addAttribute("userId", userId);
		if (null != user)
			model.addAttribute("member", user);
		else
			model.addAttribute("error", Boolean.TRUE);

		return "admin/user/update-user-step1";
	}

	@RequestMapping(value = "/user/user-update-step2.do")
	public String updateUserStep2(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String userId = req.getParameter("userId");
		User user = userService.getUser(Long.parseLong(userId));

		model.addAttribute("userName", req.getParameter("userName"));
		model.addAttribute("email", req.getParameter("email") == null ? "" : (req.getParameter("email")));
		model.addAttribute("enable", req.getParameter("enable") == null ? 0 : req.getParameter("enable"));

		model.addAttribute("userId", userId);

		Set<Integer> userRolesExisted = user.getUserRoles();

		List<Role> allRoles = roleService.listAllRole();

		for (Role role : allRoles) {
			if (userRolesExisted.contains(role.getRoleId()))
				role.setChecked(true);
			else
				role.setChecked(false);
		}

		model.addAttribute("roles", allRoles);

		return "admin/user/update-user-step2";
	}

	@RequestMapping(value = "/user/user-update-step3.do")
	public String updateUserStep3(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String filterTitle = req.getParameter("filterTitle");

		String userId = req.getParameter("userId");
		User user = userService.getUser(Long.parseLong(userId));

		model.addAttribute("userName", req.getParameter("userName"));
		model.addAttribute("email", req.getParameter("email") == null ? "" : (req.getParameter("email")));
		model.addAttribute("enable", req.getParameter("enable") == null ? 0 : req.getParameter("enable"));

		model.addAttribute("userId", userId);

		String[] roleParams = req.getParameterValues("role");
		String roles = "";
		boolean flag = false;
		if (roleParams != null) {
			for (String roleParam : roleParams) {
				if (flag)
					roles += ",";
				roles += roleParam;
				flag = true;
			}
		} else {
			roles = req.getParameter("roles");
		}
		model.addAttribute("roles", roles == null ? "" : roles);

		List<Project> allProjects = projectService.listAllProject();
		if (StringUtils.isNotEmpty(filterTitle)) {
			List<Project> tempProjects = new ArrayList<Project>();
			for (Project project : allProjects) {
				if (project.getProjectName().contains(filterTitle))
					tempProjects.add(project);
			}
			allProjects = tempProjects;
		}
		Set<Integer> userProjectsExisted = user.getUserProjects();

		for (Project project : allProjects) {
			if (userProjectsExisted.contains(project.getProjectId()))
				project.setIsChecked(true);
			else
				project.setIsChecked(false);
		}

		List<List<Project>> res = new ArrayList<List<Project>>();
		List<Project> temp = new ArrayList<Project>();
		for (Project project : allProjects) {
			temp.add(project);
			if (temp.size() == 4) {
				res.add(temp);
				temp = new ArrayList<Project>();
			}
		}
		if (temp.size() != 0)
			res.add(temp);

		model.addAttribute("projects", res);
		model.addAttribute("filterTitle", filterTitle == null ? "" : filterTitle);

		return "admin/user/update-user-step3";
	}

	@RequestMapping(value = "/user/user-update-final.do")
	public String updateUser(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String userId = req.getParameter("userId");
		User user = userService.getUser(Long.parseLong(userId));
		String userName = req.getParameter("userName");
		String userEmail = req.getParameter("email");
		String userEnable = req.getParameter("enable");
		userEnable = (userEnable == null ? "1" : userEnable);
		if (StringUtils.isNotBlank(userEmail))
			userEmail = userEmail + "@800best.com";

		String userRoles = req.getParameter("roles");

		String[] userProjects = req.getParameterValues("userProject");

		String projectString = "";

		if (userProjects != null) {
			for (String project : userProjects) {
				if (projectString.length() > 0)
					projectString += ",";
				projectString += project;
			}
		}

		user.setUserEmail(userEmail);
		user.setUserEnable(Integer.parseInt(userEnable));
		user.setUserName(userName);
		user.setUserProject(projectString);
		user.setUserRole(userRoles);

		userService.updateMember(user);

		return "redirect:/user/user-index.do";
	}

	@RequestMapping(value = "/user/user-add-step1.do")
	public String addNewUserStep1(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		return "admin/user/add-user-step1";
	}

	@RequestMapping(value = "/user/user-add-step2.do")
	public String addNewUserStep2(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		model.addAttribute("loginName", req.getParameter("loginName"));
		model.addAttribute("userName", req.getParameter("userName"));
		model.addAttribute("password", req.getParameter("password"));
		model.addAttribute("email", req.getParameter("email") == null ? "" : (req.getParameter("email")));
		model.addAttribute("enable", req.getParameter("enable") == null ? 0 : req.getParameter("enable"));

		List<Role> allRoles = roleService.listAllRole();

		model.addAttribute("roles", allRoles);

		return "admin/user/add-user-step2";
	}

	@RequestMapping(value = "/user/user-add-step3.do")
	public String addNewUserStep3(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		model.addAttribute("loginName", req.getParameter("loginName"));
		model.addAttribute("userName", req.getParameter("userName"));
		model.addAttribute("password", req.getParameter("password"));
		model.addAttribute("email", req.getParameter("email") == null ? "" : (req.getParameter("email")));
		model.addAttribute("enable", req.getParameter("enable") == null ? 0 : req.getParameter("enable"));

		String filterTitle = req.getParameter("filterTitle");

		String[] roleParams = req.getParameterValues("role");
		String roles = "";
		boolean flag = false;
		if (roleParams != null) {
			for (String roleParam : roleParams) {
				if (flag)
					roles += ",";
				roles += roleParam;
				flag = true;
			}
		} else {
			roles = req.getParameter("roles");
		}
		model.addAttribute("roles", roles == null ? "" : roles);

		List<Project> allProjects = projectService.listAllProject();
		if (StringUtils.isNotEmpty(filterTitle)) {
			List<Project> tempProjects = new ArrayList<Project>();
			for (Project project : allProjects) {
				if (project.getProjectName().contains(filterTitle))
					tempProjects.add(project);
			}
			allProjects = tempProjects;
		}
		List<List<Project>> res = new ArrayList<List<Project>>();
		List<Project> temp = new ArrayList<Project>();
		for (Project project : allProjects) {
			temp.add(project);
			if (temp.size() == 4) {
				res.add(temp);
				temp = new ArrayList<Project>();
			}
		}
		if (temp.size() != 0)
			res.add(temp);

		model.addAttribute("projects", res);
		model.addAttribute("filterTitle", filterTitle == null ? "" : filterTitle);

		return "admin/user/add-user-step3";
	}

	@RequestMapping(value = "/user/user-add-final.do")
	public String addNewUser(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String loginName = req.getParameter("loginName");
		String userName = req.getParameter("userName");
		String userPsw = req.getParameter("password");
		String userEmail = req.getParameter("email");
		String userEnable = req.getParameter("enable");
		userEnable = (userEnable == null ? "1" : userEnable);
		if (StringUtils.isNotBlank(userEmail))
			userEmail = userEmail + "@800best.com";

		String userRoles = req.getParameter("roles");

		String[] userProjects = req.getParameterValues("userProject");

		String projectString = "";

		if (userProjects != null) {
			for (String project : userProjects) {
				if (projectString.length() > 0)
					projectString += ",";
				projectString += project;
			}
		}

		User user = new User();
		user.setLoginName(loginName);
		user.setUserEmail(userEmail);
		user.setUserEnable(Integer.parseInt(userEnable));
		user.setUserName(userName);
		user.setUserPsw(userPsw);
		user.setUserProject(projectString);
		user.setUserRole(userRoles);

		userService.addMember(user);

		return "redirect:/user/user-index.do";
	}

	@RequestMapping(value = "/user/member-delete.do")
	public String memberDelete(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String userId = req.getParameter("userId");
		userService.deleteMember(userId);

		return "redirect:/user/user-index.do";
	}

	@RequestMapping(value = "/user/member-view.do")
	public String memberView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String userId = req.getParameter("userId");
		User user = userService.getUser(Long.parseLong(userId));

		model.addAttribute("member", user);

		return "";
	}

	@RequestMapping(value = "/user/user-index.do")
	public String memberIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String searchLoginName = req.getParameter("loginName");
		String searchEnabled = req.getParameter("searchEnabled");
		searchEnabled = (searchEnabled == null ? "-1" : searchEnabled);

		int enableType = Integer.parseInt(searchEnabled);

		List<User> users = userService.listAllUsers();

		List<Role> roles = roleService.listAllRole();

		List<User> res = new ArrayList<User>();
		for (User user : users) {
			if (StringUtils.isNotBlank(searchLoginName)) {
				if (user.getUserName().toLowerCase().contains(searchLoginName.toLowerCase())
						|| user.getLoginName().toLowerCase().contains(searchLoginName.toLowerCase()))
					;
				else
					continue;
			}
			if (enableType >= 0) {
				if (user.getUserEnable() != enableType)
					continue;
			}
			Set<Integer> userRoles = user.getUserRoles();

			String roleName = "";
			boolean flag = false;
			for (Role role : roles) {
				if (userRoles.contains(role.getRoleId())) {
					if (flag)
						roleName += ";";
					roleName += role.getRoleName();
					flag = true;
				}
			}
			user.setRoleName(roleName);
			res.add(user);
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
			model.addAttribute("userRes", new ArrayList<User>());
		else
			model.addAttribute("userRes", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchLoginName", searchLoginName == null ? "" : searchLoginName.trim());
		model.addAttribute("searchEnable", searchEnabled);

		return "admin/user/user-index";
	}

	@RequestMapping(value = "/user/user-password-reset.do")
	public String userPasswordReset(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		User obj = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		if (obj.getUserPrivliages().contains(7)) {
			String userId = req.getParameter("userId");
			userService.resetUserPassword(Long.parseLong(userId));
		}
		return "redirect:/user/user-index.do";
	}

	@RequestMapping(value = "/user/user-message.do")
	public String memberMessage(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";
		User obj = (User) req.getSession().getAttribute(Constants.USER_TOKEN_IDENTIFY);
		List<Message> res = messageService.listAllMessage(obj.getUserId());

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
			model.addAttribute("res", new ArrayList<Message>());
		else
			model.addAttribute("res", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);

		return "user/user-message";
	}
}
