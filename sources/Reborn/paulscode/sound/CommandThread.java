package paulscode.sound;

public class CommandThread extends SimpleThread
{
    protected SoundSystemLogger logger;
    private SoundSystem soundSystem;
    protected String className;
    
    public CommandThread(final SoundSystem soundSystem) {
        this.className = "CommandThread";
        this.logger = SoundSystemConfig.getLogger();
        this.soundSystem = soundSystem;
    }
    
    protected void cleanup() {
        this.kill();
        this.logger = null;
        this.soundSystem = null;
        super.cleanup();
    }
    
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.soundSystem == null) {
            this.errorMessage("SoundSystem was null in method run().", 0);
            this.cleanup();
            return;
        }
        this.snooze(3600000L);
        while (!this.dying()) {
            this.soundSystem.ManageSources();
            this.soundSystem.CommandQueue(null);
            final long currentTimeMillis2 = System.currentTimeMillis();
            if (!this.dying() && currentTimeMillis2 - currentTimeMillis > 10000L) {
                currentTimeMillis = currentTimeMillis2;
                this.soundSystem.removeTemporarySources();
            }
            if (!this.dying()) {
                this.snooze(3600000L);
            }
        }
        this.cleanup();
    }
    
    protected void message(final String s, final int n) {
        this.logger.message(s, n);
    }
    
    protected void importantMessage(final String s, final int n) {
        this.logger.importantMessage(s, n);
    }
    
    protected boolean errorCheck(final boolean b, final String s) {
        return this.logger.errorCheck(b, this.className, s, 0);
    }
    
    protected void errorMessage(final String s, final int n) {
        this.logger.errorMessage(this.className, s, n);
    }
}
