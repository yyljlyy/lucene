package cn.yyljlyy.utils;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;

import cn.yyljlyy.bean.Article;

/**
 * article对象与document对象的转换
 * 
 * @author lee
 * 
 */
public class ArticleToDocument {
	public static Document articleToDocument(Article article) {
		Document document = new Document();
		IntField idField = new IntField("id", article.getId(), Store.YES);
		TextField titleField = new TextField("title", article.getTitle(),
				Store.YES);
		TextField contentField = new TextField("title", article.getContent(),
				Store.YES);
		StringField authorfField = new StringField("author",
				article.getAuthor(), Store.YES);
		StringField linkField = new StringField("link", article.getLink(),
				Store.YES);
		document.add(idField);
		document.add(titleField);
		document.add(contentField);
		document.add(authorfField);
		document.add(linkField);
		return document;
	}
}
