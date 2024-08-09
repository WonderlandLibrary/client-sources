/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import net.minecraft.client.gui.screen.AbstractCommandBlockScreen;
import net.minecraft.entity.item.minecart.CommandBlockMinecartEntity;
import net.minecraft.network.play.client.CUpdateMinecartCommandBlockPacket;
import net.minecraft.tileentity.CommandBlockLogic;

public class EditMinecartCommandBlockScreen
extends AbstractCommandBlockScreen {
    private final CommandBlockLogic commandBlockLogic;

    public EditMinecartCommandBlockScreen(CommandBlockLogic commandBlockLogic) {
        this.commandBlockLogic = commandBlockLogic;
    }

    @Override
    public CommandBlockLogic getLogic() {
        return this.commandBlockLogic;
    }

    @Override
    int func_195236_i() {
        return 1;
    }

    @Override
    protected void init() {
        super.init();
        this.trackOutput = this.getLogic().shouldTrackOutput();
        this.updateTrackOutput();
        this.commandTextField.setText(this.getLogic().getCommand());
    }

    @Override
    protected void func_195235_a(CommandBlockLogic commandBlockLogic) {
        if (commandBlockLogic instanceof CommandBlockMinecartEntity.MinecartCommandLogic) {
            CommandBlockMinecartEntity.MinecartCommandLogic minecartCommandLogic = (CommandBlockMinecartEntity.MinecartCommandLogic)commandBlockLogic;
            this.minecraft.getConnection().sendPacket(new CUpdateMinecartCommandBlockPacket(minecartCommandLogic.getMinecart().getEntityId(), this.commandTextField.getText(), commandBlockLogic.shouldTrackOutput()));
        }
    }
}

