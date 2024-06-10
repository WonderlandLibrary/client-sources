using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace MyeGhost
{
	// Token: 0x02000003 RID: 3
	public partial class clicker : Form
	{
		// Token: 0x06000004 RID: 4
		[DllImport("user32", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x06000005 RID: 5
		[DllImport("user32.dll")]
		public static extern bool RegisterHotKey(IntPtr hWnd, int id, int fsModifiers, int vlc);

		// Token: 0x06000006 RID: 6
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000007 RID: 7
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);

		// Token: 0x06000008 RID: 8
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr hWnd);

		// Token: 0x06000009 RID: 9
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

		// Token: 0x0600000A RID: 10 RVA: 0x000027B4 File Offset: 0x000009B4
		public static bool ApplicationIsActivated()
		{
			IntPtr foregroundWindow = clicker.GetForegroundWindow();
			bool result;
			if (foregroundWindow == IntPtr.Zero)
			{
				result = false;
			}
			else
			{
				int id = Process.GetCurrentProcess().Id;
				int num;
				clicker.GetWindowThreadProcessId(foregroundWindow, out num);
				result = (num == id);
			}
			return result;
		}

		// Token: 0x0600000B RID: 11 RVA: 0x000027F4 File Offset: 0x000009F4
		private string GetCaptionOfActiveWindow()
		{
			string result = string.Empty;
			IntPtr foregroundWindow = clicker.GetForegroundWindow();
			int num = clicker.GetWindowTextLength(foregroundWindow) + 1;
			StringBuilder stringBuilder = new StringBuilder(num);
			if (clicker.GetWindowText(foregroundWindow, stringBuilder, num) > 0)
			{
				result = stringBuilder.ToString();
			}
			return result;
		}

		// Token: 0x0600000C RID: 12 RVA: 0x0000282E File Offset: 0x00000A2E
		public static string RandomString(int length)
		{
			return new string((from s in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", length)
			select s[clicker.random.Next(s.Length)]).ToArray<char>());
		}

		// Token: 0x0600000D RID: 13 RVA: 0x0000286C File Offset: 0x00000A6C
		public clicker()
		{
			this.InitializeComponent();
			int id = 1;
			int vlc = 119;
			bool flag = clicker.RegisterHotKey(base.Handle, id, 0, vlc);
			int id2 = 2;
			int vlc2 = 115;
			bool flag2 = clicker.RegisterHotKey(base.Handle, id2, 0, vlc2);
			int id3 = 3;
			int vlc3 = 121;
			bool flag3 = clicker.RegisterHotKey(base.Handle, id3, 0, vlc3);
			if (!flag)
			{
				Console.WriteLine("Global Hotkey F8 couldn't be registered !");
			}
			if (!flag2)
			{
				Console.WriteLine("Global Hotkey F4 couldn't be registered !");
			}
			if (!flag3)
			{
				Console.WriteLine("Global Hotkey F4 couldn't be registered !");
			}
		}

		// Token: 0x0600000E RID: 14
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(Keys vKey);

		// Token: 0x0600000F RID: 15 RVA: 0x000028FC File Offset: 0x00000AFC
		private void timer1_Tick(object sender, EventArgs e)
		{
			if (this.checkBox1.Checked && (this.GetCaptionOfActiveWindow().Contains("Minecraft") || this.GetCaptionOfActiveWindow().Contains("Badlion") || this.GetCaptionOfActiveWindow().Contains("Labymod") || this.GetCaptionOfActiveWindow().Contains("OCMC") || this.GetCaptionOfActiveWindow().Contains("CheatBreaker") || this.GetCaptionOfActiveWindow().Contains("J3Ultimate") || this.GetCaptionOfActiveWindow().Contains("Lunar Client")) && !clicker.ApplicationIsActivated() && Control.MouseButtons == MouseButtons.Left)
			{
				clicker.mouse_event(4, 0, 0, 0, 0);
				clicker.mouse_event(2, 0, 0, 0, 0);
			}
		}

		// Token: 0x06000010 RID: 16 RVA: 0x000029BE File Offset: 0x00000BBE
		private void t2_Tick(object sender, EventArgs e)
		{
			if (clicker.GetAsyncKeyState(Keys.F4) < 0)
			{
				Thread.Sleep(155);
				this.t1.Stop();
				this.t3.Start();
				this.t2.Stop();
			}
		}

		// Token: 0x06000011 RID: 17 RVA: 0x000029F5 File Offset: 0x00000BF5
		private void t3_Tick(object sender, EventArgs e)
		{
			if (clicker.GetAsyncKeyState(Keys.F4) < 0)
			{
				Thread.Sleep(155);
				this.t1.Start();
				this.t2.Start();
				this.t3.Stop();
			}
		}

		// Token: 0x04000002 RID: 2
		private Random rnd = new Random();

		// Token: 0x04000003 RID: 3
		private const int MOUSEEVENTF_MOVE = 1;

		// Token: 0x04000004 RID: 4
		private const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x04000005 RID: 5
		private const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x04000006 RID: 6
		private const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x04000007 RID: 7
		private const int MOUSEEVENTF_RIGHTUP = 22;

		// Token: 0x04000008 RID: 8
		private static Random random = new Random();
	}
}
