package net.java.games.input;

import java.io.*;

private final class WriteTask extends LinuxDeviceTask
{
    private int value;
    
    public final void write(final int value) throws IOException {
        this.value = value;
        LinuxEnvironmentPlugin.execute(this);
    }
    
    protected final Object execute() throws IOException {
        LinuxForceFeedbackEffect.access$300(LinuxForceFeedbackEffect.this).writeEvent(21, LinuxForceFeedbackEffect.access$200(LinuxForceFeedbackEffect.this), this.value);
        return null;
    }
}
