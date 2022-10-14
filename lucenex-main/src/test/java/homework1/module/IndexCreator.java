package homework1.module;
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
	String indexPath="";	//where to save Luceene Index
	
	public IndexCreator(String path)
	{
		this.indexPath=path;
	}
	
	/**
	 * Creation of index giving the path of corpus 
	 * @param path
	 */
	public void createIndex(String path)	
	{
		//to do
	}
	
}
