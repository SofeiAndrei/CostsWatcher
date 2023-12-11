package com.costswatcher.costswatcher.group;

import com.costswatcher.costswatcher.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {
    Optional<GroupEntity> findGroupsByIdGroup(Integer idGroup);
    @Modifying
    @Query("SELECT gr FROM GroupEntity gr, GroupMember gm WHERE gm.id.idGroup = gr.idGroup AND gm.id.idUser = ?1")
    List<GroupEntity> findAllForUser(int idUser);
    @Modifying
    @Query("SELECT ue FROM UserEntity ue, GroupMember gm WHERE ue.idUser = gm.id.idUser AND gm.id.idGroup = ?1")
    List<UserEntity> getAllMembersForGroup(int idGroup);
}
