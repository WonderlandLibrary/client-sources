package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.src.*;

public final class FastBow extends ModBase
{
    Item setItem;
    
    public FastBow() {
        super("FastBow", "0", true, ".t fastbow");
        this.setItem = Item.bow;
        this.setDescription("Spari velocemente.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            final Thread var1 = new Thread(new FastBow$FastBowThread(this, this), "FastBow Thread");
            var1.start();
        }
    }
}
