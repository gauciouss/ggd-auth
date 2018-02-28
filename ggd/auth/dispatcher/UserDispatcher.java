package ggd.auth.dispatcher;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.Util;
import ggd.auth.AuthException;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmGroup;
import ggd.auth.vo.AdmUser;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;

@Component("auth.user")
public class UserDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(UserDispatcher.class);
	
	public static final String ALL_APPROVED_GROUPS = UserDispatcher.class + "_GROUP";
	
	@Autowired
	@Qualifier("AuthService")
	private AuthService service;
	

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		Profiler p = new Profiler();
		String action = request.getParameter(Constant.ACTION_TYPE);
		action = StringUtil.isEmptyString(action) ? "index" : action;
		log.trace("START: {}.handler(), action: {}", this.getClass(), action);
		switch(action) {
			case "edit":
				doEdit(view, request);
				break;			
			case "save":
				doEdit(view, request);
				break;
			case "confirm":
				doConfirm(view, request);
				break;
			case "search":
				doSearch(view, request);
				break;				
			case "index":
			default:
				doIndex(view, request);
				break;
		}
		log.info("END: {}.handler(), action: {}, exec TIME: {} ms.", this.getClass(), action, p.executeTime());
	}
	
	private void doConfirm(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String tel = request.getParameter("tel");
		String phone = request.getParameter("phone");
		String group = request.getParameter("group");
		String isEnabled = request.getParameter("isEnabled");
		String isApproved = request.getParameter("isApproved");
		log.debug("START: {}.doConfirm(), account: {}, password: {}, name: {}, email: {}, address: {}, tel: {}, phone: {}, group: {}, isEnabled: {}, isApproved: {}", this.getClass(), account, password, name, email, address, tel, phone, group, isEnabled, isApproved);
		try {
			AdmUser user = service.findUserById(account);
			if(user == null) {
				service.addUser(new AdmUser(account, password, name, email, address, tel, phone, new Timestamp(System.currentTimeMillis()), null, Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1, group));
			}
			else {
				service.updateUser(account, password, name, email, address, tel, phone, group, Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1);
			}	
			view.addObject(Constant.ACTION_RESULT, "1");
			log.info("END: {}.doConfirm(), account: {}, password: {}, name: {}, email: {}, address: {}, tel: {}, phone: {}, group: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), account, password, name, email, address, tel, phone, group, isEnabled, isApproved, p.executeTime());
		}
		catch(AuthException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		this.doIndex(view, request);
	}
	
	
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();		
		String account = request.getParameter("account"); 
		log.trace("START: {}.deEdit(), account: {}", this.getClass(), account);
		try {
			AdmUser user = Util.isEmpty(account) ? new AdmUser() : service.findUserById(account);
			List<AdmGroup> groups = service.findAllGroup(true, true);
			view.addObject(Constant.DATA_LIST, user);
			view.addObject(ALL_APPROVED_GROUPS, groups);
			view.setViewName("user/edit");
		}
		catch(AuthException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doEdit(), account: {}, exec TIME: {} ms.", this.getClass(), account, p.executeTime());
	}
	
	private void doSearch(ModelAndView view, HttpServletRequest request) {
		String account = request.getParameter("account");
		try {
			List<AdmUser> users = service.findUsers(account);
			view.addObject(Constant.DATA_LIST, users);
			view.addObject(Constant.PAGE, 1);
			view.setViewName("user/index");
		}
		catch(AuthException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
	}
	
	private void doIndex(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		log.trace("START: {}.doIndex()", this.getClass());
		try {
			List<AdmUser> users = service.findUsers();
			log.debug("users: {}", users);
			view.addObject(Constant.DATA_LIST, users);
			view.setViewName("user/index");
		}
		catch(AuthException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doIndex(), exec TIME: {} ms.", this.getClass(), p.executeTime());
	}

	
}
