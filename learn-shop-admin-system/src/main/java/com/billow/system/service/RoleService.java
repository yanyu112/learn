package com.billow.system.service;

import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.RoleVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    /**
     * 通过用户id 查询用户角色信息
     *
     * @param userId 用户id
     * @return java.util.List<RoleVo>
     * @author LiuYongTao
     * @date 2018/11/5 16:19
     */
    List<RoleVo> findRoleListInfoByUserId(Long userId);

    /**
     * 根据条件查询角色列表信息
     *
     * @param roleVo
     * @return
     */
    Page<RolePo> findRoleByCondition(RoleVo roleVo) throws Exception;

    /**
     * 根据角色ID查询权限ID
     *
     * @param roleId 角色ID
     * @return java.util.List<java.lang.Long>
     * @author LiuYongTao
     * @date 2019/7/12 11:37
     */
    List<Long> findPermissionByRoleId(Long roleId) throws Exception;

    /**
     * 根据角色ID查询菜单ID
     *
     * @param roleId
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/7/12 15:17
     */
    List<String> findMenuByRoleId(Long roleId) throws Exception;

    /**
     * 保存角色信息、角色菜单和角色权限
     *
     * @param roleVo
     * @return void
     * @author LiuYongTao
     * @date 2019/7/12 17:50
     */
    void saveRole(RoleVo roleVo);
}
