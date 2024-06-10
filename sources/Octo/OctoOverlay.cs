using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Octo_Client.Properties;

namespace Octo_Client
{
	// Token: 0x02000002 RID: 2
	public partial class OctoOverlay : Form
	{
		// Token: 0x06000001 RID: 1
		[DllImport("user32.dll")]
		private static extern int SetWindowLong(IntPtr hWnd, int nIndex, int dwNewLong);

		// Token: 0x06000002 RID: 2
		[DllImport("user32.dll", SetLastError = true)]
		private static extern int GetWindowLong(IntPtr hWnd, int nIndex);

		// Token: 0x06000003 RID: 3
		[DllImport("user32.dll", SetLastError = true)]
		private static extern IntPtr FindWindow(string lpClassName, string lpWindowName);

		// Token: 0x06000004 RID: 4
		[DllImport("user32.dll")]
		[return: MarshalAs(UnmanagedType.Bool)]
		public static extern bool GetWindowRect(IntPtr hWnd, out OctoOverlay.RECT lpRect);

		// Token: 0x06000005 RID: 5 RVA: 0x00002048 File Offset: 0x00000248
		public OctoOverlay()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000006 RID: 6 RVA: 0x00002074 File Offset: 0x00000274
		private void OctoOverlay_Load(object sender, EventArgs e)
		{
			this.BackColor = Color.Wheat;
			base.TransparencyKey = Color.Wheat;
			base.TopMost = true;
			base.FormBorderStyle = FormBorderStyle.None;
			int windowLong = OctoOverlay.GetWindowLong(base.Handle, -20);
			OctoOverlay.SetWindowLong(base.Handle, -20, windowLong | 524288 | 32);
			this.backgroundWorker1.RunWorkerAsync();
		}

		// Token: 0x06000007 RID: 7 RVA: 0x000020DC File Offset: 0x000002DC
		private void OctoOverlay_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x06000008 RID: 8 RVA: 0x000020E0 File Offset: 0x000002E0
		private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
		{
			for (;;)
			{
				OctoOverlay.GetWindowRect(this.handle, out this.rect);
				base.Size = new Size(this.rect.right - this.rect.left, this.rect.bottom - this.rect.top);
				base.Top = this.rect.top;
				base.Left = this.rect.left;
				bool speedEnabled = OctoMain.speedEnabled;
				if (speedEnabled)
				{
					this.speed.Show();
				}
				else
				{
					this.speed.Hide();
				}
				bool airJumpEnabled = OctoMain.AirJumpEnabled;
				if (airJumpEnabled)
				{
					this.label5.Show();
				}
				else
				{
					this.label5.Hide();
				}
				bool noFallEnabled = OctoMain.NoFallEnabled;
				if (noFallEnabled)
				{
					this.label4.Show();
				}
				else
				{
					this.label4.Hide();
				}
				bool slowFallEnabled = OctoMain.SlowFallEnabled;
				if (slowFallEnabled)
				{
					this.label3.Show();
				}
				else
				{
					this.label3.Hide();
				}
				bool flyEnabled = OctoMain.FlyEnabled;
				if (flyEnabled)
				{
					this.label2.Show();
				}
				else
				{
					this.label2.Hide();
				}
				bool superJumpEnabled = OctoMain.SuperJumpEnabled;
				if (superJumpEnabled)
				{
					this.label6.Show();
				}
				else
				{
					this.label6.Hide();
				}
				bool hoverENabled = OctoMain.HoverENabled;
				if (hoverENabled)
				{
					this.label7.Show();
				}
				else
				{
					this.label7.Hide();
				}
				bool phaseEnabled = OctoMain.PhaseEnabled;
				if (phaseEnabled)
				{
					this.label8.Show();
				}
				else
				{
					this.label8.Hide();
				}
				bool instaMineEnabled = OctoMain.InstaMineEnabled;
				if (instaMineEnabled)
				{
					this.label9.Show();
				}
				else
				{
					this.label9.Hide();
				}
				bool fullbrightEnabled = OctoMain.FullbrightEnabled;
				if (fullbrightEnabled)
				{
					this.label10.Show();
				}
				else
				{
					this.label10.Hide();
				}
				bool noWebEnabled = OctoMain.NoWebEnabled;
				if (noWebEnabled)
				{
					this.label11.Show();
				}
				else
				{
					this.label11.Hide();
				}
			}
		}

		// Token: 0x06000009 RID: 9 RVA: 0x00002327 File Offset: 0x00000527
		private void panel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x0600000A RID: 10 RVA: 0x0000232A File Offset: 0x0000052A
		private void label1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x04000001 RID: 1
		private OctoOverlay.RECT rect;

		// Token: 0x04000002 RID: 2
		public const string WINDOW_NAME = "Minecraft";

		// Token: 0x04000003 RID: 3
		private IntPtr handle = OctoOverlay.FindWindow(null, "Minecraft");

		// Token: 0x02000007 RID: 7
		public struct RECT
		{
			// Token: 0x04000054 RID: 84
			public int left;

			// Token: 0x04000055 RID: 85
			public int top;

			// Token: 0x04000056 RID: 86
			public int right;

			// Token: 0x04000057 RID: 87
			public int bottom;
		}
	}
}
