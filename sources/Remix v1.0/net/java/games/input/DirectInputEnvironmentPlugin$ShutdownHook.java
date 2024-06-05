package net.java.games.input;

private final class ShutdownHook extends Thread
{
    public final void run() {
        for (int i = 0; i < DirectInputEnvironmentPlugin.access$200(DirectInputEnvironmentPlugin.this).size(); ++i) {
            final IDirectInputDevice device = DirectInputEnvironmentPlugin.access$200(DirectInputEnvironmentPlugin.this).get(i);
            device.release();
        }
    }
}
