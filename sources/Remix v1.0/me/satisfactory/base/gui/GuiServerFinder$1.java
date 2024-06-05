package me.satisfactory.base.gui;

class GuiServerFinder$1 implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000L);
            GuiServerFinder.access$0(GuiServerFinder.this, "");
        }
        catch (InterruptedException ex) {}
    }
}