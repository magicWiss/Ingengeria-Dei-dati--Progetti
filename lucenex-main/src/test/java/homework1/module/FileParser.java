package homework1.module;

import java.util.HashMap;
import java.util.Map;

public class FileParser 
{
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
	public Map<String,String> textParser(String fileName)
	{
		Map<String,String> field2Text=new HashMap<>();
		
		//to do
		return field2Text;
		
	}
}
