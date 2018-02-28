package ggd.auth.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import baytony.util.Profiler;
import ggd.auth.vo.AdmFunc;
import ggd.auth.vo.AdmFuncEntity;
import ggd.core.db.HibernateDao;

@Repository("AdmFuncDao")
public class AdmFuncDao extends HibernateDao<AdmFunc, String> {

	private final static Logger log = LoggerFactory.getLogger(AdmFuncDao.class);

	private static final String SQL_ENABLE = "update Adm_Func set isEnabled = true, isApproved = false where func_id = ?";
	private static final String SQL_DISABLE = "update Adm_Func set isEnabled = false, isApproved = false where func_id = ?";
	private static final String SQL_APPROVED = "update Adm_Func set isApproved = true, isEnabled = true where func_id = ?";
	private static final String SQL_UNAPPROVED = "update Adm_Func set isApproved = false where func_id = ?";
	private static final String SQL_GET_MAX_ID = "select max(func_id) from adm_func";
	private static final String SQL_ADD_NEW_FUNCTION = "insert into adm_func(func_id, func_name, parent_id, is_root, url, sort, create_date, isEnabled, isApproved) values (?, ?, ?, ?, ?, ?, now(), ?, ?)";
	private static final String SQL_UPDATE_FUNCTION = "update adm_func set func_name = ?, parent_id = ?, is_root = ?, sort = ?, url = ?, isEnabled = ?, isApproved = ?, update_date = now() where func_id = ?";
	private static final String HQL_FIND_ALL_ROOT_FUNCTION = "from AdmFunc a where a.isRoot = true";
	private static final String HQL_FIND_ALL_APPROVED = "from AdmFunc a where a.isEnabled = true and a.isApproved = true order by a.parent.funcId";
	private static final String SQL_FIND_ALL_FUNCTION = "select func_id, func_name, isEnabled, isApproved from adm_func";
	
	public List<AdmFuncEntity> findAllFuncEntity() {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllFuncEntity()", this.getClass());
		List<AdmFuncEntity> list = super.findBySql(SQL_FIND_ALL_FUNCTION, AdmFuncEntity.class);
		log.info("END: {}.findAllFuncEntity(), exec TIME: {} msl", this.getClass(), p.executeTime());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmFunc> findAllApprovedFunc() {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllApprovedFunc()", this.getClass());
		List<AdmFunc> list = (List<AdmFunc>) super.findByHql(HQL_FIND_ALL_APPROVED);
		log.info("END: {}.findAllApprovedFunc(), list: {}, exec TIME: {} msl", this.getClass(), list, p.executeTime());
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmFunc> findAllRootFunc() {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllRootFunc()", this.getClass());
		List<AdmFunc> list = (List<AdmFunc>) super.findByHql(HQL_FIND_ALL_ROOT_FUNCTION);
		log.info("END: {}.findAllRootFunc(), list: {}, exec TIME: {} msl", this.getClass(), list, p.executeTime());
		return list;
	}
	
	public void updateFunc(String funcId, String funcName, boolean isRoot, String parentId, String url, int sort, boolean isEnabled, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateFunc(), funcId: {} funcName: {}, isRoot: {}, parentId: {}, url: {}, sort: {}, isEnabled: {}, isApproved: {}", this.getClass(), funcId, funcName, isRoot, parentId, url, sort, isEnabled, isApproved);
		String pid = parentId;
		String u = url;
		if(isRoot) {
			pid = funcId;
			u = "";
		}
		super.executeUpateQuery(SQL_UPDATE_FUNCTION, funcName, pid, isRoot, sort, u, isEnabled, isApproved, funcId);
		log.info("END: {}.updateFunc(), funcName: {}, isRoot: {}, parentId: {}, url: {}, sort: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), funcName, isRoot, parentId, url, sort, isEnabled, isApproved, p.executeTime());
	}
	
	public void addNewFunc(String funcName, boolean isRoot, String parentId, String url, int sort, boolean isEnabled, boolean isApproved) {
		Profiler p = new Profiler();
		log.trace("START: {}.addNewFunc(), funcName: {}, isRoot: {}, parentId: {}, url: {}, sort: {}, isEnabled: {}, isApproved: {}", this.getClass(), funcName, isRoot, parentId, url, sort, isEnabled, isApproved);
		String nextId = this.getNextId();
		String pid = parentId;
		if(isRoot) {
			pid = nextId;
		}
		super.executeUpateQuery(SQL_ADD_NEW_FUNCTION, nextId, funcName, pid, isRoot, url, sort, isEnabled, isApproved);
		log.info("END: {}.addNewFunc(), funcName: {}, isRoot: {}, parentId: {}, url: {}, sort: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), funcName, isRoot, parentId, url, sort, isEnabled, isApproved, p.executeTime());
	}
	
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
	
	@SuppressWarnings("unchecked" )
	private String getNextId() {
		Profiler p = new Profiler();
		log.trace("START: {}.getNextId()", this.getClass());
		String maxId = ((List<String>) super.findBySql(SQL_GET_MAX_ID)).get(0);
		String seq = maxId.substring(3);
		int i = Integer.parseInt(seq);		
		String nextId = "FUN" + String.format("%07d", ++i);
		log.debug("nextId: {}", nextId);
		log.info("END: {}.getNextId(), nextId: {}, exec TIME: {} ms.", this.getClass(), nextId, p.executeTime());
		return nextId;
	}
}
