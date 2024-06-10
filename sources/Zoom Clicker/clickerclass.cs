using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Zoom
{
    class clickerclass
    {
        [DllImport("user32", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall)]
        public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

        public static void leftclick(int count)
        {
            mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
            mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);
        }
        public static void rightclick(int count)
        {
            mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0);
            mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0);
        }
        private const int MOUSEEVENTF_MOVE = 0x0001;
        private const int MOUSEEVENTF_LEFTDOWN = 0x02;
        private const int MOUSEEVENTF_LEFTUP = 0x04;
        private const int MOUSEEVENTF_RIGHTDOWN = 0x08;
        private const int MOUSEEVENTF_RIGHTUP = 0x10;
    }
}
