package com.taewoo.silenth.repository;

import com.taewoo.silenth.common.TimeSlot;
import com.taewoo.silenth.web.entity.CollectiveEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Query 어노테이션 import
import org.springframework.data.repository.query.Param; // Param 어노테이션 import
import java.util.List;

public interface CollectiveEntryRepository extends JpaRepository<CollectiveEntry, Long> {

    Page<CollectiveEntry> findByTimeSlotInOrderByOriginalCreatedAtDesc(List<TimeSlot> timeSlots, Pageable pageable);

    // 👇 태그 필터링을 위한 새 메서드를 추가합니다.
    @Query("SELECT ce FROM CollectiveEntry ce WHERE ce.timeSlot IN :timeSlots AND ce.tags LIKE %:tagName%")
    Page<CollectiveEntry> findByTimeSlotInAndTagsContainingOrderByOriginalCreatedAtDesc(
            @Param("timeSlots") List<TimeSlot> timeSlots,
            @Param("tagName") String tagName,
            Pageable pageable
    );
}