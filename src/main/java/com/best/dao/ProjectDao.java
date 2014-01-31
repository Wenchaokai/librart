package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Project;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:RoleDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("projectDao")
public class ProjectDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "projectSpace.";

	@SuppressWarnings("unchecked")
	public List<Project> listAllProject() {
		return (List<Project>) this.list(space + "GET_ALL_PROJECT", sqlMapClient);
	}

	public void addProject(Project project) {
		this.insert(space + "ADD_PROJECT", project, sqlMapClient);
	}

	public void updateProject(Project project) {
		this.update(space + "UPDATE_PROJECT", project, sqlMapClient);
	}
}
