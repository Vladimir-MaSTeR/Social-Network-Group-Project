package com.skillbox.sw.controller;


import com.skillbox.sw.api.request.RequestDialogUsersApi;
import com.skillbox.sw.api.response.AbstractResponse;
import com.skillbox.sw.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.skillbox.sw.config.SecurityConstants.HEADER;

@RestController
@RequestMapping("/dialogs")
public class DialogController {

    @Autowired
    private DialogService dialogService;

    @PostMapping
    public AbstractResponse createDialog(@RequestHeader(value = HEADER) String token,
                                         @RequestBody RequestDialogUsersApi userIdList) {

        return dialogService.createDialog(userIdList,token);
    }

}
