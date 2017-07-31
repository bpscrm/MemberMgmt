package com.bp.wei.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bp.wei.model.Member;
import com.bp.wei.service.MemberMgmtService;

@Controller
@RequestMapping
public class MemberMgmtController {
	public static Logger log = LoggerFactory.getLogger(MemberMgmtController.class);
	
	@Autowired
	MemberMgmtService memberService;
	
	
	@RequestMapping(value="memberregister", method = RequestMethod.GET)
	public String redirectMemberregister(){		
		return "memberregister";
	}
	
	@RequestMapping(value="memberinfo", method = RequestMethod.GET)
	public String redirectMemberinfo(){	
		return "memberinfo";
	}

	@RequestMapping(value="mypromotion", method = RequestMethod.GET)
	public String redirectMypromotion(){	
		return "mypromotion";
	}
	
	
	@RequestMapping(value="childrenlist", method = RequestMethod.GET)
	public String redirectChildrenlist(){	
		return "childrenlist";
	}
	
	@RequestMapping(value="register", method = RequestMethod.GET)
	public String redirectRegister(){		
		return "register";
	}
	
	@RequestMapping(value="msg_success", method = RequestMethod.GET)
	public String redirectMsg_success(){	
		return "msg_success";
	}
	
	@RequestMapping(value="childinfo", method = RequestMethod.GET)
	public String redirectChildinfo(){	
		return "childinfo";
	}
	
	@RequestMapping(value="purchaselist", method = RequestMethod.GET)
	public String redirectPurchaselist(){	
		return "purchaselist";
	}
	
	@RequestMapping(value="feedbacklist", method = RequestMethod.GET)
	public String redirectFeedbacklist(){	
		return "feedbacklist";
	}
	
	@RequestMapping(value="feedbackinfo", method = RequestMethod.GET)
	public String redirectFeedbackinfo(){	
		return "feedbackinfo";
	}
	
	@RequestMapping(value="myfollower", method = RequestMethod.GET)
	public String redirectMyfollower(){	
		return "myfollower";
	}
	
	@RequestMapping(value="myqrcode", method = RequestMethod.GET)
	public String redirectMyqrcode(){	
		return "myqrcode";
	}
	
	@RequestMapping(value="getmember", method = RequestMethod.GET)
	public @ResponseBody Member findMember(int id){
		
		return memberService.getMemberById(new Integer(id));
		
	}
	
	@RequestMapping(value="setmember", method = RequestMethod.POST)
	public @ResponseBody Member SetMember(int id){
		
		return memberService.getMemberById(new Integer(id));
		
	}
}
