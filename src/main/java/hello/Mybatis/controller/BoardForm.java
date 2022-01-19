package hello.Mybatis.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class BoardForm {

    private Long boardId;

    @NotEmpty(message = "제목은 필수입니다.")
    private String boardTitle;

    @NotEmpty(message = "내용은 필수입니다.")
    private String boardContent;


}
