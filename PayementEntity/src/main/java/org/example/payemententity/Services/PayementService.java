package org.example.payemententity.Services;

import org.example.payemententity.Models.Payement;

import java.util.List;

public interface PayementService {

    public String proceedToPayement(Payement payement);
    public String validatePayement(Integer id);
    public List<Payement> listOfFactures(Integer userId);
}
