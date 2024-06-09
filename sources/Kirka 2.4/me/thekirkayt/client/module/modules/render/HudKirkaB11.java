/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Aqua Theme;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Blue Theme;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Gradient Theme #1;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Green Theme;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Kirka Theme #1;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Kirka Theme #2;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Orange Theme;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Purple Theme;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Rainbow Theme #1;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Rainbow Theme #2;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Red Theme;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Yellow Theme;
import me.thekirkayt.client.module.modules.render.hudkirkab11.tabgui.LucidTabGui;
import me.thekirkayt.client.module.modules.render.hudkirkab11.tabgui.NewTabGui;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.KeyPressEvent;
import me.thekirkayt.event.events.Render2DEvent;
import me.thekirkayt.event.events.TickEvent;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Module.Mod(enabled=true, shown=false)
public class HudKirkaB11
extends Module {
    private Kirka Theme #1 Kirka Theme #1 = new Kirka Theme #1("Kirka Theme #1", true, this);
    private Kirka Theme #2 Kirka Theme #2 = new Kirka Theme #2("Kirka Theme #2", false, this);
    private Rainbow Theme #1 Rainbow Theme #1 = new Rainbow Theme #1("Rainbow Theme #1", false, this);
    private Rainbow Theme #2 Rainbow Theme #2 = new Rainbow Theme #2("Rainbow Theme #2", false, this);
    private Red Theme Red Theme = new Red Theme("Red Theme", false, this);
    private Orange Theme Orange Theme = new Orange Theme("Orange Theme", false, this);
    private Yellow Theme Yellow Theme = new Yellow Theme("Yellow Theme", false, this);
    private Green Theme Green Theme = new Green Theme("Green Theme", false, this);
    private Aqua Theme Aqua Theme = new Aqua Theme("Aqua Theme", false, this);
    private Blue Theme Blue Theme = new Blue Theme("Blue Theme", false, this);
    private Purple Theme Purple Theme = new Purple Theme("Purple Theme", false, this);
    private Gradient Theme #1 Gradient Theme #1 = new Gradient Theme #1("Gradient Theme #1", false, this);
    public LucidTabGui lucidTab = new LucidTabGui("Kirka b11 TabGui (Black)", true, this);
    public NewTabGui newTab = new NewTabGui("Kirka b11 TabGui (Transparent)", false, this);
    @Option.Op(name="Show TabGui")
    private boolean tabgui = true;

    @Override
    public void preInitialize() {
        if (this.tabgui) {
            this.newTab.setupSizes();
            this.lucidTab.setupSizes();
        }
        OptionManager.getOptionList().add(this.Kirka Theme #1);
        OptionManager.getOptionList().add(this.Kirka Theme #2);
        OptionManager.getOptionList().add(this.Rainbow Theme #1);
        OptionManager.getOptionList().add(this.Rainbow Theme #2);
        OptionManager.getOptionList().add(this.Red Theme);
        OptionManager.getOptionList().add(this.Orange Theme);
        OptionManager.getOptionList().add(this.Yellow Theme);
        OptionManager.getOptionList().add(this.Green Theme);
        OptionManager.getOptionList().add(this.Aqua Theme);
        OptionManager.getOptionList().add(this.Blue Theme);
        OptionManager.getOptionList().add(this.Purple Theme);
        OptionManager.getOptionList().add(this.Gradient Theme #1);
        OptionManager.getOptionList().add(this.lucidTab);
        OptionManager.getOptionList().add(this.newTab);
        super.preInitialize();
    }

    @EventTarget
    private void onRender2D(Render2DEvent render2DEvent) {
        this.Kirka Theme #1.onRender(render2DEvent);
        this.Kirka Theme #2.onRender(render2DEvent);
        this.Rainbow Theme #1.onRender(render2DEvent);
        this.Rainbow Theme #2.onRender(render2DEvent);
        this.Red Theme.onRender(render2DEvent);
        this.Orange Theme.onRender(render2DEvent);
        this.Yellow Theme.onRender(render2DEvent);
        this.Green Theme.onRender(render2DEvent);
        this.Aqua Theme.onRender(render2DEvent);
        this.Blue Theme.onRender(render2DEvent);
        this.Purple Theme.onRender(render2DEvent);
        this.Gradient Theme #1.onRender(render2DEvent);
        if (this.tabgui) {
            this.newTab.onRender(render2DEvent);
            this.lucidTab.onRender(render2DEvent);
        }
    }

    @EventTarget
    private void onTick(TickEvent tickEvent) {
        Gradient Theme #1.updateFade();
    }

    @EventTarget
    private void onKeypress(KeyPressEvent keyPressEvent) {
        if (this.tabgui) {
            this.newTab.onKeypress(keyPressEvent);
            this.lucidTab.onKeypress(keyPressEvent);
        }
    }
}

