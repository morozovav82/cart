package ru.morozov.cart.repo;

public interface RedisRepository {
    void add(String key, Object value);
    Object find(String key);
}