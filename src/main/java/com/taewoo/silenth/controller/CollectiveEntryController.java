package com.taewoo.silenth.controller;

import com.taewoo.silenth.common.TimeSlot;
import com.taewoo.silenth.service.CollectiveEntryService;
import com.taewoo.silenth.web.dto.CollectiveEntryResponse;
import com.taewoo.silenth.web.dto.commonResponse.ApiResponse;
// PageResponse DTO를 import 해야 합니다.
import com.taewoo.silenth.web.dto.commonResponse.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
// Page는 이제 직접 사용하지 않으므로 import 문을 제거하거나 그대로 두어도 괜찮습니다.
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
    public ResponseEntity<ApiResponse<PageResponse<CollectiveEntryResponse>>> getTimeline(
            @Parameter(description = "조회할 시간대(들)") @RequestParam("timeSlots") List<TimeSlot> timeSlots,
            // 👇 required=false를 통해 tagName이 선택적 파라미터임을 명시합니다.
            @Parameter(description = "필터링할 태그 이름") @RequestParam(required = false) String tagName,
            @PageableDefault(size = 20, sort = "originalCreatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // 👇 서비스 호출 시 tagName을 함께 전달합니다.
        PageResponse<CollectiveEntryResponse> timelinePage = collectiveEntryService.getTimeline(timeSlots, tagName, pageable);
        return ResponseEntity.ok(ApiResponse.onSuccessWithData(timelinePage));
    }
}