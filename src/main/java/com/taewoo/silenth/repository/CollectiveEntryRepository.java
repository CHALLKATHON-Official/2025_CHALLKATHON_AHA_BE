package com.taewoo.silenth.repository;

import com.taewoo.silenth.common.TimeSlot;
import com.taewoo.silenth.web.entity.CollectiveEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // List를 import 합니다.

public interface CollectiveEntryRepository extends JpaRepository<CollectiveEntry, Long> {
    // 👇 단수형(findByTimeSlot...)을 복수형(findByTimeSlotIn...)으로 변경하고, List를 받도록 수정합니다.
    Page<CollectiveEntry> findByTimeSlotInOrderByOriginalCreatedAtDesc(List<TimeSlot> timeSlots, Pageable pageable);
}