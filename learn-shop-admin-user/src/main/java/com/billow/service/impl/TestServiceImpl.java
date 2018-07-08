package com.billow.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.billow.common.resData.BaseResponse;
import com.billow.dao.TestDao;
import com.billow.common.enums.ResCodeEnum;

import com.billow.pojo.po.TestPo;
import com.billow.service.TestService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.pojo.vo.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 测试用
 *
 * @author liuyongtao
 * @create 2018-02-11 9:46
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(TestVo test) {
        testDao.save(test);
    }

    @Override
    public void saveProcess(TestVo test) {
        testDao.save(test);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(TestVo test) {
        testDao.save(test);
    }

    @Override
    @TxTransaction
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse<TestVo> saveUser(TestVo testVo) {
        BaseResponse<TestVo> res = new BaseResponse<>(ResCodeEnum.OK);
        try {
            testVo.setName("billow");
            testVo.setAge(18);
            testVo.setUpdateTime(new Date());
            testVo.setCreateTime(new Date());
            TestPo testPo = ConvertUtils.convert(testVo, TestPo.class);
            testDao.save(testPo);
            res.setResData(testVo);
        } catch (Exception e) {
            e.printStackTrace();
            res.setResCode(ResCodeEnum.FAIL);
        }
        return res;
    }
}
