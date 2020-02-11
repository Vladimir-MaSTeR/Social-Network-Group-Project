package com.skillbox.sw.service;

import com.skillbox.sw.SocialNetworkImplApplication;
import com.skillbox.sw.api.request.RequestPersonApi;
import com.skillbox.sw.api.request.RequestPostApi;
import com.skillbox.sw.domain.Person;
import com.skillbox.sw.domain.Post;
import com.skillbox.sw.domain.enums.MessagesPermission;
import com.skillbox.sw.repository.PersonRepository;
import com.skillbox.sw.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@ActiveProfiles("test_data")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SocialNetworkImplApplication.class)
class PersonServiceTest {

  @Autowired
  private PersonService service;

  @MockBean
  private PersonRepository personRepository;

  @MockBean
  private PostRepository postRepository;

  private static Person person;
  private static RequestPersonApi personApi;

  private static RequestPostApi postpostApi;
  private static LocalDate localDate;

  private static List<Person> allPersons;

  private static List<Post> posts;
  private static Post post1;
  private static Post post2;

  private static Person unblockedUser;
  private static Person blockedUser;

  @BeforeAll
  static void init() {
    person = new Person();
    String email = "evpatiy@mail.ru";
    person.setEmail(email);
    person.setId(100);

    personApi = RequestPersonApi.builder()
            .firstName("Johny")
            .lastName("Fatter")
            .birthDate(LocalDate.of(1980, Month.APRIL, 12).toEpochDay())//atStartOfDay(ZoneOffset.UTC).toEpochSecond() * 1000
            .about("Джонни родился в небогатой, но честной семье ")
            .phone("663656")
            .photoId("646")
            .town("Detroit")
            .messagesPermission(MessagesPermission.FRIENDS).build();
    localDate = LocalDate.of(2020, Month.MAY, 9);

    postpostApi = new RequestPostApi("News","Greta Thunberg should go away and study economics before lecturing investors.");

    allPersons = new ArrayList<>();
    allPersons.add(person);

    posts = new ArrayList<>();
    post1 = new Post();
    post2 = new Post();
    post1.setId(1);
    post2.setId(2);
    post1.setAuthor(person);
    post2.setAuthor(person);
    posts.add(post1);
    posts.add(post2);

    unblockedUser = new Person();
    unblockedUser.setId(25);
    unblockedUser.setBlocked(false);

    blockedUser = new Person();
    blockedUser.setId(41);
    blockedUser.setBlocked(true);
  }

  @Test
  void getCurrentUser() {
    Mockito.when(personRepository.findByEmail(person.getEmail()))
        .thenReturn(java.util.Optional.of(person));
    service.getCurrentUser(person.getEmail());
    Assertions.assertEquals(person, service.getCurrentUser(person.getEmail()));
  }

  @Test
  void editCurrentUser() {
    Mockito.when(personRepository.findByEmail(person.getEmail()))
        .thenReturn(java.util.Optional.of(person));
    Mockito.when(personRepository.save(person)).thenReturn(person);
    service.editCurrentUser(person.getEmail(), personApi);
    Assertions.assertEquals("Your profile has been successfully modified.",
        service.editCurrentUser(person.getEmail(), personApi));
  }

  @Test
  void deleteCurrentUser() {
    Mockito.when(personRepository.findByEmail(person.getEmail()))
        .thenReturn(java.util.Optional.of(person));
    service.deleteCurrentUser(person.getEmail());
    Assertions.assertEquals("Your profile has been deleted successfully.",
        service.deleteCurrentUser(person.getEmail()));
  }

//  @Test
//  void getUser() {
//    Mockito.when(personRepository.findByEmail(person.getEmail()))
//        .thenReturn(java.util.Optional.of(person));
//    Mockito.when(personRepository.findById(person.getId()))
//        .thenReturn(java.util.Optional.of(person));
//    service.getUser(person.getId(), person.getEmail());
//    Assertions.assertEquals(person, service.getUser(person.getId(), person.getEmail()));
//  }
//
//  @Test
//  void getPosts() {
//    Mockito.when(personRepository.findByEmail(person.getEmail()))
//        .thenReturn(java.util.Optional.of(person));
//    Mockito.when(postRepository.findAllByAuthorId(person.getId())).thenReturn(posts);
//    service.getPosts(person.getId(), person.getEmail(), 0, 20);
//    Assertions.assertEquals(posts, service.getPosts(person.getId(), person.getEmail(), 0, 20));
//  }
//
//  @Test
//  void addPost() {
//    Mockito.when(personRepository.findByEmail(person.getEmail()))
//        .thenReturn(java.util.Optional.of(person));
//    Mockito.when(personRepository.findById(person.getId()))
//        .thenReturn(java.util.Optional.of(person));
//    service.addPost(person.getId(), localDate, person.getEmail(), postpostApi);
//    Assertions.assertEquals("Your post has been successfully posted.",
//        service.addPost(person.getId(), localDate, person.getEmail(), postpostApi));
//  }
//
//  @Test
//  void searchUser() {
//    Mockito.when(personRepository.findByEmail(person.getEmail()))
//        .thenReturn(java.util.Optional.of(person));
//    Mockito.when(personRepository.findAll()).thenReturn(allPersons);
//    service.searchUser("Johny", "Fatter", 25, 45, "Detroit", 0,
//        20, person.getEmail());
//    Assertions.assertEquals(allPersons,
//        service.searchUser("Johny", "Fatter", 25, 45, "Detroit",
//            0, 20, person.getEmail()));
//  }
//
//  @Test
//  void blockUser() {
//    personRepository.save(unblockedUser);
//    Mockito.when(personRepository.findByEmail(person.getEmail()))
//        .thenReturn(java.util.Optional.of(person));
//    Mockito.when(personRepository.findById(unblockedUser.getId()))
//        .thenReturn(java.util.Optional.of(unblockedUser));
//    service.blockUser(unblockedUser.getId(), person.getEmail());
//    Assertions.assertEquals("User successfully blocked.",
//        service.blockUser(unblockedUser.getId(), person.getEmail()));
//  }
//
//  @Test
//  void unblockUser() {
//    personRepository.save(blockedUser);
//    Mockito.when(personRepository.findByEmail(person.getEmail()))
//        .thenReturn(java.util.Optional.of(person));
//    Mockito.when(personRepository.findById(blockedUser.getId()))
//        .thenReturn(java.util.Optional.of(blockedUser));
//    service.unblockUser(blockedUser.getId(), person.getEmail());
//    Assertions.assertEquals("User successfully unlocked.",
//        service.unblockUser(blockedUser.getId(), person.getEmail()));
//  }
}