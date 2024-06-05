package me.superskidder.lune.utils.client;

import me.superskidder.lune.Lune;
import me.superskidder.lune.guis.login.Alt;
import me.superskidder.lune.guis.login.AltManager;
import me.superskidder.lune.guis.clickgui2.Config;
import me.superskidder.lune.guis.clickgui2.VapeClickGui;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.utils.json.JsonUtil;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static File mcDir = Minecraft.getMinecraft().mcDataDir;
    public static File dir = new File(mcDir, "Lune");

    public FileUtil() {
        if (!FileUtil.dir.exists()) {
            FileUtil.dir.mkdir();
        }
    }

    public static void copyFile(String srcPathStr, String desPathStr) {
        //1.获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\") + 1); //目标文件地址
        System.out.println(newFileName);
        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        System.out.println(desPathStr);

        try {
            //2.创建输入输出流对象
            FileInputStream fis = new FileInputStream(srcPathStr);
            FileOutputStream fos = new FileOutputStream(desPathStr);

            //创建搬运工具
            byte datas[] = new byte[1024 * 8];
            //创建长度
            int len = 0;
            //循环读取数据
            while ((len = fis.read(datas)) != -1) {
                fos.write(datas, 0, len);
            }
            //3.释放资源
            fis.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String readFile(String fileName) {
        StringBuilder result = new StringBuilder();

        try {
            File file = new File(FileUtil.dir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fIn = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fIn))) {
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    result.append(str);
                    result.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    public static String readFile(String dir,String fileName) {
        StringBuilder result = new StringBuilder();

        try {
            File file = new File(dir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fIn = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fIn))) {
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    result.append(str);
                    result.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static List<String> read(String file) {
        final List<String> out = new ArrayList<>();
        try {
            if (!FileUtil.dir.exists()) {
                FileUtil.dir.mkdir();
            }
            final File f = new File(FileUtil.dir, file);
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
            final File f = new File(FileUtil.dir, file);
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

    public static void saveFile(String fileName, String context) {
        File dir = new File(mcDir, "Lune");
        File file = new File(dir, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(context);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveFile(String dir,String fileName, String context) {
        File file = new File(dir, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(context);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void saveAlts() {
        String alts = "";
        if (AltManager.getAlts().size() == 0)
            return;
        for (Alt a : AltManager.getAlts()) {
            alts = alts + a.getUsername() + ":" + a.getPassword() + "\n";
        }
        saveFile("alts.txt", alts);
    }
    public static void loadAlts() {
        AltManager.alts.clear();
        String alts = readFile("alts.txt");

        for (String s : alts.split("\n")) {
            String[] split = s.split(":");
            if (split.length < 2) {
                return;
            }
            AltManager.alts.add(new Alt(split[0], split[1].substring(0, split[1].toCharArray().length - 1)));
        }

    }





    public static void saveConfig(String text, boolean add) {

        if (add) {
            save("configs.txt", text + "\n", true);
        }


        // Save opened features
        String enable = "";
        for (Mod m : ModuleManager.modList) {
            if (!m.getState()) {
                continue;
            }
            enable = enable + String.format("%s%s", m.getName(), System.lineSeparator());
        }
        FileUtil.save(text + "_Enabled.txt", enable, false);

        // Save bound keys
        StringBuilder keys = new StringBuilder();
        for (Mod mod : ModuleManager.modList) {
            int key = mod.getKey();
            if (key != 0) {
                keys.append(String.format("%s:%s%s", mod.getName(), key, System.lineSeparator()));
            }
        }
        FileUtil.save(text + "_Keys.txt", keys.toString(), false);

        // Save function values
        StringBuilder content = new StringBuilder();
        for (Mod m : ModuleManager.modList) {
            for (Value v : m.getValues()) {
                content.append(String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator()));
            }
        }
        FileUtil.save(text + "_Values.txt", content.toString(), false);
    }
    
    public static void getConfigs() {
    	File configPathFile = new File(dir.getAbsolutePath(), "Configs");
    	
    	for(File file : getFiles(configPathFile.getAbsolutePath())) {
    		String name = file.getAbsolutePath();
    		
			if(name.endsWith("config.json")) {
    			String[] split = name.split(Util.getOSType() == Util.EnumOS.WINDOWS ? "\\\\" : "/");

    			// 防止将客户端默认配置当成可加载的配置
    			if(!split[split.length - 3].equals("Configs")) {
    				continue;
    			}
    			
				VapeClickGui.configs.add(new Config(split[split.length - 2], "Location", true));
    		}
    	}
    }
    
    public static List<File> getFiles(String path) {
    	List<File> files = new ArrayList<File>();
    	File pathFile = new File(path);
    	
		File[] filesInPath = pathFile.listFiles();
		for(File file : filesInPath) {
	    	if (file.isDirectory()) {
	    		files.addAll(getFiles(file.getAbsolutePath()));
	    	} else {
	    		files.add(file);
	    	}
		}
    	
		return files;
    }
    
    public static void loadConfig(String cname) {
    	JsonUtil.saveConfig();
        //LoadEnables
        List<String> enabled = FileUtil.read(cname + "_Enabled.txt");
        for (String v : enabled) {
            Mod m = ModuleManager.getModsByName(v);
            if (m == null) {
                continue;
            }
            m.setStage(true);
        }
        //LoadKeys
        String msg = FileUtil.readFile(cname + "_Keys.txt");
        if ("".equals(msg)) {
            return;
        }
        String[] lines = msg.split(System.lineSeparator());
        for (String line : lines) {
            String[] ModAndKey = line.split(":");
            if (ModAndKey.length < 2) {
                return;
            }
            Mod mod = ModuleManager.getModsByName(ModAndKey[0]);
            if (mod == null) {
                return;
            }
            int key = Integer.parseInt(ModAndKey[1]);
            mod.setKey(key);
        }
        //LoadValues
        String content = FileUtil.readFile(cname + "_Values.txt");
        if ("".equals(content)) {
            return;
        }
        String[] cs = content.split(System.lineSeparator());
        for (String line : cs) {
            String name = line.split(":")[0];
            String values = line.split(":")[1];
            Mod m = ModuleManager.getModsByName(name);
            if (m == null) {
                continue;
            }
            for (Value v : m.getValues()) {
                if (!v.getName().equalsIgnoreCase(values)) {
                    continue;
                }

                if (v instanceof Bool) {
                    v.setValue(Boolean.parseBoolean(line.split(":")[2]));
                    continue;
                } else if (v instanceof Num) {
                    v.setValue(Double.parseDouble(line.split(":")[2]));
                    continue;
                } else if (v instanceof Mode) {
                    ((Mode) v).setMode(line.split(":")[2]);
                }
            }
        }
    }



}
