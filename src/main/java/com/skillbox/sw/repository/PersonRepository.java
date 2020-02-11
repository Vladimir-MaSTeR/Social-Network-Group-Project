package com.skillbox.sw.repository;

import com.skillbox.sw.domain.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);

    List<Person> findByIdIn(List<Integer> ids);

//    @Query("from Person p limit :offset, :itemPerPage")
//    List<Person> findLimited(int offset, int itemPerPage);
}