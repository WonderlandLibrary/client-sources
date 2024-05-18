package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import org.lwjgl.util.*;
import net.minecraft.client.*;
import me.darkmagician6.morbid.*;

public final class Test extends ModBase
{
    private int clickSlot;
    private final Timer timer;
    Minecraft mc;
    
    public Test() {
        super("Test", "L", true, ".t test");
        this.clickSlot = -1;
        this.timer = new Timer();
        this.mc = MorbidWrapper.mcObj();
        this.setDescription("Aims at players with a bow.");
    }
    
    @Override
    public void onToggle() {
    }
}
