package com.xiaoma.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.reggie.entity.Employee;
import com.xiaoma.reggie.mapper.EmployeeMapper;
import com.xiaoma.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    //继承ServiceImpl 指定两个泛型,第一个是Mapper,第二个是实体
    //实现EmployeeService接口
}
