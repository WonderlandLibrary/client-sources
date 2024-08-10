// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.other;

import cc.slack.start.Slack;
import cc.slack.events.impl.game.TickEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name = "Tweaks",
        category = Category.OTHER
)
public class Tweaks extends Module {
    
    public final BooleanValue nojumpdelay = new BooleanValue("No Jump Delay", false);
    public final NumberValue<Integer> noJumpDelayTicks = new NumberValue<>("Jump Delay Ticks", 0, 0, 10, 1);
    private final BooleanValue fullbright = new BooleanValue("FullBright", true);
    public final BooleanValue noclickdelay = new BooleanValue("No Click Delay", true);
    public final BooleanValue noachievement = new BooleanValue("No Achievement", true);
    public final BooleanValue noblockhitdelay = new BooleanValue("No Block Hit Delay", false);
    private final BooleanValue exitGUIFix = new BooleanValue("Exit Gui Fix", true);
    public final BooleanValue customTitle = new BooleanValue("Custom Title", false);
    public final BooleanValue noPumpkin = new BooleanValue("No Pumpkin", true);
    public final BooleanValue noChatBack = new BooleanValue("No Chat Background", false);
    public final BooleanValue infinitechat = new BooleanValue("Infinite Chat", false);
    public final BooleanValue nobosshealth = new BooleanValue("No Boss Health", false);
    public final BooleanValue noSkinValue = new BooleanValue("No Render Skins", false);
    public final BooleanValue noTickInvisValue = new BooleanValue("Don't Tick invisibles", false);
    public final BooleanValue noExpBar = new BooleanValue("No XP Bar", false);

    float prevGamma = -1F;
    boolean wasGUI = false;

    public String status = "";

    public Tweaks() {
        super();
        addSettings(noachievement, noblockhitdelay, noclickdelay, noSkinValue, noTickInvisValue, noExpBar, noChatBack, infinitechat, fullbright, nobosshealth, nojumpdelay, noJumpDelayTicks, exitGUIFix, noPumpkin, customTitle);
    }

    @Override
    public void onEnable() {prevGamma = mc.gameSettings.gammaSetting;}

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {

        if (fullbright.getValue()) {
            if (mc.gameSettings.gammaSetting <= 1000f) mc.gameSettings.gammaSetting++;
        } else if (prevGamma != -1f) {
            mc.gameSettings.gammaSetting = prevGamma;
            prevGamma = -1f;
        }

        if (exitGUIFix.getValue()) {
            if (mc.getCurrentScreen() == null) {
                if (wasGUI) {
                    MovementUtil.updateBinds();
                }
                wasGUI = false;
            } else {
                wasGUI = true;
            }
        }

        if (noclickdelay.getValue()) mc.leftClickCounter = 0;
    }

    @SuppressWarnings("unused")
    @Listen
    public void onTick (TickEvent event) {
        if (Slack.getInstance().getModuleManager().getInstance(RichPresence.class).started.get()) {
            status = "ON";
        } else {
            status = "OFF";
        }
    }

    @SuppressWarnings("unused")
    @Listen
    public void onMotion (MotionEvent event) { if (noblockhitdelay.getValue()) { mc.playerController.blockHitDelay = 0; } }

    @Override
    public void onDisable() {
        if (prevGamma == -1f) return;
        mc.gameSettings.gammaSetting = prevGamma;
        prevGamma = -1f;
    }
}
