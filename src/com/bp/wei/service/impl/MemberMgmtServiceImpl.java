package com.bp.wei.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bp.wei.dao.MemberDao;
import com.bp.wei.dao.MemberinfoDao;
import com.bp.wei.dao.FollowerinfoDao;
import com.bp.wei.dao.MemberToFollowerDao;
import com.bp.wei.model.Member;
import com.bp.wei.model.MemberinfoWithBLOBs;
import com.bp.wei.service.MemberMgmtService;

@Service
public class MemberMgmtServiceImpl implements MemberMgmtService {
	
	public static Logger log = LoggerFactory.getLogger(MemberMgmtService.class);
	

	//for register member
	@Resource
	private MemberinfoDao Mbdao;
	private FollowerinfoDao Fldao;
	private MemberToFollowerDao mtfdao;
	
	@Override
	public int insertMemberinfo(MemberinfoWithBLOBs memberinfowithblogs, String openid) {
		int result = Mbdao.insert(memberinfowithblogs);
		//int result1 = Fldao.select();
		//int result2 = mtfdao.insert();
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

}
