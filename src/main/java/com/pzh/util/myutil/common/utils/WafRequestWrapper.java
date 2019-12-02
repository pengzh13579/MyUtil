/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pzh.util.myutil.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/***
 * Request请求过滤包装
 */
public class WafRequestWrapper extends HttpServletRequestWrapper {

	private boolean filterXSS = true;
	private boolean filterSQL = true;

	public WafRequestWrapper(HttpServletRequest request, boolean filterXSS, boolean filterSQL) {
		super(request);
		this.filterXSS = filterXSS;
		this.filterSQL = filterSQL;
	}

	public WafRequestWrapper(HttpServletRequest request) {
		this(request, true, true);
	}

	/***
	 * @Description 数组参数过滤
	 * @param parameter
	 * 				过滤参数
	 * @return
	 */
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if ( values == null ) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for ( int i = 0 ; i < count ; i++ ) {
			encodedValues[i] = filterParamString(values[i]);
		}

		return encodedValues;
	}

	/***
	 *
	 * @return
	 */
	@Override
	public Map getParameterMap() {
		Map<String, String[]> primary = super.getParameterMap();
		Map<String, String[]> result = new HashMap<String, String[]>(primary.size());
		for ( Map.Entry<String, String[]> entry : primary.entrySet() ) {
			result.put(entry.getKey(), filterEntryString(entry.getValue()));
		}
		return result;

	}

	/***
	 *
	 * @param rawValue
	 * @return
	 */
	protected String[] filterEntryString(String[] rawValue) {
		for ( int i = 0 ; i < rawValue.length ; i++ ) {
			rawValue[i] = filterParamString(rawValue[i]);
		}
		return rawValue;
	}

	/***
	 * 参数过滤
	 * @param parameter 过滤参数
	 * @return
	 */
	@Override
	public String getParameter(String parameter) {
		return filterParamString(super.getParameter(parameter));
	}

	/***
	 * 请求头过滤
	 * @param name 过滤内容
	 * @return
	 */
	@Override
	public String getHeader(String name) {
		return filterParamString(super.getHeader(name));
	}

	/***
	 * Cookie内容过滤
	 * @return
	 */
	@Override
	public Cookie[] getCookies() {
		Cookie[] existingCookies = super.getCookies();
		if (existingCookies != null) {
			for (int i = 0 ; i < existingCookies.length ; ++i) {
				Cookie cookie = existingCookies[i];
				cookie.setValue(filterParamString(cookie.getValue()));
			}
		}
		return existingCookies;
	}

	/***
	 * 过滤字符串内容
	 * @param rawValue 待处理内容
	 * @return
	 */
	protected String filterParamString(String rawValue) {
		if (null == rawValue) {
			return null;
		}
		String tmpStr = rawValue;
		if (this.filterXSS) {
			tmpStr = stripXSS(rawValue);
		}
		if (this.filterSQL) {
			tmpStr = stripSqlInjection(tmpStr);
		}
		return tmpStr;
	}

	/***
	 * 过滤XSS脚本内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static String stripXSS(String value) {
		String rlt = null;

		if (null != value) {
			rlt = value.replaceAll("", "");

			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			rlt = scriptPattern.matcher(rlt).replaceAll("");

			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			rlt = scriptPattern.matcher(rlt).replaceAll("");
		}

		return rlt;
	}

	/***
	 * 过滤SQL注入内容
	 * @param value
	 * 				待处理内容
	 * @return
	 */
	public static String stripSqlInjection(String value) {
		return (null == value) ? null : value.replaceAll("('.+--)|(--)|(%7C)", "");
	}

	/***
	 * 过滤SQL/XSS注入内容
	 * @param value
	 * @return
	 */
	public static String stripSqlXSS(String value) {
		return stripXSS(stripSqlInjection(value));
	}
}
