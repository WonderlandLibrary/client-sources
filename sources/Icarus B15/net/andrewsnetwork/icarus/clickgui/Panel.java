// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.clickgui;

import net.andrewsnetwork.icarus.utilities.NahrFont;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.minecraft.client.gui.GuiScreen;
import net.andrewsnetwork.icarus.Icarus;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import java.util.Iterator;
import java.util.ArrayList;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.clickgui.element.elements.ElementStartMenuButton;
import net.andrewsnetwork.icarus.clickgui.element.Element;
import java.util.List;
import net.andrewsnetwork.icarus.clickgui.element.elements.ElementActionButton;

public class Panel
{
    private float yPos;
    private final ElementActionButton shutdownButton;
    private final List<Element> elements;
    public static final List<ElementStartMenuButton> startButtons;
    public boolean hoveringLogo;
    private boolean decompilerFucker;
    private Module.Category category;
    private int x;
    private int y;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private int toggleFade;
    private int openFade;
    private boolean open;
    private boolean drag;
    private boolean toggled;
    private boolean done;
    private boolean visible;
    
    static {
        startButtons = new ArrayList<ElementStartMenuButton>();
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public Panel(final Module.Category category, final int x, final int y, final int width, final int height, final boolean open, final boolean visible) {
        this.shutdownButton = new ElementActionButton("Shutdown", ElementActionButton.ActionType.SHUTDOWN);
        this.elements = new ArrayList<Element>();
        this.decompilerFucker = true;
        Panel.startButtons.add(new ElementStartMenuButton(this));
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.open = open;
        this.visible = visible;
        this.done = true;
        this.toggled = false;
        this.setupItems();
    }
    
    public void setupItems() {
    }
    
    public int getElementsHeight() {
        int height = 0;
        int count = 0;
        for (final Element element : this.elements) {
            if (count >= 10) {
                continue;
            }
            height += element.getHeight() + 1;
            ++count;
        }
        return height;
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        final float textWidth = RenderHelper.getNahrFont().getStringWidth(StringUtils.stripControlCodes(this.category.getName())) - 100.0f;
        return mouseX >= this.x - textWidth / 2.0f - 4.0f && mouseX <= this.x - textWidth / 2.0f + RenderHelper.getNahrFont().getStringWidth(StringUtils.stripControlCodes(this.category.getName())) + 4.0f && mouseY >= this.y - 1 && mouseY <= this.y + this.height - 3;
    }
    
    public boolean isHoveringOverLogoButton(final int mouseX, final int mouseY) {
        return mouseX >= 0 && mouseX <= 24 && mouseY >= RenderHelper.getScaledRes().getScaledHeight() - 22 && mouseY <= RenderHelper.getScaledRes().getScaledHeight();
    }
    
    public boolean isHoveringOverCloseAll(final int mouseX, final int mouseY) {
        return mouseX >= RenderHelper.getScaledRes().getScaledWidth() - 4 && mouseX <= RenderHelper.getScaledRes().getScaledWidth() && mouseY >= RenderHelper.getScaledRes().getScaledHeight() - 22 && mouseY <= RenderHelper.getScaledRes().getScaledHeight();
    }
    
    public void drag(final int mouseX, final int mouseY) {
        if (!this.visible) {
            return;
        }
        if (this.drag) {
            this.x = this.x2 + mouseX;
            this.y = this.y2 + mouseY;
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHoveringOverLogoButton(mouseX, mouseY)) {
            Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.25f, 0.75f);
            this.toggled = !this.toggled;
            this.toggleFade = RenderHelper.getScaledRes().getScaledHeight() - 23;
            return;
        }
        if (mouseButton == 0 && this.isHoveringOverCloseAll(mouseX, mouseY)) {
            for (final Panel panel : Icarus.getClickGUI().getPanels()) {
                panel.setVisible(false);
            }
            Minecraft.getMinecraft().displayGuiScreen(null);
            return;
        }
        if (this.toggled) {
            this.shutdownButton.mouseClicked(mouseX, mouseY, mouseButton);
            for (final ElementStartMenuButton sB : Panel.startButtons) {
                sB.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        if (!this.visible) {
            return;
        }
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.x2 = this.x - mouseX;
            this.y2 = this.y - mouseY;
            this.drag = true;
            return;
        }
        if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
            this.open = !this.open;
            this.openFade = this.y + 19;
            this.done = false;
            return;
        }
        if (!this.open) {
            return;
        }
        for (final Element element : this.elements) {
            if (element instanceof ElementStartMenuButton) {
                return;
            }
            element.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (!this.visible) {
            return;
        }
        if (state == 0) {
            this.drag = false;
        }
        if (!this.open) {
            return;
        }
        for (final Element element : this.elements) {
            element.mouseReleased(mouseX, mouseY, state);
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        if (this.decompilerFucker) {
            OutputStreamWriter request = new OutputStreamWriter(System.out);
            Label_0046: {
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0046;
                }
                finally {
                    request = null;
                }
                request = null;
            }
            this.decompilerFucker = false;
        }
        this.hoveringLogo = false;
        if (this.isHoveringOverLogoButton(mouseX, mouseY)) {
            this.hoveringLogo = true;
        }
        if (this.toggleFade > RenderHelper.getScaledRes().getScaledHeight() / 2) {
            this.toggleFade -= 5;
        }
        if (this.toggled) {
            GL11.glPushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            RenderHelper.drawBorderedCorneredRect(0.0f, this.toggleFade, 100.0f, RenderHelper.getScaledRes().getScaledHeight() - 24, 1.0f, Integer.MIN_VALUE, 1076176165);
            this.shutdownButton.setLocation(2, RenderHelper.getScaledRes().getScaledHeight() - 46);
            this.shutdownButton.setWidth(94);
            this.shutdownButton.setHeight(24);
            if (this.toggleFade < this.shutdownButton.getY()) {
                this.shutdownButton.drawScreen(mouseX, mouseY, button);
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/power.png"));
            Gui.drawScaledCustomSizeModalRect(8, RenderHelper.getScaledRes().getScaledHeight() - 44, 0.0f, 0.0f, 16, 16, 16, 16, 16.0f, 16.0f);
            GL11.glPopMatrix();
        }
        int yPos = 0;
        for (final ElementStartMenuButton sB : Panel.startButtons) {
            sB.setLocation(0, RenderHelper.getScaledRes().getScaledHeight() / 2 + yPos - 3);
            sB.setWidth(100);
            sB.setHeight(26);
            sB.setLimit(this.toggleFade);
            sB.drawScreen(mouseX, mouseY, button);
            yPos += sB.getHeight() - 4;
            if (this.yPos < yPos) {
                this.yPos = yPos;
            }
        }
        this.yPos = 0.0f;
        if (!this.visible) {
            return;
        }
        this.drag(mouseX, mouseY);
        float elementsHeight = this.open ? (this.getElementsHeight() - 1) : 0;
        final float textWidth = RenderHelper.getNahrFont().getStringWidth(StringUtils.stripControlCodes(this.category.getName()));
        RenderHelper.drawBorderedCorneredRect(this.getX() - (textWidth - 100.0f) / 2.0f - 4.0f, this.getY() - 1, this.getX() - (textWidth - 100.0f) / 2.0f + textWidth + 4.0f, this.getY() + 15, 1.0f, -587202560, this.open ? -865506967 : -872415232);
        if (this.isHovering(mouseX, mouseY)) {
            RenderHelper.drawRect(this.getX() - (textWidth - 100.0f) / 2.0f - 4.0f, this.getY() - 1, this.getX() - (textWidth - 100.0f) / 2.0f + textWidth + 4.0f, this.getY() + 15, this.drag ? -1862270977 : 1627389951);
        }
        RenderHelper.getNahrFont().drawString(this.getTitle(), this.getX() - (textWidth - 100.0f) / 2.0f, this.getY() - 1, NahrFont.FontType.SHADOW_THIN, -1, -16777216);
        if (this.open) {
            if (this.getTitle() == "Player") {
                elementsHeight += 34.0f;
            }
            if (this.getTitle() == "Movement") {
                elementsHeight += 51.0f;
            }
            if (this.getTitle() == "Combat") {
                elementsHeight += 17.0f;
            }
            if (this.getTitle() == "Misc") {
                elementsHeight += 34.0f;
            }
            for (int i = 0; i < 2; ++i) {
                if (this.openFade < this.getY() + elementsHeight + 19.0f && !this.done) {
                    ++this.openFade;
                }
                else {
                    this.done = true;
                }
            }
            RenderHelper.drawBorderedCorneredRect(this.getX(), this.getY() + 18, this.getX() + 100, this.done ? (this.getY() + elementsHeight + 19.0f) : ((float)this.openFade), 1.0f, -872415232, -580294295);
            int elementY = this.y + 16;
            for (final Element element : this.elements) {
                element.setLocation(this.x + 2, elementY);
                element.setWidth(this.getWidth() - 4);
                if (this.openFade > element.getY() + 17 || this.done) {
                    element.drawScreen(mouseX, mouseY, button);
                }
                elementY += element.getHeight() + 1;
            }
        }
    }
    
    public Module.Category getCategory() {
        return this.category;
    }
    
    public int getX() {
        return this.x;
    }
    
    public String getTitle() {
        return this.category.getName();
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public boolean getOpen() {
        return this.open;
    }
    
    public List<Element> getElements() {
        return this.elements;
    }
    
    public void setX(final int dragX) {
        this.x = dragX;
    }
    
    public void setY(final int dragY) {
        this.y = dragY;
    }
    
    public boolean getToggled() {
        return this.toggled;
    }
}
