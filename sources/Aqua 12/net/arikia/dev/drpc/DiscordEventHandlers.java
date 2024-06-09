// 
// Decompiled by Procyon v0.5.36
// 

package net.arikia.dev.drpc;

import java.util.Arrays;
import java.util.List;
import net.arikia.dev.drpc.callbacks.JoinRequestCallback;
import net.arikia.dev.drpc.callbacks.SpectateGameCallback;
import net.arikia.dev.drpc.callbacks.JoinGameCallback;
import net.arikia.dev.drpc.callbacks.ErroredCallback;
import net.arikia.dev.drpc.callbacks.DisconnectedCallback;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import com.sun.jna.Structure;

public class DiscordEventHandlers extends Structure
{
    public ReadyCallback ready;
    public DisconnectedCallback disconnected;
    public ErroredCallback errored;
    public JoinGameCallback joinGame;
    public SpectateGameCallback spectateGame;
    public JoinRequestCallback joinRequest;
    
    public List<String> getFieldOrder() {
        return Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest");
    }
    
    public static class Builder
    {
        DiscordEventHandlers h;
        
        public Builder() {
            this.h = new DiscordEventHandlers();
        }
        
        public Builder setReadyEventHandler(final ReadyCallback r) {
            this.h.ready = r;
            return this;
        }
        
        public Builder setDisconnectedEventHandler(final DisconnectedCallback d) {
            this.h.disconnected = d;
            return this;
        }
        
        public Builder setErroredEventHandler(final ErroredCallback e) {
            this.h.errored = e;
            return this;
        }
        
        public Builder setJoinGameEventHandler(final JoinGameCallback j) {
            this.h.joinGame = j;
            return this;
        }
        
        public Builder setSpectateGameEventHandler(final SpectateGameCallback s) {
            this.h.spectateGame = s;
            return this;
        }
        
        public Builder setJoinRequestEventHandler(final JoinRequestCallback j) {
            this.h.joinRequest = j;
            return this;
        }
        
        public DiscordEventHandlers build() {
            return this.h;
        }
    }
}
