package com.skillbox.sw.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageApi extends AbstractResponse {

    private int id;
    private long time;
    @JsonProperty("author_id")
    private Integer authorId;
    @JsonProperty("recipient_id")
    private Integer recipientId;
    @JsonProperty("message_text")
    private String messageText;
    @JsonProperty("read_status")
    private Status readStatus;

    public enum Status {SENT, READ}
}
