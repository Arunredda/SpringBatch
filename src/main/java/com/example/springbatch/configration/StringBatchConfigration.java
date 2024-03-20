package com.example.springbatch.configration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.example.springbatch.Entity.Student;
import com.example.springbatch.Entity.StudentDetails;




@Configuration
public class StringBatchConfigration extends  DefaultBatchConfigurer {

	
	/*
	 * @Autowired public DataSource dataSource;
	 */
	

	/*
	 * @Bean public ItemReader<Student> iteamReader(DataSource dataSource) {
	 * 
	 * JdbcPagingItemReader<Student> read = new JdbcPagingItemReader<>();
	 * read.setDataSource(dataSource); read.setPageSize(1000);
	 * 
	 * PagingQueryProvider qp = pagingQueryProvider(); read.setQueryProvider(qp);
	 * read.setRowMapper(new BeanPropertyRowMapper<>(Student.class));
	 * 
	 * return read;
	 * 
	 * }
	 * 
	 * private PagingQueryProvider pagingQueryProvider() { MySqlPagingQueryProvider
	 * query = new MySqlPagingQueryProvider();
	 * query.setSelectClause("Select  * from batch.student_details"); return query;
	 * 
	 * }
	 */

	/*
	 * @Bean public ItemProcessor<Student, StudentDetails> itemProcessor() { // TODO
	 * Auto-generated method stub int cout = 0; System.out.println("process Count "
	 * + cout++); StudentDetails studentDetails = new StudentDetails(); return
	 * student -> studentDetails; }
	 */
	/*
	 * @Bean public ItemWriter<StudentDetails> itemWriter() {
	 * 
	 * //String sql =
	 * "insert into studentDetails (id,student_name,email_Id) values(:id, :student_name, :email_Id)"
	 * ;
	 * 
	 * 
	 * String sql = "select * from batch.student_details";
	 * 
	 * JdbcBatchItemWriter<StudentDetails> jdbcBatchItemWriter = new
	 * JdbcBatchItemWriter<>(); jdbcBatchItemWriter.setSql(sql);
	 * jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new
	 * BeanPropertyItemSqlParameterSourceProvider<>());
	 * 
	 * // jdbcBatchItemWriter.setJdbcTemplate(namedParameterJdbcTemplate);
	 * jdbcBatchItemWriter.setAssertUpdates(false);
	 * 
	 * return jdbcBatchItemWriter;
	 * 
	 * }
	 * 
	 * 
	 * @Bean public Step step(StepBuilderFactory stepBuilderFactory,
	 * ItemReader<Student> itemReader, ItemProcessor<Student, StudentDetails>
	 * itemProcessor, ItemWriter<StudentDetails> itemWriter) {
	 * 
	 * return stepBuilderFactory.get("step").<Student,StudentDetails>
	 * chunk(100).reader(itemReader).
	 * processor(itemProcessor).writer(itemWriter).build();
	 * 
	 * 
	 * }
	 * 
	 * @Bean public Job runJob(JobBuilderFactory jobBuilderFactory, Step step) {
	 * return jobBuilderFactory.get("step1") .incrementer(new
	 * RunIdIncrementer()).start(step).build();
	 * 
	 * }
	 */
	
	 @Autowired
	    private DataSource dataSource;
	 

	    @Autowired
	    private JobBuilderFactory jobBuilderFactory;

	    @Autowired
	    private StepBuilderFactory stepBuilderFactory;
	
	    @Bean
	    public JdbcCursorItemReader<Student> reader() {
	        JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<>();
	        reader.setDataSource(dataSource);
	        reader.setSql("SELECT * FROM student");
	        reader.setRowMapper(new BeanPropertyRowMapper<>(Student.class));
	        return reader;
	    }
	    
	    
	    
	    
	    @Bean
	    public ItemProcessor<Student, StudentDetails> itemProcessor() { 
	   	  int cout = 0; 
	   	  System.out.println("process Count " + cout++);   
	   	  
	   	  StudentDetails studentDetails = new StudentDetails();
	   	  return	  student -> studentDetails;
	   	  }
	    
	    @Bean
	    public JdbcBatchItemWriter<StudentDetails> writer() {
	        JdbcBatchItemWriter<StudentDetails> writer = new JdbcBatchItemWriter<>();
	        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
	        writer.setSql("INSERT INTO student_details (id,email_id, student_name) VALUES (:id, :emailId, :studentName)");
	        writer.setDataSource(dataSource);
	        return writer;
	    }

	     @Bean
	    public Step step1() {
	        return stepBuilderFactory.get("step1")
	                .<Student, StudentDetails>chunk(10)
	                .reader(reader())
					.writer(writer())/*.taskExecutor(taskExecutor()) */             
	                .build();
	    }

	    @Bean
	    public Job myJob() {
	        return jobBuilderFactory.get("myJob")
	                .incrementer(new RunIdIncrementer())
	                .flow(step1())
	                .end()
	                .build();
	    }
	    
//		 @Bean 
//	    public TaskExecutor taskExecutor() {
//	    	SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//	    	asyncTaskExecutor.setConcurrencyLimit(10);
//	        return asyncTaskExecutor;
//	    }
	    
	    
}
