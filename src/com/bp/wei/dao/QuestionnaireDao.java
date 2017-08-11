package com.bp.wei.dao;

import com.bp.wei.model.Questionnaire;

public interface QuestionnaireDao {
    int deleteByPrimaryKey(String id);

    int insert(Questionnaire record);

    int insertSelective(Questionnaire record);

    Questionnaire selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Questionnaire record);

    int updateByPrimaryKeyWithBLOBs(Questionnaire record);

    int updateByPrimaryKey(Questionnaire record);
    
    Questionnaire selectByPrimaryKeyWithQA(String id);
}