package homework1.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileParserOnlyContent implements FileParser
{

	private static final String contentField="contenuto";
	public FileParserOnlyContent()
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
	@Override
	public String textParser(File file)
	{

		try
		{
			FileReader fr=new FileReader(file);
			BufferedReader br= new BufferedReader(fr);
			StringBuffer sb= new StringBuffer();
			String line="";
			String contenuto="";
			while ((line=br.readLine())!=null)
			{

				contenuto=contenuto.concat(line);

			}
			fr.close();

			return contenuto;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}

	}
}
