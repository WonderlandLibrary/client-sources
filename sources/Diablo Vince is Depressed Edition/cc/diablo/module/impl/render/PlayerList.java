/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.render;

import cc.diablo.event.impl.OverlayEvent;
import cc.diablo.helpers.render.ColorHelper;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerList
extends Module {
    public ArrayList<Entity> entityArrayList = new ArrayList();
    public ModeSetting mode = new ModeSetting("Playerlist", "Nearby", "Server", "Nearby");

    public PlayerList() {
        super("PlayerList", "List all players in your game", 0, Category.Render);
        this.addSettings(this.mode);
    }

    @Subscribe
    public void onRender(OverlayEvent e) {
        int theY = 33;
        int x = 3;
        int y = 17;
        int width = PlayerList.mc.fontRendererObj.getStringWidth("aaaaaaaaaaaaa") + 40;
        NetHandlerPlayClient nethandlerplayclient = PlayerList.mc.thePlayer.sendQueue;
        List list = GuiPlayerTabOverlay.field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        switch (this.mode.getMode()) {
            case "Server": {
                RenderUtils.drawRect(x - 1, y - 3, x + width + 1, y + 12 * list.size() + 17, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
                RenderUtils.drawRect(x, y, x + width, y + 12 * list.size() + 16, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
                RenderUtils.drawRect(x, y, x + width, y + 13, RenderUtils.transparency(new Color(35, 35, 35, 255).getRGB(), 0.8f));
                RenderUtils.drawRect(x, y - 2, x + width, y, RenderUtils.transparency(ColorHelper.getColor(0), 0.8f));
                PlayerList.mc.fontRendererObj.drawStringWithShadow("Players", x + 2, y + 3, -1);
                for (NetworkPlayerInfo networkplayerinfo : list) {
                    PlayerList.mc.fontRendererObj.drawStringWithShadow(networkplayerinfo.getGameProfile().getName(), 18.0f, theY + 1, new Color(200, 200, 200).getRGB());
                    RenderUtils.drawHead((AbstractClientPlayer)Minecraft.theWorld.getPlayerEntityByUUID(nethandlerplayclient.getGameProfile().getId()), 5, theY, 10, 10);
                    PlayerList.mc.fontRendererObj.drawStringWithShadow(String.valueOf(list.size()), x + width - 2 - PlayerList.mc.fontRendererObj.getStringWidth(String.valueOf(list.size())), y + 3, -1);
                    theY += 12;
                }
            }
            case "Nearby": {
                int count = 0;
                for (Entity ent : Minecraft.theWorld.loadedEntityList) {
                    if (!(ent instanceof EntityPlayer) || ent.isInvisible()) continue;
                    ++count;
                }
                RenderUtils.drawRect(x - 1, y - 3, x + width + 1, y + 12 * count + 17, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
                RenderUtils.drawRect(x, y, x + width, y + 12 * count + 16, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
                RenderUtils.drawRect(x, y, x + width, y + 13, RenderUtils.transparency(new Color(35, 35, 35, 255).getRGB(), 0.8f));
                RenderUtils.drawRect(x, y - 2, x + width, y, RenderUtils.transparency(ColorHelper.getColor(0), 0.8f));
                PlayerList.mc.fontRendererObj.drawStringWithShadow("Players", x + 2, y + 3, -1);
                for (Entity ent : Minecraft.theWorld.loadedEntityList) {
                    if (!(ent instanceof EntityPlayer) || ent.isInvisible()) continue;
                    PlayerList.mc.fontRendererObj.drawStringWithShadow(ent.getName(), 18.0f, theY + 1, new Color(200, 200, 200).getRGB());
                    RenderUtils.drawHead((AbstractClientPlayer)ent, 5, theY, 10, 10);
                    theY += 12;
                }
                PlayerList.mc.fontRendererObj.drawStringWithShadow(String.valueOf(count), x + width - 2 - PlayerList.mc.fontRendererObj.getStringWidth(String.valueOf(count)), y + 3, -1);
            }
        }
    }
}

