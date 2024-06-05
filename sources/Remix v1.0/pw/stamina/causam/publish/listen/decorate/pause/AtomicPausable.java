package pw.stamina.causam.publish.listen.decorate.pause;

import java.util.concurrent.atomic.*;

final class AtomicPausable implements Pausable
{
    private final AtomicBoolean paused;
    
    AtomicPausable() {
        this.paused = new AtomicBoolean();
    }
    
    @Override
    public boolean isPaused() {
        return this.paused.get();
    }
    
    @Override
    public void pause() {
        this.paused.set(true);
    }
    
    @Override
    public void resume() {
        this.paused.set(false);
    }
}
