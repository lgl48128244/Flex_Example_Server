package flex3Bible;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ROService {

	public ROService() {
	}

	public String helloWorld() {
		return "Hello from the world of Java";
	}

	public List getArray() {
		Map stateObj;
		List ar = new ArrayList();
		stateObj = new HashMap();
		stateObj.put("首都", "北京");
		stateObj.put("name", "北京");
		ar.add(stateObj);
		stateObj = new HashMap();
		stateObj.put("首都", "奥林匹克");
		stateObj.put("name", "华盛顿");
		ar.add(stateObj);
		stateObj = new HashMap();
		stateObj.put("首都", "塞伧");
		stateObj.put("name", "旧金山");
		ar.add(stateObj);
		return ar;
	}

	public String concatValues(String val1, String val2) {
		return "You passed values " + val1 + " and " + val2;
	}

	public String setContact(Contact myContact) {
		return "Contact sent from server: " + myContact.getFirstName() + " " + myContact.getLastName();
	}

	public Contact getContact(String val1, String val2) {
		Contact myContact = new Contact();
		myContact.setFirstName(val1);
		myContact.setLastName(val2);
		return myContact;
	}
}
