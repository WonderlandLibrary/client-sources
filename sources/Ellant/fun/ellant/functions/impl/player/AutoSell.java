package fun.ellant.functions.impl.player;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.inventory.container.Container;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import fun.ellant.utils.client.IMinecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.ChestContainer;
import java.util.concurrent.TimeUnit;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.settings.Setting;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.functions.settings.impl.StringSetting;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.api.Function;

@FunctionRegister(name = "AutoSell", type = Category.PLAYER, desc = "Автоматически выставляет на аукцион предметы")
public class AutoSell extends Function
{
    public StringSetting amount;
    StopWatch stopWatch;
    private final ScheduledExecutorService scheduler;

    public AutoSell() {
        this.amount = new StringSetting("Сумма", "5000000", "Введите сюда сумму за которую вы хотите выставлять предметы", true);
        this.stopWatch = new StopWatch();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.addSettings(new Setting[] { (Setting)this.amount });
    }

    @Subscribe
    public void onUpdate(final EventUpdate event) {
        final double f = (500 - AutoSell.mc.player.ticksExisted) / 20.0f;
        if (AutoSell.mc.ingameGUI.getTabList().header != null && AutoSell.mc.player.ticksExisted < 500 && !AutoSell.mc.ingameGUI.getTabList().header.getString().contains("Хаб")) {
            this.print("Подождите " + MathUtil.round(f, 0.1) + " секунд");
            return;
        }
        if (!(AutoSell.mc.currentScreen instanceof ChestScreen) && this.stopWatch.isReached(8500L)) {
            this.scheduler.schedule(() -> AutoSell.mc.player.connection.sendPacket(new CHeldItemChangePacket(0)), 5L, TimeUnit.MILLISECONDS);
            this.scheduler.schedule(() -> AutoSell.mc.player.sendChatMessage("/ah sell " + (String)this.amount.get()), 10L, TimeUnit.MILLISECONDS);
            this.scheduler.schedule(() -> AutoSell.mc.player.connection.sendPacket(new CHeldItemChangePacket(1)), 20L, TimeUnit.MILLISECONDS);
            this.scheduler.schedule(() -> AutoSell.mc.player.sendChatMessage("/ah sell " + (String)this.amount.get()), 30L, TimeUnit.MILLISECONDS);
            this.scheduler.schedule(() -> AutoSell.mc.player.connection.sendPacket(new CHeldItemChangePacket(2)), 40L, TimeUnit.MILLISECONDS);
            this.scheduler.schedule(() -> AutoSell.mc.player.sendChatMessage("/ah sell " + (String)this.amount.get()), 50L, TimeUnit.MILLISECONDS);
            this.scheduler.schedule(() -> AutoSell.mc.player.sendChatMessage("/ah " + AutoSell.mc.session.getUsername()), 3000L, TimeUnit.MILLISECONDS);
            this.stopWatch.reset();
        }
        if (AutoSell.mc.currentScreen != null) {
            final Container openContainer = AutoSell.mc.player.openContainer;
            if (openContainer instanceof final ChestContainer chest) {
                if (AutoSell.mc.ingameGUI.getTabList().header != null && AutoSell.mc.player.ticksExisted < 500 && !AutoSell.mc.ingameGUI.getTabList().header.getString().contains("Хаб")) {
                    return;
                }
                if (AutoSell.mc.currentScreen.getTitle().getString().contains("Аукционы") && this.stopWatch.isReached(5500L)) {
                    this.scheduler.schedule(() -> AutoSell.mc.playerController.windowClick(chest.windowId, 46, 0, ClickType.QUICK_MOVE, AutoSell.mc.player), 4L, TimeUnit.SECONDS);
                    this.stopWatch.reset();
                }
                if (AutoSell.mc.currentScreen.getTitle().getString().contains("Аукционы (" + AutoSell.mc.session.getUsername())) {
                    if (AutoSell.mc.ingameGUI.getTabList().header != null && AutoSell.mc.player.ticksExisted < 500 && !AutoSell.mc.ingameGUI.getTabList().header.getString().contains("Хаб")) {
                        return;
                    }
                    if (IMinecraft.mc.player.openContainer instanceof ChestContainer) {
                        final ContainerScreen container = (ContainerScreen)IMinecraft.mc.currentScreen;
                        for (int index = 0; index < container.getContainer().inventorySlots.size(); ++index) {
                            if (this.stopWatch.isReached(200L)) {
                                IMinecraft.mc.playerController.windowClick(container.getContainer().windowId, index, 0, ClickType.QUICK_MOVE, IMinecraft.mc.player);
                                this.stopWatch.reset();
                            }
                        }
                        this.scheduler.schedule(() -> AutoSell.mc.player.closeScreen(), 4000L, TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
        if (AutoSell.mc.ingameGUI.getTabList().header != null && AutoSell.mc.ingameGUI.getTabList().header.getString().contains("Хаб") && this.stopWatch.isReached(3500L)) {
            this.scheduler.schedule(() -> AutoSell.mc.player.sendChatMessage("/an" + this.getAnarchyServerNumber()), 150L, TimeUnit.MILLISECONDS);
            this.stopWatch.reset();
        }
    }

    private int getAnarchyServerNumber() {
        if (AutoSell.mc.ingameGUI.getTabList().header != null) {
            final String serverHeader = TextFormatting.getTextWithoutFormattingCodes(AutoSell.mc.ingameGUI.getTabList().header.getString());
            if (serverHeader != null && serverHeader.contains("Анархия-")) {
                return Integer.parseInt(serverHeader.split("Анархия-")[1].trim());
            }
        }
        return -1;
    }
}
