package com.best.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.BillDetailDao;
import com.best.domain.BillDetail;

/**
 * ClassName:RoleService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Service("billDetailService")
public class BillDetailService extends BaseService {
	@Autowired
	private BillDetailDao billDetailDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_BILL_DETAIL_KEY = "ALL_BILL_DETAIL_KEY_";

	@SuppressWarnings("unchecked")
	public List<BillDetail> listAllBillDetail(Integer billId) {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_BILL_DETAIL_KEY + billId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = billDetailDao.listAllBillDetail(billId);
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_BILL_DETAIL_KEY + billId, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<BillDetail>) res;
	}

	public BillDetail getBillDetail(Integer billDetailId) {
		return billDetailDao.getBillDetail(billDetailId);
	}

	public void addBillDetail(BillDetail billDetail) {
		try {
			memcachedClient.delete(ALL_BILL_DETAIL_KEY + billDetail.getBillId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		billDetailDao.addBillDetail(billDetail);
	}

	public void updateBillDetail(BillDetail billDetail) {
		try {
			memcachedClient.delete(ALL_BILL_DETAIL_KEY + billDetail.getBillId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		billDetailDao.updateBillDetail(billDetail);
	}

	public void deleteBillDetail(Integer billDetailId, Integer billId) {
		try {
			memcachedClient.delete(ALL_BILL_DETAIL_KEY + billId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		billDetailDao.deleteBillDetail(billDetailId);
	}
}
