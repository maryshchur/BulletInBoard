package com.example.app.repository;

import com.example.app.entities.Bulletin;
import com.example.app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BulletinRepository extends PagingAndSortingRepository<Bulletin, Long> {
    @Override
    Bulletin save(Bulletin bulletin);

    void deleteById(Long id);

    @Override
    Page<Bulletin> findAll(Pageable pageable);

    Page<Bulletin> findAllByUser(Pageable pageable, User user);

    @Override
    Optional<Bulletin> findById(Long aLong);
}
