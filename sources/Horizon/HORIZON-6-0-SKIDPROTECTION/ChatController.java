package HORIZON-6-0-SKIDPROTECTION;

import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatChannelInfo;
import tv.twitch.chat.ChatEvent;
import java.util.ListIterator;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatRawMessage;
import com.google.common.collect.Lists;
import tv.twitch.chat.ChatBadgeData;
import java.util.LinkedList;
import java.util.List;
import tv.twitch.chat.IChatChannelListener;
import tv.twitch.chat.ChatTokenizationOption;
import java.util.HashSet;
import tv.twitch.chat.ChatAPI;
import tv.twitch.chat.StandardChatAPI;
import tv.twitch.CoreAPI;
import tv.twitch.StandardCoreAPI;
import tv.twitch.ErrorCode;
import org.apache.logging.log4j.LogManager;
import tv.twitch.chat.IChatAPIListener;
import tv.twitch.chat.ChatEmoticonData;
import java.util.HashMap;
import tv.twitch.AuthToken;
import tv.twitch.chat.Chat;
import tv.twitch.Core;
import org.apache.logging.log4j.Logger;

public class ChatController
{
    private static final Logger µà;
    protected Â HorizonCode_Horizon_È;
    protected String Â;
    protected String Ý;
    protected String Ø­áŒŠá;
    protected Core Âµá€;
    protected Chat Ó;
    protected Ý à;
    protected AuthToken Ø;
    protected HashMap áŒŠÆ;
    protected int áˆºÑ¢Õ;
    protected Âµá€ ÂµÈ;
    protected Âµá€ á;
    protected ChatEmoticonData ˆÏ­;
    protected int £á;
    protected int Å;
    protected IChatAPIListener £à;
    private static final String ˆà = "CL_00001819";
    
    static {
        µà = LogManager.getLogger();
    }
    
    public void HorizonCode_Horizon_È(final Â p_152990_1_) {
        this.HorizonCode_Horizon_È = p_152990_1_;
    }
    
    public void HorizonCode_Horizon_È(final AuthToken p_152994_1_) {
        this.Ø = p_152994_1_;
    }
    
    public void HorizonCode_Horizon_È(final String p_152984_1_) {
        this.Ý = p_152984_1_;
    }
    
    public void Â(final String p_152998_1_) {
        this.Â = p_152998_1_;
    }
    
    public Ý HorizonCode_Horizon_È() {
        return this.à;
    }
    
    public boolean Ý(final String p_175990_1_) {
        if (!this.áŒŠÆ.containsKey(p_175990_1_)) {
            return false;
        }
        final HorizonCode_Horizon_È var2 = this.áŒŠÆ.get(p_175990_1_);
        return var2.HorizonCode_Horizon_È() == ChatController.Ø­áŒŠá.Ý;
    }
    
    public Ø­áŒŠá Ø­áŒŠá(final String p_175989_1_) {
        if (!this.áŒŠÆ.containsKey(p_175989_1_)) {
            return ChatController.Ø­áŒŠá.Âµá€;
        }
        final HorizonCode_Horizon_È var2 = this.áŒŠÆ.get(p_175989_1_);
        return var2.HorizonCode_Horizon_È();
    }
    
