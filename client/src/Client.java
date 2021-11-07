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
      System.out.println("Connected to server as '" + username + "'");

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
    dos.writeUTF("username:" + username);

    while (true) {
      final String message = input("");
      dos.writeUTF("message:" + message);
    }
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
