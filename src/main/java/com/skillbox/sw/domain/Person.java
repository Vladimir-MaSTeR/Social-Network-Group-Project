package com.skillbox.sw.domain;

import com.skillbox.sw.domain.enums.MessagesPermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    private String firstName;

    private String lastName;

    @CreationTimestamp
    @Column(columnDefinition = "DATE")
    private LocalDate regDate;

    @Column(columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "e_mail")
    private String email;

    private Integer phone;

    private String password;

    @Column(length = 2048)
    private String photo;

    @Column(length = 2048)
    private String about;

    @Column(length = 45)
    private String town;

    @Column(length = 45)
    private String confirmationCode;

    private boolean isApproved;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('ALL', 'FRIENDS')")
    private MessagesPermission messagesPermission;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate lastOnlineTime;

    private boolean isBlocked;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BlockHistory> blockHistories;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostComment> postComments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostLike> postLikes;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
