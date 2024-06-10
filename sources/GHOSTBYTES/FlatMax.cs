using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x0200000F RID: 15
	internal class FlatMax : Control
	{
		// Token: 0x060000F6 RID: 246 RVA: 0x00003078 File Offset: 0x00001278
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060000F7 RID: 247 RVA: 0x0000308E File Offset: 0x0000128E
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060000F8 RID: 248 RVA: 0x000030A4 File Offset: 0x000012A4
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060000F9 RID: 249 RVA: 0x000030BA File Offset: 0x000012BA
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060000FA RID: 250 RVA: 0x000030D0 File Offset: 0x000012D0
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.X;
			base.Invalidate();
		}

		// Token: 0x060000FB RID: 251 RVA: 0x00005EE0 File Offset: 0x000040E0
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

		// Token: 0x1700006B RID: 107
		// (get) Token: 0x060000FC RID: 252 RVA: 0x000030EB File Offset: 0x000012EB
		// (set) Token: 0x060000FD RID: 253 RVA: 0x000030F3 File Offset: 0x000012F3
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

		// Token: 0x1700006C RID: 108
		// (get) Token: 0x060000FE RID: 254 RVA: 0x000030FC File Offset: 0x000012FC
		// (set) Token: 0x060000FF RID: 255 RVA: 0x00003104 File Offset: 0x00001304
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

		// Token: 0x06000100 RID: 256 RVA: 0x0000303E File Offset: 0x0000123E
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(18, 18);
		}

		// Token: 0x06000101 RID: 257 RVA: 0x00005F20 File Offset: 0x00004120
		public FlatMax()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(50, 50, 50);
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.White;
			base.Size = new Size(18, 18);
			this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
			this.Font = new Font("Marlett", 12f);
		}

		// Token: 0x06000102 RID: 258 RVA: 0x00005FB0 File Offset: 0x000041B0
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

		// Token: 0x0400002E RID: 46
		private MouseState State;

		// Token: 0x0400002F RID: 47
		private int x;

		// Token: 0x04000030 RID: 48
		private Color _BaseColor;

		// Token: 0x04000031 RID: 49
		private Color _TextColor;
	}
}
