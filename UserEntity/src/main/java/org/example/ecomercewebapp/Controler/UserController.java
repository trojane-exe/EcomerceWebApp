package org.example.ecomercewebapp.Controler;

import lombok.Data;
import org.example.ecomercewebapp.Model.Utilisateur;
import org.example.ecomercewebapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Data
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userServiceImpl")UserService us){
        this.userService = us;
    }

    @PostMapping("/add-user")
    public ResponseEntity<?>addUser(@RequestBody Utilisateur user){
        Map<String,String> response = new HashMap<>();
        String result = userService.addUSer(user);
        if(result != null){
            response.put("Enable to add user",
                    " Missing data or duplicated email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);


        }
        else{
            response.put("SUCCESS","User added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

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
            response.put("SUCCESS","User deleted successfully");
            return ResponseEntity.ok().body(response);
        }
        else{
            response.put("Enable to delete","User not found");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
