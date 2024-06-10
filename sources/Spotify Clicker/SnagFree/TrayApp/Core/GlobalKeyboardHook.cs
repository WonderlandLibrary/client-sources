using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Runtime.InteropServices;

namespace SnagFree.TrayApp.Core
{
	// Token: 0x02000003 RID: 3
	internal class GlobalKeyboardHook : IDisposable
	{
		// Token: 0x14000001 RID: 1
		// (add) Token: 0x06000006 RID: 6 RVA: 0x00002088 File Offset: 0x00000288
		// (remove) Token: 0x06000007 RID: 7 RVA: 0x000020C0 File Offset: 0x000002C0
		public event EventHandler<GlobalKeyboardHookEventArgs> KeyboardPressed;

		// Token: 0x06000008 RID: 8 RVA: 0x000020F8 File Offset: 0x000002F8
		public GlobalKeyboardHook()
		{
			this._windowsHookHandle = IntPtr.Zero;
			this._user32LibraryHandle = IntPtr.Zero;
			this._hookProc = new GlobalKeyboardHook.HookProc(this.LowLevelKeyboardProc);
			this._user32LibraryHandle = GlobalKeyboardHook.LoadLibrary("User32");
			if (this._user32LibraryHandle == IntPtr.Zero)
			{
				int lastWin32Error = Marshal.GetLastWin32Error();
				throw new Win32Exception(lastWin32Error, string.Format("Failed to load library 'User32.dll'. Error {0}: {1}.", lastWin32Error, new Win32Exception(Marshal.GetLastWin32Error()).Message));
			}
			this._windowsHookHandle = GlobalKeyboardHook.SetWindowsHookEx(13, this._hookProc, this._user32LibraryHandle, 0);
			if (this._windowsHookHandle == IntPtr.Zero)
			{
				int lastWin32Error2 = Marshal.GetLastWin32Error();
				throw new Win32Exception(lastWin32Error2, string.Format("Failed to adjust keyboard hooks for '{0}'. Error {1}: {2}.", Process.GetCurrentProcess().ProcessName, lastWin32Error2, new Win32Exception(Marshal.GetLastWin32Error()).Message));
			}
		}

		// Token: 0x06000009 RID: 9 RVA: 0x000021E4 File Offset: 0x000003E4
		protected virtual void Dispose(bool disposing)
		{
			if (disposing && this._windowsHookHandle != IntPtr.Zero)
			{
				if (!GlobalKeyboardHook.UnhookWindowsHookEx(this._windowsHookHandle))
				{
					int lastWin32Error = Marshal.GetLastWin32Error();
					throw new Win32Exception(lastWin32Error, string.Format("Failed to remove keyboard hooks for '{0}'. Error {1}: {2}.", Process.GetCurrentProcess().ProcessName, lastWin32Error, new Win32Exception(Marshal.GetLastWin32Error()).Message));
				}
				this._windowsHookHandle = IntPtr.Zero;
				this._hookProc = (GlobalKeyboardHook.HookProc)Delegate.Remove(this._hookProc, new GlobalKeyboardHook.HookProc(this.LowLevelKeyboardProc));
			}
			if (this._user32LibraryHandle != IntPtr.Zero)
			{
				if (!GlobalKeyboardHook.FreeLibrary(this._user32LibraryHandle))
				{
					int lastWin32Error2 = Marshal.GetLastWin32Error();
					throw new Win32Exception(lastWin32Error2, string.Format("Failed to unload library 'User32.dll'. Error {0}: {1}.", lastWin32Error2, new Win32Exception(Marshal.GetLastWin32Error()).Message));
				}
				this._user32LibraryHandle = IntPtr.Zero;
			}
		}

		// Token: 0x0600000A RID: 10 RVA: 0x000022D0 File Offset: 0x000004D0
		~GlobalKeyboardHook()
		{
			this.Dispose(false);
		}

		// Token: 0x0600000B RID: 11 RVA: 0x00002300 File Offset: 0x00000500
		public void Dispose()
		{
			this.Dispose(true);
			GC.SuppressFinalize(this);
		}

