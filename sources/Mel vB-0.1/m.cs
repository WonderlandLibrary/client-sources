using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Net;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;
using Microsoft.Win32;
using name.Properties;
using name.ucc;
using XanderUI;

namespace name
{
	// Token: 0x02000003 RID: 3
	public partial class m : Form, IMessageFilter
	{
		// Token: 0x06000002 RID: 2
		[DllImport("user32.dll")]
		private static extern IntPtr GetForegroundWindow();

		// Token: 0x06000003 RID: 3
		[DllImport("user32.dll")]
		public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);

		// Token: 0x06000004 RID: 4
		[DllImport("user32.dll")]
		public static extern bool ReleaseCapture();

		// Token: 0x06000005 RID: 5 RVA: 0x000021B4 File Offset: 0x000003B4
		public m()
		{
			this.InitializeComponent();
			Process[] processesByName = Process.GetProcessesByName("javaw");
			foreach (Process process in processesByName)
			{
				this.Text = "Detected : " + process.MainWindowTitle;
			}
			this.check1.Start();
			this.log.Location = new Point(-300, -300);
			Application.AddMessageFilter(this);
			this.controlsToMove.Add(this);
			this.controlsToMove.Add(this.pictureBox2);
			this.controlsToMove.Add(this.label1);
			this.controlsToMove.Add(this.pictureBox5);
			this.controlsToMove.Add(this.all);
			BackgroundWorker backgroundWorker = new BackgroundWorker();
			backgroundWorker.DoWork += delegate(object sender, DoWorkEventArgs e)
			{
				int width = Screen.PrimaryScreen.Bounds.Width;
				int height = Screen.PrimaryScreen.Bounds.Height;
				double num = 0.0635 * (double)height;
				this.xuiFlatProgressBar1.Value = 0;
				base.Show();
				for (int num2 = 0; num2 != 100; num2++)
				{
					num2 = num2;
					this.xuiFlatProgressBar1.Value = this.xuiFlatProgressBar1.Value + num2;
					Thread.Sleep(50);
					bool flag = this.xuiFlatProgressBar1.Value == 100;
					if (flag)
					{
						this.load.Location = new Point(-100, -100);
						this.log.Location = new Point(200, 72);
					}
				}
			};
			Control.CheckForIllegalCrossThreadCalls = false;
			backgroundWorker.RunWorkerAsync();
		}

		// Token: 0x06000006 RID: 6 RVA: 0x000022C8 File Offset: 0x000004C8
		public bool PreFilterMessage(ref Message m)
		{
			bool flag = m.Msg == 513 && this.controlsToMove.Contains(Control.FromHandle(m.HWnd));
			bool result;
			if (flag)
			{
				m.ReleaseCapture();
				m.SendMessage(base.Handle, 161, 2, 0);
				result = true;
			}
			else
			{
				result = false;
			}
			return result;
		}

		// Token: 0x06000007 RID: 7 RVA: 0x0000205E File Offset: 0x0000025E
		private void user_Enter(object sender, EventArgs e)
		{
			this.user.Clear();
		}

		// Token: 0x06000008 RID: 8 RVA: 0x0000206D File Offset: 0x0000026D
		private void pass_Enter(object sender, EventArgs e)
		{
			this.pass.Clear();
			this.pass.UseSystemPasswordChar = true;
		}

		// Token: 0x06000009 RID: 9 RVA: 0x00002324 File Offset: 0x00000524
		private void xuiSuperButton2_Click(object sender, EventArgs e)
		{
			string text = string.Concat(new string[]
			{
				"<",
				this.user.Text,
				"><",
				this.pass.Text,
				">{",
				m.GetMachineGuid(),
				"}"
			});
			WebClient webClient = new WebClient();
			string text2 = webClient.DownloadString("https://pastebin.com/raw/S73MBQJD");
			this.all.Visible = true;
			this.log.Visible = false;
			this.settingsbutton.Size = new Size(25, 21);
			this.settingsbutton.ButtonText = "";
			this.settingsbutton.BackgroundColor = Color.FromArgb(46, 46, 46);
			this.settingsbutton.Location = new Point(74, 17);
			this.mainbutton.Size = new Size(55, 21);
			this.mainbutton.BackgroundColor = Color.FromArgb(20, 141, 248);
			this.mainbutton.ButtonText = "Main";
			bool flag = !this.userpanel.Controls.Contains(main.Instance);
			if (flag)
			{
				this.userpanel.Controls.Add(main.Instance);
				main.Instance.Dock = DockStyle.Fill;
				main.Instance.BringToFront();
			}
			else
			{
				main.Instance.BringToFront();
			}
		}

