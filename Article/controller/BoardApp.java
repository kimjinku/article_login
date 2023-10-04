package Article.controller;

import Article.model.Member;

import java.util.Scanner;

public class BoardApp {

    ArticleController articleController = new ArticleController();
    MemberController memberController = new MemberController();
    Scanner scan = new Scanner(System.in);

    public void start() {
        while (true) {
            Member loginedMember = memberController.getLoginedMember();
            articleController.setLoginedMember(loginedMember);
            if (loginedMember == null) {
                System.out.print("명령어: ");
            } else {
                System.out.printf("명령어[%s(%s)]: ", loginedMember.getLoginId(), loginedMember.getNickname());
            }
            String command = scan.nextLine();
            if (command.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (command.equals("add")) {
                articleController.add();
            } else if (command.equals("list")) {
                articleController.list();
            } else if (command.equals("update")) {
                articleController.update();
            } else if (command.equals("delete")) {
                articleController.delete();
            } else if (command.equals("detail")) {
                articleController.detail();
            } else if (command.equals("search")) {
                articleController.search();
            } else if (command.equals("signup")) {
                memberController.signup();
            } else if (command.equals("login")) {
                memberController.login();
            } else if (command.equals("sort")){
                articleController.sort();
            } else if (command.equals("page")){
                articleController.page();
            }
        }
    }
}