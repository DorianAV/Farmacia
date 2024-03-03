package com.utsem.farmacia.Controller;


import com.utsem.farmacia.Model.Fabricante;
import com.utsem.farmacia.Service.FabricanteService;
import com.utsem.farmacia.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fabricante")
public class FabricanteController {
    @Autowired
    FabricanteService fabricanteService;

    @PostMapping("registro")
    public String Registro(@RequestBody Fabricante fabricante) {
        return fabricanteService.registro(fabricante);
    }
}
