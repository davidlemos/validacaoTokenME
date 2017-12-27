package br.com.davidlemos.validacaoTokenME;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import br.com.davidlemos.validacaoTokenME.service.TokenService;


@Controller
public class TokenController {
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping(value = "validacao", method = RequestMethod.GET)
	public String validar(@RequestParam("authToken") String authToken, Model model){
		
		TokenService tokenService = new TokenService();
		String email = tokenService.validarToken(authToken);

		if(!email.isEmpty()) {
			String uri = "http://localhost:9000/infopessoa?email="+email;
		     
			Map<String, String> params = new HashMap<String, String>();
			params.put("email", email);
			
			try {
				RestTemplate restTemplate = new RestTemplate();
				String result = restTemplate.getForObject(uri, String.class, params);
												
				model.addAttribute("infoPessoa", result);
				model.addAttribute("msg", "Token válido!");
			}
			catch(Exception e) {
				model.addAttribute("infoPessoa", "");
				model.addAttribute("msg", "Oops! Não foi possível fazer conexão com o BD. Tente novamente!");
			}
			
		}
		else {		
			model.addAttribute("infoPessoa", "");
			model.addAttribute("msg", "Token inválido ou expirado!");
		}
				
		return "validacao";	
	}
		
}
