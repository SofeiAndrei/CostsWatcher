package com.costswatcher.costswatcher.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean signUpUser(UserEntity user) {
        boolean userExists = userRepository.existsByUsername(user.getUsername());
        if (!userExists)
            userRepository.save(user);
        return !userExists;
    }

    public boolean signInUser(UserEntity user) {
        Optional<UserEntity> foundUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        foundUser.ifPresent(value -> UserEntity.signedInUser = value);
        return foundUser.isPresent();
    }

    public void signOutUser() {
        UserEntity.signedInUser = null;
    }

    public boolean checkIfUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> getUserByIdUser(int idUser) {
        return userRepository.findById(idUser);
    }
}
