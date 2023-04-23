package com.artemrogov.streaming.entities;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "post")
@Table(name = "posts")
@Data
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
}
