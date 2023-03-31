package com.example;

import java.nio.ByteBuffer;

public class LineSplitLayer implements BufferCallBack {
    private BufferCallBack cb;

    
    public LineSplitLayer() {
    }

    public void subscribe(BufferCallBack cb) {
        this.cb = cb;
    }

    @Override
    public void onComplete() {
        cb.onComplete();
        
    }

    @Override
    public void onError(Throwable t) {
        cb.onError(t);        
    }

    @Override
    public void onNext(ByteBuffer bb) {
        ByteBuffer line = ByteBuffer.allocate(100);
        while(bb.hasRemaining()) {
            byte b = bb.get(); line.put(b);
            if (b == '\n' || !line.hasRemaining()) {
                line.flip();
                cb.onNext(line);
                line = ByteBuffer.allocate(100);
                }
            }
            
        
    }
    
}
