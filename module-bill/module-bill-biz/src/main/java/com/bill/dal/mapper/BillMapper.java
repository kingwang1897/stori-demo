package com.bill.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.bill.dal.dao.Bill;

@Component
@Mapper
public interface BillMapper {

    Bill selectBillById(Long id);
}
