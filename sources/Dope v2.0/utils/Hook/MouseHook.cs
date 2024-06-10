using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using static Dope.utils.Imports;

namespace Dope.utils.Hook
{
    class MouseHook
    {
        private HookHandler hookHandler;

        #region Events
        public event MouseHookCallback LeftButtonDown;
        public event MouseHookCallback LeftButtonUp;
        public event MouseHookCallback RightButtonDown;
        public event MouseHookCallback RightButtonUp;
        public event MouseHookCallback MouseMove;
        public event MouseHookCallback MouseWheel;
        public event MouseHookCallback DoubleClick;
        public event MouseHookCallback MiddleButtonDown;
        public event MouseHookCallback MiddleButtonUp;
        #endregion

        /// <summary>
        /// Low level mouse hook's ID
        /// </summary>
        private IntPtr hookID = IntPtr.Zero;

        /// <summary>
        /// Install low level mouse hook
        /// </summary>
        /// <param name="mouseHookCallbackFunc">Callback function</param>
        public void Install()
        {
            hookHandler = HookFunc;
            hookID = SetHook(hookHandler);
        }

        /// <summary>
        /// Remove low level mouse hook
        /// </summary>
        public void Uninstall()
        {
            if (hookID == IntPtr.Zero)
                return;

            UnhookWindowsHookEx(hookID);
            hookID = IntPtr.Zero;
        }

        /// <summary>
        /// Destructor. Unhook current hook
        /// </summary>
        ~MouseHook()
        {
            Uninstall();
        }

        /// <summary>
        /// Sets hook and assigns its ID for tracking
        /// </summary>
        /// <param name="proc">Internal callback function</param>
        /// <returns>Hook ID</returns>
        private IntPtr SetHook(HookHandler proc)
        {
            using (ProcessModule module = Process.GetCurrentProcess().MainModule)
                return SetWindowsHookEx(WH_MOUSE_LL, proc, GetModuleHandle(module.ModuleName), 0);
        }

        /// <summary>
        /// Callback function
        /// </summary>
        private IntPtr HookFunc(int nCode, IntPtr wParam, IntPtr lParam)
        {
            // parse system messages
            if (nCode >= 0)
            {
                if (MouseMessages.WM_LBUTTONDOWN == (MouseMessages)wParam)
                    if (LeftButtonDown != null)
                        LeftButtonDown((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
                if (MouseMessages.WM_LBUTTONUP == (MouseMessages)wParam)
                    if (LeftButtonUp != null)
                        LeftButtonUp((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
                if (MouseMessages.WM_RBUTTONDOWN == (MouseMessages)wParam)
                    if (RightButtonDown != null)
                        RightButtonDown((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
                if (MouseMessages.WM_RBUTTONUP == (MouseMessages)wParam)
                    if (RightButtonUp != null)
                        RightButtonUp((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
                if (MouseMessages.WM_MOUSEMOVE == (MouseMessages)wParam)
                    if (MouseMove != null)
                        MouseMove((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
                if (MouseMessages.WM_MOUSEWHEEL == (MouseMessages)wParam)
                    if (MouseWheel != null)
                        MouseWheel((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
                if (MouseMessages.WM_LBUTTONDBLCLK == (MouseMessages)wParam)
                    if (DoubleClick != null)
                        DoubleClick((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
                if (MouseMessages.WM_MBUTTONDOWN == (MouseMessages)wParam)
                    if (MiddleButtonDown != null)
                        MiddleButtonDown((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
                if (MouseMessages.WM_MBUTTONUP == (MouseMessages)wParam)
                    if (MiddleButtonUp != null)
                        MiddleButtonUp((MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MSLLHOOKSTRUCT)));
            }
            return CallNextHookEx(hookID, nCode, wParam, lParam);
        }
    }
}
