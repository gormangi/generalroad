package com.generalroad.shop.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pagination {

    private int totalDataCnt;       // 총 데이터 갯수

    private int currentPage;        // 현재 페이지

    private int totalPageCnt;       // 전체 페이지 갯수

    private int viewDataCnt;        // 한 화면에 보여질 데이터 갯수

    private int viewPageCnt;        // 한 화면에 보여질 페이지 갯수

    private int firstPage;          // 페이지 리스트의 첫번째 번호

    private int lastPage;           // 페이지 리스트의 마지막 번호

    private int firstRecordIndex;   // sql 조건문

    private int lastRecordIndex;    // sql 조건문

    private boolean hasPrevPage;    // 이전 페이지 존재 여부

    private boolean hasNextPage;    // 다음 페이지 존재 여부

    public void setTotalDataCnt(int totalDataCnt) {
        this.totalDataCnt = totalDataCnt;
        if (totalDataCnt > 0) {
            calculation();
        }
    }

    private void calculation() {
        // 전체 페이지 수 (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장)
        totalPageCnt = ((totalDataCnt - 1) / this.getViewDataCnt()) + 1;
        if (this.getCurrentPage() > totalPageCnt) {
            this.setCurrentPage(totalPageCnt);
        }

        // 페이지 리스트의 첫 페이지 번호
        firstPage = ((this.getCurrentPage() - 1) / this.getViewPageCnt()) * this.getViewPageCnt() + 1;

        // 페이지 리스트의 마지막 페이지 번호 (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장)
        lastPage = firstPage + this.getViewPageCnt() - 1;
        if (lastPage > totalPageCnt) {
            lastPage = totalPageCnt;
        }

        // SQL의 조건절에 사용되는 첫 RNUM
        firstRecordIndex = ((this.getCurrentPage() - 1) * this.getViewDataCnt() + 1);

        // SQL의 조건절에 사용되는 마지막 RNUM
        lastRecordIndex = this.getCurrentPage() * this.getViewDataCnt();

        // 이전 페이지 존재 여부
        hasPrevPage = firstPage != 1;
        if(!hasPrevPage) {
            if(currentPage != firstPage) {
                hasPrevPage = true;
            }
        }

        // 다음 페이지 존재 여부
        hasNextPage = (lastPage * this.getViewDataCnt()) < totalPageCnt;
        if(!hasNextPage) {
            //마지막 페이지에서 현재페이지가 마지막 페이지가 아닌경우 next처리
            if(currentPage != lastPage) {
                hasNextPage = true;
            }
        }
    }

}
