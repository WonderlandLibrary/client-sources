using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x0200000E RID: 14
	internal class FlatClose : Control
	{
		// Token: 0x060000E9 RID: 233 RVA: 0x00002FBC File Offset: 0x000011BC
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060000EA RID: 234 RVA: 0x00002FD2 File Offset: 0x000011D2
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x060000EB RID: 235 RVA: 0x00002FE8 File Offset: 0x000011E8
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x060000EC RID: 236 RVA: 0x00002FFE File Offset: 0x000011FE
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x060000ED RID: 237 RVA: 0x00003014 File Offset: 0x00001214
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.X;
			base.Invalidate();
		}

		// Token: 0x060000EE RID: 238 RVA: 0x0000302F File Offset: 0x0000122F
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			Environment.Exit(0);
		}

		// Token: 0x060000EF RID: 239 RVA: 0x0000303E File Offset: 0x0000123E
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(18, 18);
		}

		// Token: 0x17000069 RID: 105
		// (get) Token: 0x060000F0 RID: 240 RVA: 0x00003056 File Offset: 0x00001256
		// (set) Token: 0x060000F1 RID: 241 RVA: 0x0000305E File Offset: 0x0000125E
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

		// Token: 0x1700006A RID: 106
		// (get) Token: 0x060000F2 RID: 242 RVA: 0x00003067 File Offset: 0x00001267
		// (set) Token: 0x060000F3 RID: 243 RVA: 0x0000306F File Offset: 0x0000126F
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

		// Token: 0x060000F4 RID: 244 RVA: 0x00005D30 File Offset: 0x00003F30
		public FlatClose()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(50, 50, 50);
			this._TextColor = Color.FromArgb(220, 220, 220);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.White;
			base.Size = new Size(18, 18);
			this.Anchor = (AnchorStyles.Top | AnchorStyles.Right);
			this.Font = new Font("Marlett", 12f);
		}

		// Token: 0x060000F5 RID: 245 RVA: 0x00005DC0 File Offset: 0x00003FC0
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

		// Token: 0x0400002A RID: 42
		private MouseState State;

		// Token: 0x0400002B RID: 43
		private int x;

		// Token: 0x0400002C RID: 44
		private Color _BaseColor;

		// Token: 0x0400002D RID: 45
		private Color _TextColor;
	}
}
