package ggd.auth.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import baytony.util.Profiler;
import ggd.auth.vo.AdmFunc;
import ggd.core.db.HibernateDao;

public class AdmFuncDao extends HibernateDao<AdmFunc, String> {

	private final static Logger log = LoggerFactory.getLogger(AdmFuncDao.class);

	private static final String SQL_ENABLE = "update Adm_Func set isEnabled = true, isApproved = false where func_id = ?";
	private static final String SQL_DISABLE = "update Adm_Func set isEnabled = false, isApproved = false where func_id = ?";
	private static final String SQL_APPROVED = "update Adm_Func set isApproved = true, isEnabled = true where func_id = ?";
	private static final String SQL_UNAPPROVED = "update Adm_Func set isApproved = false where func_id = ?";
	
	public void enable(String funcId, boolean isEnabled) {
		Profiler p = new Profiler();
		log.trace("START: {}.enable(), grpId: {}", this.getClass(), funcId);
		if(isEnabled)
			super.executeUpateQuery(SQL_ENABLE, funcId);
		else
			super.executeUpateQuery(SQL_DISABLE, funcId);
		log.info("END: {}.enable(), grpId: {}, exec TIME: {} ms.", this.getClass(), p.executeTime(), funcId);
	}
	
	public void approve(String funcId, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.approve(), funcId: {}", this.getClass(), funcId);
		if(isApproved)
			super.executeUpateQuery(SQL_APPROVED, funcId);
		else
			super.executeUpateQuery(SQL_UNAPPROVED, funcId);
		log.info("END: {}.approve(), funcId: {}, exec TIME: {} ms.", this.getClass(), funcId, p.executeTime());
	}
}
