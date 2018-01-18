package il.org.yadvashem.lang;
import java.lang.Exception;

public class FileFormatException extends Exception {
	
	public FileFormatException() { }
	
	public FileFormatException(String msg) 
	{ 
		super(msg);
	}
	
}
