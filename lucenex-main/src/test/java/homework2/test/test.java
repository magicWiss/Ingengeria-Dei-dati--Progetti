package homework2.test;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import homework2.module.IndexCreator;
import homework2.module.IndexCreatorOneIndex;
import homework2.module.QueryGenOneIndex;

public class test 
{
	@Test
	public void testCreateTitleHowToBecomeQuery() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="titolo";
		String what="how to become";
		QueryGenOneIndex qg=new QueryGenOneIndex(what, what);
		float[] out=qg.runQuery();
		assert out[0]>3;
	}
	@Test
	public void testCreateTitleHowToBecomePhraseQuery() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="titolo";
		String what="'how to become'";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]==2;
	}
	
	@Test
	public void testCreateContentQueryComputerVision() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="contenuto";
		String what="computer vision";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]>2;		//retrives all machine learning docs
	}
	@Test
	public void testCreateTitleQueryComputerVision() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="titolo";
		String what="computer vision";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]==2;		//retrives all machine learning docs
	}
	
	@Test
	public void testCreateTitleQueryVision() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="titolo";
		String what=" vision";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]==2;			//retrives computer vision and infrared vision docs
	}
	
	@Test
	public void testCreateContentQueryCNNs() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="contenuto";
		String what="CNNs";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]==1;
	}
	@Test
	public void testCreateContentQueryCV() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="contenuto";
		String what="computer vision";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]>2;
	}
	@Test
	public void testCreateTitleQueryHowTo() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="titolo";
		String what="how to";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]>2;
	}
	
	@Test
	public void testCreateContentPhraseQueryCNNs() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="contenuto";
		String what="'CNNs'";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]==0;		//the word in doc is cnns, the phrase query does not find a match
	}
	@Test
	public void testCreateContentQueryAI() throws Exception 
	{
	
		IndexCreator ind=new IndexCreatorOneIndex();
		
		ind.createIndex(new SimpleTextCodec());
		String where="contenuto";
		String what="Artificial inteligent";
		QueryGenOneIndex qg=new QueryGenOneIndex(where, what);
		float[] out=qg.runQuery();
		assert out[0]>1;		//the word in doc is cnns, the phrase query does not find a match
	}
	
	
	
	 

	
}
