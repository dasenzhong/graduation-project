package bishe.dgut.edu.cn.reallygoodapp.bean;

import java.io.Serializable;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/12.
 */

public class Resume implements Serializable {

    StudentUser studentUser;

    public StudentUser getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(StudentUser studentUser) {
        this.studentUser = studentUser;
    }
}
