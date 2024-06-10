// Token: 0x0200000D RID: 13
public partial class balanosmelerca : global::System.Windows.Forms.Form
{
	// Token: 0x06000041 RID: 65 RVA: 0x0000802C File Offset: 0x0000622C
	private void InitializeComponent()
	{
		this.icontainer_0 = new global::System.ComponentModel.Container();
		global::System.ComponentModel.ComponentResourceManager componentResourceManager = new global::System.ComponentModel.ComponentResourceManager(typeof(global::balanosmelerca));
		this.bunifuElipse_0 = new global::Bunifu.Framework.UI.BunifuElipse(this.icontainer_0);
		this.gunaElipsePanel1 = new global::Guna.UI.WinForms.GunaElipsePanel();
		this.gunaElipsePanel2 = new global::Guna.UI.WinForms.GunaElipsePanel();
		this.gunaElipsePanel3 = new global::Guna.UI.WinForms.GunaElipsePanel();
		this.gunaElipsePanel4 = new global::Guna.UI.WinForms.GunaElipsePanel();
		this.gunaControlBox1 = new global::Guna.UI.WinForms.GunaControlBox();
		this.button1 = new global::System.Windows.Forms.Button();
		this.button2 = new global::System.Windows.Forms.Button();
		this.button3 = new global::System.Windows.Forms.Button();
		this.button4 = new global::System.Windows.Forms.Button();
		this.gunaElipsePanel5 = new global::Guna.UI.WinForms.GunaElipsePanel();
		this.gunaSeparator2 = new global::Guna.UI.WinForms.GunaSeparator();
		this.gunaPictureBox1 = new global::Guna.UI.WinForms.GunaPictureBox();
		this.gunaSeparator1 = new global::Guna.UI.WinForms.GunaSeparator();
		this.backgroundWorker_0 = new global::System.ComponentModel.BackgroundWorker();
		this.backgroundWorker_1 = new global::System.ComponentModel.BackgroundWorker();
		this.gunaElipsePanel5.SuspendLayout();
		this.gunaPictureBox1.BeginInit();
		base.SuspendLayout();
		this.bunifuElipse_0.ElipseRadius = 5;
		this.bunifuElipse_0.TargetControl = this;
		this.gunaElipsePanel1.BackColor = global::System.Drawing.Color.Transparent;
		this.gunaElipsePanel1.BaseColor = global::System.Drawing.Color.DarkSlateBlue;
		this.gunaElipsePanel1.Location = new global::System.Drawing.Point(0, -8);
		this.gunaElipsePanel1.Name = "gunaElipsePanel1";
		this.gunaElipsePanel1.Size = new global::System.Drawing.Size(644, 10);
		this.gunaElipsePanel1.TabIndex = 0;
		this.gunaElipsePanel2.BackColor = global::System.Drawing.Color.Transparent;
		this.gunaElipsePanel2.BaseColor = global::System.Drawing.Color.DarkSlateBlue;
		this.gunaElipsePanel2.Location = new global::System.Drawing.Point(0, 354);
		this.gunaElipsePanel2.Name = "gunaElipsePanel2";
		this.gunaElipsePanel2.Size = new global::System.Drawing.Size(644, 10);
		this.gunaElipsePanel2.TabIndex = 0;
		this.gunaElipsePanel3.BackColor = global::System.Drawing.Color.Transparent;
		this.gunaElipsePanel3.BaseColor = global::System.Drawing.Color.DarkSlateBlue;
		this.gunaElipsePanel3.Location = new global::System.Drawing.Point(0, -23);
		this.gunaElipsePanel3.Name = "gunaElipsePanel3";
		this.gunaElipsePanel3.Size = new global::System.Drawing.Size(1, 426);
		this.gunaElipsePanel3.TabIndex = 0;
		this.gunaElipsePanel4.BackColor = global::System.Drawing.Color.Transparent;
		this.gunaElipsePanel4.BaseColor = global::System.Drawing.Color.DarkSlateBlue;
		this.gunaElipsePanel4.Location = new global::System.Drawing.Point(619, -3);
		this.gunaElipsePanel4.Name = "gunaElipsePanel4";
		this.gunaElipsePanel4.Size = new global::System.Drawing.Size(1, 362);
		this.gunaElipsePanel4.TabIndex = 0;
		this.gunaControlBox1.Anchor = (global::System.Windows.Forms.AnchorStyles.Top | global::System.Windows.Forms.AnchorStyles.Right);
		this.gunaControlBox1.AnimationHoverSpeed = 0.07f;
		this.gunaControlBox1.AnimationSpeed = 0.03f;
		this.gunaControlBox1.IconColor = global::System.Drawing.Color.BlanchedAlmond;
		this.gunaControlBox1.IconSize = 15f;
		this.gunaControlBox1.Location = new global::System.Drawing.Point(4, 324);
		this.gunaControlBox1.Name = "gunaControlBox1";
		this.gunaControlBox1.OnHoverBackColor = global::System.Drawing.Color.FromArgb(103, 58, 183);
		this.gunaControlBox1.OnHoverIconColor = global::System.Drawing.Color.White;
		this.gunaControlBox1.OnPressedColor = global::System.Drawing.Color.BlanchedAlmond;
		this.gunaControlBox1.Size = new global::System.Drawing.Size(45, 29);
		this.gunaControlBox1.TabIndex = 1;
		this.button1.BackColor = global::System.Drawing.Color.BlueViolet;
		this.button1.FlatStyle = global::System.Windows.Forms.FlatStyle.Popup;
		this.button1.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 12f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
		this.button1.ForeColor = global::System.Drawing.Color.White;
		this.button1.Location = new global::System.Drawing.Point(405, 316);
		this.button1.Name = "button1";
		this.button1.Size = new global::System.Drawing.Size(75, 38);
		this.button1.TabIndex = 2;
		this.button1.Text = "Reach";
		this.button1.UseVisualStyleBackColor = false;
		this.button1.Click += new global::System.EventHandler(this.button1_Click);
		this.button2.BackColor = global::System.Drawing.Color.BlueViolet;
		this.button2.FlatStyle = global::System.Windows.Forms.FlatStyle.Popup;
		this.button2.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 12f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
		this.button2.ForeColor = global::System.Drawing.Color.White;
		this.button2.Location = new global::System.Drawing.Point(473, 316);
		this.button2.Name = "button2";
		this.button2.Size = new global::System.Drawing.Size(75, 38);
		this.button2.TabIndex = 2;
		this.button2.Text = "Velocity";
		this.button2.UseVisualStyleBackColor = false;
		this.button2.Click += new global::System.EventHandler(this.button2_Click);
		this.button3.BackColor = global::System.Drawing.Color.BlueViolet;
		this.button3.FlatStyle = global::System.Windows.Forms.FlatStyle.Popup;
		this.button3.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 12f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
		this.button3.ForeColor = global::System.Drawing.Color.White;
		this.button3.Location = new global::System.Drawing.Point(544, 316);
		this.button3.Name = "button3";
		this.button3.Size = new global::System.Drawing.Size(75, 38);
		this.button3.TabIndex = 2;
		this.button3.Text = "Clicker";
		this.button3.UseVisualStyleBackColor = false;
		this.button3.Click += new global::System.EventHandler(this.button3_Click);
		this.button4.BackColor = global::System.Drawing.Color.BlueViolet;
		this.button4.FlatStyle = global::System.Windows.Forms.FlatStyle.Popup;
		this.button4.Font = new global::System.Drawing.Font("Microsoft Sans Serif", 22f, global::System.Drawing.FontStyle.Regular, global::System.Drawing.GraphicsUnit.Point, 0);
		this.button4.ForeColor = global::System.Drawing.Color.White;
		this.button4.Location = new global::System.Drawing.Point(333, 316);
		this.button4.Name = "button4";
		this.button4.Size = new global::System.Drawing.Size(75, 38);
		this.button4.TabIndex = 2;
		this.button4.Text = "...";
		this.button4.UseVisualStyleBackColor = false;
		this.button4.Click += new global::System.EventHandler(this.button4_Click);
		this.gunaElipsePanel5.BackColor = global::System.Drawing.Color.FromArgb(9, 9, 9);
		this.gunaElipsePanel5.BaseColor = global::System.Drawing.Color.FromArgb(11, 11, 11);
		this.gunaElipsePanel5.Controls.Add(this.gunaSeparator2);
		this.gunaElipsePanel5.Location = new global::System.Drawing.Point(44, 51);
		this.gunaElipsePanel5.Name = "gunaElipsePanel5";
		this.gunaElipsePanel5.Size = new global::System.Drawing.Size(530, 250);
		this.gunaElipsePanel5.TabIndex = 3;
		this.gunaElipsePanel5.Paint += new global::System.Windows.Forms.PaintEventHandler(this.gunaElipsePanel5_Paint);
		this.gunaSeparator2.LineColor = global::System.Drawing.Color.Silver;
		this.gunaSeparator2.Location = new global::System.Drawing.Point(0, 244);
		this.gunaSeparator2.Name = "gunaSeparator2";
		this.gunaSeparator2.Size = new global::System.Drawing.Size(530, 10);
		this.gunaSeparator2.TabIndex = 5;
		this.gunaPictureBox1.BaseColor = global::System.Drawing.Color.White;
		this.gunaPictureBox1.Image = global::Class13.Bitmap_1;
		this.gunaPictureBox1.Location = new global::System.Drawing.Point(0, -3);
		this.gunaPictureBox1.Name = "gunaPictureBox1";
		this.gunaPictureBox1.Size = new global::System.Drawing.Size(620, 10);
		this.gunaPictureBox1.SizeMode = global::System.Windows.Forms.PictureBoxSizeMode.StretchImage;
		this.gunaPictureBox1.TabIndex = 4;
		this.gunaPictureBox1.TabStop = false;
		this.gunaSeparator1.LineColor = global::System.Drawing.Color.Silver;
		this.gunaSeparator1.Location = new global::System.Drawing.Point(44, 42);
		this.gunaSeparator1.Name = "gunaSeparator1";
		this.gunaSeparator1.Size = new global::System.Drawing.Size(530, 10);
		this.gunaSeparator1.TabIndex = 5;
		base.AutoScaleDimensions = new global::System.Drawing.SizeF(6f, 13f);
		base.AutoScaleMode = global::System.Windows.Forms.AutoScaleMode.Font;
		this.BackColor = global::System.Drawing.Color.FromArgb(6, 6, 6);
		base.ClientSize = new global::System.Drawing.Size(621, 358);
		base.Controls.Add(this.gunaSeparator1);
		base.Controls.Add(this.gunaPictureBox1);
		base.Controls.Add(this.gunaElipsePanel5);
		base.Controls.Add(this.button4);
		base.Controls.Add(this.button3);
		base.Controls.Add(this.button2);
		base.Controls.Add(this.button1);
		base.Controls.Add(this.gunaControlBox1);
		base.Controls.Add(this.gunaElipsePanel4);
		base.Controls.Add(this.gunaElipsePanel3);
		base.Controls.Add(this.gunaElipsePanel2);
		base.Controls.Add(this.gunaElipsePanel1);
		base.FormBorderStyle = global::System.Windows.Forms.FormBorderStyle.None;
		base.Icon = (global::System.Drawing.Icon)componentResourceManager.GetObject("$this.Icon");
		base.Name = "balanosmelerca";
		base.Load += new global::System.EventHandler(this.balanosmelerca_Load);
		base.MouseDown += new global::System.Windows.Forms.MouseEventHandler(this.balanosmelerca_MouseDown);
		base.MouseMove += new global::System.Windows.Forms.MouseEventHandler(this.balanosmelerca_MouseMove);
		this.gunaElipsePanel5.ResumeLayout(false);
		this.gunaPictureBox1.EndInit();
		base.ResumeLayout(false);
	}

