import es.Search;
import es.impl.EsSearch;

public class ConnectTest {
    public static void main(String[] args) {
        Search search = new EsSearch();
        search.close();
    }
}
