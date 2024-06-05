package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.RandomUtil;
import alphentus.utils.TimeUtil;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Keyboard;

import java.util.Random;

/**
 * @author avox | lmao
 * @since on 15.08.2020.
 */
public class Spammer extends Mod {

    String lastMessage = "";
    TimeUtil timer = new TimeUtil();
    public String[] spammerModes = {"ClientMessage", "TrashTalk"};
    public Setting spammerMode = new Setting("Texting Mode", spammerModes, "ClientMessage", this);
    public Setting delay = new Setting("Spammer Delay", 10, 5000, 2500, true, this);
    public Setting toAll = new Setting("Write Global", false, this);
    RandomUtil randomUtil = new RandomUtil();

    public Spammer() {
        super("Spammer", Keyboard.KEY_NONE, true, ModCategory.PLAYER);

        Init.getInstance().settingManager.addSetting(spammerMode);
        Init.getInstance().settingManager.addSetting(delay);
        Init.getInstance().settingManager.addSetting(toAll);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;
        if (event.getType() != Type.TICKUPDATE)
            return;

        Init init = Init.getInstance();
        String clientMessage = randomUtil.randomInt(1000, 5000) + " - " + init.CLIENT_NAME + " by " + init.CLIENT_AUTHORS[0] + " & " + init.CLIENT_AUTHORS[1] + " - " + randomUtil.randomInt(1000, 5000);
        String[] messages = new String[11];
        messages[0] = "Alphentus > Icarus";
        messages[1] = "Alphentus > AAC4";
        messages[2] = "Alphentus > Intave";
        messages[3] = "Sogar Legit besser als ihr...";
        messages[4] = "Habe mehr Accounts, als ihr Kills habt";
        messages[5] = "IP banns? Was sind IP banns?";
        messages[6] = "Nach 30 Sekunden wieder da c:";
        messages[7] = "Hört doch einfach auf zu Schwitzen und entspannt beim cheaten";
        messages[8] = "Alphentus > Sectio";
        messages[9] = "Ich bin der Meinung, skidden ist Legitim...";
        messages[10] = "Intave ist nicht zu bypassen";

        String prefix = toAll.isState() ? "@a " : "";
        String finalText = prefix + (spammerMode.getSelectedCombo().equals("ClientMessage") ? clientMessage : messages[new Random().nextInt(messages.length)]);

        if (mc.currentScreen == null && mc.thePlayer.isServerWorld() && mc.theWorld != null) {
            if (timer.isDelayComplete(delay.getCurrent()) && !lastMessage.equals(finalText) || lastMessage == null) {
                mc.thePlayer.sendChatMessage(finalText);
                lastMessage = finalText;
                timer.reset();
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
