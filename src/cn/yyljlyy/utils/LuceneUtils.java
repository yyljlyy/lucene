package cn.yyljlyy.utils;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import sun.security.krb5.Config;

public class LuceneUtils {
	private static Directory directory = null;
	private static IndexWriterConfig config = null;
	private static Version version = null;
	private static Analyzer analyzer = null;
	private static IndexReader indexReader = null;
	static {
		// ³õÊ¼»¯²Ù×÷
		try {
			directory = FSDirectory.open(new File(Constants.INDEXDIR));
			version = Version.LUCENE_44;
			analyzer = new StandardAnalyzer(version);
			config = new IndexWriterConfig(version, analyzer);
			indexReader = DirectoryReader.open(directory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static IndexWriter getIndexWriter() throws IOException {
		IndexWriter indexWriter = new IndexWriter(directory, config);
		return indexWriter;
	}

	public static IndexSearcher gIndexSearcher() {
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		return indexSearcher;
	}
	public static Version getCurentVersion(){
		return version;
	}
	public static Analyzer getCurentaAnalyzer(){
		return analyzer;
	}
	
}