		// Token: 0x0600000C RID: 12
		[DllImport("kernel32.dll")]
		private static extern IntPtr LoadLibrary(string lpFileName);

		// Token: 0x0600000D RID: 13
		[DllImport("kernel32.dll", CharSet = CharSet.Auto)]
		private static extern bool FreeLibrary(IntPtr hModule);

		// Token: 0x0600000E RID: 14
		[DllImport("USER32", SetLastError = true)]
		private static extern IntPtr SetWindowsHookEx(int idHook, GlobalKeyboardHook.HookProc lpfn, IntPtr hMod, int dwThreadId);

		// Token: 0x0600000F RID: 15
		[DllImport("USER32", SetLastError = true)]
		public static extern bool UnhookWindowsHookEx(IntPtr hHook);

		// Token: 0x06000010 RID: 16
		[DllImport("USER32", SetLastError = true)]
		private static extern IntPtr CallNextHookEx(IntPtr hHook, int code, IntPtr wParam, IntPtr lParam);

		// Token: 0x06000011 RID: 17 RVA: 0x00002310 File Offset: 0x00000510
		public IntPtr LowLevelKeyboardProc(int nCode, IntPtr wParam, IntPtr lParam)
		{
			bool flag = false;
			int num = wParam.ToInt32();
			if (Enum.IsDefined(typeof(GlobalKeyboardHook.KeyboardState), num))
			{
				GlobalKeyboardHookEventArgs globalKeyboardHookEventArgs = new GlobalKeyboardHookEventArgs((GlobalKeyboardHook.LowLevelKeyboardInputEvent)Marshal.PtrToStructure(lParam, typeof(GlobalKeyboardHook.LowLevelKeyboardInputEvent)), (GlobalKeyboardHook.KeyboardState)num);
				EventHandler<GlobalKeyboardHookEventArgs> keyboardPressed = this.KeyboardPressed;
				if (keyboardPressed != null)
				{
					keyboardPressed(this, globalKeyboardHookEventArgs);
				}
				flag = globalKeyboardHookEventArgs.Handled;
			}
			if (!flag)
			{
				return GlobalKeyboardHook.CallNextHookEx(IntPtr.Zero, nCode, wParam, lParam);
			}
			return (IntPtr)1;
		}

		// Token: 0x04000004 RID: 4
		private IntPtr _windowsHookHandle;

		// Token: 0x04000005 RID: 5
		private IntPtr _user32LibraryHandle;

		// Token: 0x04000006 RID: 6
		private GlobalKeyboardHook.HookProc _hookProc;

		// Token: 0x04000007 RID: 7
		public const int WH_KEYBOARD_LL = 13;

		// Token: 0x04000008 RID: 8
		public const int VkSnapshot = 44;

		// Token: 0x04000009 RID: 9
		private const int KfAltdown = 8192;

		// Token: 0x0400000A RID: 10
		public const int LlkhfAltdown = 32;

		// Token: 0x02000011 RID: 17
		// (Invoke) Token: 0x060000A9 RID: 169
		private delegate IntPtr HookProc(int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x02000012 RID: 18
		public struct LowLevelKeyboardInputEvent
		{
			// Token: 0x040000CC RID: 204
			public int VirtualCode;

			// Token: 0x040000CD RID: 205
			public int HardwareScanCode;

			// Token: 0x040000CE RID: 206
			public int Flags;

			// Token: 0x040000CF RID: 207
			public int TimeStamp;

			// Token: 0x040000D0 RID: 208
			public IntPtr AdditionalInformation;
		}

		// Token: 0x02000013 RID: 19
		public enum KeyboardState
		{
			// Token: 0x040000D2 RID: 210
			KeyDown = 256,
			// Token: 0x040000D3 RID: 211
			KeyUp,
			// Token: 0x040000D4 RID: 212
			SysKeyDown = 260,
			// Token: 0x040000D5 RID: 213
			SysKeyUp
		}
	}
}
