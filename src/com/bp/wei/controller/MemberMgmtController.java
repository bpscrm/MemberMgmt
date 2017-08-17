package com.bp.wei.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bp.wei.model.Childinfo;
import com.bp.wei.model.FeedbackWithBLOBs;
import com.bp.wei.model.Member;
import com.bp.wei.model.Memberinfo;
import com.bp.wei.model.MemberinfoWithBLOBs;
import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.Purchaseinfo;
import com.bp.wei.service.MemberMgmtService;
import com.itextpdf.text.pdf.qrcode.BitMatrix;
import com.itextpdf.text.pdf.qrcode.QRCodeWriter;

import java.io.InputStreamReader;
import java.io.OutputStream;
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

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

//import com.google.zxing.BarcodeFormat;

@Controller
@RequestMapping
public class MemberMgmtController 
{
	public static Logger log = LoggerFactory.getLogger(MemberMgmtController.class);
	
	private final static String USER_AGENT = "Mozilla/5.0";
	private static String gbl_openid;
	private static String gbl_access_token;
	
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

	@RequestMapping(value="mypromotion", params = {"openid"}, method = RequestMethod.GET)
	public String mypromotion(){	
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
	public String redirectMyqrcode() throws IOException
	{
		//Generate qrcode png
		
		System.out.println("within redirectMyqrcode");
		System.out.println(gbl_openid);
		System.out.println(gbl_access_token);
		
		TreeMap<String,String> params = new TreeMap<String,String>();  
        params.put("access_token", gbl_access_token);  
        Map<String,Integer> intMap = new HashMap<String,Integer>();  
        intMap.put("scene_id",10000);  
        Map<String, Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();  
        mapMap.put("scene", intMap);  
        //  
        Map<String,Object> paramsMap = new HashMap<String,Object>();  
        paramsMap.put("expire_seconds", 500);  
        paramsMap.put("action_name", "QR_SCENE");  
        paramsMap.put("action_info", mapMap);  
        String data = JSONObject.fromObject(paramsMap).toString();  
        
		String ws_get_tmp_ticket = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
			
		/*
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : paramsMap.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        */
		
		URL obj = null;
		try {
			obj = new URL(ws_get_tmp_ticket);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	HttpURLConnection con = null;
    	System.setProperty("http.keepAlive", "false"); // must be set
    	
    	try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    	con.setRequestProperty("Accept-Charset", "UTF-8");
    	con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);      
    	
    	try 
    	{
    		con.setRequestMethod("POST");
    	} 
    	catch (ProtocolException e) 
    	{
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	OutputStream output = con.getOutputStream();  
        output.write(data.getBytes("UTF-8"));  
        output.flush();  
	      output.close();  
    	       	
    	//add request header
        //con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
       // con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

        //con.getOutputStream().write(postDataBytes);
        
 
    	int responseCode = 0;
		try {
				responseCode = con.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("\n[redirectMyqrcode]Sending 'GET' request to URL : " + ws_get_tmp_ticket);
    	System.out.println("[redirectMyqrcode]Response Code : " + responseCode);

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
    		String trim_response=null;

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

    		System.out.println("trim_response "+trim_response);
    		   		 		
    	  JSONArray jsonArray = (net.sf.json.JSONArray) JSONArray.fromObject("["+trim_response+"]");	
  
          System.out.println(jsonArray);
                   
          //JSONObject json = (JSONObject) outerArray.get(0);
 
          System.out.println("json size");
          System.out.println(jsonArray.size());
          
          JSONObject station = jsonArray.getJSONObject(0);
          String ws_ticket = station.getString("ticket");
          String ws_ticket_url = station.getString("url");
          
          System.out.println("ticket "+ ws_ticket);
          
          String ws_get_picture = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ws_ticket;
        	  
          try {
    			obj = new URL(ws_get_tmp_ticket);
          } catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
          }

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

          try {
        	  responseCode = con.getResponseCode();
          } catch (IOException e) {
        	  // TODO Auto-generated catch block
        	  e.printStackTrace();
    	}
        System.out.println("After get qrcode picture");  
          
		/*
		URL path = null;
		String ws_application_path = "";
		//path = new File(".").getCanonicalPath();
		path = this.getClass().getProtectionDomain().getCodeSource().getLocation();
		ws_application_path = path.toString();
		//file:/C:/10/xampp/tomcat/webapps/MemberMgmt/WEB-INF/classes/com/bp/wei/controller/MemberMgmtController.class
		ws_application_path = ws_application_path.substring(6, ws_application_path.length() - 64);
		
		String ws_qrcode_path = ws_application_path + "images//" + gbl_openid+".png";
		System.out.println("config " + ws_application_path);
		*/
        // 生成二维码
		
		//String keycode = req.getParameter("keycode");
		//int size=129;
        //QRCodeWriter writer = new QRCodeWriter();
       // BitMatrix m = writer.encode(keycode, BarcodeFormat.QR_CODE, size, size);
        //MatrixToImageWriter.writeToStream(m, IMAGETYPE, stream);
		
		/*
		String content = "http://wongls.hopto.org/WeWeb?hello=isme";
		File directory = new File("");
		String imgPath = null;
		//imgPath = directory.getCanonicalPath() + "\\test.png";
		imgPath = ws_qrcode_path;
		makeQRcode(content, imgPath);
		*/
		
		/*
		try {
			System.out.println(decodeQRCode(imgPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		/*
		URL path = null;
		String ws_application_path = "";
		//path = new File(".").getCanonicalPath();
		path = this.getClass().getProtectionDomain().getCodeSource().getLocation();
		ws_application_path = path.toString();
		//file:/C:/10/xampp/tomcat/webapps/MemberMgmt/WEB-INF/classes/com/bp/wei/controller/MemberMgmtController.class
		ws_application_path = ws_application_path.substring(6, ws_application_path.length() - 64);
		
		String ws_qrcode_path = ws_application_path + "images//" + gbl_openid+".png";
		System.out.println("config " + ws_application_path);
        // 生成二维码
		BufferedImage qrCode = QrcodeImage("http://wongls.hopto.org/WeWeb");
		//temporary mask off
        //BufferedImage qrCode = QrcodeImage(gbl_openid);
        //writeImage(qrCode, "c:\\10\\qecode2.png");
        writeImage(qrCode, ws_qrcode_path);
		*/
		
        // 生成带有图片logo的二维码
        //File qrcode = new File("c:\\10\\qecode2.png");
        //File logo = new File("c:\\10\\logo.png");
        //BufferedImage logoQrcode = encodeImgLogo(qrcode, logo);
       // writeImage(logoQrcode, "c:\\10\\"+gbl_openid+".png");
        
		//redirectAttributes.addAttribute("rd", "rdValue");
		//redirectAttributes.addFlashAttribute("openid", gbl_openid);
	   
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
		String ws_access_token = "unknown";
		
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
          ws_access_token = station.getString("access_token");
          
          System.out.println("station openid "+ ws_openid+" access ["+ws_access_token+"]");
          // take the elements of the json array 
          /*
          for(int i=0; i<lang.size(); i++)
          { 
                System.out.println("The " + i + " element of the array: "+lang.get(i)); 
          } 
		  */
        gbl_openid = ws_openid;
        gbl_access_token = ws_access_token;
          
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
	
    public static void makeQRcode(String content, String imgPath) 
    {
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);
		BufferedImage bufImg = new BufferedImage(325, 325,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = bufImg.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, 325, 325);
		gs.setColor(Color.BLACK);
		try {
			byte[] contentBytes = content.getBytes("utf-8");
			if (0 < contentBytes.length && contentBytes.length < 120) {
				boolean[][] codeOut = qrcode.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 7 + 5, i * 7 + 5, 7, 7);
						}
					}
				}
			}
			gs.dispose();
			bufImg.flush();
			File imgFile = new File(imgPath);
			//生成为png
			ImageIO.write(bufImg, "png", imgFile);
			System.out.println("生成成功！");
			ImageIO.setUseCache(false);

		} catch (Exception e) {
			System.out.println("出错了！");
		}

	}

	public static String decodeQRCode(String imgPath) throws IOException 
	{
		File imageFile = new File(imgPath);
		BufferedImage bufImg = null;
		String decodedData = null;
		bufImg = ImageIO.read(imageFile);
		QRCodeDecoder decoder = new QRCodeDecoder();
		decodedData = new String(decoder.decode(new J2SEImage(bufImg)));
		System.out.println("解析成功！");
		return decodedData;
	}
	/*
    {
        // 生成二维码的图片
        File file = new File(imgPath);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	*/
}

class J2SEImage implements QRCodeImage {
	BufferedImage bufImg;

	public J2SEImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}

	public int getWidth() {
		return bufImg.getWidth();
	}

	public int getHeight() {
		return bufImg.getHeight();
	}

	public int getPixel(int x, int y) {
		return bufImg.getRGB(x, y);
	}
}
