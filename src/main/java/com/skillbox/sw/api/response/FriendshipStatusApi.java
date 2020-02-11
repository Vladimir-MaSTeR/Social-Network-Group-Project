package com.skillbox.sw.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendshipStatusApi extends AbstractResponse {

    private int id;
    private long time;
    private String name;
    private Code code;

    public enum Code {REQUEST, FRIEND, BLOCKED, DECLINED, SUBSCRIBED}
}
