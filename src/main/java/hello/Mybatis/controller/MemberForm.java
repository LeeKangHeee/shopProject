package hello.Mybatis.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberForm {


    @NotEmpty(message="회원 이름은 필수 입니다")
    private String memberName;

    private String address;

    @NotEmpty(message="아이디 입력은 필수입니다.")
    private String loginId;

    @NotEmpty(message="패스워드 입력은 필수 입니다")
    private String LoginPass;

}
