/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.AbstractCommandBlockScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.network.play.client.CUpdateCommandBlockPacket;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandBlockScreen
extends AbstractCommandBlockScreen {
    private final CommandBlockTileEntity commandBlock;
    private Button modeBtn;
    private Button conditionalBtn;
    private Button autoExecBtn;
    private CommandBlockTileEntity.Mode commandBlockMode = CommandBlockTileEntity.Mode.REDSTONE;
    private boolean conditional;
    private boolean automatic;

    public CommandBlockScreen(CommandBlockTileEntity commandBlockTileEntity) {
        this.commandBlock = commandBlockTileEntity;
    }

    @Override
    CommandBlockLogic getLogic() {
        return this.commandBlock.getCommandBlockLogic();
    }

    @Override
    int func_195236_i() {
        return 0;
    }

    @Override
    protected void init() {
        super.init();
        this.modeBtn = this.addButton(new Button(this.width / 2 - 50 - 100 - 4, 165, 100, 20, new TranslationTextComponent("advMode.mode.sequence"), this::lambda$init$0));
        this.conditionalBtn = this.addButton(new Button(this.width / 2 - 50, 165, 100, 20, new TranslationTextComponent("advMode.mode.unconditional"), this::lambda$init$1));
        this.autoExecBtn = this.addButton(new Button(this.width / 2 + 50 + 4, 165, 100, 20, new TranslationTextComponent("advMode.mode.redstoneTriggered"), this::lambda$init$2));
        this.doneButton.active = false;
        this.trackOutputButton.active = false;
        this.modeBtn.active = false;
        this.conditionalBtn.active = false;
        this.autoExecBtn.active = false;
    }

    public void updateGui() {
        CommandBlockLogic commandBlockLogic = this.commandBlock.getCommandBlockLogic();
        this.commandTextField.setText(commandBlockLogic.getCommand());
        this.trackOutput = commandBlockLogic.shouldTrackOutput();
        this.commandBlockMode = this.commandBlock.getMode();
        this.conditional = this.commandBlock.isConditional();
        this.automatic = this.commandBlock.isAuto();
        this.updateTrackOutput();
        this.updateMode();
        this.updateConditional();
        this.updateAutoExec();
        this.doneButton.active = true;
        this.trackOutputButton.active = true;
        this.modeBtn.active = true;
        this.conditionalBtn.active = true;
        this.autoExecBtn.active = true;
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        super.resize(minecraft, n, n2);
        this.updateTrackOutput();
        this.updateMode();
        this.updateConditional();
        this.updateAutoExec();
        this.doneButton.active = true;
        this.trackOutputButton.active = true;
        this.modeBtn.active = true;
        this.conditionalBtn.active = true;
        this.autoExecBtn.active = true;
    }

    @Override
    protected void func_195235_a(CommandBlockLogic commandBlockLogic) {
        this.minecraft.getConnection().sendPacket(new CUpdateCommandBlockPacket(new BlockPos(commandBlockLogic.getPositionVector()), this.commandTextField.getText(), this.commandBlockMode, commandBlockLogic.shouldTrackOutput(), this.conditional, this.automatic));
    }

    private void updateMode() {
        switch (1.$SwitchMap$net$minecraft$tileentity$CommandBlockTileEntity$Mode[this.commandBlockMode.ordinal()]) {
            case 1: {
                this.modeBtn.setMessage(new TranslationTextComponent("advMode.mode.sequence"));
                break;
            }
            case 2: {
                this.modeBtn.setMessage(new TranslationTextComponent("advMode.mode.auto"));
                break;
            }
            case 3: {
                this.modeBtn.setMessage(new TranslationTextComponent("advMode.mode.redstone"));
            }
        }
    }

    private void nextMode() {
        switch (1.$SwitchMap$net$minecraft$tileentity$CommandBlockTileEntity$Mode[this.commandBlockMode.ordinal()]) {
            case 1: {
                this.commandBlockMode = CommandBlockTileEntity.Mode.AUTO;
                break;
            }
            case 2: {
                this.commandBlockMode = CommandBlockTileEntity.Mode.REDSTONE;
                break;
            }
            case 3: {
                this.commandBlockMode = CommandBlockTileEntity.Mode.SEQUENCE;
            }
        }
    }

    private void updateConditional() {
        if (this.conditional) {
            this.conditionalBtn.setMessage(new TranslationTextComponent("advMode.mode.conditional"));
        } else {
            this.conditionalBtn.setMessage(new TranslationTextComponent("advMode.mode.unconditional"));
        }
    }

    private void updateAutoExec() {
        if (this.automatic) {
            this.autoExecBtn.setMessage(new TranslationTextComponent("advMode.mode.autoexec.bat"));
        } else {
            this.autoExecBtn.setMessage(new TranslationTextComponent("advMode.mode.redstoneTriggered"));
        }
    }

    private void lambda$init$2(Button button) {
        this.automatic = !this.automatic;
        this.updateAutoExec();
    }

    private void lambda$init$1(Button button) {
        this.conditional = !this.conditional;
        this.updateConditional();
    }

    private void lambda$init$0(Button button) {
        this.nextMode();
        this.updateMode();
    }
}

