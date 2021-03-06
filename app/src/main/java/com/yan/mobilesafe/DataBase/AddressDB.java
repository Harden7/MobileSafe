package com.yan.mobilesafe.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 导入归属地数据库
 * Created by a7501 on 2015/11/27.
 */
public class AddressDB {
    private static final String PATH = "data/data/com.yan.mobilesafe/files/address.db";

    public static String getAddress(String number){
        String address = "未知号码";

        //获取数据库对象
        SQLiteDatabase database = SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.OPEN_READONLY);

        //手机号正则表达式匹配
        //1 + （3.4.5.6.7.8)+(9位数字)
        //^1[3-8]\d{9}$

        if (number.matches("^1[3-8]\\d{9}$")){
            Cursor cursor = database.rawQuery("select location from data2 where id = (select outkey from data1 where id = ?)"
            ,new String[]{number.substring(0,7)});
            if (cursor.moveToNext()){
                address = cursor.getString(0);
            }
            cursor.close();
        }else if (number.matches("^\\d+$")){ //匹配数字
            switch (number.length()){
                case 3:
                    address = "报警电话";
                    break;
                case 4:
                    address = "模拟器电话";
                    break;
                case 5:
                    address = "客服电话";
                    break;
                case 7:
                case 8:
                    address = "本地电话";
                    break;
                default:
                    //03154011111
                    //01055225525
                    if (number.startsWith("0") && number.length()>10){  //可能是长途电话
                        //先查询3位区号
                        Cursor cursor = database.rawQuery("select location from data2 where area = ?",
                                new String[]{number.substring(1,3)});
                        if (cursor.moveToNext()){
                            address = cursor.getString(0);
                            address = address.substring(0,address.length()-2);
                        }else {
                            cursor.close();
                            //查询4位区号
                            cursor = database.rawQuery("select location from data2 where area = ?",
                                    new String[]{number.substring(1,4)});
                            if (cursor.moveToNext()){
                                address = cursor.getString(0);
                                address = address.substring(0,address.length()-2);
                            }
                            cursor.close();
                        }
                    }
                    break;
            }
        }

        database.close();
        return address;
    }
}
