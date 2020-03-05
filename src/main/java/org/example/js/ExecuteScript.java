package org.example.js;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.example.js.*;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/*

执行js脚本
 */
public class ExecuteScript {

	public static void main(String[] args) {
		String username = "20161222159";
		String password = "132633";
		ExecuteScript executeScript = new ExecuteScript();

		String ss = executeScript.executeScript(username, password);
		System.out.println(ss);
	}

	private static InputStream is1;
	private static BufferedReader br1;
	private static InputStream is2;
	private static BufferedReader br2;

	private static String lt = "LT-71527-j1u929daaX5xR60LrxcWAqSWd0a5fW-tpass";


	public String executeScript(String exponent, String password) {

		String enpassword = null;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {

			// initJS();

			/*
			 * jar包下
			 */
/*
			String path1 = "resources/base64.js";
			is1 = ExecuteScript.class.getClassLoader().getResourceAsStream(path1);
			br1 = new BufferedReader(new InputStreamReader(is1));

			String path2 = "resources/jsbn.js";
			is2 = ExecuteScript.class.getClassLoader().getResourceAsStream(path2);
			br2 = new BufferedReader(new InputStreamReader(is2));

*/
			/*
			 * IDEA 下
			 */

			  String path1 = "des.js";
			  is1=ExecuteScript.class.getClassLoader().getResourceAsStream(path1);
			  br1=new BufferedReader(new InputStreamReader(is1));



			String path2 = "passwordencrypt.js";
			is2=ExecuteScript.class.getClassLoader().getResourceAsStream(path2);
			br2=new BufferedReader(new InputStreamReader(is2));

			engine.eval(br1);
			engine.eval(br2);


			if (engine instanceof Invocable) {
				Invocable invocable = (Invocable) engine;
				JavaScriptInterface executeMethod = invocable.getInterface(JavaScriptInterface.class);
				if(executeMethod==null){
					System.out.println("sdsdsd");
				}
				enpassword = executeMethod.getEncryptPassword(exponent, password,lt);

			}
			return enpassword;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} finally {
			try {
				br1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				is1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				br2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				is2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}


		}

	}

}