    public ChatController() {
        this.HorizonCode_Horizon_È = null;
        this.Â = "";
        this.Ý = "";
        this.Ø­áŒŠá = "";
        this.Âµá€ = null;
        this.Ó = null;
        this.à = ChatController.Ý.HorizonCode_Horizon_È;
        this.Ø = new AuthToken();
        this.áŒŠÆ = new HashMap();
        this.áˆºÑ¢Õ = 128;
        this.ÂµÈ = ChatController.Âµá€.HorizonCode_Horizon_È;
        this.á = ChatController.Âµá€.HorizonCode_Horizon_È;
        this.ˆÏ­ = null;
        this.£á = 500;
        this.Å = 2000;
        this.£à = (IChatAPIListener)new IChatAPIListener() {
            private static final String Â = "CL_00002373";
            
            public void chatInitializationCallback(final ErrorCode p_chatInitializationCallback_1_) {
                if (ErrorCode.succeeded(p_chatInitializationCallback_1_)) {
                    ChatController.this.Ó.setMessageFlushInterval(ChatController.this.£á);
                    ChatController.this.Ó.setUserChangeEventInterval(ChatController.this.Å);
                    ChatController.this.Ó();
                    ChatController.this.HorizonCode_Horizon_È(ChatController.Ý.Ý);
                }
                else {
                    ChatController.this.HorizonCode_Horizon_È(ChatController.Ý.HorizonCode_Horizon_È);
                }
                try {
                    if (ChatController.this.HorizonCode_Horizon_È != null) {
                        ChatController.this.HorizonCode_Horizon_È.Ø­áŒŠá(p_chatInitializationCallback_1_);
                    }
                }
                catch (Exception var3) {
                    ChatController.this.à(var3.toString());
                }
            }
            
            public void chatShutdownCallback(final ErrorCode p_chatShutdownCallback_1_) {
                if (ErrorCode.succeeded(p_chatShutdownCallback_1_)) {
                    final ErrorCode var2 = ChatController.this.Âµá€.shutdown();
                    if (ErrorCode.failed(var2)) {
                        final String var3 = ErrorCode.getString(var2);
                        ChatController.this.à(String.format("Error shutting down the Twitch sdk: %s", var3));
                    }
                    ChatController.this.HorizonCode_Horizon_È(ChatController.Ý.HorizonCode_Horizon_È);
                }
                else {
                    ChatController.this.HorizonCode_Horizon_È(ChatController.Ý.Ý);
                    ChatController.this.à(String.format("Error shutting down Twith chat: %s", p_chatShutdownCallback_1_));
                }
                try {
                    if (ChatController.this.HorizonCode_Horizon_È != null) {
                        ChatController.this.HorizonCode_Horizon_È.Âµá€(p_chatShutdownCallback_1_);
                    }
                }
                catch (Exception var4) {
                    ChatController.this.à(var4.toString());
                }
            }
            
            public void chatEmoticonDataDownloadCallback(final ErrorCode p_chatEmoticonDataDownloadCallback_1_) {
                if (ErrorCode.succeeded(p_chatEmoticonDataDownloadCallback_1_)) {
                    ChatController.this.à();
                }
            }
        };
        this.Âµá€ = Core.getInstance();
        if (this.Âµá€ == null) {
            this.Âµá€ = new Core((CoreAPI)new StandardCoreAPI());
        }
        this.Ó = new Chat((ChatAPI)new StandardChatAPI());
    }
    
    public boolean Â() {
        if (this.à != ChatController.Ý.HorizonCode_Horizon_È) {
            return false;
        }
        this.HorizonCode_Horizon_È(ChatController.Ý.Â);
        ErrorCode var1 = this.Âµá€.initialize(this.Ý, (String)null);
        if (ErrorCode.failed(var1)) {
            this.HorizonCode_Horizon_È(ChatController.Ý.HorizonCode_Horizon_È);
            final String var2 = ErrorCode.getString(var1);
            this.à(String.format("Error initializing Twitch sdk: %s", var2));
            return false;
        }
        this.á = this.ÂµÈ;
        final HashSet var3 = new HashSet();
        switch (ChatController.Ó.Ý[this.ÂµÈ.ordinal()]) {
            case 1: {
                var3.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
                break;
            }
            case 2: {
                var3.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
                break;
            }
            case 3: {
                var3.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
                break;
            }
        }
        var1 = this.Ó.initialize(var3, this.£à);
        if (ErrorCode.failed(var1)) {
            this.Âµá€.shutdown();
            this.HorizonCode_Horizon_È(ChatController.Ý.HorizonCode_Horizon_È);
            final String var4 = ErrorCode.getString(var1);
            this.à(String.format("Error initializing Twitch chat: %s", var4));
            return false;
        }
        this.HorizonCode_Horizon_È(ChatController.Ý.Ý);
        return true;
    }
    
    public boolean Âµá€(final String p_152986_1_) {
        return this.HorizonCode_Horizon_È(p_152986_1_, false);
    }
    
    protected boolean HorizonCode_Horizon_È(final String p_175987_1_, final boolean p_175987_2_) {
        if (this.à != ChatController.Ý.Ý) {
            return false;
        }
        if (this.áŒŠÆ.containsKey(p_175987_1_)) {
            this.à("Already in channel: " + p_175987_1_);
            return false;
        }
        if (p_175987_1_ != null && !p_175987_1_.equals("")) {
            final HorizonCode_Horizon_È var3 = new HorizonCode_Horizon_È(p_175987_1_);
            this.áŒŠÆ.put(p_175987_1_, var3);
            final boolean var4 = var3.HorizonCode_Horizon_È(p_175987_2_);
            if (!var4) {
                this.áŒŠÆ.remove(p_175987_1_);
            }
            return var4;
        }
        return false;
    }
    
