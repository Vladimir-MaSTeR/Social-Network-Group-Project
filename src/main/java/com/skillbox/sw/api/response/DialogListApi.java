package com.skillbox.sw.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DialogListApi extends AbstractResponse {

    private List<DialogApi> dialogs;
}