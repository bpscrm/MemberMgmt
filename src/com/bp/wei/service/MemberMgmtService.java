package com.bp.wei.service;


import com.bp.wei.model.Childinfo;
import com.bp.wei.model.Member;
import com.bp.wei.model.MemberinfoWithBLOBs;

public interface MemberMgmtService {
	
	///////////////////for member
	//insert
	int insertMemberinfo(MemberinfoWithBLOBs memberinfowithblogs, String openid);
	//search
	MemberinfoWithBLOBs getMemberinfobyname(String name);
	//update
	int updateMemberinfo(MemberinfoWithBLOBs memberinfowithblogs);

	
	///////////////////for child
	//insert
	int insertChildinfo(Childinfo childinfo, String mbname);

	//for examples
	Member getMemberById(int memberId);
	int setMember(Member member);
	
	Member getMemberWithChildren(String memberId);
}
