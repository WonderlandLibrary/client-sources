using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x0200000D RID: 13
	internal class FormSkin : ContainerControl
	{
		// Token: 0x17000064 RID: 100
		// (get) Token: 0x060000D8 RID: 216 RVA: 0x00002F28 File Offset: 0x00001128
		// (set) Token: 0x060000D9 RID: 217 RVA: 0x00002F30 File Offset: 0x00001130
		[Category("Colors")]
		public Color HeaderColor
		{
			get
			{
				return this._HeaderColor;
			}
			set
			{
				this._HeaderColor = value;
			}
		}

		// Token: 0x17000065 RID: 101
		// (get) Token: 0x060000DA RID: 218 RVA: 0x00002F39 File Offset: 0x00001139
		// (set) Token: 0x060000DB RID: 219 RVA: 0x00002F41 File Offset: 0x00001141
		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		// Token: 0x17000066 RID: 102
		// (get) Token: 0x060000DC RID: 220 RVA: 0x00002F4A File Offset: 0x0000114A
		// (set) Token: 0x060000DD RID: 221 RVA: 0x00002F52 File Offset: 0x00001152
		[Category("Colors")]
		public Color BorderColor
		{
			get
			{
				return this._BorderColor;
			}
			set
			{
				this._BorderColor = value;
			}
		}

		// Token: 0x17000067 RID: 103
		// (get) Token: 0x060000DE RID: 222 RVA: 0x00002F5B File Offset: 0x0000115B
		// (set) Token: 0x060000DF RID: 223 RVA: 0x00002F62 File Offset: 0x00001162
		[Category("Colors")]
		public Color FlatColor
		{
			get
			{
				return Helpers._FlatColor;
			}
			set
			{
				Helpers._FlatColor = value;
			}
		}

		// Token: 0x17000068 RID: 104
		// (get) Token: 0x060000E0 RID: 224 RVA: 0x00002F6A File Offset: 0x0000116A
		// (set) Token: 0x060000E1 RID: 225 RVA: 0x00002F72 File Offset: 0x00001172
		[Category("Options")]
		public bool HeaderMaximize
		{
			get
			{
				return this._HeaderMaximize;
			}
			set
			{
				this._HeaderMaximize = value;
			}
		}

		// Token: 0x060000E2 RID: 226 RVA: 0x00005934 File Offset: 0x00003B34
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Button == MouseButtons.Left & new Rectangle(0, 0, base.Width, Conversions.ToInteger(this.MoveHeight)).Contains(e.Location))
			{
				this.Cap = true;
				this.MousePoint = e.Location;
			}
		}

		// Token: 0x060000E3 RID: 227 RVA: 0x00005994 File Offset: 0x00003B94
		private void FormSkin_MouseDoubleClick(object sender, MouseEventArgs e)
		{
			if (this.HeaderMaximize && (e.Button == MouseButtons.Left & new Rectangle(0, 0, base.Width, Conversions.ToInteger(this.MoveHeight)).Contains(e.Location)))
			{
				if (base.FindForm().WindowState == FormWindowState.Normal)
				{
					base.FindForm().WindowState = FormWindowState.Maximized;
					base.FindForm().Refresh();
					return;
				}
				if (base.FindForm().WindowState == FormWindowState.Maximized)
				{
					base.FindForm().WindowState = FormWindowState.Normal;
					base.FindForm().Refresh();
				}
			}
		}

		// Token: 0x060000E4 RID: 228 RVA: 0x00002F7B File Offset: 0x0000117B
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.Cap = false;
		}

		// Token: 0x060000E5 RID: 229 RVA: 0x00002F8B File Offset: 0x0000118B
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			if (this.Cap)
			{
				base.Parent.Location = Control.MousePosition - (Size)this.MousePoint;
			}
		}

		// Token: 0x060000E6 RID: 230 RVA: 0x00005A2C File Offset: 0x00003C2C
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			base.ParentForm.FormBorderStyle = FormBorderStyle.None;
			base.ParentForm.AllowTransparency = false;
			base.ParentForm.TransparencyKey = Color.Fuchsia;
			base.ParentForm.FindForm().StartPosition = FormStartPosition.CenterScreen;
			this.Dock = DockStyle.Fill;
			base.Invalidate();
		}

		// Token: 0x060000E7 RID: 231 RVA: 0x00005A88 File Offset: 0x00003C88
		public FormSkin()
		{
			base.MouseDoubleClick += this.FormSkin_MouseDoubleClick;
			this.Cap = false;
			this._HeaderMaximize = false;
			this.MousePoint = new Point(0, 0);
			this.MoveHeight = 50;
			this._HeaderColor = Color.FromArgb(50, 50, 50);
			this._BaseColor = Color.FromArgb(50, 50, 50);
			this._BorderColor = Color.FromArgb(0, 170, 220);
			this.TextColor = Color.FromArgb(212, 198, 209);
			this._HeaderLight = Color.FromArgb(171, 171, 172);
			this._BaseLight = Color.FromArgb(196, 199, 200);
			this.TextLight = Color.FromArgb(45, 47, 49);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.White;
			this.Font = new Font("Segoe UI", 12f);
		}

		// Token: 0x060000E8 RID: 232 RVA: 0x00005BA0 File Offset: 0x00003DA0
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			Rectangle rect = new Rectangle(0, 0, this.W, this.H);
			Rectangle rect2 = new Rectangle(0, 0, this.W, 50);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			g.FillRectangle(new SolidBrush(this._BaseColor), rect);
			g.FillRectangle(new SolidBrush(this._HeaderColor), rect2);
			g.FillRectangle(new SolidBrush(Color.FromArgb(243, 243, 243)), new Rectangle(8, 16, 4, 18));
			g.FillRectangle(new SolidBrush(Color.FromArgb(0, 170, 220)), 16, 16, 4, 18);
			g.DrawString(this.Text, this.Font, new SolidBrush(this.TextColor), new Rectangle(26, 15, this.W, this.H), Helpers.NearSF);
			g.DrawRectangle(new Pen(this._BorderColor), rect);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x0400001D RID: 29
		private int W;

		// Token: 0x0400001E RID: 30
		private int H;

		// Token: 0x0400001F RID: 31
		private bool Cap;

		// Token: 0x04000020 RID: 32
		private bool _HeaderMaximize;

		// Token: 0x04000021 RID: 33
		private Point MousePoint;

		// Token: 0x04000022 RID: 34
		private object MoveHeight;

		// Token: 0x04000023 RID: 35
		private Color _HeaderColor;

		// Token: 0x04000024 RID: 36
		private Color _BaseColor;

		// Token: 0x04000025 RID: 37
		private Color _BorderColor;

		// Token: 0x04000026 RID: 38
		private Color TextColor;

		// Token: 0x04000027 RID: 39
		private Color _HeaderLight;

		// Token: 0x04000028 RID: 40
		private Color _BaseLight;

		// Token: 0x04000029 RID: 41
		public Color TextLight;
	}
}
