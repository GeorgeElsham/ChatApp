import java.io.IOException;

public class ServerApp {
  public static void main(String[] args) {
    final ServerApp app = new ServerApp();
    app.run();
  }

  public void run() {
    try {
      final Server server = new Server(new Port(8000));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
