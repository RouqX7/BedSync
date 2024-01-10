package com.example.BedSync.models;

import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    @Indexed(unique = true,direction = IndexDirection.DESCENDING)
    private String firstName;
    private String lastName;
    @Column(unique = true)

    private String email;
    private String password;
    private Date createdAt;
    private Date updatedAt;

    @DBRef
    private Role role;
    private Set<Role> roles; // Assuming Role is a class or enum

    private String phoneNumber;
    private String securityQuestion;
    private String securityAnswer;
    private String alternateEmail;
    private String[] permissions;
    @Transient
    private MultipartFile image;
    private String profilePictureUrl;


    public User(String id, String firstName, String lastName, String email, String password, Date createdAt, Date updatedAt, Role role, Set<Role> roles, String phoneNumber, String securityQuestion, String securityAnswer, String alternateEmail, String[] permissions, String profilePictureUrl, MultipartFile image) {        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.alternateEmail = alternateEmail;
        this.permissions = permissions;
        this.profilePictureUrl = profilePictureUrl;
        this.image = image;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", role=" + role +
                ", roles=" + roles +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", alternateEmail='" + alternateEmail + '\'' +
                ", permissions=" + Arrays.toString(permissions) +
                ", image=" + image +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
