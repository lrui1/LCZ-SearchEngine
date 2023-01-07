import ES.Search;
import ES.impl.ESearch;

public class ConnectTest {
    public static void main(String[] args) {
        Search search = new ESearch();
        search.close();
    }
}
