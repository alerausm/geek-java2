public interface GeekChat extends Runnable {
    int SERVER_PORT_DEFAULT = 3345;
    String SERVER_HOST_DEFAULT = "localhost";
    void postMessage(String string);
}
