package com.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class OrnekServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("acildi");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("kapandi");
    }
}
