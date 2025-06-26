package com.taewoo.silenth.web.entity;

import com.taewoo.silenth.common.TimeSlot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollectiveEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_post_id", unique = true)
    private SilentPost originalPost;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private int eraYear;

    @Column(nullable = false)
    private int eraMonth;

    @Column(nullable = false)
    private LocalDateTime originalCreatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeSlot timeSlot;

    // 👇 태그 정보를 저장할 필드를 추가합니다.
    @Column(nullable = false)
    private String tags;

    @Builder
    public CollectiveEntry(SilentPost originalPost) {
        this.originalPost = originalPost;
        this.content = originalPost.getContent();
        this.originalCreatedAt = originalPost.getCreatedAt();
        this.eraYear = originalPost.getCreatedAt().getYear();
        this.eraMonth = originalPost.getCreatedAt().getMonthValue();
        this.timeSlot = TimeSlot.Eof(originalPost.getCreatedAt().toLocalTime());
        // 👇 빌더 로직에 태그를 문자열로 변환하여 저장하는 코드를 추가합니다.
        this.tags = originalPost.getEmotionTags().stream()
                .map(tagLink -> tagLink.getEmotionTag().getTagName())
                .collect(Collectors.joining(","));
    }
}
