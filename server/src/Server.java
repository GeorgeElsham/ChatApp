import java.io.*;
import java.net.*;

public class Server {
  private final ServerSocket server;

  public Server(Port port) throws IOException {
    server = new ServerSocket(port.getPortNumber());
    System.out.println("Server started on local port '" + server.getLocalPort() + "'");

    final Socket socket = server.accept();
    System.out.println("Accepted client socket");

    final InputStream is = socket.getInputStream();
    final DataInputStream dis = new DataInputStream(is);
    System.out.println("Socket output: " + dis.readUTF());
  }
}
