package wtf.diablo.client.module.impl.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import wtf.diablo.client.gui.draggable.AbstractDraggableElement;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.util.render.RenderUtil;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@ModuleMetaData(name = "Player List", description = "Shows a list of players on the server", category = ModuleCategoryEnum.RENDER)
public final class PlayerListModule extends AbstractModule {
    private static final Color BAR_COLOR = new Color(19, 19, 19, 216);
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255, 107);

    private final AbstractDraggableElement draggableElement = new AbstractDraggableElement("Player List", 100, 100, 150, 100, this) {
        @Override
        protected void draw() {
            final List<EntityPlayer> players = mc.theWorld.playerEntities.stream().filter(player -> player != mc.thePlayer && player != null && player.getName() != null && player.getUniqueID() != null && player.getHealth() > 0 && player.isEntityAlive() && !player.isInvisible()).sorted((player1, player2) -> (int) (player2.getHealth() - player1.getHealth())).collect(Collectors.toList());

            int y = 0;

            final int BAR_HEIGHT = 16;

            RenderUtil.drawRect(0, y, getWidth(), BAR_HEIGHT, BAR_COLOR.getRGB());
            y += BAR_HEIGHT;

            mc.fontRendererObj.drawString("Players (" + players.size() + ")", 4, 4, -1);

            for (final EntityPlayer player : players) {
                if (player != mc.thePlayer && player != null && player.getName() != null && player.getUniqueID() != null && player.getHealth() > 0 && player.isEntityAlive() && !player.isInvisible()) {
                    RenderUtil.drawRect(0, y, getWidth(), BAR_HEIGHT, new Color(66, 66, 66, 69).getRGB());

                    if (mc.getNetHandler() != null && player.getUniqueID() != null) {
                        NetworkPlayerInfo i = mc.getNetHandler().getPlayerInfo(player.getUniqueID());
                        if (i != null) {
                            mc.getTextureManager().bindTexture(i.getLocationSkin());
                            GlStateManager.color(1, 1, 1);
                            Gui.drawModalRectWithCustomSizedTexture(2, y + 2, (float) (12), (float) (12), 12, 12, (float) 96, (float) 96);
                        }
                    }

                    mc.fontRendererObj.drawString(player.getName(), 18, y + 4, -1);

                    mc.fontRendererObj.drawString(ChatFormatting.RED + String.valueOf((int) player.getHealth()), getWidth() - 16, y + 4, -1);
                    y += BAR_HEIGHT;
                }
            }

            this.setWidth(175);
            this.setHeight(y);
        }
    };
}
