package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.Appointment;
import com.example.lostsandfounds.Model.Request;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.AppointmentRepository;
import com.example.lostsandfounds.Repository.RequestRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final RequestRepository requestRepository;


    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }





    ///  addAppointment >> user can add a an appointment to pick up their approved claimed item
    /// based on the approval the appointment status will be true if the appointment status is true then user is allowed to
    ///request an appointment

    public String addAppointment (Appointment appointment){


        User user = userRepository.findUserById(appointment.getUserId());


        if (user == null) {
            return "userNF";
        }


        if (!user.getAppointment()){
            return "No appointments";
        }

        appointmentRepository.save(appointment);
        return "successfully";



    }




    ///  rescheduleAppointment >> user can reschedule their appointment to pick up their claimed items

    public Boolean rescheduleAppointment (Appointment appointment, Integer id)
    {
        Appointment oldAppointment= appointmentRepository.findAppointmentById(id);


        if(oldAppointment!=null){
            oldAppointment.setTheDate(appointment.getTheDate());
            oldAppointment.setTheTime(appointment.getTheTime());
            oldAppointment.setLocation(appointment.getLocation());

            appointmentRepository.save(oldAppointment);
            return true;
        }
        return false;
    }



    public Boolean deleteAppointment (Integer id){
        Appointment ap= appointmentRepository.findAppointmentById(id);
        if(ap!=null){
            appointmentRepository.delete(ap);
            return true;
        }
        return false;
    }



/// get Appointments by userId

    public List<Appointment> getAppointmentByUserId(Integer userId){
       return  appointmentRepository.findAppointmentByUserId(userId);
    }















}
