/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Component;
import net.java.games.input.LinuxDeviceTask;
import net.java.games.input.LinuxEnvironmentPlugin;
import net.java.games.input.LinuxEventDevice;
import net.java.games.input.Rumbler;

abstract class LinuxForceFeedbackEffect
implements Rumbler {
    private final LinuxEventDevice device;
    private final int ff_id;
    private final WriteTask write_task = new WriteTask(this, null);
    private final UploadTask upload_task = new UploadTask(this, null);

    public LinuxForceFeedbackEffect(LinuxEventDevice linuxEventDevice) throws IOException {
        this.device = linuxEventDevice;
        this.ff_id = this.upload_task.doUpload(-1, 0.0f);
    }

    protected abstract int upload(int var1, float var2) throws IOException;

    protected final LinuxEventDevice getDevice() {
        return this.device;
    }

    public final synchronized void rumble(float f) {
        try {
            if (f > 0.0f) {
                this.upload_task.doUpload(this.ff_id, f);
                this.write_task.write(1);
            } else {
                this.write_task.write(0);
            }
        } catch (IOException iOException) {
            LinuxEnvironmentPlugin.logln("Failed to rumble: " + iOException);
        }
    }

    public final String getAxisName() {
        return null;
    }

    public final Component.Identifier getAxisIdentifier() {
        return null;
    }

    static int access$200(LinuxForceFeedbackEffect linuxForceFeedbackEffect) {
        return linuxForceFeedbackEffect.ff_id;
    }

    static LinuxEventDevice access$300(LinuxForceFeedbackEffect linuxForceFeedbackEffect) {
        return linuxForceFeedbackEffect.device;
    }

    static class 1 {
    }

    private final class WriteTask
    extends LinuxDeviceTask {
        private int value;
        private final LinuxForceFeedbackEffect this$0;

        private WriteTask(LinuxForceFeedbackEffect linuxForceFeedbackEffect) {
            this.this$0 = linuxForceFeedbackEffect;
        }

        public final void write(int n) throws IOException {
            this.value = n;
            LinuxEnvironmentPlugin.execute(this);
        }

        protected final Object execute() throws IOException {
            LinuxForceFeedbackEffect.access$300(this.this$0).writeEvent(21, LinuxForceFeedbackEffect.access$200(this.this$0), this.value);
            return null;
        }

        WriteTask(LinuxForceFeedbackEffect linuxForceFeedbackEffect, 1 var2_2) {
            this(linuxForceFeedbackEffect);
        }
    }

    private final class UploadTask
    extends LinuxDeviceTask {
        private int id;
        private float intensity;
        private final LinuxForceFeedbackEffect this$0;

        private UploadTask(LinuxForceFeedbackEffect linuxForceFeedbackEffect) {
            this.this$0 = linuxForceFeedbackEffect;
        }

        public final int doUpload(int n, float f) throws IOException {
            this.id = n;
            this.intensity = f;
            LinuxEnvironmentPlugin.execute(this);
            return this.id;
        }

        protected final Object execute() throws IOException {
            this.id = this.this$0.upload(this.id, this.intensity);
            return null;
        }

        UploadTask(LinuxForceFeedbackEffect linuxForceFeedbackEffect, 1 var2_2) {
            this(linuxForceFeedbackEffect);
        }
    }
}

