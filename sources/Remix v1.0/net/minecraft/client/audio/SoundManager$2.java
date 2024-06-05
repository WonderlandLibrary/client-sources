package net.minecraft.client.audio;

import paulscode.sound.*;

class SoundManager$2 implements Runnable {
    @Override
    public void run() {
        SoundSystemConfig.setLogger((SoundSystemLogger)new SoundSystemLogger() {
            public void message(final String p_message_1_, final int p_message_2_) {
                if (!p_message_1_.isEmpty()) {
                    SoundManager.access$000().info(p_message_1_);
                }
            }
            
            public void importantMessage(final String p_importantMessage_1_, final int p_importantMessage_2_) {
                if (!p_importantMessage_1_.isEmpty()) {
                    SoundManager.access$000().warn(p_importantMessage_1_);
                }
            }
            
            public void errorMessage(final String p_errorMessage_1_, final String p_errorMessage_2_, final int p_errorMessage_3_) {
                if (!p_errorMessage_2_.isEmpty()) {
                    SoundManager.access$000().error("Error in class '" + p_errorMessage_1_ + "'");
                    SoundManager.access$000().error(p_errorMessage_2_);
                }
            }
        });
        SoundManager.access$102(SoundManager.this, new SoundSystemStarterThread(null));
        SoundManager.access$202(SoundManager.this, true);
        SoundManager.access$100(SoundManager.this).setMasterVolume(SoundManager.access$300(SoundManager.this).getSoundLevel(SoundCategory.MASTER));
        SoundManager.access$000().info(SoundManager.access$400(), "Sound engine started");
    }
}