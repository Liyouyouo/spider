package com.jubensha.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jubensha.manager.dao.CouponMapper;
import com.jubensha.manager.model.entity.Coupon;
import com.jubensha.manager.service.CouponService;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {
}
