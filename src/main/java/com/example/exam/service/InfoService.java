package com.example.exam.service;

import org.springframework.stereotype.Service;

@Service
public class InfoService {

    public int getSum() {
        return (int) java.util.stream.LongStream
                .rangeClosed(1, 1_000_000)
                .parallel()
                .reduce(0, Long::sum);
    }
}
