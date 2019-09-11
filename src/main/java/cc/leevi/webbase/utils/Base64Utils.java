package cc.leevi.webbase.utils;

import sun.misc.BASE64Encoder;

public class Base64Utils {

    /**
     * 对key值进行加密
     * @param key
     * @return
     */
    public static String encryptBASE64(String key) {
        byte[] bt = key.getBytes();
        return (new BASE64Encoder()).encodeBuffer(bt);
    }
}
