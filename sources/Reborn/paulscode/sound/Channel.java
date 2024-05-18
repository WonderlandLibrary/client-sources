package paulscode.sound;

import java.util.*;
import javax.sound.sampled.*;

public class Channel
{
    protected Class libraryType;
    public int channelType;
    private SoundSystemLogger logger;
    public Source attachedSource;
    public int buffersUnqueued;
    
    public Channel(final int channelType) {
        this.libraryType = Library.class;
        this.attachedSource = null;
        this.buffersUnqueued = 0;
        this.logger = SoundSystemConfig.getLogger();
        this.channelType = channelType;
    }
    
    public void cleanup() {
        this.logger = null;
    }
    
    public boolean preLoadBuffers(final LinkedList list) {
        return true;
    }
    
    public boolean queueBuffer(final byte[] array) {
        return false;
    }
    
    public int feedRawAudioData(final byte[] array) {
        return 1;
    }
    
    public int buffersProcessed() {
        return 0;
    }
    
    public float millisecondsPlayed() {
        return -1.0f;
    }
    
    public boolean processBuffer() {
        return false;
    }
    
    public void setAudioFormat(final AudioFormat audioFormat) {
    }
    
    public void flush() {
    }
    
    public void close() {
    }
    
    public void play() {
    }
    
    public void pause() {
    }
    
    public void stop() {
    }
    
    public void rewind() {
    }
    
    public boolean playing() {
        return false;
    }
    
    public String getClassName() {
        final String libraryTitle = SoundSystemConfig.getLibraryTitle(this.libraryType);
        if (libraryTitle.equals("No Sound")) {
            return "Channel";
        }
        return "Channel" + libraryTitle;
    }
    
    protected void message(final String s) {
        this.logger.message(s, 0);
    }
    
    protected void importantMessage(final String s) {
        this.logger.importantMessage(s, 0);
    }
    
    protected boolean errorCheck(final boolean b, final String s) {
        return this.logger.errorCheck(b, this.getClassName(), s, 0);
    }
    
    protected void errorMessage(final String s) {
        this.logger.errorMessage(this.getClassName(), s, 0);
    }
    
    protected void printStackTrace(final Exception ex) {
        this.logger.printStackTrace(ex, 1);
    }
}
