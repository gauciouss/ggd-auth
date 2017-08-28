package ggd.auth.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.auth.vo.AdmGroup;
import ggd.core.db.HibernateDao;

@Repository("AdmGroupDao")
public class AdmGroupDao extends HibernateDao<AdmGroup, String> {
	
	private final static Logger log = LoggerFactory.getLogger(AdmGroupDao.class);

	
	private static final String SQL_ADD_NEW_GROUP = "insert into Adm_Group values (?, ?, now(), null, false, false)";
	private static final String SQL_UPDATE_GROUP = "update Adm_Group set group_name = ?, update = now() where group_id = ?";
	private static final String SQL_ENABLE_GROUP = "update Adm_Group set isEnabled = true, isApproved = false where group_id = ?";
	private static final String SQL_DISABLE_GROUP = "update Adm_Group set isEnabled = false, isApproved = false where group_id = ?";
	private static final String SQL_APPROVED_GROUP = "update Adm_Group set isApproved = true, isEnabled = true where group_id = ?";
	private static final String SQL_UNAPPROVED_GROUP = "update Adm_Group set isApproved = false where group_id = ?";
	
	private static final String SQL_GET_MAX_ID = "select max(group_id) from Adm_Group";
	
	
	public void addNewGroup(String grpName) {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewGroup(). grpName: {}", this.getClass(), grpName);
		super.executeUpateQuery(SQL_ADD_NEW_GROUP, this.getNextId(), grpName);
		log.info("END: {}.addNewGroup(), grpName: {}, exec TIME: {} ms.", this.getClass(), grpName, p.executeTime());
	}
	
	
	public void updateGroupInfo(AdmGroup grp) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateGroupInfo(). grp: {}", this.getClass(), grp);
		super.executeUpateQuery(SQL_UPDATE_GROUP, grp.getGroupName(), grp.getGroupId());
		log.info("END: {}.updateGroupInfo(), grp: {}, exec TIME: {} ms.", this.getClass(), grp, p.executeTime());
	}
	
	
	public void enableGroup(String grpId, boolean isEnabled) {
		Profiler p = new Profiler();
		log.trace("START: {}.enableGroup(). grpId: {}", this.getClass(), grpId);
		if(isEnabled)
			super.executeUpateQuery(SQL_ENABLE_GROUP, grpId);
		else
			super.executeUpateQuery(SQL_DISABLE_GROUP, grpId);
		log.info("END: {}.enableGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}
	
	public void approveGroup(String grpId, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.approveGroup(). grpId: {}", this.getClass(), grpId);
		if(isApproved)
			super.executeUpateQuery(SQL_APPROVED_GROUP, grpId);
		else
			super.executeUpateQuery(SQL_UNAPPROVED_GROUP, grpId);
		log.info("END: {}.approveGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}
	
	
	public String getNextId() {
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
