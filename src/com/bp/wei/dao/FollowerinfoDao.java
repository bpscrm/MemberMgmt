package com.bp.wei.dao;

import com.bp.wei.model.Followerinfo;

public interface FollowerinfoDao {
    int deleteByPrimaryKey(String id);

    int insert(Followerinfo record);

    int insertSelective(Followerinfo record);

    String selectByPrimaryOpenid(String name);
    
    Followerinfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Followerinfo record);

    int updateByPrimaryKeyWithBLOBs(Followerinfo record);

    int updateByPrimaryKey(Followerinfo record);
}