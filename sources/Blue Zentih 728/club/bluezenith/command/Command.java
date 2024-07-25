package club.bluezenith.command;

import club.bluezenith.util.client.ClientUtils;
import club.bluezenith.util.MinecraftInstance;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class Command extends MinecraftInstance {
    public boolean isScript;
    public String name;
    public String[] pref;
    public String description;
    public String syntax;
    public Command(String name, String description, String syntax, String... pref){
        this.name = name;
        this.pref = pref;
        this.description = description;
        this.syntax = syntax;
    }
    public void changedSound(){
        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("mob.cat.purreow")));
    }
    public void execute(String[] args){

    }
    public void chat(String f){
        ClientUtils.displayChatMessage(String.format("§r§b%s §r§7\u00bb " + f, name));
    }
}
