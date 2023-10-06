package Article.model;

import Article.controller.Pagination;

import util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Dao
// Repository
public class ArticleRepository {

    private ArrayList<Article> articles = new ArrayList<>();
    private int lastArticleId = 4;

    public ArticleRepository() {
        Article a1 = new Article(2, "안녕하세요 반갑습니다. 자바 공부중이에요.", "냉무", 1, Util.getCurrentDate());
        a1.setHit(10);
        Article a2 = new Article(3, "자바 질문좀 할게요~.", "냉무", 2, Util.getCurrentDate());
        a2.setHit(102);
        Article a3 = new Article(1, "정처기 따야되나요?", "냉무", 1, Util.getCurrentDate());
        a3.setHit(55);

        articles.add(a1);
        articles.add(a2);
        articles.add(a3);
    }

    public void insert(String title, String content, int memberId) {
        Article article = new Article(lastArticleId, title, content, memberId, Util.getCurrentDate());
        articles.add(article);
        lastArticleId++;
    }

    public void delete(Article article) {
        //articles.remove(i); // 위치 기반으로 삭제
        articles.remove(article);// 값 기반 삭제
    }

    public ArrayList<Article> findAllArticles() {
        return articles;
    }

    public Article findById(int id) {

        Article target = null;

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            if (id == article.getId()) {
                target = article;
            }
        }

        return target;
    }

    public ArrayList<Article> findByTitle(String keyword) {
        ArrayList<Article> searchedArticles = new ArrayList<>();

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            String title = article.getTitle();

            if (title.contains(keyword)) {
                searchedArticles.add(article);
            }
        }

        return searchedArticles;
    }

    public void update(int articleId, String title, String content) {
        Article article = findById(articleId);
        article.setTitle(title);
        article.setContent(content);
    }

    public int getTotalArticleCount() {
        return articles.size();
    }

    public ArrayList<Article> findPagedArticles(Pagination pagination) {
        ArrayList<Article> pagedArticles = new ArrayList<>();
        for (int i = pagination.getStartIndex(); i < pagination.getEndIndex(); i++) {
            pagedArticles.add(articles.get(i));

        }return pagedArticles;
    }
    public void sortArticles(int sortTarget,int sortType){

        Collections.sort(articles,new SortFactory().getSort(sortTarget).setDirection(sortType));
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