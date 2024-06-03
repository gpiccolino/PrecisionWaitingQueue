package com.precisionpos.tv.waitingqueue.utils;

import com.google.gson.Gson;
import com.precisionpos.tv.waitingqueue.beans.TVWaitQueueResponseBean;

/**
 * Use GSON to parse order
 * @since 1/4/23
 * @author Kate
 */
public class TVWaitQueueResponseParser {

    public TVWaitQueueResponseParser() {}

    public TVWaitQueueResponseBean parse(String jsonResponseMessage) {

        try {
            Gson gson = new Gson();
            TVWaitQueueResponseBean responseBean = gson.fromJson(jsonResponseMessage, TVWaitQueueResponseBean.class);
            return responseBean;
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("JSON RESPONSE PARSE ERROR: " + e.getMessage());
            return null;
        }
    }
}
