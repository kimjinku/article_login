package Article.controller;

import Article.model.*;
import Article.view.ArticleView;
import util.Util;

import java.util.*;

public class ArticleController {

    private ArticleView articleView = new ArticleView();
    private ArticleRepository articleRepository = new ArticleRepository();
    private ReplyRepository replyRepository = new ReplyRepository();
    private MemberRepository memberRepository = new MemberRepository();

    private LikeRepository likeRepository = new LikeRepository();
    private Scanner scan = new Scanner(System.in);

    private Member loginedMember = null;

    public void add() {

        if (loginedMember == null) {
            System.out.println("로그인을 해야 글쓰기가 가능합니다.");
            return;
        }

        System.out.print("게시물 제목을 입력해주세요 : ");
        String title = scan.nextLine();
        System.out.print("게시물 내용을 입력해주세요 : ");
        String content = scan.nextLine();

        articleRepository.insert(title, content, loginedMember.getId());

        System.out.println("게시물이 등록되었습니다.");
    }

    public void list() {
        ArrayList<Article> articles = articleRepository.findAllArticles();
        articleView.printArticles(articles);
    }

    public void update() {

        System.out.print("수정할 게시물 번호 : ");
        int targetId = getParamInt(scan.nextLine(), -1);
        if (targetId == -1) {
            return;
        }
        Article article = articleRepository.findById(targetId);

        if (article == null) {
            System.out.println("없는 게시물입니다.");
        } else {
            System.out.print("제목 : ");
            String newTitle = scan.nextLine();
            System.out.print("내용 : ");
            String newContent = scan.nextLine();

            article.setTitle(newTitle);
            article.setContent(newContent);

            System.out.println("수정이 완료되었습니다.");
        }
    }

    public void delete() {
        System.out.print("삭제할 게시물 번호 : ");
        int targetId = getParamInt(scan.nextLine(), -1);

        if (targetId == -1) {
            return;
        }

        Article article = articleRepository.findById(targetId);

        if (article == null) {
            System.out.println("없는 게시물입니다.");
        } else {
            articleRepository.delete(article);
        }
    }

    public void detail() {
        // 중복 -> 함수
        if (isNotLogin())
            return;

        System.out.print("상세보기 할 게시물 번호를 입력해주세요 : ");
        int targetId = getParamInt(scan.nextLine(), -1);
        if (targetId == -1) {
            return;
        }
        Article article = articleRepository.findById(targetId);

        if (article == null) {
            System.out.println("존재하지 않는 게시물입니다.");
        } else {
            article.setHit(article.getHit() + 1);
            ArrayList<Reply> replies = replyRepository.getRepliesByArticleId(article.getId());
            Member member = memberRepository.getMemberById(article.getMemberId());
            Like like = likeRepository.getLikeByArticleIdAndMemeberId(article.getId(), loginedMember.getId());
            int likeCount = likeRepository.getCountOfLikeByArticleId(article.getId());

            articleView.printArticleDetail(article, member, replies, likeCount, like);
            doDetailProcess(article, member, replies);
        }
    }

    public void doDetailProcess(Article article, Member member, ArrayList<Reply> replies) {
        while (true) {
            System.out.print("상세보기 기능을 선택해주세요(1. 댓글 등록, 2. 추천, 3. 수정, 4. 삭제, 5. 목록으로) : ");
            int cmd = getParamInt(scan.nextLine(), -1);

            switch (cmd) {
                case 1:
                    addReply(article, member);
                    break;
                case 2:
                    checkLike(article, member, replies);
                    break;
                case 3:
                    updateMyArticle(article, member, replies);
                    break;
                case 4:
                    deleteMyArticle(article);
                    break;
                case 5:
                    System.out.println("목록으로 돌아갑니다.");
                    break;
            }

            if (cmd == 5) {
                break;
            }
        }
    }

    private void checkLike(Article article, Member member, ArrayList<Reply> replies) {
        //하나의 게시물에 한명의 유저가 체크 가능 -->어떤 게시물에 어떤 회원이 좋아요를 체크했는지 기억해야한다.
        //좋아요를  어떤 게시물에 누가, 언제 했는지 알아야한다.
        if (isNotLogin())
            return;
        likeRepository.getLikeByArticleIdAndMemeberId(article.getId(), loginedMember.getId());

        Like like = likeRepository.getLikeByArticleIdAndMemeberId(article.getId(), loginedMember.getId());


        if (like == null) {
            likeRepository.insert(article.getId(), loginedMember.getId());
            System.out.println("이 게시물을 좋아합니다.");


        } else {
            likeRepository.delete(like);
            System.out.println("해당 게시물의 좋아요를 해제합니다.");

        }
        int likeCount = likeRepository.getCountOfLikeByArticleId(article.getId());
        like = likeRepository.getLikeByArticleIdAndMemeberId(article.getId(), loginedMember.getId());
        articleView.printArticleDetail(article, member, replies, likeCount, like);

    }

