package com.jubensha.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jubensha.manager.model.dto.LoginRequest;
import com.jubensha.manager.model.entity.User;
import com.jubensha.manager.model.vo.DmInfoVO;
import com.jubensha.manager.model.vo.LoginVO;

import java.util.List;

public interface UserService extends IService<User> {

    LoginVO login(LoginRequest request);

    User getByUsername(String username);

    void updatePassword(Long userId, String oldPwd, String newPwd);

    /** 按角色统计用户数量 */
    long countByRole(String role);

    /** 查询所有DM及其绩效信息 */
    List<DmInfoVO> listAllDmsInfo();
}
