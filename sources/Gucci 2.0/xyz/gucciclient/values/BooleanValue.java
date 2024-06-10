package xyz.gucciclient.values;

public class BooleanValue
{
    private String name;
    private boolean value;
    
    public BooleanValue(final String name, final boolean value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public boolean getState() {
        return this.value;
    }
    
    public void toggle() {
        this.value = !this.value;
    }
    
    public void setState(final boolean state) {
        if (state == this.value) {
            return;
        }
        this.value = state;
    }
}
