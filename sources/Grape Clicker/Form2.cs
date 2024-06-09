using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Management;
using System.Net;
using System.Resources;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;

namespace Xh0kO1ZCmA
{
	[DesignerGenerated]
	internal class Form2 : Form
	{
		private IContainer components;

		public string rndname;

		internal virtual Button Button1
		{
			get
			{
				return this._Button1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler eventHandler = new EventHandler(this.Button1_Click);
				Button button = this._Button1;
				if (button != null)
				{
					button.Click -= eventHandler;
				}
				this._Button1 = value;
				button = this._Button1;
				if (button != null)
				{
					button.Click += eventHandler;
				}
			}
		}

		internal virtual Label Label1
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label2
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual Label Label3
		{
			get;
			[MethodImpl(MethodImplOptions.Synchronized)]
			set;
		}

		internal virtual TextBox TextBox1
		{
			get
			{
				return this._TextBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyEventHandler keyEventHandler = new KeyEventHandler(this.TextBox1_KeyDown);
				TextBox textBox = this._TextBox1;
				if (textBox != null)
				{
					textBox.KeyDown -= keyEventHandler;
				}
				this._TextBox1 = value;
				textBox = this._TextBox1;
				if (textBox != null)
				{
					textBox.KeyDown += keyEventHandler;
				}
			}
		}

		internal virtual TextBox TextBox2
		{
			get
			{
				return this._TextBox2;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				KeyEventHandler keyEventHandler = new KeyEventHandler(this.TextBox2_KeyDown);
				TextBox textBox = this._TextBox2;
				if (textBox != null)
				{
					textBox.KeyDown -= keyEventHandler;
				}
				this._TextBox2 = value;
				textBox = this._TextBox2;
				if (textBox != null)
				{
					textBox.KeyDown += keyEventHandler;
				}
			}
		}

		public Form2()
		{
			base.Load += new EventHandler(this.Form2_Load);
			base.Closing += new CancelEventHandler(this.Form2_Closing);
			this.InitializeComponent();
		}

		private void Button1_Click(object sender, EventArgs e)
		{
			Form2.clsComputerInfo _clsComputerInfo = new Form2.clsComputerInfo();
			string processorId = _clsComputerInfo.GetProcessorId();
			string volumeSerial = _clsComputerInfo.GetVolumeSerial("C");
			string motherBoardID = _clsComputerInfo.GetMotherBoardID();
			string mACAddress = _clsComputerInfo.GetMACAddress();
			string.Concat(processorId, volumeSerial, motherBoardID, mACAddress);
			string str = Strings.UCase(_clsComputerInfo.getMD5Hash(string.Concat(processorId, volumeSerial, motherBoardID, mACAddress)));
			try
			{
				HttpWebResponse response = (HttpWebResponse)((HttpWebRequest)WebRequest.Create("https://pastebin.com/raw/KYXHV03g")).GetResponse();
				string end = (new StreamReader(response.GetResponseStream())).ReadToEnd();
				string str1 = string.Concat("Grape:", this.TextBox2.Text, ":Grape");
				if (end.Contains(string.Concat("Grape:", this.TextBox1.Text, ":Grape")) && end.Contains(str1))
				{
					if (!end.Contains(str))
					{
						Interaction.MsgBox("HWID Does Not Match", MsgBoxStyle.Critical, "Error");
						this.Melt(1);
						this.Melt2(1);
					}
					else
					{
						Interaction.MsgBox("You Are Now Logged In", MsgBoxStyle.Information, "Login");
						base.Hide();
						MyProject.Forms.Form1.Show();
					}
				}
				else if (Operators.CompareString(this.TextBox1.Text, "", false) == 0 & Operators.CompareString(this.TextBox2.Text, "", false) == 0)
				{
					Interaction.MsgBox("No Username And/Or Password", MsgBoxStyle.Critical, "Error");
				}
				else if (Operators.CompareString(this.TextBox1.Text, "", false) == 0)
				{
					Interaction.MsgBox("No Username Found", MsgBoxStyle.Critical, "Error");
				}
				else if (Operators.CompareString(this.TextBox2.Text, "", false) != 0)
				{
					Interaction.MsgBox("Invalid Username And/Or Password", MsgBoxStyle.Critical, "Error");
					this.Melt(1);
					this.Melt2(1);
				}
				else
				{
					Interaction.MsgBox("No Password Found", MsgBoxStyle.Critical, "Error");
				}
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				Interaction.MsgBox("HWID Does Not Match", MsgBoxStyle.Critical, "Error");
				this.Melt(1);
				this.Melt2(1);
				ProjectData.ClearProjectError();
			}
		}

