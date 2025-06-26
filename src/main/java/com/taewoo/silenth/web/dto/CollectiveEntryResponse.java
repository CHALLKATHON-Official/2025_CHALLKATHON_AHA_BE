package com.taewoo.silenth.web.dto;

import com.taewoo.silenth.web.entity.CollectiveEntry;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays; // Arrays import 추가
import java.util.Collections; // Collections import 추가
import java.util.List;

@Getter
public class CollectiveEntryResponse {
    private final Long entryId;
    private final String content;
    private final int eraYear;
    private final int eraMonth;
    private final LocalDateTime originalCreatedAt;
    private final List<String> tags; // 👇 태그 필드 추가 (List<String>)

    public CollectiveEntryResponse(CollectiveEntry entry) {
        this.entryId = entry.getId();
        this.content = entry.getContent();
        this.eraYear = entry.getEraYear();
        this.eraMonth = entry.getEraMonth();
        this.originalCreatedAt = entry.getOriginalCreatedAt();
        // 👇 생성자에서 쉼표로 구분된 문자열 태그를 리스트로 변환합니다.
        this.tags = (entry.getTags() != null && !entry.getTags().isEmpty())
                ? Arrays.asList(entry.getTags().split(","))
                : Collections.emptyList();
    }
}