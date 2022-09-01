package com.example.project_board.repository.board;

import com.example.project_board.entity.data.FileUploadEntity;
import org.springframework.data.repository.CrudRepository;

//FileUploadEntity를 저장하는 인터페이스 (JPA CrudRepository(List<Board> boardList)
public interface FileUploadInfoRepository extends CrudRepository<FileUploadEntity, Long> {
}
