package com.potential.hackathon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicUpdate
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userId = UUID.randomUUID();
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String nickname;
    @CreatedDate
    private LocalDateTime createdAt;
    private Boolean requiredTerms;
    private Boolean marketingTerms;

    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comments> comments = new ArrayList<>();
    @OneToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Posts> posts = new ArrayList<>();

}
