import java.io.IOException;
import java.net.ConnectException;

public class ClientApp {
  public static void main(String[] args) {
    final ClientApp app = new ClientApp();
    app.run();
  }

  public void run() {
    try {
      final Client client = new Client("localhost", new Port(8000));
    } catch (ConnectException connectException) {
      System.err.println(connectException.getLocalizedMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
