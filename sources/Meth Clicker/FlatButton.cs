using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200001D RID: 29
	internal class FlatButton : Control
	{
		// Token: 0x170000AA RID: 170
		// (get) Token: 0x0600022C RID: 556 RVA: 0x00010834 File Offset: 0x0000EA34
		// (set) Token: 0x0600022D RID: 557 RVA: 0x0001083C File Offset: 0x0000EA3C
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

		// Token: 0x170000AB RID: 171
		// (get) Token: 0x0600022E RID: 558 RVA: 0x00010845 File Offset: 0x0000EA45
		// (set) Token: 0x0600022F RID: 559 RVA: 0x0001084D File Offset: 0x0000EA4D
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

		// Token: 0x170000AC RID: 172
		// (get) Token: 0x06000230 RID: 560 RVA: 0x00010856 File Offset: 0x0000EA56
		// (set) Token: 0x06000231 RID: 561 RVA: 0x0001085E File Offset: 0x0000EA5E
		[Category("Options")]
		public bool Rounded
		{
			get
			{
				return this._Rounded;
			}
			set
			{
				this._Rounded = value;
			}
		}

		// Token: 0x06000232 RID: 562 RVA: 0x00010867 File Offset: 0x0000EA67
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000233 RID: 563 RVA: 0x0001087D File Offset: 0x0000EA7D
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000234 RID: 564 RVA: 0x00010893 File Offset: 0x0000EA93
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000235 RID: 565 RVA: 0x000108A9 File Offset: 0x0000EAA9
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000236 RID: 566 RVA: 0x000108C0 File Offset: 0x0000EAC0
		public FlatButton()
		{
			this._Rounded = false;
			this.State = MouseState.None;
			this._BaseColor = Helpers._FlatColor;
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(106, 32);
			this.BackColor = Color.Transparent;
			this.Font = new Font("Segoe UI", 12f);
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x06000237 RID: 567 RVA: 0x00010954 File Offset: 0x0000EB54
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			GraphicsPath path = new GraphicsPath();
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			switch (this.State)
			{
			case MouseState.None:
				if (this.Rounded)
				{
					path = Helpers.RoundRec(rectangle, 6);
					g.FillPath(new SolidBrush(this._BaseColor), path);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				break;
			case MouseState.Over:
				if (this.Rounded)
				{
					path = Helpers.RoundRec(rectangle, 6);
					g.FillPath(new SolidBrush(this._BaseColor), path);
					g.FillPath(new SolidBrush(Color.FromArgb(20, Color.White)), path);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
					g.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.White)), rectangle);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				break;
			case MouseState.Down:
				if (this.Rounded)
				{
					path = Helpers.RoundRec(rectangle, 6);
					g.FillPath(new SolidBrush(this._BaseColor), path);
					g.FillPath(new SolidBrush(Color.FromArgb(20, Color.Black)), path);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
					g.FillRectangle(new SolidBrush(Color.FromArgb(20, Color.Black)), rectangle);
					g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), rectangle, Helpers.CenterSF);
				}
				break;
			}
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x0400011C RID: 284
		private int W;

		// Token: 0x0400011D RID: 285
		private int H;

		// Token: 0x0400011E RID: 286
		private bool _Rounded;

		// Token: 0x0400011F RID: 287
		private MouseState State;

		// Token: 0x04000120 RID: 288
		private Color _BaseColor;

		// Token: 0x04000121 RID: 289
		private Color _TextColor;
	}
}
