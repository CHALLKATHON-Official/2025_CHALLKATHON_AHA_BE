package com.taewoo.silenth.controller;

import com.taewoo.silenth.common.TimeSlot;
import com.taewoo.silenth.service.CollectiveEntryService;
import com.taewoo.silenth.web.dto.CollectiveEntryResponse;
import com.taewoo.silenth.web.dto.commonResponse.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Collective Timeline", description = "공감 연대기 API")
@RestController
@RequestMapping("/api/v1/timeline")
@RequiredArgsConstructor
public class CollectiveEntryController {

    private final CollectiveEntryService collectiveEntryService;

    @GetMapping
    @Operation(summary = "공감 연대기 기록 조회", description = "특정 시간대의 아카이빙된 기록들을 최신순으로 조회")
    public ResponseEntity<ApiResponse<Page<CollectiveEntryResponse>>> getTimeline(
            // 👇 @RequestParam의 이름을 'timeSlots'(복수형)로 변경하고 List를 받도록 합니다.
            @Parameter(description = "조회할 시간대(들)") @RequestParam List<TimeSlot> timeSlots,
            @PageableDefault(size = 20, sort = "originalCreatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<CollectiveEntryResponse> timelinePage = collectiveEntryService.getTimeline(timeSlots, pageable);
        return ResponseEntity.ok(ApiResponse.onSuccessWithData(timelinePage));
    }
}