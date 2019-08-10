package test;

import com.hao.domain.Account;
import com.hao.service.AccountService;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Describe com.hao.test
 * @Auther wenhao chen
 * @CreateDate 2019/8/2
 * @Version 1.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class AccountTest {


    @Autowired
    @Qualifier("proxyAccountServie")
    private AccountService as;


    @Test
    public void transfer(){
        as.transfer("aaa","bbb",10f);
        List<Account> allAccount = as.findAllAccount();
        for (Account account:allAccount) {
            System.out.println(account);
        }
    }

}
