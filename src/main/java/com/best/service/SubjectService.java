package com.best.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.SubjectDao;
import com.best.domain.Subject;

/**
 * ClassName:SubjectService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-1-9
 */
@Service("subjectService")
public class SubjectService extends BaseService {

	@Autowired
	private SubjectDao subjectDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_SUBJECT_KEY = "ALL_SUBJECT_KEY";

	public static final String ALL_SUBJECT_KEY_PROJECT = "ALL_SUBJECT_KEY_PROJECT_";

	@SuppressWarnings("unchecked")
	public List<Subject> listAllSubject() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_SUBJECT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = subjectDao.listAllSubject();
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_SUBJECT_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Subject>) res;
	}

	@SuppressWarnings("unchecked")
	public List<Subject> listSubjectById(Integer pId) {
		Object res = null;
		// try {
		// res = memcachedClient.get(ALL_SUBJECT_KEY_PROJECT + pId);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		if (res == null) {
			res = subjectDao.listSubjects(pId);
		}
		// if (null != res) {
		// try {
		// memcachedClient.set(ALL_SUBJECT_KEY_PROJECT + pId, memcachedTimeOut,
		// res);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		if (null == res)
			return null;
		return (List<Subject>) res;
	}

	public void addSubject(Subject subject) {
		try {
			memcachedClient.delete(ALL_SUBJECT_KEY);
			memcachedClient.delete(ALL_SUBJECT_KEY_PROJECT + subject.getSubjectProjectId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		subjectDao.addSubject(subject);
	}

	public Subject getSubject(int subjectId) {
		return subjectDao.getSubject(subjectId);
	}

	public void updateSubject(Subject subject) {
		try {
			memcachedClient.delete(ALL_SUBJECT_KEY);
			memcachedClient.delete(ALL_SUBJECT_KEY_PROJECT + subject.getSubjectProjectId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		subjectDao.updateSubject(subject);
		subjectDao.updateSubjectByParent(subject);
	}

	public List<Subject> listCommonSubjects() {
		List<Subject> subjects = listAllSubject();
		List<Subject> commonSubjects = new ArrayList<Subject>();
		for (Subject subject : subjects) {
			if (subject.getSubjectType() == 2 && subject.getSubjectProjectId() == -1) {
				commonSubjects.add(subject);
			}
		}
		return commonSubjects;
	}

	public List<Subject> listCommonSubjectsByProjectId(Integer pId) {
		List<Subject> subjects = listSubjectById(pId);
		List<Subject> commonSubjects = new ArrayList<Subject>();
		for (Subject subject : subjects) {
			if (subject.getSubjectType() == 2) {
				commonSubjects.add(subject);
			}
		}
		return commonSubjects;
	}

	public void deleteSubject(int subjectId, int productId) {
		try {
			memcachedClient.delete(ALL_SUBJECT_KEY);
			memcachedClient.delete(ALL_SUBJECT_KEY_PROJECT + productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		subjectDao.deleteSubject(subjectId);
	}

}
