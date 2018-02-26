package ggd.auth.dispatcher;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import baytony.util.Profiler;
import baytony.util.StringUtil;
import baytony.util.Util;
import ggd.auth.AuthException;
import ggd.auth.AuthService;
import ggd.auth.vo.AdmFunc;
import ggd.auth.vo.AdmGroup;
import ggd.core.CoreException;
import ggd.core.common.Constant;
import ggd.core.dispatcher.Dispatcher;
import ggd.core.util.JSONUtil;

@Component("auth.grp")
public class GroupDispatcher implements Dispatcher {
	
	private final static Logger log = LoggerFactory.getLogger(GroupDispatcher.class);
	
	public static final String ALL_APPROVE_FUNCS = GroupDispatcher.class + "_ALL_APPROVE_FUNC";
	
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
		String groupId = request.getParameter("groupId");
		String groupName = request.getParameter("groupName");
		String isManager = request.getParameter("isManager");
		String isEnabled = request.getParameter("isEnabled");
		String isApproved = request.getParameter("isApproved");
		String execFunc = request.getParameter("execFunc");
		log.trace("START: {}.doConfirm(), groupId: {}, groupName: {}, execFunc: {}", this.getClass(), groupId, groupName, execFunc);
		try {
			List<String> funcs = new ArrayList<String>();
			JsonNode jo = JSONUtil.parser(execFunc);
			if(jo.isArray()) {
				JsonNode arrayNode = new ObjectMapper().readTree(execFunc);
				for(JsonNode node : arrayNode) {
					log.debug("selected exec func id: {}", node.get("id").asText());
					funcs.add(node.get("id").asText());
				}
				log.debug("funcs: {}", funcs);
			}
			else {
				throw new AuthException("權限資料必須為陣列型態，格式錯誤");
			}
			if(Util.isEmpty(groupId)) {
				//to insert
				groupId = service.addGroup(groupName, Integer.parseInt(isManager) == 1, Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1);
			}
			else {
				//to update
				service.updateGroup(groupId, groupName, Integer.parseInt(isEnabled) == 1, Integer.parseInt(isApproved) == 1);
			}	
			
			if(!Util.isEmpty(funcs)) {
				service.addFunc2Group(groupId, funcs);
			}
			
			view.addObject(Constant.ACTION_RESULT, "1");
		}
		catch(AuthException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(JsonProcessingException e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		catch(Exception e) {
			view.addObject(Constant.ACTION_RESULT, "0");
			log.error(StringUtil.getStackTraceAsString(e));
		}
		log.info("END: {}.doEdit(), groupId: {}, groupName: {}, execFunc: {}, exec TIME: {} ms.", this.getClass(), groupId, groupName, execFunc, p.executeTime());
		this.doIndex(view, request);
	}
	
	
	
	private void doEdit(ModelAndView view, HttpServletRequest request) {
		Profiler p = new Profiler();
		String dataId = request.getParameter("dataId"); 
		log.trace("START: {}.deEdit(), dataId: {}", this.getClass(), dataId);
		try {
			AdmGroup grp = Util.isEmpty(dataId) ? new AdmGroup() : service.findGroup(dataId);
			List<AdmFunc> funcs = service.findAllApprovedFunc();
			view.addObject(Constant.DATA_LIST, grp);
			view.addObject(ALL_APPROVE_FUNCS, funcs);
			view.setViewName("grp/edit");
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
		List<AdmGroup> grps = null;
		try {
			grps = service.findAllGroup();
			view.addObject(Constant.DATA_LIST, grps);		
			view.setViewName("grp/index");
		} catch (AuthException e) {
			log.error(StringUtil.getStackTraceAsString(e));
		}
		
	}

	
}
