package white.floor.features;


import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import white.floor.event.EventManager;
import white.floor.helpers.notifications.Notification;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;
import white.floor.helpers.render.AnimationHelper;
import white.floor.helpers.render.Translate;
import white.floor.helpers.world.TimerHelper;

public class Feature {
    protected static Minecraft mc = Minecraft.getMinecraft();
    public static TimerHelper timerHelper = new TimerHelper();
    private final Translate translate = new Translate(0.0F, 0.0F);
    protected String name;
    protected String desc;
    private String moduleName;
    private String suffix;
    private int key;
    private Category category;
    private boolean toggled;
    private final AnimationHelper animation;
    public double slidex = 0;
    public double slidey = 0;

    public Feature(String name, String desc, int key, Category category) {
        this.name = name;
        this.desc = desc;
        this.key = key;
        this.category = category;
        toggled = false;
        animation = new AnimationHelper(150, this.isToggled());
        setup();
    }

    public void onEnable() {
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            EventManager.register(this);
        } else {
            EventManager.unregister(this);
        }
        this.toggled = enabled;
    }

    public void onToggle() {
    }

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if (toggled) {
            NotificationPublisher.queue("Module toggled", getName() + " was " + ChatFormatting.GREEN + "Enabled!", NotificationType.SUCCESS);
            onEnable();
            slidex = 0;
            slidey = 0;
        } else {
            NotificationPublisher.queue("Module toggled", getName() + " was " + ChatFormatting.RED + "Disabled!", NotificationType.ERROR);
            onDisable();
            slidex = 0;
            slidey = 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isToggled() {
        return toggled;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getModuleName() {
        return moduleName == null ? name : moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setup() {}

    public static double deltaTime()
    {
        return Minecraft.getMinecraft().getDebugFPS() > 0  ? (1.0000 / Minecraft.getMinecraft().getDebugFPS()) : 1;
    }

    public AnimationHelper getAnimation() {
        return animation;
    }

    public Translate getTranslate() {
        return translate;
    }
}
