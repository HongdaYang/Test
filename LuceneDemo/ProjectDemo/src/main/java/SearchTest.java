
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;

public class SearchTest {
    private static void testTermQuery() {
        try {
            //Query query = new TermQuery(new Term("fileContent","韩国"));
            Term term1 = new Term("fileContent","java");
            Term term2 = new Term("fileContent","梨");
            BooleanQuery.Builder builder = new BooleanQuery.Builder();
            Query query1 = new TermQuery(term1);
            Query query2 = new TermQuery(term2);
            builder.add(query1, BooleanClause.Occur.MUST);
            builder.add(query2, BooleanClause.Occur.SHOULD);
            BooleanQuery query = builder.build();
            //Query query=new FuzzyQuery(new Term("fileContent", "韩国"));
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(IndexUtils.indexFolder));
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            TopDocs topDocs = indexSearcher.search(query,10);

            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            System.out.println("共搜索到记录数：" + topDocs.totalHits);
            for(ScoreDoc scoreDoc : scoreDocs) {
                int docId = scoreDoc.doc;
                Document document = indexSearcher.doc(docId);

                IndexUtils.printDocumentOfFile(document);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testTermQuery();
    }
}
