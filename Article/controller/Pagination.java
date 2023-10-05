package Article.controller;

import Article.model.ArticleRepository;

public class Pagination {
    private int currentPageNo = 1; //현재 페이지 번호
    private int itemsCountPerPage = 3; // 한번에 보여줄 게시물의 수
    private int pageCntPerBlock = 5; // 한번에 보여줄 게시물 블록의 수

    private int totalCount = 0; // 일단 기본값. 게시물의 총 수량

    public int getEndPageNo(){
        return getStartPageNo() +pageCntPerBlock -1;
    }

    public int getStartPageNo(){
        return  (getPageBlockNo()-1)*pageCntPerBlock+1;
    }

    public int getPageBlockNo(){
        return  (int)(Math.ceil((double)currentPageNo/pageCntPerBlock));
    }
    public double getPageCount(){
        return  (int)(Math.ceil((double)totalCount/itemsCountPerPage));
    }


    public int getStartIndex() {
        int startIndex = (currentPageNo - 1) * itemsCountPerPage;
        if (startIndex < 0) {
            startIndex = 1;
        }
        return startIndex;
    }

    public int getEndIndex() {
        int endIndex = getStartIndex() + 3;
        if (endIndex >= totalCount) {
            endIndex = totalCount;
        }
        return endIndex;

    }

    private ArticleRepository articleRepository;

    public void pagination() {
        articleRepository = new ArticleRepository();
        this.totalCount = articleRepository.getTotalArticleCount();

    }
}
