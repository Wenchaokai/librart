package com.best.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.best.dao.BillDao;
import com.best.domain.Bill;

/**
 * ClassName:RoleService Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Service("billService")
public class BillService extends BaseService {
	@Autowired
	private BillDao billDao;

	@Value("${best.base.memcache.timeout}")
	private Integer memcachedTimeOut;

	public static final String ALL_BILL_KEY = "ALL_BILL_KEY";

	@SuppressWarnings("unchecked")
	public List<Bill> listAllBill() {
		Object res = null;
		try {
			res = memcachedClient.get(ALL_BILL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res == null) {
			res = billDao.listAllBill();
		}
		if (null != res) {
			try {
				memcachedClient.set(ALL_BILL_KEY, memcachedTimeOut, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == res)
			return null;
		return (List<Bill>) res;
	}

	public void addBill(Bill bill) {
		try {
			memcachedClient.delete(ALL_BILL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		billDao.addBill(bill);
	}

	public void updateBill(Bill bill) {
		try {
			memcachedClient.delete(ALL_BILL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		billDao.updateBill(bill);
	}

	public void deleteBill(Integer billId) {
		try {
			memcachedClient.delete(ALL_BILL_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		billDao.deleteBill(billId);
	}

	public Bill getBillById(int billId) {
		return billDao.getBill(billId);
	}

	public List<Bill> listAllNonFinishBill(String currentTime) {
		return billDao.listAllNonFinishBill(currentTime);
	}
}
