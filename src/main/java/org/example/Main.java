package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa una URL válida para iniciar el proceso: ");
        String url = scanner.nextLine();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("Esta es la estructura de la página completa: ");
            System.out.println(responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        Document document = Jsoup.connect(url).get();

        String htmlContent = document.html();
        //Contar la cantidad de lineas
        int lineCount = htmlContent.split("\n").length;
        System.out.println("Cantidad de líneas: " + lineCount);

        //Contar la cantidad de párrafos
        int paragraphCount = document.select("p").size();
        System.out.println("Cantidad de párrafos: " + paragraphCount);

        //Mostrar la cantidad de forms y ver los tipos y nombres de cada uno
        Elements forms = document.select("form");
        for (Element form : forms) {
            System.out.println("Formulario: ");
            Elements inputFields = form.select("input");
            for (Element input : inputFields) {
                String fieldType = input.attr("type");
                String fieldName = input.attr("name");
                System.out.println(" Tipo: " + fieldType + ", Nombre: " + fieldName);
            }
        }
    }
}