package com.example.app.repository;

import com.example.app.entities.Bulletin;
import com.example.app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BulletinRepository extends PagingAndSortingRepository<Bulletin, Long> {
    @Override
    Bulletin save(Bulletin bulletin);

    void deleteById(Long id);

    @Override
    Page<Bulletin> findAll(Pageable pageable);
    List<Bulletin> findAllByUser(User user);
    Page<Bulletin> findAllByUser(Pageable pageable, User user);

    @Override
    Optional<Bulletin> findById(Long aLong);
}
