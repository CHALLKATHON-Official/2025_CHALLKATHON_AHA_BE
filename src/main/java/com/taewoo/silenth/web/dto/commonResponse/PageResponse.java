package com.taewoo.silenth.web.dto.commonResponse;

import lombok.Getter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
public class PageResponse<T> {

    private final List<T> content;        // 실제 데이터 리스트
    private final int totalPages;       // 전체 페이지 수
    private final long totalElements;     // 전체 데이터 개수
    private final int pageNumber;       // 현재 페이지 번호
    private final int pageSize;         // 페이지 크기
    private final boolean isFirst;        // 첫 페이지 여부
    private final boolean isLast;         // 마지막 페이지 여부

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
    }
}