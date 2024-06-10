using System;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;

namespace ns0;

public static class Locator
{
	public static int int_0;

	public static int int_1;

	public static KeysConverter keysConverter_0 = new KeysConverter();

	public static uint uint_0;

	public static uint uint_1;

	public static int int_2;

	public static ushort ushort_0;

	public static ushort ushort_1;

	public static ushort ushort_2;

	public static ushort ushort_3;

	public static int int_3;

	public static int int_4;

	public static int int_5;

	public static int int_6;

	[DllImport("User32.Dll")]
	public static extern int PostMessageA(IntPtr intptr_0, int int_7, int int_8, int int_9);

	[DllImport("user32.dll")]
	public static extern bool ReleaseCapture();

	[DllImport("Gdi32.dll")]
	public static extern IntPtr CreateRoundRectRgn(int int_7, int int_8, int int_9, int int_10, int int_11, int int_12);

	[DllImport("kernel32.dll")]
	public static extern bool AllocConsole();

	[DllImport("user32.dll")]
	public static extern uint SetWindowDisplayAffinity(IntPtr intptr_0, uint uint_2);

	[DllImport("user32.dll")]
	public static extern IntPtr GetForegroundWindow();

	[DllImport("user32.dll")]
	public static extern ushort GetAsyncKeyState(int int_7);

	[DllImport("User32.dll", EntryPoint = "GetAsyncKeyState")]
	public static extern short GetAsyncKeyState_1(Keys keys_0);

	[DllImport("user32.dll", EntryPoint = "GetAsyncKeyState")]
	public static extern short GetAsyncKeyState_2(ushort ushort_4);

	[DllImport("user32.dll", SetLastError = true)]
	public static extern IntPtr FindWindow(string string_0, string string_1);

	[DllImport("user32.dll", CharSet = CharSet.Auto)]
	public static extern uint MapVirtualKey(uint uint_2, uint uint_3);

	[DllImport("User32.Dll", EntryPoint = "PostMessageA")]
	public static extern bool PostMessageA_1(IntPtr intptr_0, uint uint_2, int int_7, int int_8);

	[DllImport("user32.dll")]
	public static extern int GetWindowText(IntPtr intptr_0, StringBuilder stringBuilder_0, int int_7);

	public static string smethod_0()
	{
		StringBuilder stringBuilder = new StringBuilder(256);
		if (GetWindowText(GetForegroundWindow(), stringBuilder, 256) > 0)
		{
			return stringBuilder.ToString();
		}
		return null;
	}

	[DllImport("gdi32.dll")]
	public static extern IntPtr AddFontMemResourceEx(IntPtr intptr_0, uint uint_2, IntPtr intptr_1, [In] ref uint uint_3);

	public static int smethod_1(int int_7, int int_8)
	{
		return (int_8 << 16) | (int_7 & 0xFFFF);
	}

	[DllImport("kernel32.dll")]
	public static extern IntPtr GetConsoleWindow();

	[DllImport("user32.dll")]
	public static extern bool ShowWindow(IntPtr intptr_0, int int_7);

	[DllImport("kernel32.dll")]
	public static extern bool ReadProcessMemory(int int_7, long long_0, byte[] byte_0, int int_8, ref int int_9);

	[DllImport("ntdll.dll", SetLastError = true)]
	public static extern GEnum0 NtReadVirtualMemory(IntPtr intptr_0, long long_0, byte[] byte_0, uint uint_2, uint uint_3);

	[DllImport("ntdll.dll", SetLastError = true)]
	public static extern GEnum0 NtWriteVirtualMemory(IntPtr intptr_0, long long_0, byte[] byte_0, uint uint_2, uint uint_3);

	[DllImport("kernel32.dll")]
	public static extern bool VirtualQueryEx(IntPtr intptr_0, long long_0, out GStruct3 gstruct3_0, uint uint_2);

	[DllImport("user32.dll")]
	public static extern uint GetWindowThreadProcessId(IntPtr intptr_0, out uint uint_2);

	[DllImport("kernel32.dll", SetLastError = true)]
	public static extern IntPtr OpenProcess(uint uint_2, bool bool_0, int int_7);
}
