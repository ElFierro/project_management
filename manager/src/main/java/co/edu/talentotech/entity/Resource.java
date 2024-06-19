package co.edu.talentotech.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import co.edu.talentotech.response.Records;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
@Data
public class Resource extends Records<Resource> implements java.io.Serializable {
	private static final long serialVersionUID = 5L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	 private LocalDateTime createdDate;

	 @PrePersist
	 protected void onCreate() {
	      this.createdDate = LocalDateTime.now();
	 }
}
