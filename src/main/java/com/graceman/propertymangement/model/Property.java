package com.graceman.propertymangement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Entity
@Table(name = "PROPERTY_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class Property extends BaseEntity {
    @Column(name = "PROPERTY_TITLE", nullable = false)
    private String title;
    private String description;
    private Double price;
    private String address;
    @ManyToOne//(fetch = FetchType.LAZY)//it will not fetch the user data while fetching property
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Property(Long id, String title, String description, Double price, String address, User user, Date CreatedAt, Date UpdatedAt) {
        super(id, CreatedAt, UpdatedAt);
        this.title = title;
        this.description = description;
        this.price = price;
        this.address = address;
        this.user = user;
    }

}
