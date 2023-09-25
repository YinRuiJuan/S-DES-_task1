import java.util.Arrays;

public class SDES_ASCII {
public static String key1;
public static String key2;
public static int[] IP = new int[] { 2, 6, 3, 1, 4, 8, 5, 7 };
public static int[] EP = new int[] { 4, 1, 2, 3, 2, 3, 4, 1 };
public static int[] P10 = new int[] { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
public static int[] P8 = new int[] { 6, 3, 7, 4, 8, 5, 10, 9 };
public static int[] P4 = new int[] { 2, 4, 3, 1 };
public static int[] IP_1 = new int[] { 4, 1, 3, 5, 7, 2, 8, 6 };
public static String[][] S1_box = new String[][] {
	{ "01", "00", "11", "10" }, { "11", "10", "01", "00" },
	{ "00", "10", "01", "11" }, { "11", "01", "00", "10" } };
public static String[][] S2_box = new String[][] {
	{ "00", "01", "10", "11" }, { "10", "11", "01", "00" },
	{ "11", "00", "01", "10" }, { "10", "01", "00", "11" } };


public static String substitue(String str, int[] P) { //进行置换操作    
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < P.length; i++) {
		sb.append(str.charAt((P[i]) - 1));
	}
	return new String(sb);
}

public static String xor(String str, String key) { //进行异或操作
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < str.length(); i++) {
		if (str.charAt(i) == key.charAt(i)) {
			sb.append("0");
		} else {
			sb.append("1");
		}
	}
	return new String(sb);
}

public static String searchSbox(String str, int n) { //S盒的查找
	StringBuilder sb = new StringBuilder();
	sb.append(str.charAt(0));
	sb.append(str.charAt(3));
	String ret = new String(sb);
	StringBuilder sb1 = new StringBuilder();
	sb1.append(str.charAt(1));
	sb1.append(str.charAt(2));
	String ret1 = new String(sb1);
	String retu = new String();
	if (n == 1) {
		retu = S1_box[Integer.parseInt(ret, 2)][Integer.parseInt(ret1, 2)];
	} else {
		retu = S2_box[Integer.parseInt(ret, 2)][Integer.parseInt(ret1, 2)];
	}
	return retu;
}

public static void getkey(String key) { //获得key1和key2
	String mainkey = substitue(key, P10);
	String Ls11 = mainkey.substring(0, 5);
	Ls11 = move(Ls11, 1);//移位后
	String Ls12 = mainkey.substring(5, 10);
	Ls12 = move(Ls12, 1);//移位后
	key1 = Ls11 + Ls12;
	key1 = substitue(key1, P8);
	//System.out.println("key1= " + key1);
	String Ls21 = move(Ls11, 2);
	String Ls22 = move(Ls12, 2);
	key2 = Ls21 + Ls22;
	key2 = substitue(key2, P8);
	//System.out.println("key2= " + key2);
}

