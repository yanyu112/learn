package com.billow.service;

import com.billow.common.resData.BaseResponse;
import com.billow.pojo.vo.OrderVo;


/**
 * 订单处理
 *
 * @author liuyongtao
 * @create 2018-03-01 14:55
 */
public interface CoreOrderService {

    void sendOrderCar();

    void save(OrderVo OrderVo);

    /**
     * 用于测试远程调用用户系统带事务的
     *
     * @return
     */
    BaseResponse<OrderVo> saveUserAndOrder();

    BaseResponse<OrderVo> saveUserAndOrderTx();
}
