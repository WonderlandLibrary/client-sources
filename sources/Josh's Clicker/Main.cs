using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Net;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;
using dotClick.Properties;
using MetroFramework;
using MetroFramework.Components;
using MetroFramework.Controls;
using MetroFramework.Forms;

namespace dotClick
{
	// Token: 0x02000008 RID: 8
	public partial class Main : MetroForm
	{
		// Token: 0x06000019 RID: 25 RVA: 0x000026D0 File Offset: 0x000008D0
		public Main()
		{
			this.InitializeComponent();
			this.SetupKeyboardHooks();
			base.Icon = Resources.off;
		}

		// Token: 0x0600001A RID: 26 RVA: 0x000020A8 File Offset: 0x000002A8
		public void Global_LeftUp()
		{
			this.generalTimer.Stop();
		}

		// Token: 0x0600001B RID: 27 RVA: 0x000020B5 File Offset: 0x000002B5
		public void Global_LeftDown()
		{
			this.generalTimer.Start();
		}

		// Token: 0x0600001C RID: 28 RVA: 0x000020C2 File Offset: 0x000002C2
		public void Global_RightUp()
		{
			this.holdingRight = false;
		}

		// Token: 0x0600001D RID: 29 RVA: 0x000020CB File Offset: 0x000002CB
		public void Global_RightDown()
		{
			this.holdingRight = true;
		}

		// Token: 0x0600001E RID: 30 RVA: 0x00002758 File Offset: 0x00000958
		private void staticRefresher_Tick(object sender, EventArgs e)
		{
			if (this.staticRefresherInstance.refreshStaticLeftDown())
			{
				this.Global_LeftDown();
			}
			if (this.staticRefresherInstance.refreshStaticLeftUp())
			{
				this.Global_LeftUp();
			}
			if (this.staticRefresherInstance.refreshStaticRightDown())
			{
				this.Global_RightDown();
			}
			if (this.staticRefresherInstance.refreshStaticRightUp())
			{
				this.Global_RightUp();
			}
		}

		// Token: 0x0600001F RID: 31 RVA: 0x000027B4 File Offset: 0x000009B4
		private void generalTimer_Tick(object sender, EventArgs e)
		{
			if (this.onOffToggle)
			{
				if (this.GetActiveWindowTitle() == null || this.GetActiveWindowTitle().Contains("Minecraft") || this.GetActiveWindowTitle().Contains("CosmicClient") || this.GetActiveWindowTitle().Contains("ZuesClient") || !this.GetActiveWindowTitle().Contains("ApolloClient") || !this.GetActiveWindowTitle().Contains("Launcher"))
				{
				}
				this.leftUpTimer.Interval = this.generalTimer.Interval / 3;
				this.rightDownTimer.Interval = this.generalTimer.Interval / 4;
				this.rightUpTimer.Interval = this.generalTimer.Interval / 2;
				this.generalTimer.Interval = this.modifyInterval(new Random().Next(1000 / this.min * 10, 1000 / this.max * 10));
				if (this.holdingRight)
				{
					this.generalTimer.Interval = this.generalTimer.Interval * 2 - this.generalTimer.Interval / 2;
				}
				this.mouseEventInstance.runLeftDown();
				this.leftUpTimer.Start();
				if (this.holdingRight)
				{
					this.rightDownTimer.Start();
				}
			}
		}

		// Token: 0x06000020 RID: 32 RVA: 0x000020D4 File Offset: 0x000002D4
		private void leftUpTimer_Tick(object sender, EventArgs e)
		{
			this.leftUpTimer.Stop();
			this.mouseEventInstance.runLeftUp();
		}

		// Token: 0x06000021 RID: 33 RVA: 0x000020EC File Offset: 0x000002EC
		private void rightDownTimer_Tick(object sender, EventArgs e)
		{
			this.rightDownTimer.Stop();
			this.mouseEventInstance.runRightDown();
			this.rightUpTimer.Start();
		}

		// Token: 0x06000022 RID: 34 RVA: 0x0000210F File Offset: 0x0000030F
		private void rightUpTimer_Tick(object sender, EventArgs e)
		{
			this.rightUpTimer.Stop();
			this.mouseEventInstance.runRightUp();
		}

