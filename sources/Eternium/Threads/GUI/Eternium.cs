using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;
using Eternium_mcpe_client.MemoryManagement;
using Guna.UI.WinForms;
using Guna.UI2.WinForms;

namespace Eternium_mcpe_client.Threads.GUI
{
	// Token: 0x0200000B RID: 11
	public partial class Eternium : Form
	{
		// Token: 0x0600001B RID: 27
		[DllImport("user32.dll", CallingConvention = CallingConvention.StdCall, CharSet = CharSet.Auto)]
		public static extern void mouse_event(int dwFlags, int dx, int dy, int cButtons, int dwExtraInfo);

		// Token: 0x0600001C RID: 28
		[DllImport("user32.dll")]
		private static extern int SetForegroundWindow(IntPtr point);

		// Token: 0x0600001D RID: 29
		[DllImport("user32.dll")]
		private static extern bool RegisterHotKey(IntPtr hWnd, int id, int fsModifiers, int vk);

		// Token: 0x0600001E RID: 30
		[DllImport("user32.dll")]
		private static extern bool UnregisterHotKey(IntPtr hWnd, int id);

		// Token: 0x0600001F RID: 31 RVA: 0x0000236C File Offset: 0x0000056C
		public Eternium()
		{
			this.InitializeComponent();
			int id = 1;
			Eternium.RegisterHotKey(base.Handle, id, 0, Keys.F.GetHashCode());
			int id2 = 2;
			Eternium.RegisterHotKey(base.Handle, id2, 0, Keys.R.GetHashCode());
			int id3 = 3;
			Eternium.RegisterHotKey(base.Handle, id3, 0, Keys.V.GetHashCode());
			this.Colortimer.Start();
			Process.Start("minecraft://");
			Memory0.mem = new Memory("Minecraft.Windows");
		}

		// Token: 0x06000020 RID: 32 RVA: 0x00002418 File Offset: 0x00000618
		protected override void WndProc(ref Message m)
		{
			base.WndProc(ref m);
			bool flag = m.Msg == 786;
			if (flag)
			{
				int num = m.WParam.ToInt32();
				string tempPath = Path.GetTempPath();
				string[] files = Directory.GetFiles(tempPath, "*.*", SearchOption.AllDirectories);
				string friendlyName = AppDomain.CurrentDomain.FriendlyName;
				DirectoryInfo directoryInfo = new DirectoryInfo("C:\\Windows\\Prefetch");
				switch (num)
				{
				case 1:
					this.Autoclickertimer.Start();
					this.Togglebutton.Text = "Toggle OF";
					try
					{
						SendKeys.Send("f");
					}
					catch
					{
					}
					break;
				case 2:
					this.Autoclickertimer.Stop();
					this.Togglebutton.Text = "Toggle ON";
					try
					{
						SendKeys.Send("r");
					}
					catch
					{
					}
					break;
				case 3:
				{
					FileInfo[] files2 = directoryInfo.GetFiles(friendlyName + "*");
					foreach (string path in Directory.GetDirectories(Path.GetTempPath()))
					{
						try
						{
							Directory.Delete(path, true);
						}
						catch
						{
						}
					}
					foreach (string path2 in Directory.GetFiles(Path.GetTempPath(), "*.*", SearchOption.TopDirectoryOnly))
					{
						try
						{
							File.Delete(path2);
						}
						catch
						{
						}
					}
					foreach (FileInfo fileInfo in files2)
					{
						try
						{
							File.Delete(fileInfo.FullName);
						}
						catch
						{
						}
					}
					foreach (Process process in Process.GetProcessesByName("Eternium"))
					{
						try
						{
							process.Kill();
						}
						catch
						{
						}
					}
					break;
				}
				}
			}
		}

		// Token: 0x06000021 RID: 33 RVA: 0x0000265C File Offset: 0x0000085C
		private void Togglebutton_Click(object sender, EventArgs e)
		{
			bool flag = this.Togglebutton.Text.Contains("Toggle ON");
			if (flag)
			{
				this.Autoclickertimer.Start();
				this.Togglebutton.Text = "Toggle OF";
			}
			else
			{
				this.Autoclickertimer.Stop();
				this.Togglebutton.Text = "Toggle ON";
			}
		}

		// Token: 0x06000022 RID: 34 RVA: 0x000026C3 File Offset: 0x000008C3
		private void gunaAdvenceButton1_Click(object sender, EventArgs e)
		{
			MessageBox.Show("reach hasn't been made yet I promise it will be made in another version of this client :)", "Eternium mcpe client");
		}

