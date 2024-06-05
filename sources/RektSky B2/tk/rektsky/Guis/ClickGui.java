package tk.rektsky.Guis;

import net.minecraft.util.*;
import tk.rektsky.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import tk.rektsky.Module.*;
import java.util.*;

public class ClickGui extends GuiScreen
{
    private Module seletedModule;
    private static Module.Category currentTab;
    private GuiScreen previousScreen;
    private ResourceLocation left;
    private ResourceLocation right;
    private ResourceLocation icon;
    private UnicodeFontRenderer fontRendererObj;
    
    public ClickGui(final GuiScreen previousScreen) {
        this.left = new ResourceLocation("rektsky/clickgui/left.png");
        this.right = new ResourceLocation("rektsky/clickgui/right.png");
        this.icon = new ResourceLocation("rektsky/icons/icon.png");
        this.fontRendererObj = Client.getHDFont();
        this.previousScreen = previousScreen;
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final boolean enabledBlend = GL11.glIsEnabled(3042);
        if (!enabledBlend) {
            GL11.glEnable(3042);
        }
        Gui.drawRect(this.getResizedX(289), this.getResizedY(113), this.getResizedX(545), this.getResizedY(998), -13487566);
        Gui.drawRect(this.getResizedX(818), this.getResizedY(136), this.getResizedX(1600), this.getResizedY(944), -12632257);
        Gui.drawRect(this.getResizedX(545), this.getResizedY(128), this.getResizedX(818), this.getResizedY(956), -13092808);
        GlStateManager.color(255.0f, 255.0f, 255.0f, 255.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("rektsky/clickgui/icon.png"));
        Gui.drawModalRectWithCustomSizedTexture(this.getResizedX(290), this.getResizedY(130), 0.0f, 0.0f, this.getResizedX(100), this.getResizedY(100), (float)this.getResizedX(100), (float)this.getResizedY(100));
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)this.getResizedX(388), (float)this.getResizedY(137), 0.0f);
        GlStateManager.scale(1.3f, 1.3f, 0.0f);
        this.fontRendererObj.drawString("RektSky", 0.0f, 0.0f, 16777215);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)this.getResizedX(396), (float)this.getResizedY(170), 0.0f);
        GlStateManager.scale(0.7f, 0.7f, 0.0f);
        this.fontRendererObj.drawString("Version: B2", 0.0f, 0.0f, 16777215);
        this.fontRendererObj.drawString("Public Beta", 0.0f, 10.0f, 16777215);
        this.fontRendererObj.drawString("User: " + Client.userName, 0.0f, 20.0f, 16777215);
        this.fontRendererObj.drawString("Role: " + Client.role, 0.0f, 30.0f, 16777215);
        GlStateManager.popMatrix();
        Gui.drawRect(this.getResizedX(290), this.getResizedY(262), this.getResizedX(545), this.getResizedY(264), -9408400);
        final List<Module.Category> categories = Arrays.asList(Module.Category.values());
        for (int i = 1; i <= 7; ++i) {
            if (mouseX >= this.getResizedX(290) && mouseX <= this.getResizedX(545) && mouseY >= this.getResizedY(288 + 67 * (i - 1)) && mouseY <= this.getResizedY(288 + 67 * i)) {
                Gui.drawRect(this.getResizedX(290), this.getResizedY(288 + 67 * (i - 1)), this.getResizedX(545), this.getResizedY(288 + 67 * i), -10197916);
            }
            int c = 11053224;
            if (ClickGui.currentTab == categories.get(i - 1)) {
                c = 16777215;
            }
            this.fontRendererObj.drawString(categories.get(i - 1).getName(), (float)this.getResizedX(368), (float)this.getResizedY(317 + 67 * (i - 1)), c);
            Minecraft.getMinecraft().getTextureManager().bindTexture(categories.get(i - 1).getIcon());
            Gui.drawModalRectWithCustomSizedTexture(this.getResizedX(305), this.getResizedY(300 + 67 * (i - 1)), 0.0f, 0.0f, this.getResizedX(45), this.getResizedY(45), (float)this.getResizedX(45), (float)this.getResizedY(45));
        }
        final ArrayList<Module> modulesToRender = new ArrayList<Module>();
        if (ClickGui.currentTab != null) {
            for (final Module module : ModulesManager.getModules()) {
                if (module.category == ClickGui.currentTab) {
                    modulesToRender.add(module);
                }
            }
        }
        int j = 0;
        for (final Module module2 : modulesToRender) {
            int c2 = 7960953;
            if (mouseX >= this.getResizedX(560) && mouseY >= this.getResizedY(154 + 46 * j) && mouseX <= this.getResizedX(560) + this.fontRendererObj.getStringWidth("- " + module2.name)) {
                final int n = 154 + 46 * j;
                this.fontRendererObj.getClass();
                if (mouseY <= this.getResizedY(n + 9)) {
                    c2 = 11184810;
                }
            }
            if (module2.isToggled()) {
                c2 = 16777215;
            }
            this.fontRendererObj.drawString("- " + module2.name, (float)this.getResizedX(560), (float)this.getResizedY(154 + 46 * j), c2);
            ++j;
        }
        j = 0;
        if (!enabledBlend) {
            GL11.glDisable(3042);
        }
        if (this.seletedModule == null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)this.getResizedX(1200), (float)this.getResizedY(506), 0.0f);
            GlStateManager.scale(3.0f, 3.0f, 0.0f);
            this.fontRendererObj.drawString("Welcome to RektSky!", (float)(-this.fontRendererObj.getStringWidth("Welcome to RektSky!") / 2), 0.0f, 16777215);
            GlStateManager.popMatrix();
            final UnicodeFontRenderer fontRendererObj = this.fontRendererObj;
            final String text = "Right click a module to edit settings";
            final float x = (float)(this.getResizedX(1200) - this.fontRendererObj.getStringWidth("Right click a module to edit settings") / 2);
            final int resizedY = this.getResizedY(506);
            this.fontRendererObj.getClass();
            fontRendererObj.drawString(text, x, (float)(resizedY + 9 * 3), 16777215);
        }
        else {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)this.getResizedX(862), (float)this.getResizedY(172), 0.0f);
            GlStateManager.scale(2.0f, 2.0f, 0.0f);
            this.fontRendererObj.drawString(this.seletedModule.name, 0.0f, 0.0f, 16777215);
            GlStateManager.popMatrix();
            final UnicodeFontRenderer fontRendererObj2 = this.fontRendererObj;
            final String description = this.seletedModule.description;
            final float x2 = (float)this.getResizedX(892);
            final int resizedY2 = this.getResizedY(172);
            this.fontRendererObj.getClass();
            fontRendererObj2.drawString(description, x2, (float)(resizedY2 + 9 * 2), 16777215);
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final List<Module.Category> categories = Arrays.asList(Module.Category.values());
        for (int i = 1; i <= 7; ++i) {
            if (mouseX >= this.getResizedX(290) && mouseX <= this.getResizedX(545) && mouseY >= this.getResizedY(288 + 67 * (i - 1)) && mouseY <= this.getResizedY(288 + 67 * i)) {
                ClickGui.currentTab = categories.get(i - 1);
                return;
            }
        }
        final ArrayList<Module> modulesToRender = new ArrayList<Module>();
        if (ClickGui.currentTab != null) {
            for (final Module module : ModulesManager.getModules()) {
                if (module.category == ClickGui.currentTab) {
                    modulesToRender.add(module);
                }
            }
        }
        int j = 0;
        for (final Module module2 : modulesToRender) {
            if (mouseX >= this.getResizedX(560) && mouseY >= this.getResizedY(154 + 46 * j) && mouseX <= this.getResizedX(560) + this.fontRendererObj.getStringWidth("- " + module2.name)) {
                final int n = 154 + 46 * j;
                this.fontRendererObj.getClass();
                if (mouseY <= this.getResizedY(n + 9)) {
                    if (mouseButton == 0) {
                        module2.toggle();
                    }
                    if (mouseButton == 1) {
                        this.seletedModule = module2;
                    }
                }
            }
            ++j;
        }
        j = 0;
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    @Override
    protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
    }
    
    @Override
    public void initGui() {
        this.seletedModule = null;
    }
    
    public int getResizedX(final int x) {
        return Math.round(x / 2.0f);
    }
    
    public int getResizedY(final int y) {
        return Math.round(y / 2.0f);
    }
    
    static {
        ClickGui.currentTab = Module.Category.COMBAT;
    }
}
