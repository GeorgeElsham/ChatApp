import java.io.*;

public class Chat {
  public final DataInputStream dis;
  private String username;

  public Chat(DataInputStream dis) {
    this.dis = dis;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;

    if (username == null) {
      try {
        dis.close();
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(ExitCodes.IO_EXCEPTION);
      }
    }
  }
}
