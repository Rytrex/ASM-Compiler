package scanner;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TokenTester
{
	public static void main(String[] args) 
	{
		//Takes in a file
		String filename = "/home/vod/workspace/Compiler/src/scanner/simplest.pas";
		//Declare and try to put the file into FileInputStream
		FileInputStream fis = null;
		try {
			fis = new FileInputStream( filename);
		} catch (Exception e ) { e.printStackTrace();}
		//Put fis into InputStreamReader to read inputed file
		InputStreamReader isr = new InputStreamReader( fis);
		//Declare scanner and token
		MyScanner scanner = new MyScanner( isr);
		Token aToken = null;
		//Print out all token types in the file
		do
		{
			try {
				aToken = scanner.nextToken();
			}
			catch( Exception e) { e.printStackTrace();}
			System.out.println("The token returned was " + aToken);
		} while( aToken != null);
	}


}