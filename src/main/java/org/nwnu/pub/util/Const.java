package org.nwnu.pub.util;

public class Const {
	
	public static final String lOGIN_SYSUSER = "sysLoginUser";
	public static final String lOGIN_SHOPUSER = "shopLoginUser";
    public static final String lOGIN_StuUSER = "stuLoginUser";
	public static final String SYSDICT_SPEC = "spec";
	public static final String COOKIES_SHOP_USER_ID = "shopUserId";
	public static final String COOKIES_SHOP_OPENID = "shopUserOpenid";
	public static final String COOKIES_SHOP_HEADURL = "shopUserheadurl";
	public static final String COOKIES_SHOP_WEINAME = "shopUserweiname";
	
	public static final String CACHE_SESSION = "session";
	
	public static final int SESSION_TIME = 60 * 60 * 24; // 存储用户微信信息的openid的cookie时间
	public static final int COOKIES_TIME = 60 * 60 * 24 * 7; // 存储用户微信信息的id的cookie时间
	
	// 微信首页地址
	public static final String WEIXIN_INDEX_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaccb6821e1fd111e&redirect_uri=http%3A%2F%2Fwww.bravedawn.cn%2FApp%2F"
			+ "index.do&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
	
	public interface DateSearch{
		String START_TIME = " 0:00:00";
		String END_TIME = " 23:59:59";
	}

}
