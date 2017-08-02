package com.bp.wei.dao;

import com.bp.wei.model.Childinfo;

public interface ChildinfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Childinfo record);

    int insertSelective(Childinfo record);

    Childinfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Childinfo record);

    int updateByPrimaryKeyWithBLOBs(Childinfo record);

    int updateByPrimaryKey(Childinfo record);
}