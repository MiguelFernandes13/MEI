package com;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import static io.reactivex.rxjava3.schedulers.Schedulers.computation;


public class Backpressure {
    public static void main(String[] args) {
        //Observable.interval(1, TimeUnit.MILLISECONDS)
        //        .map(i-> {
        //            System.out.println("produzi: " + i);
        //            return i;
        //        })
        //        .observeOn(computation()) // esta linha faz diferenca
        //        .map(i->  {
        //            System.out.println("antes: " + i);
        //            Thread.sleep(200);
        //            System.out.println("depois " + i);
        //            return  i;
        //        })
        //        .blockingSubscribe();

        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .map(i ->
                {
                    //System.out.println("Emitting " + i);
                    return i;
                })
                .onBackpressureDrop()
                .observeOn(computation())
                .map(i ->
                {
                    System.out.println("Before " + i);
                    Thread.sleep(200);
                    System.out.println("After " + i);
                    return i;
                })
                .blockingSubscribe();
    }
}
