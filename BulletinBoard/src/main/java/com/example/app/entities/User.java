package com.example.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false,length = 50)
    private String lastName;

    @Column(nullable = false,unique = true, length = 50)
    private String email;

    @ToString.Exclude
    @Column(nullable = false)
    private String password;
//    @ToString.Exclude
//    @OneToMany(mappedBy = "user", orphanRemoval = true)
//    private List<Bulletin> bulletins;
}
