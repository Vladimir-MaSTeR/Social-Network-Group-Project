package com.skillbox.sw.domain;

import com.skillbox.sw.domain.enums.FriendshipCode;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class FriendshipStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate time;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('REQUEST','FRIEND','BLOCKED','DECLINED','SUBSCRIBED','UNBLOCK')")
    private FriendshipCode code;
}
