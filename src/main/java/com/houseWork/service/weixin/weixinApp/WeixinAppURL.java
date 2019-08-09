package com.houseWork.service.weixin.weixinApp;

public class WeixinAppURL {
    // 发送模板消息
    public static final String MESSAGE_PUT = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";
    // 获取access_token
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    // 获取用户信息的API_URL
    public static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    // 微信统一下单地址
    public static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 微信退款地址
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    // 微信企业付款地址
    public static final String ENTERPRISE_PAY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    // 证书地址
    public static final String KEY_PATH = "/www/wechat/cert/apiclient_cert.p12";
    // 支付类型
    public static final String TRADE_TYPE = "JSAPI";
}
