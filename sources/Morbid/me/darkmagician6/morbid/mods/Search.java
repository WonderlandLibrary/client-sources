package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.util.misc.*;
import org.lwjgl.opengl.*;
import me.darkmagician6.morbid.util.*;
import me.darkmagician6.morbid.*;

public final class Search extends ModBase
{
    private int counter;
    private int size;
    private int blockID;
    public static BlockCoord[] espBlocks;
    
    public Search() {
        super("Search", "O", true);
        this.counter = 0;
        this.size = 0;
        this.blockID = 0;
        this.setDescription("Draws boxes arround searched blocks.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.blockID == 0) {
            return;
        }
        ++this.counter;
        if (this.counter >= 120) {
            this.refresh();
            this.counter = 0;
        }
        GL11.glLineWidth(1.0f);
        GL11.glColor3f(1.0f, 0.25f, 0.0f);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        for (int cur = 0; cur < this.size; ++cur) {
            final BlockCoord curBlock = Search.espBlocks[cur];
            final double x = curBlock.getDeltaX();
            final double y = curBlock.getDeltaY();
            final double z = curBlock.getDeltaZ();
            GLHelper.drawOutlinedBoundingBox(new aqx(x + 1.0, y + 1.0, z + 1.0, x, y, z));
        }
        GL11.glEnable(3553);
        GL11.glDisable(2848);
    }
    
    @Override
    public void onCommand(final String s) {
        final String[] split = s.split(" ");
        if (s.toLowerCase().startsWith(".s")) {
            try {
                final int blockID = Integer.parseInt(split[1]);
                this.blockID = blockID;
                final int block = Integer.parseInt(split[1]);
                if (block <= 0) {
                    this.setEnabled(false);
                    this.getWrapper();
                    MorbidWrapper.addChat("Stopped searching.");
                }
                else {
                    this.setEnabled(true);
                    this.getWrapper();
                    MorbidWrapper.addChat("Now searching for: " + split[1]);
                }
            }
            catch (Exception e) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .s add [block ID]");
            }
            ModBase.setCommandExists(true);
        }
    }
    
    public void refresh() {
        if (!this.isEnabled()) {
            return;
        }
        this.size = 0;
        final int radius = 64;
        for (int y = 0; y < 128; ++y) {
            for (int x = 0; x < radius; ++x) {
                for (int z = 0; z < radius; ++z) {
                    this.getWrapper();
                    final int xx = (int)MorbidWrapper.getPlayer().u - radius / 2 + x;
                    this.getWrapper();
                    final int zz = (int)MorbidWrapper.getPlayer().w - radius / 2 + z;
                    this.getWrapper();
                    final int blockID = MorbidWrapper.getWorld().a(xx, y, zz);
                    if (this.blockID == blockID) {
                        Search.espBlocks[this.size++] = new BlockCoord(xx, y, zz);
                    }
                }
            }
        }
    }
    
    static {
        Search.espBlocks = new BlockCoord[10000000];
    }
}
