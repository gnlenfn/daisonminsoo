package com.potential.hackathon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uniqueUserId = UUID.randomUUID();
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String nickname;
    @CreatedDate
    private LocalDateTime createdAt;
    private Boolean requiredTerms;
    private Boolean marketingTerms;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Posts> posts = new ArrayList<>();

}
