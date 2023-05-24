package com.ke.clipboard.dao;

import com.ke.clipboard.model.FileDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileDTO, String> {
    // Add custom methods for data access if needed
}

