package advisor;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.Objects;

public class Main {
    @Parameter(names = {"-access"})
    String access;

    @Parameter(names = {"-resource"})
    String resource;

    @Parameter(names = {"-page"})
    String page;


    static String SERVER_PATH;
    static String RESOURCE;
    static int PAGE;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.getAccess();
        main.getResource();
        main.getPage();


        Advisor advisor = new Advisor();
        advisor.start();
    }

    private void getAccess() {
        SERVER_PATH = Objects.requireNonNullElse(access, "https://accounts.spotify.com");
    }

    private void getResource() {
        RESOURCE = Objects.requireNonNullElse(resource, "https://api.spotify.com");
    }

    private void getPage() {
        PAGE = page != null ? Integer.parseInt(page) : 5;
    }
}