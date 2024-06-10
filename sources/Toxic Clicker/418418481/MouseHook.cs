using System;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace ac.ac
{
	// Token: 0x02000008 RID: 8
	internal class MouseHook
	{
		// Token: 0x14000003 RID: 3
		// (add) Token: 0x06000025 RID: 37 RVA: 0x000029AC File Offset: 0x00000BAC
		// (remove) Token: 0x06000026 RID: 38 RVA: 0x000029E4 File Offset: 0x00000BE4
		public event MouseHook.MouseHookCallback LeftButtonDown;

		// Token: 0x14000004 RID: 4
		// (add) Token: 0x06000027 RID: 39 RVA: 0x00002A1C File Offset: 0x00000C1C
		// (remove) Token: 0x06000028 RID: 40 RVA: 0x00002A54 File Offset: 0x00000C54
		public event MouseHook.MouseHookCallback LeftButtonUp;

		// Token: 0x14000005 RID: 5
		// (add) Token: 0x06000029 RID: 41 RVA: 0x00002A8C File Offset: 0x00000C8C
		// (remove) Token: 0x0600002A RID: 42 RVA: 0x00002AC4 File Offset: 0x00000CC4
		public event MouseHook.MouseHookCallback RightButtonDown;

		// Token: 0x14000006 RID: 6
		// (add) Token: 0x0600002B RID: 43 RVA: 0x00002AFC File Offset: 0x00000CFC
		// (remove) Token: 0x0600002C RID: 44 RVA: 0x00002B34 File Offset: 0x00000D34
		public event MouseHook.MouseHookCallback RightButtonUp;

		// Token: 0x14000007 RID: 7
		// (add) Token: 0x0600002D RID: 45 RVA: 0x00002B6C File Offset: 0x00000D6C
		// (remove) Token: 0x0600002E RID: 46 RVA: 0x00002BA4 File Offset: 0x00000DA4
		public event MouseHook.MouseHookCallback MouseMove;

		// Token: 0x14000008 RID: 8
		// (add) Token: 0x0600002F RID: 47 RVA: 0x00002BDC File Offset: 0x00000DDC
		// (remove) Token: 0x06000030 RID: 48 RVA: 0x00002C14 File Offset: 0x00000E14
		public event MouseHook.MouseHookCallback MouseWheel;

		// Token: 0x14000009 RID: 9
		// (add) Token: 0x06000031 RID: 49 RVA: 0x00002C4C File Offset: 0x00000E4C
		// (remove) Token: 0x06000032 RID: 50 RVA: 0x00002C84 File Offset: 0x00000E84
		public event MouseHook.MouseHookCallback DoubleClick;

		// Token: 0x1400000A RID: 10
		// (add) Token: 0x06000033 RID: 51 RVA: 0x00002CBC File Offset: 0x00000EBC
		// (remove) Token: 0x06000034 RID: 52 RVA: 0x00002CF4 File Offset: 0x00000EF4
		public event MouseHook.MouseHookCallback MiddleButtonDown;

		// Token: 0x1400000B RID: 11
		// (add) Token: 0x06000035 RID: 53 RVA: 0x00002D2C File Offset: 0x00000F2C
		// (remove) Token: 0x06000036 RID: 54 RVA: 0x00002D64 File Offset: 0x00000F64
		public event MouseHook.MouseHookCallback MiddleButtonUp;

		// Token: 0x06000037 RID: 55 RVA: 0x00002D99 File Offset: 0x00000F99
		public void Install()
		{
			this.hookHandler = new MouseHook.MouseHookHandler(this.HookFunc);
			this.hookID = this.SetHook(this.hookHandler);
		}

		// Token: 0x06000038 RID: 56 RVA: 0x00002DBF File Offset: 0x00000FBF
		public void Uninstall()
		{
			if (this.hookID == IntPtr.Zero)
			{
				return;
			}
			MouseHook.UnhookWindowsHookEx(this.hookID);
			this.hookID = IntPtr.Zero;
		}

		// Token: 0x06000039 RID: 57 RVA: 0x00002DEC File Offset: 0x00000FEC
		~MouseHook()
		{
			this.Uninstall();
		}

		// Token: 0x0600003A RID: 58 RVA: 0x00002E18 File Offset: 0x00001018
		private IntPtr SetHook(MouseHook.MouseHookHandler proc)
		{
			IntPtr result;
			using (ProcessModule mainModule = Process.GetCurrentProcess().MainModule)
			{
				result = MouseHook.SetWindowsHookEx(14, proc, MouseHook.GetModuleHandle(mainModule.ModuleName), 0u);
			}
			return result;
		}

		// Token: 0x0600003B RID: 59 RVA: 0x00002E64 File Offset: 0x00001064
		private IntPtr HookFunc(int nCode, IntPtr wParam, IntPtr lParam)
		{
			if (nCode >= 0)
			{
				if (513 == (int)wParam && this.LeftButtonDown != null)
				{
					this.LeftButtonDown((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
				if (514 == (int)wParam && this.LeftButtonUp != null)
				{
					this.LeftButtonUp((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
				if (516 == (int)wParam && this.RightButtonDown != null)
				{
					this.RightButtonDown((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
				if (517 == (int)wParam && this.RightButtonUp != null)
				{
					this.RightButtonUp((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
				if (512 == (int)wParam && this.MouseMove != null)
				{
					this.MouseMove((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
				if (522 == (int)wParam && this.MouseWheel != null)
				{
					this.MouseWheel((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
				if (515 == (int)wParam && this.DoubleClick != null)
				{
					this.DoubleClick((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
				if (519 == (int)wParam && this.MiddleButtonDown != null)
				{
					this.MiddleButtonDown((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
				if (520 == (int)wParam && this.MiddleButtonUp != null)
				{
					this.MiddleButtonUp((MouseHook.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(MouseHook.MSLLHOOKSTRUCT)));
				}
			}
			return MouseHook.CallNextHookEx(this.hookID, nCode, wParam, lParam);
		}

		// Token: 0x0600003C RID: 60
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr SetWindowsHookEx(int idHook, MouseHook.MouseHookHandler lpfn, IntPtr hMod, uint dwThreadId);

		// Token: 0x0600003D RID: 61
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool UnhookWindowsHookEx(IntPtr hhk);

		// Token: 0x0600003E RID: 62
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x0600003F RID: 63
		[DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr GetModuleHandle(string lpModuleName);

		// Token: 0x0400001B RID: 27
		private MouseHook.MouseHookHandler hookHandler;

		// Token: 0x04000025 RID: 37
		private IntPtr hookID = IntPtr.Zero;

		// Token: 0x04000026 RID: 38
		private const int WH_MOUSE_LL = 14;

		// Token: 0x0200000D RID: 13
		// (Invoke) Token: 0x06000056 RID: 86
		private delegate IntPtr MouseHookHandler(int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x0200000E RID: 14
		// (Invoke) Token: 0x0600005A RID: 90
		public delegate void MouseHookCallback(MouseHook.MSLLHOOKSTRUCT mouseStruct);

		// Token: 0x0200000F RID: 15
		private enum MouseMessages
		{
			// Token: 0x040000E2 RID: 226
			WM_LBUTTONDOWN = 513,
			// Token: 0x040000E3 RID: 227
			WM_LBUTTONUP,
			// Token: 0x040000E4 RID: 228
			WM_MOUSEMOVE = 512,
			// Token: 0x040000E5 RID: 229
			WM_MOUSEWHEEL = 522,
			// Token: 0x040000E6 RID: 230
			WM_RBUTTONDOWN = 516,
			// Token: 0x040000E7 RID: 231
			WM_RBUTTONUP,
			// Token: 0x040000E8 RID: 232
			WM_LBUTTONDBLCLK = 515,
			// Token: 0x040000E9 RID: 233
			WM_MBUTTONDOWN = 519,
			// Token: 0x040000EA RID: 234
			WM_MBUTTONUP
		}

		// Token: 0x02000010 RID: 16
		public struct POINT
		{
			// Token: 0x040000EB RID: 235
			public int x;

			// Token: 0x040000EC RID: 236
			public int y;
		}

		// Token: 0x02000011 RID: 17
		public struct MSLLHOOKSTRUCT
		{
			// Token: 0x040000ED RID: 237
			public MouseHook.POINT pt;

			// Token: 0x040000EE RID: 238
			public uint mouseData;

			// Token: 0x040000EF RID: 239
			public uint flags;

			// Token: 0x040000F0 RID: 240
			public uint time;

			// Token: 0x040000F1 RID: 241
			public IntPtr dwExtraInfo;
		}
	}
}
