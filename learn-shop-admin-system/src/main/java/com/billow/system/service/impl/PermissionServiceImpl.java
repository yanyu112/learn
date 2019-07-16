package com.billow.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.billow.common.base.DefaultSpec;
import com.billow.system.dao.PermissionDao;
import com.billow.system.dao.RolePermissionDao;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.service.PermissionService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Set<PermissionPo> findPermissionByRole(RolePo rolePo) {

        Set<PermissionPo> permissionPos = new HashSet<>();

        // 查询权限信息
        List<RolePermissionPo> rolePermissionPos = rolePermissionDao.findByRoleIdIsAndValidIndIsTrue(rolePo.getId());
        if (CollectionUtils.isEmpty(rolePermissionPos)) {
            logger.warn("角色：{}，未分配权限！", rolePo.getRoleName());
            return permissionPos;
        }

        rolePermissionPos.stream().forEach(rp -> {
            Optional<PermissionPo> permissionPo = permissionDao.findByIdAndValidIndIsTrue(rp.getPermissionId());
            if (!permissionPo.isPresent()) {
                logger.warn("角色：{}，permissionId:{},未查询到信息！", rolePo.getRoleName(), rp.getId());
                return;
            }
            permissionPos.add(permissionPo.get());
        });

        return permissionPos;
    }

    @Override
    public Page<PermissionVo> findPermissionList(PermissionVo permissionVo) {
        PermissionPo convert = ConvertUtils.convert(permissionVo, PermissionPo.class);
        DefaultSpec<PermissionPo> defaultSpec = new DefaultSpec<>(convert);
        Pageable pageable = new PageRequest(permissionVo.getPageNo(), permissionVo.getPageSize());
        Page<PermissionVo> permissionVos = permissionDao.findAll(defaultSpec, pageable).map(this::convertToPermissionVo);
        return permissionVos;
    }

    private PermissionVo convertToPermissionVo(PermissionPo permissionPo) {
        PermissionVo convert = ConvertUtils.convert(permissionPo, PermissionVo.class);
        String systemModule = convert.getSystemModule();
        if (ToolsUtils.isNotEmpty(systemModule)) {
            ArrayList<String> list = CollectionUtil.newArrayList(systemModule.split(","));
            convert.setSystemModules(list);
        }
        return convert;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PermissionVo deletePermissionById(Long id) {
        PermissionPo permissionPo = permissionDao.findOne(id);
        permissionDao.delete(id);
        return ConvertUtils.convert(permissionPo, PermissionVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePermission(PermissionVo permissionVo) {
        PermissionPo convert = ConvertUtils.convert(permissionVo, PermissionPo.class);
        permissionDao.save(convert);
        ConvertUtils.convert(convert, permissionVo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePermission(PermissionVo permissionVo) {
        this.savePermission(permissionVo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PermissionVo prohibitPermissionById(Long id) {
        PermissionPo permissionPo = permissionDao.findOne(id);
        permissionPo.setValidInd(false);
        permissionDao.save(permissionPo);
        return ConvertUtils.convert(permissionPo, PermissionVo.class);
    }

    @Override
    public List<PermissionVo> findPermissionAll() {
        PermissionPo permissionPo = new PermissionPo();
        permissionPo.setValidInd(true);
        DefaultSpec<PermissionPo> defaultSpec = new DefaultSpec<>(permissionPo);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<PermissionPo> permissionPos = permissionDao.findAll(defaultSpec, sort);
        List<PermissionVo> permissionVos = new ArrayList<>();
        permissionPos.stream().forEach(f -> {
            permissionVos.add(this.convertToPermissionVo(f));
        });
        return permissionVos;
    }
}