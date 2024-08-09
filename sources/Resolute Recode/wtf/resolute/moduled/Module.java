package wtf.resolute.moduled;

import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.manage.Managed;
import wtf.resolute.moduled.impl.misc.ClientSounds;
import wtf.resolute.moduled.settings.Setting;
import wtf.resolute.utiled.client.ClientUtil;
import wtf.resolute.utiled.client.IMinecraft;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.TextFormatting;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public abstract class Module implements IMinecraft {

    final String name;
    final String server;
    final Categories category;

    boolean state;
    @Setter
    int bind;
    final List<Setting<?>> settings = new ObjectArrayList<>();

    final Animation animation = new Animation();

    public Module() {
        this.name = getClass().getAnnotation(ModuleAnontion.class).name();
        this.category = getClass().getAnnotation(ModuleAnontion.class).type();
        this.bind = getClass().getAnnotation(ModuleAnontion.class).key();
        this.server = getClass().getAnnotation(ModuleAnontion.class).server();
    }

    public Module(String name) {
        this.name = name;
        this.category = Categories.Combat;
        this.server = "";
    }

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(List.of(settings));
    }

    public void onEnable() {
        animation.animate(1, 0.25f, Easings.CIRC_OUT);
        ResoluteInfo.getInstance().getEventBus().register(this);
    }

    public void onDisable() {
        animation.animate(0, 0.25f, Easings.CIRC_OUT);
        ResoluteInfo.getInstance().getEventBus().unregister(this);
    }


    public final void toggle() {
        if (mc.player == null || mc.world == null) {
            return;
        }
        this.state = !state;
        if (!state)
            onDisable();
        else
            onEnable();
        Managed.NOTIFICATION_MANAGER.add(name + " was " + (state ? "enable" : "disable"), "",3);
        ModuleRegister functionRegistry = ResoluteInfo.getInstance().getFunctionRegistry();
        ClientSounds clientSounds = functionRegistry.getClientSounds();

        if (clientSounds != null && clientSounds.isState()) {
            String fileName = clientSounds.getFileName(state);
            float volume = clientSounds.volume.get();
            ClientUtil.playSound(fileName, volume, false);
        }
    }

    public final void setState(boolean newState, boolean config) {
        if (state == newState) {
            return;
        }

        state = newState;

        try {
            if (state) {
                onEnable();
            } else {
                onDisable();
            }
            if (!config) {
                ModuleRegister functionRegistry = ResoluteInfo.getInstance().getFunctionRegistry();
                ClientSounds clientSounds = functionRegistry.getClientSounds();

                if (clientSounds != null && clientSounds.isState()) {
                    String fileName = clientSounds.getFileName(state);
                    float volume = clientSounds.volume.get();
                    ClientUtil.playSound(fileName, volume, false);
                }
            }
        } catch (Exception e) {
            handleException(state ? "onEnable" : "onDisable", e);
        }

    }

    private void handleException(String methodName, Exception e) {
        if (mc.player != null) {
            print("[" + name + "] Произошла ошибка в методе " + TextFormatting.RED + methodName + TextFormatting.WHITE
                    + "() Предоставьте это сообщение разработчику: " + TextFormatting.GRAY + e.getMessage());
            e.printStackTrace();
        } else {
            System.out.println("[" + name + " Error" + methodName + "() Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
