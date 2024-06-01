package best.actinium.module.api.command.impl;

import best.actinium.module.api.command.Command;
import best.actinium.util.io.BackendUtil;
import best.actinium.util.io.SoundUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.util.Formatting;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public final class SusCommand extends Command {
    public SusCommand() {
        super("sus");
    }

    @Override
    public void execute(String[] args) {
        SoundUtil.playSound(new ResourceLocation("actinium/sus.wav"),0.9f);
    }
}