package com.mc.apimc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MercadoLibreAPI {
    private static final OkHttpClient client = new OkHttpClient();

    public static String getProducts(String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/users/" + sellerId + "/items/search?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

    public void getProductDetails(String itemId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/items/" + itemId + "?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

            // Extraer y mostrar la información del producto
            String id = jsonResponse.get("id").isJsonNull() ? "No ID" : jsonResponse.get("id").getAsString();
            String title = jsonResponse.get("title").isJsonNull() ? "No Title" : jsonResponse.get("title").getAsString();
            String subtitle = jsonResponse.has("subtitle") && !jsonResponse.get("subtitle").isJsonNull() ? jsonResponse.get("subtitle").getAsString() : "No Subtitle";
            String sellerId = jsonResponse.get("seller_id").isJsonNull() ? "No Seller ID" : jsonResponse.get("seller_id").getAsString();
            String categoryId = jsonResponse.get("category_id").isJsonNull() ? "No Category ID" : jsonResponse.get("category_id").getAsString();
            double price = jsonResponse.has("price") && !jsonResponse.get("price").isJsonNull() ? jsonResponse.get("price").getAsDouble() : 0.0;
            String currencyId = jsonResponse.get("currency_id").isJsonNull() ? "No Currency ID" : jsonResponse.get("currency_id").getAsString();
            int availableQuantity = jsonResponse.has("available_quantity") && !jsonResponse.get("available_quantity").isJsonNull() ? jsonResponse.get("available_quantity").getAsInt() : 0;
            int soldQuantity = jsonResponse.has("sold_quantity") && !jsonResponse.get("sold_quantity").isJsonNull() ? jsonResponse.get("sold_quantity").getAsInt() : 0;
            String buyingMode = jsonResponse.get("buying_mode").isJsonNull() ? "No Buying Mode" : jsonResponse.get("buying_mode").getAsString();
            String condition = jsonResponse.get("condition").isJsonNull() ? "No Condition" : jsonResponse.get("condition").getAsString();
            String listingTypeId = jsonResponse.get("listing_type_id").isJsonNull() ? "No Listing Type ID" : jsonResponse.get("listing_type_id").getAsString();
            String startTime = jsonResponse.get("start_time").isJsonNull() ? "No Start Time" : jsonResponse.get("start_time").getAsString();
            //String startTime = jsonResponse.has("start_time") && !jsonResponse.get("start_time").isJsonNull() ? jsonResponse.get("start_time").getAsString() : "No Start Time";
            String endTime = jsonResponse.get("end_time").isJsonNull() ? "No End Time" : jsonResponse.get("end_time").getAsString();
            //String endTime = jsonResponse.has("end_time") && !jsonResponse.get("end_time").isJsonNull() ? jsonResponse.get("end_time").getAsString() : "No End Time";
            JsonObject shipping = jsonResponse.has("shipping") && !jsonResponse.get("shipping").isJsonNull() ? jsonResponse.getAsJsonObject("shipping") : null;
            JsonArray pictures = jsonResponse.has("pictures") && !jsonResponse.get("pictures").isJsonNull() ? jsonResponse.getAsJsonArray("pictures") : new JsonArray();
            String description = jsonResponse.has("description") && !jsonResponse.get("description").isJsonNull() ? jsonResponse.get("description").getAsString() : "No Description";
            String warranty = jsonResponse.has("warranty") && !jsonResponse.get("warranty").isJsonNull() ? jsonResponse.get("warranty").getAsString() : "No Warranty";
            String catalogProductId = jsonResponse.has("catalog_product_id") && !jsonResponse.get("catalog_product_id").isJsonNull() ? jsonResponse.get("catalog_product_id").getAsString() : "No Catalog ID";

            // Imprimir detalles
            System.out.println("Product ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Subtitle: " + subtitle);
            System.out.println("Seller ID: " + sellerId);
            System.out.println("Category ID: " + categoryId);
            System.out.println("Price: " + price);
            System.out.println("Currency ID: " + currencyId);
            System.out.println("Available Quantity: " + availableQuantity);
            System.out.println("Sold Quantity: " + soldQuantity);
            System.out.println("Buying Mode: " + buyingMode);
            System.out.println("Condition: " + condition);
            System.out.println("Listing Type ID: " + listingTypeId);
            System.out.println("Start Time: " + startTime);
            System.out.println("End Time: " + endTime);
            if (shipping != null) {
                System.out.println("Shipping Info: " + shipping.toString());
            }
            System.out.println("Pictures:");
            for (int i = 0; i < pictures.size(); i++) {
                JsonObject picture = pictures.get(i).getAsJsonObject();
                System.out.println(" - " + picture.get("url").getAsString());
            }
            System.out.println("Description: " + description);
            System.out.println("Warranty: " + warranty);
            System.out.println("Catalog Product ID: " + catalogProductId);
            System.out.println("----------------------------------------------------------------------------------------");
        }
    }

    public static JsonArray getProductIds(String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/users/" + sellerId + "/items/search?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            return jsonResponse.getAsJsonArray("results");
        }
    }

    public static JsonArray getAllProductIds(String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();
        JsonArray allProductIds = new JsonArray();
        String scrollId = null;
        boolean hasMore = true;

        while (hasMore) {
            String url = "https://api.mercadolibre.com/users/" + sellerId + "/items/search?search_type=scan";
            if (scrollId != null) {
                url += "&scroll_id=" + scrollId;
            }

            Request request = new Request.Builder()
                    .url(url)
                    .header("Authorization", "Bearer " + accessToken)
                    .get()
                    .build();


            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                JsonArray results = jsonResponse.getAsJsonArray("results");
                allProductIds.addAll(results);
                System.out.println(results.size());

                if (jsonResponse.has("scroll_id")) {
                    scrollId = jsonResponse.get("scroll_id").getAsString();
                } else {
                    hasMore = false;
                }
            }
        }

        return allProductIds;
    }

    public static List<String> getAlProductIds(String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();
        List<String> allProductIds = new ArrayList<>();
        String scrollId = null;
        boolean hasMore = true;

        while (hasMore) {
            // Construir la URL de búsqueda con search_type=scan
            String url = "https://api.mercadolibre.com/users/" + sellerId + "/items/search?search_type=scan";
            if (scrollId != null) {
                url += "&scroll_id=" + scrollId;
            }

            Request request = new Request.Builder()
                    .url(url)
                    .header("Authorization", "Bearer " + accessToken)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                JsonArray results = jsonResponse.getAsJsonArray("results");
                if (results != null) {
                    for (JsonElement result : results) {
                        if (result.isJsonObject()) {
                            JsonObject resultObj = result.getAsJsonObject();
                            if (resultObj.has("id") && !resultObj.get("id").isJsonNull()) {
                                allProductIds.add(resultObj.get("id").getAsString());
                            }
                        }
                    }
                }

                // Verificar si hay más resultados
                if (jsonResponse.has("scroll_id")) {
                    scrollId = jsonResponse.get("scroll_id").getAsString();
                } else {
                    hasMore = false;
                }

                // Verificar si hay más páginas
                if (!jsonResponse.has("results") || jsonResponse.get("results").getAsJsonArray().size() == 0) {
                    hasMore = false;
                }
            }
        }

        return allProductIds;
    }

    public static JsonArray getAllllProductIds(String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();
        JsonArray allProductIds = new JsonArray();
        String scrollId = null;
        boolean hasMore = true;

        while (hasMore) {
            String url = "https://api.mercadolibre.com/users/" + sellerId + "/items/search?search_type=scan";
            if (scrollId != null) {
                url += "&scroll_id=" + scrollId;
            }

            Request request = new Request.Builder()
                    .url(url)
                    .header("Authorization", "Bearer " + accessToken)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                JsonArray results = jsonResponse.getAsJsonArray("results");
                if (results != null && results.size() > 0) {
                    for (JsonElement result : results) {
                        if (result.isJsonObject()) {
                            JsonObject resultObj = result.getAsJsonObject();
                            System.out.println(results.size());
                            if (resultObj.has("id") && !resultObj.get("id").isJsonNull()) {
                                allProductIds.add(resultObj.get("id"));
                                System.out.println(resultObj.get("id").getAsString());
                            }
                        }
                    }
                    // Obtener el siguiente scroll_id
                    if (jsonResponse.has("scroll_id")) {
                        scrollId = jsonResponse.get("scroll_id").getAsString();
                    } else {
                        hasMore = false;
                    }
                } else {
                    hasMore = false;
                }
            }
        }

        return allProductIds;
    }


    // Método para obtener detalles de un producto y verificar por seller_id
    public void getProductDetailsBySellerId(String itemId, String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        // Construir la URL para obtener detalles del producto
        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/items/" + itemId + "?access_token=" + accessToken)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

            // Verificar que el producto pertenece al vendedor especificado
            if (jsonResponse.has("seller_id") && jsonResponse.get("seller_id").getAsString().equals(sellerId)) {
                // Extraer y mostrar la información del producto
                String id = jsonResponse.get("id").isJsonNull() ? "No ID" : jsonResponse.get("id").getAsString();
                String title = jsonResponse.get("title").isJsonNull() ? "No Title" : jsonResponse.get("title").getAsString();
                String subtitle = jsonResponse.has("subtitle") && !jsonResponse.get("subtitle").isJsonNull() ? jsonResponse.get("subtitle").getAsString() : "No Subtitle";
                String sellerIdValue = jsonResponse.get("seller_id").isJsonNull() ? "No Seller ID" : jsonResponse.get("seller_id").getAsString();
                String categoryId = jsonResponse.get("category_id").isJsonNull() ? "No Category ID" : jsonResponse.get("category_id").getAsString();
                double price = jsonResponse.has("price") && !jsonResponse.get("price").isJsonNull() ? jsonResponse.get("price").getAsDouble() : 0.0;
                String currencyId = jsonResponse.get("currency_id").isJsonNull() ? "No Currency ID" : jsonResponse.get("currency_id").getAsString();
                int availableQuantity = jsonResponse.has("available_quantity") && !jsonResponse.get("available_quantity").isJsonNull() ? jsonResponse.get("available_quantity").getAsInt() : 0;
                int soldQuantity = jsonResponse.has("sold_quantity") && !jsonResponse.get("sold_quantity").isJsonNull() ? jsonResponse.get("sold_quantity").getAsInt() : 0;
                String buyingMode = jsonResponse.get("buying_mode").isJsonNull() ? "No Buying Mode" : jsonResponse.get("buying_mode").getAsString();
                String condition = jsonResponse.get("condition").isJsonNull() ? "No Condition" : jsonResponse.get("condition").getAsString();
                String listingTypeId = jsonResponse.get("listing_type_id").isJsonNull() ? "No Listing Type ID" : jsonResponse.get("listing_type_id").getAsString();
                String startTime = jsonResponse.get("start_time").isJsonNull() ? "No Start Time" : jsonResponse.get("start_time").getAsString();
                String endTime = jsonResponse.get("end_time").isJsonNull() ? "No End Time" : jsonResponse.get("end_time").getAsString();
                JsonObject shipping = jsonResponse.has("shipping") && !jsonResponse.get("shipping").isJsonNull() ? jsonResponse.getAsJsonObject("shipping") : null;
                JsonArray pictures = jsonResponse.has("pictures") && !jsonResponse.get("pictures").isJsonNull() ? jsonResponse.getAsJsonArray("pictures") : new JsonArray();
                String description = jsonResponse.has("description") && !jsonResponse.get("description").isJsonNull() ? jsonResponse.get("description").getAsString() : "No Description";
                String warranty = jsonResponse.has("warranty") && !jsonResponse.get("warranty").isJsonNull() ? jsonResponse.get("warranty").getAsString() : "No Warranty";
                String catalogProductId = jsonResponse.has("catalog_product_id") && !jsonResponse.get("catalog_product_id").isJsonNull() ? jsonResponse.get("catalog_product_id").getAsString() : "No Catalog ID";

                // Imprimir detalles
                System.out.println("Product ID: " + id);
                System.out.println("Title: " + title);
                System.out.println("Subtitle: " + subtitle);
                System.out.println("Seller ID: " + sellerIdValue);
                System.out.println("Category ID: " + categoryId);
                System.out.println("Price: " + price);
                System.out.println("Currency ID: " + currencyId);
                System.out.println("Available Quantity: " + availableQuantity);
                System.out.println("Sold Quantity: " + soldQuantity);
                System.out.println("Buying Mode: " + buyingMode);
                System.out.println("Condition: " + condition);
                System.out.println("Listing Type ID: " + listingTypeId);
                System.out.println("Start Time: " + startTime);
                System.out.println("End Time: " + endTime);
                if (shipping != null) {
                    System.out.println("Shipping Info: " + shipping.toString());
                }
                System.out.println("Pictures:");
                for (int i = 0; i < pictures.size(); i++) {
                    JsonObject picture = pictures.get(i).getAsJsonObject();
                    System.out.println(" - " + picture.get("url").getAsString());
                }
                System.out.println("Description: " + description);
                System.out.println("Warranty: " + warranty);
                System.out.println("Catalog Product ID: " + catalogProductId);
            } else {
                System.out.println("Product ID " + itemId + " does not belong to seller ID " + sellerId);
            }
        }
    }

    public static JsonArray getAlllProductIds(String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();
        JsonArray allProductIds = new JsonArray();
        String scrollId = null;
        boolean hasMore = true;

        while (hasMore) {
            String url = "https://api.mercadolibre.com/users/" + sellerId + "/items/search?access_token=" + accessToken + "&limit=100";
            if (scrollId != null) {
                url += "&scroll_id=" + scrollId;
            }

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                JsonArray results = jsonResponse.getAsJsonArray("results");
                allProductIds.addAll(results);

                if (jsonResponse.has("scroll_id") && results.size() > 0) {
                    scrollId = jsonResponse.get("scroll_id").getAsString();
                } else {
                    hasMore = false;
                }
            }
        }

        return allProductIds;
    }