public static String move(String str, int n) { //进行移位操作，只能1位或者2位
	char[] ch = str.toCharArray();
	char[] copy_ch = new char[5];
	for (int i = 0; i < ch.length; i++) {
		int a = ((i - n) % ch.length);
		if (a < 0) {
			if (n == 1) {
				copy_ch[ch.length - 1] = ch[i];
			}
			if (n == 2) {
				if (i == 0) {
					copy_ch[ch.length - 2] = ch[i];
				} else {
					copy_ch[ch.length - 1] = ch[i];
				}

			}
		} else {
			copy_ch[a] = ch[i];
		}
	}
	return new String(copy_ch);
} 
public static String encrypt1(String plaintext, String key) { //加密主体
    /* 
	System.out.println("-----请输入要加密的信息(8位)------");
	Scanner sc = new Scanner(System.in);
	String plaintext = sc.nextLine();
    */
	getkey(key);
	String[] plain=textToBits(plaintext, 8);//转化成为二进制的8个一组组成的字符串数组
	String ciphertrue="";
	for(int i=0;i<plain.length;i++){
		//System.out.println(plain[i]);

		plain[i] = substitue(plain[i], IP);
		String L0 = plain[i].substring(0, 4);
		String R0 = plain[i].substring(4, 8);
		String R0E = substitue(R0, EP);
		R0E = xor(R0E, key1);
		String S1 = R0E.substring(0, 4);
		String S2 = R0E.substring(4, 8);
		S1 = searchSbox(S1, 1);
		S2 = searchSbox(S2, 2);
		String SS = S1 + S2;
		String f1 = substitue(SS, P4);
		String L1 = R0;
		String R1 = xor(f1, L0);
		//这里求出L1,R1
		//-----------------第二轮-------------
		String R11 = substitue(R1, EP);
		R11 = xor(R11, key2);
		S1 = R11.substring(0, 4);
		S2 = R11.substring(4, 8);
		S1 = searchSbox(S1, 1);
		S2 = searchSbox(S2, 2);
		SS = S1 + S2;
		String f2 = substitue(SS, P4);
		String L2 = xor(f2, L1);
		String R2 = R1;
		//这里求出L2,R2
		String ciphertext = L2 + R2;
		ciphertext = substitue(ciphertext, IP_1);
		ciphertrue+=ciphertext;
		System.out.println("ciphertrue:"+ciphertrue);
	}
    String c=bitsToText(ciphertrue, 8);
	return c;
	
}
public static String decrypt1(String ciphertext, String key) {//解密主体
    getkey(key);
	String[] cipher=textToBits(ciphertext, 8);
	String plaintrue="";
	for(int j=0;j<cipher.length;j++){
		cipher[j] = substitue(cipher[j], IP);
		String L0 = cipher[j].substring(0, 4);
		String R0 = cipher[j].substring(4, 8);
		String R0E = substitue(R0, EP);
		R0E = xor(R0E, key2);
		String S1 = R0E.substring(0, 4);
		String S2 = R0E.substring(4, 8);
		S1 = searchSbox(S1, 1);
		S2 = searchSbox(S2, 2);
		String SS = S1 + S2;
		String f1 = substitue(SS, P4);
		String L1 = R0;
		String R1 = xor(f1, L0);
		//这里求出L1,R1
		//-----------------第二轮-------------
		String R11 = substitue(R1, EP);
		R11 = xor(R11, key1);
		S1 = R11.substring(0, 4);
		S2 = R11.substring(4, 8);
		S1 = searchSbox(S1, 1);
		S2 = searchSbox(S2, 2);
		SS = S1 + S2;
		String f2 = substitue(SS, P4);
		String L2 = xor(f2, L1);
		String R2 = R1;
		//这里求出L2,R2
		String plaintext = L2 + R2;
		plaintext = substitue(plaintext, IP_1);
		plaintrue+=plaintext;
		System.out.println("plaintrue:"+plaintrue);
	}
    String c=bitsToText(plaintrue, 8);
	return c;
}

private static String[] textToBits(String text, int blockSize) {
	int[] bits = new int[text.length() * blockSize];//每一个字符转换成8个bit（ascii码是这样编码的）
	for (int i = 0; i < text.length(); i++) {//对于每一个字符
		char c = text.charAt(i);//提取出每一个字符
		for (int j = 0; j < blockSize; j++) {
			bits[i * blockSize + j] = (c >> (blockSize - 1 - j)) & 1;
		}
	}
	//return bits;//这是每一个字符都转化成为了8个bits之后拼接在一起的
	String arrayString = Arrays.toString(bits);
	arrayString = arrayString.replace("[", "").replace("]", "").replace(", ", "");
	String[] str=new String[arrayString.length() /8];//每八个化成一个数组
	for(int i=0;i<str.length;i++){
		String c=arrayString.substring(8*i, 8*i+8);
		str[i]=c;
	}
	return str;
}
private static String bitsToText(String str, int blockSize) {
	
	int[] bits = new int[str.length()];

	for (int i = 0; i < str.length(); i++) {
		bits[i] = Character.getNumericValue(str.charAt(i));
	}

	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < bits.length; i += blockSize) {
		int value = 0;
		for (int j = 0; j < blockSize; j++) {
			value |= bits[i + j] << (blockSize - 1 - j);
		}
		sb.append((char) value);
	}
	return sb.toString();
}

}
