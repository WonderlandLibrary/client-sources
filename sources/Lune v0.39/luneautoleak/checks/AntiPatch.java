package me.superskidder.lune.luneautoleak.checks;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Random;

import me.superskidder.lune.Lune;
import me.superskidder.lune.luneautoleak.LuneAutoLeak;

/**
 * @description 反补丁
 * @author LuneClientTeam
 */
public class AntiPatch {
    //反补丁
    private static String VM = "";
    public static boolean patched = false;

    public AntiPatch() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        for (String s:arguments){
            if(contains_(s,"Xbootclasspath")){
                patched = true;
            	Lune.flag = -new Random().nextInt(555);
            }
        }
        if(!"Xboot".contains("X") || !"Xboo".contains("b") || !"Classpath".contains("h") || !((String[])ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";"))[0].contains(File.separator + "lib" + File.separator) || ((String[])ManagementFactory.getRuntimeMXBean().getBootClassPath().split(";"))[0].replace("l", "I").contains(File.separator + "lib" + File.separator)) {
        	patched = true;
        	Lune.flag = -new Random().nextInt(555);
        }

        if(patched) {
           	Lune.flag = -new Random().nextInt(555);
        }
    }

    public boolean contains_(String s,String t){
        char[] array1 = s.toCharArray();
        char[] array2 = t.toCharArray();
        boolean status = false;

        if(array2.length < array1.length) {
            for(int i=0; i<array1.length; i++){
                if (array1[i] == array2[0] && i+array2.length-1<array1.length) {
                    int j = 0;
                    while(j<array2.length)
                    {
                        if (array1[i+j] == array2[j])
                        {
                            j++;
                        }
                        else
                            break;
                    }
                    if (j==array2.length)
                    {
                        status = true;
                        break;
                    }
                }

            }
        }
        if(!Lune.INSTANCE.luneAutoLeak.didVerify.contains(0)) {
            Lune.INSTANCE.luneAutoLeak.didVerify.add(0);
        }
        return status;
    }
}
