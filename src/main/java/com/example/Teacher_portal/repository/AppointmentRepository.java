package com.example.Teacher_portal.repository;

import com.example.Teacher_portal.Entity.Appointments;
import com.example.Teacher_portal.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Long> {


    @Query("SELECT a FROM Appointments a WHERE a.teacher.id = :userId OR a.student.id = :userId")
    List<Appointments> findByUserId(@Param("userId") Long userId);


    @Query("SELECT a FROM Appointments a WHERE a.teacher = :teacher "
            + "AND a.startTime = :startTime "
            + "AND a.endTime = :endTime")
    Appointments findByTeacherAndStartTimeAndEndTime(
            @Param("teacher") User teacher,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

}
