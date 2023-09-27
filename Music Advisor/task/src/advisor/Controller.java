package advisor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    static String CATEGORIES = "/v1/browse/categories";
    static String NEW = "/v1/browse/new-releases";
    static String FEATURED = "/v1/browse/featured-playlists";
    static String PLAYLIST = "/v1/browse/categories/";


    public String getRequest(String _path) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authorization.ACCESS_TOKEN)
                .uri(URI.create(_path))
                .GET()
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            assert response != null;
            return response.body();

        } catch (InterruptedException | IOException e) {
            return "Error response";
        }
    }

    public String getCategories() {
        List<Model> models = new ArrayList<>();
        String response = getRequest(Main.RESOURCE + CATEGORIES);

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("categories");
        for (JsonElement item : categories.getAsJsonArray("items")) {
            Model element = new Model();
            element.setCategories(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));
            models.add(element);
        }

        StringBuilder result = new StringBuilder();
        for (Model each : models) {
            result.append(each.categories).append("\n").append("\n");
        }
        return result.toString();
    }

    public String getNews() {
        List<Model> models = new ArrayList<>();
        String response = getRequest(Main.RESOURCE + NEW);

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("albums");


        for (JsonElement item : categories.getAsJsonArray("items")) {
            Model element = new Model();
            element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));

            StringBuilder artists = new StringBuilder("[");

            for (JsonElement name : item.getAsJsonObject().getAsJsonArray("artists")) {
                if (!artists.toString().endsWith("[")) {
                    artists.append(", ");
                }
                artists.append(name.getAsJsonObject().get("name"));
            }

            element.setName(artists.append("]").toString().replaceAll("\"", ""));

            element.setLink(item.getAsJsonObject().get("external_urls")
                    .getAsJsonObject().get("spotify")
                    .toString().replaceAll("\"", ""));

            models.add(element);
        }

        StringBuilder result = new StringBuilder();
        for (Model each : models) {
            result.append(each.album).append("\n")
                    .append(each.name).append("\n")
                    .append(each.link).append("\n")
                    .append("\n");
        }
        return result.toString();
    }


    public String getFeatured() {
        List<Model> models = new ArrayList<>();
        String response = getRequest(Main.RESOURCE + FEATURED);

        JsonObject jo = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categories = jo.getAsJsonObject("playlists");

        for (JsonElement item : categories.getAsJsonArray("items")) {
            Model element = new Model();
            element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));

            element.setLink(item.getAsJsonObject().get("external_urls")
                    .getAsJsonObject().get("spotify")
                    .toString().replaceAll("\"", ""));

            models.add(element);
        }
        StringBuilder result = new StringBuilder();
        for (Model each : models) {
            result.append(each.album).append("\n")
                    .append(each.link).append("\n")
                    .append("\n");
        }
        return result.toString();
    }


    public String getPlaylist(String cNAME) {
        List<Model> models = new ArrayList<>();

        String response = getRequest(Main.RESOURCE + CATEGORIES);
        String id_categories = "Unknown category name.";

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        JsonObject categories = jsonObject.getAsJsonObject("categories");
        for (JsonElement item : categories.getAsJsonArray("items")) {
            if (item.getAsJsonObject().get("name").toString().replaceAll("\"", "").equals(cNAME)) {
                id_categories = item.getAsJsonObject().get("id").toString().replaceAll("\"", "");
                break;
            }
        }
        if (id_categories.equals("Unknown category name.")) {
            return id_categories;
        }

        response = getRequest(Main.RESOURCE + PLAYLIST + id_categories + "/playlists");
        System.out.println(response);
        if (response.contains("Test unpredictable error message")) {
            return "Test unpredictable error message";
        }
        jsonObject = JsonParser.parseString(response).getAsJsonObject();
        categories = jsonObject.getAsJsonObject("playlists");

        for (JsonElement item : categories.getAsJsonArray("items")) {
            Model element = new Model();
            element.setAlbum(item.getAsJsonObject().get("name").toString().replaceAll("\"", ""));

            element.setLink(item.getAsJsonObject().get("external_urls")
                    .getAsJsonObject().get("spotify")
                    .toString().replaceAll("\"", ""));

            models.add(element);
        }

        StringBuilder result = new StringBuilder();
        for (Model each : models) {
            result.append(each.album).append("\n")
                    .append(each.link).append("\n")
                    .append("\n");
        }
        return result.toString();

    }
}