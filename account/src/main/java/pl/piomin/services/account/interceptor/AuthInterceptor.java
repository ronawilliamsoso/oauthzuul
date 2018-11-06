package pl.piomin.services.account.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import pl.piomin.services.account.common.RestTemplateFactory;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// allow cross access
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Allow", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");

		if (true) {
			return true;
		}
		// filter url
		String url = request.getRequestURI();
		if (url.indexOf("/indexing") != -1 || url.indexOf("/outInterface") != -1 || url.indexOf("/me") != -1
				|| url.indexOf("/health") != -1 || url.indexOf("/configuration/ui") != -1
				|| url.indexOf("/v2/api-docs") != -1 || url.indexOf("/swagger-resources") != -1) {
			return true;
		}

		/*
		 * if(true) return true;
		 */

		String token = request.getParameter("token");

		JSONObject result = new JSONObject();
		// token is null
		if (StringUtils.isBlank(token)) {
			result.put("status", 300);
			result.put("msg", "token is null");
			response.getWriter().write(result.toString());
			response.getWriter().flush();
			return false;
		}

		HttpSession session = request.getSession();
		Object user = session.getAttribute(token);
		if (user != null) {
			return true;
		}

		RestTemplate rt = RestTemplateFactory.getTemplate();
		JSONObject checkuser = rt.getForObject(url, JSONObject.class);

		if (checkuser == null) {
			result.put("status", 300);
			result.put("msg", "token is erro");

			response.getWriter().write(result.toString());
			response.getWriter().flush();
			return false;

		} else {
			session.setAttribute(token, checkuser);
			return true;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
