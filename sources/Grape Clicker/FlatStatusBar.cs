using Microsoft.VisualBasic.CompilerServices;
using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatStatusBar : Control
	{
		private int W;

		private int H;

		private bool _ShowTimeDate;

		private Color _BaseColor;

		private Color _TextColor;

		private Color _RectColor;

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

		[Category("Colors")]
		public Color RectColor
		{
			get
			{
				return this._RectColor;
			}
			set
			{
				this._RectColor = value;
			}
		}

		public bool ShowTimeDate
		{
			get
			{
				return this._ShowTimeDate;
			}
			set
			{
				this._ShowTimeDate = value;
			}
		}

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

		public FlatStatusBar()
		{
			this._ShowTimeDate = false;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._TextColor = Color.White;
			this._RectColor = Helpers._FlatColor;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new System.Drawing.Font("Segoe UI", 8f);
			this.ForeColor = Color.White;
			base.Size = new System.Drawing.Size(base.Width, 20);
		}

		protected override void CreateHandle()
		{
			base.CreateHandle();
			this.Dock = DockStyle.Bottom;
		}

		public string GetTimeDate()
		{
			string[] str = new string[5];
			DateTime now = DateTime.Now;
			str[0] = Conversions.ToString(now.Date);
			str[1] = " ";
			now = DateTime.Now;
			str[2] = Conversions.ToString(now.Hour);
			str[3] = ":";
			now = DateTime.Now;
			str[4] = Conversions.ToString(now.Minute);
			return string.Concat(str);
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BaseColor);
			g.FillRectangle(new SolidBrush(this.BaseColor), rectangle);
			g.DrawString(this.Text, this.Font, Brushes.White, new Rectangle(10, 4, this.W, this.H), Helpers.NearSF);
			g.FillRectangle(new SolidBrush(this._RectColor), new Rectangle(4, 4, 4, 14));
			if (this.ShowTimeDate)
			{
				g.DrawString(this.GetTimeDate(), this.Font, new SolidBrush(this._TextColor), new Rectangle(-4, 2, this.W, this.H), new StringFormat()
				{
					Alignment = StringAlignment.Far,
					LineAlignment = StringAlignment.Center
				});
			}
			g = null;
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}
	}
}