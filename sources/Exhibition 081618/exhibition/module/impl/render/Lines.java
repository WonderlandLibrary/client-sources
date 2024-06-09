package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRender3D;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.module.data.SettingsMap;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;

public class Lines
extends Module {
    public static final String CHEST = "CHEST";
    public static final String SIGN = "SIGN";
    public static final String SPAWNER = "SPAWNER";
    public static final String PLAYER = "PLAYER";

    public Lines(ModuleData data) {
        super(data);
        this.settings.put(SIGN, new Setting(SIGN, false, "Draw lines at signs."));
        this.settings.put(CHEST, new Setting(CHEST, true, "Draw lines at chests."));
        this.settings.put(SPAWNER, new Setting(SPAWNER, false, "Draw lines at spawners."));
    }

    @RegisterEvent(events={EventRender3D.class})
    public void onEvent(Event event) {
        EventRender3D er = (EventRender3D)event;
        for (Object o : Lines.mc.theWorld.loadedTileEntityList) {
            float posX;
            TileEntity ent;
            boolean bobbing;
            float posY;
            float posZ;
            int color = -1;
            if (o instanceof TileEntityChest && ((Boolean)((Setting)this.settings.get(CHEST)).getValue()).booleanValue()) {
                ent = (TileEntityChest)o;
                color = Colors.getColor(114, 0, 187);
                posX = (float)((double)ent.getPos().getX() - RenderManager.renderPosX);
                posY = (float)((double)ent.getPos().getY() - RenderManager.renderPosY);
                posZ = (float)((double)ent.getPos().getZ() - RenderManager.renderPosZ);
                bobbing = Lines.mc.gameSettings.viewBobbing;
                Lines.mc.gameSettings.viewBobbing = false;
                RenderingUtil.draw3DLine(posX, posY, posZ, color);
                Lines.mc.gameSettings.viewBobbing = bobbing;
            }
            if (o instanceof TileEntityChest && ((Boolean)((Setting)this.settings.get(CHEST)).getValue()).booleanValue()) {
                ent = (TileEntityChest)o;
                color = Colors.getColor(114, 0, 187);
                posX = (float)((double)ent.getPos().getX() - RenderManager.renderPosX);
                posY = (float)((double)ent.getPos().getY() - RenderManager.renderPosY);
                posZ = (float)((double)ent.getPos().getZ() - RenderManager.renderPosZ);
                bobbing = Lines.mc.gameSettings.viewBobbing;
                Lines.mc.gameSettings.viewBobbing = false;
                RenderingUtil.draw3DLine(posX, posY, posZ, color);
                Lines.mc.gameSettings.viewBobbing = bobbing;
            }
            if (o instanceof TileEntityMobSpawner && ((Boolean)((Setting)this.settings.get(SPAWNER)).getValue()).booleanValue()) {
                ent = (TileEntityMobSpawner)o;
                color = Colors.getColor(255, 156, 0);
                posX = (float)((double)ent.getPos().getX() - RenderManager.renderPosX);
                posY = (float)((double)ent.getPos().getY() - RenderManager.renderPosY);
                posZ = (float)((double)ent.getPos().getZ() - RenderManager.renderPosZ);
                bobbing = Lines.mc.gameSettings.viewBobbing;
                Lines.mc.gameSettings.viewBobbing = false;
                RenderingUtil.draw3DLine(posX, posY, posZ, color);
                Lines.mc.gameSettings.viewBobbing = bobbing;
            }
            if (!(o instanceof TileEntitySign) || !((Boolean)((Setting)this.settings.get(SIGN)).getValue()).booleanValue()) continue;
            ent = (TileEntitySign)o;
            color = Colors.getColor(130, 162, 0);
            posX = (float)((double)ent.getPos().getX() - RenderManager.renderPosX);
            posY = (float)((double)ent.getPos().getY() - RenderManager.renderPosY);
            posZ = (float)((double)ent.getPos().getZ() - RenderManager.renderPosZ);
            bobbing = Lines.mc.gameSettings.viewBobbing;
            Lines.mc.gameSettings.viewBobbing = false;
            RenderingUtil.draw3DLine(posX, posY, posZ, color);
            Lines.mc.gameSettings.viewBobbing = bobbing;
        }
    }
}

