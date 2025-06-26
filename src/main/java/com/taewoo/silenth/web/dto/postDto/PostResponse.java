package com.taewoo.silenth.web.dto.postDto;

import com.taewoo.silenth.web.entity.SilentPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {

    private Long postId;
    private String content;
    private String authorNickname;
    private String authorProfileImageUrl; // ✨ 이 필드는 null일 수 있습니다.
    private int echoCount;
    private LocalDateTime createdAt;
    private boolean isEchoed;
    private List<String> tags;
    private boolean isAnonymous;
    private boolean consentToArchive;

    public static PostResponse from(SilentPost post) {
        boolean isAnonymous = post.isAnonymous();
        String nickname;
        String profileImageUrl;

        if (isAnonymous) {
            // 👇 익명일 경우, 닉네임은 "익명의 감정"으로, 프로필 URL은 null로 설정합니다.
            nickname = "익명의 감정";
            profileImageUrl = null;
        } else {
            // 👇 실명일 경우, 실제 사용자 정보와 프로필 이미지 URL을 사용합니다.
            nickname = post.getUser().getNickname();
            profileImageUrl = post.getUser().getProfileImageUrl();
        }

        return PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .authorNickname(nickname)
                .authorProfileImageUrl(profileImageUrl) // 분기 처리된 값이 전달됩니다.
                .echoCount(post.getEchos() != null ? post.getEchos().size() : 0)
                .createdAt(post.getCreatedAt())
                .isEchoed(post.getEchos().stream().anyMatch(echo -> echo.getUser().getId().equals(post.getUser().getId())))
                .tags(post.getEmotionTags().stream()
                        .map(tagLink -> tagLink.getEmotionTag().getTagName())
                        .collect(Collectors.toList()))
                .isAnonymous(isAnonymous)
                .consentToArchive(post.isConsentToArchive())
                .build();
    }
}