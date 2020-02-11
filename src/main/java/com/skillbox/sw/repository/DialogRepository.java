package com.skillbox.sw.repository;

import com.skillbox.sw.domain.Dialog;
import com.skillbox.sw.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Integer> {

 //   List<Dialog> findByOwnerId(int id);
//
//    Page<Dialog> findByOwnerId(int id, Pageable pageable);
//
//    Optional<Dialog> findById(int id);
//
//    List<Dialog> findByOwnerAndRecipients(Person owner, List<Person> recipients);
}