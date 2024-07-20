/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package paulscode.sound;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import paulscode.sound.SimpleThread;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.Source;

public class StreamThread
extends SimpleThread {
    private SoundSystemLogger logger;
    private List<Source> streamingSources;
    private final Object listLock = new Object();

    public StreamThread() {
        this.logger = SoundSystemConfig.getLogger();
        this.streamingSources = new LinkedList<Source>();
    }

    protected void cleanup() {
        this.kill();
        super.cleanup();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        this.snooze(3600000L);
        while (!this.dying()) {
            while (!this.dying() && !this.streamingSources.isEmpty()) {
                Object object = this.listLock;
                synchronized (object) {
                    ListIterator<Source> iter = this.streamingSources.listIterator();
                    while (!this.dying() && iter.hasNext()) {
                        Source src = iter.next();
                        if (src == null) {
                            iter.remove();
                            continue;
                        }
                        if (src.stopped()) {
                            if (src.rawDataStream) continue;
                            iter.remove();
                            continue;
                        }
                        if (!src.active()) {
                            if (src.toLoop || src.rawDataStream) {
                                src.toPlay = true;
                            }
                            iter.remove();
                            continue;
                        }
                        if (src.paused()) continue;
                        src.checkFadeOut();
                        if (src.stream() || src.rawDataStream || src.channel != null && src.channel.processBuffer()) continue;
                        if (src.nextCodec == null) {
                            src.readBuffersFromNextSoundInSequence();
                        }
                        if (src.toLoop) {
                            if (src.playing()) continue;
                            SoundSystemConfig.notifyEOS(src.sourcename, src.getSoundSequenceQueueSize());
                            if (src.checkFadeOut()) {
                                src.preLoad = true;
                                continue;
                            }
                            src.incrementSoundSequence();
                            src.preLoad = true;
                            continue;
                        }
                        if (src.playing()) continue;
                        SoundSystemConfig.notifyEOS(src.sourcename, src.getSoundSequenceQueueSize());
                        if (src.checkFadeOut()) continue;
                        if (src.incrementSoundSequence()) {
                            src.preLoad = true;
                            continue;
                        }
                        iter.remove();
                    }
                }
                if (this.dying() || this.streamingSources.isEmpty()) continue;
                this.snooze(20L);
            }
            if (this.dying() || !this.streamingSources.isEmpty()) continue;
            this.snooze(3600000L);
        }
        this.cleanup();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void watch(Source source) {
        if (source == null) {
            return;
        }
        if (this.streamingSources.contains(source)) {
            return;
        }
        Object object = this.listLock;
        synchronized (object) {
            ListIterator<Source> iter = this.streamingSources.listIterator();
            while (iter.hasNext()) {
                Source src = iter.next();
                if (src == null) {
                    iter.remove();
                    continue;
                }
                if (source.channel != src.channel) continue;
                src.stop();
                iter.remove();
            }
            this.streamingSources.add(source);
        }
    }

    private void message(String message) {
        this.logger.message(message, 0);
    }

    private void importantMessage(String message) {
        this.logger.importantMessage(message, 0);
    }

    private boolean errorCheck(boolean error, String message) {
        return this.logger.errorCheck(error, "StreamThread", message, 0);
    }

    private void errorMessage(String message) {
        this.logger.errorMessage("StreamThread", message, 0);
    }
}

