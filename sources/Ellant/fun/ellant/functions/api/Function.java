package fun.ellant.functions.api;

import fun.ellant.Ellant;
import fun.ellant.functions.impl.render.ClientSounds;
import fun.ellant.functions.settings.Setting;
import fun.ellant.ui.NotificationManager;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.client.IMinecraft;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.Event;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public abstract class Function implements IMinecraft {

    final String name;
    final Category category;
    String desc = "";


    boolean state;
    @Setter
    int bind;
    final List<Setting<?>> settings = new ObjectArrayList<>();

    final Animation animation = new Animation();

    public Function() {
        this.name = getClass().getAnnotation(FunctionRegister.class).name();
        this.category = getClass().getAnnotation(FunctionRegister.class).type();
        this.desc = getClass().getAnnotation(FunctionRegister.class).desc();
        this.bind = getClass().getAnnotation(FunctionRegister.class).key();
    }

    public Function(String name) {
        this.name = name;
        this.category = Category.COMBAT;
        this.desc = desc;

    }

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(List.of(settings));
    }

    public boolean onEnable() {
        animation.animate(1, 0.25f, Easings.CIRC_OUT);
        Ellant.getInstance().getEventBus().register(this);
        return false;
    }

    public void onDisable() {
        animation.animate(0, 0.25f, Easings.CIRC_OUT);
        Ellant.getInstance().getEventBus().unregister(this);
    }


    public final void toggle() {
        if (Function.mc.player == null || Function.mc.world == null) {
            return;
        }
        boolean bl = this.state = !this.state;
        if (!this.state) {
            this.onDisable();
            NaksonPaster.NOTIFICATION_MANAGER.add("Функция " + this.name + " была выключена.", "", 1, NotificationManager.ImageType.FIRST_PHOTO);
        } else {
            this.onEnable();
            NaksonPaster.NOTIFICATION_MANAGER.add("Функция " + this.name + " была включена.", "", 1, NotificationManager.ImageType.SECOND_PHOTO);
        }
        FunctionRegistry functionRegistry = Ellant.getInstance().getFunctionRegistry();
        ClientSounds clientSounds = functionRegistry.getClientSounds();
        if (clientSounds != null && clientSounds.isState()) {
            String fileName = clientSounds.getFileName(this.state);
            float volume = ((Float)clientSounds.volume.get()).floatValue();
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
                FunctionRegistry functionRegistry = Ellant.getInstance().getFunctionRegistry();
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
