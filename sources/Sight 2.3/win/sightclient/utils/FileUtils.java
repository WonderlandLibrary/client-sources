package win.sightclient.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.Sys;

import net.minecraft.util.Util;

public class FileUtils {

	public static ArrayList<String> getLines(String fileName) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static ArrayList<String> getLines(File f) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static void createFile(String fileName) {
		File usersFile = new File(fileName);
		if (!usersFile.exists()) {
			try {
				if (!usersFile.createNewFile()) {
					System.out.println("ERROR: Could not create " + fileName + " file");
				}
			} catch (IOException e) {
				System.out.println("ERROR: Could not create " + fileName + " file");
				e.printStackTrace();
			}
		}
	}
	
	public static void createFile(File usersFile) {
		if (!usersFile.exists()) {
			try {
				if (!usersFile.createNewFile()) {
					System.out.println("ERROR: Could not create " + usersFile.getAbsolutePath() + " file");
				}
			} catch (IOException e) {
				System.out.println("ERROR: Could not create " + usersFile.getAbsolutePath() + " file");
				e.printStackTrace();
			}
		}
	}
	
	public static void writeToFile(String fileName, List<String> lines) {
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter pw = new PrintWriter(file);
			for (int k = 0; k < lines.size(); k++) {
				String str = lines.get(k);
				if (str != lines.get(lines.size() - 1)) {
					pw.println(str);
				} else {
					pw.print(str);
				}
			}
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(File file, List<String> lines) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter pw = new PrintWriter(file);
			for (int k = 0; k < lines.size(); k++) {
				String str = lines.get(k);
				if (str != lines.get(lines.size() - 1)) {
					pw.println(str);
				} else {
					pw.print(str);
				}
			}
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void openDirectory(File dir) {
        String s = dir.getAbsolutePath();

        if (Util.getOSType() == Util.EnumOS.OSX) {
            try{
                Runtime.getRuntime().exec(new String[] {"/usr/bin/open", s});
                return;
            }
            catch (IOException ioexception1) {
            	ioexception1.printStackTrace();
            }
        }
        else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
            String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {s});
            try {
                Runtime.getRuntime().exec(s1);
                return;
            }
            catch (IOException ioexception) {
            	ioexception.printStackTrace();
            }
        }

        boolean flag = false;
        try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {dir.toURI()});
        }
        catch (Throwable throwable) {
            flag = true;
        }

        if (flag) {
            Sys.openURL("file://" + s);
        }
	}
}
