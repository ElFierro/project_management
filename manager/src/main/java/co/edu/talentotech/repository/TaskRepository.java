package co.edu.talentotech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.talentotech.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByProjectId(Long projectId);
}