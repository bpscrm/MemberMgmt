package com.bp.wei.dao;

import com.bp.wei.model.Feedback;
import com.bp.wei.model.FeedbackWithBLOBs;

public interface FeedbackDao {
    int deleteByPrimaryKey(String id);

    int insert(FeedbackWithBLOBs record);

    int insertSelective(FeedbackWithBLOBs record);

    FeedbackWithBLOBs selectByPrimaryKey(String id);
    
    FeedbackWithBLOBs selectByFeedbackName(String name);

    int updateByPrimaryKeySelective(FeedbackWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FeedbackWithBLOBs record);

    int updateByPrimaryKey(Feedback record);
}