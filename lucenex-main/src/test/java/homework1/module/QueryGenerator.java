package homework1.module;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceCharFilterFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class QueryGenerator 
{
	private static final String indexPathTitle="homework1/index/title";	//where to save Luceene Index

	private static final String indexPathContent="homework1/index/content";	//where to save Luceene Index
	private static final String titleField="titolo";
	private static final String contentField="contenuto";
	private static final String phraseQueryDelimiter="'";
	private String index;
	private String what;
	private boolean isPhraseQuery;
	public QueryGenerator(String whereToSearch, String whatToSearch)
	{
		this.index=whereToSearch;
		this.what=whatToSearch;
		this.isPhraseQuery=this.what.contains(phraseQueryDelimiter);
	}
	
	
	private Path getPath() 
	{
		if (this.index==titleField)
		{
			return Paths.get(indexPathTitle);
		}
		return Paths.get(indexPathContent);
	}
	private String prepData()
	{
		String out=this.what.replace("'", "");
		out=out.trim();
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

		System.out.println("Running a query on Content index");
		Analyzer a = CustomAnalyzer.builder().withTokenizer(WhitespaceTokenizerFactory.class)
				
				.addTokenFilter(StopFilterFactory.class)
				.addTokenFilter(LowerCaseFilterFactory.class)
				.addTokenFilter(WordDelimiterGraphFilterFactory.class)
				.build();
		QueryParser queryParser = new QueryParser(contentField, a);
		Query parse=queryParser.parse(this.what);
		return parse;
        
		
	}

	

	private Query createTitleQuery() throws Exception
	{
		System.out.println("Running a query on title index");
		Analyzer a1 = CustomAnalyzer.builder().withTokenizer(WhitespaceTokenizerFactory.class)

				.addTokenFilter(StopFilterFactory.class)
				.addTokenFilter(LowerCaseFilterFactory.class)
				.build();
		QueryParser queryParser = new QueryParser(titleField, a1);
		Query parse=queryParser.parse(this.what);
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
	private void run(IndexSearcher searcher, Query toRun) throws IOException 
	{
		 boolean explain=true;
		 TopDocs hits = searcher.search(toRun, 10);
		 if (hits.scoreDocs.length==0)
		 {
			 System.out.println("no match");
			 return ;
		 }
	        for (int i = 0; i < hits.scoreDocs.length; i++) {
	            ScoreDoc scoreDoc = hits.scoreDocs[i];
	            Document doc = searcher.doc(scoreDoc.doc);
	            System.out.println("doc"+scoreDoc.doc + ":"+ doc.get("titolo") + " (" + scoreDoc.score +")");
	            if (explain) {
	                Explanation explanation = searcher.explain(toRun, scoreDoc.doc);
	                System.out.println(explanation);
	            }
	        }
		
	}
	
	public void runQuery() throws Exception
	{	
		Query toRun=this.createQuery();
		try (Directory directory = FSDirectory.open(this.getPath())) {
            
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                this.run(searcher, toRun);
            } finally {
                directory.close();
            }

        }
		
	}
	
	
	 
}
