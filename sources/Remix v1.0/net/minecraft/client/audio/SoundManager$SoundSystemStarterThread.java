package net.minecraft.client.audio;

import paulscode.sound.*;

class SoundSystemStarterThread extends SoundSystem
{
    private SoundSystemStarterThread() {
    }
    
    SoundSystemStarterThread(final SoundManager this$0, final Object p_i45118_2_) {
        this(this$0);
    }
    
    public boolean playing(final String p_playing_1_) {
        final Object var2 = SoundSystemConfig.THREAD_SYNC;
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary == null) {
                return false;
            }
            final Source var3 = this.soundLibrary.getSources().get(p_playing_1_);
            return var3 != null && (var3.playing() || var3.paused() || var3.preLoad);
        }
    }
}
