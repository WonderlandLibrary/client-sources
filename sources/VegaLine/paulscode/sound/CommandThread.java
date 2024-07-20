/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package paulscode.sound;

import paulscode.sound.SimpleThread;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemLogger;

public class CommandThread
extends SimpleThread {
    protected SoundSystemLogger logger = SoundSystemConfig.getLogger();
    private SoundSystem soundSystem;
    protected String className = "CommandThread";

    public CommandThread(SoundSystem s) {
        this.soundSystem = s;
    }

    protected void cleanup() {
        this.kill();
        this.logger = null;
        this.soundSystem = null;
        super.cleanup();
    }

    public void run() {
        long previousTime;
        long currentTime = previousTime = System.currentTimeMillis();
        if (this.soundSystem == null) {
            this.errorMessage("SoundSystem was null in method run().", 0);
            this.cleanup();
            return;
        }
        this.snooze(3600000L);
        while (!this.dying()) {
            this.soundSystem.ManageSources();
            this.soundSystem.CommandQueue(null);
            currentTime = System.currentTimeMillis();
            if (!this.dying() && currentTime - previousTime > 10000L) {
                previousTime = currentTime;
                this.soundSystem.removeTemporarySources();
            }
            if (this.dying()) continue;
            this.snooze(3600000L);
        }
        this.cleanup();
    }

    protected void message(String message, int indent) {
        this.logger.message(message, indent);
    }

    protected void importantMessage(String message, int indent) {
        this.logger.importantMessage(message, indent);
    }

    protected boolean errorCheck(boolean error, String message) {
        return this.logger.errorCheck(error, this.className, message, 0);
    }

    protected void errorMessage(String message, int indent) {
        this.logger.errorMessage(this.className, message, indent);
    }
}

