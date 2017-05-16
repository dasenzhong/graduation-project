package bishe.dgut.edu.cn.reallygoodapp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * ZDX
 *
 * @GitHub: https://github.com/dasenzhong
 * Created by Administrator.
 * Created on 2017/5/12.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resume implements Serializable {

    Integer id;

    String name;				//简历名字
    String birthday;			//出生年月
    String telephone;			//电话
    String liveProvince;		//省份
    String liveCity;			//城市
    String liveTown;			//城镇
    String school;				//学校

    boolean isPrefect;			//是否完善

    StudentUser studentUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isPrefect() {
        return isPrefect;
    }

    public void setPrefect(boolean prefect) {
        isPrefect = prefect;
    }

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
