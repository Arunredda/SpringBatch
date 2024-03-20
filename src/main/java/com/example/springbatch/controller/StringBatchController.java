package com.example.springbatch.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbatch.Entity.Student;
import com.example.springbatch.Repostory.StudentRepos;

@RestController
@EnableBatchProcessing
public class StringBatchController {
	
	@Autowired
	private StudentRepos  studentRepos;
	
	@Autowired
	private JobLauncher joblaun;
	
	@Autowired
	private Job job;
	
	
	
	@GetMapping("/savestudent")
	public String savestudent() {
		System.out.println("Arun");
		System.out.println("Arun");
		System.out.println("Arun");
		System.out.println("Arun");
		List<Student> list=new ArrayList<>();
		for(int i=0; i<=10; i++) {
			int id=0;
			Student student=new Student();
			student.setStudentName("Arun");
			student.setEmailId("arun@gmail.com");
			student.setId(id++);
			list.add(student);
			
		}			
		studentRepos.saveAll(list);		
		return "OK"; 
		
	}
	
	
	@GetMapping("/jobLuncher")
	public String jobLuncher() {
		
		JobParameters  JobParameters=new JobParametersBuilder()
		.addLong("Time", System.currentTimeMillis()).toJobParameters();	
		
		try {
			joblaun.run(job, JobParameters);
			
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return "run";
		
	}
	

}
