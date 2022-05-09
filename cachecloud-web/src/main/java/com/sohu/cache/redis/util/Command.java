package com.sohu.cache.redis.util;

import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

public enum Command implements ProtocolCommand {
    PING, SET, GET, QUIT, EXISTS, DEL, UNLINK, TYPE, FLUSHDB, KEYS, RANDOMKEY, RENAME, RENAMENX,
    RENAMEX, DBSIZE, EXPIRE, EXPIREAT, TTL, SELECT, MOVE, FLUSHALL, GETSET, MGET, SETNX, SETEX,
    MSET, MSETNX, DECRBY, DECR, INCRBY, INCR, APPEND, SUBSTR, HSET, HGET, HSETNX, HMSET, HMGET,
    HINCRBY, HEXISTS, HDEL, HLEN, HKEYS, HVALS, HGETALL, RPUSH, LPUSH, LLEN, LRANGE, LTRIM, LINDEX,
    LSET, LREM, LPOP, RPOP, RPOPLPUSH, SADD, SMEMBERS, SREM, SPOP, SMOVE, SCARD, SISMEMBER, SINTER,
    SINTERSTORE, SUNION, SUNIONSTORE, SDIFF, SDIFFSTORE, SRANDMEMBER, ZADD, ZRANGE, ZREM, ZINCRBY,
    ZRANK, ZREVRANK, ZREVRANGE, ZCARD, ZSCORE, ZPOPMAX, ZPOPMIN, MULTI, DISCARD, EXEC, WATCH,
    UNWATCH, SORT, BLPOP, BRPOP, AUTH, SUBSCRIBE, PUBLISH, UNSUBSCRIBE, PSUBSCRIBE, PUNSUBSCRIBE,
    PUBSUB, ZCOUNT, ZRANGEBYSCORE, ZREVRANGEBYSCORE, ZREMRANGEBYRANK, ZREMRANGEBYSCORE, ZUNIONSTORE,
    ZINTERSTORE, ZLEXCOUNT, ZRANGEBYLEX, ZREVRANGEBYLEX, ZREMRANGEBYLEX, SAVE, BGSAVE, BGREWRITEAOF,
    LASTSAVE, SHUTDOWN, INFO, MONITOR, SLAVEOF, CONFIG, STRLEN, SYNC, LPUSHX, PERSIST, RPUSHX, ECHO,
    LINSERT, DEBUG, BRPOPLPUSH, SETBIT, GETBIT, BITPOS, SETRANGE, GETRANGE, EVAL, EVALSHA, SCRIPT,
    SLOWLOG, OBJECT, BITCOUNT, BITOP, SENTINEL, DUMP, RESTORE, PEXPIRE, PEXPIREAT, PTTL, INCRBYFLOAT,
    PSETEX, CLIENT, TIME, MIGRATE, HINCRBYFLOAT, SCAN, HSCAN, SSCAN, ZSCAN, WAIT, CLUSTER, ASKING,
    PFADD, PFCOUNT, PFMERGE, READONLY, GEOADD, GEODIST, GEOHASH, GEOPOS, GEORADIUS, GEORADIUS_RO,
    GEORADIUSBYMEMBER, GEORADIUSBYMEMBER_RO, MODULE, BITFIELD, HSTRLEN, TOUCH, SWAPDB, MEMORY,
    XADD, XLEN, XDEL, XTRIM, XRANGE, XREVRANGE, XREAD, XACK, XGROUP, XREADGROUP, XPENDING, XCLAIM, LATENCY,
    ACL, XINFO, BITFIELD_RO;

    private final byte[] raw;

    Command() {
      raw = SafeEncoder.encode(this.name());
    }

    @Override
    public byte[] getRaw() {
      return raw;
    }
  }