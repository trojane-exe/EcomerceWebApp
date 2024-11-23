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
import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("/api/admin")
@Validated

public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(@Qualifier("userServiceAdminImpl") UserService us){
        this.userService = us;
    }

    @PostMapping("/add_user")
    public ResponseEntity<?>addUser(@RequestBody Utilisateur user){
        Map<String,String> response = new HashMap<>();
        String result = userService.addUSer(user);
        if(result.equals("error")){
            response.put("ERROR" , "Missing or invalid data");
            return ResponseEntity.badRequest().body(response);
        } else if (result.equals("user")) {
            response.put("SUCCESS","User added successfully");
            return ResponseEntity.ok().body(response);
        }
        else{
            response.put("SUCCESS","Admin added successfully");
            return ResponseEntity.ok().body(response);
        }

    }

    @GetMapping("/list/users")
    public ResponseEntity<List<Utilisateur>>listUsers(){
        List<Utilisateur> users = userService.listOfUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("profil/{id}")
    public ResponseEntity<Utilisateur> getSingleUser(@PathVariable("id")Integer id){
        Utilisateur user = userService.findUser(id);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(user);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateProfile(@PathVariable("id") Integer id,@RequestBody Utilisateur user){
        Map<String,String> response = new HashMap<>();
        String result = userService.updateUser(id,user);
        if(result.equals("error")){
            response.put("ERROR","Enable to update this user");
            return ResponseEntity.badRequest().body(response);
        }
        else {
            response.put("SUCCESS","User updated successfully");
            return ResponseEntity.ok().body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable("id") Integer id){
        Map<String,String> response = new HashMap<>();
        String result = userService.deleteUser(id);
        if(result.equals("error")){
            response.put("ERROR","User not found");
            return ResponseEntity.badRequest().body(response);
        }
        else{
            response.put("SUCCESS","User deleted");
            return ResponseEntity.ok().body(response);
        }

    }
}
