package com.costswatcher.costswatcher.group;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupMemberId implements Serializable {
    private Integer idUser;
    private Integer idGroup;

    public GroupMemberId(Integer idUser, Integer idGroup) {
        this.idUser = idUser;
        this.idGroup = idGroup;
    }

    public GroupMemberId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupMemberId that)) return false;
        return Objects.equals(idUser, that.idUser) &&
                Objects.equals(idGroup, that.idGroup);
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idGroup);
    }

    @Override
    public String toString() {
        return "GroupMemberId{" +
                "idUser=" + idUser +
                ", idGroup=" + idGroup +
                '}';
    }
}