//    public List<String> searchProductsByTitle(String title, String sellerId) throws IOException {
//        String accessToken = MercadoLibreToken.getValidAccessToken();
//        List<String> productIds = new ArrayList<>();
//
//        // Construir la URL de búsqueda
//        String url = "https://api.mercadolibre.com/sites/MLM/search?q=" + title + "access_token=" + accessToken + "&seller_id=" + sellerId;
//        Request request = new Request.Builder().url(url).get().build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
//            }
//
//            String responseBody = response.body().string();
//            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
//            JsonArray results = jsonResponse.getAsJsonArray("results");
//
//            // Iterar sobre los resultados y guardar los IDs en la lista
//            for (int i = 0; i < results.size(); i++) {
//                JsonObject result = results.get(i).getAsJsonObject();
//                if (result.has("id") && !result.get("id").isJsonNull()) {
//                    productIds.add(result.get("id").getAsString());
//                }
//            }
//        }
//
//        return productIds;
//    }

    public List<String> searchProductsByTitle(String title, String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();
        List<String> productIds = new ArrayList<>();

        // Codificar el título para usar en la URL
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());

        // Construir la URL de búsqueda con el filtro por vendedor
        String url = "https://api.mercadolibre.com/sites/MLM/search?q=" + encodedTitle + "&access_token=" + accessToken + "&seller_id=" + sellerId;
        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray results = jsonResponse.getAsJsonArray("results");

            // Iterar sobre los resultados y guardar los IDs en la lista
            for (int i = 0; i < results.size(); i++) {
                JsonObject result = results.get(i).getAsJsonObject();
                if (result.has("id") && !result.get("id").isJsonNull()) {
                    productIds.add(result.get("id").getAsString());
                }
            }
        }

        return productIds;
    }

