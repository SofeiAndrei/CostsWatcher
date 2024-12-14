package com.costswatcher.costswatcher.expense;

import com.costswatcher.costswatcher.expense.helper.GroupExpenseParticipantId;
import com.costswatcher.costswatcher.group.GroupEntity;
import com.costswatcher.costswatcher.group.GroupMemberService;
import com.costswatcher.costswatcher.group.GroupService;
import com.costswatcher.costswatcher.user.UserEntity;
import com.costswatcher.costswatcher.user.UserService;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupExpenseService {
    private final GroupExpenseRepository grExpRepository;
    private final GroupExpenseParticipantRepository grExpPartRepository;
    private final GroupMemberService groupMemberService;
    private final GroupService groupService;
    private final UserService userService;

    public GroupExpenseService(
            GroupExpenseRepository grExpRepository,
            GroupExpenseParticipantRepository grExpPartRepository,
            GroupMemberService groupMemberService,
            UserService userService,
            GroupService groupService) {
        this.grExpRepository = grExpRepository;
        this.grExpPartRepository = grExpPartRepository;
        this.groupMemberService = groupMemberService;
        this.groupService = groupService;
        this.userService = userService;
    }

    public int getTotalSum(int groupId) {
        List<GroupExpenseEntity> expenses = this.getAllGroupExpenses(groupId);
        int sum = 0;
        for (GroupExpenseEntity expense : expenses) {
            List<Tuple> participants = this.getExpenseParticipants(expense.getIdExpense());
            for (Tuple participant : participants) {
                sum += (int)(participant.get(2));
            }
        }
        return sum;
    }

    public List<GroupExpenseEntity> getAllGroupExpenses(int idGroup) {
        return grExpRepository.findAllByIdGroup(idGroup);
    }

    public Map<Integer, Boolean> getIsExpenseParticipantMap(int idUser, int idGroup) {
        Map<Integer, Boolean> result = new HashMap<>();
        List<GroupExpenseEntity> expenses = this.getAllGroupExpenses(idGroup);
        for (GroupExpenseEntity expense : expenses) {
            List<Tuple> participants = this.getExpenseParticipants(expense.getIdExpense());
            for (Tuple participant : participants) {
                if (participant.get(1).toString().equals(String.valueOf(idUser))) {
                    result.put(expense.getIdExpense(), true);
                    break;
                }
            }
            if (!result.containsKey(expense.getIdExpense())) {
                result.put(expense.getIdExpense(), false);
            }
        }
        return result;
    }

    @Transactional
    public void deleteGroupExpense(int idExpense, int idGroup) {
        grExpPartRepository.deleteAllByIdExpense(idExpense);
        grExpRepository.deleteByIdExpenseAndIdGroup(idExpense, idGroup);
    }

    public Optional<GroupExpenseEntity> getExpenseObjByIdExpense(int idExpense) {
        return grExpRepository.findById(idExpense);
    }

    public List<Tuple> getExpenseParticipants(int idExpense) {
        return grExpPartRepository.getAllByIdExpense(idExpense);
    }

    public void saveEditedExpenseName(int idExpense, String newName) {
        Optional<GroupExpenseEntity> expenseObj = grExpRepository.findById(idExpense);
        expenseObj.ifPresent(expense -> {
            expense.setName(newName);
            grExpRepository.save(expense);
        });
    }

    @Transactional
    public void deleteExpenseParticipant(GroupExpenseParticipantId participantId) {
        grExpPartRepository.deleteById(participantId);
    }

    public String addExpenseParticipant(int idExpense, String username, int amount) {
        Optional<UserEntity> userSearchResult = userService.getUserByUsername(username);
        if (userSearchResult.isEmpty())
            return "The specified user is not a member of this group!";

        Optional<GroupExpenseEntity> expSearchResult = grExpRepository.findById(idExpense);
        if (expSearchResult.isEmpty())
            return "There is no expense with the given ID!";

        int idGroup = expSearchResult.get().getIdGroup();
        if (!groupMemberService.checkIfGroupHasMember(idGroup, username))
            return "The specified user is not a member of this group!";

        int idUser = userSearchResult.get().getIdUser();
        GroupExpenseParticipantId participantId = new GroupExpenseParticipantId(idUser, idExpense);
        Optional<GroupExpenseParticipantEntity> mbSearchResult = grExpPartRepository.findById(participantId);
        if (mbSearchResult.isPresent())
            return "The user already takes part in this expense!";

        grExpPartRepository.save(new GroupExpenseParticipantEntity(participantId, amount));
        return null;
    }

    public String addGroupExpense(String expenseName, int idGroup, List<Pair<String, Integer>> participants) {
        Optional<GroupEntity> groupSearchResult = null;
        if (groupService != null)
            groupSearchResult = groupService.getGroupById(idGroup);
            if (groupSearchResult == null || groupSearchResult.isEmpty())
                return "There is no group with the given ID";

        for (Pair<String, Integer> member : participants)
            if (!groupMemberService.checkIfGroupHasMember(idGroup, member.getFirst()))
                return "The user " + member.getFirst() + " is not a member of this group!";

        GroupExpenseEntity newExpense = new GroupExpenseEntity(-1, expenseName, idGroup, new Timestamp(System.currentTimeMillis()));
        int expenseId = grExpRepository.save(newExpense).getIdExpense();
        if (expenseId == -1)
            return "Unknown error on adding the new expense!";

        for (Pair<String, Integer> member : participants)
            this.addExpenseParticipant(expenseId, member.getFirst(), member.getSecond());
        return null;
    }
}
