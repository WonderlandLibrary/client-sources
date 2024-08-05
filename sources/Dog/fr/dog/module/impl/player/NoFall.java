package fr.dog.module.impl.player;

import fr.dog.component.impl.packet.BlinkComponent;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.event.impl.render.Render2DEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import fr.dog.util.packet.PacketUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

import java.awt.*;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", ModuleCategory.PLAYER);
        this.registerProperties(mode, blinkindicator);
    }

    public ModeProperty mode = ModeProperty.newInstance("Mode", new String[]{"Blink", "NoGround", "Packet", "Dynamic"}, "Blink");
    public ModeProperty blinkindicator = ModeProperty.newInstance("Blink Indicator", new String[]{"Legit", "Raven", "Number", "None"}, "Legit", () -> mode.is("Blink"));
    public boolean blinking = false;
    private boolean wasBlinking = false;
    private int ticks = 0;

    public void onDisable() {
        blinking = false;
        wasBlinking = false;
        BlinkComponent.onDisable();
    }

    @SubscribeEvent
    private void onRender2D(Render2DEvent event) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        if (blinking) {
            switch (blinkindicator.getValue()) {
                case "Legit":
                    mc.fontRendererObj.drawStringWithShadow("Blinking...", sr.getScaledWidth() / 1.9f, sr.getScaledHeight() / 1.9f, Color.WHITE.getRGB());
                    mc.fontRendererObj.drawStringWithShadow("Ticks : " + ticks, sr.getScaledWidth() / 1.9f, sr.getScaledHeight() / 1.9f + 12, Color.WHITE.getRGB());
                    break;
                case "Raven":
                    mc.fontRendererObj.drawStringWithShadow("§fblinking : §a" + ticks, sr.getScaledWidth() / 1.9f, sr.getScaledHeight() / 1.9f, Color.WHITE.getRGB());
                    break;
                case "Number":
                    mc.fontRendererObj.drawStringWithShadow("§a" + ticks, sr.getScaledWidth() / 1.9f, sr.getScaledHeight() / 1.9f, Color.WHITE.getRGB());
                    break;
                case "None":
                    break;
            }
        }
    }

    @SubscribeEvent
    private void onUpdate(PlayerNetworkTickEvent event) {
        this.setSuffix(mode.getValue());
        switch (mode.getValue()) {
            case "Blink":
                if (mc.thePlayer.onGround) {
                    blinking = false;
                    ticks = 0;
                } else {
                    boolean canFall = !(getDistanceToGround() < 2);

                    if (mc.thePlayer.airTicks < 2 && mc.thePlayer.motionY < 0 && canFall) {
                        blinking = true;
                    }
                }

                if (blinking && ticks < 115) {
                    event.setOnGround(true);
                    mc.thePlayer.fallDistance = 0;
                    BlinkComponent.onEnable();
                } else {
                    if (wasBlinking && !blinking) {
                        BlinkComponent.onDisable();
                    }
                }

                wasBlinking = blinking;
                ticks++;
                break;
            case "NoGround":
                event.setOnGround(false);
                break;
            case "Packet":
                if (mc.thePlayer.fallDistance > 3 && getDistanceToGround() != 0) {
                    mc.timer.timerSpeed = 0.5f;
                    PacketUtil.sendPacket(new C03PacketPlayer(true));
                } else {
                    mc.timer.timerSpeed = 1f;
                }
                break;
            case "Dynamic":
                if (mc.thePlayer.onGround) {
                    blinking = false;
                    ticks = 0;
                    mc.timer.timerSpeed = 1f;
                } else {
                    boolean canFall = !(getDistanceToGround() < 2);
                    if (mc.thePlayer.airTicks < 2 && mc.thePlayer.motionY < 0 && canFall) {
                        blinking = true;
                    } else {
                        if (mc.thePlayer.fallDistance > 3 && getDistanceToGround() != 0) {
                            mc.timer.timerSpeed = 0.5f;
                            PacketUtil.sendPacket(new C03PacketPlayer(true));
                        } else {
                            mc.timer.timerSpeed = 1f;
                        }
                    }
                }

                if (blinking && ticks < 115) {
                    event.setOnGround(true);
                    mc.thePlayer.fallDistance = 0;
                    BlinkComponent.onEnable();
                } else {
                    if (wasBlinking && !blinking) {
                        BlinkComponent.onDisable();
                    }
                }

                wasBlinking = blinking;
                ticks++;
                break;
        }
    }

    private float getDistanceToGround() {
        for (float y = (float) mc.thePlayer.posY; y > 0; y -= 1) {
            BlockPos bp = new BlockPos(mc.thePlayer.posX, y, mc.thePlayer.posZ);

            if (!(mc.theWorld.getBlockState(bp).getBlock() instanceof BlockAir)) {
                return (float) (mc.thePlayer.posY - y);
            }
        }
        return 0;
    }
}
