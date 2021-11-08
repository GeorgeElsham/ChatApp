import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
  private final Socket client;
  private DataOutputStream dos;
  private String username;

  public Client(Port port) throws IOException {
    do {
      username = input("Enter username: ");
    } while (!username.matches("[a-zA-Z0-9]+"));

    client = new Socket("localhost", port.getPortNumber());

    if (client.isConnected()) {
      final OutputStream os = client.getOutputStream();
      dos = new DataOutputStream(os);
      enterChat();
    } else {
      System.err.println("Failed to connect to server");
      System.exit(ExitCodes.CONNECTION_FAILED);
    }
  }

  private void enterChat() throws IOException {
    System.out.println("\nChat");
    System.out.println("――――");
    System.out.println(username + " (you) joined the chat");
    dos.writeUTF("joined:" + username);

    boolean inChat = true;

    while (inChat) {
      final String msg = String.format("\u001B[34m%s\u001B[0m: ", username);
      final String message = input(msg).strip();

      if (message.isEmpty()) {
        exitChat();
        inChat = false;
      } else {
        dos.writeUTF("message:" + message);
      }
    }
  }

  private void exitChat() throws IOException {
    dos.writeUTF("left:" + username);
    System.out.println("―――――――――");
    System.out.println("\nYou have left the chat");
  }

  /**
   * Asks for console input.
   *
   * @param message Question to ask in input.
   * @return Text inputted.
   */
  private String input(String message) {
    System.out.print(message);
    final Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
}
