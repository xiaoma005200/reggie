package com.xiaoma.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.reggie.entity.DishFlavor;
import com.xiaoma.reggie.mapper.DishFlavorMapper;
import com.xiaoma.reggie.service.DishFlavorService;
import com.xiaoma.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
