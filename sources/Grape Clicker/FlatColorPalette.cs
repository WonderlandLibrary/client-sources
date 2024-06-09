using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatColorPalette : Control
	{
		private int W;

		private int H;

		private Color _Red;

		private Color _Cyan;

		private Color _Blue;

		private Color _LimeGreen;

		private Color _Orange;

		private Color _Purple;

		private Color _Black;

		private Color _Gray;

		private Color _White;

		[Category("Colors")]
		public Color Black
		{
			get
			{
				return this._Black;
			}
			set
			{
				this._Black = value;
			}
		}

		[Category("Colors")]
		public Color Blue
		{
			get
			{
				return this._Blue;
			}
			set
			{
				this._Blue = value;
			}
		}

		[Category("Colors")]
		public Color Cyan
		{
			get
			{
				return this._Cyan;
			}
			set
			{
				this._Cyan = value;
			}
		}

		[Category("Colors")]
		public Color Gray
		{
			get
			{
				return this._Gray;
			}
			set
			{
				this._Gray = value;
			}
		}

		[Category("Colors")]
		public Color LimeGreen
		{
			get
			{
				return this._LimeGreen;
			}
			set
			{
				this._LimeGreen = value;
			}
		}

		[Category("Colors")]
		public Color Orange
		{
			get
			{
				return this._Orange;
			}
			set
			{
				this._Orange = value;
			}
		}

		[Category("Colors")]
		public Color Purple
		{
			get
			{
				return this._Purple;
			}
			set
			{
				this._Purple = value;
			}
		}

		[Category("Colors")]
		public Color Red
		{
			get
			{
				return this._Red;
			}
			set
			{
				this._Red = value;
			}
		}

		[Category("Colors")]
		public Color White
		{
			get
			{
				return this._White;
			}
			set
			{
				this._White = value;
			}
		}

		public FlatColorPalette()
		{
			this._Red = Color.FromArgb(220, 85, 96);
			this._Cyan = Color.FromArgb(10, 154, 157);
			this._Blue = Color.FromArgb(0, 128, 255);
			this._LimeGreen = Color.FromArgb(35, 168, 109);
			this._Orange = Color.FromArgb(253, 181, 63);
			this._Purple = Color.FromArgb(155, 88, 181);
			this._Black = Color.FromArgb(45, 47, 49);
			this._Gray = Color.FromArgb(63, 70, 73);
			this._White = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(60, 70, 73);
			base.Size = new System.Drawing.Size(160, 80);
			this.Font = new System.Drawing.Font("Segoe UI", 12f);
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = checked(base.Width - 1);
			this.H = checked(base.Height - 1);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			g.FillRectangle(new SolidBrush(this._Red), new Rectangle(0, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Cyan), new Rectangle(20, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Blue), new Rectangle(40, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._LimeGreen), new Rectangle(60, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Orange), new Rectangle(80, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Purple), new Rectangle(100, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Black), new Rectangle(120, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._Gray), new Rectangle(140, 0, 20, 40));
			g.FillRectangle(new SolidBrush(this._White), new Rectangle(160, 0, 20, 40));
			g.DrawString("Color Palette", this.Font, new SolidBrush(this._White), new Rectangle(0, 22, this.W, this.H), Helpers.CenterSF);
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Width = 180;
			base.Height = 80;
		}
	}
}