	// Token: 0x04000030 RID: 48
	private global::System.ComponentModel.IContainer icontainer_0 = null;

	// Token: 0x04000031 RID: 49
	private global::Bunifu.Framework.UI.BunifuElipse bunifuElipse_0;

	// Token: 0x04000032 RID: 50
	private global::Guna.UI.WinForms.GunaElipsePanel gunaElipsePanel1;

	// Token: 0x04000033 RID: 51
	private global::Guna.UI.WinForms.GunaElipsePanel gunaElipsePanel4;

	// Token: 0x04000034 RID: 52
	private global::Guna.UI.WinForms.GunaElipsePanel gunaElipsePanel3;

	// Token: 0x04000035 RID: 53
	private global::Guna.UI.WinForms.GunaElipsePanel gunaElipsePanel2;

	// Token: 0x04000036 RID: 54
	private global::Guna.UI.WinForms.GunaControlBox gunaControlBox1;

	// Token: 0x04000037 RID: 55
	private global::System.Windows.Forms.Button button2;

	// Token: 0x04000038 RID: 56
	private global::System.Windows.Forms.Button button1;

	// Token: 0x04000039 RID: 57
	private global::System.Windows.Forms.Button button4;

	// Token: 0x0400003A RID: 58
	private global::System.Windows.Forms.Button button3;

	// Token: 0x0400003B RID: 59
	private global::Guna.UI.WinForms.GunaElipsePanel gunaElipsePanel5;

	// Token: 0x0400003C RID: 60
	private global::Guna.UI.WinForms.GunaPictureBox gunaPictureBox1;

	// Token: 0x0400003D RID: 61
	private global::Guna.UI.WinForms.GunaSeparator gunaSeparator1;

	// Token: 0x0400003E RID: 62
	private global::Guna.UI.WinForms.GunaSeparator gunaSeparator2;

	// Token: 0x0400003F RID: 63
	private global::System.ComponentModel.BackgroundWorker backgroundWorker_0;

	// Token: 0x04000040 RID: 64
	private global::System.ComponentModel.BackgroundWorker backgroundWorker_1;
}
