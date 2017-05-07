package bishe.dgut.edu.cn.reallygoodapp.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/6.
 */

public class MD5 {

    public static String getMD5(String val) {
        try {
            //定义一个名为MD5的哈希值
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //传入val转化为byte二进制的摘要
            md5.update(val.getBytes());
            //计算哈希值，并返回
            byte[] m = md5.digest();
            return getString(m);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(aB);
        }
        return sb.toString();
    }

}
