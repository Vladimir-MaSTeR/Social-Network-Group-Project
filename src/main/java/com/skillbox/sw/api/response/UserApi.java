package com.skillbox.sw.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserApi extends AbstractResponse {

    private int id;
    private String name;
    private String email;
    private Type type;

    public enum Type {MODERATOR, ADMIN}
}
