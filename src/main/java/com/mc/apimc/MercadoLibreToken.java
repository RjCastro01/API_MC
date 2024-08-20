package com.mc.apimc;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

public class MercadoLibreToken {
    private static final String CLIENT_ID = "4416214161764373";
    private static final String CLIENT_SECRET = "IH3WfLHVYi60RADMkNSkrh6GbVHmlOf8";
    private static final String REDIRECT_URI = "https://freightryt.com";
    private static final String AUTHORIZATION_CODE = "TG-66a3e8ceb80f510001eeb62c-741822144";
    private static final String TOKEN_FILE = "tokens.txt";

    private static final OkHttpClient client = new OkHttpClient();

    public static String getValidAccessToken() throws IOException {
        TokenData tokenData = readTokenData();

        if (tokenData == null || tokenData.getAccessToken() == null || tokenData.isAccessTokenExpired()) {
            if (tokenData == null) {
                // Si el archivo de tokens no existe o está corrupto, obtenemos los tokens iniciales
                tokenData = obtainInitialTokens();
            } else {
                // Refresca el token
                tokenData = refreshAccessToken(tokenData.getRefreshToken());
            }
            saveTokenData(tokenData);
        }

        return tokenData.getAccessToken();
    }

    private static TokenData obtainInitialTokens() throws IOException {
        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/x-www-form-urlencoded"),
                "grant_type=authorization_code" +
                        "&client_id=" + CLIENT_ID +
                        "&client_secret=" + CLIENT_SECRET +
                        "&code=" + AUTHORIZATION_CODE +
                        "&redirect_uri=" + REDIRECT_URI
        );

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/oauth/token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

            TokenData tokenData = new TokenData();
            tokenData.setAccessToken(jsonObject.get("access_token").getAsString());
            tokenData.setRefreshToken(jsonObject.get("refresh_token").getAsString());
            tokenData.setExpiresAt(Instant.now().getEpochSecond() + jsonObject.get("expires_in").getAsLong());

            return tokenData;
        }
    }

    private static TokenData refreshAccessToken(String refreshToken) throws IOException {
        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/x-www-form-urlencoded"),
                "grant_type=refresh_token" +
                        "&client_id=" + CLIENT_ID +
                        "&client_secret=" + CLIENT_SECRET +
                        "&refresh_token=" + refreshToken
        );

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/oauth/token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

            TokenData tokenData = new TokenData();
            tokenData.setAccessToken(jsonObject.get("access_token").getAsString());
            tokenData.setRefreshToken(jsonObject.get("refresh_token").getAsString());
            tokenData.setExpiresAt(Instant.now().getEpochSecond() + jsonObject.get("expires_in").getAsLong());

            return tokenData;
        }
    }

    private static TokenData readTokenData() throws IOException {
        File file = new File(TOKEN_FILE);
        if (!file.exists()) {
            return null; // Si el archivo no existe, retornar null
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String accessToken = reader.readLine();
            String refreshToken = reader.readLine();
            long expiresAt = Long.parseLong(reader.readLine());

            TokenData tokenData = new TokenData();
            tokenData.setAccessToken(accessToken);
            tokenData.setRefreshToken(refreshToken);
            tokenData.setExpiresAt(expiresAt);

            return tokenData;
        }
    }

    private static void saveTokenData(TokenData tokenData) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TOKEN_FILE))) {
            writer.write(tokenData.getAccessToken());
            writer.newLine();
            writer.write(tokenData.getRefreshToken());
            writer.newLine();
            writer.write(Long.toString(tokenData.getExpiresAt()));
        }
    }
}