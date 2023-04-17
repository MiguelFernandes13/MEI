package com;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Server {
    public static void main(String[] args) throws Exception {
        //Exercice 1: Test back pressures strategies using Flowable.interval() and Thread.sleep()
        //Flowable.interval(1, TimeUnit.MILLISECONDS)
        //        .map(i ->
        //        {
        //            System.out.println("Emitting " + i);
        //            return i;
        //        })
        //        //remove BackpressureDrop() and the program will crash duo to lack of requests
        //        .onBackpressureDrop()
        //        .observeOn(Schedulers.computation())
        //        .map(i ->
        //        {
        //            System.out.println("Before " + i);
        //            Thread.sleep(200);
        //            System.out.println("After " + i);
        //            return i;
        //        })
        //        .blockingSubscribe();

        //ServerSocketChannel ss = ServerSocketChannel.open();
        //ss.bind(new InetSocketAddress(12345));
        //Mainloop loop = new Mainloop();
        //var ss_obs = loop.accept(ss);
        //ss_obs.subscribe(s -> {
        //    var s_flow = loop.read(conn)
        //        .observeOn(computation())
        //        .lift(new LineSplitOperator())
        //        .map(bb -> StandardCharsets.UTF_8.decode(bb))
        //        .map(String::toUpperCase)
        //        .map(s -> StandardCharsets.UTF_8.encode(s));
        //    loop.write(s_flow, conn);
        //});
    }
        
}