package advisor;

public class Service {
    boolean isAuthorized = false;
    Controller controller = new Controller();

    public void setAuthorization() {
        Authorization authorization = new Authorization();
        authorization.getAccessCode();
        authorization.getAccessToken();
        this.isAuthorized = true;
    }


    public String getReleases() {
        if (isAuthorized) {
            return controller.getNews();
        } else {
            return "Please, provide access for application.";
        }
    }

    public String getFeatured() {
        if (isAuthorized) {
            return controller.getFeatured();
        } else {
            return "Please, provide access for application.";
        }
    }

    public String getCategories() {
        if (isAuthorized) {
            return controller.getCategories();
        } else {
            return "Please, provide access for application.";
        }
    }

    public String getPlaylists(String _C_NAME) {
        if (isAuthorized) {
            return controller.getPlaylist(_C_NAME);
        } else {
            return "Please, provide access for application.";
        }
    }
}