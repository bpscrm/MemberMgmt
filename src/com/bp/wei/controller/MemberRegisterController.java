package com.bp.wei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class MemberRegisterController {
	public static Logger log = LoggerFactory.getLogger(MemberRegisterController.class);
	
	@RequestMapping(value="register", method = RequestMethod.GET)
	public String redirectRegister(){		
		return "register";
	}
}
