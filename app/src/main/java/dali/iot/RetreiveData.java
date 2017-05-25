package dali.iot;

/**
 * Created by Mohamed ali on 22/05/2017.
 */

public class RetreiveData {
    public static String topic        = "v1/devices/me/attributes";
    public static String content      = "";
    public static int qos             = 2;
    public static String broker       = "192.168.1.50";
    public static String clientId     = "JavaSample";

    public static String conn(String ip){

      JSONParser parser=new JSONParser();
        String Link=ip+":8080/api/v1/TokenRaspTemp1452/attributes";
        return parser.makeHttpRequest(Link);
    }


    public static int ret_temp(){
        return 0;
    }
}
