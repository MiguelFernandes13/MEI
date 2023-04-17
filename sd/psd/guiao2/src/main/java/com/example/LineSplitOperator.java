package com.example;

import java.nio.ByteBuffer;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableOperator;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class LineSplitOperator implements ObservableOperator<ByteBuffer, ByteBuffer> {

    @Override
    public @NonNull Observer<? super @NonNull ByteBuffer> apply(@NonNull Observer<? super @NonNull ByteBuffer> observer)
            throws Throwable {
        return new Observer<ByteBuffer>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(@NonNull ByteBuffer t) {
                ByteBuffer line = ByteBuffer.allocate(100);
                while(t.hasRemaining()) {
                    byte b = t.get();
                    line.put(b);
                    if (b == '\n' || !line.hasRemaining()) {
                        line.flip();
                        observer.onNext(line);
                        line = ByteBuffer.allocate(100);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                observer.onComplete();
            }
        };
    }
    
}
