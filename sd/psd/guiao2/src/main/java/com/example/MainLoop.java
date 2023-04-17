package com.example;

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

    public MainLoop() {
    }

    public Observable<ByteBuffer> read(SocketChannel s) {
        return Observable.create(sub -> {
                s.configureBlocking(false);
                s.register(sel, SelectionKey.OP_READ, sub);
        });
    }

    public Observable<SocketChannel> accept(SocketChannel s) {
        return Observable.create(sub -> {
        s.configureBlocking(false);
        s.register(sel, SelectionKey.OP_ACCEPT, sub);
        });
    }
    
    public void readAndSubscribe(SocketChannel s, BufferCallBack cb) {
        s.configureBlocking(false);
        s.register(sel, SelectionKey.OP_READ, cb);   
    }

    public void run() {
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
                    ObservableEmitter<SocketChannel> sub = (ObservableEmitter<SocketChannel>) key.attachment();
                    sub.onNext(s);
                }
                else if (key.isReadable()) {
                    ObservableEmitter<SocketChannel> sub = (ObservableEmitter<SocketChannel>) key.attachment();
                    SocketChannel s = (SocketChannel) key.channel();
                    
                    sub.onNext(s);

                }
            }
        }


}
