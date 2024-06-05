package net.minecraft.client.stream;

import com.google.common.collect.*;
import tv.twitch.*;
import java.util.*;
import tv.twitch.chat.*;

public class ChatChannelListener implements IChatChannelListener
{
    protected String field_176048_a;
    protected boolean field_176046_b;
    protected EnumChannelState field_176047_c;
    protected List field_176044_d;
    protected LinkedList field_176045_e;
    protected LinkedList field_176042_f;
    protected ChatBadgeData field_176043_g;
    
    public ChatChannelListener(final String p_i46061_2_) {
        this.field_176048_a = null;
        this.field_176046_b = false;
        this.field_176047_c = EnumChannelState.Created;
        this.field_176044_d = Lists.newArrayList();
        this.field_176045_e = new LinkedList();
        this.field_176042_f = new LinkedList();
        this.field_176043_g = null;
        this.field_176048_a = p_i46061_2_;
    }
    
    public EnumChannelState func_176040_a() {
        return this.field_176047_c;
    }
    
    public boolean func_176038_a(final boolean p_176038_1_) {
        this.field_176046_b = p_176038_1_;
        ErrorCode var2 = ErrorCode.TTV_EC_SUCCESS;
        if (p_176038_1_) {
            var2 = ChatController.this.field_153008_f.connectAnonymous(this.field_176048_a, (IChatChannelListener)this);
        }
        else {
            var2 = ChatController.this.field_153008_f.connect(this.field_176048_a, ChatController.this.field_153004_b, ChatController.this.field_153012_j.data, (IChatChannelListener)this);
        }
        if (ErrorCode.failed(var2)) {
            final String var3 = ErrorCode.getString(var2);
            ChatController.this.func_152995_h(String.format("Error connecting: %s", var3));
            this.func_176036_d(this.field_176048_a);
            return false;
        }
        this.func_176035_a(EnumChannelState.Connecting);
        this.func_176041_h();
        return true;
    }
    
