package homework1.module;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataExtractorTitleContent implements DataExtractor
{
	private FileParser fileParser;
	public DataExtractorTitleContent()
	{
		this.fileParser= new FileParserOnlyContent();
	}
	
	private String extractTitle(File file)
	{
		
		return file.getName().replaceFirst("[.][^.]+$", "");
		
	}
	public Map<String,String> extractDataFromTextFile(File file)
	{
		Map<String,String> out= new HashMap<>();
		String content=this.fileParser.textParser(file);
	    String title=this.extractTitle(file);
	    out.put("titolo",title);
	    out.put("contenuto", content);
	    return out;
		
	}

}
