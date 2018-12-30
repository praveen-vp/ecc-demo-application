package com.msf.ecc.eccdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msf.ecc.eccdemo.models.BaseModel;
import com.msf.ecc.eccdemo.services.GenerateKeyPairService;
import com.msf.ecc.eccdemo.services.imp.GenerateECKeyPairServiceImp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICallHelper {


    public static void makeTestServiceApiCall() throws Exception {

        String apiUrl = "http://localhost:8080/testService";

        URL url = new URL(apiUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();

        System.out.println(" status " + httpURLConnection.getResponseCode());

        String response = readResponse(httpURLConnection);
        httpURLConnection.disconnect();

        GenerateKeyPairService generateKeyPairService = new GenerateECKeyPairServiceImp();
        System.out.println("public key from server --- "
                + generateKeyPairService.generatePublicKeyFromString
                (new ObjectMapper().readValue(response, BaseModel.class).getPublicKey()));

    }

    public static String postRequest(String apiUrl, String postParams) throws IOException {

        System.out.println("URL -- " + apiUrl);
        System.out.println("Post Parameters -- " + postParams);

        URL url = new URL(apiUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setDoOutput(true);

        DataOutputStream outputStreamWriter = new DataOutputStream(httpURLConnection.getOutputStream());
        outputStreamWriter.writeBytes(postParams);
        outputStreamWriter.flush();
        outputStreamWriter.close();

        System.out.println(" status : " + httpURLConnection.getResponseCode());
        String response = readResponse(httpURLConnection);

        return response;
    }

    public static String readResponse(HttpURLConnection HttpURLConnection) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(HttpURLConnection.getInputStream()));
        StringBuffer responseContent = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            responseContent.append(inputLine);
        }

        bufferedReader.close();

        return new String(responseContent);
    }
}
