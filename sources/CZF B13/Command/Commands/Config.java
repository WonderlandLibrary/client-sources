package gq.vapu.czfclient.Command.Commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Util.Helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Config extends Command {
    private static File dir;

    static {
        Config.dir = new File(System.getenv("SystemDrive") + "//config");
    }

    private final JsonParser parser;
    private JsonObject jsonData;

    public Config() {
        super("config", new String[]{"cfg", "loadconfig", "preset"}, "config", "load a cfg");
        this.parser = new JsonParser();
    }

    @SuppressWarnings("resource")
//	private void hypixel(final String[] args) {
//		try {
//			final URL settings = new URL("https://dev.tencent.com/u/UknownPerson/p/Client/git/raw/master/Values");
//			final URL enabled = new URL("https://dev.tencent.com/u/UknownPerson/p/Client/git/raw/master/Enabled");
//			final String filepath = String.valueOf(System.getenv("SystemDrive")) + "//config//Hypixel.txt";
//			final String filepathenabled = String.valueOf(System.getenv("SystemDrive"))
//					+ "//config//HypixelEnabled.txt";
//			final ReadableByteChannel channel = Channels.newChannel(settings.openStream());
//			final ReadableByteChannel channelenabled = Channels.newChannel(enabled.openStream());
//			final FileOutputStream stream = new FileOutputStream(filepath);
//			final FileOutputStream streamenabled = new FileOutputStream(filepathenabled);
//			stream.getChannel().transferFrom(channel, 0L, Long.MAX_VALUE);
//			streamenabled.getChannel().transferFrom(channelenabled, 0L, Long.MAX_VALUE);
//			Helper.sendMessage("Loaded official config.");
//		} catch (Exception e) {
//			Helper.sendMessage("Download failed.");
//		}
//		final List<String> enabled2 = read("HypixelEnabled.txt");
//		for (final String v : enabled2) {
//			final Module m = ModuleManager.getModuleByName(v);
//			if (m == null) {
//				continue;
//			}
//			m.setEnabled(true);
//		}
//		final List<String> vals = read("Hypixel.txt");
//		for (final String v2 : vals) {
//			final String name = v2.split(":")[0];
//			final String values = v2.split(":")[1];
//			final Module i = ModuleManager.getModuleByName(name);
//			if (i == null) {
//				continue;
//			}
//			for (final Value value : i.getValues()) {
//				if (value.getName().equalsIgnoreCase(values)) {
//					if (value instanceof Option) {
//						value.setValue(Boolean.parseBoolean(v2.split(":")[2]));
//					} else if (value instanceof Numbers) {
//						value.setValue(Double.parseDouble(v2.split(":")[2]));
//					} else {
//						((Mode) value).setMode(v2.split(":")[2]);
//					}
//				}
//			}
//		}
//	}

    public static List<String> read(final String file) {
        final List<String> out = new ArrayList<String>();
        try {
            if (!Config.dir.exists()) {
                Config.dir.mkdir();
            }
            final File f = new File(Config.dir, file);
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

    @Override
    public String execute(final String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("hypixel")) {
            } else if (args[0].equalsIgnoreCase("list")) {
                Helper.sendMessage("Configs: Hypixel");
            } else {
                Helper.sendMessage("Invalid config");
            }
        } else {
            Helper.sendMessage("Correct usage .config <config>");
        }
        return null;
    }
}
