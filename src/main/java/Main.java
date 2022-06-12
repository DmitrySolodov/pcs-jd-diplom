import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        // Выполним индксацию нашей директории
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        // Если индексация прошла успешно - запускаем сервер
        Server server = new Server();
        server.start(engine);
    }
}