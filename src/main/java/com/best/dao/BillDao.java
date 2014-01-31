package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Bill;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:RoleDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("billDao")
public class BillDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "billSpace.";

	@SuppressWarnings("unchecked")
	public List<Bill> listAllBill() {
		return (List<Bill>) this.list(space + "GET_ALL_BILL", sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<Bill> listAllNonFinishBill(String endTime) {
		return (List<Bill>) this.list(space + "GET_ALL_BILL_NON_FINISH", endTime, sqlMapClient);
	}

	public void addBill(Bill bill) {
		this.insert(space + "ADD_BILL", bill, sqlMapClient);
	}

	public void updateBill(Bill bill) {
		this.update(space + "UPDATE_BILL", bill, sqlMapClient);
	}

	public void deleteBill(Integer billId) {
		this.delete(space + "DELETE_BILL", billId, sqlMapClient);
	}

	public Bill getBill(int billId) {
		return (Bill) this.object(space + "GET_BILL", billId, sqlMapClient);
	}
}
