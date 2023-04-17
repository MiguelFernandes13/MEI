package com.example;

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

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;

public class MainLoop {
    Selector sel;

    public MainLoop() {
        try {
            sel = SelectorProvider.provider().openSelector();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Observable<ByteBuffer> read(SocketChannel s) {
        return Observable.create(sub -> {
                s.configureBlocking(false);
                s.register(sel, SelectionKey.OP_READ, sub);
        });
    }

    public Observable<SocketChannel> accept(ServerSocketChannel s) throws IOException {
        return Observable.create(sub -> {
                s.configureBlocking(false);
                s.register(sel, SelectionKey.OP_ACCEPT, sub);
        });
    }
    
    public void readAndSubscribe(SocketChannel s, BufferCallBack cb) throws IOException {
        s.configureBlocking(false);
        s.register(sel, SelectionKey.OP_READ, cb);   
    }

    public void run() throws IOException {
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(12345));
        ss.configureBlocking(false);
        ss.register(sel, SelectionKey.OP_ACCEPT);

        while (true) {
            sel.select();
            for (Iterator<SelectionKey> i = sel.selectedKeys().iterator(); i.hasNext();) {
                SelectionKey key = i.next();
                // i/o
                if (key.isAcceptable()) {
                    SocketChannel s = ss.accept();
                    ObservableEmitter<SocketChannel> sub = (ObservableEmitter<SocketChannel>) Observable.create(sub -> {
                        s.configureBlocking(false);
                        s.register(sel, SelectionKey.OP_READ, sub);
                    });
                    sub.onNext(s);
                    
                }
                else if (key.isReadable()) {
                    //BufferCallBack cb = (BufferCallBack) key.attachment();
                    ObservableEmitter<ByteBuffer> sub = (ObservableEmitter<ByteBuffer>) key.attachment();
                    SocketChannel s = (SocketChannel) key.channel();
                    ByteBuffer buf = ByteBuffer.allocate(100);
                    try{

                        int r = s.read(buf);
                        if (r > 0){
                            buf.flip();
                            System.out.println("received " + buf.remaining());
                            sub.onNext(buf);
                        }
                        else{
                            sub.onComplete();
                            s.close();
                        }               
                    } catch (IOException e) {
                        sub.onError(e);
                        s.close();
                    }
                }
            }
        }
    }
}
