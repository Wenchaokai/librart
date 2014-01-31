package com.best.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.ReportDao;
import com.best.domain.Report;

/**
 * ClassName:RoleService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Service("reportService")
public class ReportService extends BaseService {
	@Autowired
	private ReportDao reportDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_REPORT_KEY = "ALL_REPORT_KEY";

	@SuppressWarnings("unchecked")
	public List<Report> listAllReport() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_REPORT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = reportDao.listAllReport();
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_REPORT_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Report>) res;
	}

	public void addReport(Report report) {
		try {
			memcachedClient.delete(ALL_REPORT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reportDao.addReport(report);
	}

	public Report getReport(int reportId) {
		List<Report> reports = listAllReport();
		for (Report report : reports) {
			if (report.getReportId() == reportId)
				return report;
		}
		return null;
	}

	public void updateReport(Report report) {
		try {
			memcachedClient.delete(ALL_REPORT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reportDao.updateReport(report);
	}

	public List<Report> listAllReportByProjectId(int projectId) {
		List<Report> reports = listAllReport();
		List<Report> res = new ArrayList<Report>();
		for (Report report : reports) {
			if (report.getReportProjectId() == projectId)
				res.add(report);
		}
		return res;
	}
}
