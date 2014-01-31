package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Report;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:RoleDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("reportDao")
public class ReportDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "reportSpace.";

	@SuppressWarnings("unchecked")
	public List<Report> listAllReport() {
		return (List<Report>) this.list(space + "GET_ALL_REPORT", sqlMapClient);
	}

	public void addReport(Report report) {
		this.insert(space + "ADD_REPORT", report, sqlMapClient);
	}

	public void updateReport(Report report) {
		this.update(space + "UPDATE_REPORT", report, sqlMapClient);
	}
}
