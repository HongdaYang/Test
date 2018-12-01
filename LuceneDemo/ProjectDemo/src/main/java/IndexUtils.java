import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class IndexUtils {
    // index source
    static String searchSource = "/Users/yanghongda/Desktop/luceneTest";
    // index path
    static String indexFolder = "/Users/yanghongda/Desktop/luceneIndex";

    // create Document from files
    static List<Document> file2Document(String folderPath) throws Exception {
        List<Document> resultList = new ArrayList<>();
        File folder  = new File(folderPath);
        if(!folder.isDirectory())
            return null;
        File[] files = folder.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if(file.isFile()) {
                String fileContent = FileUtils.readFileToString(file);
                String filePath = file.getAbsolutePath();
                Long fileSize = FileUtils.sizeOf(file);
                Document document = new Document();
                Field field_fileName = new StringField("fileName",fileName, Field.Store.YES);
                Field field_fileContent = new TextField("fileContent",fileContent, Field.Store.YES);
                Field field_fileSize = new TextField("fileSize",String.valueOf(fileSize), Field.Store.YES);
                Field field_filePath = new StoredField("filePath",filePath);

                //Field auto_field = new TextField("auto","auto", Field.Store.YES);

                document.add(field_fileName);
                document.add(field_fileContent);
                document.add(field_fileSize);
                document.add(field_filePath);
                //document.add(auto_field);

                resultList.add(document);
            }
        }
        return resultList;
    }

    static void printDocumentOfFile(Document document) {
        System.out.println("文档名称 = " + document.get("fileName"));
        System.out.println("文档大小 = " + document.get("fileSize"));
        System.out.println("文档内容 = " + document.get("fileContent"));
    }
}
