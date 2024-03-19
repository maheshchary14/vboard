package com.college.jobboard.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "job_pool")
public class JobPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_pool_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "applied_at")
    private Date appliedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "viewed_at")
    private Date viewedAt;
}
