package com.hao.service.impl;

import com.hao.dao.AccountDao;
import com.hao.domain.Account;
import com.hao.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Describe com.hao.service.impl
 * @Auther wenhao chen
 * @CreateDate 2019/8/2
 * @Version 1.0
 * 被动态代理的类，中的方法都是连接点
 * 被代理后增强的方法，是切入点，未被增强的不是切入点
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    public Account findAccountById(Integer id) {
        return accountDao.findAccountById(id);
    }

    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public void delateAccount(Integer id) {
            accountDao.delateAccount(id);
    }

    public void transfer(String sourceName, String targetName, Float money) {

        Account source = accountDao.findAccountByName(sourceName);
        Account target = accountDao.findAccountByName(targetName);
        //验证转出账户余额
        if (source.getMoney() > money){
            source.setMoney(source.getMoney() - money);
        }else {
            throw new RuntimeException("转出账户余额不足");
        }
        //测试事务
        //int i = 1/0;
        target.setMoney(target.getMoney() + money);
        //更新两个账户
        accountDao.updateAccount(source);
        accountDao.updateAccount(target);
        System.out.println("转账完毕");
    }


}
