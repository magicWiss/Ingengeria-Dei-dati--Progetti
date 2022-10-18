package homework1.module;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceCharFilterFactory;
import org.apache.lucene.analysis.pattern.PatternTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

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
	private Path path;
	private static final String indexPathTitle="homework1/index/title";	//where to save Luceene Index

	private static final String indexPathContent="homework1/index/content";	//where to save Luceene Index
	private static final String corpusPath="C:/Users/Wissel/OneDrive/Desktop/università/MAGISTRALE/Ing.Dati/Homeworks/Homework2/corpus";
	private static final String titleField="titolo";
	private static final String contentField="contenuto";
	private  DataExtractor dataExtractor;


	public IndexCreator()
	{	

		//for extracting data from txt file
		this.dataExtractor=new DataExtractorTitleContent();	

	}

	/**
	 * Creation of the two index for title and content
	 * @param path
	 */
	private void indexDocs( Codec codec) throws IOException
	{
		File dir=new File(corpusPath);		//corpus path

		String content="";
		String title="";

		IndexWriter writerContent=this.createWriterContent(codec);	//writer for content
		IndexWriter writerTitle=this.createWriterTitle(codec);		//writer for title



		//deleting everything before indexing
		writerContent.deleteAll();		
		writerTitle.deleteAll();

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

				Document docTitle= new Document();

				Document docContent= new Document();

				docTitle.add(new TextField(titleField,title,Field.Store.YES));

				docContent.add(new TextField(contentField,content,Field.Store.NO));

				writerContent.addDocument(docContent);
				

				writerTitle.addDocument(docTitle);
				


			}



		}
		writerContent.commit();
		writerTitle.commit();
		
		writerContent.close();

		
		writerTitle.close();

	}

	//creation of the Writer index for title index
	private IndexWriter createWriterTitle(Codec codec) throws IOException {
		Path path = Paths.get(indexPathTitle);
		Directory directory = FSDirectory.open(path);
		
		
		Analyzer a1 = CustomAnalyzer.builder().withTokenizer(WhitespaceTokenizerFactory.class)

				.addTokenFilter(StopFilterFactory.class)
				.addTokenFilter(LowerCaseFilterFactory.class)
				.addTokenFilter(WordDelimiterGraphFilterFactory.class)
				.build();
		
		IndexWriterConfig config = new IndexWriterConfig(a1);
		if (codec != null) {
			config.setCodec(codec);
		}
		return new IndexWriter(directory, config);



	}
	//creates a map <term, replacment>
	private Map<String, String> createMapOfSubTerms() 
	{
		Map<String,String> terms2replace=new HashMap<>();
		terms2replace.put("pattern", "\\[\\d*\\]");
		terms2replace.put("replacement", "");
		return terms2replace;
	}

	//creation of the Writer index for content index
	private IndexWriter createWriterContent(Codec codec) throws IOException 
	{
		Path path = Paths.get(indexPathContent);
		Directory directory = FSDirectory.open(path);

		Map<String,String> patternReplacment=this.createMapOfSubTerms();
		Analyzer a = CustomAnalyzer.builder().withTokenizer(WhitespaceTokenizerFactory.class)
				.addCharFilter(PatternReplaceCharFilterFactory.NAME, patternReplacment)
				.addTokenFilter(StopFilterFactory.class)
				.addTokenFilter(LowerCaseFilterFactory.class)
				.addTokenFilter(WordDelimiterGraphFilterFactory.class)
				.build();
		IndexWriterConfig config = new IndexWriterConfig(a);
		if (codec != null) {
			config.setCodec(codec);
		}
		IndexWriter out=new IndexWriter(directory, config);
		return out;


	}

	public void createIndex(Codec codec) throws Exception	
	{



		this.indexDocs(codec);


	}
}


