package pl.sda.mlr.miniblog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.mlr.miniblog.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiPostController {

    private PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostSummary>> getPosts(){
        List<PostSummary> postSummaryList = postService.getAllPosts().stream()
                .map(post -> new PostSummary(post.getId(), post.getTitle()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(postSummaryList);
    }

}
