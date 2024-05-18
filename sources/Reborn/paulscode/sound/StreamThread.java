package paulscode.sound;

import java.util.*;

public class StreamThread extends SimpleThread
{
    private SoundSystemLogger logger;
    private List streamingSources;
    private final Object listLock;
    
    public StreamThread() {
        this.listLock = new Object();
        this.logger = SoundSystemConfig.getLogger();
        this.streamingSources = new LinkedList();
    }
    
    protected void cleanup() {
        this.kill();
        super.cleanup();
    }
    
    public void run() {
        this.snooze(3600000L);
        while (!this.dying()) {
            while (!this.dying() && !this.streamingSources.isEmpty()) {
                synchronized (this.listLock) {
                    final ListIterator<Source> listIterator = (ListIterator<Source>)this.streamingSources.listIterator();
                    while (!this.dying() && listIterator.hasNext()) {
                        final Source source = listIterator.next();
                        if (source == null) {
                            listIterator.remove();
                        }
                        else if (source.stopped()) {
                            if (source.rawDataStream) {
                                continue;
                            }
                            listIterator.remove();
                        }
                        else if (!source.active()) {
                            if (source.toLoop || source.rawDataStream) {
                                source.toPlay = true;
                            }
                            listIterator.remove();
                        }
                        else {
                            if (source.paused()) {
                                continue;
                            }
                            source.checkFadeOut();
                            if (source.stream() || source.rawDataStream || (source.channel != null && source.channel.processBuffer())) {
                                continue;
                            }
                            if (source.nextCodec == null) {
                                source.readBuffersFromNextSoundInSequence();
                            }
                            if (source.toLoop) {
                                if (source.playing()) {
                                    continue;
                                }
                                SoundSystemConfig.notifyEOS(source.sourcename, source.getSoundSequenceQueueSize());
                                if (source.checkFadeOut()) {
                                    source.preLoad = true;
                                }
                                else {
                                    source.incrementSoundSequence();
                                    source.preLoad = true;
                                }
                            }
                            else {
                                if (source.playing()) {
                                    continue;
                                }
                                SoundSystemConfig.notifyEOS(source.sourcename, source.getSoundSequenceQueueSize());
                                if (source.checkFadeOut()) {
                                    continue;
                                }
                                if (source.incrementSoundSequence()) {
                                    source.preLoad = true;
                                }
                                else {
                                    listIterator.remove();
                                }
                            }
                        }
                    }
                }
                if (!this.dying() && !this.streamingSources.isEmpty()) {
                    this.snooze(20L);
                }
            }
            if (!this.dying() && this.streamingSources.isEmpty()) {
                this.snooze(3600000L);
            }
        }
        this.cleanup();
    }
    
    public void watch(final Source source) {
        if (source == null) {
            return;
        }
        if (this.streamingSources.contains(source)) {
            return;
        }
        synchronized (this.listLock) {
            final ListIterator<Source> listIterator = (ListIterator<Source>)this.streamingSources.listIterator();
            while (listIterator.hasNext()) {
                final Source source2 = listIterator.next();
                if (source2 == null) {
                    listIterator.remove();
                }
                else {
                    if (source.channel != source2.channel) {
                        continue;
                    }
                    source2.stop();
                    listIterator.remove();
                }
            }
            this.streamingSources.add(source);
        }
    }
    
    private void message(final String s) {
        this.logger.message(s, 0);
    }
    
    private void importantMessage(final String s) {
        this.logger.importantMessage(s, 0);
    }
    
    private boolean errorCheck(final boolean b, final String s) {
        return this.logger.errorCheck(b, "StreamThread", s, 0);
    }
    
    private void errorMessage(final String s) {
        this.logger.errorMessage("StreamThread", s, 0);
    }
}
