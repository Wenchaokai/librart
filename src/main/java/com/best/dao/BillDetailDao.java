package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.BillDetail;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:RoleDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("billDetailDao")
public class BillDetailDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "billDetailSpace.";

	@SuppressWarnings("unchecked")
	public List<BillDetail> listAllBillDetail(Integer billId) {
		return (List<BillDetail>) this.list(space + "GET_ALL_BILL_DETAIL", billId, sqlMapClient);
	}

	public void addBillDetail(BillDetail billDetail) {
		this.insert(space + "ADD_BILL_DETAIL", billDetail, sqlMapClient);
	}

	public void updateBillDetail(BillDetail billDetail) {
		this.update(space + "UPDATE_BILL_DETAIL", billDetail, sqlMapClient);
	}

	public void deleteBillDetail(Integer billDetailId) {
		this.update(space + "DELETE_BILL_DETAIL", billDetailId, sqlMapClient);
	}

	public BillDetail getBillDetail(Integer billDetailId) {
		return (BillDetail) this.object(space + "GET_BILL_DETAIL", billDetailId, sqlMapClient);
	}
}
