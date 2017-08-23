package ggd.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ggd.core.CoreException;
import ggd.core.dispatcher.Dispatcher;

@Component("auth.index")
public class IndexDispatcher implements Dispatcher {

	@Override
	public void handler(ModelAndView view, HttpServletRequest request) throws CoreException {
		
	}
}
