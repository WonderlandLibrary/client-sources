package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.apache.commons.lang3.RandomUtils;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.math.time.Stopwatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@ModuleMetaData(
        name = "Chat Spammer",
        description = "Spams the chat with a custom message",
        category = ModuleCategoryEnum.MISC
)
public final class ChatSpammerModule extends AbstractModule {
    private final NumberSetting<Long> delay = new NumberSetting<>("Delay (ms)",500L, 0L, 10000L, 25L);

    private final Stopwatch stopwatch = new Stopwatch();

    public ChatSpammerModule() {
        this.registerSettings(delay);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        this.stopwatch.reset();
    }

    private String message;

    private final String filePath = new File(Diablo.getInstance().getMainDirectory(), "chat.txt").getAbsolutePath();

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        this.createFile();
        this.readFile();

        if (this.stopwatch.hasReached(this.delay.getValue())) {
            this.mc.getNetHandler().addToSendQueueNoEvent(new C01PacketChatMessage(this.message + " " + RandomUtils.nextInt(1000, 100000)));
            this.stopwatch.reset();
        }
    };

    private void readFile() {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.filePath));

            this.message = bufferedReader.readLine();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    private void createFile() {
        final File file = new File(this.filePath);

        if (file.exists())
            return;

        if (file.toString().startsWith(".irc"))
            return;

        try {
            file.createNewFile();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

}