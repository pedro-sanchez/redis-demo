package com.company.project.bo;

import com.company.project.bean.Repository;
import com.company.project.bo.iface.IRepositoryBO;
import com.company.project.dao.RepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryBO implements IRepositoryBO {
    private RepositoryDao repositoryDao;

    @Autowired
    public RepositoryBO (RepositoryDao repositoryDao) {
        this.repositoryDao = repositoryDao;
    }

    @Override
    public Repository insert(Repository repository) {
        return repositoryDao.insert(repository);
    }

    @Override
    public Repository update(Repository repository) {
        return repositoryDao.update(repository);
    }

    @Override
    public void delete(String id) {
        repositoryDao.delete(id);
    }

    @Override
    public Repository findById(String id) {
        return repositoryDao.findById(id);
    }

    @Override
    public List<Repository> findAll() {
        return repositoryDao.findAll();
    }
}
