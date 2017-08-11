package com.bp.wei.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bp.wei.model.Childinfo;
import com.bp.wei.model.FeedbackWithBLOBs;
import com.bp.wei.model.Member;
import com.bp.wei.model.Memberinfo;
import com.bp.wei.model.MemberinfoWithBLOBs;
import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.Purchaseinfo;
import com.bp.wei.model.Questionnaire;
import com.bp.wei.service.MemberMgmtService;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.json.JSONException;
import net.sf.json.JSONArray;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Controller
@RequestMapping
public class MemberMgmtController {
	public static Logger log = LoggerFactory.getLogger(MemberMgmtController.class);
	
	private final static String USER_AGENT = "Mozilla/5.0";
	
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
	
	
	@RequestMapping(value="register", method = RequestMethod.GET)
	public String redirectRegister(){		
		return "register";
	}

	@RequestMapping(value="childrenlist", method = RequestMethod.GET)
	public String redirectChildrenlist(){	
		return "childrenlist";
	}
	
	@RequestMapping(value="msg_success", method = RequestMethod.GET)
	public String redirectMsg_success(){	
		return "msg_success";
	}
	
	@RequestMapping(value="childinfoadd", method = RequestMethod.GET)
	public String redirectChildinfoadd(){	
		return "childinfoadd";
	}
	
	@RequestMapping(value="childinfoupdate", method = RequestMethod.GET)
	public String redirectChildinfoupdate(){	
		return "childinfoupdate";
	}
	
	@RequestMapping(value="purchaselist", method = RequestMethod.GET)
	public String redirectPurchaselist(){	
		return "purchaselist";
	}
	
	@RequestMapping(value="feedbacklist", method = RequestMethod.GET)
	public String redirectFeedbacklist(){	
		return "feedbacklist";
	}
	
	@RequestMapping(value="feedbackinfoadd", method = RequestMethod.GET)
	public String redirectFeedbackinfoadd(){	
		return "feedbackinfoadd";
	}
	
	@RequestMapping(value="feedbackinfoupdate", method = RequestMethod.GET)
	public String redirectFeedbackinfoupdate(){	
		return "feedbackinfoupdate";
	}
	
	@RequestMapping(value="myfollower", method = RequestMethod.GET)
	public String redirectMyfollower(){	
		return "myfollower";
	}
	
	@RequestMapping(value="myqrcode", method = RequestMethod.GET)
	public String redirectMyqrcode(){	
		return "myqrcode";
	}
	
	@RequestMapping(value="registerinfo", method = RequestMethod.POST)
	public ModelAndView viewRegisterinfo(HttpServletRequest request){	
		log.debug("redirectRegisterinfo start...");
		Member member = new Member();
		member.setName(request.getParameter("membername"));
		log.debug("################" + member.toString());
		ModelAndView result = new ModelAndView();
		//Map<String, Object> modelMap = new HashMap<String, Object>();
		result.setViewName("registerinfo");		
		result.addObject("member", JSONObject.fromObject(member));	
		return result;
	}
	
	@RequestMapping(value="getmember", method = RequestMethod.GET)
	public @ResponseBody Member findMember(int id){
		
		return memberService.getMemberById(new Integer(id));
		
	}
	
	@RequestMapping(value="getMemberchild", method = RequestMethod.GET)
	public @ResponseBody Memberinfo findMemberWithChildren(String id){
		log.debug("###########memberid: " + id);
		if(id == null || id.length() == 0){
			return null;
		}
		log.debug("###########memberid: " + id);
		Memberinfo member = memberService.getMemberWithChildren(id);
		log.debug("###########" + member.toString());
		return member;
	}
	
	@RequestMapping(value="setmember", method = RequestMethod.POST)
	public @ResponseBody int setMember(@RequestBody JSONObject strMember){
		log.debug("Start to set member...");
		if(strMember == null){
			log.error("Failed to get member info from UI: " + strMember);
			return -1;
		}
		
		Member member = new Member();
		String mobile = strMember.getString("membermobile");
		if(mobile != null && mobile.length() > 0){
			member.setMobile(mobile);
		}
		String name = strMember.getString("membername");
		if(name != null && name.length() > 0){
			member.setName(name);
		}
		member.setGender("F");
		member.setBirthday("2017-01-01");
		
		int result = memberService.setMember(member);		
		
		return result;		
	}
	
