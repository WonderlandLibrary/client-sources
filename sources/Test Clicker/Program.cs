using System;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;

namespace test_clicker
{
    class Program
    {
        [DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
        private static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll")]
        static extern short GetAsyncKeyState(Keys vKey);

        [DllImport("user32.dll", SetLastError = true, CharSet = CharSet.Auto)]
        static extern bool PostMessage(IntPtr hwnd, uint Msg, IntPtr wParam, IntPtr lParam);

        static void Main(string[] args)
        {
            Console.WriteLine("clicker");
            while (true)
            {
                if (GetAsyncKeyState(Keys.LButton) < 0)
                {
                    @function(MouseButtons.Left);
                }
            }
        }

        public static void @function(MouseButtons button)
        {
            IntPtr hWnd = GetForegroundWindow();
            const uint WM_LBUTTONDOWN = 0x0201;
            const uint WM_LBUTTONUP = 0x0202;
            int dword = MakeLParam(Cursor.Position.X, Cursor.Position.Y);

            PostMessage(hWnd, WM_LBUTTONDOWN, (IntPtr)5, (IntPtr)5);
            Thread.Sleep(new Random().Next(1, 5));
            PostMessage(hWnd, WM_LBUTTONUP, (IntPtr)MakeWParam(Cursor.Position.X, Cursor.Position.Y), (IntPtr)MakeLParam(Cursor.Position.X, Cursor.Position.Y));
        }
        public static int MakeLParam(int LoWord, int HiWord)
        {
            return (int)((HiWord << 16) | (LoWord & 0xFFFF));
        }
        public static int MakeWParam(int LoWord, int HiWord)
        {
            return (int)((HiWord << 16) | (LoWord & 0xFFFF));
        }
    }
}
