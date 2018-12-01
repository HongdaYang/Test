

import com.cn.serviceInterface.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import java.io.IOException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("客户端启动....");
        TTransport transport = null;

        try {

            transport = new TSocket("localhost", 9898, 30000);
            // 协议要和服务端一致
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloService.Client client = new HelloService.Client(protocol);
            Scanner sc = new Scanner(System.in);
            StringBuilder stringBuilder = new StringBuilder();
            while(!stringBuilder.toString().equals("EOF")) {
                stringBuilder.delete(0,stringBuilder.length());
                System.out.println("Enter your value:");
                //str = sc.nextLine();
                stringBuilder.append(sc.nextLine());
                String result = client.Hello(stringBuilder.toString());
                System.out.println(result);
            }
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
}
