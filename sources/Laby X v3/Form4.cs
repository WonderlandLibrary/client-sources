using System;
using System.ComponentModel;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using MonoFlat;

namespace LabyxV3
{
	// Token: 0x02000008 RID: 8
	public partial class Form4 : Form
	{
		// Token: 0x0600004B RID: 75
		[DllImport("user32", CharSet = CharSet.Ansi, EntryPoint = "mouse_event", ExactSpelling = true, SetLastError = true)]
		private static extern bool apimouse_event(int dwFlags, int dX, int dY, int cButtons, int dwExtraInfo);

		// Token: 0x0600004C RID: 76
		[DllImport("user32", CharSet = CharSet.Ansi, ExactSpelling = true, SetLastError = true)]
		private static extern int GetAsyncKeyState(long vkey);

		// Token: 0x17000007 RID: 7
		// (get) Token: 0x0600004D RID: 77 RVA: 0x0000551E File Offset: 0x0000371E
		// (set) Token: 0x0600004E RID: 78 RVA: 0x00005526 File Offset: 0x00003726
		public object Timer1 { get; private set; }

		// Token: 0x17000008 RID: 8
		// (get) Token: 0x0600004F RID: 79 RVA: 0x0000552F File Offset: 0x0000372F
		// (set) Token: 0x06000050 RID: 80 RVA: 0x00005537 File Offset: 0x00003737
		public bool ISENABLED { get; private set; }

		// Token: 0x17000009 RID: 9
		// (get) Token: 0x06000051 RID: 81 RVA: 0x00005540 File Offset: 0x00003740
		// (set) Token: 0x06000052 RID: 82 RVA: 0x00005548 File Offset: 0x00003748
		public object Keybind { get; private set; }

		// Token: 0x06000053 RID: 83 RVA: 0x00005551 File Offset: 0x00003751
		public Form4()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000054 RID: 84 RVA: 0x00005569 File Offset: 0x00003769
		private void Form4_Load(object sender, EventArgs e)
		{
			base.KeyPreview = true;
		}

		// Token: 0x06000055 RID: 85 RVA: 0x00005574 File Offset: 0x00003774
		private void Button2_Click(object sender, EventArgs e)
		{
			this.timer1.Stop();
			this.Button1.Text = "Disattivo";
			base.Hide();
		}

		// Token: 0x06000056 RID: 86 RVA: 0x0000559C File Offset: 0x0000379C
		private void monoFlat_TrackBar3_ValueChanged()
		{
			this.Label3.Text = this.monoFlat_TrackBar3.Value.ToString();
			bool flag = this.monoFlat_TrackBar3.Value > this.monoFlat_TrackBar4.Value;
			if (flag)
			{
				this.monoFlat_TrackBar3.Value = checked(this.monoFlat_TrackBar4.Value - 1);
			}
		}

		// Token: 0x06000057 RID: 87 RVA: 0x00005600 File Offset: 0x00003800
		private void monoFlat_TrackBar4_ValueChanged()
		{
			this.label1.Text = this.monoFlat_TrackBar4.Value.ToString();
			bool flag = this.monoFlat_TrackBar3.Value > this.monoFlat_TrackBar4.Value;
			if (flag)
			{
				this.monoFlat_TrackBar3.Value = checked(this.monoFlat_TrackBar4.Value - 1);
			}
		}

		// Token: 0x06000058 RID: 88 RVA: 0x00005664 File Offset: 0x00003864
		private void Button1_Click(object sender, EventArgs e)
		{
			checked
			{
				this.toggle++;
				bool flag = this.toggle != 1;
				if (flag)
				{
					this.timer1.Stop();
					this.toggle = 0;
					this.Button1.Text = "Disattivo";
				}
				else
				{
					this.timer1.Start();
					this.Button1.Text = "Attivo";
				}
				this.toggle_flag_active_disable_ToggledChanged();
			}
		}

		// Token: 0x06000059 RID: 89 RVA: 0x000056E0 File Offset: 0x000038E0
		private void timer1_Tick(object sender, EventArgs e)
		{
			bool flag = this.status == 1;
			if (flag)
			{
				int interval = checked(new Random().Next((int)Math.Round(907.0 / (double)this.monoFlat_TrackBar4.Value), (int)Math.Round(907.0 / (double)this.monoFlat_TrackBar3.Value)));
				this.timer1.Interval = interval;
				bool flag2 = Control.MouseButtons == MouseButtons.Left;
				if (flag2)
				{
					Form4.apimouse_event(2, 0, 0, 0, 0);
				}
			}
		}

		// Token: 0x0600005A RID: 90 RVA: 0x0000576C File Offset: 0x0000396C
		private void toggle_flag_active_disable_ToggledChanged()
		{
			bool flag = this.status == 0;
			if (flag)
			{
				this.status = 1;
			}
			else
			{
				bool flag2 = this.status == 1;
				if (flag2)
				{
					this.status = 0;
				}
			}
		}

		// Token: 0x0600005B RID: 91 RVA: 0x000057A7 File Offset: 0x000039A7
		private void button3_Click(object sender, EventArgs e)
		{
			base.Hide();
		}

		// Token: 0x0400004E RID: 78
		private readonly object VBMath;

		// Token: 0x0400004F RID: 79
		private int toggle;

		// Token: 0x04000050 RID: 80
		private int status;

		// Token: 0x04000051 RID: 81
		private Keys keyData;

		// Token: 0x04000052 RID: 82
		private int XDDDDD;
	}
}
