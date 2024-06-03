package com.precisionpos.tv.waitingqueue.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Communication utility class for sending requests
 */
public class CommunicationUtils {

    public static final int HTTPMETHOD_POST 	= 1;
    public static final int HTTPMETHOD_GET 		= 2;
    public static final int HTTPMETHOD_PATCH 	= 3;
    public static final int HTTPMETHOD_PUT 		= 4;
    public static final int HTTPMETHOD_DELETE 	= 5;

    /**
     *
     * @param jsonRequest
     * @param endpointURL
     * @param httpMethod {1 = POST, 2 = GET, 3 = PATCH, 4 = PUT}
     * @return OrderAggregatorResponse
     *
     * @author Kate
     * @since 8/3/21
     */
    public static String sendRequest(String jsonRequest, String endpointURL, int httpMethod) {

        X509TrustManager trustAllCerts = new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
                return;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
                return;
            }
        };

        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, new TrustManager[] { trustAllCerts }, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Used to write output
        OutputStream outputStream = null;
        BufferedReader inputReader = null;

        String jsonResponseMessage = "";
        try {
            // Create the URL and authorization
            String charset = "UTF-8";

            URL url = new URL(endpointURL); // DD endpoint***

            //allowMethods("PATCH", "DELETE");

            // Create the connection
//			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            switch (httpMethod) {
                case 1: connection.setRequestMethod("POST");
                    break;

                case 2: connection.setRequestMethod("GET");
                    break;

                case 3: connection.setRequestMethod("PATCH");
                    connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                    break;

                case 4: connection.setRequestMethod("PUT");
                    break;

                case 5: connection.setRequestMethod("DELETE");
                    break;

                default: connection.setRequestMethod("POST");
                    break;
            }
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(180000);
            connection.setReadTimeout(180000);

            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // Send the payload only if there is one
            if(jsonRequest != null && jsonRequest.trim().length() > 0) {
                byte[] payLoad = jsonRequest.trim().getBytes("utf-8");

                // Write the output
                outputStream = connection.getOutputStream();
                outputStream.write(payLoad, 0, payLoad.length); // Replace with JSON
                // string***
                outputStream.flush();
            }


            // Get Response Message
            String responseMessage = connection.getResponseMessage();
            int responseCode = connection.getResponseCode();


            // POST response message
            System.out.println(responseMessage + " : " + responseCode); // For debug

            // FAILURE
            if (responseCode >= 500 && responseCode < 600) {
                StringBuilder inputError = new StringBuilder();
                inputReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                String inputLine = inputReader.readLine();
                while (inputLine != null) {
                    inputError.append(inputLine);
                    inputError.append("\n");
                    inputLine = inputReader.readLine();
                }
                System.out.println("Error JSON Response: " + inputError.toString()); // for debug
            }
            // SUCCESS
            else if(responseCode == 200 || responseCode == 201) {

                StringBuilder successMessage = new StringBuilder();
                inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine = inputReader.readLine();
                while (inputLine != null) {
                    successMessage.append(inputLine);
                    successMessage.append("\n");
                    inputLine = inputReader.readLine();
                }
                System.out.println("Success JSON Response: " + successMessage.toString()); // for debug
                jsonResponseMessage = successMessage.toString();
            }
            else {
                StringBuilder inputError = new StringBuilder();
                inputReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                String inputLine = inputReader.readLine();
                while (inputLine != null) {
                    inputError.append(inputLine);
                    inputError.append("\n");
                    inputLine = inputReader.readLine();
                }
                System.out.println("Error Message: " + inputError.toString()); // for debug
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the output stream
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Close the input stream
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Help out the GC
            jsonRequest =  null;
        }
        return jsonResponseMessage;
    }

    /**
     * Hack to allow PATCH method requests
     *
     * @param methods
     * @author Kate
     * @since 10/20/20
     */
//    private static void allowMethods(String... methods) {
//        try {
//            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
//
//            Field modifiersField = Field.class.getDeclaredField("modifiers");
//            modifiersField.setAccessible(true);
//            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);
//
//            methodsField.setAccessible(true);
//
//            String[] oldMethods = (String[]) methodsField.get(null);
//            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
//            methodsSet.addAll(Arrays.asList(methods));
//            String[] newMethods = methodsSet.toArray(new String[0]);
//
//            methodsField.set(null/*static field*/, newMethods);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new IllegalStateException(e);
//        }
//    }
}