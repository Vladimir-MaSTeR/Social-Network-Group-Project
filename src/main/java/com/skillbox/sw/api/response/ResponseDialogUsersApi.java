package com.skillbox.sw.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.print.attribute.IntegerSyntax;
import java.util.List;

/**
 * response for PUT /api/v1/dialogs/{id}/users
 */

@Data
@AllArgsConstructor
public class ResponseDialogUsersApi extends AbstractResponse {

    @JsonProperty("user_ids")
    private List<Integer> userIds;
}