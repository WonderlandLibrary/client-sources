using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace Lavy
{
    class MouseHook
    {

        private IntPtr hookID = IntPtr.Zero;
        private MouseHook.MouseHookHandler hookHandler;
        private const int WH_MOUSE_LL = 14;

        public event MouseHook.MouseHookCallback LeftButtonDown;

        public event MouseHook.MouseHookCallback LeftButtonUp;

        public event MouseHook.MouseHookCallback RightButtonDown;

        public event MouseHook.MouseHookCallback RightButtonUp;

        public event MouseHook.MouseHookCallback MouseMove;

        public event MouseHook.MouseHookCallback MouseWheel;

        public event MouseHook.MouseHookCallback DoubleClick;

        public event MouseHook.MouseHookCallback MiddleButtonDown;

        public event MouseHook.MouseHookCallback MiddleButtonUp;

        public void Install()
        {
            this.hookHandler = new MouseHook.MouseHookHandler(this.HookFunc);
            this.hookID = this.SetHook(this.hookHandler);
        }

        public void Uninstall()
        {
            if (this.hookID == IntPtr.Zero)
                return;
            MouseHook.UnhookWindowsHookEx(this.hookID);
            this.hookID = IntPtr.Zero;
        }

        ~MouseHook()
        {
            this.Uninstall();
        }

        private IntPtr SetHook(MouseHook.MouseHookHandler proc)
        {
            using (ProcessModule mainModule = Process.GetCurrentProcess().MainModule)
                return MouseHook.SetWindowsHookEx(14, proc, MouseHook.GetModuleHandle(mainModule.ModuleName), 0U);
        }

        private IntPtr HookFunc(int nCode, IntPtr wParam, IntPtr lParam)
        {
            if (nCode >= 0)
            {
                // ISSUE: reference to a compiler-generated field
                if (513 == (int)wParam && this.LeftButtonDown != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.LeftButtonDown((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
                // ISSUE: reference to a compiler-generated field
                if (514 == (int)wParam && this.LeftButtonUp != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.LeftButtonUp((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
                // ISSUE: reference to a compiler-generated field
                if (516 == (int)wParam && this.RightButtonDown != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.RightButtonDown((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
                // ISSUE: reference to a compiler-generated field
                if (517 == (int)wParam && this.RightButtonUp != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.RightButtonUp((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
                // ISSUE: reference to a compiler-generated field
                if (512 == (int)wParam && this.MouseMove != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.MouseMove((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
                // ISSUE: reference to a compiler-generated field
                if (522 == (int)wParam && this.MouseWheel != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.MouseWheel((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
                // ISSUE: reference to a compiler-generated field
                if (515 == (int)wParam && this.DoubleClick != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.DoubleClick((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
                // ISSUE: reference to a compiler-generated field
                if (519 == (int)wParam && this.MiddleButtonDown != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.MiddleButtonDown((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
                // ISSUE: reference to a compiler-generated field
                if (520 == (int)wParam && this.MiddleButtonUp != null)
                {
                    // ISSUE: reference to a compiler-generated field
                    this.MiddleButtonUp((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
                }
            }
            return MouseHook.CallNextHookEx(this.hookID, nCode, wParam, lParam);
        }

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr SetWindowsHookEx(int idHook, MouseHook.MouseHookHandler lpfn, IntPtr hMod, uint dwThreadId);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool UnhookWindowsHookEx(IntPtr hhk);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

        [DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr GetModuleHandle(string lpModuleName);

        private delegate IntPtr MouseHookHandler(int nCode, IntPtr wParam, IntPtr lParam);

        public delegate void MouseHookCallback(MouseHook.MSLLHOOKSTRUCT mouseStruct);

        private enum MouseMessages
        {
            
            WM_MOUSEMOVE = 512, // 0x00000200
            WM_LBUTTONDOWN = 513, // 0x00000201
            WM_LBUTTONUP = 514, // 0x00000202
            WM_LBUTTONDBLCLK = 515, // 0x00000203
            WM_RBUTTONDOWN = 516, // 0x00000204
            WM_RBUTTONUP = 517, // 0x00000205
            WM_MBUTTONDOWN = 519, // 0x00000207
            WM_MBUTTONUP = 520, // 0x00000208
            WM_MOUSEWHEEL = 522, // 0x0000020A
        }

        public struct POINT
        {
            public int x;
            public int y;
        }

        public struct MSLLHOOKSTRUCT
        {
            public MouseHook.POINT pt;
            public uint mouseData;
            public uint flags;
            public uint time;
            public IntPtr dwExtraInfo;
        }

    }
}