    public boolean Ó(final String p_175991_1_) {
        if (this.à != ChatController.Ý.Ý) {
            return false;
        }
        if (!this.áŒŠÆ.containsKey(p_175991_1_)) {
            this.à("Not in channel: " + p_175991_1_);
            return false;
        }
        final HorizonCode_Horizon_È var2 = this.áŒŠÆ.get(p_175991_1_);
        return var2.Â();
    }
    
    public boolean Ý() {
        if (this.à != ChatController.Ý.Ý) {
            return false;
        }
        final ErrorCode var1 = this.Ó.shutdown();
        if (ErrorCode.failed(var1)) {
            final String var2 = ErrorCode.getString(var1);
            this.à(String.format("Error shutting down chat: %s", var2));
            return false;
        }
        this.Ø();
        this.HorizonCode_Horizon_È(ChatController.Ý.Ø­áŒŠá);
        return true;
    }
    
    public void Ø­áŒŠá() {
        if (this.HorizonCode_Horizon_È() != ChatController.Ý.HorizonCode_Horizon_È) {
            this.Ý();
            if (this.HorizonCode_Horizon_È() == ChatController.Ý.Ø­áŒŠá) {
                while (this.HorizonCode_Horizon_È() != ChatController.Ý.HorizonCode_Horizon_È) {
                    try {
                        Thread.sleep(200L);
                        this.Âµá€();
                    }
                    catch (InterruptedException ex) {}
                }
            }
        }
    }
    