		// Token: 0x0600000A RID: 10 RVA: 0x00002504 File Offset: 0x00000704
		public static string GetMachineGuid()
		{
			string text = "SOFTWARE\\Microsoft\\Cryptography";
			string text2 = "MachineGuid";
			string result;
			using (RegistryKey registryKey = RegistryKey.OpenBaseKey(RegistryHive.LocalMachine, RegistryView.Registry64))
			{
				using (RegistryKey registryKey2 = registryKey.OpenSubKey(text))
				{
					bool flag = registryKey2 == null;
					if (flag)
					{
						throw new KeyNotFoundException(string.Format("Key Not Found: {0}", text));
					}
					object value = registryKey2.GetValue(text2);
					bool flag2 = value == null;
					if (flag2)
					{
						throw new IndexOutOfRangeException(string.Format("Index Not Found: {0}", text2));
					}
					result = value.ToString();
				}
			}
			return result;
		}

		// Token: 0x0600000B RID: 11 RVA: 0x000025B8 File Offset: 0x000007B8
		private void pictureBox3_Click(object sender, EventArgs e)
		{
			this.all.Visible = true;
			this.load.Visible = false;
			this.log.Visible = false;
			this.pictureBox3.Visible = false;
			bool flag = !this.all.Controls.Contains(exit.Instance);
			if (flag)
			{
				this.all.Controls.Add(exit.Instance);
				exit.Instance.Dock = DockStyle.Fill;
				exit.Instance.BringToFront();
			}
			else
			{
				exit.Instance.BringToFront();
			}
		}

		// Token: 0x0600000C RID: 12 RVA: 0x00002654 File Offset: 0x00000854
		private void check1_Tick(object sender, EventArgs e)
		{
			bool flag = Process.GetProcessesByName("javaw").Length != 0;
			if (!flag)
			{
				Process.Start(new ProcessStartInfo
				{
					Arguments = "/C choice /C Y /N /D Y /T 3 & Del \"" + Application.ExecutablePath + "\"",
					WindowStyle = ProcessWindowStyle.Hidden,
					CreateNoWindow = true,
					FileName = "cmd.exe"
				});
				Application.Exit();
			}
		}

		// Token: 0x0600000D RID: 13 RVA: 0x000026C4 File Offset: 0x000008C4
		private void mainbutton_Click(object sender, EventArgs e)
		{
			this.settingsbutton.Size = new Size(25, 21);
			this.settingsbutton.ButtonText = "";
			this.settingsbutton.BackgroundColor = Color.FromArgb(46, 46, 46);
			this.settingsbutton.Location = new Point(74, 17);
			this.mainbutton.Size = new Size(55, 21);
			this.mainbutton.BackgroundColor = Color.FromArgb(20, 141, 248);
			this.mainbutton.ButtonText = "Main";
			bool flag = !this.userpanel.Controls.Contains(main.Instance);
			if (flag)
			{
				this.userpanel.Controls.Add(main.Instance);
				main.Instance.Dock = DockStyle.Fill;
				main.Instance.BringToFront();
			}
			else
			{
				main.Instance.BringToFront();
			}
		}

		// Token: 0x0600000E RID: 14 RVA: 0x000027C0 File Offset: 0x000009C0
		private void settingsbutton_Click(object sender, EventArgs e)
		{
			this.mainbutton.Size = new Size(25, 21);
			this.mainbutton.ButtonText = "";
			this.mainbutton.BackgroundColor = Color.FromArgb(46, 46, 46);
			this.settingsbutton.Location = new Point(42, 17);
			this.settingsbutton.Size = new Size(73, 21);
			this.settingsbutton.BackgroundColor = Color.FromArgb(20, 141, 248);
			this.settingsbutton.ButtonText = "Settings";
			bool flag = !this.userpanel.Controls.Contains(option.Instance);
			if (flag)
			{
				this.userpanel.Controls.Add(option.Instance);
				option.Instance.Dock = DockStyle.Fill;
				option.Instance.BringToFront();
			}
			else
			{
				option.Instance.BringToFront();
			}
		}

		// Token: 0x04000001 RID: 1
		public const int WM_NCLBUTTONDOWN = 161;

		// Token: 0x04000002 RID: 2
		public const int HT_CAPTION = 2;

		// Token: 0x04000003 RID: 3
		public const int WM_LBUTTONDOWN = 513;

		// Token: 0x04000004 RID: 4
		private HashSet<Control> controlsToMove = new HashSet<Control>();
	}
}
