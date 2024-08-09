/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  ca.weblite.objc.Client
 *  ca.weblite.objc.NSObject
 *  ca.weblite.objc.Proxy
 *  ca.weblite.objc.annotations.Msg
 */
package com.mojang.text2speech;

import ca.weblite.objc.Client;
import ca.weblite.objc.NSObject;
import ca.weblite.objc.Proxy;
import ca.weblite.objc.annotations.Msg;
import com.google.common.collect.Queues;
import com.mojang.text2speech.Narrator;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NarratorOSX
extends NSObject
implements Narrator {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Proxy synth = Client.getInstance().sendProxy("NSSpeechSynthesizer", "alloc", new Object[0]);
    private boolean speaking;
    private boolean crashed;
    private final Queue<String> queue = Queues.newConcurrentLinkedQueue();

    public NarratorOSX() {
        super("NSObject");
        this.synth.send("init", new Object[0]);
        this.synth.send("setDelegate:", new Object[]{this});
    }

    private void startSpeaking(String string) {
        this.synth.send("startSpeakingString:", new Object[]{string});
    }

    @Msg(selector="speechSynthesizer:didFinishSpeaking:", signature="v@:B")
    public void didFinishSpeaking(boolean bl) {
        if (this.queue.isEmpty()) {
            this.speaking = false;
        } else {
            this.startSpeaking(this.queue.poll());
        }
    }

    @Override
    public void say(String string, boolean bl) {
        if (this.crashed) {
            return;
        }
        try {
            if (bl) {
                this.synth.send("stopSpeaking", new Object[0]);
            }
            if (this.speaking) {
                this.queue.offer(string);
            } else {
                this.speaking = true;
                this.startSpeaking(string);
            }
        } catch (Throwable throwable) {
            this.crashed = true;
            LOGGER.error(String.format("Narrator crashed : %s", throwable));
        }
    }

    @Override
    public void clear() {
        this.queue.clear();
        this.synth.send("stopSpeaking", new Object[0]);
    }

    @Override
    public boolean active() {
        return false;
    }

    @Override
    public void destroy() {
    }
}

