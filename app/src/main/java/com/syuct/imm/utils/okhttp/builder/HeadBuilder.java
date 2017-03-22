package com.syuct.imm.utils.okhttp.builder;

import com.syuct.imm.utils.okhttp.OkHttpUtils;
import com.syuct.imm.utils.okhttp.request.OtherRequest;
import com.syuct.imm.utils.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
