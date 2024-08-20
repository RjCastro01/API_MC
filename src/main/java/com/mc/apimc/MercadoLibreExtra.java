package com.mc.apimc;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class MercadoLibreExtra {
    private static final OkHttpClient client = new OkHttpClient();

    public static String getSellerId(String accessToken) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/users/me?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            return jsonObject.get("id").getAsString();
        }
    }

    public static String getCategories() throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/sites/MLA/categories?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

    public static String getCurrencyConversionRates() throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/currency_conversions/search?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

    public static String getListingTypes() throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/sites/MLA/listing_types?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

    public static String getSites() throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/sites?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

    public static String getOrderDetails(String orderId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/orders/" + orderId + "?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

    public static String getShipmentDetails(String shipmentId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/shipments/" + shipmentId + "?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }
}