    public boolean func_176034_g() {
        switch (SwitchEnumEmoticonMode.field_175976_a[this.field_176047_c.ordinal()]) {
            case 1:
            case 2: {
                final ErrorCode var1 = ChatController.this.field_153008_f.disconnect(this.field_176048_a);
                if (ErrorCode.failed(var1)) {
                    final String var2 = ErrorCode.getString(var1);
                    ChatController.this.func_152995_h(String.format("Error disconnecting: %s", var2));
                    return false;
                }
                this.func_176035_a(EnumChannelState.Disconnecting);
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    protected void func_176035_a(final EnumChannelState p_176035_1_) {
        if (p_176035_1_ != this.field_176047_c) {
            this.field_176047_c = p_176035_1_;
        }
    }
    
    public void func_176032_a(final String p_176032_1_) {
        if (ChatController.this.field_175995_l == EnumEmoticonMode.None) {
            this.field_176045_e.clear();
            this.field_176042_f.clear();
        }
        else {
            if (this.field_176045_e.size() > 0) {
                final ListIterator var2 = this.field_176045_e.listIterator();
                while (var2.hasNext()) {
                    final ChatRawMessage var3 = var2.next();
                    if (var3.userName.equals(p_176032_1_)) {
                        var2.remove();
                    }
                }
            }
            if (this.field_176042_f.size() > 0) {
                final ListIterator var2 = this.field_176042_f.listIterator();
                while (var2.hasNext()) {
                    final ChatTokenizedMessage var4 = var2.next();
                    if (var4.displayName.equals(p_176032_1_)) {
                        var2.remove();
                    }
                }
            }
        }
        try {
            if (ChatController.this.field_153003_a != null) {
                ChatController.this.field_153003_a.func_176019_a(this.field_176048_a, p_176032_1_);
            }
        }
        catch (Exception var5) {
            ChatController.this.func_152995_h(var5.toString());
        }
    }
    
    public boolean func_176037_b(final String p_176037_1_) {
        if (this.field_176047_c != EnumChannelState.Connected) {
            return false;
        }
        final ErrorCode var2 = ChatController.this.field_153008_f.sendMessage(this.field_176048_a, p_176037_1_);
        if (ErrorCode.failed(var2)) {
            final String var3 = ErrorCode.getString(var2);
            ChatController.this.func_152995_h(String.format("Error sending chat message: %s", var3));
            return false;
        }
        return true;
    }
    
    protected void func_176041_h() {
        if (ChatController.this.field_175995_l != EnumEmoticonMode.None && this.field_176043_g == null) {
            final ErrorCode var1 = ChatController.this.field_153008_f.downloadBadgeData(this.field_176048_a);
            if (ErrorCode.failed(var1)) {
                final String var2 = ErrorCode.getString(var1);
                ChatController.this.func_152995_h(String.format("Error trying to download badge data: %s", var2));
            }
        }
    }
    
    protected void func_176039_i() {
        if (this.field_176043_g == null) {
            this.field_176043_g = new ChatBadgeData();
            final ErrorCode var1 = ChatController.this.field_153008_f.getBadgeData(this.field_176048_a, this.field_176043_g);
            if (ErrorCode.succeeded(var1)) {
                try {
                    if (ChatController.this.field_153003_a != null) {
                        ChatController.this.field_153003_a.func_176016_c(this.field_176048_a);
                    }
                }
                catch (Exception var2) {
                    ChatController.this.func_152995_h(var2.toString());
                }
            }
            else {
                ChatController.this.func_152995_h("Error preparing badge data: " + ErrorCode.getString(var1));
            }
        }
    }
    
    protected void func_176033_j() {
        if (this.field_176043_g != null) {
            final ErrorCode var1 = ChatController.this.field_153008_f.clearBadgeData(this.field_176048_a);
            if (ErrorCode.succeeded(var1)) {
                this.field_176043_g = null;
                try {
                    if (ChatController.this.field_153003_a != null) {
                        ChatController.this.field_153003_a.func_176020_d(this.field_176048_a);
                    }
                }
                catch (Exception var2) {
                    ChatController.this.func_152995_h(var2.toString());
                }
            }
            else {
                ChatController.this.func_152995_h("Error releasing badge data: " + ErrorCode.getString(var1));
            }
        }
    }
    
    protected void func_176031_c(final String p_176031_1_) {
        try {
            if (ChatController.this.field_153003_a != null) {
                ChatController.this.field_153003_a.func_180606_a(p_176031_1_);
            }
        }
        catch (Exception var3) {
            ChatController.this.func_152995_h(var3.toString());
        }
    }
    
    protected void func_176036_d(final String p_176036_1_) {
        try {
            if (ChatController.this.field_153003_a != null) {
                ChatController.this.field_153003_a.func_180607_b(p_176036_1_);
            }
        }
        catch (Exception var3) {
            ChatController.this.func_152995_h(var3.toString());
        }
    }
    
    private void func_176030_k() {
        if (this.field_176047_c != EnumChannelState.Disconnected) {
            this.func_176035_a(EnumChannelState.Disconnected);
            this.func_176036_d(this.field_176048_a);
            this.func_176033_j();
        }
    }
    
    public void chatStatusCallback(final String p_chatStatusCallback_1_, final ErrorCode p_chatStatusCallback_2_) {
        if (!ErrorCode.succeeded(p_chatStatusCallback_2_)) {
            ChatController.this.field_175998_i.remove(p_chatStatusCallback_1_);
            this.func_176030_k();
        }
    }
    
    public void chatChannelMembershipCallback(final String p_chatChannelMembershipCallback_1_, final ChatEvent p_chatChannelMembershipCallback_2_, final ChatChannelInfo p_chatChannelMembershipCallback_3_) {
        switch (SwitchEnumEmoticonMode.field_175974_b[p_chatChannelMembershipCallback_2_.ordinal()]) {
            case 1: {
                this.func_176035_a(EnumChannelState.Connected);
                this.func_176031_c(p_chatChannelMembershipCallback_1_);
                break;
            }
            case 2: {
                this.func_176030_k();
                break;
            }
        }
    }
    
    public void chatChannelUserChangeCallback(final String p_chatChannelUserChangeCallback_1_, final ChatUserInfo[] p_chatChannelUserChangeCallback_2_, final ChatUserInfo[] p_chatChannelUserChangeCallback_3_, final ChatUserInfo[] p_chatChannelUserChangeCallback_4_) {
        for (int var5 = 0; var5 < p_chatChannelUserChangeCallback_3_.length; ++var5) {
            final int var6 = this.field_176044_d.indexOf(p_chatChannelUserChangeCallback_3_[var5]);
            if (var6 >= 0) {
                this.field_176044_d.remove(var6);
            }
        }
        for (int var5 = 0; var5 < p_chatChannelUserChangeCallback_4_.length; ++var5) {
            final int var6 = this.field_176044_d.indexOf(p_chatChannelUserChangeCallback_4_[var5]);
            if (var6 >= 0) {
                this.field_176044_d.remove(var6);
            }
            this.field_176044_d.add(p_chatChannelUserChangeCallback_4_[var5]);
        }
        for (int var5 = 0; var5 < p_chatChannelUserChangeCallback_2_.length; ++var5) {
            this.field_176044_d.add(p_chatChannelUserChangeCallback_2_[var5]);
        }
        try {
            if (ChatController.this.field_153003_a != null) {
                ChatController.this.field_153003_a.func_176018_a(this.field_176048_a, p_chatChannelUserChangeCallback_2_, p_chatChannelUserChangeCallback_3_, p_chatChannelUserChangeCallback_4_);
            }
        }
        catch (Exception var7) {
            ChatController.this.func_152995_h(var7.toString());
        }
    }
    
    public void chatChannelRawMessageCallback(final String p_chatChannelRawMessageCallback_1_, final ChatRawMessage[] p_chatChannelRawMessageCallback_2_) {
        for (int var3 = 0; var3 < p_chatChannelRawMessageCallback_2_.length; ++var3) {
            this.field_176045_e.addLast(p_chatChannelRawMessageCallback_2_[var3]);
        }
        try {
            if (ChatController.this.field_153003_a != null) {
                ChatController.this.field_153003_a.func_180605_a(this.field_176048_a, p_chatChannelRawMessageCallback_2_);
            }
        }
        catch (Exception var4) {
            ChatController.this.func_152995_h(var4.toString());
        }
        while (this.field_176045_e.size() > ChatController.this.field_153015_m) {
            this.field_176045_e.removeFirst();
        }
    }
    
    public void chatChannelTokenizedMessageCallback(final String p_chatChannelTokenizedMessageCallback_1_, final ChatTokenizedMessage[] p_chatChannelTokenizedMessageCallback_2_) {
        for (int var3 = 0; var3 < p_chatChannelTokenizedMessageCallback_2_.length; ++var3) {
            this.field_176042_f.addLast(p_chatChannelTokenizedMessageCallback_2_[var3]);
        }
        try {
            if (ChatController.this.field_153003_a != null) {
                ChatController.this.field_153003_a.func_176025_a(this.field_176048_a, p_chatChannelTokenizedMessageCallback_2_);
            }
        }
        catch (Exception var4) {
            ChatController.this.func_152995_h(var4.toString());
        }
        while (this.field_176042_f.size() > ChatController.this.field_153015_m) {
            this.field_176042_f.removeFirst();
        }
    }
    
    public void chatClearCallback(final String p_chatClearCallback_1_, final String p_chatClearCallback_2_) {
        this.func_176032_a(p_chatClearCallback_2_);
    }
    
    public void chatBadgeDataDownloadCallback(final String p_chatBadgeDataDownloadCallback_1_, final ErrorCode p_chatBadgeDataDownloadCallback_2_) {
        if (ErrorCode.succeeded(p_chatBadgeDataDownloadCallback_2_)) {
            this.func_176039_i();
        }
    }
}
