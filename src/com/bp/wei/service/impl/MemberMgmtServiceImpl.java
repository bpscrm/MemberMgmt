package com.bp.wei.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bp.wei.dao.ChildToMemberDao;
import com.bp.wei.dao.ChildinfoDao;
import com.bp.wei.dao.FeedbackDao;
import com.bp.wei.dao.FeedbackToPurchaseDao;
import com.bp.wei.dao.MemberDao;
import com.bp.wei.dao.MemberinfoDao;
import com.bp.wei.dao.FollowerinfoDao;
import com.bp.wei.dao.MemberToFollowerDao;
import com.bp.wei.dao.PurchaseinfoDao;
import com.bp.wei.model.ChildToMember;
import com.bp.wei.model.Childinfo;
import com.bp.wei.model.FeedbackToPurchase;
import com.bp.wei.model.FeedbackWithBLOBs;
import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.Member;
import com.bp.wei.model.MemberToFollower;
import com.bp.wei.model.MemberinfoWithBLOBs;
import com.bp.wei.service.MemberMgmtService;

@Service
public class MemberMgmtServiceImpl implements MemberMgmtService {
	
	public static Logger log = LoggerFactory.getLogger(MemberMgmtService.class);
	
	
	@Resource
	private MemberinfoDao mbdao;
	
	@Resource
	private FollowerinfoDao fldao;
	
	@Resource
	private MemberToFollowerDao mtfdao;
	
	@Resource
	private ChildinfoDao cdao;
	
	@Resource
	private ChildToMemberDao ctmdao;
	
	@Resource
	private FeedbackDao fdao;
	
	@Resource
	private PurchaseinfoDao pdao;
	
	@Resource
	private FeedbackToPurchaseDao ftpdao;
	
	////////////////for member
	//insert
	@Override
	public int insertMemberinfo(MemberinfoWithBLOBs memberinfowithblogs, String openid) {
		
		int result = mbdao.insert(memberinfowithblogs);
		//System.out.println("@@@@@@@@@@@@@@member id: " + memberinfowithblogs.getId());
		
		String followerID = fldao.selectByPrimaryOpenid(openid);
		
		MemberToFollower mbTofl = new MemberToFollower();
		mbTofl.setEc1MemberEc1Followerec1MemberIda(memberinfowithblogs.getId());
		mbTofl.setEc1MemberEc1Followerec1FollowerIdb(followerID);
		
		result = mtfdao.insert(mbTofl);
		
		return result;
	}
	//search
	@Override
	public MemberinfoWithBLOBs getMemberinfobyname(String name) {
		if(name.length() <= 0){
			log.error("Invalid member name: " + name);
			return null;
		}
		MemberinfoWithBLOBs memberinfo = mbdao.selectByMemberName(new String(name));
		return memberinfo;
	}
	//update
	public int updateMemberinfo(MemberinfoWithBLOBs memberinfowithblogs) {
		
		int result = mbdao.updateByPrimaryKeyWithBLOBs(memberinfowithblogs);
		
		return result;
	}
	
	////////////////for child
	//insert
	@Override
	public int insertChildinfo(Childinfo childinfo, String mbname) {
	
		int result = cdao.insert(childinfo);
		
		String mbID = mbdao.selectIDByMember(mbname);
		
		ChildToMember cdTomb = new ChildToMember();
		cdTomb.setEc1ChildDataEc1Memberec1MemberIda(mbID);
		cdTomb.setEc1ChildDataEc1Memberec1ChildDataIdb(childinfo.getId());
		
		result = ctmdao.insert(cdTomb);
		
		return result;
	}
	//search
	@Override
	public Childinfo getchildinfo(String name) {
		if(name.length() <= 0){
			log.error("Invalid member name: " + name);
			return null;
		}
		Childinfo childinfo = cdao.selectByChildName(new String(name));
		return childinfo;
	}
	//update
	public int updateChildinfo(Childinfo childinfo) {
		int result = cdao.updateByPrimaryKeyWithBLOBs(childinfo);
		
		return result;
	}
	
	
	
	////////////////for feedbacks
	//insert
	@Override
	public int insertFeedbackinfo(FeedbackWithBLOBs feedbackinfo, String purchasename) {
	
		int result = fdao.insert(feedbackinfo);
		
		String purID = pdao.selectIDByMember(purchasename);
		
		FeedbackToPurchase fdToPCH = new FeedbackToPurchase();
		
		fdToPCH.setEc1FeedbackEc1PurchaseDataec1PurchaseDataIda(purID);
		fdToPCH.setEc1FeedbackEc1PurchaseDataec1FeedbackIdb(feedbackinfo.getId());
		
		result = ftpdao.insert(fdToPCH);
		
		return result;
	}
	//search
	@Override
	public FeedbackWithBLOBs getFeedbackinfobyname(String name) {
		System.out.println("@@@@@@@@@@@@@@feedback name: " + name);
		if(name.length() <= 0){
			log.error("Invalid member name: " + name);
			return null;
		}
		FeedbackWithBLOBs feedbackinfo = fdao.selectByFeedbackName(new String(name));
		return feedbackinfo;
	}
	//update
	public int updateFeedbackinfo(FeedbackWithBLOBs feedbackinfo) {
		
		System.out.println("@@@@@@@@@@@@@@feedback: " + feedbackinfo.getName());
		
		int result = fdao.updateByPrimaryKeyWithBLOBs(feedbackinfo);
		
		return result;
	}
	
	
	///////////////////for test follower  
	public String getTestFollowerinfo(Followerinfo follow) {
		
		System.out.println("@@@@@@@@@@@@@@follower open id: " + follow.getName());
		if(follow.getName().length() <= 0){
			log.error("Invalid member name: " + follow.getName());
			return "null";
		}
		
		String FollowerID = fldao.selectByPrimaryOpenid(follow.getName());
		
		if(FollowerID != null && FollowerID.length() > 0){
			return FollowerID;
		} else {
			
			int result = fldao.insert(follow);
			if(result == 1){
				return follow.getId();
			} else {
				return "null";
			}
			
		}
	}
		

	//for examples
	@Resource
	private MemberDao dao;

	@Override
	public Member getMemberById(int memberId) {
		if(memberId <= 0){
			log.error("Invalid member id: " + memberId);
			return null;
		}
		Member member = dao.selectByPrimaryKey(new Integer(memberId));
		return member;
	}

	@Override
	public int setMember(Member member) {
		int result = dao.insertSelective(member);
		return result;
	}

	@Override
	public Member getMemberWithChildren(String memberId) {
		Member member = mbdao.selectChildrenByKey(memberId);
		return member;
	}

}
