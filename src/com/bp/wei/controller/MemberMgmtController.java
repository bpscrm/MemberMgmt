package com.bp.wei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class MemberMgmtController {
	public static Logger log = LoggerFactory.getLogger(MemberMgmtController.class);
	
	@RequestMapping(value="register", method = RequestMethod.GET)
	public String redirectRegister(){		
		return "register";
	}
	
	@RequestMapping(value="msg_success", method = RequestMethod.GET)
	public String redirectMsg_success(){	
		return "msg_success";
	}
	
	@RequestMapping(value="memberinfo", method = RequestMethod.GET)
	public String redirectMemberinfo(){	
		return "memberinfo";
	}

	@RequestMapping(value="childrenlist", method = RequestMethod.GET)
	public String redirectChildrenlist(){	
		return "childrenlist";
	}
	
	@RequestMapping(value="childinfo", method = RequestMethod.GET)
	public String redirectChildinfo(){	
		return "childinfo";
	}
	
	
	
}