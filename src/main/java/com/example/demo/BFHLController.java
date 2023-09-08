package com.example.demo;

import com.example.demo.entity.InputData;
import com.example.demo.entity.OperationCodeResponse;
import com.example.demo.entity.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bfhl")
public class BFHLController {

    private static final String USER_ID = "john_doe_17091999";
    private static final String EMAIL = "john@xyz.com";
    private static final String ROLL_NUMBER = "ABCD123";

    @PostMapping
    public ResponseEntity<?> processInput(@RequestBody InputData inputData) {
        try {
            ResponseData responseData = new ResponseData();
            responseData.setIs_success(true);
            responseData.setUser_id(USER_ID);
            responseData.setEmail(EMAIL);
            responseData.setRoll_number(ROLL_NUMBER);
            responseData.setNumbers(new ArrayList<>());
            responseData.setAlphabets(new ArrayList<>());
            responseData.setHighest_alphabet(new ArrayList<>());

            for (String item : inputData.getData()) {
                if (item.matches("[0-9]+")) {
                    responseData.getNumbers().add(item);
                } else if (item.matches("[a-zA-Z]")) {
                    responseData.getAlphabets().add(item);
                }
            }

            if (!responseData.getAlphabets().isEmpty()) {
                char highest = 'A';
                for (String alphabet : responseData.getAlphabets()) {
                    char charValue = alphabet.charAt(0);
                    if (Character.toUpperCase(charValue) > Character.toUpperCase(highest)) {
                        highest = charValue;
                    }
                }
                responseData.getHighest_alphabet().add(String.valueOf(highest));
            }

            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getOperationCode() {
        try {
            return new ResponseEntity<>(new OperationCodeResponse(1), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
