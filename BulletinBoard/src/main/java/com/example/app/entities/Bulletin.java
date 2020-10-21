package com.example.app.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Bulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    private String image;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime addedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "likedBulletins")
    private Set<User> likes
            = new HashSet<>();
}
