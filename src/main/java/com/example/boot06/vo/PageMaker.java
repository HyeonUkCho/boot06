package com.example.boot06.vo;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = "pageList")
@Log
public class PageMaker<T> {

    private Page<T> result;

    private Pageable prevPage;
    private Pageable nextPage;

    private int currentPageNum;
    private int totalPageNum;

    private Pageable currentPage;

    private List<Pageable> pageList;

    public PageMaker(Page<T> result) {
        this.result = result;
        this.currentPage = result.getPageable();
        this.currentPageNum = currentPage.getPageNumber() + 1;
        this.totalPageNum = result.getTotalPages();
        this.pageList = new ArrayList<>();

        calcPages();
    }

    private void calcPages() {
        log.info("this.currentPageNum: " + this.currentPageNum);
        int tempEndNum = (int)(Math.ceil(this.currentPageNum/10.0)*10);

        log.info("tempEndNum: " + tempEndNum);
        int startNum = tempEndNum - 9;

        log.info("this.currentPage: " + this.currentPage);
        Pageable startPage = this.currentPage;
        log.info("startPage: " + startPage);

        // move to start Pageable
        for(int i=startNum; i<this.currentPageNum; i++) {
            Pageable tempPage = startPage.previousOrFirst();
            log.info("startPage.previousOrFirst(): " + tempPage);
            startPage = tempPage;
        }

        this.prevPage = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();

        log.info("tempEndNum: " + tempEndNum);
        log.info("totalPageNum: " + totalPageNum);

        if(this.totalPageNum < tempEndNum) {
            tempEndNum = this.totalPageNum;
            this.nextPage = null;
        }

        for(int i=startNum; i<=tempEndNum; i++) {
            log.info("pageList.add(startPage): " + startPage);
            pageList.add(startPage);
            startPage = startPage.next();
            log.info("startPage.next(): " + startPage);
        }
        log.info("pageList: " + pageList.toString());

        this.nextPage = startPage.getPageNumber() + 1 < totalPageNum ? startPage : null;
        log.info("nextPage: " + nextPage);
    }
}
