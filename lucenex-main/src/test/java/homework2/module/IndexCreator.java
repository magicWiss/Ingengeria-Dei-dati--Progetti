package homework2.module;

import org.apache.lucene.codecs.Codec;

public interface IndexCreator 
{
	public void createIndex(Codec codec) throws Exception;	
}
