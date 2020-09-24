package com.example.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

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
}
