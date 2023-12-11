package com.costswatcher.costswatcher.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    Optional<GroupMember> findGroupMemberById(GroupMemberId groupMemberId);
    @Modifying
    @Query("DELETE FROM GroupMember gr WHERE gr.id.idGroup = :idGroup")
    void deleteAllByGroupId(@Param("idGroup") Integer idGroup);


}
