package com.syuct.zhanglong.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanglong on 4/9/15.
 */
public class FriendListCache implements Serializable{

    List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    public FriendListCache(List<Map<String,Object>> list) {
           this.list=list;
    }
    public void refresh(List<Map<String,Object>> list){
        this.list=list;
    }
    public List<Map<String,Object>> getList(){

        return list;
    }

}
