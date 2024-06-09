using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatGroupBox : ContainerControl
	{
		private int W;

		private int H;

		private bool _ShowText;

		private Color _BaseColor;

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

		public FlatGroupBox()
		{
			this._ShowText = true;
			this._BaseColor = Color.FromArgb(60, 70, 73);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			base.Size = new System.Drawing.Size(240, 180);
			this.Font = new System.Drawing.Font("Segoe ui", 10f);
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = checked(base.Width - 1);
			this.H = checked(base.Height - 1);
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath graphicsPath1 = new GraphicsPath();
			GraphicsPath graphicsPath2 = new GraphicsPath();
			Rectangle rectangle = new Rectangle(8, 8, checked(this.W - 16), checked(this.H - 16));
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			graphicsPath = Helpers.RoundRec(rectangle, 8);
			g.FillPath(new SolidBrush(this._BaseColor), graphicsPath);
			graphicsPath1 = Helpers.DrawArrow(28, 2, false);
			g.FillPath(new SolidBrush(this._BaseColor), graphicsPath1);
			graphicsPath2 = Helpers.DrawArrow(28, 8, true);
			g.FillPath(new SolidBrush(Color.FromArgb(60, 70, 73)), graphicsPath2);
			if (this.ShowText)
			{
				g.DrawString(this.Text, this.Font, new SolidBrush(Helpers._FlatColor), new Rectangle(16, 16, this.W, this.H), Helpers.NearSF);
			}
			g = null;
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}
	}
}