    public void Âµá€() {
        if (this.à != ChatController.Ý.HorizonCode_Horizon_È) {
            final ErrorCode var1 = this.Ó.flushEvents();
            if (ErrorCode.failed(var1)) {
                final String var2 = ErrorCode.getString(var1);
                this.à(String.format("Error flushing chat events: %s", var2));
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final String p_175986_1_, final String p_175986_2_) {
        if (this.à != ChatController.Ý.Ý) {
            return false;
        }
        if (!this.áŒŠÆ.containsKey(p_175986_1_)) {
            this.à("Not in channel: " + p_175986_1_);
            return false;
        }
        final HorizonCode_Horizon_È var3 = this.áŒŠÆ.get(p_175986_1_);
        return var3.Â(p_175986_2_);
    }
    
    protected void HorizonCode_Horizon_È(final Ý p_175985_1_) {
        if (p_175985_1_ != this.à) {
            this.à = p_175985_1_;
            try {
                if (this.HorizonCode_Horizon_È != null) {
                    this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_175985_1_);
                }
            }
            catch (Exception var3) {
                this.à(var3.toString());
            }
        }
    }
    
    protected void Ó() {
        if (this.á != ChatController.Âµá€.HorizonCode_Horizon_È && this.ˆÏ­ == null) {
            final ErrorCode var1 = this.Ó.downloadEmoticonData();
            if (ErrorCode.failed(var1)) {
                final String var2 = ErrorCode.getString(var1);
                this.à(String.format("Error trying to download emoticon data: %s", var2));
            }
        }
    }
    
    protected void à() {
        if (this.ˆÏ­ == null) {
            this.ˆÏ­ = new ChatEmoticonData();
            final ErrorCode var1 = this.Ó.getEmoticonData(this.ˆÏ­);
            if (ErrorCode.succeeded(var1)) {
                try {
                    if (this.HorizonCode_Horizon_È != null) {
                        this.HorizonCode_Horizon_È.Ø­áŒŠá();
                    }
                }
                catch (Exception var2) {
                    this.à(var2.toString());
                }
            }
            else {
                this.à("Error preparing emoticon data: " + ErrorCode.getString(var1));
            }
        }
    }
    
    protected void Ø() {
        if (this.ˆÏ­ != null) {
            final ErrorCode var1 = this.Ó.clearEmoticonData();
            if (ErrorCode.succeeded(var1)) {
                this.ˆÏ­ = null;
                try {
                    if (this.HorizonCode_Horizon_È != null) {
                        this.HorizonCode_Horizon_È.Âµá€();
                    }
                }
                catch (Exception var2) {
                    this.à(var2.toString());
                }
            }
            else {
                this.à("Error clearing emoticon data: " + ErrorCode.getString(var1));
            }
        }
    }
    
    protected void à(final String p_152995_1_) {
        ChatController.µà.error(TwitchStream.HorizonCode_Horizon_È, "[Chat controller] {}", new Object[] { p_152995_1_ });
    }
    
    public enum Ý
    {
        HorizonCode_Horizon_È("Uninitialized", 0, "Uninitialized", 0), 
        Â("Initializing", 1, "Initializing", 1), 
        Ý("Initialized", 2, "Initialized", 2), 
        Ø­áŒŠá("ShuttingDown", 3, "ShuttingDown", 3);
        
        private static final Ý[] Âµá€;
        private static final String Ó = "CL_00001817";
        
        static {
            à = new Ý[] { Ý.HorizonCode_Horizon_È, Ý.Â, Ý.Ý, Ý.Ø­áŒŠá };
            Âµá€ = new Ý[] { Ý.HorizonCode_Horizon_È, Ý.Â, Ý.Ý, Ý.Ø­áŒŠá };
        }
        
        private Ý(final String s, final int n, final String stateName, final int id) {
        }
    }
    
    public enum Ø­áŒŠá
    {
        HorizonCode_Horizon_È("Created", 0, "Created", 0), 
        Â("Connecting", 1, "Connecting", 1), 
        Ý("Connected", 2, "Connected", 2), 
        Ø­áŒŠá("Disconnecting", 3, "Disconnecting", 3), 
        Âµá€("Disconnected", 4, "Disconnected", 4);
        
        private static final Ø­áŒŠá[] Ó;
        private static final String à = "CL_00002371";
        
        static {
            Ø = new Ø­áŒŠá[] { Ø­áŒŠá.HorizonCode_Horizon_È, Ø­áŒŠá.Â, Ø­áŒŠá.Ý, Ø­áŒŠá.Ø­áŒŠá, Ø­áŒŠá.Âµá€ };
            Ó = new Ø­áŒŠá[] { Ø­áŒŠá.HorizonCode_Horizon_È, Ø­áŒŠá.Â, Ø­áŒŠá.Ý, Ø­áŒŠá.Ø­áŒŠá, Ø­áŒŠá.Âµá€ };
        }
        
        private Ø­áŒŠá(final String s, final int n, final String p_i46062_1_, final int p_i46062_2_) {
        }
    }
    
    public enum Âµá€
    {
        HorizonCode_Horizon_È("None", 0, "None", 0), 
        Â("Url", 1, "Url", 1), 
        Ý("TextureAtlas", 2, "TextureAtlas", 2);
        
        private static final Âµá€[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002369";
        
        static {
            Ó = new Âµá€[] { ChatController.Âµá€.HorizonCode_Horizon_È, ChatController.Âµá€.Â, ChatController.Âµá€.Ý };
            Ø­áŒŠá = new Âµá€[] { ChatController.Âµá€.HorizonCode_Horizon_È, ChatController.Âµá€.Â, ChatController.Âµá€.Ý };
        }
        
        private Âµá€(final String s, final int n, final String p_i46060_1_, final int p_i46060_2_) {
        }
    }
    
    public class HorizonCode_Horizon_È implements IChatChannelListener
    {
        protected String HorizonCode_Horizon_È;
        protected boolean Â;
        protected Ø­áŒŠá Ý;
        protected List Ø­áŒŠá;
        protected LinkedList Âµá€;
        protected LinkedList Ó;
        protected ChatBadgeData à;
        private static final String áŒŠÆ = "CL_00002370";
        
        public HorizonCode_Horizon_È(final String p_i46061_2_) {
            this.HorizonCode_Horizon_È = null;
            this.Â = false;
            this.Ý = ChatController.Ø­áŒŠá.HorizonCode_Horizon_È;
            this.Ø­áŒŠá = Lists.newArrayList();
            this.Âµá€ = new LinkedList();
            this.Ó = new LinkedList();
            this.à = null;
            this.HorizonCode_Horizon_È = p_i46061_2_;
        }
        
        public Ø­áŒŠá HorizonCode_Horizon_È() {
            return this.Ý;
        }
        
        public boolean HorizonCode_Horizon_È(final boolean p_176038_1_) {
            this.Â = p_176038_1_;
            ErrorCode var2 = ErrorCode.TTV_EC_SUCCESS;
            if (p_176038_1_) {
                var2 = ChatController.this.Ó.connectAnonymous(this.HorizonCode_Horizon_È, (IChatChannelListener)this);
            }
            else {
                var2 = ChatController.this.Ó.connect(this.HorizonCode_Horizon_È, ChatController.this.Â, ChatController.this.Ø.data, (IChatChannelListener)this);
            }
            if (ErrorCode.failed(var2)) {
                final String var3 = ErrorCode.getString(var2);
                ChatController.this.à(String.format("Error connecting: %s", var3));
                this.Ø­áŒŠá(this.HorizonCode_Horizon_È);
                return false;
            }
            this.HorizonCode_Horizon_È(ChatController.Ø­áŒŠá.Â);
            this.Ý();
            return true;
        }
        
        public boolean Â() {
            switch (ChatController.Ó.HorizonCode_Horizon_È[this.Ý.ordinal()]) {
                case 1:
                case 2: {
                    final ErrorCode var1 = ChatController.this.Ó.disconnect(this.HorizonCode_Horizon_È);
                    if (ErrorCode.failed(var1)) {
                        final String var2 = ErrorCode.getString(var1);
                        ChatController.this.à(String.format("Error disconnecting: %s", var2));
                        return false;
                    }
                    this.HorizonCode_Horizon_È(ChatController.Ø­áŒŠá.Ø­áŒŠá);
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        protected void HorizonCode_Horizon_È(final Ø­áŒŠá p_176035_1_) {
            if (p_176035_1_ != this.Ý) {
                this.Ý = p_176035_1_;
            }
        }
        
        public void HorizonCode_Horizon_È(final String p_176032_1_) {
            if (ChatController.this.á == ChatController.Âµá€.HorizonCode_Horizon_È) {
                this.Âµá€.clear();
                this.Ó.clear();
            }
            else {
                if (this.Âµá€.size() > 0) {
                    final ListIterator var2 = this.Âµá€.listIterator();
                    while (var2.hasNext()) {
                        final ChatRawMessage var3 = var2.next();
                        if (var3.userName.equals(p_176032_1_)) {
                            var2.remove();
                        }
                    }
                }
                if (this.Ó.size() > 0) {
                    final ListIterator var2 = this.Ó.listIterator();
                    while (var2.hasNext()) {
                        final ChatTokenizedMessage var4 = var2.next();
                        if (var4.displayName.equals(p_176032_1_)) {
                            var2.remove();
                        }
                    }
                }
            }
            try {
                if (ChatController.this.HorizonCode_Horizon_È != null) {
                    ChatController.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_176032_1_);
                }
            }
            catch (Exception var5) {
                ChatController.this.à(var5.toString());
            }
        }
        
        public boolean Â(final String p_176037_1_) {
            if (this.Ý != ChatController.Ø­áŒŠá.Ý) {
                return false;
            }
            final ErrorCode var2 = ChatController.this.Ó.sendMessage(this.HorizonCode_Horizon_È, p_176037_1_);
            if (ErrorCode.failed(var2)) {
                final String var3 = ErrorCode.getString(var2);
                ChatController.this.à(String.format("Error sending chat message: %s", var3));
                return false;
            }
            return true;
        }
        
        protected void Ý() {
            if (ChatController.this.á != ChatController.Âµá€.HorizonCode_Horizon_È && this.à == null) {
                final ErrorCode var1 = ChatController.this.Ó.downloadBadgeData(this.HorizonCode_Horizon_È);
                if (ErrorCode.failed(var1)) {
                    final String var2 = ErrorCode.getString(var1);
                    ChatController.this.à(String.format("Error trying to download badge data: %s", var2));
                }
            }
        }
        
        protected void Ø­áŒŠá() {
            if (this.à == null) {
                this.à = new ChatBadgeData();
                final ErrorCode var1 = ChatController.this.Ó.getBadgeData(this.HorizonCode_Horizon_È, this.à);
                if (ErrorCode.succeeded(var1)) {
                    try {
                        if (ChatController.this.HorizonCode_Horizon_È != null) {
                            ChatController.this.HorizonCode_Horizon_È.Ý(this.HorizonCode_Horizon_È);
                        }
                    }
                    catch (Exception var2) {
                        ChatController.this.à(var2.toString());
                    }
                }
                else {
                    ChatController.this.à("Error preparing badge data: " + ErrorCode.getString(var1));
                }
            }
        }
        
        protected void Âµá€() {
            if (this.à != null) {
                final ErrorCode var1 = ChatController.this.Ó.clearBadgeData(this.HorizonCode_Horizon_È);
                if (ErrorCode.succeeded(var1)) {
                    this.à = null;
                    try {
                        if (ChatController.this.HorizonCode_Horizon_È != null) {
                            ChatController.this.HorizonCode_Horizon_È.Ø­áŒŠá(this.HorizonCode_Horizon_È);
                        }
                    }
                    catch (Exception var2) {
                        ChatController.this.à(var2.toString());
                    }
                }
                else {
                    ChatController.this.à("Error releasing badge data: " + ErrorCode.getString(var1));
                }
            }
        }
        
        protected void Ý(final String p_176031_1_) {
            try {
                if (ChatController.this.HorizonCode_Horizon_È != null) {
                    ChatController.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_176031_1_);
                }
            }
            catch (Exception var3) {
                ChatController.this.à(var3.toString());
            }
        }
        
        protected void Ø­áŒŠá(final String p_176036_1_) {
            try {
                if (ChatController.this.HorizonCode_Horizon_È != null) {
                    ChatController.this.HorizonCode_Horizon_È.Â(p_176036_1_);
                }
            }
            catch (Exception var3) {
                ChatController.this.à(var3.toString());
            }
        }
        
        private void Ó() {
            if (this.Ý != ChatController.Ø­áŒŠá.Âµá€) {
                this.HorizonCode_Horizon_È(ChatController.Ø­áŒŠá.Âµá€);
                this.Ø­áŒŠá(this.HorizonCode_Horizon_È);
                this.Âµá€();
            }
        }
        
        public void chatStatusCallback(final String p_chatStatusCallback_1_, final ErrorCode p_chatStatusCallback_2_) {
            if (!ErrorCode.succeeded(p_chatStatusCallback_2_)) {
                ChatController.this.áŒŠÆ.remove(p_chatStatusCallback_1_);
                this.Ó();
            }
        }
        
        public void chatChannelMembershipCallback(final String p_chatChannelMembershipCallback_1_, final ChatEvent p_chatChannelMembershipCallback_2_, final ChatChannelInfo p_chatChannelMembershipCallback_3_) {
            switch (ChatController.Ó.Â[p_chatChannelMembershipCallback_2_.ordinal()]) {
                case 1: {
                    this.HorizonCode_Horizon_È(ChatController.Ø­áŒŠá.Ý);
                    this.Ý(p_chatChannelMembershipCallback_1_);
                    break;
                }
                case 2: {
                    this.Ó();
                    break;
                }
            }
        }
        
        public void chatChannelUserChangeCallback(final String p_chatChannelUserChangeCallback_1_, final ChatUserInfo[] p_chatChannelUserChangeCallback_2_, final ChatUserInfo[] p_chatChannelUserChangeCallback_3_, final ChatUserInfo[] p_chatChannelUserChangeCallback_4_) {
            for (int var5 = 0; var5 < p_chatChannelUserChangeCallback_3_.length; ++var5) {
                final int var6 = this.Ø­áŒŠá.indexOf(p_chatChannelUserChangeCallback_3_[var5]);
                if (var6 >= 0) {
                    this.Ø­áŒŠá.remove(var6);
                }
            }
            for (int var5 = 0; var5 < p_chatChannelUserChangeCallback_4_.length; ++var5) {
                final int var6 = this.Ø­áŒŠá.indexOf(p_chatChannelUserChangeCallback_4_[var5]);
                if (var6 >= 0) {
                    this.Ø­áŒŠá.remove(var6);
                }
                this.Ø­áŒŠá.add(p_chatChannelUserChangeCallback_4_[var5]);
            }
            for (int var5 = 0; var5 < p_chatChannelUserChangeCallback_2_.length; ++var5) {
                this.Ø­áŒŠá.add(p_chatChannelUserChangeCallback_2_[var5]);
            }
            try {
                if (ChatController.this.HorizonCode_Horizon_È != null) {
                    ChatController.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_chatChannelUserChangeCallback_2_, p_chatChannelUserChangeCallback_3_, p_chatChannelUserChangeCallback_4_);
                }
            }
            catch (Exception var7) {
                ChatController.this.à(var7.toString());
            }
        }
        
        public void chatChannelRawMessageCallback(final String p_chatChannelRawMessageCallback_1_, final ChatRawMessage[] p_chatChannelRawMessageCallback_2_) {
            for (int var3 = 0; var3 < p_chatChannelRawMessageCallback_2_.length; ++var3) {
                this.Âµá€.addLast(p_chatChannelRawMessageCallback_2_[var3]);
            }
            try {
                if (ChatController.this.HorizonCode_Horizon_È != null) {
                    ChatController.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_chatChannelRawMessageCallback_2_);
                }
            }
            catch (Exception var4) {
                ChatController.this.à(var4.toString());
            }
            while (this.Âµá€.size() > ChatController.this.áˆºÑ¢Õ) {
                this.Âµá€.removeFirst();
            }
        }
        
        public void chatChannelTokenizedMessageCallback(final String p_chatChannelTokenizedMessageCallback_1_, final ChatTokenizedMessage[] p_chatChannelTokenizedMessageCallback_2_) {
            for (int var3 = 0; var3 < p_chatChannelTokenizedMessageCallback_2_.length; ++var3) {
                this.Ó.addLast(p_chatChannelTokenizedMessageCallback_2_[var3]);
            }
            try {
                if (ChatController.this.HorizonCode_Horizon_È != null) {
                    ChatController.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_chatChannelTokenizedMessageCallback_2_);
                }
            }
            catch (Exception var4) {
                ChatController.this.à(var4.toString());
            }
            while (this.Ó.size() > ChatController.this.áˆºÑ¢Õ) {
                this.Ó.removeFirst();
            }
        }
        
        public void chatClearCallback(final String p_chatClearCallback_1_, final String p_chatClearCallback_2_) {
            this.HorizonCode_Horizon_È(p_chatClearCallback_2_);
        }
        
        public void chatBadgeDataDownloadCallback(final String p_chatBadgeDataDownloadCallback_1_, final ErrorCode p_chatBadgeDataDownloadCallback_2_) {
            if (ErrorCode.succeeded(p_chatBadgeDataDownloadCallback_2_)) {
                this.Ø­áŒŠá();
            }
        }
    }
    
