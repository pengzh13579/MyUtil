package com.pzh.util.myutil.controller;

import com.pzh.util.myutil.common.json.JsonModel;
import com.pzh.util.myutil.common.utils.HttpUtil;
import com.pzh.util.myutil.common.utils.MacAddressUtil;
import com.pzh.util.myutil.common.utils.MoneyToChineseUtil;
import com.pzh.util.myutil.model.ClientInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(tags="所有的工具所用的controller", value="")
@RestController
@RequestMapping ("/utilsController")
public class UtilsController {

    @ApiOperation(value="阿拉伯数字转中文大写", notes="根据用户输入的阿拉伯数字，找到对应的中文大写返回")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "initValue", value = "初始化阿拉伯数字", required = true, dataType = "String"),
            @ApiImplicitParam(name = "response", value = "响应参数不需要传入", required = false, dataType = "HttpServletResponse")
    })
    @RequestMapping(value = "/toggleCase", method = { RequestMethod.POST })
    public String toggleCase(String initValue, HttpServletResponse response) {
        return MoneyToChineseUtil.digitUppercase(Double.parseDouble(initValue));
    }

    @RequestMapping(value = "/getClientInfo", method = { RequestMethod.GET, RequestMethod.POST })
    public JsonModel getClientInfo(HttpServletRequest request) throws Exception {
        JsonModel result = new JsonModel();
        result.setResult(true);
        ClientInfo clientInfo = new ClientInfo();
        String ipAddress = HttpUtil.getIpAddress(request);
        clientInfo.setIpAddress(ipAddress);
        clientInfo.setMacAddress(MacAddressUtil.getMacAddress(ipAddress));
        clientInfo.setUserAgent(request.getHeader("user-agent"));
        result.setData(clientInfo);
        return result;
    }
}
