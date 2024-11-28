

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.sql.DriverManager;


public class Movalidation {
	Hhelper hh = new Hhelper();
	public String mess = "";
	String ss;
	Boolean yes = false;

	public boolean ftitlevalid(String content) {
		if (hh.zempty(content)) {
			mess= "Title is empty !";
			return false;
		}
		return true;
	}
	public boolean fgernevalid(String content) {
		if (hh.zempty(content)) {
			mess = "Gerne is empty !";
			return false;
		}
		return true;
	}
	public boolean yearvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Year is empty !";
			return false;
		}
		return true;
	}
	public boolean durationvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Duration is empty !";
			return false;
		}
		return true;
	}
	public Boolean phonevalid(String content) {
		if (hh.zempty(content)) {
			mess = "Phone is empty !";
			return false;
		} else if (content.length() < 7) {
			mess = "Phone is short !";
			return false;
		}
		return true;
	}

	public boolean emailvalid(String content) {
		if (hh.zempty(content)) {
		     mess = "Email is empty !";
			return false;
		} else if (content.indexOf(".") == -1 || content.indexOf("@") == -1) {
			mess = "Incorrect email  !";
			return false;
		}
		return true;
	}
	public boolean datevalid(String content) {
		if (hh.zempty(content)) {
			mess = "Date is empty !";
			return false;
		} else {
			if (!hh.validatedate(content, "N")) {
				mess= "Incorrect date !";
				return false;
			}
		}
		return true;
	}
	public Boolean countryvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Country is empty !";
			return false;	
		}
		return true;
	}
}
