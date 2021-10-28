package com.pepedevs.minestom;

public class MinestomImpl {

    public static Server server;

    public static void main(String[] args) {
        server = new Server(25566);
        server.init();
    }

}
