package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.BlockUtils;
import net.minecraft.util.BlockPos;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", new String[]{"autoyool"}, ModuleType.Player);
    }

    public Class type() {
        return EventPacketSend.class;
    }

    @EventHandler
    public void onEvent(EventPreUpdate event) {
        if (!mc.gameSettings.keyBindAttack.isKeyDown()) {
            return;
        }
        if (mc.objectMouseOver == null) {
            return;
        }
        BlockPos pos = mc.objectMouseOver.getBlockPos();
        if (pos == null) {
            return;
        }
        BlockUtils.updateTool(pos);
    }
}