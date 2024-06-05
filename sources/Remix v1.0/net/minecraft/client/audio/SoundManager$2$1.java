package net.minecraft.client.audio;

import paulscode.sound.*;

class SoundManager$2$1 extends SoundSystemLogger {
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
}