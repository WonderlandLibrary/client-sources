using System;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000010 RID: 16
	internal class Class6 : Label
	{
		// Token: 0x060000B6 RID: 182 RVA: 0x0000211F File Offset: 0x0000031F
		public Class6()
		{
			<Module>.Class0.smethod_0();
			base..ctor();
			this.Color_0 = Color.Green;
			this.Single_0 = 1.5f;
		}

		// Token: 0x17000022 RID: 34
		// (get) Token: 0x060000B7 RID: 183 RVA: 0x0001D81C File Offset: 0x0001BA1C
		// (set) Token: 0x060000B8 RID: 184 RVA: 0x0001D884 File Offset: 0x0001BA84
		public Color Color_0
		{
			[CompilerGenerated]
			get
			{
				int num = 0;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 2);
				return this.color_0;
			}
			[CompilerGenerated]
			set
			{
				int num = 0;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 2)
					{
						this.color_0 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 3);
			}
		}

		// Token: 0x17000023 RID: 35
		// (get) Token: 0x060000B9 RID: 185 RVA: 0x0001D904 File Offset: 0x0001BB04
		// (set) Token: 0x060000BA RID: 186 RVA: 0x0001D96C File Offset: 0x0001BB6C
		public float Single_0
		{
			[CompilerGenerated]
			get
			{
				int num = 0;
				do
				{
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 2);
				return this.float_0;
			}
			[CompilerGenerated]
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						this.float_0 = value;
						num = 3;
					}
					if (num == 1)
					{
						<Module>.Class0.smethod_0();
						num = 2;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 3);
			}
		}

		// Token: 0x060000BB RID: 187 RVA: 0x0001D9EC File Offset: 0x0001BBEC
		protected virtual void OnPaint(PaintEventArgs e)
		{
			int num = 0;
			GraphicsPath graphicsPath;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 3)
				{
					graphicsPath = new GraphicsPath();
					num = 4;
				}
				if (num == 2)
				{
					e.Graphics.FillRectangle(new SolidBrush(this.BackColor), base.ClientRectangle);
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 4);
			try
			{
				using (Pen pen = new Pen(this.Color_0, this.Single_0)
				{
					LineJoin = LineJoin.Round
				})
				{
					using (StringFormat stringFormat = new StringFormat())
					{
						using (Brush brush = new SolidBrush(this.ForeColor))
						{
							graphicsPath.AddString(this.Text, this.Font.FontFamily, (int)this.Font.Style, this.Font.Size, base.ClientRectangle, stringFormat);
							e.Graphics.ScaleTransform(1.3f, 1.35f);
							e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
							e.Graphics.DrawPath(pen, graphicsPath);
							e.Graphics.FillPath(brush, graphicsPath);
						}
					}
				}
			}
			finally
			{
				if (graphicsPath != null)
				{
					((IDisposable)graphicsPath).Dispose();
				}
			}
		}

		// Token: 0x04000090 RID: 144
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private Color color_0;

		// Token: 0x04000091 RID: 145
		[CompilerGenerated]
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		private float float_0;
	}
}
