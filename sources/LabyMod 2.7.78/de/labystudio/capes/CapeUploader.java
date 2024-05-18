package de.labystudio.capes;

import de.labystudio.chat.ClientConnection;
import de.labystudio.chat.EnumAlertType;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import de.labystudio.utils.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CapeUploader extends Thread
{
    public static boolean upload;
    public static boolean openUpload = false;
    CapeCallback callBack;
    private final String CrLf = "\r\n";

    public CapeUploader(CapeCallback callBack)
    {
        this.callBack = callBack;
    }

    public void run()
    {
        upload = false;
        openUpload = true;
        HttpURLConnection httpurlconnection = null;
        OutputStream outputstream = null;
        InputStream inputstream = null;
        File file1 = this.selectCape();

        if (file1 != null && file1.exists())
        {
            try
            {
                openUpload = false;
                upload = true;
                System.out.println("[LabyMod] Uploading cape " + file1.getName());
                StringBuilder stringbuilder = (new StringBuilder()).append(Source.url_changeCape).append("?username=").append(LabyMod.getInstance().getPlayerName()).append("&capeKey=");
                LabyMod.getInstance().getClient().getClientConnection();
                URL url = new URL(stringbuilder.append(ClientConnection.getCapeKey()).toString());
                httpurlconnection = (HttpURLConnection)url.openConnection();
                httpurlconnection.setDoOutput(true);
                String s = "";
                InputStream inputstream1 = new FileInputStream(file1);
                byte[] abyte = new byte[inputstream1.available()];
                inputstream1.read(abyte);
                inputstream1.close();
                String s1 = "";
                s1 = s1 + "-----------------------------4664151417711\r\n";
                s1 = s1 + "Content-Disposition: form-data; name=\"file\"; filename=\"" + file1.getName() + "\"" + "\r\n";
                s1 = s1 + "Content-Type: image/png\r\n";
                s1 = s1 + "\r\n";
                String s2 = "";
                s2 = s2 + "\r\n-----------------------------4664151417711--\r\n";
                httpurlconnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------4664151417711");
                httpurlconnection.setRequestProperty("Content-Length", String.valueOf(s1.length() + s2.length() + abyte.length));
                outputstream = httpurlconnection.getOutputStream();
                outputstream.write(s1.getBytes());
                int i = 0;
                int j = 1024;

                while (true)
                {
                    if (i + j > abyte.length)
                    {
                        j = abyte.length - i;
                    }

                    outputstream.write(abyte, i, j);
                    i += j;

                    if (i >= abyte.length)
                    {
                        break;
                    }
                }

                outputstream.write(s2.getBytes());
                outputstream.flush();
                inputstream = httpurlconnection.getInputStream();
                byte[] abyte1 = new byte[1024];
                int k = 0;
                String s3 = "";

                while ((k = inputstream.read(abyte1)) > 0)
                {
                    if (k > 0)
                    {
                        s3 = s3 + new String(abyte1, 0, k);
                        System.out.println("[LabyMod] Output: " + s3);
                    }
                }

                System.out.println(s3);

                if (s3.equalsIgnoreCase("OK"))
                {
                    LabyMod.getInstance().getCapeManager().refresh();
                    this.callBack.done();
                }
                else
                {
                    this.callBack.failed(s3);
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                LabyMod.getInstance().achievementGui.displayBroadcast("CapeManager", Color.cl("c") + "Error: " + exception.getMessage(), EnumAlertType.LABYMOD);
                this.callBack.failed(exception.getMessage());
            }
            finally
            {
                try
                {
                    outputstream.close();
                }
                catch (Exception var27)
                {
                    ;
                }

                try
                {
                    inputstream.close();
                }
                catch (Exception var26)
                {
                    ;
                }
            }

            upload = false;
            openUpload = false;
        }
        else
        {
            openUpload = false;
        }
    }

    private File selectCape()
    {
        JFileChooser jfilechooser = new JFileChooser();
        FileNameExtensionFilter filenameextensionfilter = new FileNameExtensionFilter("PNG image files", new String[] {"png"});
        jfilechooser.setFileFilter(filenameextensionfilter);
        jfilechooser.setMultiSelectionEnabled(false);
        jfilechooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        jfilechooser.setDialogTitle("Select your cape");
        JFrame jframe = new JFrame();
        jfilechooser.showOpenDialog(jframe.getParent());
        return jfilechooser.getSelectedFile();
    }
}
