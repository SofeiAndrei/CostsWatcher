package com.costswatcher.costswatcher.group;

import jakarta.persistence.*;

@Entity
@Table
public class Groups {
    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_sequence"
    )
    private Integer idGroup;
    private String groupName;

    public Groups() {
    }
    public Groups(Integer idGroup, String groupName) {
        this.idGroup = idGroup;
        this.groupName = groupName;
    }

    public Groups(String groupName) {
        this.groupName = groupName;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Group{" +
                "idGroup=" + idGroup +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
