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

public class Server {
   

    public static void main(String[] args) throws IOException {

        //Selector sel = SelectorProvider.provider().openSelector();
        //ServerSocketChannel ss = ServerSocketChannel.open(new InetSocketAddress(12345));
        MainLoop loop = new MainLoop();
        loop.run();
        Observable<SocketChannel> server = loop.accept(ss);
    }
}
