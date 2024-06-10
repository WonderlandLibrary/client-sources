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
	// Token: 0x02000023 RID: 35
	public class Login : KryptonForm
	{
		// Token: 0x060000B2 RID: 178
		[DllImport("Gdi32.dll")]
		private static extern IntPtr CreateRoundRectRgn(int int_0, int int_1, int int_2, int int_3, int int_4, int int_5);

		// Token: 0x060000B3 RID: 179 RVA: 0x00006068 File Offset: 0x00004268
		public Login()
		{
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
			this.InitializeComponent();
		}

		// Token: 0x060000B4 RID: 180 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000B5 RID: 181 RVA: 0x000025E7 File Offset: 0x000007E7
		private void ExitCTRBOX_Click(object sender, EventArgs e)
		{
			Environment.Exit(0);
		}

		// Token: 0x060000B6 RID: 182 RVA: 0x000025EF File Offset: 0x000007EF
		private void MiniCTRBOX_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x060000B7 RID: 183 RVA: 0x0000612C File Offset: 0x0000432C
		private void Login_Load(object sender, EventArgs e)
		{
			Program.NewLaunch();
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
				Login.AutoClosingMessageBox.Show("Process is being emulated.", "Error", 4000);
				Program.Hacking();
				Main.selfDelete();
				Environment.Exit(0);
			}
			else if (MainModule.IsVM())
			{
				Login.AutoClosingMessageBox.Show("Aura can't run in VMs. \nPlease use a real machine.", "Error", 4000);
				Program.Hacking();
				Main.selfDelete();
				Environment.Exit(0);
			}
			else if (MainModule.IsSandboxie())
			{
				Login.AutoClosingMessageBox.Show("Aura can't run in Sandboxes. \nPlease use a real machine.", "Error", 4000);
				Program.Hacking();
				Main.selfDelete();
				Environment.Exit(0);
			}
		}

		// Token: 0x060000B8 RID: 184 RVA: 0x000025E5 File Offset: 0x000007E5
		private void username_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060000B9 RID: 185 RVA: 0x000025E5 File Offset: 0x000007E5
		private void LoginBTN_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000BA RID: 186 RVA: 0x00006200 File Offset: 0x00004400
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

		// Token: 0x060000BB RID: 187 RVA: 0x000062FC File Offset: 0x000044FC
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

		// Token: 0x060000BC RID: 188
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x060000BD RID: 189
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr intptr_0);

		// Token: 0x060000BE RID: 190
		[DllImport("user32", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int GetWindowText(IntPtr intptr_0, StringBuilder stringBuilder_0, int int_0);

		// Token: 0x060000BF RID: 191
		[DllImport("user32.dll")]
		public static extern IntPtr FindWindow(string string_0, string string_1);

		// Token: 0x060000C0 RID: 192 RVA: 0x000063F8 File Offset: 0x000045F8
		private static string title()
		{
			string result = string.Empty;
			IntPtr foregroundWindow = Login.GetForegroundWindow();
			int num = Login.GetWindowTextLength(foregroundWindow) + 1;
			StringBuilder stringBuilder = new StringBuilder(num);
			if (Login.GetWindowText(foregroundWindow, stringBuilder, num) > 0)
			{
				result = stringBuilder.ToString();
			}
			return result;
		}

		// Token: 0x060000C1 RID: 193 RVA: 0x0000643C File Offset: 0x0000463C
		private void timer2_Tick(object sender, EventArgs e)
		{
			if (!(Login.FindWindow("LWJGL", Login.title().ToString()).ToString() == Login.GetForegroundWindow().ToString()))
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

		// Token: 0x060000C2 RID: 194 RVA: 0x0000668C File Offset: 0x0000488C
		private void timer3_Tick(object sender, EventArgs e)
		{
			Process[] processesByName = Process.GetProcessesByName("javaw");
			if (processesByName.Length == 0)
			{
				Environment.Exit(0);
			}
		}

		// Token: 0x060000C3 RID: 195 RVA: 0x000025F8 File Offset: 0x000007F8
		private void Login_FormClosed(object sender, FormClosedEventArgs e)
		{
			Program.selfDelete();
		}

		// Token: 0x060000C4 RID: 196 RVA: 0x000066B4 File Offset: 0x000048B4
		private void kryptonButton1_Click(object sender, EventArgs e)
		{
			if (API.Login(this.username.Text, this.password.Text))
			{
				MessageBox.Show("Login successful!", "Success", MessageBoxButtons.OK, MessageBoxIcon.Asterisk);
				Main main = new Main();
				main.Show();
				base.Hide();
			}
		}

		// Token: 0x060000C5 RID: 197 RVA: 0x00006704 File Offset: 0x00004904
		private void kryptonButton2_Click(object sender, EventArgs e)
		{
			base.Hide();
			Register register = new Register();
			register.Show();
		}

		// Token: 0x060000C6 RID: 198 RVA: 0x00006724 File Offset: 0x00004924
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
		}

		// Token: 0x060000C7 RID: 199 RVA: 0x000025FF File Offset: 0x000007FF
		protected override void Dispose(bool bool_0)
		{
			if (bool_0 && this.components != null)
			{
				this.components.Dispose();
			}
			base.Dispose(bool_0);
		}

		// Token: 0x060000C8 RID: 200 RVA: 0x00006790 File Offset: 0x00004990
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
			this.kryptonButton1 = new KryptonButton();
			this.kryptonButton2 = new KryptonButton();
			this.pictureBox1 = new PictureBox();
			this.username = new Guna2TextBox();
			this.password = new Guna2TextBox();
			((ISupportInitialize)this.pictureBox1).BeginInit();
			base.SuspendLayout();
			this.StripLBL.AutoSize = true;
			this.StripLBL.Font = new Font("Yu Gothic", 15f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.StripLBL.ForeColor = Color.FromArgb(64, 64, 64);
			this.StripLBL.Location = new Point(32, 91);
			this.StripLBL.Margin = new Padding(2, 0, 2, 0);
			this.StripLBL.Name = "StripLBL";
			this.StripLBL.Size = new Size(132, 26);
			this.StripLBL.TabIndex = 27;
			this.StripLBL.Text = "Aura | Login";
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
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateNormal.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateNormal.Border.DrawBorders = 15;
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateNormal.Border.Width = 0;
			this.kryptonPalette1.ButtonStyles.ButtonForm.StatePressed.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonForm.StatePressed.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonForm.StatePressed.Border.DrawBorders = 15;
			this.kryptonPalette1.ButtonStyles.ButtonForm.StatePressed.Border.Width = 0;
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateTracking.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateTracking.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateTracking.Border.DrawBorders = 15;
			this.kryptonPalette1.ButtonStyles.ButtonForm.StateTracking.Border.Width = 0;
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StatePressed.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StatePressed.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StatePressed.Border.DrawBorders = 15;
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StatePressed.Border.Width = 0;
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StateTracking.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StateTracking.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StateTracking.Border.DrawBorders = 15;
			this.kryptonPalette1.ButtonStyles.ButtonFormClose.StateTracking.Border.Width = 0;
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Border.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Border.DrawBorders = 15;
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Border.GraphicsHint = 0;
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Border.Rounding = 12;
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.ButtonEdgeInset = 12;
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.Content.Padding = new Padding(10, -1, -1, -1);
			this.kryptonButton1.Location = new Point(32, 300);
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
			this.kryptonButton1.TabIndex = 37;
			this.kryptonButton1.Values.Text = "Get Started";
			this.kryptonButton1.Click += this.kryptonButton1_Click;
			this.kryptonButton2.Location = new Point(200, 300);
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
			this.kryptonButton2.TabIndex = 38;
			this.kryptonButton2.Values.Text = "Sign Up";
			this.kryptonButton2.Click += this.kryptonButton2_Click;
			this.pictureBox1.Image = Resources.Usability_testing_amico;
			this.pictureBox1.Location = new Point(449, 14);
			this.pictureBox1.Name = "pictureBox1";
			this.pictureBox1.Size = new Size(358, 401);
			this.pictureBox1.SizeMode = PictureBoxSizeMode.Zoom;
			this.pictureBox1.TabIndex = 39;
			this.pictureBox1.TabStop = false;
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
			this.username.Location = new Point(32, 163);
			this.username.Margin = new Padding(4);
			this.username.Name = "username";
			this.username.PasswordChar = '\0';
			this.username.PlaceholderText = "Username";
			this.username.SelectedText = "";
			this.username.ShadowDecoration.BorderRadius = 9;
			this.username.ShadowDecoration.Parent = this.username;
			this.username.Size = new Size(379, 39);
			this.username.TabIndex = 40;
			this.username.TextOffset = new Point(19, 0);
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
			this.password.Location = new Point(32, 227);
			this.password.Margin = new Padding(4, 4, 4, 4);
			this.password.Name = "password";
			this.password.PasswordChar = '*';
			this.password.PlaceholderText = "Password";
			this.password.SelectedText = "";
			this.password.ShadowDecoration.BorderRadius = 9;
			this.password.ShadowDecoration.Parent = this.password;
			this.password.Size = new Size(379, 39);
			this.password.TabIndex = 41;
			this.password.TextOffset = new Point(19, 0);
			base.AutoScaleDimensions = new SizeF(6f, 13f);
			base.AutoScaleMode = AutoScaleMode.Font;
			this.BackColor = Color.FromArgb(250, 252, 252);
			base.ClientSize = new Size(850, 500);
			base.Controls.Add(this.password);
			base.Controls.Add(this.username);
			base.Controls.Add(this.pictureBox1);
			base.Controls.Add(this.kryptonButton2);
			base.Controls.Add(this.kryptonButton1);
			base.Controls.Add(this.StripLBL);
			base.Name = "Login";
			base.Opacity = 0.95;
			base.Palette = this.kryptonPalette1;
			base.PaletteMode = 12;
			base.ShowIcon = false;
			base.StartPosition = FormStartPosition.CenterScreen;
			base.TransparencyKey = Color.Maroon;
			base.FormClosed += this.Login_FormClosed;
			base.Load += this.Login_Load;
			((ISupportInitialize)this.pictureBox1).EndInit();
			base.ResumeLayout(false);
			base.PerformLayout();
		}

		// Token: 0x0400029F RID: 671
		private string releaseId = "";

		// Token: 0x040002A0 RID: 672
		private bool alreadyAlerted;

		// Token: 0x040002A1 RID: 673
		private string debuggerRunning = "None";

		// Token: 0x040002A2 RID: 674
		private IContainer components = null;

		// Token: 0x040002A3 RID: 675
		private Label StripLBL;

		// Token: 0x040002A4 RID: 676
		private Guna2DragControl guna2DragControl1;

		// Token: 0x040002A5 RID: 677
		private System.Windows.Forms.Timer timer1;

		// Token: 0x040002A6 RID: 678
		private BackgroundWorker backgroundWorker1;

		// Token: 0x040002A7 RID: 679
		private System.Windows.Forms.Timer timer2;

		// Token: 0x040002A8 RID: 680
		private System.Windows.Forms.Timer timer3;

		// Token: 0x040002A9 RID: 681
		private KryptonPalette kryptonPalette1;

		// Token: 0x040002AA RID: 682
		private KryptonButton kryptonButton1;

		// Token: 0x040002AB RID: 683
		private KryptonButton kryptonButton2;

		// Token: 0x040002AC RID: 684
		private PictureBox pictureBox1;

		// Token: 0x040002AD RID: 685
		private Guna2TextBox username;

		// Token: 0x040002AE RID: 686
		private Guna2TextBox password;

		// Token: 0x02000024 RID: 36
		public class AutoClosingMessageBox
		{
			// Token: 0x060000C9 RID: 201 RVA: 0x00002624 File Offset: 0x00000824
			private AutoClosingMessageBox(string string_0, string string_1, int int_0)
			{
				this._caption = string_1;
				this._timeoutTimer = new System.Threading.Timer(new TimerCallback(this.OnTimerElapsed), null, int_0, -1);
				MessageBox.Show(string_0, string_1);
			}

			// Token: 0x060000CA RID: 202 RVA: 0x00002655 File Offset: 0x00000855
			public static void Show(string string_0, string string_1, int int_0)
			{
				new Login.AutoClosingMessageBox(string_0, string_1, int_0);
			}

			// Token: 0x060000CB RID: 203 RVA: 0x00007F6C File Offset: 0x0000616C
			private void OnTimerElapsed(object object_0)
			{
				IntPtr intPtr = Login.AutoClosingMessageBox.FindWindow(null, this._caption);
				if (intPtr != IntPtr.Zero)
				{
					Login.AutoClosingMessageBox.SendMessage(intPtr, 16U, IntPtr.Zero, IntPtr.Zero);
				}
				this._timeoutTimer.Dispose();
			}

			// Token: 0x060000CC RID: 204
			[DllImport("user32.dll", SetLastError = true)]
			private static extern IntPtr FindWindow(string string_0, string string_1);

			// Token: 0x060000CD RID: 205
			[DllImport("user32.dll", CharSet = CharSet.Auto)]
			private static extern IntPtr SendMessage(IntPtr intptr_0, uint uint_0, IntPtr intptr_1, IntPtr intptr_2);

			// Token: 0x040002AF RID: 687
			private System.Threading.Timer _timeoutTimer;

			// Token: 0x040002B0 RID: 688
			private string _caption;

			// Token: 0x040002B1 RID: 689
			private const int WM_CLOSE = 16;
		}
	}
}
