package wtf.shiyeno.modules.impl.util;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;

@FunctionAnnotation(name = "ClientSounds", type = Type.Util)
public class ClientSounds extends Function {

    @Override
    public void onEvent(Event event) {
    }
}