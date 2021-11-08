import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
  public Server(Port port) throws IOException {
    final ServerSocket server = new ServerSocket(port.getPortNumber());
    System.out.println("Server started on local port '" + server.getLocalPort() + "'");
    System.out.println("\nChat");
    System.out.println("――――");

    while (true) {
      final Socket socket = server.accept();

      final Thread chatThread = new Thread(() -> {
        final Chat chat;
        try {
          chat = createNewChat(socket);
          startChat(chat);
        } catch (IOException e) {
          e.printStackTrace();
          System.exit(ExitCodes.IO_EXCEPTION);
        }
      });

      chatThread.start();
    }
  }

  private void startChat(Chat chat) throws IOException {
    do {
      readInput(chat);
    } while (chat.getUsername() != null);
  }

  private void readInput(Chat chat) throws IOException {
    final String message = chat.dis.readUTF();
    final String[] splitMessage = message.split(":", 2);

    if (splitMessage.length > 1) {
      final String command = splitMessage[0];

      if (chat.getUsername() != null || Objects.equals(command, "joined")) {
        final String content = splitMessage[1];
        messageReceived(chat, command, content);
      }
    } else {
      System.err.println("Invalid message sent to server");
    }
  }

  private void messageReceived(Chat chat, String command, String content) {
    switch (command) {
      case "joined" -> {
        chat.setUsername(content);
        final String msg = String.format("\u001B[33m%s joined the chat\u001B[0m", chat.getUsername());
        System.out.println(msg);
      }
      case "left" -> {
        final String msg = String.format("\u001B[33m%s left the chat\u001B[0m", chat.getUsername());
        System.out.println(msg);
        deleteChat(chat);
      }
      case "message" -> {
        final String msg = String.format("\u001B[34m%s\u001B[0m: \u001B[1m%s\u001B[0m", chat.getUsername(), content);
        System.out.println(msg);
      }
      default -> {
        System.err.println("Unrecognized command '" + command + "'");
        System.exit(ExitCodes.UNRECOGNIZED_COMMAND);
      }
    }
  }

  private Chat createNewChat(Socket socket) throws IOException {
    final InputStream is = socket.getInputStream();
    final DataInputStream dis = new DataInputStream(is);
    return new Chat(dis);
  }

  private void deleteChat(Chat chat) {
    chat.setUsername(null);
  }
}
