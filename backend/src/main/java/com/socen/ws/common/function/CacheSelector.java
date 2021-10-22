package com.socen.ws.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
