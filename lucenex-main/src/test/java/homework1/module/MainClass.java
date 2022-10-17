package homework1.module;

import org.apache.lucene.codecs.simpletext.SimpleTextCodec;

public class MainClass 
{
	public static void main(String[] args) throws Exception 
	{
		IndexCreator ind=new IndexCreator();
		ind.createIndex(new SimpleTextCodec());
	}
}
