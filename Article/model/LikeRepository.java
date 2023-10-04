package Article.model;

import util.Util;

import java.util.ArrayList;

public class LikeRepository {
    ArrayList <Like> likes = new ArrayList<>();
    public void insert(int articleId, int memberId){
        Like like = new Like(articleId,memberId, Util.getCurrentDate());
        likes.add(like);

    }

    public Like getLikeByArticleIdAndMemeberId(int Articleid,int MemberId) {
        for(Like like : likes){
            if(like.getArticleId()==Articleid && like.getMemberId() == MemberId){
                return like;

            }

        }
        return null;
    }

    public void delete(Like like) {
        likes.remove(like);
    }
    public int getCountOfLikeByArticleId(int ArticleId){
        int count = 0;
        for(Like like : likes){
            if(like.getArticleId() == ArticleId){
                count++;

            }


        }return count;

    }
}
