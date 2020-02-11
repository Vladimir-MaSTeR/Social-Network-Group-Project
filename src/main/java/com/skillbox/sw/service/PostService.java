package com.skillbox.sw.service;

import com.skillbox.sw.api.request.PostCommentApi;
import com.skillbox.sw.api.request.RequestPostApi;
import com.skillbox.sw.api.response.AbstractResponse;
import com.skillbox.sw.api.response.CommentApi;
import com.skillbox.sw.api.response.ListResponseApi;
import com.skillbox.sw.api.response.ResponsePostApi;
import com.skillbox.sw.domain.Message;
import com.skillbox.sw.domain.Person;
import com.skillbox.sw.domain.Post;
import com.skillbox.sw.domain.PostComment;
import com.skillbox.sw.repository.MessageRepository;
import com.skillbox.sw.repository.PostCommentRepository;
import com.skillbox.sw.repository.PostRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private PostRepository postRepository;
    private PostCommentRepository commentRepository;
    private MessageRepository messageRepository;

    public PostService(PostRepository postRepository, PostCommentRepository commentRepository, MessageRepository messageRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.messageRepository = messageRepository;
    }
    // поиск пубоикации
    public List<ResponsePostApi> searchPublication(String text, long dateFrom, long dateTo, int offset, int itemPerPage) {

        List<Post> allPosts = postRepository.findAll();
        if (allPosts.isEmpty()) throw new EntityNotFoundException("Not Found");

        List<ResponsePostApi> responsePostApiList = new ArrayList<>();
        Iterator<Post> iterator = allPosts.iterator();


        Iterable<Post> postIterable = postRepository.findAll();

        for (Post post : postIterable) {

            if (post.getPostText().contains(text)) {
//                return new ResponseEntity(post, HttpStatus.OK);
            }

        }

//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return null;
    }

    // получение публикации по id
    public AbstractResponse receivingPublication(int postId) {

        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
//        return new ResponseEntity(postOptional.get(), HttpStatus.OK);
        return null;
    }

    // Редактирование публикации по ID
    public AbstractResponse editingPublication(int postId, long publishDate, RequestPostApi requestPostApi) {

        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            Post editPost = postOptional.get();
            if (!editPost.isBlocked() && editPost.getId() == postId) {
            }
        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return null;
    }

    // Удаление текущего пользователя по ID
    public AbstractResponse deletePublication(int postId) {

        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else  {
            Post deletePost = postOptional.get();
            deletePost.setBlocked(true);
//            return new ResponseEntity(postOptional.get(), HttpStatus.OK);
        }
        return null;
    }

    // Восстановление публикации по ID
    public AbstractResponse recoveryPublication(int postId) {

        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            Post recoveryPost = postOptional.get();
            recoveryPost.setBlocked(false);
//            return new ResponseEntity(postOptional.get(), HttpStatus.OK);
        }
        return null;
    }

    // Получение комментариев на публикации
    public List<CommentApi> gettingCommentsPublication(int postId, int offset, int itemPerPage) {

        Iterable<PostComment> postComment = commentRepository.findAll();
        ArrayList<PostComment> commentArrayList = new ArrayList<>();
        for (PostComment comment : postComment) {
            if (comment.getId() == postId) {
                commentArrayList.add(comment);
            }
        }
//        return new ResponseEntity(commentArrayList, HttpStatus.OK);
        return null;
    }

    // Создание комментария к публикации
    public AbstractResponse createCommentsPublication (int postId, PostCommentApi postCommentApi) {

        Optional<Post> postOptional = postRepository.findById(postId);
        if (!postOptional.isPresent()) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            Post createComment = postOptional.get();
            if (!createComment.isBlocked()) {
                PostComment comment = new PostComment();
//                comment.setPostId(postId);
//                comment.setParentId(parentId);
//                comment.setCommentText(commentText);
                commentRepository.save(comment);
//                return new ResponseEntity(postOptional.get(), HttpStatus.OK);
            }
        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return null;
    }

    // Редактирование комментария к публикации
    public AbstractResponse editingCommentPublication(int commentId, int postId, PostCommentApi postCommentApi) {

        Optional<PostComment> postCommentOptional = commentRepository.findById(commentId);
        if (!postCommentOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            PostComment comment = postCommentOptional.get();
            if (!comment.isBlocked() && comment.getId() == commentId && comment.getId() == postId) {
//                comment.setParentId(parentId);
//                comment.setCommentText(commentText);
//                return new ResponseEntity(postCommentOptional.get(), HttpStatus.OK);
            }
        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return null;
    }

    // Удаление комментария к публикации
    public AbstractResponse deleteCommentPublication (int postId, int commentId) {

        Optional<PostComment> optionalPostComment = commentRepository.findById(commentId);
        if (!optionalPostComment.isPresent()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            PostComment deleteComment = optionalPostComment.get();
            if (deleteComment.getId() == postId) {
                deleteComment.setBlocked(true);
//                return new ResponseEntity(optionalPostComment.get(), HttpStatus.OK);
            }
        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        return null;
    }

    // Восстановление комментария
    public AbstractResponse recoverComment( int postId, int commentId) {

        Optional<PostComment> postCommentOptional = commentRepository.findById(commentId);
        if (!postCommentOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            PostComment recoveryComment = postCommentOptional.get();
            if (recoveryComment.getId() == postId) {
                recoveryComment.setBlocked(false);
//                return new ResponseEntity(postCommentOptional.get(), HttpStatus.OK);
            }
        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return null;
    }

    // Подать жалобу на публикацию
    public AbstractResponse reportPublication(int postId) {

        if (!postRepository.findById(postId).isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {

            Message messageReportPost = new Message();
//            messageReportPost.setMessageText(textReport);
            messageRepository.save(messageReportPost);
//            return new ResponseEntity(postRepository.findById(postId).get(), HttpStatus.OK);
        }
        return null;
    }

    // Подать жалобу на комментарий к публикации
    public AbstractResponse reportComment(int postId, int commentId) {

        if (!commentRepository.findById(commentId).isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            Message messageReportComment = new Message();
//            messageReportComment.setMessageText(textReport);
            messageRepository.save(messageReportComment);
//            return new ResponseEntity(commentRepository.findById(commentId).get(), HttpStatus.OK);
        }
        return null;

    }

}
