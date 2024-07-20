/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;

public class MusicHelper {
    private static AudioInputStream stream;
    private static final List<Clip> CLIPS_LIST;

    public static void playSound(String location, float volume) {
        CLIPS_LIST.stream().filter(Objects::nonNull).filter(clip -> clip.isOpen()).filter(clip -> !clip.isRunning()).forEach(Line::close);
        CLIPS_LIST.stream().filter(Objects::nonNull).filter(clip -> !clip.isOpen() || !clip.isRunning()).forEach(DataLine::stop);
        CLIPS_LIST.stream().filter(Objects::nonNull).toList().forEach(clip -> {
            if (!clip.isRunning()) {
                CLIPS_LIST.remove(clip);
            }
        });
        try {
            String resourcePath = "/assets/minecraft/vegaline/sounds/" + location;
            InputStream inputStream = MusicHelper.class.getResourceAsStream(resourcePath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            stream = AudioSystem.getAudioInputStream(bufferedInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (stream == null) {
            return;
        }
        try {
            CLIPS_LIST.add(AudioSystem.getClip());
        } catch (Exception e) {
            e.printStackTrace();
        }
        CLIPS_LIST.stream().filter(Objects::nonNull).filter(clip -> !clip.isOpen()).forEach(clip -> {
            try {
                clip.open(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        CLIPS_LIST.stream().filter(Objects::nonNull).filter(Line::isOpen).forEach(clip -> {
            float volumeVal = volume < 0.0f ? 0.0f : (volume > 1.0f ? 1.0f : volume);
            FloatControl volumeControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue((float)(Math.log(volumeVal) / Math.log(10.0) * 20.0));
        });
        CLIPS_LIST.stream().filter(Objects::nonNull).filter(Line::isOpen).filter(clip -> !clip.isRunning()).forEach(DataLine::start);
    }

    public static void playSound(String location) {
        MusicHelper.playSound(location, 0.45f);
    }

    static {
        CLIPS_LIST = new ArrayList<Clip>();
    }
}

