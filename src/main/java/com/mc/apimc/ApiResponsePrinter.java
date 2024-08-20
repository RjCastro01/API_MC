package com.mc.apimc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;

public class ApiResponsePrinter {

    // Método para imprimir el JSON formateado
    public static void printJsonResponse(String jsonResponse) {
        JsonElement jsonElement = JsonParser.parseString(jsonResponse);
        String formattedJson = new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement);
        System.out.println(formattedJson);
    }

    // Método para imprimir los productos
    public static void printProductsResponse(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Imprimir el seller_id
        String sellerId = jsonObject.get("seller_id").getAsString();
        System.out.println("Seller ID: " + sellerId);

        // Imprimir la lista de productos
        JsonArray productIds = jsonObject.getAsJsonArray("results");
        System.out.println("Product IDs:");
        for (JsonElement productId : productIds) {
            System.out.println(" - " + productId.getAsString());
        }

        // Imprimir la información de paginación
        JsonObject paging = jsonObject.getAsJsonObject("paging");
        int limit = paging.get("limit").getAsInt();
        int offset = paging.get("offset").getAsInt();
        int total = paging.get("total").getAsInt();
        System.out.println("Paging Information:");
        System.out.println(" Limit: " + limit);
        System.out.println(" Offset: " + offset);
        System.out.println(" Total: " + total);

        // Imprimir las opciones de ordenamiento disponibles
        JsonArray availableOrders = jsonObject.getAsJsonArray("available_orders");
        System.out.println("Available Orders:");
        for (JsonElement order : availableOrders) {
            JsonObject orderObject = order.getAsJsonObject();
            String id = orderObject.get("id").getAsString();
            String name = orderObject.get("name").getAsString();
            System.out.println(" - ID: " + id + ", Name: " + name);
        }

        // Opcionalmente, imprimir otras secciones si las necesitas
        // ...
    }
}

