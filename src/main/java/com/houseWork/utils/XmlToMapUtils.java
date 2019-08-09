package com.houseWork.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlToMapUtils {
	/**
	 * 
	 * <p>
	 * Description:解析XML<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月12日
	 * @param xml
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getResult(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Iterator<Element> it = root.elementIterator();
			while (it.hasNext()) {
				Element element = it.next();
				map.put(element.getName(), element.getTextTrim());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;

	}

	public static void main(String[] a) {
		String str = "<?xml version='1.0' encoding='UTF-8'?><m><op_code>Y</op_code><op_info>04558613-9ab1-4d5c-8ad5-96bfee9e2165</op_info></m>";
		Map<String, Object> map = getResult(str);
		for (String key : map.keySet()) {
			System.out.println("key=" + key);
			System.out.println("value=" + map.get(key));
		}
	}

}
