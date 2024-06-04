import java.io.IOException;

import meaty.ServerAPIs.ConnectionAPI;

public class ConnectionAPITest {

    public static void main(String[] args) throws IOException {
        // Test ConnectionAPI
        ConnectionAPI conn = new ConnectionAPI();
        
        // Example of sending an authentication request
        System.out.println("Sending authentication request...");
        conn.sendAuthRequest("user1", "password123");

        // Example of sending a post request
        System.out.println("Sending post request...");
        String userMessage = "hehe how you doin'";
        conn.sendPostRequest(userMessage);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
