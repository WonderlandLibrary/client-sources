package me.kaimson.melonclient.gui.settings;

import me.kaimson.melonclient.features.*;
import com.google.common.collect.*;
import me.kaimson.melonclient.*;
import java.util.*;
import me.kaimson.melonclient.gui.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import me.kaimson.melonclient.config.*;
import java.io.*;
import me.kaimson.melonclient.gui.elements.*;
import me.kaimson.melonclient.utils.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;

public class GuiModules extends SettingsBase
{
    private int row;
    private int column;
    private int columns;
    private int gap;
    public static int scrollState;
    private ElementTextfield search;
    
    public GuiModules(final axu parentScreen) {
        super(parentScreen);
        this.columns = 3;
    }
    
    @Override
    public void b() {
        this.initGui(false);
    }
    
    public void initGui(final boolean keepSearch) {
        this.row = 1;
        this.column = 1;
        this.gap = this.getLayoutWidth(this.getMainWidth() / 6) / 8;
        this.elements.clear();
        this.components.clear();
        if (keepSearch) {
            this.elements.add(this.search);
        }
        else {
            this.elements.add(this.search = new ElementTextfield(this.l / 2 + this.getWidth() / 2 - 104, this.m / 2 - this.getHeight() / 2 + 4, 100, 10, "Search...", this));
        }
        final LinkedHashSet<Module> modules = new LinkedHashSet<Module>(ModuleManager.INSTANCE.modules);
        final Set<Module> toRemove = (Set<Module>)Sets.newLinkedHashSet();
        final Set<Module> set;
        modules.forEach(module -> {
            if (Client.getSearchPattern(this.search.getText()).matcher(module.displayName).find() || module.settings.stream().anyMatch(setting -> Client.getSearchPattern(this.search.getText()).matcher(setting.getDescription()).find())) {
                this.addElement(module);
            }
            else {
                set.add(module);
            }
            return;
        });
        if (keepSearch) {
            modules.removeAll(toRemove);
        }
        super.b();
        this.registerScroll(new Scroll(modules, this.l, this.m, this.m / 2 - this.getHeight() / 2 + 20, this.m / 2 + this.getHeight() / 2 - 20, 40, this.l / 2 + this.getWidth() / 2 - 4, this.columns));
        this.setScrollState(GuiModules.scrollState);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawBackground();
        GuiModules.scrollState = this.getScrollState();
        final avr sr = new avr(this.j);
        final int x = (this.l / 2 - this.getWidth() / 2) * sr.e();
        final int y = (this.m / 2 - this.getHeight() / 2 + 1) * sr.e();
        final int xWidth = (this.l / 2 + this.getWidth() / 2) * sr.e() - x;
        final int yHeight = (this.m / 2 + this.getHeight() / 2 - 20) * sr.e() - y;
        GL11.glEnable(3089);
        this.scissorFunc(sr, x, y, xWidth, yHeight);
        super.a(mouseX, mouseY, partialTicks);
        GL11.glDisable(3089);
    }
    
    @Override
    public void m() {
        super.m();
        Keyboard.enableRepeatEvents(false);
        ModuleConfig.INSTANCE.saveConfig();
    }
    
    @Override
    protected void a(final char typedChar, final int keyCode) throws IOException {
        super.a(typedChar, keyCode);
        if (this.search.isFocused()) {
            this.initGui(true);
        }
    }
    
    private void addElement(final Module module) {
        final int minWidth = 90;
        int margin = (this.getMainWidth() / 6 < 70) ? 10 : (this.getMainWidth() / 6);
        margin = 15;
        final LayoutBuilder lb = new LayoutBuilder(this.l / 2 - this.getWidth() / 2 + 18 + margin, this.getLayoutWidth(margin), this.gap, this.columns);
        final int boxWidth = Math.max(minWidth, lb.getSplittedWidth());
        final int boxHeight = 23;
        final int x = lb.getCoordinateForIndex(this.column - 1);
        final int y = (int)this.getRowHeight(this.row, boxHeight + 10);
        this.elements.add(new ElementModule(x + 1, y + 1, boxWidth, boxHeight, module, button -> {
            if (this.elements.stream().filter(element -> element != button).noneMatch(element -> element.hovered)) {
                ModuleConfig.INSTANCE.setEnabled(module, !ModuleConfig.INSTANCE.isEnabled(module));
            }
            return;
        }));
        if (module.settings.size() > 0) {
            this.elements.add(new ElementModuleSettings(x + boxWidth - 8, y + boxHeight - 8, 6, 6, "icons/settings.png", 14, true, module, element -> this.nextGui(new GuiModuleSettings(module, this))));
        }
        ++this.column;
        if (this.column > this.columns) {
            this.column = 1;
            ++this.row;
        }
    }
    
