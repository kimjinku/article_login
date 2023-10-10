package Article.controller;

import Article.model.ArticleRepository;


public class Pagination {
    private int currentPageNo = 1; //현재 페이지 번호
    private int itemsCountPerPage = 3; // 한번에 보여줄 게시물의 수
    private int pageCntPerBlock = 5; // 한번에 보여줄 게시물 블록의 수

    private int totalCount = 0; // 일단 기본값. 게시물의 총 수량?

    private ArticleRepository articleRepository;

    public int getEndPageNo() {
        int endPageNo = getStartPageNo() + pageCntPerBlock - 1;
        if (endPageNo >= getLastPageNo()) {
            endPageNo = getLastPageNo();
        }
        return endPageNo;
    }

    public int getStartPageNo() {
        return (getPageBlockNo() - 1) * pageCntPerBlock + 1;
    }

    public int getPageBlockNo() {
        return (int) (Math.ceil((double) currentPageNo / pageCntPerBlock));
    }
    // 현재 내가 보고있는 페이지 넘버

    public int getLastPageNo() {

        return (int) Math.ceil((double) getTotalCount() / itemsCountPerPage);

    }


    public int getStartIndex() {
        int startIndex = (currentPageNo - 1) * itemsCountPerPage;
        if (startIndex < 0) {
            startIndex = 1;
        }
        return startIndex;
    }

    public int getEndIndex() {
        int endIndex = getStartIndex() + itemsCountPerPage;
        if (endIndex >= getTotalCount()) {
            endIndex = getTotalCount();
        }
        return endIndex;

    }


    public Pagination() {
        articleRepository = new ArticleRepository();
        this.totalCount = getTotalCount();

    }

    public int getCurrentPageNo() {
        return currentPageNo;

    }

    public int getTotalCount() {
        return articleRepository.getTotalArticleCount();
    }

    public int getLastPageBlockNumber() {
        return (int) Math.ceil((double) getLastPageNo() / pageCntPerBlock);
    }

    public boolean hasNext() {
        if (getPageBlockNo() < getLastPageNo()) {
            return true;

        }
        return false;

    }
    public boolean hasPrevBlock() {
        if (getPageBlockNo() > 1) {
            return true;

        }
        return false;

    }

    public void prevPage() {
        currentPageNo--;
        if(currentPageNo<1)
            currentPageNo=1;

    }
    public void nextPage() {
        currentPageNo++;
        if(currentPageNo>getLastPageNo())
            currentPageNo=getLastPageNo();

    }

    public void selectPage(int pageNo) {
        if(pageNo>=1 || pageNo <= getLastPageNo()){
            currentPageNo =pageNo;

        }
    }
}
