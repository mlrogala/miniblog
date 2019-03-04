package pl.sda.mlr.miniblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.mlr.miniblog.entity.Comment;
import pl.sda.mlr.miniblog.entity.Post;
import pl.sda.mlr.miniblog.entity.User;
import pl.sda.mlr.miniblog.form.NewPostForm;
import pl.sda.mlr.miniblog.repository.CommentRepository;
import pl.sda.mlr.miniblog.repository.PostRepository;
import pl.sda.mlr.miniblog.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public void addNewPost(NewPostForm newPostForm){
        Post post = new Post();
        post.setTitle(newPostForm.getTitle());
        post.setPostBody(newPostForm.getPostBody());
        post.setAdded(LocalDateTime.now());

        postRepository.save(post);
    }

    public Optional<Post> getSinglePost(Long postId){
        return postRepository.findById(postId);

//        Post one = null;
//        try {
//            one = postRepository.getOne(postId);
//        } catch (Exception e) {
//            return Optional.empty();
//        }
//        return Optional.ofNullable(one);
//        if(one == null){
//            return Optional.empty();
//        } else {
//            return Optional.of(one);
//        }

    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void addNewComment(Long postId, String userLogin, String commentBody) {
        Comment comment = new Comment();
        comment.setCommentBody(commentBody);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post not found"));

        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new RuntimeException("user not found"));

        comment.setPost(post);
        comment.setUser(user);

        comment.setAdded(LocalDateTime.now());

        commentRepository.save(comment);
    }
}


//mój kod


/*
@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public void addPost(PostForm postForm){
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setBody(postForm.getBody());
        post.setDateTime(LocalDateTime.now());

        postRepository.save(post);
    }

//    public Post showSinglePost(Long postId){
//        return postRepository.getById(Long.valueOf(postId));
//    }


    public Optional<Post> showPostWithOptional(Long postId){
/*      konwersja obiektu na Optionala:

        Optional.ofNullable(postRepository.getById(postId));        //konwertuje obiekt na Optionala (z pominięciem NullPointerException (którym sypie Optional.of() )
*/
/*
        return postRepository.findById(postId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void addNewComment(Long postId, String userLogin, String commentBody) {
        Comment comment = new Comment();
        comment.setBody(commentBody);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findByEmail(userLogin)
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setPost(post);
        comment.setUser(user);
        comment.setAdded(LocalDateTime.now());

        commentRepository.save(comment);
    }
}
*/