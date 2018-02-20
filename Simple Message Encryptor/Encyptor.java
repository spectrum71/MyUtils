
public class Encyptor {
	public static String EncryptOne(String str, int factor) {
		String out="";
		for(int i=0;i<str.length();i++) {
			char j = (char)(str.charAt(i)+((i+1)*factor)%92);
			if(j>125) j-=92;
			else if(j<33) j+=92;
			out+=j;
		}
		return out;
	}
	public static String DencryptOne(String str, int factor) {
		String out="";
		for(int i=0;i<str.length();i++) {
			char j = (char)(str.charAt(i)-((i+1)*factor)%92);
			if(j>125) j-=92;
			else if(j<33) j+=92;
			out+=j;
		}
		return out;
	}
	public static String EncryptTwo(String str) {
		String out="";
		int factor = (str.charAt(0)+str.length())%92 - str.charAt(0);
		for(int i=0;i<str.length();i++) {
			char j = (char)(str.charAt(i)+((i+1)*factor)%92);
			if(j>125) j-=92;
			else if(j<33) j+=92;
			out+=j;
		}
		return out;
	}
	public static String DecryptTwo(String str) {
		String out="";
		int factor = (str.charAt(0)+str.length())%92 - str.charAt(0);
		for(int i=0;i<str.length();i++) {
			char j = (char)(str.charAt(i)-((i+1)*factor)%92);
			if(j>125) j-=92;
			else if(j<33) j+=92;
			out+=j;
		}
		return out;
	}

}
