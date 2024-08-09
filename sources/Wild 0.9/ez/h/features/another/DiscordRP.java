package ez.h.features.another;

import club.minnced.discord.rpc.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.*;

public class DiscordRP extends Feature
{
    public static final AccountType accountType;
    DiscordRichPresence discordRichPresense;
    OptionMode details;
    DiscordRPC discordRPC;
    String discordID;
    public static final int UID;
    
    @Override
    public void onDisable() {
        try {
            this.stopRPC();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onDisable();
    }
    
    void stopRPC() {
        this.discordRPC.Discord_Shutdown();
        this.discordRPC.Discord_ClearPresence();
    }
    
    public DiscordRP() {
        super("DiscordRP", "\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0447\u0438\u0442 \u0432 Discord \u0441\u0442\u0430\u0442\u0443\u0441\u0435.", Category.ANOTHER);
        this.details = new OptionMode(this, "Details", "Server", new String[] { "Server", "Nickname", "Enabled Features", "UID" }, 0);
        this.discordID = "902181385043845201";
        this.discordRichPresense = new DiscordRichPresence();
        this.discordRPC = DiscordRPC.INSTANCE;
        this.addOptions(this.details);
    }
    
    void startRPC() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: invokespecial   club/minnced/discord/rpc/DiscordEventHandlers.<init>:()V
        //     7: astore_1       
        //     8: aload_1        
        //     9: invokedynamic   BootstrapMethod #0, accept:()Lclub/minnced/discord/rpc/DiscordEventHandlers$OnStatus;
        //    14: putfield        club/minnced/discord/rpc/DiscordEventHandlers.disconnected:Lclub/minnced/discord/rpc/DiscordEventHandlers$OnStatus;
        //    17: aload_0        
        //    18: getfield        ez/h/features/another/DiscordRP.discordRPC:Lclub/minnced/discord/rpc/DiscordRPC;
        //    21: aload_0        
        //    22: getfield        ez/h/features/another/DiscordRP.discordID:Ljava/lang/String;
        //    25: aload_1        
        //    26: iconst_1       
        //    27: aconst_null    
        //    28: invokeinterface club/minnced/discord/rpc/DiscordRPC.Discord_Initialize:(Ljava/lang/String;Lclub/minnced/discord/rpc/DiscordEventHandlers;ZLjava/lang/String;)V
        //    33: aload_0        
        //    34: getfield        ez/h/features/another/DiscordRP.discordRichPresense:Lclub/minnced/discord/rpc/DiscordRichPresence;
        //    37: invokestatic    java/lang/System.currentTimeMillis:()J
        //    40: putfield        club/minnced/discord/rpc/DiscordRichPresence.startTimestamp:J
        //    43: new             Ljava/lang/Thread;
        //    46: dup            
        //    47: aload_0        
        //    48: invokedynamic   BootstrapMethod #1, run:(Lez/h/features/another/DiscordRP;)Ljava/lang/Runnable;
        //    53: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //    56: invokevirtual   java/lang/Thread.start:()V
        //    59: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void onEnable() {
        try {
            this.startRPC();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onEnable();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
    }
    
    static {
        accountType = AccountType.USER;
        UID = NativeTunnel.getPlayerUid();
    }
    
    public enum AccountType
    {
        MODER, 
        CODER, 
        BETA, 
        USER;
    }
}
