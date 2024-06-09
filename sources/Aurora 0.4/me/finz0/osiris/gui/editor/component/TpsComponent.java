package me.finz0.osiris.gui.editor.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.OsirisEvent;
import me.finz0.osiris.event.events.PacketEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

/**
 * Author Seth
 * 7/25/2019 @ 7:44 AM.
 */
public final class TpsComponent extends DraggableHudComponent {

    public TpsComponent() {
        super("Tps");
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        final String tickrate = String.format(ChatFormatting.WHITE + "TPS: %.2f", TickRateManager.getTickRate());

        this.setW(Minecraft.getMinecraft().fontRenderer.getStringWidth(tickrate));
        this.setH(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);

        //RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH(), 0x90222222);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(tickrate, this.getX(), this.getY(), -1);
    }

}

final class TickRateManager {

    private long prevTime;
    public static float[] ticks = new float[20];
    private int currentTick;

    public TickRateManager() {
        this.prevTime = -1;

        for (int i = 0, len = this.ticks.length; i < len; i++) {
            this.ticks[i] = 0.0f;
        }

        AuroraMod.EVENT_BUS.subscribe(this);
    }

    public static float getTickRate() {
        int tickCount = 0;
        float tickRate = 0.0f;

        for(int i = 0; i < TickRateManager.ticks.length; i++) {
            final float tick = TickRateManager.ticks[i];

            if (tick > 0.0f) {
                tickRate += tick;
                tickCount++;
            }
        }

        return MathHelper.clamp((tickRate / tickCount), 0.0f, 20.0f);
    }

    public void unload() {
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    public Listener<PacketEvent.Receive> listener = new Listener<>(event ->  {
        if(event.getEra() == OsirisEvent.Era.PRE) {
            if(event.getPacket() instanceof SPacketTimeUpdate) {
                if (this.prevTime != -1) {
                    this.ticks[this.currentTick % this.ticks.length] = MathHelper.clamp((20.0f / ((float) (System.currentTimeMillis() - this.prevTime) / 1000.0f)), 0.0f, 20.0f);
                    this.currentTick++;
                }

                this.prevTime = System.currentTimeMillis();
            }
        }
    });

}
