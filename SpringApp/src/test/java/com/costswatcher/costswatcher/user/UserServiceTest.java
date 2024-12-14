package com.costswatcher.costswatcher.user;

import com.costswatcher.costswatcher.user.UserEntity;
import com.costswatcher.costswatcher.user.UserRepository;
import com.costswatcher.costswatcher.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUpUser_NewUser_Successful() {
        UserEntity user = new UserEntity();
        user.setUsername("newUser");
        user.setPassword("password");

        when(userRepository.existsByUsername("newUser")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        boolean signUpResult = userService.signUpUser(user);

        assertTrue(signUpResult);
        verify(userRepository, times(1)).existsByUsername("newUser");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSignUpUser_ExistingUser_Failure() {
        UserEntity user = new UserEntity();
        user.setUsername("existingUser");
        user.setPassword("password");

        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        boolean signUpResult = userService.signUpUser(user);

        assertFalse(signUpResult);
        verify(userRepository, times(1)).existsByUsername("existingUser");
        verify(userRepository, never()).save(user);
    }

    @Test
    void testSignInUser_ValidCredentials_Successful() {
        UserEntity user = new UserEntity();
        user.setUsername("existingUser");
        user.setPassword("password");

        when(userRepository.findByUsernameAndPassword("existingUser", "password")).thenReturn(Optional.of(user));

        boolean signInResult = userService.signInUser(user);

        assertTrue(signInResult);
        assertEquals(user, UserEntity.signedInUser);
    }

    @Test
    void testSignInUser_InvalidCredentials_Failure() {
        UserEntity user = new UserEntity();
        user.setUsername("existingUser");
        user.setPassword("invalidPassword");

        when(userRepository.findByUsernameAndPassword("existingUser", "invalidPassword")).thenReturn(Optional.empty());

        boolean signInResult = userService.signInUser(user);

        assertFalse(signInResult);
        assertNull(UserEntity.signedInUser);
    }

    @Test
    void testSignOutUser_UserSignedIn_Successful() {
        UserEntity.signedInUser = new UserEntity();

        userService.signOutUser();

        assertNull(UserEntity.signedInUser);
    }

    @Test
    void testCheckIfUserExists_UserExists_ReturnsTrue() {
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        boolean exists = userService.checkIfUserExists("existingUser");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("existingUser");
    }

    @Test
    void testCheckIfUserExists_UserDoesNotExist_ReturnsFalse() {
        when(userRepository.existsByUsername("nonExistingUser")).thenReturn(false);

        boolean exists = userService.checkIfUserExists("nonExistingUser");

        assertFalse(exists);
        verify(userRepository, times(1)).existsByUsername("nonExistingUser");
    }

    @Test
    void testGetUserByUsername_UserExists_ReturnsUserEntity() {
        UserEntity user = new UserEntity();
        user.setIdUser(1);
        user.setUsername("existingUser");
        user.setPassword("password");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(user));

        Optional<UserEntity> retrievedUser = userService.getUserByUsername("existingUser");

        assertTrue(retrievedUser.isPresent());
        assertEquals(user, retrievedUser.get());
        verify(userRepository, times(1)).findByUsername("existingUser");
    }

    @Test
    void testGetUserByUsername_UserDoesNotExist_ReturnsEmptyOptional() {
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        Optional<UserEntity> retrievedUser = userService.getUserByUsername("nonExistingUser");

        assertTrue(retrievedUser.isEmpty());
        verify(userRepository, times(1)).findByUsername("nonExistingUser");
    }
}
