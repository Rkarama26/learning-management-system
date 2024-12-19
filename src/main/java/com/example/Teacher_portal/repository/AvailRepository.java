package com.example.Teacher_portal.repository;

import com.example.Teacher_portal.Entity.Availability;
import com.example.Teacher_portal.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByUser_Id(Long userId);

    @Query("SELECT a FROM Availability a WHERE a.user = :user "
            + "AND a.startTime = :startTime "
            + "AND a.endTime = :endTime")
    Availability findByUserAndStartTimeAndEndTime(
            @Param("user") User user,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

}
