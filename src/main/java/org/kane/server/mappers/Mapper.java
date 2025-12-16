package org.kane.server.mappers;

public interface Mapper<F, T> {
    T map(F from);
}
