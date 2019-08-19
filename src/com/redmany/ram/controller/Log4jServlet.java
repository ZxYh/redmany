package com.redmany.ram.controller;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.File;

/**
 * Created by hy on 2017/8/6.
 */
@WebServlet(initParams={@WebInitParam(name="log4j", value="log4j.properties")})
public class Log4jServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        String path = config.getServletContext().getRealPath("WEB-INF\\classes");
        path  = path + File.separator + config.getInitParameter("log4j");
        PropertyConfigurator.configure(path);
    }
}
