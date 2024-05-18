package baritone.api.process;

public interface IMineProcess extends IBaritoneProcess
{
    void mineByName(final int p0, final String... p1);
    
    void mine(final int p0, final aow... p1);
    
    default void mineByName(final String... array) {
        this.mineByName(0, array);
    }
    
    default void mine(final aow... array) {
        this.mine(0, array);
    }
    
    default void cancel() {
        this.onLostControl();
    }
}
