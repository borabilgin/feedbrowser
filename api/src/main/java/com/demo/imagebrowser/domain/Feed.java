package com.demo.imagebrowser.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "FEED")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "ADDRESS")
    private String address;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
    private FeedCategory category;

    public Feed() {
    }

    public Feed(Long id, String name, String address, FeedCategory category) {
        this(name, address, category);
        this.id = id;
    }

    public Feed(String name, String address, FeedCategory category) {
        this.name = name;
        this.address = address;
        this.category = category;
    }
}
