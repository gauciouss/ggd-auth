package ggd.auth;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import ggd.auth.dao.AdmFuncDao;
import ggd.auth.dao.AdmGroupDao;
import ggd.auth.dao.AdmUserDao;
import ggd.auth.vo.AdmFunc;
import ggd.auth.vo.AdmFuncEntity;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmGroupEntity;
import ggd.auth.vo.AdmUser;

@Service("AuthService")
public class AuthServiceImpl implements AuthService {
	
	private final static Logger log = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	@Qualifier("AdmUserDao")
	private AdmUserDao admUserDao;
	
	@Autowired
	@Qualifier("AdmGroupDao")
	private AdmGroupDao admGroupDao;
	
	@Autowired
	@Qualifier("AdmFuncDao")
	private AdmFuncDao admFuncDao;

	@Override
	public AdmUser authenticate(String account, String pwd) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.authenticate(), account: {}, pwd: {} ", this.getClass(), account, pwd);
		AdmUser user = admUserDao.getApprovedUser(account);
		log.debug("authenticate user: {}", user);
		if(user != null && !StringUtil.isEmptyString(pwd) && pwd.equals(user.getPwd())) {
			
			log.info("END: {}.authenticate(), account: {}, pwd: {}, exec TIME: {} ms.", this.getClass(), account, pwd, p.executeTime());
			return user;
		}
		return null;
	}
	
	@Override
	public List<AdmUser> findUsers() throws AuthException {
		Profiler p = new Profiler();
		List<AdmUser> users = admUserDao.findUsers();
		log.info("END: {}.findUsers(), users: {}, exec TIME: {} ms.", this.getClass(), users, p.executeTime());
		return users;
	}
	
	@Override
	public List<AdmUser> findUsers(int page, int row) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findUsers(), page: {}, row: {}", this.getClass(), page, row);
		List<AdmUser> users = admUserDao.findUsers(page, row);
		log.info("END: {}.findUsers(), users: {}, exec TIME: {} ms.", this.getClass(), users, p.executeTime());
		return users;
	}
	
	@Override
	public List<AdmUser> findUsers(String value) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findUsers(), value: {}", this.getClass(), value);
		List<AdmUser> users = admUserDao.findUsers(value);
		log.info("END: {}.findUsers(), users: {}, exec TIME: {} ms.", this.getClass(), users, p.executeTime());
		return users;
	}
	
	@Override
	public AdmUser findUserById(String id) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findUserById(), account: {}", this.getClass(), id);
		AdmUser user = admUserDao.findById(id);
		log.info("END: {}.findUserById(), user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
		return user;
	}

	@Override
	public void addUser(AdmUser user) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.addUser(), user: {}", this.getClass(), user);
		admUserDao.save(user);
		log.info("END: {}.addUser(), user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
	}

	
	@Override
	public void updateUser(String account, String password, String name, String email, String address, String tel, String phone, String groupId) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.updateUser(), account: {}, password: {}, name: {}, email: {}, address: {}, tel: {}, phone: {}, groupId: {}", this.getClass(), account, password, name, email, address, tel, phone, groupId);
		AdmUser user = admUserDao.findById(account);
		user.setPwd(password);
		user.setAddress(address);
		user.setApproved(false);
		user.setEnabled(false);
		user.setEmail(email);
		user.setName(name);
		user.setPhone(phone);
		user.setTel(tel);
		user.setGroupId(groupId);
		admUserDao.update(user);
		log.info("END: {}.updateUser(), update user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
	}
	
	@Override
	public void updateUser(String account, String password, String name, String email, String address, String tel, String phone, String groupId, boolean isEnabled, boolean isApproved) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.updateUser(), account: {}, password: {}, name: {}, email: {}, address: {}, tel: {}, phone: {}, groupId: {}, isEnabled: {}, isApproved: {}", this.getClass(), account, password, name, email, address, tel, phone, groupId, isEnabled, isApproved);
		AdmUser user = admUserDao.findById(account);
		user.setPwd(password);
		user.setAddress(address);
		user.setApproved(false);
		user.setEnabled(false);
		user.setEmail(email);
		user.setName(name);
		user.setPhone(phone);
		user.setTel(tel);
		user.setEnabled(isEnabled);
		user.setApproved(isApproved);
		user.setGroupId(groupId);
		admUserDao.update(user);
		log.info("END: {}.updateUser(), update user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
	}

	@Override
	public void enableUser(String account) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.enableUser(), account: {}", this.getClass(), account);
		admUserDao.enable(account, true);
		log.info("END: {}.enableUser(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}

	@Override
	public void disableUser(String account) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.disableUser(), account: {}", this.getClass(), account);
		admUserDao.enable(account, false);
		log.info("END: {}.disableUser(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}

	@Override
	public void approveUser(String account) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.approveUser(), account: {}", this.getClass(), account);
		admUserDao.approve(account, true);
		log.info("END: {}.approveUser(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}

	@Override
	public void unApproveUser(String account) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.unApproveUser(), account: {}", this.getClass(), account);
		admUserDao.approve(account, false);
		log.info("END: {}.unApproveUser(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}

	@Override
	public String addGroup(String grpName, boolean isManager, boolean isEnabled, boolean isApproved) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.addGroup(), grpName: {}", this.getClass(), grpName);
		String grpId = admGroupDao.addNewGroup(grpName, isManager, isEnabled, isApproved);
		log.info("END: {}.addGroup(), grpName: {}, exec TIME: {} ms.", this.getClass(), grpName, p.executeTime());
		return grpId;
	}

	@Override
	public void updateGroup(String grpId, String grpName, boolean isEnabled, boolean isApproved) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.updateGroup(), grpId: {}, grpName: {}, isEnabled: {}, isApproved: {}", this.getClass(), grpId, grpName, isEnabled, isApproved);
		admGroupDao.updateGroupInfo(grpId, grpName, isEnabled, isApproved);
		log.info("END: {}.updateGroup(), grpId: {}, grpName: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), grpId, grpName, isEnabled, isApproved, p.executeTime());
	}

	@Override
	public void enableGroup(String grpId) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.enableGroup(), grpId: {}", this.getClass(), grpId);
		admGroupDao.enable(grpId, true);
		log.info("END: {}.enableGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}

	@Override
	public void disableGroup(String grpId) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.disableGroup(), grpId: {}", this.getClass(), grpId);
		admGroupDao.enable(grpId, false);
		log.info("END: {}.disableGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}

	@Override
	public void approveGroup(String grpId) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.approveGroup(), grpId: {}", this.getClass(), grpId);
		admGroupDao.approve(grpId, true);
		log.info("END: {}.approveGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}

	@Override
	public void unApproveGroup(String grpId) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.unApproveGroup(), grpId: {}", this.getClass(), grpId);
		admGroupDao.approve(grpId, false);
		log.info("END: {}.unApproveGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}
	
	@Override
	public List<AdmGroup> findAllGroup(boolean isEnabled, boolean isApproved) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllGroup(), isEnabled: {}, isApproved: {}", this.getClass(), isEnabled, isApproved);
		List<AdmGroup> groups = (List<AdmGroup>) admGroupDao.findAll(isEnabled, isApproved);
		log.info("END: {}.findAllGroup(), isEnabled: {}, isApproved: {}, groups: {}, exec TIME: {} ms.", this.getClass(), isEnabled, isApproved, groups, p.executeTime());
		return groups;
	}
	
	@Override
	public List<AdmGroup> findAllGroup() throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllGroup()", this.getClass());
		List<AdmGroup> groups = (List<AdmGroup>) admGroupDao.findAll();
		log.info("END: {}.findAllGroup(), groups: {}, exec TIME: {} ms.", this.getClass(), groups, p.executeTime());
		return groups;
	}
	
	@Override
	public List<AdmGroupEntity> findAllGroupEntity() throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllGroup()", this.getClass());
		List<AdmGroupEntity> groups = admGroupDao.findAllEntity();
		log.info("END: {}.findAllGroup(), groups: {}, exec TIME: {} ms.", this.getClass(), groups, p.executeTime());
		return groups;
	}
	
	@Override
	public AdmGroup findGroup(String id) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findGroup(), id: {}", this.getClass(), id);
		AdmGroup group =  admGroupDao.findById(id);
		log.info("END: {}.findGroup(), id: {}, group: {}, exec TIME: {} ms.", this.getClass(), id, group, p.executeTime());
		return group;
	}

	@Override
	public void addFunc(String funcName, boolean isRoot, String parentId, String url, int sort, boolean isEnabled, boolean isApproved) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.addFunc(), funcName: {}, isRoot: {}, parentId: {}, url: {}, sort: {}, isEnabled: {}, isApproved: {}", this.getClass(), funcName, isRoot, parentId, url, sort, isEnabled, isApproved);
		try {
			admFuncDao.addNewFunc(funcName, isRoot, parentId, url, sort, isEnabled, isApproved);
		}
		catch(Exception e) {
			throw new AuthException(e);
		}
		log.info("END: {}.addFunc(), funcName: {}, isRoot: {}, parentId: {}, url: {}, sort: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), funcName, isRoot, parentId, url, sort, isEnabled, isApproved, p.executeTime());
	}

	@Override
	public void updateFunc(String funcId, String funcName, boolean isRoot, String parentId, String url, int sort, boolean isEnabled, boolean isApproved) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.updateFunc(), funcId: {}, funcName: {}, isRoot: {}, parentId: {}, url: {}, sort: {}, isEnabled: {}, isApproved: {}", this.getClass(), funcId, funcName, isRoot, parentId, url, sort, isEnabled, isApproved);
		try {
			admFuncDao.updateFunc(funcId, funcName, isRoot, parentId, url, sort, isEnabled, isApproved);
		}
		catch(Exception e) {
			throw new AuthException(e);
		}
		log.info("END: {}.updateFunc(), funcId: {}, funcName: {}, isRoot: {}, parentId: {}, url: {}, sort: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), funcId, funcName, isRoot, parentId, url, sort, isEnabled, isApproved, p.executeTime());
	}
	
	@Override
	public List<AdmFunc> findAllFunc(boolean rootOnly) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllFunc(), rootOnly: {}", this.getClass(), rootOnly);
		List<AdmFunc> list = rootOnly ? admFuncDao.findAllRootFunc() : admFuncDao.findAll();
		log.info("END: {}.findAllFunc(), rootOnly: {}, list: {}, exec TIME: {} ms.", this.getClass(), rootOnly, list, p.executeTime());
		return list;
	}
	
	@Override
	public List<AdmFuncEntity> findAllFuncEntity() throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllFunc()", this.getClass());
		List<AdmFuncEntity> list = admFuncDao.findAllFuncEntity();
		log.debug("function entities: {}", list);
		log.info("END: {}.findAllFunc(), exec TIME: {} ms.", this.getClass(), p.executeTime());
		return list;
	}
	
	@Override
	public AdmFunc findFuncById(String id) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findFuncById(), id: {}", this.getClass(), id);
		AdmFunc func = admFuncDao.findById(id);
		log.info("END: {}.findFuncById(), id: {}, exec TIME: {} ms.", this.getClass(), id, p.executeTime());
		return func;
	}

	@Override
	public void enableFunc(String funcid, boolean isEnabled) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findFuncById(), id: {}, isEnabled: {}", this.getClass(), funcid, isEnabled);
		admFuncDao.enable(funcid, isEnabled);
		log.info("END: {}.findFuncById(), id: {}, isEnabled: {}, exec TIME: {} ms.", this.getClass(), funcid, isEnabled, p.executeTime());
	}

	@Override
	public void approveFunc(String funcid, boolean isApproved) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findFuncById(), id: {}, isApproved: {}", this.getClass(), funcid, isApproved);
		admFuncDao.approve(funcid, isApproved);
		log.info("END: {}.findFuncById(), id: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), funcid, isApproved, p.executeTime());
	}
	
	@Override
	public List<AdmFunc> findAllApprovedFunc() throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.findAllApprovedFunc().", this.getClass());
		List<AdmFunc> list = admFuncDao.findAllApprovedFunc();
		log.info("END: {}.findAllApprovedFunc(), list: {}, exec TIME: {} ms.", this.getClass(), list, p.executeTime());
		return list;
	}

	@Override
	public void addUser2Group(String account, String groupId) throws AuthException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUserFromGroup(String account, String groupId) throws AuthException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFunc2Group(String groupId, List<String> funcs) throws AuthException {
		Profiler p = new Profiler();
		log.trace("START: {}.addFunc2Group(), funcs: {}, groupId: {}", this.getClass(), funcs, groupId);
		admGroupDao.removeAllFunc(groupId);
		List<String> totalPID = new ArrayList<String>();
		for(String func : funcs) {
			AdmFunc pFunc = admFuncDao.findById(func);
			String pid = pFunc.getParent().getFuncId();
			if(!totalPID.contains(pid)) {
				totalPID.add(pid);
				admGroupDao.addFunc2Group(groupId, pid);
			}
			admGroupDao.addFunc2Group(groupId, func);			
			
		}
		log.info("END: {}.addFunc2Group(), funcs: {}, groupId: {}, exec TIME: {} ms.", this.getClass(), funcs, groupId, p.executeTime());
	}

	@Override
	public void removeFuncFromGroup(String funcId, String groupId) throws AuthException {
		// TODO Auto-generated method stub

	}

}
