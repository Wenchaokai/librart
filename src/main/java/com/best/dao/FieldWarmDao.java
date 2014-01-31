package com.best.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.best.domain.FieldWarm;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ClassName:FieldWarmDao Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2013-12-31
 */
@Repository("fieldWarmDao")
public class FieldWarmDao extends BaseDao {
	private static final String space = "fieldWarmSpace.";

	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;

	@SuppressWarnings("unchecked")
	public List<FieldWarm> listAllFieldWarm() {
		return (List<FieldWarm>) this.list(space + "GET_ALL_FIELD_WARM", sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<FieldWarm> listAllFieldWarmByUserId(Long userId) {
		return (List<FieldWarm>) this.list(space + "GET_ALL_FIELD_WARM_BY_USER_ID", userId, sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<FieldWarm> listFieldWarm(Integer fieldWarmProjectId) {
		return (List<FieldWarm>) this.list(space + "GET_FIELD_WARM", fieldWarmProjectId, sqlMapClient);
	}

	public FieldWarm getFieldWarm(Integer projectId, Integer fieldId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		map.put("fieldId", fieldId);
		return (FieldWarm) this.object(space + "GET_FIELD_WARM_BY_PROJECT", map, sqlMapClient);

	}

	public void deleteFieldWarmByProjectId(Integer fieldWarmProjectId) {
		this.delete(space + "DELETE_FIELD_WARM_BY_PROJECT_ID", fieldWarmProjectId, sqlMapClient);
	}

	public void deleteFieldWarmById(Integer fieldWarmId) {
		this.delete(space + "DELETE_FIELD_WARM_BY_ID", fieldWarmId, sqlMapClient);
	}

	public void addFieldWarm(FieldWarm fieldWarm) {
		this.insert(space + "ADD_FIELD_WARM", fieldWarm, sqlMapClient);
	}

	public void updateFieldWarm(FieldWarm fieldWarm) {
		this.update(space + "UPDATE_FIELD_WARM", fieldWarm, sqlMapClient);
	}
}
