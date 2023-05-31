package com.xiaoma.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoma.reggie.common.CustomException;
import com.xiaoma.reggie.entity.Category;
import com.xiaoma.reggie.entity.Dish;
import com.xiaoma.reggie.entity.Setmeal;
import com.xiaoma.reggie.mapper.CategoryMapper;
import com.xiaoma.reggie.service.CategoryService;
import com.xiaoma.reggie.service.DishService;
import com.xiaoma.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前需要判断是否与菜品或者套餐关联
     * @param id
     */
    @Override
    public void remove(Long id) {
        //1.查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishqueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据id来查
        dishqueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishqueryWrapper);
        if(count1 > 0){
            //已关联菜品，抛出业务异常
            throw new CustomException("当前的分类关联了菜品，不能删除");
        }

        //2.查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据id来查
        setmealQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealQueryWrapper);
        if(count2 > 0){
            //已关联套餐，抛出业务异常
            throw new CustomException("当前的分类关联了套餐，不能删除");
        }

        //正常删除分类
        super.removeById(id);
    }
}
