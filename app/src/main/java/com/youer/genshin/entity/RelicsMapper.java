package com.youer.genshin.entity;

/**
 * @author youer
 * @date 10/9/23
 */
public class RelicsMapper {

    /**
     * 插入数据
     * @param record
     * @return
     */
    public boolean insert(Relics record){
        return record.save();
    }
} 