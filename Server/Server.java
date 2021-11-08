import java.io.*;
import java.net.*;

public class Server {
  private final ServerSocket server;
  private final DataInputStream dis;
  private String username;
  private boolean inChat = false;

  public Server(Port port) throws IOException {
    server = new ServerSocket(port.getPortNumber());
    System.out.println("Server started on local port '" + server.getLocalPort() + "'");

    final Socket socket = server.accept();
    System.out.println("Accepted client socket");

    final InputStream is = socket.getInputStream();
    dis = new DataInputStream(is);
    startChat();
  }

  private void startChat() throws IOException {
    inChat = true;
    System.out.println("\nChat");
    System.out.println("――――");

    while (inChat) {
      readInput();
    }
  }

  private void readInput() throws IOException {
    final String message = dis.readUTF();
    final String[] splitMessage = message.split(":", 2);

    if (splitMessage.length > 1) {
      final String command = splitMessage[0];
      final String content = splitMessage[1];
      messageReceived(command, content);
    } else {
      System.err.println("Invalid message sent to server");
    }
  }

  private void messageReceived(String command, String content) {
    switch (command) {
      case "joined" -> {
        username = content;
        System.out.println(username + " joined the chat");
      }
      case "left" -> {
        System.out.println(username + " left the chat");
        username = null;
        inChat = false;
      }
      case "message" -> {
        final String user = username != null ? username : "anonymous";
        final String msg = String.format("[%s]: \u001B[1m%s\u001B[0m", user, content);
        System.out.println(msg);
      }
      default -> {
        System.err.println("Unrecognized command '" + command + "'");
        System.exit(1);
      }
    }
  }
}
