package tk.rektsky.Commands;

import net.minecraft.client.*;
import tk.rektsky.Event.*;

public class Command
{
    private String name;
    private String description;
    private String[] aliases;
    private String argumentDescription;
    public Minecraft mc;
    
    public Command(final String name, final String[] aliases, final String argumentDescription, final String description) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.argumentDescription = argumentDescription;
        final Minecraft mc = this.mc;
        this.mc = Minecraft.getMinecraft();
    }
    
    public Command(final String name, final String argumentDescription, final String description) {
        this(name, new String[0], argumentDescription, description);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getArgumentDescription() {
        return this.argumentDescription;
    }
    
    public String[] getAliases() {
        return this.aliases;
    }
    
    public void onEvent(final Event event) {
    }
    
    public void onCommand(final String label, final String[] args) {
    }
}
