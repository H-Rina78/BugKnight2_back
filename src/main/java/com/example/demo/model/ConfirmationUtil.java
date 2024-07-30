package com.example.demo.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

public class ConfirmationUtil {
	
	public static String encodeSessionId(String sessionId) {
        String encodeSessionId = "";
        try {
        	String encodedString = Base64.getEncoder().encodeToString(sessionId.getBytes());
            encodeSessionId = URLEncoder.encode(encodedString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeSessionId;
    }
	
	public static String decodeSessionId(String sessionId) {
        String decodeSessionId = "";
        try {
            String decodedString = URLDecoder.decode(sessionId, "UTF-8");
            String decodedURL = URLDecoder.decode(decodedString, "UTF-8");
            decodeSessionId = new String(Base64.getDecoder().decode(decodedURL));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeSessionId;
    }
}
