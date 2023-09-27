package Article.model;

public class Reply {
    private int id; // 댓글 번호
    private int articleId; // 댓글이 달린 게시물 번호
    private String body; // 댓글 내용
    private String regDate; // 댓글 작성일

    public Reply(int id, int articleId, String body, String regDate) {
        this.id = id;
        this.articleId = articleId;
        this.body = body;
        this.regDate = regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}