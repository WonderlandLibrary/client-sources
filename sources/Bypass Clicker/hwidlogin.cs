using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Management;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace BypassClicker
{
    public partial class hwidlogin : Form
    {
		int mf;
		int mfX;
		int mfY;
		public hwidlogin()
        {
            new clsComputerInfo();
            InitializeComponent();
        }

        private void bunifuButton2_Click(object sender, EventArgs e)
        {
            clsComputerInfo clsComputerInfo = new clsComputerInfo();
            string processorId = clsComputerInfo.GetProcessorId();
            string volumeSerial = clsComputerInfo.GetVolumeSerial("C");
            string motherBoardID = clsComputerInfo.GetMotherBoardID();
            string macaddress = clsComputerInfo.GetMACAddress();

            string hwid = macaddress + motherBoardID + volumeSerial + processorId;

            if (hwid == "64315026D2DF0B40hF0F7D002BFEBFBFF000106E5")
            {
                Form f1 = new Form1();
                this.Hide();
                f1.ShowDialog();
                this.Close();
            }
            else
            {
                MessageBox.Show("You aren't whitelisted!");
                this.Close();
            }
        }

		public class clsComputerInfo
		{
			// Token: 0x06000099 RID: 153 RVA: 0x00009824 File Offset: 0x00007A24
			internal string GetProcessorId()
			{
				string result = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new SelectQuery("Win32_processor")).Get())
				{
					result = managementBaseObject.GetPropertyValue("processorId").ToString();
				}
				return result;
			}

			// Token: 0x0600009A RID: 154 RVA: 0x00009890 File Offset: 0x00007A90
			internal string GetMACAddress()
			{
				ManagementObjectCollection instances = new ManagementClass("Win32_NetworkAdapterConfiguration").GetInstances();
				string text = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in instances)
				{
					ManagementObject managementObject = (ManagementObject)managementBaseObject;
					if (text.Equals(string.Empty))
					{
						if (Convert.ToBoolean(managementObject.GetPropertyValue("IPEnabled")))
						{
							text = managementObject.GetPropertyValue("MacAddress").ToString();
						}
						managementObject.Dispose();
					}
					text = text.Replace(":", string.Empty);
				}
				return text;
			}

			// Token: 0x0600009B RID: 155 RVA: 0x000025A4 File Offset: 0x000007A4
			internal string GetVolumeSerial(string strDriveLetter = "C")
			{
				ManagementObject managementObject = new ManagementObject(string.Format("win32_logicaldisk.deviceid=\"{0}:\"", strDriveLetter));
				managementObject.Get();
				return managementObject.GetPropertyValue("VolumeSerialNumber").ToString();
			}

			// Token: 0x0600009C RID: 156 RVA: 0x00009934 File Offset: 0x00007B34
			public string GetMotherBoardID()
			{
				string result = string.Empty;
				foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new SelectQuery("Win32_BaseBoard")).Get())
				{
					result = managementBaseObject.GetPropertyValue("product").ToString();
				}
				return result;
			}

			// Token: 0x0600009D RID: 157 RVA: 0x000099A0 File Offset: 0x00007BA0
			internal string getMD5Hash(string strToHash)
			{
				HashAlgorithm hashAlgorithm = new MD5CryptoServiceProvider();
				byte[] array = Encoding.ASCII.GetBytes(strToHash);
				array = hashAlgorithm.ComputeHash(array);
				string text = "";
				foreach (byte b in array)
				{
					text += b.ToString("x2");
				}
				return text;
			}
		}

		private void hwidlogin_MouseDown(object sender, MouseEventArgs e)
		{			
			mf = 1;
			mfX = e.X;
			mfY = e.Y;
		}

		private void hwidlogin_MouseMove(object sender, MouseEventArgs e)
		{
			if (mf == 1)
			{
				this.SetDesktopLocation(MousePosition.X - mfX, MousePosition.Y - mfY);
			}
		}

		private void hwidlogin_MouseUp(object sender, MouseEventArgs e)
		{
			mf = 0;
		}
	}
}
