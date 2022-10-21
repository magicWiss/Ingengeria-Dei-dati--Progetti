package homework2.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface FileParser 
{
	
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
	public String textParser(File file);
	
		
		
		
		
		
		
	
}
