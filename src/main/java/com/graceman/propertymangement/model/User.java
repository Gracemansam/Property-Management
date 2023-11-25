package com.graceman.propertymangement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yaml.snakeyaml.events.Event;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    private String ownerName;
    @NaturalId(mutable = true)
    @Column(name = "EMAIL", nullable = false)
    private String ownerEmail;
    private String phoneNumber;
    private String password;
    private String userName;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_role_junction",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;


    private boolean isEnabled = false;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private Set<Property> properties = new HashSet<>();




    public User() {
            super();
            authorities = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
