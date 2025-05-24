package com.ravi.semester.repository;

import com.ravi.semester.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByYearAndStartDateLessThanEqualAndEndDateGreaterThanEqual(int year, LocalDate startDate, LocalDate endDate);
    @Query("SELECT e FROM Event e WHERE e.year = :studentYear AND e.endDate <= :date ORDER BY e.endDate DESC")
    List<Event> findCompletedEventsTillDate(@Param("studentYear") int studentYear, @Param("date") LocalDate date);

    @Query("SELECT e FROM Event e WHERE e.year = :studentYear AND e.startDate <= :date AND e.endDate >= :date")
    Optional<Event> findLiveEventOnDate(@Param("studentYear") int studentYear, @Param("date") LocalDate date);

}