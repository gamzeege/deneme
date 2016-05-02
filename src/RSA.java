import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;



public class RSA {
	private BigInteger p;
	private BigInteger q;
	private BigInteger N;
	private BigInteger phi;
	private BigInteger e;
	private BigInteger d;
	private int bitlength = 1024;
	private int blocksize = 256;
	//blocksize in byte
	private Random r;
	public RSA() {
		r = new Random();
		p = BigInteger.probablePrime(bitlength, r);
		q = BigInteger.probablePrime(bitlength, r);
		N = p.multiply(q);
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		e = BigInteger.probablePrime(bitlength/2, r);
		while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0 ) {
			e.add(BigInteger.ONE);
		}
		d = e.modInverse(phi);
	}
	public RSA(String N, String e, String d) {
		
		this.N = new BigInteger(N);
		this.e = new BigInteger(e);
		this.d = new BigInteger(d);
	}
	public RSA(BigInteger e, BigInteger d, BigInteger N) {
		this.e = e; // public
		this.d = d; // private
		this.N = N;
	}
	public BigInteger getE(){
		return this.e;
	}
	public BigInteger getN(){
		return this.N;
	}
	public BigInteger getD(){
		return this.d;
	}
	public static void main(String[] args){
		String x = "hubele";
		BigInteger e=new BigInteger("9015065445596874281200751166670083701104595200584756138659507727528399696731131787291687932929275295842850177651233032504035489132176926473756646414921777");
		BigInteger d=new BigInteger("9605549867890186247792235749206060230050552889736177566915202567220261271692567193707673570711053133065632475974252833854571141452497657937204778029183906011376731306601467376329815209125024027416501116206071127856130055449606155786118842085149052197190914438669350075608827990893439650595549261938847064813468786833499672905235381864725641154830289731057154251460004647422773945181761008885318588719902399653773100108392318051562574917169322758066852767823317937185158610783083876768276343688930442285783020347681372903181141080533493025931646688500312462048835459654784703598867700099729448252466702656689003546993");
		BigInteger N=new BigInteger("14913634252337896247012436698881839482321672335621549436270410723992175111427774421763053154786640945868292209918326217952945511134467631349105160793280570372669797686317381330084878895742075855354080571894937674028693772976797584389243810857365623941916099823511798405724702037675325081721679597826665061291811641142114638281933759077546728657959964410690732583136224111602608984099782003975307912913978766335107601191102296768562871815097866429728419066518742439139790901344634177277421567434167479505242750059293538621997284702504891232293406280268903281159228257415756742929379478144000955857571096310747694534109");
		RSA rsa = new RSA(e,d,N);
		System.out.println(new String(rsa.encrypt(rsa.decrypt(x.getBytes()))));
	}
	public static void main2(String[] args) throws IOException {
		String x = "12";
		System.out.println((byte)Integer.parseInt(x));
		
		// TODO Auto-generated method stub
		//for(int i =0; i<5; i++){
			RSA rsa = new RSA();
		//	System.out.println("E:" + rsa.getE().toString());
		//	System.out.println("D:" + rsa.getD().toString());
		//	System.out.println("N:" + rsa.getN().toString());
	//	}
		DataInputStream in=new DataInputStream(System.in);
		String teststring ;
		System.out.println("Enter the plain text:");
		teststring=in.readLine();
		System.out.println("Encrypting String: " + teststring.length());
		System.out.println(Arrays.toString(teststring.getBytes()));
		System.out.println(teststring.getBytes(Charset.forName("UTF-8")));
		System.out.println(teststring.getBytes(Charset.forName("UTF-8")));
		System.out.println("String in Bytes: " + bytesToString(teststring.getBytes(Charset.forName("UTF-8"))));
		// encrypt
		byte[] encrypted = rsa.decrypt(teststring.getBytes(Charset.forName("UTF-8")));
		System.out.println(encrypted.toString());
		System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));
		System.out.println(encrypted.length);
		// decrypt
	
		String s = bytesToHex(encrypted);
		System.out.println(encrypted);
		System.out.println(s);
		System.out.println(hexStringToByteArray(s));
		byte[] decrypted = rsa.encrypt(hexStringToByteArray(""));

		System.out.println(decrypted.toString().getBytes(Charset.forName("UTF-8")));
		System.out.println("Decrypted String in Bytes: " +  bytesToString(decrypted));
		System.out.println("Decrypted String: " + decryptedString(decrypted));
		
	}
	private static String bytesToString(byte[] encrypted) {
		String test = "";
		for (byte b : encrypted) {
			test += Byte.toString(b);
		}
		return test;
	}
	//Encrypt message
	public byte[] encrypt(byte[] message) {
		return (new BigInteger(message)).modPow(e, N).toByteArray();
	}
	// Decrypt message
	public byte[] decrypt(byte[] message) {
		return (new BigInteger(message)).modPow(d, N).toByteArray();
	}
	public static String decryptedString(byte[] message){
        return (new String (message));
    }
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
	    }
	    return data;
	}

}
