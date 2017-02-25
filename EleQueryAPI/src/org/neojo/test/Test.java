package org.neojo.test;

import java.io.IOException;

import org.neojo.Exception.CheckException;
import org.neojo.Exception.LoginException;
import org.neojo.entity.Dormitory;
import org.neojo.service.ElectricityQuery;

import com.google.gson.Gson;

public class Test {
	public static void main(String[] args) {
		ElectricityQuery qur;
		try {
			String __VIEWSTATE = "/wEPDwUKLTQ0MDY2MDE3Ng9kFgICAQ9kFgYCAQ8QDxYGHg1EYXRhVGV4dEZpZWxkBQhST09NTkFNRR4ORGF0YVZhbHVlRmllbGQFBnJvb21kbR4LXyFEYXRhQm91bmRnZBAVFAbmpbzmoIsEMS0wMQQxLTAyBDEtMDMEMS0wNAQxLTA1BDEtMDYEMS0wNwQxLTA4BDEtMDkEMS0xMAQxLTExBDEtMTIEMS0xMwQxLTE0BDEtMTUEMS0xNgQxLTE3BDEtMTgEMS0xORUUAAQwMTAxBDAxMDIEMDEwMwQwMTA0BDAxMDUEMDEwNgQwMTA3BDAxMDgEMDEwOQQwMTEwBDAxMTEEMDExMgQwMTEzBDAxMTQEMDExNQQwMTE2BDAxMTcEMDExOAQwMTE5FCsDFGdnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnFgECDWQCAw8QDxYGHwAFCFJPT01OQU1FHwEFBnJvb21kbR8CZ2QQFQcG5qW85bGCBzEtMTMtMDEHMS0xMy0wMgcxLTEzLTAzBzEtMTMtMDQHMS0xMy0wNQcxLTEzLTA2FQcABjAxMTMwMQYwMTEzMDIGMDExMzAzBjAxMTMwNAYwMTEzMDUGMDExMzA2FCsDB2dnZ2dnZ2cWAQICZAIFDxAPFgYfAAUIUk9PTU5BTUUfAQUGcm9vbWRtHwJnZBAVIwbmiL/pl7QJMS0xMy0wMjAxCTEtMTMtMDIwMgkxLTEzLTAyMDMJMS0xMy0wMjA0CTEtMTMtMDIwNQkxLTEzLTAyMDYJMS0xMy0wMjA3CTEtMTMtMDIwOAkxLTEzLTAyMDkJMS0xMy0wMjEwCTEtMTMtMDIxMQkxLTEzLTAyMTIJMS0xMy0wMjEzCTEtMTMtMDIxNAkxLTEzLTAyMTUJMS0xMy0wMjE2CTEtMTMtMDIxNwkxLTEzLTAyMTgJMS0xMy0wMjE5CTEtMTMtMDIyMAkxLTEzLTAyMjEJMS0xMy0wMjIyCTEtMTMtMDIyMwkxLTEzLTAyMjQJMS0xMy0wMjI1CTEtMTMtMDIyNgkxLTEzLTAyMjcJMS0xMy0wMjI4CTEtMTMtMDIyOQkxLTEzLTAyMzAJMS0xMy0wMjMxCTEtMTMtMDIzMgkxLTEzLTAyMzMJMS0xMy0wMjM0FSMACDAxMTMwMjAxCDAxMTMwMjAyCDAxMTMwMjAzCDAxMTMwMjA0CDAxMTMwMjA1CDAxMTMwMjA2CDAxMTMwMjA3CDAxMTMwMjA4CDAxMTMwMjA5CDAxMTMwMjEwCDAxMTMwMjExCDAxMTMwMjEyCDAxMTMwMjEzCDAxMTMwMjE0CDAxMTMwMjE1CDAxMTMwMjE2CDAxMTMwMjE3CDAxMTMwMjE4CDAxMTMwMjE5CDAxMTMwMjIwCDAxMTMwMjIxCDAxMTMwMjIyCDAxMTMwMjIzCDAxMTMwMjI0CDAxMTMwMjI1CDAxMTMwMjI2CDAxMTMwMjI3CDAxMTMwMjI4CDAxMTMwMjI5CDAxMTMwMjMwCDAxMTMwMjMxCDAxMTMwMjMyCDAxMTMwMjMzCDAxMTMwMjM0FCsDI2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgQFBGJ1eVIFBXVzZWRSBQxJbWFnZUJ1dHRvbjEFDEltYWdlQnV0dG9uMkAEREnXdORgwrRrf97Nb/bvoVPscSGgIwdfeW938G62";
			Dormitory dor = new Dormitory(13, 2, 24);
			qur = new ElectricityQuery(dor);
			System.out.println(new Gson().toJson(dor));
			System.out.println(qur.checkBalance(__VIEWSTATE));
			System.out.println(qur.checkUsedLog(__VIEWSTATE, 31));
			System.out.println(qur.checkBuyLog(__VIEWSTATE, 300));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}