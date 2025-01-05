package org.example.payemententity.Services;

import com.netflix.discovery.converters.Auto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.payemententity.Models.EtatEnum;
import org.example.payemententity.Models.Payement;
import org.example.payemententity.Repository.PayementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Data

public class PayementServiceImpl  implements PayementService {


    private final PayementRepository payementRepository;
    @Autowired
    public PayementServiceImpl(PayementRepository payementRepository) {
        this.payementRepository = payementRepository;
    }

    @Override
    public String proceedToPayement(Payement payement) {
        if(payement !=null){
            payement.setStatus(EtatEnum.attente);
            payementRepository.save(payement);
            return "ok";
        }
        else {
            return "error";
        }
    }

    @Override
    public String validatePayement(Integer id) {
        Payement payement = payementRepository.findById(id).orElse(null);
        if(payement!=null){
            payement.setStatus(EtatEnum.payé);
            payementRepository.save(payement);
        }
        return null;
    }

    @Override
    public List<Payement> listOfFactures(Integer userID) {
        return payementRepository.getAllByUserId(userID);
    }
}
