package com.xiaoma.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoma.reggie.dto.DishDto;
import com.xiaoma.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品功能，同时插入菜品口味数据，设计到dish,dishflavor两张表
    public void saveWithFlavor(DishDto dishDto);
}
