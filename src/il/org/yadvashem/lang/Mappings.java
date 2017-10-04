package il.org.yadvashem.lang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Mappings {
	public node Root;
	private File input_file;
	private String Header;
		
	public Mappings(File file) {
		input_file = file;
	}
	
	public HashMap<String, node> build() 
	{
        try 
        {
        	HashMap<String, node> hash = new HashMap<String, node>();
            BufferedReader b = new BufferedReader(new FileReader(input_file));
            String readLine; // always skip the first line
            Header = b.readLine();
            if(Header == null)
            	return null;
            
            Root = null;
            
            while ((readLine = b.readLine()) != null) 
            {
//                System.out.println(readLine);
                String ary[] = readLine.split("\t");
                if(ary != null && ary.length > 4)
                {
                	String id = ary[0];
                	String title = ary[2];
                	String level = ary[1];
                	String type = ary[4];
                	node nd = hash.get(id);
                	String parentS = ary[3];
                	node parent = null;
                	if(parentS != null && !parentS.isEmpty()) {
                		parent = hash.get(parentS);
                		if(parent == null) {
                			System.out.println("Exception: could not find parent " + parentS);
                			return null;
                		}
                	}
                	String lang = ary[5];
                	if(nd == null) {
	                	nd = new node(id, lang, parent, title, level, type);
	                	hash.put(id, nd);
                	}
                	else nd.AddLanguage(lang); 
                	if(Root == null && parent == null)
                		Root = nd;
                }
            }
            return hash;
        } 
	    catch (FileNotFoundException e) {
	    	System.out.println(e.getMessage());
	    	return null;
		} 
        catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }
	
	public void PrintHeader(BufferedWriter writer) 
	{
		try 		
		{
			writer.write(Header + "\n");		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());			
		}	
	}
}
