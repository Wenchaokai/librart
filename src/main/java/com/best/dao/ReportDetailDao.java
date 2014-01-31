package com.best.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.ReportDetail;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:RoleDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("reportDetailDao")
public class ReportDetailDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "reportDetailSpace.";

	@SuppressWarnings("unchecked")
	public List<ReportDetail> listAllReportDetail() {
		return (List<ReportDetail>) this.list(space + "GET_ALL_REPORT_DETAIL", sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<ReportDetail> listAllNonFinishReportDetail(String endTime) {
		return (List<ReportDetail>) this.list(space + "GET_ALL_REPORT_DETAIL_NON_FINISH", endTime, sqlMapClient);
	}

	public void addReportDetail(ReportDetail reportDetail) {
		this.insert(space + "ADD_REPORT_DETAIL", reportDetail, sqlMapClient);
	}

	public void updateReportDetail(Map<String, Object> map) {
		this.update(space + "UPDATE_REPORT_DETAIL", map, sqlMapClient);
	}

	public void deleteReportDetail(Integer reportDetailId) {
		this.update(space + "DELETE_REPORT_DETAIL", reportDetailId, sqlMapClient);
	}
}
