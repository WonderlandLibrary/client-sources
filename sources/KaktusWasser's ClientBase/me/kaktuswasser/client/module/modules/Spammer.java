// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.CopyOnWriteArrayList;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.EveryTick;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.Logger;
import me.kaktuswasser.client.utilities.TimeHelper;
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.Value;

import java.util.Random;
import java.util.List;

public class Spammer extends Module
{
    public Value<Float> delay;
    public Value<Boolean> filemode;
    public Value<String> message;
    public List messages;
    public TimeHelper time;
    public Random rand;
    
    public Spammer() {
        super("Spammer", -8092269, Category.PLAYER);
        this.delay = (Value<Float>)new ConstrainedValue("spammer_Delay", "delay", 2000.0f, 500.0f, 100000.0f, this);
        this.filemode = new Value<Boolean>("spammer_File", "file", false, this);
        this.message = new Value<String>("spammer_Message", "message", "", this);
        this.messages = new CopyOnWriteArrayList();
        this.time = new TimeHelper();
        this.rand = new Random();
        Client.getCommandManager().getCommands().add(new Command("spam", "<message/file/delay>") {
            @Override
            public void run(final String message) {
                final String[] arguments = message.split(" ");
                final String arg = arguments[1];
                if (arg.equalsIgnoreCase("message")) {
                    Spammer.this.message.setValue(message.substring((".spam " + arg + " ").length()));
                    Logger.writeChat("Spammer Message set to: \"" + Spammer.this.message.getValue() + "\"");
                }
                else if (arg.equalsIgnoreCase("file")) {
                    Spammer.this.filemode.setValue(!Spammer.this.filemode.getValue());
                    Logger.writeChat("Spammer will " + (Spammer.this.filemode.getValue() ? "now" : "no longer") + " use messages in the \"spam.txt\" file.");
                }
                else if (arg.equalsIgnoreCase("delay")) {
                    if (message.split(" ")[2].equalsIgnoreCase("-d")) {
                        Spammer.this.delay.setValue(Spammer.this.delay.getDefaultValue());
                    }
                    else {
                        Spammer.this.delay.setValue(Float.parseFloat(arguments[2]));
                    }
                    if (Spammer.this.delay.getValue() < 500.0f) {
                        Spammer.this.delay.setValue(500.0f);
                    }
                    Logger.writeChat("Spammer Delay set to: " + Spammer.this.delay.getValue());
                }
                else {
                    Logger.writeChat("Invalid option! Valid options: message, file, delay");
                }
            }
        });
    }
    
    public List getMessages() {
        return this.messages;
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        if (this.messages.isEmpty() && Client.getFileManager().getFileByName("spammermessage") != null) {
            Client.getFileManager().getFileByName("spammermessage").loadFile();
        }
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EveryTick) {
            if (Spammer.mc.thePlayer == null || Spammer.mc.theWorld == null) {
                return;
            }
            if (this.time.hasReached(this.delay.getValue())) {
                final Random randomNum = new Random();
                for (int msg = 1; msg <= 10; ++msg) {
                    randomNum.nextInt(100);
                }
                String var5 = new StringBuilder().append(randomNum).toString();
                if (this.filemode.getValue()) {
                    if (this.messages.isEmpty()) {
                        Client.getFileManager().getFileByName("spammermessage").loadFile();
                        return;
                    }
                    if (!this.messages.isEmpty() && Client.getFileManager().getFileByName("spammermessage") != null) {
                        var5 = (String) this.messages.get(0);
                        this.messages.remove(this.messages.get(0));
                    }
                }
                else {
                    if (this.message.getValue().equals("")) {
                        Logger.writeChat("You need to set a spam message! Type \".spam message <message>\"");
                        this.toggle();
                        return;
                    }
                    var5 = this.message.getValue();
                }
                Spammer.mc.thePlayer.sendChatMessage(String.valueOf(var5) + (((boolean)this.filemode.getValue()) ? "" : this.rand.nextInt(9999)));
                this.time.reset();
            }
        }
    }
}
