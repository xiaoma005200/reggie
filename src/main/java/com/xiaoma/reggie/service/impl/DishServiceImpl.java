package com.xiaoma.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.reggie.entity.Dish;
import com.xiaoma.reggie.mapper.DishMapper;
import com.xiaoma.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
