import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public void start(BooleanSearchEngine engine) {
        JsonFormatOutputConverter converter = new JsonFormatOutputConverter();
        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     ) {
                    String word = in.readLine();
                    System.out.println("Получено слово " + "'" + word + "', провожу поиск...");
                    List<PageEntry> list = engine.search(word);
                    System.out.println("Успешно! Отправляю клиенту результат");
                    out.println(converter.convert(list));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
