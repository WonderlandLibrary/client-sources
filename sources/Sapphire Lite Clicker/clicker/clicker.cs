using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Security.Policy;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Sapphire_LITE.clicker {
    public class clicker {

        #region Misc

        // mouse events
        const int DOWN = 0x0201, UP = 0x0202;
        const int RIGHT_DOWN = 0x0204, RIGHT_UP = 0x0205;

        public static Random r = new Random();

        #endregion

        #region Variables

        // conditions
        public static bool always_on = false, shift_disable = false, smart_mode = false;

        // other
        public static bool left_enabled = false, right_enabled = false, first_click = false;
        public static int left_cps = 12, right_cps = 12, randomization_distribution = 25;

        public static IntPtr minecraft_process = IntPtr.Zero;

        public static Stopwatch sw = new Stopwatch();

        #endregion

        #region Clicker functionality

        public static void clickerThread() {
            while (true) {
                Thread.Sleep(1);

                // find LWJGL (Java Edition) window, you can also just make an array of window class names and sort through those
                minecraft_process = DLLImports.FindWindow("LWJGL", null);

                // check if user is tabbed into minecraft
                if (minecraft_process.ToString() != DLLImports.GetForegroundWindow().ToString()) continue;

                // disable when cursor is detected but still allow quick refilling
                if (smart_mode & IsCursorVisisble() && !KeyListener.isKeyPressed(Keys.LShiftKey)) continue;

                // shift disable condition
                if (shift_disable && KeyListener.isKeyPressed(Keys.LShiftKey)) continue;

                // always on condition
                if (always_on) {
                    if (left_enabled) sendClick(left_cps, DOWN, UP);
                    if (right_enabled) sendClick(right_cps, RIGHT_DOWN, RIGHT_UP);
                }

                // send clicks separately if the dedicated key is held to have RMB lock by default
                if (KeyListener.isKeyPressed(Keys.LButton) && left_enabled) sendClick(left_cps, DOWN, UP);
                if (KeyListener.isKeyPressed(Keys.RButton) && right_enabled) sendClick(right_cps, RIGHT_DOWN, RIGHT_UP);
            }
        }

        public static void sendClick(int cps, uint type1, uint type2) {
            Thread.Sleep(rand(cps));
            DLLImports.PostMessage(minecraft_process, type1, 0, 0);
            Thread.Sleep(rand(cps));
            DLLImports.PostMessage(minecraft_process, type2, 0, 0);
        }

        #endregion
         
        #region Randomization

        public static bool randomize = true;

        public static int rand(int cps) {
            // micro adjustments to the ms value generated based on the selected cps
            int numerator = randomize ? r.Next(475 - randomization_distribution, 475 + randomization_distribution) : 520;

            // toggle for randomization
            int deviation = randomize ? deviation = 3 : deviation = 0;

            // outliers
            if (r.Next(100) < 3 && randomize) return r.Next(100, 150);

            return r.Next(100) < 5 ? (numerator / cps) : (numerator / r.Next(cps - deviation, cps + deviation));
        }

        #endregion

        #region Cursor visibility detection

        // this is all experimental, may not work on all clients or windows versions.

        [DllImport("user32.dll")]
        private static extern bool GetCursorInfo(out CURSORINFO pci);

        [StructLayout(LayoutKind.Sequential)]
        struct POINT {
            public Int32 x;
            public Int32 y;
        }

        [StructLayout(LayoutKind.Sequential)]
        struct CURSORINFO {
            public Int32 cbSize;
            public Int32 flags;
            public IntPtr hCursor;
            public POINT ptScreenPos;
        }

        static CURSORINFO GetCinfo() {
            CURSORINFO cinfo;
            cinfo.cbSize = Marshal.SizeOf(typeof(CURSORINFO));
            GetCursorInfo(out cinfo);
            return cinfo;
        }

        public static bool IsCursorVisisble() {
            CURSORINFO cinfo = GetCinfo();
            int cursorVal = Convert.ToInt32(cinfo.hCursor.ToInt64());

            if (Math.Abs(cursorVal) > 70000) return false;
            if (Math.Abs(cursorVal) < 70000) return true;
            return true;
        }

        #endregion
    }
}
