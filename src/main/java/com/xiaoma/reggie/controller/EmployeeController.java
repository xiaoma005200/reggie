package com.xiaoma.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoma.reggie.common.R;
import com.xiaoma.reggie.entity.Employee;
import com.xiaoma.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public com.xiaoma.reggie.common.R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //@RequestBody 接收json数据
        //HttpServletRequest 登录成功将用户信息存到session一份 方便获取用户信息

        //1.将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);//数据库对username字段做了唯一性uniqe约束，可以使用getOne方法

        //3.如果没有查询到数据则返回登录失败结果
        if(emp == null){
            return R.error("登录失败");
        }

        //4.密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        //6.登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //1.清理Session中保存的当前员工的id
        request.getSession().removeAttribute("employee");

        //2.返回正确结果
        return R.success("退出成功");
    }

    /**
     * 新增员工信息
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工信息：{}",employee.toString());

        //1.设置员工初始密码123456并进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //2.设置员工创建时间和更新时间
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        //3.设置当前登录用户的id,从session中获取用到HttpServletRequest
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        //4.返回成功信息
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page = {}, pageSize = {}, name = {}",page,pageSize,name);
        //1.构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //2.构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件--------当前端传过来的name不为空的时候再去创建条件构造器
        //queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);   //isNotEmpty没有了
        if(name != null){
            queryWrapper.like(Employee::getName,name);
        }
        //3.添加排序条件--------按更新时间降序排列
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //4.执行查询操作
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息，通用方法（启用禁用，修改员工信息）
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());

        //Long empId = (Long)request.getSession().getAttribute("employee");
        //1.设置更新时间和更新这条记录的人
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);

        //2.执行更新操作
        employeeService.updateById(employee);
        return R.success("员工信息更新成功");
    }


    /**
     * 根据id修改员工信息第一步，根据id查询员工信息并回显到页面
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("获取到当前员工id:"+id);
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }



}
