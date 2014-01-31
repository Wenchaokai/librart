package com.best.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.wms.Asn;
import com.best.domain.wms.AsnItem;
import com.best.domain.wms.Ldo;
import com.best.domain.wms.LdoItem;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:RoleDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("ecbossDao")
public class EcbossDao extends BaseDao {

	@Resource(name = "ecbossSqlMapClient")
	protected SqlMapClient ecbossSqlMapClient;

	private static final String asnspace = "asnSpace.";
	private static final String asnItemSpace = "asnItemSpace.";
	private static final String ldoSpace = "ldoSpace.";
	private static final String ldoItemSpace = "ldoItemSpace.";

	@SuppressWarnings("unchecked")
	public List<Asn> listAsns(String customerCode, String wareHouseCode, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerCode", customerCode);
		map.put("warehouseCode", wareHouseCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return (List<Asn>) this.list(asnspace + "GET_DATA", map, ecbossSqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<AsnItem> listAsnItems(String customerCode, String wareHouseCode, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerCode", customerCode);
		map.put("warehouseCode", wareHouseCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return (List<AsnItem>) this.list(asnItemSpace + "GET_DATA", map, ecbossSqlMapClient);

	}

	@SuppressWarnings("unchecked")
	public List<Ldo> listLdos(String customerCode, String wareHouseCode, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerCode", customerCode);
		map.put("warehouseCode", wareHouseCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return (List<Ldo>) this.list(ldoSpace + "GET_DATA", map, ecbossSqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<LdoItem> listLdoItems(List<String> fields, String customerCode, String wareHouseCode, String startTime,
			String endTime) {
		String fieldString = "";
		boolean flag = false;
		for (String field : fields) {
			if (flag)
				fieldString += ",";
			if (field.equals("createTime"))
				fieldString += "to_char(a.CREATE_TIME,'yyyyMMdd') AS create_time";
			else
				fieldString += field;
			flag = true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fields", fieldString);
		map.put("customerCode", customerCode);
		map.put("warehouseCode", wareHouseCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return (List<LdoItem>) this.list(ldoItemSpace + "GET_DATA", map, ecbossSqlMapClient);

		// List<LdoItem> res = new ArrayList<LdoItem>();
		// String tt = DateUtil.getCurrentDateString();
		// for (int i = 0; i < 10; i++) {
		// LdoItem item = new LdoItem();
		// try {
		// item.setCreateTime(DateUtil.getNextDate(tt));
		// tt = DateUtil.getNextDate(tt);
		// res.add(item);
		// } catch (ParseException e) {
		// }
		//
		// }
		// return res;
	}

}
