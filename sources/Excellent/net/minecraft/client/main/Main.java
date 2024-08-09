package net.minecraft.client.main;

public class Main {

    public static void main(String[] args) {
        Starter.start();
    }

    static {
        System.setProperty("java.awt.headless", "true");
    }
}