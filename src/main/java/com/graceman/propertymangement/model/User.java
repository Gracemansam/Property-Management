package com.graceman.propertymangement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User extends BaseEntity {

    private String ownerName;
    @Column(name = "EMAIL", nullable = false)
    private String ownerEmail;
    private String phoneNumber;
    private String password;
    private String username;
    private String role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Property> properties = new HashSet<>();


    public User(Long id, Date CreatedAt, Date UpdatedAt, String ownerName, String ownerEmail, String phoneNumber, String password, String username, String role, HashSet<Property> properties) {
        super(id, CreatedAt, UpdatedAt);
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.username = username;
        this.role = role;
        this.properties = properties;
    }

    public User() {
    }
}
