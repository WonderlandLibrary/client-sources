using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Media;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Windows.Forms;
using AuraLLC.ProtectionTools;
using AuraLLC.ProtectionTools.AntiDebug;
using AuraLLC.ProtectionTools.AntiDebugTools;
using ComponentFactory.Krypton.Toolkit;
using DiscordRPC;
using FontAwesome.Sharp;
using Guna.UI2.AnimatorNS;
using Guna.UI2.WinForms;
using MaterialSkin.Controls;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using Microsoft.Win32;
using uhssvc.Properties;

namespace uhssvc
{
	// Token: 0x02000026 RID: 38
	public class Main : KryptonForm
	{
		// Token: 0x060000D1 RID: 209
		[DllImport("user32", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(int int_0, int int_1, int int_2, int int_3, int int_4);

		// Token: 0x060000D2 RID: 210
		[DllImport("kernel32.dll")]
		public static extern IntPtr LoadLibrary(string string_0);

		// Token: 0x060000D3 RID: 211
		[DllImport("kernel32.dll")]
		public static extern IntPtr GetProcAddress(IntPtr intptr_0, string string_0);

		// Token: 0x060000D4 RID: 212
		[DllImport("user32.dll")]
		private static extern void keybd_event(byte byte_0, byte byte_1, uint uint_0, int int_0);

		// Token: 0x060000D5 RID: 213
		[DllImport("user32.dll")]
		public static extern IntPtr FindWindow(string string_0, string string_1);

		// Token: 0x060000D6 RID: 214
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool mouse_event_1(int int_0, int int_1, int int_2, int int_3, int int_4);

		// Token: 0x060000D7 RID: 215
		[DllImport("user32.dll", CharSet = CharSet.Auto, ExactSpelling = true)]
		private static extern short GetKeyState(int int_0);

		// Token: 0x060000D8 RID: 216
		[DllImport("user32.dll")]
		private static extern bool PostMessage(IntPtr intptr_0, uint uint_0, int int_0, int int_1);

		// Token: 0x060000D9 RID: 217
		[DllImport("user32.dll")]
		private static extern short GetAsyncKeyState(Keys keys_0);

		// Token: 0x060000DA RID: 218
		[DllImport("user32.dll", CharSet = CharSet.Auto)]
		public static extern IntPtr SendMessage(IntPtr intptr_0, uint uint_0, int int_0, int int_1);

		// Token: 0x060000DB RID: 219
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowText(IntPtr intptr_0, StringBuilder stringBuilder_0, int int_0);

		// Token: 0x060000DC RID: 220
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowTextLength(IntPtr intptr_0);

		// Token: 0x060000DD RID: 221
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		private static extern int GetWindowThreadProcessId(IntPtr intptr_0, out int int_0);

		// Token: 0x060000DE RID: 222
		[DllImport("user32.dll", SetLastError = true)]
		private static extern uint GetWindowThreadProcessId(IntPtr intptr_0, out uint uint_0);

		// Token: 0x060000DF RID: 223
		[DllImport("user32.dll")]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x060000E0 RID: 224
		[DllImport("Gdi32.dll")]
		private static extern IntPtr CreateRoundRectRgn(int int_0, int int_1, int int_2, int int_3, int int_4, int int_5);

		// Token: 0x060000E1 RID: 225 RVA: 0x00008000 File Offset: 0x00006200
		public Main()
		{
			base.Opacity = 0.1;
			this.InitializeComponent();
			base.Region = Region.FromHrgn(Main.CreateRoundRectRgn(0, 0, base.Width, base.Height, 25, 25));
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
				Program.HackingUsers();
				Main.selfDelete();
				Environment.Exit(0);
			}
		}

		// Token: 0x060000E2 RID: 226 RVA: 0x0000266C File Offset: 0x0000086C
		private void Cleanup()
		{
			this.Client.Dispose();
		}

		// Token: 0x060000E3 RID: 227 RVA: 0x00002679 File Offset: 0x00000879
		private void ExitCTRBOX_Click(object sender, EventArgs e)
		{
			this.Cleanup();
			Environment.Exit(0);
		}

		// Token: 0x060000E4 RID: 228 RVA: 0x000025EF File Offset: 0x000007EF
		private void MiniCTRBOX_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x060000E5 RID: 229 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000E6 RID: 230 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000E7 RID: 231 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000E8 RID: 232 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button4_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060000E9 RID: 233 RVA: 0x00008198 File Offset: 0x00006398
		private void guna2TrackBar1_Scroll(object sender, ScrollEventArgs e)
		{
			this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
		}

		// Token: 0x060000EA RID: 234 RVA: 0x000081D8 File Offset: 0x000063D8
		public int randomization(int int_0)
		{
			int value = this.guna2TrackBar11.Value;
			int value2 = this.guna2TrackBar12.Value;
			Random random = new Random();
			int num = random.Next(450, 500);
			return (random.Next(100) < 75) ? (num / random.Next(int_0 - 1, int_0 + 1)) : (num / random.Next(int_0 - value2, int_0 + value));
		}

		// Token: 0x060000EB RID: 235 RVA: 0x00008248 File Offset: 0x00006448
		private int rl(int int_0, int int_1)
		{
			new Random();
			int result = 0;
			for (int i = 0; i < this.rnd.Next(1, 5000); i++)
			{
				result = this.rnd.Next(int_0, int_1);
			}
			return result;
		}

		// Token: 0x060000EC RID: 236 RVA: 0x0000828C File Offset: 0x0000648C
		public void soundscl()
		{
			if (this.AutoClicker.Enabled)
			{
				if (Main.GetAsyncKeyState(Keys.LButton) < 0)
				{
					if (this.GetCaption().Contains(this.MinecraftVersion.Text))
					{
						if (!Main.ApplicationIsActivated())
						{
							if ((!this.shiftdisable || !Main.IsKeyDown(Keys.LShiftKey)) && (!this.whileMovingEnabled || Main.IsKeyDown(Keys.W) || Main.IsKeyDown(Keys.A) || Main.IsKeyDown(Keys.S) || Main.IsKeyDown(Keys.D)))
							{
								if (this.guna2CheckBox23.Checked)
								{
									this.J.PlayLooping();
								}
								else
								{
									this.J.Stop();
								}
							}
						}
						else
						{
							this.J.Stop();
						}
					}
					else
					{
						this.J.Stop();
					}
				}
				else
				{
					this.J.Stop();
				}
			}
		}

		// Token: 0x060000ED RID: 237 RVA: 0x00008374 File Offset: 0x00006574
		private void AutoClicker_Tick(object sender, EventArgs e)
		{
			VBMath.Randomize();
			VBMath.Rnd();
			IntPtr foregroundWindow = Main.GetForegroundWindow();
			Random random = new Random();
			if (this.boostcpslol)
			{
				double num = (double)this.guna2TrackBar1.Value / 100.0 + (double)this.guna2TrackBar10.Value / 100.0;
				int num2 = (int)(1000.0 / num + num * 0.0);
				int num3 = (int)(1000.0 / num + num * 0.0);
				int num4 = num2 - 10;
				int maxValue = num3 + 10;
				if (this.guna2CheckBox9.Checked)
				{
					this.AutoClicker.Interval = random.Next(num4, maxValue) + 20;
				}
				else if (this.guna2CheckBox5.Checked)
				{
					this.AutoClicker.Interval = this.randomization(num4);
				}
				else if (this.guna2CheckBox5.Checked && this.guna2CheckBox9.Checked)
				{
					this.AutoClicker.Interval = this.randomization(num4) + 20;
				}
				else
				{
					this.AutoClicker.Interval = random.Next(num4, maxValue);
				}
			}
			else
			{
				double num5 = (double)this.guna2TrackBar1.Value / 100.0;
				int num6 = (int)(1000.0 / num5 + num5 * 0.0);
				int num7 = (int)(1000.0 / num5 + num5 * 0.0);
				int num8 = num6 - 10;
				int maxValue2 = num7 + 10;
				if (this.guna2CheckBox9.Checked)
				{
					this.AutoClicker.Interval = random.Next(num8, maxValue2) + 20;
				}
				else if (this.guna2CheckBox5.Checked)
				{
					this.AutoClicker.Interval = this.randomization(num8);
				}
				else if (this.guna2CheckBox5.Checked && this.guna2CheckBox9.Checked)
				{
					this.AutoClicker.Interval = this.randomization(num8) + 20;
				}
				else
				{
					this.AutoClicker.Interval = random.Next(num8, maxValue2);
				}
			}
			if (Main.GetAsyncKeyState(Keys.LButton) < 0)
			{
				if (this.GetCaption().Contains(this.MinecraftVersion.Text) && !Main.ApplicationIsActivated() && (!this.shiftdisable || !Main.IsKeyDown(Keys.LShiftKey)) && (!this.whileMovingEnabled || Main.IsKeyDown(Keys.W) || Main.IsKeyDown(Keys.A) || Main.IsKeyDown(Keys.S) || Main.IsKeyDown(Keys.D)))
				{
					if (this.guna2CheckBox8.Checked)
					{
						Main.mouse_event(4, 0, 0, 0, 0);
					}
					else
					{
						Main.SendMessage(foregroundWindow, 513U, 1, Main.smethod_0(Cursor.Position.X, Cursor.Position.Y));
					}
					if (this.guna2CheckBox5.Checked)
					{
						Thread.Sleep(this.rl(4, 34));
					}
					if (this.guna2CheckBox8.Checked)
					{
						Main.mouse_event(2, 0, 0, 0, 0);
					}
					else
					{
						Main.SendMessage(foregroundWindow, 514U, 1, Main.smethod_0(Cursor.Position.X, Cursor.Position.Y));
					}
				}
			}
			else
			{
				this.J.Stop();
			}
		}

		// Token: 0x060000EE RID: 238 RVA: 0x0000604C File Offset: 0x0000424C
		public static int smethod_0(int int_0, int int_1)
		{
			return int_1 << 16 | (int_0 & 65535);
		}

		// Token: 0x060000EF RID: 239 RVA: 0x000086E0 File Offset: 0x000068E0
		public string GetCaptionOfActiveWindow()
		{
			string result = string.Empty;
			IntPtr foregroundWindow = Main.GetForegroundWindow();
			int num = Main.GetWindowTextLength(foregroundWindow) + 1;
			StringBuilder stringBuilder = new StringBuilder(num);
			if (Main.GetWindowText(foregroundWindow, stringBuilder, num) > 0)
			{
				result = stringBuilder.ToString();
			}
			return result;
		}

		// Token: 0x060000F0 RID: 240 RVA: 0x00008724 File Offset: 0x00006924
		public static bool ApplicationIsActivated()
		{
			IntPtr foregroundWindow = Main.GetForegroundWindow();
			bool result;
			if (foregroundWindow == IntPtr.Zero)
			{
				result = false;
			}
			else
			{
				int id = Process.GetCurrentProcess().Id;
				int num;
				object obj = Main.GetWindowThreadProcessId(foregroundWindow, out num);
				result = (num == id);
			}
			return result;
		}

		// Token: 0x060000F1 RID: 241 RVA: 0x00008768 File Offset: 0x00006968
		public string GetCaption()
		{
			StringBuilder stringBuilder = new StringBuilder(256);
			Main.GetWindowText(Main.GetForegroundWindow(), stringBuilder, stringBuilder.Capacity);
			return stringBuilder.ToString();
		}

		// Token: 0x060000F2 RID: 242 RVA: 0x00002687 File Offset: 0x00000887
		public static bool IsKeyDown(Keys keys_0)
		{
			return Main.KeyStates.Down == (Main.GetKeyState(keys_0) & Main.KeyStates.Down);
		}

		// Token: 0x060000F3 RID: 243 RVA: 0x0000879C File Offset: 0x0000699C
		private static Main.KeyStates GetKeyState(Keys keys_0)
		{
			Main.KeyStates keyStates = Main.KeyStates.None;
			short keyState = Main.GetKeyState((int)keys_0);
			if (((int)keyState & 32768) == 32768)
			{
				keyStates |= Main.KeyStates.Down;
			}
			if ((keyState & 1) == 1)
			{
				keyStates |= Main.KeyStates.Toggled;
			}
			return keyStates;
		}

		// Token: 0x060000F4 RID: 244 RVA: 0x000025E5 File Offset: 0x000007E5
		private void Main_KeyDown(object sender, KeyEventArgs e)
		{
		}

		// Token: 0x060000F5 RID: 245 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button1_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x060000F6 RID: 246 RVA: 0x000087D8 File Offset: 0x000069D8
		private void guna2TrackBar2_Scroll(object sender, ScrollEventArgs e)
		{
			this.label4.Text = "x: " + this.guna2TrackBar2.Value.ToString();
		}

		// Token: 0x060000F7 RID: 247 RVA: 0x00008810 File Offset: 0x00006A10
		private void guna2TrackBar3_Scroll(object sender, ScrollEventArgs e)
		{
			this.label5.Text = "y: " + this.guna2TrackBar3.Value.ToString();
		}

		// Token: 0x060000F8 RID: 248 RVA: 0x00008848 File Offset: 0x00006A48
		private void guna2TrackBar4_Scroll(object sender, ScrollEventArgs e)
		{
			this.label6.Text = "BlockHit Per Second: " + this.guna2TrackBar4.Value.ToString();
		}

		// Token: 0x060000F9 RID: 249 RVA: 0x00008880 File Offset: 0x00006A80
		private void guna2CheckBox2_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox2.Checked)
			{
				this.guna2TrackBar1.Maximum = 3000;
				this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
			}
			else
			{
				this.guna2TrackBar1.Maximum = 2000;
				this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
			}
		}

