package com.company.project.bo.iface;

import com.company.project.bean.Repository;

import java.util.List;

public interface IRepositoryBO {
    Repository insert(Repository repository);

    Repository update(Repository repository);

    void delete(String id);

    Repository findById(String id);

    List<Repository> findAll();
}
