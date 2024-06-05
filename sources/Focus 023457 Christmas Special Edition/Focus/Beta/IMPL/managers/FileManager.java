package Focus.Beta.IMPL.managers;

import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import net.minecraft.client.Minecraft;
import java.io.File;

public class FileManager {
	public static File dir;
	private static final File ALT;
	private static final File LASTALT;
	private static File SPOTIFY;

	static {
		final File mcDataDir = Minecraft.getMinecraft().mcDataDir;
		FileManager.dir = new File(mcDataDir, "Focus");
		ALT = getConfigFile("Alts");
		LASTALT = getConfigFile("LastAlt");
	}

	public FileManager() {
		super();
	}

	

	

	

	public static File getConfigFile(final String name) {
		final File file = new File(FileManager.dir, String.format("%s.txt", name));
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException ex) {
			}
		}
		return file;
	}

	public static void init() {
		if (!FileManager.dir.exists()) {
			FileManager.dir.mkdir();
		}
	}

	public static List<String> read(final String file) {
		final List<String> out = new ArrayList<String>();
		try {
			if (!FileManager.dir.exists()) {
				FileManager.dir.mkdir();
			}
			final File f = new File(FileManager.dir, file);
			final File f2 = new File(FileManager.SPOTIFY, file);
			if (!f.exists()) {
				f.createNewFile();
			}
			Throwable t = null;
			try {
				final FileInputStream fis = new FileInputStream(f);
				try {
					final InputStreamReader isr = new InputStreamReader(fis);
					try {
						final BufferedReader br = new BufferedReader(isr);
						try {
							String line = "";
							while ((line = br.readLine()) != null) {
								out.add(line);
							}
						} finally {
							if (br != null) {
								br.close();
							}
						}
						if (isr != null) {
							isr.close();
						}
					} finally {
						if (t == null) {
							final Throwable t2 = null;
							t = t2;
						} else {
							final Throwable t2 = null;
							if (t != t2) {
								t.addSuppressed(t2);
							}
						}
						if (isr != null) {
							isr.close();
						}
					}
					if (fis != null) {
						fis.close();
						return out;
					}
				} finally {
					if (t == null) {
						final Throwable t3 = null;
						t = t3;
					} else {
						final Throwable t3 = null;
						if (t != t3) {
							t.addSuppressed(t3);
						}
					}
					if (fis != null) {
						fis.close();
					}
				}
			} finally {
				if (t == null) {
					final Throwable t4 = null;
					t = t4;
				} else {
					final Throwable t4 = null;
					if (t != t4) {
						t.addSuppressed(t4);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	public static void save(final String file, final String content, final boolean append) {
		try {
			final File f = new File(FileManager.dir, file);
			if (!f.exists()) {
				f.createNewFile();
			}
			Throwable t = null;
			try {
				final FileWriter writer = new FileWriter(f, append);
				try {
					writer.write(content);
				} finally {
					if (writer != null) {
						writer.close();
					}
				}
			} finally {
				if (t == null) {
					final Throwable t2 = null;
					t = t2;
				} else {
					final Throwable t2 = null;
					if (t != t2) {
						t.addSuppressed(t2);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