    private double getRowHeight(double row, final int buttonHeight) {
        --row;
        return this.m / 2 - this.getHeight() / 2 + 20 + row * (buttonHeight + 10);
    }
    
    private int getLayoutWidth(final int margin) {
        return this.l / 2 + this.getWidth() / 2 - margin - (this.l / 2 - this.getWidth() / 2 + 18 + margin);
    }
    
    private int getLayoutHeight(final int margin) {
        return this.m / 2 + this.getHeight() / 2 + margin - (this.m / 2 - this.getHeight() / 2 - margin);
    }
    
    private int getMainWidth() {
        return this.l / 2 + this.getWidth() / 2 - (this.l / 2 - this.getWidth() / 2 + 16);
    }
    
    static {
        GuiModules.scrollState = 0;
    }
    
    public static class Scroll extends GuiScrolling
    {
        private final Set<?> list;
        private final int scrollbarX;
        private final int columns;
        private int expandedWidth;
        
        public Scroll(final Set<?> list, final int width, final int height, final int topIn, final int bottomIn, final int slotHeightIn, final int scrollbarX, final int columns) {
            super(ave.A(), width, height, topIn, bottomIn, slotHeightIn);
            this.list = list;
            this.scrollbarX = scrollbarX;
            this.columns = columns;
        }
        
        public void expandBy(final int expandBy) {
            this.expandedWidth += expandBy;
        }
        
        protected int k() {
            return super.k() + this.expandedWidth;
        }
        
        public int d() {
            return this.scrollbarX;
        }
        
        @Override
        public void drawScroll(final int i, final int j) {
            final int j2 = this.m();
            if (j2 > 0) {
                int height = (this.e - this.d) * (this.e - this.d) / this.k();
                height = ns.a(height, 32, this.e - this.d - 8);
                height -= (int)Math.min((this.n < 0.0) ? ((double)(int)(-this.n)) : ((double)((this.n > this.m()) ? ((int)this.n - this.m()) : 0)), height * 0.75);
                final int minY = Math.min(Math.max(this.n() * (this.e - this.d - height) / this.m() + this.d, this.d), this.e - height);
                final Color c = new Color(255, 255, 255, 255);
                GLRectUtils.drawRoundedRect((float)(j - 5), (float)this.d, (float)(j - 5 + 3), (float)(this.e - 1), 1.5f, new Color(0, 0, 0, 100).getRGB());
                GLRectUtils.drawRoundedRect((float)(j - 5), (float)minY, (float)(j - 5 + 3), (float)(minY + height - 1), 1.5f, Client.getMainColor(150));
                GLRectUtils.drawRoundedRect((float)(j - 5), (float)minY, j - 5 + 2.75f, minY + height - 1.25f, 1.5f, Client.getMainColor(255));
            }
            this.b(this.i, this.j);
        }
        
        @Override
        public void a(final int mouseXIn, final int mouseYIn, final float p_148128_3_) {
            super.a(mouseXIn, mouseYIn, p_148128_3_);
            this.i = mouseXIn;
            this.j = mouseYIn;
        }
        
        protected int b() {
            if (this.list.size() % this.columns != 0) {
                return (int)Math.ceil(this.list.size() / (double)this.columns);
            }
            return this.list.size() / this.columns;
        }
        
        protected void a(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        }
        
        protected boolean a(final int slotIndex) {
            return false;
        }
        
        protected void a() {
        }
        
        protected void a(final int entryID, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int mouseXIn, final int mouseYIn) {
        }
    }
}
