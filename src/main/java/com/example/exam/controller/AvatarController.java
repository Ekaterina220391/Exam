package com.example.exam.controller;

import com.example.exam.model.Avatar;
import com.example.exam.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avatars")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;


    @GetMapping
    public ResponseEntity<Page<Avatar>> getAvatars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Avatar> avatars = avatarService.getAvatars(pageable);
        return ResponseEntity.ok(avatars);
    }
}
