package com.example;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class Main {
    int op1(int i){
        try{
            Thread.sleep(i);
        }
        catch(InterruptedException e){}
        return i + 1;
    }

    Flowable<Integer> rxOp1(int i){
        return Flowable.just(i)
            .delay(i, TimeUnit.SECONDS)
            .map(j -> j + 1);
    }

    int op2(int i){
        return i*2;
    }

    int m1(){
        int i = 1;
        i = op1(i) + 1;
        i = op2(i);
        return op1(i);
    }

    Flowable<Integer> rx1(){
        return Flowable.just(1)
            .flatMap(i -> rxOp1(i))
            .map(i -> i + 1)
            .map(i -> op2(i))
            .flatMap(i -> rxOp1(i));
    }

    public void run(){
        System.out.println("Resultado = " + m1());
    }

    public void rxRun(){
        rx1().blockingSubscribe(r ->{
            System.out.println("Resultado = " + r);
        });
    }

    int m2() { int i=op1(1)+op1(2); return op2(i); }

    class Vars 
    { 
        int i1, i2, i;

        public Vars() {
            this.i1 = 1;
            this.i2 = 2;
            this.i = 0;
        }

        public Vars(int i1, int i2, int i) {
            this.i1 = i1;
            this.i2 = i2;
            this.i = i;
        }

        
    }

    Flowable<Integer> rxM2(){
        Flowable.just(new Vars())
            .flatMap(v -> rxOp1(1))
                .map(r -> new Vars(r, v.i2, v.i))
            .flatMap(v -> rxOp1(2))
                .map(r -> new Vars(v.i1, r, v.i))
            .map(v -> Vars(v.i1 + v.i2, v.i2, v.i1 + v.i2)))
            .map(v -> op2(v.i)); 
    }

    Single<Integer> rxM2Melhorada(){
        return Flowable.just(1, 2)
            .flatMap(i -> rxOp1(1))
            .reduce(0, (a, b) -> a + b)
            .map(i -> op2(i)); 
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.run();
        m.rxRun();
    }
}
