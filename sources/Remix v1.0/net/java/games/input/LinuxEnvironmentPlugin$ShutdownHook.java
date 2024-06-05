package net.java.games.input;

import java.io.*;

private final class ShutdownHook extends Thread
{
    public final void run() {
        for (int i = 0; i < LinuxEnvironmentPlugin.access$200(LinuxEnvironmentPlugin.this).size(); ++i) {
            try {
                final LinuxDevice device = LinuxEnvironmentPlugin.access$200(LinuxEnvironmentPlugin.this).get(i);
                device.close();
            }
            catch (IOException e) {
                ControllerEnvironment.logln("Failed to close device: " + e.getMessage());
            }
        }
    }
}
