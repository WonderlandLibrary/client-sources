using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000010 RID: 16
	internal class FlatMini : Control
	{
		// Token: 0x06000103 RID: 259 RVA: 0x0000310D File Offset: 0x0000130D
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000104 RID: 260 RVA: 0x00003123 File Offset: 0x00001323
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000105 RID: 261 RVA: 0x00003139 File Offset: 0x00001339
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000106 RID: 262 RVA: 0x0000314F File Offset: 0x0000134F
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000107 RID: 263 RVA: 0x00003165 File Offset: 0x00001365
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.X;
			base.Invalidate();
		}

		// Token: 0x06000108 RID: 264 RVA: 0x00006124 File Offset: 0x00004324
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

		// Token: 0x1700006D RID: 109
		// (get) Token: 0x06000109 RID: 265 RVA: 0x00003180 File Offset: 0x00001380
		// (set) Token: 0x0600010A RID: 266 RVA: 0x00003188 File Offset: 0x00001388
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

		// Token: 0x1700006E RID: 110
		// (get) Token: 0x0600010B RID: 267 RVA: 0x00003191 File Offset: 0x00001391
		// (set) Token: 0x0600010C RID: 268 RVA: 0x00003199 File Offset: 0x00001399
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

		// Token: 0x0600010D RID: 269 RVA: 0x0000303E File Offset: 0x0000123E
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Size = new Size(18, 18);
		}

		// Token: 0x0600010E RID: 270 RVA: 0x00006168 File Offset: 0x00004368
		public FlatMini()
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

		// Token: 0x0600010F RID: 271 RVA: 0x000061F8 File Offset: 0x000043F8
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

		// Token: 0x04000032 RID: 50
		private MouseState State;

		// Token: 0x04000033 RID: 51
		private int x;

		// Token: 0x04000034 RID: 52
		private Color _BaseColor;

		// Token: 0x04000035 RID: 53
		private Color _TextColor;
	}
}
