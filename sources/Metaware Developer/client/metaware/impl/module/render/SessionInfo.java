package client.metaware.impl.module.render;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.render.Render2DEvent;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.StencilUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.play.server.S02PacketChat;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleInfo(name = "SessionInfo", renderName = "Session Info", category = Category.VISUALS)
public class SessionInfo extends Module {

        public TimerUtil timer = new TimerUtil(), minTimer = new TimerUtil(), secTimer = new TimerUtil();
        public DoubleProperty transparency = new DoubleProperty("Transparency", 250, 1, 253, 1);
        private CustomFontRenderer font = Metaware.INSTANCE.getFontManager().currentFont().size(20);
        private CustomFontRenderer font2 = Metaware.INSTANCE.getFontManager().currentFont().size(17);
        double x, y;
        public DoubleProperty xCord = new DoubleProperty("X", 45.0f, 0, 1920, 1, () -> false);
        public DoubleProperty yCord = new DoubleProperty("Y", 45.0f, 0, 1080, 1, () -> false);
        private int hour = 0;
        private int min = 0;
        private int sec = 0;
        private int Kills = 0;



        @EventHandler
        private final Listener<PacketEvent> packetInboundEventListener = event -> {
            if (mc.isSingleplayer() || mc.thePlayer == null || mc.theWorld == null) return;
            if (event.getPacket() instanceof S02PacketChat) {
                if (mc.isSingleplayer() || mc.thePlayer == null || mc.theWorld == null) return;
                final S02PacketChat packet = (S02PacketChat) event.getPacket();
                String text = packet.getChatComponent().getUnformattedText();
                if (text.contains("was killed by " + mc.thePlayer.getGameProfile().getName())) {
                    Kills += 1;
                } else if (text.contains("by " + mc.thePlayer.getGameProfile().getName())) {
                    Kills += 1;
                }
            }
        };

