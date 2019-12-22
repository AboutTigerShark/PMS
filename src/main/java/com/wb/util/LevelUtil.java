package com.wb.util;

import org.apache.commons.lang3.StringUtils;

public class LevelUtil {
    public final static String SEPARATOR = "."; //层级分隔符,如0.1

    public final static String ROOT = "0";  // 0表示根级

    //计算层级
    public static String calculateLevel(String parentLevel, Integer deptId){
        if(StringUtils.isBlank(parentLevel)){ //如果父层级为空则当前层级为根级
            return ROOT;
        }else { //否则返回拼接的层级
            return StringUtils.join(parentLevel, SEPARATOR, deptId);
        }

    }
}
