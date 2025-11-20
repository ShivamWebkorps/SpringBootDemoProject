package com.example.springbootdemo.Repository;
import com.example.springbootdemo.Entity.ResponseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<ResponseResult, Long> {

    ResponseResult findByEmployee_Id(Long employeeId);
}
