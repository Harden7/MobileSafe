package com.yan.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ��ȡ�������Ĺ���
 * Created by yan on 2015/11/18.
 */
public class StreamUtils  {

    /**
     * ��ȡ������
     * **/
    public static String readFromStream(InputStream stream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int len = 0;
        byte[] bytes = new byte[1024];

        while((len = stream.read(bytes))!= -1){
            outputStream.write(bytes,0,len);
        }
        String result = outputStream.toString();
        stream.close();
        outputStream.close();
        return result;

    }
}
