package ggd.auth.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.auth.vo.AdmUser;
import ggd.core.db.HibernateDao;

@Repository("AdmUserDao")
public class AdmUserDao extends HibernateDao<AdmUser, String> {
	
	private final static Logger log = LoggerFactory.getLogger(AdmUserDao.class);

	private static final String SQL_ENABLE = "update Adm_User set isEnabled = true, isApproved = false where account = ?";
	private static final String SQL_DISABLE = "update Adm_User set isEnabled = false, isApproved = false where account = ?";
	private static final String SQL_APPROVED = "update Adm_User set isApproved = true, isEnabled = true where account = ?";
	private static final String SQL_UNAPPROVED = "update Adm_User set isApproved = false where account = ?";
	
	public void enable(String account, boolean isEnabled) {
		Profiler p = new Profiler();
		log.trace("START: {}.enable(). grpId: {}", this.getClass(), account);
		if(isEnabled)
			super.executeUpateQuery(SQL_ENABLE, account);
		else
			super.executeUpateQuery(SQL_DISABLE, account);
		log.info("END: {}.enable(), grpId: {}, exec TIME: {} ms.", this.getClass(), p.executeTime(), account);
	}
	
	public void approve(String account, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.approve(). account: {}", this.getClass(), account);
		if(isApproved)
			super.executeUpateQuery(SQL_APPROVED, account);
		else
			super.executeUpateQuery(SQL_UNAPPROVED, account);
		log.info("END: {}.approve(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}
}
