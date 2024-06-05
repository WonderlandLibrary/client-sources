package pw.stamina.causam.publish.listen.decorate.pause;

public interface Pausable
{
    boolean isPaused();
    
    void pause();
    
    void resume();
    
    default Pausable simple() {
        return new SimplePausable();
    }
    
    default Pausable atomic() {
        return new AtomicPausable();
    }
}
