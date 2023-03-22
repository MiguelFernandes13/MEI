package com.example;

import java.nio.ByteBuffer;

public interface BufferCallBack {
    public void onNext(ByteBuffer buf);
    public void onError(Throwable t);
    public void onComplete();
}
