using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Management;
using System.Net;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Meth.My;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x0200000A RID: 10
	[DesignerGenerated]
	public partial class Main : Form
	{
		// Token: 0x0600003C RID: 60
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern int GetAsyncKeyState(int key);

		// Token: 0x0600003D RID: 61
		[DllImport("user32.dll", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x0600003E RID: 62
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "GetMessageExtraInfo", ExactSpelling = true, SetLastError = true)]
		private static extern int apiGetMessageExtraInfo();

		// Token: 0x0600003F RID: 63
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern long SetCursorPos(long x, long y);

		// Token: 0x06000040 RID: 64
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern long GetCursorPos(ref Main.POINTAPI point);

		// Token: 0x06000041 RID: 65
		[DllImport("user32.dll", CharSet = CharSet.Ansi, EntryPoint = "SendMessageA", ExactSpelling = true, SetLastError = true)]
		public static extern int eAPISMessage(int hWnd, int wMsg, int wParam, [MarshalAs(UnmanagedType.VBByRefStr)] ref string lParam);

		// Token: 0x06000042 RID: 66
		[DllImport("user32.dll", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);

		// Token: 0x06000043 RID: 67
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "MapVirtualKeyA", ExactSpelling = true, SetLastError = true)]
		public static extern int MapVirtualKey(int wCode, int wMapType);

		// Token: 0x06000044 RID: 68
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern long GetActiveWindow();

		// Token: 0x06000045 RID: 69
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000046 RID: 70
		[DllImport("user32", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int GetWindowText(IntPtr hWnd, StringBuilder lpString, int cch);

		// Token: 0x06000047 RID: 71 RVA: 0x00002C70 File Offset: 0x00000E70
		public string GetCaption()
		{
			StringBuilder stringBuilder = new StringBuilder(256);
			Main.GetWindowText(Main.GetForegroundWindow(), stringBuilder, stringBuilder.Capacity);
			return stringBuilder.ToString();
		}

		// Token: 0x1700000C RID: 12
		// (get) Token: 0x06000048 RID: 72 RVA: 0x00002CA0 File Offset: 0x00000EA0
		// (set) Token: 0x06000049 RID: 73 RVA: 0x00002CA8 File Offset: 0x00000EA8
		private virtual Hook mHook
		{
			[CompilerGenerated]
			get
			{
				return this._mHook;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				Hook.Mouse_Left_DownEventHandler obj = new Hook.Mouse_Left_DownEventHandler(this.mHook_Mouse_Left_Down);
				Hook.Mouse_Left_UpEventHandler obj2 = new Hook.Mouse_Left_UpEventHandler(this.mHook_Mouse_Left_Up);
				Hook mHook = this._mHook;
				if (mHook != null)
				{
					mHook.Mouse_Left_Down -= obj;
					mHook.Mouse_Left_Up -= obj2;
				}
				this._mHook = value;
				mHook = this._mHook;
				if (mHook != null)
				{
					mHook.Mouse_Left_Down += obj;
					mHook.Mouse_Left_Up += obj2;
				}
			}
		}

		// Token: 0x0600004A RID: 74 RVA: 0x00002D08 File Offset: 0x00000F08
		private void Main_Load(object sender, EventArgs e)
		{
			Main._Closure$__47-0 CS$<>8__locals1 = new Main._Closure$__47-0(CS$<>8__locals1);
			CS$<>8__locals1.$VB$Me = this;
			base.Opacity = 0.0;
			CS$<>8__locals1.$VB$Local_tmr = new System.Windows.Forms.Timer();
			CS$<>8__locals1.$VB$Local_tmr.Interval = 1;
			CS$<>8__locals1.$VB$Local_tmr.Start();
			CS$<>8__locals1.$VB$Local_tmr.Tick += delegate(object a0, EventArgs a1)
			{
				base._Lambda$__0();
			};
			this.ThirteenComboBox1.SelectedItem = "Human Clicks";
			this.mHook.HookMouse();
			this.prefetchPath = Environment.GetEnvironmentVariable("windir", EnvironmentVariableTarget.Machine) + "\\Prefetch";
			Process[] processes = Process.GetProcesses();
			Process.GetProcessesByName("Process");
			foreach (string text in Directory.GetFiles(this.prefetchPath))
			{
				if (text.Contains("EXPLORER"))
				{
					File.Delete(text);
				}
			}
			foreach (string text2 in Directory.GetFiles(this.prefetchPath))
			{
				if (text2.Contains("RUNDLL"))
				{
					File.Delete(text2);
				}
			}
			foreach (string text3 in Directory.GetFiles(this.prefetchPath))
			{
				if (text3.Contains("JNA"))
				{
					File.Delete(text3);
				}
			}
			foreach (string text4 in Directory.GetFiles(this.prefetchPath))
			{
				if (text4.Contains("METH"))
				{
					File.Delete(text4);
				}
			}
			foreach (string text5 in Directory.GetFiles(this.prefetchPath))
			{
				if (text5.Contains("MAIN"))
				{
					File.Delete(text5);
				}
			}
			foreach (string text6 in Directory.GetFiles(this.prefetchPath))
			{
				if (text6.Contains("EXPLORER"))
				{
					File.Delete(text6);
				}
			}
			foreach (string text7 in Directory.GetFiles(this.prefetchPath))
			{
				if (text7.Contains("ANYDESK"))
				{
					File.Delete(text7);
				}
			}
			foreach (string text8 in Directory.GetFiles(this.prefetchPath))
			{
				if (text8.Contains("WINLOGON"))
				{
					File.Delete(text8);
				}
			}
			foreach (string text9 in Directory.GetFiles(this.prefetchPath))
			{
				if (text9.Contains("SEARCH"))
				{
					File.Delete(text9);
				}
			}
			foreach (string text10 in Directory.GetFiles(this.prefetchPath))
			{
				if (text10.Contains("EXPLORER"))
				{
					File.Delete(text10);
				}
			}
			foreach (string text11 in Directory.GetFiles(this.prefetchPath))
			{
				if (text11.Contains("ANYDESK"))
				{
					File.Delete(text11);
				}
			}
			foreach (string text12 in Directory.GetFiles(this.prefetchPath))
			{
				if (text12.Contains("WINLOGON"))
				{
					File.Delete(text12);
				}
			}
			foreach (string text13 in Directory.GetFiles(this.prefetchPath))
			{
				if (text13.Contains("DLL"))
				{
					File.Delete(text13);
				}
			}
			this.Delay(1.5);
			int num8 = processes.Length - 1;
			for (int num9 = 0; num9 <= num8; num9++)
			{
				checked
				{
					if (processes[num9].ProcessName.Contains("explorer"))
					{
						Process[] processesByName = Process.GetProcessesByName(processes[num9].ProcessName);
						for (int num10 = 0; num10 < processesByName.Length; num10++)
						{
							processesByName[num10].Kill();
						}
					}
				}
			}
			new WebClient();
			new Main.clsComputerInfo();
			this.Text = "Bleu Clicker";
			this.MainForm.Text = "Bleu Clicker";
			this.SelectedColor = Color.Red;
			this.SelectedRainbowNumber = 0f;
			foreach (string text14 in Directory.GetFiles(this.prefetchPath))
			{
				if (text14.Contains("ANYDESK"))
				{
					File.Delete(text14);
				}
			}
		}

		// Token: 0x0600004B RID: 75 RVA: 0x00003198 File Offset: 0x00001398
		public Main()
		{
			base.Load += this.Main_Load;
			base.Closing += this.Form1_Closing;
			this.rnd = new Random();
			this.slot = 0;
			this.mHook = new Hook();
			this.shouldClick = false;
			this.ignoreNextRelease = false;
			this.directory = Environment.GetFolderPath(Environment.SpecialFolder.Desktop) + "\\AnyDesk.exe";
			this.MouseMoving = false;
			this.InitializeComponent();
		}

		// Token: 0x0600004C RID: 76 RVA: 0x00003220 File Offset: 0x00001420
		private void mHook_Mouse_Left_Down(Point ptLocat)
		{
			new Thread(new ThreadStart(this.Clicking));
			this.isHeld_LMB = true;
			this.shouldClick = true;
			Thread thread = new Thread(new ThreadStart(this.Jitter));
			if (!(this.Autoclicker.Enabled & this.FlatCheckBox3.Checked))
			{
				thread.Abort();
				return;
			}
			if (!thread.IsAlive)
			{
				thread.Start();
				return;
			}
			thread.Join();
		}

		// Token: 0x0600004D RID: 77 RVA: 0x00003294 File Offset: 0x00001494
		private void mHook_Mouse_Left_Up(Point ptLocat)
		{
			this.isHeld_LMB = false;
			if (!this.ignoreNextRelease)
			{
				this.shouldClick = false;
			}
			this.ignoreNextRelease = false;
		}

		// Token: 0x0600004E RID: 78 RVA: 0x000032B4 File Offset: 0x000014B4
		public void Jitter()
		{
			VBMath.Randomize();
			Main.POINTAPI pointapi;
			Main.GetCursorPos(ref pointapi);
			Cursor.Position = new Point(pointapi.x + this.rnd.Next(-this.FlatTrackBar1.Value, this.FlatTrackBar1.Value), pointapi.y + this.rnd.Next(-this.FlatTrackBar1.Value, this.FlatTrackBar1.Value));
		}

		// Token: 0x0600004F RID: 79 RVA: 0x0000332C File Offset: 0x0000152C
		private void ThirteenButton1_Click(object sender, EventArgs e)
		{
			this.toggle++;
			if (this.toggle == 1)
			{
				this.Autoclicker.Start();
				this.FlatLabel37.Text = "On";
				return;
			}
			this.FlatLabel37.Text = "Off";
			this.Autoclicker.Stop();
			this.toggle = 0;
		}

		// Token: 0x06000050 RID: 80 RVA: 0x0000338E File Offset: 0x0000158E
		private void Donate_Click(object sender, EventArgs e)
		{
			Process.Start("https://www.paypal.me/Grand0x");
		}

		// Token: 0x06000051 RID: 81 RVA: 0x0000339C File Offset: 0x0000159C
		private void ThirteenButton2_Click(object sender, EventArgs e)
		{
			this.toggle++;
			if (this.toggle == 1)
			{
				this.FlatLabel38.Text = "On";
				this.WTap.Start();
				return;
			}
			this.FlatLabel38.Text = "Off";
			this.WTap.Start();
			this.toggle = 0;
		}

		// Token: 0x06000052 RID: 82 RVA: 0x00003400 File Offset: 0x00001600
		private void ThirteenButton3_Click(object sender, EventArgs e)
		{
			this.toggle++;
			if (this.toggle == 1)
			{
				this.STap.Start();
				this.FlatLabel39.Text = "On";
				return;
			}
			this.STap.Stop();
			this.FlatLabel39.Text = "Off";
			this.toggle = 0;
		}

		// Token: 0x06000053 RID: 83 RVA: 0x00003464 File Offset: 0x00001664
		private void ThirteenButton4_Click(object sender, EventArgs e)
		{
			this.toggle++;
			if (this.toggle == 1)
			{
				this.AutoPot.Start();
				this.FlatLabel40.Text = "On";
				return;
			}
			this.FlatLabel40.Text = "Off";
			this.AutoPot.Stop();
			this.toggle = 0;
		}

		// Token: 0x06000054 RID: 84 RVA: 0x000034C8 File Offset: 0x000016C8
		private void ThirteenButton5_Click(object sender, EventArgs e)
		{
			this.toggle++;
			if (this.toggle == 1)
			{
				this.AutoRod.Start();
				this.FlatLabel41.Text = "On";
				return;
			}
			this.FlatLabel41.Text = "Off";
			this.AutoRod.Stop();
			this.toggle = 0;
		}

		// Token: 0x06000055 RID: 85 RVA: 0x0000352A File Offset: 0x0000172A
		private void MinimumCPS_Scroll(object sender)
		{
			this.FlatLabel2.Text = Conversions.ToString(this.MinimumCPS.Value);
		}

		// Token: 0x06000056 RID: 86 RVA: 0x00003547 File Offset: 0x00001747
		private void MaximumCPS_Scroll(object sender)
		{
			this.FlatLabel4.Text = Conversions.ToString(this.MaximumCPS.Value);
		}

		// Token: 0x06000057 RID: 87 RVA: 0x00003564 File Offset: 0x00001764
		public void Clicking()
		{
			VBMath.Randomize();
			if (this.Autoclicker.Enabled)
			{
				Main.apimouse_event(2, 0, 0, 2, Main.apiGetMessageExtraInfo());
				Thread.Sleep(this.rnd.Next(50, 70));
				this.ignoreNextRelease = true;
				Main.apimouse_event(4, 0, 0, 2, Main.apiGetMessageExtraInfo());
				Thread.Sleep(this.rnd.Next(50, 70));
			}
		}

		// Token: 0x06000058 RID: 88 RVA: 0x000035D0 File Offset: 0x000017D0
		public void Delay(double dblSecs)
		{
			DateAndTime.Now.AddSeconds(1.1574074074074073E-05);
			DateTime t = DateAndTime.Now.AddSeconds(1.1574074074074073E-05).AddSeconds(dblSecs);
			while (DateTime.Compare(DateAndTime.Now, t) <= 0)
			{
				Application.DoEvents();
			}
		}

		// Token: 0x06000059 RID: 89 RVA: 0x0000362C File Offset: 0x0000182C
		private void Autoclicker_Tick(object sender, EventArgs e)
		{
			int maxValue = (int)Math.Round(1000.0 / ((double)this.MinimumCPS.Value + (double)this.MaximumCPS.Value * 0.2));
			int minValue = (int)Math.Round(1000.0 / ((double)this.MinimumCPS.Value + (double)this.MaximumCPS.Value * 0.48));
			Thread thread = new Thread(new ThreadStart(this.Clicking));
			if (Operators.ConditionalCompareObjectEqual(this.ThirteenComboBox1.SelectedItem, "Simple Random", false))
			{
				this.Autoclicker.Interval = this.rnd.Next(minValue, maxValue);
			}
			if (this.FlatCheckBox4.Checked)
			{
				if (this.GetCaption().StartsWith("Minecraft"))
				{
					if (!this.shouldClick)
					{
						thread.Abort();
						return;
					}
					if (!thread.IsAlive)
					{
						thread.Start();
						return;
					}
					thread.Join();
					return;
				}
			}
			else if (this.shouldClick)
			{
				if (!thread.IsAlive)
				{
					thread.Start();
					return;
				}
				thread.Join();
				return;
			}
			else
			{
				thread.Abort();
			}
		}

		// Token: 0x0600005A RID: 90 RVA: 0x0000374C File Offset: 0x0000194C
		private void WTap_Tick(object sender, EventArgs e)
		{
			this.WTap.Interval = this.rnd.Next(this.FlatTrackBar3.Value, this.FlatTrackBar2.Value);
			if ((Main.GetAsyncKeyState(87) & ((-((this.Autoclicker.Enabled > false) ? 1 : 0)) ? 1 : 0) & ((-((this.shouldClick > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				InputHelper.SetKeyState(Keys.W, true);
				this.Delay(0.025);
				InputHelper.SetKeyState(Keys.W, false);
			}
		}

		// Token: 0x0600005B RID: 91 RVA: 0x000037CB File Offset: 0x000019CB
		private void FlatTrackBar3_Scroll(object sender)
		{
			this.FlatLabel7.Text = Conversions.ToString(this.FlatTrackBar3.Value);
		}

		// Token: 0x0600005C RID: 92 RVA: 0x000037E8 File Offset: 0x000019E8
		private void FlatTrackBar2_Scroll(object sender)
		{
			this.FlatLabel5.Text = Conversions.ToString(this.FlatTrackBar2.Value);
		}

		// Token: 0x0600005D RID: 93 RVA: 0x00003805 File Offset: 0x00001A05
		private void ThirteenButton8_Click(object sender, EventArgs e)
		{
			this.HideButton.Start();
		}

		// Token: 0x0600005E RID: 94 RVA: 0x00003814 File Offset: 0x00001A14
		private void STap_Tick(object sender, EventArgs e)
		{
			this.STap.Interval = this.rnd.Next(this.FlatTrackBar5.Value, this.FlatTrackBar4.Value);
			if ((Main.GetAsyncKeyState(87) & ((-((this.Autoclicker.Enabled > false) ? 1 : 0)) ? 1 : 0) & ((-((this.shouldClick > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				InputHelper.SetKeyState(Keys.S, true);
				this.Delay(0.05);
				InputHelper.SetKeyState(Keys.S, false);
			}
		}

		// Token: 0x0600005F RID: 95 RVA: 0x00003894 File Offset: 0x00001A94
		private void HideButton_Tick_1(object sender, EventArgs e)
		{
			Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
			dictionary.Add("A", Keys.A);
			dictionary.Add("B", Keys.B);
			dictionary.Add("C", Keys.C);
			dictionary.Add("D", Keys.D);
			dictionary.Add("E", Keys.E);
			dictionary.Add("F", Keys.F);
			dictionary.Add("G", Keys.G);
			dictionary.Add("H", Keys.H);
			dictionary.Add("I", Keys.I);
			dictionary.Add("J", Keys.J);
			dictionary.Add("K", Keys.K);
			dictionary.Add("L", Keys.L);
			dictionary.Add("M", Keys.M);
			dictionary.Add("N", Keys.N);
			dictionary.Add("O", Keys.O);
			dictionary.Add("P", Keys.P);
			dictionary.Add("Q", Keys.Q);
			dictionary.Add("R", Keys.R);
			dictionary.Add("S", Keys.S);
			dictionary.Add("T", Keys.T);
			dictionary.Add("U", Keys.U);
			dictionary.Add("V", Keys.V);
			dictionary.Add("W", Keys.W);
			dictionary.Add("X", Keys.X);
			dictionary.Add("Y", Keys.Y);
			dictionary.Add("Z", Keys.Z);
			dictionary.Add("a", Keys.A);
			dictionary.Add("b", Keys.B);
			dictionary.Add("c", Keys.C);
			dictionary.Add("d", Keys.D);
			dictionary.Add("e", Keys.E);
			dictionary.Add("f", Keys.F);
			dictionary.Add("g", Keys.G);
			dictionary.Add("h", Keys.H);
			dictionary.Add("i", Keys.I);
			dictionary.Add("j", Keys.J);
			dictionary.Add("k", Keys.K);
			dictionary.Add("l", Keys.L);
			dictionary.Add("m", Keys.M);
			dictionary.Add("n", Keys.N);
			dictionary.Add("o", Keys.O);
			dictionary.Add("p", Keys.P);
			dictionary.Add("q", Keys.Q);
			dictionary.Add("r", Keys.R);
			dictionary.Add("s", Keys.S);
			dictionary.Add("t", Keys.T);
			dictionary.Add("u", Keys.U);
			dictionary.Add("v", Keys.V);
			dictionary.Add("w", Keys.W);
			dictionary.Add("x", Keys.X);
			dictionary.Add("y", Keys.Y);
			dictionary.Add("z", Keys.Z);
			dictionary.Add("R-SHIFT", Keys.RShiftKey);
			string text = this.ThirteenTextBox1.Text;
			if ((Main.GetAsyncKeyState(162) & Main.GetAsyncKeyState((int)dictionary[this.ThirteenTextBox1.Text])) != 0)
			{
				this.Delay(0.075);
				MyProject.Forms.Main.Hide();
			}
			if ((Main.GetAsyncKeyState(162) & Main.GetAsyncKeyState((int)dictionary[this.ThirteenTextBox1.Text])) != 0)
			{
				this.Delay(0.075);
				MyProject.Forms.Main.Show();
			}
		}

		// Token: 0x06000060 RID: 96 RVA: 0x00003BF1 File Offset: 0x00001DF1
		private void FlatTrackBar5_Scroll(object sender)
		{
			this.FlatLabel13.Text = Conversions.ToString(this.FlatTrackBar5.Value);
		}

		// Token: 0x06000061 RID: 97 RVA: 0x00003C0E File Offset: 0x00001E0E
		private void FlatTrackBar4_Scroll(object sender)
		{
			this.FlatLabel11.Text = Conversions.ToString(this.FlatTrackBar4.Value);
		}

		// Token: 0x06000062 RID: 98 RVA: 0x00003C2B File Offset: 0x00001E2B
		private void ThirteenButton1_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000063 RID: 99 RVA: 0x00003C30 File Offset: 0x00001E30
		private void AutoPot_Tick(object sender, EventArgs e)
		{
			Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
			this.keypot = this.ThirteenTextBox12.Text;
			this.bind1 = this.ThirteenTextBox3.Text;
			this.bind2 = this.ThirteenTextBox4.Text;
			this.bind3 = this.ThirteenTextBox5.Text;
			this.bind4 = this.ThirteenTextBox6.Text;
			this.bind5 = this.ThirteenTextBox7.Text;
			this.bind6 = this.ThirteenTextBox8.Text;
			this.bind7 = this.ThirteenTextBox9.Text;
			this.bind8 = this.ThirteenTextBox10.Text;
			this.bind9 = this.ThirteenTextBox11.Text;
			dictionary.Add("A", Keys.A);
			dictionary.Add("B", Keys.B);
			dictionary.Add("C", Keys.C);
			dictionary.Add("D", Keys.D);
			dictionary.Add("E", Keys.E);
			dictionary.Add("F", Keys.F);
			dictionary.Add("G", Keys.G);
			dictionary.Add("H", Keys.H);
			dictionary.Add("I", Keys.I);
			dictionary.Add("J", Keys.J);
			dictionary.Add("K", Keys.K);
			dictionary.Add("L", Keys.L);
			dictionary.Add("M", Keys.M);
			dictionary.Add("N", Keys.N);
			dictionary.Add("O", Keys.O);
			dictionary.Add("P", Keys.P);
			dictionary.Add("Q", Keys.Q);
			dictionary.Add("R", Keys.R);
			dictionary.Add("S", Keys.S);
			dictionary.Add("T", Keys.T);
			dictionary.Add("U", Keys.U);
			dictionary.Add("V", Keys.V);
			dictionary.Add("W", Keys.W);
			dictionary.Add("X", Keys.X);
			dictionary.Add("Y", Keys.Y);
			dictionary.Add("Z", Keys.Z);
			dictionary.Add("a", Keys.A);
			dictionary.Add("b", Keys.B);
			dictionary.Add("c", Keys.C);
			dictionary.Add("d", Keys.D);
			dictionary.Add("e", Keys.E);
			dictionary.Add("f", Keys.F);
			dictionary.Add("g", Keys.G);
			dictionary.Add("h", Keys.H);
			dictionary.Add("i", Keys.I);
			dictionary.Add("j", Keys.J);
			dictionary.Add("k", Keys.K);
			dictionary.Add("l", Keys.L);
			dictionary.Add("m", Keys.M);
			dictionary.Add("n", Keys.N);
			dictionary.Add("o", Keys.O);
			dictionary.Add("p", Keys.P);
			dictionary.Add("q", Keys.Q);
			dictionary.Add("r", Keys.R);
			dictionary.Add("s", Keys.S);
			dictionary.Add("t", Keys.T);
			dictionary.Add("u", Keys.U);
			dictionary.Add("v", Keys.V);
			dictionary.Add("w", Keys.W);
			dictionary.Add("x", Keys.X);
			dictionary.Add("y", Keys.Y);
			dictionary.Add("z", Keys.Z);
			dictionary.Add("1", Keys.D1);
			dictionary.Add("2", Keys.D2);
			dictionary.Add("3", Keys.D3);
			dictionary.Add("4", Keys.D4);
			dictionary.Add("5", Keys.D5);
			dictionary.Add("6", Keys.D6);
			dictionary.Add("7", Keys.D7);
			dictionary.Add("8", Keys.D8);
			dictionary.Add("9", Keys.D9);
			dictionary.Add("", Keys.None);
			dictionary.Add("NONE", Keys.LButton);
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 0) > false) ? 1 : 0)) ? 1 : 0) & ((-((this.FlatCheckBox5.Checked > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind2);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.05);
				SendKeys.Send(this.ThirteenTextBox2.Text);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			else if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 0) > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				this.Delay(0.03);
				SendKeys.Send(this.bind2);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.03);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 1) > false) ? 1 : 0)) ? 1 : 0) & ((-((this.FlatCheckBox5.Checked > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				this.Delay(0.03);
				SendKeys.Send(this.bind3);
				this.Delay(0.01);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.01);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.01);
				SendKeys.Send(this.ThirteenTextBox2.Text);
				this.Delay(0.01);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			else if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 1) > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				this.Delay(0.03);
				SendKeys.Send(this.bind3);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.03);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 2) > false) ? 1 : 0)) ? 1 : 0) & ((-((this.FlatCheckBox5.Checked > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind4);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.05);
				SendKeys.Send(this.ThirteenTextBox2.Text);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			else if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 2) > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind4);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 3) > false) ? 1 : 0)) ? 1 : 0) & ((-((this.FlatCheckBox5.Checked > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind5);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.05);
				SendKeys.Send(this.ThirteenTextBox2.Text);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			else if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 3) > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind5);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 4) > false) ? 1 : 0)) ? 1 : 0) & ((-((this.FlatCheckBox5.Checked > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind6);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.05);
				SendKeys.Send(this.ThirteenTextBox2.Text);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			else if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 4) > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind6);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 5) > false) ? 1 : 0)) ? 1 : 0) & ((-((this.FlatCheckBox5.Checked > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind7);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.05);
				SendKeys.Send(this.ThirteenTextBox2.Text);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			else if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 5) > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind7);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 6) > false) ? 1 : 0)) ? 1 : 0) & ((-((this.FlatCheckBox5.Checked > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind8);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.05);
				SendKeys.Send(this.ThirteenTextBox2.Text);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			else if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 6) > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind8);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 7) > false) ? 1 : 0)) ? 1 : 0) & ((-((this.FlatCheckBox5.Checked > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind9);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.05);
				SendKeys.Send(this.ThirteenTextBox2.Text);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			else if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-(((this.slot == 7) > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				SendKeys.Send(this.bind9);
				this.Delay(0.03);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.05);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.03);
				SendKeys.Send(this.bind1);
				this.slot++;
			}
			this.AutoPot.Interval = this.FlatTrackBar6.Value;
			if (Main.GetAsyncKeyState((int)dictionary[this.ThirteenTextBox13.Text]) != 0)
			{
				this.slot = 1;
			}
			if ((Main.GetAsyncKeyState((int)dictionary[this.keypot]) & ((-((this.slot > 7 > false) ? 1 : 0)) ? 1 : 0)) != 0)
			{
				this.slot = 1;
			}
		}

		// Token: 0x06000064 RID: 100 RVA: 0x00004B08 File Offset: 0x00002D08
		private void FlatTrackBar6_Scroll(object sender)
		{
			this.Label2.Text = Conversions.ToString(this.FlatTrackBar6.Value);
		}

		// Token: 0x06000065 RID: 101 RVA: 0x00004B28 File Offset: 0x00002D28
		private void Bind_Tick(object sender, EventArgs e)
		{
			Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
			dictionary.Add("A", Keys.A);
			dictionary.Add("B", Keys.B);
			dictionary.Add("C", Keys.C);
			dictionary.Add("D", Keys.D);
			dictionary.Add("E", Keys.E);
			dictionary.Add("F", Keys.F);
			dictionary.Add("G", Keys.G);
			dictionary.Add("H", Keys.H);
			dictionary.Add("I", Keys.I);
			dictionary.Add("J", Keys.J);
			dictionary.Add("K", Keys.K);
			dictionary.Add("L", Keys.L);
			dictionary.Add("M", Keys.M);
			dictionary.Add("N", Keys.N);
			dictionary.Add("O", Keys.O);
			dictionary.Add("P", Keys.P);
			dictionary.Add("Q", Keys.Q);
			dictionary.Add("R", Keys.R);
			dictionary.Add("S", Keys.S);
			dictionary.Add("T", Keys.T);
			dictionary.Add("U", Keys.U);
			dictionary.Add("V", Keys.V);
			dictionary.Add("W", Keys.W);
			dictionary.Add("X", Keys.X);
			dictionary.Add("Y", Keys.Y);
			dictionary.Add("Z", Keys.Z);
			dictionary.Add("a", Keys.A);
			dictionary.Add("b", Keys.B);
			dictionary.Add("c", Keys.C);
			dictionary.Add("d", Keys.D);
			dictionary.Add("e", Keys.E);
			dictionary.Add("f", Keys.F);
			dictionary.Add("g", Keys.G);
			dictionary.Add("h", Keys.H);
			dictionary.Add("i", Keys.I);
			dictionary.Add("j", Keys.J);
			dictionary.Add("k", Keys.K);
			dictionary.Add("l", Keys.L);
			dictionary.Add("m", Keys.M);
			dictionary.Add("n", Keys.N);
			dictionary.Add("o", Keys.O);
			dictionary.Add("p", Keys.P);
			dictionary.Add("q", Keys.Q);
			dictionary.Add("r", Keys.R);
			dictionary.Add("s", Keys.S);
			dictionary.Add("t", Keys.T);
			dictionary.Add("u", Keys.U);
			dictionary.Add("v", Keys.V);
			dictionary.Add("w", Keys.W);
			dictionary.Add("x", Keys.X);
			dictionary.Add("y", Keys.Y);
			dictionary.Add("z", Keys.Z);
			dictionary.Add("R-SHIFT", Keys.RShiftKey);
			dictionary.Add("", Keys.None);
			string text = this.ThirteenTextBox14.Text;
			if (Main.GetAsyncKeyState((int)dictionary[text]) != 0)
			{
				if (this.Autoclicker.Enabled)
				{
					this.Autoclicker.Stop();
				}
				else
				{
					this.Autoclicker.Start();
				}
			}
			if (Main.GetAsyncKeyState((int)dictionary[this.ThirteenTextBox15.Text]) != 0)
			{
				if (this.WTap.Enabled)
				{
					this.WTap.Stop();
				}
				else
				{
					this.WTap.Start();
				}
			}
			if (Main.GetAsyncKeyState((int)dictionary[this.ThirteenTextBox16.Text]) != 0)
			{
				if (this.STap.Enabled)
				{
					this.STap.Stop();
				}
				else
				{
					this.STap.Start();
				}
			}
			if (Main.GetAsyncKeyState((int)dictionary[this.ThirteenTextBox17.Text]) != 0 && !this.AutoPot.Enabled)
			{
				this.AutoPot.Start();
			}
			if (Main.GetAsyncKeyState((int)dictionary[this.ThirteenTextBox18.Text]) != 0)
			{
				if (this.AutoRod.Enabled)
				{
					this.AutoRod.Stop();
					return;
				}
				this.AutoRod.Start();
			}
		}

		// Token: 0x06000066 RID: 102 RVA: 0x00004F2F File Offset: 0x0000312F
		private void ThirteenButton6_Click(object sender, EventArgs e)
		{
			this.Bind.Start();
		}

		// Token: 0x06000067 RID: 103 RVA: 0x00004F2F File Offset: 0x0000312F
		private void ThirteenButton7_Click(object sender, EventArgs e)
		{
			this.Bind.Start();
		}

		// Token: 0x06000068 RID: 104 RVA: 0x00004F2F File Offset: 0x0000312F
		private void ThirteenButton9_Click(object sender, EventArgs e)
		{
			this.Bind.Start();
		}

		// Token: 0x06000069 RID: 105 RVA: 0x00004F2F File Offset: 0x0000312F
		private void ThirteenButton10_Click(object sender, EventArgs e)
		{
			this.Bind.Start();
		}

		// Token: 0x0600006A RID: 106 RVA: 0x00004F2F File Offset: 0x0000312F
		private void ThirteenButton11_Click(object sender, EventArgs e)
		{
			this.Bind.Start();
		}

		// Token: 0x0600006B RID: 107 RVA: 0x00004F3C File Offset: 0x0000313C
		private void AutoRod_Tick(object sender, EventArgs e)
		{
			Dictionary<string, Keys> dictionary = new Dictionary<string, Keys>();
			string text = this.ThirteenTextBox19.Text;
			this.bind1 = this.ThirteenTextBox20.Text;
			this.bind2 = this.ThirteenTextBox21.Text;
			dictionary.Add("A", Keys.A);
			dictionary.Add("B", Keys.B);
			dictionary.Add("C", Keys.C);
			dictionary.Add("D", Keys.D);
			dictionary.Add("E", Keys.E);
			dictionary.Add("F", Keys.F);
			dictionary.Add("G", Keys.G);
			dictionary.Add("H", Keys.H);
			dictionary.Add("I", Keys.I);
			dictionary.Add("J", Keys.J);
			dictionary.Add("K", Keys.K);
			dictionary.Add("L", Keys.L);
			dictionary.Add("M", Keys.M);
			dictionary.Add("N", Keys.N);
			dictionary.Add("O", Keys.O);
			dictionary.Add("P", Keys.P);
			dictionary.Add("Q", Keys.Q);
			dictionary.Add("R", Keys.R);
			dictionary.Add("S", Keys.S);
			dictionary.Add("T", Keys.T);
			dictionary.Add("U", Keys.U);
			dictionary.Add("V", Keys.V);
			dictionary.Add("W", Keys.W);
			dictionary.Add("X", Keys.X);
			dictionary.Add("Y", Keys.Y);
			dictionary.Add("Z", Keys.Z);
			dictionary.Add("a", Keys.A);
			dictionary.Add("b", Keys.B);
			dictionary.Add("c", Keys.C);
			dictionary.Add("d", Keys.D);
			dictionary.Add("e", Keys.E);
			dictionary.Add("f", Keys.F);
			dictionary.Add("g", Keys.G);
			dictionary.Add("h", Keys.H);
			dictionary.Add("i", Keys.I);
			dictionary.Add("j", Keys.J);
			dictionary.Add("k", Keys.K);
			dictionary.Add("l", Keys.L);
			dictionary.Add("m", Keys.M);
			dictionary.Add("n", Keys.N);
			dictionary.Add("o", Keys.O);
			dictionary.Add("p", Keys.P);
			dictionary.Add("q", Keys.Q);
			dictionary.Add("r", Keys.R);
			dictionary.Add("s", Keys.S);
			dictionary.Add("t", Keys.T);
			dictionary.Add("u", Keys.U);
			dictionary.Add("v", Keys.V);
			dictionary.Add("w", Keys.W);
			dictionary.Add("x", Keys.X);
			dictionary.Add("y", Keys.Y);
			dictionary.Add("z", Keys.Z);
			dictionary.Add("1", Keys.D1);
			dictionary.Add("2", Keys.D2);
			dictionary.Add("3", Keys.D3);
			dictionary.Add("4", Keys.D4);
			dictionary.Add("5", Keys.D5);
			dictionary.Add("6", Keys.D6);
			dictionary.Add("7", Keys.D7);
			dictionary.Add("8", Keys.D8);
			dictionary.Add("9", Keys.D9);
			dictionary.Add("", Keys.None);
			dictionary.Add("NONE", Keys.None);
			if (Main.GetAsyncKeyState((int)dictionary[text]) != 0)
			{
				SendKeys.Send(this.bind2);
				this.Delay(0.04);
				Main.apimouse_event(8, 0, 0, 0, 0);
				this.Delay(0.04);
				Main.apimouse_event(16, 0, 0, 0, 0);
				this.Delay(0.04);
				SendKeys.Send(this.bind1);
			}
		}

		// Token: 0x0600006C RID: 108 RVA: 0x00005317 File Offset: 0x00003517
		private void FlatTrackBar7_Scroll(object sender)
		{
			this.Label3.Text = Conversions.ToString(this.FlatTrackBar7.Value);
			this.AutoRod.Interval = this.FlatTrackBar7.Value;
		}

		// Token: 0x0600006D RID: 109 RVA: 0x0000534C File Offset: 0x0000354C
		private void Melt3(int Timeout)
		{
			Process.Start(new ProcessStartInfo("cmd.exe")
			{
				Arguments = string.Concat(new string[]
				{
					"/C ping 1.1.1.1 -n 1 -w ",
					Timeout.ToString(),
					" > Nul & Del \"",
					Application.ExecutablePath,
					"\""
				}),
				CreateNoWindow = true,
				ErrorDialog = false,
				WindowStyle = ProcessWindowStyle.Hidden
			});
		}

		// Token: 0x0600006E RID: 110 RVA: 0x000053BC File Offset: 0x000035BC
		private void ThirteenButton12_Click(object sender, EventArgs e)
		{
			this.Label1.Text = null;
			this.Label2.Text = null;
			this.Label4.Text = null;
			this.FlatLabel1.Text = null;
			this.FlatLabel2.Text = null;
			this.FlatLabel3.Text = null;
			this.FlatLabel4.Text = null;
			this.FlatLabel5.Text = null;
			this.FlatLabel6.Text = null;
			this.FlatLabel7.Text = null;
			this.FlatLabel8.Text = null;
			this.FlatLabel9.Text = null;
			this.FlatLabel10.Text = null;
			this.FlatLabel11.Text = null;
			this.FlatLabel12.Text = null;
			this.FlatLabel13.Text = null;
			this.FlatLabel14.Text = null;
			this.FlatLabel16.Text = null;
			this.FlatLabel17.Text = null;
			this.FlatLabel18.Text = null;
			this.FlatLabel19.Text = null;
			this.FlatLabel20.Text = null;
			this.FlatLabel21.Text = null;
			this.FlatLabel22.Text = null;
			this.FlatLabel23.Text = null;
			this.FlatLabel24.Text = null;
			this.FlatLabel25.Text = null;
			this.FlatLabel26.Text = null;
			this.FlatLabel27.Text = null;
			this.FlatLabel28.Text = null;
			this.FlatLabel29.Text = null;
			this.FlatLabel30.Text = null;
			this.FlatLabel31.Text = null;
			this.FlatTabControl1.TabPages[0].Text = null;
			this.FlatTabControl1.TabPages[1].Text = null;
			this.FlatTabControl1.TabPages[2].Text = null;
			this.FlatTabControl1.TabPages[3].Text = null;
			this.Label1.Dispose();
			this.Label2.Dispose();
			this.Label4.Dispose();
			this.FlatLabel1.Dispose();
			this.FlatLabel2.Dispose();
			this.FlatLabel3.Dispose();
			this.FlatLabel4.Dispose();
			this.FlatLabel5.Dispose();
			this.FlatLabel6.Dispose();
			this.FlatLabel7.Dispose();
			this.FlatLabel8.Dispose();
			this.FlatLabel9.Dispose();
			this.FlatLabel10.Dispose();
			this.FlatLabel11.Dispose();
			this.FlatLabel12.Dispose();
			this.FlatLabel13.Dispose();
			this.FlatLabel14.Dispose();
			this.FlatLabel16.Dispose();
			this.FlatLabel17.Dispose();
			this.FlatLabel18.Dispose();
			this.FlatLabel19.Dispose();
			this.FlatLabel20.Dispose();
			this.FlatLabel21.Dispose();
			this.FlatLabel22.Dispose();
			this.FlatLabel23.Dispose();
			this.FlatLabel24.Dispose();
			this.FlatLabel25.Dispose();
			this.FlatLabel26.Dispose();
			this.FlatLabel27.Dispose();
			this.FlatLabel28.Dispose();
			this.FlatLabel29.Dispose();
			this.FlatLabel30.Dispose();
			this.FlatLabel31.Dispose();
			this.FlatTabControl1.TabPages[0].Dispose();
			this.FlatTabControl1.TabPages[1].Dispose();
			this.FlatTabControl1.TabPages[2].Dispose();
			this.FlatTabControl1.TabPages[3].Dispose();
			this.Melt3(1);
			this.MainForm.Dispose();
			this.MainForm.Text = null;
			base.Dispose();
			Application.Exit();
		}

		// Token: 0x0600006F RID: 111 RVA: 0x000057A0 File Offset: 0x000039A0
		private void Form1_Closing(object sender, CancelEventArgs e)
		{
			e.Cancel = true;
			base.Opacity = 1.0;
			System.Windows.Forms.Timer tmr = new System.Windows.Forms.Timer();
			tmr.Interval = 1;
			tmr.Start();
			tmr.Tick += delegate(object a0, EventArgs a1)
			{
				base._Lambda$__0();
			};
		}

		// Token: 0x06000070 RID: 112 RVA: 0x00005818 File Offset: 0x00003A18
		internal string getMD5Hash(string strToHash)
		{
			HashAlgorithm hashAlgorithm = new MD5CryptoServiceProvider();
			byte[] array = Encoding.ASCII.GetBytes(strToHash);
			array = hashAlgorithm.ComputeHash(array);
			string text = "";
			foreach (byte b in array)
			{
				text += b.ToString("x2");
			}
			return text;
		}

		// Token: 0x06000071 RID: 113 RVA: 0x0000586C File Offset: 0x00003A6C
		private void picRainbow_MouseMove(object sender, MouseEventArgs e)
		{
			this.MouseMoving = true;
			if (this.FlatCheckBox7.Checked)
			{
				this.CustomRainbow.Start();
				return;
			}
			if (this.FlatCheckBox6.Checked)
			{
				float number = (float)e.X / (float)this.picRainbow.ClientSize.Width;
				this.SelectedColor = Rainbow.RainbowNumberToColor(number);
				this.SelectedRainbowNumber = Rainbow.ColorToRainbowNumber(this.SelectedColor);
				this.picRainbow.Refresh();
				this.FlatTabControl1.ActiveColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTabControl1.Refresh();
				this.ThirteenControlBox1.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenControlBox1.Refresh();
				this.MainForm.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.MainForm.Refresh();
				this.ThirteenButton1.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton1.Refresh();
				this.ThirteenButton2.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton2.Refresh();
				this.ThirteenButton3.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton3.Refresh();
				this.ThirteenButton4.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton4.Refresh();
				this.ThirteenButton5.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton5.Refresh();
				this.ThirteenButton6.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton6.Refresh();
				this.ThirteenButton7.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton7.Refresh();
				this.ThirteenButton8.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton8.Refresh();
				this.ThirteenButton9.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton9.Refresh();
				this.ThirteenButton10.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton10.Refresh();
				this.ThirteenButton11.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton11.Refresh();
				this.ThirteenButton12.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.ThirteenButton12.Refresh();
				this.MinimumCPS.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.MinimumCPS.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.MinimumCPS.Refresh();
				this.MaximumCPS.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.MaximumCPS.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.MaximumCPS.Refresh();
				this.FlatTrackBar1.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar1.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar1.Refresh();
				this.FlatTrackBar2.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar2.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar2.Refresh();
				this.FlatTrackBar3.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar3.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar3.Refresh();
				this.FlatTrackBar4.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar4.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar4.Refresh();
				this.FlatTrackBar5.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar5.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar5.Refresh();
				this.FlatTrackBar6.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar6.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar6.Refresh();
				this.FlatTrackBar7.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar7.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar7.Refresh();
				this.FlatTrackBar8.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar8.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
				this.FlatTrackBar8.Refresh();
				this.MouseMoving = false;
			}
		}

		// Token: 0x06000072 RID: 114 RVA: 0x00005D0A File Offset: 0x00003F0A
		private void picRainbow_Resize(object sender, EventArgs e)
		{
			this.picRainbow.Refresh();
		}

		// Token: 0x06000073 RID: 115 RVA: 0x00005D18 File Offset: 0x00003F18
		private void picRainbow_Paint(object sender, PaintEventArgs e)
		{
			using (Brush brush = Rainbow.RainbowBrush(new Point(0, 0), new Point(this.picRainbow.ClientSize.Width, this.picRainbow.ClientSize.Height)))
			{
				e.Graphics.FillRectangle(brush, this.picRainbow.ClientRectangle);
			}
			int num = (int)Math.Round((double)(this.SelectedRainbowNumber * (float)this.picRainbow.ClientSize.Width));
			Point[] points = new Point[]
			{
				new Point(num - 5, 0),
				new Point(num, 5),
				new Point(num + 5, 0)
			};
			e.Graphics.FillPolygon(Brushes.Black, points);
		}

		// Token: 0x06000074 RID: 116 RVA: 0x00005DFC File Offset: 0x00003FFC
		private void FlatCheckBox6_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox6.Checked)
			{
				this.CustomColor.Start();
				this.CustomRainbow.Stop();
				this.picRainbow.Refresh();
			}
			else
			{
				this.CustomColor.Stop();
			}
			if (this.FlatCheckBox7.Checked)
			{
				this.FlatCheckBox7.Checked = false;
			}
		}

		// Token: 0x06000075 RID: 117 RVA: 0x00005E60 File Offset: 0x00004060
		private void CustomRainbow_Tick(object sender, EventArgs e)
		{
			this.CustomRainbow.Interval = this.FlatTrackBar8.Value;
			this.SelectedRainbowNumber = (float)((double)this.SelectedRainbowNumber + 0.005);
			if (this.SelectedRainbowNumber > 1f)
			{
				this.SelectedRainbowNumber = 0f;
			}
			this.FlatTabControl1.ActiveColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTabControl1.Refresh();
			this.MainForm.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.MainForm.Refresh();
			this.ThirteenButton1.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton1.Refresh();
			this.ThirteenButton2.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton2.Refresh();
			this.ThirteenButton3.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton3.Refresh();
			this.ThirteenButton4.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton4.Refresh();
			this.ThirteenButton5.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton5.Refresh();
			this.ThirteenButton6.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton6.Refresh();
			this.ThirteenButton7.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton7.Refresh();
			this.ThirteenButton8.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton8.Refresh();
			this.ThirteenButton9.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton9.Refresh();
			this.ThirteenButton10.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton10.Refresh();
			this.ThirteenButton11.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton11.Refresh();
			this.ThirteenButton12.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenButton12.Refresh();
			this.MinimumCPS.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.MinimumCPS.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.MinimumCPS.Refresh();
			this.MaximumCPS.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.MaximumCPS.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.MaximumCPS.Refresh();
			this.FlatTrackBar1.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar1.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar1.Refresh();
			this.FlatTrackBar2.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar2.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar2.Refresh();
			this.FlatTrackBar3.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar3.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar3.Refresh();
			this.FlatTrackBar4.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar4.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar4.Refresh();
			this.FlatTrackBar5.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar5.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar5.Refresh();
			this.FlatTrackBar6.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar6.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar6.Refresh();
			this.FlatTrackBar7.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar7.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar7.Refresh();
			this.FlatTrackBar8.HatchColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar8.TrackColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.FlatTrackBar8.Refresh();
			this.ThirteenControlBox1.AccentColor = Rainbow.RainbowNumberToColor(this.SelectedRainbowNumber);
			this.ThirteenControlBox1.Refresh();
		}

		// Token: 0x06000076 RID: 118 RVA: 0x000062C8 File Offset: 0x000044C8
		private void FlatTrackBar8_Scroll_1(object sender)
		{
			this.FlatLabel33.Text = Conversions.ToString(this.FlatTrackBar8.Value);
		}

		// Token: 0x06000077 RID: 119 RVA: 0x000062E8 File Offset: 0x000044E8
		private void FlatCheckBox7_CheckedChanged(object sender)
		{
			if (this.FlatCheckBox7.Checked)
			{
				this.CustomRainbow.Start();
				this.picRainbow.Refresh();
			}
			else
			{
				this.CustomRainbow.Stop();
			}
			if (this.FlatCheckBox6.Checked)
			{
				this.FlatCheckBox6.Checked = false;
			}
		}

		// Token: 0x06000078 RID: 120 RVA: 0x00006340 File Offset: 0x00004540
		private void Prefetch_Tick(object sender, EventArgs e)
		{
			foreach (string text in Directory.GetFiles(this.prefetchPath))
			{
				if (text.Contains("EXPLORER"))
				{
					File.Delete(text);
				}
			}
			foreach (string text2 in Directory.GetFiles(this.prefetchPath))
			{
				if (text2.Contains("RUNDLL"))
				{
					File.Delete(text2);
				}
			}
			foreach (string text3 in Directory.GetFiles(this.prefetchPath))
			{
				if (text3.Contains("JNA"))
				{
					File.Delete(text3);
				}
			}
			foreach (string text4 in Directory.GetFiles(this.prefetchPath))
			{
				if (text4.Contains("METH"))
				{
					File.Delete(text4);
				}
			}
			foreach (string text5 in Directory.GetFiles(this.prefetchPath))
			{
				if (text5.Contains("MAIN"))
				{
					File.Delete(text5);
				}
			}
			foreach (string text6 in Directory.GetFiles(this.prefetchPath))
			{
				if (text6.Contains("EXPLORER"))
				{
					File.Delete(text6);
				}
			}
			foreach (string text7 in Directory.GetFiles(this.prefetchPath))
			{
				if (text7.Contains("ANYDESK"))
				{
					File.Delete(text7);
				}
			}
			foreach (string text8 in Directory.GetFiles(this.prefetchPath))
			{
				if (text8.Contains("WINLOGON"))
				{
					File.Delete(text8);
				}
			}
			foreach (string text9 in Directory.GetFiles(this.prefetchPath))
			{
				if (text9.Contains("SEARCH"))
				{
					File.Delete(text9);
				}
			}
			foreach (string text10 in Directory.GetFiles(this.prefetchPath))
			{
				if (text10.Contains("EXPLORER"))
				{
					File.Delete(text10);
				}
			}
			foreach (string text11 in Directory.GetFiles(this.prefetchPath))
			{
				if (text11.Contains("ANYDESK"))
				{
					File.Delete(text11);
				}
			}
			foreach (string text12 in Directory.GetFiles(this.prefetchPath))
			{
				if (text12.Contains("WINLOGON"))
				{
					File.Delete(text12);
				}
			}
			foreach (string text13 in Directory.GetFiles(this.prefetchPath))
			{
				if (text13.Contains("DLL"))
				{
					File.Delete(text13);
				}
			}
			this.Prefetch.Stop();
		}

		// Token: 0x06000079 RID: 121 RVA: 0x00006658 File Offset: 0x00004858
		private void Timer1_Tick(object sender, EventArgs e)
		{
			new WebClient();
			Main.clsComputerInfo clsComputerInfo = new Main.clsComputerInfo();
			string processorId = clsComputerInfo.GetProcessorId();
			string volumeSerial = clsComputerInfo.GetVolumeSerial("C");
			processorId + volumeSerial;
			string str = Strings.UCase(this.getMD5Hash(processorId + volumeSerial));
			HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create("REMOVED" + str);
			httpWebRequest.Method = "GET";
			WebResponse response = httpWebRequest.GetResponse();
			if (Operators.CompareString(new StreamReader(response.GetResponseStream(), Encoding.UTF8).ReadToEnd(), "REMOVED", false) == 0)
			{
				base.Show();
				return;
			}
			this.Melt3(1);
			Application.Exit();
		}

		// Token: 0x1700000D RID: 13
		// (get) Token: 0x0600007C RID: 124 RVA: 0x0000C0FD File Offset: 0x0000A2FD
		// (set) Token: 0x0600007D RID: 125 RVA: 0x0000C108 File Offset: 0x0000A308
		internal virtual System.Windows.Forms.Timer Autoclicker
		{
			[CompilerGenerated]
			get
			{
				return this._Autoclicker;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Autoclicker_Tick);
				System.Windows.Forms.Timer autoclicker = this._Autoclicker;
				if (autoclicker != null)
				{
					autoclicker.Tick -= value2;
				}
				this._Autoclicker = value;
				autoclicker = this._Autoclicker;
				if (autoclicker != null)
				{
					autoclicker.Tick += value2;
				}
			}
		}

		// Token: 0x1700000E RID: 14
		// (get) Token: 0x0600007E RID: 126 RVA: 0x0000C14B File Offset: 0x0000A34B
		// (set) Token: 0x0600007F RID: 127 RVA: 0x0000C154 File Offset: 0x0000A354
		internal virtual System.Windows.Forms.Timer WTap
		{
			[CompilerGenerated]
			get
			{
				return this._WTap;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.WTap_Tick);
				System.Windows.Forms.Timer wtap = this._WTap;
				if (wtap != null)
				{
					wtap.Tick -= value2;
				}
				this._WTap = value;
				wtap = this._WTap;
				if (wtap != null)
				{
					wtap.Tick += value2;
				}
			}
		}

		// Token: 0x1700000F RID: 15
		// (get) Token: 0x06000080 RID: 128 RVA: 0x0000C197 File Offset: 0x0000A397
		// (set) Token: 0x06000081 RID: 129 RVA: 0x0000C1A0 File Offset: 0x0000A3A0
		internal virtual System.Windows.Forms.Timer STap
		{
			[CompilerGenerated]
			get
			{
				return this._STap;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.STap_Tick);
				System.Windows.Forms.Timer stap = this._STap;
				if (stap != null)
				{
					stap.Tick -= value2;
				}
				this._STap = value;
				stap = this._STap;
				if (stap != null)
				{
					stap.Tick += value2;
				}
			}
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x06000082 RID: 130 RVA: 0x0000C1E3 File Offset: 0x0000A3E3
		// (set) Token: 0x06000083 RID: 131 RVA: 0x0000C1EC File Offset: 0x0000A3EC
		internal virtual System.Windows.Forms.Timer AutoPot
		{
			[CompilerGenerated]
			get
			{
				return this._AutoPot;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.AutoPot_Tick);
				System.Windows.Forms.Timer autoPot = this._AutoPot;
				if (autoPot != null)
				{
					autoPot.Tick -= value2;
				}
				this._AutoPot = value;
				autoPot = this._AutoPot;
				if (autoPot != null)
				{
					autoPot.Tick += value2;
				}
			}
		}

		// Token: 0x17000011 RID: 17
		// (get) Token: 0x06000084 RID: 132 RVA: 0x0000C22F File Offset: 0x0000A42F
		// (set) Token: 0x06000085 RID: 133 RVA: 0x0000C238 File Offset: 0x0000A438
		internal virtual System.Windows.Forms.Timer AutoRod
		{
			[CompilerGenerated]
			get
			{
				return this._AutoRod;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.AutoRod_Tick);
				System.Windows.Forms.Timer autoRod = this._AutoRod;
				if (autoRod != null)
				{
					autoRod.Tick -= value2;
				}
				this._AutoRod = value;
				autoRod = this._AutoRod;
				if (autoRod != null)
				{
					autoRod.Tick += value2;
				}
			}
		}

		// Token: 0x17000012 RID: 18
		// (get) Token: 0x06000086 RID: 134 RVA: 0x0000C27B File Offset: 0x0000A47B
		// (set) Token: 0x06000087 RID: 135 RVA: 0x0000C284 File Offset: 0x0000A484
		internal virtual System.Windows.Forms.Timer HideButton
		{
			[CompilerGenerated]
			get
			{
				return this._HideButton;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.HideButton_Tick_1);
				System.Windows.Forms.Timer hideButton = this._HideButton;
				if (hideButton != null)
				{
					hideButton.Tick -= value2;
				}
				this._HideButton = value;
				hideButton = this._HideButton;
				if (hideButton != null)
				{
					hideButton.Tick += value2;
				}
			}
		}

		// Token: 0x17000013 RID: 19
		// (get) Token: 0x06000088 RID: 136 RVA: 0x0000C2C7 File Offset: 0x0000A4C7
		// (set) Token: 0x06000089 RID: 137 RVA: 0x0000C2CF File Offset: 0x0000A4CF
		internal virtual Theme MainForm { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000014 RID: 20
		// (get) Token: 0x0600008A RID: 138 RVA: 0x0000C2D8 File Offset: 0x0000A4D8
		// (set) Token: 0x0600008B RID: 139 RVA: 0x0000C2E0 File Offset: 0x0000A4E0
		internal virtual ThirteenControlBox ThirteenControlBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000015 RID: 21
		// (get) Token: 0x0600008C RID: 140 RVA: 0x0000C2E9 File Offset: 0x0000A4E9
		// (set) Token: 0x0600008D RID: 141 RVA: 0x0000C2F1 File Offset: 0x0000A4F1
		internal virtual FlatTabControl FlatTabControl1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000016 RID: 22
		// (get) Token: 0x0600008E RID: 142 RVA: 0x0000C2FA File Offset: 0x0000A4FA
		// (set) Token: 0x0600008F RID: 143 RVA: 0x0000C302 File Offset: 0x0000A502
		internal virtual TabPage MainTab { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000017 RID: 23
		// (get) Token: 0x06000090 RID: 144 RVA: 0x0000C30B File Offset: 0x0000A50B
		// (set) Token: 0x06000091 RID: 145 RVA: 0x0000C314 File Offset: 0x0000A514
		internal virtual ThirteenButton ThirteenButton1
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton1_Click);
				EventHandler value3 = new EventHandler(this.ThirteenButton1_Click_1);
				ThirteenButton thirteenButton = this._ThirteenButton1;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
					thirteenButton.Click -= value3;
				}
				this._ThirteenButton1 = value;
				thirteenButton = this._ThirteenButton1;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
					thirteenButton.Click += value3;
				}
			}
		}

		// Token: 0x17000018 RID: 24
		// (get) Token: 0x06000092 RID: 146 RVA: 0x0000C372 File Offset: 0x0000A572
		// (set) Token: 0x06000093 RID: 147 RVA: 0x0000C37C File Offset: 0x0000A57C
		internal virtual ThirteenButton ThirteenButton5
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton5;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton5_Click);
				ThirteenButton thirteenButton = this._ThirteenButton5;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton5 = value;
				thirteenButton = this._ThirteenButton5;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x17000019 RID: 25
		// (get) Token: 0x06000094 RID: 148 RVA: 0x0000C3BF File Offset: 0x0000A5BF
		// (set) Token: 0x06000095 RID: 149 RVA: 0x0000C3C8 File Offset: 0x0000A5C8
		internal virtual ThirteenButton ThirteenButton4
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton4;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton4_Click);
				ThirteenButton thirteenButton = this._ThirteenButton4;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton4 = value;
				thirteenButton = this._ThirteenButton4;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x1700001A RID: 26
		// (get) Token: 0x06000096 RID: 150 RVA: 0x0000C40B File Offset: 0x0000A60B
		// (set) Token: 0x06000097 RID: 151 RVA: 0x0000C414 File Offset: 0x0000A614
		internal virtual ThirteenButton ThirteenButton3
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton3_Click);
				ThirteenButton thirteenButton = this._ThirteenButton3;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton3 = value;
				thirteenButton = this._ThirteenButton3;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x1700001B RID: 27
		// (get) Token: 0x06000098 RID: 152 RVA: 0x0000C457 File Offset: 0x0000A657
		// (set) Token: 0x06000099 RID: 153 RVA: 0x0000C460 File Offset: 0x0000A660
		internal virtual ThirteenButton ThirteenButton2
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton2_Click);
				ThirteenButton thirteenButton = this._ThirteenButton2;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton2 = value;
				thirteenButton = this._ThirteenButton2;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x1700001C RID: 28
		// (get) Token: 0x0600009A RID: 154 RVA: 0x0000C4A3 File Offset: 0x0000A6A3
		// (set) Token: 0x0600009B RID: 155 RVA: 0x0000C4AB File Offset: 0x0000A6AB
		internal virtual TabPage AutoclickerTab { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700001D RID: 29
		// (get) Token: 0x0600009C RID: 156 RVA: 0x0000C4B4 File Offset: 0x0000A6B4
		// (set) Token: 0x0600009D RID: 157 RVA: 0x0000C4BC File Offset: 0x0000A6BC
		internal virtual ThirteenButton ThirteenButton6
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton6_Click);
				ThirteenButton thirteenButton = this._ThirteenButton6;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton6 = value;
				thirteenButton = this._ThirteenButton6;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x1700001E RID: 30
		// (get) Token: 0x0600009E RID: 158 RVA: 0x0000C4FF File Offset: 0x0000A6FF
		// (set) Token: 0x0600009F RID: 159 RVA: 0x0000C507 File Offset: 0x0000A707
		internal virtual ThirteenTextBox ThirteenTextBox14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700001F RID: 31
		// (get) Token: 0x060000A0 RID: 160 RVA: 0x0000C510 File Offset: 0x0000A710
		// (set) Token: 0x060000A1 RID: 161 RVA: 0x0000C518 File Offset: 0x0000A718
		internal virtual FlatCheckBox FlatCheckBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000020 RID: 32
		// (get) Token: 0x060000A2 RID: 162 RVA: 0x0000C521 File Offset: 0x0000A721
		// (set) Token: 0x060000A3 RID: 163 RVA: 0x0000C529 File Offset: 0x0000A729
		internal virtual FlatCheckBox FlatCheckBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000021 RID: 33
		// (get) Token: 0x060000A4 RID: 164 RVA: 0x0000C532 File Offset: 0x0000A732
		// (set) Token: 0x060000A5 RID: 165 RVA: 0x0000C53A File Offset: 0x0000A73A
		internal virtual FlatTrackBar FlatTrackBar1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000022 RID: 34
		// (get) Token: 0x060000A6 RID: 166 RVA: 0x0000C543 File Offset: 0x0000A743
		// (set) Token: 0x060000A7 RID: 167 RVA: 0x0000C54B File Offset: 0x0000A74B
		internal virtual FlatLabel FlatLabel4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000023 RID: 35
		// (get) Token: 0x060000A8 RID: 168 RVA: 0x0000C554 File Offset: 0x0000A754
		// (set) Token: 0x060000A9 RID: 169 RVA: 0x0000C55C File Offset: 0x0000A75C
		internal virtual FlatLabel FlatLabel3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000024 RID: 36
		// (get) Token: 0x060000AA RID: 170 RVA: 0x0000C565 File Offset: 0x0000A765
		// (set) Token: 0x060000AB RID: 171 RVA: 0x0000C570 File Offset: 0x0000A770
		internal virtual FlatTrackBar MaximumCPS
		{
			[CompilerGenerated]
			get
			{
				return this._MaximumCPS;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.MaximumCPS_Scroll);
				FlatTrackBar maximumCPS = this._MaximumCPS;
				if (maximumCPS != null)
				{
					maximumCPS.Scroll -= obj;
				}
				this._MaximumCPS = value;
				maximumCPS = this._MaximumCPS;
				if (maximumCPS != null)
				{
					maximumCPS.Scroll += obj;
				}
			}
		}

		// Token: 0x17000025 RID: 37
		// (get) Token: 0x060000AC RID: 172 RVA: 0x0000C5B3 File Offset: 0x0000A7B3
		// (set) Token: 0x060000AD RID: 173 RVA: 0x0000C5BB File Offset: 0x0000A7BB
		internal virtual FlatLabel FlatLabel2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000026 RID: 38
		// (get) Token: 0x060000AE RID: 174 RVA: 0x0000C5C4 File Offset: 0x0000A7C4
		// (set) Token: 0x060000AF RID: 175 RVA: 0x0000C5CC File Offset: 0x0000A7CC
		internal virtual FlatLabel FlatLabel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000027 RID: 39
		// (get) Token: 0x060000B0 RID: 176 RVA: 0x0000C5D5 File Offset: 0x0000A7D5
		// (set) Token: 0x060000B1 RID: 177 RVA: 0x0000C5E0 File Offset: 0x0000A7E0
		internal virtual FlatTrackBar MinimumCPS
		{
			[CompilerGenerated]
			get
			{
				return this._MinimumCPS;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.MinimumCPS_Scroll);
				FlatTrackBar minimumCPS = this._MinimumCPS;
				if (minimumCPS != null)
				{
					minimumCPS.Scroll -= obj;
				}
				this._MinimumCPS = value;
				minimumCPS = this._MinimumCPS;
				if (minimumCPS != null)
				{
					minimumCPS.Scroll += obj;
				}
			}
		}

		// Token: 0x17000028 RID: 40
		// (get) Token: 0x060000B2 RID: 178 RVA: 0x0000C623 File Offset: 0x0000A823
		// (set) Token: 0x060000B3 RID: 179 RVA: 0x0000C62B File Offset: 0x0000A82B
		internal virtual TabPage WTapTab { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000029 RID: 41
		// (get) Token: 0x060000B4 RID: 180 RVA: 0x0000C634 File Offset: 0x0000A834
		// (set) Token: 0x060000B5 RID: 181 RVA: 0x0000C63C File Offset: 0x0000A83C
		internal virtual FlatCheckBox FlatCheckBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002A RID: 42
		// (get) Token: 0x060000B6 RID: 182 RVA: 0x0000C645 File Offset: 0x0000A845
		// (set) Token: 0x060000B7 RID: 183 RVA: 0x0000C64D File Offset: 0x0000A84D
		internal virtual FlatLabel FlatLabel5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002B RID: 43
		// (get) Token: 0x060000B8 RID: 184 RVA: 0x0000C656 File Offset: 0x0000A856
		// (set) Token: 0x060000B9 RID: 185 RVA: 0x0000C65E File Offset: 0x0000A85E
		internal virtual FlatLabel FlatLabel6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002C RID: 44
		// (get) Token: 0x060000BA RID: 186 RVA: 0x0000C667 File Offset: 0x0000A867
		// (set) Token: 0x060000BB RID: 187 RVA: 0x0000C670 File Offset: 0x0000A870
		internal virtual FlatTrackBar FlatTrackBar2
		{
			[CompilerGenerated]
			get
			{
				return this._FlatTrackBar2;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar2_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar2;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this._FlatTrackBar2 = value;
				flatTrackBar = this._FlatTrackBar2;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x1700002D RID: 45
		// (get) Token: 0x060000BC RID: 188 RVA: 0x0000C6B3 File Offset: 0x0000A8B3
		// (set) Token: 0x060000BD RID: 189 RVA: 0x0000C6BB File Offset: 0x0000A8BB
		internal virtual FlatLabel FlatLabel7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002E RID: 46
		// (get) Token: 0x060000BE RID: 190 RVA: 0x0000C6C4 File Offset: 0x0000A8C4
		// (set) Token: 0x060000BF RID: 191 RVA: 0x0000C6CC File Offset: 0x0000A8CC
		internal virtual FlatLabel FlatLabel8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700002F RID: 47
		// (get) Token: 0x060000C0 RID: 192 RVA: 0x0000C6D5 File Offset: 0x0000A8D5
		// (set) Token: 0x060000C1 RID: 193 RVA: 0x0000C6E0 File Offset: 0x0000A8E0
		internal virtual FlatTrackBar FlatTrackBar3
		{
			[CompilerGenerated]
			get
			{
				return this._FlatTrackBar3;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar3_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar3;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this._FlatTrackBar3 = value;
				flatTrackBar = this._FlatTrackBar3;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000030 RID: 48
		// (get) Token: 0x060000C2 RID: 194 RVA: 0x0000C723 File Offset: 0x0000A923
		// (set) Token: 0x060000C3 RID: 195 RVA: 0x0000C72B File Offset: 0x0000A92B
		internal virtual TabPage STapTab { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000031 RID: 49
		// (get) Token: 0x060000C4 RID: 196 RVA: 0x0000C734 File Offset: 0x0000A934
		// (set) Token: 0x060000C5 RID: 197 RVA: 0x0000C73C File Offset: 0x0000A93C
		internal virtual FlatCheckBox FlatCheckBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000032 RID: 50
		// (get) Token: 0x060000C6 RID: 198 RVA: 0x0000C745 File Offset: 0x0000A945
		// (set) Token: 0x060000C7 RID: 199 RVA: 0x0000C74D File Offset: 0x0000A94D
		internal virtual FlatLabel FlatLabel11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000033 RID: 51
		// (get) Token: 0x060000C8 RID: 200 RVA: 0x0000C756 File Offset: 0x0000A956
		// (set) Token: 0x060000C9 RID: 201 RVA: 0x0000C75E File Offset: 0x0000A95E
		internal virtual FlatLabel FlatLabel12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000034 RID: 52
		// (get) Token: 0x060000CA RID: 202 RVA: 0x0000C767 File Offset: 0x0000A967
		// (set) Token: 0x060000CB RID: 203 RVA: 0x0000C770 File Offset: 0x0000A970
		internal virtual FlatTrackBar FlatTrackBar4
		{
			[CompilerGenerated]
			get
			{
				return this._FlatTrackBar4;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar4_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar4;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this._FlatTrackBar4 = value;
				flatTrackBar = this._FlatTrackBar4;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000035 RID: 53
		// (get) Token: 0x060000CC RID: 204 RVA: 0x0000C7B3 File Offset: 0x0000A9B3
		// (set) Token: 0x060000CD RID: 205 RVA: 0x0000C7BB File Offset: 0x0000A9BB
		internal virtual FlatLabel FlatLabel13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000036 RID: 54
		// (get) Token: 0x060000CE RID: 206 RVA: 0x0000C7C4 File Offset: 0x0000A9C4
		// (set) Token: 0x060000CF RID: 207 RVA: 0x0000C7CC File Offset: 0x0000A9CC
		internal virtual FlatLabel FlatLabel14 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000037 RID: 55
		// (get) Token: 0x060000D0 RID: 208 RVA: 0x0000C7D5 File Offset: 0x0000A9D5
		// (set) Token: 0x060000D1 RID: 209 RVA: 0x0000C7E0 File Offset: 0x0000A9E0
		internal virtual FlatTrackBar FlatTrackBar5
		{
			[CompilerGenerated]
			get
			{
				return this._FlatTrackBar5;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar5_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar5;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this._FlatTrackBar5 = value;
				flatTrackBar = this._FlatTrackBar5;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000038 RID: 56
		// (get) Token: 0x060000D2 RID: 210 RVA: 0x0000C823 File Offset: 0x0000AA23
		// (set) Token: 0x060000D3 RID: 211 RVA: 0x0000C82B File Offset: 0x0000AA2B
		internal virtual TabPage AutoPotTab { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000039 RID: 57
		// (get) Token: 0x060000D4 RID: 212 RVA: 0x0000C834 File Offset: 0x0000AA34
		// (set) Token: 0x060000D5 RID: 213 RVA: 0x0000C83C File Offset: 0x0000AA3C
		internal virtual Label Label2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003A RID: 58
		// (get) Token: 0x060000D6 RID: 214 RVA: 0x0000C845 File Offset: 0x0000AA45
		// (set) Token: 0x060000D7 RID: 215 RVA: 0x0000C84D File Offset: 0x0000AA4D
		internal virtual Label Label1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003B RID: 59
		// (get) Token: 0x060000D8 RID: 216 RVA: 0x0000C856 File Offset: 0x0000AA56
		// (set) Token: 0x060000D9 RID: 217 RVA: 0x0000C860 File Offset: 0x0000AA60
		internal virtual FlatTrackBar FlatTrackBar6
		{
			[CompilerGenerated]
			get
			{
				return this._FlatTrackBar6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar6_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar6;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this._FlatTrackBar6 = value;
				flatTrackBar = this._FlatTrackBar6;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x1700003C RID: 60
		// (get) Token: 0x060000DA RID: 218 RVA: 0x0000C8A3 File Offset: 0x0000AAA3
		// (set) Token: 0x060000DB RID: 219 RVA: 0x0000C8AB File Offset: 0x0000AAAB
		internal virtual FlatCheckBox FlatCheckBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003D RID: 61
		// (get) Token: 0x060000DC RID: 220 RVA: 0x0000C8B4 File Offset: 0x0000AAB4
		// (set) Token: 0x060000DD RID: 221 RVA: 0x0000C8BC File Offset: 0x0000AABC
		internal virtual FlatLabel FlatLabel27 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003E RID: 62
		// (get) Token: 0x060000DE RID: 222 RVA: 0x0000C8C5 File Offset: 0x0000AAC5
		// (set) Token: 0x060000DF RID: 223 RVA: 0x0000C8CD File Offset: 0x0000AACD
		internal virtual FlatLabel FlatLabel26 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700003F RID: 63
		// (get) Token: 0x060000E0 RID: 224 RVA: 0x0000C8D6 File Offset: 0x0000AAD6
		// (set) Token: 0x060000E1 RID: 225 RVA: 0x0000C8DE File Offset: 0x0000AADE
		internal virtual FlatLabel FlatLabel25 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000040 RID: 64
		// (get) Token: 0x060000E2 RID: 226 RVA: 0x0000C8E7 File Offset: 0x0000AAE7
		// (set) Token: 0x060000E3 RID: 227 RVA: 0x0000C8EF File Offset: 0x0000AAEF
		internal virtual ThirteenTextBox ThirteenTextBox13 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000041 RID: 65
		// (get) Token: 0x060000E4 RID: 228 RVA: 0x0000C8F8 File Offset: 0x0000AAF8
		// (set) Token: 0x060000E5 RID: 229 RVA: 0x0000C900 File Offset: 0x0000AB00
		internal virtual ThirteenTextBox ThirteenTextBox12 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000042 RID: 66
		// (get) Token: 0x060000E6 RID: 230 RVA: 0x0000C909 File Offset: 0x0000AB09
		// (set) Token: 0x060000E7 RID: 231 RVA: 0x0000C911 File Offset: 0x0000AB11
		internal virtual ThirteenTextBox ThirteenTextBox11 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000043 RID: 67
		// (get) Token: 0x060000E8 RID: 232 RVA: 0x0000C91A File Offset: 0x0000AB1A
		// (set) Token: 0x060000E9 RID: 233 RVA: 0x0000C922 File Offset: 0x0000AB22
		internal virtual ThirteenTextBox ThirteenTextBox10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000044 RID: 68
		// (get) Token: 0x060000EA RID: 234 RVA: 0x0000C92B File Offset: 0x0000AB2B
		// (set) Token: 0x060000EB RID: 235 RVA: 0x0000C933 File Offset: 0x0000AB33
		internal virtual ThirteenTextBox ThirteenTextBox9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000045 RID: 69
		// (get) Token: 0x060000EC RID: 236 RVA: 0x0000C93C File Offset: 0x0000AB3C
		// (set) Token: 0x060000ED RID: 237 RVA: 0x0000C944 File Offset: 0x0000AB44
		internal virtual ThirteenTextBox ThirteenTextBox8 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000046 RID: 70
		// (get) Token: 0x060000EE RID: 238 RVA: 0x0000C94D File Offset: 0x0000AB4D
		// (set) Token: 0x060000EF RID: 239 RVA: 0x0000C955 File Offset: 0x0000AB55
		internal virtual ThirteenTextBox ThirteenTextBox7 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000047 RID: 71
		// (get) Token: 0x060000F0 RID: 240 RVA: 0x0000C95E File Offset: 0x0000AB5E
		// (set) Token: 0x060000F1 RID: 241 RVA: 0x0000C966 File Offset: 0x0000AB66
		internal virtual ThirteenTextBox ThirteenTextBox6 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000048 RID: 72
		// (get) Token: 0x060000F2 RID: 242 RVA: 0x0000C96F File Offset: 0x0000AB6F
		// (set) Token: 0x060000F3 RID: 243 RVA: 0x0000C977 File Offset: 0x0000AB77
		internal virtual ThirteenTextBox ThirteenTextBox5 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000049 RID: 73
		// (get) Token: 0x060000F4 RID: 244 RVA: 0x0000C980 File Offset: 0x0000AB80
		// (set) Token: 0x060000F5 RID: 245 RVA: 0x0000C988 File Offset: 0x0000AB88
		internal virtual ThirteenTextBox ThirteenTextBox4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004A RID: 74
		// (get) Token: 0x060000F6 RID: 246 RVA: 0x0000C991 File Offset: 0x0000AB91
		// (set) Token: 0x060000F7 RID: 247 RVA: 0x0000C999 File Offset: 0x0000AB99
		internal virtual ThirteenTextBox ThirteenTextBox3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004B RID: 75
		// (get) Token: 0x060000F8 RID: 248 RVA: 0x0000C9A2 File Offset: 0x0000ABA2
		// (set) Token: 0x060000F9 RID: 249 RVA: 0x0000C9AA File Offset: 0x0000ABAA
		internal virtual ThirteenTextBox ThirteenTextBox2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004C RID: 76
		// (get) Token: 0x060000FA RID: 250 RVA: 0x0000C9B3 File Offset: 0x0000ABB3
		// (set) Token: 0x060000FB RID: 251 RVA: 0x0000C9BB File Offset: 0x0000ABBB
		internal virtual FlatLabel FlatLabel24 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004D RID: 77
		// (get) Token: 0x060000FC RID: 252 RVA: 0x0000C9C4 File Offset: 0x0000ABC4
		// (set) Token: 0x060000FD RID: 253 RVA: 0x0000C9CC File Offset: 0x0000ABCC
		internal virtual FlatLabel FlatLabel23 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004E RID: 78
		// (get) Token: 0x060000FE RID: 254 RVA: 0x0000C9D5 File Offset: 0x0000ABD5
		// (set) Token: 0x060000FF RID: 255 RVA: 0x0000C9DD File Offset: 0x0000ABDD
		internal virtual FlatLabel FlatLabel22 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700004F RID: 79
		// (get) Token: 0x06000100 RID: 256 RVA: 0x0000C9E6 File Offset: 0x0000ABE6
		// (set) Token: 0x06000101 RID: 257 RVA: 0x0000C9EE File Offset: 0x0000ABEE
		internal virtual FlatLabel FlatLabel21 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000050 RID: 80
		// (get) Token: 0x06000102 RID: 258 RVA: 0x0000C9F7 File Offset: 0x0000ABF7
		// (set) Token: 0x06000103 RID: 259 RVA: 0x0000C9FF File Offset: 0x0000ABFF
		internal virtual FlatLabel FlatLabel20 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000051 RID: 81
		// (get) Token: 0x06000104 RID: 260 RVA: 0x0000CA08 File Offset: 0x0000AC08
		// (set) Token: 0x06000105 RID: 261 RVA: 0x0000CA10 File Offset: 0x0000AC10
		internal virtual FlatLabel FlatLabel19 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000052 RID: 82
		// (get) Token: 0x06000106 RID: 262 RVA: 0x0000CA19 File Offset: 0x0000AC19
		// (set) Token: 0x06000107 RID: 263 RVA: 0x0000CA21 File Offset: 0x0000AC21
		internal virtual FlatLabel FlatLabel18 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000053 RID: 83
		// (get) Token: 0x06000108 RID: 264 RVA: 0x0000CA2A File Offset: 0x0000AC2A
		// (set) Token: 0x06000109 RID: 265 RVA: 0x0000CA32 File Offset: 0x0000AC32
		internal virtual FlatLabel FlatLabel17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000054 RID: 84
		// (get) Token: 0x0600010A RID: 266 RVA: 0x0000CA3B File Offset: 0x0000AC3B
		// (set) Token: 0x0600010B RID: 267 RVA: 0x0000CA43 File Offset: 0x0000AC43
		internal virtual FlatLabel FlatLabel16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000055 RID: 85
		// (get) Token: 0x0600010C RID: 268 RVA: 0x0000CA4C File Offset: 0x0000AC4C
		// (set) Token: 0x0600010D RID: 269 RVA: 0x0000CA54 File Offset: 0x0000AC54
		internal virtual TabPage AutoRodTab { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000056 RID: 86
		// (get) Token: 0x0600010E RID: 270 RVA: 0x0000CA5D File Offset: 0x0000AC5D
		// (set) Token: 0x0600010F RID: 271 RVA: 0x0000CA65 File Offset: 0x0000AC65
		internal virtual TabPage Miscellaneous { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000057 RID: 87
		// (get) Token: 0x06000110 RID: 272 RVA: 0x0000CA6E File Offset: 0x0000AC6E
		// (set) Token: 0x06000111 RID: 273 RVA: 0x0000CA76 File Offset: 0x0000AC76
		internal virtual FlatLabel FlatLabel10 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000058 RID: 88
		// (get) Token: 0x06000112 RID: 274 RVA: 0x0000CA7F File Offset: 0x0000AC7F
		// (set) Token: 0x06000113 RID: 275 RVA: 0x0000CA87 File Offset: 0x0000AC87
		internal virtual FlatLabel FlatLabel9 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000059 RID: 89
		// (get) Token: 0x06000114 RID: 276 RVA: 0x0000CA90 File Offset: 0x0000AC90
		// (set) Token: 0x06000115 RID: 277 RVA: 0x0000CA98 File Offset: 0x0000AC98
		internal virtual ThirteenButton ThirteenButton8
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton8;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton8_Click);
				ThirteenButton thirteenButton = this._ThirteenButton8;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton8 = value;
				thirteenButton = this._ThirteenButton8;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x1700005A RID: 90
		// (get) Token: 0x06000116 RID: 278 RVA: 0x0000CADB File Offset: 0x0000ACDB
		// (set) Token: 0x06000117 RID: 279 RVA: 0x0000CAE3 File Offset: 0x0000ACE3
		internal virtual ThirteenTextBox ThirteenTextBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005B RID: 91
		// (get) Token: 0x06000118 RID: 280 RVA: 0x0000CAEC File Offset: 0x0000ACEC
		// (set) Token: 0x06000119 RID: 281 RVA: 0x0000CAF4 File Offset: 0x0000ACF4
		internal virtual TabPage DestructTab { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005C RID: 92
		// (get) Token: 0x0600011A RID: 282 RVA: 0x0000CAFD File Offset: 0x0000ACFD
		// (set) Token: 0x0600011B RID: 283 RVA: 0x0000CB08 File Offset: 0x0000AD08
		internal virtual ThirteenButton ThirteenButton7
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton7;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton7_Click);
				ThirteenButton thirteenButton = this._ThirteenButton7;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton7 = value;
				thirteenButton = this._ThirteenButton7;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x1700005D RID: 93
		// (get) Token: 0x0600011C RID: 284 RVA: 0x0000CB4B File Offset: 0x0000AD4B
		// (set) Token: 0x0600011D RID: 285 RVA: 0x0000CB53 File Offset: 0x0000AD53
		internal virtual ThirteenTextBox ThirteenTextBox15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700005E RID: 94
		// (get) Token: 0x0600011E RID: 286 RVA: 0x0000CB5C File Offset: 0x0000AD5C
		// (set) Token: 0x0600011F RID: 287 RVA: 0x0000CB64 File Offset: 0x0000AD64
		internal virtual ThirteenButton ThirteenButton9
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton9;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton9_Click);
				ThirteenButton thirteenButton = this._ThirteenButton9;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton9 = value;
				thirteenButton = this._ThirteenButton9;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x1700005F RID: 95
		// (get) Token: 0x06000120 RID: 288 RVA: 0x0000CBA7 File Offset: 0x0000ADA7
		// (set) Token: 0x06000121 RID: 289 RVA: 0x0000CBAF File Offset: 0x0000ADAF
		internal virtual ThirteenTextBox ThirteenTextBox16 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000060 RID: 96
		// (get) Token: 0x06000122 RID: 290 RVA: 0x0000CBB8 File Offset: 0x0000ADB8
		// (set) Token: 0x06000123 RID: 291 RVA: 0x0000CBC0 File Offset: 0x0000ADC0
		internal virtual ThirteenButton ThirteenButton10
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton10;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton10_Click);
				ThirteenButton thirteenButton = this._ThirteenButton10;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton10 = value;
				thirteenButton = this._ThirteenButton10;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x17000061 RID: 97
		// (get) Token: 0x06000124 RID: 292 RVA: 0x0000CC03 File Offset: 0x0000AE03
		// (set) Token: 0x06000125 RID: 293 RVA: 0x0000CC0B File Offset: 0x0000AE0B
		internal virtual ThirteenTextBox ThirteenTextBox17 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000062 RID: 98
		// (get) Token: 0x06000126 RID: 294 RVA: 0x0000CC14 File Offset: 0x0000AE14
		// (set) Token: 0x06000127 RID: 295 RVA: 0x0000CC1C File Offset: 0x0000AE1C
		internal virtual ThirteenButton ThirteenButton11
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton11;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton11_Click);
				ThirteenButton thirteenButton = this._ThirteenButton11;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton11 = value;
				thirteenButton = this._ThirteenButton11;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x17000063 RID: 99
		// (get) Token: 0x06000128 RID: 296 RVA: 0x0000CC5F File Offset: 0x0000AE5F
		// (set) Token: 0x06000129 RID: 297 RVA: 0x0000CC67 File Offset: 0x0000AE67
		internal virtual ThirteenTextBox ThirteenTextBox18 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000064 RID: 100
		// (get) Token: 0x0600012A RID: 298 RVA: 0x0000CC70 File Offset: 0x0000AE70
		// (set) Token: 0x0600012B RID: 299 RVA: 0x0000CC78 File Offset: 0x0000AE78
		internal virtual System.Windows.Forms.Timer Bind
		{
			[CompilerGenerated]
			get
			{
				return this._Bind;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Bind_Tick);
				System.Windows.Forms.Timer bind = this._Bind;
				if (bind != null)
				{
					bind.Tick -= value2;
				}
				this._Bind = value;
				bind = this._Bind;
				if (bind != null)
				{
					bind.Tick += value2;
				}
			}
		}

		// Token: 0x17000065 RID: 101
		// (get) Token: 0x0600012C RID: 300 RVA: 0x0000CCBB File Offset: 0x0000AEBB
		// (set) Token: 0x0600012D RID: 301 RVA: 0x0000CCC3 File Offset: 0x0000AEC3
		internal virtual ThirteenTextBox ThirteenTextBox21 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000066 RID: 102
		// (get) Token: 0x0600012E RID: 302 RVA: 0x0000CCCC File Offset: 0x0000AECC
		// (set) Token: 0x0600012F RID: 303 RVA: 0x0000CCD4 File Offset: 0x0000AED4
		internal virtual ThirteenTextBox ThirteenTextBox20 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000067 RID: 103
		// (get) Token: 0x06000130 RID: 304 RVA: 0x0000CCDD File Offset: 0x0000AEDD
		// (set) Token: 0x06000131 RID: 305 RVA: 0x0000CCE5 File Offset: 0x0000AEE5
		internal virtual ThirteenTextBox ThirteenTextBox19 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000068 RID: 104
		// (get) Token: 0x06000132 RID: 306 RVA: 0x0000CCEE File Offset: 0x0000AEEE
		// (set) Token: 0x06000133 RID: 307 RVA: 0x0000CCF6 File Offset: 0x0000AEF6
		internal virtual FlatLabel FlatLabel28 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000069 RID: 105
		// (get) Token: 0x06000134 RID: 308 RVA: 0x0000CCFF File Offset: 0x0000AEFF
		// (set) Token: 0x06000135 RID: 309 RVA: 0x0000CD08 File Offset: 0x0000AF08
		internal virtual ThirteenButton ThirteenButton12
		{
			[CompilerGenerated]
			get
			{
				return this._ThirteenButton12;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.ThirteenButton12_Click);
				ThirteenButton thirteenButton = this._ThirteenButton12;
				if (thirteenButton != null)
				{
					thirteenButton.Click -= value2;
				}
				this._ThirteenButton12 = value;
				thirteenButton = this._ThirteenButton12;
				if (thirteenButton != null)
				{
					thirteenButton.Click += value2;
				}
			}
		}

		// Token: 0x1700006A RID: 106
		// (get) Token: 0x06000136 RID: 310 RVA: 0x0000CD4B File Offset: 0x0000AF4B
		// (set) Token: 0x06000137 RID: 311 RVA: 0x0000CD53 File Offset: 0x0000AF53
		internal virtual FlatLabel FlatLabel31 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006B RID: 107
		// (get) Token: 0x06000138 RID: 312 RVA: 0x0000CD5C File Offset: 0x0000AF5C
		// (set) Token: 0x06000139 RID: 313 RVA: 0x0000CD64 File Offset: 0x0000AF64
		internal virtual FlatLabel FlatLabel30 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006C RID: 108
		// (get) Token: 0x0600013A RID: 314 RVA: 0x0000CD6D File Offset: 0x0000AF6D
		// (set) Token: 0x0600013B RID: 315 RVA: 0x0000CD75 File Offset: 0x0000AF75
		internal virtual FlatLabel FlatLabel29 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006D RID: 109
		// (get) Token: 0x0600013C RID: 316 RVA: 0x0000CD7E File Offset: 0x0000AF7E
		// (set) Token: 0x0600013D RID: 317 RVA: 0x0000CD86 File Offset: 0x0000AF86
		internal virtual Label Label3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006E RID: 110
		// (get) Token: 0x0600013E RID: 318 RVA: 0x0000CD8F File Offset: 0x0000AF8F
		// (set) Token: 0x0600013F RID: 319 RVA: 0x0000CD97 File Offset: 0x0000AF97
		internal virtual Label Label4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700006F RID: 111
		// (get) Token: 0x06000140 RID: 320 RVA: 0x0000CDA0 File Offset: 0x0000AFA0
		// (set) Token: 0x06000141 RID: 321 RVA: 0x0000CDA8 File Offset: 0x0000AFA8
		internal virtual FlatTrackBar FlatTrackBar7
		{
			[CompilerGenerated]
			get
			{
				return this._FlatTrackBar7;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar7_Scroll);
				FlatTrackBar flatTrackBar = this._FlatTrackBar7;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this._FlatTrackBar7 = value;
				flatTrackBar = this._FlatTrackBar7;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000070 RID: 112
		// (get) Token: 0x06000142 RID: 322 RVA: 0x0000CDEB File Offset: 0x0000AFEB
		// (set) Token: 0x06000143 RID: 323 RVA: 0x0000CDF4 File Offset: 0x0000AFF4
		internal virtual PictureBox picRainbow
		{
			[CompilerGenerated]
			get
			{
				return this._picRainbow;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				MouseEventHandler value2 = new MouseEventHandler(this.picRainbow_MouseMove);
				EventHandler value3 = new EventHandler(this.picRainbow_Resize);
				PaintEventHandler value4 = new PaintEventHandler(this.picRainbow_Paint);
				PictureBox picRainbow = this._picRainbow;
				if (picRainbow != null)
				{
					picRainbow.MouseClick -= value2;
					picRainbow.Resize -= value3;
					picRainbow.Paint -= value4;
				}
				this._picRainbow = value;
				picRainbow = this._picRainbow;
				if (picRainbow != null)
				{
					picRainbow.MouseClick += value2;
					picRainbow.Resize += value3;
					picRainbow.Paint += value4;
				}
			}
		}

		// Token: 0x17000071 RID: 113
		// (get) Token: 0x06000144 RID: 324 RVA: 0x0000CE6D File Offset: 0x0000B06D
		// (set) Token: 0x06000145 RID: 325 RVA: 0x0000CE78 File Offset: 0x0000B078
		internal virtual FlatCheckBox FlatCheckBox7
		{
			[CompilerGenerated]
			get
			{
				return this._FlatCheckBox7;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox7_CheckedChanged);
				FlatCheckBox flatCheckBox = this._FlatCheckBox7;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this._FlatCheckBox7 = value;
				flatCheckBox = this._FlatCheckBox7;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x17000072 RID: 114
		// (get) Token: 0x06000146 RID: 326 RVA: 0x0000CEBB File Offset: 0x0000B0BB
		// (set) Token: 0x06000147 RID: 327 RVA: 0x0000CEC4 File Offset: 0x0000B0C4
		internal virtual FlatCheckBox FlatCheckBox6
		{
			[CompilerGenerated]
			get
			{
				return this._FlatCheckBox6;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatCheckBox.CheckedChangedEventHandler obj = new FlatCheckBox.CheckedChangedEventHandler(this.FlatCheckBox6_CheckedChanged);
				FlatCheckBox flatCheckBox = this._FlatCheckBox6;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged -= obj;
				}
				this._FlatCheckBox6 = value;
				flatCheckBox = this._FlatCheckBox6;
				if (flatCheckBox != null)
				{
					flatCheckBox.CheckedChanged += obj;
				}
			}
		}

		// Token: 0x17000073 RID: 115
		// (get) Token: 0x06000148 RID: 328 RVA: 0x0000CF07 File Offset: 0x0000B107
		// (set) Token: 0x06000149 RID: 329 RVA: 0x0000CF10 File Offset: 0x0000B110
		internal virtual System.Windows.Forms.Timer CustomRainbow
		{
			[CompilerGenerated]
			get
			{
				return this._CustomRainbow;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.CustomRainbow_Tick);
				System.Windows.Forms.Timer customRainbow = this._CustomRainbow;
				if (customRainbow != null)
				{
					customRainbow.Tick -= value2;
				}
				this._CustomRainbow = value;
				customRainbow = this._CustomRainbow;
				if (customRainbow != null)
				{
					customRainbow.Tick += value2;
				}
			}
		}

		// Token: 0x17000074 RID: 116
		// (get) Token: 0x0600014A RID: 330 RVA: 0x0000CF53 File Offset: 0x0000B153
		// (set) Token: 0x0600014B RID: 331 RVA: 0x0000CF5B File Offset: 0x0000B15B
		internal virtual FlatLabel FlatLabel33 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000075 RID: 117
		// (get) Token: 0x0600014C RID: 332 RVA: 0x0000CF64 File Offset: 0x0000B164
		// (set) Token: 0x0600014D RID: 333 RVA: 0x0000CF6C File Offset: 0x0000B16C
		internal virtual FlatLabel FlatLabel32 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000076 RID: 118
		// (get) Token: 0x0600014E RID: 334 RVA: 0x0000CF75 File Offset: 0x0000B175
		// (set) Token: 0x0600014F RID: 335 RVA: 0x0000CF80 File Offset: 0x0000B180
		internal virtual FlatTrackBar FlatTrackBar8
		{
			[CompilerGenerated]
			get
			{
				return this._FlatTrackBar8;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				FlatTrackBar.ScrollEventHandler obj = new FlatTrackBar.ScrollEventHandler(this.FlatTrackBar8_Scroll_1);
				FlatTrackBar flatTrackBar = this._FlatTrackBar8;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll -= obj;
				}
				this._FlatTrackBar8 = value;
				flatTrackBar = this._FlatTrackBar8;
				if (flatTrackBar != null)
				{
					flatTrackBar.Scroll += obj;
				}
			}
		}

		// Token: 0x17000077 RID: 119
		// (get) Token: 0x06000150 RID: 336 RVA: 0x0000CFC3 File Offset: 0x0000B1C3
		// (set) Token: 0x06000151 RID: 337 RVA: 0x0000CFCB File Offset: 0x0000B1CB
		internal virtual System.Windows.Forms.Timer CustomColor { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000078 RID: 120
		// (get) Token: 0x06000152 RID: 338 RVA: 0x0000CFD4 File Offset: 0x0000B1D4
		// (set) Token: 0x06000153 RID: 339 RVA: 0x0000CFDC File Offset: 0x0000B1DC
		internal virtual FlatLabel FlatLabel34 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000079 RID: 121
		// (get) Token: 0x06000154 RID: 340 RVA: 0x0000CFE5 File Offset: 0x0000B1E5
		// (set) Token: 0x06000155 RID: 341 RVA: 0x0000CFED File Offset: 0x0000B1ED
		internal virtual ThirteenComboBox ThirteenComboBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007A RID: 122
		// (get) Token: 0x06000156 RID: 342 RVA: 0x0000CFF6 File Offset: 0x0000B1F6
		// (set) Token: 0x06000157 RID: 343 RVA: 0x0000D000 File Offset: 0x0000B200
		internal virtual System.Windows.Forms.Timer Prefetch
		{
			[CompilerGenerated]
			get
			{
				return this._Prefetch;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Prefetch_Tick);
				System.Windows.Forms.Timer prefetch = this._Prefetch;
				if (prefetch != null)
				{
					prefetch.Tick -= value2;
				}
				this._Prefetch = value;
				prefetch = this._Prefetch;
				if (prefetch != null)
				{
					prefetch.Tick += value2;
				}
			}
		}

		// Token: 0x1700007B RID: 123
		// (get) Token: 0x06000158 RID: 344 RVA: 0x0000D043 File Offset: 0x0000B243
		// (set) Token: 0x06000159 RID: 345 RVA: 0x0000D04B File Offset: 0x0000B24B
		internal virtual FlatLabel FlatLabel15 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007C RID: 124
		// (get) Token: 0x0600015A RID: 346 RVA: 0x0000D054 File Offset: 0x0000B254
		// (set) Token: 0x0600015B RID: 347 RVA: 0x0000D05C File Offset: 0x0000B25C
		internal virtual FlatLabel FlatLabel41 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007D RID: 125
		// (get) Token: 0x0600015C RID: 348 RVA: 0x0000D065 File Offset: 0x0000B265
		// (set) Token: 0x0600015D RID: 349 RVA: 0x0000D06D File Offset: 0x0000B26D
		internal virtual FlatLabel FlatLabel40 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007E RID: 126
		// (get) Token: 0x0600015E RID: 350 RVA: 0x0000D076 File Offset: 0x0000B276
		// (set) Token: 0x0600015F RID: 351 RVA: 0x0000D07E File Offset: 0x0000B27E
		internal virtual FlatLabel FlatLabel39 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x1700007F RID: 127
		// (get) Token: 0x06000160 RID: 352 RVA: 0x0000D087 File Offset: 0x0000B287
		// (set) Token: 0x06000161 RID: 353 RVA: 0x0000D08F File Offset: 0x0000B28F
		internal virtual FlatLabel FlatLabel38 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000080 RID: 128
		// (get) Token: 0x06000162 RID: 354 RVA: 0x0000D098 File Offset: 0x0000B298
		// (set) Token: 0x06000163 RID: 355 RVA: 0x0000D0A0 File Offset: 0x0000B2A0
		internal virtual FlatLabel FlatLabel37 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000081 RID: 129
		// (get) Token: 0x06000164 RID: 356 RVA: 0x0000D0A9 File Offset: 0x0000B2A9
		// (set) Token: 0x06000165 RID: 357 RVA: 0x0000D0B1 File Offset: 0x0000B2B1
		internal virtual FlatLabel FlatLabel36 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000082 RID: 130
		// (get) Token: 0x06000166 RID: 358 RVA: 0x0000D0BA File Offset: 0x0000B2BA
		// (set) Token: 0x06000167 RID: 359 RVA: 0x0000D0C2 File Offset: 0x0000B2C2
		internal virtual FlatLabel FlatLabel35 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000083 RID: 131
		// (get) Token: 0x06000168 RID: 360 RVA: 0x0000D0CB File Offset: 0x0000B2CB
		// (set) Token: 0x06000169 RID: 361 RVA: 0x0000D0D4 File Offset: 0x0000B2D4
		internal virtual ThirteenButton Donate
		{
			[CompilerGenerated]
			get
			{
				return this._Donate;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Donate_Click);
				ThirteenButton donate = this._Donate;
				if (donate != null)
				{
					donate.Click -= value2;
				}
				this._Donate = value;
				donate = this._Donate;
				if (donate != null)
				{
					donate.Click += value2;
				}
			}
		}

		// Token: 0x0400002D RID: 45
		private bool shouldRClick;

		// Token: 0x0400002E RID: 46
		private string prefetchPath;

		// Token: 0x0400002F RID: 47
		private int toggle;

		// Token: 0x04000030 RID: 48
		private int extendtoggle;

		// Token: 0x04000031 RID: 49
		private Random rnd;

		// Token: 0x04000032 RID: 50
		private int slot;

		// Token: 0x04000033 RID: 51
		private string keypot;

		// Token: 0x04000034 RID: 52
		private string bind1;

		// Token: 0x04000035 RID: 53
		private string bind2;

		// Token: 0x04000036 RID: 54
		private string bind3;

		// Token: 0x04000037 RID: 55
		private string bind4;

		// Token: 0x04000038 RID: 56
		private string bind5;

		// Token: 0x04000039 RID: 57
		private string bind6;

		// Token: 0x0400003A RID: 58
		private string bind7;

		// Token: 0x0400003B RID: 59
		private string bind8;

		// Token: 0x0400003C RID: 60
		private string bind9;

		// Token: 0x0400003D RID: 61
		public const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x0400003E RID: 62
		public const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x0400003F RID: 63
		public const int MOUSEEVENTF_MIDDLEDOWN = 32;

		// Token: 0x04000040 RID: 64
		public const int MOUSEEVENTF_MIDDLEUP = 64;

		// Token: 0x04000041 RID: 65
		public const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x04000042 RID: 66
		public const int MOUSEEVENTF_RIGHTUP = 16;

		// Token: 0x04000043 RID: 67
		public const int MOUSEEVENTF_MOVE = 1;

		// Token: 0x04000044 RID: 68
		public bool bHook;

		// Token: 0x04000045 RID: 69
		public bool bTimerEnd;

		// Token: 0x04000046 RID: 70
		public DateTime timeLastClick;

		// Token: 0x04000047 RID: 71
		public int intervalClick;

		// Token: 0x04000049 RID: 73
		private bool isHeld_LMB;

		// Token: 0x0400004A RID: 74
		private bool shouldClick;

		// Token: 0x0400004B RID: 75
		private bool ignoreNextRelease;

		// Token: 0x0400004C RID: 76
		private string directory;

		// Token: 0x0400004D RID: 77
		private Color SelectedColor;

		// Token: 0x0400004E RID: 78
		private float SelectedRainbowNumber;

		// Token: 0x0400004F RID: 79
		private bool MouseMoving;

		// Token: 0x02000041 RID: 65
		public struct POINTAPI
		{
			// Token: 0x040001A7 RID: 423
			public int x;

			// Token: 0x040001A8 RID: 424
			public int y;
		}

		// Token: 0x02000042 RID: 66
		public class clsComputerInfo
		{
			// Token: 0x06000368 RID: 872 RVA: 0x00015B10 File Offset: 0x00013D10
			internal string GetProcessorId()
			{
				string result = string.Empty;
				SelectQuery query = new SelectQuery("Win32_processor");
				ManagementObjectSearcher managementObjectSearcher = new ManagementObjectSearcher(query);
				try
				{
					foreach (ManagementBaseObject managementBaseObject in managementObjectSearcher.Get())
					{
						result = ((ManagementObject)managementBaseObject)["processorId"].ToString();
					}
				}
				finally
				{
					ManagementObjectCollection.ManagementObjectEnumerator enumerator;
					if (enumerator != null)
					{
						((IDisposable)enumerator).Dispose();
					}
				}
				return result;
			}

			// Token: 0x06000369 RID: 873 RVA: 0x00015B84 File Offset: 0x00013D84
			internal string GetVolumeSerial(string strDriveLetter = "C")
			{
				ManagementObject managementObject = new ManagementObject(string.Format("win32_logicaldisk.deviceid=\"{0}:\"", strDriveLetter));
				managementObject.Get();
				return managementObject["VolumeSerialNumber"].ToString();
			}
		}
	}
}
