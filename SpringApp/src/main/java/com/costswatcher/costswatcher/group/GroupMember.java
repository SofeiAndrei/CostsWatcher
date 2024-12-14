package com.costswatcher.costswatcher.group;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class GroupMember {
    @EmbeddedId
    private GroupMemberId id;
    private Boolean pendingRequest;

    public GroupMember() {
    }
    public GroupMember(GroupMemberId id, Boolean pendingRequest) {
        this.id = id;
        this.pendingRequest = pendingRequest;
    }

    public GroupMemberId getId() {
        return id;
    }

    public void setId(GroupMemberId id) {
        this.id = id;
    }

    public Boolean getPendingRequest() {
        return pendingRequest;
    }

    public void setPendingRequest(Boolean pendingRequest) {
        this.pendingRequest = pendingRequest;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "id=" + id.toString() +
                ", pendingRequest=" + pendingRequest +
                '}';
    }
}
