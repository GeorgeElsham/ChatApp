import java.io.IOException;
import java.net.ConnectException;

public class ClientApp {
  public static void main(String[] args) {
    final ClientApp app = new ClientApp();
    final String host = args.length > 0 ? args[0] : "localhost";
    app.run(host);
  }

  public void run(String host) {
    try {
      new Client(host, new Port(8000));
    } catch (ConnectException connectException) {
      System.err.println(connectException.getLocalizedMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
