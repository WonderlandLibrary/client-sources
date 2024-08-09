package im.expensive.functions.api;

import im.expensive.Expensive;
import im.expensive.functions.impl.misc.ClientSounds;
import im.expensive.functions.settings.Setting;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.client.IMinecraft;
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
public abstract class Function implements IMinecraft {

    public static boolean unhooked;
    final String name;
    final Category category;

    boolean state;
    @Setter
    int bind;
    final List<Setting<?>> settings = new ObjectArrayList<>();

    final Animation animation = new Animation();

    public Function() {
        this.name = getClass().getAnnotation(FunctionRegister.class).name();
        this.category = getClass().getAnnotation(FunctionRegister.class).type();
        this.bind = getClass().getAnnotation(FunctionRegister.class).key();
    }

    public Function(String name) {
        this.name = name;
        this.category = Category.Combat;
    }

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(List.of(settings));
    }

    public void onEnable() {
        animation.animate(1, 0.25f, Easings.CIRC_OUT);
        Expensive.getInstance().getEventBus().register(this);
    }

    public void onDisable() {
        animation.animate(0, 0.25f, Easings.CIRC_OUT);
        Expensive.getInstance().getEventBus().unregister(this);
    }


    public final void toggle() {
        setState(!state, false);
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
                FunctionRegistry functionRegistry = Expensive.getInstance().getFunctionRegistry();
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
