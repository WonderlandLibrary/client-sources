using System;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace fate.Classes.Other
{
	// Token: 0x02000027 RID: 39
	public static class WinApi
	{
		// Token: 0x06000139 RID: 313
		[DllImport("User32.Dll", EntryPoint = "PostMessageA")]
		public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);

		// Token: 0x0600013A RID: 314
		[DllImport("user32.dll")]
		public static extern bool ReleaseCapture();

		// Token: 0x0600013B RID: 315
		[DllImport("Gdi32.dll")]
		public static extern IntPtr CreateRoundRectRgn(int nLeftRect, int nTopRect, int nRightRect, int nBottomRect, int nWidthEllipse, int nHeightEllipse);

		// Token: 0x0600013C RID: 316
		[DllImport("user32.dll")]
		public static extern IntPtr GetForegroundWindow();

		// Token: 0x0600013D RID: 317
		[DllImport("user32.dll")]
		public static extern ushort GetAsyncKeyState(int vKey);

		// Token: 0x0600013E RID: 318
		[DllImport("User32.dll")]
		public static extern short GetAsyncKeyState(Keys vKey);

		// Token: 0x0600013F RID: 319
		[DllImport("user32.dll")]
		public static extern short GetAsyncKeyState(ushort virtualKeyCode);

		// Token: 0x06000140 RID: 320
		[DllImport("user32.dll", SetLastError = true)]
		public static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

		// Token: 0x040000F7 RID: 247
		public const int WM_NCLBUTTONDOWN = 161;

		// Token: 0x040000F8 RID: 248
		public const int HT_CAPTION = 2;

		// Token: 0x040000F9 RID: 249
		public const uint WM_KEYDOWN = 256U;

		// Token: 0x040000FA RID: 250
		public const uint WM_KEYUP = 257U;

		// Token: 0x040000FB RID: 251
		public const int WM_SYSKEYDOWN = 260;

		// Token: 0x040000FC RID: 252
		public const ushort VK_LBUTTON = 1;

		// Token: 0x040000FD RID: 253
		public const ushort VK_RBUTTON = 2;

		// Token: 0x040000FE RID: 254
		public const ushort VK_XBUTTON2 = 6;

		// Token: 0x040000FF RID: 255
		public const ushort VK_XBUTTON1 = 5;

		// Token: 0x04000100 RID: 256
		public const int WM_RBUTTONDOWN = 516;

		// Token: 0x04000101 RID: 257
		public const int WM_RBUTTONUP = 517;
	}
}
