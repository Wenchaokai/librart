package com.best.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.best.Constants;
import com.best.domain.Priviliege;
import com.best.domain.Role;
import com.best.service.PriviliegeService;
import com.best.service.RoleService;
import com.best.utils.CommonUtils;

/**
 * ClassName:RoleController Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private PriviliegeService priviliegeService;

	@RequestMapping(value = "/role/role-index.do")
	public String roleIndexView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {

		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String searchRoleParams = req.getParameter("searchRoleParams");
		List<Role> allRoles = roleService.listAllRole();
		List<Role> res = null;
		if (StringUtils.isNotBlank(searchRoleParams)) {
			res = new ArrayList<Role>();
			for (Role role : allRoles) {
				if (role.getRoleName().toLowerCase().contains(searchRoleParams.toLowerCase())) {
					res.add(role);
				}
			}
		} else {
			res = allRoles;
		}
		if (res == null)
			res = new ArrayList<Role>();

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
			model.addAttribute("roleRes", new ArrayList<Role>());
		else
			model.addAttribute("roleRes", res.subList(start, end));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("searchRoleParams", searchRoleParams == null ? "" : searchRoleParams.trim());

		return "admin/role/role-index";
	}

	@RequestMapping(value = "/role/role-add-view.do")
	public String roleAddView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		List<Priviliege> privilieges = priviliegeService.listAllPriviliege();

		List<List<Priviliege>> res = new ArrayList<List<Priviliege>>();
		int index = 1;
		List<Priviliege> tuple = new ArrayList<Priviliege>();
		for (Priviliege priviliege : privilieges) {
			tuple.add(priviliege);
			priviliege.setIndex(index - 1);
			if (index++ % 4 == 0) {
				res.add(tuple);
				tuple = new ArrayList<Priviliege>();
			}
		}
		if (tuple.size() != 0)
			res.add(tuple);
		model.addAttribute("priviliages", res);
		return "admin/role/role-add-view";
	}

	@RequestMapping(value = "/role/role-add.do")
	public String roleAdd(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String roleName = req.getParameter("roleName");
		String roleDesc = req.getParameter("roleDesc");
		String[] rolePrivilieges = req.getParameterValues("rolePriviliege");

		String rolePriviliege = "";
		if (null != rolePrivilieges) {
			for (String priviliege : rolePrivilieges) {
				if (rolePriviliege.length() > 0)
					rolePriviliege += ",";
				rolePriviliege += priviliege;
			}
		}
		Role role = new Role();
		role.setRoleDesc(roleDesc == null ? "" : roleDesc.trim());
		role.setRoleName(roleName.trim());
		role.setRolePrivilieges(rolePriviliege);
		role.setGmtCreate(Constants.decia.format(new Date()));

		roleService.addRole(role);

		return "redirect:/role/role-index.do";
	}

	@RequestMapping(value = "/role/role-update-view.do")
	public String roleUpdateView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String roleId = req.getParameter("roleId");
		if (StringUtils.isEmpty(roleId))
			throw new IOException("没有输入RoleId");

		Role role = roleService.getRole(Integer.parseInt(roleId));

		Set<Integer> rolePriviliageIds = role.getPriviliege();

		List<Priviliege> privilieges = priviliegeService.listAllPriviliege();

		List<List<Priviliege>> res = new ArrayList<List<Priviliege>>();
		int index = 1;
		List<Priviliege> tuple = new ArrayList<Priviliege>();
		for (Priviliege priviliege : privilieges) {
			if (rolePriviliageIds.contains(priviliege.getPriviliegeId()))
				priviliege.setCheck(true);
			else
				priviliege.setCheck(false);

			tuple.add(priviliege);
			priviliege.setIndex(index - 1);
			if (index++ % 4 == 0) {
				res.add(tuple);
				tuple = new ArrayList<Priviliege>();
			}
		}

		if (tuple.size() != 0)
			res.add(tuple);

		model.addAttribute("role", role);
		model.addAttribute("priviliages", res);
		return "admin/role/role-update-view";
	}

	@RequestMapping(value = "/role/role-update.do")
	public String roleUpdate(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String roleId = req.getParameter("roleId");
		if (StringUtils.isEmpty(roleId))
			throw new IOException("没有输入RoleId");

		Role role = roleService.getRole(Integer.parseInt(roleId));

		String roleName = req.getParameter("roleName");
		String roleDesc = req.getParameter("roleDesc");
		String[] rolePrivilieges = req.getParameterValues("rolePriviliege");

		String rolePriviliege = "";
		if (null != rolePrivilieges) {
			for (String priviliege : rolePrivilieges) {
				if (rolePriviliege.length() > 0)
					rolePriviliege += ",";
				rolePriviliege += priviliege;

			}
		}
		role.setRoleDesc(roleDesc == null ? "" : roleDesc.trim());
		role.setRoleName(roleName.trim());
		role.setRolePrivilieges(rolePriviliege);

		roleService.updateRole(role);

		return "redirect:/role/role-index.do";
	}

	@RequestMapping(value = "/role/role-view.do")
	public String roleView(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
		if (!CommonUtils.checkSessionTimeOut(req))
			return "redirect:/login.do";

		String roleId = req.getParameter("roleId");
		if (StringUtils.isEmpty(roleId))
			throw new IOException("没有输入RoleId");

		Role role = roleService.getRole(Integer.parseInt(roleId));

		Set<Integer> rolePriviliageIds = role.getPriviliege();

		List<Priviliege> privilieges = priviliegeService.listAllPriviliege();

		List<List<Priviliege>> res = new ArrayList<List<Priviliege>>();
		int index = 1;
		List<Priviliege> tuple = new ArrayList<Priviliege>();
		for (Priviliege priviliege : privilieges) {
			if (rolePriviliageIds.contains(priviliege.getPriviliegeId()))
				priviliege.setCheck(true);
			else
				priviliege.setCheck(false);

			tuple.add(priviliege);
			priviliege.setIndex(index - 1);
			if (index++ % 4 == 0) {
				res.add(tuple);
				tuple = new ArrayList<Priviliege>();
			}
		}

		if (tuple.size() != 0)
			res.add(tuple);

		model.addAttribute("role", role);
		model.addAttribute("priviliages", res);
		return "admin/role/role-view";
	}
}
