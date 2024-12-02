package com.example.trady.service;

import com.example.trady.entity.Buying;
import com.example.trady.entity.Member;
import com.example.trady.entity.Selling;

import java.util.List;

public interface BuyingService {

   Buying createBuyingWithUser(Long productId, Long productOptionId, Member user) throws Exception;
   List<Buying> getPurchasesByProduct(Long productId);
   List<Buying> findAllByUser(Member user);

}
