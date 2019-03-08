package org.tyaa.java.portal.android;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Utils {
    public static String prepareString(String _string){
        try {
            return URLEncoder.encode(_string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String decodeString(String _string){
        try {
            return URLDecoder.decode(_string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
