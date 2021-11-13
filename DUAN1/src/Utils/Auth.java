/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import MODEL.NhanVien;

/**
 *
 * @author Admin
 */
public class Auth {

    public static NhanVien user = null;

    public static void clean() {
	Auth.user = null;
    }

    public static boolean islogin() {
	return Auth.user != null;
    }

    public static boolean ismaneger() {
	return Auth.islogin() && user.isVAITRO();
    }
}
