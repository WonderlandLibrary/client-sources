using System;
using System.ComponentModel;
using System.Drawing;
using System.Net;
using System.Runtime.InteropServices;
using System.Windows.Forms;

namespace dgbfdfuib
{
	// Token: 0x02000002 RID: 2
	public partial class Form1 : Form
	{
		// Token: 0x06000001 RID: 1 RVA: 0x00002050 File Offset: 0x00000250
		public Form1()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000002 RID: 2
		[DllImport("user32.dll")]
		public static extern bool mouse_event(int asd, int dsa, int gsd, int he, int agfh);

		// Token: 0x06000003 RID: 3 RVA: 0x00002060 File Offset: 0x00000260
		private void TrackBar1_Scroll(object sender, EventArgs e)
		{
			this.Label2.Text = this.TrackBar1.Value.ToString();
			if (this.TrackBar1.Value > this.TrackBar2.Value)
			{
				this.TrackBar2.Value = this.TrackBar1.Value;
				this.Label3.Text = this.TrackBar2.Value.ToString();
			}
		}

		// Token: 0x06000004 RID: 4 RVA: 0x000020D8 File Offset: 0x000002D8
		private void TrackBar2_Scroll(object sender, EventArgs e)
		{
			this.Label3.Text = this.TrackBar2.Value.ToString();
			if (this.TrackBar2.Value < this.TrackBar1.Value)
			{
				this.TrackBar1.Value = this.TrackBar2.Value;
				this.Label2.Text = this.TrackBar1.Value.ToString();
			}
		}

		// Token: 0x06000005 RID: 5 RVA: 0x00002150 File Offset: 0x00000350
		private void Timer1_Tick(object sender, EventArgs e)
		{
			int num = (int)(1000.0 / (double)this.TrackBar1.Value);
			int num2 = (int)(1000.0 / (double)this.TrackBar2.Value);
			this.Timer1.Interval = (num + num2) / 2;
			if (Control.MouseButtons == MouseButtons.Left)
			{
				Form1.mouse_event(4, 0, 0, 0, 0);
				Form1.mouse_event(2, 0, 0, 0, 0);
			}
		}

		// Token: 0x06000006 RID: 6 RVA: 0x000021C0 File Offset: 0x000003C0
		private void Button1_Click(object sender, EventArgs e)
		{
			this.HhHh++;
			if (this.HhHh == 1)
			{
				this.Button1.Text = "Toggle Off";
				return;
			}
			this.HhHh = 0;
			this.Button1.Text = "Toggle On";
		}

		// Token: 0x06000007 RID: 7 RVA: 0x0000220C File Offset: 0x0000040C
		private void Button1_MouseHover(object sender, EventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Stop();
			}
		}

		// Token: 0x06000008 RID: 8 RVA: 0x00002230 File Offset: 0x00000430
		private void Button1_MouseLeave(object sender, EventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Start();
			}
		}

		// Token: 0x06000009 RID: 9 RVA: 0x00002254 File Offset: 0x00000454
		private void TrackBar1_MouseDown(object sender, MouseEventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Stop();
			}
		}

		// Token: 0x0600000A RID: 10 RVA: 0x00002278 File Offset: 0x00000478
		private void TrackBar1_MouseUp(object sender, MouseEventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Start();
			}
		}

		// Token: 0x0600000B RID: 11 RVA: 0x0000229C File Offset: 0x0000049C
		private void Form1_KeyDown(object sender, KeyEventArgs e)
		{
			if (e.KeyCode == Keys.F)
			{
				this.HhHh++;
				if (this.HhHh == 1)
				{
					this.Button1.Text = "Toggle Off";
					return;
				}
				this.HhHh = 0;
				this.Button1.Text = "Toggle On";
			}
		}

		// Token: 0x0600000C RID: 12 RVA: 0x000022F2 File Offset: 0x000004F2
		private void Form1_Load(object sender, EventArgs e)
		{
			if (bool.Parse(new WebClient().DownloadString("http://www.geusa.it/LEO/aaa.txt")))
			{
				MessageBox.Show("S.M.T. is not available");
				base.Close();
			}
		}

		// Token: 0x0600000D RID: 13 RVA: 0x0000231B File Offset: 0x0000051B
		private void Form1_FormClosed(object sender, FormClosedEventArgs e)
		{
		}

		// Token: 0x0600000E RID: 14 RVA: 0x0000231D File Offset: 0x0000051D
		private void Button1_TextChanged(object sender, EventArgs e)
		{
			if (this.Button1.Text == "Toggle Off")
			{
				this.Timer1.Start();
				return;
			}
			this.Timer1.Stop();
		}

		// Token: 0x04000001 RID: 1
		public const int AaAa = 2;

		// Token: 0x04000002 RID: 2
		public const int BbBb = 4;

		// Token: 0x04000003 RID: 3
		public const int CcCc = 32;

		// Token: 0x04000004 RID: 4
		public const int DdDd = 64;

		// Token: 0x04000005 RID: 5
		public const int EeEe = 8;

		// Token: 0x04000006 RID: 6
		public const int FfFf = 16;

		// Token: 0x04000007 RID: 7
		public const int GgGg = 1;

		// Token: 0x04000008 RID: 8
		private int HhHh;
	}
}
