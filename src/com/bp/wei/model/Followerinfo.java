package com.bp.wei.model;

import java.util.Date;

public class Followerinfo {
    private String id;

    private String name;

    private Date dateEntered;

    private Date dateModified;

    private String modifiedUserId;

    private String createdBy;

    private Boolean deleted;

    private String assignedUserId;

    private Date followDt;

    private String wechatUserid;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId == null ? null : modifiedUserId.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId == null ? null : assignedUserId.trim();
    }

    public Date getFollowDt() {
        return followDt;
    }

    public void setFollowDt(Date followDt) {
        this.followDt = followDt;
    }

    public String getWechatUserid() {
        return wechatUserid;
    }

    public void setWechatUserid(String wechatUserid) {
        this.wechatUserid = wechatUserid == null ? null : wechatUserid.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}