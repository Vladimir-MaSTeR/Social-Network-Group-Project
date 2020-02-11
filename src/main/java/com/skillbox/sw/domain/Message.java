package com.skillbox.sw.domain;

import com.skillbox.sw.domain.enums.ReadStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate time;//  - дата и время отправки

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Person recipient;

    @Column(length = 2048)
    private String messageText;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('SENT','READ')")
    private ReadStatus readStatus;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id", nullable = false)
    private Dialog dialog;

    @OneToMany(mappedBy = "entityId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> entity;
}

