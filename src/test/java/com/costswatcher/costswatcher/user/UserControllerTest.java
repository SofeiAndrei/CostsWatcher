package com.costswatcher.costswatcher.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHomePage() {
        String view = userController.homePage(model);
        assertEquals("home", view);
    }

    @Test
    void testSignInPage_UserSignedIn_RedirectToGroups() {
        UserEntity.signedInUser = new UserEntity();
        String view = userController.signInPage(model);
        assertEquals("redirect:/groups", view);
        UserEntity.signedInUser = null; // Resetting for subsequent tests
    }

    @Test
    void testSignInPage_UserNotSignedIn_RenderSignIn() {
        String view = userController.signInPage(model);
        assertEquals("signin", view);
    }

    @Test
    void testSignUpPage_UserSignedIn_RedirectToGroups() {
        UserEntity.signedInUser = new UserEntity();
        String view = userController.signUpPage(model);
        assertEquals("redirect:/groups", view);
        UserEntity.signedInUser = null; // Resetting for subsequent tests
    }

    @Test
    void testSignUpPage_UserNotSignedIn_RenderSignUp() {
        String view = userController.signUpPage(model);
        assertEquals("signup", view);
    }

    @Test
    void testSignInSubmit_UserSignedIn_RedirectToGroups() {
        UserEntity.signedInUser = new UserEntity();
        when(userService.signInUser(any())).thenReturn(true);

        String view = userController.signInSubmit(model, new UserEntity());
        assertEquals("redirect:/groups", view);
        verify(userService, never()).signInUser(any());
        UserEntity.signedInUser = null; // Resetting for subsequent tests
    }

    @Test
    void testSignInSubmit_UserNotSignedIn_SuccessfulSignIn_RedirectToGroups() {
        when(userService.signInUser(any())).thenReturn(true);

        String view = userController.signInSubmit(model, new UserEntity());
        assertEquals("redirect:/groups", view);
        verify(userService, times(1)).signInUser(any());
    }

    @Test
    void testSignInSubmit_UserNotSignedIn_UnsuccessfulSignIn_RenderSignIn() {
        when(userService.signInUser(any())).thenReturn(false);

        String view = userController.signInSubmit(model, new UserEntity());
        assertEquals("signin", view);
        verify(userService, times(1)).signInUser(any());
    }

    @Test
    void testSignUpSubmit_UserSignedIn_RedirectToGroups() {
        UserEntity.signedInUser = new UserEntity();
        String view = userController.signUpSubmit(new UserEntity(), model);
        assertEquals("redirect:/groups", view);
        UserEntity.signedInUser = null; // Resetting for subsequent tests
    }

    @Test
    void testSignUpSubmit_UserNotSignedIn_SuccessfulSignUp_RedirectToHome() {
        when(userService.signUpUser(any())).thenReturn(true);

        String view = userController.signUpSubmit(new UserEntity(), model);
        assertEquals("home", view);
        verify(userService, times(1)).signUpUser(any());
    }

    @Test
    void testSignUpSubmit_UserNotSignedIn_UnsuccessfulSignUp_RenderSignUp() {
        when(userService.signUpUser(any())).thenReturn(false);

        String view = userController.signUpSubmit(new UserEntity(), model);
        assertEquals("signup", view);
        verify(userService, times(1)).signUpUser(any());
    }

    @Test
    void testSignOut_UserSignedIn_SignOutAndRedirectToHomePage() {
        UserEntity.signedInUser = new UserEntity();

        String view = userController.signOut(model);
        assertEquals("redirect:/", view);
        verify(userService, times(1)).signOutUser();
        UserEntity.signedInUser = null; // Resetting for subsequent tests
    }

    @Test
    void testSignOut_UserNotSignedIn_NoAction() {
        UserEntity.signedInUser = null;

        String view = userController.signOut(model);
        assertEquals("redirect:/", view);
        verify(userService, times(1)).signOutUser();
    }
}
