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
import com.bp.wei.model.ChildToMember;
import com.bp.wei.model.Childinfo;
import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.Member;
import com.bp.wei.model.MemberToFollower;
import com.bp.wei.model.Memberinfo;
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
