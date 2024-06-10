using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x02000017 RID: 23
	internal class FormSkin : ContainerControl
	{
		// Token: 0x17000094 RID: 148
		// (get) Token: 0x060001D9 RID: 473 RVA: 0x0000F60A File Offset: 0x0000D80A
		// (set) Token: 0x060001DA RID: 474 RVA: 0x0000F612 File Offset: 0x0000D812
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

		// Token: 0x17000095 RID: 149
		// (get) Token: 0x060001DB RID: 475 RVA: 0x0000F61B File Offset: 0x0000D81B
		// (set) Token: 0x060001DC RID: 476 RVA: 0x0000F623 File Offset: 0x0000D823
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

		// Token: 0x17000096 RID: 150
		// (get) Token: 0x060001DD RID: 477 RVA: 0x0000F62C File Offset: 0x0000D82C
		// (set) Token: 0x060001DE RID: 478 RVA: 0x0000F634 File Offset: 0x0000D834
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

		// Token: 0x17000097 RID: 151
		// (get) Token: 0x060001DF RID: 479 RVA: 0x0000F63D File Offset: 0x0000D83D
		// (set) Token: 0x060001E0 RID: 480 RVA: 0x0000F644 File Offset: 0x0000D844
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

		// Token: 0x17000098 RID: 152
		// (get) Token: 0x060001E1 RID: 481 RVA: 0x0000F64C File Offset: 0x0000D84C
		// (set) Token: 0x060001E2 RID: 482 RVA: 0x0000F654 File Offset: 0x0000D854
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

		// Token: 0x060001E3 RID: 483 RVA: 0x0000F660 File Offset: 0x0000D860
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Button == MouseButtons.Left & new Rectangle(0, 0, base.Width, Conversions.ToInteger(this.MoveHeight)).Contains(e.Location))
			{
				this.Cap = true;
				this.MousePoint = e.Location;
			}
		}

		// Token: 0x060001E4 RID: 484 RVA: 0x0000F6C0 File Offset: 0x0000D8C0
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

		// Token: 0x060001E5 RID: 485 RVA: 0x0000F758 File Offset: 0x0000D958
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.Cap = false;
		}

		// Token: 0x060001E6 RID: 486 RVA: 0x0000F768 File Offset: 0x0000D968
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			if (this.Cap)
			{
				base.Parent.Location = Control.MousePosition - (Size)this.MousePoint;
			}
		}

		// Token: 0x060001E7 RID: 487 RVA: 0x0000F79C File Offset: 0x0000D99C
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

		// Token: 0x060001E8 RID: 488 RVA: 0x0000F7F8 File Offset: 0x0000D9F8
		public FormSkin()
		{
			base.MouseDoubleClick += this.FormSkin_MouseDoubleClick;
			this.Cap = false;
			this._HeaderMaximize = false;
			this.MousePoint = new Point(0, 0);
			this.MoveHeight = 50;
			this._HeaderColor = Color.FromArgb(45, 47, 49);
			this._BaseColor = Color.FromArgb(60, 70, 73);
			this._BorderColor = Color.FromArgb(53, 58, 60);
			this.TextColor = Color.FromArgb(234, 234, 234);
			this._HeaderLight = Color.FromArgb(171, 171, 172);
			this._BaseLight = Color.FromArgb(196, 199, 200);
			this.TextLight = Color.FromArgb(45, 47, 49);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.White;
			this.Font = new Font("Segoe UI", 12f);
		}

		// Token: 0x060001E9 RID: 489 RVA: 0x0000F90C File Offset: 0x0000DB0C
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
			g.FillRectangle(new SolidBrush(Helpers._FlatColor), 16, 16, 4, 18);
			g.DrawString(this.Text, this.Font, new SolidBrush(this.TextColor), new Rectangle(26, 15, this.W, this.H), Helpers.NearSF);
			g.DrawRectangle(new Pen(this._BorderColor), rect);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x040000F4 RID: 244
		private int W;

		// Token: 0x040000F5 RID: 245
		private int H;

		// Token: 0x040000F6 RID: 246
		private bool Cap;

		// Token: 0x040000F7 RID: 247
		private bool _HeaderMaximize;

		// Token: 0x040000F8 RID: 248
		private Point MousePoint;

		// Token: 0x040000F9 RID: 249
		private object MoveHeight;

		// Token: 0x040000FA RID: 250
		private Color _HeaderColor;

		// Token: 0x040000FB RID: 251
		private Color _BaseColor;

		// Token: 0x040000FC RID: 252
		private Color _BorderColor;

		// Token: 0x040000FD RID: 253
		private Color TextColor;

		// Token: 0x040000FE RID: 254
		private Color _HeaderLight;

		// Token: 0x040000FF RID: 255
		private Color _BaseLight;

		// Token: 0x04000100 RID: 256
		public Color TextLight;
	}
}
