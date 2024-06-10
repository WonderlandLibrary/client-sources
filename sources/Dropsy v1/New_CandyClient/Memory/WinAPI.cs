using System;
using System.Drawing;
using System.Runtime.InteropServices;

namespace New_CandyClient.Memory
{
	// Token: 0x02000049 RID: 73
	internal static class WinAPI
	{
		// Token: 0x060001AA RID: 426
		[DllImport("user32.dll")]
		public static extern bool GetClientRect(IntPtr hWnd, out Rect RECT);

		// Token: 0x060001AB RID: 427
		[DllImport("user32.dll")]
		public static extern bool ClientToScreen(IntPtr hWnd, out Point point);

		// Token: 0x060001AC RID: 428
		[DllImport("user32.dll")]
		public static extern bool GetWindowRect(IntPtr hWnd, out Rect RECT);

		// Token: 0x060001AD RID: 429
		[DllImport("user32.dll")]
		public static extern bool BlockInput(bool fBlockIt);

		// Token: 0x060001AE RID: 430
		[DllImport("user32.dll", SetLastError = true)]
		public static extern int GetWindowLong(IntPtr hWnd, int nIndex);

		// Token: 0x060001AF RID: 431
		[DllImport("user32.dll")]
		public static extern int SetWindowLong(IntPtr hWnd, int nIndex, int dwNewLong);

		// Token: 0x060001B0 RID: 432
		[DllImport("user32.dll")]
		public static extern bool SetLayeredWindowAttributes(IntPtr hwnd, uint crKey, byte bAlpha, uint dwFlags);

		// Token: 0x060001B1 RID: 433
		[DllImport("dwmapi.dll")]
		public static extern void DwmExtendFrameIntoClientArea(IntPtr hWnd, ref Margins pMargins);

		// Token: 0x060001B2 RID: 434
		[DllImport("user32", CharSet = CharSet.Ansi, SetLastError = true)]
		public static extern int GetAsyncKeyState(int vKey);

		// Token: 0x060001B3 RID: 435
		[DllImport("kernel32.dll")]
		internal static extern IntPtr OpenProcess(Enums.ProcessAccessFlags dwDesiredAccess, [MarshalAs(UnmanagedType.Bool)] bool bInheritHandle, int dwProcessId);

		// Token: 0x060001B4 RID: 436
		[DllImport("kernel32.dll")]
		internal static extern bool CloseHandle(IntPtr hProcess);

		// Token: 0x060001B5 RID: 437
		[DllImport("ntdll")]
		internal static extern bool ZwReadVirtualMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, uint nSize, ref uint lpNumberOfBytesRead);

		// Token: 0x060001B6 RID: 438
		[DllImport("ntdll")]
		internal static extern bool NtWriteVirtualMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, IntPtr nSize, ref uint lpNumberOfBytesWritten);

		// Token: 0x060001B7 RID: 439
		[DllImport("ntdll")]
		public static extern bool ZwWriteVirtualMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] lpBuffer, IntPtr nSize, ref uint lpNumberOfBytesWritten);

		// Token: 0x060001B8 RID: 440
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern bool VirtualFreeEx(IntPtr hProcess, IntPtr lpAddress, IntPtr dwSize, Enums.FreeType dwFreeType);

		// Token: 0x060001B9 RID: 441
		[DllImport("kernel32.dll")]
		internal static extern uint WaitForSingleObject(IntPtr hProcess, uint dwMilliseconds);

		// Token: 0x060001BA RID: 442
		[DllImport("kernel32.dll", SetLastError = true)]
		internal static extern IntPtr CreateRemoteThread(IntPtr hProcess, IntPtr lpThreadAttributes, IntPtr dwStackSize, IntPtr lpStartAddress, IntPtr lpParameter, uint dwCreationFlags, IntPtr lpThreadId);

		// Token: 0x060001BB RID: 443
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		internal static extern IntPtr VirtualAllocEx(IntPtr hProcess, IntPtr lpAddress, IntPtr dwSize, uint flAllocationType, uint flProtect);

		// Token: 0x060001BC RID: 444
		[DllImport("kernel32.dll", ExactSpelling = true, SetLastError = true)]
		public static extern bool VirtualFreeEx(IntPtr hProcess, IntPtr lpAddress, int dwSize, uint dwFreeType);

		// Token: 0x040003FE RID: 1022
		public const int PAGE_READWRITE = 64;

		// Token: 0x0200004A RID: 74
		// (Invoke) Token: 0x060001BE RID: 446
		internal delegate int ThreadProc(IntPtr param);
	}
}
