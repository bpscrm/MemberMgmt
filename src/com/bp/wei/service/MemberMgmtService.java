package com.bp.wei.service;


import com.bp.wei.model.Member;
import com.bp.wei.model.Memberinfo;
import com.bp.wei.model.MemberinfoWithBLOBs;

public interface MemberMgmtService {
	//for register member
	int insertMemberinfo(MemberinfoWithBLOBs memberinfowithblogs, String openid);
	MemberinfoWithBLOBs getMemberinfobyname(String name);


	//for examples
	Member getMemberById(int memberId);
	int setMember(Member member);
}
