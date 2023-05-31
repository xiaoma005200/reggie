package com.xiaoma.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.reggie.entity.Setmeal;
import com.xiaoma.reggie.mapper.SetmealMapper;
import com.xiaoma.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
