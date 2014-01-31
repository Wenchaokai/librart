package com.best.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Inventory;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:FieldDao Description: k
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-14
 */
@Repository("inventoryDao")
public class InventoryDao extends BaseDao {
	private static final String space = "inventorySpace.";

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	public void addInventory(Inventory inventory) {
		this.insert(space + "ADD_INVENTORY", inventory, sqlMapClient);
	}

	public Inventory getInventory(Map<String, Object> map) {
		return (Inventory) this.object(space + "GET_INVENTORY", map, sqlMapClient);
	}

}
