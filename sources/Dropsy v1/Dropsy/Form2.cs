using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.IO;
using System.Linq;
using System.Media;
using System.Net;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.ServiceProcess;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Discord.Webhook;
using Discord.Webhook.HookRequest;
using DropsyUtils;
using Guna.UI.WinForms;
using Guna.UI2.WinForms;
using KeybindManager_;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using New_CandyClient.Memory;
using New_CandyClient.Utils;

namespace Dropsy
{
	// Token: 0x02000017 RID: 23
	public partial class Form2 : Form
	{
		// Token: 0x0600007D RID: 125 RVA: 0x0000560C File Offset: 0x00003A0C
		public Form2()
		{
			base.Opacity = 0.0;
			this.InitializeComponent();
			Form1 form = new Form1();
			Form form2 = new Form3();
			new Form5().Close();
			form.Close();
			form2.Close();
			Keys[] array = new Keys[4];
			this.keys_0 = array;
			this.bool0 = false;
			this.bool1 = false;
			//new Keys[4];
			this.keys_1 = array;
			this.keybindManager_0 = new KeybindManager();
			this.keybindManager_0.start();
		}

		// Token: 0x0600007E RID: 126
		[DllImport("kernel32.dll", EntryPoint = "GetSystemTime", SetLastError = true)]
		private static extern void GetSystemTimes(ref Form2.SystemTime Time);

		// Token: 0x0600007F RID: 127
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern bool SetSystemTime(ref Form2.SystemTime Time2);

		// Token: 0x06000080 RID: 128
		[DllImport("user32.dll")]
		private static extern int GetWindowThreadProcessId([In] IntPtr intptr_0, ref int int_6);

		// Token: 0x06000081 RID: 129
		[DllImport("kernel32.dll")]
		internal static extern bool CloseHandle(IntPtr intPtr);

		// Token: 0x06000082 RID: 130
		[DllImport("USER32.dll")]
		private static extern short GetKeyState(Keys nVirtKey);

		// Token: 0x06000083 RID: 131
		[DllImport("user32.dll")]
		public static extern bool RegisterHotKey(IntPtr hWnd, int id, int fsModifiers, int vlc);

		// Token: 0x06000084 RID: 132
		[DllImport("user32", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int GetWindowText(IntPtr hWnd, StringBuilder lpString, int cch);

		// Token: 0x06000085 RID: 133
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000086 RID: 134
		[DllImport("user32.dll", SetLastError = true)]
		private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

		// Token: 0x06000087 RID: 135
		[DllImport("user32.dll", SetLastError = true)]
		private static extern uint GetWindowThreadProcessId(IntPtr hWnd, out uint processId);

		// Token: 0x06000088 RID: 136
		[DllImport("user32")]
		public static extern bool ReleaseCapture();

		// Token: 0x06000089 RID: 137
		[DllImport("user32.dll", CharSet = CharSet.Auto)]
		public static extern IntPtr SendMessage(IntPtr hWnd, uint msg, int wParam, int lParam);

		// Token: 0x0600008A RID: 138
		[DllImport("user32.dll")]
		private static extern void keybd_event(byte bVk, byte bScan, uint dwFlags, int dwExtraInfo);

		// Token: 0x0600008B RID: 139
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(Keys vKey);

		// Token: 0x0600008C RID: 140
		[DllImport("user32.dll")]
		private static extern int MapVirtualKey(int uCode, uint uMapType);

		// Token: 0x0600008D RID: 141
		[DllImport("wininet.dll")]
		private static extern bool InternetGetConnectedState(out int description, int reservedValue);

		// Token: 0x0600008E RID: 142
		[DllImport("kernel32.dll")]
		private static extern int VirtualQueryEx(IntPtr hProcess, IntPtr lpAddress, out Form2.MEMORY_BASIC_INFORMATION64 lpBuffer, uint dwLength);

		// Token: 0x0600008F RID: 143
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr hWnd);

		// Token: 0x06000090 RID: 144
		[DllImport("kernel32.dll", SetLastError = true)]
		public static extern uint GetProcessIdOfThread(IntPtr handle);

		// Token: 0x06000091 RID: 145
		[DllImport("kernel32.dll", SetLastError = true)]
		private static extern IntPtr OpenThread(Form2.ThreadAccess dwDesiredAccess, bool bInheritHandle, uint dwThreadId);

		// Token: 0x06000092 RID: 146
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetClassName(IntPtr hWnd, StringBuilder lpClassName, int nMaxCount);

		// Token: 0x06000093 RID: 147
		[DllImport("user32.dll")]
		private static extern uint GetWindowThreadProcessId(IntPtr hWnd, IntPtr ProcessId);

		// Token: 0x06000094 RID: 148
		[DllImport("user32.dll")]
		private static extern bool EnumWindows(Form2.EnumWindowsProc enumProc, IntPtr lParam);

		// Token: 0x06000095 RID: 149
		[DllImport("kernel32.dll")]
		public static extern bool ReadProcessMemory(int hProcess, int lpBaseAddress, byte[] lpBuffer, int dwSize, ref int lpNumberOfBytesRead);

		// Token: 0x06000096 RID: 150
		[DllImport("kernel32.dll")]
		public static extern IntPtr OpenProcess(int dwDesiredAccess, bool bInheritHandle, int dwProcessId);

		// Token: 0x06000097 RID: 151 RVA: 0x00005700 File Offset: 0x00003B00
		public static bool IsInternetAvailable()
		{
			int num;
			return Form2.InternetGetConnectedState(out num, 0);
		}

		// Token: 0x06000098 RID: 152 RVA: 0x00005718 File Offset: 0x00003B18
		public void Delay(double dblSecs)
		{
			DateTime.Now.AddSeconds(1.1574074074074073E-05);
			DateTime t = DateTime.Now.AddSeconds(1.1574074074074073E-05).AddSeconds(dblSecs);
			while (DateTime.Compare(DateTime.Now, t) <= 0)
			{
				Application.DoEvents();
			}
		}

		// Token: 0x06000099 RID: 153 RVA: 0x00005774 File Offset: 0x00003B74
		private void Clicking()
		{
			if (this.acBTN.Checked)
			{
				Form2.SendMessage(Form2.GetForegroundWindow(), 513U, 0, 0);
				Thread.Sleep(this.rnd.Next(104, 312));
				Form2.SendMessage(Form2.GetForegroundWindow(), 514U, 0, 0);
				Thread.Sleep(this.rnd.Next(75, 124));
			}
		}

		// Token: 0x0600009A RID: 154 RVA: 0x000057E0 File Offset: 0x00003BE0
		private void method12(Keys keys_1)
		{
			if (this.bool1)
			{
				if (keys_1 == Keys.Escape)
				{
					this.bool1 = false;
					this.refreshToggleKey();
				}
				else if (!ArrayUtils.arrayContain<Keys>(this.keys_0, keys_1))
				{
					this.bool1 = false;
					this.togglekey = keys_1;
					this.refreshToggleKey();
					this.BindButton.Refresh();
					this.acBTN.Refresh();
				}
				if (this.keybindManager_0.getKeyStatus(Keys.Escape))
				{
					this.keybindManager_0.stop();
					this.bool1 = false;
					this.BindButton.Text = "Not Bound";
					this.BindButton.Refresh();
					this.acBTN.Refresh();
					return;
				}
			}
			else if (keys_1 == this.togglekey)
			{
				this.bool0 = !this.bool0;
				this.refreshStatus();
				this.BindButton.Refresh();
				this.acBTN.Refresh();
			}
		}

		// Token: 0x0600009B RID: 155 RVA: 0x000058C0 File Offset: 0x00003CC0
		private void SetHotkey()
		{
			this.keybindManager_0.registerKeyToggleCallback(new KeybindManager.keyStatusCallBack(this.method12));
		}

		// Token: 0x0600009C RID: 156 RVA: 0x000058DC File Offset: 0x00003CDC
		public void refreshStatus()
		{
			bool @checked = this.bool0;
			bool flag = this.onandoff;
			this.acBTN.Checked = @checked;
			this.onandoff = flag;
			this.acBTN.Refresh();
		}

		// Token: 0x0600009D RID: 157 RVA: 0x00005924 File Offset: 0x00003D24
		public void refreshToggleKey()
		{
			if (this.keybindManager_0.getKeyStatus(Keys.Escape))
			{
				this.keybindManager_0.stop();
				this.bool1 = false;
				this.BindButton.Text = "Not Bound";
				this.BindButton.Refresh();
			}
			this.BindButton.Text = KeybindManager.getKeyName(this.togglekey, 6);
			this.BindButton.Refresh();
			this.acBTN.Refresh();
		}

		// Token: 0x0600009E RID: 158 RVA: 0x0000599A File Offset: 0x00003D9A
		private void SetToggleBtn_Click(object sender, EventArgs e)
		{
			if (!this.bool1 && this.keybindManager_0.getKeyStatus(Keys.LButton))
			{
				this.bool1 = true;
				this.BindButton.Text = "Waiting For Bind";
				this.BindButton.Refresh();
			}
		}

		// Token: 0x0600009F RID: 159 RVA: 0x000059D4 File Offset: 0x00003DD4
		public string GetCaption()
		{
			StringBuilder stringBuilder = new StringBuilder(256);
			Form2.GetWindowText(Form2.GetForegroundWindow(), stringBuilder, stringBuilder.Capacity);
			return stringBuilder.ToString();
		}

		// Token: 0x060000A0 RID: 160 RVA: 0x00005A04 File Offset: 0x00003E04
		private void StartAutoclicker_Tick(object sender, EventArgs e)
		{
			checked
			{
				try
				{
					VBMath.Randomize();
					VBMath.Rnd();
					int maxValue = (int)((double)this.rnd.Next(1035, 1120) / unchecked((double)this.min.Value + (double)this.max.Value * 0.23));
					int minValue = (int)((double)this.rnd.Next(1115, 1255) / unchecked((double)this.min.Value + (double)this.max.Value * 0.58));
					Thread thread = new Thread(new ThreadStart(this.Clicking));
					this.startAutoclicker.Interval = this.rnd.Next(minValue, maxValue);
					if (Form2.GetAsyncKeyState(Keys.LButton) < 0)
					{
						bool flag = Form2.GetAsyncKeyState(Keys.LButton) < 0;
					}
					if (this.GetCaption().Contains(this.CaptionBox.Text) && this.clicker)
					{
						if (Control.MouseButtons != MouseButtons.Left)
						{
							thread.Abort();
							Thread.Sleep(1);
						}
						else if (!thread.IsAlive)
						{
							thread.Start();
							Thread.Sleep(1);
						}
						else
						{
							thread.Join();
							Thread.Sleep(1);
						}
						Thread.Sleep(1);
					}
				}
				catch
				{
				}
			}
		}

		// Token: 0x060000A1 RID: 161 RVA: 0x00005B58 File Offset: 0x00003F58
		private void svchost_Load(object sender, EventArgs e)
		{
			string text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			char[] array = new char[10];
			Random random = new Random();
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = text[random.Next(text.Length)];
			}
			string text2 = new string(array);
			base.Text = text2;
			this.anti1.Stop();
			this.timer1.Stop();
			this.timer2.Stop();
			this.timer3.Stop();
			this.DisableReach.Stop();
			base.Hide();
			if (Form2.IsInternetAvailable())
			{
				base.Activate();
				base.Show();
			}
			if (!Form2.IsInternetAvailable())
			{
				MessageBox.Show("No internet available, please try again later.", "Dropsy");
				Process.Start(new ProcessStartInfo
				{
					Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
					WindowStyle = ProcessWindowStyle.Hidden,
					CreateNoWindow = true,
					FileName = "cmd.exe"
				});
				Environment.Exit(1);
			}
			this.fadeinLoad.Start();
			this.fadein.Start();
			base.Activate();
			base.Show();
			base.Update();
			this.Hiding.Enabled = false;
			this.SetHotkey();
			this.logging.Text = "logged in as " + Form3.passingText + ".";
			Form2.GetWindowThreadProcessId(Form2.FindWindow("LWJGL", null), out this.pid);
		}

