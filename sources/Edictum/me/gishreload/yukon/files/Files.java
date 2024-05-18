package me.gishreload.yukon.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class Files {

	public static boolean binds = true;
	public static List bind = new ArrayList();
	
	public static void Binds(){
        if (binds){
            try
            {
                File exception = new File(Minecraft.getMinecraft().mcDataDir, "EdictumBinds.txt");
                BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(exception));
                for (int i = 0; i < bind.size(); ++i){
                    bufferedwriter.write((String)bind.get(i) + "\r\n");
                }
                bufferedwriter.flush();
                bufferedwriter.close();
            }
            catch (Exception var3){
                System.err.print(var3.toString());
            }
        }
    }
}
