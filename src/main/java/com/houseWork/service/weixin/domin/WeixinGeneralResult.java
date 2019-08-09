package com.houseWork.service.weixin.domin;

import com.houseWork.entity.response.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class WeixinGeneralResult <T>{
	private ResultCode code = ResultCode.SUCCESS;
    /**
     * 消息
     */
    private String message;

    private T dataResult;
    
    
}
