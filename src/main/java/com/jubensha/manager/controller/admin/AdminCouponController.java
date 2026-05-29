package com.jubensha.manager.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jubensha.manager.common.Result;
import com.jubensha.manager.model.entity.Coupon;
import com.jubensha.manager.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final CouponService couponService;

    @GetMapping
    public Result<Page<Coupon>> list(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(couponService.page(new Page<>(page, size)));
    }

    @PostMapping
    public Result<Coupon> create(@RequestBody Coupon coupon) {
        couponService.save(coupon);
        return Result.success(coupon);
    }

    @PutMapping("/{id}")
    public Result<Coupon> update(@PathVariable Long id, @RequestBody Coupon coupon) {
        coupon.setId(id);
        couponService.updateById(coupon);
        return Result.success(coupon);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        couponService.removeById(id);
        return Result.success();
    }
}
