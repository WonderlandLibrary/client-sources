using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace Cooper.Modules
{
    public static class CheckInv
    {
        [StructLayout(LayoutKind.Sequential)]
        public struct PointStruct
        {
            public int x;
            public int y;
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct CursorInfoStruct
        {
            public int cbSize;
            public int flags;
            public IntPtr hCursor;
            public PointStruct pt;
        }

        [DllImport("user32.dll")]
        public static extern bool GetCursorInfo(ref CursorInfoStruct pci);
        [DllImport("user32.dll")]
        public static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll")]
        public static extern IntPtr FindWindow(string lpWindowClass, string lpWindowCaption);

        public static bool InMenu()
        {
            if (IsCursorVisible()) return true;
            else return false;
        }

        public static bool IsCursorVisible()
        {
            CursorInfoStruct pci = new CursorInfoStruct { cbSize = Marshal.SizeOf(typeof(CursorInfoStruct)) };

            GetCursorInfo(ref pci);
            if (pci.hCursor.ToInt32() > 100000)
                return false;

            return true;
        }
    }
}