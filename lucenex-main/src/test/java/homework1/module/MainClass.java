package homework1.module;

import java.util.Scanner;

import org.apache.lucene.codecs.simpletext.SimpleTextCodec;

public class MainClass 
{
	public static void main(String[] args) throws Exception 
	{
		//creazione indidice
		IndexCreator ind=new IndexCreator();
		ind.createIndex(new SimpleTextCodec());
		
		
		
		//acquisizione query
		String whereToQuery="";
		String query="";
		System.out.println("Inser query (es. titolo, how to become a data sciensce): ");
		Scanner in = new Scanner(System.in);
		String s=in.nextLine();
		//formattazione query
		String [] input=new String[2];
		input=formatInput(s);
		System.out.println("Dove: "+input[0]);
		System.out.println("Cosa:"+ input[1]);
		QueryGenerator qg= new QueryGenerator(input[0],input[1]);
		qg.runQuery();
		
		
		
		
		 
        
		
	}

	private static String[] formatInput(String in) {
		
		
		return in.split(",");
	}
}
