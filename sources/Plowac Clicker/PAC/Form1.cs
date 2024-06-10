using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using FlatUI;
using Transitions;

namespace PAC
{
	// Token: 0x02000002 RID: 2
	public partial class Form1 : Form
	{
		// Token: 0x06000001 RID: 1
		[DllImport("user32.dll")]
		public static extern bool RegisterHotKey(IntPtr hWnd, int id, int fsModifiers, int vlc);

		// Token: 0x06000002 RID: 2
		[DllImport("user32", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x06000003 RID: 3
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr hWnd);

		// Token: 0x06000004 RID: 4
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

		// Token: 0x06000005 RID: 5
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000006 RID: 6
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);

		// Token: 0x06000007 RID: 7
		[DllImport("user32.dll")]
		public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);

		// Token: 0x06000008 RID: 8
		[DllImport("user32.dll")]
		public static extern bool ReleaseCapture();

		// Token: 0x06000009 RID: 9 RVA: 0x0000CDF0 File Offset: 0x0000AFF0
		public static bool ApplicationIsActivated()
		{
			IntPtr foregroundWindow = Form1.GetForegroundWindow();
			bool flag = foregroundWindow == IntPtr.Zero;
			bool result;
			if (flag)
			{
				result = false;
			}
			else
			{
				int id = Process.GetCurrentProcess().Id;
				int num;
				Form1.GetWindowThreadProcessId(foregroundWindow, out num);
				result = (num == id);
			}
			return result;
		}

		// Token: 0x0600000A RID: 10 RVA: 0x0000CE48 File Offset: 0x0000B048
		public Form1()
		{
			this.InitializeComponent();
			int id = 1;
			int vlc = 119;
			bool flag = Form1.RegisterHotKey(base.Handle, id, 0, vlc);
			bool flag2 = !flag;
			if (flag2)
			{
				Console.WriteLine("Global Hotkey F8 couldn't be registered !");
			}
			int id2 = 2;
			int vlc2 = 121;
			bool flag3 = Form1.RegisterHotKey(base.Handle, id2, 0, vlc2);
			bool flag4 = !flag3;
			if (flag4)
			{
				Console.WriteLine("Global Hotkey F10 couldn't be registered !");
			}
			int id3 = 3;
			int vlc3 = 115;
			bool flag5 = Form1.RegisterHotKey(base.Handle, id3, 0, vlc3);
			bool flag6 = !flag5;
			if (flag6)
			{
				Console.WriteLine("Global Hotkey F8 couldn't be registered !");
			}
			int id4 = 4;
			int vlc4 = 118;
			bool flag7 = Form1.RegisterHotKey(base.Handle, id4, 0, vlc4);
			bool flag8 = !flag7;
			if (flag8)
			{
				Console.WriteLine("Global Hotkey F7 couldn't be registered !");
			}
		}

		// Token: 0x0600000B RID: 11 RVA: 0x00002068 File Offset: 0x00000268
		private void pictureBox1_Click(object sender, EventArgs e)
		{
			Environment.Exit(1);
		}

		// Token: 0x0600000C RID: 12 RVA: 0x00002072 File Offset: 0x00000272
		private void bunifuFlatButton2_Click(object sender, EventArgs e)
		{
			this.timer1.Stop();
		}

		// Token: 0x0600000D RID: 13 RVA: 0x00002081 File Offset: 0x00000281
		private void bunifuFlatButton1_Click_1(object sender, EventArgs e)
		{
			this.timer1.Start();
		}

