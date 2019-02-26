/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.server.web.controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import org.tyaa.java.portal.model.JsonHttpResponse;
import org.tyaa.java.portal.server.web.service.AuthorService;

/**
 *
 * @author yurii
 */
@Stateless
public class AuthorController implements IController{
    
    @EJB
    private AuthorService mAuthorService;
    
    /*public Object doAction(HttpServletRequest request){
    
        Object result = null;
        String action =
                request.getAttribute("action").toString();
        switch(action){
            case "get-all":{
                //result = new AuthorService().getAll();
                result = mAuthorService.getAll();
                break;
            }
        }
        return result;
    }*/

    @Override
    public JsonHttpResponse getAll(Object o) {
        
        return mAuthorService.getAll();
    }

    @Override
    public JsonHttpResponse get(Object id) {
        return mAuthorService.get(Integer.valueOf(id.toString()));
    }

    @Override
    public JsonHttpResponse create(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonHttpResponse update(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonHttpResponse remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
