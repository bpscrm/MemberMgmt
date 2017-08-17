package com.bp.wei.service;


import com.bp.wei.model.Childinfo;
import com.bp.wei.model.FeedbackWithBLOBs;
import com.bp.wei.model.Followerinfo;
import com.bp.wei.model.Member;
import com.bp.wei.model.Memberinfo;
import com.bp.wei.model.MemberinfoWithBLOBs;
import com.bp.wei.model.Purchaseinfo;

public interface MemberMgmtService {
	
	///////////////////for follower
	//search myfollower
	Followerinfo getFollowerlist(String id);
	
	///////////////////for member
	//insert
	int insertMemberinfo(MemberinfoWithBLOBs memberinfowithblogs, String openid);
	//search
	MemberinfoWithBLOBs getMemberinfobyname(String name);
	//update
	int updateMemberinfo(MemberinfoWithBLOBs memberinfowithblogs);

	///////////////////for child
	//insert
	int insertChildinfo(Childinfo childinfo, String mbid);
	//search
	Memberinfo getMemberWithChildren(String memberId);
	Childinfo getchildinfo(String id);
	//update
	int updateChildinfo(Childinfo childinfo);
	
	///////////////////for Purchase
	//search
	Memberinfo getMemberWithPurchase(String id);
	Purchaseinfo getPurchaseInfo(String id);
	
	///////////////////for feedback
	//insert
	int insertFeedbackinfo(FeedbackWithBLOBs feedbackinfo, String purchaseid);
	//search
	FeedbackWithBLOBs getFeedbackinfobyid(String id);
	Purchaseinfo getFeedbacklist(String id);
	//update
	int updateFeedbackinfo(FeedbackWithBLOBs feedbackinfo);

	
    ///////////////////for test follower  
	String getTestFollowerinfo(Followerinfo follow);
	
	
	//for examples
	Member getMemberById(int memberId);
	int setMember(Member member);
	
	
}
