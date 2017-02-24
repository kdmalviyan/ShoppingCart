package com.kd.example.shoppingcart.dao;

import java.util.List;

import com.kd.example.shoppingcart.model.CartInfo;
import com.kd.example.shoppingcart.model.OrderDetailInfo;
import com.kd.example.shoppingcart.model.OrderInfo;
import com.kd.example.shoppingcart.model.PaginationResult;

public interface OrderDAO {

	public void saveOrder(CartInfo cartInfo);

	public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage);

	public OrderInfo getOrderInfo(String orderId);

	public List<OrderDetailInfo> listOrderDetailInfos(String orderId);

}