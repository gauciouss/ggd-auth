package ggd.auth.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmGroupEntity;
import ggd.core.db.HibernateDao;

@Repository("AdmGroupDao")
public class AdmGroupDao extends HibernateDao<AdmGroup, String> {
	
	private final static Logger log = LoggerFactory.getLogger(AdmGroupDao.class);

	
	private static final String SQL_ADD_NEW_GROUP = "insert into adm_group(group_id, group_name, is_Manager, create_date, isEnabled, isApproved) values (?, ?, ?, now(), ?, ?)";
	private static final String SQL_UPDATE_GROUP = "update adm_group set group_name = ?, update_date = now(), isEnabled = ? and isApproved = ? where group_id = ?";
	private static final String SQL_ENABLE_GROUP = "update adm_group set isEnabled = true, isApproved = false where group_id = ?";
	private static final String SQL_DISABLE_GROUP = "update adm_group set isEnabled = false, isApproved = false where group_id = ?";
	private static final String SQL_APPROVED_GROUP = "update adm_group set isApproved = true, isEnabled = true where group_id = ?";
	private static final String SQL_UNAPPROVED_GROUP = "update adm_group set isApproved = false where group_id = ?";
	private static final String SQL_GET_MAX_ID = "select max(group_id) from adm_group";
	private static final String HQL_FIND_ALL_GROUP = "from AdmGroup where isEnabled = ? and isApproved = ?";	
	private static final String SQL_REMOVE_ALL_GROUP_FUNC = "delete from adm_group_func_map where group_id = ?";
	private static final String SQL_ADD_FUNC_TO_GROUP = "insert into adm_group_func_map(group_id, func_id) values (?, ?)";
	private static final String SQL_FIND_ALL_GROUP = "select group_id, group_name, isEnabled, isApproved from adm_group";
	
	public List<AdmGroupEntity> findAllEntity() {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllEntity()", this.getClass());
		List<AdmGroupEntity> groups =  super.findBySql(SQL_FIND_ALL_GROUP, AdmGroupEntity.class);
		log.info("END: {}.findAllEntity(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return groups;
	}
	
	
	public void addFunc2Group(String grpId, String funcId) {
		Profiler p = new Profiler();
		log.trace("START: {}.addFunc2Group(), grpId: {}, funcId: {}", this.getClass(), grpId, funcId);
		super.executeUpateQuery(SQL_ADD_FUNC_TO_GROUP, grpId, funcId);
		log.info("END: {}.addFunc2Group(), grpId: {}, funcId: {}, exec TIME: {} ms.", this.getClass(), grpId, funcId, p.executeTime());
	}
	
	public void removeAllFunc(String grpId) {
		Profiler p = new Profiler();
		log.trace("START: {}.removeAllFunc(), grpId: {}", this.getClass(), grpId);
		super.executeUpateQuery(SQL_REMOVE_ALL_GROUP_FUNC, grpId);
		log.info("END: {}.removeAllFunc(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AdmGroup> findAll(boolean isEnabled, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.findAll(), isEnabled: {}, isApproved: {}", this.getClass(), isEnabled, isApproved);
		List<AdmGroup> groups = (List<AdmGroup>) super.findByHql(HQL_FIND_ALL_GROUP, isEnabled, isApproved);
		log.info("END: {}.findAll(), isEnabled: {}, isApproved: {}, groups: {}, exec TIME: {} ms.", this.getClass(), isEnabled, isApproved, groups, p.executeTime());
		return groups;
	}
	
	
	
	public String addNewGroup(String grpName, boolean isManager, boolean isEnabled, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewGroup(), grpName: {}", this.getClass(), grpName);
		String nId = this.getNextId();
		super.executeUpateQuery(SQL_ADD_NEW_GROUP, nId, grpName, isManager, isEnabled, isApproved);
		log.info("END: {}.addNewGroup(), grpName: {}, exec TIME: {} ms.", this.getClass(), grpName, p.executeTime());
		return nId;
	}
	
	
	public void updateGroupInfo(String grpId, String grpName, boolean isEnabled, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateGroupInfo(), grpId: {}, grpName: {}, isEnabled: {}, isApproved: {}", this.getClass(), grpId, grpName, isEnabled, isApproved);
		super.executeUpateQuery(SQL_UPDATE_GROUP, grpName, isEnabled,isApproved, grpId);
		log.info("END: {}.updateGroupInfo(), grpId: {}, grpName: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), grpId, grpName, isEnabled, isApproved, p.executeTime());
	}
	
	
	public void enable(String grpId, boolean isEnabled) {
		Profiler p = new Profiler();
		log.trace("START: {}.enableGroup(), grpId: {}", this.getClass(), grpId);
		if(isEnabled)
			super.executeUpateQuery(SQL_ENABLE_GROUP, grpId);
		else
			super.executeUpateQuery(SQL_DISABLE_GROUP, grpId);
		log.info("END: {}.enableGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}
	
	public void approve(String grpId, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.approveGroup(), grpId: {}", this.getClass(), grpId);
		if(isApproved)
			super.executeUpateQuery(SQL_APPROVED_GROUP, grpId);
		else
			super.executeUpateQuery(SQL_UNAPPROVED_GROUP, grpId);
		log.info("END: {}.approveGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}
	
	
	@SuppressWarnings("unchecked")
	private String getNextId() {
		Profiler p = new Profiler();
		log.trace("START: {}.getNextId()");
		String maxId = ((List<String>) super.findBySql(SQL_GET_MAX_ID)).get(0);
		String seq = maxId.substring(3);
		int i = Integer.parseInt(seq);		
		String nextId = "GRP" + String.format("%07d", ++i);
		log.debug("nextId: {}", nextId);
		log.info("END: {}.getNextId(), nextId: {}, exec TIME: {} ms.", this.getClass(), nextId, p.executeTime());
		return nextId;
	}
}
