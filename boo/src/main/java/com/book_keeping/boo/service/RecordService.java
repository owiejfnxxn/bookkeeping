package com.book_keeping.boo.service;
import com.book_keeping.boo.Entity.Category;
import com.book_keeping.boo.Entity.Record;
import com.book_keeping.boo.Entity.Repository.RecordRepository;
import com.book_keeping.boo.commons.Bill_type;
import com.book_keeping.boo.controller.requestEntity.ExpenditurePutRequest;
import com.book_keeping.boo.controller.requestEntity.ExpenditureUpdateRequest;
import com.book_keeping.boo.controller.requestEntity.IncomePutRequest;
import com.book_keeping.boo.controller.requestEntity.IncomeUpdateRequest;
import com.book_keeping.boo.controller.response.EITableDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RecordService {
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    CategoryService categoryService;

    // 通过用户id和年份获取所有的记录
    public EITableDataResponse getAllRecordByUserIdAndYear(Integer userId, String year, Integer page, Integer size) {
        //将年份转换为时间区间
        String startTime = year + "-01-01";
        String endTime = year + "-12-31";
        System.out.println(" startTime : "+startTime);
        System.out.println(" endTime : "+endTime);
        // 通过用户id和时间区间获取所有的记录 分页获取
        return getEITableData(userId, startTime, endTime, page, size);
    }

    // 通过用户id和月份获取所有的记录
    public EITableDataResponse getAllRecordByUserIdAndMonth(Integer userId, String month, Integer page, Integer size) {
        //将月份转换为时间区间
        String startTime = month + "-01";
        String endTime = month + "-31";
        System.out.println(" startTime : "+startTime);
        System.out.println(" endTime : "+endTime);
        // 通过用户id和时间区间获取所有的记录 分页获取
        return getEITableData(userId, startTime, endTime, page, size);
    }

    // 通过用户id获取时间段的总收入/支出
    public Double TotalMoneyByUserIdAndBetweenDate(Integer userId, String startTime, String endTime, Integer type) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime2 = startTime+ " 00:00:00";
        String endTime2 =endTime+ " 23:59:59";
        return recordRepository.findTotalMoneyByUserIdAndDateBetweenAndBillType(userId,  LocalDateTime.parse(startTime2,df), LocalDateTime.parse(endTime2,df), type);
    }
    // 通过用户id和月份获取总支出
    public Double getTotalExpenditureMonth(Integer userId, String month) {
        //将月份转换为时间区间
        String startTime = month + "-01";
        String endTime = month + "-31";
        // 通过用户id和时间区间获取所有的记录
        return TotalMoneyByUserIdAndBetweenDate(userId, startTime, endTime, Bill_type.支出.getType());
    }
    // 通过用户id和年份获取总支出
    public Double getTotalExpenditureYear(Integer userId, String year) {
        //将年份转换为时间区间
        String startTime = year + "-01-01";
        String endTime = year + "-12-31";
        // 通过用户id和时间区间获取所有的记录
        return TotalMoneyByUserIdAndBetweenDate(userId, startTime, endTime, Bill_type.支出.getType());
    }

    // 通过用户id和时间区间获取所有的记录
    public EITableDataResponse getEITableData(Integer userId, String startTime, String endTime,Integer page, Integer size) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime2 = startTime+ " 00:00:00";
        String endTime2 =endTime+ " 23:59:59";
        System.out.println(" startTime : "+startTime2+" endTime : "+endTime2);
        Integer totalPage = recordRepository.getTotalRecordByUserId(userId, LocalDateTime.parse(startTime2,df), LocalDateTime.parse(endTime2,df));

        // 通过用户id和时间区间获取所有的记录 分页获取
         List<Record> records = recordRepository.findAllByUserIdAndDateBetween(userId, LocalDateTime.parse(startTime2,df), LocalDateTime.parse(endTime2,df),(page)*size,size);
         // 不分页获取
        // 通过用户id和时间区间获取总收入
        Double incomeTotal = recordRepository.findTotalMoneyByUserIdAndDateBetweenAndBillType(userId, LocalDateTime.parse(startTime2,df), LocalDateTime.parse(endTime2,df), Bill_type.收入.getType());
        // 通过用户id和时间区间获取总支出
        Double expenditureTotal = recordRepository.findTotalMoneyByUserIdAndDateBetweenAndBillType(userId,  LocalDateTime.parse(startTime2,df), LocalDateTime.parse(endTime2,df), Bill_type.支出.getType());

        return new EITableDataResponse(records, incomeTotal, expenditureTotal,totalPage);
    }

    // 添加记录
    public Record addRecord(IncomePutRequest incomePutRequest,Integer userId) {
        Record record = new Record(incomePutRequest,userId);
        Category idByUserIdAndName = categoryService.getIdByUserIdAndName(userId, incomePutRequest.getIncomeType());
        System.out.println("Service idByUserIdAndName:" + idByUserIdAndName);
        record.setCategoryId(Integer.parseInt(idByUserIdAndName.getCategoryId()));
        System.out.println("Service record:" + record);
        return recordRepository.save(record);
    }

    public Record addRecord(ExpenditurePutRequest expenditurePutRequest,Integer userId) {
        Record record = new Record(expenditurePutRequest, userId);
        Category idByUserIdAndName = categoryService.getIdByUserIdAndName(userId, expenditurePutRequest.getExpenditureType());
        System.out.println("Service idByUserIdAndName:" + idByUserIdAndName);
        record.setCategoryId(Integer.parseInt(idByUserIdAndName.getCategoryId()));
        System.out.println("Service record:" + record);
        return recordRepository.save(record);
    }

    // 修改记录
    public Record updateRecord(IncomeUpdateRequest incomeUpdateRequest, Integer userId) {
        Record record = new Record(incomeUpdateRequest,userId);
        System.out.println("Service record:" + record);
        recordRepository.deleteById(incomeUpdateRequest.getIncomeId());
        return recordRepository.save(record);
    }

    public Record updateRecord(ExpenditureUpdateRequest expenditureUpdateRequest, Integer userId) {
        Record record = new Record(expenditureUpdateRequest,userId);
        System.out.println("Service record:" + record);
        recordRepository.deleteById(expenditureUpdateRequest.getExpenditureId());
        return recordRepository.save(record);
    }

    // 删除记录
    public void deleteRecord(Integer id) {
        recordRepository.deleteById(id);
    }

    // 通过时间区间获取收入详情
    public EITableDataResponse getIncomeDetailsByTimeFrame(Integer userId,String sortType, String startTime, String endTime, Integer page, Integer size) {
        Integer bill_type = 1;
        return getEiTableDataResponse(userId, sortType, startTime, endTime, page, size, bill_type);
    }

    // 通过时间区间获取支出详情
    public EITableDataResponse getExpenditureDetailsByTimeFrame(Integer userId,String sortType, String startTime, String endTime, Integer page, Integer size) {
        Integer bill_type = 0;
        return getEiTableDataResponse(userId, sortType, startTime, endTime, page, size, bill_type);
    }

    // 通过时间区间获取收入详情
    private EITableDataResponse getEiTableDataResponse(Integer userId, String sortType, String startTime, String endTime, Integer page, Integer size, Integer bill_type) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        startTime = startTime + " 00:00:00";
        endTime = endTime + " 23:59:59";
        List<Record> allByUserIdAndDateBetweenAndBillType = null;
        Integer totalPage = null;
        if(sortType.equals("date")){
            allByUserIdAndDateBetweenAndBillType = recordRepository.findAllByUserIdAndDateBetweenAndBillTypeOrderByDateDesc(userId, bill_type, LocalDateTime.parse(startTime,df), LocalDateTime.parse(endTime,df), page * size, size);
            totalPage = recordRepository.getTotalRecordByUserIdAndDateBetweenAndBillType(userId,bill_type,LocalDateTime.parse(startTime,df), LocalDateTime.parse(endTime,df));
        }else{
            allByUserIdAndDateBetweenAndBillType = recordRepository.findAllByUserIdAndDateBetweenAndBillTypeOrderByMoneyDesc(userId, bill_type, LocalDateTime.parse(startTime,df), LocalDateTime.parse(endTime,df), page * size, size);
            totalPage = recordRepository.getTotalRecordByUserIdAndDateBetweenAndBillType(userId,bill_type,LocalDateTime.parse(startTime,df), LocalDateTime.parse(endTime,df));
        }
        return new EITableDataResponse(allByUserIdAndDateBetweenAndBillType,0.0,0.0,totalPage);
    }

    // 通过月份获取支出详情
    public EITableDataResponse getIncomeDetailsByMonth(Integer userId, String month, String sortType, Integer page, Integer size) {
        // 将月份转为时间段,再调用getIncomeDetailsByTimeFrame
        String startTime = month + "-01";
        String endTime = month + "-31";
        Integer bill_type = 1;
        return getIncomeDetailsByTimeFrame(userId, sortType, startTime, endTime, page, size);
    }

    // 通过月份获取支出详情
    public EITableDataResponse getExpenditureDetailsByMonth(Integer userId, String sortType, String month, Integer page, Integer size) {
        // 将月份转为时间段,再调用getExpenditureDetailsByTimeFrame
        String startTime = month + "-01";
        String endTime = month + "-31";
        Integer bill_type = 0;
        return getExpenditureDetailsByTimeFrame(userId, sortType, startTime, endTime, page, size);
    }

    // 通过年份获取支出详情
    public EITableDataResponse getIncomeDetailsByYear(Integer userId, String year, String sortType, Integer page, Integer size) {
        // 将年份转为时间段,再调用getIncomeDetailsByTimeFrame
        String startTime = year + "-01-01";
        String endTime = year + "-12-31";
        Integer bill_type = 1;
        return getIncomeDetailsByTimeFrame(userId, sortType, startTime, endTime, page, size);
    }

    // 通过年份获取支出详情
    public EITableDataResponse getExpenditureDetailsByYear(Integer userId, String year, String sortType, Integer page, Integer size) {
        // 将年份转为时间段,再调用getExpenditureDetailsByTimeFrame
        String startTime = year + "-01-01";
        String endTime = year + "-12-31";
        Integer bill_type = 0;
        return getExpenditureDetailsByTimeFrame(userId, sortType, startTime, endTime, page, size);
    }


    // 通过时间区间和类别id获取支出总额
    public Double getExpenditureTotalByTimeFrameAndCategoryId(Integer userId,
                                                              String startTime,
                                                              String endTime,
                                                              Integer categoryId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        startTime = startTime + " 00:00:00";
        endTime = endTime + " 23:59:59";
        return recordRepository.getTotalMoneyByUserIdAndDateBetweenAndCategoryId(userId, LocalDateTime.parse(startTime,df), LocalDateTime.parse(endTime,df), categoryId);
    }

    // 通过月份和类别id获取支出总额
    public Double getExpenditureTotalByMonthAndCategoryId(Integer userId, String month, Integer categoryId) {
        // 将月份转为时间段,再调用getIncomeDetailsByTimeFrame
        String startTime = month + "-01";
        String endTime = month + "-31";
        return getExpenditureTotalByTimeFrameAndCategoryId(userId, startTime, endTime, categoryId);
    }
    // 通过年份和类别id获取支出总额
    public Double getExpenditureTotalByYearAndCategoryId(Integer userId, String year, Integer categoryId) {
        // 将年份转为时间段,再调用getIncomeDetailsByTimeFrame
        String startTime = year + "-01-01";
        String endTime = year + "-12-31";
        return getExpenditureTotalByTimeFrameAndCategoryId(userId, startTime, endTime, categoryId);
    }

    public EITableDataResponse getAllRecordByUserIdAndCategory(Integer id, int categoryId) {
        List<Record> allByUserIdAndCategoryId = recordRepository.findAllByUserIdAndCategoryId(id, categoryId);
        return new EITableDataResponse(allByUserIdAndCategoryId,0.0,0.0,0);
    }
}
