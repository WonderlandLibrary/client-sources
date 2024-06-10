package me.kaimson.melonclient.gui;

import java.util.*;
import me.kaimson.melonclient.gui.components.*;
import com.google.common.collect.*;
import java.io.*;
import me.kaimson.melonclient.gui.elements.settings.*;
import java.util.concurrent.*;
import org.lwjgl.opengl.*;
import me.kaimson.melonclient.gui.elements.*;
import me.kaimson.melonclient.gui.settings.*;

public abstract class GuiScreen extends axu
{
    public final List<Element> elements;
    public final List<Component> components;
    private ScheduledExecutorService executorService;
    private GuiScrolling scroll;
    private int xPosition;
    private int yPosition;
    private int scissorX;
    private int scissorY;
    private boolean scissor;
    protected boolean enableInput;
    
    public GuiScreen() {
        this.elements = (List<Element>)Lists.newArrayList();
        this.components = (List<Component>)Lists.newArrayList();
        this.enableInput = true;
    }
    
    public void b() {
        super.b();
        this.elements.forEach(Element::init);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.a(mouseX, mouseY, mouseButton);
        this.elements.forEach(element -> element.mouseClicked(mouseX, mouseY, mouseButton));
        this.components.forEach(component -> component.elements.forEach(element -> element.mouseClicked(mouseX, mouseY, mouseButton)));
    }
    
    protected void b(final int mouseX, final int mouseY, final int state) {
        super.b(mouseX, mouseY, state);
        this.elements.forEach(element -> element.mouseReleased(mouseX, mouseY, state));
        this.components.forEach(component -> component.elements.forEach(element -> element.mouseReleased(mouseX, mouseY, state)));
    }
    
    protected void a(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            if (this.elements.stream().noneMatch(element -> element instanceof SettingElementKeybind) || this.elements.stream().filter(element -> element instanceof SettingElementKeybind).noneMatch(element -> element.selection)) {
                super.a(typedChar, keyCode);
            }
        }
        else {
            super.a(typedChar, keyCode);
        }
        this.elements.forEach(element -> element.keyTyped(typedChar, keyCode));
        this.components.forEach(component -> component.elements.forEach(element -> element.keyTyped(typedChar, keyCode)));
    }
    
    public void k() throws IOException {
        super.k();
        if (this.scroll != null && this.enableInput) {
            this.scroll.p();
        }
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        super.a(mouseX, mouseY, partialTicks);
        if (this.executorService == null) {
            (this.executorService = Executors.newSingleThreadScheduledExecutor()).scheduleAtFixedRate(() -> {
                this.elements.forEach(Element::update);
                this.components.forEach(component -> component.elements.forEach(Element::update));
                return;
            }, 0L, 16L, TimeUnit.MILLISECONDS);
        }
        if (this.scroll != null) {
            this.scroll.a(mouseX, mouseY, partialTicks);
            this.yPosition = this.scroll.n();
        }
        this.elements.forEach(element -> {
            if (element.shouldScissor) {
                if (this.scissor) {
                    GL11.glEnable(3089);
                }
                element.addOffsetToY(-this.yPosition);
            }
            else {
                GL11.glDisable(3089);
            }
            if (element instanceof ElementModule) {
                if (mouseY > this.scissorY + 18) {
                    element.hovered(mouseX, mouseY);
                }
                else {
                    element.hovered = false;
                }
            }
            element.render(mouseX, mouseY, partialTicks);
            if (element.shouldScissor) {
                if (this.scissor) {
                    GL11.glDisable(3089);
                }
                element.addOffsetToY(this.yPosition);
            }
            return;
        });
        this.components.forEach(component -> component.render(mouseX, mouseY, partialTicks));
    }
    
    protected void scissorFunc(final avr sr, final int x, final int y, final int width, final int height) {
        this.scissorX = x;
        this.scissorY = y / sr.e();
        this.scissor = true;
        GL11.glScissor(x, y, width, height);
    }
    
    public void m() {
        super.m();
        if (this.executorService != null) {
            this.executorService.shutdownNow();
            this.executorService = null;
        }
    }
    
    protected void registerScroll(final GuiScrolling scroll) {
        (this.scroll = scroll).d(7, 8);
    }
    
    protected void expandScroll(final int expandedAmount) {
        ((GuiModules.Scroll)this.scroll).expandBy(expandedAmount);
        this.scroll.scrollTo((float)(this.scroll.n() + expandedAmount), true, 1500L);
    }
    
    protected void setScrollState(final int scrollState) {
        this.scroll.scrollTo((float)scrollState, true, 1500L);
    }
    
    protected int getScrollState() {
        return this.scroll.n();
    }
}
