package com.example.lostsandfounds.Controller;

import com.example.lostsandfounds.Api.ApiResponse;
import com.example.lostsandfounds.Model.Appointment;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.UserRepository;
import com.example.lostsandfounds.Service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {


    private final AppointmentService appointmentService;
    private final UserRepository userRepository;


    @GetMapping("get")
    public ResponseEntity getAllAppointments() {
        return ResponseEntity.status(200).body(appointmentService.getAllAppointments());
    }


///17
    @GetMapping("getById/{userId}")
    public ResponseEntity getAllAppointments(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(appointmentService.getAppointmentByUserId(userId));
    }


    ///
    ///  addAppointment >> user can add a an appointment to pick up their approved claimed item
    /// based on the approval the appointment status will be true if the appointment status is true then user is allowed to
    /// request an appointment

    /// 11
    @PostMapping("add")
    public ResponseEntity addAppointment(@Valid @RequestBody Appointment appointment, Errors e) {


        if (e.hasErrors()) {
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());

        }

        User user = userRepository.findUserById(appointment.getUserId());

        String result = appointmentService.addAppointment(appointment);


        if (result.equals("userNF")) {
            return ResponseEntity.status(400).body(new ApiResponse("The user id  is not found"));
        }
        if (result.equals("No appointments")) {
            return ResponseEntity.status(400).body(new ApiResponse("The user with the id : " + appointment.getUserId() + " does not have any appointments "));

        } else return ResponseEntity.status(200).body(new ApiResponse("The request is done successfully ."));


    }


    /// 12 rescheduleAppointment >> user can reschedule their appointment to pick up their claimed items
    @PutMapping("reschedule/{id}")
    public ResponseEntity rescheduleAppointment(@Valid @RequestBody Appointment appointment, Errors e, @PathVariable Integer id) {

        if (e.hasErrors()) {
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }


        if (appointmentService.rescheduleAppointment(appointment, id)) {
            return ResponseEntity.status(200).body(new ApiResponse("The appointment is added successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The appointment is not found"));


    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteAppointment(@PathVariable Integer id) {

        if (appointmentService.deleteAppointment(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("The Appointment is deleted successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The Appointment is not found"));

    }


}
