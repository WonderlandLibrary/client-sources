package rip.athena.client.modules.impl.render;

import rip.athena.client.utils.*;
import rip.athena.client.config.*;
import java.awt.*;
import rip.athena.client.gui.hud.*;
import rip.athena.client.modules.*;
import org.lwjgl.opengl.*;
import rip.athena.client.*;
import net.minecraft.client.gui.*;
import rip.athena.client.utils.font.*;
import rip.athena.client.utils.render.*;
import rip.athena.client.events.types.input.*;
import rip.athena.client.events.*;

public class CPS extends Module
{
    private final ClickCounter leftClickCounter;
    private final ClickCounter rightClickCounter;
    @ConfigValue.Boolean(name = "Right Click Counter")
    private boolean rightClick;
    @ConfigValue.Color(name = "Color")
    private Color color;
    @ConfigValue.Boolean(name = "Background")
    private boolean backGround;
    @ConfigValue.List(name = "Display Mode", values = { "Circle", "Modern", "Fade", "Old" }, description = "Chose display of background")
    private String backgroundMode;
    @ConfigValue.Color(name = "Background Color")
    private Color background;
    @ConfigValue.Integer(name = "Transparency", min = 0, max = 255)
    private int transparency;
    @ConfigValue.Boolean(name = "Custom Font")
    private boolean customFont;
    @ConfigValue.Boolean(name = "Static Chroma")
    private boolean isUsingStaticChroma;
    @ConfigValue.Boolean(name = "Wave Chroma")
    private boolean isUsingWaveChroma;
    private HUDElement hud;
    private int width;
    private int height;
    
    public CPS() {
        super("CPS", Category.RENDER, "Athena/gui/mods/cps.png");
        this.leftClickCounter = new ClickCounter();
        this.rightClickCounter = new ClickCounter();
        this.rightClick = false;
        this.color = Color.WHITE;
        this.backGround = true;
        this.backgroundMode = "Circle";
        this.background = new Color(0, 0, 0, 150);
        this.transparency = 255;
        this.customFont = false;
        this.isUsingStaticChroma = false;
        this.isUsingWaveChroma = false;
        this.width = 56;
        this.height = 18;
        (this.hud = new HUDElement("cps", this.width, this.height) {
            @Override
            public void onRender() {
                CPS.this.render();
            }
        }).setX(1);
        this.hud.setY(175);
        this.addHUD(this.hud);
    }
    
    public void render() {
        if (CPS.mc.gameSettings.showDebugInfo) {
            return;
        }
        GL11.glPushMatrix();
        final String string = this.leftClickCounter.getCps() + (this.rightClick ? (" | " + this.rightClickCounter.getCps()) : "") + " CPS";
        final int width = this.hud.getWidth();
        final int height = this.hud.getHeight();
        if (this.backGround) {
            if (this.backgroundMode.equalsIgnoreCase("Modern")) {
                if (Athena.INSTANCE.getThemeManager().getTheme().isTriColor()) {
                    RoundedUtils.drawGradientRound((float)this.hud.getX(), (float)this.hud.getY(), (float)this.hud.getWidth(), (float)this.hud.getHeight(), 6.0f, Athena.INSTANCE.getThemeManager().getTheme().getFirstColor(), Athena.INSTANCE.getThemeManager().getTheme().getSecondColor(), Athena.INSTANCE.getThemeManager().getTheme().getThirdColor(), Athena.INSTANCE.getThemeManager().getTheme().getFirstColor());
                }
                else {
                    RoundedUtils.drawGradientRound((float)this.hud.getX(), (float)this.hud.getY(), (float)this.hud.getWidth(), (float)this.hud.getHeight(), 6.0f, Athena.INSTANCE.getThemeManager().getTheme().getFirstColor(), Athena.INSTANCE.getThemeManager().getTheme().getFirstColor(), Athena.INSTANCE.getThemeManager().getTheme().getSecondColor(), Athena.INSTANCE.getThemeManager().getTheme().getSecondColor());
                }
            }
            else if (this.backgroundMode.equalsIgnoreCase("Circle")) {
                RoundedUtils.drawGradientRound((float)this.hud.getX(), (float)this.hud.getY(), (float)this.hud.getWidth(), (float)this.hud.getHeight(), 6.0f, ColorUtil.getClientColor(0, this.transparency), ColorUtil.getClientColor(90, this.transparency), ColorUtil.getClientColor(180, this.transparency), ColorUtil.getClientColor(270, this.transparency));
            }
            else if (this.backgroundMode.equalsIgnoreCase("Fade")) {
                RoundedUtils.drawRoundedRect((float)this.hud.getX(), (float)this.hud.getY(), (float)(this.hud.getX() + width), (float)(this.hud.getY() + height), 8.0f, Athena.INSTANCE.getThemeManager().getTheme().getAccentColor().getRGB());
            }
            else {
                Gui.drawRect(this.hud.getX(), this.hud.getY(), this.hud.getX() + width, this.hud.getY() + height, this.background.getRGB());
            }
        }
        final float posY = (float)(this.hud.getY() + 2);
        final float posX = (float)(this.hud.getX() + 9);
        if (this.customFont) {
            this.hud.setWidth(FontManager.getProductSansRegular(25).width(string) + 16);
            this.hud.setHeight((int)rip.athena.client.font.FontManager.baloo17.getHeight(string) + 7);
            if (this.isUsingStaticChroma) {
                DrawUtils.drawCustomFontChromaString(FontManager.getProductSansRegular(25), string, (int)posX, (int)posY + 1, true, true);
            }
            else if (this.isUsingWaveChroma) {
                DrawUtils.drawCustomFontChromaString(FontManager.getProductSansRegular(25), string, (int)posX, (int)posY + 1, false, true);
            }
            else {
                FontManager.getProductSansRegular(25).drawString(string, (int)posX + 1, (int)posY + 2, this.color.getRGB());
            }
        }
        else {
            this.hud.setWidth(CPS.mc.fontRendererObj.getStringWidth(string) + 16);
            this.hud.setHeight(CPS.mc.fontRendererObj.FONT_HEIGHT + 9);
            if (this.isUsingStaticChroma) {
                DrawUtils.drawChromaString(string, posX, posY + 3.0f, true, true);
            }
            else if (this.isUsingWaveChroma) {
                DrawUtils.drawChromaString(string, posX, posY + 3.0f, false, true);
            }
            else {
                CPS.mc.fontRendererObj.drawStringWithShadow(string, posX, posY + 3.0f, this.color.getRGB());
            }
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    @SubscribeEvent
    public void onClick(final MouseDownEvent e) {
        if (e.getButton() == 0) {
            this.leftClickCounter.onClick();
        }
        else if (e.getButton() == 1) {
            this.rightClickCounter.onClick();
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
}
