package com.bp.wei.dao;

import com.bp.wei.model.Member;

public interface MemberDao {
	public int addMember(Member member);
	
	public Member getMember(String name, String mobile);
}
