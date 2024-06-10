using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000019 RID: 25
	internal class FlatMax : Control
	{
		// Token: 0x060001F7 RID: 503 RVA: 0x0000FCFE File Offset: 0x0000DEFE
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001F8 RID: 504 RVA: 0x0000FD14 File Offset: 0x0000DF14
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001F9 RID: 505 RVA: 0x0000FD2A File Offset: 0x0000DF2A
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001FA RID: 506 RVA: 0x0000FD40 File Offset: 0x0000DF40
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001FB RID: 507 RVA: 0x0000FD56 File Offset: 0x0000DF56
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.X;
			base.Invalidate();
		}

		// Token: 0x060001FC RID: 508 RVA: 0x0000FD74 File Offset: 0x0000DF74
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			FormWindowState windowState = base.FindForm().WindowState;
			if (windowState != FormWindowState.Normal)
			{
				if (windowState == FormWindowState.Maximized)
				{
					base.FindForm().WindowState = FormWindowState.Normal;
					return;
				}
			}
			else
			{
				base.FindForm().WindowState = FormWindowState.Maximized;
			}
		}

		// Token: 0x1700009B RID: 155
		// (get) Token: 0x060001FD RID: 509 RVA: 0x0000FDB4 File Offset: 0x0000DFB4
		// (set) Token: 0x060001FE RID: 510 RVA: 0x0000FDBC File Offset: 0x0000DFBC
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

		// Token: 0x1700009C RID: 156
		// (get) Token: 0x060001FF RID: 511 RVA: 0x0000FDC5 File Offset: 0x0000DFC5
		// (set) Token: 0x06000200 RID: 512 RVA: 0x0000FDCD File Offset: 0x0000DFCD
		[Category("Colors")]
		public Color TextColor
		{
			get
			{
				return this._TextColor;
			}
			set
			{
				this._TextColor = value;
			}
		}

		// Token: 0x06000201 RID: 513 RVA: 0x0000FB11 File Offset: 0x0000DD11
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(18, 18);
		}

		// Token: 0x06000202 RID: 514 RVA: 0x0000FDD8 File Offset: 0x0000DFD8
		public FlatMax()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.White;
			base.Size = new Size(18, 18);
			this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
			this.Font = new Font("Marlett", 12f);
		}

		// Token: 0x06000203 RID: 515 RVA: 0x0000FE68 File Offset: 0x0000E068
		protected override void OnPaint(PaintEventArgs e)
		{
			Bitmap bitmap = new Bitmap(base.Width, base.Height);
			Graphics graphics = Graphics.FromImage(bitmap);
			Rectangle rect = new Rectangle(0, 0, base.Width, base.Height);
			Graphics graphics2 = graphics;
			graphics2.SmoothingMode = SmoothingMode.HighQuality;
			graphics2.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics2.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics2.Clear(this.BackColor);
			graphics2.FillRectangle(new SolidBrush(this._BaseColor), rect);
			if (base.FindForm().WindowState == FormWindowState.Maximized)
			{
				graphics2.DrawString("1", this.Font, new SolidBrush(this.TextColor), new Rectangle(1, 1, base.Width, base.Height), Helpers.CenterSF);
			}
			else if (base.FindForm().WindowState == FormWindowState.Normal)
			{
				graphics2.DrawString("2", this.Font, new SolidBrush(this.TextColor), new Rectangle(1, 1, base.Width, base.Height), Helpers.CenterSF);
			}
			MouseState state = this.State;
			if (state != MouseState.Over)
			{
				if (state == MouseState.Down)
				{
					graphics2.FillRectangle(new SolidBrush(Color.FromArgb(30, Color.Black)), rect);
				}
			}
			else
			{
				graphics2.FillRectangle(new SolidBrush(Color.FromArgb(30, Color.White)), rect);
			}
			base.OnPaint(e);
			graphics.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(bitmap, 0, 0);
			bitmap.Dispose();
		}

		// Token: 0x04000105 RID: 261
		private MouseState State;

		// Token: 0x04000106 RID: 262
		private int x;

		// Token: 0x04000107 RID: 263
		private Color _BaseColor;

		// Token: 0x04000108 RID: 264
		private Color _TextColor;
	}
}
