package com.best.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.Field;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:FieldDao Description: k
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-14
 */
@Repository("fieldDao")
public class FieldDao extends BaseDao {
	private static final String space = "fieldSpace.";

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<Field> listAllField(int type) {
		return (List<Field>) this.list(space + "GET_ALL_FIELD", type, sqlMapClient);
	}

	public void addField(Field field) {
		this.insert(space + "ADD_FIELD", field, sqlMapClient);
	}

	public Field getField(int fieldId) {
		return (Field) this.object(space + "GET_FIELD", fieldId, sqlMapClient);
	}

	public void updateField(Field field) {
		this.update(space + "UPDATE_FIELD", field, sqlMapClient);
	}
}
