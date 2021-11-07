import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
  private final Socket client;

  public Client(Port port) throws IOException {
    String username;
    do {
      username = input("Enter username: ");
    } while (!username.matches("[a-zA-Z0-9]+"));

    client = new Socket("localhost", port.getPortNumber());

    if (client.isConnected()) {
      System.out.println("Connected to server as '" + username + "'");

      final OutputStream os = client.getOutputStream();
      final DataOutputStream dos = new DataOutputStream(os);
      dos.writeUTF("username:" + username);
      dos.writeUTF("message:Hello world!");
    } else {
      System.err.println("Failed to connect to server");
      System.exit(ExitCodes.CONNECTION_FAILED);
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