        @EventHandler
        private final Listener<Render2DEvent> render2DEventListener = event -> {
            if (mc.isSingleplayer() || mc.thePlayer == null || mc.theWorld == null) return;
            GL11.glPushMatrix();
            ScaledResolution scaledResolution = event.getScaledResolution();
            x = xCord.getValue();
            y = yCord.getValue();
            Gui.drawRect(0, 0, 0, 0, 0);

            int var141 = scaledResolution.getScaledWidth();
            int var151 = scaledResolution.getScaledHeight();
            final float n = 2;
            scaledResolution.scaledWidth *= (int) (1.0f / n);
            scaledResolution.scaledHeight *= (int) (1.0f / n);
            int mouseX = Mouse.getX() * var141 / mc.displayWidth;
            int mouseY = var151 - Mouse.getY() * var151 / mc.displayHeight - 1;
            if (Mouse.isButtonDown(0) && mc.currentScreen instanceof GuiChat && RenderUtil.isHovered((float)x, (float)y, 120, 60.5f, mouseX, mouseY)) {
                xCord.setValue((double) (mouseX - 50));
                yCord.setValue((double) (mouseY - 20));
            }
            if (GuiConnecting.isConnected) {
                if (minTimer.delay(60100)) {
                    min += 1;
                    if (min >= 60) {
                        hour += 1;
                        min = 0;
                    }
                    minTimer.reset();
                }
                if (secTimer.delay(1000)) {
                    if (sec >= 60) {
                        sec = 0;
                    }
                    sec += 1;
                    secTimer.reset();
                }

            } else {
                resetTimers();
                hour = 0;
                min = 0;
                sec = 0;
            }
            if (mc.thePlayer != null) {
                //GL11.glPushMatrix();
                final ScaledResolution sr = event.getScaledResolution();
                Color darkgary2 = new Color(65, 65, 65, 210);
                double rectwidth = 120;
                double rectheight = 60.5f;

                int rectX = (int) (x + 2);
                int rectY = (int) (y + 2);

                CustomFontRenderer fontRenderer = Metaware.INSTANCE.getFontManager().testFont().size(12);

                EntityPlayerSP player = mc.thePlayer;
                double xDist = player.posX - player.lastTickPosX;
                double zDist = player.posZ - player.lastTickPosZ;
                float d = (float) StrictMath.sqrt(xDist * xDist + zDist * zDist);
                String Text = String.format("%.2f", d * 20 * mc.timer.timerSpeed);
                String Text2 = String.valueOf(mc.getCurrentServerData().pingToServer);
                String Text3 = (hour > 0 ? String.valueOf(hour) + "h " : "") + (min > 0 ? min + "m " : "") + String.valueOf(sec) + "s";



                GL11.glPushMatrix();
                StencilUtil.initStencilToWrite();
                RenderUtil.glDrawRoundedRectEllipse(rectX, rectY, rectwidth - 17, rectheight, RenderUtil.RoundingMode.FULL, 20, 8, 0x90000000);
                StencilUtil.readStencilBuffer(1);
                Metaware.INSTANCE.getBlurShader(25).blur();
                StencilUtil.uninitStencilBuffer();
                GL11.glPopMatrix();



                GL11.glPushMatrix();
                RenderUtil.drawGradientSideways(rectX, y + 15, x + rectwidth - 15, y + 16, RenderUtil.getRainbowFelix(6000, 30, 0.625f), new Color(0xffb82525).brighter().getRGB());
                //RenderUtil.drawGr((float) rectwidth + 1f, (float) 2, (float) 17, new Color(0xffb82525).brighter().getRGB());
                GL11.glColor4f(255, 255, 255, 255);
                GL11.glPopMatrix();

                font.drawString("Session Info", (float) ((x + rectwidth / 1.29f) - font.getWidth("Session Info") - 8), rectY + 3,
                        -1);

                font2.drawString("Time Played:", rectX + 1, rectY + 16,
                        new Color(255, 255, 255, transparency.getValue().intValue()).getRGB());

                //fontRenderer.drawString("K", (rectX + 1) + font2.getWidth("Time Played:"), rectY + 16, -1);
                //RenderUtil.drawImage12(new ResourceLocation("whiz/clock.png"), rectX + 1 + font2.getWidth("Time Played:"), rectY + 17, 12, 12, 255);

                font2.drawString(Text3, (float) (x + rectwidth - font2.getWidth(Text3) - 14), rectY + 17, new Color(255, 255, 255, transparency.getValue().intValue()).getRGB());


                font2.drawString("BPS:", rectX + 1, rectY + 27,
                        new Color(255, 255, 255, transparency.getValue().intValue()).getRGB());

                font2.drawString(Text, (float) (x + rectwidth - font2.getWidth(Text) - 14), rectY + 27, new Color(255, 255, 255, transparency.getValue().intValue()).getRGB());


                long measuredTime = 2000L;
                float tps = (20 * (1000f / measuredTime)) * 2;

                font2.drawString("TPS:", rectX + 1, rectY + 37,
                        new Color(255, 255, 255, transparency.getValue().intValue()).getRGB());

                font2.drawString("" + Math.round(tps), (float) (x + rectwidth - font2.getWidth("" + Math.round(tps)) - 14), rectY + 37, new Color(255, 255, 255, transparency.getValue().intValue()).getRGB());

                font2.drawString("Kills:", rectX + 1, rectY + 47,
                        new Color(255, 255, 255, transparency.getValue().intValue()).getRGB());

                font2.drawString("" + Kills, (float) (x + rectwidth - font2.getWidth("" + Kills) - 14), rectY + 47, new Color(255, 255, 255, transparency.getValue().intValue()).getRGB());

            }
            GL11.glPopMatrix();
        };


        @Override
        public void onEnable() {
            super.onEnable();
            resetTimers();
            hour = 0;
            min = 0;
            sec = 0;
        }

        @Override
        public void onDisable() {
            super.onDisable();
            resetTimers();
        }

        private final void resetTimers() {
            minTimer.reset();
            timer.reset();
            secTimer.reset();
        }
    }