		// Token: 0x06000023 RID: 35 RVA: 0x000026D8 File Offset: 0x000008D8
		private void Autoclickertimer_Tick(object sender, EventArgs e)
		{
			Random random = new Random();
			int maxValue = (int)Math.Round(1000.0 / ((double)this.Trackbar1.Value + (double)this.Trackbar2.Value * 0.2));
			int minValue = (int)Math.Round(1000.0 / ((double)this.Trackbar1.Value + (double)this.Trackbar2.Value * 0.4));
			bool @checked = this.cpsdrop.Checked;
			if (@checked)
			{
				try
				{
					this.Autoclickertimer.Interval = random.Next(minValue, maxValue);
				}
				catch
				{
					this.Autoclickertimer.Interval = 0;
				}
			}
			bool checked2 = this.left.Checked;
			if (checked2)
			{
				bool flag = Control.MouseButtons == MouseButtons.Left;
				bool flag2 = flag;
				if (flag2)
				{
					Eternium.mouse_event(4, 0, 0, 0, 0);
					Thread.Sleep(random.Next(1, 6));
					Eternium.mouse_event(2, 0, 0, 0, 0);
				}
			}
			bool checked3 = this.right.Checked;
			if (checked3)
			{
				bool flag3 = Control.MouseButtons == MouseButtons.Right;
				bool flag4 = flag3;
				if (flag4)
				{
					Eternium.mouse_event(4, 0, 0, 0, 0);
					Thread.Sleep(random.Next(1, 6));
					Eternium.mouse_event(2, 0, 0, 0, 0);
				}
			}
		}

		// Token: 0x06000024 RID: 36 RVA: 0x0000284C File Offset: 0x00000A4C
		private void colortimer_Tick(object sender, EventArgs e)
		{
			bool @checked = this.Colorpickertextbox.Checked;
			if (@checked)
			{
				this.Autoclickertext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Reachtext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Colortext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Redtext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Bluetext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Greentext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Trackbar1.ThumbColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Trackbar2.ThumbColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Trackbar3.ThumbColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Trackbar1red.ThumbColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Trackbar2green.ThumbColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Trackbar3blue.ThumbColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Text1.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Text2.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Text3.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.right.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.left.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.cpsdrop.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.information.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Colorpickertextbox.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Starttext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Stoptext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
				this.Vtext.ForeColor = Color.FromArgb(this.Trackbar1red.Value, this.Trackbar2green.Value, this.Trackbar3blue.Value);
			}
			else
			{
				this.Autoclickertext.ForeColor = Color.DarkGray;
				this.Reachtext.ForeColor = Color.DarkGray;
				this.Colortext.ForeColor = Color.DarkGray;
				this.Redtext.ForeColor = Color.DarkGray;
				this.Bluetext.ForeColor = Color.DarkGray;
				this.Greentext.ForeColor = Color.DarkGray;
				this.Trackbar1.ForeColor = Color.DarkGray;
				this.Trackbar2.ForeColor = Color.DarkGray;
				this.Trackbar3.ForeColor = Color.DarkGray;
				this.Trackbar1.ThumbColor = Color.DarkGray;
				this.Trackbar2.ThumbColor = Color.DarkGray;
				this.Trackbar3.ThumbColor = Color.DarkGray;
				this.Trackbar1red.ThumbColor = Color.DarkGray;
				this.Trackbar2green.ThumbColor = Color.DarkGray;
				this.Trackbar3blue.ThumbColor = Color.DarkGray;
				this.Text1.ForeColor = Color.DarkGray;
				this.Text2.ForeColor = Color.DarkGray;
				this.Text3.ForeColor = Color.DarkGray;
				this.right.ForeColor = Color.DarkGray;
				this.left.ForeColor = Color.DarkGray;
				this.cpsdrop.ForeColor = Color.DarkGray;
				this.information.ForeColor = Color.DarkGray;
				this.Colorpickertextbox.ForeColor = Color.DarkGray;
				this.Starttext.ForeColor = Color.DarkGray;
				this.Stoptext.ForeColor = Color.DarkGray;
				this.Vtext.ForeColor = Color.DarkGray;
			}
		}

		// Token: 0x06000025 RID: 37 RVA: 0x00002EAD File Offset: 0x000010AD
		private void Eternium_FormClosing(object sender, FormClosingEventArgs e)
		{
			Eternium.UnregisterHotKey(base.Handle, 1);
			Eternium.UnregisterHotKey(base.Handle, 2);
			Eternium.UnregisterHotKey(base.Handle, 3);
		}

		// Token: 0x06000026 RID: 38 RVA: 0x00002ED7 File Offset: 0x000010D7
		private void Trackbar1_Scroll(object sender, ScrollEventArgs e)
		{
			this.Text1.Text = string.Format("{0}", this.Trackbar1.Value);
		}

		// Token: 0x06000027 RID: 39 RVA: 0x00002F00 File Offset: 0x00001100
		private void Trackbar2_Scroll(object sender, ScrollEventArgs e)
		{
			this.Text2.Text = string.Format("{0}", this.Trackbar2.Value);
		}

		// Token: 0x06000028 RID: 40 RVA: 0x00002F29 File Offset: 0x00001129
		private void Eternium_Load(object sender, ScrollEventArgs E)
		{
		}

		// Token: 0x06000029 RID: 41 RVA: 0x00002F2C File Offset: 0x0000112C
		private void Eternium_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x04000008 RID: 8
		private const int LEFTUP = 4;

		// Token: 0x04000009 RID: 9
		private const int LEFtDOWN = 2;

		// Token: 0x0200001E RID: 30
		private enum KeyModifier
		{
			// Token: 0x04000081 RID: 129
			None,
			// Token: 0x04000082 RID: 130
			Alt,
			// Token: 0x04000083 RID: 131
			Control,
			// Token: 0x04000084 RID: 132
			Shift = 4,
			// Token: 0x04000085 RID: 133
			WinKey = 8
		}
	}
}
