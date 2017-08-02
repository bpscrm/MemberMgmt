package com.bp.wei.dao;

import com.bp.wei.model.ChildToMember;

public interface ChildToMemberMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChildToMember record);

    int insertSelective(ChildToMember record);

    ChildToMember selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChildToMember record);

    int updateByPrimaryKey(ChildToMember record);
}