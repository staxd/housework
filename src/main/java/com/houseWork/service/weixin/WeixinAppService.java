package com.houseWork.service.weixin;

import com.houseWork.entity.user.UserInfo;
import com.houseWork.entity.weixin.EnterprisePayDO;
import com.houseWork.entity.weixin.EnterprisePayInfo;
import com.houseWork.entity.weixin.EnterprisePayParam;
import com.houseWork.entity.weixin.MessageInfo;
import com.houseWork.entity.weixin.OrderInfo;
import com.houseWork.entity.weixin.OrderResponseInfo;
import com.houseWork.entity.weixin.RefundInfo;
import com.houseWork.entity.weixin.UserPayParam;
import com.houseWork.entity.weixin.UserRefundInfo;
import com.houseWork.entity.weixin.UserRefundParam;
import com.alibaba.fastjson.JSONObject;
import com.houseWork.entity.response.ResultCode;
import com.houseWork.service.weixin.domin.WeixinGeneralResult;
import com.houseWork.service.weixin.weixinApp.WeixinAppCoding;
import com.houseWork.service.weixin.weixinApp.WeixinAppURL;
import com.houseWork.utils.AesUtils;
import com.houseWork.utils.ArithUtils;
import com.houseWork.utils.DateUtil;
import com.houseWork.utils.JsonUtils;
import com.houseWork.utils.PayUtils;
import com.houseWork.utils.RandomUtil;
import com.houseWork.utils.SignatureUtils;
import com.houseWork.utils.XmlToMapUtils;
import com.jpay.util.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("weixinAppLoginService")
public class WeixinAppService {

    private static CloseableHttpClient httpClient = null;

