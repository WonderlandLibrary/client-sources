
package lunadevs.luna.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import lunadevs.luna.login.Alt;
import lunadevs.luna.main.Luna;
import lunadevs.luna.manage.ModuleManager;
import net.minecraft.client.Minecraft;

public class FileUtils {
	
	private static final File ALT = FileUtils.getConfigFile("Alts");
	private static final File LASTALT = FileUtils.getConfigFile("LastAlt");

	public static void init() {
		loadLastAlt();
		loadAlts();
		ModuleManager.load();
	}

	public static void loadLastAlt() {
		try {
			String s;
			if (!LASTALT.exists()) {
				PrintWriter printWriter = new PrintWriter(new FileWriter(LASTALT));
				printWriter.println();
				printWriter.close();
			} else if (LASTALT.exists()) {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(LASTALT));
				while ((s = bufferedReader.readLine()) != null) {
					if (s.contains("\t")) {
						s = s.replace("\t", "    ");
					}
					if (s.contains("    ")) {
						String[] parts = s.split("    ");
						String[] account = parts[1].split(":");
						if (account.length == 2) {
							Luna.getAltManager().setLastAlt(new Alt(account[0], account[1], parts[0]));
						} else {
							String pw = account[1];
							for (int i = 2; i < account.length; i++) {
								pw = pw + ":" + account[i];
							}
							Luna.getAltManager().setLastAlt(new Alt(account[0], pw, parts[0]));
						}
					} else {
						String[] account = s.split(":");
						if (account.length == 1) {
							Luna.getAltManager().setLastAlt(new Alt(account[0], ""));
						} else if (account.length == 2) {
							Luna.getAltManager().setLastAlt(new Alt(account[0], account[1]));
						} else {
							String pw = account[1];
							for (int i = 2; i < account.length; i++) {
								pw = pw + ":" + account[i];
							}
							Luna.getAltManager().setLastAlt(new Alt(account[0], pw));
						}
					}
				}
				bufferedReader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveLastAlt() {
		try {
			PrintWriter printWriter = new PrintWriter(LASTALT);
			Alt alt = Luna.getAltManager().getLastAlt();
			if (alt != null) {
				if (alt.getMask().equals("")) {
					printWriter.println(alt.getUsername() + ":" + alt.getPassword());
				} else {
					printWriter.println(alt.getMask() + "    " + alt.getUsername() + ":" + alt.getPassword());
				}
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void loadAlts() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(ALT));
			String s;
			if (!ALT.exists()) {
				PrintWriter printWriter = new PrintWriter(new FileWriter(ALT));
				printWriter.println();
				printWriter.close();
			} else if (ALT.exists()) {
				while ((s = bufferedReader.readLine()) != null) {
					if (s.contains("\t")) {
						s = s.replace("\t", "    ");
					}
					if (s.contains("    ")) {
						String[] parts = s.split("    ");
						String[] account = parts[1].split(":");
						if (account.length == 2) {
							Luna.getAltManager().getAlts().add(new Alt(account[0], account[1], parts[0]));
						} else {
							String pw = account[1];
							for (int i = 2; i < account.length; i++) {
								pw = pw + ":" + account[i];
							}
							Luna.getAltManager().getAlts().add(new Alt(account[0], pw, parts[0]));
						}
					} else {
						String[] account = s.split(":");
						if (account.length == 1) {
							Luna.getAltManager().getAlts().add(new Alt(account[0], ""));
						} else if (account.length == 2) {
							try {
								Luna.getAltManager().getAlts().add(new Alt(account[0], account[1]));
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							String pw = account[1];
							for (int i = 2; i < account.length; i++) {
								pw = pw + ":" + account[i];
							}
							Luna.getAltManager().getAlts().add(new Alt(account[0], pw));
						}
					}
				}
			}
			bufferedReader.close();
		} catch (Exception e) {

		}
	}

	public static void saveAlts() {
		try {
			PrintWriter printWriter = new PrintWriter(ALT);
			for (Alt alt : Luna.getAltManager().getAlts()) {
				if (alt.getMask().equals("")) {
					printWriter.println(alt.getUsername() + ":" + alt.getPassword());
				} else {
					printWriter.println(alt.getMask() + "    " + alt.getUsername() + ":" + alt.getPassword());
				}
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
    public static List<String> read(final File inputFile) {
        final ArrayList<String> readContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String currentReadLine2;
            while ((currentReadLine2 = reader.readLine()) != null) {
                readContent.add(currentReadLine2);
            }
        }
        catch (FileNotFoundException currentReadLine3) {}
        catch (IOException currentReadLine4) {}
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException ex) {}
        }
        try {
            if (reader != null) {
                reader.close();
            }
        }
        catch (IOException ex2) {}
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
        catch (IOException outputLine2) {}
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException ex) {}
        }
        try {
            if (writer != null) {
                writer.close();
            }
        }
        catch (IOException ex2) {}
    }
    
    public static File getConfigDir() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir, "Luna");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
    
    public static File getConfigFile(final String name) {
        final File file = new File(getConfigDir(), String.format("%s.txt", name));
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {}
        }
        return file;
    }
}
