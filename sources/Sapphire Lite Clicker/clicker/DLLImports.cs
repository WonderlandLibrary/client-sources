using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Sapphire_LITE.clicker {
    public class DLLImports {
        [DllImport("user32.dll")] public static extern IntPtr GetForegroundWindow();
        [DllImport("user32.dll")] public static extern IntPtr FindWindow(string lpClassName, string lpWindowName);
        [DllImport("user32.dll")] public static extern bool PostMessage(IntPtr hWnd, UInt32 Msg, int wParam, int lParam);
        [DllImport("user32.dll")] public static extern short GetKeyState(int keyCode);
        [DllImport("NTDLL.DLL", SetLastError = true)] public static extern uint NtSetTimerResolution(uint DesiredResolution, bool SetResolution, out uint CurrentResolution);
    }
}
