package advisor;

public class View {
    String[] output;
    int objectOnPage;
    int totalPages;
    int currentPage;
    int startObject;
    int endObject;


    public View(int _onPage){
        objectOnPage = _onPage;
    }


    public void print(String _data) {
        startObject = 0;
        endObject = 0;
        output = _data.split("\n\n");
        if (output.length % objectOnPage > 0) {
            totalPages = output.length / objectOnPage + 1;
        } else {
            totalPages = output.length / objectOnPage;
        }
        currentPage = 1;
        for (int i = 0; i < objectOnPage; i++) {
            System.out.println(output[i]);
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }


    public void printNext(){
        if (currentPage + 1 < totalPages) {
            startObject = objectOnPage * currentPage;
            endObject = startObject + objectOnPage;
        } else if (currentPage + 1 == totalPages) {
            startObject = objectOnPage * currentPage;
            if (output.length % objectOnPage > 0) {
                endObject = startObject + output.length % objectOnPage;
            } else {
                endObject = startObject + objectOnPage;
            }
        } else {
            System.out.println("No more pages");
            return;
        }
        for (int i = startObject ; i < endObject; i++) {
            System.out.println(output[i] + "\n");
        }
        currentPage++;
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }


    public void printPrev() {
        currentPage--;
        if (currentPage > 0) {
            startObject = startObject - objectOnPage;
            endObject = startObject + objectOnPage;
            for (int i = startObject; i < endObject; i++) {
                System.out.println(output[i] + "\n");
            }
            System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
        } else {
            currentPage++;
            System.out.println("No more pages");
        }
    }
}
