using System;
using System.Runtime.InteropServices;

namespace AutoClicker
{
	// Token: 0x0200000E RID: 14
	public class Win32
	{
		// Token: 0x06000092 RID: 146
		[DllImport("user32.dll")]
		public static extern bool UnregisterHotKey(IntPtr hWnd, int id);

		// Token: 0x06000093 RID: 147
		[DllImport("user32.dll")]
		public static extern bool RegisterHotKey(IntPtr hWnd, int id, uint fsModifiers, uint vk);

		// Token: 0x06000094 RID: 148
		[DllImport("user32.dll")]
		public static extern uint SendInput(uint nInputs, Win32.INPUT[] pInputs, int cbSize);

		// Token: 0x06000095 RID: 149
		[DllImport("user32.dll")]
		public static extern int GetSystemMetrics(Win32.SystemMetric smIndex);

		// Token: 0x06000096 RID: 150 RVA: 0x00009AEA File Offset: 0x00007CEA
		public static int CalculateAbsoluteCoordinateX(int x)
		{
			return x * 65536 / Win32.GetSystemMetrics(Win32.SystemMetric.CXScreen);
		}

		// Token: 0x06000097 RID: 151 RVA: 0x00009AFA File Offset: 0x00007CFA
		public static int CalculateAbsoluteCoordinateY(int y)
		{
			return y * 65536 / Win32.GetSystemMetrics(Win32.SystemMetric.CYScreen);
		}

		// Token: 0x040000C8 RID: 200
		public const int WM_HOTKEY = 786;

		// Token: 0x02000021 RID: 33
		public enum SendInputEventType
		{
			// Token: 0x040000F0 RID: 240
			InputMouse,
			// Token: 0x040000F1 RID: 241
			InputKeyboard
		}

		// Token: 0x02000022 RID: 34
		public enum MouseEventFlags
		{
			// Token: 0x040000F3 RID: 243
			Move = 1,
			// Token: 0x040000F4 RID: 244
			LeftDown,
			// Token: 0x040000F5 RID: 245
			LeftUp = 4,
			// Token: 0x040000F6 RID: 246
			RightDown = 8,
			// Token: 0x040000F7 RID: 247
			RightUp = 16,
			// Token: 0x040000F8 RID: 248
			MiddleDown = 32,
			// Token: 0x040000F9 RID: 249
			MiddleUp = 64,
			// Token: 0x040000FA RID: 250
			Wheel = 128,
			// Token: 0x040000FB RID: 251
			XDown = 256,
			// Token: 0x040000FC RID: 252
			XUp = 512,
			// Token: 0x040000FD RID: 253
			Absolute = 32768
		}

		// Token: 0x02000023 RID: 35
		public enum SystemMetric
		{
			// Token: 0x040000FF RID: 255
			CXScreen,
			// Token: 0x04000100 RID: 256
			CYScreen
		}

		// Token: 0x02000024 RID: 36
		public enum fsModifiers : uint
		{
			// Token: 0x04000102 RID: 258
			Alt = 1U,
			// Token: 0x04000103 RID: 259
			Control,
			// Token: 0x04000104 RID: 260
			Shift = 4U,
			// Token: 0x04000105 RID: 261
			Windows = 8U,
			// Token: 0x04000106 RID: 262
			NoRepeat = 16384U
		}

		// Token: 0x02000025 RID: 37
		public struct INPUT
		{
			// Token: 0x04000107 RID: 263
			public Win32.SendInputEventType type;

			// Token: 0x04000108 RID: 264
			public Win32.MOUSEINPUT mi;
		}

		// Token: 0x02000026 RID: 38
		public struct MOUSEINPUT
		{
			// Token: 0x04000109 RID: 265
			public int dx;

			// Token: 0x0400010A RID: 266
			public int dy;

			// Token: 0x0400010B RID: 267
			public int mouseData;

			// Token: 0x0400010C RID: 268
			public Win32.MouseEventFlags dwFlags;

			// Token: 0x0400010D RID: 269
			public int time;

			// Token: 0x0400010E RID: 270
			public IntPtr dwExtraInfo;
		}
	}
}
