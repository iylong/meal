package org.nwnu.pub.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Statistics;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	
	// 消息类型常量
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VEDIO = "vedio";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE_PUSH = "scancode_push";
	public static final String MESSAGE_LOCATION = "location";
	
	/**
	 * xml转map集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		
		Map<String, String> map = new HashMap<String, String>();
		
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document dom = reader.read(ins);
		
		Element root = dom.getRootElement();
		List<Element> list = root.elements();
		for(Element element : list){
			map.put(element.getName(), element.getText());
		}
		ins.close();
		return map;
	}
	
	
	
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String getMenuText(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("欢迎关注该公众号，请按照菜单提示进行操作：\n\n");
		stringBuffer.append("1、公众号简介\n");
		stringBuffer.append("2、新鲜果蔬购物商城\n");
		stringBuffer.append("回复？调出此菜单");
		return stringBuffer.toString();
	}
	
	
	

}
