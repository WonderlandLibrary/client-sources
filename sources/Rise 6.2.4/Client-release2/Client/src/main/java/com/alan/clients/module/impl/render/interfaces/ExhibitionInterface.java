package com.alan.clients.module.impl.render.interfaces;

import com.alan.clients.component.impl.player.PingComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.impl.render.Interface;
import com.alan.clients.module.impl.render.interfaces.api.ModuleComponent;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.StringValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExhibitionInterface extends Mode<Interface> {

    private final ModeValue colorMode = new ModeValue("ArrayList Color Mode", this) {{
        add(new SubMode("Static"));
        add(new SubMode("Rainbow"));
        add(new SubMode("Fade"));
        //  add(new SubMode("Trans"));
        setDefault("Rainbow");
    }};

    private final ModeValue alignment = new ModeValue("ArrayList Alignment", this) {{
        add(new SubMode("Right"));
        add(new SubMode("Left"));
        setDefault("Right");
    }};

    //private final BooleanValue customFont = new BooleanValue("Font", this, false);
    private final BooleanValue sideBar = new BooleanValue("Arraylist Side Bar", this, false);
    private final BooleanValue background = new BooleanValue("Arraylist Background", this, false);
    private final BooleanValue showFpsOption = new BooleanValue("Show Fps", this, false);
    private final BooleanValue showTimeOption = new BooleanValue("Show Time", this, false);
    private final BooleanValue showPingOption = new BooleanValue("Show Ping", this, false);
    private final BooleanValue showCoordinates = new BooleanValue("Show Coordinates", this, true);
    private final StringValue customClientName = new StringValue("Custom Client Name", this, "Rise");

    public ExhibitionInterface(String name, Interface parent) {
        super(name, parent);
    }

    private static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 10.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.6f, 1f).getRGB();
    }

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (mc == null || mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null) {
            return;
        }

        Font interfaceFont = mc.fontRendererObj;
        //if (customFont.getValue()) {
        //   interfaceFont = FontManager.getSanFan(20);
        //  }

        int moduleCount = 0;
        Color[] colors = {new Color(91, 206, 250), new Color(245, 169, 184), Color.WHITE, new Color(245, 169, 184),}; // Define the pattern of colors
        int colorIndex = 0;

        this.getParent().setModuleSpacing(this.mc.fontRendererObj.height() + 2F);
        this.getParent().setWidthComparator(this.mc.fontRendererObj);
        this.getParent().setEdgeOffset(3);

        double y = mc.scaledResolution.getScaledHeight() - 10;
        String posX2 = String.valueOf(Math.round(mc.thePlayer.posX));
        String posY2 = String.valueOf(Math.round(mc.thePlayer.posY));
        String posZ2 = String.valueOf(Math.round(mc.thePlayer.posZ));

        for (final ModuleComponent moduleComponent : this.getParent().getActiveModuleComponents()) {
            if (moduleComponent.animationTime == 0) {
                continue;
            }
            final boolean hasTag = !moduleComponent.getTag().isEmpty() && this.getParent().suffix.getValue();

            double posX = moduleComponent.getPosition().getX();
            double posY = moduleComponent.getPosition().getY();

            if (alignment.getValue().getName().equals("Left")) {
                posY = moduleComponent.getPosition().getY() + interfaceFont.height() + 2;
                if (sideBar.getValue()) {
                    posX = 3 + moduleComponent.animationTime - 8;
                } else {
                    posX = 3 + moduleComponent.animationTime - 10;

                }
            }

            Color finalColor = moduleComponent.getColor();
            Color color = this.getTheme().getFirstColor();

            switch (colorMode.getValue().getName()) {

                case "Fade":
                    color = this.getTheme().getAccentColor(new Vector2d(0, moduleComponent.getPosition().getY()));
                    break;

                case "Rainbow":
                    color = new Color(rainbow(500 * moduleCount / 6));
                    break;

                case "Trans":
                    finalColor = colors[colorIndex];
                    break;

            }

            if (background.getValue()) {
                RenderUtil.rectangle(posX - 2, posY - 2, (moduleComponent.nameWidth + moduleComponent.tagWidth) + 4, this.getParent().moduleSpacing, getTheme().getBackgroundShade());
            }

            if (sideBar.getValue()) {
                if (this.alignment.getValue().getName().equals("Left")) {
                    RenderUtil.rectangle(posX - (this.background.getValue() ? 2 : 3), posY - 2, 1, this.getParent().moduleSpacing, color);
                } else {
                    RenderUtil.rectangle(posX + (moduleComponent.nameWidth + moduleComponent.tagWidth) + 2, posY - 2, 1, this.getParent().moduleSpacing, color);
                }
            }

            interfaceFont.drawWithShadow(moduleComponent.getDisplayName(), posX, posY, finalColor.getRGB());
            moduleCount++;
            colorIndex = (colorIndex + 1) % colors.length;

            if (hasTag) {
                interfaceFont.drawWithShadow(moduleComponent.getDisplayTag(), posX + moduleComponent.getNameWidth() + 3, posY, 0xFFCCCCCC);
            }

            moduleComponent.setColor(color);
        }

        int colour = colorMode.getValue().getName().equals("Rainbow") ? rainbow(1000) : getTheme().getFirstColor().getRGB();
        interfaceFont.drawWithShadow(getWatermark(), 3, 3, colour);

        if (showCoordinates.getValue()) {
            interfaceFont.drawWithShadow("X:§7 " + posX2, 3, y - mc.fontRendererObj.height() * 2, colour);
            interfaceFont.drawWithShadow("Y:§7 " + posY2, 3, y - mc.fontRendererObj.height(), colour);
            interfaceFont.drawWithShadow("Z:§7 " + posZ2, 3, y, colour);
        }
    };

    private String getWatermark() {
        if (!customClientName.getValue().isEmpty()) {
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
            String formattedTime = sdf.format(currentDate);

            String text = customClientName.getValue().charAt(0) + "§7" + customClientName.getValue().substring(1);

            if (showTimeOption.getValue()) text += " [§f" + formattedTime + "§7]";
            if (showFpsOption.getValue()) text += " [§f" + Minecraft.getDebugFPS() + " FPS§7]";
            if (showPingOption.getValue()) text += " [§f" + PingComponent.getPing() + "ms§7]";
            return text;

        } else {
            customClientName.setValue("Rise");
        }
        return null;
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> threadPool.execute(() -> {
        // modules in the top right corner of the screen
        for (final ModuleComponent moduleComponent : this.getParent().getActiveModuleComponents()) {
            if (moduleComponent.animationTime == 0) {
                continue;
            }
            Font interfaceFont = mc.fontRendererObj;
            //if (customFont.getValue()) {
            //   interfaceFont = FontManager.getSanFan(20);
            // }

            moduleComponent.setHasTag(!moduleComponent.getTag().isEmpty() && this.getParent().suffix.getValue());
            String name = (this.getParent().lowercase.getValue() ? moduleComponent.getTranslatedName().toLowerCase() : moduleComponent.getTranslatedName())
                    .replace(getParent().getRemoveSpaces().getValue() ? " " : "", "");

            String tag = (this.getParent().lowercase.getValue() ? moduleComponent.getTag().toLowerCase() : moduleComponent.getTag())
                    .replace(getParent().getRemoveSpaces().getValue() ? " " : "", "");

            moduleComponent.setNameWidth(interfaceFont.width(name));
            moduleComponent.setTagWidth(moduleComponent.isHasTag() ? (interfaceFont.width(tag) + 3) : 0);
            moduleComponent.setDisplayName(name);
            moduleComponent.setDisplayTag(tag);
        }
    });
}
