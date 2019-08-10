package com.hao.factory;

import com.hao.service.AccountService;
import com.hao.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Describe com.hao.factory
 * @Auther wenhao chen
 * @CreateDate 2019/8/8
 * @Version 1.0
 *
 * 创建service的代理对象的工厂
 *
 */
public class BeanFactory {

    private AccountService accountService;

    private TransactionManager txm;

    public final void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setTxm(TransactionManager txm) {
        this.txm = txm;
    }

    /**
     * 获取service代理对象
     * @return
     */
    public AccountService getAccountService() {
        //被织入增强后，return的Proxy类，就是代理对象
        return (AccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 添加对事务的支持
                     *
                     * @param proxy
                     * @param method
                     * @param args
                     * @return
                     * @throws Throwable
                     *
                     * 整个invoke方法的执行，就是环绕通知
                     */
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object resultValue = null;
                        try {
                            //1,开启事务，前置通知
                            txm.beginTransaction();
                            //2，执行操作，连接点，accountService即使被代理对象
                            resultValue = method.invoke(accountService, args);
                            //3，提交事务，后置通知
                            txm.commit();
                            //4，返回结果
                            return resultValue;
                        } catch (Exception e) {
                            //5，回滚操作，异常通知
                            txm.rollback();
                            throw new RuntimeException(e);
                        } finally {
                            //6，释放资源，最终通知
                            txm.release();
                        }
                    }
                });
    }

}
