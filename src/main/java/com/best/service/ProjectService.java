package com.best.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.ProjectDao;
import com.best.domain.Project;

/**
 * ClassName:RoleService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Service("projectService")
public class ProjectService extends BaseService {
	@Autowired
	private ProjectDao projectDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_PROJECT_KEY = "ALL_RPROJECT_KEY";

	@SuppressWarnings("unchecked")
	public List<Project> listAllProject() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_PROJECT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = projectDao.listAllProject();
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_PROJECT_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Project>) res;
	}

	public List<Project> listUnEnabledProject() {
		List<Project> projects = listAllProject();
		List<Project> res = new ArrayList<Project>();
		for (Project project : projects) {
			if (project.getProjectEnable() == 0)
				res.add(project);
		}
		return res;
	}

	public void addProject(Project project) {
		try {
			memcachedClient.delete(ALL_PROJECT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		projectDao.addProject(project);
	}

	public Project getProject(int projectId) {
		List<Project> projects = listAllProject();
		for (Project project : projects) {
			if (project.getProjectId() == projectId)
				return project;
		}
		return null;
	}

	public void updateProject(Project project) {
		try {
			memcachedClient.delete(ALL_PROJECT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		projectDao.updateProject(project);
	}
}
