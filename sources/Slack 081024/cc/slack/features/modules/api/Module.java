// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.slack.start.Slack;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.utils.EventUtil;
import cc.slack.utils.client.IMinecraft;
import cc.slack.utils.drag.DragUtil;
import cc.slack.utils.other.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

@Getter
@Setter
public abstract class Module implements IMinecraft {

    private final List<Value> setting = new ArrayList<>();

    final ModuleInfo moduleInfo = getClass().getAnnotation(ModuleInfo.class);
    private final String name = moduleInfo.name();
    private final String displayName = moduleInfo.displayName().isEmpty() ? moduleInfo.name() : moduleInfo.displayName();
    public final Category category = moduleInfo.category();
    private int key = moduleInfo.key();
    private boolean toggle;
    public boolean render = true;

    public TimeUtil enabledTime = new TimeUtil();
    public TimeUtil disabledTime = new TimeUtil();

    public void onToggled() {}
    public void onEnable() {}
    public void onDisable() {}
    public String getMode() { return ""; }
	public void setXYPosition(double x, double y) { }

    public void toggle() {
        setToggle(!toggle);
    }

    public void enableModule() {
        if(!toggle)
            toggle();
    }
    public void disableModule() {
        if(toggle)
            toggle();
    }

    public void setToggle(boolean toggle) {
        if (this.toggle == toggle) return;

        try {
            if (Slack.getInstance().getModuleManager().getInstance(Interface.class).sound.getValue())
                PlaySound();
        } catch (Exception ignored) {

        }

        this.toggle = toggle;

        if (toggle) {
            enabledTime.reset();
            EventUtil.register(this);
            onEnable();
            Slack.getInstance().addNotification("Enabled module: " + name + ".", " ", 2000L, Slack.NotificationStyle.SUCCESS);
        } else {
            disabledTime.reset();
            EventUtil.unRegister(this);
            onDisable();
            Slack.getInstance().addNotification("Disabled module: " + name + ".", " ", 2000L, Slack.NotificationStyle.FAIL);
        }
        onToggled();
    }

    public Value getValueByName(String n) {
        for (Value m : setting) {
            if (m.getName().equals(n))
                return m;
        }

        return null;
    }
    
	public DragUtil getPosition(){
		return null;
	}

    public void addSettings(Value... settings) {
        setting.addAll(Arrays.asList(settings));
    }

    private void PlaySound() {
        ResourceLocation soundLocation = new ResourceLocation("random.orb");
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(soundLocation, 5.0F));
    }
}
