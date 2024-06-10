using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000008 RID: 8
	public class MyFadeEffect : Button
	{
		// Token: 0x1700000A RID: 10
		// (get) Token: 0x06000013 RID: 19 RVA: 0x0000220B File Offset: 0x0000040B
		// (set) Token: 0x06000014 RID: 20 RVA: 0x00002213 File Offset: 0x00000413
		private Image SetImage
		{
			get
			{
				return this._SetImage;
			}
			set
			{
				this._SetImage = value;
				base.Invalidate();
			}
		}

		// Token: 0x06000015 RID: 21 RVA: 0x00002224 File Offset: 0x00000424
		public MyFadeEffect()
		{
			this.ImgAtt = new ImageAttributes();
			this.Alpha = 0f;
			this.SpeedFade = 0f;
			this.TmrFade = new Timer
			{
				Enabled = false,
				Interval = 1
			};
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			base.Size = new Size(150, 50);
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00002298 File Offset: 0x00000498
		private void DrawFadeEffect(Graphics G)
		{
			this.SpeedFade = 0.11f;
			float[][] array = new float[5][];
			int num = 0;
			float[] array2 = new float[5];
			array2[0] = 1f;
			array[num] = array2;
			int num2 = 1;
			float[] array3 = new float[5];
			array3[1] = 1f;
			array[num2] = array3;
			int num3 = 2;
			float[] array4 = new float[5];
			array4[2] = 1f;
			array[num3] = array4;
			int num4 = 3;
			float[] array5 = new float[5];
			array5[3] = this.Alpha;
			array[num4] = array5;
			array[4] = new float[]
			{
				0f,
				0f,
				0f,
				0f,
				1f
			};
			ColorMatrix newColorMatrix = new ColorMatrix(array);
			this.ImgAtt.SetColorMatrix(newColorMatrix, ColorMatrixFlag.Default, ColorAdjustType.Bitmap);
			this.ImgHover = new Bitmap(base.Width, base.Height);
			this.GrHover = Graphics.FromImage(this.ImgHover);
			SolidBrush brush = new SolidBrush(Color.FromArgb(150, Color.Blue));
			this.GrHover.FillRectangle(brush, 0, 0, base.Width, base.Height);
			G.DrawImage(this.ImgHover, new Rectangle(0, 0, this.ImgHover.Width, this.ImgHover.Height), 0, 0, this.ImgHover.Width, this.ImgHover.Height, GraphicsUnit.Pixel, this.ImgAtt);
			this.ImgHover.Dispose();
			this.GrHover.Dispose();
		}

		// Token: 0x06000017 RID: 23 RVA: 0x000023D8 File Offset: 0x000005D8
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			e.Graphics.DrawRectangle(Pens.Red, 0, 0, base.Width - 1, base.Height - 1);
			TextRenderer.DrawText(e.Graphics, this.Text, this.Font, base.ClientRectangle, this.ForeColor);
		}

		// Token: 0x06000018 RID: 24 RVA: 0x00002431 File Offset: 0x00000631
		protected override void OnPaintBackground(PaintEventArgs e)
		{
			base.OnPaintBackground(e);
			this.DrawFadeEffect(e.Graphics);
		}

		// Token: 0x06000019 RID: 25 RVA: 0x00002446 File Offset: 0x00000646
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.StartFade = true;
			this.TmrFade.Enabled = true;
			base.Invalidate();
		}

		// Token: 0x0600001A RID: 26 RVA: 0x00002468 File Offset: 0x00000668
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.StartFade = false;
			this.TmrFade.Enabled = true;
			base.Invalidate();
		}

		// Token: 0x0600001B RID: 27 RVA: 0x0000248C File Offset: 0x0000068C
		private void TmrFade_Tick(object sender, EventArgs e)
		{
			if (this.StartFade)
			{
				ref float ptr = ref this.Alpha;
				this.Alpha = ptr + this.SpeedFade;
			}
			else
			{
				ref float ptr = ref this.Alpha;
				this.Alpha = ptr - this.SpeedFade;
			}
			this.SetImage = this.ImgHover;
			if (this.Alpha >= 1f)
			{
				this.Alpha = 1f;
				this.TmrFade.Enabled = false;
				return;
			}
			if (this.Alpha <= 0f)
			{
				this.Alpha = 0f;
				this.TmrFade.Enabled = false;
			}
		}

		// Token: 0x1700000B RID: 11
		// (get) Token: 0x0600001C RID: 28 RVA: 0x0000251C File Offset: 0x0000071C
		// (set) Token: 0x0600001D RID: 29 RVA: 0x00002524 File Offset: 0x00000724
		internal virtual Timer TmrFade
		{
			[CompilerGenerated]
			get
			{
				return this._TmrFade;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.TmrFade_Tick);
				Timer tmrFade = this._TmrFade;
				if (tmrFade != null)
				{
					tmrFade.Tick -= value2;
				}
				this._TmrFade = value;
				tmrFade = this._TmrFade;
				if (tmrFade != null)
				{
					tmrFade.Tick += value2;
				}
			}
		}

		// Token: 0x0400000B RID: 11
		private ImageAttributes ImgAtt;

		// Token: 0x0400000C RID: 12
		private bool StartFade;

		// Token: 0x0400000D RID: 13
		private float Alpha;

		// Token: 0x0400000E RID: 14
		private float SpeedFade;

		// Token: 0x0400000F RID: 15
		private Bitmap ImgHover;

		// Token: 0x04000010 RID: 16
		private Graphics GrHover;

		// Token: 0x04000011 RID: 17
		private Image _SetImage;
	}
}
