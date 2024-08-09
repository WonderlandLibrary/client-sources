//package mathax.client.systems.modules.misc;
//
//import mathax.client.eventbus.EventHandler;
//import mathax.client.events.game.GameLeftEvent;
//import mathax.client.events.game.OpenScreenEvent;
//import mathax.client.events.world.TickEvent;
//import mathax.client.settings.*;
//import mathax.client.systems.modules.Categories;
//import mathax.client.systems.modules.Module;
//import mathax.client.utils.Utils;
//import net.minecraft.client.gui.screen.DisconnectedScreen;
//import net.minecraft.item.Items;
//import net.minecraft.text.Text;
//import org.apache.commons.lang3.RandomStringUtils;
//
//import java.util.List;
//
//public class DmSpam extends Module {
//    private final SettingGroup sgGeneral = settings.getDefaultGroup();
//    //ported from meteorist
//
//    private final Setting<String> command = sgGeneral.add(new StringSetting.Builder()
//        .name("messages")
//        .description("The command.")
//        .defaultValue("/msg ")
//        .build()
//    );
//
//    private final Setting<List<String>> messages = sgGeneral.add(new StringListSetting.Builder()
//        .name("messages")
//        .description("Messages to use for spam.")
//        .defaultValue(List.of("Hi!"))
//        .build()
//    );
//
//    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
//        .name("delay")
//        .description("The delay between specified messages in ticks.")
//        .defaultValue(20)
//        .min(0)
//        .sliderMax(200)
//        .build()
//    );
//
//    private final Setting<Boolean> disableOnLeave = sgGeneral.add(new BoolSetting.Builder()
//        .name("disable-on-leave")
//        .description("Disables spam when you leave a server.")
//        .defaultValue(true)
//        .build()
//    );
//
//
//    private final Setting<Boolean> disableOnDisconnect = sgGeneral.add(new BoolSetting.Builder()
//        .name("disable-on-disconnect")
//        .description("Disables spam when you are disconnected from a server.")
//        .defaultValue(true)
//        .build()
//    );
//
//    private final Setting<Boolean> random = sgGeneral.add(new BoolSetting.Builder()
//        .name("randomise")
//        .description("Selects a random message from your spam message list.")
//        .defaultValue(false)
//        .build()
//    );
//
//    private final Setting<Boolean> bypass = sgGeneral.add(new BoolSetting.Builder()
//        .name("bypass")
//        .description("Add random text at the end of the message to try to bypass anti spams.")
//        .defaultValue(false)
//        .build()
//    );
//
//    private final Setting<Integer> length = sgGeneral.add(new IntSetting.Builder()
//        .name("length")
//        .description("Number of characters used to bypass anti spam.")
//        .visible(bypass::get)
//        .defaultValue(16)
//        .sliderRange(1, 256)
//        .build()
//    );
//
//    private int messageI, timer;
//
//    public DmSpam() {
//        super(Categories.Misc, Items.ACACIA_FENCE, "/MSGspam","Spams messages in players direct messages.");
//    }
//
//    @Override
//    public boolean onActivate() {
//        timer = delay.get();
//        messageI = 0;
//        return false;
//    }
//
//    @EventHandler
//    private void onScreenOpen(OpenScreenEvent event) {
//        if (disableOnDisconnect.get() && event.screen instanceof DisconnectedScreen) {
//            toggle();
//        }
//    }
//
//    @EventHandler
//    private void onGameLeft(GameLeftEvent event) {
//        if (disableOnLeave.get()) toggle();
//    }
//
//    @EventHandler
//    private void onTick(TickEvent.Post event) {
//        mc.player.sendMessage(Text.of(mc.world.getPlayers().toString()));
//        if (messages.get().isEmpty()) return;
//
//        if (timer <= 0) {
//            int i;
//            if (random.get()) {
//                i = Utils.random(0, messages.get().size());
//            }
//            else {
//                if (messageI >= messages.get().size()) messageI = 0;
//                i = messageI++;
//            }
//
//            String text = command.get() + messages.get().get(i);
//            if (bypass.get()) {
//                text += " " + RandomStringUtils.randomAlphabetic(length.get()).toLowerCase();
//            }
//            mc.getNetworkHandler().sendChatCommand(text);
//            timer = delay.get();
//        }
//        else {
//            timer--;
//        }
//    }
//}
