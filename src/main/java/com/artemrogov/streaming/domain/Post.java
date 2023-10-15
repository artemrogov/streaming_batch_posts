package com.artemrogov.streaming.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "active",columnDefinition = "boolean default false")
    private Boolean active;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified", insertable = false, updatable = false)
    @Generated(GenerationTime.ALWAYS)
    private Date lastModified;

    @ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id",nullable = false)
    private Set<Image> images = new HashSet<>();
}
