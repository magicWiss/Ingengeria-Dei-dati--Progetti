package homework2.module;

import java.util.Scanner;

import org.apache.lucene.codecs.simpletext.SimpleTextCodec;

public class MainClass 
{
	public static void main(String[] args) throws Exception 
	{
		//creazione indidice
		
		IndexCreator ind=new IndexCreatorOneIndex();
		long startTimeIndexing = System.nanoTime();
		
		
		ind.createIndex(new SimpleTextCodec());
		long endTimeIndexing = System.nanoTime();

		long durationIndexing = (endTimeIndexing - startTimeIndexing)/1000000;  
		
		
		
		//acquisizione query
		String whereToQuery="";
		String query="";
		System.out.println("Inser query (es. titolo, how to become a data sciensce): ");
		Scanner in = new Scanner(System.in);
		String s=in.nextLine();
		//formattazione query
		String [] input=new String[2];
		input=formatInput(s);
		
		QueryGenOneIndex qg= new QueryGenOneIndex(input[0],input[1]);
		//queryfortitle qg=new queryfortitle(input[0]);
		long startTimequerying = System.nanoTime();
		
		
		
		

		
		float[] out=qg.runQuery();
		long endTimequerying= System.nanoTime();
		long durationquerying = (endTimequerying - startTimequerying)/1000000; 
		
		
		System.out.println("STATISTICS");
		StatsCreator sts=new StatsCreator();
		sts.getStats();
		System.out.println("Duration index: "+ durationIndexing+"ms");
		System.out.println("Query:"+input[0]+" -> "+ input[1]);
		System.out.println("Docs retrived:"+ out[0]);
		System.out.println("Avg score:"+ out[1]);
		
		System.out.println("Duration querying:" +  durationquerying+"ms");
		
		
		
		
		
		 
        
		
	}

	private static String[] formatInput(String in) {
		
		
		return in.split(",");
	}
}
