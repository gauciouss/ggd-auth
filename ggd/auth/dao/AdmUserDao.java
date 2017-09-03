package ggd.auth.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import baytony.util.Util;
import ggd.auth.vo.AdmUser;
import ggd.core.db.HibernateDao;

@Repository("AdmUserDao")
public class AdmUserDao extends HibernateDao<AdmUser, String> {
	
	private final static Logger log = LoggerFactory.getLogger(AdmUserDao.class);

	private static final String SQL_ENABLE = "update Adm_User set isEnabled = true, isApproved = false where account = ?";
	private static final String SQL_DISABLE = "update Adm_User set isEnabled = false, isApproved = false where account = ?";
	private static final String SQL_APPROVED = "update Adm_User set isApproved = true, isEnabled = true where account = ?";
	private static final String SQL_UNAPPROVED = "update Adm_User set isApproved = false where account = ?";
	private static final String HQL_FIND_APPROVED_USER = "from AdmUser where account = ? and isEnabled = true and isApproved = true";
	private static final String HQL_FIND_USERS = "from AdmUser";
	private static final String HQL_FIND_USERS_BY_ID_OR_NAME = "from AdmUser where account = ? or name like ?";
	
	private static final String SQL_UPDATE_USER = "update adm_user set pwd = ?, name = ?, email = ?, address = ?, tel = ?, phone = ?, group_id = ?, update_date = now() where account = ?";
	
	@SuppressWarnings("unchecked")
	public List<AdmUser> findUsers(String value) {
		List<AdmUser> list = (List<AdmUser>) super.findByHql(HQL_FIND_USERS_BY_ID_OR_NAME, value, "%"+value+"%");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmUser> findUsers(int page, int row) {
		Query query = super.createHqlQuery(HQL_FIND_USERS).setFirstResult((page-1)*row);
		query.setMaxResults(row);
		List<AdmUser> list = query.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public AdmUser getApprovedUser(String account) {
		Profiler p = new Profiler();
		log.trace("START: {}.getApprovedUser(), account: {}", this.getClass(), account);
		List<AdmUser> users = (List<AdmUser>) super.findByHql(HQL_FIND_APPROVED_USER, account);
		log.debug("users count: {}", users.size());
		AdmUser user = null;
		if(!Util.isEmpty(users)) {
			user = users.get(0);
		}
		log.info("END: {}.getApprovedUser(), user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
		return user;
	}
	
	public void enable(String account, boolean isEnabled) {
		Profiler p = new Profiler();
		log.trace("START: {}.enable(), grpId: {}", this.getClass(), account);
		if(isEnabled)
			super.executeUpateQuery(SQL_ENABLE, account);
		else
			super.executeUpateQuery(SQL_DISABLE, account);
		log.info("END: {}.enable(), grpId: {}, exec TIME: {} ms.", this.getClass(), p.executeTime(), account);
	}
	
	public void approve(String account, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.approve(), account: {}", this.getClass(), account);
		if(isApproved)
			super.executeUpateQuery(SQL_APPROVED, account);
		else
			super.executeUpateQuery(SQL_UNAPPROVED, account);
		log.info("END: {}.approve(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}
}
