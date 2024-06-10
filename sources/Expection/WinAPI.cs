using System;
using System.Drawing;
using System.Runtime.InteropServices;

namespace AnyDesk
{
	// Token: 0x02000018 RID: 24
	internal static class WinAPI
	{
		// Token: 0x0600004C RID: 76
		[DllImport("user32.dll")]
		public static extern bool GetClientRect(IntPtr hWnd, out Rect RECT);

		// Token: 0x0600004D RID: 77
		[DllImport("user32.dll")]
		public static extern bool ClientToScreen(IntPtr hWnd, out Point point);

		// Token: 0x0600004E RID: 78
		[DllImport("user32.dll")]
		public static extern bool GetWindowRect(IntPtr hWnd, out Rect RECT);

		// Token: 0x0600004F RID: 79
		[DllImport("user32.dll")]
		public static extern bool BlockInput(bool fBlockIt);

		// Token: 0x06000050 RID: 80
		[DllImport("user32.dll", SetLastError = true)]
		public static extern int GetWindowLong(IntPtr hWnd, int nIndex);

		// Token: 0x06000051 RID: 81
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, int nIndex, int dwNewLong);

		// Token: 0x06000052 RID: 82
		[DllImport("user32.dll")]
		public static extern bool SetLayeredWindowAttributes(IntPtr hwnd, uint crKey, byte bAlpha, uint dwFlags);

		// Token: 0x06000053 RID: 83
		[DllImport("dwmapi.dll")]
		public static extern void DwmExtendFrameIntoClientArea(IntPtr hWnd, ref Margins pMargins);

		// Token: 0x06000054 RID: 84
		[DllImport("user32", CharSet = CharSet.Ansi, SetLastError = true)]
		public static extern int GetAsyncKeyState(int vKey);

		// Token: 0x06000055 RID: 85
		[DllImport("kernel32.dll")]
		internal static extern IntPtr OpenProcess(Enums.ProcessAccessFlags dwDesiredAccess, [MarshalAs(UnmanagedType.Bool)] bool bInheritHandle, int dwProcessId);

		// Token: 0x06000056 RID: 86
		[DllImport("kernel32.dll")]
		internal static extern bool CloseHandle(IntPtr hProcess);

		// Token: 0x06000057 RID: 87
		[DllImport("Kernel32.dll")]
		internal static extern bool ReadProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, uint nSize, ref uint lpNumberOfBytesRead);

		// Token: 0x06000058 RID: 88
		[DllImport("kernel32.dll")]
		internal static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, IntPtr nSize, ref uint lpNumberOfBytesWritten);

		// Token: 0x06000059 RID: 89
		[DllImport("kernel32.dll")]
		public static extern bool WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] buffer, int size, int lpNumberOfBytesWritten);

		// Token: 0x0600005A RID: 90
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern bool VirtualFreeEx(IntPtr hProcess, IntPtr lpAddress, IntPtr dwSize, Enums.FreeType dwFreeType);

		// Token: 0x0600005B RID: 91
		[DllImport("kernel32.dll")]
		internal static extern uint WaitForSingleObject(IntPtr hProcess, uint dwMilliseconds);

		// Token: 0x0600005C RID: 92
		[DllImport("kernel32.dll", SetLastError = true)]
		internal static extern IntPtr CreateRemoteThread(IntPtr hProcess, IntPtr lpThreadAttributes, IntPtr dwStackSize, IntPtr lpStartAddress, IntPtr lpParameter, uint dwCreationFlags, IntPtr lpThreadId);

		// Token: 0x0600005D RID: 93
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern IntPtr VirtualAllocEx(IntPtr hProcess, IntPtr lpAddress, IntPtr dwSize, uint flAllocationType, uint flProtect);

		// Token: 0x0600005E RID: 94
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		public static extern bool VirtualFreeEx(IntPtr hProcess, IntPtr lpAddress, int dwSize, uint dwFreeType);

		// Token: 0x040000A4 RID: 164
		public const int PAGE_READWRITE = 64;

		// Token: 0x02000019 RID: 25
		// (Invoke) Token: 0x06000060 RID: 96
		internal delegate int ThreadProc(IntPtr param);
	}
}
