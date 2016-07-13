/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cummins.DBConexion;

import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author nc784
 */
public class ConexionOracleInterface {
    static DataSource dataSource;
    
    private ConexionOracleInterface(){
    }
    
    static{    
        ApplicationContext context1 = 
                    new ClassPathXmlApplicationContext("applicationContext.xml");
        dataSource=(DataSource)context1.getBean("dataSourceOracleInterface");
    }

    public static DataSource getDS(){
        return dataSource;
    }
}
