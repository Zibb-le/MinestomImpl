package org.zibble.minestom;

public class MinestomImpl {

    public static Server server;

    public static void main(String[] args) {
        server = new Server(25592);
        server.init();
    }

}
