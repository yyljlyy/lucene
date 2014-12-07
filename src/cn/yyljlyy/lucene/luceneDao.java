package cn.yyljlyy.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.omg.CORBA.PUBLIC_MEMBER;

import cn.yyljlyy.bean.Article;
import cn.yyljlyy.utils.ArticleToDocument;
import cn.yyljlyy.utils.LuceneUtils;

/**
 * 创建lucene dao用来操作索引库
 * 
 * @author lee
 * 
 */
public class luceneDao {
	/**
	 * 创建索引 通过indexWriter
	 * @throws IOException 
	 */
	public void createIndex(Article article) throws IOException {
		IndexWriter indexWriter = LuceneUtils.getIndexWriter();
		Document document = ArticleToDocument.articleToDocument(article);
		indexWriter.addDocument(document);
		indexWriter.close();
	}

	public void deleteIndex(String fieldName,String fieldValue) throws IOException {
		IndexWriter indexWriter = LuceneUtils.getIndexWriter();
		Term term = new Term(fieldName,fieldValue);
		indexWriter.deleteDocuments(term);
		indexWriter.close();
	}

	public void updateIndex(String fieldName,String fieldValue,Article article) throws IOException {
		IndexWriter indexWriter = LuceneUtils.getIndexWriter();
		
		Term term = new Term(fieldName,fieldValue);
		/**
		 * term 条件
		 * 
		 * 更新内容的对象
		 */
		Document doc = ArticleToDocument.articleToDocument(article);
		indexWriter.updateDocument(term, doc);
		indexWriter.close();
	}
	/**
	 * 用从客户端传过来的关键字进行搜索
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public void queryIndex(String keywords,int firstResult,int maxResult) throws IOException, ParseException {
		IndexSearcher indexSearcher = LuceneUtils.gIndexSearcher();
		//Query query = new TermQuery(new Term("fieldName","fieldValue"));
		String fields[] = {"title","content","author"};
		//多个字段来查询
		QueryParser queryParser = new MultiFieldQueryParser(LuceneUtils.getCurentVersion(), fields, LuceneUtils.getCurentaAnalyzer());
		Query query = queryParser.parse(keywords);
		TopDocs topDocs = indexSearcher.search(query, firstResult + maxResult);
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		int endResult=Math.min(firstResult+maxResult ,scoreDocs.length);
		for (int i = firstResult; i <endResult; i++) {
			int docID = scoreDocs[i].doc;
			Document document = indexSearcher.doc(docID);
			System.out.println("id = "+document.get("id"));
			System.out.println("title = "+document.get("title"));
			System.out.println("content = "+document.get("content"));
			System.out.println("author = "+document.get("author"));
			System.out.println("link = "+document.get("link"));
		}
	}
	
	
}
