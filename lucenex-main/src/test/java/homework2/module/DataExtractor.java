package homework2.module;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface DataExtractor 
{
	public Map<String,String> extractDataFromTextFile(File file);
}

