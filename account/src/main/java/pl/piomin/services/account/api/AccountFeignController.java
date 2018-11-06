package pl.piomin.services.account.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.piomin.services.account.feign.AccountFeignClient;

@RestController
@RequestMapping("/accountFeign")
public class AccountFeignController implements AccountFeignClient {
	@Override
	@RequestMapping(value="/getAccountById",method=RequestMethod.GET)
	public String getAccountById(String accountId) {
		return "hello " + accountId;
	}
}
