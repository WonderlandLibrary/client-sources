package cc.slack.features.modules.impl.render;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.utilties.NameProtect;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.font.MCFontRenderer;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S40PacketDisconnect;
import org.lwjgl.input.Mouse;

import java.awt.*;

@ModuleInfo(
        name = "SessionInfo",
        category = Category.RENDER
)
public class SessionInfo extends Module {

    private final NumberValue<Float> xValue = new NumberValue<>("Pos X", 8.0F, 1.0F, 300.0F, 1F);
    private final NumberValue<Float> yValue = new NumberValue<>("Pos Y", 160F, 1.0F, 300.0F, 1F);
    private final NumberValue<Integer> alphaValue = new NumberValue<>("Alpha", 170, 0, 255, 1);
    private final BooleanValue roundedValue = new BooleanValue("Rounded", false);
    public final BooleanValue resetPos = new BooleanValue("Reset Position", false);

    private final ModeValue<String> fontValue = new ModeValue<>("Font", new String[]{"Apple", "Poppins"});

    private boolean dragging = false;
    private float dragX = 0, dragY = 0;

    public long gameJoined;
    public long killAmount;
    public long currentTime;
    public long timeJoined;

    public SessionInfo() {
        addSettings(xValue, yValue, alphaValue, roundedValue, resetPos,fontValue);
    }

    @Override
    public void onEnable() {
        currentTime = System.currentTimeMillis();
    }

    @Override
    public void onDisable() {
        currentTime = 0L;
        timeJoined = System.currentTimeMillis();
    }

    @Listen
    public void onPacket(PacketEvent event) {
        try {
            if (event.getPacket() instanceof S02PacketChat) {
                S02PacketChat packet = event.getPacket();
                String message = packet.getChatComponent().getUnformattedText();
                if (message.contains(mc.getSession().getUsername() + " wants to fight!") ||
                        message.contains(mc.getSession().getUsername() + " has joined") ||
                        message.contains(mc.getSession().getUsername() + " se ha unido") ||
                        message.contains(mc.getSession().getUsername() + " ha entrado")) {
                    ++gameJoined;
                }
                if (message.contains("by " + mc.getSession().getUsername()) ||
                        (message.contains(mc.thePlayer.getNameClear()) && message.contains("fue brutalmente asesinado por") ||
                                message.contains(mc.thePlayer.getNameClear()) && message.contains("fue empujado al vacío por") ||
                                message.contains(mc.thePlayer.getNameClear()) && message.contains("no resistió los ataques de") ||
                                message.contains(mc.thePlayer.getNameClear()) && message.contains("pensó que era un buen momento de morir a manos de") ||
                                message.contains(mc.thePlayer.getNameClear()) && message.contains("ha sido asesinado por"))) {
                    ++killAmount;
                }
            }
            if (event.getPacket() instanceof S40PacketDisconnect) {
                currentTime = 0L;
                timeJoined = System.currentTimeMillis();
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public String getSessionLengthString() {
        long totalSeconds = (System.currentTimeMillis() - timeJoined) / 1000L;
        long hours = totalSeconds / 3600L;
        long minutes = totalSeconds % 3600L / 60L;
        long seconds = totalSeconds % 60L;
        return (hours > 0L ? hours + "h, " : "") + (minutes > 0L ? minutes + "m, " : "") + seconds + "s";
    }

    @SuppressWarnings("unused")
    @Listen
    public void onRender(RenderEvent event) {
        if (resetPos.getValue()) {
            xValue.setValue(8F);
            yValue.setValue(160F);
            Slack.getInstance().getModuleManager().getInstance(SessionInfo.class).resetPos.setValue(false);
        }

        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        int x = xValue.getValue().intValue();
        int y = yValue.getValue().intValue();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int mouseX = Mouse.getX() * sr.getScaledWidth() / mc.displayWidth;
        int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / mc.displayHeight - 1;

        if (dragging) {
            xValue.setValue((float) (mouseX - dragX));
            yValue.setValue((float) (mouseY - dragY));
        }

        String username = Slack.getInstance().getModuleManager().getInstance(NameProtect.class).isToggle() ? "Slack User" : mc.getSession().getUsername();
        int alpha = new Color(0, 0, 0, alphaValue.getValue()).getRGB();

        double cornerRadius = roundedValue.getValue() ? 8.0 : 1.0;
        RenderUtil.drawRoundedRect(x, y, x + 170, y + 58, cornerRadius, alpha);

        String sessionInfo = "Session Info";
        String playTime = "Play time: " + getSessionLengthString();
        String ign = "IGN: " + username;
        String kills = "Kills: " + killAmount;

        int xOffset = 8;
        int[] yOffset = {8, 22, 34, 46};

        MCFontRenderer fontRenderer;
        switch (fontValue.getValue()) {
            case "Apple":
                fontRenderer = Fonts.apple20;
                break;
            case "Poppins":
                fontRenderer = Fonts.poppins20;
                break;
            default:
                fontRenderer = Fonts.apple20;
                break;
        }

        fontRenderer.drawStringWithShadow(sessionInfo, x + 53, y + yOffset[0], -1);
        fontRenderer.drawStringWithShadow(playTime, x + xOffset, y + yOffset[1], -1);
        fontRenderer.drawStringWithShadow(ign, x + xOffset, y + yOffset[2], -1);
        fontRenderer.drawStringWithShadow(kills, x + xOffset, y + yOffset[3], -1);

        handleMouseInput(mouseX, mouseY, x, y, 170, 58);
    }

    private void handleMouseInput(int mouseX, int mouseY, int rectX, int rectY, int rectWidth, int rectHeight) {
        if (Mouse.isButtonDown(0)) {
            if (!dragging) {
                if (mouseX >= rectX && mouseX <= rectX + rectWidth &&
                        mouseY >= rectY && mouseY <= rectY + rectHeight) {
                    dragging = true;
                    dragX = mouseX - xValue.getValue();
                    dragY = mouseY - yValue.getValue();
                }
            }
        } else {
            dragging = false;
        }
    }
}