//    public List<String> searchProductsByKeyword(String keyword, String sellerId) throws IOException {
//        String accessToken = MercadoLibreToken.getValidAccessToken();
//        List<String> productIds = new ArrayList<>();
//        int page = 0; // Empezar desde la primera página
//
//        while (true) {
//            // Construir la URL de búsqueda con paginación
//            String url = "https://api.mercadolibre.com/sites/MLM/search?q=" + keyword + "&access_token=" + accessToken + "&seller_id=" + sellerId + "&offset=" + (page * 50);
//            Request request = new Request.Builder().url(url).get().build();
//
//            try (Response response = client.newCall(request).execute()) {
//                if (!response.isSuccessful()) {
//                    throw new IOException("Unexpected code " + response);
//                }
//
//                String responseBody = response.body().string();
//                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
//                JsonArray results = jsonResponse.getAsJsonArray("results");
//
//                // Si no hay resultados, detener la búsqueda
//                if (results.size() == 0) {
//                    break;
//                }
//
//                // Iterar sobre los resultados y guardar los IDs en la lista
//                for (int i = 0; i < results.size(); i++) {
//                    JsonObject result = results.get(i).getAsJsonObject();
//                    if (result.has("id") && !result.get("id").isJsonNull()) {
//                        productIds.add(result.get("id").getAsString());
//                    }
//                }
//
//                // Incrementar el número de página
//                page++;
//                System.out.println("Pasando a la página " + page);
//            }
//        }
//
//        return productIds;
//    }

    public List<String> searchProductsByKeyword(String keyword, String sellerId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();
        List<String> productIds = new ArrayList<>();
        String scrollId = null; // Inicialmente no hay scroll_id

        while (true) {
            // Construir la URL de búsqueda con el scroll_id si existe
            String url;
            if (scrollId == null) {
                url = "https://api.mercadolibre.com/sites/MLM/search?q=" + keyword + "&access_token=" + accessToken + "&seller_id=" + sellerId;
            } else {
                url = "https://api.mercadolibre.com/sites/MLM/search?scroll_id=" + scrollId + "&access_token=" + accessToken;
            }

            Request request = new Request.Builder().url(url).get().build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray results = jsonResponse.getAsJsonArray("results");

                // Obtener el nuevo scroll_id de la respuesta
                JsonElement scrollIdElement = jsonResponse.get("scroll_id");
                scrollId = scrollIdElement != null && !scrollIdElement.isJsonNull() ? scrollIdElement.getAsString() : null;

                // Si no hay resultados y no hay scroll_id, detener la búsqueda
                if (results.size() == 0 && scrollId == null) {
                    break;
                }

                // Iterar sobre los resultados y guardar los IDs en la lista
                for (int i = 0; i < results.size(); i++) {
                    JsonObject result = results.get(i).getAsJsonObject();
                    if (result.has("id") && !result.get("id").isJsonNull()) {
                        productIds.add(result.get("id").getAsString());
                    }
                }

                // Si no hay scroll_id, hemos llegado al final de los resultados
                if (scrollId == null) {
                    break;
                }

                // Imprimir el estado actual
                System.out.println("Continuando con el scroll_id: " + scrollId);
            }
        }

        return productIds;
    }


    public void printProductDetails(List<String> productIds) throws IOException {
        String sellerID = "741822144";
        for (String productId : productIds) {
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("Fetching details for product ID: " + productId);
            getProductDetailsBySellerId(productId, sellerID);
            System.out.println();
        }
    }


    public static String addProduct(JsonObject product) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        RequestBody body = RequestBody.create(product.toString(), okhttp3.MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/items?access_token=" + accessToken)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }


    public static String updateProduct(String itemId, JsonObject product) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        RequestBody body = RequestBody.create(product.toString(), okhttp3.MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://api.mercadolibre.com/items/" + itemId + "?access_token=" + accessToken)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }


    public static String pausedProduct(String itemId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        // Pausar el ítem antes de eliminarlo
        JsonObject pausePayload = new JsonObject();
        pausePayload.addProperty("status", "paused");

        // Crear la solicitud PUT para pausar el ítem
        RequestBody pauseBody = RequestBody.create(
                pausePayload.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request pauseRequest = new Request.Builder()
                .url("https://api.mercadolibre.com/items/" + itemId)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .put(pauseBody)
                .build();

        try (Response pauseResponse = client.newCall(pauseRequest).execute()) {
            if (!pauseResponse.isSuccessful()) {
                String errorBody = pauseResponse.body().string();
                throw new IOException("Failed to pause product. Unexpected code " + pauseResponse + "\nError body: " + errorBody);
            }
            return pauseResponse.body().string();
        }
    }

    public static String closedProduct(String itemId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        // Pausar el ítem antes de eliminarlo
        JsonObject closePayload = new JsonObject();
        closePayload.addProperty("status", "closed");

        // Crear la solicitud PUT para pausar el ítem
        RequestBody closeBody = RequestBody.create(
                closePayload.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request pauseRequest = new Request.Builder()
                .url("https://api.mercadolibre.com/items/" + itemId)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .put(closeBody)
                .build();

        try (Response closeResponse = client.newCall(pauseRequest).execute()) {
            if (!closeResponse.isSuccessful()) {
                String errorBody = closeResponse.body().string();
                throw new IOException("Failed to closed product. Unexpected code " + closeResponse + "\nError body: " + errorBody);
            }
            return closeResponse.body().string();
        }
    }

    public static String deleteProduct(String itemId) throws IOException {
        String accessToken = MercadoLibreToken.getValidAccessToken();

        // Marcar el ítem como eliminado
        JsonObject deletePayload = new JsonObject();
        deletePayload.addProperty("deleted", "true");

        // Crear la solicitud PUT para eliminar el ítem
        RequestBody deleteBody = RequestBody.create(
                deletePayload.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request deleteRequest = new Request.Builder()
                .url("https://api.mercadolibre.com/items/" + itemId)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .put(deleteBody)
                .build();

        try (Response deleteResponse = client.newCall(deleteRequest).execute()) {
            if (!deleteResponse.isSuccessful()) {
                String errorBody = deleteResponse.body().string();
                throw new IOException("Failed to delete product. Unexpected code " + deleteResponse + "\nError body: " + errorBody);
            }

            return deleteResponse.body().string();
        } catch (IOException e) {
            System.err.println("Failed to delete product: " + e.getMessage());
            throw e;
        }
    }

}
