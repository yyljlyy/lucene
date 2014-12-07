package cn.yyljlyy.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class testLucene {
	/**
	 * 使用lucene 创建索引 Indexwirte 创建索引...
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateIndex() throws IOException {
		// 索引存放的目录....
		Directory directory = FSDirectory.open(new File("indexDir/"));
		// 当前匹配版本
		Version version = Version.LUCENE_44;
		/**
		 * 分词器 Analyzer是一个抽象类，具体的切分词的规则有子类实现
		 */
		Analyzer analyzer = new StandardAnalyzer(version);
		// 配置写索引时的一些相关性的类...
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(version,
				analyzer);
		// 构造IndexWrite对象，使用此对象来创建索引....
		IndexWriter iWriter = new IndexWriter(directory, indexWriterConfig);
		// 在lucenc 的索引库中存放的对象都是docement对外部对象要转换成lucene对象
		Document document = new Document();
		// 字段
		IndexableField field = new IntField("id", 1, Store.YES);
		IndexableField stringfField = new StringField("title",
				"Apache Lucene 是一款优质的检索引擎", Store.YES);
		IndexableField textField = new TextField("content", "Apache Lucene",
				Store.YES);
		document.add(field);
		document.add(textField);
		document.add(stringfField);
		iWriter.addDocument(document);
		iWriter.close();
	}

	/**
	 * 使用indexsearcher类对document进行搜索
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSearcher() throws IOException {
		Directory directory = FSDirectory.open(new File("indexDir/"));
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 查询条件对象
		Query query = new TermQuery(new Term("title",
				"Apache Lucene 是一款优质的检索引擎"));
		// 检索符合query条件的前n条记录
		TopDocs topDocs = indexSearcher.search(query, 100);
		System.out.println("返回总记录数" + topDocs.totalHits);
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docID = scoreDoc.doc;
			// 根据id检索document
			Document document = indexSearcher.doc(docID);
			System.out.println(document.get("title"));
			System.out.println(document.get("content"));
		}
	}
}
