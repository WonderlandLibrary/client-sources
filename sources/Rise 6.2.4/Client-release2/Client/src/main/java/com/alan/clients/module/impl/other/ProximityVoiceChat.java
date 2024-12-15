package com.alan.clients.module.impl.other;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;
import com.alan.clients.value.impl.SubMode;
import org.lwjgl.input.Keyboard;

import javax.sound.sampled.*;

@ModuleInfo(aliases = {"module.other.proximityvoicechat.name"}, description = "module.other.proximityvoicechat.description", category = Category.PLAYER)
public class ProximityVoiceChat extends Module {

    private final BooleanValue listenToYourself = new BooleanValue("Listen to yourself", this, false);
    private final NumberValue sampleRate = new NumberValue("Sample Rate", this, 16000, 8000, 192000, 4000);
    private final NumberValue sampleSizeInBits = new NumberValue("Sample Size in bits", this, 16, 8, 32, 1);
    public final ModeValue channel = new ModeValue("Channel (dont)", this)
            .add(new SubMode("Mono"))
            .add(new SubMode("Stereo"))
            .add(new SubMode("Quadraphonic"))
            .add(new SubMode("Surround Sound"))
            .setDefault("Mono");

    private Thread thread;
    private SourceDataLine sourceDataLine;
    private TargetDataLine targetDataLine;
    private boolean pressed = false;
    private int pressed_time = 0;

    private void enable() {
        if (thread != null) {
            thread.interrupt();
            targetDataLine.stop();
            targetDataLine.close();
            sourceDataLine.stop();
            sourceDataLine.close();
        }
        thread = new Thread(() -> {
            try {
                AudioFormat format = getAudioFormat();
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

                targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
                targetDataLine.open(format);
                targetDataLine.start();

                DataLine.Info outInfo = new DataLine.Info(SourceDataLine.class, format);
                sourceDataLine = (SourceDataLine) AudioSystem.getLine(outInfo);
                sourceDataLine.open(format);
                sourceDataLine.start();

                byte[] buffer = new byte[4096];

                while (true) {
                    int bytesRead = targetDataLine.read(buffer, 0, buffer.length);
                    if (listenToYourself.getValue()) {
                        sourceDataLine.write(buffer, 0, bytesRead);
                    }
//                    Network.getInstance().getClient().sendBinaryMessage(buffer);
                }

            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    private void disable() {
        if (thread != null) {
            thread.interrupt();
        }
        if (targetDataLine != null) {
            targetDataLine.stop();
            targetDataLine.close();
        }
        if (sourceDataLine != null) {
            sourceDataLine.stop();
            sourceDataLine.close();
        }
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        if (Keyboard.isKeyDown(45)) {
            if (!pressed) {
                pressed = true;
                pressed_time = 0;
                enable();
                ChatUtil.display("Start Talking");
            } else {
                ChatUtil.display("Talking");
                pressed_time++;
            }
        } else {
            if (pressed) {
                ChatUtil.display("Stop talking");

                pressed = false;
                disable();
            }
        }
    };

    @Override
    public void onDisable() {
        disable();
    }

    private AudioFormat getAudioFormat() {
        int channels;
        switch (channel.getValue().getName()) {
            case "Stereo":
                channels = 2;
                break;
            case "Quadraphonic":
                channels = 4;
                break;
            case "Surround Sound":
                channels = 6;
                break;
            default:
                channels = 1;
                break;
        }
        return new AudioFormat(sampleRate.getValue().floatValue(), sampleSizeInBits.getValue().intValue(), channels, true, false);
    }
}
