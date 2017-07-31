package com.bp.wei.service;


import com.bp.wei.model.Member;

public interface MemberMgmtService {
	Member getMemberById(int memberId);
	
	int setMember(Member member);
}
