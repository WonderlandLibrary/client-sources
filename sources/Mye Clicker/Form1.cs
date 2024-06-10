using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using FlatUI;
using MyeClicker.Properties;
using Siticone.UI.WinForms;

namespace d
{
	// Token: 0x02000004 RID: 4
	public partial class Form1 : Form
	{
		// Token: 0x0600000B RID: 11
		[DllImport("user32", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x0600000C RID: 12
		[DllImport("user32.dll")]
		public static extern bool RegisterHotKey(IntPtr hWnd, int id, int fsModifiers, int vlc);

		// Token: 0x0600000D RID: 13
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x0600000E RID: 14
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowThreadProcessId(IntPtr handle, out int processId);

		// Token: 0x0600000F RID: 15
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr hWnd);

		// Token: 0x06000010 RID: 16
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

		// Token: 0x06000011 RID: 17 RVA: 0x000020F0 File Offset: 0x000002F0
		public static bool ApplicationIsActivated()
		{
			IntPtr foregroundWindow = Form1.GetForegroundWindow();
			bool result;
			if (foregroundWindow == IntPtr.Zero)
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

		// Token: 0x06000012 RID: 18 RVA: 0x00002130 File Offset: 0x00000330
		private string GetCaptionOfActiveWindow()
		{
			string result = string.Empty;
			IntPtr foregroundWindow = Form1.GetForegroundWindow();
			int num = Form1.GetWindowTextLength(foregroundWindow) + 1;
			StringBuilder stringBuilder = new StringBuilder(num);
			if (Form1.GetWindowText(foregroundWindow, stringBuilder, num) > 0)
			{
				result = stringBuilder.ToString();
			}
			return result;
		}

		// Token: 0x06000013 RID: 19 RVA: 0x0000216A File Offset: 0x0000036A
		public static string RandomString(int length)
		{
			return new string((from s in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", length)
			select s[Form1.random.Next(s.Length)]).ToArray<char>());
		}

		// Token: 0x06000014 RID: 20 RVA: 0x000021A5 File Offset: 0x000003A5
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000015 RID: 21
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(Keys vKey);

		// Token: 0x06000016 RID: 22 RVA: 0x000021B3 File Offset: 0x000003B3
		private void siticoneMetroTrackBar1_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000017 RID: 23 RVA: 0x000021B8 File Offset: 0x000003B8
		private void timer1_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			if (this.siticoneTrackBar1.Minimum == 0)
			{
				this.siticoneTrackBar1.Minimum = 1;
			}
			if (this.siticoneMetroTrackBar2.Minimum == 0)
			{
				this.siticoneMetroTrackBar2.Minimum = 1;
			}
			if (!this.checkBox1.Checked)
			{
				random.Next(this.siticoneTrackBar1.Value, this.siticoneMetroTrackBar2.Value);
				if (this.siticoneTrackBar1.Minimum > 0)
				{
					int num = 1000 / this.siticoneTrackBar1.Value;
					int value = this.siticoneMetroTrackBar2.Value;
					int minValue = num + 0;
					int num2 = 1000 / this.siticoneTrackBar1.Value;
					int value2 = this.siticoneMetroTrackBar2.Value;
					int maxValue = num2 + 0;
					this.timer1.Interval = random.Next(minValue, maxValue);
				}
				if (this.siticoneCustomCheckBox2.Checked)
				{
					if ((this.GetCaptionOfActiveWindow().Contains("Minecraft") || this.GetCaptionOfActiveWindow().Contains("Badlion") || this.GetCaptionOfActiveWindow().Contains("Labymod") || this.GetCaptionOfActiveWindow().Contains("OCMC") || this.GetCaptionOfActiveWindow().Contains("Cheatbreaker") || this.GetCaptionOfActiveWindow().Contains("Lunar Client")) && !Form1.ApplicationIsActivated() && Control.MouseButtons == MouseButtons.Left)
					{
						Form1.mouse_event(4, 0, 0, 0, 0);
						Form1.mouse_event(2, 0, 0, 0, 0);
						if (this.trackjitter.Value == 0)
						{
							this.trackjitter.Value = 0;
						}
						int num3 = random.Next(1, this.trackjitter.Value);
						int num4 = random.Next(1, this.trackjitter.Value);
						int x = Control.MousePosition.X;
						int y = Control.MousePosition.Y;
						Cursor.Position = new Point(x - num3, y - num4);
						return;
					}
				}
				else if (!Form1.ApplicationIsActivated() && Control.MouseButtons == MouseButtons.Left)
				{
					Form1.mouse_event(4, 0, 0, 0, 0);
					Form1.mouse_event(2, 0, 0, 0, 0);
					if (this.trackjitter.Value == 0)
					{
						this.trackjitter.Value = 0;
					}
					int num5 = random.Next(0, this.trackjitter.Value);
					int num6 = random.Next(0, this.trackjitter.Value);
					int x2 = Control.MousePosition.X;
					int y2 = Control.MousePosition.Y;
					Cursor.Position = new Point(x2 - num5, y2 - num6);
				}
			}
		}

		// Token: 0x06000018 RID: 24 RVA: 0x00002445 File Offset: 0x00000645
		private void Form1_Load(object sender, EventArgs e)
		{
			this.siticoneShadowForm1.SetShadowForm(this);
		}

		// Token: 0x06000019 RID: 25 RVA: 0x00002453 File Offset: 0x00000653
		private void siticoneCustomCheckBox1_CheckedChanged_1(object sender, EventArgs e)
		{
			if (this.siticoneCustomCheckBox1.Checked)
			{
				base.TopMost = true;
				return;
			}
			base.TopMost = false;
		}

		// Token: 0x0600001A RID: 26 RVA: 0x00002474 File Offset: 0x00000674
		private void timer2_Tick(object sender, EventArgs e)
		{
			if (Form1.GetAsyncKeyState(Keys.F4) < 0)
			{
				Thread.Sleep(155);
				this.siticoneButton7.Checked = true;
				this.timer1.Start();
				this.siticoneButton7.Text = "toggle off";
				this.key2.Start();
				this.key1.Stop();
			}
		}

		// Token: 0x0600001B RID: 27 RVA: 0x000024D4 File Offset: 0x000006D4
		private void timer3_Tick(object sender, EventArgs e)
		{
			if (Form1.GetAsyncKeyState(Keys.F4) < 0)
			{
				Thread.Sleep(155);
				this.siticoneButton7.Checked = false;
				this.siticoneButton7.Text = "toggle";
				this.timer1.Stop();
				this.key1.Start();
				this.key2.Stop();
			}
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00002532 File Offset: 0x00000732
		private void siticoneButton1_Click_1(object sender, EventArgs e)
		{
			MessageBox.Show("LClicker Key: F4");
		}

		// Token: 0x0600001D RID: 29 RVA: 0x0000253F File Offset: 0x0000073F
		private void siticoneControlBox1_Click(object sender, EventArgs e)
		{
			Process.GetCurrentProcess().Kill();
		}

		// Token: 0x0600001E RID: 30 RVA: 0x0000254C File Offset: 0x0000074C
		private void siticoneButton2_Click(object sender, EventArgs e)
		{
			if (this.siticoneButton2.Checked)
			{
				this.pictureBox6.Visible = true;
				this.siticoneMetroTrackBar2.ThumbColor = Color.CornflowerBlue;
				this.siticoneTrackBar1.ThumbColor = Color.CornflowerBlue;
				this.label1.ForeColor = Color.CornflowerBlue;
				this.siticoneCustomCheckBox1.CheckedState.BorderColor = Color.CornflowerBlue;
				this.siticoneCustomCheckBox1.CheckedState.FillColor = Color.CornflowerBlue;
			}
		}

		// Token: 0x0600001F RID: 31 RVA: 0x000021B3 File Offset: 0x000003B3
		private void siticoneGradientButton1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000020 RID: 32 RVA: 0x000021B3 File Offset: 0x000003B3
		private void siticoneCustomCheckBox3_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000021 RID: 33 RVA: 0x000021B3 File Offset: 0x000003B3
		private void siticoneCustomCheckBox2_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000022 RID: 34 RVA: 0x000021B3 File Offset: 0x000003B3
		private void siticoneButton2_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000023 RID: 35 RVA: 0x000021B3 File Offset: 0x000003B3
		private void siticoneGradientButton1_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000024 RID: 36 RVA: 0x000025CC File Offset: 0x000007CC
		private void siticoneTrackBar1_Scroll(object sender, ScrollEventArgs e)
		{
			this.label1.Text = this.siticoneTrackBar1.Value.ToString();
		}

		// Token: 0x06000025 RID: 37 RVA: 0x000021B3 File Offset: 0x000003B3
		private void siticoneTrackBar2_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000026 RID: 38 RVA: 0x000021B3 File Offset: 0x000003B3
		private void siticoneButton1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000027 RID: 39 RVA: 0x000025F7 File Offset: 0x000007F7
		private void siticoneButton3_Click(object sender, EventArgs e)
		{
			this.myeTabControl1.Visible = false;
			this.siticoneWinProgressIndicator1.Visible = true;
			this.label8.Visible = true;
			this.tmrDest.Start();
		}

		// Token: 0x06000028 RID: 40 RVA: 0x00002628 File Offset: 0x00000828
		private void tmrDest_Tick(object sender, EventArgs e)
		{
			this.siticoneWinProgressIndicator1.AutoStart = true;
			this.flatTabControl1.Visible = false;
			this.label1.Visible = false;
			this.label2.Visible = false;
			this.label3.Visible = false;
			this.siticoneCircleProgressBar1.Visible = false;
			this.trackjitter.Visible = false;
			this.label6.Visible = false;
			this.label4.Visible = false;
			this.siticoneButton7.Visible = false;
			this.label7.Visible = false;
			this.label10.Visible = false;
			this.label12.Visible = false;
			this.siticoneCustomCheckBox3.Visible = false;
			this.siticoneCustomCheckBox2.Visible = false;
			this.label8.Visible = false;
			this.checkBox1.Visible = false;
			this.siticoneCustomCheckBox1.Visible = false;
			this.siticoneCustomCheckBox2.Visible = false;
			this.siticoneCustomCheckBox4.Visible = false;
			this.siticoneButton2.Visible = false;
			this.siticoneButton3.Visible = false;
			this.siticoneButton2.Visible = false;
			this.flatTabControl1.Text = null;
			this.label6.Text = null;
			this.tabPage5.Text = null;
			this.tabPage2.Text = null;
			this.label7.Text = null;
			this.label10.Text = null;
			this.label1.Text = null;
			this.label12.Text = null;
			this.label2.Text = null;
			this.label3.Text = null;
			this.label4.Text = null;
			this.label8.Text = null;
			this.checkBox1.Text = null;
			this.siticoneButton2.Text = null;
			this.siticoneButton3.Text = null;
			this.siticoneButton2.Text = null;
			this.flatTabControl1.Dispose();
			this.trackjitter.Dispose();
			this.tabPage5.Dispose();
			this.label6.Dispose();
			this.tabPage2.Dispose();
			this.siticoneCircleProgressBar1.Dispose();
			this.label1.Dispose();
			this.siticoneCustomCheckBox2.Dispose();
			this.label12.Dispose();
			this.label2.Dispose();
			this.label3.Dispose();
			this.label4.Dispose();
			this.label8.Dispose();
			this.label9.Dispose();
			this.siticoneButton2.Dispose();
			this.siticoneButton3.Dispose();
			this.siticoneButton2.Dispose();
			this.timer1.Dispose();
			this.siticoneShadowForm1.Dispose();
			this.siticoneDragControl1.Dispose();
			this.siticoneDragControl2.Dispose();
			this.siticoneControlBox1.Dispose();
			this.siticoneCustomCheckBox1.Dispose();
			this.siticoneCustomCheckBox2.Dispose();
			this.siticoneCustomCheckBox4.Dispose();
			this.checkBox1.Dispose();
			this.siticoneElipse1.Dispose();
			this.siticoneElipse2.Dispose();
			this.siticoneElipse3.Dispose();
			this.siticoneElipse4.Dispose();
			this.pictureBox4.Dispose();
			this.siticoneCustomCheckBox3.Dispose();
			this.siticoneCustomCheckBox2.Dispose();
			this.siticoneButton7.Dispose();
			this.label10.Dispose();
			this.label7.Dispose();
			this.pictureBox6.Dispose();
			this.key1.Dispose();
			this.key2.Dispose();
			this.siticoneControlBox1.Dispose();
			this.siticoneControlBox2.Dispose();
			Thread.Sleep(100);
			this.siticoneWinProgressIndicator1.Visible = false;
			this.label8.Text = null;
			this.label8.Dispose();
			this.siticoneWinProgressIndicator1.Dispose();
			this.tmrDest.Dispose();
			Process.GetCurrentProcess().Kill();
		}

		// Token: 0x06000029 RID: 41 RVA: 0x00002A18 File Offset: 0x00000C18
		private void siticoneButton7_Click(object sender, EventArgs e)
		{
			if (this.siticoneButton7.Checked)
			{
				this.timer1.Start();
				this.siticoneButton7.Text = "toggle off";
				return;
			}
			this.timer1.Stop();
			this.siticoneButton7.Text = "toggle";
		}

		// Token: 0x0600002A RID: 42 RVA: 0x00002A6C File Offset: 0x00000C6C
		private void siticoneCustomCheckBox3_CheckedChanged_1(object sender, EventArgs e)
		{
			if (this.siticoneCustomCheckBox3.Checked)
			{
				this.siticoneTrackBar1.Maximum = 65;
				this.siticoneMetroTrackBar2.Maximum = 65;
				this.siticoneMetroTrackBar2.Value = 65;
				return;
			}
			this.siticoneMetroTrackBar2.Value = 25;
			this.siticoneMetroTrackBar2.Maximum = 25;
			this.siticoneTrackBar1.Maximum = 25;
			this.siticoneTrackBar1.Value = 25;
			this.label1.Text = "25";
		}

		// Token: 0x0600002B RID: 43 RVA: 0x00002AF4 File Offset: 0x00000CF4
		private void trackjitter_Scroll(object sender, ScrollEventArgs e)
		{
			if (this.trackjitter.Value == 0)
			{
				this.siticoneCircleProgressBar1.Value = 0;
			}
			if (this.trackjitter.Value == 1)
			{
				this.siticoneCircleProgressBar1.Value = 1;
			}
			if (this.trackjitter.Value == 2)
			{
				this.siticoneCircleProgressBar1.Value = 2;
			}
			if (this.trackjitter.Value == 3)
			{
				this.siticoneCircleProgressBar1.Value = 3;
			}
			if (this.trackjitter.Value == 4)
			{
				this.siticoneCircleProgressBar1.Value = 4;
			}
			if (this.trackjitter.Value == 5)
			{
				this.siticoneCircleProgressBar1.Value = 5;
			}
			if (this.trackjitter.Value == 6)
			{
				this.siticoneCircleProgressBar1.Value = 6;
			}
			if (this.trackjitter.Value == 7)
			{
				this.siticoneCircleProgressBar1.Value = 7;
			}
			if (this.trackjitter.Value == 8)
			{
				this.siticoneCircleProgressBar1.Value = 8;
			}
			if (this.trackjitter.Value == 9)
			{
				this.siticoneCircleProgressBar1.Value = 9;
			}
			if (this.trackjitter.Value == 10)
			{
				this.siticoneCircleProgressBar1.Value = 10;
			}
			if (this.trackjitter.Value == 11)
			{
				this.siticoneCircleProgressBar1.Value = 11;
			}
			if (this.trackjitter.Value == 12)
			{
				this.siticoneCircleProgressBar1.Value = 12;
			}
			if (this.trackjitter.Value == 13)
			{
				this.siticoneCircleProgressBar1.Value = 13;
			}
			if (this.trackjitter.Value == 14)
			{
				this.siticoneCircleProgressBar1.Value = 14;
			}
			if (this.trackjitter.Value == 15)
			{
				this.siticoneCircleProgressBar1.Value = 15;
			}
			if (this.trackjitter.Value == 16)
			{
				this.siticoneCircleProgressBar1.Value = 16;
			}
			if (this.trackjitter.Value == 17)
			{
				this.siticoneCircleProgressBar1.Value = 17;
			}
			if (this.trackjitter.Value == 18)
			{
				this.siticoneCircleProgressBar1.Value = 18;
			}
			if (this.trackjitter.Value == 19)
			{
				this.siticoneCircleProgressBar1.Value = 19;
			}
			if (this.trackjitter.Value == 20)
			{
				this.siticoneCircleProgressBar1.Value = 20;
			}
			if (this.trackjitter.Value == 21)
			{
				this.siticoneCircleProgressBar1.Value = 21;
			}
			if (this.trackjitter.Value == 22)
			{
				this.siticoneCircleProgressBar1.Value = 22;
			}
			if (this.trackjitter.Value == 23)
			{
				this.siticoneCircleProgressBar1.Value = 23;
			}
			if (this.trackjitter.Value == 24)
			{
				this.siticoneCircleProgressBar1.Value = 24;
			}
			if (this.trackjitter.Value == 25)
			{
				this.siticoneCircleProgressBar1.Value = 25;
			}
			if (this.trackjitter.Value == 26)
			{
				this.siticoneCircleProgressBar1.Value = 26;
			}
			if (this.trackjitter.Value == 27)
			{
				this.siticoneCircleProgressBar1.Value = 27;
			}
			if (this.trackjitter.Value == 28)
			{
				this.siticoneCircleProgressBar1.Value = 28;
			}
			if (this.trackjitter.Value == 29)
			{
				this.siticoneCircleProgressBar1.Value = 29;
			}
			if (this.trackjitter.Value == 30)
			{
				this.siticoneCircleProgressBar1.Value = 30;
			}
			if (this.trackjitter.Value == 31)
			{
				this.siticoneCircleProgressBar1.Value = 31;
			}
			if (this.trackjitter.Value == 32)
			{
				this.siticoneCircleProgressBar1.Value = 32;
			}
			if (this.trackjitter.Value == 33)
			{
				this.siticoneCircleProgressBar1.Value = 33;
			}
			if (this.trackjitter.Value == 34)
			{
				this.siticoneCircleProgressBar1.Value = 34;
			}
			if (this.trackjitter.Value == 35)
			{
				this.siticoneCircleProgressBar1.Value = 35;
			}
			if (this.trackjitter.Value == 36)
			{
				this.siticoneCircleProgressBar1.Value = 36;
			}
			if (this.trackjitter.Value == 37)
			{
				this.siticoneCircleProgressBar1.Value = 37;
			}
			if (this.trackjitter.Value == 38)
			{
				this.siticoneCircleProgressBar1.Value = 38;
			}
			if (this.trackjitter.Value == 39)
			{
				this.siticoneCircleProgressBar1.Value = 39;
			}
			if (this.trackjitter.Value == 40)
			{
				this.siticoneCircleProgressBar1.Value = 40;
			}
			if (this.trackjitter.Value == 41)
			{
				this.siticoneCircleProgressBar1.Value = 41;
			}
			if (this.trackjitter.Value == 42)
			{
				this.siticoneCircleProgressBar1.Value = 42;
			}
			if (this.trackjitter.Value == 43)
			{
				this.siticoneCircleProgressBar1.Value = 43;
			}
			if (this.trackjitter.Value == 44)
			{
				this.siticoneCircleProgressBar1.Value = 44;
			}
			if (this.trackjitter.Value == 45)
			{
				this.siticoneCircleProgressBar1.Value = 45;
			}
			if (this.trackjitter.Value == 46)
			{
				this.siticoneCircleProgressBar1.Value = 46;
			}
			if (this.trackjitter.Value == 47)
			{
				this.siticoneCircleProgressBar1.Value = 47;
			}
			if (this.trackjitter.Value == 48)
			{
				this.siticoneCircleProgressBar1.Value = 48;
			}
			if (this.trackjitter.Value == 49)
			{
				this.siticoneCircleProgressBar1.Value = 49;
			}
			if (this.trackjitter.Value == 50)
			{
				this.siticoneCircleProgressBar1.Value = 50;
			}
		}

		// Token: 0x04000004 RID: 4
		private const int MOUSEEVENTF_MOVE = 1;

		// Token: 0x04000005 RID: 5
		private const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x04000006 RID: 6
		private const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x04000007 RID: 7
		private const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x04000008 RID: 8
		private const int MOUSEEVENTF_RIGHTUP = 22;

		// Token: 0x04000009 RID: 9
		private static Random random = new Random();
	}
}
