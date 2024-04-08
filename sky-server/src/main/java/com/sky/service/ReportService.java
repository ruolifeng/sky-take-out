package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 08/04/2024 10:58 AM
 */
@Service
public interface ReportService {
    TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end);
}
