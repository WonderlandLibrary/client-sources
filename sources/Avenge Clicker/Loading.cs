using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Bypass.My;
using Microsoft.VisualBasic.CompilerServices;

namespace Bypass
{
	// Token: 0x0200000C RID: 12
	[DesignerGenerated]
	public partial class Loading : Form
	{
		// Token: 0x06000094 RID: 148 RVA: 0x0000531C File Offset: 0x0000371C
		public Loading()
		{
			MessageBox.Show("shitty clicker src leaked by @EruditeSquad on Telegram, don't even use this shit.");
			this.seconde = 6;
			this.NewPoint = default(Point);
			this.InitializeComponent();
		}

		// Token: 0x1700002D RID: 45
		// (get) Token: 0x06000097 RID: 151 RVA: 0x0000578C File Offset: 0x00003B8C
		// (set) Token: 0x06000098 RID: 152 RVA: 0x000057A0 File Offset: 0x00003BA0
		internal virtual Panel Panel1
		{
			get
			{
				return this._Panel1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				MouseEventHandler value2 = new MouseEventHandler(this.Panel1_MouseDown);
				MouseEventHandler value3 = new MouseEventHandler(this.Panel1_MouseMove);
				if (this._Panel1 != null)
				{
					this._Panel1.MouseDown -= value2;
					this._Panel1.MouseMove -= value3;
				}
				this._Panel1 = value;
				if (this._Panel1 != null)
				{
					this._Panel1.MouseDown += value2;
					this._Panel1.MouseMove += value3;
				}
			}
		}

		// Token: 0x1700002E RID: 46
		// (get) Token: 0x06000099 RID: 153 RVA: 0x00005810 File Offset: 0x00003C10
		// (set) Token: 0x0600009A RID: 154 RVA: 0x00005824 File Offset: 0x00003C24
		internal virtual PictureBox PictureBox1
		{
			get
			{
				return this._PictureBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.PictureBox1_Click);
				if (this._PictureBox1 != null)
				{
					this._PictureBox1.Click -= value2;
				}
				this._PictureBox1 = value;
				if (this._PictureBox1 != null)
				{
					this._PictureBox1.Click += value2;
				}
			}
		}

		// Token: 0x1700002F RID: 47
		// (get) Token: 0x0600009B RID: 155 RVA: 0x00005870 File Offset: 0x00003C70
		// (set) Token: 0x0600009C RID: 156 RVA: 0x00005884 File Offset: 0x00003C84
		internal virtual Timer Timer1
		{
			get
			{
				return this._Timer1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer1_Tick);
				if (this._Timer1 != null)
				{
					this._Timer1.Tick -= value2;
				}
				this._Timer1 = value;
				if (this._Timer1 != null)
				{
					this._Timer1.Tick += value2;
				}
			}
		}

		// Token: 0x17000030 RID: 48
		// (get) Token: 0x0600009D RID: 157 RVA: 0x000058D0 File Offset: 0x00003CD0
		// (set) Token: 0x0600009E RID: 158 RVA: 0x000058E4 File Offset: 0x00003CE4
		internal virtual Label Label2
		{
			get
			{
				return this._Label2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Label2_Click);
				if (this._Label2 != null)
				{
					this._Label2.Click -= value2;
				}
				this._Label2 = value;
				if (this._Label2 != null)
				{
					this._Label2.Click += value2;
				}
			}
		}

		// Token: 0x17000031 RID: 49
		// (get) Token: 0x0600009F RID: 159 RVA: 0x00005930 File Offset: 0x00003D30
		// (set) Token: 0x060000A0 RID: 160 RVA: 0x00005944 File Offset: 0x00003D44
		internal virtual PictureBox PictureBox2
		{
			get
			{
				return this._PictureBox2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._PictureBox2 = value;
			}
		}

		// Token: 0x060000A1 RID: 161 RVA: 0x00005950 File Offset: 0x00003D50
		private void PictureBox1_Click(object sender, EventArgs e)
		{
			this.Close();
		}

		// Token: 0x060000A2 RID: 162 RVA: 0x00005958 File Offset: 0x00003D58
		private void Timer1_Tick(object sender, EventArgs e)
		{
			this.seconde = Operators.SubtractObject(this.seconde, 1);
			this.Label2.Text = Conversions.ToString(this.seconde);
			if (Operators.CompareString(this.Label2.Text, "0", false) == 0)
			{
				MyProject.Forms.Form2.Show();
				this.Close();
			}
		}

		// Token: 0x060000A3 RID: 163 RVA: 0x000059C0 File Offset: 0x00003DC0
		private void Panel1_MouseDown(object sender, MouseEventArgs e)
		{
			checked
			{
				this.X = Control.MousePosition.X - this.Location.X;
				this.Y = Control.MousePosition.Y - this.Location.Y;
			}
		}

		// Token: 0x060000A4 RID: 164 RVA: 0x00005A14 File Offset: 0x00003E14
		private void Panel1_MouseMove(object sender, MouseEventArgs e)
		{
			checked
			{
				if (e.Button == MouseButtons.Left)
				{
					this.NewPoint = Control.MousePosition;
					this.NewPoint.Y = this.NewPoint.Y - this.Y;
					this.NewPoint.X = this.NewPoint.X - this.X;
					this.Location = this.NewPoint;
				}
			}
		}

		// Token: 0x060000A5 RID: 165 RVA: 0x00005A80 File Offset: 0x00003E80
		private void Label1_MouseDown(object sender, MouseEventArgs e)
		{
			checked
			{
				this.X = Control.MousePosition.X - this.Location.X;
				this.Y = Control.MousePosition.Y - this.Location.Y;
			}
		}

		// Token: 0x060000A6 RID: 166 RVA: 0x00005AD4 File Offset: 0x00003ED4
		private void Label1_MouseMove(object sender, MouseEventArgs e)
		{
			checked
			{
				if (e.Button == MouseButtons.Left)
				{
					this.NewPoint = Control.MousePosition;
					this.NewPoint.Y = this.NewPoint.Y - this.Y;
					this.NewPoint.X = this.NewPoint.X - this.X;
					this.Location = this.NewPoint;
				}
			}
		}

		// Token: 0x060000A7 RID: 167 RVA: 0x00005B40 File Offset: 0x00003F40
		private void Label2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x04000044 RID: 68
		[AccessedThroughProperty("Panel1")]
		private Panel _Panel1;

		// Token: 0x04000045 RID: 69
		[AccessedThroughProperty("PictureBox1")]
		private PictureBox _PictureBox1;

		// Token: 0x04000046 RID: 70
		[AccessedThroughProperty("Timer1")]
		private Timer _Timer1;

		// Token: 0x04000047 RID: 71
		[AccessedThroughProperty("Label2")]
		private Label _Label2;

		// Token: 0x04000048 RID: 72
		[AccessedThroughProperty("PictureBox2")]
		private PictureBox _PictureBox2;

		// Token: 0x04000049 RID: 73
		private object seconde;

		// Token: 0x0400004A RID: 74
		private Point NewPoint;

		// Token: 0x0400004B RID: 75
		private int X;

		// Token: 0x0400004C RID: 76
		private int Y;
	}
}
