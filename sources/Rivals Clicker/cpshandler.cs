using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;
using svchost.My;

namespace svchost
{
	// Token: 0x0200000B RID: 11
	[DesignerGenerated]
	public partial class cpshandler : Form
	{
		// Token: 0x06000046 RID: 70 RVA: 0x000022E8 File Offset: 0x000004E8
		public cpshandler()
		{
			base.Closing += this.cpshandler_Closing;
			this.InitializeComponent();
		}

		// Token: 0x1700001D RID: 29
		// (get) Token: 0x06000049 RID: 73 RVA: 0x00002308 File Offset: 0x00000508
		// (set) Token: 0x0600004A RID: 74 RVA: 0x0000410C File Offset: 0x0000230C
		internal virtual Button Button1
		{
			[CompilerGenerated]
			get
			{
				return this._Button1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button1_Click);
				Button button = this._Button1;
				if (button != null)
				{
					button.Click -= value2;
				}
				this._Button1 = value;
				button = this._Button1;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x1700001E RID: 30
		// (get) Token: 0x0600004B RID: 75 RVA: 0x00002312 File Offset: 0x00000512
		// (set) Token: 0x0600004C RID: 76 RVA: 0x00004150 File Offset: 0x00002350
		internal virtual Timer clickingspeed
		{
			[CompilerGenerated]
			get
			{
				return this._clickingspeed;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Clickingspeed_Tick);
				Timer clickingspeed = this._clickingspeed;
				if (clickingspeed != null)
				{
					clickingspeed.Tick -= value2;
				}
				this._clickingspeed = value;
				clickingspeed = this._clickingspeed;
				if (clickingspeed != null)
				{
					clickingspeed.Tick += value2;
				}
			}
		}

		// Token: 0x1700001F RID: 31
		// (get) Token: 0x0600004D RID: 77 RVA: 0x0000231C File Offset: 0x0000051C
		// (set) Token: 0x0600004E RID: 78 RVA: 0x00004194 File Offset: 0x00002394
		internal virtual Timer reset
		{
			[CompilerGenerated]
			get
			{
				return this._reset;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer2_Tick);
				Timer reset = this._reset;
				if (reset != null)
				{
					reset.Tick -= value2;
				}
				this._reset = value;
				reset = this._reset;
				if (reset != null)
				{
					reset.Tick += value2;
				}
			}
		}

		// Token: 0x0600004F RID: 79 RVA: 0x00002326 File Offset: 0x00000526
		private void Button1_Click(object sender, EventArgs e)
		{
			checked
			{
				this.Clicks++;
			}
		}

		// Token: 0x06000050 RID: 80 RVA: 0x00002337 File Offset: 0x00000537
		private void Timer2_Tick(object sender, EventArgs e)
		{
			MyProject.Forms.Form1.cpsAverage.Text = Conversions.ToString((double)this.Clicks / 2.0);
			this.Clicks = 0;
		}

		// Token: 0x06000051 RID: 81 RVA: 0x000041D8 File Offset: 0x000023D8
		private void Clickingspeed_Tick(object sender, EventArgs e)
		{
			Random Rnd = new Random();
			this.clickingspeed.Interval = MyProject.Forms.Form1.autoclicker.Interval;
			this.Button1.PerformClick();
			checked
			{
				int num = (int)Math.Round(unchecked(MySettingsProperty.Settings.Overclock + 1.0));
				for (int index = 1; index <= num; index++)
				{
					this.Button1.PerformClick();
				}
			}
		}

		// Token: 0x06000052 RID: 82 RVA: 0x0000236C File Offset: 0x0000056C
		private void cpshandler_Closing(object sender, CancelEventArgs e)
		{
		}

		// Token: 0x04000013 RID: 19
		private int Clicks;
	}
}
