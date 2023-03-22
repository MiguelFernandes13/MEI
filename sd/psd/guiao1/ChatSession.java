import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ChatSession {
    private List<ByteBuffer> queue = new ArrayList<>();
    private int i = 0;
    

    public ChatSession(List<ByteBuffer> queue) {
        this.queue = queue;
        this.i = 0;
    }

    public void insert(ByteBuffer buffer) {
        queue.add(buffer);
    }

    public ByteBuffer get(int i) {
        if (queue.size() > i) {
            return queue.get(i);
        }
        return null;
    }

}
