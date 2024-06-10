using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000012 RID: 18
	internal class FlatButton : Control
	{
		// Token: 0x17000071 RID: 113
		// (get) Token: 0x06000116 RID: 278 RVA: 0x000031C4 File Offset: 0x000013C4
		// (set) Token: 0x06000117 RID: 279 RVA: 0x000031CC File Offset: 0x000013CC
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

		// Token: 0x17000072 RID: 114
		// (get) Token: 0x06000118 RID: 280 RVA: 0x000031D5 File Offset: 0x000013D5
		// (set) Token: 0x06000119 RID: 281 RVA: 0x000031DD File Offset: 0x000013DD
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

		// Token: 0x17000073 RID: 115
		// (get) Token: 0x0600011A RID: 282 RVA: 0x000031E6 File Offset: 0x000013E6
		// (set) Token: 0x0600011B RID: 283 RVA: 0x000031EE File Offset: 0x000013EE
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

		// Token: 0x0600011C RID: 284 RVA: 0x000031F7 File Offset: 0x000013F7
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x0600011D RID: 285 RVA: 0x0000320D File Offset: 0x0000140D
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600011E RID: 286 RVA: 0x00003223 File Offset: 0x00001423
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x0600011F RID: 287 RVA: 0x00003239 File Offset: 0x00001439
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000120 RID: 288 RVA: 0x0000650C File Offset: 0x0000470C
		public FlatButton()
		{
			this._Rounded = false;
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(0, 170, 220);
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(106, 32);
			this.BackColor = Color.Transparent;
			this.Font = new Font("Segoe UI", 10f);
			this.Cursor = Cursors.Hand;
		}

		// Token: 0x06000121 RID: 289 RVA: 0x000065AC File Offset: 0x000047AC
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
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
		}

		// Token: 0x0400003A RID: 58
		private int W;

		// Token: 0x0400003B RID: 59
		private int H;

		// Token: 0x0400003C RID: 60
		private bool _Rounded;

		// Token: 0x0400003D RID: 61
		private MouseState State;

		// Token: 0x0400003E RID: 62
		private Color _BaseColor;

		// Token: 0x0400003F RID: 63
		private Color _TextColor;
	}
}
