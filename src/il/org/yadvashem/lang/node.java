package il.org.yadvashem.lang;
import java.util.List;
import java.io.BufferedWriter;
import java.util.ArrayList;


public class node {
	public String ID;
	public String Title;
	public String Level;
	public String Type;
	public node Parent;
	public List<String> Languages;
	public List<node> Children;
	
	public node(String id, String language, node parent, String title, String level, String type) {
		ID = id;
		Parent = parent;
		Title = title;
		Level = level;
		Type = type;
		Languages = new ArrayList<String>();
		Languages.add(language);
		Children = new ArrayList<node>();
		CheckParentLanguages(language);	
		if(parent != null)
			parent.AddChild(this);
	}
	
	public void AddLanguage(String lang) {
		Languages.add(lang);
		CheckParentLanguages(lang);	
	}
	
	public void CheckParentLanguages(String lang) {
		node parent = Parent;
		while(parent != null) {
			if(!parent.Languages.contains(lang)) {
				System.out.println("missing language " + lang + " id: " + parent.ID);
				parent.Languages.add(lang);
			}
			parent = parent.Parent;
		}
	}
	
	public void AddChild(node child) {
		Children.add(child);
	}
	
	public void Print(BufferedWriter writer) {
		try {			
			for(int i=0;i<Languages.size();i++) {
				String buf = ID + "\t" + Level + "\t" + Title + "\t";
				if(Parent == null) buf += "\t";
				else buf += Parent.ID + "\t";
				buf += Type + "\t" + Languages.get(i) + "\n";
				writer.write(buf);
			}
			for(int i=0;i<Children.size();i++) 
			{
				node child = Children.get(i);
				child.Print(writer);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());			
		}
	}
}
