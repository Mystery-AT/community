package com.lrj.community.dto;

import java.util.ArrayList;
import java.util.List;

//用于分页后的数据封装 每一页展示多少条
public class PaginationDTO {
    private List<QuestionDTO> questions;//首页列表数据
    private boolean showPrevious;//是否显示上一页的按钮
    private boolean showFirstPage;//是否显示第一页的按钮
    private boolean showNext;//是否显示下一页的按钮
    private boolean showEndPage;//是否显示最后一页的按钮
    private Integer page;//当前页码
    private List<Integer> pages = new ArrayList<>();//收集页码
    private Integer totalPage;//总页数

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "questions=" + questions +
                ", showPrevious=" + showPrevious +
                ", showFirstPage=" + showFirstPage +
                ", showNext=" + showNext +
                ", showEndPage=" + showEndPage +
                ", page=" + page +
                ", pages=" + pages +
                ", totalPage=" + totalPage +
                '}';
    }

    public void setPagination(Integer totalPage, Integer page) {
        //总页数
//        Integer totalPage;
        //如果总条数 % 每页的条数 = 0  就刚好可以分页不
        /*if (totalCount % size == 0) {
            //获得总页数
            this.totalPage = totalCount / size;
            //如果 != 0 就 +1 页
        } else {
            //获得总页数
            this.totalPage = totalCount / size + 1;
        }*/
        //
//        this.totalPage = totalPage;

        //如果当前传入的页码 <1 则默认第一页
        /*if (page < 1) {
            page = 1;
        }
        //如果传入的页码 > 总页数 则默认最后一页
        if (page > totalPage) {
            page = totalPage;
        }*/
        this.totalPage = totalPage;
        this.page = page;

        //存入一个页码，作为中间码
        pages.add(page);
        //向该页码前后 各添加 3 个页码 如果有则添加，一个页面显示7个页码
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        System.out.println(pages);

        //是否展示上一页的按钮，如果是第一页则不能上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //是否展示下一页的按钮，如果是最后一页则不能下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页的按钮，如果不是第一页可以直接点击跳转到第一页
        if (page == 1){
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //是否展示最后一页的按钮，如果不是最后一页可以直接点击调到最后一页
        if (page == totalPage){
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
