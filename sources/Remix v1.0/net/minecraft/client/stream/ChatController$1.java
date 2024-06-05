package net.minecraft.client.stream;

import tv.twitch.chat.*;
import tv.twitch.*;

class ChatController$1 implements IChatAPIListener {
    public void chatInitializationCallback(final ErrorCode p_chatInitializationCallback_1_) {
        if (ErrorCode.succeeded(p_chatInitializationCallback_1_)) {
            ChatController.this.field_153008_f.setMessageFlushInterval(ChatController.this.field_175993_n);
            ChatController.this.field_153008_f.setUserChangeEventInterval(ChatController.this.field_175994_o);
            ChatController.this.func_153001_r();
            ChatController.this.func_175985_a(ChatState.Initialized);
        }
        else {
            ChatController.this.func_175985_a(ChatState.Uninitialized);
        }
        try {
            if (ChatController.this.field_153003_a != null) {
                ChatController.this.field_153003_a.func_176023_d(p_chatInitializationCallback_1_);
            }
        }
        catch (Exception var3) {
            ChatController.this.func_152995_h(var3.toString());
        }
    }
    
    public void chatShutdownCallback(final ErrorCode p_chatShutdownCallback_1_) {
        if (ErrorCode.succeeded(p_chatShutdownCallback_1_)) {
            final ErrorCode var2 = ChatController.this.field_175992_e.shutdown();
            if (ErrorCode.failed(var2)) {
                final String var3 = ErrorCode.getString(var2);
                ChatController.this.func_152995_h(String.format("Error shutting down the Twitch sdk: %s", var3));
            }
            ChatController.this.func_175985_a(ChatState.Uninitialized);
        }
        else {
            ChatController.this.func_175985_a(ChatState.Initialized);
            ChatController.this.func_152995_h(String.format("Error shutting down Twith chat: %s", p_chatShutdownCallback_1_));
        }
        try {
            if (ChatController.this.field_153003_a != null) {
                ChatController.this.field_153003_a.func_176022_e(p_chatShutdownCallback_1_);
            }
        }
        catch (Exception var4) {
            ChatController.this.func_152995_h(var4.toString());
        }
    }
    
    public void chatEmoticonDataDownloadCallback(final ErrorCode p_chatEmoticonDataDownloadCallback_1_) {
        if (ErrorCode.succeeded(p_chatEmoticonDataDownloadCallback_1_)) {
            ChatController.this.func_152988_s();
        }
    }
}