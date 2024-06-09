package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.tileentity.*;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class StorageESP extends Module {
    public StorageESP() {
        super("StorageESP", Category.RENDER, "Highlight chests and stuff");
    }

    Setting a;
    Setting w;

    ConcurrentHashMap<TileEntity, String> chests = new ConcurrentHashMap<>();

    public void setup(){
        a = new Setting("Alpha", this, 150, 0, 255, true, "StorageESPAlpha");
        AuroraMod.getInstance().settingsManager.rSetting(a);
        w = new Setting("Width", this, 1, 1, 10, true, "StorageESPWidth");
        AuroraMod.getInstance().settingsManager.rSetting(w);
    }

    public void onUpdate(){
        mc.world.loadedTileEntityList.forEach(e->{
                chests.put(e, "");
        });
    }

    public void onWorldRender(RenderEvent event){
        Color c1 = new Color(200, 100, 0, (int)a.getValDouble());
        Color c2 = new Color(200, 0, 200, (int)a.getValDouble());
        Color c3 = new Color(150, 150, 150, (int)a.getValDouble());
        if(chests != null && chests.size() > 0){
            OsirisTessellator.prepareGL();
            chests.forEach((c, t)->{
                if(mc.world.loadedTileEntityList.contains(c)) {
                    if(c instanceof TileEntityChest
                            || c instanceof TileEntityShulkerBox)
                        //OsirisTessellator.drawBox(c.getPos(), c1.getRGB(), GeometryMasks.Quad.ALL);
                        OsirisTessellator.drawBoundingBox(mc.world.getBlockState(c.getPos()).getSelectedBoundingBox(mc.world, c.getPos()), (float)w.getValDouble(), c1.getRGB());
                    if(c instanceof TileEntityEnderChest)
                        //OsirisTessellator.drawBox(c.getPos(), c2.getRGB(), GeometryMasks.Quad.ALL);
                        OsirisTessellator.drawBoundingBox(mc.world.getBlockState(c.getPos()).getSelectedBoundingBox(mc.world, c.getPos()), (float)w.getValDouble(), c2.getRGB());
                    if(c instanceof TileEntityDispenser
                            || c instanceof TileEntityFurnace
                            || c instanceof TileEntityHopper)
                    //OsirisTessellator.drawBox(c.getPos(), c3.getRGB(), GeometryMasks.Quad.ALL);
                        OsirisTessellator.drawBoundingBox(mc.world.getBlockState(c.getPos()).getSelectedBoundingBox(mc.world, c.getPos()), (float)w.getValDouble(), c3.getRGB());
                }
            });
            OsirisTessellator.releaseGL();
        }
    }
}
