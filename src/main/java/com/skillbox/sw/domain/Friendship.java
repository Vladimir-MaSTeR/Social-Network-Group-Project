package com.skillbox.sw.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "src_person_id")
    private Person srcPerson;// - пользователь, запросивший дружбу

    @ManyToOne
    @JoinColumn(name = "dst_person_id")
    private Person dstPerson;// - пользователь, получивший запрос

    @OneToMany(mappedBy = "entityId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> entity;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private FriendshipStatus friendshipStatus;
}
