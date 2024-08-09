package wtf.shiyeno.modules.impl.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.awt.Color;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SJoinGamePacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import wtf.shiyeno.Initilization;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.util.drag.Dragging;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.font.styled.StyledFont;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.render.BloomHelper;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.RenderUtil.Render2D;

import static wtf.shiyeno.util.render.ColorUtil.getColor;

@FunctionAnnotation(
        name = "LogsAC",
        type = Type.Util
)
public class LogsAC extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    int simulation = 0;
    int timer = 0;
    int negativetimer = 0;
    int noslow = 0;
    public BooleanOption render = new BooleanOption("Рисовать AntiCheat List", true);
    final Dragging LogsHUD = Initilization.createDrag(this, "LogsHUD", 10.0F, 400.0F);
    final StyledFont medium;

    public LogsAC() {
        this.medium = Fonts.msMedium[16];
        this.addSettings(new Setting[]{this.render});
    }

    public void onEvent(Event event) {
        if (event instanceof EventPacket e) {
            if (e.getPacket() instanceof SJoinGamePacket) {
                this.simulation = 0;
                this.timer = 0;
                this.negativetimer = 0;
                this.noslow = 0;
            }
        }

        if (event instanceof EventPacket e) {
            if (e.isReceivePacket()) {
                IPacket var4 = e.getPacket();
                TextFormatting var10000;
                if (var4 instanceof SPlayerPositionLookPacket) {
                    SPlayerPositionLookPacket p = (SPlayerPositionLookPacket)var4;
                    mc.player.func_242277_a(new Vector3d(p.getX(), p.getY(), p.getZ()));
                    mc.player.setRawPosition(p.getX(), p.getY(), p.getZ());
                    ++this.simulation;
                    var10000 = TextFormatting.RED;
                    noPrefixSendMessage("" + var10000 + "AntiCheat: " + TextFormatting.RESET + mc.player.getScoreboardName() + TextFormatting.RED + " failed " + TextFormatting.RESET + "Simulation (" + TextFormatting.RED + "x" + this.simulation + TextFormatting.RESET + ")");
                    if (mc.timer.timerSpeed > 1.0F) {
                        ++this.timer;
                        var10000 = TextFormatting.RED;
                        noPrefixSendMessage("" + var10000 + "AntiCheat: " + TextFormatting.RESET + mc.player.getScoreboardName() + TextFormatting.RED + " failed " + TextFormatting.RESET + "timer (" + TextFormatting.RED + "x" + this.timer + TextFormatting.RESET + ")");
                    }

                    if (mc.player.isHandActive()) {
                        ++this.noslow;
                        var10000 = TextFormatting.RED;
                        noPrefixSendMessage("" + var10000 + "AntiCheat: " + TextFormatting.RESET + mc.player.getScoreboardName() + TextFormatting.RED + " failed " + TextFormatting.RESET + "NoSlowA (prediction) (" + TextFormatting.RED + "x" + this.noslow + TextFormatting.RESET + ")");
                    }
                }

                if (mc.timer.timerSpeed < 1.0F) {
                    ++this.negativetimer;
                    var10000 = TextFormatting.RED;
                    noPrefixSendMessage("" + var10000 + "AntiCheat: " + TextFormatting.RESET + mc.player.getScoreboardName() + TextFormatting.RED + " failed " + TextFormatting.RESET + "NegativeTimer (" + TextFormatting.RED + "x" + this.negativetimer + TextFormatting.RESET + ")");
                    this.timerUtil.reset();
                }
            }
        }

        if (event instanceof EventRender e) {
            if (e.isRender2D()) {
                boolean glowing = true;
                boolean offset = false;
                MatrixStack matrixStack = e.matrixStack;
                if (this.render.get()) {
                    this.render2D(matrixStack, 0, true);
                }
            }
        }
    }

    public static void noPrefixSendMessage(String message) {
        mc.player.sendMessage(new StringTextComponent(message), Util.DUMMY_UUID);
    }

    private void render2D(MatrixStack matrixStack, int offset, boolean glowing) {
        float x = this.LogsHUD.getX();
        float y = this.LogsHUD.getY();
        float width = 80.0F;
        float height = 40.0F;
        Render2D.drawRoundedCorner(x + 5.0F, y, width, height, 5.0F, (new Color(17, 24, 39, 255)).getRGB());
        Render2D.drawRoundedRect(x + 5.0F, y + 13.0F, width, 1.0F, 7.0F, (new Color(255, 255, 255, 102)).getRGB());
        BloomHelper.registerRenderCall(() -> {
        this.medium.drawString(matrixStack, "AntiCheat", x + 25.0F, y + 5.0F, (ColorUtil.getColorStyle(0)));
            RenderUtil.reAlphaInt(getColor(100), 210);
        });
        this.medium.drawString(matrixStack, "AntiCheat", x + 25.0F, y + 5.0F, (ColorUtil.getColorStyle(0)));
        ColorUtil.interpolateColor(getColor(100),
                new Color(255, 255, 255).getRGB(), 0.5f);
        Fonts.gilroyBold[13].drawString(matrixStack, "Simulation: " + this.simulation, (double)(x + 10.0F), (double)(y + 18.0F), (new Color(255, 255, 255, 255)).getRGB());
        Fonts.gilroyBold[13].drawString(matrixStack, "Timer: " + this.timer, (double)(x + 10.0F), (double)(y + 25.0F), (new Color(255, 255, 255, 255)).getRGB());
        Fonts.gilroyBold[13].drawString(matrixStack, "NoSlow: " + this.noslow, (double)(x + 10.0F), (double)(y + 32.0F), (new Color(255, 255, 255, 255)).getRGB());
        this.LogsHUD.setWidth(width);
        this.LogsHUD.setHeight(height);
    }

    public void onDisable() {
        this.simulation = 1;
        this.timer = 1;
        this.negativetimer = 1;
        this.noslow = 1;
        super.onDisable();
    }
}