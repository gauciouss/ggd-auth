package ggd.auth.dao;

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
	private static final String SQL_ENABLE_GROUP = "update Adm_Group set isEnabled = true where group_id = ?";
	private static final String SQL_DISABLE_GROUP = "update Adm_Group set isEnabled = false, isApproved = false where group_id = ?";
	private static final String SQL_APPROVED_GROUP = "update Adm_Group set isApproved = true where group_id = ?";
	private static final String SQL_UNAPPROVED_GROUP = "update Adm_Group set isApproved = false where group_id = ?";
	
	
	public void addNewGroup(AdmGroup grp) {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewGroup(). grp: {}", this.getClass(), grp);
		super.executeUpateQuery(SQL_ADD_NEW_GROUP, grp.getGroupId(), grp.getGroupName());
		log.info("END: {}.addNewGroup(), exec time: {} ms. grp: {}", this.getClass(), p.executeTime(), grp);
	}
	
	
	public void updateGroupInfo(AdmGroup grp) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateGroupInfo(). grp: {}", this.getClass(), grp);
		super.executeUpateQuery(SQL_UPDATE_GROUP, grp.getGroupName(), grp.getGroupId());
		log.info("END: {}.updateGroupInfo(), exec time: {} ms. grp: {}", this.getClass(), p.executeTime(), grp);
	}
	
	
}
