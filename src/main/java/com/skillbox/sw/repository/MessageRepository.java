package com.skillbox.sw.repository;

import com.skillbox.sw.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {


}
