using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Media;
using System.Reflection;
using System.Resources;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Guna.UI2.AnimatorNS;
using Guna.UI2.WinForms;
using ns1;
using ns15;
using ns16;
using ns2;
using ns23;
using ns27;
using ns3;
using ns8;
using Siticone.UI.WinForms;

namespace stable-src
{
	// Token: 0x02000038 RID: 56
	public partial class Form1 : Form
	{
		// Token: 0x06000159 RID: 345
		[DllImport("User32.Dll")]
		private static extern bool PostMessageA(IntPtr intptr_1, uint uint_0, int int_9, int int_10);

		// Token: 0x0600015A RID: 346
		[DllImport("user32.dll")]
		public static extern int SendMessage(IntPtr intptr_1, int int_9, int int_10, int int_11);

		// Token: 0x0600015B RID: 347
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x0600015C RID: 348
		[DllImport("user32.dll", SetLastError = true)]
		private static extern IntPtr FindWindow(string string_1, string string_2);

		// Token: 0x0600015D RID: 349
		[DllImport("User32.dll")]
		private static extern short GetAsyncKeyState(Keys keys_0);

		// Token: 0x0600015E RID: 350
		[DllImport("User32.dll")]
		private static extern bool SetCursorPos(int int_9, int int_10);

		// Token: 0x0600015F RID: 351
		[DllImport("kernel32")]
		private static extern long WritePrivateProfileString(string string_1, string string_2, string string_3, string string_4);

		// Token: 0x06000160 RID: 352
		[DllImport("kernel32")]
		private static extern int GetPrivateProfileString(string string_1, string string_2, string string_3, StringBuilder stringBuilder_0, int int_9, string string_4);

		// Token: 0x06000161 RID: 353 RVA: 0x0000DC6C File Offset: 0x0000BE6C
		public Form1()
		{
			this.InitializeComponent();
			this.soundPlayer_0 = new SoundPlayer(Class29.UnmanagedMemoryStream_1);
			this.soundPlayer_1 = new SoundPlayer(Class29.UnmanagedMemoryStream_0);
			this.soundPlayer_2 = new SoundPlayer(Class29.UnmanagedMemoryStream_4);
			this.soundPlayer_3 = new SoundPlayer(Class29.UnmanagedMemoryStream_3);
			this.soundPlayer_4 = new SoundPlayer(Class29.UnmanagedMemoryStream_2);
		}

		// Token: 0x06000162 RID: 354 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_0(object object_0)
		{
		}

		// Token: 0x06000163 RID: 355 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_1(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000164 RID: 356 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_2(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000165 RID: 357 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_3(object sender, EventArgs e)
		{
		}

		// Token: 0x06000166 RID: 358 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_4(object sender, EventArgs e)
		{
		}

		// Token: 0x06000167 RID: 359 RVA: 0x000026A4 File Offset: 0x000008A4
		private void Form1_Load(object sender, EventArgs e)
		{
			this.timer_11.Start();
		}

		// Token: 0x06000168 RID: 360 RVA: 0x0000DD1C File Offset: 0x0000BF1C
		public void method_5()
		{
			Process[] processesByName = Process.GetProcessesByName("javaw");
			foreach (Process process in processesByName)
			{
				this.intptr_0 = Form1.FindWindow(null, process.MainWindowTitle);
			}
			string text = this.method_6();
			if (text != null)
			{
			}
		}

		// Token: 0x06000169 RID: 361 RVA: 0x0000DD6C File Offset: 0x0000BF6C
		public string method_6()
		{
			try
			{
				IntPtr foregroundWindow = Form1.GetForegroundWindow();
				Process[] processes = Process.GetProcesses();
				foreach (Process process in processes)
				{
					if (foregroundWindow == process.MainWindowHandle)
					{
						return process.ProcessName;
					}
				}
			}
			catch
			{
			}
			return null;
		}

		// Token: 0x0600016A RID: 362 RVA: 0x0000DDD4 File Offset: 0x0000BFD4
		public void method_7(ref string string_1)
		{
			Process[] processesByName = Process.GetProcessesByName(string_1);
			foreach (Process process in processesByName)
			{
				this.intptr_0 = Form1.FindWindow(null, process.MainWindowTitle);
			}
			string text = this.method_6();
			if (text != null)
			{
			}
		}

