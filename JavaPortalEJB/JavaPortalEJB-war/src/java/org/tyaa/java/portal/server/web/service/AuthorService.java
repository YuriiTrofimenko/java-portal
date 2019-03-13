/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.server.web.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tyaa.java.portal.model.Author;
import org.tyaa.java.portal.model.JsonHttpResponse;
import org.tyaa.java.portal.server.ejb.session.AuthorFacade;

/**
 *
 * @author yurii
 */
@Stateless
public class AuthorService {

    @EJB
    private AuthorFacade mAuthorFacade;
    
    public JsonHttpResponse getAll(){
        
        try{
            List<org.tyaa.java.portal.server.ejb.entity.Author> authors =
                mAuthorFacade.findAll();

            List<Author> authorModels = null;
            if (authors != null) {

                authors.stream().forEach((a) -> {
                    //System.out.println(a.getStartedAt());
                    System.out.println(a.getStartedAt() != null ? a.getStartedAt() : "null");
                });
                authorModels = authors.stream().map((a) -> {
                    return new Author(a.getId(), a.getName(), a.getAbout(), a.getStartedAt());
                }).collect(Collectors.toList());
            }
            return new JsonHttpResponse("", "", authorModels);
        } catch(Exception ex){
            return new JsonHttpResponse(
                    "error"
                    , ex.getMessage() != null ? ex.getMessage() : "Unknown error"
                    , null
            );
        }
    }
    
    public JsonHttpResponse get(Integer _id){
        org.tyaa.java.portal.server.ejb.entity.Author author =
                mAuthorFacade.find(_id);
        Author authorModel = null;
        if (author != null) {
            authorModel =
                new Author(author.getId(), author.getName(), author.getAbout(), author.getStartedAt());
        }
        return new JsonHttpResponse("", "", authorModel);
    }
    
    public JsonHttpResponse create(Author _author){
        
        String result;
        org.tyaa.java.portal.server.ejb.entity.Author author =
                new org.tyaa.java.portal.server.ejb.entity.Author();
        author.setName(_author.getName());
        author.setAbout(_author.getAbout());
        try{
            System.out.println(author);
            mAuthorFacade.create(author);
            result = "created";
        }   catch(Exception ex){
            result = "error";
        }
        
        return new JsonHttpResponse(result, "", null);
    }
    
    public JsonHttpResponse delete(Integer _id){
        
        try {
            org.tyaa.java.portal.server.ejb.entity.Author author =
                mAuthorFacade.find(_id);
            
            if (author != null) {
                mAuthorFacade.remove(author);
                return new JsonHttpResponse("deleted", "", null);
            } else {
                return new JsonHttpResponse("error", "Record with id " + _id + " not exists", null);
            }
        } catch (Exception e) {
            return new JsonHttpResponse("error", "Unknown error", null);
        }
    }
}
