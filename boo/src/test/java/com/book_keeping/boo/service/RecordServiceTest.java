package com.book_keeping.boo.service;

import com.book_keeping.boo.Entity.Record;
import com.book_keeping.boo.Entity.Repository.RecordRepository;
import com.book_keeping.boo.controller.response.EITableDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RecordServiceTest {
    @Autowired
    RecordRepository recordRepository;

    public EITableDataResponse getAllRecordByUserIdAndCategoryTest() {
        List<Record> allByUserIdAndCategoryId = recordRepository.findAllByUserIdAndCategoryId(1, 1);
        return null;
    }
}