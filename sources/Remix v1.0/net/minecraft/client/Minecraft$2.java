package net.minecraft.client;

class Minecraft$2 extends Thread {
    @Override
    public void run() {
        while (Minecraft.this.running) {
            try {
                Thread.sleep(2147483647L);
            }
            catch (InterruptedException ex) {}
        }
    }
}