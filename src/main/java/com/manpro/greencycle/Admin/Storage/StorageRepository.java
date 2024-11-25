package com.manpro.greencycle.Admin.Storage;

import java.util.List;

public interface StorageRepository {
    List<Storage> findAll();
    List<Storage> findWithFilter(String filter);
}
