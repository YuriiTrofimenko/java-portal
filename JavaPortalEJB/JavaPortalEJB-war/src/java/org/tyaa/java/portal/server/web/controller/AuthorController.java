/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.server.web.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.json.JsonObject;
import org.json.simple.JSONObject;
import org.tyaa.java.portal.model.Author;
import org.tyaa.java.portal.model.JsonHttpResponse;
import org.tyaa.java.portal.server.web.Utils;
import org.tyaa.java.portal.server.web.service.AuthorService;

/**
 *
 * @author yurii
 */
@Stateless
public class AuthorController implements IController{
    
    private Gson mGson;
    
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
    
    public AuthorController(){
        mGson =
            (new GsonBuilder())
                .setDateFormat("dd.MM.yyyy")
                .create();
    }

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
        //
        System.out.println("json = " + (String)o);
        JSONObject data =
                (JSONObject)mGson.fromJson(
                        (String)o
                        , JSONObject.class
                );
        String authorJSONString = (String)data.get("author");
        Author author =
                (Author) mGson.fromJson(
                        authorJSONString
                        , Author.class
                );
        //author.setName(Utils.decodeString(author.getName()));
        //author.setAbout(Utils.decodeString(author.getAbout()));
        //System.out.println(Utils.decodeString(author.getName()));
        return mAuthorService.create(author);
    }

    @Override
    public JsonHttpResponse update(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JsonHttpResponse delete(Object _id) {
        return mAuthorService.delete(Integer.valueOf(_id.toString()));
    }
}
