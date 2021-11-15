/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class utilityHelper {

    public static boolean checkpass(JPasswordField txt) {
	if (txt.getPassword().length > 0) {
	    return true;
	} else {
	    txt.setBackground(Color.yellow);
	    JOptionPane.showMessageDialog(txt.getRootPane(), "Không được để trống mật khẩu");
	    return false;
	}

    }

    public static boolean checknull(JTextField txt) {
	txt.setBackground(Color.white);
	if (txt.getText().trim().length() >0) {
	    return true;
	} else {
	    txt.setBackground(Color.yellow);
	    JOptionPane.showMessageDialog(txt.getRootPane(), "Vui lòng không để trống thông tin");
	    return false;
	}
    }
}
