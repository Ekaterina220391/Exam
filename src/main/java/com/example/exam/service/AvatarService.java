package com.example.exam.service;

import com.example.exam.model.Avatar;
import com.example.exam.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {


    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final AvatarRepository avatarRepository;

    public Page<Avatar> getAvatars(Pageable pageable) {

        logger.info("Was invoked method for get avatars (pagination)");


        logger.debug("Pagination details: page number = {}, page size = {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Avatar> avatarsPage = avatarRepository.findAll(pageable);


        logger.debug("Total elements found: {}, Total pages: {}",
                avatarsPage.getTotalElements(), avatarsPage.getTotalPages());

        return avatarsPage;
    }
}

