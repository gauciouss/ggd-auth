package ggd.auth;

import java.sql.Timestamp;
import java.util.Calendar;

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
import ggd.auth.vo.AdmGroup;
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

	@Override
	public AdmUser authenticate(String account, String pwd) {
		Profiler p = new Profiler();
		log.trace("START: {}.authenticate(), account: {}, pwd: {} ", this.getClass(), account, pwd);
		AdmUser user = admUserDao.findById(account);
		if(user != null && !StringUtil.isEmptyString(pwd) && pwd.equals(user.getPwd())) {
			log.info("END: {}.authenticate(), account: {}, pwd: {}, exec TIME: {} ms.", this.getClass(), account, pwd, p.executeTime());
			return user;
		}
		return null;
	}

	@Override
	public void addUser(AdmUser user) {
		Profiler p = new Profiler();
		log.trace("START: {}.addUser(), user: {}", this.getClass(), user);
		admUserDao.save(user);
		log.info("END: {}.addUser(), user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
	}

	@Override
	public void updateUser(AdmUser user) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateUser(), user: {}", this.getClass(), user);
		user.setUpdateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));		
		admUserDao.update(user);
		log.info("END: {}.updateUser(), user: {}, exec TIME: {} ms.", this.getClass(), user, p.executeTime());
	}

	@Override
	public void enableUser(String account) {
		Profiler p = new Profiler();
		log.trace("START: {}.enableUser(), account: {}", this.getClass(), account);
		admUserDao.enable(account, true);
		log.info("END: {}.enableUser(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}

	@Override
	public void disableUser(String account) {
		Profiler p = new Profiler();
		log.trace("START: {}.disableUser(), account: {}", this.getClass(), account);
		admUserDao.enable(account, false);
		log.info("END: {}.disableUser(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}

	@Override
	public void approveUser(String account) {
		Profiler p = new Profiler();
		log.trace("START: {}.approveUser(), account: {}", this.getClass(), account);
		admUserDao.approve(account, true);
		log.info("END: {}.approveUser(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}

	@Override
	public void unApproveUser(String account) {
		Profiler p = new Profiler();
		log.trace("START: {}.unApproveUser(), account: {}", this.getClass(), account);
		admUserDao.approve(account, false);
		log.info("END: {}.unApproveUser(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}

	@Override
	public void addGroup(String grpName) {
		Profiler p = new Profiler();
		log.trace("START: {}.addGroup(), grpName: {}", this.getClass(), grpName);
		admGroupDao.addNewGroup(grpName);
		log.info("END: {}.addGroup(), grpName: {}, exec TIME: {} ms.", this.getClass(), grpName, p.executeTime());
	}

	@Override
	public void updateGroup(AdmGroup group) {
		Profiler p = new Profiler();
		log.trace("START: {}.updateGroup(), group: {}", this.getClass(), group);
		admGroupDao.updateGroupInfo(group);
		log.info("END: {}.updateGroup(), group: {}, exec TIME: {} ms.", this.getClass(), group, p.executeTime());
	}

	@Override
	public void enableGroup(String grpId) {
		Profiler p = new Profiler();
		log.trace("START: {}.enableGroup(), grpId: {}", this.getClass(), grpId);
		admGroupDao.enable(grpId, true);
		log.info("END: {}.enableGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}

	@Override
	public void disableGroup(String grpId) {
		Profiler p = new Profiler();
		log.trace("START: {}.disableGroup(), grpId: {}", this.getClass(), grpId);
		admGroupDao.enable(grpId, false);
		log.info("END: {}.disableGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}

	@Override
	public void approveGroup(String grpId) {
		Profiler p = new Profiler();
		log.trace("START: {}.approveGroup(), grpId: {}", this.getClass(), grpId);
		admGroupDao.approve(grpId, true);
		log.info("END: {}.approveGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}

	@Override
	public void unApproveGroup(String grpId) {
		Profiler p = new Profiler();
		log.trace("START: {}.unApproveGroup(), grpId: {}", this.getClass(), grpId);
		admGroupDao.approve(grpId, false);
		log.info("END: {}.unApproveGroup(), grpId: {}, exec TIME: {} ms.", this.getClass(), grpId, p.executeTime());
	}

	@Override
	public void addFunc(AdmFunc func) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFunc(AdmFunc func) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableFunc(String funcid, boolean isEnable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void approveFunc(String funcid, boolean isApprove) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUser2Group(String account, String groupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUserFromGroup(String account, String groupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFunc2Group(String funcId, String groupId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFuncFromGroup(String funcId, String groupId) {
		// TODO Auto-generated method stub

	}

}
