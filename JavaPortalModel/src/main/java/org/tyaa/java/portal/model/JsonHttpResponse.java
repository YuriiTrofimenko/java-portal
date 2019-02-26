/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.java.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author yurii
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonHttpResponse {
    
    public String status;
    public String message;
    public Object data;
    
    public JsonHttpResponse(){}

    public JsonHttpResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
}
