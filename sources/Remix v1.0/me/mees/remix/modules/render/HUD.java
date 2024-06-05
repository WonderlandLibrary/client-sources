package me.mees.remix.modules.render;

import net.minecraft.util.*;
import me.mees.remix.modules.render.hud.*;
import me.satisfactory.base.setting.*;
import net.minecraft.client.gui.*;
import me.satisfactory.base.*;
import me.satisfactory.base.module.*;
import java.util.*;
import me.satisfactory.base.events.*;
import me.mees.remix.font.*;
import me.satisfactory.base.gui.tabgui.*;
import pw.stamina.causam.scan.method.model.*;

public class HUD extends Module
{
    private static final ResourceLocation LOGO;
    
    public HUD() {
        super("HUD", 0, Category.RENDER);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Remix");
        options.add("Blur");
        this.addMode(new Remix(this));
        this.addMode(new Blur(this));
        this.addSetting(new Setting("HudMode", this, "Remix", options));
        this.addSetting(new Setting("TabGui", this, true));
        this.addSetting(new Setting("Logo", this, true));
        this.addSetting(new Setting("Arraylist", this, true));
        this.addSetting(new Setting("Animation Speed", this, 10.0, 1.0, 20.0, true, 1.0));
    }
    
    private void renderWatermark() {
        Gui.drawImage(HUD.LOGO, -5, -10, 100, 100);
    }
    
    private void renderArrayList(final CFontRenderer fu) {
        int yCount = 2;
        final ScaledResolution sc = new ScaledResolution(HUD.mc, HUD.mc.displayWidth, HUD.mc.displayHeight);
        Base.INSTANCE.getModuleManager();
        final List<Module> modules = new ArrayList<Module>(ModuleManager.modules.values());
        String string;
        final StringBuilder sb;
        final String moduleText0;
        String string2;
        final StringBuilder sb2;
        final String moduleText2;
        final int w1;
        final int w2;
        modules.sort((m0, m1) -> {
            new StringBuilder().append(m0.getName());
            if (m0.getMode() == null) {
                string = "";
            }
            else {
                string = " (" + m0.getMode().getName() + ")";
            }
            moduleText0 = sb.append(string).toString();
            new StringBuilder().append(m1.getName());
            if (m1.getMode() == null) {
                string2 = "";
            }
            else {
                string2 = " (" + m1.getMode().getName() + ")";
            }
            moduleText2 = sb2.append(string2).toString();
            w1 = fu.getStringWidth(moduleText0);
            w2 = fu.getStringWidth(moduleText2);
            return -Float.compare((float)w1, (float)w2);
        });
        for (final Module i : modules) {
            if (i.isEnabled() && i.getCategory() != Category.HIDDEN) {
                final String moduleText3 = i.getName() + ((i.getMode() == null) ? "" : (" §f(" + i.getMode().getName() + ")"));
                for (int j = 0; j < (int)this.getSettingByModule(this, "Animation Speed").doubleValue(); ++j) {
                    if (i.getAnimation() < fu.getStringWidth(moduleText3)) {
                        i.setAnimation(i.getAnimation() + 1);
                    }
                    if (i.getAnimation() > fu.getStringWidth(moduleText3)) {
                        i.setAnimation(i.getAnimation() - 1);
                    }
                }
                Gui.drawRoundedRect((float)(sc.getScaledWidth() - i.getAnimation() - 7), (float)(yCount - 2), (float)(sc.getScaledWidth() + 4 + 2), (float)(yCount + 10), 2.0f, -1610612736);
                Gui.drawRect(sc.getScaledWidth() - i.getAnimation() - 4, yCount + 9, sc.getScaledWidth() - i.getAnimation() - 6 - 1 + 2, yCount + 0, Base.INSTANCE.GetMainColor());
                fu.drawString(moduleText3, (float)(sc.getScaledWidth() - i.getAnimation() - 2), yCount + fu.getStringHeight(moduleText3) / 2 - 0.5f, Base.INSTANCE.GetMainColor());
                yCount += 12;
            }
            else {
                if (i.getAnimation() <= 0) {
                    continue;
                }
                i.setAnimation(0);
            }
        }
    }
    
    @Subscriber
    public void onRender(final Event2DRender event) {
        if (this.getSettingByModule(this, "TabGui").booleanValue()) {
            TabGUI.render(FontLoaders.confortaa18);
            TabGUI.init();
        }
        if (this.getSettingByModule(this, "Logo").booleanValue()) {
            this.renderWatermark();
        }
        if (this.getSettingByModule(this, "Arraylist").booleanValue()) {
            this.renderArrayList(FontLoaders.confortaa18);
        }
    }
    
    static {
        LOGO = new ResourceLocation("remix/remixlogotab.png");
    }
}
