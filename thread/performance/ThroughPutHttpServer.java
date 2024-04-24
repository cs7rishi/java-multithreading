package thread.performance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ThroughtPutHttpServer {
    private static final String INPUT_FILE = "resource/throughput/war";

    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        startServer(text);
    }

    public static void startServer(String text){
        ThroughtPutHttpServer server = com.sun.net.httpserver.HttpServer.create()
    }
}
