package homework1.module;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is specialized in the creation of the index
 * It iterates in the corpus folder
 * For each document it uses FileParser to extract Titolo and Contenuto
 * It creates Luceene Documents starting from the data extracted
 * @author Wissel
 *
 */
public class IndexCreator 
{
	
	private static final String indexPath="";	//where to save Luceene Index
	private static final String corpusPath="C:/Users/Wissel/OneDrive/Desktop/università/MAGISTRALE/Ing.Dati/Homeworks/Homework2/corpus";
	private static final String titleField="titolo";
	private static final String contentField="contenuto";
	
	public IndexCreator()
	{
		
	}
	
	/**
	 * Creation of index giving the path of corpus 
	 * @param path
	 */
	public void createIndex()	
	{
		File dir=new File(corpusPath);
		FileParser fileParser=new FileParser();
		for (File file: dir.listFiles())
		{
			if (file.getName().contains(".txt"))
			   {
			      Map<String,String> field2text=new HashMap<>();
			      if (field2text!=null)
			      {
			    	  field2text=fileParser.textParser(file);
				      String titolo=field2text.get(titleField);
				      String contenuto=field2text.get(contentField);
				      
				      System.out.println("nomeFile:"+file.getName());
				      System.out.println("titolo:"+titolo);
				      System.out.println("contenuto"+contenuto);
				        
			      }
			      
			      
			   }
			
		}
	}
	
}
