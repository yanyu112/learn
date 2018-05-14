package com.ft.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器
 *
 * @author liuyongtao
 * @create 2018-01-29 9:47
 */
@Component
public class MyFilter extends ZuulFilter {

    private static Logger LOG = LoggerFactory.getLogger(MyFilter.class);

    protected String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        LOG.debug("当前访问IP为：" + ip);
        return ip;
    }

    protected String getClientType(HttpServletRequest request) {
        String clientType = request.getHeader("User-Agent");
        if (clientType == null || clientType.length() == 0 || "unknown".equalsIgnoreCase(clientType)) {
            clientType = "";
        }
        LOG.debug("当前访问客户端类型为：" + clientType);
        return clientType;
    }

    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：<br/>
     * pre：路由之前<br/>
     * routing：路由之时<br/>
     * post： 路由之后<br/>
     * error：发送错误调用<br/>
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 这里可以写逻辑判断，是否要过滤，本文true,永远过滤
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        this.getRemoteHost(request);
        this.getClientType(request);
        LOG.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }
}