		// Token: 0x06000023 RID: 35 RVA: 0x00002127 File Offset: 0x00000327
		public void SetupKeyboardHooks()
		{
			this._globalKeyboardHook = new GlobalKeyboardHook();
			this._globalKeyboardHook.KeyboardPressed += this.OnKeyPressed;
		}

		// Token: 0x06000024 RID: 36 RVA: 0x0000290C File Offset: 0x00000B0C
		private void OnKeyPressed(object sender, GlobalKeyboardHookEventArgs e)
		{
			if (e.KeyboardData.VirtualCode == this.keyInt && e.KeyboardState == GlobalKeyboardHook.KeyboardState.KeyDown)
			{
				this.onOffToggle = !this.onOffToggle;
				if (this.onOffToggle)
				{
					base.Style = MetroColorStyle.Blue;
					this.metroStyle.Style = MetroColorStyle.Blue;
					this.metroTile1.Style = MetroColorStyle.Blue;
					this.hideIcon.Icon = Resources.on;
					base.Icon = Resources.on;
					this.metroTile1.Text = "on";
				}
				else
				{
					base.Style = MetroColorStyle.Purple;
					this.metroStyle.Style = MetroColorStyle.Purple;
					this.metroTile1.Style = MetroColorStyle.Purple;
					this.hideIcon.Icon = Resources.off;
					base.Icon = Resources.off;
					this.metroTile1.Text = "off";
				}
			}
		}

		// Token: 0x06000025 RID: 37 RVA: 0x0000214B File Offset: 0x0000034B
		public new void Dispose()
		{
			GlobalKeyboardHook globalKeyboardHook = this._globalKeyboardHook;
			if (globalKeyboardHook != null)
			{
				globalKeyboardHook.Dispose();
			}
		}

		// Token: 0x06000026 RID: 38 RVA: 0x0000215E File Offset: 0x0000035E
		private void onClosure(object sender, EventArgs e)
		{
			base.Close();
		}

		// Token: 0x06000027 RID: 39 RVA: 0x000029F4 File Offset: 0x00000BF4
		private void Form1_Load(object sender, EventArgs e)
		{
			MetroForm metroForm = new MetroForm();
			MetroButton metroButton = new MetroButton();
			MetroStyleManager metroStyleManager = new MetroStyleManager();
			MetroTextBox value = new MetroTextBox();
			metroStyleManager.Theme = MetroThemeStyle.Dark;
			metroStyleManager.Style = MetroColorStyle.Purple;
			metroForm.Size = new Size(240, 100);
			metroForm.Theme = MetroThemeStyle.Dark;
			metroForm.Style = MetroColorStyle.Purple;
			metroForm.StyleManager = metroStyleManager;
			metroStyleManager.Owner = metroForm;
			metroForm.Controls.Add(value);
			metroForm.Controls.Add(metroButton);
			metroForm.Resizable = false;
			metroForm.MaximizeBox = false;
			metroForm.MinimizeBox = false;
			metroForm.FormClosed += new FormClosedEventHandler(this.onClosure);
			metroButton.Text = "OK";
			metroButton.Location = new Point(150, 62);
			this.ver.BackColor = Color.Transparent;
			this.staticRefresher.Start();
			this.hideIcon.Icon = Resources.off;
			this.hideIcon.Visible = false;
			MessageBox.Show("Source code released by MrCreeper2010, good luck <3", "", MessageBoxButtons.OK, MessageBoxIcon.None);

		}


		// Token: 0x06000029 RID: 41 RVA: 0x00002B24 File Offset: 0x00000D24
		private void pinButton_Click(object sender, EventArgs e)
		{
			this.pinned = !this.pinned;
			if (this.pinned)
			{
				base.TopMost = true;
			}
			else
			{
				base.TopMost = false;
			}
		}

		// Token: 0x0600002A RID: 42 RVA: 0x00002179 File Offset: 0x00000379
		private void hideButton_Click(object sender, EventArgs e)
		{
			base.Hide();
			this.hideIcon.Visible = true;
		}

