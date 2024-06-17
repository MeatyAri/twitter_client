package meaty.ServerAPIs;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import meaty.ServerAPIs.protocol.*;

public class ConnectionAPI {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;
    private static final Gson gson = new Gson();

    private static SocketChannel socketChannel;
    private static long lastId = 0;
    private static final List<Response> responses = new CopyOnWriteArrayList<>();
    private static String token;

    static {
        try {
            _init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void _init() throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
        socketChannel.configureBlocking(false);
        System.out.println("Connected to the server.");

        Thread readThread = new Thread(() -> {
            try {
                ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
                while (true) {
                    if (lengthBuffer.hasRemaining()) {
                        int bytesRead = socketChannel.read(lengthBuffer);
                        if (bytesRead == -1) {
                            socketChannel.close();
                            System.out.println("Server disconnected");
                            break;
                        }
                        if (!lengthBuffer.hasRemaining()) {
                            lengthBuffer.flip();
                            int messageLength = lengthBuffer.getInt();
                            ByteBuffer messageBuffer = ByteBuffer.allocate(messageLength);
                            bytesRead = socketChannel.read(messageBuffer);
                            if (bytesRead == -1) {
                                socketChannel.close();
                                System.out.println("Server disconnected");
                                return;
                            }
                            if (!messageBuffer.hasRemaining()) {
                                messageBuffer.flip();
                                String serverMessage = StandardCharsets.UTF_8.decode(messageBuffer).toString();
                                // System.out.println(serverMessage);
                                Response response = gson.fromJson(serverMessage, Response.class);
                                responses.add(response);
                                lengthBuffer.clear();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        readThread.setDaemon(true);
        readThread.start();
    }

    public static long sendMessage(RequestType type, JsonObject data) throws IOException {
        Request request = new Request();
        request.setId(++lastId);
        request.setType(type);
        request.setData(data);
        byte[] requestBytes = gson.toJson(request).getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4 + requestBytes.length);
        buffer.putInt(requestBytes.length);
        buffer.put(requestBytes);
        buffer.flip();
        socketChannel.write(buffer);

        return request.getId();
    }

    public static boolean checkConnection() {
        if (socketChannel != null) {
            return true;
        }

        try {
            _init();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Response awaitResponse(long id) {
        while (true) {
            if (responses.size() <= 0) {
                try {
                    Thread.sleep(100);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (Response r : responses) {
                if (r.getId() == id) {
                    responses.remove(r);
                    return r;
                }
            }
        }
    }

    public static String getResponseStr(Response response) {
        switch (response.getStatus()) {
            case 200:
                return response.getData().get("content").getAsString();
            default:
                return "Error: " + response.getStatus();
        }
    }

    public static void saveToken(String token) {
        try {
            File tokenFile = new File("token");
            if (!tokenFile.exists()) {
                tokenFile.createNewFile();
            }
            FileWriter writer = new FileWriter(tokenFile);
            writer.write(token);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readToken() {
        try {
            File tokenFile = new File("token");
            if (!tokenFile.exists()) {
                return null;
            }

            Scanner scanner = new Scanner(tokenFile);
            String token = scanner.nextLine();
            scanner.close();
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getToken() {
        if (token == null) {
            String readToken = ConnectionAPI.readToken();

            if (readToken == null || readToken.isEmpty()) {
                return null;
            }

            token = readToken;
        }

        return token;
    }
}
