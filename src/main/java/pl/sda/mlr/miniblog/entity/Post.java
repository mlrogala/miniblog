package pl.sda.mlr.miniblog.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 200)
    private String title;
    @Column(name = "post_body", length = 4000)
    private String postBody;

    @Column(name = "added")
    private LocalDateTime added; //= LocalDateTime.now();

}