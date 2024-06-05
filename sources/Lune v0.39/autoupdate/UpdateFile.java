package me.superskidder.lune.autoupdate;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * @author 熊立伟
 * @version 1.0
 * @date 2020/3/4 22:10
 */
public class UpdateFile {
    InputStream in = null;
    static File file = null;
    DataOutputStream dos = null;
    URLConnection con = null;
    String fileName = "luneupdatefile.jar";
    URL url = null;
    // 连接


    public void connection(String source) {
        try {
            url = new URL(source);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            con = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 创建本地文件
    public void createFile() {
        file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //将从网络获取的文件的写入到本地
    private void writeResult() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        dos = new DataOutputStream(bos);
        try {
            in = con.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedInputStream bis = new BufferedInputStream(in);

        DataInputStream dis = new DataInputStream(bis);
        try {
            while (true) {
                dos.write(dis.readByte());
            }
        } catch (EOFException e) {
            System.out.println("下载完成,文件名为" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void download(String source) {
        UpdateFile download = new UpdateFile();
        download.connection(source);
        download.createFile();
        download.writeResult();
    }
}