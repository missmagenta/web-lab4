package com.lab4.backend.backend.controller;

import com.google.gson.Gson;
import com.lab4.backend.backend.dto.AttemptDTO;
import com.lab4.backend.backend.dto.ResponseDTO;
import com.lab4.backend.backend.model.Attempt;
import com.lab4.backend.backend.service.AttemptService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttemptController {
    private final AttemptService service;

    private final Gson gson = new Gson();

    public AttemptController(AttemptService service) {
        this.service = service;
    }

    @PostMapping(path="/attempt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String createAttempt(@RequestBody AttemptDTO attemptDTO) {
        Attempt attempt = service.createAttempt(attemptDTO);
        ResponseDTO resp;
        if (attempt != null) {
            attempt.setUser(null);
            resp = new ResponseDTO(200, "Attempt was added", attempt);
        } else {
            resp = new ResponseDTO(400, "Attempt not created. Incorrect user", null);
        }
        return gson.toJson(resp);
    }

    @GetMapping("/attempts")
    @CrossOrigin
    public String getAllAttempts(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PageableDefault(value=5, page=0) Pageable pageable) {
        Page<Attempt> page = service.findAll(jwtToken, pageable);
        List<Attempt> attempt = page.getContent();
        ResponseDTO resp;
        if (attempt != null) {
            for(Attempt a : attempt) {
                a.setUser(null);
            }
            resp = new ResponseDTO(200, "", attempt);
        } else {
            resp = new ResponseDTO(400, "Incorrect user", null);

        }
        return gson.toJson(resp);
    }

    @DeleteMapping("/attempts")
    @CrossOrigin
    public String deleteAllAttempts(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        boolean result = service.deleteAllAttempts(jwtToken);
        ResponseDTO resp;
        if (result) {
            resp = new ResponseDTO(200, "All attempts were deleted", null);
        } else {
            resp = new ResponseDTO(400, "Incorrect user", null);
        }
        return gson.toJson(resp);
    }

    @GetMapping("/attempts/number")
    @CrossOrigin
    public String getNumberOfAllAttemtps(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        Integer number = service.getNumberOfAllAttemtps(jwtToken);
        ResponseDTO resp;
        if (number != null) {
            resp = new ResponseDTO(200, "", number);
        } else {
            resp = new ResponseDTO(400, "Incorrect user", null);
        }
        return gson.toJson(resp);
    }
    
    @GetMapping("/ping")
    @CrossOrigin
    public String ping() {
        return "{\"pong\":true}";
    }

}