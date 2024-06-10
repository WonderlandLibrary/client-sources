using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200001A RID: 26
	internal class FlatMini : Control
	{
		// Token: 0x06000204 RID: 516 RVA: 0x0000FFDC File Offset: 0x0000E1DC
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000205 RID: 517 RVA: 0x0000FFF2 File Offset: 0x0000E1F2
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000206 RID: 518 RVA: 0x00010008 File Offset: 0x0000E208
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000207 RID: 519 RVA: 0x0001001E File Offset: 0x0000E21E
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000208 RID: 520 RVA: 0x00010034 File Offset: 0x0000E234
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.X;
			base.Invalidate();
		}

		// Token: 0x06000209 RID: 521 RVA: 0x00010050 File Offset: 0x0000E250
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			FormWindowState windowState = base.FindForm().WindowState;
			if (windowState == FormWindowState.Normal)
			{
				base.FindForm().WindowState = FormWindowState.Minimized;
				return;
			}
			if (windowState != FormWindowState.Maximized)
			{
				return;
			}
			base.FindForm().WindowState = FormWindowState.Minimized;
		}

		// Token: 0x1700009D RID: 157
		// (get) Token: 0x0600020A RID: 522 RVA: 0x00010091 File Offset: 0x0000E291
		// (set) Token: 0x0600020B RID: 523 RVA: 0x00010099 File Offset: 0x0000E299
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

		// Token: 0x1700009E RID: 158
		// (get) Token: 0x0600020C RID: 524 RVA: 0x000100A2 File Offset: 0x0000E2A2
		// (set) Token: 0x0600020D RID: 525 RVA: 0x000100AA File Offset: 0x0000E2AA
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

		// Token: 0x0600020E RID: 526 RVA: 0x0000FB11 File Offset: 0x0000DD11
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(18, 18);
		}

		// Token: 0x0600020F RID: 527 RVA: 0x000100B4 File Offset: 0x0000E2B4
		public FlatMini()
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

		// Token: 0x06000210 RID: 528 RVA: 0x00010144 File Offset: 0x0000E344
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
			graphics2.DrawString("0", this.Font, new SolidBrush(this.TextColor), new Rectangle(2, 1, base.Width, base.Height), Helpers.CenterSF);
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

		// Token: 0x04000109 RID: 265
		private MouseState State;

		// Token: 0x0400010A RID: 266
		private int x;

		// Token: 0x0400010B RID: 267
		private Color _BaseColor;

		// Token: 0x0400010C RID: 268
		private Color _TextColor;
	}
}
