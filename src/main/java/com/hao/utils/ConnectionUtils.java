package com.hao.utils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @Describe com.hao.utils
 * @Auther wenhao chen
 * @CreateDate 2019/8/5
 * @Version 1.0
 *
 * 连接的工具类，从数据源中获取一个链接，并实现与线程的绑定
 */
public class ConnectionUtils {


    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取当前线程上的连接
     * @return
     */
    public Connection getThreadConnection(){
        try{
            //从ThreadLocal获取连接
            Connection conn = tl.get();
            //判断当前线程有无连接
            if(conn == null){
                //从数据源获取一个链接，存入ThreadLocal中
                conn = dataSource.getConnection();
                tl.set(conn);
            }
            //返回当前线程的连接
            return conn;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 解绑连接和线程
     */
    public void removeConnection(){
        tl.remove();
    }
}
