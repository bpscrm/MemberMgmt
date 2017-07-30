package com.bp.wei.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bp.wei.dao.MemberDao;
import com.bp.wei.model.Member;
import com.bp.wei.service.MemberMgmtService;

@Service
public class MemberMgmtServiceImpl implements MemberMgmtService {
	
	public static Logger log = LoggerFactory.getLogger(MemberMgmtService.class);
	
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

}
