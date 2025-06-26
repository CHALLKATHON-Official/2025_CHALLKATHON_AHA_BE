package com.taewoo.silenth.service;

import com.taewoo.silenth.common.TimeSlot;
import com.taewoo.silenth.repository.CollectiveEntryRepository;
import com.taewoo.silenth.web.dto.CollectiveEntryResponse;
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

    // 👇 파라미터를 List<TimeSlot>으로 받도록 수정합니다.
    public Page<CollectiveEntryResponse> getTimeline(List<TimeSlot> timeSlots, Pageable pageable) {
        return collectiveEntryRepository
                .findByTimeSlotInOrderByOriginalCreatedAtDesc(timeSlots, pageable) // 수정된 Repository 메서드 호출
                .map(CollectiveEntryResponse::new);
    }
}