package wtf.shiyeno.modules.impl.util;

import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Color;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.player.EventMotion;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil.Render2D;
import wtf.shiyeno.util.render.RenderUtil.Render3D;
import wtf.shiyeno.util.render.animation.AnimationMath;

@FunctionAnnotation(
        name = "Blink",
        type = Type.Util
)
public final class Blink extends Function {
    private final CopyOnWriteArrayList<IPacket> packets = new CopyOnWriteArrayList();
    private BooleanOption delay = new BooleanOption("Пульсации", false);
    private SliderSetting delayS = (new SliderSetting("Задержка", 100.0F, 50.0F, 1000.0F, 50.0F)).setVisible(() -> {
        return this.delay.get();
    });
    private long started;
    float animation;
    public TimerUtil timerUtil = new TimerUtil();
    Vector3d lastPos = new Vector3d(0.0, 0.0, 0.0);

    public Blink() {
        this.addSettings(new Setting[]{this.delay, this.delayS});
    }

    protected void onEnable() {
        super.onEnable();
        this.started = System.currentTimeMillis();
        this.lastPos = mc.player.getPositionVec();
    }

    public void onEvent(Event event) {
        if (event instanceof EventRender e) {
            if (e.isRender3D()) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                Render3D.drawBox(AxisAlignedBB.fromVector(this.lastPos).expand(0.0, 1.0, 0.0).offset(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z).offset(-0.5, 0.0, -0.5).grow(-0.2, 0.0, -0.2), -1);
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            if (e.isRender2D()) {
                float width = 120.0F;
                float height = 10.0F;
                float state = 1.0F - (float)(System.currentTimeMillis() - this.started) / 30000.0F;
                this.animation = AnimationMath.lerp(this.animation, width * state, 10.0F);
                Render2D.drawShadow((float)e.scaledResolution.getScaledWidth() / 2.0F - width / 2.0F, 25.0F, width, height, 10, (new Color(23, 23, 23, 200)).getRGB());
                Render2D.drawRect((float)e.scaledResolution.getScaledWidth() / 2.0F - width / 2.0F, 25.0F, width, height, (new Color(23, 23, 23, 200)).getRGB());
                Render2D.drawShadow((float)e.scaledResolution.getScaledWidth() / 2.0F - width / 2.0F, 25.0F, this.animation, height, 10, ColorUtil.getColorStyle(10.0F));
                Render2D.drawRect((float)e.scaledResolution.getScaledWidth() / 2.0F - width / 2.0F, 25.0F, this.animation, height, ColorUtil.getColorStyle(10.0F));
                Fonts.gilroyBold[14].drawCenteredString(e.matrixStack, "Blink", (double)((float)e.scaledResolution.getScaledWidth() / 2.0F), (double)(5.0F + height), -1);
            }
        }

        if (event instanceof EventPacket e) {
            if (mc.player != null && mc.world != null && !mc.isSingleplayer() && !mc.player.getShouldBeDead()) {
                if (e.isSendPacket()) {
                    this.packets.add(e.getPacket());
                    e.setCancel(true);
                }
            } else {
                this.toggle();
            }
        }

        if (event instanceof EventMotion e) {
            if (System.currentTimeMillis() - this.started >= 29900L) {
                this.toggle();
            }

            if (this.delay.get() && this.timerUtil.hasTimeElapsed(this.delayS.getValue().longValue())) {
                Iterator var8 = this.packets.iterator();

                while(var8.hasNext()) {
                    IPacket packet = (IPacket)var8.next();
                    mc.player.connection.getNetworkManager().sendPacketWithoutEvent(packet);
                }

                this.packets.clear();
                this.started = System.currentTimeMillis();
                this.timerUtil.reset();
            }
        }
    }

    public void onDisable() {
        super.onDisable();
        Iterator var1 = this.packets.iterator();

        while(var1.hasNext()) {
            IPacket packet = (IPacket)var1.next();
            mc.player.connection.sendPacket(packet);
        }

        this.packets.clear();
    }
}