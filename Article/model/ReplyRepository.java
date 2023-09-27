package Article.model;

import java.util.ArrayList;

public class ReplyRepository {
    private ArrayList<Reply> replies = new ArrayList<>();
    private int lastReplyId = 1;

    public void insert(int articleId, String body, String regDate) {
        Reply reply = new Reply(lastReplyId, articleId, body, regDate);
        replies.add(reply);
        lastReplyId++;
    }

    public ArrayList<Reply> getRepliesByArticleId(int articleId) {
        ArrayList<Reply> searchedReplies = new ArrayList<>();
        for (Reply reply : replies) {
            if(reply.getArticleId() == articleId) {
                searchedReplies.add(reply);
            }
        }

        return searchedReplies;

    }
}