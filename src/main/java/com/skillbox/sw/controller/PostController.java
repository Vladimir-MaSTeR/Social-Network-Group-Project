package com.skillbox.sw.controller;

import com.skillbox.sw.api.request.PostCommentApi;
import com.skillbox.sw.api.request.RequestPostApi;
import com.skillbox.sw.api.response.*;
import org.springframework.http.HttpStatus;
import com.skillbox.sw.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
public class PostController {

    private PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK) // поиск пубоикации
    public AbstractResponse searchPublication(@PathVariable String text, @RequestParam("date_from") long dateFrom,
                                              @RequestParam("date_to") long dateTo, @RequestParam("offset") int offset,
                                              @RequestParam("itemPerPage") int itemPerPage) {

        List<ResponsePostApi> responsePostApiList = service.searchPublication(text, dateFrom, dateTo, offset, itemPerPage);
        int total = responsePostApiList.size();

        return new PostListApi("Search completed successfully.", responsePostApiList, total, offset, itemPerPage);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // получение публикации по id
    public AbstractResponse receivingPublication(@PathVariable int postId) {

        return new ResponseApi("Post found successfully.", service.receivingPublication(postId));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)  // Редактирование публикации по ID
    public AbstractResponse editingPublication(@PathVariable int postId, @RequestParam("publish_date") long publishDate,
                                               @RequestBody RequestPostApi requestPostApi) {

        return new ResponseApi("Successful editing of the publication.", service.editingPublication(postId, publishDate, requestPostApi));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)  // Удаление текущего пользователя по ID
    public AbstractResponse deletePublication(@PathVariable int postId) {

        return new ResponseApi("Your post has been successfully deleted.",
                service.deletePublication(postId));
    }

    @PutMapping("/{id}/recover")
    @ResponseStatus(HttpStatus.OK)  // Восстановление публикации по ID
    public AbstractResponse recoveryPublication(@PathVariable int postId) {

        return new ResponseApi("Post restored.", service.recoveryPublication(postId));
    }

    //??????????????
    @GetMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.OK)  // Получение комментариев на публикации
    public AbstractResponse gettingCommentsPublication(@PathVariable int postId, @RequestParam("offset") int offset,
                                                       @RequestParam("itemPerPage") int itemPerPage) {

        List<CommentApi> commentApiList = service.gettingCommentsPublication(postId, offset, itemPerPage);
        int total = commentApiList.size();

        return new CommentListApi(commentApiList, total, offset, itemPerPage);
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.OK)  // Создание комментария к публикации
    public AbstractResponse createCommentsPublication(@PathVariable int postId, @RequestBody PostCommentApi postCommentApi) {

        return new ResponseApi("A comment to your post has been created.", service.createCommentsPublication(postId, postCommentApi));
    }

    @PutMapping("/{id}/comments/{comment_id}")
    @ResponseStatus(HttpStatus.OK)  // Редактирование комментария к публикации
    public AbstractResponse editingCommentPublication(@PathVariable int postId, @PathVariable int commentId, @RequestBody PostCommentApi postCommentApi) {

        return new ResponseApi("Comment edited.", service.editingCommentPublication(postId, commentId, postCommentApi));
    }

    @DeleteMapping("/{id}/comments/{comment_id}")
    @ResponseStatus(HttpStatus.OK)  // Удаление комментария к публикации
    public AbstractResponse deleteCommentPublication(@PathVariable int postId, @PathVariable int commentId) {

       return new ResponseApi("Comment has been deleted.", service.deleteCommentPublication(postId, commentId));
    }

    @PutMapping("/{id}/comments/{comment_id}/recover")
    @ResponseStatus(HttpStatus.OK)  // Восстановление комментария
    public AbstractResponse recoverComment(@PathVariable int postId, @PathVariable int commentId) {

        return new ResponseApi(" ", service.recoverComment(postId, commentId));
    }

    @PostMapping("/{id}/report")
    @ResponseStatus(HttpStatus.OK)  // Подать жалобу на публикацию
    public AbstractResponse reportPublication(@PathVariable int postId) {

        return new ResponseApi("The successful establishment of the complaint to the publication.", service.reportPublication(postId));
    }

    @PostMapping("/{id}/comments/{comment_id}/report")
    @ResponseStatus(HttpStatus.OK)  // Подать жалобу на комментарий к публикации
    public AbstractResponse reportComment(@PathVariable int postId, @PathVariable int commentId) {

        return new ResponseApi("The successful establishment of complaints review.", service.reportComment(postId, commentId));
    }

}
