package co.edu.talentotech.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import co.edu.talentotech.response.Records;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ToString
@Entity
public class Task extends Records<Task> implements java.io.Serializable{
	private static final long serialVersionUID = 5L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String assigned;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
