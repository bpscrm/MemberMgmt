package com.bp.wei.dao;

import com.bp.wei.model.FeedbackToPurchase;

public interface FeedbackToPurchaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(FeedbackToPurchase record);

    int insertSelective(FeedbackToPurchase record);

    FeedbackToPurchase selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FeedbackToPurchase record);

    int updateByPrimaryKey(FeedbackToPurchase record);
}