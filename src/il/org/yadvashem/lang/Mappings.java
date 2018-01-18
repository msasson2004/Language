package il.org.yadvashem.lang;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Mappings {
	public node Root;
	private String input_file;
	private String[] Header;
	public HashMap<String, node> hash;
    public ArrayList<node> Order;
	
	public Mappings() {
		hash = new HashMap<String, node>();
		Order = new ArrayList<node>();
	}
	
	public String AddFile(File file) {
		input_file = file.getAbsolutePath();

		try 
        {
        	Reader reader = Files.newBufferedReader(Paths.get(input_file));
            CSVReader csvReader = new CSVReader(reader);
        	
            String[] nextRecord;

            // always skip the first line
            Header = csvReader.readNext();            

            if(Header == null)
            	return "Header is missing";
            
            Root = null;
            node nd = null;
            while ((nextRecord = csvReader.readNext()) != null) 
            {
            	if(nextRecord.length == 0) 
            	{
            		if(nd == null)
            			throw new FileFormatException("Bad format file: " + input_file);
            		nd.AddData(nextRecord);
            	}
            	else 
            	{          
            		if(nd == null)
            			nd = new node(nextRecord);
            		else 
            		{
            			String id = nextRecord[0].replace("\"", "");
            			if(id.equals(nd.ID))
            				nd.AddData(nextRecord);
            			else
            			{ 
            				AddNode(nd);
            				nd = new node(nextRecord);
            			}            			 
            		}            		
            	}            		
            }
            AddNode(nd);
            csvReader.close();            
            return null;
        } 
	    catch (FileNotFoundException e) {
	    	String err = e.getMessage();
	    	System.out.println(err);
	    	return err;
		} 
	    catch (FileFormatException e) {
	    	String err = e.getMessage();
	    	System.out.println(err);
	    	return err;
		} 
        catch (IOException e) {
	    	String err = e.getMessage();
            System.out.println(err);
            return err;
        }
    }
	
	private void AddNode(node nd) 
	{
		if(nd.ParentID != null && !nd.ParentID.isEmpty())
		{
			if(nd.ParentID.equals(new String("root"))) {
				Root = nd;
			}
			else {
				node parent = hash.get(nd.ParentID);
				if(parent != null) {
					nd.Parent = parent;
					parent.AddChild(nd);
		        	nd.CheckParentLanguages();
				}
			}
		}
		Order.add(nd);
		// no else. If it is empty or null, ignore.
		Iterator<Entry<String, node>> it = hash.entrySet().iterator();
	    while (it.hasNext()) 
	    {
	        Map.Entry<String, node> pair = (Map.Entry<String, node>)it.next();
	        node child = pair.getValue();
	        // if it's root or parentId = null, skip. Otherwise if this child is a parent 
	        // node of nd, set it as nd parent
	        if(child != Root && child.ParentID != null && child.ParentID.equals(nd.ID) && child.Parent == null)
	        {
	        	child.Parent = nd;
	        	nd.AddChild(child);
	        	child.CheckParentLanguages();
	        }
	    }	
		
	    hash.put(nd.ID, nd);	    
	}
	
	public void Print(CSVWriter writer) 
	{
		try 		
		{
			writer.writeNext(Header);	
			for(int i=0;i<Order.size();i++) 
			{
				node nd = Order.get(i);
				nd.Print(writer);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());			
		}	
	}
}
