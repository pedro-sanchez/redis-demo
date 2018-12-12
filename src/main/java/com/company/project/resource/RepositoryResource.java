package com.company.project.resource;

import com.company.project.bean.Repository;
import com.company.project.bo.iface.IRepositoryBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/repositories")
public class RepositoryResource {

    private IRepositoryBO repositoryBO;

    @Autowired
    public RepositoryResource(IRepositoryBO repositoryBO) {
        this.repositoryBO = repositoryBO;
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insert(@RequestBody(required = true) Repository repository) {
        repositoryBO.insert(repository);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable String id, @RequestBody(required = true) Repository repository) {
        //recovery and merge
        repositoryBO.update(repository);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable String id) {
        repositoryBO.delete(id);
        return ResponseEntity.ok().build();
    }

    //GET
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Repository> findById(@PathVariable String id) {
        Repository repository = repositoryBO.findById(id);
        return new ResponseEntity<>(repository, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Repository>> findAll() {
        List<Repository> repositories = repositoryBO.findAll();
        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }

}
