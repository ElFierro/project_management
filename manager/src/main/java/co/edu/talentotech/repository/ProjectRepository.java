package co.edu.talentotech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.talentotech.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}