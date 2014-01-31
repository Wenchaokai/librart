package com.best;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.best.dao.DeriveFieldDao;
import com.best.dao.FieldDao;
import com.best.dao.InventoryDao;
import com.best.dao.ProjectDao;
import com.best.domain.Field;
import com.best.domain.Inventory;
import com.best.domain.Project;
import com.best.domain.Subject;
import com.best.service.SubjectService;
import com.best.utils.DateUtil;

/**
 * ClassName:InventoryJob Description:
 * 
 * @author ChaoKai Wen email:wenchaokai@gmail.com
 * @version
 * @Date 2014-4-21
 */
public class InventoryJob {

	private static final Logger LOG = LoggerFactory.getLogger(InventoryJob.class);

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private FieldDao fieldDao;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private DeriveFieldDao deriveFieldDao;

	public void triggerInfo() {

		try {
			List<Project> allProjects = projectDao.listAllProject();
			if (allProjects != null) {
				for (Project project : allProjects) {
					LOG.warn(">>>>>>>>>>>>Start Caculate Inventory={} <<<<<<<<<<<<", project.getProjectName());
					List<Subject> subjects = subjectService.listSubjectById(project.getProjectId());
					if (subjects != null) {
						for (Subject subject : subjects) {
							List<Field> allFields = subject.getFields();
							if (allFields != null) {
								for (Field field : allFields) {
									field = fieldDao.getField(field.getFieldId());
									if (field.getFieldType() == 3 && field.getFieldTypeBaobiaoType() == 0) {
										// 开始计算库存量
										String currentDate = DateUtil.getCurrentDateString();
										String preDate = null;
										try {
											preDate = DateUtil.getPreDate();
										} catch (ParseException e) {
											e.printStackTrace();
										}
										Map<String, Object> paramMap = new HashMap<String, Object>();
										paramMap.put("customerCode", project.getCustomerCode());
										paramMap.put("warehouseCode", project.getWareHouseCode());
										paramMap.put("startTime", preDate);
										paramMap.put("endTime", currentDate);

										Object obj = deriveFieldDao.getData(paramMap, field.getFieldTypeMapping());
										if (null != obj) {
											Inventory inventory = new Inventory();
											inventory.setDateTime(preDate);
											inventory.setFieldId(field.getFieldId());
											inventory.setProjectId(project.getProjectId());
											inventory.setSubjectId(subject.getSubjectId());

											inventory.setValue(((Integer) obj).intValue());

											inventoryDao.addInventory(inventory);
										}

										break;
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
