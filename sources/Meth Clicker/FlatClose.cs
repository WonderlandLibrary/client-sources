using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000018 RID: 24
	internal class FlatClose : Control
	{
		// Token: 0x060001EA RID: 490 RVA: 0x0000FA8F File Offset: 0x0000DC8F
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001EB RID: 491 RVA: 0x0000FAA5 File Offset: 0x0000DCA5
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060001EC RID: 492 RVA: 0x0000FABB File Offset: 0x0000DCBB
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060001ED RID: 493 RVA: 0x0000FAD1 File Offset: 0x0000DCD1
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060001EE RID: 494 RVA: 0x0000FAE7 File Offset: 0x0000DCE7
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.X;
			base.Invalidate();
		}

		// Token: 0x060001EF RID: 495 RVA: 0x0000FB02 File Offset: 0x0000DD02
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			Environment.Exit(0);
		}

		// Token: 0x060001F0 RID: 496 RVA: 0x0000FB11 File Offset: 0x0000DD11
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(18, 18);
		}

		// Token: 0x17000099 RID: 153
		// (get) Token: 0x060001F1 RID: 497 RVA: 0x0000FB29 File Offset: 0x0000DD29
		// (set) Token: 0x060001F2 RID: 498 RVA: 0x0000FB31 File Offset: 0x0000DD31
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

		// Token: 0x1700009A RID: 154
		// (get) Token: 0x060001F3 RID: 499 RVA: 0x0000FB3A File Offset: 0x0000DD3A
		// (set) Token: 0x060001F4 RID: 500 RVA: 0x0000FB42 File Offset: 0x0000DD42
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

		// Token: 0x060001F5 RID: 501 RVA: 0x0000FB4C File Offset: 0x0000DD4C
		public FlatClose()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(168, 35, 35);
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.White;
			base.Size = new Size(18, 18);
			this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
			this.Font = new Font("Marlett", 10f);
		}

		// Token: 0x060001F6 RID: 502 RVA: 0x0000FBE0 File Offset: 0x0000DDE0
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
			graphics2.DrawString("r", this.Font, new SolidBrush(this.TextColor), new Rectangle(0, 0, base.Width, base.Height), Helpers.CenterSF);
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

		// Token: 0x04000101 RID: 257
		private MouseState State;

		// Token: 0x04000102 RID: 258
		private int x;

		// Token: 0x04000103 RID: 259
		private Color _BaseColor;

		// Token: 0x04000104 RID: 260
		private Color _TextColor;
	}
}
