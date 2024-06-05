package pw.stamina.causam.publish.listen.decorate.pause;

final class SimplePausable implements Pausable
{
    private boolean paused;
    
    @Override
    public boolean isPaused() {
        return this.paused;
    }
    
    @Override
    public void pause() {
        this.paused = true;
    }
    
    @Override
    public void resume() {
        this.paused = false;
    }
}
