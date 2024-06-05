package net.minecraft.client.gui;

import net.minecraft.client.audio.*;

class GuiOptions$1 extends GuiButton {
    @Override
    public void playPressSound(final SoundHandler soundHandlerIn) {
        final SoundEventAccessorComposite var2 = soundHandlerIn.getRandomSoundFromCategories(SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER);
        if (var2 != null) {
            soundHandlerIn.playSound(PositionedSoundRecord.createPositionedSoundRecord(var2.getSoundEventLocation(), 0.5f));
        }
    }
}