    static final class Ó
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        static final int[] Ý;
        private static final String Ø­áŒŠá = "CL_00002372";
        
        static {
            Ý = new int[Âµá€.values().length];
            try {
                Ó.Ý[Âµá€.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Ó.Ý[Âµá€.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Ó.Ý[Âµá€.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            Â = new int[ChatEvent.values().length];
            try {
                Ó.Â[ChatEvent.TTV_CHAT_JOINED_CHANNEL.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                Ó.Â[ChatEvent.TTV_CHAT_LEFT_CHANNEL.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            HorizonCode_Horizon_È = new int[ChatController.Ø­áŒŠá.values().length];
            try {
                Ó.HorizonCode_Horizon_È[ChatController.Ø­áŒŠá.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                Ó.HorizonCode_Horizon_È[ChatController.Ø­áŒŠá.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                Ó.HorizonCode_Horizon_È[ChatController.Ø­áŒŠá.HorizonCode_Horizon_È.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                Ó.HorizonCode_Horizon_È[ChatController.Ø­áŒŠá.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                Ó.HorizonCode_Horizon_È[ChatController.Ø­áŒŠá.Ø­áŒŠá.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
        }
    }
    
    public interface Â
    {
        void Ø­áŒŠá(final ErrorCode p0);
        
        void Âµá€(final ErrorCode p0);
        
        void Ø­áŒŠá();
        
        void Âµá€();
        
        void HorizonCode_Horizon_È(final Ý p0);
        
        void HorizonCode_Horizon_È(final String p0, final ChatTokenizedMessage[] p1);
        
        void HorizonCode_Horizon_È(final String p0, final ChatRawMessage[] p1);
        
        void HorizonCode_Horizon_È(final String p0, final ChatUserInfo[] p1, final ChatUserInfo[] p2, final ChatUserInfo[] p3);
        
        void HorizonCode_Horizon_È(final String p0);
        
        void Â(final String p0);
        
        void HorizonCode_Horizon_È(final String p0, final String p1);
        
        void Ý(final String p0);
        
        void Ø­áŒŠá(final String p0);
    }
}
