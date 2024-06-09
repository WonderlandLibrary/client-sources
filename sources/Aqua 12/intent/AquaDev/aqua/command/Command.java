// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command;

public class Command
{
    private String name;
    
    public Command(final String name) {
        this.name = name;
    }
    
    public void execute(final String[] args) {
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
