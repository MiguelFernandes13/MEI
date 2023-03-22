import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Chat {
    private List<ByteBuffer> queue = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition c = lock.newCondition();


    private void insert(ByteBuffer buffer) {
        try {
            lock.lock();
            queue.add(buffer);
            c.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private ByteBuffer get(int i) throws InterruptedException
    {
        try {
            lock.lock();
            while (queue.size() <= i) {
                c.await();
            }
            return queue.get(i);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws IOException {
        var state = new Chat();

        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(12345));

        while(true){
            SocketChannel s = ss.accept();
            Thread reader = new Thread(() -> read(state, s));
            reader.start();
            Thread writer = new Thread(() -> write(state, s));
            writer.start();
            
        }
    }

    public static void read(Chat state, SocketChannel s) {
        try{

            while(true){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                if (s.read(buffer) < 0) {
                    break;
                }
                buffer.flip();
                state.insert(buffer);
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void write(Chat state, SocketChannel s){
        int next = 0;
        try{
            while(true){
                var buffer = state.get(next++);
                s.write(buffer.duplicate());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
