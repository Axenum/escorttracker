package com.allits.escorttracker.rest;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpMessage;
import org.jsoup.Connection;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpHeaders;

import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Set;

import com.allits.escorttracker.common.Constants;

public class HeadersHelper {

    public static void addHeadersToConnection(Connection connection) {
        HttpHeaders headers = createHeadersWithSecurity();
        Set<String> headerKeys = headers.keySet();
        for (String headerKey : headerKeys) {
            connection.header(headerKey, headers.get(headerKey).get(0));
        }
    }

    public static void addHeadersToConnection(HttpURLConnection httpURLConnection) {
        HttpHeaders headers = createHeadersWithSecurity();
        Set<String> headerKeys = headers.keySet();
        for (String headerKey : headerKeys) {
            httpURLConnection.setRequestProperty(headerKey, headers.get(headerKey).get(0));
        }
    }

    public static void addHeadersToConnection(HttpMessage httpMessage) {
        HttpHeaders headers = createHeadersWithSecurity();
        Set<String> headerKeys = headers.keySet();
        for (String headerKey : headerKeys) {
            httpMessage.addHeader(headerKey, headers.get(headerKey).get(0));
        }
    }

    private static HttpHeaders createHeadersWithSecurity() {
        return new HttpHeaders() {
            {
                setAuthorization(new HttpAuthentication() {
                    @Override
                    public String getHeaderValue() {
                        String auth = Constants.HTTP_AUTH;
                        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                        String authHeader = "Basic " + new String(encodedAuth);
                        return authHeader;
                    }
                });
            }
        };
    }

}

