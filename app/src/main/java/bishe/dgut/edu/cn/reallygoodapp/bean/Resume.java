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

    String name;				//简历名字
    String birthday;			//出生年月
    String tlelephone;			//电话
    String liveProvince;		//省份
    String liveCity;			//城市
    String liveTown;			//城镇
    String school;				//学校

    StudentUser studentUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTlelephone() {
        return tlelephone;
    }

    public void setTlelephone(String tlelephone) {
        this.tlelephone = tlelephone;
    }

    public String getLiveProvince() {
        return liveProvince;
    }

    public void setLiveProvince(String liveProvince) {
        this.liveProvince = liveProvince;
    }

    public String getLiveCity() {
        return liveCity;
    }

    public void setLiveCity(String liveCity) {
        this.liveCity = liveCity;
    }

    public String getLiveTown() {
        return liveTown;
    }

    public void setLiveTown(String liveTown) {
        this.liveTown = liveTown;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public StudentUser getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(StudentUser studentUser) {
        this.studentUser = studentUser;
    }
}
