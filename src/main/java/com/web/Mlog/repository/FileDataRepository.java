package com.web.Mlog.repository;

import com.web.Mlog.domain.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, String> {
    List<FileData> findAllByUuidIn(List<String> uuids);
}
