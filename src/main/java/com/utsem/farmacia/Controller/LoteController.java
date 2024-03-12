package com.utsem.farmacia.Controller;

import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.Service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lote")
public class LoteController {
    @Autowired
    LoteService loteService;
    @PostMapping("registro")
    public String Registro(@RequestBody LoteDTO lote){
        return loteService.registro(lote);
    }
}
