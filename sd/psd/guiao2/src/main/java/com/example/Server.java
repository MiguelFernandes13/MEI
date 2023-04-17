package com.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.lang.Thread;

public class Server {
   

    public static void main(String[] args) throws IOException, InterruptedException {
        
        //Simple example
        //Observable.just("a", "b", "c")
        //          .subscribe(m -> {
        //                System.out.println("received " + m);
        //          });
    

        //Asynchronous observable and cancelation
        //Disposable d = Observable.interval(1, TimeUnit.SECONDS)
        //                         .subscribe(m -> {
        //                             System.out.println("received " + m);
        //                         });
        //Thread.sleep(10000);
        //d.dispose();
        //Exercice 2:
        //Observable.just("xyz\n", "abc\n123", "456\n", "def")
        //          .map(s -> StandardCharsets.UTF_8.encode(s))
        //          .lift(new LineSplitOperator())
        //          .map(bb -> StandardCharsets.UTF_8.decode(bb))
        //          .subscribe(s -> System.out.println(s));
        
        //Exercice 3:
        MainLoop loop = new MainLoop();
        loop.run();
    }
}
