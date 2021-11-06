import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
  private final Socket client;

  public Client(Port port) throws IOException {
    final String username = input("Enter username: ");
    client = new Socket("localhost", port.getPortNumber());

    if (client.isConnected()) {
      System.out.println("Connected to server as '" + username + "'");

      final OutputStream os = client.getOutputStream();
      final DataOutputStream dos = new DataOutputStream(os);
      dos.writeUTF("Hello world!");
    } else {
      System.err.println("Failed to connect to server");
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