    private void deleteMyArticle(Article article) {
        if (isNotLogin()) return;
        System.out.print("정말 게시물을 삭제하시겠습니까? (y/n) : ");
        String isAgree = scan.nextLine();
        if (isAgree.equals("y")) {
            articleRepository.delete(article);
            System.out.printf("홍길동님의 %d번 게시물을 삭제했습니다.\n", article.getId());
            list();
        }
    }

    private void updateMyArticle(Article article, Member member, ArrayList<Reply> replies) {
        if (isNotLogin()) return;
        System.out.print("새로운 제목 : ");
        String title = scan.nextLine();
        System.out.print("새로운 내용 : ");
        String body = scan.nextLine();

        articleRepository.update(article.getId(), title, body);
        Like like = likeRepository.getLikeByArticleIdAndMemeberId(article.getId(), loginedMember.getId());
        int likeCount = likeRepository.getCountOfLikeByArticleId(article.getId());
        articleView.printArticleDetail(article, member, replies, likeCount, like);
    }

    public void addReply(Article article, Member member) {
        if (isNotLogin()) return;
        System.out.print("댓글 내용 : ");
        String body = scan.nextLine();
        String regDate = Util.getCurrentDate();
        int articleId = article.getId();

        replyRepository.insert(articleId, body, regDate);

        System.out.println("댓글이 성공적으로 등록되었습니다.");
        ArrayList<Reply> replies = replyRepository.getRepliesByArticleId(article.getId());
        Like like = likeRepository.getLikeByArticleIdAndMemeberId(article.getId(), loginedMember.getId());
        int likeCount = likeRepository.getCountOfLikeByArticleId(article.getId());
        articleView.printArticleDetail(article, member, replies, likeCount, like);
    }

    public void search() {
        System.out.print("검색 키워드를 입력해주세요 : ");
        String keyword = scan.nextLine();
        ArrayList<Article> searchedArticles = articleRepository.findByTitle(keyword);
        articleView.printArticles(searchedArticles);
    }

    public boolean isNotLogin() {
        if (loginedMember == null) {
            System.out.println("로그인을 해주세요");
            return true;

        }
        return false;
    }


    public int getParamInt(String input, int defaulValue) {

        try {
            int num = Integer.parseInt(input);
            return num;

        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다.");
        }

        return defaulValue;
    }

    public Member getLoginedMember() {
        return loginedMember;
    }

    public void setLoginedMember(Member loginedMember) {
        this.loginedMember = loginedMember;
    }

    public void sort() {
        System.out.print("정렬 대상을 선택해주세요. (1. 번호,  2. 조회수) : ");
        int sortTarget = getParamInt(scan.nextLine(), -1);
        System.out.print("정렬 방법을 선택해주세요. (1. 오름차순,  2. 내림차순) : ");
        int sortType = getParamInt(scan.nextLine(), -1);
        ArrayList<Article> allArticles = articleRepository.findAllArticles();
        Collections.sort(allArticles,new SortFactory().getSort(sortTarget).setDirection(sortType));

        articleView.printArticles(allArticles);
    }//

    public void page() {
        ArrayList<Article> articles = articleRepository.findAllArticles();
        articleView.printArticles(articles);
        System.out.println("1,2,3,4,5");
        System.out.println("페이징 이동 기능을 선택 가능. (1 : 이전, 2 : 다음, 3 : 선택, 4 : 뒤로가기)");
        getParamInt(scan.nextLine(),-1);
    }

}

class Sort { //오름차순인지 내림차순인지
    protected int order = 1;

    Comparator<Article> setDirection(int direction) { //생성자,
        if (direction == 2) {
            order = -1;
        }

        return (Comparator<Article>)this;
    }

}

class SortFactory { //번호인지 조회수인지
    public Sort getSort(int sortTarget){
        if (sortTarget==1){ //sortTarget이 번호
            return new SortById();
        } else{
            return new SortByHit();

        }
    }
}

class SortById extends Sort implements Comparator<Article> { //리턴이 1 이면 바꾸고 -1이면 안바꾼다


    @Override
    public int compare(Article o1, Article o2) {
        if (o1.getId() > o2.getId()) {
            return order;
        }
        return -1 * order;
    }
}

class SortByHit extends Sort implements Comparator<Article> {


    @Override
    public int compare(Article o1, Article o2) {
        if (o1.getHit() > o2.getHit()) {
            return order;
        }
        return -1 * order;
    }

}