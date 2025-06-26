package com.taewoo.silenth.service;

import com.taewoo.silenth.common.TimeSlot;
import com.taewoo.silenth.repository.CollectiveEntryRepository;
import com.taewoo.silenth.web.dto.CollectiveEntryResponse;
import com.taewoo.silenth.web.dto.commonResponse.PageResponse;
// 👇 여기서 'domain'을 'web.entity'로 수정했습니다.
import com.taewoo.silenth.web.entity.CollectiveEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CollectiveEntryService {

    private final CollectiveEntryRepository collectiveEntryRepository;

    public PageResponse<CollectiveEntryResponse> getTimeline(List<TimeSlot> timeSlots, String tagName, Pageable pageable) {
        Page<CollectiveEntry> page;

        if (tagName != null && !tagName.isBlank()) {
            // 태그 필터링이 있는 경우
            page = collectiveEntryRepository.findByTimeSlotInAndTagsContainingOrderByOriginalCreatedAtDesc(timeSlots, tagName, pageable);
        } else {
            // 태그 필터링이 없는 경우
            page = collectiveEntryRepository.findByTimeSlotInOrderByOriginalCreatedAtDesc(timeSlots, pageable);
        }

        // Page<CollectiveEntry>를 PageResponse<CollectiveEntryResponse>로 변환하여 반환합니다.
        return new PageResponse<>(page.map(CollectiveEntryResponse::new));
    }
}