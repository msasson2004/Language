package il.org.yadvashem.lang;
import java.io.File;
import java.io.FileWriter;
import com.opencsv.CSVWriter;

public class App {
	public static void main(String[] args) {
		// First get the directory name from argument
    	String folder_input, result_file, out_folder;
    	File[] listOfFiles;

    	if(args.length > 0) {
    		folder_input = args[0];
        	out_folder = folder_input + "_langFix";
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
    		// listOfFiles contains all files found in the input directory.
	    	for(int i=0;i < listOfFiles.length;i++) 
	    	{
	    		if (listOfFiles[i].isFile()) // if element is a file 
	    		{	   
	                String file_name = listOfFiles[i].getName();
	            	result_file = out_folder + "/" + file_name;        	
			        System.out.println("working on: " + file_name);
			        Mappings map = new Mappings();
			        String err = map.AddFile(listOfFiles[i]);
			    	if(err != null && !err.isEmpty())
			    	{
			    		System.out.println("Error encountered. Stopping");
			    		break;
			    	}
			    	CSVWriter writer = new CSVWriter( new FileWriter( result_file ));
			    	map.Print(writer);
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

