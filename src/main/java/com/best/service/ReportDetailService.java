package com.best.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.ReportDetailDao;
import com.best.domain.ReportDetail;

/**
 * ClassName:RoleService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Service("reportDetailService")
public class ReportDetailService extends BaseService {
	@Autowired
	private ReportDetailDao reportDetailDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_REPORT_DETAIL_KEY = "ALL_REPORT_DETAIL_KEY";

	@SuppressWarnings("unchecked")
	public List<ReportDetail> listAllReportDetail() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_REPORT_DETAIL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = reportDetailDao.listAllReportDetail();
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_REPORT_DETAIL_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<ReportDetail>) res;
	}

	public void addDetailReport(ReportDetail reportDetail) {
		try {
			memcachedClient.delete(ALL_REPORT_DETAIL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reportDetailDao.addReportDetail(reportDetail);
	}

	public void updateReportDetail(Map<String, Object> map) {
		try {
			memcachedClient.delete(ALL_REPORT_DETAIL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reportDetailDao.updateReportDetail(map);
	}

	public void deleteReportDetail(Integer reportDetailId) {
		try {
			memcachedClient.delete(ALL_REPORT_DETAIL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reportDetailDao.deleteReportDetail(reportDetailId);
	}
}
