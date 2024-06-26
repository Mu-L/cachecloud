package com.sohu.cache.redis.enums;

import java.util.ArrayList;
import java.util.List;

import com.sohu.cache.constant.RedisConstant;

/**
 * Redis报警配置枚举
 * @author leifu
 * @Date 2017年6月13日
 * @Time 下午5:34:42
 */
public enum RedisInfoEnum {
    
    /**
     * Stats
     */
    client_biggest_input_buf(RedisConstant.Stats, "client_biggest_input_buf", "输入缓冲区最大buffer大小(单位：字节)", false),
    client_longest_output_list(RedisConstant.Stats, "client_longest_output_list", "输出缓冲区最大队列长度", false),
    instantaneous_ops_per_sec(RedisConstant.Stats, "instantaneous_ops_per_sec", "实时ops", false),
    rejected_connections(RedisConstant.Stats, "rejected_connections", "拒绝客户端连接数", true),
    sync_partial_err(RedisConstant.Stats, "sync_partial_err", "部分复制失败次数", true),
    sync_partial_ok(RedisConstant.Stats, "sync_partial_ok", "部分复制成功次数", true),
    sync_full(RedisConstant.Stats, "sync_full", "全量复制执行次数", true),
    total_net_input_bytes(RedisConstant.Stats, "total_net_input_bytes", "网络输入流量(单位：字节)", true),
    total_net_output_bytes(RedisConstant.Stats, "total_net_output_bytes", "网络输出流量(单位：字节)", true),
    keyspace_hits(RedisConstant.Stats, "keyspace_hits", "键命中数", true),
    keyspace_misses(RedisConstant.Stats, "keyspace_misses", "键未命中数", true),
    evicted_keys(RedisConstant.Stats, "evicted_keys", "键剔除数", true),
    expired_keys(RedisConstant.Stats, "expired_keys", "键过期数", true),
    connected_clients(RedisConstant.Clients, "connected_clients", "客户端连接数", false),

    /**
     * Persistence
     */
    aof_current_size(RedisConstant.Persistence, "aof_current_size", "aof当前尺寸(单位：字节)", false),
    aof_base_size(RedisConstant.Persistence, "aof_base_size", "aof基准尺寸(单位：字节)", false),
    aof_delayed_fsync(RedisConstant.Persistence, "aof_delayed_fsync", "aof阻塞次数", true),
    latest_fork_usec(RedisConstant.Persistence, "latest_fork_usec", "上次fork所用时间(单位：微秒)", false),
    rdb_last_bgsave_status(RedisConstant.Persistence, "rdb_last_bgsave_status", "上一次bgsave状态", false),
    rdb_bgsave_in_progress(RedisConstant.Persistence, "rdb_bgsave_in_progress", "是否正在进行bgsave", false),
    rdb_last_save_time(RedisConstant.Persistence, "rdb_last_save_time", "上一次bgsave时间", false),
    rdb_last_bgsave_time_sec(RedisConstant.Persistence, "rdb_last_bgsave_time_sec", "上一次bgsave花费时间", false),
    loading(RedisConstant.Persistence, "loading", "指示是否正在进行dump(rdb/aof)文件加载", false),
    loading_eta_seconds(RedisConstant.Persistence, "loading_eta_seconds", "dump加载完成预计完成时间（秒）", false),

    /**
     * CPU
     */
    used_cpu_sys(RedisConstant.CPU, "used_cpu_sys", "redis进程系统态消耗(单位-秒)", true),
    used_cpu_user(RedisConstant.CPU, "used_cpu_user", "redis进程用户态消耗(单位-秒)", true),
    used_cpu_sys_children(RedisConstant.CPU, "used_cpu_sys_children", "redis子进程系统态消耗(单位-秒)", true),
    used_cpu_user_children(RedisConstant.CPU, "used_cpu_user_children", "redis子进程用户态消耗(单位-秒)", true),

    /**
     * Memory
     */
    used_memory(RedisConstant.Memory, "used_memory", "内存使用(单位：字节)", false),
    used_memory_rss(RedisConstant.Memory, "used_memory_rss", "物理内存使用(单位：字节)", false),
    mem_fragmentation_ratio(RedisConstant.Memory, "mem_fragmentation_ratio", "内存碎片率", false),
    
    /**
     * Replication
     */
    role(RedisConstant.Replication, "role", "主从角色", false),
    master_host(RedisConstant.Replication, "master_host", "主节点host", false),
    master_port(RedisConstant.Replication, "master_port", "主节点端口", false),
    connected_slaves(RedisConstant.Replication, "connected_slaves", "从节点数量", false),
    master_repl_offset(RedisConstant.Replication, "master_repl_offset", "主节点偏移量", false),
    master_link_status(RedisConstant.Replication, "master_link_status", "主节点连接状态", false),
    slave_repl_offset(RedisConstant.Replication, "slave_repl_offset", "从节点偏移量", false),
    master_sync_in_progress(RedisConstant.Replication, "master_sync_in_progress", "从节点是否正在同步主节点", false)

    ;
    
    private final static List<RedisInfoEnum> RedisInfoEnumList = new ArrayList<RedisInfoEnum>();
    static {
        for (RedisInfoEnum redisInfoEnum : RedisInfoEnum.values()) {
            RedisInfoEnumList.add(redisInfoEnum);
        }
    }
    
    private RedisConstant redisConstant;
    
    private String value;
    
    private String info;
    
    private boolean needCalDif;

    private RedisInfoEnum(RedisConstant redisConstant, String value, String info, boolean needCalDif) {
        this.redisConstant = redisConstant;
        this.value = value;
        this.info = info;
        this.needCalDif = needCalDif;
    }
    
    /**
     * 获取需要计算差值的统计属性
     * @return
     */
    public static List<RedisInfoEnum> getNeedCalDifRedisInfoEnumList() {
        List<RedisInfoEnum> resultList = new ArrayList<RedisInfoEnum>();
        for (RedisInfoEnum redisInfoEnum : RedisInfoEnumList) {
            if (redisInfoEnum.isNeedCalDif()) {
                resultList.add(redisInfoEnum);
            }
        }
        return resultList;
    }
    
    public RedisConstant getRedisConstant() {
        return redisConstant;
    }

    public String getValue() {
        return value;
    }

    public String getInfo() {
        return info;
    }

    public boolean isNeedCalDif() {
        return needCalDif;
    }


    
}
