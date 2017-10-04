package il.org.yadvashem.lang;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.HashMap;

public class App {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	String folder_input, result_file, out_folder;
    	File[] listOfFiles;

    	if(args.length > 0) {
    		folder_input = args[0];
        	out_folder = folder_input + "_result";
        	File theDir = new File(out_folder);
        	
        	try 
        	{
        		if(!theDir.exists())
        			theDir.mkdir();
        		File folder = new File(folder_input);
        		listOfFiles = folder.listFiles();
        	}
        	catch(Exception e) {
        		System.out.println(e.getMessage());
        		return;
        	}
    	}
    	else {
    		System.out.println("Syntax: java App.class <input dir>");
    		return;
    	}

	
    	try 
    	{	
	    	for(int i=0;i < listOfFiles.length;i++) 
	    	{
	    		if (listOfFiles[i].isFile()) 
	    		{	   
	                String file_name = listOfFiles[i].getName();
	            	result_file = out_folder + "/" + file_name;        	
			        System.out.println("working on: " + file_name);
			    	Mappings map = new Mappings(listOfFiles[i]);
			    	HashMap<String, node> hash = map.build();	
			    	if(hash == null)
			    		break;
			    	BufferedWriter writer = new BufferedWriter( new FileWriter( result_file ));
			    	map.PrintHeader(writer);
			    	map.Root.Print(writer);
			    	writer.flush();
			    	writer.close();
	    		}
	    	}
	        System.out.println("Done");
	        System.exit(0);
		}
	    catch (Exception ex) {
			System.out.println(ex.getMessage());
		}	    	
	}
}

