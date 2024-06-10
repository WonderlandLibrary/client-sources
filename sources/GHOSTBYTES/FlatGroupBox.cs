using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000011 RID: 17
	internal class FlatGroupBox : ContainerControl
	{
		// Token: 0x1700006F RID: 111
		// (get) Token: 0x06000110 RID: 272 RVA: 0x000031A2 File Offset: 0x000013A2
		// (set) Token: 0x06000111 RID: 273 RVA: 0x000031AA File Offset: 0x000013AA
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

		// Token: 0x17000070 RID: 112
		// (get) Token: 0x06000112 RID: 274 RVA: 0x000031B3 File Offset: 0x000013B3
		// (set) Token: 0x06000113 RID: 275 RVA: 0x000031BB File Offset: 0x000013BB
		public bool ShowText
		{
			get
			{
				return this._ShowText;
			}
			set
			{
				this._ShowText = value;
			}
		}

		// Token: 0x06000114 RID: 276 RVA: 0x00006318 File Offset: 0x00004518
		public FlatGroupBox()
		{
			this._ShowText = true;
			this._BaseColor = Color.FromArgb(60, 60, 60);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			base.Size = new Size(240, 180);
			this.Font = new Font("Segoe ui", 10f);
		}

		// Token: 0x06000115 RID: 277 RVA: 0x0000638C File Offset: 0x0000458C
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
				this.W = base.Width - 1;
				this.H = base.Height - 1;
				GraphicsPath path = new GraphicsPath();
				GraphicsPath path2 = new GraphicsPath();
				GraphicsPath path3 = new GraphicsPath();
				Rectangle rectangle = new Rectangle(8, 8, this.W - 16, this.H - 16);
				Graphics g = Helpers.G;
				g.SmoothingMode = SmoothingMode.HighQuality;
				g.PixelOffsetMode = PixelOffsetMode.HighQuality;
				g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				g.Clear(this.BackColor);
				path = Helpers.RoundRec(rectangle, 8);
				g.FillPath(new SolidBrush(this._BaseColor), path);
				path2 = Helpers.DrawArrow(28, 2, false);
				g.FillPath(new SolidBrush(this._BaseColor), path2);
				path3 = Helpers.DrawArrow(28, 8, true);
				g.FillPath(new SolidBrush(Color.FromArgb(60, 70, 73)), path3);
				if (this.ShowText)
				{
					g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(0, 170, 220)), new Rectangle(16, 16, this.W, this.H), Helpers.NearSF);
				}
				base.OnPaint(e);
				Helpers.G.Dispose();
				e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
				Helpers.B.Dispose();
			}
		}

		// Token: 0x04000036 RID: 54
		private int W;

		// Token: 0x04000037 RID: 55
		private int H;

		// Token: 0x04000038 RID: 56
		private bool _ShowText;

		// Token: 0x04000039 RID: 57
		private Color _BaseColor;
	}
}
