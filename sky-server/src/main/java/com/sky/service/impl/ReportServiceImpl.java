package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 08/04/2024 10:59 AM
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        while (begin != end) {
            // 计算指定日期的后一天日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        List<Double> turnoverList = new ArrayList<>();
        dateList.forEach(data -> {
            LocalDateTime localDateTime = LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime localDateTime1 = LocalDateTime.of(data, LocalTime.MAX);
            Map map = new HashMap();
            map.put("begin", localDateTime);
            map.put("end", localDateTime1);
            map.put("status", Orders.COMPLETED);
            Double turnOver = orderMapper.sumByMap(map);
            turnOver = turnOver == null ? 0.0 : turnOver;
            turnoverList.add(turnOver);
        });
        String join = StringUtils.join(dateList, ",");
        return TurnoverReportVO.builder()
                .dateList(join)
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }
}
