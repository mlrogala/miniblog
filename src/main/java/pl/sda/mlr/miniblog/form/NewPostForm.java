package pl.sda.mlr.miniblog.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewPostForm {
    @NotBlank
    @Size(min = 5)
    private String title;
    @NotBlank
    @Size(min = 10)
    private String postBody;

}