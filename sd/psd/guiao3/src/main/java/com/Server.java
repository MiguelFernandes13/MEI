package com;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel ss = ServerSocketChannel.open(new InetSocketAddress(12345));
        Mainloop loop = new Mainloop();
        var ss_obs = loop.accept(ss);
        ss_obs.subscribe(s -> {
            var s_flow = loop.read(conn)
                .observeOn(computation())
                .lift(new LineSplitOperator())
                .map(bb -> StandardCharsets.UTF_8.decode(bb))
                .map(String::toUpperCase)
                .map(s -> StandardCharsets.UTF_8.encode(s));
            loop.write(s_flow, conn);
        });
    }
        
}