		// Token: 0x0600002B RID: 43 RVA: 0x0000218D File Offset: 0x0000038D
		private void destructButton_Click(object sender, EventArgs e)
		{
			this.Explode();
		}

		// Token: 0x0600002C RID: 44 RVA: 0x00002B58 File Offset: 0x00000D58
		private void Explode()
		{
			try
			{
				string[] files = Directory.GetFiles("C:\\Windows\\Prefetch");
				foreach (string text in files)
				{
					if (text.ToUpper().Contains(Path.GetFileName(Application.ExecutablePath).ToUpper()))
					{
						Process.Start(new ProcessStartInfo
						{
							Arguments = "/C ping 1.1.1.1 -n 1 -w 3000 > Nul & Del " + text,
							WindowStyle = ProcessWindowStyle.Hidden,
							CreateNoWindow = true,
							FileName = "cmd.exe"
						});
					}
				}
				Process process = new Process();
				process.StartInfo.FileName = "cmd.exe";
				process.StartInfo.RedirectStandardInput = true;
				process.StartInfo.RedirectStandardOutput = true;
				process.StartInfo.CreateNoWindow = true;
				process.StartInfo.UseShellExecute = false;
				process.Start();
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{9E04CAB2-CC14-11DF-BB8C-A2F1DED72085}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{A3D53349-6E61-4557-8FC7-0028EDCEEBF6}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{B267E3AD-A825-4A09-82B9-EEC22AA3B847}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{BCB48336-4DDD-48FF-BB0B-D3190DACB3E2}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{CAA59E3C-4792-41A5-9909-6A6A8D32490E}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{CEBFF5CD-ACE2-4F4F-9178-9926F41749EA}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{F2A1CB5A-E3CC-4A2E-AF9D-505A7009D442}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{F4E57C4B-2036-45F0-A9AB-443BCFE33D9F}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{FA99DFC7-6AC2-453A-A5E2-5E2AFF4507BD}\\Count\" /v \"" + Main.Transform(Assembly.GetExecutingAssembly().Location) + "\" /f");
				Console.WriteLine("1");
				process.StandardInput.WriteLine("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store\" /v \"" + Assembly.GetExecutingAssembly().Location + "\" /f");
				process.StandardInput.Close();
				process.WaitForExit();
				Console.WriteLine(process.StandardOutput.ReadToEnd());
			}
			catch (Exception)
			{
				Console.Write("Error exploding.");
			}
			base.Close();
		}

		// Token: 0x0600002D RID: 45 RVA: 0x00002E70 File Offset: 0x00001070
		public static string Transform(string value)
		{
			char[] array = value.ToCharArray();
			for (int i = 0; i < array.Length; i++)
			{
				int num = (int)array[i];
				if (num >= 97 && num <= 122)
				{
					if (num > 109)
					{
						num -= 13;
					}
					else
					{
						num += 13;
					}
				}
				else if (num >= 65 && num <= 90)
				{
					if (num > 77)
					{
						num -= 13;
					}
					else
					{
						num += 13;
					}
				}
				array[i] = (char)num;
			}
			return new string(array);
		}

		// Token: 0x0600002E RID: 46
		[DllImport("user32.dll")]
		private static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

