package edu.nuist.author.util;
import org.apache.tika.metadata.Metadata;  
import org.apache.tika.parser.AutoDetectParser;  
import org.apache.tika.parser.ParseContext;  
import org.apache.tika.parser.Parser;  
import org.apache.tika.sax.BodyContentHandler;  
import org.xml.sax.ContentHandler;  
import java.io.*;  
public class TiKaUtil {
	public static String parseFile(File file){  
        Parser parser = new AutoDetectParser();  
        InputStream input = null;  
        try{  
            Metadata metadata = new Metadata();  
            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");  
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());  
            input = new FileInputStream(file);  
            ContentHandler handler = new BodyContentHandler();//当文件大于100000时，new BodyContentHandler(1024*1024*10);   
            ParseContext context = new ParseContext();  
            context.set(Parser.class,parser);  
            parser.parse(input,handler, metadata,context);  
            for(String name:metadata.names()) {  
               // System.out.println(name+":"+metadata.get(name));  
            }  
            //System.out.println(handler.toString());  
            return handler.toString();  
        }catch (Exception e){  
            e.printStackTrace();  
        }finally {  
            try {  
                if(input!=null)input.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
  
    }  
   public String parsePDF(String path){
	  return parseFile(new File(path));
   }
    public static void main(String argt0[])throws Exception{  
    //	new TiKaUtil().parsePDF("D:\\Documents\\虚词分析\\paper\\吴黎兵\\VANET-cellular环境下安全消息广播中继选择方法研究.pdf");
        parseFile(new File("D:\\Documents\\虚词分析\\paper\\吴黎兵\\VANET-cellular环境下安全消息广播中继选择方法研究.pdf"));  
    }  
}
