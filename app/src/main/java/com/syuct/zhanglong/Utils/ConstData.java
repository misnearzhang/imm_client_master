package com.syuct.zhanglong.Utils;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

/**
 * Created by RiKS on 2014/10/16.
 */
public class ConstData {
    private static List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();

    private static FriendListCache cache=new FriendListCache(list);
    public static List<Map<String,Object>> getList(){
        if(list.size()==0){
            restore();
        }
        return list;
    }
    public static void addList(Map<String, Object> map){
        list.add(map);
        cache.refresh(list);
        save(cache);
    }


    public static void save(FriendListCache cache){
        try {
            FileOutputStream fos = new FileOutputStream("sdcard/list");
            ObjectOutputStream oos = new ObjectOutputStream(fos);


            oos.writeObject(cache);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restore() {
        try {
            FileInputStream fis = new FileInputStream("sdcard/list");
            ObjectInputStream ois = new ObjectInputStream(fis);

            cache = (FriendListCache)ois.readObject();

            list=cache.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

