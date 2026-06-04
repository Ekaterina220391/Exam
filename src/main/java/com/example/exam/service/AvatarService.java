package com.example.exam.service;

import com.example.exam.model.Avatar;
import com.example.exam.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public Page<Avatar> getAvatars(Pageable pageable) {
        return avatarRepository.findAll(pageable);
    }
}

