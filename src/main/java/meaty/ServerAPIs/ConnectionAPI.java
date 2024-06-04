package meaty.ServerAPIs;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConnectionAPI {
    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 5000;
    private final Gson gson = new Gson();
    SocketChannel socketChannel;

    public ConnectionAPI() {
        try {
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
                                    System.out.println(serverMessage);
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAuthRequest(String username, String password) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("username", username);
        data.addProperty("password", password);
        sendMessage(RequestType.LOGIN, data);
    }

    public void sendPostRequest(String content) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("content", content);
        sendMessage(RequestType.POST, data);
    }

    public void sendMessage(RequestType type, JsonObject data) throws IOException {
        JsonObject message = new JsonObject();
        message.addProperty("type", type.toString());
        message.add("data", data);
        byte[] messageBytes = gson.toJson(message).getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4 + messageBytes.length);
        buffer.putInt(messageBytes.length);
        buffer.put(messageBytes);
        buffer.flip();
        socketChannel.write(buffer);
    }
}
