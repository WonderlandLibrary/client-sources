package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.util.misc.*;
import org.lwjgl.opengl.*;
import net.minecraft.src.*;
import me.enrythebest.reborn.cracked.util.*;
import me.enrythebest.reborn.cracked.*;

public final class Search extends ModBase
{
    private int counter;
    private int size;
    private int blockID;
    public static BlockCoord[] espBlocks;
    
    static {
        Search.espBlocks = new BlockCoord[10000000];
    }
    
    public Search() {
        super("Search", "O", true);
        this.counter = 0;
        this.size = 0;
        this.blockID = 0;
        this.setDescription("Draws boxes arround searched blocks.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.blockID != 0) {
            ++this.counter;
            if (this.counter >= 120) {
                this.refresh();
                this.counter = 0;
            }
            GL11.glLineWidth(1.0f);
            GL11.glColor3f(1.0f, 0.25f, 0.0f);
            GL11.glEnable(2848);
            GL11.glDisable(3553);
            for (int var1 = 0; var1 < this.size; ++var1) {
                final BlockCoord var2 = Search.espBlocks[var1];
                final double var3 = var2.getDeltaX();
                final double var4 = var2.getDeltaY();
                final double var5 = var2.getDeltaZ();
                GLHelper.drawOutlinedBoundingBox(new AxisAlignedBB(var3 + 1.0, var4 + 1.0, var5 + 1.0, var3, var4, var5));
            }
            GL11.glEnable(3553);
            GL11.glDisable(2848);
        }
    }
    
    @Override
    public void onCommand(final String var1) {
        final String[] var2 = var1.split(" ");
        if (var1.toLowerCase().startsWith(".s")) {
            try {
                final int var3 = Integer.parseInt(var2[1]);
                this.blockID = var3;
                final int var4 = Integer.parseInt(var2[1]);
                if (var4 <= 0) {
                    this.setEnabled(false);
                    this.getWrapper();
                    MorbidWrapper.addChat("Stopped searching.");
                }
                else {
                    this.setEnabled(true);
                    this.getWrapper();
                    MorbidWrapper.addChat("Now searching for: " + var2[1]);
                }
            }
            catch (Exception var5) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .s add [block ID]");
            }
            ModBase.setCommandExists(true);
        }
    }
    
    public void refresh() {
        if (this.isEnabled()) {
            this.size = 0;
            final byte var1 = 64;
            for (int var2 = 0; var2 < 128; ++var2) {
                for (int var3 = 0; var3 < var1; ++var3) {
                    for (int var4 = 0; var4 < var1; ++var4) {
                        this.getWrapper();
                        final int var5 = (int)MorbidWrapper.getPlayer().posX - var1 / 2 + var3;
                        this.getWrapper();
                        final int var6 = (int)MorbidWrapper.getPlayer().posZ - var1 / 2 + var4;
                        this.getWrapper();
                        final int var7 = MorbidWrapper.getWorld().getBlockId(var5, var2, var6);
                        if (this.blockID == var7) {
                            Search.espBlocks[this.size++] = new BlockCoord(var5, var2, var6);
                        }
                    }
                }
            }
        }
    }
}
