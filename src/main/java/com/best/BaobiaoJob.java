package com.best;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.best.dao.EcbossDao;
import com.best.dao.ReportDetailDao;
import com.best.domain.ReportDetail;
import com.best.domain.wms.Asn;
import com.best.domain.wms.AsnItem;
import com.best.domain.wms.Ldo;
import com.best.domain.wms.LdoItem;
import com.best.service.ReportDetailService;
import com.best.utils.CommonUtils;
import com.best.utils.DateUtil;

/**
 * ClassName:BaobiaoJob Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-2-9
 */
public class BaobiaoJob {
	@Autowired
	private ReportDetailDao reportDetailDao;

	@Autowired
	private ReportDetailService reportDetailService;

	@Autowired
	private EcbossDao ecbossDao;

	private String baseDir;

	private ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(2);

	public void init() {

		scheduleService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String currentDate = DateUtil.getCurrentDateString();
				List<ReportDetail> reportDetails = reportDetailDao.listAllNonFinishReportDetail(currentDate);
				for (ReportDetail reportDetail : reportDetails) {
					if (StringUtils.isEmpty(reportDetail.getWareHouseCode())
							|| StringUtils.isEmpty(reportDetail.getCustomerCode()))
						continue;
					if (currentDate.compareTo(reportDetail.getReportDetailEnd()) >= 0) {
						String reportField = reportDetail.getReportField();
						if (StringUtils.isEmpty(reportDetail))
							continue;
						List<String> fields = new ArrayList<String>();
						fields.add("createTime");
						List<String> fieldNames = new ArrayList<String>();
						fieldNames.add("创建时间");
						String[] params = reportField.split(",");
						int type = 1;
						for (String param : params) {
							String[] splits = param.split("&");
							if (splits.length != 3)
								continue;

							if (splits[0].equals("ASN"))
								type = 1;
							else if (splits[0].equals("ASN_ITEM"))
								type = 2;
							else if (splits[0].equals("LDO"))
								type = 3;
							else if (splits[0].equals("LDO_ITEM"))
								type = 4;
							if (splits[1].equals("createTime"))
								continue;
							fields.add(splits[1]);
							fieldNames.add(splits[2]);
						}
						String startTime = reportDetail.getReportDetailStart();
						String endTime = reportDetail.getReportDetailEnd();
						String nextStartTime = startTime;
						String key = "REPORT_ID_" + reportDetail.getReportDetailId();

						String dir = baseDir + "/baobiao/file";
						try {
							int index = CommonUtils.createAsnExcelFile(null, fields, fieldNames, 0, dir, key, true);
							boolean flag = true;
							while (nextStartTime.compareTo(endTime) <= 0) {
								try {
									String nextEndTime = DateUtil.getNextDate(nextStartTime);
									if (type == 1) {
										List<Asn> asns = ecbossDao.listAsns(reportDetail.getCustomerCode(),
												reportDetail.getWareHouseCode(), nextStartTime, nextEndTime);
										index = CommonUtils.createAsnExcelFile(asns, fields, fieldNames, index, dir, key, false);
									} else if (type == 2) {
										List<AsnItem> asnItems = ecbossDao.listAsnItems(reportDetail.getCustomerCode(),
												reportDetail.getWareHouseCode(), nextStartTime, nextEndTime);
										index = CommonUtils.createAsnItemExcelFile(asnItems, fields, fieldNames, index, dir, key,
												false);
									} else if (type == 3) {
										List<Ldo> ldos = ecbossDao.listLdos(reportDetail.getCustomerCode(),
												reportDetail.getWareHouseCode(), nextStartTime, nextEndTime);
										index = CommonUtils.createLdoExcelFile(ldos, fields, fieldNames, index, dir, key, false);
									} else {
										List<LdoItem> ldoItems = ecbossDao.listLdoItems(fields, reportDetail.getCustomerCode(),
												reportDetail.getWareHouseCode(), nextStartTime, nextEndTime);
										index = CommonUtils.createLdoItemExcelFile(ldoItems, fields, fieldNames, index, dir, key,
												false);
									}
									nextStartTime = nextEndTime;
								} catch (ParseException e) {
									e.printStackTrace();
									flag = false;
									break;
								}
							}
							if (flag) {
								String filePath = "/json/baobiao/file/" + key + ".xls";
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("filePath", filePath);
								map.put("reportProgress", 100);
								map.put("reportDetailId", reportDetail.getReportDetailId());

								reportDetailService.updateReportDetail(map);

							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		}, 0L, 3L, TimeUnit.HOURS);

	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

}
