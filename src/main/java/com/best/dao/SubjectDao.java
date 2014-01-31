package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Subject;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:SubjectDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-1
 */
@Repository("subjectDao")
public class SubjectDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "subjectSpace.";

	@SuppressWarnings("unchecked")
	public List<Subject> listAllSubject() {
		return (List<Subject>) this.list(space + "GET_ALL_SUBJECT", sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<Subject> listAllEnableSubject() {
		return (List<Subject>) this.list(space + "GET_ALL_ENABLE_SUBJECT", sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<Subject> listSubjects(Integer projectId) {
		return (List<Subject>) this.list(space + "GET_ALL_SUBJECT_BY_PROJECT_ID", projectId, sqlMapClient);
	}

	public Subject getSubject(Integer subjectId) {
		return (Subject) this.object(space + "GET_SUBJECT", subjectId, sqlMapClient);
	}

	public void addSubject(Subject subject) {
		this.insert(space + "ADD_SUBJECT", subject, sqlMapClient);
	}

	public void updateSubject(Subject subject) {
		this.update(space + "UPDATE_SUBJECT", subject, sqlMapClient);

	}

	public void updateSubjectByParent(Subject subject) {
		this.update(space + "UPDATE_SUBJECT_PARENT_ID", subject, sqlMapClient);
	}

	public void deleteSubject(int subjectId) {
		this.delete(space + "DELETE_SUBJECT", subjectId, sqlMapClient);
	}

}
