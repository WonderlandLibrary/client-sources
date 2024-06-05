package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.client.*;

public final class Sprint extends ModBase
{
    private double sprintSpeed;
    
    public Sprint() {
        super("Sprint", "G", true, ".t sprint");
        this.sprintSpeed = 1.05;
        this.setDescription("Sprints for you.");
    }
    
    @Override
    public void preUpdate() {
        Minecraft.thePlayer.setSprinting(true);
    }
}
