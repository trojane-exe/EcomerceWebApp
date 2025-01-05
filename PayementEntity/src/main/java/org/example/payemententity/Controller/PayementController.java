package org.example.payemententity.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpStatus;
import org.example.payemententity.Models.Payement;
import org.example.payemententity.Services.PayementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@Validated
@RequestMapping("/api/payement")
public class PayementController {

    private final PayementService payementService;

    @Autowired
    public PayementController(PayementService payementService) {
        this.payementService = payementService;
    }


    @GetMapping("/myFactures")
    public ResponseEntity<List<Payement>>getPayements(@RequestParam("userId") Integer userId){
        return ResponseEntity.ok(payementService.listOfFactures(userId));
    }

    @PostMapping("/proceed")
    public ResponseEntity<String> proceedToPayement(@RequestBody Payement payement){
        String result = payementService.proceedToPayement(payement);
        if(result.equals("ok")){
            return ResponseEntity.status(HttpStatus.SC_CREATED).body("Created");
        }
        else {
            return ResponseEntity.badRequest().body("error");
        }

    }

    @PutMapping("/validate")
    public ResponseEntity<String> validatePayement(@RequestParam("id")Integer id){
        String result = payementService.validatePayement(id);
        if(result==null){
            return ResponseEntity.status(HttpStatus.SC_CREATED).body("validated");
        }
        else{
            return ResponseEntity.badRequest().body("error");
        }
    }




}
