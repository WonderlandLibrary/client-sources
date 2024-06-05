package me.superskidder.lune.openapi.java;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.CommandManager;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.player.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.HttpUtil;
import org.apache.commons.io.IOUtils;
import librarys.org.objectweb.asm.ClassReader;
import librarys.org.objectweb.asm.ClassWriter;
import librarys.org.objectweb.asm.Opcodes;
import librarys.org.objectweb.asm.tree.AbstractInsnNode;
import librarys.org.objectweb.asm.tree.ClassNode;
import librarys.org.objectweb.asm.tree.MethodInsnNode;
import librarys.org.objectweb.asm.tree.VarInsnNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @description: Java插件管理
 * @author: QianXia
 * @create: 2020/10/5 15:54
 **/
public class PluginManager {
    public List<LunePlugin> plugins = new ArrayList<>();
    public Map<URLClassLoader, String> urlCL = new HashMap<>();
    
    public PluginManager(){
        this.loadPlugins(false);
    }

    public void loadPlugins(boolean reload){
        try {
            File luneDir = new File(Minecraft.getMinecraft().mcDataDir, Lune.CLIENT_NAME);
            File pluginDir = new File(luneDir, "Plugins");

            // 检测及创建插件目录
            if(!pluginDir.exists()){
                if(!pluginDir.mkdirs()){
                    System.err.println("Create Plugin Folder Failed!");
                }
            }

            // 列出全部的jar包
            File[] files = pluginDir.listFiles((dir, name) -> name.endsWith(".jar"));

            if(files == null){
                return;
            }

            for (File file : files) {
                urlCL.put(new URLClassLoader(new URL[]{new URL("file:" + file.getPath())}), this.getMain(file));
            }

            // 用Classloader逐个获取实例
            loading: for (int i = 0; i < urlCL.size(); i++) {
                URLClassLoader loader = (URLClassLoader) urlCL.keySet().toArray()[i];
                Class<?> clazz;
                try {
                	String pluginMain = urlCL.get(loader);
                	
                	if(pluginMain == null || Lune.flag < 0) {
                		PlayerUtil.sendMessage("加载插件 " + files[i].getAbsolutePath() + " 失败！原因：没有找到主类！");
                		continue;
                	}
                    clazz = loader.loadClass(pluginMain);
                    LunePlugin instance = (LunePlugin) clazz.newInstance();

                    // 防止多次载入同一款插件
                    if(plugins.contains(instance)){
                        continue;
                    }

                    // 防止载入同一个插件的不同版本
                    for (LunePlugin oldPlugin : plugins) {
                        if (oldPlugin.pluginName.equals(instance.pluginName)) {
                            float oldPluginVersion = oldPlugin.version;
                            float newPluginVersion = instance.version;
                            if(oldPluginVersion >= newPluginVersion){
                                continue loading;
                            }else{
                                plugins.remove(oldPlugin);
                                break;
                            }
                        }
                    }
                    plugins.add(instance);
                }catch (NoClassDefFoundError e){
                    PlayerUtil.sendMessage("Failed to load plugin because of client updated :L");
                    PlayerUtil.sendMessage("I am trying to fix the error...");
                    FixPlugin fixPlugin = new FixPlugin(files, i);
                    fixPlugin.start();
                    // 等到修复插件线程结束再准备重载 防止出现未修复完毕就重载的问题
                    Check checkThread = new Check(fixPlugin);
                    checkThread.start();
                    return;
                }
            }

            // 重载需要重新调用这些函数
            if(reload){
                this.onModuleManagerLoad(Lune.moduleManager, true);
                this.onCommandManagerLoad(Lune.commandManager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMain(File file) {
    	String main = null;
    	try {
    		ZipFile zip = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zip.entries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
		
				if(entry.getName().endsWith(".class")) {
					InputStream input = zip.getInputStream(entry);
					ClassReader cr = new ClassReader(input);
					ClassNode cn = new ClassNode();
					cr.accept(cn, ClassReader.SKIP_FRAMES);
					if("me/superskidder/lune/openapi/java/LunePlugin".equals(cn.superName)) {
						return entry.getName().replaceAll("/", ".").replaceAll(".class", "");
					}
				}
			}
			
			
			zip.close();
		} catch (ZipException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	return main;
    }
    
    public boolean isPluginEnabled(LunePlugin plugin){
        for (Object value : ModuleManager.pluginModsList.values()) {
            if (value instanceof LunePlugin) {
                if (value.equals(plugin)) {
                    return true;
                }
            }
        }

        for (Object value : CommandManager.pluginCommands.values()) {
            if (value instanceof LunePlugin) {
                if (value.equals(plugin)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setPluginState(LunePlugin plugin, boolean state) {
        AtomicReference<Mod> tempMod = new AtomicReference<>();
        AtomicReference<Command> tempCmd = new AtomicReference<>();

        if (state) {
            ModuleManager.disabledPluginList.forEach((mod, value) -> {
                if (value instanceof LunePlugin) {
                    if (value.equals(plugin)) {
                        tempMod.set(mod);
                    }
                }
            });

            ModuleManager.disabledPluginList.remove(tempMod.get());
            ModuleManager.pluginModsList.put(tempMod.get(), plugin);
            ModuleManager.modList.add(tempMod.get());

            CommandManager.disabledPluginCommands.forEach((cmd, value) -> {
                if (value instanceof LunePlugin) {
                    if (value.equals(plugin)) {
                        tempCmd.set(cmd);
                    }
                }
            });

            CommandManager.disabledPluginCommands.remove(tempCmd.get());
            CommandManager.pluginCommands.put(tempCmd.get(), plugin);
            CommandManager.commands.add(tempCmd.get());
        } else {
            ModuleManager.pluginModsList.forEach((mod, value) -> {
                if (value instanceof LunePlugin) {
                    if (value.equals(plugin)) {
                        tempMod.set(mod);
                    }
                }
            });

            ModuleManager.pluginModsList.remove(tempMod.get());
            ModuleManager.modList.remove(tempMod.get());
            ModuleManager.disabledPluginList.put(tempMod.get(), plugin);


            CommandManager.pluginCommands.forEach((cmd, value) -> {
                if (value instanceof LunePlugin) {
                    if (value.equals(plugin)) {
                        tempCmd.set(cmd);
                    }
                }
            });

            CommandManager.pluginCommands.remove(tempCmd.get());
            CommandManager.commands.remove(tempCmd.get());
            CommandManager.disabledPluginCommands.put(tempCmd.get(), plugin);
        }
        Lune.moduleManager.sortModules();
    }

    public void onModuleManagerLoad(ModuleManager modManager, boolean reload){
        for (LunePlugin plugin : plugins) {
            plugin.onModuleManagerLoad(modManager);
        }
        if(reload) {
            Lune.moduleManager.sortModules();
        }
    }

    public void onCommandManagerLoad(CommandManager commandManager){
        for (LunePlugin plugin : plugins) {
            plugin.onCommandManagerLoad(commandManager);
        }
    }

    public void onClientStart(Lune lune){
        for (LunePlugin plugin : plugins) {
            plugin.onClientStart(lune);
        }
    }

    public void onClientStop(Lune lune){
        for (LunePlugin plugin : plugins) {
            plugin.onClientStop(lune);
        }
    }

    public static class Check extends Thread{
        private Thread thread;

        public Check(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            while (thread.isAlive()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Lune.needReload = true;
            this.interrupt();
        }
    }

    public static class FixPlugin extends Thread{
        private File[] files;
        private int index;

        public boolean stop;

        private Map<String, ClassNode> classNodes = new HashMap<>();
        private Map<String, byte[]> shits = new HashMap<>();

        public FixPlugin(File[] files, int num) {
            this.files = files;
            this.index = num;
        }

        @Override
        public void run() {
            if(stop){
                this.interrupt();
            }
            try {
                String mapping = HttpUtil.get(new URL("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/Mapping/ClassMapping"));
                mapping = mapping.replaceAll("\\.", "/");
                Map<String, String> map = new HashMap<>();
                String[] lines = mapping.split("\r");
                for (String line : lines) {
                    String[] str = line.split("->");
                    map.put(str[0], str[1]);
                }

                ZipFile zip = new ZipFile(files[index]);
                Enumeration<? extends ZipEntry> entries = zip.entries();

                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    InputStream inputStream = zip.getInputStream(zipEntry);
                    byte[] data = IOUtils.toByteArray(inputStream);
                    if (zipEntry.getName().endsWith(".class")) {
                        ClassReader cr = new ClassReader(data);
                        ClassNode node = new ClassNode();
                        cr.accept(node, ClassReader.SKIP_FRAMES);
                        this.classNodes.put(zipEntry.getName(), node);
                    }else{
                        this.shits.put(zipEntry.getName(), data);
                    }
                }

                classNodes.values().forEach(node ->{
                    node.methods.forEach(methodNode -> {
                        for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                            if (insnNode instanceof MethodInsnNode) {
                                MethodInsnNode method = (MethodInsnNode) insnNode;
                                map.keySet().forEach(key ->{
                                    if (method.owner.equalsIgnoreCase(key)) {
                                        method.owner = map.get(key);
                                    }
                                    if (node.superName.equalsIgnoreCase(key)) {
                                        node.superName = map.get(key);
                                    }
                                });

                                boolean flag = "me/superskidder/lune/manager/ModuleManager".equals(method.owner) && "addModule".equals(method.name) && "(Lme/superskidder/lune/Modules/Mod;)V".equals(method.desc);
                                if (flag) {
                                    method.name = "addPluginModule";
                                    method.desc = "(Lme/superskidder/lune/Modules/Mod;Ljava/lang/Object;)V";
                                    methodNode.instructions.insertBefore(method, new VarInsnNode(Opcodes.ALOAD, 0));
                                }
                            }
                        }
                    });
                });

                File oldFile = files[index];
                oldFile.renameTo(new File(oldFile.getPath().replace(".jar", ".BACKUP")));

                ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(files[index].getPath()));
                shits.forEach((name, val) -> {
                    ZipEntry entry = new ZipEntry(name);
                    try {
                        zipOut.putNextEntry(entry);
                        zipOut.write(val);
                        zipOut.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(String.format("Error {}", name));
                    }
                });

                classNodes.values().forEach(classNode -> {
                    try {
                        byte[] b = toByteArray(classNode);
                        if (b != null) {
                            zipOut.putNextEntry(new ZipEntry(classNode.name + ".class"));
                            zipOut.write(b);
                            zipOut.closeEntry();
                        }
                    } catch (IOException e) {
                        System.out.println(String.format("Error {}", classNode.name));
                    }
                });
                zipOut.close();
                // Clean Module Manager
                for (Mod mod : ModuleManager.pluginModsList.keySet()) {
                    mod.setStage(false);
                    ModuleManager.modList.remove(mod);
                }
                ModuleManager.pluginModsList.clear();

                // Clean Command Manager
                for(Command cmd : CommandManager.pluginCommands.keySet()){
                    CommandManager.commands.remove(cmd);
                }
                CommandManager.pluginCommands.clear();

                Lune.pluginManager.plugins.clear();
                Lune.pluginManager.urlCL.clear();

                // Reload
                Lune.pluginManager.loadPlugins(true);
                
                zip.close();
            } catch (Exception e) {
                e.printStackTrace();
                PlayerUtil.sendMessage("Sorry... I failed to fix the error.");
                PlayerUtil.sendMessage("Please contact the plugin author to fix the error.");
                files[index].renameTo(new File(files[index].getPath().replace(".jar", ".ERROR")));
                stop = true;
                return;
            }
            PlayerUtil.sendMessage("Fixed successfully!");
        }

        public byte[] toByteArray(ClassNode node) {
            if (node.innerClasses != null) {
                node.innerClasses.stream().filter(in -> in.innerName != null).forEach(in -> {
                    if (in.innerName.indexOf('/') != -1) {
                        in.innerName = in.innerName.substring(in.innerName.lastIndexOf('/') + 1); // Stringer
                    }
                });
            }
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            try {
                node.accept(writer);
            } catch (Throwable e) {
                writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                node.accept(writer);
            }
            return writer.toByteArray();
        }
    }
}
