package com.gsnotes.exceptionhandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.gsnotes.web.models.UserAndAccountInfos;

@ControllerAdvice
//@PropertySource("classpath:application.properties")
public class AppGlobalExceptionHandler {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpSession httpSession;

	public static final String DEFAULT_ERROR_VIEW = "error";

	@Value("${gsabs.app.mode}")
	private String appMode;

	@ExceptionHandler
	public ModelAndView handleException(Exception exc, HttpServletRequest req) {

		// Log the error
		LOGGER.error("Exception : " + exc);

		ModelAndView modelAndView = new ModelAndView();

		String errorMsg = "The application is configured to work in DEV mode."
				+ " An Error has occurred due to following " + " exception. For more details, please see the log file: "
				+ exc;

		// If in PROD mode
		if ("PROD".equals(appMode)) {// DEV mode
			errorMsg = "The application cannout execute the action because it has encountered an error,"
					+ " please contact the administrator of the application for more details";

		}

		// get connected User
		UserAndAccountInfos userInfo = (UserAndAccountInfos) httpSession.getAttribute("userInfo");

		String view = null;

		if ("ROLE_STUDENT".equals((userInfo).getRoleName())) {

		} else if ("ROLE_CADRE_ADMIN".equals((userInfo).getRoleName())) {
			view = "/admin/error";

		} else if ("ROLE_PROF".equals((userInfo).getRoleName())) {
			view = "/prof/error";
		} else if ("ROLE_ADMIN".equals((userInfo).getRoleName())) {
			view = "/admin/error";
		} else if ("ROLE_BIBLIO".equals((userInfo).getRoleName())) {
			view = "/biblio/error";
		} else {
			view = DEFAULT_ERROR_VIEW;
		}

		modelAndView.addObject("message", errorMsg);

		modelAndView.addObject("url", req.getRequestURL());
		modelAndView.setViewName(view);

		return modelAndView;

	}

}