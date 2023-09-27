package Article.controller;

import Article.model.Member;
import Article.model.MemberRepository;

import java.util.Scanner;

public class MemberController {

    private Scanner scan = new Scanner(System.in);
    private MemberRepository memberRepository = new MemberRepository();

    private Member loginedMember = null;

    public void signup() {
        System.out.println("==== 회원 가입을 진행합니다 ====");
        System.out.print("아이디를 입력해주세요 : ");
        String loginId = scan.nextLine();
        System.out.print("비밀번호를 입력해주세요 : ");
        String loginPw = scan.nextLine();
        System.out.print("닉네임을 입력해주세요 : ");
        String nickname = scan.nextLine();

        memberRepository.insert(loginId, loginPw, nickname);
        System.out.println("==== 회원가입이 완료되었습니다. ====");
    }

    public void login() {
        System.out.print("아이디 : ");
        String loginId = scan.nextLine();
        System.out.print("비밀번호 : ");
        String loginPw = scan.nextLine();

        Member member = memberRepository.getMemberByLoginId(loginId);

        String failMsg = "잘못된 회원 정보입니다";


        if(member == null) {
            System.out.println(failMsg);
            return;
        }

        if(!member.getLoginPw().equals(loginPw)) {
            System.out.println(failMsg);
            return;
        }

        System.out.printf("%s님 환영합니다!\n", member.getNickname());
        loginedMember = member;

    }


    public Member getLoginedMember() {
        return loginedMember;
    }
}