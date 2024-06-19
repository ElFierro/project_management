package co.edu.talentotech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.talentotech.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
	List<Resource> findByProjectId(Long projectId);
}