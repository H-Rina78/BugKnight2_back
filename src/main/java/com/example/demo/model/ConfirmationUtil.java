package com.example.demo.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

public class ConfirmationUtil {
	
	public static String encodeSessionId(String sessionId) {
        String encodeSessionId = "";
        try {
        	System.out.println("sessionId" + sessionId);
        	String encodedString = Base64.getEncoder().encodeToString(sessionId.getBytes());
        	System.out.println("sessionId" + sessionId);
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
            System.out.println(decodedString);
            decodeSessionId = new String(Base64.getDecoder().decode(decodedString));
            System.out.println(decodeSessionId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeSessionId;
    }
}
