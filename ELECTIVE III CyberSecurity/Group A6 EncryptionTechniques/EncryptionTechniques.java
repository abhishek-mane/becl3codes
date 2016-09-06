import java.awt.Point;
import java.util.Scanner;

class CeaserCipher{
	
	protected static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static String prepareText(String msg){
		return msg.toUpperCase().replaceAll("[^A-Z]", "");
	}
	
	public static String encrypt(String plainText, int shiftKey)
	{
		plainText = plainText.toUpperCase();
		String cipherText = "";
		for (int i = 0; i < plainText.length(); ++i)
		{
			int charPosition = ALPHABET.indexOf(plainText.charAt(i));
			int keyVal = (shiftKey + charPosition) % 26;
			char replaceVal = ALPHABET.charAt(keyVal);
			cipherText += replaceVal;
		}
		return cipherText;
	}
	public static String decrypt(String cipherText, int shiftKey)
	{
		cipherText = cipherText.toUpperCase();
		String plainText = "";
		for (int i = 0; i < cipherText.length(); ++i)
		{
			int charPosition = ALPHABET.indexOf(cipherText.charAt(i));
			int keyVal = (charPosition - shiftKey) % 26;
			if (keyVal < 0)
				keyVal = ALPHABET.length() + keyVal;
				
			char replaceVal = ALPHABET.charAt(keyVal);
			plainText += replaceVal;
		}
		return plainText;
	}
}

class PlayFairCipher extends CeaserCipher{
	
	private static char[][] charTable;
	private static Point[] positions;

	public static void createTable(String key, boolean containsJ) {
		charTable = new char[5][5];
		positions = new Point[26];
		
		String s = prepareText(key + ALPHABET);
		s = containsJ? s.replace("Q", ""): s.replace("J", "I");

		int len = s.length();
		for (int i = 0, k = 0; i < len; i++) {
			char c = s.charAt(i);
			if (positions[c - 'A'] == null) {
				charTable[k / 5][k % 5] = c;
				positions[c - 'A'] = new Point(k % 5, k / 5);
				k++;
			}
		}
	}
 
	public static String encrypt(String s) {
		StringBuilder sb = new StringBuilder(s);

		for (int i = 0; i < sb.length(); i += 2) {
			
			if (i == sb.length() - 1)
				sb.append(sb.length() % 2 == 1 ? 'X' : "");
 
			else if (sb.charAt(i) == sb.charAt(i + 1))
				sb.insert(i + 1, 'X');
		}
		return codec(sb, 1);
	}

	public static String decrypt(String s) {
		return codec(new StringBuilder(s), 4);
	}

	private static String codec(StringBuilder text, int direction) {
		int len = text.length();
		for (int i = 0; i < len; i += 2) {
			char a = text.charAt(i);
			char b = text.charAt(i + 1);
 
			int row1 = positions[a - 'A'].y;
			int row2 = positions[b - 'A'].y;
			int col1 = positions[a - 'A'].x;
			int col2 = positions[b - 'A'].x;

			if (row1 == row2) {
				col1 = (col1 + direction) % 5;
				col2 = (col2 + direction) % 5;
 
			} else if (col1 == col2) {
				row1 = (row1 + direction) % 5;
				row2 = (row2 + direction) % 5;

			} else {
				int tmp = col1;
				col1 = col2;
				col2 = tmp;
			}
 
			text.setCharAt(i, charTable[row1][col1]);
			text.setCharAt(i + 1, charTable[row2][col2]);
		}
		return text.toString();
	}
}

public class EncryptionTechniques{
	
	public static void main(String[] args) {
		
		new EncryptionTechniques();
		
		Scanner s = new Scanner(System.in);
		String msg, key, cipherText; int shiftVal;
		
		// take input
		System.out.print("\nEnter message to encrypt : ");
		msg = s.nextLine();
		msg = PlayFairCipher.prepareText(msg);
		
		System.out.print("\nEnter key for encryption (PlayFair Cipher) : ");
		key = s.nextLine();
		key = PlayFairCipher.prepareText(key);
		
		System.out.print("\nEnter shift value for encryption (Ceaser Cipher) : ");
		shiftVal = s.nextInt();
		s.close();
		
		// Ceaser cipher
		System.out.println("\n************ Ceaser Cipher ************");
		cipherText = PlayFairCipher.encrypt(msg, shiftVal);
		System.out.println("Encrypted Text : " + cipherText);
		System.out.println("Decrypted Text : " + PlayFairCipher.decrypt(cipherText, shiftVal));
		
		// PlayFair Cipher
		System.out.println("\n************ PlayFair Cipher ************");
		PlayFairCipher.createTable(key, msg.contains("J"));
		cipherText = PlayFairCipher.encrypt(msg);
		System.out.println("Encrypted Text : " + cipherText);
		System.out.println("Decrypted Text : " + PlayFairCipher.decrypt(cipherText));
	}
}