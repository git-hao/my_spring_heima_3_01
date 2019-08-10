package com.hao.service.impl;

import com.hao.dao.AccountDao;
import com.hao.dao.impl.AccountDaoImpl;
import com.hao.domain.Account;
import com.hao.service.AccountService;
import com.hao.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Describe com.hao.service.impl
 * @Auther wenhao chen
 * @CreateDate 2019/8/2
 * @Version 1.0
 */
@Service("accountService")
public class AccountServiceImpl_old implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransactionManager txm;




    public List<Account> findAllAccount() {

        try{
            //1,开启事务
            txm.beginTransaction();
            //2，执行操作
            List<Account> allAccount = accountDao.findAllAccount();
            //3，提交事务
            txm.commit();
            //4，返回结果
            return allAccount;
        }catch (Exception e){
            //5，回滚操作
            txm.rollback();
            throw new RuntimeException(e);
        }finally {
            //6，释放资源
            txm.release();
        }
    }

    public Account findAccountById(Integer id) {
        try{
            //1,开启事务
            txm.beginTransaction();
            //2，执行操作
            Account account = accountDao.findAccountById(id);
            //3，提交事务
            txm.commit();
            //4，返回结果
            return account;
        }catch (Exception e){
            //5，回滚操作
            txm.rollback();
            throw new RuntimeException(e);
        }finally {
            //6，释放资源
            txm.release();
        }
    }

    public void saveAccount(Account account) {
        try{
            //1,开启事务
            txm.beginTransaction();
            //2，执行操作
            accountDao.saveAccount(account);
            //3，提交事务
            txm.commit();
            //4，返回结果

        }catch (Exception e){
            //5，回滚操作
            txm.rollback();
        }finally {
            //6，释放资源
            txm.release();
        }
    }

    public void updateAccount(Account account) {
        try{
            //1,开启事务
            txm.beginTransaction();
            //2，执行操作
            accountDao.updateAccount(account);
            //3，提交事务
            txm.commit();
            //4，返回结果

        }catch (Exception e){
            //5，回滚操作
            txm.rollback();
        }finally {
            //6，释放资源
            txm.release();
        }
    }

    public void delateAccount(Integer id) {
        try{
            //1,开启事务
            txm.beginTransaction();
            //2，执行操作
            accountDao.delateAccount(id);
            //3，提交事务
            txm.commit();
            //4，返回结果

        }catch (Exception e){
            //5，回滚操作
            txm.rollback();
        }finally {
            //6，释放资源
            txm.release();
        }

    }

    public void transfer(String sourceName, String targetName, Float money) {

        try{
            //1,开启事务
            txm.beginTransaction();
            //2，执行操作
            //查询转出转入账户
            Account source = accountDao.findAccountByName(sourceName);
            Account target = accountDao.findAccountByName(targetName);
            //验证转出账户余额
            if (source.getMoney() > money){
                source.setMoney(source.getMoney() - money);
            }else {
                throw new RuntimeException("转出账户余额不足");
            }
            target.setMoney(target.getMoney() + money);
            //更新两个账户
            accountDao.updateAccount(source);
            accountDao.updateAccount(target);
            //3，提交事务
            txm.commit();
            //4，返回结果
        }catch (Exception e){
            //5，回滚操作
            txm.rollback();
            e.printStackTrace();
        }finally {
            //6，释放资源
            txm.release();
        }
    }

    public void setAccountDao(AccountDaoImpl accountDao) {
    }

    public void setTxm(TransactionManager txm) {
        this.txm = txm;
    }
}