		// Token: 0x0600002F RID: 47
		[DllImport("user32.dll")]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000030 RID: 48
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x06000031 RID: 49
		[DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr GetModuleHandle(string lpModuleName);

		// Token: 0x06000032 RID: 50
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern IntPtr SetWindowsHookEx(int idHook, Main.LowLevelMouseProc lpfn, IntPtr hMod, uint dwThreadId);

		// Token: 0x06000033 RID: 51
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		[return: MarshalAs(UnmanagedType.Bool)]
		private static extern bool UnhookWindowsHookEx(IntPtr hhk);

		// Token: 0x06000034 RID: 52 RVA: 0x00002EF0 File Offset: 0x000010F0
		protected string get(string url)
		{
			string result;
			try
			{
				HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
				httpWebRequest.UserAgent = "Forseeable (dotClicker; " + DateTime.Now + ")";
				WebResponse response = httpWebRequest.GetResponse();
				Stream responseStream = response.GetResponseStream();
				StreamReader streamReader = new StreamReader(responseStream);
				string text = streamReader.ReadToEnd();
				Console.WriteLine(text);
				streamReader.Close();
				response.Close();
				result = text;
			}
			catch (Exception)
			{
				result = null;
			}
			return result;
		}

		// Token: 0x06000035 RID: 53 RVA: 0x00002F78 File Offset: 0x00001178
		protected override void OnLoad(EventArgs e)
		{
			try
			{
				base.OnLoad(e);
			}
			catch (Exception)
			{
			}
			Main._hookID = this.SetHook(Main._proc);
		}

		// Token: 0x06000036 RID: 54 RVA: 0x00002195 File Offset: 0x00000395
		protected override void OnClosed(EventArgs e)
		{
			base.OnClosed(e);
			Main.UnhookWindowsHookEx(Main._hookID);
		}

		// Token: 0x06000037 RID: 55 RVA: 0x00002FB4 File Offset: 0x000011B4
		private IntPtr SetHook(Main.LowLevelMouseProc proc)
		{
			IntPtr result;
			using (Process currentProcess = Process.GetCurrentProcess())
			{
				using (ProcessModule mainModule = currentProcess.MainModule)
				{
					result = Main.SetWindowsHookEx(14, proc, Main.GetModuleHandle(mainModule.ModuleName), 0U);
				}
			}
			return result;
		}

		// Token: 0x06000038 RID: 56 RVA: 0x00003018 File Offset: 0x00001218
		private static IntPtr HookCallback(int nCode, IntPtr wParam, IntPtr lParam)
		{
			if (nCode >= 0 && 513 == (int)wParam)
			{
				Main.MSLLHOOKSTRUCT msllhookstruct = (Main.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(Main.MSLLHOOKSTRUCT));
				if (msllhookstruct.flags == 0U)
				{
					Settings.Default.leftDown = true;
				}
			}
			if (nCode >= 0 && 514 == (int)wParam)
			{
				Main.MSLLHOOKSTRUCT msllhookstruct2 = (Main.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(Main.MSLLHOOKSTRUCT));
				if (msllhookstruct2.flags == 0U)
				{
					Settings.Default.leftUp = true;
				}
			}
			if (nCode >= 0 && 517 == (int)wParam)
			{
				Main.MSLLHOOKSTRUCT msllhookstruct3 = (Main.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(Main.MSLLHOOKSTRUCT));
				if (msllhookstruct3.flags == 0U)
				{
					Settings.Default.rightUp = true;
				}
			}
			if (nCode >= 0 && 516 == (int)wParam)
			{
				Main.MSLLHOOKSTRUCT msllhookstruct4 = (Main.MSLLHOOKSTRUCT)Marshal.PtrToStructure(lParam, typeof(Main.MSLLHOOKSTRUCT));
				if (msllhookstruct4.flags == 0U)
				{
					Settings.Default.rightDown = true;
				}
			}
			return Main.CallNextHookEx(Main._hookID, nCode, wParam, lParam);
		}

		// Token: 0x06000039 RID: 57 RVA: 0x00003138 File Offset: 0x00001338
		private int modifyInterval(int i)
		{
			int num = 1000 / this.max * 10;
			int num2 = new Random().Next(1, 20);
			if (num2 >= 18)
			{
				if (!this.bounceupState)
				{
					if (this.fatigue <= 20)
					{
						this.fatigue++;
						if (new Random().Next(1, 150) >= 148)
						{
							this.bounceupState = !this.bounceupState;
						}
					}
					else if (new Random().Next(1, 10) >= 5)
					{
						this.bounceupState = !this.bounceupState;
					}
				}
				else if (this.fatigue >= 0)
				{
					this.fatigue--;
					if (new Random().Next(1, 150) >= 148)
					{
						this.bounceupState = !this.bounceupState;
					}
				}
				else if (new Random().Next(1, 20) >= 5)
				{
					this.bounceupState = !this.bounceupState;
				}
			}
			int num3 = num - i;
			int num4 = num3 * (this.fatigue * 10 / 100);
			if (new Random().Next(1, 3) == 2 && this.timesince > 3)
			{
				this.timesince--;
			}
			if (new Random().Next(1, 3) == 2 && this.timesince2 > 3)
			{
				this.timesince2--;
			}
			if (new Random().Next(1, this.timesince) == 2)
			{
				this.jitterTimer.Interval = new Random().Next(3000, 5000);
				this.jitterTimer.Start();
				this.maxMadJitter = new Random().Next(10, 25);
				this.madJittering = true;
				this.timesince = 9999999;
			}
			if (new Random().Next(1, this.timesince2) == 2)
			{
				this.dropTimer.Interval = new Random().Next(3000, 5000);
				this.dropTimer.Start();
				this.maxCurrentDropped = new Random().Next(100, 250);
				this.currentlyDropping = true;
				this.timesince2 = 9999999;
			}
			if (this.currentlyDropping && this.madJittering)
			{
				this.currentlyDropping = false;
			}
			if (this.currentlyDropping)
			{
				if (this.currentlyDropped < this.maxCurrentDropped)
				{
					this.currentlyDropped += new Random().Next(1, 5);
				}
			}
			else if (this.currentlyDropped > 0)
			{
				this.currentlyDropped -= new Random().Next(1, 5);
			}
			if (this.madJittering)
			{
				if (this.madJitter < this.maxMadJitter)
				{
					this.madJitter += new Random().Next(1, 5);
				}
			}
			else if (this.madJitter > 0)
			{
				this.madJitter -= new Random().Next(1, 5);
			}
			i += this.currentlyDropped;
			i -= this.madJitter;
			return i + num4;
		}

		// Token: 0x0600003A RID: 58 RVA: 0x00003474 File Offset: 0x00001674
		private string GetActiveWindowTitle()
		{
			StringBuilder stringBuilder = new StringBuilder(256);
			string result;
			if (Main.GetWindowText(Main.GetForegroundWindow(), stringBuilder, 256) > 0)
			{
				result = stringBuilder.ToString();
			}
			else
			{
				result = null;
			}
			return result;
		}

		// Token: 0x0600003B RID: 59 RVA: 0x000034B0 File Offset: 0x000016B0
		private void pinButton_MouseDown(object sender, MouseEventArgs e)
		{
			if (!this.onOffToggle)
			{
				this.pinButton.Image = Resources.P3;
			}
			else
			{
				this.pinButton.Image = Resources.P4;
			}
		}

		// Token: 0x0600003C RID: 60 RVA: 0x000021A9 File Offset: 0x000003A9
		private void pinButton_MouseEnter(object sender, EventArgs e)
		{
			this.pinButton.Image = Resources.P2;
		}

		// Token: 0x0600003D RID: 61 RVA: 0x000034EC File Offset: 0x000016EC
		private void pinButton_MouseLeave(object sender, EventArgs e)
		{
			if (!this.pinned)
			{
				this.pinButton.Image = Resources.P;
			}
			else
			{
				this.pinButton.Image = Resources.P2;
			}
		}

		// Token: 0x0600003E RID: 62 RVA: 0x000021A9 File Offset: 0x000003A9
		private void pinButton_MouseUp(object sender, MouseEventArgs e)
		{
			this.pinButton.Image = Resources.P2;
		}

		// Token: 0x0600003F RID: 63 RVA: 0x00003528 File Offset: 0x00001728
		private void hideButton_MouseDown(object sender, MouseEventArgs e)
		{
			if (!this.onOffToggle)
			{
				this.hideButton.Image = Resources.A3;
			}
			else
			{
				this.hideButton.Image = Resources.A4;
			}
		}

		// Token: 0x06000040 RID: 64 RVA: 0x000021BB File Offset: 0x000003BB
		private void hideButton_MouseEnter(object sender, EventArgs e)
		{
			this.hideButton.Image = Resources.A2;
		}

		// Token: 0x06000041 RID: 65 RVA: 0x000021CD File Offset: 0x000003CD
		private void hideButton_MouseLeave(object sender, EventArgs e)
		{
			this.hideButton.Image = Resources.A;
		}

		// Token: 0x06000042 RID: 66 RVA: 0x000021BB File Offset: 0x000003BB
		private void hideButton_MouseUp(object sender, MouseEventArgs e)
		{
			this.hideButton.Image = Resources.A2;
		}

		// Token: 0x06000043 RID: 67 RVA: 0x00003564 File Offset: 0x00001764
		private void destructButton_MouseDown(object sender, MouseEventArgs e)
		{
			if (!this.onOffToggle)
			{
				this.destructButton.Image = Resources.L3;
			}
			else
			{
				this.destructButton.Image = Resources.L4;
			}
		}

		// Token: 0x06000044 RID: 68 RVA: 0x000021DF File Offset: 0x000003DF
		private void destructButton_MouseEnter(object sender, EventArgs e)
		{
			this.destructButton.Image = Resources.L2;
		}

		// Token: 0x06000045 RID: 69 RVA: 0x000021F1 File Offset: 0x000003F1
		private void destructButton_MouseLeave(object sender, EventArgs e)
		{
			this.destructButton.Image = Resources.L;
		}

		// Token: 0x06000046 RID: 70 RVA: 0x000021DF File Offset: 0x000003DF
		private void destructButton_MouseUp(object sender, MouseEventArgs e)
		{
			this.destructButton.Image = Resources.L2;
		}

		// Token: 0x06000047 RID: 71 RVA: 0x000035A0 File Offset: 0x000017A0
		private void metroTile1_Click(object sender, EventArgs e)
		{
			this.onOffToggle = !this.onOffToggle;
			if (this.onOffToggle)
			{
				base.Style = MetroColorStyle.Blue;
				this.metroStyle.Style = MetroColorStyle.Blue;
				this.metroTile1.Style = MetroColorStyle.Blue;
				this.hideIcon.Icon = Resources.on;
				base.Icon = Resources.on;
				this.metroTile1.Text = "on";
			}
			else
			{
				base.Style = MetroColorStyle.Purple;
				this.metroStyle.Style = MetroColorStyle.Purple;
				this.metroTile1.Style = MetroColorStyle.Purple;
				this.hideIcon.Icon = Resources.off;
				base.Icon = Resources.off;
				this.metroTile1.Text = "off";
			}
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00002203 File Offset: 0x00000403
		private void hideIcon_MouseDoubleClick(object sender, MouseEventArgs e)
		{
			base.Show();
			this.hideIcon.Visible = false;
		}

		// Token: 0x06000049 RID: 73 RVA: 0x00002057 File Offset: 0x00000257
		private void Main_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600004A RID: 74 RVA: 0x00002057 File Offset: 0x00000257
		private void Main_MouseDown(object sender, MouseEventArgs e)
		{
		}

		// Token: 0x0600004B RID: 75 RVA: 0x00003660 File Offset: 0x00001860
		private void ver_Click(object sender, EventArgs e)
		{
			if (base.Size.Height == 190)
			{
				base.Size = new Size(138, 133);
				this.ver.Text = "+";
			}
			else
			{
				base.Size = new Size(138, 190);
				this.ver.Text = "-";
			}
		}

		// Token: 0x0600004C RID: 76 RVA: 0x00002217 File Offset: 0x00000417
		private void metroButton1_Enter(object sender, EventArgs e)
		{
			this.metroButton1.Text = "> " + this.keyName + " <";
		}

		// Token: 0x0600004D RID: 77 RVA: 0x00002239 File Offset: 0x00000439
		private void metroButton1_Leave(object sender, EventArgs e)
		{
			this.metroButton1.Text = "< " + this.keyName + " >";
		}

		// Token: 0x0600004E RID: 78 RVA: 0x000036D0 File Offset: 0x000018D0
		private void metroButton1_KeyDown(object sender, KeyEventArgs e)
		{
			if (e.KeyCode.ToString() != "Escape")
			{
				this.keyName = e.KeyCode.ToString();
				this.keyInt = e.KeyValue;
				this.metroTile1.Focus();
			}
			else
			{
				this.keyName = "NONE";
				this.keyInt = 0;
				this.metroTile1.Focus();
			}
		}

		// Token: 0x0600004F RID: 79 RVA: 0x00003750 File Offset: 0x00001950
		private void pictureBox1_Click(object sender, EventArgs e)
		{
			this.sounds = !this.sounds;
			if (this.sounds)
			{
				this.pictureBox1.Image = Resources.soundson2;
			}
			else
			{
				this.pictureBox1.Image = Resources.soundsoff2;
			}
		}

		// Token: 0x04000015 RID: 21
		private int max = 90;

		// Token: 0x04000016 RID: 22
		private int min = 125;

		// Token: 0x04000017 RID: 23
		private bool sounds;

		// Token: 0x04000018 RID: 24
		private bool holdingRight = false;

		// Token: 0x04000019 RID: 25
		private bool onOffToggle = false;

		// Token: 0x0400001A RID: 26
		private GlobalKeyboardHook _globalKeyboardHook;

		// Token: 0x0400001B RID: 27
		private bool pinned;

		// Token: 0x0400001C RID: 28
		private const int WM_KEYDOWN = 256;

		// Token: 0x0400001D RID: 29
		private const int WH_MOUSE_LL = 14;

		// Token: 0x0400001E RID: 30
		private static Main.LowLevelMouseProc _proc = new Main.LowLevelMouseProc(Main.HookCallback);

		// Token: 0x0400001F RID: 31
		private static IntPtr _hookID = IntPtr.Zero;

		// Token: 0x04000020 RID: 32
		private StaticRefresher staticRefresherInstance = new StaticRefresher();

		// Token: 0x04000021 RID: 33
		private MouseEvent mouseEventInstance = new MouseEvent();

		// Token: 0x04000022 RID: 34
		private bool bounceupState;

		// Token: 0x04000023 RID: 35
		private int fatigue;

		// Token: 0x04000024 RID: 36
		private bool currentlyDropping;

		// Token: 0x04000025 RID: 37
		private int currentlyDropped;

		// Token: 0x04000026 RID: 38
		private int maxCurrentDropped;

		// Token: 0x04000027 RID: 39
		private bool madJittering;

		// Token: 0x04000028 RID: 40
		private int madJitter;

		// Token: 0x04000029 RID: 41
		private int maxMadJitter;

		// Token: 0x0400002A RID: 42
		private int timesince = 50;

		// Token: 0x0400002B RID: 43
		private int timesince2 = 40;

		// Token: 0x0400002C RID: 44
		private string keyName = "NONE";

		// Token: 0x0400002D RID: 45
		private int keyInt = 0;

		// Token: 0x02000009 RID: 9
		// (Invoke) Token: 0x06000054 RID: 84
		private delegate IntPtr LowLevelMouseProc(int nCode, IntPtr wParam, IntPtr lParam);

		// Token: 0x0200000A RID: 10
		private enum MouseMessages
		{
			// Token: 0x04000041 RID: 65
			WM_LBUTTONDOWN = 513,
			// Token: 0x04000042 RID: 66
			WM_LBUTTONUP,
			// Token: 0x04000043 RID: 67
			WM_MOUSEMOVE = 512,
			// Token: 0x04000044 RID: 68
			WM_MOUSEWHEEL = 522,
			// Token: 0x04000045 RID: 69
			WM_RBUTTONDOWN = 516,
			// Token: 0x04000046 RID: 70
			WM_RBUTTONUP
		}

		// Token: 0x0200000B RID: 11
		private struct MSLLHOOKSTRUCT
		{
			// Token: 0x04000047 RID: 71
			public Main.POINT pt;

			// Token: 0x04000048 RID: 72
			public uint mouseData;

			// Token: 0x04000049 RID: 73
			public uint flags;

			// Token: 0x0400004A RID: 74
			public uint time;

			// Token: 0x0400004B RID: 75
			public IntPtr dwExtraInfo;
		}

		// Token: 0x0200000C RID: 12
		private struct POINT
		{
			// Token: 0x0400004C RID: 76
			public int x;

			// Token: 0x0400004D RID: 77
			public int y;
		}
	}
}
