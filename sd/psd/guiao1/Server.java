import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {
   

    public static void main(String[] args) throws IOException {

        Selector sel = SelectorProvider.provider().openSelector();
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(12345));
        ss.configureBlocking(false);
        ss.register(sel, SelectionKey.OP_ACCEPT);

        List<SelectionKey> clients = new ArrayList<>();
        while (true) {
            sel.select();
            for (Iterator<SelectionKey> i = sel.selectedKeys().iterator(); i.hasNext();) {
                SelectionKey key = i.next();
                // i/o
                if (key.isAcceptable()) {
                    SocketChannel s = ss.accept();
                    s.configureBlocking(false);
                    SelectionKey client = s.register(sel, SelectionKey.OP_READ);
                    clients.add(client);
                }
                else if (key.isReadable()) {
                    ByteBuffer buf = ByteBuffer.allocate(100);
                    //Attachment a = new Attachment(queue);
                    SocketChannel s = (SocketChannel) key.channel();

                    int r = s.read(buf);
                    if (r < 0) {
                        key.cancel();
                        s.close();
                    } else {
                        buf.flip();
                        List<SelectionKey> noSource = new ArrayList<>(clients);
                        //Clients see their own messages
                        noSource.remove(key);
                        for (SelectionKey k : noSource) {
                            k.attach(buf.duplicate());
                            //keep the interest and add write
                            k.interestOpsOr(SelectionKey.OP_WRITE);
                        }
                    }
                }
                else if (key.isWritable()) {
                    ByteBuffer buf = (ByteBuffer) key.attachment();
                    SocketChannel s = (SocketChannel) key.channel();
                    
                    s.write(buf);
                    key.interestOps(SelectionKey.OP_READ);
                }
                i.remove();
            }
        }
    }
}