		// Token: 0x060000A2 RID: 162 RVA: 0x00005CCC File Offset: 0x000040CC
		private void svchost_FormClosing(object sender, FormClosingEventArgs e)
		{
			try
			{
				this.Syn(3.0);
				this.keybindManager_0.stop();
			}
			catch
			{
			}
			Application.Exit();
			Environment.Exit(5);
		}

		// Token: 0x060000A3 RID: 163 RVA: 0x00005D14 File Offset: 0x00004114
		private void AntiSS_CheckedChanged(object sender)
		{
			if (this.AntiSS.Checked)
			{
				base.ShowInTaskbar = false;
				this.anti1.Start();
				return;
			}
			base.ShowInTaskbar = true;
			this.anti1.Stop();
		}

		// Token: 0x060000A4 RID: 164 RVA: 0x00005D48 File Offset: 0x00004148
		private void anti1_Tick(object sender, EventArgs e)
		{
			foreach (Process process in Process.GetProcesses())
			{
				if (process.ProcessName == "LastActivityView")
				{
					process.Kill();
					MessageBox.Show("The application was unable to start correctly (0xc0000005). Click OK to close the application.", "lastactivityview.exe - Application Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
				}
				if (process.ProcessName == "ProcessHacker")
				{
					process.Kill();
					MessageBox.Show("The application was unable to start correctly (0xc00007b). Click OK to close the application.", "ProcessHacker.exe - Application Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
				}
				if (process.ProcessName == "OpenSaveFilesView")
				{
					process.Kill();
					MessageBox.Show("The application was unable to start correctly (0xc00007b). Click OK to close the application.", "opensavefilesview.exe - Application Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
				}
				if (process.ProcessName == "WinPrefetchView")
				{
					process.Kill();
					MessageBox.Show("The application was unable to start correctly (0xc0000005). Click OK to close the application.", "winprefetchview.exe - Application Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
				}
				if (process.ProcessName == "BrowsingHistoryView")
				{
					process.Kill();
					MessageBox.Show("The application was unable to start correctly (0xc00007b). Click OK to close the application.", "browsinghistorview.exe - Application Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
				}
				if (process.ProcessName == "UserAssistView")
				{
					process.Kill();
					MessageBox.Show("The application was unable to start correctly (0xc0000005). Click OK to close the application.", "UserAssistView.exe - Application Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
				}
				if (this.AntiSS.Checked)
				{
					Directory.CreateDirectory("C:\\Users\\" + Environment.UserName + "\\AppData\\Roaming\\.minecraft\\versions\\你好你妈妈是他妈的同性恋就像你一样如");
				}
				else
				{
					Directory.Delete("C:\\Users\\" + Environment.UserName + "\\AppData\\Roaming\\.minecraft\\versions\\你好你妈妈是他妈的同性恋就像你一样如", true);
				}
			}
		}

		// Token: 0x060000A5 RID: 165 RVA: 0x00003A84 File Offset: 0x00001E84
		private void label18_Click(object sender, EventArgs e)
		{
			Application.Exit();
		}

		// Token: 0x060000A6 RID: 166 RVA: 0x00003D0C File Offset: 0x0000210C
		private void label19_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x060000A7 RID: 167 RVA: 0x00005EB8 File Offset: 0x000042B8
		private void autoclickerStatus1_Click(object sender, EventArgs e)
		{
			if (!this.onandoff)
			{
				this.clicker = true;
				this.onandoff = true;
				return;
			}
			this.clicker = false;
			this.onandoff = false;
		}

		// Token: 0x060000A8 RID: 168 RVA: 0x00005EE4 File Offset: 0x000042E4
		private void Hiding_Tick(object sender, EventArgs e)
		{
			base.Opacity = 0.1;
			if (Convert.ToBoolean(Form2.GetAsyncKeyState(Keys.Insert)))
			{
				this.fadein.Enabled = true;
				base.Show();
				this.Hiding.Enabled = false;
				return;
			}
			base.Hide();
		}

		// Token: 0x060000A9 RID: 169 RVA: 0x00005F34 File Offset: 0x00004334
		private static string sha256(string randomString)
		{
			HashAlgorithm hashAlgorithm = new SHA256Managed();
			string text = string.Empty;
			foreach (byte b in hashAlgorithm.ComputeHash(Encoding.ASCII.GetBytes(randomString)))
			{
				text += b.ToString("x2");
			}
			return text;
		}

		// Token: 0x060000AA RID: 170 RVA: 0x00005F84 File Offset: 0x00004384
		private void SpoofDeleter_Tick(object sender, EventArgs e)
		{
			string path = "C:\\Windows\\IME\\spoofer.exe";
			try
			{
				File.Delete(path);
			}
			catch
			{
			}
			this.SpoofDeleter.Stop();
		}

		// Token: 0x060000AB RID: 171 RVA: 0x00005FC0 File Offset: 0x000043C0
		private void AntiSS_CheckedChanged(object sender, EventArgs e)
		{
			if (this.AntiSS.Checked)
			{
				this.anti1.Start();
				return;
			}
			this.anti1.Stop();
		}

		// Token: 0x060000AC RID: 172 RVA: 0x00005FE6 File Offset: 0x000043E6
		public static void sendWebHook(string URL, string msg, string username)
		{
			Form2.Http.Post(URL, new NameValueCollection
			{
				{
					"username",
					username
				},
				{
					"content",
					msg
				}
			});
		}

		// Token: 0x060000AD RID: 173 RVA: 0x0000600C File Offset: 0x0000440C
		public static byte[] ToByteArray(string value)
		{
			char[] array = value.ToCharArray();
			byte[] array2 = new byte[array.Length];
			for (int i = 0; i < array.Length; i++)
			{
				byte b = Convert.ToByte(array[i]);
				array2[i] = b;
			}
			return array2;
		}

		// Token: 0x060000AE RID: 174 RVA: 0x00006048 File Offset: 0x00004448
		private void guna2Button2_Click(object sender, EventArgs e)
		{
			this.TimerReach.Stop();
			this.DisableReach.Start();
			string value = this.SafeDstrct.Checked.ToString();
			string value2 = this.chkDestruct.Checked.ToString();
			string value3 = this.method2.Checked.ToString();
			string value4 = this.LAVCrash.Checked.ToString();
			DiscordWebhook discordWebhook = new DiscordWebhook();
			discordWebhook.HookUrl = "https://discordapp.com/api/webhooks/741741792118177922/8ljYF0c07GJV6fcaft5cqw4tByOXLSExRPaclQZXjLFRCa-qe_n6ye6iW07oI-QgWBRq";
			DiscordHookBuilder discordHookBuilder = DiscordHookBuilder.Create("droppelers", null);
			DiscordEmbedField[] fields = new DiscordEmbedField[]
			{
				new DiscordEmbedField("clean traces", value, false),
				new DiscordEmbedField("self delete", value2, false),
				new DiscordEmbedField("clear cache", value3, false),
				new DiscordEmbedField("lav crasher", value4, false)
			};
			DiscordEmbed item = new DiscordEmbed("User " + Form3.passingText + " destructed.", "Parameters", 6173352, "", "Destruct 0.4", "", fields);
			discordHookBuilder.Embeds.Add(item);
			DiscordHook hookRequest = discordHookBuilder.Build();
			discordWebhook.Hook(hookRequest);
			this.keybindManager_0.stop();
			ProcessStartInfo processStartInfo = new ProcessStartInfo();
			string str = "ipconfig /flushdns";
			processStartInfo.UseShellExecute = true;
			processStartInfo.WorkingDirectory = "C:\\Windows\\System32";
			processStartInfo.FileName = "C:\\Windows\\System32\\cmd.exe";
			processStartInfo.Verb = "";
			processStartInfo.Arguments = "/c " + str;
			processStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
			Process.Start(processStartInfo);
			Thread.Sleep(1500);
			new DateTime(2649, 8, 17);
			new DateTime(1189, 9, 3);
			int num = 9;
			int num2 = 993681593;
			int num3 = 571996178;
			Form2.Private4353 = 2959;
			DateTime dateTime = new DateTime(1755, 10, 22);
			int year = 2565;
			new DateTime(year, 5, 4);
			int num4 = 3000;
			new DateTime((547052945 - dateTime.Year < 1972606450) ? Form2.Private4353 : 1479, (num3 < num2) ? num : 4, 26);
			int millisecondsTimeout = num4;
			string path = Environment.ExpandEnvironmentVariables("%AppData%\\Microsoft\\Windows\\Recent");
			DateTime lastWriteTime = Directory.GetLastWriteTime(path);
			foreach (string path2 in Directory.GetFiles(path))
			{
				try
				{
					File.Delete(path2);
					Directory.SetLastWriteTime(path, lastWriteTime);
					path = null;
				}
				catch (Exception)
				{
				}
			}
			this.cmd("net stop w32time");
			ServiceController serviceController = new ServiceController("dps");
			if (serviceController.Status == ServiceControllerStatus.Running)
			{
				serviceController.Stop();
			}
			Thread.Sleep(millisecondsTimeout);
			ServiceController serviceController2 = new ServiceController("PcaSvc");
			if (serviceController2.Status == ServiceControllerStatus.Running)
			{
				serviceController2.Stop();
			}
			serviceController.WaitForStatus(ServiceControllerStatus.Stopped);
			serviceController.Start();
			serviceController2.WaitForStatus(ServiceControllerStatus.Stopped);
			serviceController2.Start();
			this.cmd("(net start w32time) && (w32tm /resync /nowait)");
			if (this.chkDestruct.Checked)
			{
				this.cmd("(ping - n 3 127.0.0.1) && (del / Q \"" + Application.ExecutablePath + "\")");
			}
			Path.GetFileName(Environment.GetCommandLineArgs()[0]);
			DirectoryInfo directoryInfo = new DirectoryInfo("C:\\Windows\\Prefetch");
			string path3 = Environment.ExpandEnvironmentVariables("C:\\Windows\\Prefetch");
			DateTime lastWriteTime2 = Directory.GetLastWriteTime(path3);
			string friendlyName = AppDomain.CurrentDomain.FriendlyName;
			string str2 = "timeout";
			string str3 = "cmd";
			string str4 = "ipconfig";
			FileInfo[] files2 = directoryInfo.GetFiles(friendlyName + "*");
			FileInfo[] files3 = directoryInfo.GetFiles(str2 + "*");
			FileInfo[] files4 = directoryInfo.GetFiles(str3 + "*");
			FileInfo[] files5 = directoryInfo.GetFiles(str4 + "*");
			FileInfo[] array = files2;
			for (int i = 0; i < array.Length; i++)
			{
				File.Delete(array[i].FullName);
			}
			array = files3;
			for (int i = 0; i < array.Length; i++)
			{
				File.Delete(array[i].FullName);
			}
			array = files4;
			for (int i = 0; i < array.Length; i++)
			{
				File.Delete(array[i].FullName);
			}
			array = files5;
			for (int i = 0; i < array.Length; i++)
			{
				File.Delete(array[i].FullName);
			}
			Directory.SetLastWriteTime(path3, lastWriteTime2);
			base.Hide();
			Thread.Sleep(1000);
			Process.GetCurrentProcess().Kill();
			Application.Exit();
			GC.Collect();
		}

		// Token: 0x060000AF RID: 175 RVA: 0x000064D8 File Offset: 0x000048D8
		private void cmd(string command)
		{
			Process.Start(new ProcessStartInfo("cmd.exe")
			{
				CreateNoWindow = true,
				WindowStyle = ProcessWindowStyle.Hidden,
				Arguments = "/C " + command
			});
		}

		// Token: 0x060000B0 RID: 176 RVA: 0x0000406C File Offset: 0x0000246C
		private void guna2ControlBox1_Click(object sender, EventArgs e)
		{
			Environment.Exit(5);
		}

		// Token: 0x060000B1 RID: 177 RVA: 0x00003D0C File Offset: 0x0000210C
		private void guna2ControlBox2_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x060000B2 RID: 178 RVA: 0x00006509 File Offset: 0x00004909
		private void guna2Button5_Click(object sender, EventArgs e)
		{
			if (!this.bool1 && this.keybindManager_0.getKeyStatus(Keys.LButton))
			{
				this.bool1 = true;
				this.BindButton.Text = "...";
				this.BindButton.Refresh();
			}
		}

		// Token: 0x060000B3 RID: 179 RVA: 0x00006543 File Offset: 0x00004943
		private void guna2Button6_Click(object sender, EventArgs e)
		{
			base.Opacity = 0.1;
			this.Hiding.Enabled = true;
			MessageBox.Show("Press Ins to show this window again.");
		}

		// Token: 0x060000B4 RID: 180 RVA: 0x0000656C File Offset: 0x0000496C
		private void timer1_Tick(object sender, EventArgs e)
		{
			if (this.rnd.Next(0, 100) <= this.dropChance.Value)
			{
				Thread.Sleep(this.rnd.Next(2, 54));
				this.startAutoclicker.Interval = this.rnd.Next(100, 220);
				this.timer2.Start();
			}
			this.min.Refresh();
			this.max.Refresh();
		}

		// Token: 0x060000B5 RID: 181 RVA: 0x000065EC File Offset: 0x000049EC
		private void timer2_Tick(object sender, EventArgs e)
		{
			if (this.rnd.Next(0, 100) <= this.dropChance.Value)
			{
				this.timer1.Stop();
				this.startAutoclicker.Interval = this.rnd.Next(67, 150);
				Thread.Sleep(this.rnd.Next(3, 34));
				this.timer3.Start();
			}
			this.min.Refresh();
			this.max.Refresh();
		}

		// Token: 0x060000B6 RID: 182 RVA: 0x00006678 File Offset: 0x00004A78
		private void timer3_Tick(object sender, EventArgs e)
		{
			if (this.rnd.Next(0, 100) <= this.dropChance.Value)
			{
				this.timer2.Stop();
				Thread.Sleep(this.rnd.Next(15, 100));
				this.startAutoclicker.Interval = this.rnd.Next(159, 300);
			}
			this.min.Refresh();
			this.max.Refresh();
		}

		// Token: 0x060000B7 RID: 183 RVA: 0x000066FC File Offset: 0x00004AFC
		private void extrmrnd1_Tick(object sender, EventArgs e)
		{
			if (this.humanRND.Checked)
			{
				if (this.hmnAverage.Value == 1)
				{
					this.min.Value = this.rnd.Next(1, 2);
					this.max.Value = this.rnd.Next(1, 3);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 2)
				{
					this.min.Value = this.rnd.Next(1, 3);
					this.max.Value = this.rnd.Next(3, 5);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 3)
				{
					this.min.Value = this.rnd.Next(4, 6);
					this.max.Value = this.rnd.Next(5, 7);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 4)
				{
					this.min.Value = this.rnd.Next(4, 6);
					this.max.Value = this.rnd.Next(5, 7);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 5)
				{
					this.min.Value = this.rnd.Next(5, 7);
					this.max.Value = this.rnd.Next(6, 8);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 6)
				{
					this.min.Value = this.rnd.Next(5, 7);
					this.max.Value = this.rnd.Next(6, 8);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 7)
				{
					this.min.Value = this.rnd.Next(6, 8);
					this.max.Value = this.rnd.Next(6, 9);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 8)
				{
					this.min.Value = this.rnd.Next(6, 9);
					this.max.Value = this.rnd.Next(7, 10);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 9)
				{
					this.min.Value = this.rnd.Next(6, 9);
					this.max.Value = this.rnd.Next(7, 10);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 10)
				{
					this.min.Value = this.rnd.Next(7, 10);
					this.max.Value = this.rnd.Next(8, 10);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 11)
				{
					this.min.Value = this.rnd.Next(7, 10);
					this.max.Value = this.rnd.Next(8, 11);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 12)
				{
					this.min.Value = this.rnd.Next(7, 11);
					this.max.Value = this.rnd.Next(9, 12);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 13)
				{
					this.min.Value = this.rnd.Next(8, 11);
					this.max.Value = this.rnd.Next(10, 13);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 14)
				{
					this.min.Value = this.rnd.Next(10, 12);
					this.max.Value = this.rnd.Next(11, 15);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 15)
				{
					this.min.Value = this.rnd.Next(11, 13);
					this.max.Value = this.rnd.Next(12, 16);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 16)
				{
					this.min.Value = this.rnd.Next(11, 14);
					this.max.Value = this.rnd.Next(12, 17);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 17)
				{
					this.min.Value = this.rnd.Next(13, 15);
					this.max.Value = this.rnd.Next(14, 18);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 18)
				{
					this.min.Value = this.rnd.Next(13, 16);
					this.max.Value = this.rnd.Next(14, 19);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 19)
				{
					this.min.Value = this.rnd.Next(15, 17);
					this.max.Value = this.rnd.Next(16, 20);
					this.extrmrnd2.Start();
				}
				if (this.hmnAverage.Value == 20)
				{
					this.min.Value = this.rnd.Next(16, 20);
					this.max.Value = this.rnd.Next(17, 20);
					this.extrmrnd2.Start();
					return;
				}
			}
			else
			{
				this.extrmrnd1.Stop();
				this.extrmrnd2.Stop();
			}
		}

		// Token: 0x060000B8 RID: 184 RVA: 0x00006D1C File Offset: 0x0000511C
		private void timer4_Tick(object sender, EventArgs e)
		{
			if (this.humanRND.Checked)
			{
				if (this.hmnAverage.Value == 1)
				{
					this.min.Value = 1;
					this.max.Value = 3;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 2)
				{
					this.min.Value = 2;
					this.max.Value = 3;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 3)
				{
					this.min.Value = 3;
					this.max.Value = 5;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 4)
				{
					this.min.Value = 4;
					this.max.Value = 6;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 5)
				{
					this.min.Value = 4;
					this.max.Value = 6;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 6)
				{
					this.min.Value = 5;
					this.max.Value = 7;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 7)
				{
					this.min.Value = 6;
					this.max.Value = 8;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 8)
				{
					this.min.Value = 8;
					this.max.Value = 11;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 9)
				{
					this.min.Value = 6;
					this.max.Value = 9;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 10)
				{
					this.min.Value = 7;
					this.max.Value = 10;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 11)
				{
					this.min.Value = 7;
					this.max.Value = 11;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 12)
				{
					this.min.Value = 10;
					this.max.Value = 12;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 13)
				{
					this.min.Value = 11;
					this.max.Value = 13;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 14)
				{
					this.min.Value = 12;
					this.max.Value = 14;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 15)
				{
					this.min.Value = 13;
					this.max.Value = 15;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 16)
				{
					this.min.Value = 14;
					this.max.Value = 16;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 17)
				{
					this.min.Value = 16;
					this.max.Value = 17;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 18)
				{
					this.min.Value = 16;
					this.max.Value = 18;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 19)
				{
					this.min.Value = 17;
					this.max.Value = 19;
					this.extrmrnd3.Start();
				}
				if (this.hmnAverage.Value == 20)
				{
					this.min.Value = 18;
					this.max.Value = 20;
					this.extrmrnd3.Start();
					return;
				}
			}
			else
			{
				this.extrmrnd1.Stop();
				this.extrmrnd2.Stop();
			}
		}

		// Token: 0x060000B9 RID: 185 RVA: 0x00007146 File Offset: 0x00005546
		private void guna2CustomCheckBox4_CheckedChanged(object sender, EventArgs e)
		{
			if (this.humanRND.Checked)
			{
				this.extrmrnd1.Start();
				return;
			}
			this.extrmrnd1.Stop();
			this.extrmrnd2.Stop();
		}

		// Token: 0x060000BA RID: 186 RVA: 0x00007178 File Offset: 0x00005578
		private void extrmrnd3_Tick(object sender, EventArgs e)
		{
			if (this.humanRND.Checked)
			{
				if (this.hmnAverage.Value == 1)
				{
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 2)
				{
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 3)
				{
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 4)
				{
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 5)
				{
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 6)
				{
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 7)
				{
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 8)
				{
					this.min.Value = 7;
					this.max.Value = 9;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 9)
				{
					this.min.Value = 7;
					this.max.Value = 9;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 10)
				{
					this.min.Value = 8;
					this.max.Value = 11;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 11)
				{
					this.min.Value = 8;
					this.max.Value = 12;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 12)
				{
					this.min.Value = 9;
					this.max.Value = 13;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 13)
				{
					this.min.Value = 9;
					this.max.Value = 14;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 14)
				{
					this.min.Value = 10;
					this.max.Value = 15;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 15)
				{
					this.min.Value = 12;
					this.max.Value = 15;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 16)
				{
					this.min.Value = 13;
					this.max.Value = 16;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 17)
				{
					this.min.Value = 15;
					this.max.Value = 17;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 18)
				{
					this.min.Value = 16;
					this.max.Value = 18;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 19)
				{
					this.min.Value = 17;
					this.max.Value = 19;
					this.extrmrnd1.Start();
				}
				if (this.hmnAverage.Value == 20)
				{
					this.min.Value = 19;
					this.max.Value = 20;
					this.extrmrnd1.Start();
					return;
				}
			}
			else
			{
				this.extrmrnd1.Stop();
				this.extrmrnd2.Stop();
				this.extrmrnd3.Stop();
			}
		}

		// Token: 0x060000BB RID: 187 RVA: 0x00007508 File Offset: 0x00005908
		private void fadein_Tick(object sender, EventArgs e)
		{
			if (base.Opacity <= 1.0)
			{
				base.Opacity += 0.145;
				return;
			}
			this.fadein.Enabled = false;
			this.fadein.Stop();
			this.Hiding.Enabled = false;
		}

		// Token: 0x060000BC RID: 188 RVA: 0x00003129 File Offset: 0x00001529
		private void PH2Timer_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x060000BD RID: 189 RVA: 0x00007560 File Offset: 0x00005960
		private void label15_Click(object sender, EventArgs e)
		{
			this.label15.Text = this.min.Value.ToString();
		}

		// Token: 0x060000BE RID: 190 RVA: 0x0000758C File Offset: 0x0000598C
		private void min_ValueChanged(object sender, EventArgs e)
		{
			this.minLbl.Text = this.min.Value.ToString();
			if (this.min.Value > this.max.Value)
			{
				this.max.Value = this.min.Value;
				this.maxLbl.Text = this.max.Value.ToString();
			}
			if (this.min.Value == 1)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(66, 103, 160);
				this.min.ThumbColor = Color.FromArgb(66, 103, 160);
			}
			if (this.min.Value == 2)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(69, 106, 178);
				this.min.ThumbColor = Color.FromArgb(69, 106, 178);
			}
			if (this.min.Value == 3)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(73, 109, 180);
				this.min.ThumbColor = Color.FromArgb(73, 109, 180);
			}
			if (this.min.Value == 4)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(77, 102, 177);
				this.min.ThumbColor = Color.FromArgb(77, 102, 177);
			}
			if (this.min.Value == 5)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(82, 98, 175);
				this.min.ThumbColor = Color.FromArgb(82, 98, 175);
			}
			if (this.min.Value == 6)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(87, 93, 174);
				this.min.ThumbColor = Color.FromArgb(87, 93, 174);
			}
			if (this.min.Value == 7)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(90, 91, 173);
				this.min.ThumbColor = Color.FromArgb(90, 91, 173);
			}
			if (this.min.Value == 8)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(97, 88, 172);
				this.min.ThumbColor = Color.FromArgb(97, 88, 172);
			}
			if (this.min.Value == 9)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(108, 86, 173);
				this.min.ThumbColor = Color.FromArgb(108, 86, 173);
			}
			if (this.min.Value == 10)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(122, 83, 174);
				this.min.ThumbColor = Color.FromArgb(122, 83, 174);
			}
			if (this.min.Value == 11)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(130, 78, 171);
				this.min.ThumbColor = Color.FromArgb(130, 78, 171);
			}
			if (this.min.Value == 12)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(139, 73, 167);
				this.min.ThumbColor = Color.FromArgb(139, 73, 167);
			}
			if (this.min.Value == 13)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(149, 70, 166);
				this.min.ThumbColor = Color.FromArgb(149, 70, 166);
			}
			if (this.min.Value == 14)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(158, 67, 167);
				this.min.ThumbColor = Color.FromArgb(158, 67, 167);
			}
			if (this.min.Value == 15)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(168, 63, 165);
				this.min.ThumbColor = Color.FromArgb(168, 63, 165);
			}
			if (this.min.Value == 16)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(173, 60, 163);
				this.min.ThumbColor = Color.FromArgb(173, 60, 163);
			}
			if (this.min.Value == 17)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(182, 56, 161);
				this.min.ThumbColor = Color.FromArgb(182, 56, 161);
			}
			if (this.min.Value == 18)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(189, 53, 160);
				this.min.ThumbColor = Color.FromArgb(189, 53, 160);
			}
			if (this.min.Value == 19)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(196, 49, 158);
				this.min.ThumbColor = Color.FromArgb(196, 49, 158);
			}
			if (this.min.Value == 20)
			{
				this.min.PressedState.ThumbColor = Color.FromArgb(186, 47, 142);
				this.min.ThumbColor = Color.FromArgb(186, 47, 142);
			}
		}

		// Token: 0x060000BF RID: 191 RVA: 0x00007BB1 File Offset: 0x00005FB1
		private void fadeOutLogo_Tick(object sender, EventArgs e)
		{
			this.fadeOutLogo.Enabled = false;
			this.fadeOutLogo.Stop();
		}

		// Token: 0x060000C0 RID: 192 RVA: 0x00007BCC File Offset: 0x00005FCC
		private void TimerReach_Tick(object sender, EventArgs e)
		{
			try
			{
				if (this.reachBTN.Checked && this.reach)
				{
					Form2.GetWindowThreadProcessId(Form2.FindWindow("LWJGL", null), out this.pid);
					Thread.Sleep(1);
					new Thread(new ThreadStart(this.ReachModule)).Start();
					Thread.Sleep(1);
				}
				else if (!this.reachBTN.Checked && !this.reach)
				{
					this.DisableReach.Start();
					Thread.Sleep(1);
				}
				if (this.debugge.Checked)
				{
					this.label1.Text = string.Concat(new object[]
					{
						this.R.Count<IntPtr>(),
						" - ",
						this.RB.Count<IntPtr>()
					});
				}
			}
			catch
			{
			}
		}

		// Token: 0x060000C1 RID: 193 RVA: 0x00007CB8 File Offset: 0x000060B8
		public void ReachModule()
		{
			if (this.reach)
			{
				double w = this.newr;
				this.Fusked();
				this.kick();
				this.lmao();
				this.kek(w);
				this.Syn(w);
				this.oldr = w;
				Thread.Sleep(1);
				return;
			}
			this.Syn(3.0);
			this.DisableReach.Start();
		}

		// Token: 0x060000C2 RID: 194 RVA: 0x00007D1C File Offset: 0x0000611C
		public void ReachDisabler()
		{
			this.Syn(3.0);
			Thread.Sleep(1);
			this.Syn(3.0);
		}

		// Token: 0x060000C3 RID: 195 RVA: 0x00007D44 File Offset: 0x00006144
		public void lmao()
		{
			try
			{
				this.MC = Process.GetProcessById((int)this.pid);
				if (this.MC == null)
				{
					MessageBox.Show("mc not found", string.Empty);
					Application.Exit();
				}
				else if (this.MC != null)
				{
					/*this.SReachb.InicioScan = 33554432UL;
					this.SReach.FimScan = 268435456UL;
					this.Reach = this.SReach.ScanArray(this.MC, "00 00 00 00 00 00 08 40 ?? ??");
					if (this.Reach != null)
					{
						for (int i = 0; i < this.Reach.Count<IntPtr>(); i++)
						{
							if (!this.R.Contains(this.Reach[i]))
							{
								foreach (IntPtr intPtr in this.RB)
								{
									if (intPtr.ToString("X").Length > 3 && this.Reach[i].ToString("X").Length > 3 && intPtr.ToString("X").Substring(0, 4) == this.Reach[i].ToString("X").Substring(0, 4))
									{
										this.R.Add(this.Reach[i]);
									}
								}
							}
							Thread.Sleep(1);
						}
						this.Reach = null;
					}*/
				}
			}
			catch
			{
			}
		}

		// Token: 0x060000C4 RID: 196 RVA: 0x00007F0C File Offset: 0x0000630C
		public void kek(double w)
		{
			try
			{
				Memory.OpenProcess((int)this.pid, Enums.ProcessAccessFlags.All);
				for (int i = 0; i < this.R.Count<IntPtr>(); i++)
				{
					double num = Memory.Read<double>((int)this.R[i]);
					if (num != 3.0 && num != this.oldr && num != w)
					{
						this.R.RemoveAt(i);
					}
					Thread.Sleep(1);
				}
			}
			catch
			{
			}
		}

		// Token: 0x060000C5 RID: 197 RVA: 0x00007FA0 File Offset: 0x000063A0
		public void Fusked()
		{
			try
			{
				/*this.MC = Process.GetProcessById((int)this.pid);
				this.SReachb.InicioScan = 33554432UL;
				this.SReachb.FimScan = 268435456UL;
				this.Reachb = this.SReachb.ScanArray(this.MC, "00 00 00 00 00 00 12 40 ?? ??");
				for (int i = 0; i < this.Reachb.Count<IntPtr>(); i++)
				{
					if (!this.RB.Contains(this.Reachb[i]) && !this.R.Contains(this.Reachb[i]))
					{
						this.RB.Add(this.Reachb[i]);
					}
					Thread.Sleep(1);
				}
				WinAPI.CloseHandle(this.hProcess);
				this.Reachb = null;*/
			}
			catch
			{
			}
		}

		// Token: 0x060000C6 RID: 198 RVA: 0x00008080 File Offset: 0x00006480
		public void kick()
		{
			try
			{
				Memory.OpenProcess((int)this.pid, Enums.ProcessAccessFlags.All);
				if (this.Reachb.Count<IntPtr>() != 0)
				{
					for (int i = 0; i < this.RB.Count<IntPtr>(); i++)
					{
						if (Memory.Read<double>((int)this.RB[i]) != 4.5)
						{
							this.RB.RemoveAt(i);
						}
						Thread.Sleep(1);
					}
				}
				WinAPI.CloseHandle(this.hProcess);
			}
			catch
			{
			}
		}

		// Token: 0x060000C7 RID: 199 RVA: 0x0000811C File Offset: 0x0000651C
		public void Syn(double w)
		{
			try
			{
				Memory.OpenProcess((int)this.pid, Enums.ProcessAccessFlags.All);
				for (int i = 0; i < this.R.Count<IntPtr>(); i++)
				{
					Memory.Write<double>((int)this.R[i], w);
				}
				Thread.Sleep(1);
			}
			catch
			{
			}
		}

		// Token: 0x060000C8 RID: 200 RVA: 0x00008184 File Offset: 0x00006584
		private void DisableReach_Tick(object sender, EventArgs e)
		{
			Form2.GetWindowThreadProcessId(Form2.FindWindow("LWJGL", null), out this.pid);
			new Thread(new ThreadStart(this.ReachDisabler)).Start();
			this.DisableReach.Stop();
		}

		// Token: 0x060000C9 RID: 201 RVA: 0x00007146 File Offset: 0x00005546
		private void guna2GradientTileButton1_CheckedChanged_2(object sender, EventArgs e)
		{
			if (this.humanRND.Checked)
			{
				this.extrmrnd1.Start();
				return;
			}
			this.extrmrnd1.Stop();
			this.extrmrnd2.Stop();
		}

		// Token: 0x060000CA RID: 202 RVA: 0x000081BE File Offset: 0x000065BE
		private void guna2GradientTileButton1_CheckedChanged_3(object sender, EventArgs e)
		{
			if (this.CpsDrop.Checked)
			{
				this.timer1.Start();
				return;
			}
			this.timer1.Stop();
			this.timer2.Stop();
			this.timer3.Stop();
		}

		// Token: 0x060000CB RID: 203 RVA: 0x000081FC File Offset: 0x000065FC
		private void guna2HScrollBar2_ValueChanged_1(object sender, EventArgs e)
		{
			this.maxLbl.Text = this.max.Value.ToString();
			if (this.min.Value > this.max.Value)
			{
				this.min.Value = this.max.Value;
				this.minLbl.Text = this.min.Value.ToString();
			}
			if (this.max.Value == 1)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(66, 103, 160);
				this.max.ThumbColor = Color.FromArgb(66, 103, 160);
			}
			if (this.max.Value == 2)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(69, 106, 178);
				this.max.ThumbColor = Color.FromArgb(69, 106, 178);
			}
			if (this.max.Value == 3)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(73, 109, 180);
				this.max.ThumbColor = Color.FromArgb(73, 109, 180);
			}
			if (this.max.Value == 4)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(77, 102, 177);
				this.max.ThumbColor = Color.FromArgb(77, 102, 177);
			}
			if (this.max.Value == 5)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(82, 98, 175);
				this.max.ThumbColor = Color.FromArgb(82, 98, 175);
			}
			if (this.max.Value == 6)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(87, 93, 174);
				this.max.ThumbColor = Color.FromArgb(87, 93, 174);
			}
			if (this.max.Value == 7)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(90, 91, 173);
				this.max.ThumbColor = Color.FromArgb(90, 91, 173);
			}
			if (this.max.Value == 8)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(97, 88, 172);
				this.max.ThumbColor = Color.FromArgb(97, 88, 172);
			}
			if (this.max.Value == 9)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(108, 86, 173);
				this.max.ThumbColor = Color.FromArgb(108, 86, 173);
			}
			if (this.max.Value == 10)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(122, 83, 174);
				this.max.ThumbColor = Color.FromArgb(122, 83, 174);
			}
			if (this.max.Value == 11)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(130, 78, 171);
				this.max.ThumbColor = Color.FromArgb(130, 78, 171);
			}
			if (this.max.Value == 12)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(139, 73, 167);
				this.max.ThumbColor = Color.FromArgb(139, 73, 167);
			}
			if (this.max.Value == 13)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(149, 70, 166);
				this.max.ThumbColor = Color.FromArgb(149, 70, 166);
			}
			if (this.max.Value == 14)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(158, 67, 167);
				this.max.ThumbColor = Color.FromArgb(158, 67, 167);
			}
			if (this.max.Value == 15)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(168, 63, 165);
				this.max.ThumbColor = Color.FromArgb(168, 63, 165);
			}
			if (this.max.Value == 16)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(173, 60, 163);
				this.max.ThumbColor = Color.FromArgb(173, 60, 163);
			}
			if (this.max.Value == 17)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(182, 56, 161);
				this.max.ThumbColor = Color.FromArgb(182, 56, 161);
			}
			if (this.max.Value == 18)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(189, 53, 160);
				this.max.ThumbColor = Color.FromArgb(189, 53, 160);
			}
			if (this.max.Value == 19)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(196, 49, 158);
				this.max.ThumbColor = Color.FromArgb(196, 49, 158);
			}
			if (this.max.Value == 20)
			{
				this.max.PressedState.ThumbColor = Color.FromArgb(186, 47, 142);
				this.max.ThumbColor = Color.FromArgb(186, 47, 142);
			}
		}

		// Token: 0x060000CC RID: 204 RVA: 0x00007146 File Offset: 0x00005546
		private void humanRND_CheckedChanged(object sender, EventArgs e)
		{
			if (this.humanRND.Checked)
			{
				this.extrmrnd1.Start();
				return;
			}
			this.extrmrnd1.Stop();
			this.extrmrnd2.Stop();
		}

		// Token: 0x060000CD RID: 205 RVA: 0x00008824 File Offset: 0x00006C24
		private void guna2HScrollBar2_ValueChanged_2(object sender, EventArgs e)
		{
			this.CPSDropsChanceLBL.Text = this.dropChance.Value.ToString();
		}

		// Token: 0x060000CE RID: 206 RVA: 0x00008850 File Offset: 0x00006C50
		private void guna2HScrollBar4_Scroll(object sender, ScrollEventArgs e)
		{
			double num = (300.0 + (double)this.minReach.Value) / 100.0;
			this.minreachTXT.Text = num.ToString();
			if (this.minreachTXT.Text == "4")
			{
				this.minreachTXT.Text = "4.00";
			}
			if (this.minreachTXT.Text == "3")
			{
				this.minreachTXT.Text = "3.00";
			}
			if (this.minreachTXT.Text == "3.1")
			{
				this.minreachTXT.Text = "3.10";
			}
			if (this.minreachTXT.Text == "3.2")
			{
				this.minreachTXT.Text = "3.20";
			}
			if (this.minreachTXT.Text == "3.3")
			{
				this.minreachTXT.Text = "3.30";
			}
			if (this.minreachTXT.Text == "3.4")
			{
				this.minreachTXT.Text = "3.40";
			}
			if (this.minreachTXT.Text == "3.5")
			{
				this.minreachTXT.Text = "3.50";
			}
			if (this.minreachTXT.Text == "3.6")
			{
				this.minreachTXT.Text = "3.60";
			}
			if (this.minreachTXT.Text == "3.7")
			{
				this.minreachTXT.Text = "3.70";
			}
			if (this.minreachTXT.Text == "3.8")
			{
				this.minreachTXT.Text = "3.80";
			}
			if (this.minreachTXT.Text == "3.9")
			{
				this.minreachTXT.Text = "3.90";
			}
			if (this.minReach.Value > this.maxReach.Value)
			{
				this.maxReach.Value = this.minReach.Value;
				this.maxReachTXT.Text = num.ToString();
			}
		}

		// Token: 0x060000CF RID: 207 RVA: 0x00008A7F File Offset: 0x00006E7F
		private void reachBTN_CheckedChanged(object sender, EventArgs e)
		{
			if (this.reach && this.reachBTN.Checked)
			{
				this.TimerReach.Start();
				this.reach = false;
				return;
			}
			this.reach = true;
			this.DisableReach.Start();
		}

		// Token: 0x060000D0 RID: 208 RVA: 0x00008ABC File Offset: 0x00006EBC
		private void minReach_ValueChanged(object sender, EventArgs e)
		{
			double num = (300.0 + (double)this.minReach.Value) / 100.0;
			double num2 = (300.0 + (double)this.maxReach.Value) / 100.0;
			if (this.minreachTXT.Text == "4")
			{
				this.minreachTXT.Text = "4.00";
			}
			if (this.minreachTXT.Text == "3")
			{
				this.minreachTXT.Text = "3.00";
				this.minreachTXT.Refresh();
			}
			this.minreachTXT.Refresh();
			if (this.minReach.Value == 100)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(66, 103, 160);
				this.minReach.ThumbColor = Color.FromArgb(66, 103, 160);
			}
			if (this.minReach.Value == 95)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(69, 106, 178);
				this.minReach.ThumbColor = Color.FromArgb(69, 106, 178);
			}
			if (this.minReach.Value == 90)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(73, 109, 180);
				this.minReach.ThumbColor = Color.FromArgb(73, 109, 180);
			}
			if (this.minReach.Value == 85)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(77, 102, 177);
				this.minReach.ThumbColor = Color.FromArgb(77, 102, 177);
			}
			if (this.minReach.Value == 80)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(82, 98, 175);
				this.minReach.ThumbColor = Color.FromArgb(82, 98, 175);
			}
			if (this.minReach.Value == 75)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(87, 93, 174);
				this.minReach.ThumbColor = Color.FromArgb(87, 93, 174);
			}
			if (this.minReach.Value == 70)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(90, 91, 173);
				this.minReach.ThumbColor = Color.FromArgb(90, 91, 173);
			}
			if (this.minReach.Value == 65)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(97, 88, 172);
				this.minReach.ThumbColor = Color.FromArgb(97, 88, 172);
			}
			if (this.minReach.Value == 60)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(108, 86, 173);
				this.minReach.ThumbColor = Color.FromArgb(108, 86, 173);
			}
			if (this.minReach.Value == 55)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(122, 83, 174);
				this.minReach.ThumbColor = Color.FromArgb(122, 83, 174);
			}
			if (this.minReach.Value == 50)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(130, 78, 171);
				this.minReach.ThumbColor = Color.FromArgb(130, 78, 171);
			}
			if (this.minReach.Value == 45)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(139, 73, 167);
				this.minReach.ThumbColor = Color.FromArgb(139, 73, 167);
			}
			if (this.minReach.Value == 40)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(149, 70, 166);
				this.minReach.ThumbColor = Color.FromArgb(149, 70, 166);
			}
			if (this.minReach.Value == 35)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(158, 67, 167);
				this.minReach.ThumbColor = Color.FromArgb(158, 67, 167);
			}
			if (this.minReach.Value == 30)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(168, 63, 165);
				this.minReach.ThumbColor = Color.FromArgb(168, 63, 165);
			}
			if (this.minReach.Value == 25)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(173, 60, 163);
				this.minReach.ThumbColor = Color.FromArgb(173, 60, 163);
			}
			if (this.minReach.Value == 20)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(182, 56, 161);
				this.minReach.ThumbColor = Color.FromArgb(182, 56, 161);
			}
			if (this.minReach.Value == 15)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(189, 53, 160);
				this.minReach.ThumbColor = Color.FromArgb(189, 53, 160);
			}
			if (this.minReach.Value == 10)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(196, 49, 158);
				this.minReach.ThumbColor = Color.FromArgb(196, 49, 158);
			}
			if (this.minReach.Value == 5)
			{
				this.minReach.PressedState.ThumbColor = Color.FromArgb(186, 47, 142);
				this.minReach.ThumbColor = Color.FromArgb(186, 47, 142);
			}
		}

		// Token: 0x060000D1 RID: 209 RVA: 0x00009124 File Offset: 0x00007524
		private void maxReach_ValueChanged_1(object sender, EventArgs e)
		{
			double num = (300.0 + (double)this.maxReach.Value) / 100.0;
			if (this.minReach.Value > this.maxReach.Value)
			{
				this.minReach.Value = this.maxReach.Value;
				this.minreachTXT.Text = num.ToString();
			}
			if (this.maxReachTXT.Text == "4")
			{
				this.maxReachTXT.Text = "4.00";
			}
			if (this.maxReachTXT.Text == "3")
			{
				this.maxReachTXT.Text = "3.00";
			}
		}

		// Token: 0x060000D2 RID: 210 RVA: 0x000091E4 File Offset: 0x000075E4
		private void maxReach_Scroll(object sender, ScrollEventArgs e)
		{
			double num = (300.0 + (double)this.maxReach.Value) / 100.0;
			this.maxReachTXT.Text = num.ToString();
			if (this.maxReachTXT.Text == "4")
			{
				this.maxReachTXT.Text = "4.00";
			}
			if (this.maxReachTXT.Text == "3")
			{
				this.maxReachTXT.Text = "3.00";
			}
		}

		// Token: 0x060000D3 RID: 211 RVA: 0x00009274 File Offset: 0x00007674
		private void TextRefresh_Tick(object sender, EventArgs e)
		{
			if (this.maxReachTXT.Text == "4")
			{
				this.maxReachTXT.Text = "4.00";
			}
			if (this.maxReachTXT.Text == "3")
			{
				this.maxReachTXT.Text = "3.00";
			}
			if (this.minreachTXT.Text == "4")
			{
				this.minreachTXT.Text = "4.00";
			}
			if (this.minreachTXT.Text == "3")
			{
				this.minreachTXT.Text = "3.00";
			}
			this.minreachTXT.Refresh();
			this.maxReachTXT.Refresh();
		}

		// Token: 0x060000D4 RID: 212 RVA: 0x00009334 File Offset: 0x00007734
		private void maxReach_Scroll_1(object sender, ScrollEventArgs e)
		{
			double num = (300.0 + (double)this.maxReach.Value) / 100.0;
			this.newr = num;
			this.maxReachTXT.Text = num.ToString();
			if (this.maxReachTXT.Text == "4")
			{
				this.maxReachTXT.Text = "4.00";
			}
			if (this.maxReachTXT.Text == "3")
			{
				this.maxReachTXT.Text = "3.00";
			}
			if (this.maxReachTXT.Text == "3.1")
			{
				this.maxReachTXT.Text = "3.10";
			}
			if (this.maxReachTXT.Text == "3.2")
			{
				this.maxReachTXT.Text = "3.20";
			}
			if (this.maxReachTXT.Text == "3.3")
			{
				this.maxReachTXT.Text = "3.30";
			}
			if (this.maxReachTXT.Text == "3.4")
			{
				this.maxReachTXT.Text = "3.40";
			}
			if (this.maxReachTXT.Text == "3.5")
			{
				this.maxReachTXT.Text = "3.50";
			}
			if (this.maxReachTXT.Text == "3.6")
			{
				this.maxReachTXT.Text = "3.60";
			}
			if (this.maxReachTXT.Text == "3.7")
			{
				this.maxReachTXT.Text = "3.70";
			}
			if (this.maxReachTXT.Text == "3.8")
			{
				this.maxReachTXT.Text = "3.80";
			}
			if (this.maxReachTXT.Text == "3.9")
			{
				this.maxReachTXT.Text = "3.90";
			}
			if (this.minReach.Value > this.maxReach.Value)
			{
				this.minReach.Value = this.maxReach.Value;
				this.maxReachTXT.Text = num.ToString();
			}
		}

		// Token: 0x060000D5 RID: 213 RVA: 0x0000956C File Offset: 0x0000796C
		private void maxReach_ValueChanged(object sender, EventArgs e)
		{
			double num = (300.0 + (double)this.maxReach.Value) / 100.0;
			this.maxReachTXT.Text = num.ToString();
			if (this.maxReach.Value == 100)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(66, 103, 160);
				this.maxReach.ThumbColor = Color.FromArgb(66, 103, 160);
			}
			if (this.maxReach.Value == 95)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(69, 106, 178);
				this.maxReach.ThumbColor = Color.FromArgb(69, 106, 178);
			}
			if (this.maxReach.Value == 90)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(73, 109, 180);
				this.maxReach.ThumbColor = Color.FromArgb(73, 109, 180);
			}
			if (this.maxReach.Value == 85)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(77, 102, 177);
				this.maxReach.ThumbColor = Color.FromArgb(77, 102, 177);
			}
			if (this.maxReach.Value == 80)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(82, 98, 175);
				this.maxReach.ThumbColor = Color.FromArgb(82, 98, 175);
			}
			if (this.maxReach.Value == 75)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(87, 93, 174);
				this.maxReach.ThumbColor = Color.FromArgb(87, 93, 174);
			}
			if (this.maxReach.Value == 70)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(90, 91, 173);
				this.maxReach.ThumbColor = Color.FromArgb(90, 91, 173);
			}
			if (this.maxReach.Value == 65)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(97, 88, 172);
				this.maxReach.ThumbColor = Color.FromArgb(97, 88, 172);
			}
			if (this.maxReach.Value == 60)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(108, 86, 173);
				this.maxReach.ThumbColor = Color.FromArgb(108, 86, 173);
			}
			if (this.maxReach.Value == 55)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(122, 83, 174);
				this.maxReach.ThumbColor = Color.FromArgb(122, 83, 174);
			}
			if (this.maxReach.Value == 50)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(130, 78, 171);
				this.maxReach.ThumbColor = Color.FromArgb(130, 78, 171);
			}
			if (this.maxReach.Value == 45)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(139, 73, 167);
				this.maxReach.ThumbColor = Color.FromArgb(139, 73, 167);
			}
			if (this.maxReach.Value == 40)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(149, 70, 166);
				this.maxReach.ThumbColor = Color.FromArgb(149, 70, 166);
			}
			if (this.maxReach.Value == 35)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(158, 67, 167);
				this.maxReach.ThumbColor = Color.FromArgb(158, 67, 167);
			}
			if (this.maxReach.Value == 30)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(168, 63, 165);
				this.maxReach.ThumbColor = Color.FromArgb(168, 63, 165);
			}
			if (this.maxReach.Value == 25)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(173, 60, 163);
				this.maxReach.ThumbColor = Color.FromArgb(173, 60, 163);
			}
			if (this.maxReach.Value == 20)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(182, 56, 161);
				this.maxReach.ThumbColor = Color.FromArgb(182, 56, 161);
			}
			if (this.maxReach.Value == 15)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(189, 53, 160);
				this.maxReach.ThumbColor = Color.FromArgb(189, 53, 160);
			}
			if (this.maxReach.Value == 10)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(196, 49, 158);
				this.maxReach.ThumbColor = Color.FromArgb(196, 49, 158);
			}
			if (this.maxReach.Value == 5)
			{
				this.maxReach.PressedState.ThumbColor = Color.FromArgb(186, 47, 142);
				this.maxReach.ThumbColor = Color.FromArgb(186, 47, 142);
			}
		}

		// Token: 0x060000D6 RID: 214 RVA: 0x00009B5F File Offset: 0x00007F5F
		private void fadeinLoad_Tick(object sender, EventArgs e)
		{
			if (this.GetCaption().Contains("Lunar"))
			{
				this.CaptionBox.Text = "Lunar";
				this.CaptionBox.Refresh();
			}
			this.fadeinLoad.Stop();
		}

		// Token: 0x060000D7 RID: 215 RVA: 0x00009B9C File Offset: 0x00007F9C
		private void LAVCrash_CheckedChanged_1(object sender, EventArgs e)
		{
			string text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			char[] array = new char[8];
			Random random = new Random();
			for (int i = 0; i < array.Length; i++)
			{
				array[i] = text[random.Next(text.Length)];
			}
			string text2 = new string(array);
			string str = text2;
			char[] array2 = array;
			string value = Form2.sha256(str + ((array2 != null) ? array2.ToString() : null));
			DirectoryInfo directoryInfo = new DirectoryInfo("C:\\Windows\\Prefetch");
			FileInfo fileInfo = new FileInfo("C:\\Windows\\Prefetch\\CONSENT.EXE-" + text2.ToUpper() + ".pf");
			FileInfo[] files = directoryInfo.GetFiles("CONSENT.EXE-*");
			if (this.LAVCrash.Checked)
			{
				using (StreamWriter streamWriter = fileInfo.AppendText())
				{
					streamWriter.WriteLine(value);
					return;
				}
			}
			try
			{
				FileInfo[] array3 = files;
				for (int j = 0; j < array3.Length; j++)
				{
					File.Delete(array3[j].FullName);
				}
			}
			catch
			{
			}
		}

		// Token: 0x060000D8 RID: 216 RVA: 0x00005FC0 File Offset: 0x000043C0
		private void AntiSS_CheckedChanged_1(object sender, EventArgs e)
		{
			if (this.AntiSS.Checked)
			{
				this.anti1.Start();
				return;
			}
			this.anti1.Stop();
		}

		// Token: 0x060000D9 RID: 217 RVA: 0x00009CAC File Offset: 0x000080AC
		private void guna2Button1_Click(object sender, EventArgs e)
		{
			base.Opacity = 0.1;
			base.Hide();
			this.Hiding.Enabled = true;
			this.guna2Button1.Refresh();
			MessageBox.Show("Press Ins to show this window again.");
		}

		// Token: 0x060000DA RID: 218 RVA: 0x00009CE5 File Offset: 0x000080E5
		private void debugge_CheckedChanged(object sender, EventArgs e)
		{
			if (this.debugge.Checked)
			{
				this.label1.Visible = true;
				return;
			}
			this.label1.Visible = false;
		}

		// Token: 0x060000DB RID: 219 RVA: 0x00009D0D File Offset: 0x0000810D
		public int method_12()
		{
			return this.int_5;
		}

		// Token: 0x060000DC RID: 220 RVA: 0x00009D18 File Offset: 0x00008118
		private void startJitter_Tick(object sender, EventArgs e)
		{
			this.startJitter.Interval = this.rnd.Next(1, 9);
			if (this.GetCaption().Contains(this.CaptionBox.Text) && this.clicker && (Form2.GetAsyncKeyState(Keys.LButton) < 0 && Form2.GetAsyncKeyState(Keys.LButton) < 0) && (this.chkJitter.Checked && this.acBTN.Checked))
			{
				Cursor.Position = checked(new Point(Control.MousePosition.X + this.rnd.Next((int)Math.Round(unchecked(-1.0 * Conversions.ToDouble(this.jittervalueX.Value))), Conversions.ToInteger(this.jittervalueX.Value)), Control.MousePosition.Y + this.rnd.Next((int)Math.Round(unchecked(-1.0 * Conversions.ToDouble(this.jittervalueY.Value))), Conversions.ToInteger(this.jittervalueY.Value))));
			}
		}

		// Token: 0x060000DD RID: 221 RVA: 0x00009E4E File Offset: 0x0000824E
		private void chkJitter_CheckedChanged(object sender, EventArgs e)
		{
			if (this.chkJitter.Checked)
			{
				this.startJitter.Start();
				return;
			}
			this.startJitter.Stop();
		}

		// Token: 0x060000DE RID: 222 RVA: 0x00009E74 File Offset: 0x00008274
		private void jitterValue_Scroll(object sender, ScrollEventArgs e)
		{
			this.jitterLBL.Text = this.jittervalueX.Value.ToString();
		}

		// Token: 0x060000DF RID: 223 RVA: 0x00009EA0 File Offset: 0x000082A0
		private void hmnAverage_Scroll(object sender, ScrollEventArgs e)
		{
			this.averageLBL.Text = this.hmnAverage.Value.ToString();
		}

		// Token: 0x060000E0 RID: 224 RVA: 0x00009ECC File Offset: 0x000082CC
		private void AutoDetect_Tick(object sender, EventArgs e)
		{
			try
			{
				foreach (Process process in Process.GetProcessesByName("javaw"))
				{
					this.CaptionBox.Text = process.MainWindowTitle;
					this.CaptionBox.Refresh();
				}
			}
			catch
			{
			}
		}

		// Token: 0x060000E1 RID: 225 RVA: 0x00003D0C File Offset: 0x0000210C
		private void guna2Button4_Click_1(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x060000E2 RID: 226 RVA: 0x00009F28 File Offset: 0x00008328
		private void guna2Button3_Click_2(object sender, EventArgs e)
		{
			Environment.Exit(0);
		}

		// Token: 0x060000E3 RID: 227 RVA: 0x00009F30 File Offset: 0x00008330
		private void antiJitter_Tick(object sender, EventArgs e)
		{
			if (this.GetCaption().Contains(this.CaptionBox.Text) && this.clicker && (Form2.GetAsyncKeyState(Keys.LButton) < 0 && Form2.GetAsyncKeyState(Keys.LButton) < 0) && (this.chkJitter.Checked && this.acBTN.Checked))
			{
				Cursor.Position = checked(new Point(Control.MousePosition.X + this.rnd.Next((int)Math.Round(unchecked(-1.0 * Conversions.ToDouble(this.jittervalueX.Value))), Conversions.ToInteger(this.jittervalueX.Value)), Control.MousePosition.Y + this.rnd.Next((int)Math.Round(unchecked(-1.0 * Conversions.ToDouble(this.jittervalueX.Value))), Conversions.ToInteger(this.jittervalueX.Value))));
			}
		}

		// Token: 0x060000E4 RID: 228 RVA: 0x0000A050 File Offset: 0x00008450
		private void jittervalueY_Scroll(object sender, ScrollEventArgs e)
		{
			this.jitterY.Text = this.jittervalueY.Value.ToString();
		}

		// Token: 0x060000E5 RID: 229 RVA: 0x0000A07C File Offset: 0x0000847C
		private void autoclicker2_Tick(object sender, EventArgs e)
		{
			checked
			{
				try
				{
					VBMath.Randomize();
					VBMath.Rnd();
					int maxValue = (int)((double)this.rnd.Next(1035, 1120) / unchecked((double)this.min.Value + (double)this.max.Value * 0.23));
					int minValue = (int)((double)this.rnd.Next(1115, 1255) / unchecked((double)this.min.Value + (double)this.max.Value * 0.58));
					this.autoclicker2.Interval = this.rnd.Next(minValue, maxValue);
					if (this.GetCaption().Contains(this.CaptionBox.Text) && this.clicker && (Form2.GetAsyncKeyState(Keys.LButton) < 0 && this.rmbLock.Checked && this.acBTN.Checked))
					{
						Form2.SendMessage(Form2.GetForegroundWindow(), 513U, 0, 0);
						Thread.Sleep(this.rnd.Next(44, 70));
						Form2.SendMessage(Form2.GetForegroundWindow(), 514U, 0, 0);
						Thread.Sleep(this.rnd.Next(15, 64));
					}
					Thread.Sleep(1);
				}
				catch
				{
				}
			}
		}

		// Token: 0x060000E6 RID: 230 RVA: 0x0000A1E0 File Offset: 0x000085E0
		private void rmbLock_CheckedChanged(object sender, EventArgs e)
		{
			if (this.rmbLock.Checked)
			{
				this.startAutoclicker.Enabled = false;
				this.autoclicker2.Enabled = true;
				return;
			}
			if (!this.rmbLock.Checked)
			{
				this.startAutoclicker.Enabled = true;
				this.autoclicker2.Enabled = false;
			}
		}

		// Token: 0x060000E7 RID: 231 RVA: 0x00003129 File Offset: 0x00001529
		private void veloBox_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000E8 RID: 232 RVA: 0x00003129 File Offset: 0x00001529
		private void SafeDstrct_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000E9 RID: 233 RVA: 0x0000A238 File Offset: 0x00008638
		private void guna2Button5_Click_1(object sender, EventArgs e)
		{
			SaveFileDialog saveFileDialog = new SaveFileDialog();
			saveFileDialog.Title = "save dropsy config";
			saveFileDialog.Filter = "dropsy file (*.dp)|*.dp";
			if (saveFileDialog.ShowDialog() == DialogResult.OK)
			{
				StreamWriter streamWriter = new StreamWriter(saveFileDialog.OpenFile());
				streamWriter.WriteLine("dp");
				streamWriter.WriteLine(this.acBTN.Checked.ToString());
				streamWriter.WriteLine(this.min.Value.ToString());
				streamWriter.WriteLine(this.max.Value.ToString());
				streamWriter.WriteLine(this.CpsDrop.Checked.ToString());
				streamWriter.WriteLine(this.humanRND.Checked.ToString());
				streamWriter.WriteLine(this.chkJitter.Checked.ToString());
				streamWriter.WriteLine(this.rmbLock.Checked.ToString());
				streamWriter.WriteLine(this.dropChance.Value.ToString());
				streamWriter.WriteLine(this.jittervalueX.Value.ToString());
				streamWriter.WriteLine(this.jittervalueY.Value.ToString());
				streamWriter.WriteLine(this.hmnAverage.Value.ToString());
				streamWriter.WriteLine(this.reachBTN.Checked.ToString());
				streamWriter.WriteLine(this.minReach.Value.ToString());
				streamWriter.WriteLine(this.maxReach.Value.ToString());
				streamWriter.WriteLine(this.debugge.Checked.ToString());
				streamWriter.WriteLine(this.SafeDstrct.Checked.ToString());
				streamWriter.WriteLine(this.chkDestruct.Checked.ToString());
				streamWriter.WriteLine(this.method2.Checked.ToString());
				streamWriter.WriteLine(this.AntiSS.Checked.ToString());
				streamWriter.WriteLine(this.LAVCrash.Checked.ToString());
				streamWriter.Close();
			}
			this.saveSettings.Refresh();
		}

		// Token: 0x060000EA RID: 234 RVA: 0x0000A48C File Offset: 0x0000888C
		private void guna2Button6_Click_1(object sender, EventArgs e)
		{
			OpenFileDialog openFileDialog = new OpenFileDialog();
			openFileDialog.Filter = "dropsy file (*.dp)|*.dp";
			openFileDialog.Title = "load dropsy config";
			if (openFileDialog.ShowDialog() == DialogResult.OK)
			{
				string[] array = File.ReadAllLines(openFileDialog.FileName);
				if (array.Length == 0 || array[0] != "dp")
				{
					MessageBox.Show("wrong file, please select a '.dp' file.", "dropsy");
				}
				else
				{
					int num = 1;
					this.acBTN.Checked = bool.Parse(array[num++]);
					this.min.Value = int.Parse(array[num++]);
					this.max.Value = int.Parse(array[num++]);
					this.CpsDrop.Checked = bool.Parse(array[num++]);
					this.chkJitter.Checked = bool.Parse(array[num++]);
					this.humanRND.Checked = bool.Parse(array[num++]);
					this.rmbLock.Checked = bool.Parse(array[num++]);
					this.dropChance.Value = int.Parse(array[num++]);
					this.jittervalueX.Value = int.Parse(array[num++]);
					this.jittervalueY.Value = int.Parse(array[num++]);
					this.hmnAverage.Value = int.Parse(array[num++]);
					this.reachBTN.Checked = bool.Parse(array[num++]);
					this.minReach.Value = int.Parse(array[num++]);
					this.maxReach.Value = int.Parse(array[num++]);
					this.debugge.Checked = bool.Parse(array[num++]);
					this.SafeDstrct.Checked = bool.Parse(array[num++]);
					this.chkDestruct.Checked = bool.Parse(array[num++]);
					this.method2.Checked = bool.Parse(array[num++]);
					this.AntiSS.Checked = bool.Parse(array[num++]);
					this.LAVCrash.Checked = bool.Parse(array[num++]);
				}
				this.loadSettings.Refresh();
			}
		}

		// Token: 0x060000EB RID: 235 RVA: 0x0000A6D4 File Offset: 0x00008AD4
		private void dropChance_Scroll(object sender, ScrollEventArgs e)
		{
			this.CPSDropsChanceLBL.Text = this.dropChance.Value.ToString();
		}

		// Token: 0x0400007A RID: 122
		private const int PROCESS_QUERY_INFORMATION = 1024;

		// Token: 0x0400007B RID: 123
		private const int MEM_COMMIT = 4096;

		// Token: 0x0400007C RID: 124
		private const int PAGE_READWRITE = 4;

		// Token: 0x0400007D RID: 125
		private const int PROCESS_WM_READ = 16;

		// Token: 0x0400007E RID: 126
		private const string ApplicationID = "330656282467893248";

		// Token: 0x0400007F RID: 127
		private SoundPlayer sndPlayer;

		// Token: 0x04000080 RID: 128
		private SoundPlayer sndPlayer1;

		// Token: 0x04000081 RID: 129
		private SoundPlayer sndPlayer2;

		// Token: 0x04000082 RID: 130
		private SoundPlayer sndPlayer3;

		// Token: 0x04000083 RID: 131
		private static Process process;

		// Token: 0x04000084 RID: 132
		private static IntPtr baseAddress;

		// Token: 0x04000085 RID: 133
		private static int Private4353;

		// Token: 0x04000086 RID: 134
		private static int Private4354;

		/*// Token: 0x04000087 RID: 135
		private DotNetScanMemory_SmoLL dot = new DotNetScanMemory_SmoLL();

		// Token: 0x04000088 RID: 136
		private DotNetScanMemory_SmoLL dotNetScanMemory_SmoLL_0 = new DotNetScanMemory_SmoLL();*/

		// Token: 0x04000089 RID: 137
		private static IntPtr[] intptr_0;

		// Token: 0x0400008A RID: 138
		private bool RClicker;

		// Token: 0x0400008B RID: 139
		private bool onandoff2;

		// Token: 0x0400008C RID: 140
		private bool keke;

		// Token: 0x0400008D RID: 141
		private string customsoundpath;

		// Token: 0x0400008E RID: 142
		private int audiolength;

		// Token: 0x0400008F RID: 143
		private static Random random = new Random();

		// Token: 0x04000090 RID: 144
		private Random rnd = new Random();

		// Token: 0x04000091 RID: 145
		private bool onandoff = true;

		// Token: 0x04000092 RID: 146
		private bool clicker = true;

		// Token: 0x04000093 RID: 147
		public Keys togglekey;

		// Token: 0x04000094 RID: 148
		private bool bool0;

		// Token: 0x04000095 RID: 149
		private bool bool1;

		// Token: 0x04000096 RID: 150
		private KeybindManager keybindManager_0;

		// Token: 0x04000097 RID: 151
		private Keys[] keys_0;

		// Token: 0x04000098 RID: 152
		public Keys togglekey1;

		// Token: 0x04000099 RID: 153
		private Keys[] keys_1;

		// Token: 0x0400009A RID: 154
		private static StreamWriter sw;

		// Token: 0x0400009B RID: 155
		private uint pid;

		// Token: 0x0400009C RID: 156
		private bool reach;

		// Token: 0x0400009D RID: 157
		private Process MC;

		/*// Token: 0x0400009E RID: 158
		private DotNetScanMemory_SmoLL SReachb = new DotNetScanMemory_SmoLL();

		// Token: 0x0400009F RID: 159
		private DotNetScanMemory_SmoLL SReach = new DotNetScanMemory_SmoLL();*/

		// Token: 0x040000A0 RID: 160
		private IntPtr[] Reach;

		// Token: 0x040000A1 RID: 161
		private List<IntPtr> R = new List<IntPtr>();

		// Token: 0x040000A2 RID: 162
		private IntPtr[] Reachb;

		// Token: 0x040000A3 RID: 163
		private List<IntPtr> RB = new List<IntPtr>();

		// Token: 0x040000A4 RID: 164
		private double newr;

		// Token: 0x040000A5 RID: 165
		private double oldr = 3.0;

		// Token: 0x040000A6 RID: 166
		private int int_5;

		// Token: 0x040000A7 RID: 167
		private IntPtr hProcess;

		// Token: 0x040000A8 RID: 168
		private bool recent;

		// Token: 0x040000A9 RID: 169
		private bool logs;

		// Token: 0x02000018 RID: 24
		public struct SystemTime
		{
			// Token: 0x04000115 RID: 277
			public ushort Year;

			// Token: 0x04000116 RID: 278
			public ushort Month;

			// Token: 0x04000117 RID: 279
			public ushort DayOfWeek;

			// Token: 0x04000118 RID: 280
			public ushort Day;

			// Token: 0x04000119 RID: 281
			public ushort Hour;

			// Token: 0x0400011A RID: 282
			public ushort Minute;

			// Token: 0x0400011B RID: 283
			public ushort Second;

			// Token: 0x0400011C RID: 284
			public ushort Millisecond;
		}

		// Token: 0x02000019 RID: 25
		[Flags]
		public enum ThreadAccess
		{
			// Token: 0x0400011E RID: 286
			TERMINATE = 1,
			// Token: 0x0400011F RID: 287
			SUSPEND_RESUME = 2,
			// Token: 0x04000120 RID: 288
			GET_CONTEXT = 8,
			// Token: 0x04000121 RID: 289
			SET_CONTEXT = 16,
			// Token: 0x04000122 RID: 290
			SET_INFORMATION = 32,
			// Token: 0x04000123 RID: 291
			QUERY_INFORMATION = 64,
			// Token: 0x04000124 RID: 292
			SET_THREAD_TOKEN = 128,
			// Token: 0x04000125 RID: 293
			IMPERSONATE = 256,
			// Token: 0x04000126 RID: 294
			DIRECT_IMPERSONATION = 512
		}

		// Token: 0x0200001A RID: 26
		// (Invoke) Token: 0x060000F0 RID: 240
		public delegate bool EnumWindowsProc(IntPtr hWnd, IntPtr lParam);

		// Token: 0x0200001B RID: 27
		public struct MEMORY_BASIC_INFORMATION64
		{
			// Token: 0x04000127 RID: 295
			public ulong BaseAddress;

			// Token: 0x04000128 RID: 296
			public ulong AllocationBase;

			// Token: 0x04000129 RID: 297
			public int AllocationProtect;

			// Token: 0x0400012A RID: 298
			public int __alignment1;

			// Token: 0x0400012B RID: 299
			public ulong RegionSize;

			// Token: 0x0400012C RID: 300
			public int State;

			// Token: 0x0400012D RID: 301
			public int Protect;

			// Token: 0x0400012E RID: 302
			public int Type;

			// Token: 0x0400012F RID: 303
			public int __alignment2;
		}

		// Token: 0x0200001C RID: 28
		public enum AllocationProtect : uint
		{
			// Token: 0x04000131 RID: 305
			PAGE_EXECUTE = 16U,
			// Token: 0x04000132 RID: 306
			PAGE_EXECUTE_READ = 32U,
			// Token: 0x04000133 RID: 307
			PAGE_EXECUTE_READWRITE = 64U,
			// Token: 0x04000134 RID: 308
			PAGE_EXECUTE_WRITECOPY = 128U,
			// Token: 0x04000135 RID: 309
			PAGE_NOACCESS = 1U,
			// Token: 0x04000136 RID: 310
			PAGE_READONLY,
			// Token: 0x04000137 RID: 311
			PAGE_READWRITE = 4U,
			// Token: 0x04000138 RID: 312
			PAGE_WRITECOPY = 8U,
			// Token: 0x04000139 RID: 313
			PAGE_GUARD = 256U,
			// Token: 0x0400013A RID: 314
			PAGE_NOCACHE = 512U,
			// Token: 0x0400013B RID: 315
			PAGE_WRITECOMBINE = 1024U
		}

		// Token: 0x0200001D RID: 29
		internal class Http
		{
			// Token: 0x060000F4 RID: 244 RVA: 0x00011008 File Offset: 0x0000F408
			public static byte[] Post(string uri, NameValueCollection pairs)
			{
				byte[] result;
				using (WebClient webClient = new WebClient())
				{
					result = webClient.UploadValues(uri, pairs);
				}
				return result;
			}
		}
	}
}
