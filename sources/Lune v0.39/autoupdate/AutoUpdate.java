package me.superskidder.lune.autoupdate;

import me.superskidder.lune.Lune;
import net.minecraft.util.HttpUtil;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author: QianXia &　ＳｕｐｅｒＳｋｉｄｄｅｒ
 * @description: 自动更新(buG)
 * @create: 2020/12/26-17:38
 */
public class AutoUpdate {
    public static boolean needOpenUpdateMenu = false;
    public static float webVer;

    public void checkUpdate(){
        try {
            String webVersion = HttpUtil.get(new URL("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/VERSION"));
            webVer = Float.parseFloat(webVersion);
            float clientVer = Float.parseFloat(Lune.CLIENT_Ver);

            if (webVer > clientVer) {
                int flag = JOptionPane.showConfirmDialog(null, "有可更新的版本，是否更新？", "更新：", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                switch (flag) {
                    case 0:
                        new UpdateFile().download("http://azlipsclient.top/files/Lune%200.28%20Beta%20-%204.zip");


                        FileOutputStream fos = null;
                        File file;
                        String mycontent = "需要vbs数据";

                        // 指定写入文件的路径

                        file = new File("C:/LuneHelper.vbs");

                        try {
                            fos = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        /* 先检测文件是否存在，如果不存在则先创建*/

                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        byte[] bytesArray = mycontent.getBytes();
                        fos.write(bytesArray);

                        fos.flush();
                        System.exit(0000);
                        break;
                    case 1:

                        break;
                }
                needOpenUpdateMenu = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startUpdate(String usernameEncoded){
        String downloadURL = "http://azlipsclient.top/verify/update/" + usernameEncoded + ".zip";
        String downloadPath = System.getProperty("java.io.tmpdir");
        String finalPath = downloadPath + System.lineSeparator() + usernameEncoded;

        // Download Update File
        try {
            HttpUtil.downloadFromUrl(downloadURL, usernameEncoded, downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String unzipPath = finalPath + File.separator + "i1111i11ii1l";

        // Unzip update file to use patches.
        AutoUpdate.unzip(finalPath, unzipPath);

        try {
            URLClassLoader loader = new URLClassLoader(new URL[]{new URL("file:" + unzipPath + File.separator + "Main.jar")});
            Class<?> clazz = loader.loadClass("lune.PatchMain");
            clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unzip(String zipFile,String outputPath){
        if(outputPath == null)
            outputPath = "";
        else
            outputPath += File.separator;

        File outputDirectory = new File(outputPath);

        if(outputDirectory.exists())
            outputDirectory.delete();

        outputDirectory.mkdir();

        try {
            ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry entry = null;
            int len;
            byte[] buffer = new byte[1024];

            while((entry = zip.getNextEntry()) != null){

                if(!entry.isDirectory()){
                    System.out.println("-"+entry.getName());

                    // create a new file
                    File file = new File(outputPath +entry.getName());

                    // create file parent directory if does not exist
                    if(!new File(file.getParent()).exists())
                        new File(file.getParent()).mkdirs();

                    // get new file output stream
                    FileOutputStream fos = new FileOutputStream(file);

                    // copy bytes
                    while ((len = zip.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }

                    fos.close();
                }

            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
