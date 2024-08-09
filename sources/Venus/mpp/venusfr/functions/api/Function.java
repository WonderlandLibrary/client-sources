/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.api;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.misc.ClientSounds;
import mpp.venusfr.functions.settings.Setting;
import mpp.venusfr.ui.NotificationManager;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.venusfr;
import net.minecraft.util.text.TextFormatting;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

public abstract class Function
implements IMinecraft {
    private final String name;
    private final Category category;
    private boolean state;
    private int bind;
    private final List<Setting<?>> settings = new ObjectArrayList();
    private final Animation animation = new Animation();

    public Function() {
        this.name = this.getClass().getAnnotation(FunctionRegister.class).name();
        this.category = this.getClass().getAnnotation(FunctionRegister.class).type();
        this.bind = this.getClass().getAnnotation(FunctionRegister.class).key();
    }

    public Function(String string) {
        this.name = string;
        this.category = Category.Combat;
    }

    public void addSettings(Setting<?> ... settingArray) {
        this.settings.addAll(List.of((Object[])settingArray));
    }

    public void onEnable() {
        this.animation.animate(1.0, 0.25, Easings.CIRC_OUT);
        venusfr.getInstance().getEventBus().register(this);
    }

    public void onDisable() {
        this.animation.animate(0.0, 0.25, Easings.CIRC_OUT);
        venusfr.getInstance().getEventBus().unregister(this);
    }

    public final void toggle() {
        if (Function.mc.player == null || Function.mc.world == null) {
            return;
        }
        this.state = !this.state;
        boolean bl = this.state;
        if (!this.state) {
            this.onDisable();
            venusfr.getInstance().getNotificationManager().add(this.name + " was disable", "", 3, NotificationManager.ImageType.FIRST_PHOTO);
        } else {
            this.onEnable();
            venusfr.getInstance().getNotificationManager().add(this.name + " was enable", "", 3, NotificationManager.ImageType.SECOND_PHOTO);
        }
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        ClientSounds clientSounds = functionRegistry.getClientSounds();
        if (clientSounds != null && clientSounds.isState()) {
            String string = clientSounds.getFileName(this.state);
            float f = ((Float)clientSounds.volume.get()).floatValue();
            ClientUtil.playSound(string, f, false);
        }
    }

    public final void setState(boolean bl, boolean bl2) {
        if (this.state == bl) {
            return;
        }
        this.state = bl;
        try {
            FunctionRegistry functionRegistry;
            ClientSounds clientSounds;
            if (this.state) {
                this.onEnable();
            } else {
                this.onDisable();
            }
            if (!bl2 && (clientSounds = (functionRegistry = venusfr.getInstance().getFunctionRegistry()).getClientSounds()) != null && clientSounds.isState()) {
                String string = clientSounds.getFileName(this.state);
                float f = ((Float)clientSounds.volume.get()).floatValue();
                ClientUtil.playSound(string, f, false);
            }
        } catch (Exception exception) {
            this.handleException(this.state ? "onEnable" : "onDisable", exception);
        }
    }

    private void handleException(String string, Exception exception) {
        if (Function.mc.player != null) {
            this.print("[" + this.name + "] \u041f\u0440\u043e\u0438\u0437\u043e\u0448\u043b\u0430 \u043e\u0448\u0438\u0431\u043a\u0430 \u0432 \u043c\u0435\u0442\u043e\u0434\u0435 " + TextFormatting.RED + string + TextFormatting.WHITE + "() \u041f\u0440\u0435\u0434\u043e\u0441\u0442\u0430\u0432\u044c\u0442\u0435 \u044d\u0442\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435 \u0440\u0430\u0437\u0440\u0430\u0431\u043e\u0442\u0447\u0438\u043a\u0443: " + TextFormatting.GRAY + exception.getMessage());
            exception.printStackTrace();
        } else {
            System.out.println("[" + this.name + " Error" + string + "() Message: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isState() {
        return this.state;
    }

    public int getBind() {
        return this.bind;
    }

    public List<Setting<?>> getSettings() {
        return this.settings;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public void setBind(int n) {
        this.bind = n;
    }
}

