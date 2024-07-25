package club.bluezenith.module.modules.misc.hackerdetector;

import club.bluezenith.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.moved.sussy.HoverEvent;
import net.minecraft.network.Packet;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static java.lang.Math.max;
import static java.lang.String.format;
import static net.minecraft.event.ClickEvent.Action.RUN_COMMAND;
import static net.minecraft.moved.sussy.HoverEvent.Action.SHOW_TEXT;

public abstract class Check {
    protected String name, fullName;
    private char checkType;

    protected PlayerInfo playerInfo;
    protected double violations, minViolations, maxViolations, violationBuffer;

    public Check(PlayerInfo playerInfo, String name, char checkType) {
        this.playerInfo = playerInfo;
        this.name = name;
        this.checkType = checkType;
        this.fullName = name + " " + checkType;
    }


    public abstract void process(Packet<?> packet);

    public void flag(double vl, String additionalInfo, Object... additionalInfoFormat) {
        final boolean targetOnMaxVio = true; //todo make an opton in hacker detector
        violations += vl;

        if(violations >= maxViolations) {

            if(targetOnMaxVio)
            getBlueZenith().getTargetManager().addTarget(playerInfo.getPlayerProfile().getName());

            final ChatComponentText message = new ChatComponentText(
                    format("§9Blue Zenith §7§l\u00bb §f§7%s has failed %s (VL: %s)",
                            playerInfo.getPlayerProfile().getName(),
                            getFullName(),
                            MathUtil.round(violations, 2)
                    )
            );

            if(additionalInfo != null) {
                final ChatComponentText hoverText = new ChatComponentText(
                        format("§9Hacker Detector" +
                                "\n§7Additional info: %s" +
                                "\n\n§cClick here to add the player to your targets list.",
                                format(additionalInfo, additionalInfoFormat))
                );

                final HoverEvent hoverEvent = new HoverEvent(SHOW_TEXT, hoverText);
                final ClickEvent clickEvent = new ClickEvent(RUN_COMMAND, ".target " + playerInfo.getPlayerProfile().getName());

                final ChatStyle chatStyle = new ChatStyle().setChatHoverEvent(hoverEvent).setChatClickEvent(clickEvent);
                message.setChatStyle(chatStyle);
            }

            Minecraft.getMinecraft().thePlayer.addChatMessage(message);

            violations -= violations / 2;
            violationBuffer = 0;
        }
    }

    protected void checkPassed(double vlMinus, double bufferMinus) {
        violationBuffer = max(0, violationBuffer - bufferMinus);

        if(violationBuffer == 0)
            violations = max(minViolations, vlMinus);
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.fullName;
    }
}