		// Token: 0x0600000E RID: 14 RVA: 0x0000CF44 File Offset: 0x0000B144
		private void timer1_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			int num = 1000 / this.trackBar1.Value;
			int value = this.trackBar2.Value;
			int minValue = num + 0;
			int num2 = 1000 / this.trackBar1.Value;
			int value2 = this.trackBar2.Value;
			int maxValue = num2 + 0;
			this.timer1.Interval = random.Next(minValue, maxValue) + 1;
			bool flag = !Form1.ApplicationIsActivated();
			if (flag)
			{
				bool flag2 = Control.MouseButtons == MouseButtons.Left;
				if (flag2)
				{
					Form1.mouse_event(4, 0, 0, 0, 0);
					Form1.mouse_event(2, 0, 0, 0, 0);
				}
			}
		}

		// Token: 0x0600000F RID: 15 RVA: 0x00002090 File Offset: 0x00000290
		private void flatLabel4_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000010 RID: 16 RVA: 0x0000CFEC File Offset: 0x0000B1EC
		private string GetCaptionOfActiveWindow()
		{
			string result = string.Empty;
			IntPtr foregroundWindow = Form1.GetForegroundWindow();
			int num = Form1.GetWindowTextLength(foregroundWindow) + 1;
			StringBuilder stringBuilder = new StringBuilder(num);
			bool flag = Form1.GetWindowText(foregroundWindow, stringBuilder, num) > 0;
			if (flag)
			{
				result = stringBuilder.ToString();
			}
			return result;
		}

		// Token: 0x06000011 RID: 17 RVA: 0x0000D040 File Offset: 0x0000B240
		protected override void WndProc(ref Message m)
		{
			bool flag = m.Msg == 786;
			if (flag)
			{
				switch (m.WParam.ToInt32())
				{
				case 1:
				{
					Form1.onoff++;
					bool flag2 = Form1.onoff == 1;
					if (flag2)
					{
						this.label2.Text = " AC On";
						this.label2.ForeColor = Color.FromArgb(30, 255, 0);
						this.timer1.Start();
					}
					bool flag3 = Form1.onoff != 1;
					if (flag3)
					{
						this.label2.Text = "AC Off";
						this.label2.ForeColor = Color.FromArgb(255, 0, 54);
						this.timer1.Stop();
						Form1.onoff = 0;
					}
					break;
				}
				case 2:
					this.bunifuFlatButton3_Click(this, new EventArgs());
					break;
				case 3:
				{
					bool flag4 = this.estado;
					if (flag4)
					{
						this.estado = false;
						base.Show();
					}
					else
					{
						this.estado = true;
						base.Hide();
					}
					break;
				}
				case 4:
				{
					Form1.onoffb++;
					bool flag5 = Form1.onoffb == 1;
					if (flag5)
					{
						this.label1.Text = "STap On";
						this.label1.ForeColor = Color.FromArgb(30, 255, 0);
						this.timer2.Start();
					}
					bool flag6 = Form1.onoffb != 1;
					if (flag6)
					{
						this.label1.Text = "STap Off";
						this.label1.ForeColor = Color.FromArgb(255, 0, 54);
						this.timer2.Stop();
						Form1.onoffb = 0;
					}
					break;
				}
				}
			}
			base.WndProc(ref m);
		}

		// Token: 0x06000012 RID: 18 RVA: 0x0000D240 File Offset: 0x0000B440
		public static string RandomString(int length)
		{
			return new string((from s in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", length)
			select s[Form1.random.Next(s.Length)]).ToArray<char>());
		}

		// Token: 0x06000013 RID: 19 RVA: 0x0000D294 File Offset: 0x0000B494
		private void delall()
		{
			Version version = Environment.OSVersion.Version;
			this.flatLabel1.Text = null;
			this.flatLabel2.Text = null;
			this.flatLabel3.Text = null;
			this.flatLabel4.Text = null;
			this.flatLabel6.Text = null;
			this.flatLabel7.Text = null;
			this.bunifuFlatButton3.Text = null;
			this.trackBar1.Text = null;
			this.trackBar2.Text = null;
			this.pictureBox1.Text = null;
			this.pictureBox2.Text = null;
			this.flatLabel8.Text = null;
			this.flatLabel9.Text = null;
			this.flatLabel1.Dispose();
			this.flatLabel2.Dispose();
			this.flatLabel3.Dispose();
			this.flatLabel4.Dispose();
			this.flatLabel6.Dispose();
			this.flatLabel7.Dispose();
			this.flatLabel8.Dispose();
			this.bunifuFlatButton3.Dispose();
			this.trackBar1.Dispose();
			this.trackBar2.Dispose();
			this.pictureBox2.Dispose();
			this.pictureBox1.Dispose();
			this.flatLabel9.Dispose();
			try
			{
				foreach (Process process in Process.GetProcesses())
				{
					bool flag = version < new Version(6, 2);
					if (flag)
					{
						bool flag2 = process.ProcessName == "explorer";
						if (flag2)
						{
							process.Kill();
							string fileName = string.Format("{0}\\{1}", Environment.GetEnvironmentVariable("WINDIR"), "explorer.exe");
							new Process
							{
								StartInfo = 
								{
									FileName = fileName,
									UseShellExecute = true
								}
							}.Start();
							Environment.Exit(0);
							break;
						}
					}
					else
					{
						bool flag3 = process.ProcessName == "explorer";
						if (flag3)
						{
							process.Kill();
							Environment.Exit(0);
							break;
						}
					}
				}
			}
			catch (Exception arg)
			{
				MessageBox.Show("Erro: " + arg, Form1.RandomString(5), MessageBoxButtons.OK, MessageBoxIcon.Hand);
				Environment.Exit(0);
			}
		}

		// Token: 0x06000014 RID: 20 RVA: 0x0000D51C File Offset: 0x0000B71C
		private void deletarlol()
		{
			string str = Path.Combine(new string[]
			{
				Directory.GetCurrentDirectory() + "\\" + AppDomain.CurrentDomain.FriendlyName
			});
			string text = Path.Combine(new string[]
			{
				Directory.GetCurrentDirectory() + "\\Bunifu_UI_v1.5.4.dllFlatUI.dllMaterialSkin.dllMetroFramework.dllPropertyChanged.dllTransitions.dll"
			});
			bool flag = File.Exists(text);
			if (flag)
			{
				Process.Start(new ProcessStartInfo
				{
					FileName = "cmd",
					Arguments = "/C ping 1.1.1.1 -n 1 & Del " + str + " & Del " + text,
					CreateNoWindow = true,
					WindowStyle = ProcessWindowStyle.Hidden
				});
			}
			else
			{
				Process.Start(new ProcessStartInfo
				{
					FileName = "cmd",
					Arguments = "/C ping 1.1.1.1 -n 1 & Del " + str,
					CreateNoWindow = true,
					WindowStyle = ProcessWindowStyle.Hidden
				});
			}
			this.delall();
		}

		// Token: 0x06000015 RID: 21 RVA: 0x0000D608 File Offset: 0x0000B808
		private void trackBar2_Scroll(object sender, EventArgs e)
		{
			this.flatLabel3.Text = this.trackBar2.Value.ToString();
		}

		// Token: 0x06000016 RID: 22 RVA: 0x0000D638 File Offset: 0x0000B838
		private void trackBar1_Scroll(object sender, EventArgs e)
		{
			this.flatLabel4.Text = this.trackBar1.Value.ToString();
		}

		// Token: 0x06000017 RID: 23 RVA: 0x00002090 File Offset: 0x00000290
		private void flatButton1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000018 RID: 24 RVA: 0x00002090 File Offset: 0x00000290
		private void flatLabel5_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000019 RID: 25 RVA: 0x0000D668 File Offset: 0x0000B868
		private void bunifuFlatButton3_Click(object sender, EventArgs e)
		{
			bool @checked = this.bunifuCheckbox1.Checked;
			if (@checked)
			{
				this.deletarlol();
			}
			else
			{
				this.delall();
			}
		}

		// Token: 0x0600001A RID: 26 RVA: 0x0000D638 File Offset: 0x0000B838
		private void trackBar1_Scroll_1(object sender, EventArgs e)
		{
			this.flatLabel4.Text = this.trackBar1.Value.ToString();
		}

		// Token: 0x0600001B RID: 27 RVA: 0x0000D608 File Offset: 0x0000B808
		private void trackBar2_Scroll_1(object sender, EventArgs e)
		{
			this.flatLabel3.Text = this.trackBar2.Value.ToString();
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00002090 File Offset: 0x00000290
		private void Form1_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x0600001D RID: 29 RVA: 0x00002090 File Offset: 0x00000290
		private void flatLabel3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600001E RID: 30 RVA: 0x00002090 File Offset: 0x00000290
		private void pictureBox2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600001F RID: 31 RVA: 0x00002093 File Offset: 0x00000293
		private void pictureBox2_Click_1(object sender, EventArgs e)
		{
			Transition.run(this, "Height", 230, new TransitionType_EaseInEaseOut(1000));
		}

		// Token: 0x06000020 RID: 32 RVA: 0x0000D6A0 File Offset: 0x0000B8A0
		private void timer2_Tick(object sender, EventArgs e)
		{
			bool flag = Control.MouseButtons == MouseButtons.Left;
			if (flag)
			{
				int num = 6;
				this.timer2.Interval = num + 5;
				SendKeys.Send("s");
			}
		}

		// Token: 0x06000021 RID: 33 RVA: 0x0000D6E0 File Offset: 0x0000B8E0
		private void Form1_MouseDown(object sender, MouseEventArgs e)
		{
			bool flag = e.Button == MouseButtons.Left;
			if (flag)
			{
				Form1.ReleaseCapture();
				Form1.SendMessage(base.Handle, 161, 2, 0);
			}
		}

		// Token: 0x06000022 RID: 34 RVA: 0x000020B6 File Offset: 0x000002B6
		private void pictureBox3_Click(object sender, EventArgs e)
		{
			Transition.run(this, "Height", 480, new TransitionType_EaseInEaseOut(1000));
		}

		// Token: 0x04000001 RID: 1
		private const int MOUSEEVENTF_MOVE = 1;

		// Token: 0x04000002 RID: 2
		private const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x04000003 RID: 3
		private const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x04000004 RID: 4
		private const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x04000005 RID: 5
		private const int MOUSEEVENTF_RIGHTUP = 22;

		// Token: 0x04000006 RID: 6
		public static int onoff;

		// Token: 0x04000007 RID: 7
		public static int onoffb;

		// Token: 0x04000008 RID: 8
		public static int onoffc;

		// Token: 0x04000009 RID: 9
		public const int WM_NCLBUTTONDOWN = 161;

		// Token: 0x0400000A RID: 10
		public const int HT_CAPTION = 2;

		// Token: 0x0400000B RID: 11
		private bool estado = false;

		// Token: 0x0400000C RID: 12
		private static Random random = new Random();

		// Token: 0x0400000D RID: 13
		private bool use = true;

		// Token: 0x04000010 RID: 16
		private BunifuFlatButton bunifuFlatButton1;

		// Token: 0x04000011 RID: 17
		private BunifuFlatButton bunifuFlatButton2;
	}
}
