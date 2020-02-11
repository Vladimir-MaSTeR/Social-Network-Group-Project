package com.skillbox.sw.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetPasswordApi {

    private String token;
    private String password;
}
