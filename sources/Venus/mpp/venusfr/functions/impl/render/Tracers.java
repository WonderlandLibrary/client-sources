/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.AntiBot;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.utils.EntityUtils;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="Tracers", type=Category.Visual)
public class Tracers
extends Function {
    private final BooleanSetting ignoreNaked = new BooleanSetting("\u0418\u0433\u043d\u043e\u0440\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0433\u043e\u043b\u044b\u0445", true);

    public Tracers() {
        this.addSettings(this.ignoreNaked);
    }

    @Subscribe
    public void onRender(WorldEvent worldEvent) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glLineWidth(1.0f);
        Vector3d vector3d = new Vector3d(0.0, 0.0, 150.0).rotatePitch((float)(-Math.toRadians(Tracers.mc.getRenderManager().info.getPitch()))).rotateYaw((float)(-Math.toRadians(Tracers.mc.getRenderManager().info.getYaw())));
        for (AbstractClientPlayerEntity abstractClientPlayerEntity : Tracers.mc.world.getPlayers()) {
            if (abstractClientPlayerEntity == Tracers.mc.player || !abstractClientPlayerEntity.isAlive() || AntiBot.isBot(abstractClientPlayerEntity) || (float)abstractClientPlayerEntity.getTotalArmorValue() == 0.0f && ((Boolean)this.ignoreNaked.get()).booleanValue()) continue;
            Vector3d vector3d2 = EntityUtils.getInterpolatedPositionVec(abstractClientPlayerEntity).subtract(Tracers.mc.getRenderManager().info.getProjectedView());
            ColorUtils.setColor(FriendStorage.isFriend(abstractClientPlayerEntity.getGameProfile().getName()) ? FriendStorage.getColor() : -1);
            buffer.begin(1, DefaultVertexFormats.POSITION);
            buffer.pos(vector3d.x, vector3d.y, vector3d.z).endVertex();
            buffer.pos(vector3d2.x, vector3d2.y, vector3d2.z).endVertex();
            tessellator.draw();
        }
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }
}

