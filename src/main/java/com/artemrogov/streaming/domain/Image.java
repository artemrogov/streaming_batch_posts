package com.artemrogov.streaming.domain;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "image")
@Table(name = "images")
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "origin_name")
    private String originName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",updatable = false, insertable = false)
    @NotNull
    private Post post;
}
