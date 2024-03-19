package com.college.jobboard.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;
import java.util.Date;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username", unique = true)
    private User user;

    @Column(nullable = false)
    private String fullName;

    private Double cgpa;

    private String stream;

    @Column(name = "graduation_year", columnDefinition = "YEAR")
    private Year graduationYear;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private boolean backlogs;
}

