package de.labystudio.utils;

import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.minecraft.util.Session;

public class SkinChanger extends Thread
{
    private SkinChanger.MessageCallBack callBack;
    private Session session;
    private boolean steveModel;

    public SkinChanger(Session session, boolean steveModel, SkinChanger.MessageCallBack callback)
    {
        this.callBack = callback;
        this.session = session;
        this.steveModel = steveModel;
    }

    public void run()
    {
        File file1 = this.selectTexture();

        if (file1 != null && file1.exists())
        {
            this.upload(file1);
        }
        else
        {
            this.callBack.ok("File not found");
        }
    }

    private File selectTexture()
    {
        JFileChooser jfilechooser = new JFileChooser();
        FileNameExtensionFilter filenameextensionfilter = new FileNameExtensionFilter("PNG image files", new String[] {"png"});
        jfilechooser.setFileFilter(filenameextensionfilter);
        jfilechooser.setMultiSelectionEnabled(false);
        jfilechooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        jfilechooser.setDialogTitle("Select your skin");
        JFrame jframe = new JFrame();
        jfilechooser.showOpenDialog(jframe.getParent());
        jfilechooser.requestFocus();
        jfilechooser.requestFocusInWindow();
        jfilechooser.setVisible(true);
        return jfilechooser.getSelectedFile();
    }

    private boolean upload(File file)
    {
        try
        {
            String s = "https://api.mojang.com/user/profile/" + this.session.getProfile().getId().toString().replace("-", "") + "/skin";
            MultipartUtility multipartutility = new MultipartUtility(s, "UTF-8");
            multipartutility.addRequestProperty("Authorization", "Bearer " + this.session.getToken());
            multipartutility.open();
            multipartutility.addFormField("model", this.steveModel ? "classic" : "slim");
            multipartutility.addFilePart("file", file);
            List<String> list = multipartutility.finish();
            String s1 = "";

            for (String s2 : list)
            {
                s1 = s1 + s2;
            }

            this.callBack.ok(s1);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            this.callBack.ok("Error");
            return false;
        }
    }

    public interface MessageCallBack
    {
        void ok(String var1);
    }
}
