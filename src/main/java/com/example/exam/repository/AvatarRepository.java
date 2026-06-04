package com.example.exam.repository;
import com.example.exam.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {


    Page<Avatar> findAll(Pageable pageable);
}

