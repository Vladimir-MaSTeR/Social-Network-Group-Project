package com.skillbox.sw.service;

import com.skillbox.sw.api.request.RequestDialogUsersApi;
import com.skillbox.sw.api.response.AbstractResponse;
import com.skillbox.sw.api.response.IdApi;
import com.skillbox.sw.api.response.ResponseApi;
import com.skillbox.sw.config.jwt.JwtProvider;
import com.skillbox.sw.domain.Dialog;
import com.skillbox.sw.domain.Person;
import com.skillbox.sw.repository.DialogRepository;
import com.skillbox.sw.repository.MessageRepository;
import com.skillbox.sw.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DialogService {

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MessageRepository messageRepository;

    public AbstractResponse createDialog(RequestDialogUsersApi userList, String token) {

        Dialog dialog = new Dialog();

        dialog.setRecipients(personRepository.findByIdIn(userList.getUserIds()));

        Person dialogOwner = personRepository.findByEmail(JwtProvider.getUsername(token))
                                            .orElseThrow(() -> new EntityNotFoundException("No such user"));
        dialog.setOwner(dialogOwner);

        return new ResponseApi<>("dialog added", new IdApi(dialogRepository.saveAndFlush(dialog).getId()));
    }
}
