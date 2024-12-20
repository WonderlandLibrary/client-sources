package com.alan.clients.module.impl.render.nametags;

import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.component.impl.player.UserFriendAndTargetComponent;
import com.alan.clients.component.impl.render.ProjectionComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.impl.render.NameTags;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

import javax.vecmath.Vector4d;
import java.awt.*;
import java.util.List;

import static com.alan.clients.layer.Layers.REGULAR;

public class ClassicNameTags extends Mode<NameTags> {

    public ClassicNameTags(String name, NameTags parent) {
        super(name, parent);
    }
    private final BooleanValue showTeam = new BooleanValue("Show Team Tag", this, false);
    private final BooleanValue showTarget = new BooleanValue("Show Target Tag", this, false);
    private final BooleanValue showFriendTag = new BooleanValue("Show Friend Tag", this, false);
    private final BooleanValue shortenedTags  = new BooleanValue("Shortened Tags", this, false);

    @EventLink()
    public final Listener<Render2DEvent> onRender2D = event -> {
        Font font = mc.fontRendererObj;

        GlStateManager.pushMatrix();

        List<EntityLivingBase> entities = TargetComponent.getTargets(getParent().player.getValue(), getParent().invisibles.getValue(), getParent().animals.getValue(), getParent().mobs.getValue(), getParent().teams.getValue(), true);

        if (mc.gameSettings.thirdPersonView != 0 && getParent().player.getValue()) {
            entities.add(mc.thePlayer);
        }

        for (EntityLivingBase entity : entities) {
            String nametag = entity.getDisplayName().getFormattedText() + " §7[§4❤" + Math.round(entity.getHealth()) + "§7]";

            if (showTeam.getValue() && PlayerUtil.sameTeam(entity)) {
                nametag = "§a§l" + (shortenedTags.getValue() ? "[TM]" : "[TEAM]") + "§r " + nametag;
            }

            if (showTarget.getValue() && UserFriendAndTargetComponent.isTarget(entity.getCommandSenderName())) {
                nametag = "§4§l" + (shortenedTags.getValue() ? "[T]" : "[TARGET]") + "§r " + nametag;
            }

            if (showFriendTag.getValue() && UserFriendAndTargetComponent.isFriend(entity.getCommandSenderName())) {
                nametag = "§b§l" + (shortenedTags.getValue() ? "[F]" : "[FRIEND]") + "§r " + nametag;
            }

            entity.hideNameTag();

            Vector4d position = ProjectionComponent.get(entity);

            if (position == null) {
                continue;
            }

            float padding = 2;
            int height = 8;
            float width = font.width(nametag);

            float posX = (float) (position.x + (position.z - position.x) / 2);
            float posY = (float) position.y - height;

            String finalNametag = nametag;
            getLayer(REGULAR).add(() -> {
                RenderUtil.rectangle(posX - width / 2 - padding, posY - padding - 3 , width + padding * 2,
                        height + padding * 2, getTheme().getBackgroundShade());
                float centeredPosX = posX - (width / 2.0f);
                font.drawWithShadow(finalNametag, centeredPosX + 0.5f, posY - 2, Color.WHITE.getRGB());
                
            });
        }

        GlStateManager.popMatrix();
    };
}
