package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author: sunjianrong
 * @email: sunruolifeng@gmail.com
 * @date: 08/04/2024 10:53 AM
 */
@RestController
@RequestMapping("/admin/report")
@Api(tags = "数据统计")
public class ReportController {
    @Autowired
    ReportService reportService;

    @ApiOperation(value = "营业额统计")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverReport(
            @DateTimeFormat(pattern = "yyy-MM-dd")LocalDate begin,
            @DateTimeFormat(pattern = "yyy-MM-dd")LocalDate end) {
        System.out.println("///////////////////");
        TurnoverReportVO turnoverReportVO = reportService.getTurnoverReport(begin,end);
        return Result.success(turnoverReportVO);
    }
}
