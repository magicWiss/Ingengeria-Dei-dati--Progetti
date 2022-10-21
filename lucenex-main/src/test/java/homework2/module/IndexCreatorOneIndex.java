package homework2.module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceCharFilterFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexCreatorOneIndex implements IndexCreator
{
	private Path path;
	private static final String indexPath="homework1/index";	//where to save Luceene Index

	
	private static final String corpusPath="C:/Users/Wissel/OneDrive/Desktop/università/MAGISTRALE/Ing.Dati/Homeworks/Homework2/corpus";
	private static final String titleField="titolo";
	private static final String contentField="contenuto";
	private  DataExtractor dataExtractor;
	
	public IndexCreatorOneIndex()
	{
		//for extracting data from txt file
		this.dataExtractor=new DataExtractorTitleContent();	
	}
	
	private Analyzer createTitleAnalyzer() throws IOException 
	{
		Analyzer a1 = CustomAnalyzer.builder().withTokenizer(WhitespaceTokenizerFactory.class)

				.addTokenFilter(StopFilterFactory.class)
				.addTokenFilter(LowerCaseFilterFactory.class)
				.addTokenFilter(WordDelimiterGraphFilterFactory.class)
				.build();
		
		return a1;
		
		
	}
	//creates a map <term, replacment>
		private Map<String, String> createMapOfSubTerms() 
		{
			Map<String,String> terms2replace=new HashMap<>();
			terms2replace.put("pattern", "\\[\\d*\\]");
			terms2replace.put("replacement", "");
			return terms2replace;
		}

	private Analyzer createContetAnalyzer() throws Exception
	{
		Map<String,String> patternReplacment=this.createMapOfSubTerms();
		Analyzer a = CustomAnalyzer.builder().withTokenizer(WhitespaceTokenizerFactory.class)
				.addCharFilter(PatternReplaceCharFilterFactory.class, patternReplacment)
				.addTokenFilter(StopFilterFactory.class)
				.addTokenFilter(LowerCaseFilterFactory.class)
				.addTokenFilter(WordDelimiterGraphFilterFactory.class)
				.build();
		return a;
	}
	private void indexTerms(File dir, IndexWriter writer) throws IOException {
		String content;
		String title;
		//for each file in corpus
		for (File file: dir.listFiles())
		{
			//if its a .txt file we extract the data
			if (file.getName().contains(".txt"))
			{
				//the data is saved in a hashmap <titolo, title of doc>, <contenuto, content of doc>
				Map<String, String> title2content=this.dataExtractor.extractDataFromTextFile(file);	

				title=title2content.get(titleField);
				
				content=title2content.get(contentField);

				Document doc= new Document();

				

				
				
				doc.add(new TextField(titleField,title,Field.Store.YES));
				doc.add(new TextField(contentField,content,Field.Store.YES));

				writer.addDocument(doc);
				

				
				


			}



		}
		writer.commit();
		
		
		

		
		writer.close();
	}

	
	private void indexDocs(Codec codec) throws Exception 
	{
		File dir=new File(corpusPath);		//corpus path

		
		
		Path path = Paths.get(indexPath);
		Directory directory = FSDirectory.open(path);
		
		Analyzer defaultAnalyzer = new StandardAnalyzer();
		
		Analyzer titleAnalyzer=this.createTitleAnalyzer();
		
		Analyzer contentAnalyzer=this.createContetAnalyzer();
		
		Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();		//creazione di un map analyzer 
		
        perFieldAnalyzers.put(contentField, contentAnalyzer);	//contenuto verra tokenizzato seguendo la standardAnalyzer 
        
        perFieldAnalyzers.put(titleField, titleAnalyzer);			//titolo tokenizzato in base a gli spazi vuoti

        Analyzer analyzer = new PerFieldAnalyzerWrapper(defaultAnalyzer, perFieldAnalyzers);
        
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        
        if (codec != null) {
            config.setCodec(codec);
        }
        
        IndexWriter writer = new IndexWriter(directory, config);
        writer.deleteAll();

		

		indexTerms(dir, writer);

	}
	@Override
	public void createIndex(Codec codec) throws Exception	
	{


		
		this.indexDocs(codec);
		

	}
	


	
		
	}



