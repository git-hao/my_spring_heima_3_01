package com.hao.service;

import com.hao.domain.Account;

import java.util.List;

/**
 * @Describe com.hao.service
 * @Auther wenhao chen
 * @CreateDate 2019/8/2
 * @Version 1.0
 */

public interface AccountService {

    //查询所有账号
    List<Account> findAllAccount();

    //根据id查找账号
    Account findAccountById(Integer id);

    //保存一个账号
    void saveAccount(Account account);

    //更新一个账号
    void updateAccount(Account account);

    //删除一个账号
    void delateAccount(Integer id);

    //转账
    void transfer(String sourceName,String targetName,Float money);
}
