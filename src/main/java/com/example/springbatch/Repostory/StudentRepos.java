package com.example.springbatch.Repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.springbatch.Entity.Student;

@EnableJpaRepositories
public interface StudentRepos extends JpaRepository<Student, Integer> {
 
}
