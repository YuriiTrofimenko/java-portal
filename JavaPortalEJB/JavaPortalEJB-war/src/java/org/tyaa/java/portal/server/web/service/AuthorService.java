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
        List<org.tyaa.java.portal.server.ejb.entity.Author> authors =
                mAuthorFacade.findAll();
        List<Author> authorModels = null;
        if (authors != null) {
            authorModels = authors.stream().map((a) -> {
                return new Author(a.getId(), a.getName(), a.getAbout(), a.getStartedAt());
            }).collect(Collectors.toList());
        }
        return new JsonHttpResponse("", "", authorModels);
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
}
