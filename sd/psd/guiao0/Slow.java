import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Slow {
    public static void main(String[] args) throws Exception {
        SocketChannel s = SocketChannel.open(new InetSocketAddress("localhost", 12345));
    }
}
