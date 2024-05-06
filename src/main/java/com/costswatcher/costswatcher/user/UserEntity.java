package com.costswatcher.costswatcher.user;

import jakarta.persistence.*;

@Entity
public class UserEntity {
    public static UserEntity signedInUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int idUser;
    String username;
    String password;
    String email;

    public UserEntity() {
    }

    public UserEntity(int id_user, String username, String password, String email) {
        this.idUser = id_user;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + idUser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
