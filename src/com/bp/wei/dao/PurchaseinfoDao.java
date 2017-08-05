package com.bp.wei.dao;

import com.bp.wei.model.Purchaseinfo;

public interface PurchaseinfoDao {
    int deleteByPrimaryKey(String id);

    int insert(Purchaseinfo record);

    int insertSelective(Purchaseinfo record);

    Purchaseinfo selectByPrimaryKey(String id);
    
    String selectIDByMember(String name);

    int updateByPrimaryKeySelective(Purchaseinfo record);

    int updateByPrimaryKeyWithBLOBs(Purchaseinfo record);

    int updateByPrimaryKey(Purchaseinfo record);

}