/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.server.web.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tyaa.java.portal.server.web.controller.IController;

/**
 *
 * @author yurii
 */
public class MainServlet extends HttpServlet {

    private Gson mGson;
    
    //@EJB
    //private AuthorFacade mAuthorFacade;
    
    //@EJB
    //private AuthorController mAuthorController;
    
    @Override
    public void init() throws ServletException {
        super.init();
        mGson =
            (new GsonBuilder())
                .setDateFormat("dd.MM.yyyy")
                .create();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /*response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DispatcherServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DispatcherServlet at " + request.getContextPath() + "</h1>");
            out.println("<div>Test injection: " + mAuthorFacade.count() + "</div>");
            out.println("<div>Test gson: " + mGson.toJson(mAuthorFacade.count()) + "</div>");
            out.println("</body>");
            out.println("</html>");
        }*/
        
        response.setContentType("application/json;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            System.out.println("Servlet");
            
            System.out.println(((HttpServletRequest)request).getParameterMap().size());
                for (Map.Entry<String, String[]> entry : ((HttpServletRequest)request).getParameterMap().entrySet()) {
                    System.out.println(entry);
                }
            
            Object result = new Object();
            String controller =
                    (request.getAttribute("controller") != null)
                    ? request.getAttribute("controller").toString()
                    : "";
            String action =
                    (request.getAttribute("action") != null)
                    ? request.getAttribute("action").toString()
                    : "getAll";
            Integer id =
                    (request.getAttribute("id") != null)
                    ? Integer.valueOf(request.getAttribute("id").toString())
                    : null;
            if (!controller.equals("")) {
                
                try {
                    InitialContext initialContext = new InitialContext();

                    controller =
                        controller.substring(0, 1).toUpperCase()
                        + controller.substring(1)
                        + "Controller";

                    IController controllerInstance =
                        (IController)initialContext
                                .lookup("java:global/JavaPortalEJB/JavaPortalEJB-war/" + controller);

                    Object args = null;
                    if (id != null) {
                        args = id;
                    } else if (request.getAttribute("data") != null) {
                        args = request.getAttribute("data");
                    }
                    System.out.println(controllerInstance.getClass()
                            .getMethod(action, Object.class).getName());
                    result =
                            controllerInstance.getClass()
                            .getMethod(action, Object.class)
                            .invoke(controllerInstance, args);
                    System.out.println(result);
                } catch (Exception ex) {
                    System.out.println("ERROR");
                    //System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
            
                System.out.println("Redirect");
                RequestDispatcher rd = request.getRequestDispatcher("/index.html");
                rd.forward(request, response);
            }
            /*switch(controller){
                case "author":{
                    
                    result = mAuthorController.doAction(request);
                }
            }*/
            //mGson.toJson(result, (java.lang.reflect.Type) Type.getType(result.getClass()));
            
            out.println(mGson.toJson(result));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
