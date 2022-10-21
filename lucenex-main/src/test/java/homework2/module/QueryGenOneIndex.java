package homework2.module;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class QueryGenOneIndex implements QueryGen
{
	private static final String indexPath="homework1/index";	//where to save Luceene Index
	
	private static final String titleField="titolo";
	private static final String contentField="contenuto";
	private static final String phraseQueryDelimiter="'";
	private String index;
	private String what;
	private boolean isPhraseQuery;
	public QueryGenOneIndex(String whereToSearch, String whatToSearch)
	{
		this.index=whereToSearch;
		this.what=whatToSearch;
		this.isPhraseQuery=this.what.contains(phraseQueryDelimiter);
	}
	
	private Path getPath() 
	{
		return Paths.get(indexPath);
	}
	
	private String prepData()
	{
		String out=this.what.replace("'", "");
		out=out.trim();
		out=out.replace("\n", "");
		return out;
		
	}
	private Query createPhraseQuery(Path path) 
	{
		this.what=this.prepData();
		System.out.println("Running a Phrase query");
		 PhraseQuery.Builder builder = new PhraseQuery.Builder();
		 String[] termsInQuery=this.what.split(" ");
		 for (String s : termsInQuery)
			 
		 {	System.out.println("current term"+ s);
			 builder.add(new Term(this.index, s));
		 }
		
		 
		 PhraseQuery pq = builder.build();
		 return pq;
		
		
	}
	

	

	private Query createContentQuery() throws Exception
	{

		System.out.println("Running a query on Content index:"+ this.what.trim());
		Analyzer a = CustomAnalyzer.builder().withTokenizer(WhitespaceTokenizerFactory.class)
				
				.addTokenFilter(StopFilterFactory.class)
				.addTokenFilter(LowerCaseFilterFactory.class)
				.addTokenFilter(WordDelimiterGraphFilterFactory.class)
				.build();
		QueryParser queryParser = new QueryParser(contentField, a);
		Query parse=queryParser.parse(this.what);
		System.out.println(parse.toString());
		return parse;
        
		
	}

	

	private Query createTitleQuery() throws Exception
	{
		System.out.println("Running a query on title index: "+ this.what+ " hh");
		
		Analyzer a1 = CustomAnalyzer.builder().withTokenizer(WhitespaceTokenizerFactory.class)

				.addTokenFilter(StopFilterFactory.class)
				.addTokenFilter(LowerCaseFilterFactory.class)
				.addTokenFilter(WordDelimiterGraphFilterFactory.class)
				.build();
		QueryParser queryParser = new QueryParser(titleField, a1);
		Query parse=queryParser.parse(this.what);
		System.out.println(parse.toString());
		return parse;

		
		
				
		
	}
	
	public Query createQuery() throws Exception
	{
		Query toRun;
		if(this.isPhraseQuery==true)
		{
			
			toRun=this.createPhraseQuery(this.getPath());
		}
		else if (this.index.equals(titleField))
		{
			toRun=this.createTitleQuery();
		}
		else
		{
			toRun=this.createContentQuery();
		}
		
		return toRun;
	}
	private float[] run(IndexSearcher searcher, Query toRun) throws IOException 
	{
		 float avgScore=0;
		 float [] out= new float[2];
		 out[0]=0;
		 out[1]=0;
		 TopDocs hits = searcher.search(toRun, 10);
		 if (hits.scoreDocs.length==0)
		 {
			 System.out.println("no match");
			 return out;
		 }
	        for (int i = 0; i < hits.scoreDocs.length; i++) 
	        {
	            ScoreDoc scoreDoc = hits.scoreDocs[i];
	            avgScore+=scoreDoc.score;
	            Document doc = searcher.doc(scoreDoc.doc);
	            System.out.println("doc"+scoreDoc.doc + ":"+ doc.get("titolo") + " (" + scoreDoc.score +")");
	            
	            }
	        float totalRetrived=(float)hits.scoreDocs.length;
	        avgScore=avgScore/totalRetrived;
	        out[0]=totalRetrived;
	        out[1]=avgScore;
	        return out;
	        
		
	}
	@Override
	public float[] runQuery() throws Exception
	{	float[]out=new float[2];
		Query toRun=this.createQuery();
		try (Directory directory = FSDirectory.open(this.getPath())) {
            
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                out=this.run(searcher, toRun);
            } finally {
                directory.close();
            }

        }
		return out;
		
	}
}
