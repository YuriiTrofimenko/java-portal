/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.server.web.controller;

import org.tyaa.java.portal.model.JsonHttpResponse;

/**
 *
 * @author yurii
 */
public interface IController {
    
    JsonHttpResponse getAll(Object o);
    JsonHttpResponse get(Object o);
    JsonHttpResponse create(Object o);
    JsonHttpResponse update(Object o);
    JsonHttpResponse delete(Object o);
}
