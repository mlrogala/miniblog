package pl.sda.mlr.miniblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.mlr.miniblog.entity.Post;
import pl.sda.mlr.miniblog.form.NewPostForm;
import pl.sda.mlr.miniblog.service.PostService;
import pl.sda.mlr.miniblog.service.UserContextService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    private PostService postService;
    private UserContextService userContextService;

    @Autowired
    public PostController(PostService postService, UserContextService userContextService) {
        this.postService = postService;
        this.userContextService = userContextService;
    }

    @GetMapping("/post/add")
    public String showAddNewPostForm(Model model){
        model.addAttribute("newPostForm", new NewPostForm());
        return "post/addNewPostForm";
    }

    @PostMapping("/post/add")
    public String handleNewPostForm(
            @ModelAttribute @Valid NewPostForm newPostForm, BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "post/addNewPostForm";
        }

        postService.addNewPost(newPostForm);

        return "redirect:/";
    }

    @GetMapping("/post")
//    public String showSinglePost(@RequestParam Long postId){
    public String showSinglePost(@RequestParam String postId, Model model){

        return prepareSinglePost(postId, model);
    }

    @GetMapping("/post/{postName},{postId}")
//    @GetMapping("/post/{postId}")
    public String showSinglePostByPath(@PathVariable String postId, Model model){

        return prepareSinglePost(postId, model);
    }

    @GetMapping("/posts")
    public String showAllPosts(Model model){
        List<Post> allPosts = postService.getAllPosts();

        model.addAttribute("posts", allPosts);

        return "post/showPosts";
    }


    @PostMapping("/post/{id}/comment/add")
    public String handleNewCommentForm(
            @PathVariable String id,
            @RequestParam String commentBody,
            @RequestParam String postId
    ){
        postService.addNewComment(Long.parseLong(id), userContextService.getLoggedAs(), commentBody);
        return "redirect:/post/," + postId;
    }




    private String prepareSinglePost(@RequestParam String postId, Model model) {
        Long postIdLong;
        try {
            postIdLong = Long.parseLong(postId);
        } catch (NumberFormatException e) {
            return "post/postNotFound";
        }

        boolean showCommentForm = userContextService.getLoggedAs() != null
                && userContextService.hasRole("ROLE_USER");

        model.addAttribute("showCommentForm", showCommentForm);

        Optional<Post> postOptional = postService.getSinglePost(postIdLong);
        if (postOptional.isPresent() == false) {
            return "post/postNotFound";
        }

        model.addAttribute("post", postOptional.get());
        return "post/showSinglePost";
    }
}

//mój kod:

/*
@Controller
public class PostController {
    private PostService postService;
    private UserContextService userContextService;

    @Autowired
    public PostController(PostService postService, UserContextService userContextService) {
        this.postService = postService;
        this.userContextService = userContextService;
    }

    @GetMapping("/post/add")
    public String showPostForm(Model model){
        model.addAttribute("postForm", new PostForm());
        return "post/postForm";
    }

    @PostMapping("/post/add")
    public String handlePostForm(
        @ModelAttribute @Valid PostForm postForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "post/postForm";
        }
        postService.addPost(postForm);
        return "redirect:/home";
    }

    @GetMapping("/post/{postId}")   //nie będzie parametrów requestu (?.....=......), ale to, co bedzie po "/" od razu przypisujemy do zmiennej postId
    public String showPostWithPath(Model model, @PathVariable String postId){
        return prepareSinglePost(model, postId);
    }


    @GetMapping("/post")
    public String showPost(Model model, @RequestParam String postId){
        return prepareSinglePost(model, postId);
    }

    @GetMapping("/posts")
    public String showAllPosts(Model model){
        model.addAttribute("posts", postService.getAllPosts());
        return "/post/showPosts";
    }

    @PostMapping("/post/{id}/comment/add")
    public String handleNewCommentForm(@PathVariable String id,
                                       @RequestParam String commentBody
//                                      , @RequestParam String postId
    ){
        postService.addNewComment(Long.valueOf(id), userContextService.getLoggedAs(), commentBody);

        return "redirect:/post/" + id;
    }

    private String prepareSinglePost(Model model, @RequestParam String postId) {
        Long postIdForSearching;
        try {
            postIdForSearching = Long.valueOf(postId);
        } catch (NumberFormatException e) {
            return "post/postNotFound";
        }

        boolean showCommentForm = userContextService.getLoggedAs() != null && userContextService.hasRole("ROLE_USER");

        model.addAttribute("showCommentForm", showCommentForm);

        Optional<Post> postOptional = postService.showPostWithOptional(postIdForSearching);
        if(postOptional.isPresent() == false) {
            return "post/postNotFound";
        }
        model.addAttribute("singlePost", postOptional.get());
        return "post/singlePost";
    }

}
*/