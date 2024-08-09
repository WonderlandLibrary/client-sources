package dev.excellent.impl.util.other;

import dev.excellent.api.interfaces.client.IAccess;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@UtilityClass
public class SoundUtil implements IAccess {

    private static AudioInputStream stream;
    private static final List<Clip> CLIPS_LIST = new ArrayList<>();

    public static void playSound(final String location, double volume) {
        List<Clip> mutableClips = new ArrayList<>(CLIPS_LIST);
        mutableClips.stream().filter(Objects::nonNull).filter(Line::isOpen).filter(clip -> !clip.isRunning()).forEach(Clip::close);
        mutableClips.stream().filter(Objects::nonNull).filter(clip -> !(clip.isOpen() && clip.isRunning())).forEach(Clip::stop);
        mutableClips.removeIf(clip -> !clip.isRunning());
        try {
            stream = AudioSystem.getAudioInputStream(new BufferedInputStream(SoundUtil.class.getResourceAsStream("/assets/" + excellent.getInfo().getNamespace() + "/sound/" + location)));
        } catch (final Exception ignored) {
        }
        assert stream != null;
        try {
            mutableClips.add(AudioSystem.getClip());
        } catch (final Exception exception) {
            System.out.println("Client:SoundUtil:" + exception.getMessage());
        }
        mutableClips.stream().filter(Objects::nonNull).filter(clip -> !clip.isOpen()).forEach(clip -> {
            try {
                clip.open(stream);
            } catch (final Exception ignored) {
            }
        });
        mutableClips.stream().filter(Objects::nonNull).filter(Clip::isOpen).forEach(clip -> {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            int dbValue = (int) (Math.log((volume < 0.D ? 0.D : Math.min(volume, 1.D)) * .5D) / Math.log(10.D) * 20.D);
            volumeControl.setValue(dbValue);
        });
        mutableClips.stream().filter(Objects::nonNull).filter(Clip::isOpen).filter(clip -> !clip.isRunning()).forEach(Clip::start);
    }


    public static void playSound(final String location) {
        playSound(location, .25D);
    }

    //VEGA33 GARBAGE MOMENT
    public class AudioClipPlayController {
        private final AudioClip audioClip;
        private Supplier<Boolean> playIf;
        private boolean stopIsAPause;
        private boolean started;

        private AudioClipPlayController(AudioClip audioClip, Supplier<Boolean> playIf, boolean stopIsAPause) {
            this.audioClip = audioClip;
            this.playIf = playIf;
            this.stopIsAPause = stopIsAPause;
        }

        public static AudioClipPlayController build(AudioClip audioClip, Supplier<Boolean> playIf, boolean stopIsAPause) {
            return new AudioClipPlayController(audioClip, playIf, stopIsAPause);
        }

        public void setPlayIf(Supplier<Boolean> playIf) {
            this.playIf = playIf;
        }

        public void setStopIsAPauseMode(boolean stopIsAPause) {
            this.stopIsAPause = stopIsAPause;
        }

        public void updatePlayingStatus() {
            if (started && audioClip.clip == null && playIf.get()) {
                started = false;
            }
            if (!started && playIf.get()) {
                audioClip.startPlayingAudio();
                started = true;
            }
            if (stopIsAPause) {
                audioClip.setPause(!playIf.get());
                return;
            }
            if (audioClip.isPlaying() != playIf.get()) {
                if (playIf.get()) audioClip.startPlayingAudio();
                else audioClip.stopPlayingAudio();
            }
        }

        public AudioClip getAudioClip() {
            return this.audioClip;
        }

        public boolean isSucessPlaying() {
            return this.audioClip.isPlaying();
        }
    }

    public class AudioClip {
        private final boolean loop;
        private boolean pause;
        private long currentPlayTime;
        @Getter
        private String soundName;
        private Clip clip;

        private AudioClip(String soundName, boolean loop) {
            this.soundName = soundName;
            this.loop = loop;
        }

        public static AudioClip build(String soundName, boolean loop) {
            return new AudioClip(soundName, loop);
        }

        public boolean isPlaying() {
            return this.clip != null && this.clip.isOpen() && this.clip.isRunning();
        }

        public void changeAudioTrack(String soundName) {
            this.soundName = soundName;
            stopPlayingAudio();
            startPlayingAudio();
        }

        public void setLoop(boolean loop) {
            if (this.clip == null) return;
            this.clip.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);
        }

        public boolean isLoop() {
            return this.loop && clip != null && clip.isOpen();
        }

        public void setPause(boolean pause) {
            if (this.pause != pause && clip != null && clip.isOpen() && clip.getMicrosecondLength() != 0) {
                if (pause) {
                    currentPlayTime = clip.getMicrosecondPosition();
                    clip.stop();
                } else {
                    clip.setMicrosecondPosition(currentPlayTime);
                    this.setVolume(this.getVolume());
                    this.setLoop(this.isLoop());
                    clip.start();
                }
                this.pause = pause;
            }
        }

        public boolean isPaused() {
            return this.pause && clip != null && !clip.isRunning();
        }

        public void setVolume(float volume) {
            if (this.clip == null) return;
            double dbValue = Math.log(volume * 0.5D) / Math.log(10.D) * 20.D;
            FloatControl control = ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN));
            if (control.getValue() != (int) dbValue) control.setValue((int) dbValue);
        }

        private float getVolume() {
            FloatControl control = ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN));
            return control.getValue();
        }

        public void startPlayingAudio() {
            this.stopPlayingAudio();
            try {
                this.clip = AudioSystem.getClip();
                String resourcePath = "/assets/" + excellent.getInfo().getNamespace() + "/sound/" + this.soundName;
                InputStream audioSrc = SoundUtil.class.getResourceAsStream(resourcePath);
                assert audioSrc != null;
                try {
                    BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
                    clip.open(inputStream);
                    this.setVolume(this.getVolume());
                    this.setLoop(this.isLoop());
                    clip.start();
                } catch (Exception exception) {
                    System.out.println(exception.getLocalizedMessage());
                }
            } catch (Exception exception) {
                System.out.println(exception.getLocalizedMessage());
            }
        }

        public void stopPlayingAudio() {
            if (this.clip == null) return;
            if (this.clip.isRunning()) this.clip.stop();
            if (this.clip.isOpen()) this.clip.close();
            this.clip = null;
        }
    }
}