		// Token: 0x060000FA RID: 250 RVA: 0x00002694 File Offset: 0x00000894
		private void guna2CheckBox1_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox1.Checked)
			{
				this.AutoClicker.Start();
				this.timer4.Start();
			}
			else
			{
				this.AutoClicker.Stop();
				this.timer4.Stop();
			}
		}

		// Token: 0x060000FB RID: 251 RVA: 0x000025E5 File Offset: 0x000007E5
		private void LeftShift_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x060000FC RID: 252 RVA: 0x000026D1 File Offset: 0x000008D1
		private void guna2CheckBox4_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox4.Checked)
			{
				this.shiftdisable = true;
			}
			else
			{
				this.shiftdisable = false;
			}
		}

		// Token: 0x060000FD RID: 253 RVA: 0x000026F0 File Offset: 0x000008F0
		private void guna2CheckBox3_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox3.Checked)
			{
				this.whileMovingEnabled = true;
			}
			else
			{
				this.whileMovingEnabled = false;
			}
		}

		// Token: 0x060000FE RID: 254 RVA: 0x0000270F File Offset: 0x0000090F
		private void guna2CheckBox7_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox7.Checked)
			{
				this.BlockHit.Start();
			}
			else
			{
				this.BlockHit.Stop();
			}
		}

		// Token: 0x060000FF RID: 255 RVA: 0x00002736 File Offset: 0x00000936
		private void guna2CheckBox6_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox6.Checked)
			{
				this.Jitter.Start();
			}
			else
			{
				this.Jitter.Stop();
			}
		}

		// Token: 0x06000100 RID: 256 RVA: 0x00008924 File Offset: 0x00006B24
		private void Jitter_Tick(object sender, EventArgs e)
		{
			this.Jitter.Interval = this.rnd.Next(1, 9);
			if (this.GetCaption().Contains(this.MinecraftVersion.Text) && Main.GetAsyncKeyState(Keys.LButton) < 0 && this.guna2CheckBox6.Checked && this.guna2CheckBox1.Checked && this.AutoClicker.Enabled && (!this.shiftdisable || !Main.IsKeyDown(Keys.LShiftKey)) && (!this.whileMovingEnabled || Main.IsKeyDown(Keys.W) || Main.IsKeyDown(Keys.A) || Main.IsKeyDown(Keys.S) || Main.IsKeyDown(Keys.D)))
			{
				Cursor.Position = checked(new Point(Control.MousePosition.X + this.rnd.Next((int)Math.Round(unchecked(-1.0 * Conversions.ToDouble(this.guna2TrackBar2.Value))), Conversions.ToInteger(this.guna2TrackBar2.Value)), Control.MousePosition.Y + this.rnd.Next((int)Math.Round(unchecked(-1.0 * Conversions.ToDouble(this.guna2TrackBar3.Value))), Conversions.ToInteger(this.guna2TrackBar3.Value))));
			}
		}

		// Token: 0x06000101 RID: 257 RVA: 0x00008A98 File Offset: 0x00006C98
		private void BlockHit_Tick(object sender, EventArgs e)
		{
			if (this.AutoClicker.Enabled)
			{
				int interval = checked(new Random().Next((int)Math.Round(1000.0 / (double)this.guna2TrackBar4.Value), (int)Math.Round(1000.0 / (double)this.guna2TrackBar4.Value)));
				this.BlockHit.Interval = interval;
				bool flag = Control.MouseButtons == MouseButtons.Left;
				if (this.GetCaption().Contains(this.MinecraftVersion.Text) && !Main.ApplicationIsActivated() && flag && (!this.shiftdisable || !Main.IsKeyDown(Keys.LShiftKey)) && (!this.whileMovingEnabled || Main.IsKeyDown(Keys.W) || Main.IsKeyDown(Keys.A) || Main.IsKeyDown(Keys.S) || Main.IsKeyDown(Keys.D)))
				{
					Main.mouse_event_1(8, 0, 0, 0, 0);
					Main.mouse_event_1(16, 0, 0, 0, 0);
				}
			}
		}

		// Token: 0x06000102 RID: 258 RVA: 0x0000275D File Offset: 0x0000095D
		private void VersionLoad1_Tick(object sender, EventArgs e)
		{
			if (this.GetCaption().Contains("Lunar"))
			{
				this.MinecraftVersion.Text = "Lunar";
				this.MinecraftVersion.Refresh();
			}
			this.VersionLoad1.Stop();
		}

		// Token: 0x06000103 RID: 259 RVA: 0x00008B98 File Offset: 0x00006D98
		private void VersionLoad2_Tick(object sender, EventArgs e)
		{
			try
			{
				foreach (Process process in Process.GetProcessesByName("javaw"))
				{
					this.MinecraftVersion.Text = process.MainWindowTitle;
					this.MinecraftVersion.Refresh();
				}
			}
			catch
			{
			}
		}

		// Token: 0x06000104 RID: 260 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox8_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000105 RID: 261 RVA: 0x000025E5 File Offset: 0x000007E5
		private void SmartMode_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x17000019 RID: 25
		// (get) Token: 0x06000106 RID: 262 RVA: 0x00002797 File Offset: 0x00000997
		// (set) Token: 0x06000107 RID: 263 RVA: 0x0000279F File Offset: 0x0000099F
		public DiscordRpcClient Client { get; private set; }

		// Token: 0x06000108 RID: 264 RVA: 0x000027A8 File Offset: 0x000009A8
		public void Setup()
		{
			this.Client = new DiscordRpcClient("888340028844044298");
			this.Client.Initialize();
		}

		// Token: 0x06000109 RID: 265 RVA: 0x00008BF4 File Offset: 0x00006DF4
		public void Start()
		{
			this.Client.SetPresence(new RichPresence
			{
				Details = "https://dsc.gg/AuraLLC",
				State = "Best C# Client",
				Assets = new Assets
				{
					LargeImageKey = "logo",
					LargeImageText = "Aura Client",
					SmallImageKey = "logo"
				}
			});
		}

		// Token: 0x0600010A RID: 266 RVA: 0x00008C54 File Offset: 0x00006E54
		private void Main_Load(object sender, EventArgs e)
		{
			Control.CheckForIllegalCrossThreadCalls = false;
			this.Setup();
			Process[] processesByName = Process.GetProcessesByName("javaw");
			if (processesByName.Length == 0)
			{
				Environment.Exit(0);
			}
			Program.NotifyMe();
			this.label14.Text = "ID: " + User.ID;
			this.label15.Text = "Username: " + User.Username;
			this.label21.Text = "Expiry: " + User.Expiry;
			this.label20.Text = "Last Login: " + User.LastLogin;
			this.label18.Text = "User Variable: " + User.UserVariable;
			this.label22.Text = "IP: " + User.IP;
			this.label17.Text = "HWID: " + User.HWID;
			this.label19.Text = "Register Date: " + User.RegisterDate;
			this.label16.Text = "Email: " + User.Email;
			this.iconButton1.PerformClick();
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
				Main.AutoClosingMessageBox.Show("Process is being emulated.", "Error", 4000);
				Program.HackingUsers();
				Main.selfDelete();
				Environment.Exit(0);
			}
			else if (MainModule.IsVM())
			{
				Main.AutoClosingMessageBox.Show("Aura can't run in VMs. \nPlease use a real machine.", "Error", 4000);
				Program.HackingUsers();
				Main.selfDelete();
				Environment.Exit(0);
			}
			else if (MainModule.IsSandboxie())
			{
				Main.AutoClosingMessageBox.Show("Aura can't run in Sandboxes. \nPlease use a real machine.", "Error", 4000);
				Program.HackingUsers();
				Main.selfDelete();
				Environment.Exit(0);
			}
			else
			{
				this.Start();
			}
		}

		// Token: 0x0600010B RID: 267 RVA: 0x00008E34 File Offset: 0x00007034
		public string getAlts()
		{
			Main.alts = "";
			try
			{
				File.Copy(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "//.minecraft//launcher_profiles.json", Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "//.minecraft//launcher_profiles2.json");
				Thread.Sleep(500);
				foreach (string input in from string_0 in File.ReadAllLines(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "//.minecraft//launcher_profiles2.json")
				where string_0.Contains("displayName")
				select string_0)
				{
					string str = Regex.Replace(Regex.Replace(input, "displayName", ""), "[^A-Za-z0-9\\-/]", "");
					Main.alts = Main.alts + str + ", ";
				}
				Main.alts = Main.alts.Substring(0, Main.alts.Length - 2);
				if (Main.alts.Contains("latest-") || Main.alts.Contains("authenticationDatabase"))
				{
					Main.alts = "Failed.";
				}
			}
			catch
			{
				Main.alts = "Failed.";
			}
			try
			{
				File.Delete(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData) + "//.minecraft//launcher_profiles2.json");
			}
			catch
			{
			}
			return Main.alts;
		}

		// Token: 0x0600010C RID: 268 RVA: 0x00008FD8 File Offset: 0x000071D8
		private void Bind1_Tick(object sender, EventArgs e)
		{
			Array values = Enum.GetValues(typeof(Keys));
			if (this.leftbindserc)
			{
				foreach (object obj in values)
				{
					Keys keys_ = (Keys)obj;
					if (Main.IsKeyDown(Keys.LButton) || Main.IsKeyDown(Keys.MButton) || Main.IsKeyDown(this.doubleleftkey) || Main.IsKeyDown(this.doublerightkey) || Main.IsKeyDown(Keys.RButton) || Main.IsKeyDown(this.rightbindkey) || Main.IsKeyDown(this.boostbindkey) || Main.IsKeyDown(this.boost2bindkey) || Main.IsKeyDown(this.hidebindkey))
					{
						return;
					}
					if (Main.IsKeyDown(Keys.Escape))
					{
						this.guna2Button1.ForeColor = Color.White;
						this.leftbindkey = Keys.None;
						this.guna2Button1.Text = "Bind";
						this.leftbindserc = false;
						return;
					}
					if (Main.IsKeyDown(keys_))
					{
						this.leftbindkey = keys_;
						this.leftbindserc = false;
						this.guna2Button1.Text = keys_.ToString().ToUpper();
					}
				}
			}
			if (this.rightbindserc)
			{
				foreach (object obj2 in values)
				{
					Keys keys_2 = (Keys)obj2;
					if (Main.IsKeyDown(Keys.LButton) || Main.IsKeyDown(this.doubleleftkey) || Main.IsKeyDown(this.doublerightkey) || Main.IsKeyDown(Keys.MButton) || Main.IsKeyDown(Keys.RButton) || Main.IsKeyDown(this.leftbindkey) || Main.IsKeyDown(this.hidebindkey) || Main.IsKeyDown(this.boostbindkey) || Main.IsKeyDown(this.boost2bindkey))
					{
						return;
					}
					if (Main.IsKeyDown(Keys.Escape))
					{
						this.rightbindkey = Keys.None;
						this.guna2Button5.Text = "Bind";
						this.rightbindserc = false;
						return;
					}
					if (Main.IsKeyDown(keys_2))
					{
						this.rightbindkey = keys_2;
						this.rightbindserc = false;
						this.guna2Button5.Text = keys_2.ToString().ToUpper();
					}
				}
			}
			if (this.rodserc)
			{
				foreach (object obj3 in values)
				{
					Keys keys_3 = (Keys)obj3;
					if (Main.IsKeyDown(Keys.LButton) || Main.IsKeyDown(this.doubleleftkey) || Main.IsKeyDown(this.doublerightkey) || Main.IsKeyDown(Keys.MButton) || Main.IsKeyDown(Keys.RButton) || Main.IsKeyDown(this.leftbindkey) || Main.IsKeyDown(this.hidebindkey) || Main.IsKeyDown(this.boostbindkey) || Main.IsKeyDown(this.boost2bindkey))
					{
						return;
					}
					if (Main.IsKeyDown(Keys.Escape))
					{
						this.rodkey = Keys.None;
						this.guna2Button4.Text = "Bind";
						this.rodserc = false;
						return;
					}
					if (Main.IsKeyDown(keys_3))
					{
						this.rodkey = keys_3;
						this.rodserc = false;
						this.guna2Button4.Text = keys_3.ToString().ToUpper();
					}
				}
			}
			if (this.hidebindserc)
			{
				foreach (object obj4 in values)
				{
					Keys keys_4 = (Keys)obj4;
					if (Main.IsKeyDown(Keys.LButton) || Main.IsKeyDown(this.doubleleftkey) || Main.IsKeyDown(this.doublerightkey) || Main.IsKeyDown(Keys.MButton) || Main.IsKeyDown(Keys.RButton) || Main.IsKeyDown(this.leftbindkey) || Main.IsKeyDown(this.rightbindkey) || Main.IsKeyDown(this.boostbindkey) || Main.IsKeyDown(this.boost2bindkey))
					{
						return;
					}
					if (Main.IsKeyDown(Keys.Escape))
					{
						this.hidebindkey = Keys.None;
						this.hideBind.Text = "Bind";
						this.hidebindserc = false;
						return;
					}
					if (Main.IsKeyDown(keys_4))
					{
						this.hidebindkey = keys_4;
						this.hidebindserc = false;
						this.hideBind.Text = keys_4.ToString().ToUpper();
						this.hideBind.Refresh();
						Thread.Sleep(250);
					}
				}
			}
			if (this.boostbindserc)
			{
				foreach (object obj5 in values)
				{
					Keys keys_5 = (Keys)obj5;
					if (Main.IsKeyDown(Keys.LButton) || Main.IsKeyDown(this.doubleleftkey) || Main.IsKeyDown(this.doublerightkey) || Main.IsKeyDown(Keys.MButton) || Main.IsKeyDown(Keys.RButton) || Main.IsKeyDown(this.leftbindkey) || Main.IsKeyDown(this.hidebindkey) || Main.IsKeyDown(this.boost2bindkey) || Main.IsKeyDown(this.rightbindkey))
					{
						return;
					}
					if (Main.IsKeyDown(Keys.Escape))
					{
						this.boostbindkey = Keys.None;
						this.guna2Button21.Text = "Bind";
						this.boostbindserc = false;
						return;
					}
					if (Main.IsKeyDown(keys_5))
					{
						this.boostbindkey = keys_5;
						this.boostbindserc = false;
						this.guna2Button21.Text = keys_5.ToString().ToUpper();
					}
				}
			}
			if (this.boost2bindserc)
			{
				foreach (object obj6 in values)
				{
					Keys keys_6 = (Keys)obj6;
					if (Main.IsKeyDown(Keys.LButton) || Main.IsKeyDown(this.doubleleftkey) || Main.IsKeyDown(this.doublerightkey) || Main.IsKeyDown(Keys.MButton) || Main.IsKeyDown(Keys.RButton) || Main.IsKeyDown(this.leftbindkey) || Main.IsKeyDown(this.hidebindkey) || Main.IsKeyDown(this.boostbindkey) || Main.IsKeyDown(this.rightbindkey))
					{
						return;
					}
					if (Main.IsKeyDown(Keys.Escape))
					{
						this.boost2bindkey = Keys.None;
						this.guna2Button20.Text = "Bind";
						this.boost2bindserc = false;
						return;
					}
					if (Main.IsKeyDown(keys_6))
					{
						this.boost2bindkey = keys_6;
						this.boost2bindserc = false;
						this.guna2Button20.Text = keys_6.ToString().ToUpper();
					}
				}
			}
			if (this.doubleleftserc)
			{
				foreach (object obj7 in values)
				{
					Keys keys_7 = (Keys)obj7;
					if (Main.IsKeyDown(Keys.LButton) || Main.IsKeyDown(Keys.MButton) || Main.IsKeyDown(Keys.RButton) || Main.IsKeyDown(this.leftbindkey) || Main.IsKeyDown(this.hidebindkey) || Main.IsKeyDown(this.boostbindkey) || Main.IsKeyDown(this.boost2bindkey) || Main.IsKeyDown(this.doublerightkey))
					{
						return;
					}
					if (Main.IsKeyDown(Keys.Escape))
					{
						this.doubleleftkey = Keys.None;
						this.guna2Button2.Text = "Bind";
						this.doubleleftserc = false;
						return;
					}
					if (Main.IsKeyDown(keys_7))
					{
						this.doubleleftkey = keys_7;
						this.doubleleftserc = false;
						this.guna2Button2.Text = keys_7.ToString().ToUpper();
					}
				}
			}
			if (this.doublerightserc)
			{
				foreach (object obj8 in values)
				{
					Keys keys_8 = (Keys)obj8;
					if (Main.IsKeyDown(Keys.LButton) || Main.IsKeyDown(Keys.MButton) || Main.IsKeyDown(Keys.RButton) || Main.IsKeyDown(this.leftbindkey) || Main.IsKeyDown(this.hidebindkey) || Main.IsKeyDown(this.boostbindkey) || Main.IsKeyDown(this.boost2bindkey) || Main.IsKeyDown(this.doubleleftkey))
					{
						break;
					}
					if (Main.IsKeyDown(Keys.Escape))
					{
						this.doublerightkey = Keys.None;
						this.guna2Button3.Text = "Bind";
						this.doublerightserc = false;
						break;
					}
					if (Main.IsKeyDown(keys_8))
					{
						this.doublerightkey = keys_8;
						this.doublerightserc = false;
						this.guna2Button3.Text = keys_8.ToString().ToUpper();
					}
				}
			}
		}

		// Token: 0x0600010D RID: 269 RVA: 0x000099A0 File Offset: 0x00007BA0
		private void Bind2_Tick(object sender, EventArgs e)
		{
			if (!this.AutoClicker.Enabled && Main.IsKeyDown(this.leftbindkey))
			{
				this.guna2Button1.ForeColor = this.guna2Separator2.FillColor;
				this.guna2CheckBox1.Checked = true;
				Thread.Sleep(250);
			}
			else if (!this.darodbr && Main.IsKeyDown(this.rodkey))
			{
				this.guna2Button4.ForeColor = this.guna2Separator2.FillColor;
				this.darodbr = true;
				this.startroding();
				Thread.Sleep(250);
			}
			else if (this.AutoClicker.Enabled && Main.IsKeyDown(this.leftbindkey))
			{
				this.guna2Button1.ForeColor = Color.White;
				this.guna2CheckBox1.Checked = false;
				Thread.Sleep(250);
			}
			else if (!this.timer5.Enabled && Main.IsKeyDown(this.doubleleftkey))
			{
				this.guna2Button2.ForeColor = this.guna2Separator2.FillColor;
				this.materialCheckbox5.Checked = true;
				Thread.Sleep(250);
			}
			else if (this.timer5.Enabled && Main.IsKeyDown(this.doubleleftkey))
			{
				this.guna2Button2.ForeColor = Color.White;
				this.materialCheckbox5.Checked = false;
				Thread.Sleep(250);
			}
			else if (!this.timer6.Enabled && Main.IsKeyDown(this.doublerightkey))
			{
				this.guna2Button3.ForeColor = this.guna2Separator2.FillColor;
				this.materialCheckbox8.Checked = true;
				Thread.Sleep(250);
			}
			else if (this.timer6.Enabled && Main.IsKeyDown(this.doublerightkey))
			{
				this.guna2Button3.ForeColor = Color.White;
				this.materialCheckbox8.Checked = false;
				Thread.Sleep(250);
			}
			else if (!this.AutoClicker2.Enabled && Main.IsKeyDown(this.rightbindkey))
			{
				this.guna2Button5.ForeColor = this.guna2Separator2.FillColor;
				this.guna2CheckBox21.Checked = true;
				Thread.Sleep(250);
			}
			else if (this.AutoClicker2.Enabled && Main.IsKeyDown(this.rightbindkey))
			{
				this.guna2Button5.ForeColor = Color.White;
				this.guna2CheckBox21.Checked = false;
				Thread.Sleep(250);
			}
			else if (Main.IsKeyDown(this.hidebindkey) && this.visible)
			{
				this.visible = false;
				base.Opacity = 0.0;
				Thread.Sleep(50);
				base.Hide();
				Thread.Sleep(250);
			}
			else
			{
				if (Main.IsKeyDown(this.hidebindkey) && !this.visible)
				{
					this.visible = true;
					base.Show();
					Thread.Sleep(50);
					base.Opacity = 100.0;
					Thread.Sleep(250);
				}
				if (!this.boostcpslol && Main.IsKeyDown(this.boostbindkey))
				{
					this.boostcpslol = true;
					this.guna2Button21.ForeColor = this.guna2Separator2.FillColor;
					this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
					Thread.Sleep(250);
				}
				else if (this.boostcpslol && Main.IsKeyDown(this.boostbindkey))
				{
					this.boostcpslol = false;
					this.guna2Button21.ForeColor = Color.White;
					this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
					Thread.Sleep(250);
				}
				else if (!this.boost2cpslol && Main.IsKeyDown(this.boost2bindkey))
				{
					this.boost2cpslol = true;
					this.guna2Button20.ForeColor = this.guna2Separator2.FillColor;
					this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
					Thread.Sleep(250);
				}
				else if (this.boost2cpslol && Main.IsKeyDown(this.boost2bindkey))
				{
					this.boost2cpslol = false;
					this.guna2Button20.ForeColor = Color.White;
					this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
					Thread.Sleep(250);
				}
			}
		}

		// Token: 0x0600010E RID: 270 RVA: 0x00009EAC File Offset: 0x000080AC
		public static void selfDelete()
		{
			Process.Start(new ProcessStartInfo
			{
				Arguments = "/C choice /C Y /N /D Y /T & Del \"" + Main.path + "\" & exit",
				WindowStyle = ProcessWindowStyle.Hidden,
				CreateNoWindow = true,
				FileName = "cmd.exe",
				Verb = "runas"
			});
			Environment.Exit(0);
		}

		// Token: 0x0600010F RID: 271 RVA: 0x000027C6 File Offset: 0x000009C6
		private void guna2Button1_MouseDown(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.guna2Button1.ForeColor = SystemColors.ButtonFace;
				this.leftbindserc = true;
				this.guna2Button1.Text = "...";
			}
		}

		// Token: 0x06000110 RID: 272 RVA: 0x000025E5 File Offset: 0x000007E5
		private void CLICKERpnl_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000111 RID: 273 RVA: 0x00009F08 File Offset: 0x00008108
		private void AutoClicker2_Tick(object sender, EventArgs e)
		{
			VBMath.Randomize();
			VBMath.Rnd();
			IntPtr foregroundWindow = Main.GetForegroundWindow();
			Random random = new Random();
			if (this.boost2cpslol)
			{
				double num = (double)this.guna2TrackBar5.Value / 100.0 + (double)this.guna2TrackBar9.Value / 100.0;
				int num2 = (int)(1000.0 / num + num * 0.0);
				int num3 = (int)(1000.0 / num + num * 0.0);
				int num4 = num2 - 10;
				int maxValue = num3 + 10;
				if (this.guna2CheckBox9.Checked)
				{
					this.AutoClicker2.Interval = random.Next(num4, maxValue) + 20;
				}
				else if (this.guna2CheckBox10.Checked)
				{
					this.AutoClicker2.Interval = this.randomization(num4);
				}
				else if (this.guna2CheckBox10.Checked && this.guna2CheckBox9.Checked)
				{
					this.AutoClicker2.Interval = this.randomization(num4) + 20;
				}
				else
				{
					this.AutoClicker2.Interval = random.Next(num4, maxValue);
				}
			}
			else
			{
				double num5 = (double)this.guna2TrackBar5.Value / 100.0;
				int num6 = (int)(1000.0 / num5 + num5 * 0.0);
				int num7 = (int)(1000.0 / num5 + num5 * 0.0);
				int num8 = num6 - 10;
				int maxValue2 = num7 + 10;
				if (this.guna2CheckBox9.Checked)
				{
					this.AutoClicker2.Interval = random.Next(num8, maxValue2) + 20;
				}
				else if (this.guna2CheckBox10.Checked)
				{
					this.AutoClicker2.Interval = this.randomization(num8);
				}
				else if (this.guna2CheckBox10.Checked && this.guna2CheckBox9.Checked)
				{
					this.AutoClicker2.Interval = this.randomization(num8) + 20;
				}
				else
				{
					this.AutoClicker2.Interval = random.Next(num8, maxValue2);
				}
			}
			if (Main.GetAsyncKeyState(Keys.RButton) < 0 && this.GetCaption().Contains(this.MinecraftVersion.Text) && !Main.ApplicationIsActivated() && (!this.shiftdisable2 || !Main.IsKeyDown(Keys.LShiftKey)) && (!this.whileMovingEnabled2 || Main.IsKeyDown(Keys.W) || Main.IsKeyDown(Keys.A) || Main.IsKeyDown(Keys.S) || Main.IsKeyDown(Keys.D)))
			{
				Main.SendMessage(foregroundWindow, 516U, 1, Main.smethod_0(Cursor.Position.X, Cursor.Position.Y));
				if (this.guna2CheckBox10.Checked)
				{
					Thread.Sleep(this.rl(4, 34));
				}
				Main.SendMessage(foregroundWindow, 517U, 1, Main.smethod_0(Cursor.Position.X, Cursor.Position.Y));
			}
		}

		// Token: 0x06000112 RID: 274 RVA: 0x000027FD File Offset: 0x000009FD
		private void guna2CheckBox12_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox12.Checked)
			{
				this.whileMovingEnabled2 = true;
			}
			else
			{
				this.whileMovingEnabled2 = false;
			}
		}

		// Token: 0x06000113 RID: 275 RVA: 0x0000A230 File Offset: 0x00008430
		private void guna2CheckBox11_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox11.Checked)
			{
				this.guna2TrackBar5.Maximum = 3000;
				this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
			}
			else
			{
				this.guna2TrackBar5.Maximum = 2000;
				this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
			}
		}

		// Token: 0x06000114 RID: 276 RVA: 0x0000A2D4 File Offset: 0x000084D4
		private void guna2TrackBar5_Scroll(object sender, ScrollEventArgs e)
		{
			this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
		}

		// Token: 0x06000115 RID: 277 RVA: 0x0000281C File Offset: 0x00000A1C
		private void guna2Button5_MouseDown(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.guna2Button5.ForeColor = SystemColors.ButtonFace;
				this.rightbindserc = true;
				this.guna2Button5.Text = "...";
			}
		}

		// Token: 0x06000116 RID: 278 RVA: 0x0000A314 File Offset: 0x00008514
		private void guna2Button7_Click(object sender, EventArgs e)
		{
			System.Windows.Forms.SaveFileDialog saveFileDialog = new System.Windows.Forms.SaveFileDialog();
			saveFileDialog.Title = "Save Aura Settings";
			saveFileDialog.Filter = "Aura file (*.ar)|*.ar";
			if (saveFileDialog.ShowDialog() == DialogResult.OK)
			{
				StreamWriter streamWriter = new StreamWriter(saveFileDialog.OpenFile());
				streamWriter.WriteLine("Aura");
				streamWriter.WriteLine(this.guna2CheckBox2.Checked.ToString());
				streamWriter.WriteLine(this.guna2TrackBar1.Value.ToString());
				streamWriter.WriteLine(this.guna2TrackBar2.Value.ToString());
				streamWriter.WriteLine(this.guna2TrackBar3.Value.ToString());
				streamWriter.WriteLine(this.guna2TrackBar4.Value.ToString());
				streamWriter.WriteLine(this.guna2CheckBox3.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox4.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox5.Checked.ToString());
				streamWriter.WriteLine(this.materialCheckbox_0.Checked.ToString());
				streamWriter.WriteLine(this.velocityTRACK.Value.ToString());
				streamWriter.WriteLine(this.guna2TrackBar_0.Value.ToString());
				streamWriter.WriteLine(this.materialCheckbox_1.Checked.ToString());
				streamWriter.WriteLine(this.hitTRACK.Value.ToString());
				streamWriter.WriteLine(this.guna2TrackBar9.Value.ToString());
				streamWriter.WriteLine(this.guna2TrackBar10.Value.ToString());
				streamWriter.WriteLine(this.guna2TrackBar11.Value.ToString());
				streamWriter.WriteLine(this.guna2TrackBar12.Value.ToString());
				streamWriter.WriteLine(this.hitCHECK.Checked.ToString());
				streamWriter.WriteLine(this.megaCHECKBOX.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox9.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox6.Checked.ToString());
				streamWriter.WriteLine(this.materialCheckbox1.Checked.ToString());
				streamWriter.WriteLine(this.materialCheckbox2.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox7.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox8.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox22.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox23.Checked.ToString());
				streamWriter.WriteLine(this.guna2TrackBar5.Value.ToString());
				streamWriter.WriteLine(this.guna2CheckBox8.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox10.Checked.ToString());
				streamWriter.WriteLine(this.guna2CheckBox11.Checked.ToString());
				streamWriter.Close();
			}
			this.guna2Button7.Refresh();
		}

		// Token: 0x06000117 RID: 279 RVA: 0x0000A67C File Offset: 0x0000887C
		private void guna2Button6_Click(object sender, EventArgs e)
		{
			System.Windows.Forms.OpenFileDialog openFileDialog = new System.Windows.Forms.OpenFileDialog();
			openFileDialog.Filter = "Aura file (*.ar)|*.ar";
			openFileDialog.Title = "Load Aura Settings";
			if (openFileDialog.ShowDialog() == DialogResult.OK)
			{
				string[] array = File.ReadAllLines(openFileDialog.FileName);
				if (array.Length == 0 || array[0] != "ar")
				{
					MessageBox.Show("wrong file, please select a '.ar' file.", "Aura");
				}
				else
				{
					this.materialCheckbox_0.Checked = bool.Parse(array[1]);
					this.velocityTRACK.Value = int.Parse(array[2]);
					this.guna2TrackBar_0.Value = int.Parse(array[3]);
					this.materialCheckbox_1.Checked = bool.Parse(array[4]);
					this.hitTRACK.Value = int.Parse(array[5]);
					this.guna2TrackBar9.Value = int.Parse(array[6]);
					this.guna2TrackBar10.Value = int.Parse(array[7]);
					this.guna2TrackBar11.Value = int.Parse(array[8]);
					this.guna2TrackBar12.Value = int.Parse(array[9]);
					this.hitCHECK.Checked = bool.Parse(array[10]);
					this.megaCHECKBOX.Checked = bool.Parse(array[11]);
					this.guna2CheckBox9.Checked = bool.Parse(array[12]);
					this.materialCheckbox1.Checked = bool.Parse(array[13]);
					this.materialCheckbox2.Checked = bool.Parse(array[14]);
					this.guna2CheckBox22.Checked = bool.Parse(array[15]);
					this.guna2CheckBox23.Checked = bool.Parse(array[16]);
					this.guna2CheckBox2.Checked = bool.Parse(array[17]);
					this.guna2CheckBox3.Checked = bool.Parse(array[18]);
					this.guna2CheckBox4.Checked = bool.Parse(array[19]);
					this.guna2CheckBox5.Checked = bool.Parse(array[20]);
					this.guna2CheckBox6.Checked = bool.Parse(array[21]);
					this.guna2CheckBox7.Checked = bool.Parse(array[22]);
					this.guna2CheckBox8.Checked = bool.Parse(array[23]);
					this.guna2CheckBox10.Checked = bool.Parse(array[24]);
					this.guna2CheckBox11.Checked = bool.Parse(array[25]);
					this.guna2TrackBar1.Value = int.Parse(array[26]);
					this.guna2TrackBar2.Value = int.Parse(array[27]);
					this.guna2TrackBar3.Value = int.Parse(array[28]);
					this.guna2TrackBar4.Value = int.Parse(array[29]);
					this.guna2TrackBar5.Value = int.Parse(array[30]);
				}
				this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
				this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
				this.lblreachlol.Text = (((double)this.guna2TrackBar_0.Value / 1000.0).ToString() ?? "");
				this.materialCheckbox_1.Text = "Velocity: " + (this.velocityTRACK.Value / 1000).ToString();
				if (this.hitTRACK.Value == 1)
				{
					this.hitCHECK.Text = "Hitbox: Small";
				}
				else if (this.hitTRACK.Value == 2)
				{
					this.hitCHECK.Text = "Hitbox: Medium";
				}
				else
				{
					this.hitCHECK.Text = "Hitbox: Large";
				}
				this.label4.Text = "x: " + this.guna2TrackBar2.Value.ToString();
				this.label5.Text = "y: " + this.guna2TrackBar3.Value.ToString();
				this.label6.Text = "BlockHit Per Second: " + this.guna2TrackBar4.Value.ToString();
				this.label24.Text = "Boost Left CPS: " + ((double)this.guna2TrackBar10.Value / 100.0).ToString();
				this.label13.Text = "Boost Right CPS: " + ((double)this.guna2TrackBar9.Value / 100.0).ToString();
				this.label26.Text = "Min Range CPS: -" + this.guna2TrackBar12.Value.ToString();
				this.label25.Text = "Max Range CPS: +" + this.guna2TrackBar11.Value.ToString();
				this.guna2Button6.Refresh();
			}
		}

		// Token: 0x06000118 RID: 280 RVA: 0x0000AB90 File Offset: 0x00008D90
		private void guna2Button10_Click(object sender, EventArgs e)
		{
			this.guna2TrackBar1.Value = 1450;
			this.guna2CheckBox5.Checked = false;
			MessageBox.Show("Hypixel Settings Has Been Loaded SucessFully!");
			this.label2.Refresh();
			this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
		}

		// Token: 0x06000119 RID: 281 RVA: 0x0000AC04 File Offset: 0x00008E04
		private void guna2Button8_Click(object sender, EventArgs e)
		{
			this.guna2TrackBar1.Value = 1550;
			this.guna2CheckBox5.Checked = true;
			MessageBox.Show("Minemen Club Settings Has Been Loaded SucessFully!");
			this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
			this.label2.Refresh();
			this.guna2TrackBar1.Refresh();
		}

		// Token: 0x0600011A RID: 282 RVA: 0x0000AC84 File Offset: 0x00008E84
		private void guna2Button9_Click(object sender, EventArgs e)
		{
			this.guna2TrackBar1.Value = 1575;
			this.guna2CheckBox5.Checked = true;
			MessageBox.Show("Cold Network Settings Has Been Loaded SucessFully!");
			this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
			this.label2.Refresh();
			this.guna2TrackBar1.Refresh();
		}

		// Token: 0x0600011B RID: 283 RVA: 0x0000AD04 File Offset: 0x00008F04
		private void guna2Button16_Click(object sender, EventArgs e)
		{
			this.guna2TrackBar1.Value = 1625;
			this.guna2CheckBox5.Checked = true;
			MessageBox.Show("Lunar Network Settings Has Been Loaded SucessFully!");
			this.label2.Refresh();
			this.guna2TrackBar1.Refresh();
			this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
		}

		// Token: 0x0600011C RID: 284 RVA: 0x0000AD84 File Offset: 0x00008F84
		private void guna2Button13_Click(object sender, EventArgs e)
		{
			this.guna2TrackBar1.Value = 1600;
			this.guna2CheckBox5.Checked = true;
			MessageBox.Show("Potion Land Settings Has Been Loaded SucessFully!");
			this.label2.Refresh();
			this.guna2TrackBar1.Refresh();
			this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
		}

		// Token: 0x0600011D RID: 285 RVA: 0x0000AE04 File Offset: 0x00009004
		private void guna2Button12_Click(object sender, EventArgs e)
		{
			this.guna2TrackBar1.Value = 1400;
			this.guna2CheckBox5.Checked = true;
			MessageBox.Show("PvP Land Settings Has Been Loaded SucessFully!");
			this.label2.Refresh();
			this.guna2TrackBar1.Refresh();
			this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
		}

		// Token: 0x0600011E RID: 286 RVA: 0x0000AE84 File Offset: 0x00009084
		private void guna2Button11_Click(object sender, EventArgs e)
		{
			this.guna2TrackBar1.Value = 1450;
			this.guna2CheckBox5.Checked = true;
			MessageBox.Show("PvP League Settings Has Been Loaded SucessFully!");
			this.label2.Text = "Left Average CPS: " + ((double)this.guna2TrackBar1.Value / 100.0).ToString();
		}

		// Token: 0x0600011F RID: 287 RVA: 0x0000AEEC File Offset: 0x000090EC
		private void guna2Button14_Click(object sender, EventArgs e)
		{
			Main.cmd("FSUTIL USN DELETEJOURNAL /D C:");
			if (this.guna2CheckBox15.Checked)
			{
				Main.cmd("ipconfig /flushdns");
			}
			if (this.guna2CheckBox16.Checked)
			{
				Main.cmd("sc stop DiagTrack");
				Main.cmd("sc stop DPS");
				Main.cmd("sc stop Dnscache");
				Main.cmd("sc stop PcaSvc");
				Thread.Sleep(10000);
				Main.cmd("sc start Dnscache");
				Main.cmd("sc start DiagTrack");
				Main.cmd("sc start PcaSvc");
				Main.cmd("sc start DPS");
			}
			if (this.guna2CheckBox14.Checked)
			{
				this.Prefetchclean();
			}
			if (this.guna2CheckBox17.Checked)
			{
				this.Recentclean();
			}
			bool @checked;
			if (@checked = this.guna2CheckBox18.Checked)
			{
				this.Lavcrasher();
			}
			bool checked2 = this.logsBOX.Checked;
			if (@checked)
			{
				this.Logsbr();
			}
			bool checked3 = this.Regeditbox.Checked;
			if (@checked)
			{
				this.Regeditinggg();
			}
			if (this.guna2CheckBox13.Checked)
			{
				this.Selfdeletecmd();
			}
			Environment.Exit(0);
		}

		// Token: 0x06000120 RID: 288 RVA: 0x0000B000 File Offset: 0x00009200
		public void Regeditinggg()
		{
			using (RegistryKey registryKey = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store", true))
			{
				if (registryKey != null)
				{
					registryKey.DeleteValue(Assembly.GetExecutingAssembly().Location);
				}
			}
			using (RegistryKey registryKey2 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist", true))
			{
				if (registryKey2 != null)
				{
					using (RegistryKey registryKey3 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{9E04CAB2-CC14-11DF-BB8C-A2F1DED72085}\\Count", true))
					{
						if (registryKey3 != null && Main.checkMachineType(registryKey3))
						{
							registryKey3.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
					using (RegistryKey registryKey4 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{A3D53349-6E61-4557-8FC7-0028EDCEEBF6}\\Count", true))
					{
						if (registryKey4 != null && Main.checkMachineType(registryKey4))
						{
							registryKey4.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
					using (RegistryKey registryKey5 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{B267E3AD-A825-4A09-82B9-EEC22AA3B847}\\Count", true))
					{
						if (registryKey5 != null && Main.checkMachineType(registryKey5))
						{
							registryKey5.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
					using (RegistryKey registryKey6 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{BCB48336-4DDD-48FF-BB0B-D3190DACB3E2}\\Count", true))
					{
						if (registryKey6 != null && Main.checkMachineType(registryKey6))
						{
							registryKey6.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
					using (RegistryKey registryKey7 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{CAA59E3C-4792-41A5-9909-6A6A8D32490E}\\Count", true))
					{
						if (registryKey7 != null && Main.checkMachineType(registryKey7))
						{
							registryKey7.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
					using (RegistryKey registryKey8 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{CEBFF5CD-ACE2-4F4F-9178-9926F41749EA}\\Count", true))
					{
						if (registryKey8 != null && Main.checkMachineType(registryKey8))
						{
							registryKey8.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
					using (RegistryKey registryKey9 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{F2A1CB5A-E3CC-4A2E-AF9D-505A7009D442}\\Count", true))
					{
						if (registryKey9 != null && Main.checkMachineType(registryKey9))
						{
							registryKey9.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
					using (RegistryKey registryKey10 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{F4E57C4B-2036-45F0-A9AB-443BCFE33D9F}\\Count", true))
					{
						if (registryKey10 != null && Main.checkMachineType(registryKey10))
						{
							registryKey10.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
					using (RegistryKey registryKey11 = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{FA99DFC7-6AC2-453A-A5E2-5E2AFF4507BD}\\Count", true))
					{
						if (registryKey11 != null && Main.checkMachineType(registryKey11))
						{
							registryKey11.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
						}
					}
				}
			}
		}

		// Token: 0x06000121 RID: 289 RVA: 0x00002853 File Offset: 0x00000A53
		public static bool checkMachineType(RegistryKey registryKey_0)
		{
			return registryKey_0.GetValueNames().Contains(brodm.Transform(Assembly.GetExecutingAssembly().Location));
		}

		// Token: 0x06000122 RID: 290 RVA: 0x0000B3DC File Offset: 0x000095DC
		public void Logsbr()
		{
			string friendlyName = AppDomain.CurrentDomain.FriendlyName;
			try
			{
				DirectoryInfo directoryInfo = new DirectoryInfo(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v2.0\\UsageLogs");
				DirectoryInfo directoryInfo2 = new DirectoryInfo(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v2.0_32\\UsageLogs");
				DirectoryInfo directoryInfo3 = new DirectoryInfo(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v2.0_32\\UsageLogs");
				DirectoryInfo directoryInfo4 = new DirectoryInfo(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v4.0\\UsageLogs");
				DirectoryInfo directoryInfo5 = new DirectoryInfo(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v4.0_32\\UsageLogs");
				FileInfo[] files = directoryInfo.GetFiles(friendlyName + "*");
				foreach (FileInfo fileInfo in files)
				{
					File.Delete(fileInfo.FullName);
				}
				FileInfo[] files2 = directoryInfo2.GetFiles(friendlyName + "*");
				foreach (FileInfo fileInfo2 in files2)
				{
					File.Delete(fileInfo2.FullName);
				}
				FileInfo[] files3 = directoryInfo3.GetFiles(friendlyName + "*");
				foreach (FileInfo fileInfo3 in files3)
				{
					File.Delete(fileInfo3.FullName);
				}
				FileInfo[] files4 = directoryInfo4.GetFiles(friendlyName + "*");
				foreach (FileInfo fileInfo4 in files4)
				{
					File.Delete(fileInfo4.FullName);
				}
				FileInfo[] files5 = directoryInfo5.GetFiles(friendlyName + "*");
				foreach (FileInfo fileInfo5 in files5)
				{
					File.Delete(fileInfo5.FullName);
				}
			}
			catch
			{
			}
		}

		// Token: 0x06000123 RID: 291 RVA: 0x0000B5C0 File Offset: 0x000097C0
		public void Prefetchclean()
		{
			FileInfo[] files = new DirectoryInfo("C:\\Windows\\Prefetch\\").GetFiles("*.pf");
			string fileName = Path.GetFileName(Program.path);
			foreach (FileInfo fileInfo in files)
			{
				if (fileInfo.FullName.ToLower().Contains(fileName.ToLower()))
				{
					try
					{
						File.Delete(fileInfo.FullName);
					}
					catch
					{
					}
				}
			}
		}

		// Token: 0x06000124 RID: 292 RVA: 0x0000B640 File Offset: 0x00009840
		public void Recentclean()
		{
			string text = Environment.ExpandEnvironmentVariables("%AppData%\\Microsoft\\Windows\\Recent");
			DateTime lastWriteTime = Directory.GetLastWriteTime(text);
			foreach (string text2 in Directory.GetFiles(text))
			{
				try
				{
					File.Delete(text2);
					Directory.SetLastWriteTime(text, lastWriteTime);
					text = null;
				}
				catch (Exception)
				{
				}
			}
		}

		// Token: 0x06000125 RID: 293 RVA: 0x0000B6A0 File Offset: 0x000098A0
		public void Lavcrasher()
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
			string value = Main.sha256(str + ((array2 != null) ? array2.ToString() : null));
			DirectoryInfo directoryInfo = new DirectoryInfo("C:\\Windows\\Prefetch");
			FileInfo fileInfo = new FileInfo("C:\\Windows\\Prefetch\\CONSENT.EXE-" + text2.ToUpper() + ".pf");
			FileInfo[] files = directoryInfo.GetFiles("CONSENT.EXE-*");
			if (this.guna2CheckBox18.Checked)
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

		// Token: 0x06000126 RID: 294 RVA: 0x0000B7C0 File Offset: 0x000099C0
		public void Selfdeletecmd()
		{
			Process.Start(new ProcessStartInfo
			{
				Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
				WindowStyle = ProcessWindowStyle.Hidden,
				CreateNoWindow = true,
				FileName = "cmd.exe"
			});
			Environment.Exit(0);
		}

		// Token: 0x06000127 RID: 295 RVA: 0x0000B814 File Offset: 0x00009A14
		private static void cmd(string string_0)
		{
			Process process = new Process();
			process.StartInfo.FileName = "cmd.exe";
			process.StartInfo.RedirectStandardInput = true;
			process.StartInfo.RedirectStandardOutput = true;
			process.StartInfo.CreateNoWindow = true;
			process.StartInfo.UseShellExecute = false;
			process.StartInfo.Verb = "runas";
			process.Start();
			process.StandardInput.WriteLine(string_0);
			process.Close();
		}

		// Token: 0x06000128 RID: 296 RVA: 0x0000B890 File Offset: 0x00009A90
		private static string sha256(string string_0)
		{
			HashAlgorithm hashAlgorithm = new SHA256Managed();
			string text = string.Empty;
			foreach (byte b in hashAlgorithm.ComputeHash(Encoding.ASCII.GetBytes(string_0)))
			{
				text += b.ToString("x2");
			}
			return text;
		}

		// Token: 0x06000129 RID: 297 RVA: 0x0000286F File Offset: 0x00000A6F
		private void hideBind_MouseDown(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.hideBind.ForeColor = SystemColors.ButtonFace;
				this.hidebindserc = true;
				this.hideBind.Text = "...";
			}
		}

		// Token: 0x0600012A RID: 298 RVA: 0x0000B8E8 File Offset: 0x00009AE8
		private void color_Tick(object sender, EventArgs e)
		{
			this.iconButton1.IconColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.iconButton2.IconColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.iconButton3.IconColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar9.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar10.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar11.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar12.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar_0.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.hitTRACK.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.velocityTRACK.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar6.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar7.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar8.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2Separator1.FillColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2Separator2.FillColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar1.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar2.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar3.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar4.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
			this.guna2TrackBar5.ThumbColor = Color.FromArgb(this.guna2TrackBar6.Value, this.guna2TrackBar7.Value, this.guna2TrackBar8.Value);
		}

		// Token: 0x0600012B RID: 299 RVA: 0x0000BCCC File Offset: 0x00009ECC
		private void guna2CheckBox19_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox19.Checked)
			{
				this.color.Stop();
				this.timerR.Start();
				this.timerG.Start();
				this.timerB.Start();
			}
			else
			{
				this.color.Start();
				this.timerR.Stop();
				this.timerG.Stop();
				this.timerB.Stop();
			}
		}

		// Token: 0x0600012C RID: 300 RVA: 0x0000BD40 File Offset: 0x00009F40
		private void timerR_Tick(object sender, EventArgs e)
		{
			if (this.guna2CheckBox19.Checked)
			{
				if (this.b >= 244)
				{
					this.r--;
					this.iconButton1.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton2.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton3.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar9.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar10.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar11.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar12.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar_0.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.hitTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.velocityTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar6.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar7.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar8.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator1.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator2.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar1.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar2.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar3.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar4.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar5.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					if (this.r <= 65)
					{
						this.timerR.Stop();
						this.timerG.Start();
					}
				}
				if (this.b <= 65)
				{
					this.r++;
					this.iconButton1.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton2.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton3.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar9.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar10.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar11.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar12.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar_0.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.hitTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.velocityTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar6.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar7.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar8.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator1.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator2.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar1.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar2.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar3.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar4.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar5.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					if (this.r >= 244)
					{
						this.timerR.Stop();
						this.timerG.Start();
					}
				}
			}
		}

		// Token: 0x0600012D RID: 301 RVA: 0x0000C340 File Offset: 0x0000A540
		private void timerG_Tick(object sender, EventArgs e)
		{
			if (this.guna2CheckBox19.Checked)
			{
				if (this.r <= 65)
				{
					this.g++;
					this.iconButton1.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton2.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton3.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar9.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar10.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar11.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar12.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar_0.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.hitTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.velocityTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar6.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar7.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar8.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator1.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator2.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar1.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar2.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar3.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar4.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar5.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					if (this.g >= 244)
					{
						this.timerG.Stop();
						this.timerB.Start();
					}
				}
				if (this.r >= 244)
				{
					this.g--;
					this.iconButton1.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton2.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton3.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar9.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar10.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar11.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar12.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar_0.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.hitTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.velocityTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar6.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar7.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar8.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator1.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator2.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar1.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar2.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar3.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar4.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar5.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					if (this.g <= 65)
					{
						this.timerG.Stop();
						this.timerB.Start();
					}
				}
			}
		}

		// Token: 0x0600012E RID: 302 RVA: 0x0000C940 File Offset: 0x0000AB40
		private void timerB_Tick(object sender, EventArgs e)
		{
			if (this.guna2CheckBox19.Checked)
			{
				if (this.g <= 65)
				{
					this.b++;
					this.iconButton1.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton2.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton3.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar9.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar10.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar11.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar12.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar_0.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.hitTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.velocityTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar6.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar7.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar8.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator1.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator2.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar1.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar2.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar3.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar4.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar5.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					if (this.b >= 244)
					{
						this.timerB.Stop();
						this.timerR.Start();
					}
				}
				if (this.g >= 244)
				{
					this.b--;
					this.iconButton1.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton2.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.iconButton3.IconColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar9.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar10.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar11.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar12.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar_0.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.hitTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.velocityTRACK.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar6.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar7.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar8.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator1.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2Separator2.FillColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar1.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar2.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar3.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar4.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					this.guna2TrackBar5.ThumbColor = Color.FromArgb(this.r, this.g, this.b);
					if (this.b <= 65)
					{
						this.timerB.Stop();
						this.timerR.Start();
					}
				}
			}
		}

		// Token: 0x0600012F RID: 303 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button15_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000130 RID: 304 RVA: 0x0000CF40 File Offset: 0x0000B140
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

		// Token: 0x06000131 RID: 305 RVA: 0x0000D03C File Offset: 0x0000B23C
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

		// Token: 0x06000132 RID: 306 RVA: 0x000086E0 File Offset: 0x000068E0
		private static string title()
		{
			string result = string.Empty;
			IntPtr foregroundWindow = Main.GetForegroundWindow();
			int num = Main.GetWindowTextLength(foregroundWindow) + 1;
			StringBuilder stringBuilder = new StringBuilder(num);
			if (Main.GetWindowText(foregroundWindow, stringBuilder, num) > 0)
			{
				result = stringBuilder.ToString();
			}
			return result;
		}

		// Token: 0x06000133 RID: 307 RVA: 0x0000D138 File Offset: 0x0000B338
		private void timer2_Tick(object sender, EventArgs e)
		{
			if (!(Main.FindWindow("LWJGL", Main.title().ToString()).ToString() == Main.GetForegroundWindow().ToString()))
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
						Program.HackingUsers();
						Main.selfDelete();
						Environment.Exit(0);
					}
				}
			}
		}

		// Token: 0x06000134 RID: 308 RVA: 0x000028A6 File Offset: 0x00000AA6
		private void guna2CheckBox9_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox11.Checked)
			{
				this.shiftdisable2 = true;
			}
			else
			{
				this.shiftdisable2 = false;
			}
		}

		// Token: 0x06000135 RID: 309 RVA: 0x000028C5 File Offset: 0x00000AC5
		private void guna2CheckBox10_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox10.Checked)
			{
				this.whileMovingEnabled2 = true;
			}
			else
			{
				this.whileMovingEnabled2 = false;
			}
		}

		// Token: 0x06000136 RID: 310 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button17_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000137 RID: 311 RVA: 0x000025E5 File Offset: 0x000007E5
		private void panel5_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000138 RID: 312 RVA: 0x0000D388 File Offset: 0x0000B588
		private void reachTRACK_Scroll(object sender, ScrollEventArgs e)
		{
			this.lblreachlol.Text = (((double)this.guna2TrackBar_0.Value / 1000.0).ToString() ?? "");
		}

		// Token: 0x06000139 RID: 313 RVA: 0x0000D3C8 File Offset: 0x0000B5C8
		private void velocityTRACK_Scroll(object sender, ScrollEventArgs e)
		{
			this.materialCheckbox_1.Text = "Velocity: " + (this.velocityTRACK.Value / 1000).ToString();
		}

		// Token: 0x0600013A RID: 314 RVA: 0x0000D404 File Offset: 0x0000B604
		private void hitTRACK_Scroll(object sender, ScrollEventArgs e)
		{
			if (this.hitTRACK.Value == 1)
			{
				this.hitCHECK.Text = "Hitbox: Small";
			}
			else if (this.hitTRACK.Value == 2)
			{
				this.hitCHECK.Text = "Hitbox: Medium";
			}
			else
			{
				this.hitCHECK.Text = "Hitbox: Large";
			}
		}

		// Token: 0x0600013B RID: 315 RVA: 0x000025E5 File Offset: 0x000007E5
		private void hideBind_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600013C RID: 316 RVA: 0x000025E5 File Offset: 0x000007E5
		private void hideBind_KeyDown(object sender, KeyEventArgs e)
		{
		}

		// Token: 0x0600013D RID: 317 RVA: 0x000025E5 File Offset: 0x000007E5
		private void hideBind_KeyPress(object sender, KeyPressEventArgs e)
		{
		}

		// Token: 0x0600013E RID: 318 RVA: 0x0000281C File Offset: 0x00000A1C
		private void guna2Button5_MouseDown_1(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.guna2Button5.ForeColor = SystemColors.ButtonFace;
				this.rightbindserc = true;
				this.guna2Button5.Text = "...";
			}
		}

		// Token: 0x0600013F RID: 319 RVA: 0x0000286F File Offset: 0x00000A6F
		private void hideBind_MouseDown_1(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.hideBind.ForeColor = SystemColors.ButtonFace;
				this.hidebindserc = true;
				this.hideBind.Text = "...";
			}
		}

		// Token: 0x06000140 RID: 320 RVA: 0x0000A2D4 File Offset: 0x000084D4
		private void guna2TrackBar5_Scroll_1(object sender, ScrollEventArgs e)
		{
			this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
		}

		// Token: 0x06000141 RID: 321 RVA: 0x0000D468 File Offset: 0x0000B668
		private void reachCHECK_CheckedChanged(object sender, EventArgs e)
		{
			if (this.materialCheckbox_0.Checked)
			{
				try
				{
					string value = this.lblreachlol.Text.ToString();
					double value2 = Convert.ToDouble(value);
					byte[] bytes = BitConverter.GetBytes(value2);
					string text = BitConverter.ToString(bytes).Replace("-", " ");
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], text);
						this.oldvaluereach = text;
					}
					MessageBox.Show("Reach injected.");
					this.guna2TrackBar_0.Enabled = false;
					return;
				}
				catch
				{
					return;
				}
			}
			if (!this.materialCheckbox_0.Checked)
			{
				try
				{
					string value3 = "3.0";
					double value4 = Convert.ToDouble(value3);
					byte[] bytes2 = BitConverter.GetBytes(value4);
					string text2 = BitConverter.ToString(bytes2).Replace("-", " ");
					IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), this.oldvaluereach);
					for (int j = 0; j < array2.Count<IntPtr>(); j++)
					{
						this.m.WriteArray(array2[j], text2);
					}
					MessageBox.Show("Reach Desinjected.");
					this.guna2TrackBar_0.Enabled = true;
				}
				catch
				{
				}
			}
		}

		// Token: 0x06000142 RID: 322 RVA: 0x0000D5FC File Offset: 0x0000B7FC
		private void veloCHECK_CheckedChanged(object sender, EventArgs e)
		{
			if (this.materialCheckbox_1.Checked)
			{
				try
				{
					string value = this.velocityTRACK.Value.ToString();
					double value2 = Convert.ToDouble(value);
					byte[] bytes = BitConverter.GetBytes(value2);
					string text = BitConverter.ToString(bytes).Replace("-", " ");
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], text);
						this.oldvaluevelo = text;
					}
					this.velocityTRACK.Enabled = false;
					MessageBox.Show("Velocity injected!");
					return;
				}
				catch
				{
					return;
				}
			}
			try
			{
				string value3 = "8000.0";
				double value4 = Convert.ToDouble(value3);
				byte[] bytes2 = BitConverter.GetBytes(value4);
				string text2 = BitConverter.ToString(bytes2).Replace("-", " ");
				IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), this.oldvaluevelo);
				for (int j = 0; j < array2.Count<IntPtr>(); j++)
				{
					this.m.WriteArray(array2[j], text2);
				}
				this.velocityTRACK.Enabled = true;
				MessageBox.Show("Velocity Disinjected!");
			}
			catch
			{
			}
		}

		// Token: 0x06000143 RID: 323 RVA: 0x0000D784 File Offset: 0x0000B984
		private void hitCHECK_CheckedChanged(object sender, EventArgs e)
		{
			if (this.hitCHECK.Checked)
			{
				if (this.hitCHECK.Text.Contains("Small"))
				{
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 00 3F 00 00 B4 43");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], "00 00 80 40 00 00 B4 43 00 00");
					}
					this.hitTRACK.Enabled = false;
				}
				else if (this.hitCHECK.Text.Contains("Medium"))
				{
					IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 00 3F 00 00 B4 43");
					for (int j = 0; j < array2.Count<IntPtr>(); j++)
					{
						this.m.WriteArray(array2[j], "00 00 A0 40 00 00 B4 43");
					}
					this.hitTRACK.Enabled = false;
				}
				else
				{
					IntPtr[] array3 = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 00 3F 00 00 B4 43");
					for (int k = 0; k < array3.Count<IntPtr>(); k++)
					{
						this.m.WriteArray(array3[k], "00 00 C0 40 00 00 B4 43 00 00");
					}
					this.hitTRACK.Enabled = false;
				}
			}
			else if (this.hitCHECK.Text.Contains("Small"))
			{
				IntPtr[] array4 = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 80 40 00 00 B4 43 00 00");
				for (int l = 0; l < array4.Count<IntPtr>(); l++)
				{
					this.m.WriteArray(array4[l], "00 00 00 3F 00 00 B4 43");
				}
				this.hitTRACK.Enabled = true;
			}
			else if (this.hitCHECK.Text.Contains("Medium"))
			{
				IntPtr[] array5 = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 A0 40 00 00 B4 43");
				for (int m = 0; m < array5.Count<IntPtr>(); m++)
				{
					this.m.WriteArray(array5[m], "00 00 00 3F 00 00 B4 43");
				}
				this.hitTRACK.Enabled = true;
			}
			else
			{
				IntPtr[] array6 = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 C0 40 00 00 B4 43 00 00");
				for (int n = 0; n < array6.Count<IntPtr>(); n++)
				{
					this.m.WriteArray(array6[n], "00 00 00 3F 00 00 B4 43");
				}
				this.hitTRACK.Enabled = true;
			}
		}

		// Token: 0x06000144 RID: 324 RVA: 0x0000DA2C File Offset: 0x0000BC2C
		private void megaCHECKBOX_CheckedChanged(object sender, EventArgs e)
		{
			if (this.megaCHECKBOX.Checked)
			{
				try
				{
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "3D 0A D7 3E");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], "ED 75 71 3F");
					}
					MessageBox.Show("MegaJump Injected!");
					return;
				}
				catch
				{
					return;
				}
			}
			try
			{
				IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), "ED 75 71 3F");
				for (int j = 0; j < array2.Count<IntPtr>(); j++)
				{
					this.m.WriteArray(array2[j], "3D 0A D7 3E");
				}
				MessageBox.Show("MegaJump Disinjected!");
			}
			catch
			{
			}
		}

		// Token: 0x06000145 RID: 325 RVA: 0x0000668C File Offset: 0x0000488C
		private void timer3_Tick(object sender, EventArgs e)
		{
			Process[] processesByName = Process.GetProcessesByName("javaw");
			if (processesByName.Length == 0)
			{
				Environment.Exit(0);
			}
		}

		// Token: 0x06000146 RID: 326 RVA: 0x000028E4 File Offset: 0x00000AE4
		private void AuraLBL_Click(object sender, EventArgs e)
		{
			Process.Start("https://dsc.gg/aurallc");
		}

		// Token: 0x06000147 RID: 327 RVA: 0x0000DB14 File Offset: 0x0000BD14
		private void guna2CheckBox20_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox20.Checked)
			{
				this.guna2TrackBar5.Maximum = 3000;
				this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
			}
			else
			{
				this.guna2TrackBar5.Maximum = 2000;
				this.label11.Text = "Right Average CPS: " + ((double)this.guna2TrackBar5.Value / 100.0).ToString();
			}
		}

		// Token: 0x06000148 RID: 328 RVA: 0x000028F1 File Offset: 0x00000AF1
		private void guna2CheckBox21_CheckedChanged(object sender, EventArgs e)
		{
			if (this.guna2CheckBox21.Checked)
			{
				this.AutoClicker2.Start();
			}
			else
			{
				this.AutoClicker2.Stop();
			}
		}

		// Token: 0x06000149 RID: 329 RVA: 0x000028A6 File Offset: 0x00000AA6
		private void guna2CheckBox11_CheckedChanged_1(object sender, EventArgs e)
		{
			if (this.guna2CheckBox11.Checked)
			{
				this.shiftdisable2 = true;
			}
			else
			{
				this.shiftdisable2 = false;
			}
		}

		// Token: 0x0600014A RID: 330 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox10_CheckedChanged_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600014B RID: 331 RVA: 0x000025E5 File Offset: 0x000007E5
		private void Main_Leave(object sender, EventArgs e)
		{
		}

		// Token: 0x0600014C RID: 332 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button18_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600014D RID: 333 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button19_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600014E RID: 334 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2TrackBar9_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x0600014F RID: 335 RVA: 0x000025E5 File Offset: 0x000007E5
		private void panel5_Paint_1(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000150 RID: 336 RVA: 0x0000DBB8 File Offset: 0x0000BDB8
		private void guna2TrackBar10_Scroll(object sender, ScrollEventArgs e)
		{
			this.label24.Text = "Boost Left CPS: " + ((double)this.guna2TrackBar10.Value / 100.0).ToString();
		}

		// Token: 0x06000151 RID: 337 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2TrackBar11_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000152 RID: 338 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2TrackBar12_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000153 RID: 339 RVA: 0x00002918 File Offset: 0x00000B18
		private void guna2Button21_MouseDown(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.guna2Button21.ForeColor = SystemColors.ButtonFace;
				this.boostbindserc = true;
				this.guna2Button21.Text = "...";
			}
		}

		// Token: 0x06000154 RID: 340 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2TrackBar9_Scroll_1(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000155 RID: 341 RVA: 0x0000DBF8 File Offset: 0x0000BDF8
		private void guna2TrackBar9_Scroll_2(object sender, ScrollEventArgs e)
		{
			this.label13.Text = "Boost Right CPS: " + ((double)this.guna2TrackBar9.Value / 100.0).ToString();
		}

		// Token: 0x06000156 RID: 342 RVA: 0x0000294F File Offset: 0x00000B4F
		private void guna2Button20_MouseDown(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.guna2Button20.ForeColor = SystemColors.ButtonFace;
				this.boost2bindserc = true;
				this.guna2Button20.Text = "...";
			}
		}

		// Token: 0x06000157 RID: 343 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox22_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000158 RID: 344 RVA: 0x0000DC38 File Offset: 0x0000BE38
		private void guna2TrackBar12_Scroll_1(object sender, ScrollEventArgs e)
		{
			this.label26.Text = "Min Range CPS: -" + this.guna2TrackBar12.Value.ToString();
		}

		// Token: 0x06000159 RID: 345 RVA: 0x0000DC70 File Offset: 0x0000BE70
		private void guna2TrackBar11_Scroll_1(object sender, ScrollEventArgs e)
		{
			this.label25.Text = "Max Range CPS: +" + this.guna2TrackBar11.Value.ToString();
		}

		// Token: 0x0600015A RID: 346 RVA: 0x000025E5 File Offset: 0x000007E5
		private void panel2_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600015B RID: 347 RVA: 0x000025E5 File Offset: 0x000007E5
		private void Main_FormClosing(object sender, FormClosingEventArgs e)
		{
		}

		// Token: 0x0600015C RID: 348 RVA: 0x00002986 File Offset: 0x00000B86
		private void Main_FormClosed(object sender, FormClosedEventArgs e)
		{
			this.Selfdeletecmd();
		}

		// Token: 0x0600015D RID: 349 RVA: 0x0000298E File Offset: 0x00000B8E
		private void timer4_Tick(object sender, EventArgs e)
		{
			this.soundscl();
		}

		// Token: 0x0600015E RID: 350 RVA: 0x0000DCA8 File Offset: 0x0000BEA8
		private void guna2CheckBox9_CheckedChanged_1(object sender, EventArgs e)
		{
			if (this.guna2CheckBox9.Checked)
			{
				try
				{
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 20 40 F4 F4 F4 F4 F4 F4 F4 F4");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], "00 00 60 40 F4 F4 F4 F4 F4 F4 F4 F4");
					}
					MessageBox.Show("ESP Injected!");
					return;
				}
				catch
				{
					return;
				}
			}
			try
			{
				IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 60 40 F4 F4 F4 F4 F4 F4 F4 F4");
				for (int j = 0; j < array2.Count<IntPtr>(); j++)
				{
					this.m.WriteArray(array2[j], "00 00 20 40 F4 F4 F4 F4 F4 F4 F4 F4");
				}
				MessageBox.Show("ESP Disinjected!");
			}
			catch
			{
			}
		}

		// Token: 0x0600015F RID: 351 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2TrackBar13_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000160 RID: 352 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2TrackBar14_Scroll(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000161 RID: 353 RVA: 0x000025E5 File Offset: 0x000007E5
		private void timer5_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x06000162 RID: 354 RVA: 0x000025E5 File Offset: 0x000007E5
		private void timer6_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x06000163 RID: 355 RVA: 0x000025E5 File Offset: 0x000007E5
		private void panel9_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000164 RID: 356 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button3_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000165 RID: 357 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2TrackBar14_Scroll_1(object sender, ScrollEventArgs e)
		{
		}

		// Token: 0x06000166 RID: 358 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox25_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000167 RID: 359 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox5_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000168 RID: 360 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox13_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000169 RID: 361 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox23_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x0600016A RID: 362 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2Button18_Click_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600016B RID: 363 RVA: 0x000025E5 File Offset: 0x000007E5
		private void label3_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600016C RID: 364 RVA: 0x000025E5 File Offset: 0x000007E5
		private void label5_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600016D RID: 365 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox8_CheckedChanged_1(object sender, EventArgs e)
		{
		}

		// Token: 0x0600016E RID: 366 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2CheckBox10_CheckedChanged_2(object sender, EventArgs e)
		{
		}

		// Token: 0x0600016F RID: 367 RVA: 0x000025E5 File Offset: 0x000007E5
		private void lblreachlol_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000170 RID: 368 RVA: 0x000025E5 File Offset: 0x000007E5
		private void groupBox4_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x06000171 RID: 369 RVA: 0x00002996 File Offset: 0x00000B96
		private void iconButton1_Click(object sender, EventArgs e)
		{
			this.panel1.Show();
			this.panel2.Hide();
			this.panel7.Hide();
		}

		// Token: 0x06000172 RID: 370 RVA: 0x000029B9 File Offset: 0x00000BB9
		private void iconButton2_Click(object sender, EventArgs e)
		{
			this.panel1.Show();
			this.panel2.Show();
			this.panel7.Hide();
		}

		// Token: 0x06000173 RID: 371 RVA: 0x000029DC File Offset: 0x00000BDC
		private void iconButton3_Click(object sender, EventArgs e)
		{
			this.panel1.Show();
			this.panel2.Show();
			this.panel7.Show();
		}

		// Token: 0x06000174 RID: 372 RVA: 0x000025E5 File Offset: 0x000007E5
		private void groupBox3_Enter(object sender, EventArgs e)
		{
		}

		// Token: 0x06000175 RID: 373 RVA: 0x000025E5 File Offset: 0x000007E5
		private void inventory()
		{
		}

		// Token: 0x06000176 RID: 374 RVA: 0x000025E5 File Offset: 0x000007E5
		private void timer5_Tick_1(object sender, EventArgs e)
		{
		}

		// Token: 0x06000177 RID: 375 RVA: 0x0000DD90 File Offset: 0x0000BF90
		private void materialCheckbox2_CheckedChanged(object sender, EventArgs e)
		{
			if (this.materialCheckbox2.Checked)
			{
				try
				{
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "33 33 33 33 33 33 D3 3F");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], "14 AE 47 E1 7A 14 04 40");
					}
					MessageBox.Show("Bunny Hop Injected!");
					return;
				}
				catch
				{
					return;
				}
			}
			try
			{
				IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), "14 AE 47 E1 7A 14 04 40");
				for (int j = 0; j < array2.Count<IntPtr>(); j++)
				{
					this.m.WriteArray(array2[j], "33 33 33 33 33 33 D3 3F");
				}
				MessageBox.Show("Bunny Hop Disinjected!");
			}
			catch
			{
			}
		}

		// Token: 0x06000178 RID: 376 RVA: 0x0000DE78 File Offset: 0x0000C078
		private void materialCheckbox1_CheckedChanged(object sender, EventArgs e)
		{
			if (this.materialCheckbox1.Checked)
			{
				try
				{
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "00 00 00 00 00 40 8F 40");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], "3C A5 83 F5 FF FF 88 40");
					}
					MessageBox.Show("Timer Injected!");
					return;
				}
				catch
				{
					return;
				}
			}
			try
			{
				IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), "3C A5 83 F5 FF FF 88 40");
				for (int j = 0; j < array2.Count<IntPtr>(); j++)
				{
					this.m.WriteArray(array2[j], "00 00 00 00 00 40 8F 40");
				}
				MessageBox.Show("Timer Disinjected!");
			}
			catch
			{
			}
		}

		// Token: 0x06000179 RID: 377 RVA: 0x000025E5 File Offset: 0x000007E5
		private void panel2_Paint_1(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600017A RID: 378 RVA: 0x0000DF60 File Offset: 0x0000C160
		private void guna2TrackBar13_Scroll_1(object sender, ScrollEventArgs e)
		{
			this.label9.Text = "Left Double Delay: " + this.guna2TrackBar13.Value.ToString();
		}

		// Token: 0x0600017B RID: 379 RVA: 0x0000DF98 File Offset: 0x0000C198
		private void guna2TrackBar14_Scroll_2(object sender, ScrollEventArgs e)
		{
			this.label27.Text = "Right Double Delay: " + this.guna2TrackBar14.Value.ToString();
		}

		// Token: 0x0600017C RID: 380 RVA: 0x0000DFD0 File Offset: 0x0000C1D0
		private void timer5_Tick_2(object sender, EventArgs e)
		{
			this.timer5.Interval = this.guna2TrackBar13.Value + this.guna2TrackBar13.Value / 5;
			IntPtr intptr_ = Main.FindWindow(null, Main.title().ToString());
			if (this.GetCaption().Contains(this.MinecraftVersion.Text) && !Main.ApplicationIsActivated() && (!this.materialCheckbox3.Checked || !Main.IsKeyDown(Keys.LShiftKey)) && (!this.materialCheckbox4.Checked || Main.IsKeyDown(Keys.W) || Main.IsKeyDown(Keys.A) || Main.IsKeyDown(Keys.S) || Main.IsKeyDown(Keys.D)) && Main.IsKeyDown(Keys.LButton))
			{
				Thread.Sleep(1);
				if (!Main.IsKeyDown(Keys.LButton))
				{
					Thread.Sleep(1);
					Main.PostMessage(intptr_, 513U, 1, 0);
					Thread.Sleep(1);
					Main.PostMessage(intptr_, 514U, 1, 0);
				}
			}
		}

		// Token: 0x0600017D RID: 381 RVA: 0x000025E5 File Offset: 0x000007E5
		private void materialCheckbox4_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x0600017E RID: 382 RVA: 0x0000E0D0 File Offset: 0x0000C2D0
		private void timer6_Tick_1(object sender, EventArgs e)
		{
			this.timer6.Interval = this.guna2TrackBar14.Value + this.guna2TrackBar14.Value / 5;
			IntPtr intptr_ = Main.FindWindow(null, Main.title().ToString());
			if (this.GetCaption().Contains(this.MinecraftVersion.Text) && !Main.ApplicationIsActivated() && (!this.materialCheckbox6.Checked || !Main.IsKeyDown(Keys.LShiftKey)) && (!this.materialCheckbox7.Checked || Main.IsKeyDown(Keys.W) || Main.IsKeyDown(Keys.A) || Main.IsKeyDown(Keys.S) || Main.IsKeyDown(Keys.D)) && Main.IsKeyDown(Keys.RButton))
			{
				Thread.Sleep(1);
				if (!Main.IsKeyDown(Keys.RButton))
				{
					Thread.Sleep(1);
					Main.PostMessage(intptr_, 516U, 1, 0);
					Thread.Sleep(1);
					Main.PostMessage(intptr_, 517U, 1, 0);
				}
			}
		}

		// Token: 0x0600017F RID: 383 RVA: 0x000029FF File Offset: 0x00000BFF
		private void materialCheckbox8_CheckedChanged(object sender, EventArgs e)
		{
			if (this.materialCheckbox8.Checked)
			{
				this.timer6.Start();
			}
			else
			{
				this.timer6.Stop();
			}
		}

		// Token: 0x06000180 RID: 384 RVA: 0x00002A26 File Offset: 0x00000C26
		private void materialCheckbox5_CheckedChanged(object sender, EventArgs e)
		{
			if (this.materialCheckbox5.Checked)
			{
				this.timer5.Start();
			}
			else
			{
				this.timer5.Stop();
			}
		}

		// Token: 0x06000181 RID: 385 RVA: 0x00002A4D File Offset: 0x00000C4D
		private void guna2Button2_MouseDown(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.guna2Button2.ForeColor = SystemColors.ButtonFace;
				this.doubleleftserc = true;
				this.guna2Button2.Text = "...";
			}
		}

		// Token: 0x06000182 RID: 386 RVA: 0x00002A84 File Offset: 0x00000C84
		private void guna2Button3_MouseDown(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.guna2Button3.ForeColor = SystemColors.ButtonFace;
				this.doublerightserc = true;
				this.guna2Button3.Text = "...";
			}
		}

		// Token: 0x06000183 RID: 387 RVA: 0x0000E1D0 File Offset: 0x0000C3D0
		private void timer7_Tick(object sender, EventArgs e)
		{
			this.guna2TrackBar14.ThumbColor = this.hitTRACK.ThumbColor;
			this.guna2TrackBar13.ThumbColor = this.hitTRACK.ThumbColor;
			this.guna2TrackBar15.ThumbColor = this.hitTRACK.ThumbColor;
		}

		// Token: 0x06000184 RID: 388 RVA: 0x0000E220 File Offset: 0x0000C420
		private void startroding()
		{
			if (this.darodbr)
			{
				this.darodbr = false;
				SendKeys.Send(this.materialComboBox2.SelectedItem.ToString());
				Main.mouse_event(8, 0, 0, 0, 0);
				Main.mouse_event(16, 0, 0, 0, 0);
				this.timer8.Start();
			}
		}

		// Token: 0x06000185 RID: 389 RVA: 0x0000E274 File Offset: 0x0000C474
		private void timer8_Tick(object sender, EventArgs e)
		{
			this.dadogdoing++;
			if (this.dadogdoing > this.delay / 100)
			{
				if (this.materialCheckbox9.Checked)
				{
					SendKeys.Send(this.materialComboBox1.SelectedItem.ToString());
				}
				this.darod = false;
				this.dadogdoing = 0;
				this.timer8.Stop();
			}
		}

		// Token: 0x06000186 RID: 390 RVA: 0x00002ABB File Offset: 0x00000CBB
		private void guna2Button4_MouseDown(object sender, MouseEventArgs e)
		{
			if (Control.MouseButtons == MouseButtons.Left)
			{
				this.guna2Button4.ForeColor = SystemColors.ButtonFace;
				this.rodserc = true;
				this.guna2Button4.Text = "...";
			}
		}

		// Token: 0x06000187 RID: 391 RVA: 0x0000E2E0 File Offset: 0x0000C4E0
		private void guna2TrackBar15_Scroll(object sender, ScrollEventArgs e)
		{
			int value = this.guna2TrackBar15.Value;
			this.timer8.Interval = value;
			this.label30.Text = "Rod Delay: " + value.ToString();
		}

		// Token: 0x06000188 RID: 392 RVA: 0x000025E5 File Offset: 0x000007E5
		private void materialCheckbox10_CheckedChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x06000189 RID: 393 RVA: 0x000025E5 File Offset: 0x000007E5
		private void username_KeyPress(object sender, KeyPressEventArgs e)
		{
		}

		// Token: 0x0600018A RID: 394 RVA: 0x000025E5 File Offset: 0x000007E5
		private void guna2TextBox1_KeyPress(object sender, KeyPressEventArgs e)
		{
		}

		// Token: 0x0600018B RID: 395 RVA: 0x0000E324 File Offset: 0x0000C524
		private void materialCheckbox10_CheckedChanged_1(object sender, EventArgs e)
		{
			if (this.materialCheckbox10.Checked)
			{
				try
				{
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "89 AD 26 3E F4 F4 F4 F4 F4 F4 F4 F4 F4");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], "21 E6 95 3E F4 F4 F4 F4 F4 F4 F4 F4 F4");
					}
					MessageBox.Show("Speed Injected!");
					return;
				}
				catch
				{
					return;
				}
			}
			try
			{
				IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), "21 E6 95 3E F4 F4 F4 F4 F4 F4 F4 F4 F4");
				for (int j = 0; j < array2.Count<IntPtr>(); j++)
				{
					this.m.WriteArray(array2[j], "89 AD 26 3E F4 F4 F4 F4 F4 F4 F4 F4 F4");
				}
				MessageBox.Show("Speed Disinjected!");
			}
			catch
			{
			}
		}

		// Token: 0x0600018C RID: 396 RVA: 0x0000E40C File Offset: 0x0000C60C
		private void materialCheckbox11_CheckedChanged(object sender, EventArgs e)
		{
			if (this.materialCheckbox11.Checked)
			{
				try
				{
					IntPtr[] array = this.m.ScanArray(this.m.GetPID("javaw"), "0F 74 DA 3C 00 00 80 BF 00 00 10 41 00");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.m.WriteArray(array[i], "0B D7 23 3D 00 00 80 BF 00 00 10 41 00");
					}
					MessageBox.Show("NameTags Injected!");
					return;
				}
				catch
				{
					return;
				}
			}
			try
			{
				IntPtr[] array2 = this.m.ScanArray(this.m.GetPID("javaw"), "0B D7 23 3D 00 00 80 BF 00 00 10 41 00");
				for (int j = 0; j < array2.Count<IntPtr>(); j++)
				{
					this.m.WriteArray(array2[j], "0F 74 DA 3C 00 00 80 BF 00 00 10 41 00");
				}
				MessageBox.Show("NameTags Disinjected!");
			}
			catch
			{
			}
		}

		// Token: 0x0600018D RID: 397 RVA: 0x00002AF2 File Offset: 0x00000CF2
		protected override void Dispose(bool bool_0)
		{
			if (bool_0 && this.components != null)
			{
				this.components.Dispose();
			}
			base.Dispose(bool_0);
		}

		// Token: 0x0600018E RID: 398 RVA: 0x0000E4F4 File Offset: 0x0000C6F4
		private void InitializeComponent()
		{
			this.components = new Container();
			Animation animation = new Animation();
			ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof(Main));
			this.guna2DragControl1 = new Guna2DragControl(this.components);
			this.AutoClicker = new System.Windows.Forms.Timer(this.components);
			this.BlockHit = new System.Windows.Forms.Timer(this.components);
			this.Jitter = new System.Windows.Forms.Timer(this.components);
			this.VersionLoad1 = new System.Windows.Forms.Timer(this.components);
			this.VersionLoad2 = new System.Windows.Forms.Timer(this.components);
			this.Bind1 = new System.Windows.Forms.Timer(this.components);
			this.Bind2 = new System.Windows.Forms.Timer(this.components);
			this.panel7 = new Panel();
			this.label19 = new Label();
			this.label20 = new Label();
			this.label21 = new Label();
			this.label22 = new Label();
			this.label18 = new Label();
			this.label17 = new Label();
			this.label16 = new Label();
			this.label15 = new Label();
			this.label14 = new Label();
			this.guna2Button7 = new Guna2Button();
			this.guna2Button6 = new Guna2Button();
			this.MinecraftVersion = new Label();
			this.toolTip1 = new ToolTip(this.components);
			this.label28 = new Label();
			this.label29 = new Label();
			this.label12 = new Label();
			this.label1 = new Label();
			this.label7 = new Label();
			this.iconButton1 = new IconButton();
			this.iconButton2 = new IconButton();
			this.iconButton3 = new IconButton();
			this.label8 = new Label();
			this.label10 = new Label();
			this.AutoClicker2 = new System.Windows.Forms.Timer(this.components);
			this.guna2Transition1 = new Guna2Transition();
			this.AuraLBL = new Label();
			this.panel1 = new Panel();
			this.panel2 = new Panel();
			this.groupBox8 = new GroupBox();
			this.materialCheckbox9 = new MaterialCheckbox();
			this.label32 = new Label();
			this.label31 = new Label();
			this.materialComboBox2 = new MaterialComboBox();
			this.materialComboBox1 = new MaterialComboBox();
			this.guna2Button4 = new Guna2Button();
			this.label30 = new Label();
			this.guna2TrackBar15 = new Guna2TrackBar();
			this.groupBox6 = new GroupBox();
			this.guna2CheckBox19 = new MaterialCheckbox();
			this.guna2TrackBar8 = new Guna2TrackBar();
			this.guna2TrackBar7 = new Guna2TrackBar();
			this.label23 = new Label();
			this.guna2TrackBar6 = new Guna2TrackBar();
			this.guna2Separator1 = new Guna2Separator();
			this.hideBind = new Guna2Button();
			this.groupBox5 = new GroupBox();
			this.guna2CheckBox17 = new MaterialCheckbox();
			this.guna2CheckBox16 = new MaterialCheckbox();
			this.logsBOX = new MaterialCheckbox();
			this.guna2CheckBox18 = new MaterialCheckbox();
			this.Regeditbox = new MaterialCheckbox();
			this.guna2CheckBox14 = new MaterialCheckbox();
			this.guna2CheckBox15 = new MaterialCheckbox();
			this.guna2CheckBox13 = new MaterialCheckbox();
			this.guna2Button14 = new Guna2Button();
			this.groupBox7 = new GroupBox();
			this.guna2Separator3 = new Guna2Separator();
			this.materialCheckbox6 = new MaterialCheckbox();
			this.materialCheckbox7 = new MaterialCheckbox();
			this.materialCheckbox8 = new MaterialCheckbox();
			this.guna2Button3 = new Guna2Button();
			this.label27 = new Label();
			this.guna2TrackBar14 = new Guna2TrackBar();
			this.materialCheckbox3 = new MaterialCheckbox();
			this.materialCheckbox4 = new MaterialCheckbox();
			this.materialCheckbox5 = new MaterialCheckbox();
			this.guna2Button2 = new Guna2Button();
			this.label9 = new Label();
			this.guna2TrackBar13 = new Guna2TrackBar();
			this.groupBox4 = new GroupBox();
			this.groupBox3 = new GroupBox();
			this.materialCheckbox1 = new MaterialCheckbox();
			this.materialCheckbox2 = new MaterialCheckbox();
			this.guna2CheckBox9 = new MaterialCheckbox();
			this.materialCheckbox_0 = new MaterialCheckbox();
			this.hitCHECK = new MaterialCheckbox();
			this.materialCheckbox_1 = new MaterialCheckbox();
			this.megaCHECKBOX = new MaterialCheckbox();
			this.lblreachlol = new Label();
			this.hitTRACK = new Guna2TrackBar();
			this.velocityTRACK = new Guna2TrackBar();
			this.guna2TrackBar_0 = new Guna2TrackBar();
			this.groupBox2 = new GroupBox();
			this.guna2CheckBox23 = new MaterialCheckbox();
			this.guna2CheckBox22 = new MaterialCheckbox();
			this.label25 = new Label();
			this.guna2TrackBar11 = new Guna2TrackBar();
			this.label26 = new Label();
			this.guna2TrackBar12 = new Guna2TrackBar();
			this.guna2Button20 = new Guna2Button();
			this.label13 = new Label();
			this.guna2TrackBar9 = new Guna2TrackBar();
			this.guna2Button21 = new Guna2Button();
			this.label24 = new Label();
			this.guna2TrackBar10 = new Guna2TrackBar();
			this.label6 = new Label();
			this.guna2TrackBar4 = new Guna2TrackBar();
			this.label5 = new Label();
			this.guna2TrackBar3 = new Guna2TrackBar();
			this.label4 = new Label();
			this.label3 = new Label();
			this.guna2TrackBar2 = new Guna2TrackBar();
			this.groupBox1 = new GroupBox();
			this.guna2CheckBox10 = new MaterialCheckbox();
			this.guna2CheckBox11 = new MaterialCheckbox();
			this.guna2CheckBox12 = new MaterialCheckbox();
			this.guna2CheckBox20 = new MaterialCheckbox();
			this.guna2CheckBox21 = new MaterialCheckbox();
			this.guna2Button5 = new Guna2Button();
			this.label11 = new Label();
			this.guna2TrackBar5 = new Guna2TrackBar();
			this.guna2CheckBox6 = new MaterialCheckbox();
			this.guna2CheckBox7 = new MaterialCheckbox();
			this.guna2CheckBox8 = new MaterialCheckbox();
			this.guna2CheckBox5 = new MaterialCheckbox();
			this.guna2CheckBox4 = new MaterialCheckbox();
			this.guna2CheckBox3 = new MaterialCheckbox();
			this.guna2CheckBox2 = new MaterialCheckbox();
			this.guna2CheckBox1 = new MaterialCheckbox();
			this.guna2Button1 = new Guna2Button();
			this.label2 = new Label();
			this.guna2TrackBar1 = new Guna2TrackBar();
			this.guna2Separator4 = new Guna2Separator();
			this.guna2Separator2 = new Guna2Separator();
			this.color = new System.Windows.Forms.Timer(this.components);
			this.timerR = new System.Windows.Forms.Timer(this.components);
			this.timerG = new System.Windows.Forms.Timer(this.components);
			this.timerB = new System.Windows.Forms.Timer(this.components);
			this.backgroundWorker1 = new BackgroundWorker();
			this.timer1 = new System.Windows.Forms.Timer(this.components);
			this.timer2 = new System.Windows.Forms.Timer(this.components);
			this.timer3 = new System.Windows.Forms.Timer(this.components);
			this.timer4 = new System.Windows.Forms.Timer(this.components);
			this.kryptonPalette1 = new KryptonPalette(this.components);
			this.timer5 = new System.Windows.Forms.Timer(this.components);
			this.timer6 = new System.Windows.Forms.Timer(this.components);
			this.timer7 = new System.Windows.Forms.Timer(this.components);
			this.timer8 = new System.Windows.Forms.Timer(this.components);
			this.materialCheckbox10 = new MaterialCheckbox();
			this.materialCheckbox11 = new MaterialCheckbox();
			this.panel7.SuspendLayout();
			this.panel1.SuspendLayout();
			this.panel2.SuspendLayout();
			this.groupBox8.SuspendLayout();
			this.groupBox6.SuspendLayout();
			this.groupBox5.SuspendLayout();
			this.groupBox7.SuspendLayout();
			this.groupBox4.SuspendLayout();
			this.groupBox3.SuspendLayout();
			this.groupBox2.SuspendLayout();
			this.groupBox1.SuspendLayout();
			base.SuspendLayout();
			this.guna2DragControl1.TargetControl = this;
			this.AutoClicker.Interval = 1;
			this.AutoClicker.Tick += this.AutoClicker_Tick;
			this.BlockHit.Tick += this.BlockHit_Tick;
			this.Jitter.Tick += this.Jitter_Tick;
			this.VersionLoad1.Enabled = true;
			this.VersionLoad1.Tick += this.VersionLoad1_Tick;
			this.VersionLoad2.Enabled = true;
			this.VersionLoad2.Tick += this.VersionLoad2_Tick;
			this.Bind1.Enabled = true;
			this.Bind1.Interval = 1;
			this.Bind1.Tick += this.Bind1_Tick;
			this.Bind2.Enabled = true;
			this.Bind2.Interval = 1;
			this.Bind2.Tick += this.Bind2_Tick;
			this.panel7.BackColor = Color.FromArgb(250, 252, 250);
			this.panel7.Controls.Add(this.label19);
			this.panel7.Controls.Add(this.label20);
			this.panel7.Controls.Add(this.label21);
			this.panel7.Controls.Add(this.label22);
			this.panel7.Controls.Add(this.label18);
			this.panel7.Controls.Add(this.label17);
			this.panel7.Controls.Add(this.label16);
			this.panel7.Controls.Add(this.label15);
			this.panel7.Controls.Add(this.label14);
			this.guna2Transition1.SetDecoration(this.panel7, 0);
			this.panel7.Dock = DockStyle.Fill;
			this.panel7.Location = new Point(0, 0);
			this.panel7.Name = "panel7";
			this.panel7.Size = new Size(1068, 398);
			this.panel7.TabIndex = 68;
			this.panel7.Visible = false;
			this.label19.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label19, 0);
			this.label19.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label19.ForeColor = Color.FromArgb(64, 64, 64);
			this.label19.Location = new Point(11, 233);
			this.label19.Margin = new Padding(2, 0, 2, 0);
			this.label19.Name = "label19";
			this.label19.Size = new Size(83, 19);
			this.label19.TabIndex = 86;
			this.label19.Text = "registerdate";
			this.label20.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label20, 0);
			this.label20.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label20.ForeColor = Color.FromArgb(64, 64, 64);
			this.label20.Location = new Point(11, 106);
			this.label20.Margin = new Padding(2, 0, 2, 0);
			this.label20.Name = "label20";
			this.label20.Size = new Size(63, 19);
			this.label20.TabIndex = 85;
			this.label20.Text = "lastlogin";
			this.label21.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label21, 0);
			this.label21.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label21.ForeColor = Color.FromArgb(64, 64, 64);
			this.label21.Location = new Point(11, 81);
			this.label21.Margin = new Padding(2, 0, 2, 0);
			this.label21.Name = "label21";
			this.label21.Size = new Size(31, 19);
			this.label21.TabIndex = 84;
			this.label21.Text = "exp";
			this.label22.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label22, 0);
			this.label22.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label22.ForeColor = Color.FromArgb(64, 64, 64);
			this.label22.Location = new Point(11, 156);
			this.label22.Margin = new Padding(2, 0, 2, 0);
			this.label22.Name = "label22";
			this.label22.Size = new Size(21, 19);
			this.label22.TabIndex = 83;
			this.label22.Text = "ip";
			this.label18.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label18, 0);
			this.label18.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label18.ForeColor = Color.FromArgb(64, 64, 64);
			this.label18.Location = new Point(11, 132);
			this.label18.Margin = new Padding(2, 0, 2, 0);
			this.label18.Name = "label18";
			this.label18.Size = new Size(58, 19);
			this.label18.TabIndex = 81;
			this.label18.Text = "variable";
			this.label17.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label17, 0);
			this.label17.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label17.ForeColor = Color.FromArgb(64, 64, 64);
			this.label17.Location = new Point(11, 207);
			this.label17.Margin = new Padding(2, 0, 2, 0);
			this.label17.Name = "label17";
			this.label17.Size = new Size(40, 19);
			this.label17.TabIndex = 80;
			this.label17.Text = "hwid";
			this.label16.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label16, 0);
			this.label16.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label16.ForeColor = Color.FromArgb(64, 64, 64);
			this.label16.Location = new Point(11, 182);
			this.label16.Margin = new Padding(2, 0, 2, 0);
			this.label16.Name = "label16";
			this.label16.Size = new Size(43, 19);
			this.label16.TabIndex = 79;
			this.label16.Text = "email";
			this.label15.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label15, 0);
			this.label15.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label15.ForeColor = Color.FromArgb(64, 64, 64);
			this.label15.Location = new Point(11, 55);
			this.label15.Margin = new Padding(2, 0, 2, 0);
			this.label15.Name = "label15";
			this.label15.Size = new Size(69, 19);
			this.label15.TabIndex = 78;
			this.label15.Text = "username";
			this.label14.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label14, 0);
			this.label14.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label14.ForeColor = Color.FromArgb(64, 64, 64);
			this.label14.Location = new Point(11, 30);
			this.label14.Margin = new Padding(2, 0, 2, 0);
			this.label14.Name = "label14";
			this.label14.Size = new Size(47, 19);
			this.label14.TabIndex = 77;
			this.label14.Text = "userid";
			this.guna2Button7.Animated = true;
			this.guna2Button7.BorderRadius = 6;
			this.guna2Button7.CheckedState.Parent = this.guna2Button7;
			this.guna2Button7.CustomImages.Parent = this.guna2Button7;
			this.guna2Transition1.SetDecoration(this.guna2Button7, 0);
			this.guna2Button7.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button7.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button7.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button7.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button7.DisabledState.Parent = this.guna2Button7;
			this.guna2Button7.FillColor = Color.Transparent;
			this.guna2Button7.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button7.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button7.HoverState.Parent = this.guna2Button7;
			this.guna2Button7.Location = new Point(6, 13);
			this.guna2Button7.Name = "guna2Button7";
			this.guna2Button7.PressedColor = Color.FromArgb(25, 25, 25);
			this.guna2Button7.ShadowDecoration.Parent = this.guna2Button7;
			this.guna2Button7.Size = new Size(169, 31);
			this.guna2Button7.TabIndex = 7;
			this.guna2Button7.Text = "Save Custom Settings";
			this.guna2Button7.Click += this.guna2Button7_Click;
			this.guna2Button6.Animated = true;
			this.guna2Button6.BorderRadius = 6;
			this.guna2Button6.CheckedState.Parent = this.guna2Button6;
			this.guna2Button6.CustomImages.Parent = this.guna2Button6;
			this.guna2Transition1.SetDecoration(this.guna2Button6, 0);
			this.guna2Button6.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button6.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button6.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button6.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button6.DisabledState.Parent = this.guna2Button6;
			this.guna2Button6.FillColor = Color.Transparent;
			this.guna2Button6.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button6.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button6.HoverState.Parent = this.guna2Button6;
			this.guna2Button6.Location = new Point(6, 47);
			this.guna2Button6.Name = "guna2Button6";
			this.guna2Button6.PressedColor = Color.FromArgb(25, 25, 25);
			this.guna2Button6.ShadowDecoration.Parent = this.guna2Button6;
			this.guna2Button6.Size = new Size(169, 31);
			this.guna2Button6.TabIndex = 6;
			this.guna2Button6.Text = "Load Custom Settings";
			this.guna2Button6.Click += this.guna2Button6_Click;
			this.MinecraftVersion.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.MinecraftVersion, 0);
			this.MinecraftVersion.Font = new Font("Yu Gothic", 10.25f, FontStyle.Bold);
			this.MinecraftVersion.ForeColor = Color.FromArgb(64, 64, 64);
			this.MinecraftVersion.Location = new Point(2, 472);
			this.MinecraftVersion.Margin = new Padding(2, 0, 2, 0);
			this.MinecraftVersion.Name = "MinecraftVersion";
			this.MinecraftVersion.Size = new Size(58, 18);
			this.MinecraftVersion.TabIndex = 41;
			this.MinecraftVersion.Text = "Version";
			this.label28.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label28, 0);
			this.label28.ForeColor = Color.Gray;
			this.label28.Location = new Point(276, 86);
			this.label28.Name = "label28";
			this.label28.Size = new Size(21, 14);
			this.label28.TabIndex = 116;
			this.label28.Text = "[?]";
			this.toolTip1.SetToolTip(this.label28, "Allowing Autoclicking Up to 30 CPS");
			this.label29.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label29, 0);
			this.label29.ForeColor = Color.Gray;
			this.label29.Location = new Point(173, 124);
			this.label29.Name = "label29";
			this.label29.Size = new Size(21, 14);
			this.label29.TabIndex = 115;
			this.label29.Text = "[?]";
			this.toolTip1.SetToolTip(this.label29, "only allow auto click while moving");
			this.label12.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label12, 0);
			this.label12.ForeColor = Color.Gray;
			this.label12.Location = new Point(335, 314);
			this.label12.Name = "label12";
			this.label12.Size = new Size(21, 14);
			this.label12.TabIndex = 130;
			this.label12.Text = "[?]";
			this.toolTip1.SetToolTip(this.label12, "Disabling the Autoclicker If LeftShift on Hold (Needed for Speed Bridgers)");
			this.label1.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label1, 0);
			this.label1.ForeColor = Color.Gray;
			this.label1.Location = new Point(299, 272);
			this.label1.Name = "label1";
			this.label1.Size = new Size(21, 14);
			this.label1.TabIndex = 129;
			this.label1.Text = "[?]";
			this.toolTip1.SetToolTip(this.label1, "Allowing Autoclicking Up to 30 CPS");
			this.label7.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label7, 0);
			this.label7.ForeColor = Color.Gray;
			this.label7.Location = new Point(185, 312);
			this.label7.Name = "label7";
			this.label7.Size = new Size(21, 14);
			this.label7.TabIndex = 128;
			this.label7.Text = "[?]";
			this.toolTip1.SetToolTip(this.label7, "only allow auto click while moving");
			this.guna2Transition1.SetDecoration(this.iconButton1, 0);
			this.iconButton1.FlatAppearance.BorderSize = 0;
			this.iconButton1.FlatStyle = FlatStyle.Flat;
			this.iconButton1.IconChar = 61827;
			this.iconButton1.IconColor = Color.Black;
			this.iconButton1.IconFont = 0;
			this.iconButton1.Location = new Point(364, 434);
			this.iconButton1.Name = "iconButton1";
			this.iconButton1.Size = new Size(98, 56);
			this.iconButton1.TabIndex = 50;
			this.toolTip1.SetToolTip(this.iconButton1, "Modules");
			this.iconButton1.UseVisualStyleBackColor = true;
			this.iconButton1.Click += this.iconButton1_Click;
			this.guna2Transition1.SetDecoration(this.iconButton2, 0);
			this.iconButton2.FlatAppearance.BorderSize = 0;
			this.iconButton2.FlatStyle = FlatStyle.Flat;
			this.iconButton2.IconChar = 61648;
			this.iconButton2.IconColor = Color.Black;
			this.iconButton2.IconFont = 0;
			this.iconButton2.Location = new Point(506, 434);
			this.iconButton2.Name = "iconButton2";
			this.iconButton2.Size = new Size(98, 56);
			this.iconButton2.TabIndex = 78;
			this.toolTip1.SetToolTip(this.iconButton2, "Misc");
			this.iconButton2.UseVisualStyleBackColor = true;
			this.iconButton2.Click += this.iconButton2_Click;
			this.guna2Transition1.SetDecoration(this.iconButton3, 0);
			this.iconButton3.FlatAppearance.BorderSize = 0;
			this.iconButton3.FlatStyle = FlatStyle.Flat;
			this.iconButton3.IconChar = 63092;
			this.iconButton3.IconColor = Color.Black;
			this.iconButton3.IconFont = 0;
			this.iconButton3.Location = new Point(636, 434);
			this.iconButton3.Name = "iconButton3";
			this.iconButton3.Size = new Size(98, 56);
			this.iconButton3.TabIndex = 79;
			this.toolTip1.SetToolTip(this.iconButton3, "Account");
			this.iconButton3.UseVisualStyleBackColor = true;
			this.iconButton3.Click += this.iconButton3_Click;
			this.label8.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label8, 0);
			this.label8.ForeColor = Color.Gray;
			this.label8.Location = new Point(173, 114);
			this.label8.Name = "label8";
			this.label8.Size = new Size(21, 14);
			this.label8.TabIndex = 124;
			this.label8.Text = "[?]";
			this.toolTip1.SetToolTip(this.label8, "only allow double click while moving");
			this.label10.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label10, 0);
			this.label10.ForeColor = Color.Gray;
			this.label10.Location = new Point(173, 307);
			this.label10.Name = "label10";
			this.label10.Size = new Size(21, 14);
			this.label10.TabIndex = 131;
			this.label10.Text = "[?]";
			this.toolTip1.SetToolTip(this.label10, "only allow double click while moving");
			this.AutoClicker2.Interval = 1;
			this.AutoClicker2.Tick += this.AutoClicker2_Tick;
			this.guna2Transition1.AnimationType = 8;
			this.guna2Transition1.Cursor = null;
			animation.AnimateOnlyDifferences = true;
			animation.BlindCoeff = (PointF)componentResourceManager.GetObject("animation5.BlindCoeff");
			animation.LeafCoeff = 0f;
			animation.MaxTime = 1f;
			animation.MinTime = 0f;
			animation.MosaicCoeff = (PointF)componentResourceManager.GetObject("animation5.MosaicCoeff");
			animation.MosaicShift = (PointF)componentResourceManager.GetObject("animation5.MosaicShift");
			animation.MosaicSize = 0;
			animation.Padding = new Padding(0);
			animation.RotateCoeff = 0f;
			animation.RotateLimit = 0f;
			animation.ScaleCoeff = (PointF)componentResourceManager.GetObject("animation5.ScaleCoeff");
			animation.SlideCoeff = (PointF)componentResourceManager.GetObject("animation5.SlideCoeff");
			animation.TimeCoeff = 0f;
			animation.TransparencyCoeff = 1f;
			this.guna2Transition1.DefaultAnimation = animation;
			this.guna2Transition1.Interval = 1;
			this.guna2Transition1.MaxAnimationTime = 140;
			this.guna2Transition1.TimeStep = 0.01f;
			this.AuraLBL.AutoSize = true;
			this.AuraLBL.Cursor = Cursors.Hand;
			this.guna2Transition1.SetDecoration(this.AuraLBL, 0);
			this.AuraLBL.Font = new Font("Yu Gothic", 15f, FontStyle.Bold);
			this.AuraLBL.ForeColor = Color.FromArgb(64, 64, 64);
			this.AuraLBL.Location = new Point(16, -3);
			this.AuraLBL.Margin = new Padding(2, 0, 2, 0);
			this.AuraLBL.Name = "AuraLBL";
			this.AuraLBL.Size = new Size(136, 26);
			this.AuraLBL.TabIndex = 36;
			this.AuraLBL.Text = "Aura | Client";
			this.AuraLBL.Click += this.AuraLBL_Click;
			this.panel1.BackColor = Color.FromArgb(250, 252, 250);
			this.panel1.Controls.Add(this.panel2);
			this.panel1.Controls.Add(this.groupBox7);
			this.panel1.Controls.Add(this.groupBox4);
			this.panel1.Controls.Add(this.groupBox3);
			this.panel1.Controls.Add(this.groupBox2);
			this.panel1.Controls.Add(this.groupBox1);
			this.guna2Transition1.SetDecoration(this.panel1, 0);
			this.panel1.Location = new Point(5, 27);
			this.panel1.Name = "panel1";
			this.panel1.Size = new Size(1068, 398);
			this.panel1.TabIndex = 80;
			this.panel2.Controls.Add(this.panel7);
			this.panel2.Controls.Add(this.groupBox8);
			this.panel2.Controls.Add(this.groupBox6);
			this.panel2.Controls.Add(this.groupBox5);
			this.guna2Transition1.SetDecoration(this.panel2, 0);
			this.panel2.Dock = DockStyle.Fill;
			this.panel2.Location = new Point(0, 0);
			this.panel2.Name = "panel2";
			this.panel2.Size = new Size(1068, 398);
			this.panel2.TabIndex = 136;
			this.panel2.Visible = false;
			this.panel2.Paint += this.panel2_Paint_1;
			this.groupBox8.BackColor = Color.FromArgb(250, 252, 252);
			this.groupBox8.Controls.Add(this.materialCheckbox9);
			this.groupBox8.Controls.Add(this.label32);
			this.groupBox8.Controls.Add(this.label31);
			this.groupBox8.Controls.Add(this.materialComboBox2);
			this.groupBox8.Controls.Add(this.materialComboBox1);
			this.groupBox8.Controls.Add(this.guna2Button4);
			this.groupBox8.Controls.Add(this.label30);
			this.groupBox8.Controls.Add(this.guna2TrackBar15);
			this.guna2Transition1.SetDecoration(this.groupBox8, 0);
			this.groupBox8.Font = new Font("Yu Gothic", 8.25f);
			this.groupBox8.Location = new Point(359, 37);
			this.groupBox8.Name = "groupBox8";
			this.groupBox8.Size = new Size(320, 246);
			this.groupBox8.TabIndex = 99;
			this.groupBox8.TabStop = false;
			this.groupBox8.Text = "Auto Rod";
			this.materialCheckbox9.AutoSize = true;
			this.materialCheckbox9.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.materialCheckbox9, 0);
			this.materialCheckbox9.Depth = 0;
			this.materialCheckbox9.Location = new Point(22, 197);
			this.materialCheckbox9.Margin = new Padding(0);
			this.materialCheckbox9.MouseLocation = new Point(-1, -1);
			this.materialCheckbox9.MouseState = 0;
			this.materialCheckbox9.Name = "materialCheckbox9";
			this.materialCheckbox9.Ripple = true;
			this.materialCheckbox9.Size = new Size(137, 37);
			this.materialCheckbox9.TabIndex = 138;
			this.materialCheckbox9.Text = "Back to Sword";
			this.materialCheckbox9.UseVisualStyleBackColor = false;
			this.label32.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label32, 0);
			this.label32.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label32.ForeColor = Color.FromArgb(64, 64, 64);
			this.label32.Location = new Point(173, 103);
			this.label32.Margin = new Padding(2, 0, 2, 0);
			this.label32.Name = "label32";
			this.label32.Size = new Size(37, 19);
			this.label32.TabIndex = 137;
			this.label32.Text = "Rod:";
			this.label31.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label31, 0);
			this.label31.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label31.ForeColor = Color.FromArgb(64, 64, 64);
			this.label31.Location = new Point(18, 103);
			this.label31.Margin = new Padding(2, 0, 2, 0);
			this.label31.Name = "label31";
			this.label31.Size = new Size(52, 19);
			this.label31.TabIndex = 136;
			this.label31.Text = "Sword:";
			this.materialComboBox2.AutoResize = false;
			this.materialComboBox2.BackColor = Color.FromArgb(255, 255, 255);
			this.guna2Transition1.SetDecoration(this.materialComboBox2, 0);
			this.materialComboBox2.Depth = 0;
			this.materialComboBox2.DrawMode = DrawMode.OwnerDrawVariable;
			this.materialComboBox2.DropDownHeight = 174;
			this.materialComboBox2.DropDownStyle = ComboBoxStyle.DropDownList;
			this.materialComboBox2.DropDownWidth = 121;
			this.materialComboBox2.Font = new Font("Microsoft Sans Serif", 14f, FontStyle.Bold, GraphicsUnit.Pixel);
			this.materialComboBox2.ForeColor = Color.FromArgb(222, 0, 0, 0);
			this.materialComboBox2.FormattingEnabled = true;
			this.materialComboBox2.IntegralHeight = false;
			this.materialComboBox2.ItemHeight = 43;
			this.materialComboBox2.Items.AddRange(new object[]
			{
				"1",
				"2",
				"3",
				"4",
				"5",
				"6",
				"7",
				"8",
				"9"
			});
			this.materialComboBox2.Location = new Point(177, 130);
			this.materialComboBox2.MaxDropDownItems = 4;
			this.materialComboBox2.MouseState = 2;
			this.materialComboBox2.Name = "materialComboBox2";
			this.materialComboBox2.Size = new Size(121, 49);
			this.materialComboBox2.StartIndex = 0;
			this.materialComboBox2.TabIndex = 135;
			this.materialComboBox1.AutoResize = false;
			this.materialComboBox1.BackColor = Color.FromArgb(255, 255, 255);
			this.guna2Transition1.SetDecoration(this.materialComboBox1, 0);
			this.materialComboBox1.Depth = 0;
			this.materialComboBox1.DrawMode = DrawMode.OwnerDrawVariable;
			this.materialComboBox1.DropDownHeight = 174;
			this.materialComboBox1.DropDownStyle = ComboBoxStyle.DropDownList;
			this.materialComboBox1.DropDownWidth = 121;
			this.materialComboBox1.Font = new Font("Microsoft Sans Serif", 14f, FontStyle.Bold, GraphicsUnit.Pixel);
			this.materialComboBox1.ForeColor = Color.FromArgb(222, 0, 0, 0);
			this.materialComboBox1.FormattingEnabled = true;
			this.materialComboBox1.IntegralHeight = false;
			this.materialComboBox1.ItemHeight = 43;
			this.materialComboBox1.Items.AddRange(new object[]
			{
				"1",
				"2",
				"3",
				"4",
				"5",
				"6",
				"7",
				"8",
				"9"
			});
			this.materialComboBox1.Location = new Point(22, 131);
			this.materialComboBox1.MaxDropDownItems = 4;
			this.materialComboBox1.MouseState = 2;
			this.materialComboBox1.Name = "materialComboBox1";
			this.materialComboBox1.Size = new Size(121, 49);
			this.materialComboBox1.StartIndex = 0;
			this.materialComboBox1.TabIndex = 134;
			this.guna2Button4.Animated = true;
			this.guna2Button4.BorderColor = Color.FromArgb(64, 64, 64);
			this.guna2Button4.CheckedState.Parent = this.guna2Button4;
			this.guna2Button4.CustomImages.Parent = this.guna2Button4;
			this.guna2Transition1.SetDecoration(this.guna2Button4, 0);
			this.guna2Button4.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button4.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button4.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button4.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button4.DisabledState.Parent = this.guna2Button4;
			this.guna2Button4.FillColor = Color.Transparent;
			this.guna2Button4.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button4.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button4.HoverState.Parent = this.guna2Button4;
			this.guna2Button4.Location = new Point(232, 57);
			this.guna2Button4.Name = "guna2Button4";
			this.guna2Button4.PressedColor = Color.Transparent;
			this.guna2Button4.ShadowDecoration.Parent = this.guna2Button4;
			this.guna2Button4.Size = new Size(66, 25);
			this.guna2Button4.TabIndex = 133;
			this.guna2Button4.Text = "Bind";
			this.guna2Button4.MouseDown += this.guna2Button4_MouseDown;
			this.label30.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label30, 0);
			this.label30.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label30.ForeColor = Color.FromArgb(64, 64, 64);
			this.label30.Location = new Point(18, 37);
			this.label30.Margin = new Padding(2, 0, 2, 0);
			this.label30.Name = "label30";
			this.label30.Size = new Size(96, 19);
			this.label30.TabIndex = 132;
			this.label30.Text = "Rod Delay: 25";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar15, 0);
			this.guna2TrackBar15.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar15.HoverState.Parent = this.guna2TrackBar15;
			this.guna2TrackBar15.Location = new Point(22, 59);
			this.guna2TrackBar15.Maximum = 50;
			this.guna2TrackBar15.Minimum = 1;
			this.guna2TrackBar15.Name = "guna2TrackBar15";
			this.guna2TrackBar15.Size = new Size(199, 23);
			this.guna2TrackBar15.TabIndex = 131;
			this.guna2TrackBar15.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar15.Value = 25;
			this.guna2TrackBar15.Scroll += this.guna2TrackBar15_Scroll;
			this.groupBox6.BackColor = Color.FromArgb(250, 252, 252);
			this.groupBox6.Controls.Add(this.guna2CheckBox19);
			this.groupBox6.Controls.Add(this.guna2TrackBar8);
			this.groupBox6.Controls.Add(this.guna2TrackBar7);
			this.groupBox6.Controls.Add(this.label23);
			this.groupBox6.Controls.Add(this.guna2TrackBar6);
			this.groupBox6.Controls.Add(this.guna2Separator1);
			this.groupBox6.Controls.Add(this.hideBind);
			this.guna2Transition1.SetDecoration(this.groupBox6, 0);
			this.groupBox6.Font = new Font("Yu Gothic", 8.25f);
			this.groupBox6.Location = new Point(710, 41);
			this.groupBox6.Name = "groupBox6";
			this.groupBox6.Size = new Size(349, 240);
			this.groupBox6.TabIndex = 1;
			this.groupBox6.TabStop = false;
			this.groupBox6.Text = "Settings";
			this.guna2CheckBox19.AutoSize = true;
			this.guna2CheckBox19.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox19, 0);
			this.guna2CheckBox19.Depth = 0;
			this.guna2CheckBox19.Location = new Point(12, 195);
			this.guna2CheckBox19.Margin = new Padding(0);
			this.guna2CheckBox19.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox19.MouseState = 0;
			this.guna2CheckBox19.Name = "guna2CheckBox19";
			this.guna2CheckBox19.Ripple = true;
			this.guna2CheckBox19.Size = new Size(97, 37);
			this.guna2CheckBox19.TabIndex = 88;
			this.guna2CheckBox19.Text = "Rainbow";
			this.guna2CheckBox19.UseVisualStyleBackColor = false;
			this.guna2CheckBox19.CheckedChanged += this.guna2CheckBox19_CheckedChanged;
			this.guna2Transition1.SetDecoration(this.guna2TrackBar8, 0);
			this.guna2TrackBar8.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar8.HoverState.Parent = this.guna2TrackBar8;
			this.guna2TrackBar8.Location = new Point(21, 166);
			this.guna2TrackBar8.Maximum = 255;
			this.guna2TrackBar8.Name = "guna2TrackBar8";
			this.guna2TrackBar8.Size = new Size(310, 23);
			this.guna2TrackBar8.TabIndex = 84;
			this.guna2TrackBar8.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar8.Value = 249;
			this.guna2Transition1.SetDecoration(this.guna2TrackBar7, 0);
			this.guna2TrackBar7.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar7.HoverState.Parent = this.guna2TrackBar7;
			this.guna2TrackBar7.Location = new Point(21, 129);
			this.guna2TrackBar7.Maximum = 255;
			this.guna2TrackBar7.Name = "guna2TrackBar7";
			this.guna2TrackBar7.Size = new Size(310, 23);
			this.guna2TrackBar7.TabIndex = 83;
			this.guna2TrackBar7.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar7.Value = 126;
			this.label23.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label23, 0);
			this.label23.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label23.ForeColor = Color.FromArgb(64, 64, 64);
			this.label23.Location = new Point(27, 30);
			this.label23.Margin = new Padding(2, 0, 2, 0);
			this.label23.Name = "label23";
			this.label23.Size = new Size(98, 19);
			this.label23.TabIndex = 87;
			this.label23.Text = "Hide Window:";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar6, 0);
			this.guna2TrackBar6.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar6.HoverState.Parent = this.guna2TrackBar6;
			this.guna2TrackBar6.Location = new Point(22, 93);
			this.guna2TrackBar6.Maximum = 255;
			this.guna2TrackBar6.Name = "guna2TrackBar6";
			this.guna2TrackBar6.Size = new Size(310, 23);
			this.guna2TrackBar6.TabIndex = 82;
			this.guna2TrackBar6.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar6.Value = 0;
			this.guna2Transition1.SetDecoration(this.guna2Separator1, 0);
			this.guna2Separator1.FillColor = Color.FromArgb(68, 104, 174);
			this.guna2Separator1.Location = new Point(6, 59);
			this.guna2Separator1.Name = "guna2Separator1";
			this.guna2Separator1.Size = new Size(335, 10);
			this.guna2Separator1.TabIndex = 86;
			this.hideBind.Animated = true;
			this.hideBind.BorderRadius = 6;
			this.hideBind.CheckedState.Parent = this.hideBind;
			this.hideBind.CustomImages.Parent = this.hideBind;
			this.guna2Transition1.SetDecoration(this.hideBind, 0);
			this.hideBind.DisabledState.BorderColor = Color.DarkGray;
			this.hideBind.DisabledState.CustomBorderColor = Color.DarkGray;
			this.hideBind.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.hideBind.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.hideBind.DisabledState.Parent = this.hideBind;
			this.hideBind.FillColor = Color.Transparent;
			this.hideBind.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.hideBind.ForeColor = Color.FromArgb(64, 64, 64);
			this.hideBind.HoverState.Parent = this.hideBind;
			this.hideBind.Location = new Point(130, 24);
			this.hideBind.Name = "hideBind";
			this.hideBind.PressedColor = Color.FromArgb(25, 25, 25);
			this.hideBind.ShadowDecoration.Parent = this.hideBind;
			this.hideBind.Size = new Size(72, 31);
			this.hideBind.TabIndex = 85;
			this.hideBind.Text = "Bind";
			this.hideBind.MouseDown += this.hideBind_MouseDown_1;
			this.groupBox5.BackColor = Color.FromArgb(250, 252, 252);
			this.groupBox5.Controls.Add(this.guna2CheckBox17);
			this.groupBox5.Controls.Add(this.guna2CheckBox16);
			this.groupBox5.Controls.Add(this.logsBOX);
			this.groupBox5.Controls.Add(this.guna2CheckBox18);
			this.groupBox5.Controls.Add(this.Regeditbox);
			this.groupBox5.Controls.Add(this.guna2CheckBox14);
			this.groupBox5.Controls.Add(this.guna2CheckBox15);
			this.groupBox5.Controls.Add(this.guna2CheckBox13);
			this.groupBox5.Controls.Add(this.guna2Button14);
			this.guna2Transition1.SetDecoration(this.groupBox5, 0);
			this.groupBox5.Font = new Font("Yu Gothic", 8.25f);
			this.groupBox5.Location = new Point(9, 29);
			this.groupBox5.Name = "groupBox5";
			this.groupBox5.Size = new Size(320, 252);
			this.groupBox5.TabIndex = 0;
			this.groupBox5.TabStop = false;
			this.groupBox5.Text = "Self Destruct";
			this.guna2CheckBox17.AutoSize = true;
			this.guna2CheckBox17.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox17, 0);
			this.guna2CheckBox17.Depth = 0;
			this.guna2CheckBox17.Location = new Point(6, 199);
			this.guna2CheckBox17.Margin = new Padding(0);
			this.guna2CheckBox17.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox17.MouseState = 0;
			this.guna2CheckBox17.Name = "guna2CheckBox17";
			this.guna2CheckBox17.Ripple = true;
			this.guna2CheckBox17.Size = new Size(127, 37);
			this.guna2CheckBox17.TabIndex = 98;
			this.guna2CheckBox17.Text = "Clean Recent";
			this.guna2CheckBox17.UseVisualStyleBackColor = false;
			this.guna2CheckBox16.AutoSize = true;
			this.guna2CheckBox16.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox16, 0);
			this.guna2CheckBox16.Depth = 0;
			this.guna2CheckBox16.Location = new Point(159, 199);
			this.guna2CheckBox16.Margin = new Padding(0);
			this.guna2CheckBox16.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox16.MouseState = 0;
			this.guna2CheckBox16.Name = "guna2CheckBox16";
			this.guna2CheckBox16.Ripple = true;
			this.guna2CheckBox16.Size = new Size(148, 37);
			this.guna2CheckBox16.TabIndex = 97;
			this.guna2CheckBox16.Text = "Restart Services";
			this.guna2CheckBox16.UseVisualStyleBackColor = false;
			this.logsBOX.AutoSize = true;
			this.logsBOX.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.logsBOX, 0);
			this.logsBOX.Depth = 0;
			this.logsBOX.Location = new Point(159, 159);
			this.logsBOX.Margin = new Padding(0);
			this.logsBOX.MouseLocation = new Point(-1, -1);
			this.logsBOX.MouseState = 0;
			this.logsBOX.Name = "logsBOX";
			this.logsBOX.Ripple = true;
			this.logsBOX.Size = new Size(110, 37);
			this.logsBOX.TabIndex = 96;
			this.logsBOX.Text = "Clear Logs";
			this.logsBOX.UseVisualStyleBackColor = false;
			this.guna2CheckBox18.AutoSize = true;
			this.guna2CheckBox18.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox18, 0);
			this.guna2CheckBox18.Depth = 0;
			this.guna2CheckBox18.Location = new Point(159, 119);
			this.guna2CheckBox18.Margin = new Padding(0);
			this.guna2CheckBox18.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox18.MouseState = 0;
			this.guna2CheckBox18.Name = "guna2CheckBox18";
			this.guna2CheckBox18.Ripple = true;
			this.guna2CheckBox18.Size = new Size(117, 37);
			this.guna2CheckBox18.TabIndex = 95;
			this.guna2CheckBox18.Text = "L.A.V Crash";
			this.guna2CheckBox18.UseVisualStyleBackColor = false;
			this.Regeditbox.AutoSize = true;
			this.Regeditbox.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.Regeditbox, 0);
			this.Regeditbox.Depth = 0;
			this.Regeditbox.Location = new Point(6, 159);
			this.Regeditbox.Margin = new Padding(0);
			this.Regeditbox.MouseLocation = new Point(-1, -1);
			this.Regeditbox.MouseState = 0;
			this.Regeditbox.Name = "Regeditbox";
			this.Regeditbox.Ripple = true;
			this.Regeditbox.Size = new Size(132, 37);
			this.Regeditbox.TabIndex = 94;
			this.Regeditbox.Text = "Clean Regedit";
			this.Regeditbox.UseVisualStyleBackColor = false;
			this.guna2CheckBox14.AutoSize = true;
			this.guna2CheckBox14.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox14, 0);
			this.guna2CheckBox14.Depth = 0;
			this.guna2CheckBox14.Location = new Point(6, 119);
			this.guna2CheckBox14.Margin = new Padding(0);
			this.guna2CheckBox14.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox14.MouseState = 0;
			this.guna2CheckBox14.Name = "guna2CheckBox14";
			this.guna2CheckBox14.Ripple = true;
			this.guna2CheckBox14.Size = new Size(138, 37);
			this.guna2CheckBox14.TabIndex = 93;
			this.guna2CheckBox14.Text = "Clean Prefetch";
			this.guna2CheckBox14.UseVisualStyleBackColor = false;
			this.guna2CheckBox15.AutoSize = true;
			this.guna2CheckBox15.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox15, 0);
			this.guna2CheckBox15.Depth = 0;
			this.guna2CheckBox15.Location = new Point(159, 79);
			this.guna2CheckBox15.Margin = new Padding(0);
			this.guna2CheckBox15.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox15.MouseState = 0;
			this.guna2CheckBox15.Name = "guna2CheckBox15";
			this.guna2CheckBox15.Ripple = true;
			this.guna2CheckBox15.Size = new Size(132, 37);
			this.guna2CheckBox15.TabIndex = 92;
			this.guna2CheckBox15.Text = "Flushing DNS";
			this.guna2CheckBox15.UseVisualStyleBackColor = false;
			this.guna2CheckBox13.AutoSize = true;
			this.guna2CheckBox13.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox13, 0);
			this.guna2CheckBox13.Depth = 0;
			this.guna2CheckBox13.Location = new Point(6, 79);
			this.guna2CheckBox13.Margin = new Padding(0);
			this.guna2CheckBox13.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox13.MouseState = 0;
			this.guna2CheckBox13.Name = "guna2CheckBox13";
			this.guna2CheckBox13.Ripple = true;
			this.guna2CheckBox13.Size = new Size(145, 37);
			this.guna2CheckBox13.TabIndex = 91;
			this.guna2CheckBox13.Text = "Deleting on Exit";
			this.guna2CheckBox13.UseVisualStyleBackColor = false;
			this.guna2Button14.Animated = true;
			this.guna2Button14.BorderRadius = 6;
			this.guna2Button14.CheckedState.Parent = this.guna2Button14;
			this.guna2Button14.CustomImages.Parent = this.guna2Button14;
			this.guna2Transition1.SetDecoration(this.guna2Button14, 0);
			this.guna2Button14.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button14.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button14.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button14.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button14.DisabledState.Parent = this.guna2Button14;
			this.guna2Button14.FillColor = Color.Transparent;
			this.guna2Button14.Font = new Font("Segoe UI Semibold", 11.2f, FontStyle.Bold);
			this.guna2Button14.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button14.HoverState.Parent = this.guna2Button14;
			this.guna2Button14.Location = new Point(6, 31);
			this.guna2Button14.Name = "guna2Button14";
			this.guna2Button14.PressedColor = Color.White;
			this.guna2Button14.ShadowDecoration.Parent = this.guna2Button14;
			this.guna2Button14.Size = new Size(177, 31);
			this.guna2Button14.TabIndex = 90;
			this.guna2Button14.Text = "Self Destruct";
			this.guna2Button14.Click += this.guna2Button14_Click;
			this.groupBox7.BackColor = Color.FromArgb(250, 252, 252);
			this.groupBox7.Controls.Add(this.guna2Separator3);
			this.groupBox7.Controls.Add(this.materialCheckbox6);
			this.groupBox7.Controls.Add(this.materialCheckbox7);
			this.groupBox7.Controls.Add(this.materialCheckbox8);
			this.groupBox7.Controls.Add(this.label10);
			this.groupBox7.Controls.Add(this.guna2Button3);
			this.groupBox7.Controls.Add(this.label27);
			this.groupBox7.Controls.Add(this.guna2TrackBar14);
			this.groupBox7.Controls.Add(this.materialCheckbox3);
			this.groupBox7.Controls.Add(this.materialCheckbox4);
			this.groupBox7.Controls.Add(this.materialCheckbox5);
			this.groupBox7.Controls.Add(this.label8);
			this.groupBox7.Controls.Add(this.guna2Button2);
			this.groupBox7.Controls.Add(this.label9);
			this.groupBox7.Controls.Add(this.guna2TrackBar13);
			this.guna2Transition1.SetDecoration(this.groupBox7, 0);
			this.groupBox7.Font = new Font("Yu Gothic", 8.25f);
			this.groupBox7.Location = new Point(837, 9);
			this.groupBox7.Name = "groupBox7";
			this.groupBox7.Size = new Size(228, 385);
			this.groupBox7.TabIndex = 77;
			this.groupBox7.TabStop = false;
			this.groupBox7.Text = "Double Clicker";
			this.guna2Separator3.BackColor = Color.FromArgb(250, 252, 252);
			this.guna2Transition1.SetDecoration(this.guna2Separator3, 0);
			this.guna2Separator3.FillColor = Color.LightGray;
			this.guna2Separator3.Location = new Point(6, 184);
			this.guna2Separator3.Name = "guna2Separator3";
			this.guna2Separator3.Size = new Size(213, 19);
			this.guna2Separator3.TabIndex = 135;
			this.materialCheckbox6.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox6, 0);
			this.materialCheckbox6.Depth = 0;
			this.materialCheckbox6.Location = new Point(3, 337);
			this.materialCheckbox6.Margin = new Padding(0);
			this.materialCheckbox6.MouseLocation = new Point(-1, -1);
			this.materialCheckbox6.MouseState = 0;
			this.materialCheckbox6.Name = "materialCheckbox6";
			this.materialCheckbox6.Ripple = true;
			this.materialCheckbox6.Size = new Size(126, 37);
			this.materialCheckbox6.TabIndex = 134;
			this.materialCheckbox6.Text = "Shift Disable";
			this.materialCheckbox6.UseVisualStyleBackColor = true;
			this.materialCheckbox7.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox7, 0);
			this.materialCheckbox7.Depth = 0;
			this.materialCheckbox7.Location = new Point(3, 300);
			this.materialCheckbox7.Margin = new Padding(0);
			this.materialCheckbox7.MouseLocation = new Point(-1, -1);
			this.materialCheckbox7.MouseState = 0;
			this.materialCheckbox7.Name = "materialCheckbox7";
			this.materialCheckbox7.Ripple = true;
			this.materialCheckbox7.Size = new Size(167, 37);
			this.materialCheckbox7.TabIndex = 133;
			this.materialCheckbox7.Text = "Only While Moving";
			this.materialCheckbox7.UseVisualStyleBackColor = true;
			this.materialCheckbox8.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox8, 0);
			this.materialCheckbox8.Depth = 0;
			this.materialCheckbox8.Location = new Point(3, 263);
			this.materialCheckbox8.Margin = new Padding(0);
			this.materialCheckbox8.MouseLocation = new Point(-1, -1);
			this.materialCheckbox8.MouseState = 0;
			this.materialCheckbox8.Name = "materialCheckbox8";
			this.materialCheckbox8.Ripple = true;
			this.materialCheckbox8.Size = new Size(177, 37);
			this.materialCheckbox8.TabIndex = 132;
			this.materialCheckbox8.Text = "Right Double Clicker";
			this.materialCheckbox8.UseVisualStyleBackColor = true;
			this.materialCheckbox8.CheckedChanged += this.materialCheckbox8_CheckedChanged;
			this.guna2Button3.Animated = true;
			this.guna2Button3.BorderColor = Color.FromArgb(64, 64, 64);
			this.guna2Button3.CheckedState.Parent = this.guna2Button3;
			this.guna2Button3.CustomImages.Parent = this.guna2Button3;
			this.guna2Transition1.SetDecoration(this.guna2Button3, 0);
			this.guna2Button3.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button3.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button3.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button3.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button3.DisabledState.Parent = this.guna2Button3;
			this.guna2Button3.FillColor = Color.Transparent;
			this.guna2Button3.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button3.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button3.HoverState.Parent = this.guna2Button3;
			this.guna2Button3.Location = new Point(156, 237);
			this.guna2Button3.Name = "guna2Button3";
			this.guna2Button3.PressedColor = Color.Transparent;
			this.guna2Button3.ShadowDecoration.Parent = this.guna2Button3;
			this.guna2Button3.Size = new Size(66, 25);
			this.guna2Button3.TabIndex = 130;
			this.guna2Button3.Text = "Bind";
			this.guna2Button3.MouseDown += this.guna2Button3_MouseDown;
			this.label27.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label27, 0);
			this.label27.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label27.ForeColor = Color.FromArgb(64, 64, 64);
			this.label27.Location = new Point(5, 214);
			this.label27.Margin = new Padding(2, 0, 2, 0);
			this.label27.Name = "label27";
			this.label27.Size = new Size(154, 19);
			this.label27.TabIndex = 129;
			this.label27.Text = "Right Double Delay: 25";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar14, 0);
			this.guna2TrackBar14.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar14.HoverState.Parent = this.guna2TrackBar14;
			this.guna2TrackBar14.Location = new Point(9, 239);
			this.guna2TrackBar14.Maximum = 50;
			this.guna2TrackBar14.Minimum = 1;
			this.guna2TrackBar14.Name = "guna2TrackBar14";
			this.guna2TrackBar14.Size = new Size(141, 23);
			this.guna2TrackBar14.TabIndex = 128;
			this.guna2TrackBar14.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar14.Value = 25;
			this.guna2TrackBar14.Scroll += this.guna2TrackBar14_Scroll_2;
			this.materialCheckbox3.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox3, 0);
			this.materialCheckbox3.Depth = 0;
			this.materialCheckbox3.Location = new Point(3, 144);
			this.materialCheckbox3.Margin = new Padding(0);
			this.materialCheckbox3.MouseLocation = new Point(-1, -1);
			this.materialCheckbox3.MouseState = 0;
			this.materialCheckbox3.Name = "materialCheckbox3";
			this.materialCheckbox3.Ripple = true;
			this.materialCheckbox3.Size = new Size(126, 37);
			this.materialCheckbox3.TabIndex = 127;
			this.materialCheckbox3.Text = "Shift Disable";
			this.materialCheckbox3.UseVisualStyleBackColor = true;
			this.materialCheckbox4.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox4, 0);
			this.materialCheckbox4.Depth = 0;
			this.materialCheckbox4.Location = new Point(3, 107);
			this.materialCheckbox4.Margin = new Padding(0);
			this.materialCheckbox4.MouseLocation = new Point(-1, -1);
			this.materialCheckbox4.MouseState = 0;
			this.materialCheckbox4.Name = "materialCheckbox4";
			this.materialCheckbox4.Ripple = true;
			this.materialCheckbox4.Size = new Size(167, 37);
			this.materialCheckbox4.TabIndex = 126;
			this.materialCheckbox4.Text = "Only While Moving";
			this.materialCheckbox4.UseVisualStyleBackColor = true;
			this.materialCheckbox4.CheckedChanged += this.materialCheckbox4_CheckedChanged;
			this.materialCheckbox5.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox5, 0);
			this.materialCheckbox5.Depth = 0;
			this.materialCheckbox5.Location = new Point(3, 70);
			this.materialCheckbox5.Margin = new Padding(0);
			this.materialCheckbox5.MouseLocation = new Point(-1, -1);
			this.materialCheckbox5.MouseState = 0;
			this.materialCheckbox5.Name = "materialCheckbox5";
			this.materialCheckbox5.Ripple = true;
			this.materialCheckbox5.Size = new Size(168, 37);
			this.materialCheckbox5.TabIndex = 125;
			this.materialCheckbox5.Text = "Left Double Clicker";
			this.materialCheckbox5.UseVisualStyleBackColor = true;
			this.materialCheckbox5.CheckedChanged += this.materialCheckbox5_CheckedChanged;
			this.guna2Button2.Animated = true;
			this.guna2Button2.BorderColor = Color.FromArgb(64, 64, 64);
			this.guna2Button2.CheckedState.Parent = this.guna2Button2;
			this.guna2Button2.CustomImages.Parent = this.guna2Button2;
			this.guna2Transition1.SetDecoration(this.guna2Button2, 0);
			this.guna2Button2.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button2.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button2.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button2.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button2.DisabledState.Parent = this.guna2Button2;
			this.guna2Button2.FillColor = Color.Transparent;
			this.guna2Button2.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button2.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button2.HoverState.Parent = this.guna2Button2;
			this.guna2Button2.Location = new Point(156, 42);
			this.guna2Button2.Name = "guna2Button2";
			this.guna2Button2.PressedColor = Color.Transparent;
			this.guna2Button2.ShadowDecoration.Parent = this.guna2Button2;
			this.guna2Button2.Size = new Size(66, 25);
			this.guna2Button2.TabIndex = 123;
			this.guna2Button2.Text = "Bind";
			this.guna2Button2.MouseDown += this.guna2Button2_MouseDown;
			this.label9.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label9, 0);
			this.label9.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label9.ForeColor = Color.FromArgb(64, 64, 64);
			this.label9.Location = new Point(5, 20);
			this.label9.Margin = new Padding(2, 0, 2, 0);
			this.label9.Name = "label9";
			this.label9.Size = new Size(144, 19);
			this.label9.TabIndex = 122;
			this.label9.Text = "Left Double Delay: 25";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar13, 0);
			this.guna2TrackBar13.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar13.HoverState.Parent = this.guna2TrackBar13;
			this.guna2TrackBar13.Location = new Point(9, 45);
			this.guna2TrackBar13.Maximum = 50;
			this.guna2TrackBar13.Minimum = 1;
			this.guna2TrackBar13.Name = "guna2TrackBar13";
			this.guna2TrackBar13.Size = new Size(141, 23);
			this.guna2TrackBar13.TabIndex = 121;
			this.guna2TrackBar13.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar13.Value = 25;
			this.guna2TrackBar13.Scroll += this.guna2TrackBar13_Scroll_1;
			this.groupBox4.BackColor = Color.FromArgb(250, 252, 252);
			this.groupBox4.Controls.Add(this.guna2Button7);
			this.groupBox4.Controls.Add(this.guna2Button6);
			this.guna2Transition1.SetDecoration(this.groupBox4, 0);
			this.groupBox4.Font = new Font("Yu Gothic", 8.25f);
			this.groupBox4.Location = new Point(397, 310);
			this.groupBox4.Name = "groupBox4";
			this.groupBox4.Size = new Size(182, 84);
			this.groupBox4.TabIndex = 76;
			this.groupBox4.TabStop = false;
			this.groupBox4.Text = "Other";
			this.groupBox4.Enter += this.groupBox4_Enter;
			this.groupBox3.BackColor = Color.FromArgb(250, 252, 252);
			this.groupBox3.Controls.Add(this.materialCheckbox10);
			this.groupBox3.Controls.Add(this.materialCheckbox11);
			this.groupBox3.Controls.Add(this.materialCheckbox1);
			this.groupBox3.Controls.Add(this.materialCheckbox2);
			this.groupBox3.Controls.Add(this.guna2CheckBox9);
			this.groupBox3.Controls.Add(this.materialCheckbox_0);
			this.groupBox3.Controls.Add(this.hitCHECK);
			this.groupBox3.Controls.Add(this.materialCheckbox_1);
			this.groupBox3.Controls.Add(this.megaCHECKBOX);
			this.groupBox3.Controls.Add(this.lblreachlol);
			this.groupBox3.Controls.Add(this.hitTRACK);
			this.groupBox3.Controls.Add(this.velocityTRACK);
			this.groupBox3.Controls.Add(this.guna2TrackBar_0);
			this.guna2Transition1.SetDecoration(this.groupBox3, 0);
			this.groupBox3.Font = new Font("Yu Gothic", 8.25f);
			this.groupBox3.Location = new Point(397, 9);
			this.groupBox3.Name = "groupBox3";
			this.groupBox3.Size = new Size(182, 300);
			this.groupBox3.TabIndex = 2;
			this.groupBox3.TabStop = false;
			this.groupBox3.Text = "Combat";
			this.groupBox3.Enter += this.groupBox3_Enter;
			this.materialCheckbox1.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox1, 0);
			this.materialCheckbox1.Depth = 0;
			this.materialCheckbox1.Location = new Point(92, 222);
			this.materialCheckbox1.Margin = new Padding(0);
			this.materialCheckbox1.MouseLocation = new Point(-1, -1);
			this.materialCheckbox1.MouseState = 0;
			this.materialCheckbox1.Name = "materialCheckbox1";
			this.materialCheckbox1.Ripple = true;
			this.materialCheckbox1.Size = new Size(76, 37);
			this.materialCheckbox1.TabIndex = 90;
			this.materialCheckbox1.Text = "Timer";
			this.materialCheckbox1.UseVisualStyleBackColor = true;
			this.materialCheckbox1.CheckedChanged += this.materialCheckbox1_CheckedChanged;
			this.materialCheckbox2.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox2, 0);
			this.materialCheckbox2.Depth = 0;
			this.materialCheckbox2.Location = new Point(3, 222);
			this.materialCheckbox2.Margin = new Padding(0);
			this.materialCheckbox2.MouseLocation = new Point(-1, -1);
			this.materialCheckbox2.MouseState = 0;
			this.materialCheckbox2.Name = "materialCheckbox2";
			this.materialCheckbox2.Ripple = true;
			this.materialCheckbox2.Size = new Size(72, 37);
			this.materialCheckbox2.TabIndex = 89;
			this.materialCheckbox2.Text = "Bhop";
			this.materialCheckbox2.UseVisualStyleBackColor = true;
			this.materialCheckbox2.CheckedChanged += this.materialCheckbox2_CheckedChanged;
			this.guna2CheckBox9.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox9, 0);
			this.guna2CheckBox9.Depth = 0;
			this.guna2CheckBox9.Location = new Point(3, 184);
			this.guna2CheckBox9.Margin = new Padding(0);
			this.guna2CheckBox9.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox9.MouseState = 0;
			this.guna2CheckBox9.Name = "guna2CheckBox9";
			this.guna2CheckBox9.Ripple = true;
			this.guna2CheckBox9.Size = new Size(64, 37);
			this.guna2CheckBox9.TabIndex = 88;
			this.guna2CheckBox9.Text = "ESP";
			this.guna2CheckBox9.UseVisualStyleBackColor = true;
			this.guna2CheckBox9.CheckedChanged += this.guna2CheckBox9_CheckedChanged_1;
			this.materialCheckbox_0.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox_0, 0);
			this.materialCheckbox_0.Depth = 0;
			this.materialCheckbox_0.Location = new Point(9, 14);
			this.materialCheckbox_0.Margin = new Padding(0);
			this.materialCheckbox_0.MouseLocation = new Point(-1, -1);
			this.materialCheckbox_0.MouseState = 0;
			this.materialCheckbox_0.Name = "reachCHECK";
			this.materialCheckbox_0.Ripple = true;
			this.materialCheckbox_0.Size = new Size(87, 37);
			this.materialCheckbox_0.TabIndex = 87;
			this.materialCheckbox_0.Text = "Reach: ";
			this.materialCheckbox_0.UseVisualStyleBackColor = true;
			this.materialCheckbox_0.CheckedChanged += this.reachCHECK_CheckedChanged;
			this.hitCHECK.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.hitCHECK, 0);
			this.hitCHECK.Depth = 0;
			this.hitCHECK.Location = new Point(9, 125);
			this.hitCHECK.Margin = new Padding(0);
			this.hitCHECK.MouseLocation = new Point(-1, -1);
			this.hitCHECK.MouseState = 0;
			this.hitCHECK.Name = "hitCHECK";
			this.hitCHECK.Ripple = true;
			this.hitCHECK.Size = new Size(147, 37);
			this.hitCHECK.TabIndex = 86;
			this.hitCHECK.Text = "Hitbox: Medium";
			this.hitCHECK.UseVisualStyleBackColor = true;
			this.hitCHECK.CheckedChanged += this.hitCHECK_CheckedChanged;
			this.materialCheckbox_1.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox_1, 0);
			this.materialCheckbox_1.Depth = 0;
			this.materialCheckbox_1.Location = new Point(9, 65);
			this.materialCheckbox_1.Margin = new Padding(0);
			this.materialCheckbox_1.MouseLocation = new Point(-1, -1);
			this.materialCheckbox_1.MouseState = 0;
			this.materialCheckbox_1.Name = "veloCHECK";
			this.materialCheckbox_1.Ripple = true;
			this.materialCheckbox_1.Size = new Size(108, 37);
			this.materialCheckbox_1.TabIndex = 85;
			this.materialCheckbox_1.Text = "Velocity: 1";
			this.materialCheckbox_1.UseVisualStyleBackColor = true;
			this.materialCheckbox_1.CheckedChanged += this.veloCHECK_CheckedChanged;
			this.megaCHECKBOX.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.megaCHECKBOX, 0);
			this.megaCHECKBOX.Depth = 0;
			this.megaCHECKBOX.Location = new Point(3, 259);
			this.megaCHECKBOX.Margin = new Padding(0);
			this.megaCHECKBOX.MouseLocation = new Point(-1, -1);
			this.megaCHECKBOX.MouseState = 0;
			this.megaCHECKBOX.Name = "megaCHECKBOX";
			this.megaCHECKBOX.Ripple = true;
			this.megaCHECKBOX.Size = new Size(76, 37);
			this.megaCHECKBOX.TabIndex = 84;
			this.megaCHECKBOX.Text = "Jump";
			this.megaCHECKBOX.UseVisualStyleBackColor = true;
			this.megaCHECKBOX.CheckedChanged += this.megaCHECKBOX_CheckedChanged;
			this.lblreachlol.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.lblreachlol, 0);
			this.lblreachlol.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.lblreachlol.ForeColor = Color.FromArgb(64, 64, 64);
			this.lblreachlol.Location = new Point(94, 22);
			this.lblreachlol.Name = "lblreachlol";
			this.lblreachlol.Size = new Size(44, 19);
			this.lblreachlol.TabIndex = 83;
			this.lblreachlol.Text = "4.000";
			this.lblreachlol.Click += this.lblreachlol_Click;
			this.guna2Transition1.SetDecoration(this.hitTRACK, 0);
			this.hitTRACK.HoverState.Parent = this.hitTRACK;
			this.hitTRACK.Location = new Point(9, 159);
			this.hitTRACK.Maximum = 3;
			this.hitTRACK.Minimum = 1;
			this.hitTRACK.Name = "hitTRACK";
			this.hitTRACK.Size = new Size(166, 23);
			this.hitTRACK.TabIndex = 82;
			this.hitTRACK.ThumbColor = Color.DodgerBlue;
			this.hitTRACK.Value = 2;
			this.hitTRACK.Scroll += this.hitTRACK_Scroll;
			this.guna2Transition1.SetDecoration(this.velocityTRACK, 0);
			this.velocityTRACK.HoverState.Parent = this.velocityTRACK;
			this.velocityTRACK.Location = new Point(9, 99);
			this.velocityTRACK.Maximum = 100000;
			this.velocityTRACK.Minimum = 1002;
			this.velocityTRACK.Name = "velocityTRACK";
			this.velocityTRACK.Size = new Size(166, 23);
			this.velocityTRACK.TabIndex = 81;
			this.velocityTRACK.ThumbColor = Color.DodgerBlue;
			this.velocityTRACK.Value = 1002;
			this.velocityTRACK.Scroll += this.velocityTRACK_Scroll;
			this.guna2Transition1.SetDecoration(this.guna2TrackBar_0, 0);
			this.guna2TrackBar_0.HoverState.Parent = this.guna2TrackBar_0;
			this.guna2TrackBar_0.Location = new Point(9, 44);
			this.guna2TrackBar_0.Maximum = 5000;
			this.guna2TrackBar_0.Minimum = 2500;
			this.guna2TrackBar_0.Name = "reachTRACK";
			this.guna2TrackBar_0.Size = new Size(166, 23);
			this.guna2TrackBar_0.TabIndex = 80;
			this.guna2TrackBar_0.ThumbColor = Color.FromArgb(7, 127, 243);
			this.guna2TrackBar_0.Value = 4000;
			this.guna2TrackBar_0.Scroll += this.reachTRACK_Scroll;
			this.groupBox2.BackColor = Color.FromArgb(250, 252, 252);
			this.groupBox2.Controls.Add(this.guna2CheckBox23);
			this.groupBox2.Controls.Add(this.guna2CheckBox22);
			this.groupBox2.Controls.Add(this.label25);
			this.groupBox2.Controls.Add(this.guna2TrackBar11);
			this.groupBox2.Controls.Add(this.label26);
			this.groupBox2.Controls.Add(this.guna2TrackBar12);
			this.groupBox2.Controls.Add(this.guna2Button20);
			this.groupBox2.Controls.Add(this.label13);
			this.groupBox2.Controls.Add(this.guna2TrackBar9);
			this.groupBox2.Controls.Add(this.guna2Button21);
			this.groupBox2.Controls.Add(this.label24);
			this.groupBox2.Controls.Add(this.guna2TrackBar10);
			this.groupBox2.Controls.Add(this.label6);
			this.groupBox2.Controls.Add(this.guna2TrackBar4);
			this.groupBox2.Controls.Add(this.label5);
			this.groupBox2.Controls.Add(this.guna2TrackBar3);
			this.groupBox2.Controls.Add(this.label4);
			this.groupBox2.Controls.Add(this.label3);
			this.groupBox2.Controls.Add(this.guna2TrackBar2);
			this.guna2Transition1.SetDecoration(this.groupBox2, 0);
			this.groupBox2.Font = new Font("Yu Gothic", 8.25f);
			this.groupBox2.Location = new Point(585, 9);
			this.groupBox2.Name = "groupBox2";
			this.groupBox2.Size = new Size(250, 385);
			this.groupBox2.TabIndex = 1;
			this.groupBox2.TabStop = false;
			this.groupBox2.Text = "Customization";
			this.guna2CheckBox23.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox23, 0);
			this.guna2CheckBox23.Depth = 0;
			this.guna2CheckBox23.Location = new Point(117, 337);
			this.guna2CheckBox23.Margin = new Padding(0);
			this.guna2CheckBox23.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox23.MouseState = 0;
			this.guna2CheckBox23.Name = "guna2CheckBox23";
			this.guna2CheckBox23.Ripple = true;
			this.guna2CheckBox23.Size = new Size(127, 37);
			this.guna2CheckBox23.TabIndex = 97;
			this.guna2CheckBox23.Text = "Click Sounds";
			this.guna2CheckBox23.UseVisualStyleBackColor = true;
			this.guna2CheckBox22.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox22, 0);
			this.guna2CheckBox22.Depth = 0;
			this.guna2CheckBox22.Location = new Point(6, 337);
			this.guna2CheckBox22.Margin = new Padding(0);
			this.guna2CheckBox22.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox22.MouseState = 0;
			this.guna2CheckBox22.Name = "guna2CheckBox22";
			this.guna2CheckBox22.Ripple = true;
			this.guna2CheckBox22.Size = new Size(111, 37);
			this.guna2CheckBox22.TabIndex = 96;
			this.guna2CheckBox22.Text = "CPS Drops";
			this.guna2CheckBox22.UseVisualStyleBackColor = true;
			this.label25.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label25, 0);
			this.label25.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label25.ForeColor = Color.FromArgb(64, 64, 64);
			this.label25.Location = new Point(9, 289);
			this.label25.Margin = new Padding(2, 0, 2, 0);
			this.label25.Name = "label25";
			this.label25.Size = new Size(133, 19);
			this.label25.TabIndex = 95;
			this.label25.Text = "Max Range CPS: +3";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar11, 0);
			this.guna2TrackBar11.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar11.HoverState.Parent = this.guna2TrackBar11;
			this.guna2TrackBar11.Location = new Point(13, 311);
			this.guna2TrackBar11.Maximum = 5;
			this.guna2TrackBar11.Minimum = 1;
			this.guna2TrackBar11.Name = "guna2TrackBar11";
			this.guna2TrackBar11.Size = new Size(218, 23);
			this.guna2TrackBar11.TabIndex = 94;
			this.guna2TrackBar11.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar11.Value = 3;
			this.guna2TrackBar11.Scroll += this.guna2TrackBar11_Scroll_1;
			this.label26.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label26, 0);
			this.label26.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label26.ForeColor = Color.FromArgb(64, 64, 64);
			this.label26.Location = new Point(12, 240);
			this.label26.Margin = new Padding(2, 0, 2, 0);
			this.label26.Name = "label26";
			this.label26.Size = new Size(127, 19);
			this.label26.TabIndex = 93;
			this.label26.Text = "Min Range CPS: -2";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar12, 0);
			this.guna2TrackBar12.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar12.HoverState.Parent = this.guna2TrackBar12;
			this.guna2TrackBar12.Location = new Point(16, 262);
			this.guna2TrackBar12.Maximum = 5;
			this.guna2TrackBar12.Minimum = 1;
			this.guna2TrackBar12.Name = "guna2TrackBar12";
			this.guna2TrackBar12.Size = new Size(215, 23);
			this.guna2TrackBar12.TabIndex = 92;
			this.guna2TrackBar12.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar12.Value = 2;
			this.guna2TrackBar12.Scroll += this.guna2TrackBar12_Scroll_1;
			this.guna2Button20.Animated = true;
			this.guna2Button20.CheckedState.Parent = this.guna2Button20;
			this.guna2Button20.CustomImages.Parent = this.guna2Button20;
			this.guna2Transition1.SetDecoration(this.guna2Button20, 0);
			this.guna2Button20.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button20.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button20.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button20.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button20.DisabledState.Parent = this.guna2Button20;
			this.guna2Button20.FillColor = Color.Transparent;
			this.guna2Button20.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button20.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button20.HoverState.Parent = this.guna2Button20;
			this.guna2Button20.Location = new Point(178, 212);
			this.guna2Button20.Name = "guna2Button20";
			this.guna2Button20.ShadowDecoration.Parent = this.guna2Button20;
			this.guna2Button20.Size = new Size(68, 25);
			this.guna2Button20.TabIndex = 91;
			this.guna2Button20.Text = "Bind";
			this.guna2Button20.MouseDown += this.guna2Button20_MouseDown;
			this.label13.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label13, 0);
			this.label13.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label13.ForeColor = Color.FromArgb(64, 64, 64);
			this.label13.Location = new Point(11, 190);
			this.label13.Margin = new Padding(2, 0, 2, 0);
			this.label13.Name = "label13";
			this.label13.Size = new Size(145, 19);
			this.label13.TabIndex = 90;
			this.label13.Text = "Boost Right CPS: 2.00";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar9, 0);
			this.guna2TrackBar9.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar9.HoverState.Parent = this.guna2TrackBar9;
			this.guna2TrackBar9.Location = new Point(15, 212);
			this.guna2TrackBar9.Maximum = 500;
			this.guna2TrackBar9.Minimum = 100;
			this.guna2TrackBar9.Name = "guna2TrackBar9";
			this.guna2TrackBar9.Size = new Size(157, 23);
			this.guna2TrackBar9.TabIndex = 89;
			this.guna2TrackBar9.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar9.Value = 200;
			this.guna2TrackBar9.Scroll += this.guna2TrackBar9_Scroll_2;
			this.guna2Button21.Animated = true;
			this.guna2Button21.CheckedState.Parent = this.guna2Button21;
			this.guna2Button21.CustomImages.Parent = this.guna2Button21;
			this.guna2Transition1.SetDecoration(this.guna2Button21, 0);
			this.guna2Button21.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button21.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button21.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button21.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button21.DisabledState.Parent = this.guna2Button21;
			this.guna2Button21.FillColor = Color.Transparent;
			this.guna2Button21.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button21.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button21.HoverState.Parent = this.guna2Button21;
			this.guna2Button21.Location = new Point(178, 161);
			this.guna2Button21.Name = "guna2Button21";
			this.guna2Button21.ShadowDecoration.Parent = this.guna2Button21;
			this.guna2Button21.Size = new Size(68, 25);
			this.guna2Button21.TabIndex = 88;
			this.guna2Button21.Text = "Bind";
			this.guna2Button21.MouseDown += this.guna2Button21_MouseDown;
			this.label24.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label24, 0);
			this.label24.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label24.ForeColor = Color.FromArgb(64, 64, 64);
			this.label24.Location = new Point(9, 138);
			this.label24.Margin = new Padding(2, 0, 2, 0);
			this.label24.Name = "label24";
			this.label24.Size = new Size(135, 19);
			this.label24.TabIndex = 87;
			this.label24.Text = "Boost Left CPS: 2.00";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar10, 0);
			this.guna2TrackBar10.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar10.HoverState.Parent = this.guna2TrackBar10;
			this.guna2TrackBar10.Location = new Point(13, 161);
			this.guna2TrackBar10.Maximum = 500;
			this.guna2TrackBar10.Minimum = 100;
			this.guna2TrackBar10.Name = "guna2TrackBar10";
			this.guna2TrackBar10.Size = new Size(159, 23);
			this.guna2TrackBar10.TabIndex = 86;
			this.guna2TrackBar10.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar10.Value = 200;
			this.guna2TrackBar10.Scroll += this.guna2TrackBar10_Scroll;
			this.label6.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label6, 0);
			this.label6.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label6.ForeColor = Color.FromArgb(64, 64, 64);
			this.label6.Location = new Point(9, 90);
			this.label6.Margin = new Padding(2, 0, 2, 0);
			this.label6.Name = "label6";
			this.label6.Size = new Size(149, 19);
			this.label6.TabIndex = 58;
			this.label6.Text = "BlockHit Per Second: 1";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar4, 0);
			this.guna2TrackBar4.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar4.HoverState.Parent = this.guna2TrackBar4;
			this.guna2TrackBar4.Location = new Point(13, 113);
			this.guna2TrackBar4.Maximum = 10;
			this.guna2TrackBar4.Minimum = 1;
			this.guna2TrackBar4.Name = "guna2TrackBar4";
			this.guna2TrackBar4.Size = new Size(218, 23);
			this.guna2TrackBar4.TabIndex = 57;
			this.guna2TrackBar4.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar4.Value = 1;
			this.guna2TrackBar4.Scroll += this.guna2TrackBar4_Scroll;
			this.label5.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label5, 0);
			this.label5.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label5.ForeColor = Color.FromArgb(64, 64, 64);
			this.label5.Location = new Point(213, 65);
			this.label5.Margin = new Padding(2, 0, 2, 0);
			this.label5.Name = "label5";
			this.label5.Size = new Size(31, 19);
			this.label5.TabIndex = 56;
			this.label5.Text = "y: 3";
			this.label5.Click += this.label5_Click;
			this.guna2TrackBar3.BackColor = Color.Transparent;
			this.guna2Transition1.SetDecoration(this.guna2TrackBar3, 0);
			this.guna2TrackBar3.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar3.HoverState.Parent = this.guna2TrackBar3;
			this.guna2TrackBar3.Location = new Point(9, 65);
			this.guna2TrackBar3.Maximum = 30;
			this.guna2TrackBar3.Minimum = 1;
			this.guna2TrackBar3.Name = "guna2TrackBar3";
			this.guna2TrackBar3.Size = new Size(199, 23);
			this.guna2TrackBar3.TabIndex = 55;
			this.guna2TrackBar3.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar3.Value = 3;
			this.guna2TrackBar3.Scroll += this.guna2TrackBar3_Scroll;
			this.label4.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label4, 0);
			this.label4.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label4.ForeColor = Color.FromArgb(64, 64, 64);
			this.label4.Location = new Point(213, 36);
			this.label4.Margin = new Padding(2, 0, 2, 0);
			this.label4.Name = "label4";
			this.label4.Size = new Size(31, 19);
			this.label4.TabIndex = 54;
			this.label4.Text = "x: 2";
			this.label3.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label3, 0);
			this.label3.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label3.ForeColor = Color.FromArgb(64, 64, 64);
			this.label3.Location = new Point(5, 14);
			this.label3.Margin = new Padding(2, 0, 2, 0);
			this.label3.Name = "label3";
			this.label3.Size = new Size(45, 19);
			this.label3.TabIndex = 53;
			this.label3.Text = "Jitter:";
			this.label3.Click += this.label3_Click;
			this.guna2Transition1.SetDecoration(this.guna2TrackBar2, 0);
			this.guna2TrackBar2.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar2.HoverState.Parent = this.guna2TrackBar2;
			this.guna2TrackBar2.Location = new Point(9, 36);
			this.guna2TrackBar2.Maximum = 30;
			this.guna2TrackBar2.Minimum = 1;
			this.guna2TrackBar2.Name = "guna2TrackBar2";
			this.guna2TrackBar2.Size = new Size(199, 23);
			this.guna2TrackBar2.TabIndex = 52;
			this.guna2TrackBar2.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar2.Value = 2;
			this.guna2TrackBar2.Scroll += this.guna2TrackBar2_Scroll;
			this.groupBox1.BackColor = Color.FromArgb(250, 252, 252);
			this.groupBox1.Controls.Add(this.guna2CheckBox10);
			this.groupBox1.Controls.Add(this.guna2CheckBox11);
			this.groupBox1.Controls.Add(this.guna2CheckBox12);
			this.groupBox1.Controls.Add(this.guna2CheckBox20);
			this.groupBox1.Controls.Add(this.guna2CheckBox21);
			this.groupBox1.Controls.Add(this.label12);
			this.groupBox1.Controls.Add(this.label1);
			this.groupBox1.Controls.Add(this.label7);
			this.groupBox1.Controls.Add(this.guna2Button5);
			this.groupBox1.Controls.Add(this.label11);
			this.groupBox1.Controls.Add(this.guna2TrackBar5);
			this.groupBox1.Controls.Add(this.guna2CheckBox6);
			this.groupBox1.Controls.Add(this.guna2CheckBox7);
			this.groupBox1.Controls.Add(this.guna2CheckBox8);
			this.groupBox1.Controls.Add(this.guna2CheckBox5);
			this.groupBox1.Controls.Add(this.guna2CheckBox4);
			this.groupBox1.Controls.Add(this.guna2CheckBox3);
			this.groupBox1.Controls.Add(this.guna2CheckBox2);
			this.groupBox1.Controls.Add(this.guna2CheckBox1);
			this.groupBox1.Controls.Add(this.label28);
			this.groupBox1.Controls.Add(this.label29);
			this.groupBox1.Controls.Add(this.guna2Button1);
			this.groupBox1.Controls.Add(this.label2);
			this.groupBox1.Controls.Add(this.guna2TrackBar1);
			this.groupBox1.Controls.Add(this.guna2Separator4);
			this.guna2Transition1.SetDecoration(this.groupBox1, 0);
			this.groupBox1.Font = new Font("Yu Gothic", 8.25f);
			this.groupBox1.ForeColor = SystemColors.ControlText;
			this.groupBox1.Location = new Point(9, 9);
			this.groupBox1.Name = "groupBox1";
			this.groupBox1.Size = new Size(382, 385);
			this.groupBox1.TabIndex = 0;
			this.groupBox1.TabStop = false;
			this.groupBox1.Text = "Auto Clicker";
			this.guna2CheckBox10.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox10, 0);
			this.guna2CheckBox10.Depth = 0;
			this.guna2CheckBox10.Location = new Point(16, 338);
			this.guna2CheckBox10.Margin = new Padding(0);
			this.guna2CheckBox10.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox10.MouseState = 0;
			this.guna2CheckBox10.Name = "guna2CheckBox10";
			this.guna2CheckBox10.Ripple = true;
			this.guna2CheckBox10.Size = new Size(143, 37);
			this.guna2CheckBox10.TabIndex = 135;
			this.guna2CheckBox10.Text = "Randomization";
			this.guna2CheckBox10.UseVisualStyleBackColor = true;
			this.guna2CheckBox10.CheckedChanged += this.guna2CheckBox10_CheckedChanged_2;
			this.guna2CheckBox11.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox11, 0);
			this.guna2CheckBox11.Depth = 0;
			this.guna2CheckBox11.Location = new Point(206, 301);
			this.guna2CheckBox11.Margin = new Padding(0);
			this.guna2CheckBox11.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox11.MouseState = 0;
			this.guna2CheckBox11.Name = "guna2CheckBox11";
			this.guna2CheckBox11.Ripple = true;
			this.guna2CheckBox11.Size = new Size(126, 37);
			this.guna2CheckBox11.TabIndex = 134;
			this.guna2CheckBox11.Text = "Shift Disable";
			this.guna2CheckBox11.UseVisualStyleBackColor = true;
			this.guna2CheckBox11.CheckedChanged += this.guna2CheckBox11_CheckedChanged;
			this.guna2CheckBox12.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox12, 0);
			this.guna2CheckBox12.Depth = 0;
			this.guna2CheckBox12.Location = new Point(16, 301);
			this.guna2CheckBox12.Margin = new Padding(0);
			this.guna2CheckBox12.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox12.MouseState = 0;
			this.guna2CheckBox12.Name = "guna2CheckBox12";
			this.guna2CheckBox12.Ripple = true;
			this.guna2CheckBox12.Size = new Size(167, 37);
			this.guna2CheckBox12.TabIndex = 133;
			this.guna2CheckBox12.Text = "Only While Moving";
			this.guna2CheckBox12.UseVisualStyleBackColor = true;
			this.guna2CheckBox12.CheckedChanged += this.guna2CheckBox12_CheckedChanged;
			this.guna2CheckBox20.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox20, 0);
			this.guna2CheckBox20.Depth = 0;
			this.guna2CheckBox20.Location = new Point(206, 264);
			this.guna2CheckBox20.Margin = new Padding(0);
			this.guna2CheckBox20.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox20.MouseState = 0;
			this.guna2CheckBox20.Name = "guna2CheckBox20";
			this.guna2CheckBox20.Ripple = true;
			this.guna2CheckBox20.Size = new Size(86, 37);
			this.guna2CheckBox20.TabIndex = 132;
			this.guna2CheckBox20.Text = "Blatant";
			this.guna2CheckBox20.UseVisualStyleBackColor = true;
			this.guna2CheckBox20.CheckedChanged += this.guna2CheckBox20_CheckedChanged;
			this.guna2CheckBox21.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox21, 0);
			this.guna2CheckBox21.Depth = 0;
			this.guna2CheckBox21.Location = new Point(16, 265);
			this.guna2CheckBox21.Margin = new Padding(0);
			this.guna2CheckBox21.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox21.MouseState = 0;
			this.guna2CheckBox21.Name = "guna2CheckBox21";
			this.guna2CheckBox21.Ripple = true;
			this.guna2CheckBox21.Size = new Size(188, 37);
			this.guna2CheckBox21.TabIndex = 131;
			this.guna2CheckBox21.Text = "RightClicker - Enabled";
			this.guna2CheckBox21.UseVisualStyleBackColor = true;
			this.guna2CheckBox21.CheckedChanged += this.guna2CheckBox21_CheckedChanged;
			this.guna2Button5.Animated = true;
			this.guna2Button5.BorderColor = Color.Transparent;
			this.guna2Button5.CheckedState.Parent = this.guna2Button5;
			this.guna2Button5.CustomImages.Parent = this.guna2Button5;
			this.guna2Transition1.SetDecoration(this.guna2Button5, 0);
			this.guna2Button5.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button5.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button5.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button5.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button5.DisabledState.Parent = this.guna2Button5;
			this.guna2Button5.FillColor = Color.Transparent;
			this.guna2Button5.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button5.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button5.HoverState.Parent = this.guna2Button5;
			this.guna2Button5.Location = new Point(299, 235);
			this.guna2Button5.Name = "guna2Button5";
			this.guna2Button5.PressedColor = Color.Transparent;
			this.guna2Button5.ShadowDecoration.Parent = this.guna2Button5;
			this.guna2Button5.Size = new Size(73, 25);
			this.guna2Button5.TabIndex = 127;
			this.guna2Button5.Text = "Bind";
			this.guna2Button5.MouseDown += this.guna2Button5_MouseDown_1;
			this.label11.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label11, 0);
			this.label11.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label11.ForeColor = Color.FromArgb(64, 64, 64);
			this.label11.Location = new Point(5, 219);
			this.label11.Margin = new Padding(2, 0, 2, 0);
			this.label11.Name = "label11";
			this.label11.Size = new Size(141, 19);
			this.label11.TabIndex = 126;
			this.label11.Text = "Right Average CPS: 9";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar5, 0);
			this.guna2TrackBar5.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar5.HoverState.Parent = this.guna2TrackBar5;
			this.guna2TrackBar5.Location = new Point(9, 241);
			this.guna2TrackBar5.Maximum = 2000;
			this.guna2TrackBar5.Minimum = 100;
			this.guna2TrackBar5.Name = "guna2TrackBar5";
			this.guna2TrackBar5.Size = new Size(283, 19);
			this.guna2TrackBar5.TabIndex = 125;
			this.guna2TrackBar5.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar5.Value = 900;
			this.guna2TrackBar5.Scroll += this.guna2TrackBar5_Scroll;
			this.guna2CheckBox6.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox6, 0);
			this.guna2CheckBox6.Depth = 0;
			this.guna2CheckBox6.Location = new Point(295, 79);
			this.guna2CheckBox6.Margin = new Padding(0);
			this.guna2CheckBox6.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox6.MouseState = 0;
			this.guna2CheckBox6.Name = "guna2CheckBox6";
			this.guna2CheckBox6.Ripple = true;
			this.guna2CheckBox6.Size = new Size(71, 37);
			this.guna2CheckBox6.TabIndex = 124;
			this.guna2CheckBox6.Text = "Jitter";
			this.guna2CheckBox6.UseVisualStyleBackColor = true;
			this.guna2CheckBox6.CheckedChanged += this.guna2CheckBox6_CheckedChanged;
			this.guna2CheckBox7.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox7, 0);
			this.guna2CheckBox7.Depth = 0;
			this.guna2CheckBox7.ForeColor = Color.Black;
			this.guna2CheckBox7.Location = new Point(136, 150);
			this.guna2CheckBox7.Margin = new Padding(0);
			this.guna2CheckBox7.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox7.MouseState = 0;
			this.guna2CheckBox7.Name = "guna2CheckBox7";
			this.guna2CheckBox7.Ripple = true;
			this.guna2CheckBox7.Size = new Size(94, 37);
			this.guna2CheckBox7.TabIndex = 123;
			this.guna2CheckBox7.Text = "BlockHit";
			this.guna2CheckBox7.UseVisualStyleBackColor = true;
			this.guna2CheckBox7.CheckedChanged += this.guna2CheckBox7_CheckedChanged;
			this.guna2CheckBox8.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox8, 0);
			this.guna2CheckBox8.Depth = 0;
			this.guna2CheckBox8.Location = new Point(4, 149);
			this.guna2CheckBox8.Margin = new Padding(0);
			this.guna2CheckBox8.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox8.MouseState = 0;
			this.guna2CheckBox8.Name = "guna2CheckBox8";
			this.guna2CheckBox8.Ripple = true;
			this.guna2CheckBox8.Size = new Size(126, 37);
			this.guna2CheckBox8.TabIndex = 122;
			this.guna2CheckBox8.Text = "Break Blocks";
			this.guna2CheckBox8.UseVisualStyleBackColor = true;
			this.guna2CheckBox8.CheckedChanged += this.guna2CheckBox8_CheckedChanged_1;
			this.guna2CheckBox5.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox5, 0);
			this.guna2CheckBox5.Depth = 0;
			this.guna2CheckBox5.Location = new Point(192, 113);
			this.guna2CheckBox5.Margin = new Padding(0);
			this.guna2CheckBox5.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox5.MouseState = 0;
			this.guna2CheckBox5.Name = "guna2CheckBox5";
			this.guna2CheckBox5.Ripple = true;
			this.guna2CheckBox5.Size = new Size(143, 37);
			this.guna2CheckBox5.TabIndex = 121;
			this.guna2CheckBox5.Text = "Randomization";
			this.guna2CheckBox5.UseVisualStyleBackColor = true;
			this.guna2CheckBox4.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox4, 0);
			this.guna2CheckBox4.Depth = 0;
			this.guna2CheckBox4.Location = new Point(231, 150);
			this.guna2CheckBox4.Margin = new Padding(0);
			this.guna2CheckBox4.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox4.MouseState = 0;
			this.guna2CheckBox4.Name = "guna2CheckBox4";
			this.guna2CheckBox4.Ripple = true;
			this.guna2CheckBox4.Size = new Size(126, 37);
			this.guna2CheckBox4.TabIndex = 120;
			this.guna2CheckBox4.Text = "Shift Disable";
			this.guna2CheckBox4.UseVisualStyleBackColor = true;
			this.guna2CheckBox4.CheckedChanged += this.guna2CheckBox4_CheckedChanged;
			this.guna2CheckBox3.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox3, 0);
			this.guna2CheckBox3.Depth = 0;
			this.guna2CheckBox3.Location = new Point(4, 113);
			this.guna2CheckBox3.Margin = new Padding(0);
			this.guna2CheckBox3.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox3.MouseState = 0;
			this.guna2CheckBox3.Name = "guna2CheckBox3";
			this.guna2CheckBox3.Ripple = true;
			this.guna2CheckBox3.Size = new Size(167, 37);
			this.guna2CheckBox3.TabIndex = 119;
			this.guna2CheckBox3.Text = "Only While Moving";
			this.guna2CheckBox3.UseVisualStyleBackColor = true;
			this.guna2CheckBox3.CheckedChanged += this.guna2CheckBox3_CheckedChanged;
			this.guna2CheckBox2.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox2, 0);
			this.guna2CheckBox2.Depth = 0;
			this.guna2CheckBox2.Location = new Point(187, 78);
			this.guna2CheckBox2.Margin = new Padding(0);
			this.guna2CheckBox2.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox2.MouseState = 0;
			this.guna2CheckBox2.Name = "guna2CheckBox2";
			this.guna2CheckBox2.Ripple = true;
			this.guna2CheckBox2.Size = new Size(86, 37);
			this.guna2CheckBox2.TabIndex = 118;
			this.guna2CheckBox2.Text = "Blatant";
			this.guna2CheckBox2.UseVisualStyleBackColor = true;
			this.guna2CheckBox2.CheckedChanged += this.guna2CheckBox2_CheckedChanged;
			this.guna2CheckBox1.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.guna2CheckBox1, 0);
			this.guna2CheckBox1.Depth = 0;
			this.guna2CheckBox1.Location = new Point(4, 79);
			this.guna2CheckBox1.Margin = new Padding(0);
			this.guna2CheckBox1.MouseLocation = new Point(-1, -1);
			this.guna2CheckBox1.MouseState = 0;
			this.guna2CheckBox1.Name = "guna2CheckBox1";
			this.guna2CheckBox1.Ripple = true;
			this.guna2CheckBox1.Size = new Size(179, 37);
			this.guna2CheckBox1.TabIndex = 117;
			this.guna2CheckBox1.Text = "LeftClicker - Enabled";
			this.guna2CheckBox1.UseVisualStyleBackColor = true;
			this.guna2CheckBox1.CheckedChanged += this.guna2CheckBox1_CheckedChanged;
			this.guna2Button1.Animated = true;
			this.guna2Button1.BorderColor = Color.FromArgb(64, 64, 64);
			this.guna2Button1.CheckedState.Parent = this.guna2Button1;
			this.guna2Button1.CustomImages.Parent = this.guna2Button1;
			this.guna2Transition1.SetDecoration(this.guna2Button1, 0);
			this.guna2Button1.DisabledState.BorderColor = Color.DarkGray;
			this.guna2Button1.DisabledState.CustomBorderColor = Color.DarkGray;
			this.guna2Button1.DisabledState.FillColor = Color.FromArgb(169, 169, 169);
			this.guna2Button1.DisabledState.ForeColor = Color.FromArgb(141, 141, 141);
			this.guna2Button1.DisabledState.Parent = this.guna2Button1;
			this.guna2Button1.FillColor = Color.Transparent;
			this.guna2Button1.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.guna2Button1.ForeColor = Color.FromArgb(64, 64, 64);
			this.guna2Button1.HoverState.Parent = this.guna2Button1;
			this.guna2Button1.Location = new Point(307, 51);
			this.guna2Button1.Name = "guna2Button1";
			this.guna2Button1.PressedColor = Color.Transparent;
			this.guna2Button1.ShadowDecoration.Parent = this.guna2Button1;
			this.guna2Button1.Size = new Size(66, 25);
			this.guna2Button1.TabIndex = 114;
			this.guna2Button1.Text = "Bind";
			this.guna2Button1.MouseDown += this.guna2Button1_MouseDown;
			this.label2.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.label2, 0);
			this.label2.Font = new Font("Segoe UI Semibold", 10.2f, FontStyle.Bold);
			this.label2.ForeColor = Color.FromArgb(64, 64, 64);
			this.label2.Location = new Point(6, 32);
			this.label2.Margin = new Padding(2, 0, 2, 0);
			this.label2.Name = "label2";
			this.label2.Size = new Size(131, 19);
			this.label2.TabIndex = 113;
			this.label2.Text = "Left Average CPS: 9";
			this.guna2Transition1.SetDecoration(this.guna2TrackBar1, 0);
			this.guna2TrackBar1.FillColor = SystemColors.ButtonFace;
			this.guna2TrackBar1.HoverState.Parent = this.guna2TrackBar1;
			this.guna2TrackBar1.Location = new Point(10, 53);
			this.guna2TrackBar1.Maximum = 2000;
			this.guna2TrackBar1.Minimum = 100;
			this.guna2TrackBar1.Name = "guna2TrackBar1";
			this.guna2TrackBar1.Size = new Size(283, 23);
			this.guna2TrackBar1.TabIndex = 112;
			this.guna2TrackBar1.ThumbColor = Color.DodgerBlue;
			this.guna2TrackBar1.Value = 900;
			this.guna2TrackBar1.Scroll += this.guna2TrackBar1_Scroll;
			this.guna2Separator4.BackColor = Color.FromArgb(250, 252, 252);
			this.guna2Transition1.SetDecoration(this.guna2Separator4, 0);
			this.guna2Separator4.FillColor = Color.LightGray;
			this.guna2Separator4.Location = new Point(7, 193);
			this.guna2Separator4.Name = "guna2Separator4";
			this.guna2Separator4.Size = new Size(366, 19);
			this.guna2Separator4.TabIndex = 100;
			this.guna2Transition1.SetDecoration(this.guna2Separator2, 0);
			this.guna2Separator2.FillColor = Color.FromArgb(68, 104, 174);
			this.guna2Separator2.Location = new Point(352, 426);
			this.guna2Separator2.Name = "guna2Separator2";
			this.guna2Separator2.Size = new Size(394, 10);
			this.guna2Separator2.TabIndex = 81;
			this.color.Enabled = true;
			this.color.Tick += this.color_Tick;
			this.timerR.Tick += this.timerR_Tick;
			this.timerG.Tick += this.timerG_Tick;
			this.timerB.Tick += this.timerB_Tick;
			this.backgroundWorker1.DoWork += this.backgroundWorker1_DoWork;
			this.timer1.Enabled = true;
			this.timer1.Tick += this.timer1_Tick;
			this.timer2.Enabled = true;
			this.timer2.Tick += this.timer2_Tick;
			this.timer3.Enabled = true;
			this.timer3.Interval = 1;
			this.timer3.Tick += this.timer3_Tick;
			this.timer4.Tick += this.timer4_Tick;
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
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Border.DrawBorders = 15;
			this.kryptonPalette1.FormStyles.FormMain.StateCommon.Border.Rounding = 12;
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.Back.Color1 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.Back.Color2 = Color.FromArgb(250, 252, 252);
			this.kryptonPalette1.HeaderStyles.HeaderForm.StateCommon.ButtonEdgeInset = 12;
			this.timer5.Tick += this.timer5_Tick_2;
			this.timer6.Tick += this.timer6_Tick_1;
			this.timer7.Enabled = true;
			this.timer7.Interval = 99;
			this.timer7.Tick += this.timer7_Tick;
			this.timer8.Tick += this.timer8_Tick;
			this.materialCheckbox10.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox10, 0);
			this.materialCheckbox10.Depth = 0;
			this.materialCheckbox10.Location = new Point(92, 259);
			this.materialCheckbox10.Margin = new Padding(0);
			this.materialCheckbox10.MouseLocation = new Point(-1, -1);
			this.materialCheckbox10.MouseState = 0;
			this.materialCheckbox10.Name = "materialCheckbox10";
			this.materialCheckbox10.Ripple = true;
			this.materialCheckbox10.Size = new Size(79, 37);
			this.materialCheckbox10.TabIndex = 92;
			this.materialCheckbox10.Text = "Speed";
			this.materialCheckbox10.UseVisualStyleBackColor = true;
			this.materialCheckbox10.CheckedChanged += this.materialCheckbox10_CheckedChanged_1;
			this.materialCheckbox11.AutoSize = true;
			this.guna2Transition1.SetDecoration(this.materialCheckbox11, 0);
			this.materialCheckbox11.Depth = 0;
			this.materialCheckbox11.Location = new Point(92, 184);
			this.materialCheckbox11.Margin = new Padding(0);
			this.materialCheckbox11.MouseLocation = new Point(-1, -1);
			this.materialCheckbox11.MouseState = 0;
			this.materialCheckbox11.Name = "materialCheckbox11";
			this.materialCheckbox11.Ripple = true;
			this.materialCheckbox11.Size = new Size(71, 37);
			this.materialCheckbox11.TabIndex = 91;
			this.materialCheckbox11.Text = "Tags";
			this.materialCheckbox11.UseVisualStyleBackColor = true;
			this.materialCheckbox11.CheckedChanged += this.materialCheckbox11_CheckedChanged;
			base.AutoScaleDimensions = new SizeF(6f, 13f);
			base.AutoScaleMode = AutoScaleMode.Font;
			this.BackColor = Color.FromArgb(250, 252, 252);
			base.ClientSize = new Size(1075, 493);
			base.Controls.Add(this.guna2Separator2);
			base.Controls.Add(this.panel1);
			base.Controls.Add(this.iconButton3);
			base.Controls.Add(this.iconButton2);
			base.Controls.Add(this.iconButton1);
			base.Controls.Add(this.AuraLBL);
			base.Controls.Add(this.MinecraftVersion);
			this.guna2Transition1.SetDecoration(this, 0);
			this.MaximumSize = new Size(1280, 984);
			base.Name = "Main";
			base.Opacity = 0.95;
			base.Palette = this.kryptonPalette1;
			base.PaletteMode = 12;
			base.ShowIcon = false;
			base.StartPosition = FormStartPosition.CenterScreen;
			base.FormClosing += this.Main_FormClosing;
			base.FormClosed += this.Main_FormClosed;
			base.Load += this.Main_Load;
			base.KeyDown += this.Main_KeyDown;
			base.Leave += this.Main_Leave;
			this.panel7.ResumeLayout(false);
			this.panel7.PerformLayout();
			this.panel1.ResumeLayout(false);
			this.panel2.ResumeLayout(false);
			this.groupBox8.ResumeLayout(false);
			this.groupBox8.PerformLayout();
			this.groupBox6.ResumeLayout(false);
			this.groupBox6.PerformLayout();
			this.groupBox5.ResumeLayout(false);
			this.groupBox5.PerformLayout();
			this.groupBox7.ResumeLayout(false);
			this.groupBox7.PerformLayout();
			this.groupBox4.ResumeLayout(false);
			this.groupBox3.ResumeLayout(false);
			this.groupBox3.PerformLayout();
			this.groupBox2.ResumeLayout(false);
			this.groupBox2.PerformLayout();
			this.groupBox1.ResumeLayout(false);
			this.groupBox1.PerformLayout();
			base.ResumeLayout(false);
			base.PerformLayout();
		}

		// Token: 0x040002B4 RID: 692
		private SoundPlayer J = new SoundPlayer(Resources.arabicdeveloper);

		// Token: 0x040002B5 RID: 693
		private DotNetScanMemory_SmoLL m = new DotNetScanMemory_SmoLL();

		// Token: 0x040002B6 RID: 694
		public Process crProc;

		// Token: 0x040002B7 RID: 695
		private static float nm1 = 0.02666667f;

		// Token: 0x040002B8 RID: 696
		private static float nm2 = -0.02666667f;

		// Token: 0x040002B9 RID: 697
		private const int MOUSEEVENTF_LEFTDOWN = 2;

		// Token: 0x040002BA RID: 698
		private const int MOUSEEVENTF_LEFTUP = 4;

		// Token: 0x040002BB RID: 699
		private bool shiftdisable = false;

		// Token: 0x040002BC RID: 700
		private bool shiftdisable2 = false;

		// Token: 0x040002BD RID: 701
		private bool whileMovingEnabled = false;

		// Token: 0x040002BE RID: 702
		private bool whileMovingEnabled2 = false;

		// Token: 0x040002BF RID: 703
		private Random rnd = new Random();

		// Token: 0x040002C1 RID: 705
		private static string alts = "";

		// Token: 0x040002C2 RID: 706
		private bool leftbindserc;

		// Token: 0x040002C3 RID: 707
		private Keys leftbindkey;

		// Token: 0x040002C4 RID: 708
		private bool rightbindserc;

		// Token: 0x040002C5 RID: 709
		private Keys rightbindkey;

		// Token: 0x040002C6 RID: 710
		private bool boostbindserc;

		// Token: 0x040002C7 RID: 711
		private Keys boostbindkey;

		// Token: 0x040002C8 RID: 712
		private bool boost2bindserc;

		// Token: 0x040002C9 RID: 713
		private Keys boost2bindkey;

		// Token: 0x040002CA RID: 714
		private bool hidebindserc;

		// Token: 0x040002CB RID: 715
		private Keys hidebindkey;

		// Token: 0x040002CC RID: 716
		private bool rodserc;

		// Token: 0x040002CD RID: 717
		private Keys rodkey;

		// Token: 0x040002CE RID: 718
		private bool doubleleftserc;

		// Token: 0x040002CF RID: 719
		private Keys doubleleftkey;

		// Token: 0x040002D0 RID: 720
		private bool doublerightserc;

		// Token: 0x040002D1 RID: 721
		private Keys doublerightkey;

		// Token: 0x040002D2 RID: 722
		private bool visible = true;

		// Token: 0x040002D3 RID: 723
		public static string path = Assembly.GetExecutingAssembly().Location;

		// Token: 0x040002D4 RID: 724
		private int r = 244;

		// Token: 0x040002D5 RID: 725
		private int g = 65;

		// Token: 0x040002D6 RID: 726
		private int b = 65;

		// Token: 0x040002D7 RID: 727
		private string releaseId = "";

		// Token: 0x040002D8 RID: 728
		private bool alreadyAlerted;

		// Token: 0x040002D9 RID: 729
		private string debuggerRunning = "None";

		// Token: 0x040002DA RID: 730
		public string oldvaluereach = "00 00 00 00 00 00 08 40 00 00 00 00 00";

		// Token: 0x040002DB RID: 731
		public string oldvaluevelo = "00 00 00 00 00 40 BF 40";

		// Token: 0x040002DC RID: 732
		private bool boostcpslol = false;

		// Token: 0x040002DD RID: 733
		private bool boost2cpslol = false;

		// Token: 0x040002DE RID: 734
		private bool darod = false;

		// Token: 0x040002DF RID: 735
		private bool darodbr = false;

		// Token: 0x040002E0 RID: 736
		private int dadogdoing = 0;

		// Token: 0x040002E1 RID: 737
		private int delay = 100;

		// Token: 0x040002E2 RID: 738
		private const int MOUSEEVENTF_RIGHTDOWN = 8;

		// Token: 0x040002E3 RID: 739
		private const int MOUSEEVENTF_RIGHTUP = 16;

		// Token: 0x040002E4 RID: 740
		private IContainer components = null;

		// Token: 0x040002E5 RID: 741
		private Guna2DragControl guna2DragControl1;

		// Token: 0x040002E6 RID: 742
		private System.Windows.Forms.Timer AutoClicker;

		// Token: 0x040002E7 RID: 743
		private System.Windows.Forms.Timer BlockHit;

		// Token: 0x040002E8 RID: 744
		private System.Windows.Forms.Timer Jitter;

		// Token: 0x040002E9 RID: 745
		private System.Windows.Forms.Timer VersionLoad1;

		// Token: 0x040002EA RID: 746
		private System.Windows.Forms.Timer VersionLoad2;

		// Token: 0x040002EB RID: 747
		private System.Windows.Forms.Timer Bind1;

		// Token: 0x040002EC RID: 748
		private System.Windows.Forms.Timer Bind2;

		// Token: 0x040002ED RID: 749
		private ToolTip toolTip1;

		// Token: 0x040002EE RID: 750
		private System.Windows.Forms.Timer AutoClicker2;

		// Token: 0x040002EF RID: 751
		private Guna2Transition guna2Transition1;

		// Token: 0x040002F0 RID: 752
		private System.Windows.Forms.Timer color;

		// Token: 0x040002F1 RID: 753
		private System.Windows.Forms.Timer timerR;

		// Token: 0x040002F2 RID: 754
		private System.Windows.Forms.Timer timerG;

		// Token: 0x040002F3 RID: 755
		private System.Windows.Forms.Timer timerB;

		// Token: 0x040002F4 RID: 756
		private BackgroundWorker backgroundWorker1;

		// Token: 0x040002F5 RID: 757
		private System.Windows.Forms.Timer timer1;

		// Token: 0x040002F6 RID: 758
		private System.Windows.Forms.Timer timer2;

		// Token: 0x040002F7 RID: 759
		private System.Windows.Forms.Timer timer3;

		// Token: 0x040002F8 RID: 760
		private Label MinecraftVersion;

		// Token: 0x040002F9 RID: 761
		private Guna2Button guna2Button7;

		// Token: 0x040002FA RID: 762
		private Guna2Button guna2Button6;

		// Token: 0x040002FB RID: 763
		private System.Windows.Forms.Timer timer4;

		// Token: 0x040002FC RID: 764
		private Panel panel7;

		// Token: 0x040002FD RID: 765
		private Label label19;

		// Token: 0x040002FE RID: 766
		private Label label20;

		// Token: 0x040002FF RID: 767
		private Label label21;

		// Token: 0x04000300 RID: 768
		private Label label22;

		// Token: 0x04000301 RID: 769
		private Label label18;

		// Token: 0x04000302 RID: 770
		private Label label17;

		// Token: 0x04000303 RID: 771
		private Label label16;

		// Token: 0x04000304 RID: 772
		private Label label15;

		// Token: 0x04000305 RID: 773
		private Label label14;

		// Token: 0x04000306 RID: 774
		private KryptonPalette kryptonPalette1;

		// Token: 0x04000307 RID: 775
		private Label AuraLBL;

		// Token: 0x04000308 RID: 776
		private IconButton iconButton3;

		// Token: 0x04000309 RID: 777
		private IconButton iconButton2;

		// Token: 0x0400030A RID: 778
		private IconButton iconButton1;

		// Token: 0x0400030B RID: 779
		private Panel panel1;

		// Token: 0x0400030C RID: 780
		private GroupBox groupBox1;

		// Token: 0x0400030D RID: 781
		private Guna2Separator guna2Separator4;

		// Token: 0x0400030E RID: 782
		private GroupBox groupBox2;

		// Token: 0x0400030F RID: 783
		private Label label6;

		// Token: 0x04000310 RID: 784
		private Guna2TrackBar guna2TrackBar4;

		// Token: 0x04000311 RID: 785
		private Label label5;

		// Token: 0x04000312 RID: 786
		private Guna2TrackBar guna2TrackBar3;

		// Token: 0x04000313 RID: 787
		private Label label4;

		// Token: 0x04000314 RID: 788
		private Label label3;

		// Token: 0x04000315 RID: 789
		private Guna2TrackBar guna2TrackBar2;

		// Token: 0x04000316 RID: 790
		private GroupBox groupBox3;

		// Token: 0x04000317 RID: 791
		private MaterialCheckbox guna2CheckBox9;

		// Token: 0x04000318 RID: 792
		private MaterialCheckbox materialCheckbox_0;

		// Token: 0x04000319 RID: 793
		private MaterialCheckbox hitCHECK;

		// Token: 0x0400031A RID: 794
		private MaterialCheckbox materialCheckbox_1;

		// Token: 0x0400031B RID: 795
		private MaterialCheckbox megaCHECKBOX;

		// Token: 0x0400031C RID: 796
		private Label lblreachlol;

		// Token: 0x0400031D RID: 797
		private Guna2TrackBar hitTRACK;

		// Token: 0x0400031E RID: 798
		private Guna2TrackBar velocityTRACK;

		// Token: 0x0400031F RID: 799
		private Guna2TrackBar guna2TrackBar_0;

		// Token: 0x04000320 RID: 800
		private MaterialCheckbox guna2CheckBox23;

		// Token: 0x04000321 RID: 801
		private MaterialCheckbox guna2CheckBox22;

		// Token: 0x04000322 RID: 802
		private Label label25;

		// Token: 0x04000323 RID: 803
		private Guna2TrackBar guna2TrackBar11;

		// Token: 0x04000324 RID: 804
		private Label label26;

		// Token: 0x04000325 RID: 805
		private Guna2TrackBar guna2TrackBar12;

		// Token: 0x04000326 RID: 806
		private Guna2Button guna2Button20;

		// Token: 0x04000327 RID: 807
		private Label label13;

		// Token: 0x04000328 RID: 808
		private Guna2TrackBar guna2TrackBar9;

		// Token: 0x04000329 RID: 809
		private Guna2Button guna2Button21;

		// Token: 0x0400032A RID: 810
		private Label label24;

		// Token: 0x0400032B RID: 811
		private Guna2TrackBar guna2TrackBar10;

		// Token: 0x0400032C RID: 812
		private MaterialCheckbox guna2CheckBox10;

		// Token: 0x0400032D RID: 813
		private MaterialCheckbox guna2CheckBox11;

		// Token: 0x0400032E RID: 814
		private MaterialCheckbox guna2CheckBox12;

		// Token: 0x0400032F RID: 815
		private MaterialCheckbox guna2CheckBox20;

		// Token: 0x04000330 RID: 816
		private MaterialCheckbox guna2CheckBox21;

		// Token: 0x04000331 RID: 817
		private Label label12;

		// Token: 0x04000332 RID: 818
		private Label label1;

		// Token: 0x04000333 RID: 819
		private Label label7;

		// Token: 0x04000334 RID: 820
		private Guna2Button guna2Button5;

		// Token: 0x04000335 RID: 821
		private Label label11;

		// Token: 0x04000336 RID: 822
		private Guna2TrackBar guna2TrackBar5;

		// Token: 0x04000337 RID: 823
		private MaterialCheckbox guna2CheckBox6;

		// Token: 0x04000338 RID: 824
		private MaterialCheckbox guna2CheckBox7;

		// Token: 0x04000339 RID: 825
		private MaterialCheckbox guna2CheckBox8;

		// Token: 0x0400033A RID: 826
		private MaterialCheckbox guna2CheckBox5;

		// Token: 0x0400033B RID: 827
		private MaterialCheckbox guna2CheckBox4;

		// Token: 0x0400033C RID: 828
		private MaterialCheckbox guna2CheckBox3;

		// Token: 0x0400033D RID: 829
		private MaterialCheckbox guna2CheckBox2;

		// Token: 0x0400033E RID: 830
		private MaterialCheckbox guna2CheckBox1;

		// Token: 0x0400033F RID: 831
		private Label label28;

		// Token: 0x04000340 RID: 832
		private Label label29;

		// Token: 0x04000341 RID: 833
		private Guna2Button guna2Button1;

		// Token: 0x04000342 RID: 834
		private Label label2;

		// Token: 0x04000343 RID: 835
		private Guna2TrackBar guna2TrackBar1;

		// Token: 0x04000344 RID: 836
		private Guna2Separator guna2Separator2;

		// Token: 0x04000345 RID: 837
		private GroupBox groupBox4;

		// Token: 0x04000346 RID: 838
		private Panel panel2;

		// Token: 0x04000347 RID: 839
		private GroupBox groupBox6;

		// Token: 0x04000348 RID: 840
		private MaterialCheckbox guna2CheckBox19;

		// Token: 0x04000349 RID: 841
		private Guna2TrackBar guna2TrackBar8;

		// Token: 0x0400034A RID: 842
		private Guna2TrackBar guna2TrackBar7;

		// Token: 0x0400034B RID: 843
		private Label label23;

		// Token: 0x0400034C RID: 844
		private Guna2TrackBar guna2TrackBar6;

		// Token: 0x0400034D RID: 845
		private Guna2Separator guna2Separator1;

		// Token: 0x0400034E RID: 846
		private Guna2Button hideBind;

		// Token: 0x0400034F RID: 847
		private GroupBox groupBox5;

		// Token: 0x04000350 RID: 848
		private MaterialCheckbox guna2CheckBox17;

		// Token: 0x04000351 RID: 849
		private MaterialCheckbox guna2CheckBox16;

		// Token: 0x04000352 RID: 850
		private MaterialCheckbox logsBOX;

		// Token: 0x04000353 RID: 851
		private MaterialCheckbox guna2CheckBox18;

		// Token: 0x04000354 RID: 852
		private MaterialCheckbox Regeditbox;

		// Token: 0x04000355 RID: 853
		private MaterialCheckbox guna2CheckBox14;

		// Token: 0x04000356 RID: 854
		private MaterialCheckbox guna2CheckBox15;

		// Token: 0x04000357 RID: 855
		private MaterialCheckbox guna2CheckBox13;

		// Token: 0x04000358 RID: 856
		private Guna2Button guna2Button14;

		// Token: 0x04000359 RID: 857
		private MaterialCheckbox materialCheckbox1;

		// Token: 0x0400035A RID: 858
		private MaterialCheckbox materialCheckbox2;

		// Token: 0x0400035B RID: 859
		private GroupBox groupBox7;

		// Token: 0x0400035C RID: 860
		private Guna2Separator guna2Separator3;

		// Token: 0x0400035D RID: 861
		private MaterialCheckbox materialCheckbox6;

		// Token: 0x0400035E RID: 862
		private MaterialCheckbox materialCheckbox7;

		// Token: 0x0400035F RID: 863
		private MaterialCheckbox materialCheckbox8;

		// Token: 0x04000360 RID: 864
		private Label label10;

		// Token: 0x04000361 RID: 865
		private Guna2Button guna2Button3;

		// Token: 0x04000362 RID: 866
		private Label label27;

		// Token: 0x04000363 RID: 867
		private Guna2TrackBar guna2TrackBar14;

		// Token: 0x04000364 RID: 868
		private MaterialCheckbox materialCheckbox3;

		// Token: 0x04000365 RID: 869
		private MaterialCheckbox materialCheckbox4;

		// Token: 0x04000366 RID: 870
		private MaterialCheckbox materialCheckbox5;

		// Token: 0x04000367 RID: 871
		private Label label8;

		// Token: 0x04000368 RID: 872
		private Guna2Button guna2Button2;

		// Token: 0x04000369 RID: 873
		private Label label9;

		// Token: 0x0400036A RID: 874
		private Guna2TrackBar guna2TrackBar13;

		// Token: 0x0400036B RID: 875
		private System.Windows.Forms.Timer timer5;

		// Token: 0x0400036C RID: 876
		private System.Windows.Forms.Timer timer6;

		// Token: 0x0400036D RID: 877
		private System.Windows.Forms.Timer timer7;

		// Token: 0x0400036E RID: 878
		private GroupBox groupBox8;

		// Token: 0x0400036F RID: 879
		private Label label32;

		// Token: 0x04000370 RID: 880
		private Label label31;

		// Token: 0x04000371 RID: 881
		private MaterialComboBox materialComboBox2;

		// Token: 0x04000372 RID: 882
		private MaterialComboBox materialComboBox1;

		// Token: 0x04000373 RID: 883
		private Guna2Button guna2Button4;

		// Token: 0x04000374 RID: 884
		private Label label30;

		// Token: 0x04000375 RID: 885
		private Guna2TrackBar guna2TrackBar15;

		// Token: 0x04000376 RID: 886
		private System.Windows.Forms.Timer timer8;

		// Token: 0x04000377 RID: 887
		private MaterialCheckbox materialCheckbox9;

		// Token: 0x04000378 RID: 888
		private MaterialCheckbox materialCheckbox10;

		// Token: 0x04000379 RID: 889
		private MaterialCheckbox materialCheckbox11;

		// Token: 0x02000027 RID: 39
		private enum KeyStates
		{
			// Token: 0x0400037B RID: 891
			None,
			// Token: 0x0400037C RID: 892
			Down,
			// Token: 0x0400037D RID: 893
			Toggled
		}

		// Token: 0x02000028 RID: 40
		public class AutoClosingMessageBox
		{
			// Token: 0x06000190 RID: 400 RVA: 0x00002B46 File Offset: 0x00000D46
			private AutoClosingMessageBox(string string_0, string string_1, int int_0)
			{
				this._caption = string_1;
				this._timeoutTimer = new System.Threading.Timer(new TimerCallback(this.OnTimerElapsed), null, int_0, -1);
				MessageBox.Show(string_0, string_1);
			}

			// Token: 0x06000191 RID: 401 RVA: 0x00002B77 File Offset: 0x00000D77
			public static void Show(string string_0, string string_1, int int_0)
			{
				new Main.AutoClosingMessageBox(string_0, string_1, int_0);
			}

			// Token: 0x06000192 RID: 402 RVA: 0x00016E64 File Offset: 0x00015064
			private void OnTimerElapsed(object object_0)
			{
				IntPtr intPtr = Main.AutoClosingMessageBox.FindWindow(null, this._caption);
				if (intPtr != IntPtr.Zero)
				{
					Main.AutoClosingMessageBox.SendMessage(intPtr, 16U, IntPtr.Zero, IntPtr.Zero);
				}
				this._timeoutTimer.Dispose();
			}

			// Token: 0x06000193 RID: 403
			[DllImport("user32.dll", SetLastError = true)]
			private static extern IntPtr FindWindow(string string_0, string string_1);

			// Token: 0x06000194 RID: 404
			[DllImport("user32.dll", CharSet = CharSet.Auto)]
			private static extern IntPtr SendMessage(IntPtr intptr_0, uint uint_0, IntPtr intptr_1, IntPtr intptr_2);

			// Token: 0x0400037E RID: 894
			private System.Threading.Timer _timeoutTimer;

			// Token: 0x0400037F RID: 895
			private string _caption;

			// Token: 0x04000380 RID: 896
			private const int WM_CLOSE = 16;
		}
	}
}
