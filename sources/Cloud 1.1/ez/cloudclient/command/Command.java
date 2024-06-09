package ez.cloudclient.command;


import net.minecraft.client.Minecraft;

public abstract class Command {

    final String name;
    final String displayName;
    final String description;
    final String[] aliases;
    protected Minecraft mc = Minecraft.getMinecraft();

    public Command(String name, String description, String... aliases) {
        this.name = name;
        this.displayName = name.toLowerCase().replaceAll(" ", "_");
        this.aliases = aliases;
        this.description = description;
    }

    public String getName() {
        return name.replaceAll(" ", "");
    }


    protected abstract void call(String[] args);
}

