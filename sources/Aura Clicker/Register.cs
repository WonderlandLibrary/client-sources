using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using AuraLLC.ProtectionTools;
using AuraLLC.ProtectionTools.AntiDebug;
using AuraLLC.ProtectionTools.AntiDebugTools;
using ComponentFactory.Krypton.Toolkit;
using Guna.UI2.WinForms;
using Microsoft.Win32;
using uhssvc.Properties;

namespace uhssvc
{
	// Token: 0x0200002D RID: 45
	public class Register : KryptonForm
	{
		// Token: 0x060001AA RID: 426
		[DllImport("Gdi32.dll")]
		private static extern IntPtr CreateRoundRectRgn(int int_0, int int_1, int int_2, int int_3, int int_4, int int_5);

		// Token: 0x060001AB RID: 427 RVA: 0x000176C0 File Offset: 0x000158C0
		public Register()
		{
			base.Opacity = 0.1;
			this.InitializeComponent();
			base.Region = Region.FromHrgn(Register.CreateRoundRectRgn(0, 0, base.Width, base.Height, 25, 25));
			this.releaseId = Registry.GetValue("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "ReleaseId", "").ToString();
			IntPtr intptr_ = Main.LoadLibrary("kernel32.dll");
			IntPtr procAddress = Main.GetProcAddress(intptr_, "IsDebuggerPresent");
			byte[] array = new byte[1];
			Marshal.Copy(procAddress, array, 0, 1);
			IntPtr procAddress2 = Main.GetProcAddress(intptr_, "CheckRemoteDebuggerPresent");
			array = new byte[1];
			Marshal.Copy(procAddress2, array, 0, 1);
			if (array[0] == 233 || array[0] == 233)
			{
				Program.Hacking();
				Main.selfDelete();
				Environment.Exit(0);
			}
		}

		// Token: 0x060001AC RID: 428 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060001AD RID: 429 RVA: 0x000025E7 File Offset: 0x000007E7
		private void ExitCTRBOX_Click(object sender, EventArgs e)
		{
			Environment.Exit(0);
		}

		// Token: 0x060001AE RID: 430 RVA: 0x000025EF File Offset: 0x000007EF
		private void MiniCTRBOX_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x060001AF RID: 431 RVA: 0x000177B4 File Offset: 0x000159B4
		private void Register_Load(object sender, EventArgs e)
		{
			Process[] processesByName = Process.GetProcessesByName("javaw");
			if (processesByName.Length == 0)
			{
				Environment.Exit(0);
			}
			new Thread(delegate()
			{
				for (;;)
				{
					Scanner.ScanAndKill();
					if (DebugProtect1.PerformChecks() == 1)
					{
						Environment.FailFast("");
					}
					if (DebugProtect2.PerformChecks() == 1)
					{
						Environment.FailFast("");
					}
					Thread.Sleep(1000);
				}
			});
			if (MainModule.IsEmulation())
			{
				Register.AutoClosingMessageBox.Show("Process is being emulated.", "Error", 4000);
				Program.Hacking();
				Main.selfDelete();
				Environment.Exit(0);
			}
			else if (MainModule.IsVM())
			{
				Register.AutoClosingMessageBox.Show("Aura can't run in VMs. \nPlease use a real machine.", "Error", 4000);
				Program.Hacking();
				Main.selfDelete();
				Environment.Exit(0);
			}
			else if (MainModule.IsSandboxie())
			{
				Register.AutoClosingMessageBox.Show("Aura can't run in Sandboxes. \nPlease use a real machine.", "Error", 4000);
				Program.Hacking();
				Main.selfDelete();
				Environment.Exit(0);
			}
		}

		// Token: 0x060001B0 RID: 432 RVA: 0x000025E5 File Offset: 0x000007E5
		private void LoginBTN_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060001B1 RID: 433 RVA: 0x00017884 File Offset: 0x00015A84
		private void timer1_Tick(object sender, EventArgs e)
		{
			foreach (Process process in Process.GetProcessesByName("Wireshark"))
			{
				try
				{
					process.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process2 in Process.GetProcessesByName("HttpDebugger"))
			{
				try
				{
					process2.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process3 in Process.GetProcessesByName("Fiddler"))
			{
				try
				{
					process3.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process4 in Process.GetProcessesByName("ProcessHacker"))
			{
				try
				{
					process4.Kill();
				}
				catch
				{
				}
			}
		}

		// Token: 0x060001B2 RID: 434 RVA: 0x00017980 File Offset: 0x00015B80
		private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
		{
			foreach (Process process in Process.GetProcessesByName("Wireshark"))
			{
				try
				{
					process.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process2 in Process.GetProcessesByName("HttpDebugger"))
			{
				try
				{
					process2.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process3 in Process.GetProcessesByName("Fiddler"))
			{
				try
				{
					process3.Kill();
				}
				catch
				{
				}
			}
			foreach (Process process4 in Process.GetProcessesByName("ProcessHacker"))
			{
				try
				{
					process4.Kill();
				}
				catch
				{
				}
			}
		}

		// Token: 0x060001B3 RID: 435 RVA: 0x00017A80 File Offset: 0x00015C80
		private static string title()
		{
			string result = string.Empty;
			IntPtr foregroundWindow = Register.GetForegroundWindow();
			int num = Register.GetWindowTextLength(foregroundWindow) + 1;
			StringBuilder stringBuilder = new StringBuilder(num);
			if (Register.GetWindowText(foregroundWindow, stringBuilder, num) > 0)
			{
				result = stringBuilder.ToString();
			}
			return result;
		}

		// Token: 0x060001B4 RID: 436
		[DllImport("user32.dll")]
		public static extern IntPtr FindWindow(string string_0, string string_1);

		// Token: 0x060001B5 RID: 437
		[DllImport("user32", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int GetWindowText(IntPtr intptr_0, StringBuilder stringBuilder_0, int int_0);

		// Token: 0x060001B6 RID: 438
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x060001B7 RID: 439
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr intptr_0);

		// Token: 0x060001B8 RID: 440 RVA: 0x00017AC4 File Offset: 0x00015CC4
		private void timer2_Tick(object sender, EventArgs e)
		{
			if (!(Register.FindWindow("LWJGL", Register.title().ToString()).ToString() == Register.GetForegroundWindow().ToString()))
			{
				List<string> list = new List<string>();
				list.Clear();
				list.Add("processhacker");
				list.Add("ollydbg");
				list.Add("tcpview");
				list.Add("autoruns");
				list.Add("autorunsc");
				list.Add("filemon");
				list.Add("procmon");
				list.Add("idag");
				list.Add("hookshark");
				list.Add("peid");
				list.Add("lordpe");
				list.Add("regmon");
				list.Add("idaq");
				list.Add("idaq64");
				list.Add("immunitydebugger");
				list.Add("wireshark");
				list.Add("dumpcap");
				list.Add("hookexplorer");
				list.Add("importrec");
				list.Add("petools");
				list.Add("lordpe");
				list.Add("sysinspector");
				list.Add("proc_analyzer");
				list.Add("sysanalyzer");
				list.Add("sniff_hit");
				list.Add("joeboxcontrol");
				list.Add("joeboxserver");
				list.Add("ida");
				list.Add("ida64");
				list.Add("httpdebuggersvc");
				list.Add("driverview");
				list.Add("dbgview");
				list.Add("glasswire");
				list.Add("winobj");
				list.Add("megadumper");
				foreach (Process process in Process.GetProcesses())
				{
					if (list.Contains(process.ProcessName.ToLower()) && !this.alreadyAlerted)
					{
						this.debuggerRunning = process.ProcessName;
						this.alreadyAlerted = true;
						Program.Hacking();
						Main.selfDelete();
						Environment.Exit(0);
					}
				}
			}
		}

		// Token: 0x060001B9 RID: 441 RVA: 0x0000668C File Offset: 0x0000488C
		private void timer3_Tick(object sender, EventArgs e)
		{
			Process[] processesByName = Process.GetProcessesByName("javaw");
			if (processesByName.Length == 0)
			{
				Environment.Exit(0);
			}
		}

		// Token: 0x060001BA RID: 442 RVA: 0x000025F8 File Offset: 0x000007F8
		private void Register_FormClosed(object sender, FormClosedEventArgs e)
		{
			Program.selfDelete();
		}

		// Token: 0x060001BB RID: 443 RVA: 0x00017D14 File Offset: 0x00015F14
		private void kryptonButton2_Click(object sender, EventArgs e)
		{
			base.Hide();
			Login login = new Login();
			login.Show();
		}

		// Token: 0x060001BC RID: 444 RVA: 0x00017D34 File Offset: 0x00015F34
		private void kryptonButton1_Click(object sender, EventArgs e)
		{
			if (API.Register(this.username.Text, this.password.Text, this.email.Text, this.license.Text))
			{
				MessageBox.Show("Register has been successful!", "Success", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
			}
		}

		// Token: 0x060001BD RID: 445 RVA: 0x000025E5 File Offset: 0x000007E5
		private void username_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060001BE RID: 446 RVA: 0x00017D88 File Offset: 0x00015F88
		private void timer4_Tick(object sender, EventArgs e)
		{
			if (this.username.Focused)
			{
				this.username.Text = "";
			}
			else
			{
				this.username.Text = "Username";
			}
			if (this.password.Focused)
			{
				this.password.Text = "";
			}
			else
			{
				this.password.Text = "Password";
			}
			if (this.email.Focused)
			{
				this.email.Text = "";
			}
			else
			{
				this.email.Text = "Email";
			}
			if (this.license.Focused)
			{
				this.license.Text = "";
			}
			else
			{
				this.license.Text = "License";
			}
		}

		// Token: 0x060001BF RID: 447 RVA: 0x00002BC9 File Offset: 0x00000DC9
		protected override void Dispose(bool bool_0)
		{
			if (bool_0 && this.components != null)
			{
				this.components.Dispose();
			}
			base.Dispose(bool_0);
		}

		// Token: 0x060001C0 RID: 448 RVA: 0x00017E54 File Offset: 0x00016054
		private void InitializeComponent()
		{
			this.components = new Container();
			this.StripLBL = new Label();
			this.guna2DragControl1 = new Guna2DragControl(this.components);
			this.timer1 = new System.Windows.Forms.Timer(this.components);
			this.backgroundWorker1 = new BackgroundWorker();
			this.timer2 = new System.Windows.Forms.Timer(this.components);
			this.timer3 = new System.Windows.Forms.Timer(this.components);
			this.kryptonPalette1 = new KryptonPalette(this.components);
			this.kryptonButton2 = new KryptonButton();
			this.kryptonButton1 = new KryptonButton();
			this.pictureBox1 = new PictureBox();
			this.password = new Guna2TextBox();
			this.username = new Guna2TextBox();
			this.license = new Guna2TextBox();
			this.email = new Guna2TextBox();
			((ISupportInitialize)this.pictureBox1).BeginInit();
			base.SuspendLayout();
			this.StripLBL.AutoSize = true;
			this.StripLBL.Font = new Font("Yu Gothic", 15f, FontStyle.Bold);
			this.StripLBL.ForeColor = Color.FromArgb(64, 64, 64);
			this.StripLBL.Location = new Point(40, 45);
			this.StripLBL.Margin = new Padding(2, 0, 2, 0);
			this.StripLBL.Name = "StripLBL";
			this.StripLBL.Size = new Size(161, 26);
			this.StripLBL.TabIndex = 30;
			this.StripLBL.Text = "Aura | Register";
			this.guna2DragControl1.TargetControl = this;
			this.timer1.Enabled = true;
			this.timer1.Tick += this.timer1_Tick;
			this.backgroundWorker1.DoWork += this.backgroundWorker1_DoWork;
			this.timer2.Enabled = true;
			this.timer2.Tick += this.timer2_Tick;
			this.timer3.Enabled = true;
			this.timer3.Interval = 1;
			this.timer3.Tick += this.timer3_Tick;
			this.kryptonPalette1.ButtonSpecs.FormClose.Image = Resources.mc_red;
			this.kryptonPalette1.ButtonSpecs.FormMax.Image = Resources.mc_yellw;
			this.kryptonPalette1.ButtonSpecs.FormMin.Image = Resources.mc_green;
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateCommon.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateCommon.Border.DrawBorders = 15;
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateCommon.Border.Width = 0;
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StateCommon.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StateCommon.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StateCommon.Border.DrawBorders = 15;
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StateCommon.Border.Width = 0;
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Border.DrawBorders = 15;
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Border.Rounding = 12;
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.ButtonEdgeInset = 12;
			this.kryptonButton2.Location = new Point(225, 355);
			this.kryptonButton2.Name = "kryptonButton2";
			this.kryptonButton2.OverrideDefault.Back.Color1 = Color.FromArgb(250, 252, 250);
			this.kryptonButton2.OverrideDefault.Back.Color2 = Color.FromArgb(250, 252, 250);
			this.kryptonButton2.OverrideDefault.Back.ColorAngle = 45f;
			this.kryptonButton2.OverrideDefault.Border.Color1 = Color.FromArgb(8, 142, 254);
			this.kryptonButton2.OverrideDefault.Border.Color2 = Color.FromArgb(8, 142, 254);
			this.kryptonButton2.OverrideDefault.Border.ColorAngle = 45f;
			this.kryptonButton2.OverrideDefault.Border.DrawBorders = 15;
			this.kryptonButton2.OverrideDefault.Border.GraphicsHint = 1;
			this.kryptonButton2.OverrideDefault.Border.Rounding = 20;
			this.kryptonButton2.OverrideDefault.Border.Width = 1;
			this.kryptonButton2.PaletteMode = 1;
			this.kryptonButton2.Size = new Size(124, 43);
			this.kryptonButton2.StateCommon.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonButton2.StateCommon.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonButton2.StateCommon.Back.ColorAngle = 45f;
			this.kryptonButton2.StateCommon.Border.Color1 = Color.FromArgb(6, 174, 244);
			this.kryptonButton2.StateCommon.Border.Color2 = Color.FromArgb(8, 142, 254);
			this.kryptonButton2.StateCommon.Border.ColorAngle = 45f;
			this.kryptonButton2.StateCommon.Border.DrawBorders = 15;
			this.kryptonButton2.StateCommon.Border.Rounding = 20;
			this.kryptonButton2.StateCommon.Border.Width = 1;
			this.kryptonButton2.StateCommon.Content.ShortText.Color1 = Color.FromArgb(8, 142, 254);
			this.kryptonButton2.StateCommon.Content.ShortText.Font = new Font("Yu Gothic", 12f, FontStyle.Bold);
			this.kryptonButton2.StatePressed.Back.Color1 = Color.FromArgb(20, 145, 198);
			this.kryptonButton2.StatePressed.Back.Color2 = Color.FromArgb(22, 121, 206);
			this.kryptonButton2.StatePressed.Back.ColorAngle = 135f;
			this.kryptonButton2.StatePressed.Border.Color1 = Color.FromArgb(20, 145, 198);
			this.kryptonButton2.StatePressed.Border.Color2 = Color.FromArgb(22, 121, 206);
			this.kryptonButton2.StatePressed.Border.DrawBorders = 15;
			this.kryptonButton2.StatePressed.Border.Rounding = 20;
			this.kryptonButton2.StatePressed.Border.Width = 1;
			this.kryptonButton2.StatePressed.Content.ShortText.Color1 = Color.White;
			this.kryptonButton2.StateTracking.Back.Color1 = Color.FromArgb(8, 142, 254);
			this.kryptonButton2.StateTracking.Back.Color2 = Color.FromArgb(6, 174, 244);
			this.kryptonButton2.StateTracking.Back.ColorAngle = 45f;
			this.kryptonButton2.StateTracking.Border.Color1 = Color.FromArgb(6, 174, 244);
			this.kryptonButton2.StateTracking.Border.Color2 = Color.FromArgb(8, 142, 254);
			this.kryptonButton2.StateTracking.Border.ColorAngle = 45f;
			this.kryptonButton2.StateTracking.Border.DrawBorders = 15;
			this.kryptonButton2.StateTracking.Border.GraphicsHint = 1;
			this.kryptonButton2.StateTracking.Border.Rounding = 20;
			this.kryptonButton2.StateTracking.Border.Width = 1;
			this.kryptonButton2.StateTracking.Content.ShortText.Color1 = Color.White;
			this.kryptonButton2.TabIndex = 43;
			this.kryptonButton2.Values.Text = "Login";
			this.kryptonButton2.Click += this.kryptonButton2_Click;
			this.kryptonButton1.Location = new Point(45, 355);
			this.kryptonButton1.Name = "kryptonButton1";
			this.kryptonButton1.OverrideDefault.Back.Color1 = Color.FromArgb(6, 174, 244);
			this.kryptonButton1.OverrideDefault.Back.Color2 = Color.FromArgb(8, 142, 254);
			this.kryptonButton1.OverrideDefault.Back.ColorAngle = 45f;
			this.kryptonButton1.OverrideDefault.Border.Color1 = Color.FromArgb(6, 174, 244);
			this.kryptonButton1.OverrideDefault.Border.Color2 = Color.FromArgb(8, 142, 254);
			this.kryptonButton1.OverrideDefault.Border.ColorAngle = 45f;
			this.kryptonButton1.OverrideDefault.Border.DrawBorders = 15;
			this.kryptonButton1.OverrideDefault.Border.GraphicsHint = 1;
			this.kryptonButton1.OverrideDefault.Border.Rounding = 20;
			this.kryptonButton1.OverrideDefault.Border.Width = 1;
			this.kryptonButton1.PaletteMode = 1;
			this.kryptonButton1.Size = new Size(155, 43);
			this.kryptonButton1.StateCommon.Back.Color1 = Color.FromArgb(6, 174, 244);
			this.kryptonButton1.StateCommon.Back.Color2 = Color.FromArgb(8, 148, 252);
			this.kryptonButton1.StateCommon.Back.ColorAngle = 45f;
			this.kryptonButton1.StateCommon.Border.Color1 = Color.FromArgb(6, 174, 244);
			this.kryptonButton1.StateCommon.Border.Color2 = Color.FromArgb(8, 142, 254);
			this.kryptonButton1.StateCommon.Border.ColorAngle = 45f;
			this.kryptonButton1.StateCommon.Border.DrawBorders = 15;
			this.kryptonButton1.StateCommon.Border.Rounding = 20;
			this.kryptonButton1.StateCommon.Border.Width = 1;
			this.kryptonButton1.StateCommon.Content.ShortText.Color1 = Color.White;
			this.kryptonButton1.StateCommon.Content.ShortText.Color2 = Color.White;
			this.kryptonButton1.StateCommon.Content.ShortText.Font = new Font("Yu Gothic", 12f, FontStyle.Bold);
			this.kryptonButton1.StatePressed.Back.Color1 = Color.FromArgb(20, 145, 198);
			this.kryptonButton1.StatePressed.Back.Color2 = Color.FromArgb(22, 121, 206);
			this.kryptonButton1.StatePressed.Back.ColorAngle = 135f;
			this.kryptonButton1.StatePressed.Border.Color1 = Color.FromArgb(20, 145, 198);
			this.kryptonButton1.StatePressed.Border.Color2 = Color.FromArgb(22, 121, 206);
			this.kryptonButton1.StatePressed.Border.DrawBorders = 15;
			this.kryptonButton1.StatePressed.Border.Rounding = 20;
			this.kryptonButton1.StatePressed.Border.Width = 1;
			this.kryptonButton1.StateTracking.Back.Color1 = Color.FromArgb(8, 142, 254);
			this.kryptonButton1.StateTracking.Back.Color2 = Color.FromArgb(6, 174, 244);
			this.kryptonButton1.StateTracking.Back.ColorAngle = 45f;
			this.kryptonButton1.StateTracking.Border.Color1 = Color.FromArgb(6, 174, 244);
			this.kryptonButton1.StateTracking.Border.Color2 = Color.FromArgb(8, 142, 254);
			this.kryptonButton1.StateTracking.Border.ColorAngle = 45f;
			this.kryptonButton1.StateTracking.Border.DrawBorders = 15;
			this.kryptonButton1.StateTracking.Border.GraphicsHint = 1;
			this.kryptonButton1.StateTracking.Border.Rounding = 20;
			this.kryptonButton1.StateTracking.Border.Width = 1;
			this.kryptonButton1.TabIndex = 42;
			this.kryptonButton1.Values.Text = "Sign Up";
			this.kryptonButton1.Click += this.kryptonButton1_Click;
			this.pictureBox1.Image = Resources.access_control_system_abstract_concept_335657_3180;
			this.pictureBox1.Location = new Point(377, 12);
			this.pictureBox1.Name = "pictureBox1";
			this.pictureBox1.Size = new Size(334, 465);
			this.pictureBox1.SizeMode = PictureBoxSizeMode.Zoom;
			this.pictureBox1.TabIndex = 47;
			this.pictureBox1.TabStop = false;
			this.password.Animated = true;
			this.password.AutoRoundedCorners = true;
			this.password.BorderRadius = 18;
			this.password.Cursor = Cursors.IBeam;
			this.password.DefaultText = "";
			this.password.DisabledState.BorderColor = Color.FromArgb(208, 208, 208);
			this.password.DisabledState.FillColor = Color.FromArgb(226, 226, 226);
			this.password.DisabledState.ForeColor = Color.FromArgb(138, 138, 138);
			this.password.DisabledState.Parent = this.password;
			this.password.DisabledState.PlaceholderForeColor = Color.FromArgb(138, 138, 138);
			this.password.FocusedState.BorderColor = Color.FromArgb(94, 148, 255);
			this.password.FocusedState.Parent = this.password;
			this.password.Font = new Font("Yu Gothic", 11.25f);
			this.password.ForeColor = Color.Gray;
			this.password.HoverState.BorderColor = Color.FromArgb(94, 148, 255);
			this.password.HoverState.Parent = this.password;
			this.password.Location = new Point(44, 184);
			this.password.Margin = new Padding(4, 4, 4, 4);
			this.password.Name = "password";
			this.password.PasswordChar = '*';
			this.password.PlaceholderText = "Password";
			this.password.SelectedText = "";
			this.password.ShadowDecoration.BorderRadius = 9;
			this.password.ShadowDecoration.Parent = this.password;
			this.password.Size = new Size(295, 39);
			this.password.TabIndex = 49;
			this.password.TextOffset = new Point(19, 0);
			this.username.Animated = true;
			this.username.AutoRoundedCorners = true;
			this.username.BorderRadius = 18;
			this.username.Cursor = Cursors.IBeam;
			this.username.DefaultText = "";
			this.username.DisabledState.BorderColor = Color.FromArgb(208, 208, 208);
			this.username.DisabledState.FillColor = Color.FromArgb(226, 226, 226);
			this.username.DisabledState.ForeColor = Color.FromArgb(138, 138, 138);
			this.username.DisabledState.Parent = this.username;
			this.username.DisabledState.PlaceholderForeColor = Color.FromArgb(138, 138, 138);
			this.username.FocusedState.BorderColor = Color.FromArgb(94, 148, 255);
			this.username.FocusedState.Parent = this.username;
			this.username.Font = new Font("Yu Gothic", 11.25f);
			this.username.ForeColor = Color.Gray;
			this.username.HoverState.BorderColor = Color.FromArgb(94, 148, 255);
			this.username.HoverState.Parent = this.username;
			this.username.Location = new Point(44, 127);
			this.username.Margin = new Padding(4, 4, 4, 4);
			this.username.Name = "username";
			this.username.PasswordChar = '\0';
			this.username.PlaceholderText = "Username";
			this.username.SelectedText = "";
			this.username.ShadowDecoration.BorderRadius = 9;
			this.username.ShadowDecoration.Parent = this.username;
			this.username.Size = new Size(295, 39);
			this.username.TabIndex = 48;
			this.username.TextOffset = new Point(19, 0);
			this.license.Animated = true;
			this.license.AutoRoundedCorners = true;
			this.license.BorderRadius = 18;
			this.license.Cursor = Cursors.IBeam;
			this.license.DefaultText = "";
			this.license.DisabledState.BorderColor = Color.FromArgb(208, 208, 208);
			this.license.DisabledState.FillColor = Color.FromArgb(226, 226, 226);
			this.license.DisabledState.ForeColor = Color.FromArgb(138, 138, 138);
			this.license.DisabledState.Parent = this.license;
			this.license.DisabledState.PlaceholderForeColor = Color.FromArgb(138, 138, 138);
			this.license.FocusedState.BorderColor = Color.FromArgb(94, 148, 255);
			this.license.FocusedState.Parent = this.license;
			this.license.Font = new Font("Yu Gothic", 11.25f);
			this.license.ForeColor = Color.Gray;
			this.license.HoverState.BorderColor = Color.FromArgb(94, 148, 255);
			this.license.HoverState.Parent = this.license;
			this.license.Location = new Point(44, 298);
			this.license.Margin = new Padding(4, 4, 4, 4);
			this.license.Name = "license";
			this.license.PasswordChar = '\0';
			this.license.PlaceholderText = "License";
			this.license.SelectedText = "";
			this.license.ShadowDecoration.BorderRadius = 9;
			this.license.ShadowDecoration.Parent = this.license;
			this.license.Size = new Size(295, 39);
			this.license.TabIndex = 50;
			this.license.TextOffset = new Point(19, 0);
			this.email.Animated = true;
			this.email.AutoRoundedCorners = true;
			this.email.BorderRadius = 18;
			this.email.Cursor = Cursors.IBeam;
			this.email.DefaultText = "";
			this.email.DisabledState.BorderColor = Color.FromArgb(208, 208, 208);
			this.email.DisabledState.FillColor = Color.FromArgb(226, 226, 226);
			this.email.DisabledState.ForeColor = Color.FromArgb(138, 138, 138);
			this.email.DisabledState.Parent = this.email;
			this.email.DisabledState.PlaceholderForeColor = Color.FromArgb(138, 138, 138);
			this.email.FocusedState.BorderColor = Color.FromArgb(94, 148, 255);
			this.email.FocusedState.Parent = this.email;
			this.email.Font = new Font("Yu Gothic", 11.25f);
			this.email.ForeColor = Color.Gray;
			this.email.HoverState.BorderColor = Color.FromArgb(94, 148, 255);
			this.email.HoverState.Parent = this.email;
			this.email.Location = new Point(44, 241);
			this.email.Margin = new Padding(4, 4, 4, 4);
			this.email.Name = "email";
			this.email.PasswordChar = '\0';
			this.email.PlaceholderText = "Email";
			this.email.SelectedText = "";
			this.email.ShadowDecoration.BorderRadius = 9;
			this.email.ShadowDecoration.Parent = this.email;
			this.email.Size = new Size(295, 39);
			this.email.TabIndex = 51;
			this.email.TextOffset = new Point(19, 0);
			base.AutoScaleDimensions = new SizeF(6f, 13f);
			base.AutoScaleMode = AutoScaleMode.Font;
			this.BackColor = Color.FromArgb(250, 252, 252);
			base.ClientSize = new Size(723, 489);
			base.Controls.Add(this.email);
			base.Controls.Add(this.license);
			base.Controls.Add(this.password);
			base.Controls.Add(this.username);
			base.Controls.Add(this.pictureBox1);
			base.Controls.Add(this.kryptonButton2);
			base.Controls.Add(this.kryptonButton1);
			base.Controls.Add(this.StripLBL);
			this.MaximumSize = new Size(1280, 984);
			base.Name = "Register";
			base.Opacity = 0.95;
			base.Palette = this.kryptonPalette1;
			base.PaletteMode = 12;
			base.ShowIcon = false;
			base.StartPosition = FormStartPosition.CenterScreen;
			base.FormClosed += this.Register_FormClosed;
			base.Load += this.Register_Load;
			((ISupportInitialize)this.pictureBox1).EndInit();
			base.ResumeLayout(false);
			base.PerformLayout();
		}

		// Token: 0x04000388 RID: 904
		private string releaseId = "";

		// Token: 0x04000389 RID: 905
		private bool alreadyAlerted;

		// Token: 0x0400038A RID: 906
		private string debuggerRunning = "None";

		// Token: 0x0400038B RID: 907
		private IContainer components = null;

		// Token: 0x0400038C RID: 908
		private Label StripLBL;

		// Token: 0x0400038D RID: 909
		private Guna2DragControl guna2DragControl1;

		// Token: 0x0400038E RID: 910
		private System.Windows.Forms.Timer timer1;

		// Token: 0x0400038F RID: 911
		private BackgroundWorker backgroundWorker1;

		// Token: 0x04000390 RID: 912
		private System.Windows.Forms.Timer timer2;

		// Token: 0x04000391 RID: 913
		private System.Windows.Forms.Timer timer3;

		// Token: 0x04000392 RID: 914
		private KryptonPalette kryptonPalette1;

		// Token: 0x04000393 RID: 915
		private KryptonButton kryptonButton2;

		// Token: 0x04000394 RID: 916
		private KryptonButton kryptonButton1;

		// Token: 0x04000395 RID: 917
		private PictureBox pictureBox1;

		// Token: 0x04000396 RID: 918
		private Guna2TextBox email;

		// Token: 0x04000397 RID: 919
		private Guna2TextBox license;

		// Token: 0x04000398 RID: 920
		private Guna2TextBox password;

		// Token: 0x04000399 RID: 921
		private Guna2TextBox username;

		// Token: 0x0200002E RID: 46
		public class AutoClosingMessageBox
		{
			// Token: 0x060001C1 RID: 449 RVA: 0x00002BEE File Offset: 0x00000DEE
			private AutoClosingMessageBox(string string_0, string string_1, int int_0)
			{
				this._caption = string_1;
				this._timeoutTimer = new System.Threading.Timer(new TimerCallback(this.OnTimerElapsed), null, int_0, -1);
				MessageBox.Show(string_0, string_1);
			}

			// Token: 0x060001C2 RID: 450 RVA: 0x00002C1F File Offset: 0x00000E1F
			public static void Show(string string_0, string string_1, int int_0)
			{
				new Register.AutoClosingMessageBox(string_0, string_1, int_0);
			}

			// Token: 0x060001C3 RID: 451 RVA: 0x00019894 File Offset: 0x00017A94
			private void OnTimerElapsed(object object_0)
			{
				IntPtr intPtr = Register.AutoClosingMessageBox.FindWindow(null, this._caption);
				if (intPtr != IntPtr.Zero)
				{
					Register.AutoClosingMessageBox.SendMessage(intPtr, 16U, IntPtr.Zero, IntPtr.Zero);
				}
				this._timeoutTimer.Dispose();
			}

			// Token: 0x060001C4 RID: 452
			[DllImport("user32.dll", SetLastError = true)]
			private static extern IntPtr FindWindow(string string_0, string string_1);

			// Token: 0x060001C5 RID: 453
			[DllImport("user32.dll", CharSet = CharSet.Auto)]
			private static extern IntPtr SendMessage(IntPtr intptr_0, uint uint_0, IntPtr intptr_1, IntPtr intptr_2);

			// Token: 0x0400039A RID: 922
			private System.Threading.Timer _timeoutTimer;

			// Token: 0x0400039B RID: 923
			private string _caption;

			// Token: 0x0400039C RID: 924
			private const int WM_CLOSE = 16;
		}
	}
}
