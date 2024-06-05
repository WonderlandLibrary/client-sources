package net.java.games.input;

private final class ShutdownHook extends Thread
{
    public final void run() {
        for (int i = 0; i < WinTabEnvironmentPlugin.access$200(WinTabEnvironmentPlugin.this).size(); ++i) {}
        WinTabEnvironmentPlugin.access$300(WinTabEnvironmentPlugin.this).close();
    }
}
