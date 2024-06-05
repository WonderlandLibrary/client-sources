package net.java.games.input;

import java.io.*;

private final class UploadTask extends LinuxDeviceTask
{
    private int id;
    private float intensity;
    
    public final int doUpload(final int id, final float intensity) throws IOException {
        this.id = id;
        this.intensity = intensity;
        LinuxEnvironmentPlugin.execute(this);
        return this.id;
    }
    
    protected final Object execute() throws IOException {
        this.id = LinuxForceFeedbackEffect.this.upload(this.id, this.intensity);
        return null;
    }
}
