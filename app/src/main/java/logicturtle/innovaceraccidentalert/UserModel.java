package logicturtle.innovaceraccidentalert;

/**
 * Created by kunwar on 28/10/17.
 */

public class UserModel {

    public String name;
    public String emergency1;
    public String emergency2;

    public UserModel() {
    }

    public UserModel(String name, String emergency1, String emergency2) {
        this.name = name;
        this.emergency1 = emergency1;
        this.emergency2 = emergency2;
    }


    public String getName() {
        return name;
    }

    public String getEmergency1() {
        return emergency1;
    }

    public String getEmergency2() {
        return emergency2;
    }
}
