package net.java.games.input;

private static final class Key extends AbstractComponent
{
    private float value;
    
    public Key(final Component.Identifier.Key key_id) {
        super(key_id.getName(), key_id);
    }
    
    public final void setValue(final float value) {
        this.value = value;
    }
    
    protected final float poll() {
        return this.value;
    }
    
    public final boolean isAnalog() {
        return false;
    }
    
    public final boolean isRelative() {
        return false;
    }
}
