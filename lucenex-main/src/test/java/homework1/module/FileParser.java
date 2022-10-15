package homework1.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileParser 
{
	private static final String titleField="titolo";
	private static final String contentField="contenuto";
	public FileParser()
	{
		
	}
	/**
	 * This method parses the file.
	 * Opens the file defined in fileName
	 * Extracts the Title and Content
	 * Return a hashMap <"title",title of doc>,<"content",content in doc>
	 * @param fileName->path completo del file
	 * @return hashMap <key,val> 
	 * key->field type (titolo, contenuto), 
	 * value->text in doc 
	 */
	public Map<String,String> textParser(File file)
	{
		Map<String,String> field2Text=new HashMap<>();
		try
		{
			FileReader fr=new FileReader(file);
			BufferedReader br= new BufferedReader(fr);
			StringBuffer sb= new StringBuffer();
			String line="";
			String contenuto="";
			String titolo="";
			int count=0;		//conta il numero di righe
			while ((line=br.readLine())!=null)
			{
				if (count==0)
				{
					titolo=line;
					count++;
				}
				else
				{
					contenuto=contenuto.concat(line);
					count++;
					
				}
			}
			fr.close();
			field2Text.put(titleField, titolo);
			field2Text.put(contentField, contenuto);
			return field2Text;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		
		
		
		
		
	}
}