	/////////for follower
	//search myfollower
	@RequestMapping(value="getFollowerlist", method = RequestMethod.GET)
	public @ResponseBody Followerinfo findMyFollowerList(String id){
		log.debug("###########memberid: " + id);
		if(id == null || id.length() == 0){
			return null;
		}
		log.debug("###########memberid: " + id);
		Followerinfo followerinfo = memberService.getFollowerlist(id);
		log.debug("###########" + followerinfo.toString());
		return followerinfo;
	}
	
	
	/////////for member 
	//insert member
	@RequestMapping(value="setmemberinfo", method = RequestMethod.POST)
	public @ResponseBody int setMemberinfo(@RequestBody JSONObject strMemberinfo){
		
		log.debug("Start to set member...");
		if(strMemberinfo == null){
			log.error("Failed to get member info from UI: " + strMemberinfo);
			return -1;
		}
		
		System.out.println("#################" + strMemberinfo.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		MemberinfoWithBLOBs memberinfo = new MemberinfoWithBLOBs();
		
		String telnum = strMemberinfo.getString("memberinfotelnum");
		if(telnum != null && telnum.length() > 0){
			memberinfo.setName(telnum);
		}
		
		String titel = strMemberinfo.getString("memberinfotitle");
		if(titel != null && titel.length() > 0){
			memberinfo.setMbTitle(titel);
		}
		
		String mbname = strMemberinfo.getString("memberinfoname");
		if(mbname != null && mbname.length() > 0){
			memberinfo.setMbName(mbname);
		}
		
		String birthday = strMemberinfo.getString("memberinfobird");
		if(birthday != null && birthday.length() > 0){
			memberinfo.setMbBirthday(birthday);
		}
		
		String ifchild = strMemberinfo.getString("memberinfoifchild");
		if(ifchild != null && ifchild.length() > 0){
			memberinfo.setMbChild(ifchild);
		}
		
		String edulevel = strMemberinfo.getString("memberinfoedulevel");
		if(edulevel != null && edulevel.length() > 0){
			memberinfo.setMbEdu(edulevel);
		}
		
		String addr = strMemberinfo.getString("memberinfoaddr");
		if(addr != null && addr.length() > 0){
			memberinfo.setMbAddr(addr);
		}
		
		Followerinfo followerinfo = new Followerinfo();
		followerinfo.setId(strMemberinfo.getString("followid"));
		
		int result = memberService.insertMemberinfo(memberinfo, strMemberinfo.getString("followid"));
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		return result;		
	
	}
	//search member
	@RequestMapping(value="getmemberinfo", method = RequestMethod.GET)
	public @ResponseBody MemberinfoWithBLOBs findMemberinfo(String name){
		
		return memberService.getMemberinfobyname(new String(name));
		
	}
	//update member 
	@RequestMapping(value="updatememberinfo", method = RequestMethod.POST)
	public @ResponseBody int updateMemberinfo(@RequestBody JSONObject strMemberinfo){
		
		log.debug("Start to update member...");
		if(strMemberinfo == null){
			log.error("Failed to get member info from UI: " + strMemberinfo);
			return -1;
		}
		
		System.out.println("#################" + strMemberinfo.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		MemberinfoWithBLOBs memberinfo = new MemberinfoWithBLOBs();
		
		
		String mid = strMemberinfo.getString("memberid1");
		if(mid != null && mid.length() > 0){
			memberinfo.setId(mid);
		}
		
		String telnum = strMemberinfo.getString("memberinfotelnum");
		if(telnum != null && telnum.length() > 0){
			memberinfo.setName(telnum);
		}
		
		String titel = strMemberinfo.getString("memberinfotitle");
		if(titel != null && titel.length() > 0){
			memberinfo.setMbTitle(titel);
		}
		
		String mbname = strMemberinfo.getString("memberinfoname");
		if(mbname != null && mbname.length() > 0){
			memberinfo.setMbName(mbname);
		}
		
		String birthday = strMemberinfo.getString("memberinfobird");
		if(birthday != null && birthday.length() > 0){
			memberinfo.setMbBirthday(birthday);
		}
		
		String ifchild = strMemberinfo.getString("memberinfoifchild");
		if(ifchild != null && ifchild.length() > 0){
			memberinfo.setMbChild(ifchild);
		}
		
		String edulevel = strMemberinfo.getString("memberinfoedulevel");
		if(edulevel != null && edulevel.length() > 0){
			memberinfo.setMbEdu(edulevel);
		}
		
		String addr = strMemberinfo.getString("memberinfoaddr");
		if(addr != null && addr.length() > 0){
			memberinfo.setMbAddr(addr);
		}
		
		
		int result = memberService.updateMemberinfo(memberinfo);
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		return result;	
	}
	
	/////////for child
	//insert child
	@RequestMapping(value="setchildinfo", method = RequestMethod.POST)
	public @ResponseBody int setChildinfo(@RequestBody JSONObject strChildinfo){
		
		log.debug("Start to set member...");
		if(strChildinfo == null){
			log.error("Failed to get child info from UI: " + strChildinfo);
			return -1;
		}
		
		System.out.println("#################" + strChildinfo.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		Childinfo childinfo = new Childinfo();
		
		String cname = strChildinfo.getString("childname");
		if(cname != null && cname.length() > 0){
			childinfo.setName(cname);
		}
		
		String csex = strChildinfo.getString("childsex");
		if(csex != null && csex.length() > 0){
			childinfo.setChildSex(csex);
		}
		
		String cbird = strChildinfo.getString("childbird");
		if(cbird != null && cbird.length() > 0){
			childinfo.setChildBirthday(cbird);
		}
		
		String ceng = strChildinfo.getString("childeng");
		if(ceng != null && ceng.length() > 0){
			childinfo.setChildEng(ceng);
		}
		
		int result = memberService.insertChildinfo(childinfo, strChildinfo.getString("memberid1"));
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		return result;		
	}
	//search child
	@RequestMapping(value="getchildinfo", method = RequestMethod.GET)
	public @ResponseBody Childinfo findChildinfo(String id){
		
		return memberService.getchildinfo(new String(id));
		
	}
	//update child
	@RequestMapping(value="updatechildinfo", method = RequestMethod.POST)
	public @ResponseBody int updateChildinfo(@RequestBody JSONObject strChildinfo){
			
		log.debug("Start to set member...");
		if(strChildinfo == null){
			log.error("Failed to get child info from UI: " + strChildinfo);
			return -1;
		}
		
		System.out.println("#################" + strChildinfo.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		Childinfo childinfo = new Childinfo();
		
		String cid = strChildinfo.getString("childid");
		if(cid != null && cid.length() > 0){
			childinfo.setId(cid);
		}
		
		String cname = strChildinfo.getString("childname");
		if(cname != null && cname.length() > 0){
			childinfo.setName(cname);
		}
		
		String csex = strChildinfo.getString("childsex");
		if(csex != null && csex.length() > 0){
			childinfo.setChildSex(csex);
		}
		
		String cbird = strChildinfo.getString("childbird");
		if(cbird != null && cbird.length() > 0){
			childinfo.setChildBirthday(cbird);
		}
		
		String ceng = strChildinfo.getString("childeng");
		if(ceng != null && ceng.length() > 0){
			childinfo.setChildEng(ceng);
		}
		
		int result = memberService.updateChildinfo(childinfo);
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		return result;		
	}
	
	
	/////////for purchase
	//search purchase
	@RequestMapping(value="getPurchaselist", method = RequestMethod.GET)
	public @ResponseBody Memberinfo findMemberWithPurchase(String id){
		log.debug("###########memberid: " + id);
		if(id == null || id.length() == 0){
			return null;
		}
		log.debug("###########memberid: " + id);
		Memberinfo member = memberService.getMemberWithPurchase(id);
		log.debug("###########" + member.toString());
		return member;
	}
	
	@RequestMapping(value="getPurchaseinfo", method = RequestMethod.GET)
	public @ResponseBody Purchaseinfo findPurchaseInfo(String id){
		log.debug("###########memberid: " + id);
		if(id == null || id.length() == 0){
			return null;
		}
		log.debug("###########memberid: " + id);
		Purchaseinfo purinfo = memberService.getPurchaseInfo(id);
		log.debug("###########" + purinfo.toString());
		return purinfo;
	}
	
	
		
	/////////for feedback
	//insert feedback
	@RequestMapping(value="setfeedbackinfo", method = RequestMethod.POST)
	public @ResponseBody int setfeedbackinfo(@RequestBody JSONObject strfeedbackinfo){
		
		log.debug("Start to set member...");
		if(strfeedbackinfo == null){
			log.error("Failed to get child info from UI: " + strfeedbackinfo);
			return -1;
		}
		
		System.out.println("#################" + strfeedbackinfo.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		FeedbackWithBLOBs feedback = new FeedbackWithBLOBs();
		
		String fname = strfeedbackinfo.getString("feedbackname");
		if(fname != null && fname.length() > 0){
			feedback.setName(fname);
		}
		
		String fcontent = strfeedbackinfo.getString("feedbackcontent");
		if(fcontent != null && fcontent.length() > 0){
			feedback.setDescription(fcontent);
		}
		
		String ftime = strfeedbackinfo.getString("feedbacktime");
		if(ftime != null && ftime.length() > 0){
			feedback.setFdDt(ftime);
		}
		

		
		int result = memberService.insertFeedbackinfo(feedback, strfeedbackinfo.getString("purchaseid"));
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		return result;		
	}
	//search feedback
	@RequestMapping(value="getfeedbackinfo", method = RequestMethod.GET)
	public @ResponseBody FeedbackWithBLOBs findFeedbackinfo(String id){
		
		return memberService.getFeedbackinfobyid(new String(id));
		
	}
	
	@RequestMapping(value="getFeedbacklist", method = RequestMethod.GET)
	public @ResponseBody Purchaseinfo findFeedbacklist(String id){
		log.debug("###########memberid: " + id);
		if(id == null || id.length() == 0){
			return null;
		}
		log.debug("###########memberid: " + id);
		Purchaseinfo purinfo = memberService.getFeedbacklist(id);
		log.debug("###########" + purinfo.toString());
		return purinfo;
	}
	
	//update feedback
	@RequestMapping(value="updatefeedbackinfo", method = RequestMethod.POST)
	public @ResponseBody int updatefeedbackinfo(@RequestBody JSONObject strfeedbackinfo){
		
		log.debug("Start to set member...");
		if(strfeedbackinfo == null){
			log.error("Failed to get child info from UI: " + strfeedbackinfo);
			return -1;
		}
		
		System.out.println("#################" + strfeedbackinfo.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		FeedbackWithBLOBs feedback = new FeedbackWithBLOBs();
		
		String fid = strfeedbackinfo.getString("feedbackid");
		if(fid != null && fid.length() > 0){
			feedback.setId(fid);
		}
		
		String fname = strfeedbackinfo.getString("feedbackname");
		if(fname != null && fname.length() > 0){
			feedback.setName(fname);
		}
		
		String fcontent = strfeedbackinfo.getString("feedbackcontent");
		if(fcontent != null && fcontent.length() > 0){
			feedback.setDescription(fcontent);
		}
		
		String ftime = strfeedbackinfo.getString("feedbacktime");
		if(ftime != null && ftime.length() > 0){
			feedback.setFdDt(ftime);
		}
		

		
		int result = memberService.updateFeedbackinfo(feedback);
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		return result;		
	}
	
	//for test data 
	@RequestMapping(value="getopenid", method = RequestMethod.GET)
	public @ResponseBody String getopenid(String appid, String secret, String code){
		log.debug("Start to get OPENID..."+appid+" "+secret+" "+code);
		
		JSONParser parser = new JSONParser();
		String ws_openid="unknown";
		
		//Get ACCESS_TOKEN
		String token_url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID" +
			"&secret=APPSECRET&code=CODE&grant_type=authorization_code";
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", secret).replace("CODE",  code);  
				
		//System.out.println("requestUrl "+ requestUrl);

       	URL obj = null;
		try {
			obj = new URL(requestUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    	con.setRequestProperty("Accept-Charset", "UTF-8");
    		try {
				con.setRequestMethod("GET");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		//add request header
    		con.setRequestProperty("User-Agent", USER_AGENT);

    		int responseCode = 0;
			try {
				responseCode = con.getResponseCode();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("\nSending 'GET' request to URL : " + requestUrl);
    		System.out.println("Response Code : " + responseCode);

    		BufferedReader in = null;
			try {
				in = new BufferedReader(
				        new InputStreamReader(con.getInputStream(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		String inputLine;
    		StringBuffer response1 = new StringBuffer();
    		String trim_response;

    		try {
				while ((inputLine = in.readLine()) != null) {
					response1.append(inputLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		trim_response = response1.toString();
    		//trim_response = trim_response.trim().replaceAll(" +", " ") + "\n";
    		//print result
    		System.out.println("trim_response "+trim_response);
    		
    		/*
    		Object obj1 = null;
			try {
				obj1 = parser.parse(trim_response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		String userlocation = null;    	
    		*/
    		
    	  JSONArray jsonArray = (net.sf.json.JSONArray) JSONArray.fromObject("["+trim_response+"]");	
  
          System.out.println(jsonArray);
                   
          //JSONObject json = (JSONObject) outerArray.get(0);
 
          System.out.println("json size");
          System.out.println(jsonArray.size());
          
          JSONObject station = jsonArray.getJSONObject(0);
          ws_openid = station.getString("openid");
          System.out.println("station "+ station.getString("openid"));
          // take the elements of the json array 
          /*
          for(int i=0; i<lang.size(); i++)
          { 
                System.out.println("The " + i + " element of the array: "+lang.get(i)); 
          } 
		  */

		return ws_openid;
	}
	
	//for test data 
	@RequestMapping(value="gettestfollowerid", method = RequestMethod.POST)
	public @ResponseBody JSONObject getTestfollowerid(@RequestBody JSONObject strtestfollowerid){
		
		Map params = new HashMap();
		
		log.debug("Start to set member...");
		if(strtestfollowerid == null){
			log.error("Failed to get child info from UI: " + strtestfollowerid);
			
			params.put("id", "null");
			JSONObject result = JSONObject.fromObject(params);
			
			return result;
		}
		
		System.out.println("#################" + strtestfollowerid.toString());
		
		//JSONObject jsonObject = JSONObject.fromObject(strMember);
		Followerinfo follow = new Followerinfo();
		
		String flname = strtestfollowerid.getString("membertelnumname");
		if(flname != null && flname.length() > 0){
			follow.setName("openid-" + flname);
		}
				
		String flid = memberService.getTestFollowerinfo(follow);
		
		params.put("id", flid);
		JSONObject result = JSONObject.fromObject(params);
		
		System.out.println("@@@@@@@@@@@@@@result: " + result);
		
		return result;		
	}
	
	@RequestMapping(value="getQuestionnaire", method = RequestMethod.GET)
	public @ResponseBody Questionnaire getQuestionnaire(String id){
		if(id == null || id.length() <= 0){
			log.error("Invalid questionnaire id from UI.");
			return null;
		}
		Questionnaire result = memberService.getQuestionnaireById(id);
		if(result == null){
			log.error("No questionnaire definition.");
			return null;
		}
		System.out.println("@@@@@@@@@@@result: " + result.toString());
		return result;
	}
	
	@RequestMapping(value="submitSurvey", method = RequestMethod.POST)
	public ModelAndView submitSurvey(HttpServletRequest request){	
		log.debug("setSurveryResult start...");
		String surveryId = request.getParameter("sid");
		System.out.println("survery id: " + surveryId);
		int i = 1;
		boolean hasnext = true;
		while(hasnext){
			String questionId = request.getParameter("qid_" + i);
			System.out.println("question id: " + questionId);
			if(questionId != null && questionId.length() > 0){
				String answer = request.getParameter(questionId);
				System.out.println("answer id: " + answer);
				i ++;
			}else{
				hasnext = false;
			}
		}
		ModelAndView result = new ModelAndView();
		
		result.setViewName("register");		
			
		return result;
	}
}
