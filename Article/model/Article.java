package Article.model;

public class Article {

    // 게시물 번호
    private int id;
    // 제목
    private String title;
    // 내용
    private String content;

    private int memberId;

    // 조회수
    private int hit;

    private String regDate;

    public Article(int id, String title, String content, int memberId, String regDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.regDate = regDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

}