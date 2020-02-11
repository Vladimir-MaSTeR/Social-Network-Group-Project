package com.skillbox.sw.service;

import com.skillbox.sw.api.request.RequestPersonApi;
import com.skillbox.sw.api.request.RequestPostApi;
import com.skillbox.sw.api.response.AbstractResponse;
import com.skillbox.sw.api.response.MessageSendRequestBodyApi;
import com.skillbox.sw.api.response.ResponsePersonApi;
import com.skillbox.sw.api.response.ResponsePostApi;
import com.skillbox.sw.config.jwt.JwtProvider;
import com.skillbox.sw.domain.Person;
import com.skillbox.sw.domain.Post;
import com.skillbox.sw.mapper.CommentMapper;
import com.skillbox.sw.mapper.DateMapper;
import com.skillbox.sw.mapper.PersonMapper;
import com.skillbox.sw.mapper.PostMapper;
import com.skillbox.sw.repository.PersonRepository;
import com.skillbox.sw.repository.PostCommentRepository;
import com.skillbox.sw.repository.PostLikeRepository;
import com.skillbox.sw.repository.PostRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@Transactional(readOnly = false)
@AllArgsConstructor
public class PersonService implements UserDetailsService {

  private PersonRepository personRepository;
  private PostRepository postRepository;
  private PostCommentRepository postCommentRepository;
  private PostLikeRepository postLikeRepository;
  private PersonMapper personMapper;
  private PostMapper postMapper;
  private DateMapper dateMapper;
  private CommentMapper commentMapper;

  @Transactional(readOnly = true)
  public AbstractResponse getCurrentUser(String token) {
    String email = JwtProvider.getUsername(token);
    Person person = personRepository.findByEmail(email).get();
    return personMapper.personToPersonApi(person);
  }

  public AbstractResponse editCurrentUser(String token, RequestPersonApi requestPersonApi) {
    String email = JwtProvider.getUsername(token);
    Person personBefore = personRepository.findByEmail(email).get();
    Person personAfter = personMapper.personApiToPerson(requestPersonApi);
    personAfter.setId(personBefore.getId());
    personAfter.setEmail(email);
    personAfter.setPassword(personBefore.getPassword());
    personRepository.save(personAfter);
    return personMapper.personToPersonApi(personAfter);
  }

  public AbstractResponse deleteCurrentUser(String token) {
    String email = JwtProvider.getUsername(token);
    Person person = personRepository.findByEmail(email).get();
    person.setDeleted(true);
    personRepository.save(person);
    return new MessageSendRequestBodyApi("Ok");
  }

  @Transactional(readOnly = true)
  public AbstractResponse getUser(int id) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User with this ID not found."));
    return personMapper.personToPersonApi(person);
  }

  @Transactional(readOnly = true)
  public List<ResponsePostApi> getPosts(int id, int offset, int itemPerPage) {
    List<Post> allPosts = postRepository.findAllByAuthorId(id);
    if (allPosts.isEmpty()) throw new EntityNotFoundException("No records were found for this search.");
    List<ResponsePostApi> responsePostApiList = new ArrayList<>();
    Pageable pageable = PageRequest.of( offset / itemPerPage, itemPerPage);
    responsePostApiList.addAll(postMapper.postToResponsePostApi(postRepository.findAllByAuthorId(id, pageable)));
    return responsePostApiList;
  }

  public AbstractResponse addPost(@PathVariable int id, long publishDate,
      RequestPostApi requestPostApi) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Not found"));
    Post post = postMapper.requestPostApiToPost(requestPostApi);
    post.setAuthor(person);
    post.setTime(dateMapper.asLocalDate(publishDate));
    postRepository.save(post);
    return postMapper.postToResponsePostApi(post);
  }

  @Transactional(readOnly = true)
  public List<ResponsePersonApi> searchUser(String firstName, String lastName, int ageFrom,
      int ageTo, String town, int offset, int itemPerPage) {
    List<Person> allPersons = personRepository.findAll();
    if (allPersons.isEmpty()) throw new EntityNotFoundException("No user found in the database.");
    List<ResponsePersonApi> personList = new ArrayList();
    Iterator<Person> iterator = allPersons.iterator();
    int count = 0;
    int yearNow = LocalDate.now().getYear();
    Person currentPerson;
    int currentPersonAge;
//    List<Person> people = personRepository.findLimited(offset, itemPerPage);
    while (iterator.hasNext()) {
      currentPerson = iterator.next();
      currentPersonAge = yearNow - currentPerson.getBirthDate().getYear();
      if (currentPerson.getFirstName().equals(firstName)
          && currentPerson.getLastName().equals(lastName)
          && currentPerson.getTown().equals(town)
          && currentPersonAge >= ageFrom
          && currentPersonAge <= ageTo) {
        count++;
        if (count >= offset && count <= (offset + itemPerPage)) {
          personList.add(personMapper.personToPersonApi(currentPerson));
        }
      }
    }
    if (personList.isEmpty()) throw new EntityNotFoundException("No users were found for this request.");
//    return new PersonListApi(people.stream().map(p -> personMapper.personToPersonApi(p)).collect(
//        Collectors.toList()), people.size(), offset, itemPerPage);
    return personList;
  }

  public AbstractResponse blockUser(int id) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User with this ID not found."));
    if (person.isBlocked()) throw new EntityNotFoundException("User is already blocked.");
    person.setBlocked(true);
    personRepository.save(person);
    return new MessageSendRequestBodyApi("Ok");
  }

  public AbstractResponse unlockUser(int id) {
    Person person = personRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User with this ID not found."));
    if (!person.isBlocked()) throw new EntityNotFoundException("User is already unlocked.");
    person.setBlocked(false);
    personRepository.save(person);
    return new MessageSendRequestBodyApi("Ok");
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Person existPerson = personRepository.findByEmail(email).get();
    if (existPerson == null) {
      throw new UsernameNotFoundException(email);
    }
    return new User(existPerson.getEmail(), existPerson.getPassword(), new ArrayList<>());
  }

  public Person getCurrentPersonByToken(String token) {
    return personRepository.findByEmail(JwtProvider.getUsername(token)).orElse(null);
  }
}