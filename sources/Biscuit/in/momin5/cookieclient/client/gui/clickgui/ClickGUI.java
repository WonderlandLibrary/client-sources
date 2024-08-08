package in.momin5.cookieclient.client.gui.clickgui;

import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.hud.HUDClickGUI;
import com.lukflug.panelstudio.hud.HUDPanel;
import com.lukflug.panelstudio.mc12.GLInterface;
import com.lukflug.panelstudio.mc12.MinecraftHUDGUI;
import com.lukflug.panelstudio.settings.*;
import com.lukflug.panelstudio.theme.*;
import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.HudModule;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.setting.Setting;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingColor;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import in.momin5.cookieclient.api.util.utils.font.FontUtils;
import in.momin5.cookieclient.api.util.utils.render.ColorMain;
import in.momin5.cookieclient.api.util.utils.render.CustomColor;
import in.momin5.cookieclient.api.util.utils.render.SyncableColorComponent;
import in.momin5.cookieclient.client.gui.clickgui.component.KeybindButton;
import in.momin5.cookieclient.client.modules.client.ClickGuiMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ClickGUI extends MinecraftHUDGUI {

    public static final int WIDTH = 100 ,HEIGHT = 11,DISTANCE = 10,HUD_BORDER = 2;
    private final Toggleable colorToggle;
    public final GUIInterface guiInterface;
    public final HUDClickGUI gui;
    private final Theme theme, clearTheme, customTheme;
    public static ClickGUI INSTANCE;

    public ClickGUI() {
        ColorScheme scheme = new SettingsColorScheme(((ClickGuiMod)ModuleManager.getModule("ClickGUI")).EnabledColor,((ClickGuiMod)ModuleManager.getModule("ClickGUI")).BackgroundColor,((ClickGuiMod)ModuleManager.getModule("ClickGUI")).SettingBackgroundColor,((ClickGuiMod)ModuleManager.getModule("ClickGUI")).OutlineColor,((ClickGuiMod)ModuleManager.getModule("ClickGUI")).FontColor,((ClickGuiMod)ModuleManager.getModule("ClickGUI")).opacity);

        clearTheme = new ClearTheme(scheme,false,HEIGHT,2);
        customTheme = new ClientTheme(scheme,HEIGHT,2);
        //newTheme = new NewTheme(scheme,HEIGHT,2);

        theme = new ThemeMultiplexer() {
            @Override
            protected Theme getTheme() {
                if(((ClickGuiMod)ModuleManager.getModule("ClickGUI")).theme.is("Clear")){
                    return clearTheme;
                }
                return customTheme;
            }
        };


        colorToggle=new Toggleable() {
            @Override
            public void toggle() {
                ColorMain.colorModel.increment();
            }

            @Override
            public boolean isOn() {
                return ColorMain.colorModel.is("RGB");
            }
        };


        guiInterface = new GUIInterface(true) {
            @Override
            public void drawString(Point pos, String s, Color c) {
                GLInterface.end();
                int x=pos.x+2, y=pos.y+1;
                if(ModuleManager.getModuleInnit("CustomFont").isEnabled()) FontUtils.drawStringWithShadow(true, s, x, y, new CustomColor(c));
                else FontUtils.drawStringWithShadow(false, s, x, y, new CustomColor(c));
                GLInterface.begin();
            }

            @Override
            public int getFontWidth(String s) {
                if(ModuleManager.isModuleEnabled("CustomFont")) return Math.round(FontUtils.getStringWidth(true,s))+4;
                else return Math.round(FontUtils.getStringWidth(false,s))+4;
            }

            @Override
            public int getFontHeight() {
                if(ModuleManager.isModuleEnabled("CustomFont")) return Math.round(FontUtils.getFontHeight(true))+2;
                else return Math.round(FontUtils.getFontHeight(false))+2;
            }

            @Override
            protected String getResourcePrefix() {
                return "cookie:assets/";
            }
        };
        gui=new HUDClickGUI(guiInterface,null) {
            @Override
            public void handleScroll (int diff) {
                super.handleScroll(diff);
                if (((ClickGuiMod)ModuleManager.getModuleInnit("ClickGUI")).ScrollMode.is("screen")) {
                    for (FixedComponent component: components) {
                        if (!hudComponents.contains(component)) {
                            Point p=component.getPosition(guiInterface);
                            p.translate(0,-diff);
                            component.setPosition(guiInterface,p);
                        }
                    }
                }
            }
        };
        Toggleable hudToggle = new Toggleable() {
            @Override
            public void toggle() {
                //render();
            }

            @Override
            public boolean isOn() {
                return hudEditor;

            }
        };

        for (Module module: ModuleManager.getModules()) {
            if (module instanceof HudModule) {
                ((HudModule)module).populate(theme);
                gui.addHUDComponent(new HUDPanel(((HudModule)module).getComponent(),theme.getPanelRenderer(),module,new SettingsAnimation(((ClickGuiMod)ModuleManager.getModuleInnit("ClickGUI")).AnimationSpeed),hudToggle,HUD_BORDER));
            }
        }
        Point pos=new Point(DISTANCE,DISTANCE);
        for (Category category: Category.values()) {
            DraggableContainer panel=new DraggableContainer(category.name,null,theme.getPanelRenderer(),new SimpleToggleable(false),new SettingsAnimation(((ClickGuiMod)ModuleManager.getModule("ClickGUI")).AnimationSpeed),null,new Point(pos),WIDTH) {
                @Override
                protected int getScrollHeight (int childHeight) {
                    if (((ClickGuiMod)ModuleManager.getModuleInnit("ClickGUI")).ScrollMode.equals("screen")) {
                        return childHeight;
                    }
                    return Math.min(childHeight,Math.max(HEIGHT*4,ClickGUI.this.height-getPosition(guiInterface).y-renderer.getHeight(open.getValue()!=0)-HEIGHT));
                }
            };
            gui.addComponent(panel);
            pos.translate(WIDTH+DISTANCE,0);
            for (Module module: ModuleManager.getModulesInCategory(category)) {
                addModule(panel,module);
            }
        }
    }

    private void addModule (CollapsibleContainer panel, Module module) {
        CollapsibleContainer container=new CollapsibleContainer(module.getName(),module.getDescription(),theme.getContainerRenderer(),new SimpleToggleable(false),new SettingsAnimation(((ClickGuiMod)ModuleManager.getModule("ClickGUI")).AnimationSpeed),module);
            panel.addComponent(container);
            for (Setting property: module.settings) {
                if (property instanceof SettingBoolean) {
                    container.addComponent(new BooleanComponent(property.name,null,theme.getComponentRenderer(),(SettingBoolean)property));
                } else if (property instanceof SettingNumber) {
                    container.addComponent(new NumberComponent(property.name,null,theme.getComponentRenderer(),(SettingNumber)property,((SettingNumber)property).getMinimum(),((SettingNumber)property).getMaximum()));
                }  else if (property instanceof SettingMode) {
                    container.addComponent(new EnumComponent(property.name,null,theme.getComponentRenderer(),(SettingMode)property));
                }	else if (property instanceof SettingColor) {
                    container.addComponent(new SyncableColorComponent(theme,(SettingColor)property,colorToggle,new SettingsAnimation(((ClickGuiMod)ModuleManager.getModule("ClickGUI")).AnimationSpeed)));
                }
            }
        container.addComponent(new KeybindButton(theme.getComponentRenderer(), module));
    }

    public static void renderItem (ItemStack item, Point pos) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glPopAttrib();
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getRenderItem().zLevel = -150.0f;
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(item,pos.x,pos.y);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRenderer,item,pos.x,pos.y);
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0F;
        GlStateManager.popMatrix();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GLInterface.begin();
    }

    public static void renderEntity (EntityLivingBase entity, Point pos, int scale) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glPopAttrib();
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.color(1,1,1,1);
        GuiInventory.drawEntityOnScreen(pos.x,pos.y,scale,28,60,entity);
        GlStateManager.popMatrix();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GLInterface.begin();
    }

    @Override
    protected HUDClickGUI getHUDGUI() {
        return gui;
    }

    @Override
    protected GUIInterface getInterface() {
        return guiInterface;
    }

    @Override
    protected int getScrollSpeed() {
        return (int) ((ClickGuiMod)ModuleManager.getModuleInnit("ClickGUI")).scrolls.getValue();
    }
}
