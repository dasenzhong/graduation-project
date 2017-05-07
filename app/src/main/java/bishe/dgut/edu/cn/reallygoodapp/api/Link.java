package bishe.dgut.edu.cn.reallygoodapp.api;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/6.
 */

public class Link {

    private static OkHttpClient client;
    private static String serverAddress = "http://172.27.185.46:8080/reallygoodapp/api";

    static {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(cookieManager)).build();
    }

    //返回服务器连接
    public static OkHttpClient getClient() {
        return client;
    }

    //返回请求地址
    public static Request.Builder getRequestAddress(String address) {
        return new Request.Builder().url(serverAddress + address);
    }
}
