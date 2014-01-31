package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Priviliege;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:PriviliegeDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-7
 */
@Repository("priviliegeDao")
public class PriviliegeDao extends BaseDao {

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	private static final String space = "priviliegeSpace.";

	@SuppressWarnings("unchecked")
	public List<Priviliege> listAllPriviliege() {
		return (List<Priviliege>) this.list(space + "GET_ALL_PRIVILIEGE", sqlMapClient);
	}

}
