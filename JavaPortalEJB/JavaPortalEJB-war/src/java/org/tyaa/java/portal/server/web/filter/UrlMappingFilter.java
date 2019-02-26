/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.server.web.filter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author yurii
 */
@WebFilter(urlPatterns = "/api/*", dispatcherTypes = {DispatcherType.REQUEST})
public class UrlMappingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("Filter");
        if (request instanceof HttpServletRequest) {
            String urlString = ((HttpServletRequest) request).getRequestURL().toString();
            //String queryString = ((HttpServletRequest) request).getQueryString();
            System.out.println(urlString);
            //System.out.println(queryString);
            if (urlString != null) {
                String[] queryStringParts = urlString.split("/");
                /*for (String queryStringPart : queryStringParts) {
                    System.out.println(queryStringPart);
                }*/
                /*System.out.println(((HttpServletRequest)request).getParameterMap().size());
                for (Map.Entry<String, String[]> entry : ((HttpServletRequest)request).getParameterMap().entrySet()) {
                    System.out.println(entry);
                }*/
                
                if (queryStringParts.length > 4 && queryStringParts[4].equals("api")) {
                    if (queryStringParts.length > 5) {
                        request.setAttribute("controller", queryStringParts[5]);
                        //System.out.println(queryStringParts[5]);
                        if (queryStringParts.length > 6) {
                            request.setAttribute("action", queryStringParts[6]);
                            //System.out.println(queryStringParts[6]);
                            if (queryStringParts.length > 7) {
                                request.setAttribute("id", queryStringParts[7]);
                                System.out.println(queryStringParts[7]);
                            }
                        }
                    }
                }/* else {
                
                    System.out.println("Redirect");
                    System.out.println(((HttpServletRequest) request).getContextPath()
                                    + "/index.html");
                    //RequestDispatcher rd = request.getRequestDispatcher("/index.html");
                    //rd.forward(request, response);
                    ((HttpServletResponse)response)
                            .sendRedirect(
                                    ((HttpServletRequest) request).getContextPath()
                                    + "/index.html"
                            );
                    return;
                }*/
                chain.doFilter(request, response);
            } 
        }
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
