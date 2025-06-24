package com.taewoo.silenth.controller;

import com.taewoo.silenth.web.dto.ErrorResponse;
import com.taewoo.silenth.web.dto.SilentPostCreateRequest;
import com.taewoo.silenth.web.dto.SilentPostCreateResponse;
import com.taewoo.silenth.service.SilentPostService;
import com.taewoo.silenth.web.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;


@Tag(name = "SilentPost", description = "감정 기록 API")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class SilentPostController {

    private final SilentPostService silentPostService;

    @PostMapping
    @Operation(
            summary = "감정 기록 작성",
            description = "사용자의 감정을 실시간으로 작성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "작성 성공",
                            content = @Content(schema = @Schema(implementation = SilentPostCreateResponse.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<com.taewoo.silenth.web.dto.ApiResponse<SilentPostCreateResponse>> createPost(
            @RequestBody @Valid SilentPostCreateRequest request,
            Authentication authentication
    ) {
        log.info("요청 본문: {}", request);

        User loginUer = (User) authentication.getPrincipal();
        SilentPostCreateResponse response = silentPostService.createPost(loginUer.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(com.taewoo.silenth.web.dto.ApiResponse.onSuccessWithData(response));
    }
}