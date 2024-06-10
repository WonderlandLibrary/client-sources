using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using GlobalLowLevelHooks;
namespace daidesign
{
    class clicker_settings
    {
        public static int LeftMin = 9;
        public static int LeftMax = 10;
        public static KeyboardHook.VKeys LeftBind = KeyboardHook.VKeys.ACCEPT;
        public static int RightMin = 9;
        public static int RightMax = 10;
        public static KeyboardHook.VKeys RightBind = KeyboardHook.VKeys.ACCEPT;
        public static bool LeftJitter = false;
        public static bool RightJitter = false;

        public static int speed = 1;

        public static int RodDelay = 10;
        public static int RodSwordSlot = 1;
        public static int RodSlot = 2;
        public static KeyboardHook.VKeys RodBind = KeyboardHook.VKeys.ACCEPT;
        public static int WebDelay = 10;
        public static int WebSwordSlot = 1;
        public static int WebSlot = 2;
        public static KeyboardHook.VKeys WebBind = KeyboardHook.VKeys.ACCEPT;

    }
}
