package il.org.yadvashem.lang;
import java.util.List;
import com.opencsv.CSVWriter;
import java.util.ArrayList;


public class node {
	public String ID;
	public node Parent;
	public String ParentID;
	public List<String> Languages;
	public List<node> Children;
	public List<String[]> Data;
	
	public node(String [] row) {
		ID = row[0].replace("\"", "");
		Parent = null;
		
		CreateMembers(row);
		
		if(row.length > 2) {
			String lang = row[1].toLowerCase().replace("\"", "");
			if(lang.equals(new String("language"))) {
				lang = row[2].replaceAll("\"", "");
				Languages.add(lang);
			}
			else if(lang.equals(new String("parent id"))) {
				String pId = null;
				if(row[2] != null && !row[2].isEmpty())
					pId = row[2].replace("\"", "");
				else pId = "root";
				ParentID = pId;
			}
		}
	}
	
	public void AddData(String [] row) {
		Data.add(row);
		if(row.length > 2) {
			String lang = row[1].toLowerCase().replace("\"", "");
			if(lang.equals(new String("language"))) {
				lang = row[2].replaceAll("\"", "");
				Languages.add(lang);
			}
			else if(lang.equals("parent id")) {
				ParentID = row[2].replaceAll("\"", "");
			}
		}
	}
	
	private void CreateMembers(String [] row) {
		Data = new ArrayList<String[]>();
		Data.add(row);
		Languages = new ArrayList<String>();
		Children = new ArrayList<node>();		
	}
		
	public void CheckParentLanguages() {
		for(int i=0;i<Languages.size();i++) {
			String lang = Languages.get(i);		
			node parent = Parent;
			while(parent != null) {
				if(!parent.Languages.contains(lang)) 
				{
					parent.Languages.add(lang);
				}
				parent = parent.Parent;
			}
		}
	}
	
	public void AddChild(node child) {
		Children.add(child);		
	}
	
	public void Print(CSVWriter writer) {
		try 
		{			
			for(int i=0;i<Data.size();i++) 
			{
				String [] row = Data.get(i);
				if(row.length < 2)
					writer.writeNext(row);
				else if(row[1].replace("\"", "").toLowerCase().equals("language")) 
				{
					for(int j=0;j<Languages.size();j++) {
						String lang = Languages.get(j);
						row[2] = lang;
						writer.writeNext(row);
					}
					Languages.clear();					
				}
				else writer.writeNext(row);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());			
		}
	}
}
