package com.skillbox.sw.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DialogApi extends AbstractResponse {

    private int id;
    @JsonProperty("unread_count")
    private int unreadCount;
}
