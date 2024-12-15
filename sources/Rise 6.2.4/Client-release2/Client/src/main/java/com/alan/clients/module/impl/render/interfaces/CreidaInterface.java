package com.alan.clients.module.impl.render.interfaces;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.impl.render.Interface;
import com.alan.clients.module.impl.render.interfaces.api.ModuleComponent;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.alan.clients.layer.Layers.*;

public final class CreidaInterface extends Mode<Interface> {

    private final BooleanValue fontSetting = new BooleanValue("Font", this, true);

    private static final Font FONT = Fonts.MINECRAFT.get();

    private static final double HEIGHT = FONT.height() + 2;

    private static final int OFFSET = 1;

    public CreidaInterface(String name, Interface parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        final List<ModuleComponent> activeModules = this.getParent().getActiveModuleComponents();

        for (final ModuleComponent module : activeModules) {

            final float moduleSpacing = this.fontSetting.getValue() ? 11 : 10;

            this.getParent().setModuleSpacing(this.mc.fontRendererObj.height() + 3F);

            if (this.getParent().moduleSpacing != moduleSpacing) {
                //this.getParent().setModuleSpacing(moduleSpacing);
            }

            this.getParent().setEdgeOffset(3);

            final double x = module.getPosition().getX() - OFFSET - 2;
            final double y = module.getPosition().getY() + OFFSET;

            final double width = (module.nameWidth + module.tagWidth) + 2 + 3;

            getLayer(BLUR).add(() -> RenderUtil.rectangle(x - 1, y - 2, width, HEIGHT + 1, new Color(0, 0, 0, 255)));
            getLayer(BLOOM).add(() -> {
                RenderUtil.rectangle(x - 1, y - 2, width, HEIGHT + 1, new Color(0, 0, 0, 255));
            });

            getLayer(REGULAR, 1).add(() -> {
                RenderUtil.rectangle(x - 1, y - 2, width, HEIGHT + 1, new Color(0, 0, 0, 110));

                RenderUtil.rectangle(x + module.nameWidth + module.tagWidth + 4, y - 2, 1f, HEIGHT + 1, this.getTheme().getAccentColor(
                        new Vector2d(module.getPosition().getX(), module.getPosition().getY() / 1.5f)));

                this.drawText(module, x + 1, y, this.getTheme().getAccentColor(new Vector2d(module.getPosition().getX(), module.getPosition().getY() / 1.5f)).getRGB());

                //this.getFont().drawStringWithShadow("FPS: " + EnumChatFormatting.WHITE + Minecraft.getDebugFPS(), 2, scaledResolution.getScaledHeight() / 1.3F + 138, this.getTheme().getAccentColor(new Vector2d(2, 3)).getRGB());

                // this.getFont().drawStringWithShadow("XYZ: " + EnumChatFormatting.WHITE + Math.round(this.mc.thePlayer.posX) + ", " + Math.round(this.mc.thePlayer.posY) + ", " +
                //         Math.round(this.mc.thePlayer.posZ), 2, scaledResolution.getScaledHeight() / 1.3F + 148, this.getTheme().getAccentColor(new Vector2d(2, 3)).getRGB());

            });

        }

        getLayer(REGULAR).add(() -> {
            final Date date = new Date();
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            final String time = sdf.format(date);

            final String text = Client.NAME + " Client" + " | " + Client.VERSION_FULL + " | " + this.mc.thePlayer.getCommandSenderName() + " | " + time;

            RenderUtil.roundedRectangle(2, 3, (this.fontSetting.getValue() ? 0 : 8) + this.getFont().width(text), 15, 4, new Color(0, 0, 0, 100));

            GlStateManager.resetColor();

            int w = 0;

            for (int i = 0; i < text.length(); i++) {
                final char c = text.charAt(i);

                this.getFont().drawWithShadow(String.valueOf(c), 6 + w, 8, this.getTheme().getAccentColor(new Vector2d(i, i)).getRGB());

                w += this.getFont().width(String.valueOf(c));
            }

        });

        getLayer(BLUR).add(() -> {
            final Date date = new Date();
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            final String time = sdf.format(date);

            final String text = Client.NAME + " Client" + " | " + Client.VERSION_FULL + " | " + this.mc.thePlayer.getCommandSenderName() + " | " + time;

            RenderUtil.roundedRectangle(2, 3, (this.fontSetting.getValue() ? 0 : 8) + this.getFont().width(text), 15, 4, new Color(0, 0, 0, 255));
        });

        getLayer(BLOOM).add(() -> {
            final Date date = new Date();
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            final String time = sdf.format(date);

            final String text = Client.NAME + " Client" + " | " + Client.VERSION_FULL + " | " + this.mc.thePlayer.getCommandSenderName() + " | " + time;

            RenderUtil.roundedRectangle(2, 3, (this.fontSetting.getValue() ? 0 : 8) + this.getFont().width(text) - 1, 15, 5, new Color(0, 0, 0, 255));
        });
    };

    private Font getFont() {
        return this.fontSetting.getValue() ? Fonts.MAIN.get(24, Weight.REGULAR) : Fonts.MINECRAFT.get();
    }

    private void drawText(ModuleComponent component, double x, double y, int hex) {

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.getFont().drawWithShadow(component.getDisplayName(), x, y, hex);

        if (component.isHasTag()) {
            this.getFont().drawWithShadow(component.getDisplayTag(), x + component.getNameWidth() + OFFSET + 3, y, 0xFFCCCCCC);
        }

        GlStateManager.disableBlend();
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        // modules in the top right corner of the screen
        for (final ModuleComponent moduleComponent : this.getParent().getActiveModuleComponents()) {
            if (moduleComponent.animationTime == 0) {
                continue;
            }

            moduleComponent.setHasTag(!moduleComponent.getTag().isEmpty() && this.getParent().suffix.getValue());

            final String name = (this.getParent().lowercase.getValue() ? moduleComponent.getTranslatedName().toLowerCase() : moduleComponent.getTranslatedName())
                    .replace(getParent().getRemoveSpaces().getValue() ? " " : "", "");

            final String tag = (this.getParent().lowercase.getValue() ? moduleComponent.getTag().toLowerCase() : moduleComponent.getTag())
                    .replace(getParent().getRemoveSpaces().getValue() ? " " : "", "");

            moduleComponent.setNameWidth(this.getFont().width(name));
            moduleComponent.setTagWidth(moduleComponent.isHasTag() ? (this.getFont().width(tag) + OFFSET + 3) : 1);
            moduleComponent.setDisplayName(name);
            moduleComponent.setDisplayTag(tag);
        }
    };
}