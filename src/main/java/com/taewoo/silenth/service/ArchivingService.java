package com.taewoo.silenth.service;

import com.taewoo.silenth.repository.CollectiveEntryRepository;
import com.taewoo.silenth.repository.SilentPostRepository;
import com.taewoo.silenth.web.entity.CollectiveEntry;
import com.taewoo.silenth.web.entity.SilentPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArchivingService {

    private final SilentPostRepository silentPostRepository;
    private final CollectiveEntryRepository collectiveEntryRepository;

    @Transactional
    public void archiveOldPosts() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(7);
        List<SilentPost> postsToArchive = silentPostRepository.findByArchivedFalseAndConsentToArchiveTrueAndCreatedAtBefore(threshold);

        log.info("아카이빙할 게시글 수: {}", postsToArchive.size());

        if (postsToArchive.isEmpty()) {
            log.info("No posts to archive.");
            return;
        }

        // 👇 로직을 완전히 변경합니다.
        // 각 게시글을 순회하며 하나씩 처리합니다.
        for (SilentPost post : postsToArchive) {
            // 1. 원본 게시글의 상태를 먼저 'archived'로 변경합니다.
            post.archive(); 

            // 2. 변경된 원본 게시글을 기반으로 CollectiveEntry를 생성합니다.
            CollectiveEntry entry = CollectiveEntry.builder()
                                        .originalPost(post)
                                        .build();
            
            // 3. CollectiveEntry를 저장합니다.
            collectiveEntryRepository.save(entry);
        }
        
        // for문이 끝난 후 @Transactional에 의해 모든 변경사항이 최종 커밋됩니다.
    }
}