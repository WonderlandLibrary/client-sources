// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.utils;

import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import javax.sound.sampled.AudioSystem;
import java.io.FileInputStream;
import com.klintos.twelve.Twelve;
import java.io.FilenameFilter;
import java.io.File;

public class MP3Utils
{
    public static File[] findMP3(final String dirName) {
        final File dir = new File(dirName);
        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String filename) {
                return filename.endsWith(".mp3");
            }
        });
    }
    
    public static String getTitleAndArtist(final File file) {
        try {
            if (Twelve.getInstance().getMP3Player().isStopped()) {
                return "No song playing.";
            }
            final FileInputStream fis = new FileInputStream(file);
            final int size = (int)file.length();
            fis.skip(size - 128);
            final byte[] last128 = new byte[128];
            fis.read(last128);
            final String id3 = new String(last128);
            final String tag = id3.substring(0, 3);
            if (tag.equals("TAG")) {
                final String result = String.valueOf(id3.substring(33, 62).trim()) + " - " + id3.substring(3, 32).trim();
                return result;
            }
        }
        catch (Exception ex) {}
        return "No Song Playing.";
    }
    
    public static String getTitle(final File file) {
        try {
            final FileInputStream fis = new FileInputStream(file);
            final int size = (int)file.length();
            fis.skip(size - 128);
            final byte[] last128 = new byte[128];
            fis.read(last128);
            final String id3 = new String(last128);
            final String tag = id3.substring(0, 3);
            if (tag.equals("TAG")) {
                final String result = id3.substring(3, 32).trim();
                return result;
            }
        }
        catch (Exception ex) {}
        return "NOT AVALIABLE.";
    }
    
    public static int getLength(final File file) {
        try {
            final AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
            if (fileFormat instanceof TAudioFileFormat) {
                final Map<?, ?> properties = (Map<?, ?>)((TAudioFileFormat)fileFormat).properties();
                final String key = "duration";
                final Long microseconds = (Long)properties.get(key);
                final int milli = (int)(microseconds / 1000L);
                return milli;
            }
            throw new UnsupportedAudioFileException();
        }
        catch (Exception ex) {
            return 0;
        }
    }
}
