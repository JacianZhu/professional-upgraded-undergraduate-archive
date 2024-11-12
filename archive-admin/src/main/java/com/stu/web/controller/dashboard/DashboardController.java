package com.stu.web.controller.dashboard;

import com.stu.common.core.domain.AjaxResult;
import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.domain.SysClass;
import com.stu.system.domain.SysCourierCompany;
import com.stu.system.domain.SysHeadTeacher;
import com.stu.system.service.ISysAdmissionInfoService;
import com.stu.system.service.ISysClassService;
import com.stu.system.service.ISysCourierCompanyService;
import com.stu.system.service.ISysHeadTeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ISysAdmissionInfoService sysAdmissionInfoService;

    @Autowired
    private ISysHeadTeacherService sysHeadTeacherService;

    @Autowired
    private ISysClassService sysClassService;

    @Autowired
    private ISysCourierCompanyService sysCourierCompanyService;


    @GetMapping("/getStatisticData")
    public AjaxResult getStatisticData() {
        Map<String, Integer> map = new HashMap<>(16);
        map.put("student", sysAdmissionInfoService.selectSysAdmissionInfoList(new SysAdmissionInfo()).size());
        map.put("headTeacher", sysHeadTeacherService.selectSysHeadTeacherList(new SysHeadTeacher()).size());
        map.put("class", sysClassService.selectSysClassList(new SysClass()).size());
        map.put("courierCompany", sysCourierCompanyService.selectSysCourierCompanyList(new SysCourierCompany()).size());
        return AjaxResult.success(map);
    }


    @GetMapping("/statisticsMajor")
    public AjaxResult statisticsMajor() {
        List<SysAdmissionInfo> sysAdmissionInfos = sysAdmissionInfoService.selectSysAdmissionInfoList(new SysAdmissionInfo());
        // 使用 Stream API 进行分组和统计
        Map<String, Long> specialtyCountMap = sysAdmissionInfos.stream()
                .collect(Collectors.groupingBy(SysAdmissionInfo::getAdmittedSpecialty, Collectors.counting()));

        // 提取专业名称和对应的数量
        List<String> specialties = new ArrayList<>(specialtyCountMap.keySet());
        List<Long> counts = new ArrayList<>(specialtyCountMap.values());

        // 创建结果 Map
        Map<String, List<?>> result = new HashMap<>(16);
        result.put("name", specialties);
        result.put("data", counts);
        return AjaxResult.success(result);
    }

    @GetMapping("/statisticsAdmissionSchool")
    public AjaxResult statisticsAdmissionSchool() {
        List<SysAdmissionInfo> sysAdmissionInfos = sysAdmissionInfoService.selectSysAdmissionInfoList(new SysAdmissionInfo());
        Map<String, List<SysAdmissionInfo>> groupedByYear = sysAdmissionInfos.stream()
                .collect(Collectors.groupingBy(info -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    return sdf.format(info.getAdmissionDate());
                }));
        // 提取并排序年份
        List<String> years = groupedByYear.keySet().stream()
                .sorted() // 对年份进行升序排序
                .collect(Collectors.toList());

        List<Long> counts = years.stream()
                .map(year -> (long) groupedByYear.get(year).size())
                .collect(Collectors.toList());

        // 创建结果 Map
        Map<String, List<?>> result = new HashMap<>(16);
        result.put("name", years);
        result.put("data", counts);


        return AjaxResult.success(result);
    }


}
