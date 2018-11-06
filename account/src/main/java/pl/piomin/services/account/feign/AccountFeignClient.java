package pl.piomin.services.account.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("account-client")
public interface AccountFeignClient {

	@RequestMapping(value = "/accoutFeign/getAccountById", method = RequestMethod.GET, consumes = "application/json")
	public String getAccountById(@RequestParam(value = "accountId", required = true) String accountId);

}
