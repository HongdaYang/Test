import com.cn.serviceInterface.HelloService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloServiceImpl implements HelloService.Iface {

    public String Hello(String name) throws org.apache.thrift.TException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
        System.out.println("Request(" + formatter.format(new Date()) + "):" + name);
        return "Response:" + name;
    }
}
