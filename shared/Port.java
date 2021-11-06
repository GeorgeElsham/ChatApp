public class Port {
  private final int port;

  /**
   * Create an object given a port number.
   *
   * @param port Port number.
   */
  public Port(int port) {
    this.port = port;
  }

  /**
   * Get port number.
   *
   * @return Numeric port number.
   */
  public int getPortNumber() {
    return port;
  }
}
