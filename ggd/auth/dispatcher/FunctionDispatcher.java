package ggd.auth.dispatcher;

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
import ggd.auth.vo.AdmFunc;
import ggd.auth.vo.AdmFuncEntity;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;

@Component("auth.func")
public class FunctionDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(FunctionDispatcher.class);
	
	public static final String ALL_ROOT_FUNCTIONS = FunctionDispatcher.class + "_ALL_ROOT_FUNCTIONS";

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
		String funcId = request.getParameter("funcId");
		String funcName = request.getParameter("funcName");
		String isRoot = request.getParameter("isRoot");
		String parentId = request.getParameter("parentId");
		String url = request.getParameter("url");
		String sort = request.getParameter("sort");
		String isEnabled = request.getParameter("isEnabled");
		String isApproved = request.getParameter("isApproved");
		log.trace("START: {}.doConfirm(), funcId: {}, funcName: {}, isRoot: {}, parentId: {}, sort: {}, isEnabled: {}, isApproved: {}", this.getClass(), funcId, funcName, isRoot, parentId, sort, isEnabled, isApproved);
		try {
			if(Util.isEmpty(funcId)) {
				//TO insert
				service.addFunc(funcName, Integer.parseInt(isRoot) == 1, parentId, url, Integer.parseInt(sort), Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1);
			}
			else {
				//TO update
				service.updateFunc(funcId, funcName, Integer.parseInt(isRoot) == 1, parentId, url, Integer.parseInt(sort), Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1);
			}	
			view.addObject(Constant.ACTION_RESULT, "1");
		}
		catch(AuthException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.trace("START: {}.doConfirm(), funcId: {}, funcName: {}, isRoot: {}, parentId: {}, sort: {}, isEnabled: {}, isApproved: {}, exec TIME: {} ms.", this.getClass(), funcId, funcName, isRoot, parentId, sort, isEnabled, isApproved, p.executeTime());
		this.doIndex(view, request);
	}
	
	
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String dataId = request.getParameter("dataId"); 
		log.trace("START: {}.deEdit(), dataId: {}", this.getClass(), dataId);
		AdmFunc func = null;		
		try {
			List<AdmFunc> roots = service.findAllFunc(true);
			func = Util.isEmpty(dataId) ? new AdmFunc() : service.findFuncById(dataId);	
			view.addObject(Constant.DATA_LIST, func);
			view.addObject(ALL_ROOT_FUNCTIONS, roots);
			view.setViewName("func/edit");
		}
		catch(AuthException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doEdit(), dataId: {}, exec TIME: {} ms.", this.getClass(), dataId, p.executeTime());
	}
	
	private void doSearch(ModelAndView view, HttpServletRequest request) {
		
	}
	
	private void doIndex(ModelAndView view, HttpServletRequest request) {		
		List<AdmFuncEntity> funcs = null;
		try {
			funcs = service.findAllFuncEntity();
			view.addObject(Constant.DATA_LIST, funcs);		
			view.setViewName("func/index");
		} catch (AuthException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		
	}

}
