package wwe.modules.world;

import net.minecraft.world.chunk.*;
import wwe.modules.*;
import net.minecraft.util.text.*;
import java.util.*;
import net.minecraftforge.common.*;
import wwe.*;
import net.minecraft.network.play.server.*;
import wwe.utils.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PathFinder extends qMIe
{
    public static ArrayList<Chunk> LpTn;
    public ModeValue mode;
    public BooleanValue remove;
    
    public PathFinder() {
        super("Path Finder", "Makes newly generated chunks glow!", 0, Category.WORLD);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Experimental");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Normal", list);
        this.remove = new BooleanValue("Remove", this, "Removes chunk from rendering when its unloaded, may fix lag issue", false);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.owgW("Path Finder" + TextFormatting.DARK_GRAY + " Normal");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Experimental")) {
            this.owgW("Path Finder" + TextFormatting.DARK_GRAY + " Experimental");
        }
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        for (final Chunk chunk : PathFinder.LpTn) {
            BIIV.Xfgz(chunk.x * 16, 0, chunk.z * 16);
            if (PathFinder.\u2005\u2005\u200c\u200e\u2007) {
                throw null;
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        MinecraftForge.EVENT_BUS.register((Object)this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        super.qBmy();
    }
    
    @oqrr
    public void kcLG(final FCbU fCbU) {
        if (this.mode.OnGi().equalsIgnoreCase("Normal") && !fCbU.iQYs.isFullChunk()) {
            PathFinder.LpTn.add(fCbU.faFi());
        }
    }
    
    @oqrr
    public void KQfk(final LqJY lqJY) {
        if (this.mode.OnGi().equalsIgnoreCase("Experimental") && lqJY.MYlK() instanceof SPacketChunkData && !((SPacketChunkData)lqJY.MYlK()).isFullChunk()) {
            PathFinder.LpTn.add(Wrapper.mc.world.getChunk(((SPacketChunkData)lqJY.MYlK()).getChunkX(), ((SPacketChunkData)lqJY.MYlK()).getChunkZ()));
        }
    }
    
    @SubscribeEvent
    public void QHcW(final ChunkEvent.Unload unload) {
        if (this.remove.OnGi()) {
            int i = 0;
            while (i < PathFinder.LpTn.size()) {
                final Chunk chunk = PathFinder.LpTn.get(i);
                if (chunk.x == unload.getChunk().x && chunk.z == unload.getChunk().z) {
                    PathFinder.LpTn.remove(i);
                }
                ++i;
                if (PathFinder.\u2005\u2005\u200c\u200e\u2007) {
                    throw null;
                }
            }
        }
    }
    
    static {
        PathFinder.LpTn = new ArrayList<Chunk>();
    }
}
