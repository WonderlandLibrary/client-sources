using System;
using System.IO;
using System.Security.Cryptography;
using System.Text;

namespace uhssvc
{
	// Token: 0x0200001E RID: 30
	internal class Encryption
	{
		// Token: 0x0600008F RID: 143 RVA: 0x000059C0 File Offset: 0x00003BC0
		public static string smethod_0(string string_0)
		{
			string @string = Encoding.Default.GetString(Convert.FromBase64String(Constants.APIENCRYPTKEY));
			SHA256 sha = SHA256.Create();
			byte[] byte_ = sha.ComputeHash(Encoding.ASCII.GetBytes(@string));
			byte[] bytes = Encoding.ASCII.GetBytes(Encoding.Default.GetString(Convert.FromBase64String(Constants.APIENCRYPTSALT)));
			return Encryption.EncryptString(string_0, byte_, bytes);
		}

		// Token: 0x06000090 RID: 144 RVA: 0x00005A30 File Offset: 0x00003C30
		public static string EncryptService(string string_0)
		{
			string @string = Encoding.Default.GetString(Convert.FromBase64String(Constants.APIENCRYPTKEY));
			SHA256 sha = SHA256.Create();
			byte[] byte_ = sha.ComputeHash(Encoding.ASCII.GetBytes(@string));
			byte[] bytes = Encoding.ASCII.GetBytes(Encoding.Default.GetString(Convert.FromBase64String(Constants.APIENCRYPTSALT)));
			string str = Encryption.EncryptString(string_0, byte_, bytes);
			int int_ = int.Parse(OnProgramStart.AID.Substring(0, 2));
			return str + Security.Obfuscate(int_);
		}

		// Token: 0x06000091 RID: 145 RVA: 0x00005AC0 File Offset: 0x00003CC0
		public static string DecryptService(string string_0)
		{
			string @string = Encoding.Default.GetString(Convert.FromBase64String(Constants.APIENCRYPTKEY));
			SHA256 sha = SHA256.Create();
			byte[] byte_ = sha.ComputeHash(Encoding.ASCII.GetBytes(@string));
			byte[] bytes = Encoding.ASCII.GetBytes(Encoding.Default.GetString(Convert.FromBase64String(Constants.APIENCRYPTSALT)));
			return Encryption.DecryptString(string_0, byte_, bytes);
		}

		// Token: 0x06000092 RID: 146 RVA: 0x00005B30 File Offset: 0x00003D30
		public static string EncryptString(string string_0, byte[] byte_0, byte[] byte_1)
		{
			Aes aes = Aes.Create();
			aes.Mode = CipherMode.CBC;
			aes.Key = byte_0;
			aes.IV = byte_1;
			MemoryStream memoryStream = new MemoryStream();
			ICryptoTransform transform = aes.CreateEncryptor();
			CryptoStream cryptoStream = new CryptoStream(memoryStream, transform, CryptoStreamMode.Write);
			byte[] bytes = Encoding.ASCII.GetBytes(string_0);
			cryptoStream.Write(bytes, 0, bytes.Length);
			cryptoStream.FlushFinalBlock();
			byte[] array = memoryStream.ToArray();
			memoryStream.Close();
			cryptoStream.Close();
			return Convert.ToBase64String(array, 0, array.Length);
		}

		// Token: 0x06000093 RID: 147 RVA: 0x00005BB8 File Offset: 0x00003DB8
		public static string DecryptString(string string_0, byte[] byte_0, byte[] byte_1)
		{
			Aes aes = Aes.Create();
			aes.Mode = CipherMode.CBC;
			aes.Key = byte_0;
			aes.IV = byte_1;
			MemoryStream memoryStream = new MemoryStream();
			ICryptoTransform transform = aes.CreateDecryptor();
			CryptoStream cryptoStream = new CryptoStream(memoryStream, transform, CryptoStreamMode.Write);
			string result = string.Empty;
			try
			{
				byte[] array = Convert.FromBase64String(string_0);
				cryptoStream.Write(array, 0, array.Length);
				cryptoStream.FlushFinalBlock();
				byte[] array2 = memoryStream.ToArray();
				result = Encoding.ASCII.GetString(array2, 0, array2.Length);
			}
			finally
			{
				memoryStream.Close();
				cryptoStream.Close();
			}
			return result;
		}

		// Token: 0x06000094 RID: 148 RVA: 0x00005C58 File Offset: 0x00003E58
		public static string Decode(string string_0)
		{
			string_0 = string_0.Replace('_', '/').Replace('-', '+');
			int num = string_0.Length % 4;
			int num2 = num;
			if (num2 != 2)
			{
				if (num2 == 3)
				{
					string_0 += "=";
				}
			}
			else
			{
				string_0 += "==";
			}
			return Encoding.UTF8.GetString(Convert.FromBase64String(string_0));
		}
	}
}