    private static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = HttpClientBuilder.create().build();
        }
        return httpClient;
    }
    /**
     * 微信登陆
     *
     * @param platCode
     * @param platUserInfoMap
     * @return
     */
    public static WeixinGeneralResult<UserInfo> getUserInfo(String platCode, Map<String, String> platUserInfoMap) {
        WeixinGeneralResult<UserInfo> result = new WeixinGeneralResult<UserInfo>();
        String url = String.format(WeixinAppURL.USER_INFO_URL, WeixinAppCoding.APP_ID, WeixinAppCoding.SECRET, platCode);
        URI uri = URI.create(url);
        HttpGet get = new HttpGet(uri);
        HttpResponse response;
        try {
            response = getHttpClient().execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                StringBuilder sb = new StringBuilder();

                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    sb.append(temp);
                }

                Map<String, Object> resultMap = JsonUtils.getMap4Json(sb.toString().trim());
                String sessionkey = (String) resultMap.get("session_key");
                if (StringUtils.isBlank(sessionkey)) {
                    result.setCode(ResultCode.FAIL);
                    result.setMessage("微信小程序登录异常，code换取session_key失败");
                    return result;
                }
                // STEP2 获取解密用户关键信息
                String encryptedData = platUserInfoMap.get("encryptedData");
                String iv = platUserInfoMap.get("iv");
                String decryptedData = AesUtils.decrypt(encryptedData, sessionkey, iv);
                Map<String, Object> decryptedDataMap = JsonUtils.getMap4Json(decryptedData.trim());
                UserInfo userInfo = new UserInfo();
                userInfo.setOpenId((String) decryptedDataMap.get("openId"));
                userInfo.setHeadimgurl((String) decryptedDataMap.get("avatarUrl"));
                userInfo.setNickName((String) decryptedDataMap.get("nickName"));
                userInfo.setCountry((String) decryptedDataMap.get("country"));
                userInfo.setProvince((String) decryptedDataMap.get("province"));
                userInfo.setCity((String) decryptedDataMap.get("city"));
                userInfo.setSex((Integer) decryptedDataMap.get("gender"));
                userInfo.setUnionId((String) decryptedDataMap.get("unionId"));
                userInfo.setSessionKey(sessionkey);
                if (StringUtils.isBlank(userInfo.getNickName()) || StringUtils.isBlank(userInfo.getOpenId())) {
                    result.setCode(ResultCode.FAIL);
                    result.setMessage("微信小程序登录异常，返回信息不全");
                    return result;
                }
                result.setDataResult(userInfo);
            }
        } catch (ClientProtocolException e) {
            result.setMessage("微信小程序登录异常");
            result.setCode(ResultCode.FAIL);
        } catch (IOException e) {
            result.setMessage("微信小程序登录异常");
            result.setCode(ResultCode.FAIL);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
	 * 微信支付
	 * @params [param, ip, orederId, notifyUrl]
	 * @return com.houseWork.service.weixin.domin.WeixinGeneralResult<com.houseWork.entity.weixin.OrderResponseInfo>
	 * @date 2019/7/27 15:57
	 */
	public static WeixinGeneralResult<OrderResponseInfo> wxPay(UserPayParam param, String ip, String orederId,
			String notifyUrl) {
		WeixinGeneralResult<OrderResponseInfo> result = new WeixinGeneralResult<>();
		// 将钱 转换成 分
		double times = 100.00;
		double cost = param.getFee();
		double sum = ArithUtils.mul(cost, times);
		int fee = (int) sum;

		OrderInfo orderInfo = new OrderInfo();
		String platCode = param.getPlatCode();
		// 获取openId
		Map<String, Object> loginResultMap = null;
		String loginUrl = String.format(WeixinAppURL.USER_INFO_URL, WeixinAppCoding.APP_ID, WeixinAppCoding.SECRET, platCode);
		URI loginUri = URI.create(loginUrl);
		HttpGet get = new HttpGet(loginUri);
		HttpResponse loginResponse;
		try {
			loginResponse = getHttpClient().execute(get);
			if (loginResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = loginResponse.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
					sb.append(temp);
				}

				loginResultMap = JsonUtils.getMap4Json(sb.toString().trim());

				String sessionkey = (String) loginResultMap.get("session_key");
				if (StringUtils.isBlank(sessionkey)) {
					result.setCode(ResultCode.FAIL);
					result.setMessage("微信小程序登录异常，code换取session_key失败");
					return result;
				}
			}
		} catch (Exception e) {
			result.setCode(ResultCode.FAIL);
			result.setMessage(e.getMessage());
		}

		URI uri = URI.create(WeixinAppURL.PAY_URL);
		HttpPost post = new HttpPost(uri);
		HttpResponse response;
		try {
			// 构建小程序下单接口需要的数据
			String openid = (String) loginResultMap.get("openid");
			orderInfo.setAppid(WeixinAppCoding.APP_ID);
			orderInfo.setMch_id(WeixinAppCoding.MCH_ID);
			orderInfo.setNonce_str(RandomUtil.getRandomCharString(32));
			orderInfo.setBody("绿泡泡家政服务");
			// 测试订单号
			orderInfo.setOut_trade_no(orederId);
			orderInfo.setTotal_fee(fee);
			orderInfo.setSpbill_create_ip(ip);// ip
			orderInfo.setNotify_url(notifyUrl);
			orderInfo.setTrade_type(WeixinAppURL.TRADE_TYPE);
			orderInfo.setOpenid(openid);
			orderInfo.setSign_type("MD5");
			String sign = SignatureUtils.getSign(orderInfo, WeixinAppCoding.KEY);
			// 签名
			orderInfo.setSign(sign);
			// 将数据转换成xml格式
			XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
			xStream.alias("xml", OrderInfo.class);
			String xml = xStream.toXML(orderInfo);
			post.setEntity(new StringEntity(xml, "utf-8"));
			// 调用支付统一下单api
			response = getHttpClient().execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {

				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
					sb.append(temp);

				}
				Map<String, Object> resultMap = XmlToMapUtils.getResult(sb.toString().trim());
				String returnCode = (String) resultMap.get("return_code");
				if (returnCode == "SUCCESS" || returnCode.equals("SUCCESS")) {
					OrderResponseInfo info = new OrderResponseInfo();
					String pkg = (String) resultMap.get("prepay_id");
					info.setNonceStr((String) resultMap.get("nonce_str"));
					info.setPkg("prepay_id=" + pkg);
					info.setSignType("MD5");
					info.setAppId(WeixinAppCoding.APP_ID);
					String dateTime = DateUtil.formatDate(new Date(), DateUtil.dtLong);
					info.setTimeStamp(dateTime);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("nonceStr", (String) resultMap.get("nonce_str"));
					map.put("package", "prepay_id=" + (String) resultMap.get("prepay_id"));
					map.put("signType", "MD5");
					map.put("appId", WeixinAppCoding.APP_ID);
					map.put("timeStamp", dateTime);
					// 再次签名
					// info.setPaySign((String) resultMap.get(SignatureUtils.getSign(map, KEY)));
					info.setPaySign(SignatureUtils.getSign(map, WeixinAppCoding.KEY));

					result.setDataResult(info);

				}
			}
		} catch (ClientProtocolException e) {
			result.setCode(ResultCode.FAIL);
			result.setMessage("小程序支付异常");
			e.printStackTrace();
		} catch (IOException e) {
			result.setCode(ResultCode.FAIL);
			result.setMessage("小程序支付异常");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			result.setCode(ResultCode.FAIL);
			result.setMessage("小程序支付异常");
		}

		return result;
	}

	/**
	 * 微信退款
	 * @params [param]
	 * @return com.houseWork.service.weixin.domin.WeixinGeneralResult<com.houseWork.entity.weixin.UserRefundInfo>
	 * @date 2019/7/27 15:57
	 */
	public static WeixinGeneralResult<UserRefundInfo> refund(UserRefundParam param) {
		WeixinGeneralResult<UserRefundInfo> result = new WeixinGeneralResult<>();
		double times = 100.00;
		double refundFee = param.getRefundFee();
		double totalFee = param.getTotalFee();
		double refund_sum = ArithUtils.mul(refundFee, times);
		double total_sum = ArithUtils.mul(totalFee, times);
		int refund_fee = (int) refund_sum;
		int total_fee = (int) total_sum;
		RefundInfo refundInfo = new RefundInfo();
		refundInfo.setAppid(WeixinAppCoding.APP_ID);
		refundInfo.setMch_id(WeixinAppCoding.MCH_ID);
		refundInfo.setNonce_str(RandomUtil.getRandomCharString(32));
		refundInfo.setOut_refund_no(RandomUtil.getRandomNumString(64));
		refundInfo.setOut_trade_no(param.getOrderId());
		refundInfo.setRefund_fee(refund_fee);
		refundInfo.setTotal_fee(total_fee);
		String sign = "";
		try {
			sign = SignatureUtils.getSign(refundInfo, WeixinAppCoding.KEY);
			refundInfo.setSign(sign);
			XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
			xStream.alias("xml", RefundInfo.class);
			String xml = xStream.toXML(refundInfo);
			String resultStr = PayUtils.post(WeixinAppURL.REFUND_URL, xml, WeixinAppURL.KEY_PATH, WeixinAppCoding.MCH_ID);
			Map map = XmlToMapUtils.getResult(resultStr);
			String returnCode = (String) map.get("return_code");
			if (returnCode.equals("SUCCESS")) {
				UserRefundInfo refundDO = new UserRefundInfo();
				refundDO.setAppid((String) map.get("appid"));
				refundDO.setMch_id((String) map.get("mch_id"));
				refundDO.setNonce_str((String) map.get("nonce_str"));
				refundDO.setSign((String) map.get("sign"));
				refundDO.setTransaction_id((String) map.get("transaction_id"));
				refundDO.setOut_refund_no((String) map.get("out_refund_no"));
				refundDO.setOut_trade_no((String) map.get("out_trade_no"));
				refundDO.setRefund_id((String) map.get("refund_id"));
				refundDO.setRefund_fee(Integer.valueOf((String) map.get("refund_fee")));
				refundDO.setTotal_fee(Integer.valueOf((String) map.get("total_fee")));
				refundDO.setCash_fee(Integer.valueOf((String) map.get("cash_fee")));
				result.setDataResult(refundDO);

			} else {
				result.setCode(ResultCode.FAIL);
				result.setMessage("退款失败");
			}

		} catch (IllegalAccessException e) {
			result.setCode(ResultCode.FAIL);
			result.setMessage("退款时签名出错");
		}

		// refundInfo.setOut_refund_no(out_refund_no);
		return result;
	}

	/**
	 * 企业付款
	 * 
	 * @param param
	 * @param orderId
	 * @param ip
	 * @return
	 */
	public static WeixinGeneralResult<EnterprisePayDO> enterprisePay(EnterprisePayParam param, String orderId, String ip) {
		WeixinGeneralResult<EnterprisePayDO> result = new WeixinGeneralResult<>();
		EnterprisePayDO enterprisePayDO = new EnterprisePayDO();
		double times = 100.00;
		double fee = param.getFee();
		double total_sum = ArithUtils.mul(fee, times);
		int total_fee = (int) total_sum;
		// 获取openId
		Map<String, Object> loginResultMap = null;
		String loginUrl = String.format(WeixinAppURL.USER_INFO_URL, WeixinAppCoding.APP_ID, WeixinAppCoding.SECRET, param.getPlatCode());
		URI loginUri = URI.create(loginUrl);
		HttpGet get = new HttpGet(loginUri);
		HttpResponse loginResponse;
		try {
			loginResponse = getHttpClient().execute(get);
			if (loginResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = loginResponse.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
					sb.append(temp);
				}

				loginResultMap = JsonUtils.getMap4Json(sb.toString().trim());

				String sessionkey = (String) loginResultMap.get("session_key");
				if (StringUtils.isBlank(sessionkey)) {
					result.setCode(ResultCode.FAIL);
					result.setMessage("微信小程序登录异常，code换取session_key失败");
					return result;
				}
			}
		} catch (Exception e) {
			result.setCode(ResultCode.FAIL);
			result.setMessage(e.getMessage());
		}
		URI uri = URI.create(WeixinAppURL.PAY_URL);
		HttpPost post = new HttpPost(uri);
		HttpResponse response;
		try {
			EnterprisePayInfo info = new EnterprisePayInfo();
			String openid = (String) loginResultMap.get("openid");
			info.setAmount(total_fee);
			info.setCheck_name("NO_CHECK");
			info.setDesc("在元培余额提现:" + String.valueOf(fee) + "元");
			info.setMch_appid(WeixinAppCoding.APP_ID);
			info.setMchid(WeixinAppCoding.MCH_ID);
			info.setNonce_str(RandomUtil.getRandomCharString(32));
			info.setOpenid(openid);
			info.setPartner_trade_no(orderId);
			info.setSpbill_create_ip(ip);
			String sign = null;
			sign = SignatureUtils.getSign(info, WeixinAppCoding.KEY);
			info.setSign(sign);
			XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
			xStream.alias("xml", EnterprisePayInfo.class);
		
			String xml=xStream.toXML(info);
			try {
				xml=new String(xml.getBytes(),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(xml);
			// 请求官方企业付款接口
			String resultStr = PayUtils.post(WeixinAppURL.ENTERPRISE_PAY_URL, xml, WeixinAppURL.KEY_PATH, WeixinAppCoding.MCH_ID);
			Map map = XmlToMapUtils.getResult(resultStr);
			String returnCode = (String) map.get("return_code");
			String resultCode = (String) map.get("result_code");
			if (returnCode.equals("SUCCESS") && resultCode.equals("SUCCESS")) {
				enterprisePayDO.setOpenId(openid);
				enterprisePayDO.setFee(total_fee);
				enterprisePayDO.setPartner_trade_no( (String)map.get("partner_trade_no"));
				enterprisePayDO.setPayment_no((String)map.get("payment_no"));
				enterprisePayDO.setPayment_time((String)map.get("payment_time"));
				result.setDataResult(enterprisePayDO);	
				//System.out.println(JSONObject.toJSONString(object).toJSON());
			} else {
				//System.out.println(Json(map));
				result.setCode(ResultCode.FAIL);
				result.setMessage(
						"企业付款失败," + "错误" + (String) map.get("err_code") + ":" + (String) map.get("err_code_des"));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 获取access_token
	 * 
	 * @return
	 */
	public static String getAccessToken() {
		Map<String, Object> resultMap = null;
		String accessToken = null;
		String accessTokenUrl = String.format(WeixinAppURL.ACCESS_TOKEN_URL, WeixinAppCoding.APP_ID, WeixinAppCoding.SECRET);
		URI uri = URI.create(accessTokenUrl);
		HttpGet get = new HttpGet(uri);
		HttpResponse response;
		try {
			response = getHttpClient().execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
					sb.append(temp);
				}

				resultMap = JsonUtils.getMap4Json(sb.toString().trim());

				accessToken = (String) resultMap.get("access_token");
				if (StringUtils.isBlank(accessToken)) {
					return accessToken;
				}
			}
		} catch (Exception e) {

		}
		return accessToken;
	}

	/**
	 * 发送通知消息
	 * 
	 * @param messageInfo
	 * @return
	 */
	public static WeixinGeneralResult messagePut(MessageInfo messageInfo) {
		WeixinGeneralResult responseDO = new WeixinGeneralResult<>();
		// 获取acessToken
		String accessToken = getAccessToken();
		if (StringUtils.isBlank(accessToken)) {
			responseDO.setCode(ResultCode.FAIL);
			responseDO.setMessage("access_token为空");
			return responseDO;
		}
		String messagePut = String.format(WeixinAppURL.MESSAGE_PUT, accessToken);
		URI uri = URI.create(messagePut);
		HttpPost post = new HttpPost(uri);
		HttpResponse response;
		/*
		 * XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-",
		 * "_"))); xStream.alias("xml", MessageInfo.class); String xml =
		 * xStream.toXML(messageInfo);
		 */
		String json = JSONObject.toJSONString(messageInfo);
		System.out.println(json);
		StringEntity stringentity = new StringEntity(json, ContentType.create("application/json", "UTF-8"));
		post.setEntity(stringentity);
		try {
			response = getHttpClient().execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {

				HttpEntity entity = response.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuilder sb = new StringBuilder();

				for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
					sb.append(temp);

				}
				System.out.println(sb);
				Map<String, Object> resultMap = JsonUtils.getMap4Json(sb.toString().trim());
				int returnCode = Integer.valueOf(String.valueOf(resultMap.get("errcode")));
				System.out.println();
				if (returnCode == 0) {
					responseDO.setMessage("发送成功");
					return responseDO;
				} else {
					responseDO.setCode(ResultCode.FAIL);
					responseDO.setMessage("发送失败，错误码_" + returnCode + ":" + (String) resultMap.get("errMsg"));
				}
			}
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			responseDO.setCode(ResultCode.FAIL);
			responseDO.setMessage(e.getMessage());
		} catch (IOException e) {

			e.printStackTrace();
			responseDO.setCode(ResultCode.FAIL);
			responseDO.setMessage(e.getMessage());
		}

		return responseDO;
	}
}
