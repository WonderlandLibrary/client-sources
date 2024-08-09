package src.Wiksi.functions.api;

import src.Wiksi.Wiksi;
import src.Wiksi.functions.impl.misc.ClientSounds;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.ui.NotificationManager;
import src.Wiksi.utils.client.ClientUtil;
import src.Wiksi.utils.client.IMinecraft;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.TextFormatting;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import javax.management.Notification;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public abstract class Function implements IMinecraft {

    final String name;
    final src.Wiksi.functions.api.Category category;


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
        Wiksi.getInstance().getEventBus().register(this);
    }

    public void onDisable() {
        animation.animate(0, 0.25f, Easings.CIRC_OUT);
        Wiksi.getInstance().getEventBus().unregister(this);
    }


    public final void toggle() {
        if (Function.mc.player == null || Function.mc.world == null) {
            return;
        }
        boolean bl = this.state = !this.state;
        if (!this.state) {
            this.onDisable();
            NaksonPaster.NOTIFICATION_MANAGER.add("Функция " + this.name + " деактивирована", "", 1, NotificationManager.ImageType.FIRST_PHOTO);
        } else {
            this.onEnable();
            NaksonPaster.NOTIFICATION_MANAGER.add("Функция " + this.name + " активирована", "", 1, NotificationManager.ImageType.SECOND_PHOTO);
        }
        FunctionRegistry functionRegistry = Wiksi.getInstance().getFunctionRegistry();
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
                FunctionRegistry functionRegistry = Wiksi.getInstance().getFunctionRegistry();
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

    protected void init() {
    }
}