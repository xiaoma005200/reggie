package com.xiaoma.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.reggie.dto.DishDto;
import com.xiaoma.reggie.entity.Dish;
import com.xiaoma.reggie.entity.DishFlavor;
import com.xiaoma.reggie.mapper.DishMapper;
import com.xiaoma.reggie.service.DishFlavorService;
import com.xiaoma.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 自定义新增菜品功能，同时保存对应的菜品数据
     * 涉及到多张表的操作，加入事务控制注解@Transactional,在启动类添加使事务控制注解生效
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //1.保存菜品信息到菜品表,DishDto继承自Dish类，包含Dish的所有属性
        this.save(dishDto);

        //2.保存菜品口味数据到菜品口味表dishflavor，由于前端只在dishDto中封装了name,value属性，dishId需要专门进行封装
        //2.1获取菜品id
        Long dishId = dishDto.getId();
        //2.2获取菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        //2.3重新封装菜品口味
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //3.保存菜品口味集合到菜品口味表dishflavor
        dishFlavorService.saveBatch(flavors);
    }
}
