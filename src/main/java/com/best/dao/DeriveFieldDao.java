package com.best.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:DeriveFieldDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-3-2
 */
@Repository("deriveDao")
public class DeriveFieldDao extends BaseDao {

	@Resource(name = "ecbossSqlMapClient")
	protected SqlMapClient ecbossSqlMapClient;

	@Resource(name = "wmsSqlMapClient")
	protected SqlMapClient sqlMapClient;

	public Object getData(Map<String, Object> map, String sqlInfo) {
		if (sqlInfo == null || sqlInfo.length() == 0 || sqlInfo.split("&").length != 2)
			return null;
		String[] params = sqlInfo.split("&");
		SqlMapClient client = null;
		if (params[0].equals("1"))
			client = ecbossSqlMapClient;
		else if (params[0].equals("2"))
			client = sqlMapClient;

		if (client == null)
			return null;

		Object res = this.object(params[1], map, client);

		return res;
	}

	public List<Object> getDataList(Map<String, Object> map, String sqlInfo) {
		if (sqlInfo == null || sqlInfo.length() == 0 || sqlInfo.split("&").length != 2)
			return null;
		String[] params = sqlInfo.split("&");
		SqlMapClient client = null;
		if (params[0].equals("1"))
			client = ecbossSqlMapClient;
		else if (params[0].equals("2"))
			client = sqlMapClient;

		if (client == null)
			return null;

		List<Object> res = (List<Object>) this.list(params[1], map, client);

		return res;
	}
}
