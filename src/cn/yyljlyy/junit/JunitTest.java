package cn.yyljlyy.junit;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;

import sun.print.resources.serviceui;

import cn.yyljlyy.bean.Article;
import cn.yyljlyy.lucene.luceneDao;
	
public class JunitTest {
	private luceneDao luceneDao  = new luceneDao();
	
	@Test
	public void testCreateIndex() throws IOException{
		Article article = null;
		for (int i = 0; i < 25; i++) {
			article = new Article();
			article.setId(i);
			article.setAuthor("搜狐" + i);
			article.setTitle("开心习大大");
			article.setLink("www.baidu.com");
			article.setContent("党政建设问题发表看法");
			luceneDao.createIndex(article);
		}
	}
	
	@Test
	public void testSearcher() throws IOException, ParseException{
		//单字分词
		luceneDao.queryIndex("习",20,30);
	}
	@Test
	public void testdelete() throws IOException, ParseException{
		String fieldName = "title";
		String fieldValue = "习";
		//单字分词
		luceneDao.deleteIndex(fieldName, fieldValue);
	}
	@Test
	public void testUpdate() throws IOException{
		String fieldName = null;
		String fieldValue = "习";
		
		Article article = new Article();
		article.setId(111);
		article.setTitle("中国好声音");
		article.setContent("中国最强音");
		article.setLink("www.mangguotv.com");
		luceneDao.updateIndex(fieldName, fieldValue, article);
		
		
	}
}
