package com.skillbox.sw.controller;

import com.skillbox.sw.repository.TagRepository;
import com.skillbox.sw.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerIntegrationTest {

    // TODO: Дописать тесты

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagController tagController;

    @MockBean
    private TagRepository tagRepository;

    @MockBean
    private TagService tagService;


    @BeforeEach
    void setUp() {

    }


    @Test
    public void save() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getTags() {
    }
}