		// Token: 0x0600016B RID: 363 RVA: 0x0000DE20 File Offset: 0x0000C020
		private void timer_0_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			Random random2 = new Random();
			try
			{
				if (this.siticoneCheckBox1.Checked && this.BTN.Checked)
				{
					this.timer_0.Interval = 4000 / this.siticoneHScrollBar3.Value;
				}
				else if (this.siticoneCheckBox1.Checked && !this.BTN.Checked)
				{
					this.timer_0.Interval = 4000 / this.siticoneHScrollBar1.Value;
				}
			}
			catch
			{
			}
			if (this.siticoneCheckBox1.Checked && this.siticoneCheckBox1.Checked)
			{
				Process[] processesByName = Process.GetProcessesByName(this.skeetTextBox1.Text);
				foreach (Process process in processesByName)
				{
					this.intptr_0 = Form1.FindWindow(null, process.MainWindowTitle);
				}
				if (!this.intptr_0.Equals(IntPtr.Zero))
				{
					if (Control.MouseButtons == MouseButtons.Left && !this.siticoneCheckBox9.Checked)
					{
						Form1.PostMessageA(this.intptr_0, 513U, 0, 0);
						Thread.Sleep(random2.Next(1, 43));
						Form1.PostMessageA(this.intptr_0, 514U, 0, 0);
					}
					Cursor cursor_ = Cursor.Current;
					if (cursor_.smethod_1() && this.siticoneCheckBox9.Checked && Control.MouseButtons == MouseButtons.Left)
					{
						Form1.PostMessageA(this.intptr_0, 513U, 0, 0);
						Thread.Sleep(random2.Next(1, 43));
						Form1.PostMessageA(this.intptr_0, 514U, 0, 0);
					}
					if (this.siticoneCheckBox1.Checked && this.WTAP.Checked && Class10.smethod_2().ProcessName == "javaw" && Form1.GetAsyncKeyState(Keys.LButton) < 0)
					{
						this.int_0 = 1;
						int num = random.Next(0, 100);
						if (num <= this.siticoneHScrollBar5.Value)
						{
							SendKeys.Send("w");
						}
					}
					if (this.siticoneCheckBox1.Checked && this.STAP.Checked && Class10.smethod_2().ProcessName == "javaw" && Form1.GetAsyncKeyState(Keys.LButton) < 0)
					{
						this.int_0 = 1;
						int num2 = random.Next(0, 100);
						if (num2 <= this.siticoneHScrollBar6.Value)
						{
							SendKeys.Send("s");
						}
					}
					if (this.siticoneCheckBox4.Checked && this.siticoneCheckBox7.Checked && Form1.GetAsyncKeyState(Keys.LButton) < 0)
					{
						this.int_0 = 1;
						int num3 = random.Next(0, 100);
						if (num3 <= this.sdChance.Value)
						{
							Form1.PostMessageA(this.intptr_0, 516U, 0, 0);
							Thread.Sleep(random.Next(1, 7));
							Form1.PostMessageA(this.intptr_0, 517U, 0, 0);
						}
					}
					if (this.siticoneCheckBox4.Checked && (Form1.GetAsyncKeyState(Keys.LButton) < 0 && Form1.GetAsyncKeyState(Keys.RButton) < 0))
					{
						Form1.PostMessageA(this.intptr_0, 513U, 0, 0);
						Thread.Sleep(random2.Next(1, 43));
						Form1.PostMessageA(this.intptr_0, 514U, 0, 0);
						this.int_0 = 1;
						int num4 = random.Next(0, 100);
						if (num4 <= this.sdChance.Value)
						{
							Form1.PostMessageA(this.intptr_0, 516U, 0, 0);
							Thread.Sleep(random.Next(1, 7));
							Form1.PostMessageA(this.intptr_0, 517U, 0, 0);
						}
					}
					if (this.siticoneCheckBox1.Checked && this.siticoneCheckBox5.Checked && Form1.GetAsyncKeyState(Keys.LButton) < 0)
					{
						if (this.skeetComboBox1.Text == "g502")
						{
							this.soundPlayer_0.Play();
						}
						if (this.skeetComboBox1.Text == "g303")
						{
							this.soundPlayer_1.Play();
						}
						if (this.skeetComboBox1.Text == "hp")
						{
							this.soundPlayer_3.Play();
						}
						if (this.skeetComboBox1.Text == "microsoft")
						{
							this.soundPlayer_2.Play();
						}
						if (this.skeetComboBox1.Text == "gpro")
						{
							this.soundPlayer_4.Play();
						}
					}
				}
			}
		}

		// Token: 0x0600016C RID: 364 RVA: 0x00006AC4 File Offset: 0x00004CC4
		public static int smethod_0(int int_9, int int_10)
		{
			return int_10 << 16 | (int_9 & 65535);
		}

		// Token: 0x0600016D RID: 365 RVA: 0x0000E2E8 File Offset: 0x0000C4E8
		private void siticoneHScrollBar1_Scroll(object sender, ScrollEventArgs e)
		{
			this.label2.Text = (this.siticoneHScrollBar1.Value / 4).ToString() + " cps";
		}

		// Token: 0x0600016E RID: 366 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_8(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x0600016F RID: 367 RVA: 0x0000E320 File Offset: 0x0000C520
		private void siticoneCheckBox1_CheckedChanged(object sender, EventArgs e)
		{
			if (this.siticoneCheckBox1.Checked)
			{
				this.timer_0.Start();
			}
			if (!this.siticoneCheckBox1.Checked)
			{
				this.timer_0.Stop();
			}
		}