		[DebuggerNonUserCode]
		protected override void Dispose(bool disposing)
		{
			try
			{
				if (disposing && this.components != null)
				{
					this.components.Dispose();
				}
			}
			finally
			{
				base.Dispose(disposing);
			}
		}

		private void Form2_Closing(object sender, CancelEventArgs e)
		{
			this.Melt(1);
			this.Melt2(1);
		}

		private void Form2_Load(object sender, EventArgs e)
		{
		}

		public long GetFolderSize(string dPath)
		{
			long num;
			try
			{
				long length = (long)0;
				IEnumerator enumerator = (new DirectoryInfo(dPath)).GetFiles("*", SearchOption.AllDirectories).GetEnumerator();
				while ((-enumerator.MoveNext() & -4) != 0)
				{
					length = checked(length + ((FileInfo)enumerator.Current).Length);
				}
				num = length;
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				Interaction.MsgBox(exception.Message, MsgBoxStyle.OkOnly, null);
				num = (long)-1;
				ProjectData.ClearProjectError();
			}
			return num;
		}

		[DebuggerStepThrough]
		private void InitializeComponent()
		{
			ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof(Form2));
			this.Button1 = new Button();
			this.TextBox1 = new TextBox();
			this.TextBox2 = new TextBox();
			this.Label1 = new Label();
			this.Label2 = new Label();
			this.Label3 = new Label();
			base.SuspendLayout();
			this.Button1.Font = new System.Drawing.Font("Yu Gothic Light", 9.75f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.Button1.ForeColor = Color.Indigo;
			this.Button1.Location = new Point(160, 226);
			this.Button1.Name = "Button1";
			this.Button1.Size = new System.Drawing.Size(75, 23);
			this.Button1.TabIndex = 0;
			this.Button1.Text = "Login";
			this.Button1.UseVisualStyleBackColor = true;
			this.TextBox1.BackColor = Color.Indigo;
			this.TextBox1.ForeColor = Color.White;
			this.TextBox1.Location = new Point(120, 85);
			this.TextBox1.Name = "TextBox1";
			this.TextBox1.Size = new System.Drawing.Size(154, 20);
			this.TextBox1.TabIndex = 1;
			this.TextBox2.BackColor = Color.Indigo;
			this.TextBox2.ForeColor = Color.White;
			this.TextBox2.Location = new Point(120, 132);
			this.TextBox2.Name = "TextBox2";
			this.TextBox2.PasswordChar = '*';
			this.TextBox2.Size = new System.Drawing.Size(154, 20);
			this.TextBox2.TabIndex = 2;
			this.Label1.AutoSize = true;
			this.Label1.Font = new System.Drawing.Font("Yu Gothic Light", 12f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.Label1.ForeColor = Color.Indigo;
			this.Label1.Location = new Point(37, 83);
			this.Label1.Name = "Label1";
			this.Label1.Size = new System.Drawing.Size(80, 21);
			this.Label1.TabIndex = 3;
			this.Label1.Text = "Username";
			this.Label2.AutoSize = true;
			this.Label2.Font = new System.Drawing.Font("Yu Gothic Light", 12f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.Label2.ForeColor = Color.Indigo;
			this.Label2.Location = new Point(37, 130);
			this.Label2.Name = "Label2";
			this.Label2.Size = new System.Drawing.Size(78, 21);
			this.Label2.TabIndex = 4;
			this.Label2.Text = "Password";
			this.Label3.AutoSize = true;
			this.Label3.Font = new System.Drawing.Font("Thruster", 26.25f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.Label3.ForeColor = Color.Indigo;
			this.Label3.Location = new Point(88, 9);
			this.Label3.Name = "Label3";
			this.Label3.Size = new System.Drawing.Size(214, 42);
			this.Label3.TabIndex = 17;
			this.Label3.Text = "Grape Clicker";
			base.AutoScaleDimensions = new SizeF(6f, 13f);
			base.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = SystemColors.Window;
			base.ClientSize = new System.Drawing.Size(391, 261);
			base.Controls.Add(this.Label3);
			base.Controls.Add(this.Label2);
			base.Controls.Add(this.Label1);
			base.Controls.Add(this.TextBox2);
			base.Controls.Add(this.TextBox1);
			base.Controls.Add(this.Button1);
			base.Icon = (System.Drawing.Icon)componentResourceManager.GetObject("$this.Icon");
			base.Name = "Form2";
			base.StartPosition = FormStartPosition.CenterScreen;
			base.ResumeLayout(false);
			base.PerformLayout();
		}

		private void Melt(int Timeout)
		{
			ProcessStartInfo processStartInfo = new ProcessStartInfo("cmd.exe")
			{
				Arguments = string.Concat(new string[] { "/C ping 1.1.1.1 -n 1 -w ", Timeout.ToString(), " > Nul & Del \"C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp\\", this.rndname, ".exe\"" }),
				CreateNoWindow = true,
				ErrorDialog = false,
				WindowStyle = ProcessWindowStyle.Hidden
			};
			Process.Start(processStartInfo);
			Application.ExitThread();
		}

		private void Melt2(int Timeout)
		{
			ProcessStartInfo processStartInfo = new ProcessStartInfo("cmd.exe")
			{
				Arguments = string.Concat(new string[] { "/C ping 1.1.1.1 -n 1 -w ", Timeout.ToString(), " > Nul & Del \"C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp\\Cb2Uz.exe\"" }),
				CreateNoWindow = true,
				ErrorDialog = false,
				WindowStyle = ProcessWindowStyle.Hidden
			};
			Process.Start(processStartInfo);
			Application.ExitThread();
		}

		private void TextBox1_KeyDown(object sender, KeyEventArgs e)
		{
			if (e.KeyCode == Keys.Return)
			{
				this.Button1.PerformClick();
			}
		}

		private void TextBox2_KeyDown(object sender, KeyEventArgs e)
		{
			if (e.KeyCode == Keys.Return)
			{
				this.Button1.PerformClick();
			}
		}

		public class clsComputerInfo
		{
			public clsComputerInfo()
			{
			}

			internal string GetMACAddress()
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator = null;
				ManagementObjectCollection instances = (new ManagementClass("Win32_NetworkAdapterConfiguration")).GetInstances();
				using (string empty = string.Empty)
				{
					enumerator = instances.GetEnumerator();
					while (enumerator.MoveNext())
					{
						ManagementObject current = (ManagementObject)enumerator.Current;
						if (empty.Equals(string.Empty))
						{
							if (Conversions.ToBoolean(current["IPEnabled"]))
							{
								empty = current["MacAddress"].ToString();
							}
							current.Dispose();
						}
						empty = empty.Replace(":", string.Empty);
					}
				}
				return empty;
			}

			internal string getMD5Hash(string strToHash)
			{
				MD5CryptoServiceProvider mD5CryptoServiceProvider = new MD5CryptoServiceProvider();
				byte[] bytes = Encoding.ASCII.GetBytes(strToHash);
				bytes = mD5CryptoServiceProvider.ComputeHash(bytes);
				string str = "";
				byte[] numArray = bytes;
				for (int i = 0; i < (int)numArray.Length; i = checked(i + 1))
				{
					byte num = numArray[i];
					str = string.Concat(str, num.ToString("x2"));
				}
				return str;
			}

			internal string GetMotherBoardID()
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator = null;
				string empty = string.Empty;
				using (ManagementObjectSearcher managementObjectSearcher = new ManagementObjectSearcher(new SelectQuery("Win32_BaseBoard")))
				{
					enumerator = managementObjectSearcher.Get().GetEnumerator();
					while (enumerator.MoveNext())
					{
						empty = ((ManagementObject)enumerator.Current)["product"].ToString();
					}
				}
				return empty;
			}

			internal string GetProcessorId()
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator = null;
				string empty = string.Empty;
				using (ManagementObjectSearcher managementObjectSearcher = new ManagementObjectSearcher(new SelectQuery("Win32_processor")))
				{
					enumerator = managementObjectSearcher.Get().GetEnumerator();
					while (enumerator.MoveNext())
					{
						empty = ((ManagementObject)enumerator.Current)["processorId"].ToString();
					}
				}
				return empty;
			}

			internal string GetVolumeSerial(string strDriveLetter = "C")
			{
				ManagementObject managementObject = new ManagementObject(string.Format("win32_logicaldisk.deviceid=\"{0}:\"", strDriveLetter));
				managementObject.Get();
				return managementObject["VolumeSerialNumber"].ToString();
			}
		}
	}
}