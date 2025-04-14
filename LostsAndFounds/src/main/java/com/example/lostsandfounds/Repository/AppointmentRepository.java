package com.example.lostsandfounds.Repository;

import com.example.lostsandfounds.Model.Appointment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    Appointment findAppointmentById(Integer id);

    List<Appointment> findAppointmentByUserId( Integer userId);
}