		// Token: 0x06000170 RID: 368 RVA: 0x0000E360 File Offset: 0x0000C560
		private void timer_1_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			try
			{
				this.timer_1.Interval = 4000 / this.siticoneHScrollBar2.Value;
			}
			catch
			{
			}
			if (this.siticoneCheckBox2.Checked)
			{
				if (Control.MouseButtons == MouseButtons.Right)
				{
					IntPtr foregroundWindow = Form1.GetForegroundWindow();
					Form1.SendMessage(foregroundWindow, 515, 1, Form1.smethod_0(Control.MousePosition.X, Control.MousePosition.Y));
					Thread.Sleep(random.Next(1, 5));
					Form1.SendMessage(foregroundWindow, 516, 1, Form1.smethod_0(Control.MousePosition.X, Control.MousePosition.Y));
					Thread.Sleep(random.Next(1, 43));
				}
				if (this.siticoneCheckBox2.Checked && this.siticoneCheckBox5.Checked && Form1.GetAsyncKeyState(Keys.LButton) < 0)
				{
					if (this.skeetComboBox1.Text == "g502")
					{
						this.soundPlayer_0.Play();
					}
					if (this.skeetComboBox1.Text == "g303")
					{
						this.soundPlayer_1.Play();
					}
					if (this.skeetComboBox1.Text == "hp")
					{
						this.soundPlayer_3.Play();
					}
					if (this.skeetComboBox1.Text == "microsoft")
					{
						this.soundPlayer_2.Play();
					}
					if (this.skeetComboBox1.Text == "gpro")
					{
						this.soundPlayer_4.Play();
					}
				}
			}
		}

		// Token: 0x06000171 RID: 369 RVA: 0x0000E518 File Offset: 0x0000C718
		private void siticoneHScrollBar2_Scroll(object sender, ScrollEventArgs e)
		{
			this.label3.Text = (this.siticoneHScrollBar2.Value / 4).ToString() + " cps";
		}

		// Token: 0x06000172 RID: 370 RVA: 0x0000E550 File Offset: 0x0000C750
		private void siticoneCheckBox2_CheckedChanged(object sender, EventArgs e)
		{
			if (this.siticoneCheckBox2.Checked)
			{
				this.timer_1.Start();
			}
			if (!this.siticoneCheckBox2.Checked)
			{
				this.timer_1.Stop();
			}
		}

		// Token: 0x06000173 RID: 371 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_9(object sender, EventArgs e)
		{
		}

		// Token: 0x06000174 RID: 372 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_10(object sender, EventArgs e)
		{
		}

		// Token: 0x06000175 RID: 373 RVA: 0x0000E590 File Offset: 0x0000C790
		private void method_11(object sender, KeyEventArgs e)
		{
			string text = e.KeyData.ToString();
			if (!text.Contains("Alt"))
			{
				if (Form1.GetAsyncKeyState(Keys.Escape) < 0)
				{
					this.bindBtn.Text = "none";
				}
				else
				{
					this.bindBtn.Text = text;
				}
			}
		}

		// Token: 0x06000176 RID: 374 RVA: 0x0000E5EC File Offset: 0x0000C7EC
		private void timer_2_Tick(object sender, EventArgs e)
		{
			if (this.bindBtn.Text != "none" && this.bindBtn.Text != "...")
			{
				Keys keys_ = (Keys)this.keysConverter_0.ConvertFromString(this.bindBtn.Text.Replace("...", ""));
				if (Form1.GetAsyncKeyState(keys_) < 0)
				{
					while (Form1.GetAsyncKeyState(keys_) < 0)
					{
						Thread.Sleep(20);
					}
					if (!this.siticoneCheckBox1.Checked)
					{
						this.siticoneCheckBox1.Checked = true;
					}
					else if (this.siticoneCheckBox1.Checked)
					{
						this.siticoneCheckBox1.Checked = false;
					}
				}
			}
		}

		// Token: 0x06000177 RID: 375 RVA: 0x000026B1 File Offset: 0x000008B1
		private void bindBtn_Click(object sender, EventArgs e)
		{
			this.timer_2.Start();
			this.bindBtn.Text = "...";
		}

		// Token: 0x06000178 RID: 376 RVA: 0x0000E590 File Offset: 0x0000C790
		private void bindBtn_KeyDown(object sender, KeyEventArgs e)
		{
			string text = e.KeyData.ToString();
			if (!text.Contains("Alt"))
			{
				if (Form1.GetAsyncKeyState(Keys.Escape) < 0)
				{
					this.bindBtn.Text = "none";
				}
				else
				{
					this.bindBtn.Text = text;
				}
			}
		}

		// Token: 0x06000179 RID: 377 RVA: 0x000026CE File Offset: 0x000008CE
		private void bindBtn2_Click(object sender, EventArgs e)
		{
			this.timer_3.Start();
			this.bindBtn2.Text = "...";
		}

		// Token: 0x0600017A RID: 378 RVA: 0x0000E6B0 File Offset: 0x0000C8B0
		private void timer_3_Tick(object sender, EventArgs e)
		{
			if (this.bindBtn2.Text != "none" && this.bindBtn2.Text != "...")
			{
				Keys keys_ = (Keys)this.keysConverter_0.ConvertFromString(this.bindBtn2.Text.Replace("...", ""));
				if (Form1.GetAsyncKeyState(keys_) < 0)
				{
					while (Form1.GetAsyncKeyState(keys_) < 0)
					{
						Thread.Sleep(20);
					}
					if (!this.siticoneCheckBox2.Checked)
					{
						this.siticoneCheckBox2.Checked = true;
					}
					else if (this.siticoneCheckBox2.Checked)
					{
						this.siticoneCheckBox2.Checked = false;
					}
				}
			}
		}

		// Token: 0x0600017B RID: 379 RVA: 0x0000E774 File Offset: 0x0000C974
		private void bindBtn2_KeyDown(object sender, KeyEventArgs e)
		{
			string text = e.KeyData.ToString();
			if (!text.Contains("Alt"))
			{
				if (Form1.GetAsyncKeyState(Keys.Escape) < 0)
				{
					this.bindBtn2.Text = "none";
				}
				else
				{
					this.bindBtn2.Text = text;
				}
			}
		}

		// Token: 0x0600017C RID: 380 RVA: 0x0000E7D0 File Offset: 0x0000C9D0
		private void siticoneButton3_Click(object sender, EventArgs e)
		{
			this.siticoneButton3.Visible = false;
			this.panel1.Width = 182;
			this.guna2Transition_0.ShowSync(this.panel1, false, null);
			this.skeetGroupBox5.Visible = false;
			this.skeetGroupBox6.Visible = false;
		}

		// Token: 0x0600017D RID: 381 RVA: 0x000026EB File Offset: 0x000008EB
		private void method_12(object sender, EventArgs e)
		{
			this.panel1.Width = 1;
			this.siticoneButton3.Visible = true;
			this.guna2Transition_0.ShowSync(this.panel1, false, null);
		}

		// Token: 0x0600017E RID: 382 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_13(object sender, EventArgs e)
		{
		}

		// Token: 0x0600017F RID: 383 RVA: 0x0000E824 File Offset: 0x0000CA24
		private void siticoneCheckBox3_CheckedChanged(object sender, EventArgs e)
		{
			if (this.siticoneCheckBox3.Checked)
			{
				base.ShowInTaskbar = false;
			}
			if (!this.siticoneCheckBox3.Checked)
			{
				base.ShowInTaskbar = true;
			}
		}

		// Token: 0x06000180 RID: 384 RVA: 0x00002718 File Offset: 0x00000918
		private void method_14(object sender, EventArgs e)
		{
			base.Hide();
			this.timer_4.Start();
		}

		// Token: 0x06000181 RID: 385 RVA: 0x0000E85C File Offset: 0x0000CA5C
		private void timer_4_Tick(object sender, EventArgs e)
		{
			if (Form1.GetAsyncKeyState(Keys.RControlKey) < 0)
			{
				base.Show();
				this.timer_4.Stop();
			}
		}

		// Token: 0x06000182 RID: 386 RVA: 0x0000272B File Offset: 0x0000092B
		private void method_15(object sender, EventArgs e)
		{
			Environment.Exit(1);
		}

		// Token: 0x06000183 RID: 387 RVA: 0x0000E88C File Offset: 0x0000CA8C
		private void siticoneButton2_Click(object sender, EventArgs e)
		{
			this.skeetGroupBox5.Visible = false;
			this.skeetGroupBox2.Visible = false;
			this.skeetGroupBox3.Visible = true;
			this.skeetGroupBox1.Visible = false;
			this.skeetGroupBox3.Location = new Point(12, 47);
			this.panel1.Visible = false;
			this.label5.Visible = true;
			this.skeetGroupBox4.Visible = false;
			this.siticoneButton3.Visible = true;
		}

		// Token: 0x06000184 RID: 388 RVA: 0x0000E910 File Offset: 0x0000CB10
		private void siticoneButton1_Click(object sender, EventArgs e)
		{
			this.skeetGroupBox5.Visible = false;
			this.skeetGroupBox3.Visible = false;
			this.skeetGroupBox1.Visible = true;
			this.panel1.Visible = false;
			this.label5.Visible = true;
			this.siticoneButton3.Visible = true;
			this.skeetGroupBox4.Visible = false;
			this.skeetGroupBox2.Visible = true;
		}

		// Token: 0x06000185 RID: 389 RVA: 0x0000E980 File Offset: 0x0000CB80
		private void method_16(object sender, EventArgs e)
		{
			Rectangle bounds = Screen.PrimaryScreen.Bounds;
			Rectangle bounds2 = base.Bounds;
			Form1.SetCursorPos(910, 540);
		}

		// Token: 0x06000186 RID: 390 RVA: 0x0000E9B0 File Offset: 0x0000CBB0
		private void timer_5_Tick(object sender, EventArgs e)
		{
			if (this.BTN.Checked && !this.extreme.Checked)
			{
				this.int_7 = this.siticoneHScrollBar1.Value - 15;
				this.int_8 = this.siticoneHScrollBar1.Value + 10;
				Random random = new Random();
				this.siticoneHScrollBar3.Value = random.Next(this.int_7, this.int_8);
			}
			if (this.extreme.Checked && this.BTN.Checked)
			{
				this.int_7 = this.siticoneHScrollBar1.Value - 30;
				this.int_8 = this.siticoneHScrollBar1.Value + 30;
				Random random2 = new Random();
				this.siticoneHScrollBar3.Value = random2.Next(this.int_7, this.int_8);
			}
			if (this.BTN.Checked && this.RangeSettings.Checked)
			{
				this.int_7 = this.siticoneHScrollBar1.Value - this.MinRage.Value;
				this.int_8 = this.siticoneHScrollBar1.Value + this.MaxRange.Value;
				Random random3 = new Random();
				this.siticoneHScrollBar3.Value = random3.Next(this.int_7, this.int_8);
				if (this.MinRage.Value == this.MaxRange.Value)
				{
					this.MaxRange.Value = this.MinRage.Value;
				}
			}
		}

		// Token: 0x06000187 RID: 391 RVA: 0x0000EB40 File Offset: 0x0000CD40
		private void BTN_CheckedChanged(object sender, EventArgs e)
		{
			if (this.BTN.Checked)
			{
				this.timer_5.Start();
				this.timer_6.Stop();
			}
			else
			{
				this.timer_5.Stop();
				this.timer_6.Start();
			}
		}

		// Token: 0x06000188 RID: 392 RVA: 0x000021D4 File Offset: 0x000003D4
		private void timer_6_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x06000189 RID: 393 RVA: 0x000021D4 File Offset: 0x000003D4
		private void extreme_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x0600018A RID: 394 RVA: 0x000021D4 File Offset: 0x000003D4
		private void skeetGroupBox1_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x0600018B RID: 395 RVA: 0x0000EB88 File Offset: 0x0000CD88
		private void MaxRange_Scroll(object sender, ScrollEventArgs e)
		{
			this.max2.Text = (this.MaxRange.Value / 4).ToString() + " cps";
		}

		// Token: 0x0600018C RID: 396 RVA: 0x0000EBC0 File Offset: 0x0000CDC0
		private void MinRage_Scroll(object sender, ScrollEventArgs e)
		{
			this.min2.Text = (this.MinRage.Value / 4).ToString() + " cps";
		}

		// Token: 0x0600018D RID: 397 RVA: 0x000021D4 File Offset: 0x000003D4
		private void RangeSettings_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x0600018E RID: 398 RVA: 0x0000EBF8 File Offset: 0x0000CDF8
		private void skeetButton1_Click(object sender, EventArgs e)
		{
			string friendlyName = AppDomain.CurrentDomain.FriendlyName;
			DirectoryInfo directoryInfo = new DirectoryInfo("C:\\Windows\\Prefetch");
			FileInfo[] files = directoryInfo.GetFiles(friendlyName + "*");
			foreach (FileInfo fileInfo in files)
			{
				File.Delete(fileInfo.FullName);
			}
			Environment.Exit(0);
		}

		// Token: 0x0600018F RID: 399 RVA: 0x00002718 File Offset: 0x00000918
		private void method_17(object sender, EventArgs e)
		{
			base.Hide();
			this.timer_4.Start();
		}

		// Token: 0x06000190 RID: 400 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_18(object sender, EventArgs e)
		{
		}

		// Token: 0x06000191 RID: 401 RVA: 0x000021D4 File Offset: 0x000003D4
		private void timer_7_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x06000192 RID: 402 RVA: 0x000021D4 File Offset: 0x000003D4
		private void siticoneButton7_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000193 RID: 403 RVA: 0x0000EC5C File Offset: 0x0000CE5C
		private void siticoneHScrollBar3_Scroll(object sender, ScrollEventArgs e)
		{
			this.randomCps.Text = (this.siticoneHScrollBar3.Value / 4).ToString() + " cps";
		}

		// Token: 0x06000194 RID: 404 RVA: 0x0000EC94 File Offset: 0x0000CE94
		private void sdChance_Scroll(object sender, ScrollEventArgs e)
		{
			this.label12.Text = "Chance: " + this.sdChance.Value.ToString();
		}

		// Token: 0x06000195 RID: 405 RVA: 0x00002733 File Offset: 0x00000933
		private void siticoneHScrollBar4_Scroll(object sender, ScrollEventArgs e)
		{
			this.timer_5.Interval = this.siticoneHScrollBar4.Value;
		}

		// Token: 0x06000196 RID: 406 RVA: 0x000021D4 File Offset: 0x000003D4
		private void skeetGroupBox4_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x06000197 RID: 407 RVA: 0x0000ECCC File Offset: 0x0000CECC
		private void pictureBox4_Click(object sender, EventArgs e)
		{
			this.label31.Text = "Current Config: MMC";
			this.siticoneHScrollBar1.Value = 48;
			this.sdChance.Value = 20;
			this.BTN.Checked = true;
			this.siticoneHScrollBar4.Value = 160;
			this.MinRage.Value = 22;
			this.MaxRange.Value = 8;
			this.siticoneCheckBox1.Checked = true;
			this.siticoneCheckBox4.Checked = true;
			this.RangeSettings.Checked = true;
			this.BTN.Checked = true;
		}

		// Token: 0x06000198 RID: 408 RVA: 0x0000ED68 File Offset: 0x0000CF68
		private void pictureBox5_Click(object sender, EventArgs e)
		{
			this.label31.Text = "Current Config: Lunar";
			this.siticoneHScrollBar1.Value = 44;
			this.sdChance.Value = 20;
			this.BTN.Checked = true;
			this.siticoneHScrollBar4.Value = 160;
			this.MinRage.Value = 13;
			this.MaxRange.Value = 4;
			this.siticoneCheckBox1.Checked = true;
			this.siticoneCheckBox4.Checked = true;
			this.RangeSettings.Checked = true;
			this.BTN.Checked = true;
		}

		// Token: 0x06000199 RID: 409 RVA: 0x0000EE04 File Offset: 0x0000D004
		private void pictureBox8_Click(object sender, EventArgs e)
		{
			this.label31.Text = "Current Config: PVP.land";
			this.siticoneHScrollBar1.Value = 44;
			this.sdChance.Value = 20;
			this.BTN.Checked = true;
			this.siticoneHScrollBar4.Value = 180;
			this.MinRage.Value = 13;
			this.MaxRange.Value = 4;
			this.siticoneCheckBox1.Checked = true;
			this.siticoneCheckBox4.Checked = true;
			this.RangeSettings.Checked = true;
			this.BTN.Checked = true;
		}

		// Token: 0x0600019A RID: 410 RVA: 0x0000EEA0 File Offset: 0x0000D0A0
		private void pictureBox10_Click(object sender, EventArgs e)
		{
			this.label31.Text = "Current Config: Hypixel";
			this.siticoneHScrollBar1.Value = 68;
			this.sdChance.Value = 20;
			this.BTN.Checked = true;
			this.siticoneHScrollBar4.Value = 160;
			this.MinRage.Value = 13;
			this.MaxRange.Value = 4;
			this.siticoneCheckBox1.Checked = true;
			this.siticoneCheckBox4.Checked = true;
			this.RangeSettings.Checked = true;
			this.BTN.Checked = true;
		}

		// Token: 0x0600019B RID: 411 RVA: 0x000021D4 File Offset: 0x000003D4
		private void panel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600019C RID: 412 RVA: 0x0000EF3C File Offset: 0x0000D13C
		private void siticoneButton10_Click(object sender, EventArgs e)
		{
			this.skeetGroupBox5.Visible = false;
			this.skeetGroupBox2.Visible = false;
			this.skeetGroupBox4.Visible = true;
			this.skeetGroupBox1.Visible = false;
			this.skeetGroupBox4.Location = new Point(12, 47);
			this.panel1.Visible = false;
			this.label5.Visible = true;
			this.siticoneButton3.Visible = true;
		}

		// Token: 0x0600019D RID: 413 RVA: 0x000021D4 File Offset: 0x000003D4
		private void siticoneHScrollBar1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600019E RID: 414 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_19(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600019F RID: 415 RVA: 0x000021D4 File Offset: 0x000003D4
		private void siticoneCheckBox4_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060001A0 RID: 416 RVA: 0x000021D4 File Offset: 0x000003D4
		private void siticoneCheckBox5_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060001A1 RID: 417 RVA: 0x0000EFB4 File Offset: 0x0000D1B4
		private void timer_9_Tick(object sender, EventArgs e)
		{
			if (this.bindbtn3.Text != "none" && this.bindbtn3.Text != "...")
			{
				Keys keys_ = (Keys)this.keysConverter_0.ConvertFromString(this.bindbtn3.Text.Replace("...", ""));
				if (Form1.GetAsyncKeyState(keys_) < 0)
				{
					while (Form1.GetAsyncKeyState(keys_) < 0)
					{
						Thread.Sleep(20);
					}
					if (!this.siticoneCheckBox6.Checked)
					{
						this.siticoneCheckBox6.Checked = true;
						base.Show();
					}
					else if (this.siticoneCheckBox6.Checked)
					{
						base.Hide();
						this.siticoneCheckBox6.Checked = false;
					}
				}
			}
		}

		// Token: 0x060001A2 RID: 418 RVA: 0x0000F084 File Offset: 0x0000D284
		private void bindbtn3_KeyDown(object sender, KeyEventArgs e)
		{
			string text = e.KeyData.ToString();
			if (!text.Contains("Alt"))
			{
				if (Form1.GetAsyncKeyState(Keys.Escape) < 0)
				{
					this.bindbtn3.Text = "none";
				}
				else
				{
					this.bindbtn3.Text = text;
				}
			}
		}

		// Token: 0x060001A3 RID: 419 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_20(object sender, EventArgs e)
		{
		}

		// Token: 0x060001A4 RID: 420 RVA: 0x0000274B File Offset: 0x0000094B
		private void bindbtn3_Click(object sender, EventArgs e)
		{
			this.timer_9.Start();
			this.bindbtn3.Text = "...";
		}

		// Token: 0x060001A5 RID: 421 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_21(object sender, EventArgs e)
		{
		}

		// Token: 0x060001A6 RID: 422 RVA: 0x00002768 File Offset: 0x00000968
		private void method_22(object sender, EventArgs e)
		{
			Process.Start("https://cdn.discordapp.com/attachments/852571138281439262/880182253114765322/g502.wav");
		}

		// Token: 0x060001A7 RID: 423 RVA: 0x0000F0E0 File Offset: 0x0000D2E0
		private void siticoneHScrollBar5_Scroll(object sender, ScrollEventArgs e)
		{
			this.label28.Text = (this.siticoneHScrollBar5.Value / 4).ToString() + " chance";
		}

		// Token: 0x060001A8 RID: 424 RVA: 0x0000F118 File Offset: 0x0000D318
		private void siticoneHScrollBar6_Scroll(object sender, ScrollEventArgs e)
		{
			this.label30.Text = (this.siticoneHScrollBar6.Value / 4).ToString() + " chance";
		}

		// Token: 0x060001A9 RID: 425 RVA: 0x0000F150 File Offset: 0x0000D350
		private void siticoneButton15_Click(object sender, EventArgs e)
		{
			this.skeetGroupBox2.Visible = false;
			this.skeetGroupBox4.Visible = false;
			this.skeetGroupBox1.Visible = false;
			this.skeetGroupBox5.Visible = true;
			this.skeetGroupBox5.Location = new Point(12, 47);
			this.panel1.Visible = false;
			this.label5.Visible = true;
			this.panel1.Visible = false;
			this.siticoneButton3.Visible = true;
		}

		// Token: 0x060001AA RID: 426 RVA: 0x00002775 File Offset: 0x00000975
		public void method_23(string string_1, string string_2, string string_3)
		{
			Form1.WritePrivateProfileString(string_1, string_2, string_3.ToLower(), this.string_0);
		}

		// Token: 0x060001AB RID: 427 RVA: 0x0000F1D4 File Offset: 0x0000D3D4
		private void skeetButton2_Click(object sender, EventArgs e)
		{
			SaveFileDialog saveFileDialog = new SaveFileDialog();
			saveFileDialog.Title = "glassware config browser";
			saveFileDialog.Filter = "glass cfg file | *.ini";
			if (saveFileDialog.ShowDialog() == DialogResult.OK)
			{
				int value = this.siticoneHScrollBar1.Value;
				int value2 = this.siticoneHScrollBar2.Value;
				int value3 = this.siticoneHScrollBar3.Value;
				int value4 = this.siticoneHScrollBar4.Value;
				int value5 = this.sdChance.Value;
				int value6 = this.MinRage.Value;
				int value7 = this.MaxRange.Value;
				Class26 @class = new Class26(saveFileDialog.FileName);
				@class.method_0("glassware config system", "name", this.skeetTextBox2.Text);
				@class.method_0("glassware config system", "Slider1", value.ToString());
				@class.method_0("glassware config system", "Slider2", value2.ToString());
				@class.method_0("glassware config system", "Slider3", value3.ToString());
				@class.method_0("glassware config system", "Slider4", value4.ToString());
				@class.method_0("glassware config system", "BlockHitChance", value5.ToString());
				@class.method_0("glassware config system", "minrange", value6.ToString());
				@class.method_0("glassware config system", "maxrange", value7.ToString());
				if (this.siticoneCheckBox1.Checked)
				{
					@class.method_0("glassware config system", "check1", "1");
				}
				if (!this.siticoneCheckBox1.Checked)
				{
					@class.method_0("glassware config system", "check1", "2");
				}
				if (this.siticoneCheckBox2.Checked)
				{
					@class.method_0("glassware config system", "check2", "1");
				}
				if (!this.siticoneCheckBox2.Checked)
				{
					@class.method_0("glassware config system", "check2", "2");
				}
				if (this.siticoneCheckBox4.Checked)
				{
					@class.method_0("glassware config system", "check3", "1");
				}
				if (!this.siticoneCheckBox4.Checked)
				{
					@class.method_0("glassware config system", "check3", "2");
				}
				if (this.BTN.Checked)
				{
					@class.method_0("glassware config system", "check4", "1");
				}
				if (!this.BTN.Checked)
				{
					@class.method_0("glassware config system", "check4", "2");
				}
				if (this.extreme.Checked)
				{
					@class.method_0("glassware config system", "check5", "1");
				}
				if (!this.extreme.Checked)
				{
					@class.method_0("glassware config system", "check5", "2");
				}
				if (this.RangeSettings.Checked)
				{
					@class.method_0("glassware config system", "check6", "1");
				}
				if (!this.RangeSettings.Checked)
				{
					@class.method_0("glassware config system", "check6", "2");
				}
				if (this.siticoneCheckBox5.Checked)
				{
					@class.method_0("glassware config system", "check7", "1");
				}
				if (!this.siticoneCheckBox5.Checked)
				{
					@class.method_0("glassware config system", "check7", "2");
				}
				if (this.STAP.Checked)
				{
					@class.method_0("glassware config system", "check8", "1");
				}
				if (!this.STAP.Checked)
				{
					@class.method_0("glassware config system", "check8", "2");
				}
				if (this.WTAP.Checked)
				{
					@class.method_0("glassware config system", "check9", "1");
				}
				if (!this.WTAP.Checked)
				{
					@class.method_0("glassware config system", "check9", "2");
				}
			}
		}

		// Token: 0x060001AC RID: 428 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_24(object sender, EventArgs e)
		{
		}

		// Token: 0x060001AD RID: 429 RVA: 0x0000F5C8 File Offset: 0x0000D7C8
		private void skeetButton3_Click(object sender, EventArgs e)
		{
			OpenFileDialog openFileDialog = new OpenFileDialog();
			openFileDialog.Filter = "glass cfg file | *.ini";
			if (openFileDialog.ShowDialog() == DialogResult.OK)
			{
				Class26 @class = new Class26(openFileDialog.FileName);
				string a = @class.method_1("glassware config system", "check1");
				string a2 = @class.method_1("glassware config system", "check2");
				string a3 = @class.method_1("glassware config system", "check3");
				string a4 = @class.method_1("glassware config system", "check4");
				string a5 = @class.method_1("glassware config system", "check5");
				string a6 = @class.method_1("glassware config system", "check6");
				string a7 = @class.method_1("glassware config system", "check7");
				string a8 = @class.method_1("glassware config system", "check8");
				string a9 = @class.method_1("glassware config system", "check9");
				string s = @class.method_1("glassware config system", "Slider1");
				string s2 = @class.method_1("glassware config system", "Slider2");
				string s3 = @class.method_1("glassware config system", "Slider4");
				string s4 = @class.method_1("glassware config system", "Slider3");
				string s5 = @class.method_1("glassware config system", "BlockHitChance");
				string s6 = @class.method_1("glassware config system", "maxrange");
				string s7 = @class.method_1("glassware config system", "minrange");
				string str = @class.method_1("glassware config system", "name");
				this.label31.Text = "Current Config: " + str;
				int value = int.Parse(s);
				this.siticoneHScrollBar1.Value = value;
				int value2 = int.Parse(s2);
				this.siticoneHScrollBar2.Value = value2;
				int value3 = int.Parse(s3);
				this.siticoneHScrollBar3.Value = value3;
				int value4 = int.Parse(s4);
				this.siticoneHScrollBar4.Value = value4;
				int value5 = int.Parse(s5);
				this.sdChance.Value = value5;
				int value6 = int.Parse(s6);
				this.MaxRange.Value = value6;
				int value7 = int.Parse(s7);
				this.MinRage.Value = value7;
				if (a == "1")
				{
					this.siticoneCheckBox1.Checked = true;
				}
				else
				{
					this.siticoneCheckBox1.Checked = false;
				}
				if (a2 == "1")
				{
					this.siticoneCheckBox2.Checked = true;
				}
				else
				{
					this.siticoneCheckBox2.Checked = false;
				}
				if (a3 == "1")
				{
					this.siticoneCheckBox4.Checked = true;
				}
				else
				{
					this.siticoneCheckBox4.Checked = false;
				}
				if (a4 == "1")
				{
					this.BTN.Checked = true;
				}
				else
				{
					this.BTN.Checked = false;
				}
				if (a5 == "1")
				{
					this.extreme.Checked = true;
				}
				else
				{
					this.extreme.Checked = false;
				}
				if (a6 == "1")
				{
					this.RangeSettings.Checked = true;
				}
				else
				{
					this.RangeSettings.Checked = false;
				}
				if (a7 == "1")
				{
					this.siticoneCheckBox5.Checked = true;
				}
				else
				{
					this.siticoneCheckBox5.Checked = false;
				}
				if (a8 == "1")
				{
					this.STAP.Checked = true;
				}
				else
				{
					this.STAP.Checked = false;
				}
				if (a9 == "1")
				{
					this.WTAP.Checked = true;
				}
				else
				{
					this.WTAP.Checked = false;
				}
			}
		}

		// Token: 0x060001AE RID: 430 RVA: 0x0000F94C File Offset: 0x0000DB4C
		private void siticoneButton8_Click(object sender, EventArgs e)
		{
			this.skeetGroupBox5.Visible = false;
			this.skeetGroupBox2.Visible = false;
			this.skeetGroupBox4.Visible = false;
			this.skeetGroupBox5.Visible = false;
			this.skeetGroupBox6.Visible = true;
			this.skeetGroupBox1.Visible = false;
			this.skeetGroupBox6.Location = new Point(12, 47);
			this.panel1.Visible = false;
			this.label5.Visible = true;
			this.siticoneButton6.Visible = true;
			this.label5.Visible = true;
			this.siticoneButton3.Visible = true;
		}

		// Token: 0x060001AF RID: 431 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_25(object sender, EventArgs e)
		{
		}

		// Token: 0x060001B0 RID: 432 RVA: 0x000021D4 File Offset: 0x000003D4
		private void timer_11_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x060001B1 RID: 433 RVA: 0x000021D4 File Offset: 0x000003D4
		private void siticoneCheckBox9_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060001B2 RID: 434 RVA: 0x000021D4 File Offset: 0x000003D4
		private void timer_10_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x060001B3 RID: 435 RVA: 0x000021D4 File Offset: 0x000003D4
		private void skeetGroupBox2_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x060001B4 RID: 436 RVA: 0x0000268E File Offset: 0x0000088E
		private void Form1_FormClosing(object sender, FormClosingEventArgs e)
		{
			Environment.Exit(0);
		}

		// Token: 0x060001B5 RID: 437 RVA: 0x0000F9F4 File Offset: 0x0000DBF4
		protected virtual void Dispose(bool disposing)
		{
			if (disposing && this.icontainer_0 != null)
			{
				this.icontainer_0.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x040002EE RID: 750
		private SolidBrush solidBrush_0 = new SolidBrush(Color.DeepSkyBlue);

		// Token: 0x040002EF RID: 751
		private SolidBrush solidBrush_1 = new SolidBrush(Color.White);

		// Token: 0x040002F0 RID: 752
		private int int_0;

		// Token: 0x040002F1 RID: 753
		private SoundPlayer soundPlayer_0;

		// Token: 0x040002F2 RID: 754
		private SoundPlayer soundPlayer_1;

		// Token: 0x040002F3 RID: 755
		private SoundPlayer soundPlayer_2;

		// Token: 0x040002F4 RID: 756
		private SoundPlayer soundPlayer_3;

		// Token: 0x040002F5 RID: 757
		private SoundPlayer soundPlayer_4;

		// Token: 0x040002F6 RID: 758
		private ResourceManager resourceManager_0 = new ResourceManager("g502.wav", Assembly.GetExecutingAssembly());

		// Token: 0x040002F7 RID: 759
		private string string_0;

		// Token: 0x040002F8 RID: 760
		public const int int_1 = 161;

		// Token: 0x040002F9 RID: 761
		public const int int_2 = 2;

		// Token: 0x040002FA RID: 762
		public const int int_3 = 513;

		// Token: 0x040002FB RID: 763
		public const int int_4 = 514;

		// Token: 0x040002FC RID: 764
		public const int int_5 = 516;

		// Token: 0x040002FD RID: 765
		public const int int_6 = 517;

		// Token: 0x040002FE RID: 766
		private int int_7;

		// Token: 0x040002FF RID: 767
		private int int_8;

		// Token: 0x04000300 RID: 768
		private IntPtr intptr_0;

		// Token: 0x04000301 RID: 769
		private KeysConverter keysConverter_0 = new KeysConverter();
	}
}
