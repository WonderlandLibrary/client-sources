package me.swezedcode.client.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import me.swezedcode.client.manager.managers.FileManager;
import net.minecraft.client.Minecraft;

public class FileUtils {

	public static Clip clip = null;
	public static String ljw = "";

	public static void download(String link, String downloadname, String location, String format) {
		try {
			URL url = new URL(link);

			File download = new File(FileManager.getDirectory() + "/" + location, downloadname + "." + format);
			if (download.exists()) {
				System.out.println(downloadname + "." + format + " already exists, ignoring...");
				return;
			}
			org.apache.commons.io.FileUtils.copyURLToFile(url, download);

			System.out.println("Successfully Downloaded " + downloadname + "." + format);
		} catch (IOException e) {
			System.out.println("Failed to Download " + downloadname + "." + format);
		}
	}

	public static String downloadString(String url) throws Exception {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();
		return response.toString();
	}

	public static void playSound(String path) {
		try {
			File inputStream = new File(path).getAbsoluteFile();
			AudioInputStream aStream = AudioSystem.getAudioInputStream(inputStream);
			ljw = inputStream.getName();
			Clip clip = AudioSystem.getClip();

			FileUtils.clip = clip;
			clip.open(aStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			clip = null;
			return;
		}
	}

	public static void stopSound(String path) {
		try {
			File inputStream = new File(path).getAbsoluteFile();
			AudioInputStream aStream = AudioSystem.getAudioInputStream(inputStream);
			ljw = inputStream.getName();
			Clip clip = AudioSystem.getClip();

			FileUtils.clip = clip;
			clip.open(aStream);
			clip.stop();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			clip = null;
			return;
		}
	}

	public static void setVolume(float volume) {
		FloatControl fct = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		fct.setValue(volume);
	}

	public String getURLSource(String link) {
		try {
			URL url = new URL(link);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder str = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				str.append(inputLine);
			}
			reader.close();
			return str.toString();
		} catch (Exception e) {
			throw new RuntimeException("");
		}
	}
	
	public static List<String> read(final File inputFile) {
        final List<String> readContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String currentReadLine;
            while ((currentReadLine = reader.readLine()) != null) {
                readContent.add(currentReadLine);
            }
        }
        catch (FileNotFoundException ex) {}
        catch (IOException ex2) {}
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException ex3) {}
        }
        try {
            if (reader != null) {
                reader.close();
            }
        }
        catch (IOException ex4) {}
        return readContent;
    }
    
    public static void write(final File outputFile, final List<String> writeContent, final boolean overrideContent) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, !overrideContent));
            for (final String outputLine : writeContent) {
                writer.write(outputLine);
                writer.flush();
                writer.newLine();
            }
        }
        catch (IOException ex) {}
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException ex2) {}
        }
        try {
            if (writer != null) {
                writer.close();
            }
        }
        catch (IOException ex3) {}
    }

	public static File getConfigDir() {
		File file = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath(), "Tea");
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	public static File getConfigFile(String name) {
		File file = new File(getConfigDir(), String.format("%s.txt", new Object[] { name }));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException localIOException) {
			}
		}
		return file;
	}

}
