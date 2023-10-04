package Article.model;


public class Like {
    public Like(int articleId, int memberId, String regDate) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.regDate = regDate;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setArticle(int articleId) {
        this.articleId = articleId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    private int articleId;

    private int memberId;

    private String regDate;
}


