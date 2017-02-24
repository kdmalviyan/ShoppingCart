package com.kd.example.shoppingcart.dao;

import com.kd.example.shoppingcart.entity.Account;

public interface AccountDAO {
    
    Account findAccount(String username);
}
