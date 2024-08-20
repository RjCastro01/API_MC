package com.mc.apimc;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Programa Corriendo!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try {
            MercadoLibreAPI api = new MercadoLibreAPI();

            // Obtener el access token
            String accessToken = MercadoLibreToken.getValidAccessToken();

            // Obtener el seller ID
            String sellerId = MercadoLibreExtra.getSellerId(accessToken);
            System.out.println("Seller ID: " + sellerId);

//            JsonArray productIds = api.getAllllProductIds(sellerId);
//
//            System.out.println("Product IDs:");
//            System.out.println("Número total de IDs de productos: " + productIds.size());
//            for (int i = 0; i < productIds.size(); i++) {
//                System.out.println(" - " + productIds.get(i).getAsString());
//            }

//            //Obtener todos los IDs de los productos
//            JsonArray productIds = MercadoLibreAPI.getProductIds("741822144");
//            System.out.println("Product IDs:");
//            for (int i = 0; i < productIds.size(); i++) {
//                System.out.println(" - " + productIds.get(i).getAsString());
//            }
//
//            JsonArray allproductIds = MercadoLibreAPI.getAllProductIds("741822144");
//            System.out.println("Todos los IDs de productos:");
//            System.out.println("Número total de IDs de productos: " + allproductIds.size());
//            for (int i = 0; i < allproductIds.size(); i++) {
//                System.out.println("- " + allproductIds.get(i).getAsString());
//            }

//            List<String> productIds = api.getAlProductIds(sellerId);
//
//            System.out.println("Número total de IDs de productos: " + productIds.size());
//            for (String productId : productIds) {
//                System.out.println("Product ID: " + productId);
//            }
//            JsonArray productIds = MercadoLibreAPI.getAlllProductIds(sellerId);
//
//            System.out.println("Número total de IDs de productos: " + productIds.size());
//
//            System.out.println("--------------------------------------------------------------------------------------");

//            //Obtener detalles de un producto
//            api.getProductDetails("MLM1313378725");


//            // Obtener categorías
//            String categories = MercadoLibreExtra.getCategories();
//            System.out.println("Categorías: " + categories);
//
//            // Obtener tasas de conversión de moneda
//            String conversionRates = MercadoLibreExtra.getCurrencyConversionRates();
//            System.out.println("Tasas de Conversión: " + conversionRates);
//
//            // Obtener tipos de listado
//            String listingTypes = MercadoLibreExtra.getListingTypes();
//            System.out.println("Tipos de Listado: " + listingTypes);
//
//            // Obtener sitios
//            String sites = MercadoLibreExtra.getSites();
//            System.out.println("Sitios: " + sites);
//
/*            // Ejemplo de listar productos
            String products = MercadoLibreAPI.getProducts("741822144");
            System.out.println("Productos: " + products);
            ApiResponsePrinter.printProductsResponse(products);*/

//
//            // Ejemplo de agregar un producto
//            JsonObject newProduct = new JsonObject();
//            newProduct.addProperty("title", "Nuevo Producto");
//            newProduct.addProperty("category_id", "MLA3530");
//            newProduct.addProperty("price", 100);
//            newProduct.addProperty("currency_id", "ARS");
//            newProduct.addProperty("available_quantity", 10);
//            newProduct.addProperty("buying_mode", "buy_it_now");
//            newProduct.addProperty("listing_type_id", "bronze");
//            newProduct.addProperty("condition", "new");
//            newProduct.addProperty("description", "Descripción del nuevo producto");
//
//            String addProductResponse = MercadoLibreAPI.addProduct(newProduct);
//            System.out.println("Agregar Producto: " + addProductResponse);
//


//            // Ejemplo de modificar un producto
//            String itemId = "MLM1313378725"; // ID del producto que quieres modificar
//
//            // Crear el objeto Json con las propiedades a actualizar
//            JsonObject updatedProduct = new JsonObject();
//            updatedProduct.addProperty("price", 2400.0);
//
//            // Llamar al método updateProduct para modificar el producto
//            String updateProductResponse = MercadoLibreAPI.updateProduct(itemId, updatedProduct);
//            System.out.println("Modificar Producto: " + updateProductResponse);
////
//
//            System.out.println("--------------------------------------------------------------------------------------");
//
//            //Obtener detalles de un producto
//            api.getProductDetails("MLM2031839981");

            api.getProductDetailsBySellerId("MLM1313378725", sellerId);
            System.out.println("--------------------------------------------------------------------------------------");
//            api.getProductDetails("MLM1535255362");
//            System.out.println("--------------------------------------------------------------------------------------");
//            api.getProductDetails("MLM2955152936");
//            System.out.println("--------------------------------------------------------------------------------------");
//            api.getProductDetails("MLM1772670647");
//            System.out.println("--------------------------------------------------------------------------------------");
//            api.getProductDetails("MLM2962868780");
//

//            String updateProductResponse = MercadoLibreAPI.updateProduct(itemId, updatedProduct);
//            System.out.println("Modificar Producto: " + updateProductResponse);
//


            // Ejemplo de borrar un producto

            //Cerrarlo primero

//            String paused = MercadoLibreAPI.closedProduct("MLM1313378725");
//            System.out.println("Response: " + paused);
//
            String response = MercadoLibreAPI.deleteProduct("MLM1313378725");
            System.out.println("Response: " + response);
//
//            api.getProductDetailsBySellerId("MLM1313378725", sellerId);
//            System.out.println("Borrado Exitoso");
//            System.out.println("--------------------------------------------------------------------------------------");

            //--------------------------------------- Buscar por palabras ------------------------------------------------

            //List<String> productIds = api.searchProductsByTitle("Fiesta", sellerId);

//            List<String> productIdss = api.searchProductsByKeyword("Computadora", sellerId);
//
//            System.out.println("Número total de productos encontrados: " + productIdss.size());
//            for (String productId : productIdss) {
//                System.out.println("---------------------------------------------------------------------------------");
//                api.getProductDetailsBySellerId(productId, sellerId); // Obtener y mostrar los detalles del producto
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        launch();
    }
}