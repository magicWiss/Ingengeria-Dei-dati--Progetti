package homework2.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatsCreator 
{
	private static final String corpusPath="C:/Users/Wissel/OneDrive/Desktop/università/MAGISTRALE/Ing.Dati/Homeworks/Homework2/corpus";

	public StatsCreator ()
	{

	}

	public void getStats() throws Exception
	{
		String line;  
		int count = 0; 
		int total=0;
		Map <String,Integer> name2stats=new HashMap<>();
		File dir=new File(corpusPath);		//corpus path
		for (File file: dir.listFiles())
		{
			//if its a .txt file we extract the data
			if (file.getName().contains(".txt"))
			{
				count=0;

				//Opens a file in read mode  
				FileReader fr=new FileReader(file);
				BufferedReader br= new BufferedReader(fr);
				StringBuffer sb= new StringBuffer(); 

				//Gets each line till end of file is reached  
				while((line = br.readLine()) != null) {  
					//Splits each line into words  
					String words[] = line.split("");  
					//Counts each word  
					count = count + words.length;  
				}
				total+=count;
				name2stats.put(file.getName().replaceFirst("[.][^.]+$", ""), count);
				br.close();  


			}  


		} 
		
		System.out.println("Stats per document");
		for (String name: name2stats.keySet()) {
		    String key = name.toString();
		    String value = name2stats.get(name).toString();
		    System.out.println("'"+key+"'" + ": " + value+",");
		}
		
		System.out.println("/nGeneral stats");
		System.out.println("TOTAL WORD:"+ total);
		System.out.println("AVG words in doc"+ total/22);
		
		
		
	}  
}

