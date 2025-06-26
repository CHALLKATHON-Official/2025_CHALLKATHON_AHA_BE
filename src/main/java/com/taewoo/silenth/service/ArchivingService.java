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

        // 👇 로깅 레벨 설정을 변경했으므로 이 로그는 이제 보여야 합니다.
        log.info("아카이빙 대상 게시글 수: {}", postsToArchive.size());

        if (postsToArchive.isEmpty()) {
            log.info("아카이빙할 게시글이 없습니다.");
            return;
        }

        // 👇 디버깅과 안정성을 위해 로직을 수정합니다.
        for (SilentPost post : postsToArchive) {
            try {
                // 1. 아카이빙 시작 로그
                log.info("Post ID: [{}] 아카이빙 시도...", post.getId());

                // 2. CollectiveEntry 생성을 먼저 시도하여 데이터 정합성을 확인합니다.
                CollectiveEntry entry = CollectiveEntry.builder()
                                            .originalPost(post)
                                            .build();
            
                // 3. CollectiveEntry를 먼저 저장합니다.
                collectiveEntryRepository.save(entry);

                // 4. 원본 게시글의 상태를 'archived'로 변경하고 저장합니다.
                // 이 작업이 가장 마지막에 위치해야, 위 과정 실패 시 롤백됩니다.
                post.archive(); 
                silentPostRepository.save(post);

                log.info("Post ID: [{}] 아카이빙 성공.", post.getId());

            } catch (Exception e) {
                // 5. 개별 게시글 처리 중 오류 발생 시, 어떤 게시글에서 무슨 오류가 났는지 정확히 로그로 남깁니다.
                log.error("!!! Post ID: [{}] 아카이빙 중 심각한 오류 발생 !!!", post.getId(), e);
            }
        }
    }
}