package org.example.ecomercewebapp.Controler;

import lombok.Data;
import org.example.ecomercewebapp.Model.Utilisateur;
import org.example.ecomercewebapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Data
@RestController
@RequestMapping("/api/users")
@Validated
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userServiceImpl")UserService us){
        this.userService = us;
    }


    @GetMapping("/profil")
    public ResponseEntity<Utilisateur> viewAccount(@RequestParam("userId")Integer userId){
        return ResponseEntity.ok(userService.findUser(userId));

    }
    @PutMapping("/update-user/{id}")
    public ResponseEntity<?>updateUser(@PathVariable("id") Integer id, @RequestBody Utilisateur user){
        Map<String,String>response = new HashMap<>();
        String result = userService.updateUser(id,user);
        if(result.equals("ok")){
            response.put("SUCCESS","User updated successfully");
            return ResponseEntity.ok().body(response);
        } else if (result.equals("errorInfos")) {
            response.put("Enable to update","You can not change your email or role");
            return ResponseEntity.badRequest().body(response);

        } else{
            response.put("Enable to update","wrong infos or user not existing");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable("id") Integer id){
        Map<String,String> response = new HashMap<>();
        String result  = userService.deleteUser(id);
        if(result.equals("ok")){
            response.put("SUCCESS","Account deleted successfully");
            return ResponseEntity.ok().body(response);
        }
        else{
            response.put("Enable to delete","Account not found");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
