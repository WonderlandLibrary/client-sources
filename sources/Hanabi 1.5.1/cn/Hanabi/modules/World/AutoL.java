package cn.Hanabi.modules.World;

import cn.Hanabi.value.*;
import net.minecraft.entity.player.*;
import ClassSub.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import java.util.*;

public class AutoL extends Mod
{
    public static Class205 LTimer;
    public static Value<Boolean> ad;
    public static Value<Boolean> wdr;
    public static Value<Boolean> abuse;
    public static List<String> wdred;
    public static List<EntityPlayer> power;
    static Class343 baizhijun;
    static Class343 fanyangxiao;
    static Class343 lilejie;
    
    
    public AutoL() {
        super("AutoL", Category.WORLD);
    }
    
    @EventTarget
    public void onChat(final EventChat eventChat) {
    }
    
    public static String getSB() {
        final int nextInt = new Random().nextInt(3);
        if (nextInt == 0) {
            return AutoL.baizhijun.getSB();
        }
        if (nextInt == 1) {
            return AutoL.fanyangxiao.getSB();
        }
        if (nextInt == 2) {
            return AutoL.lilejie.getSB();
        }
        return AutoL.baizhijun.getSB();
    }
    
    static {
        AutoL.LTimer = new Class205();
        AutoL.ad = new Value<Boolean>("AutoL_AD", true);
        AutoL.wdr = new Value<Boolean>("AutoL_WatchdogReport", true);
        AutoL.abuse = new Value<Boolean>("AutoL_Abuse", false);
        AutoL.wdred = new ArrayList<String>();
        AutoL.power = new ArrayList<EntityPlayer>();
        AutoL.baizhijun = new Class343("白治�?", "15397699681", "陈桂�?", "四川广安");
        AutoL.fanyangxiao = new Class343("范杨�?", "18361960482", "没有妈妈", "江苏苏州");
        AutoL.lilejie = new Class343("李乐�?", "13546806422", "陈少�?", "广东汕头");
    }
}
