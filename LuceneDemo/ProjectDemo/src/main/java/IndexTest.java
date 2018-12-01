import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.FileSystems;
import java.util.List;

public class IndexTest {
    private static void testCreateIndex() {
        try {
            List<Document> documentList = IndexUtils.file2Document(IndexUtils.searchSource);
            Analyzer analyzer = new SmartChineseAnalyzer();
            //Analyzer analyzer = new StandardAnalyzer();
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(IndexUtils.indexFolder));

            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            IndexWriter indexWriter = new IndexWriter(directory,config);

            indexWriter.deleteAll();
            if(documentList != null) {
                for (Document document : documentList) {
                    TokenStream tokenStream = analyzer.tokenStream("fileContent",document.get("fileContent"));
                    System.out.println(document.get("fileName") + "分词器分词结果：" );
                    doToken(tokenStream);
                    indexWriter.addDocument(document);
                }
            }
            indexWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("索引创建完成");
    }

    private static void doToken(TokenStream tokenStream) throws Exception {
        tokenStream.reset();
        CharTermAttribute cta = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            System.out.print(cta.toString() + "|");
        }
        System.out.println();
        tokenStream.end();
        tokenStream.close();
    }

    public static void main(String[] args) {
        testCreateIndex();
    }
}
